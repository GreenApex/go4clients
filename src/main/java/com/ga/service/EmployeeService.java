package com.ga.service;

import java.util.List;

import com.ga.entity.Employee;
import com.ga.entity.ForgotPassword;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.vaadin.spring.events.EventBus;

public interface EmployeeService {

	List<Employee> findAllBy(Pageable pageable);

	List<Employee> findByNameLikeIgnoreCase(String nameFilter);

	public Employee findByEmailAndPassword(@Param("name") String name,
										 @Param("password") String password);
	
	public void delete(Employee employee);
	
	public void loginUser(String userName, String password);
	public void resetPassword(String userName);
	public ForgotPassword verifyResetPasswordView(ForgotPassword resetPassword,String token,Integer userId);
	public void resetPassword(ForgotPassword resetPassword,String oldPassword,String newPassword);
	public void addUser(Employee employee, Boolean isNewEmployee,
			EventBus.UIEventBus eventBus);
}
