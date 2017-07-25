package com.ga.listeners;

import org.springframework.beans.factory.annotation.Autowired;

import com.ga.service.EmployeeService;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.TextField;

public class ResetPasswordButtonListener implements Button.ClickListener {

	private static final long serialVersionUID = 1L;

	TextField userName;

	@Autowired
	EmployeeService employeeService;

	public ResetPasswordButtonListener(TextField userName) {
		this.userName = userName;
	}

	@Override
	public void buttonClick(ClickEvent event) {
		employeeService.resetPassword(userName.getValue());
	}
}
