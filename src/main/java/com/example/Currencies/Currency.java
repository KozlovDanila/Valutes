package com.example.Currencies;

import java.math.BigDecimal;

public class Currency implements ICurrency {

	private String id;
	private String name;
	private BigDecimal value;
	private Integer numberCode;
	private String charCode;
	private Integer nominal;

	@Override
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	@Override
	public Integer getNumberCode() {
		return numberCode;
	}

	public void setNumberCode(Integer numberCode) {
		this.numberCode = numberCode;
	}

	@Override
	public String getCharCode() {
		return charCode;
	}

	public void setCharCode(String charCode) {
		this.charCode = charCode;
	}

	@Override
	public Integer getNominal() {
		return nominal;
	}

	public void setNominal(Integer nominal) {
		this.nominal = nominal;
	}
}
