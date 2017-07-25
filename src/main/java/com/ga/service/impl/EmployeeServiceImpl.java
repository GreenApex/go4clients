package com.ga.service.impl;

import java.util.List;
import java.util.UUID;

import com.ga.entity.Employee;
import com.ga.entity.ForgotPassword;
import com.ga.events.UserModifiedEvent;
import com.ga.mapper.EmployeeRepository;
import com.ga.mapper.ForgotPasswordRepository;
import com.ga.service.EmployeeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.vaadin.spring.events.EventBus;

import com.ga.util.PasswordEncrypter;
import com.ga.vaadin.DashboardView;
import com.ga.vaadin.LoginView;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;

@Component
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	EmployeeRepository employeeRepository;

	@Autowired
	ForgotPasswordRepository forgotPasswordRepository;

	@Override
	public List<Employee> findAllBy(Pageable pageable) {
		return employeeRepository.findAllBy(pageable);
	}

	@Override
	public List<Employee> findByNameLikeIgnoreCase(String nameFilter) {
		return employeeRepository.findByNameLikeIgnoreCase(nameFilter);
	}

	@Override
	public Employee findByEmailAndPassword(String name, String password) {
		Employee dbPerson = employeeRepository.findByEmail(name);

		if (dbPerson != null) {
			boolean isPasswordExsist = PasswordEncrypter.checkPassword(
					password, dbPerson.getUserPassword());
			if (isPasswordExsist) {
				return dbPerson;
			}
		}
		return null;
	}

	@Override
	public void delete(Employee employee) {
		employeeRepository.delete(employee);
	}

	@Override
	public void loginUser(String userName, String password) {
		Employee dbPerson = findByEmailAndPassword(userName, password);
		if (dbPerson == null) {
			Notification.show("Please Enter Valid Email Or Password");
		} else {
			VaadinSession.getCurrent().setAttribute("user", dbPerson);
			UI.getCurrent().getNavigator().navigateTo(DashboardView.VIEW_NAME);
		}
	}

	/* Change Password */
	@Override
	public void resetPassword(String userName) {
		Employee dbPerson = employeeRepository.findByEmail(userName);
		if (dbPerson == null) {
			Notification.show("Please Enter Valid Email");
		} else {

			ForgotPassword forgotPassword = forgotPasswordRepository
					.findByUserId(dbPerson.getId());
			if (forgotPassword != null) {
				forgotPasswordRepository.delete(forgotPassword);
			}

			ForgotPassword resetPassword = new ForgotPassword();
			resetPassword.setUserId(dbPerson.getId());
			resetPassword.setToken(UUID.randomUUID().toString());

			forgotPasswordRepository.save(resetPassword);

			// Stub - Send Reset Password Email Notification

			Notification
					.show("Reset Password Link has been sent to your email id");
		}
	}

	@Override
	public ForgotPassword verifyResetPasswordView(ForgotPassword resetPassword,
			String token, Integer userId) {
		ForgotPassword rsPassword = forgotPasswordRepository
				.findByUserId(userId);
		if (rsPassword != null
				&& !rsPassword.getToken().equalsIgnoreCase(token)) {
			return null;
		} else {
			return rsPassword;
		}
	}
	
	@Override
	public void resetPassword(ForgotPassword resetPassword,String oldPassword,String newPassword){
		Employee person = employeeRepository.findOne(resetPassword.getUserId());
		if(PasswordEncrypter.checkPassword(oldPassword, person.getUserPassword())){
			person.setUserPassword(PasswordEncrypter.generateSecuredPasswordHash(newPassword));
			employeeRepository.save(person);
			UI.getCurrent().getNavigator().navigateTo(LoginView.VIEW_NAME);
			Notification.show("Your password has been reset successfully");
			forgotPasswordRepository.delete(resetPassword);
		}else{
			Notification.show("Wrong Password !");
		}
	}

	public void addUser(Employee employee, Boolean isNewEmployee,
			EventBus.UIEventBus eventBus) {
		if (isNewEmployee) {
			if (employeeRepository.findByEmail(employee.getEmail()) != null) {
				Notification.show("User with same email already exsist");
			} else {
				employee.setUserPassword(PasswordEncrypter
						.generateSecuredPasswordHash(employee.getUserPassword()));
				employeeRepository.save(employee);
				eventBus.publish(this, new UserModifiedEvent(employee));
			}
		} else {
			employeeRepository.save(employee);
			eventBus.publish(this, new UserModifiedEvent(employee));
		}
	}
}
