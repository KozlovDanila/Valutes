package com.example.Readers;

import com.example.Currencies.ICurrency;
import java.io.IOException;
import java.util.Collection;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

/**
 * Интерфейс чтения
 */
public interface IReader {

	/**
	 * @return Коллекцию записей с данными по валюте
	 */
	Collection<ICurrency> read() throws ParserConfigurationException, IOException, SAXException;
}
