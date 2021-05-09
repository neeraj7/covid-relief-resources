package com.covid.relief.ui;

import java.awt.Panel;
import java.util.stream.Collectors;

import com.covid.relief.dto.Tweet;
import com.covid.relief.init.AppInitializer;
import com.covid.relief.service.TwitterService;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.TemplateRenderer;
import com.vaadin.flow.router.Route;

@Route("")
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
        title.setHeight("100px");
        
        updateCityAndResource();
        
        HorizontalLayout hl = new HorizontalLayout();
        hl.add(cities, resources);
        
        add(title, hl, grid);
        
        grid.setSizeFull();
        grid.setHeightByRows(true);
        grid.setPageSize(10);
        
        grid.removeColumnByKey("older");
        grid.removeColumnByKey("text");
        
        grid.addColumn(
				TemplateRenderer.<Tweet>of("<div style='white-space:normal'>[[item.text]]</div>")
						.withProperty("text", Tweet::getText))
				.setHeader("Text").setFlexGrow(1);
        grid.addColumn(
				TemplateRenderer.<Tweet>of("<div style='white-space:normal'>[[item.older]]</div>")
						.withProperty("older", Tweet::getOlder))
				.setHeader("Older").setWidth("150px").setFlexGrow(0);

        updateList();
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
