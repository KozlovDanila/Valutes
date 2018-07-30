package com.example.Readers;

import com.example.Currencies.Currency;
import com.example.Currencies.ICurrency;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XmlReader implements IReader {

	@Override
	public Collection<ICurrency> read() throws ParserConfigurationException, IOException, SAXException {
		Collection<ICurrency> result = new ArrayList<>();

		File fXmlFile = new File("curr.xml");

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(fXmlFile);
		doc.getDocumentElement().normalize();

		NodeList nodeList = doc.getElementsByTagName("curr");
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				result.add(createCurrency((Element) node));
			}
		}
		return result;
	}

	private ICurrency createCurrency(Element element) {
		Currency result = new Currency();
		result.setId(element.getAttribute("ID"));
		result.setCharCode(element.getAttribute("CharCode"));
		result.setNumberCode(Integer.parseInt(element.getAttribute("Code")));
		result.setName(element.getAttribute("Name"));
		result.setNominal(Integer.parseInt(element.getAttribute("Nominal")));
		result.setValue(new BigDecimal(element.getAttribute("Value").replace(",", ".")));
		return result;
	}
}
