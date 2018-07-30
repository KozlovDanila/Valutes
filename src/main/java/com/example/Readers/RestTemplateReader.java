package com.example.Readers;

import com.example.Currencies.ValCurs;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class RestTemplateReader {

	protected static final String SOURCE = "https://www.cbr-xml-daily.ru/daily.xml";

	public static ResponseEntity<ValCurs> getValute() {
		return new RestTemplate().getForEntity(SOURCE, ValCurs.class);
	}
}
