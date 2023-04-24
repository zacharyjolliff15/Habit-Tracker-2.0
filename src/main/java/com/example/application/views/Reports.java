package com.example.application.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import java.util.ArrayList;
import java.util.List;

@PermitAll
@Route(value = "Reports", layout = MainLayout.class) // <1>
@PageTitle("Reports | Habit Tracker")
public class Reports extends VerticalLayout {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Reports() {
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
		
        // Define the layout
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
        add(new H1("Habit Tracker Reports"));

        // Create a data source
        List<Habit> habits = new ArrayList<>();
        habits.add(new Habit("Exercise", true));
        habits.add(new Habit("Read for 30 minutes", false));
        habits.add(new Habit("Meditate", true));

        // Display the data
        Grid<Habit> grid = new Grid<>(Habit.class);
        grid.setItems(habits);
        grid.setColumns("name", "completed");
        add(grid);
    }

    public class Habit {
        private String name;
        private boolean completed;

        public Habit(String name, boolean completed) {
            this.name = name;
            this.completed = completed;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isCompleted() {
            return completed;
        }

        public void setCompleted(boolean completed) {
            this.completed = completed;
        }
    }
}