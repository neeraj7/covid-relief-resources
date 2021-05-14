package com.covid.relief;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.material.Material;

@SpringBootApplication
@EnableScheduling
@Theme(themeClass = Material.class)
public class CovidHelpResourcesApplication implements AppShellConfigurator {

	public static void main(String[] args) {
		SpringApplication.run(CovidHelpResourcesApplication.class, args);
	}

}
