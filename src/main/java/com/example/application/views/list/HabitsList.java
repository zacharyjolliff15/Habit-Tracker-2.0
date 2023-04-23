package com.example.application.views.list;

import com.example.application.data.entity.Contact;
import com.example.application.data.service.CrmService;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import jakarta.annotation.security.PermitAll;
import org.springframework.context.annotation.Scope;

@SpringComponent
@Scope("prototype")
@PermitAll
@Route(value = "", layout = MainLayout.class)
@PageTitle("Habits List | Habit Tracker")
public class HabitsList extends VerticalLayout {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Grid<Contact> grid = new Grid<>(Contact.class);
	TextField filterText = new TextField();
	ContactForm form;
	CrmService service;

	public HabitsList(CrmService service) {
		this.service = service;
		addClassName("list-view");
		setSizeFull();
		configureGrid();
		configureForm();

		add(getToolbar(), getContent());
		updateList();
		closeEditor();

		// Add dark mode checkbox at the bottom left corner
		Checkbox darkModeCheckbox = new Checkbox("Dark Mode");
		darkModeCheckbox.addValueChangeListener(e -> {
			if (e.getValue()) {
				UI.getCurrent().getElement().getThemeList().add("dark");
			} else {
				UI.getCurrent().getElement().getThemeList().remove("dark");
			}
		});
		darkModeCheckbox.setWidth("130px");
		darkModeCheckbox.setHeight("40px");
		darkModeCheckbox.getStyle().set("position", "absolute");
		darkModeCheckbox.getStyle().set("left", "10px");
		darkModeCheckbox.getStyle().set("bottom", "10px");
		add(darkModeCheckbox);
	}

	private HorizontalLayout getContent() {
		HorizontalLayout content = new HorizontalLayout(grid, form);
		content.setFlexGrow(2, grid);
		content.setFlexGrow(1, form);
		content.addClassNames("content");
		content.setSizeFull();
		return content;
	}

	private void configureForm() {
		form = new ContactForm(service.findAllCompanies(), service.findAllStatuses());
		form.setWidth("25em");
		form.addSaveListener(this::saveContact); // <1>
		form.addDeleteListener(this::deleteContact); // <2>
		form.addCloseListener(e -> closeEditor()); // <3>
	}

	private void saveContact(ContactForm.SaveEvent event) {
		service.saveContact(event.getContact());
		updateList();
		closeEditor();
	}

	private void deleteContact(ContactForm.DeleteEvent event) {
		service.deleteContact(event.getContact());
		updateList();
		closeEditor();
	}

	private void configureGrid() {
		grid.addClassNames("contact-grid");
		grid.setSizeFull();
		grid.setColumns("habitName", "habitDescription", "habitDate");
		grid.addColumn(contact -> contact.getStatus().getName()).setHeader("Importance");
		grid.addColumn(contact -> contact.getCompany().getName()).setHeader("Category");
		grid.getColumns().forEach(col -> col.setAutoWidth(true));

		grid.asSingleSelect().addValueChangeListener(event -> editContact(event.getValue()));
	}

	private Component getToolbar() {
		filterText.setPlaceholder("Filter by name...");
		filterText.setClearButtonVisible(true);
		filterText.setValueChangeMode(ValueChangeMode.LAZY);
		filterText.addValueChangeListener(e -> updateList());

		Button addHabitButton = new Button("Add Habit");
		addHabitButton.addClickListener(click -> addContact());

		var toolbar = new HorizontalLayout(filterText, addHabitButton);
		toolbar.addClassName("toolbar");
		return toolbar;
	}

	public void editContact(Contact contact) {
		if (contact == null) {
			closeEditor();
		} else {
			form.setContact(contact);
			form.setVisible(true);
			addClassName("editing");
		}
	}

	private void closeEditor() {
		form.setContact(null);
		form.setVisible(false);
		removeClassName("editing");
	}

	private void addContact() {
		grid.asSingleSelect().clear();
		editContact(new Contact());
	}

	private void updateList() {
		grid.setItems(service.findAllContacts(filterText.getValue()));
	}
}