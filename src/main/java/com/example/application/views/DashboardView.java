package com.example.application.views;

import java.util.Random;
import com.example.application.data.service.CrmService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.ChartType;
import com.vaadin.flow.component.charts.model.DataSeries;
import com.vaadin.flow.component.charts.model.DataSeriesItem;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;
import jakarta.annotation.security.PermitAll;
import com.vaadin.flow.component.textfield.TextField;

@PermitAll
@Route(value = "dashboard", layout = MainLayout.class) // <1>
@PageTitle("Dashboard | Habit Tracker")
public class DashboardView extends VerticalLayout {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final TextField quoteTextField;
    private final CrmService service;

    public DashboardView(CrmService service) { // <2>
    	// Set up motivational quote generator
    	quoteTextField = new TextField("Motivational Quotes");
        Button generateButton = new Button("Generate Quote");
        generateButton.addClickListener(e -> generateRandomMotivationalQuote());
        add(quoteTextField, generateButton);
    	
        this.service = service;
        addClassName("dashboard-view");
        setDefaultHorizontalComponentAlignment(Alignment.CENTER); // <3>

        add(getContactStats(), getCompaniesChart());

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

    private Component getContactStats() {
        Span stats = new Span(service.countContacts() + " contacts"); // <4>
        stats.addClassNames(
            LumoUtility.FontSize.XLARGE,
            LumoUtility.Margin.Top.MEDIUM);
        return stats;
    }

    private Chart getCompaniesChart() {
        Chart chart = new Chart(ChartType.PIE);

        DataSeries dataSeries = new DataSeries();
        service.findAllCompanies().forEach(company ->
            dataSeries.add(new DataSeriesItem(company.getName(), company.getEmployeeCount()))); // <5>
        chart.getConfiguration().setSeries(dataSeries);
        return chart;
    }
    
    private final String[] motivationalQuotes = {
    		// Contains all of the motivational quotes
    		"Believe in yourself and all that you are. Know that there is something inside you that is greater than any obstacle.",
            "You are never too old to set another goal or to dream a new dream.",
            "The future belongs to those who believe in the beauty of their dreams.",
            "Believe you can and you're halfway there.",
            "If you want to achieve greatness, stop asking for permission.",
            "Success is not final, failure is not fatal: it is the courage to continue that counts.",
            "It does not matter how slowly you go as long as you do not stop.",
            "You miss 100% of the shots you don't take.",
            "The only way to do great work is to love what you do.",
            "The best way to predict the future is to invent it."
       };

    private void generateRandomMotivationalQuote() {
        Random random = new Random();
        int quoteIndex = random.nextInt(motivationalQuotes.length);
        String randomQuote = motivationalQuotes[quoteIndex];
        quoteTextField.setSizeFull();
        quoteTextField.setValue(randomQuote);
    }

}