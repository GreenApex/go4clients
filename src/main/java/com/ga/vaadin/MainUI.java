package com.ga.vaadin;

import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.annotation.SpringViewDisplay;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@Theme("srtheme")
@SpringUI
@SpringViewDisplay
@UIScope
public class MainUI extends UI {

	private static final long serialVersionUID = 1L;

	@Override
	protected void init(VaadinRequest request) {

		final VerticalLayout root = new VerticalLayout();
		root.setSizeFull();
		setContent(root);

		final CssLayout navigationBar = new CssLayout();
		navigationBar.addStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
	}
}
