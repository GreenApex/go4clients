package com.ga.vaadin;

import org.vaadin.spring.events.EventBus;
import org.vaadin.viritin.fields.MTextField;
import org.vaadin.viritin.form.AbstractForm;
import org.vaadin.viritin.layouts.MFormLayout;
import org.vaadin.viritin.layouts.MVerticalLayout;

import com.ga.entity.Employee;
import com.ga.events.UserModifiedEvent;
import com.ga.service.EmployeeService;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Component;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;

@UIScope
@SpringComponent
public class PersonForm extends AbstractForm<Employee> {

	private static final long serialVersionUID = 1L;

	EmployeeService employeeService;
	EventBus.UIEventBus eventBus;

	TextField companyName = new MTextField("Company Name");
	TextField companyAddress = new MTextField("Company Address");
	TextField companyState = new MTextField("State");
	TextField companyZip = new MTextField("Zip");
	PasswordField userPassword = new PasswordField("Password");
	TextField name = new MTextField("Name");
	TextField lastName = new MTextField("lastName");
	TextField email = new MTextField("Email");
	TextField phoneNumber = new MTextField("Phone");

	Boolean isNewEmployee = true;

	PersonForm(EmployeeService employeeService, EventBus.UIEventBus b) {
		super(Employee.class);
		this.eventBus = b;
		this.employeeService = employeeService;
		/* Subscribe event */
		setSavedHandler(person -> {
			employeeService.addUser(person, isNewEmployee, eventBus);
		});
		setResetHandler(p -> eventBus.publish(this, new UserModifiedEvent(p)));
		setSizeUndefined();
	}

	@Override
	protected void bind() {
		super.bind();
	}

	@Override
	protected Component createContent() {
		return new MVerticalLayout(new MFormLayout(companyName, companyAddress,
				companyState, companyZip, name, userPassword, lastName, email,
				phoneNumber).withWidth(""), getToolbar()).withWidth("");
	}

	/* Getter - Setter */
	public Boolean getNewEmployee() {
		return isNewEmployee;
	}

	public void setNewEmployee(Boolean newEmployee) {
		isNewEmployee = newEmployee;
	}
}
