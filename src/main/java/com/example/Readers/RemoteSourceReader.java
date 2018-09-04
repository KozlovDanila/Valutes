package com.example.Readers;

import com.example.Currencies.Currency;
import com.example.Currencies.ICurrency;
import com.example.Currencies.ValCurs;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

//Кэширование?
public class RemoteSourceReader implements IReader {

	@Override
	public Collection<ICurrency> read() {
		Collection<ICurrency> result = new ArrayList<>();
		for (ValCurs.Valute valute : RestTemplateReader.getValute().getBody().getValute()) {
			result.add(createCurrency(valute));
		}
		return result;
	}

	private ICurrency createCurrency(ValCurs.Valute valute) {
		Currency result = new Currency();
		result.setId(valute.getID());
		result.setCharCode(valute.getCharCode());
		result.setNumberCode((int) valute.getNumCode());
		result.setName(valute.getName());
		result.setNominal((int) valute.getNominal());
		result.setValue(new BigDecimal(valute.getValue().replace(",", ".")));
		return result;
	}
}
