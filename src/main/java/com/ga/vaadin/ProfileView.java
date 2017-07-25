package com.ga.vaadin;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.ga.common.ViewModel;
import com.ga.entity.Employee;
import com.ga.mapper.EmployeeRepository;
import com.ga.service.EmployeeService;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinSession;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;

@SpringView(name = ProfileView.VIEW_NAME)
public class ProfileView extends CustomComponent implements View, ViewModel {

	private static final long serialVersionUID = 1L;

	public static final String VIEW_NAME = "profile";
	static final String LAYOUT_NAME = "profileView";

	Employee employee;

	CustomLayout layout;

	TextField firstName = new TextField("First Name");
	TextField lastName = new TextField("Last Name");
	TextField email = new TextField("Email");
	TextField companyName = new TextField("Company Name");
	TextField companyAddress = new TextField("Company Address");
	TextField companyState = new TextField("State");
	TextField companyZip = new TextField("Zip");

	Button updateProfile = new Button("Update");
	Button cancelProfile = new Button("Cancel");

	HeaderView headerView;
	EmployeeService employeeService;
	EmployeeRepository employeeRepository;

	@Autowired
	public ProfileView(EmployeeService employeeService,
			EmployeeRepository employeeRepository, HeaderView headerView,
			PersonForm f) {
		this.employeeService = employeeService;
		this.employeeRepository = employeeRepository;
		this.headerView = headerView;
	}

	@PostConstruct
	void init() {

		Employee employee = (Employee) VaadinSession.getCurrent().getAttribute(
				"user");

		if (employee == null) {
			VaadinSession.getCurrent().setAttribute("user", null);
			UI.getCurrent().getNavigator().removeView(DashboardView.VIEW_NAME);
			Page.getCurrent().setUriFragment("");
		} else {
			prepareView();
			attachListener();
		}
	}

	@Override
	public void prepareView() {
		layout = new CustomLayout(LAYOUT_NAME);
		layout.addComponent(firstName, "firstName");
		layout.addComponent(lastName, "lastName");
		layout.addComponent(email, "email");
		layout.addComponent(companyName, "companyName");
		layout.addComponent(companyAddress, "companyAddress");
		layout.addComponent(companyState, "companyState");
		layout.addComponent(companyZip, "companyZip");
		layout.addComponent(updateProfile, "updateProfile");
		layout.addComponent(cancelProfile, "cancelProfile");
		layout.addComponent(headerView, "headerView");
		setCompositionRoot(layout);
		setSizeFull();
	};

	@Override
	public void attachListener() {
		updateProfile.addClickListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {

				// TODO - Refactor code
				Employee currentEmployee = new Employee();
				currentEmployee = employeeRepository.findOne(employee.getId());

				currentEmployee.setName(firstName.getValue());
				currentEmployee.setLastName(lastName.getValue());
				currentEmployee.setEmail(email.getValue());
				currentEmployee.setCompanyName(companyName.getValue());
				currentEmployee.setCompanyAddress(companyAddress.getValue());
				currentEmployee.setCompanyState(companyState.getValue());
				currentEmployee.setCompanyZip(companyZip.getValue());

				employeeRepository.save(currentEmployee);
				VaadinSession.getCurrent()
						.setAttribute("user", currentEmployee);
				// UI.getCurrent().getNavigator().navigateTo(DashboardView.VIEW_NAME);
				Notification.show("User Profile Updated");
			}
		});

		cancelProfile.addClickListener(new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent clickEvent) {
				System.out.print("cancel");
				UI.getCurrent().getNavigator()
						.navigateTo(DashboardView.VIEW_NAME);
			}
		});
	};

	@Override
	public void enter(ViewChangeEvent event) {
		employee = (Employee) VaadinSession.getCurrent().getAttribute("user");

		if (employee == null) {
			UI.getCurrent().getNavigator().navigateTo(LoginView.VIEW_NAME);
		} else {
			setProfile();
		}
	}
	
	public void setProfile(){
		firstName.setValue(employee.getName());
		lastName.setValue(employee.getLastName());
		email.setValue(employee.getEmail());
		companyName.setValue(employee.getCompanyName());
		companyAddress.setValue(employee.getCompanyAddress());
		companyState.setValue(employee.getCompanyState());
		companyZip.setValue(employee.getCompanyZip());
	}
}
