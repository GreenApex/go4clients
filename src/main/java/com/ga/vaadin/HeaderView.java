package com.ga.vaadin;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.vaadin.viritin.button.MButton;

import com.ga.common.ViewModel;
import com.ga.service.EmployeeService;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.UI;

@Component
public class HeaderView extends CustomComponent implements View, ViewModel {

	private static final long serialVersionUID = 1L;

	static final String LAYOUT_NAME = "headerView";

	CustomLayout layout;
	Button profileButton = new Button("My Profile");
	Button logout = new MButton("Logout", this::logout);

	EmployeeService employeeService;

	@Autowired
	public HeaderView(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	@PostConstruct
	void init() {
		prepareView();
		attachListener();
	}

	public void logout(ClickEvent clickEvent) {
		logout();
	}

	void logout() {
		UI.getCurrent().getNavigator().removeView(DashboardView.VIEW_NAME);
		VaadinSession.getCurrent().setAttribute("user", null);
		Page.getCurrent().setUriFragment("");
	}

	@Override
	public void prepareView() {
		layout = new CustomLayout(LAYOUT_NAME);
		layout.addComponent(profileButton, "profileButton");
		layout.addComponent(logout, "logoutButton");
		setCompositionRoot(layout);
		setSizeFull();
	}

	@Override
	public void attachListener() {
		profileButton.addClickListener(new Button.ClickListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(Button.ClickEvent clickEvent) {
				UI.getCurrent().getNavigator()
						.navigateTo(ProfileView.VIEW_NAME);
			}
		});
	}

	@Override
	public void enter(ViewChangeEvent event) {
		if (VaadinSession.getCurrent().getAttribute("user") == null) {
			logout();
		}
	}
}
