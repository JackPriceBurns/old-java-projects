package com.jack.prisoncells.utils;

import com.jack.prisoncells.PrisonCells;

public class PlayerUtils {
	
	public PlayerUtils(PrisonCells p) {}

	public String colourise(String string) {
		string = string.replaceAll("&", "§");
		return string;
	}
	
	
}
