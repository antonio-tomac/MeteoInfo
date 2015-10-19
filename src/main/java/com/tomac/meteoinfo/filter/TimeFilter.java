
package com.tomac.meteoinfo.filter;

import com.tomac.meteoinfo.data.DayTimeInfo;
import org.json.JSONObject;

/**
 *
 * @author Antonio Tomac <antonio.tomac@mediatoolkit.com>
 */
public class TimeFilter implements Filter {

	private final int startHour;
	private final int endHour;
	
	public static final TimeFilter ALL_DAY = new TimeFilter(0, 24);
	
	public static TimeFilter fromTo(int fromStartHour, int toEndHour) {
		return new TimeFilter(fromStartHour, toEndHour);
	}

	private TimeFilter(int startHour, int endHour) {
		this.startHour = startHour;
		this.endHour = endHour;
	}
	
	@Override
	public boolean matchesDayInfo(DayTimeInfo dayInfo) {
		int hourOfDay = dayInfo.getDateTime().getHourOfDay();
		return hourOfDay >= startHour && hourOfDay <= endHour;
	}
	
	@Override
	public JSONObject explain() {
		return new JSONObject()
				.put("time", new JSONObject()
						.put("start_hour", startHour)
						.put("end_hour", endHour)
				);
	}
	
}
