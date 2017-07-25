package com.ga.vaadin;

import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.spring.events.EventBus;
import org.vaadin.spring.events.EventScope;
import org.vaadin.spring.events.annotation.EventBusListenerMethod;
import org.vaadin.viritin.button.ConfirmButton;
import org.vaadin.viritin.button.MButton;
import org.vaadin.viritin.fields.MTextField;
import org.vaadin.viritin.grid.MGrid;
import org.vaadin.viritin.layouts.MHorizontalLayout;
import org.vaadin.viritin.layouts.MVerticalLayout;

import com.ga.common.ViewModel;
import com.ga.entity.Employee;
import com.ga.events.UserModifiedEvent;
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
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;

@SpringView(name = DashboardView.VIEW_NAME)
public class DashboardView extends CustomComponent implements View, ViewModel {

	private static final long serialVersionUID = 1L;
	public static final String VIEW_NAME = "dashboard";
	static final String LAYOUT_NAME = "dashboardView";
	
	private MGrid<Employee> list = new MGrid<>(Employee.class)
			.withProperties("id", "name", "lastName", "email", "companyName",
					"companyAddress", "companyState", "companyZip")
			.withColumnHeaders("ID", "First Name", "Last Name", "Email",
					"Company Name", "Company Address", "Company State",
					"Company Zip").withFullWidth();

	private MTextField filterByName = new MTextField()
			.withPlaceholder("Filter by name");
	private Button addNew = new MButton("Add User", this::add);
	private Button edit = new MButton("Edit User", this::edit);
	private Button delete = new ConfirmButton(null, "Delete",
			"Are you sure you want to delete the entry?", this::remove);

	
	HeaderView headerView;
	
	EmployeeService employeeService;
	Employee employee;
	PersonForm personForm;
	EventBus.UIEventBus eventBus;
	
	@Autowired
	public DashboardView(EmployeeService employeeService, PersonForm f,
			EventBus.UIEventBus b, HeaderView headerView) {
		this.employeeService = employeeService;
		this.personForm = f;
		this.eventBus = b;
		this.headerView = headerView;
	}

	CustomLayout layout;

	@PostConstruct
	void init() {

		employee = (Employee) VaadinSession.getCurrent().getAttribute("user");
		if (employee == null) {
			UI.getCurrent().getNavigator().removeView(DashboardView.VIEW_NAME);
			VaadinSession.getCurrent().setAttribute("user", null);
			Page.getCurrent().setUriFragment("");
		} else {
			prepareView();
		}
	}

	/* show - hide action buttons */
	protected void adjustActionButtonState() {
		edit.setEnabled(!list.getSelectedItems().isEmpty());
		delete.setEnabled(!list.getSelectedItems().isEmpty());
	}

	private void listEntities() {
		listEntities(filterByName.getValue());
	}

	final int PAGESIZE = 45;

	private void listEntities(String nameFilter) {

		String likeFilter = "%" + nameFilter + "%";

		List<Employee> employeeList = employeeService
				.findByNameLikeIgnoreCase(likeFilter);

		Iterator<Employee> iter = employeeList.iterator();
		while (iter.hasNext()) {
			Employee e = iter.next();
			if (e.getIsAdmin() == true)
				iter.remove();
		}

		list.setItems(employeeList);
		adjustActionButtonState();
	}

	/* Add Employee */
	public void add(ClickEvent clickEvent) {
		edit(new Employee());
	}

	/* Edit Employee */
	public void edit(ClickEvent e) {
		edit(list.asSingleSelect().getValue());
	}

	/* Delete Employee */
	public void remove() {
		employeeService.delete(list.asSingleSelect().getValue());
		list.deselectAll();
		listEntities();
	}

	protected void edit(final Employee employee) {
		personForm.setEntity(employee);
		if (employee.getEmail() == null || employee.getEmail().trim().equalsIgnoreCase("")) {
			personForm.userPassword.setVisible(true);
			personForm.setNewEmployee(true);
		} else {
			personForm.userPassword.setVisible(false);
			personForm.setNewEmployee(false);
		}

		try {
			personForm.openInModalPopup();
		} catch (Exception e) {
			/* If already exsist then close first */
			personForm.closePopup();
			personForm.openInModalPopup();
		}
	}

	@EventBusListenerMethod(scope = EventScope.UI)
	public void onPersonModified(UserModifiedEvent event) {
		listEntities();
		personForm.closePopup();
	}

	@Override
	public void prepareView() {
		layout = new CustomLayout(LAYOUT_NAME);
		layout.addComponent(headerView, "headerView");

		/* Admin Feature */
		if (employee.getIsAdmin()) {
			layout.addComponent(new MVerticalLayout(new MHorizontalLayout(
					filterByName, addNew, edit, delete)), "MVerticalLayout");
			listEntities();
			layout.addComponent(list, "list");
			attachListener();
		} else {
			layout.addComponent(new Label("Welcome " + employee.getName()),
					"userName");
		}
		// Listen to change events emitted by PersonForm see onEvent method
		eventBus.subscribe(this);

		setCompositionRoot(layout);
		setSizeFull();
	}

	@Override
	public void attachListener() {
		list.asSingleSelect().addValueChangeListener(
				e -> adjustActionButtonState());
		filterByName.addValueChangeListener(e -> {
			listEntities(e.getValue());
		});
	}

	@Override
	public void enter(ViewChangeEvent event) {
		if (VaadinSession.getCurrent().getAttribute("user") == null) {
			getUI().getNavigator().navigateTo(LoginView.VIEW_NAME);
		}
	}
}
