package com.tomac.meteoinfo.filter;

import com.tomac.meteoinfo.data.DayTimeInfo;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Antonio Tomac <antonio.tomac@mediatoolkit.com>
 */
public class DayFilter implements Filter {

	public enum Day {

		MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY
	}

	public static final DayFilter ANY_DAY = DayFilter.forDays(Day.values());

	private static int toDayOfWeek(Day day) {
		return Arrays.binarySearch(Day.values(), 0, Day.values().length, day) + 1;
	}

	private final Set<Integer> daysInWeek;

	private DayFilter(Set<Integer> daysInWeek) {
		this.daysInWeek = daysInWeek;
	}

	public static DayFilter forDays(Day... days) {
		Set<Integer> daysSet = new LinkedHashSet<>();
		for (Day day : days) {
			daysSet.add(toDayOfWeek(day));
		}
		return new DayFilter(daysSet);
	}

	public static DayFilter forDays(Integer... days) {
		return new DayFilter(new LinkedHashSet<>(Arrays.asList(days)));
	}

	@Override
	public boolean matchesDayInfo(DayTimeInfo dayInfo) {
		return daysInWeek.contains(dayInfo.getDateTime().getDayOfWeek());
	}

	@Override
	public JSONObject explain() {
		JSONArray days = new JSONArray();
		for (Integer dayIndex : daysInWeek) {
			days.put(Day.values()[dayIndex - 1].toString());
		}
		return new JSONObject()
				.put("days", days);
	}

}
