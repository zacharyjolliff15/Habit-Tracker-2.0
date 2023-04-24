package com.example.application.views;

import com.example.application.security.SecurityService;
import com.example.application.views.list.HabitsList;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.theme.lumo.LumoUtility;

public class MainLayout extends AppLayout {
	private static final long serialVersionUID = 1L;
	private final SecurityService securityService;

	public MainLayout(SecurityService securityService) {
		this.securityService = securityService;
		createHeader();
		createDrawer();
	}

	private void createHeader() {
		H1 logo = new H1("Habit Tracker");
		logo.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.MEDIUM);

		String u = securityService.getAuthenticatedUser().getUsername();
		Button logout = new Button("Log out " + u, e -> securityService.logout()); // <2>

		var header = new HorizontalLayout(new DrawerToggle(), logo, logout);

		header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
		header.expand(logo); // <4>
		header.setWidthFull();
		header.addClassNames(LumoUtility.Padding.Vertical.NONE, LumoUtility.Padding.Horizontal.MEDIUM);
		addToNavbar(header);
	}

	private void createDrawer() {
	    addToDrawer(new VerticalLayout(
	        new RouterLink("Habits List", HabitsList.class),
	        new RouterLink("Motivation", Motivation.class),
	        new RouterLink("Reports", Reports.class),
	        new RouterLink("", HabitTracker.class)
	    ));
	}
}