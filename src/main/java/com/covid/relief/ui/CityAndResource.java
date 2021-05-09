//package com.covid.relief.ui;
//
//import java.util.stream.Collectors;
//
//import com.covid.relief.init.AppInitializer;
//import com.vaadin.flow.component.button.Button;
//import com.vaadin.flow.component.button.ButtonVariant;
//import com.vaadin.flow.component.combobox.ComboBox;
//import com.vaadin.flow.component.formlayout.FormLayout;
//import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
//import com.vaadin.flow.component.textfield.TextField;
//
//public class CityAndResource extends FormLayout {
//	
//	private TextField test = new TextField("this is a test");
//	private ComboBox<String> list = new ComboBox<>("Select resource:");
//	private Button next = new Button("Test button");
//	
//	public CityAndResource() {
//		list.setItems(AppInitializer.getResourcesList().stream().map(qh -> qh.getResource()).collect(Collectors.toList()));
//		HorizontalLayout buttons = new HorizontalLayout(next);
//	    next.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
//	    add(test, list, buttons);
//	}
//
//}
