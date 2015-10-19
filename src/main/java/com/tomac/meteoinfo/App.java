package com.tomac.meteoinfo;

import com.tomac.meteoinfo.data.DayTimeInfo;
import com.tomac.meteoinfo.data.MeteoData;
import com.tomac.meteoinfo.data.MeteoInfoDataFetcher;
import com.tomac.meteoinfo.filter.AndFilter;
import com.tomac.meteoinfo.filter.DayFilter;
import com.tomac.meteoinfo.filter.DayFilter.Day;
import com.tomac.meteoinfo.filter.Filter;
import com.tomac.meteoinfo.filter.OrFilter;
import com.tomac.meteoinfo.filter.PecritipationFilter;
import com.tomac.meteoinfo.filter.TimeFilter;
import com.tomac.meteoinfo.filter.WindFilter;
import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App {

	public static void main(String[] args) throws IOException, InterruptedException {
		Filter filter = AndFilter.allOf(
				WindFilter.above(2.0),
				PecritipationFilter.below(0.1),
				OrFilter.anyOf(
						TimeFilter.fromTo(18, 23),
						DayFilter.forDays(Day.SATURDAY, Day.SUNDAY)
				)
		);
		while (true) {
			MeteoData meteoData = new MeteoInfoDataFetcher().getMeteoData();
			List<DayTimeInfo> matching = new LinkedList<>();
			for (DayTimeInfo dayTimeInfo : meteoData.getDayInfos().values()) {
				if (filter.matchesDayInfo(dayTimeInfo)) {
					matching.add(dayTimeInfo);
					System.out.println(dayTimeInfo);
				}
			}
			if (!matching.isEmpty()) {
				DayTimeInfo[] toArray = matching.toArray(new DayTimeInfo[matching.size()]);
				//AlertTrigger.emailNotification(filter, toArray);
			} else {
				System.out.println("no matches");
			}
			Thread.sleep(1000 * 3600 * 8);
		}
	}
}
