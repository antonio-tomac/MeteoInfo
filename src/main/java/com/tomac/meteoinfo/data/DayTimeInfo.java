
package com.tomac.meteoinfo.data;

import java.util.Date;
import org.joda.time.DateTime;

/**
 *
 * @author Antonio Tomac <antonio.tomac@mediatoolkit.com>
 */
public class DayTimeInfo {
	
	private final DateTime dateTime;
	private final Double wind;
	private final Double temperature;
	private final Double precipitation;
	private final Double humidity;
	private final Double pressure;

	public DayTimeInfo(DateTime dateTime, Double wind, Double temperature, 
			Double precipitation, Double humidity, Double pressure) {
		this.dateTime = dateTime;
		this.wind = wind;
		this.temperature = temperature;
		this.precipitation = precipitation;
		this.humidity = humidity;
		this.pressure = pressure;
	}

	public DateTime getDateTime() {
		return dateTime;
	}

	public Double getWind() {
		return wind;
	}

	public Double getTemperature() {
		return temperature;
	}

	public Double getPrecipitation() {
		return precipitation;
	}

	public Double getHumidity() {
		return humidity;
	}

	public Double getPressure() {
		return pressure;
	}

	@Override
	public String toString() {
		return "DayTimeInfo{" + "date=" + dateTime + ", wind=" + wind + ", temperature=" + temperature + ", precipitation=" + precipitation + ", humidity=" + humidity + ", pressure=" + pressure + '}';
	}
	
}
