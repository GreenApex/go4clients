package com.ga.vaadin;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.ga.common.ViewModel;
import com.ga.entity.ForgotPassword;
import com.ga.service.EmployeeService;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;

@SpringView(name = ResetPasswordView.VIEW_NAME)
public class ResetPasswordView extends CustomComponent implements View,
		ViewModel {
	private static final long serialVersionUID = 1L;
	public static final String VIEW_NAME = "resetPassword";
	static final String LAYOUT_NAME = "resetPasswordView";

	CustomLayout layout;

	TextField oldPassword = new TextField("Old Password");
	TextField newPassword = new TextField("New Password");
	Button resetPasswordButton = new Button("Reset");

	ForgotPassword resetPassword = new ForgotPassword();
	EmployeeService employeeService;

	@Autowired
	public ResetPasswordView(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	@PostConstruct
	void init() {
		prepareView();
		attachListener();
		setCompositionRoot(layout);
		setSizeFull();
	}

	@Override
	public void enter(ViewChangeEvent event) {
		if (event.getParameters() != null) {
			String[] parameters = event.getParameters().split("/");
			Integer userId = Integer.parseInt(parameters[0]);
			String token = parameters[1];
			resetPassword = employeeService.verifyResetPasswordView(resetPassword,
					token, userId);
			if (resetPassword == null) {
				UI.getCurrent().getNavigator().navigateTo(LoginView.VIEW_NAME);
			}
		}
	}

	@Override
	public void prepareView() {
		layout = new CustomLayout(LAYOUT_NAME);
		layout.addComponent(oldPassword, "oldPassword");
		layout.addComponent(newPassword, "newPassword");
		layout.addComponent(resetPasswordButton, "resetPasswordButton");
	}

	@Override
	public void attachListener() {
		resetPasswordButton.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(Button.ClickEvent clickEvent) {
				employeeService.resetPassword(resetPassword,
						oldPassword.getValue(), newPassword.getValue());
			}
		});
	}
}
