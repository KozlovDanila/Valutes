package com.example.demo;

import com.example.Currencies.ICurrency;
import com.example.Readers.IReader;
import com.example.Readers.RestTemplateReader;
import com.example.Writers.XmlWriter;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.xml.sax.SAXException;

@SpringUI
public class MyUI extends UI {

	@Autowired
	@Qualifier("remoteResourceReader")
	private IReader remoteResourceReader;

	@Autowired
	@Qualifier("xmlReader")
	private IReader xmlReader;

	@Override
	protected void init(VaadinRequest request) {

		FormLayout layoutWithFormItems = new FormLayout();
		layoutWithFormItems.setWidth("1500");

		TextField currencies = new TextField();
		layoutWithFormItems.addComponent(currencies);

		Button getCurrByRemoteButton = new Button("Получить курс валют с сайта");
		layoutWithFormItems.addComponent(getCurrByRemoteButton);
		getCurrByRemoteButton.addClickListener(clickEvent -> {
			try {
				createTable(currencies, layoutWithFormItems, remoteResourceReader.read());
			} catch (ParserConfigurationException | IOException | SAXException e) {
				createSubMessage("Ошибка", "Ошибка " + e.getMessage());
			}
		});

		Button getCurrByXmlButton = new Button("Получить курс валют из файла");
		layoutWithFormItems.addComponent(getCurrByXmlButton);
		getCurrByXmlButton.addClickListener(clickEvent -> {
			try {
				createTable(currencies, layoutWithFormItems, xmlReader.read());
			} catch (ParserConfigurationException | IOException | SAXException e) {
				createSubMessage("Ошибка", "Ошибка " + e.getMessage());
			}
		});

		Button saveCurrButton = new Button("Сохранить курс валют");
		layoutWithFormItems.addComponent(saveCurrButton);
		saveCurrButton.addClickListener(clickEvent -> {
			try {
				new XmlWriter().write(RestTemplateReader.getValute());
				createSubMessage("Сохранение", "Сохранено успешно");
			} catch (ParserConfigurationException | TransformerException e) {
				createSubMessage("Ошибка", "Ошибка " + e.getMessage());
			}
		});

		setContent(layoutWithFormItems);
	}

	private void createSubMessage(String title, String label) {
		Window subWindow = new Window(title);
		VerticalLayout subContent = new VerticalLayout();
		subWindow.setContent(subContent);
		subContent.addComponent(new Label(label));
		subWindow.center();
		addWindow(subWindow);
	}

	private void createTable(TextField currencies, FormLayout layoutWithFormItems, Collection<ICurrency> valuteList) {
		String curr = currencies.getValue();
		removeComponent(layoutWithFormItems);
		if (!curr.isEmpty()) {
			List<ICurrency> filterValute = valuteList.stream().filter(valute -> curr.contains(valute.getCharCode())).collect(Collectors.toList());
			layoutWithFormItems.addComponent(createTableValue(filterValute));
		} else {
			layoutWithFormItems.addComponent(createTableValue(valuteList));
		}
	}

	private Grid<ICurrency> createTableValue(Collection<ICurrency> curr) {
		Grid<ICurrency> grid = new Grid<>();
		grid.setWidth("1500");
		grid.setItems(curr);
		grid.addColumn(ICurrency::getId).setCaption("ID");
		grid.addColumn(ICurrency::getName).setCaption("Наименование");
		grid.addColumn(ICurrency::getValue).setCaption("Значение");
		grid.addColumn(ICurrency::getNumberCode).setCaption("Код");
		grid.addColumn(ICurrency::getCharCode).setCaption("Символьный код");
		grid.addColumn(ICurrency::getNominal).setCaption("Номинал");
		grid.setId("grid");
		return grid;
	}

	private void removeComponent(FormLayout formLayout) {
		for (int i = 0; i < formLayout.getComponentCount(); i++) {
			Component component = formLayout.getComponent(i);
			if ("grid".equals(component.getId())) {
				formLayout.removeComponent(component);
				return;
			}
		}
	}
}
