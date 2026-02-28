package com.adminremit.common.util;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class NumberUtil {
	
	public static String formatValueToGivenDecimalPlaceFormat(String format, String value) {
		DecimalFormat df = new DecimalFormat(format);
		return df.format(Double.parseDouble(value));
	}
	
	public static String formatValueToGivenDecimalPlaceFormatWithoutRounding(String format, String value) {
		DecimalFormat df = new DecimalFormat(format);
		df.setRoundingMode(RoundingMode.DOWN);
		return df.format(Double.parseDouble(value));
	}
}
