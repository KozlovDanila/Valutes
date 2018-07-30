package com.example.Currencies;

import java.math.BigDecimal;

/**
 * Интерфейс представления валюты
 */
public interface ICurrency {

	/**
	 * @return идентификатор
	 */
	String getId();

	/**
	 * @return Наименование
	 */
	String getName();

	/**
	 * @return Значение
	 */
	BigDecimal getValue();

	/**
	 * @return Числовой код
	 */
	Integer getNumberCode();

	/**
	 * @return Буквенный код
	 */
	String getCharCode();

	/**
	 * @return номинал
	 */
	Integer getNominal();
}
