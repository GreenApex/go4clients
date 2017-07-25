package com.ga.vaadin;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.ga.common.ViewModel;
import com.ga.listeners.ForgotPasswordButtonListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.TextField;

@Component
@SpringView(name = VerifyEmailWindow.VIEW_NAME)
public class VerifyEmailWindow extends CustomComponent implements View,
		ViewModel {
	public static final String VIEW_NAME = "verifyEmail";
	static final String LAYOUT_NAME = "emailVerificationpopup";

	private static final long serialVersionUID = 1L;

	CustomLayout layout;

	TextField userName = new TextField("Email");
	Button verifyEmail = new Button("Verify Email");

	public VerifyEmailWindow() {
		
	}

	@PostConstruct
	void init() {
		prepareView();
		attachListener();
	}

	@Override
	public void prepareView() {
		layout = new CustomLayout(LAYOUT_NAME);
		layout.addComponent(userName, "userEmail");
		layout.addComponent(verifyEmail, "verifyEmailButton");
		setCompositionRoot(layout);
		setSizeFull();
	}

	@Override
	public void attachListener() {
		verifyEmail
				.addClickListener(new ForgotPasswordButtonListener(userName));
	}

	@Override
	public void enter(ViewChangeListener.ViewChangeEvent event) {

	}
}
