package com.example.demo;

import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@SpringUI
public class MyUI extends UI {

	private boolean isUse = false;

	@Override
	protected void init(VaadinRequest request) {

		FormLayout layoutWithFormItems = new FormLayout();
		layoutWithFormItems.setWidth("1500");

		List<ValCurs.Valute> valutes = getValute();

		TextField currencies = new TextField();
		layoutWithFormItems.addComponent(currencies);
		Button button = new Button("Получить курс валют");
		layoutWithFormItems.addComponent(button);

		button.addClickListener(clickEvent ->
		{
			String curr = currencies.getValue();
			if (isUse) {
				layoutWithFormItems.removeComponent(layoutWithFormItems.getComponent(2));
			} else {
				isUse = true;
			}
			if (!curr.isEmpty()) {
				List<ValCurs.Valute> filterValute = valutes.stream().filter(valute -> curr.contains(valute.getCharCode())).collect(Collectors.toList());
				layoutWithFormItems.addComponent(createTable(filterValute));

			} else {
				layoutWithFormItems.addComponent(createTable(valutes));
			}

		});
		setContent(layoutWithFormItems);
	}


	private List<ValCurs.Valute> getValute() {
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<ValCurs> curr = restTemplate.getForEntity("https://www.cbr-xml-daily.ru/daily.xml", ValCurs.class);
		return curr.getBody().getValute();
	}

	private Grid<ValCurs.Valute> createTable(List<ValCurs.Valute> valutes) {
		Grid<ValCurs.Valute> grid = new Grid<>();
		grid.setWidth("1500");
		grid.setItems(valutes);
		grid.addColumn(ValCurs.Valute::getID).setCaption("ID");
		grid.addColumn(ValCurs.Valute::getName).setCaption("Наименование");
		grid.addColumn(ValCurs.Valute::getValue).setCaption("Значение");
		grid.addColumn(ValCurs.Valute::getNumCode).setCaption("Код");
		grid.addColumn(ValCurs.Valute::getCharCode).setCaption("Символьный код");
		grid.addColumn(ValCurs.Valute::getNominal).setCaption("Номинал");
		return grid;
	}


}
