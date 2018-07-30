package com.example.Writers;

import com.example.Currencies.ValCurs;
import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.springframework.http.ResponseEntity;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Запись информации по валютам в xml файл
 */
public class XmlWriter implements IWriter {

	@Override
	public void write(ResponseEntity<ValCurs> curr) throws ParserConfigurationException, TransformerException {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

		Document doc = docBuilder.newDocument();
		Element rootElement = doc.createElement("Currencies");
		rootElement.setAttribute("date", curr.getBody().getDate());
		doc.appendChild(rootElement);

		for (ValCurs.Valute valute : curr.getBody().getValute()) {
			Element element = createElement(valute, doc);
			rootElement.appendChild(element);
		}

		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(new File("curr.xml"));

		transformer.transform(source, result);
	}

	private Element createElement(ValCurs.Valute valute, Document doc) {
		Element result = doc.createElement("curr");
		result.setAttribute("ID", valute.getID());
		result.setAttribute("Name", valute.getName());
		result.setAttribute("Value", valute.getValue());
		result.setAttribute("Code", String.valueOf(valute.getNumCode()));
		result.setAttribute("CharCode", valute.getCharCode());
		result.setAttribute("Nominal", String.valueOf(valute.getNominal()));
		return result;
	}


}
