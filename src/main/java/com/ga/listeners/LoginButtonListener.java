package com.ga.listeners;

import com.ga.service.EmployeeService;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;

public class LoginButtonListener implements Button.ClickListener {

	private static final long serialVersionUID = 1L;

	EmployeeService employeeService;

	TextField userName;
	PasswordField password;

	public LoginButtonListener(EmployeeService employeeService,
			TextField userName, PasswordField password) {
		this.userName = userName;
		this.password = password;
		this.employeeService = employeeService;
	}

	@Override
	public void buttonClick(ClickEvent event) {
		employeeService.loginUser(userName.getValue(), password.getValue());
	}
}
