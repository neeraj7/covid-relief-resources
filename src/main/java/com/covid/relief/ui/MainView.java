package com.covid.relief.ui;

import java.util.stream.Collectors;

import com.covid.relief.dto.Tweet;
import com.covid.relief.init.AppInitializer;
import com.covid.relief.service.TwitterService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.TemplateRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.server.WebBrowser;

@Route("")
@PageTitle("Covid Relief Resources")
public class MainView extends VerticalLayout {
	
	private ComboBox<String> resources = new ComboBox<String>("Select resource");
	private ComboBox<String> cities = new ComboBox<String>("Select city");
	
	private TwitterService twitterService;
	
	// copied code
    final Grid<Tweet> grid;
	
	public MainView(TwitterService twitterService) {
		this.twitterService = twitterService;
		setSizeFull();
		// copied code
        this.grid = new Grid<>(Tweet.class);
        
        Label title = new Label("Covid Relief Resources"); 
        Label grace = new Label("With the grace of Lord Shiva");
        title.getStyle().set("font-size", "50px");
        setMaxHeight("100%");
        updateCityAndResource();
        Button btn = new Button("Refresh");
        btn.addClickListener(e -> updateList());
        
        
        HorizontalLayout hl = new HorizontalLayout(cities, resources, btn);
        
        
        hl.setDefaultVerticalComponentAlignment(Alignment.CENTER);
        Label footer = new Label("Note: Aim of this website is to help people by providing details of covid related resources fetched from tweets by humans who are willing to help others.");
        footer.getStyle().set("margin", "20px");
        footer.getStyle().set("margin-top", "60px");
        footer.getStyle().set("background-color", "yellow");
        
        
        VerticalLayout vl = new VerticalLayout();
        
        // Updating UI based on the device
        if(isMobileDevice()) {
        	HorizontalLayout forButtonMobile = new HorizontalLayout(cities, resources);
        	VerticalLayout forMobile = new VerticalLayout(forButtonMobile, btn);
        	title.getStyle().set("font-size", "30px");
        	vl.add(grace, title, forMobile, grid, footer);
        	vl.setSpacing(false);
        	grid.setMaxHeight("75%");
        	grace.getStyle().set("padding-top", "0px");
        	grace.getStyle().set("margin-top", "0px");
            footer.getStyle().set("margin-top", "20px");
        } else {
        	grace.getStyle().set("padding-top", "10px");
        	vl.add(grace, title, hl, grid, footer);
        	grid.setMaxHeight("74%");
        }
        
        vl.setAlignItems(Alignment.CENTER);
        vl.setHeight("600px");
        add(vl);
        
        grid.setMaxWidth("90%");
        grid.setHeightByRows(true);
        grid.setPageSize(10);
        this.setHorizontalComponentAlignment(Alignment.CENTER, grid);
        
        grid.removeColumnByKey("older");
        grid.removeColumnByKey("text");
        
        grid.addColumn(
				TemplateRenderer.<Tweet>of("<div style='white-space:normal'>[[item.text]]</div>")
						.withProperty("text", Tweet::getText))
				.setHeader("Tweets").setFlexGrow(1);
        grid.addColumn(
				TemplateRenderer.<Tweet>of("<div style='white-space:normal'>[[item.older]]</div>")
						.withProperty("older", Tweet::getOlder))
				.setHeader("Older").setWidth("150px").setFlexGrow(0);

        updateList();
	}
	
	public boolean isMobileDevice() {
		WebBrowser browser = VaadinSession.getCurrent().getBrowser();
		return browser.isAndroid() || browser.isIPhone() || browser.isWindowsPhone();
	}

	private void updateCityAndResource() {
		cities.addValueChangeListener(e -> updateList());
		resources.addValueChangeListener(e -> updateList());
		cities.setClearButtonVisible(true);
		resources.setClearButtonVisible(true);
		resources.setItems(AppInitializer.getResourcesList().stream().map(qh -> qh.getResource().substring(0, 1).toUpperCase().concat(qh.getResource().substring(1))).collect(Collectors.toList()));
		
		cities.setItems(AppInitializer.getCitiesMapping().keySet().stream().map(city -> city.substring(0, 1).toUpperCase().concat(city.substring(1))).collect(Collectors.toList()));
	}

	private void updateList() {
		grid.setItems(twitterService.getAllSavedTweets(cities.getValue(), resources.getValue()));
	}
}
