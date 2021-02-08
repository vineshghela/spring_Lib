package com.qa.springExample.global;

import lombok.Getter;

@Getter
public enum LIBRARY {

	LL("London Library"), KL("Kenyan Library"), AL("American Library");

	private final String name;

	LIBRARY(String name) {
		this.name = name;
	}

}
