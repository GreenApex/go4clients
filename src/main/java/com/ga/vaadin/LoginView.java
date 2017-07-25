package com.ga.vaadin;

import javax.annotation.PostConstruct;

import com.ga.listeners.LoginButtonListener;
import com.ga.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.ga.common.*;

@SpringView(name = LoginView.VIEW_NAME)
public class LoginView extends CustomComponent implements View,ViewModel {

	private static final long serialVersionUID = 1L;
	
	public static final String VIEW_NAME = "";
	static final String LAYOUT_NAME = "loginView";

	CustomLayout layout;

	TextField userName = new TextField("User Email");
	PasswordField password = new PasswordField("Password");
	Button loginButton = new Button("Login");
	CheckBox rememberMe = new CheckBox();
	Button forgetPasswordLink = new Button("Forget Password");
	VerifyEmailWindow verifyEmailWindow;

	EmployeeService employeeService;

	@Autowired
	public LoginView(EmployeeService employeeService,
			VerifyEmailWindow verifyEmailWindow) {
		this.employeeService = employeeService;
		this.verifyEmailWindow = verifyEmailWindow;
	}

	@PostConstruct
	void init() {
		prepareView();	
		attachListener();	
	}
	
	@Override
	public void prepareView(){
		rememberMe.setCaption("Remember Me");
		layout = new CustomLayout(LAYOUT_NAME);
		layout.addComponent(userName, "userName");
		layout.addComponent(password, "password");
		layout.addComponent(loginButton, "loginButton");
		layout.addComponent(rememberMe, "rememberMe");
		layout.addComponent(rememberMe, "rememberMe");
		layout.addComponent(forgetPasswordLink, "forgetPasswordLink");
		setCompositionRoot(layout);
		setSizeFull();
	}
	
	@Override
	public void attachListener() {
		loginButton.addClickListener(new LoginButtonListener(employeeService,userName, password));

		forgetPasswordLink.addClickListener(new Button.ClickListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(Button.ClickEvent clickEvent) {
				UI.getCurrent().getNavigator()
						.navigateTo(VerifyEmailWindow.VIEW_NAME);
			}
		});
	}
	
	@Override
	public void enter(ViewChangeEvent event) {

	}
}
