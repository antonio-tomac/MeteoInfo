package com.tomac.meteoinfo.data;

import java.util.Collections;
import java.util.Map;
import org.joda.time.DateTime;

/**
 *
 * @author Antonio Tomac <antonio.tomac@mediatoolkit.com>
 */
public class MeteoData {

	private final Map<DateTime, DayTimeInfo> dayInfos;

	public MeteoData(Map<DateTime, DayTimeInfo> dayInfos) {
		this.dayInfos = dayInfos;
	}

	public Map<DateTime, DayTimeInfo> getDayInfos() {
		return Collections.unmodifiableMap(dayInfos);
	}
	
	public DayTimeInfo getDayInfo(DateTime dateTime) {
		return dayInfos.get(dateTime);
	}
	
}
