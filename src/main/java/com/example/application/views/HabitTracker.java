package com.example.application.views;

import java.util.ArrayList;
import java.util.List;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import com.vaadin.flow.component.datepicker.DatePicker;
import java.time.LocalDate;
import com.vaadin.flow.component.checkbox.Checkbox;

@PermitAll
@Route(value = "Habit Tracker", layout = MainLayout.class) // <1>
@PageTitle("Dashboard | Habit Tracker")

public class HabitTracker extends VerticalLayout {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final List<Habit> habits = new ArrayList<>();
    private final Grid<Habit> habitGrid = new Grid<>(Habit.class);

    public HabitTracker() {
        habitGrid.setColumns("name", "startDate", "endDate", "frequency", "progress");
        habitGrid.addComponentColumn(this::buildProgressCheckbox);
        habitGrid.addComponentColumn(this::buildDeleteButton);
        habitGrid.setItems(habits);

        TextField nameField = new TextField("Habit Name");
        DatePicker startDatePicker = new DatePicker("Start Date");
        DatePicker endDatePicker = new DatePicker("End Date");
        TextField frequencyField = new TextField("Frequency (in days)");
        Button addButton = new Button("Add Habit", event -> addHabit());

        add(nameField, startDatePicker, endDatePicker, frequencyField, addButton, habitGrid);
    }
    
    private void addHabit() {
        String name = ((TextField) getComponentAt(0)).getValue();
        LocalDate startDate = ((DatePicker) getComponentAt(1)).getValue();
        LocalDate endDate = ((DatePicker) getComponentAt(2)).getValue();
        int frequency = 0;

        try {
            frequency = Integer.parseInt(((TextField) getComponentAt(3)).getValue());
        } catch (NumberFormatException e) {
            Notification.show("Invalid frequency value. Please enter a number.");
            return;
        }

        if (startDate == null || endDate == null) {
            Notification.show("Please select both start and end dates.");
            return;
        }

        if (startDate.isAfter(endDate)) {
            Notification.show("Start date cannot be after end date.");
            return;
        }

        if (name.isEmpty()) {
            Notification.show("Please enter a name for the habit.");
            return;
        }

        Habit habit = new Habit(name, startDate, endDate, frequency);
        habits.add(habit);
        habitGrid.setItems(habits);
        Notification.show("Habit added!");
    }


    private Checkbox buildProgressCheckbox(Habit habit) {
        Checkbox checkbox = new Checkbox();
        checkbox.setValue(habit.getProgress().equals("Done"));
        checkbox.addValueChangeListener(event -> {
            if (event.getValue()) {
                habit.setProgress("Done");
            } else {
                habit.setProgress("");
            }
        });
        return checkbox;
    }

    private Button buildDeleteButton(Habit habit) {
        Button button = new Button("Delete", event -> {
            habits.remove(habit);
            habitGrid.setItems(habits);
            Notification.show("Habit deleted!");
        });
        return button;
    }

    public static class Habit {
        private String name;
        private LocalDate startDate;
        private LocalDate endDate;
        private int frequency;
        private String progress;

        public Habit(String name, LocalDate startDate, LocalDate endDate, int frequency) {
            this.name = name;
            this.startDate = startDate;
            this.endDate = endDate;
            this.frequency = frequency;
            this.progress = "";
        }

        public String getName() {
            return name;
        }

        public LocalDate getStartDate() {
            return startDate;
        }

        public LocalDate getEndDate() {
            return endDate;
        }

        public int getFrequency() {
            return frequency;
        }

        public String getProgress() {
            return progress;
        }

        public void setProgress(String progress) {
            this.progress = progress;
        }
    }
}