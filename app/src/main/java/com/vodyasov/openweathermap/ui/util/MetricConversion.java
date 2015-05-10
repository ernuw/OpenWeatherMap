package com.vodyasov.openweathermap.ui.util;

public class MetricConversion
{
	private MetricConversion() {}

	public static double paTommHg(double pa)
	{
		final double mmHg = 133.3223684, eps = 2;
		double result = pa / mmHg;
		double d = Math.pow(10, eps);
		return Math.floor(result * d) / d;
	}

	public static double KelvinToCelsius(double temperature)
	{
		final double delta = 273.15, eps = 2;
		double d = Math.pow(10, eps);
		return Math.floor((temperature - delta) * d) / d ; 
	}

	public static String windDirection(double deg)
	{
		final int DEGREES  = 360, SECTOR_COUNT = 16;
		final double SECTOR_SIZE = DEGREES / SECTOR_COUNT;
		final String VALUES[] = new String[]
								 {
									 "Север", "Север Северо-Восток", "Северо-Восток", "Восток Северо-Восток",
									 "Восток", "Восток Юго-Восток", "Юго-Восток", "Юг Юго-Восток",
									 "Юг", "Юг Юго-Запад", "Юго-Запад", "Запад Юго-Запад",
									 "Запад", "Запад Северо-Запад", "Северо-Запад", "Север Северо-Запад",
									 "Север"
								 };
		int i = (int) Math.floor( ((double) (deg / SECTOR_SIZE)) + 0.5);
		return VALUES[i];
	}
}
