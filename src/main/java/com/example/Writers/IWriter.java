package com.example.Writers;

import com.example.Currencies.ValCurs;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import org.springframework.http.ResponseEntity;

/**
 * Интерфейс чтения валют
 */
public interface IWriter {

	/**
	 * @param curr Информацию по валюте
	 * В данном методе происходит запись валют куда-либо
	 */
	void write(ResponseEntity<ValCurs> curr) throws ParserConfigurationException, TransformerException;
}
