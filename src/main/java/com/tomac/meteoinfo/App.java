package com.tomac.meteoinfo;

import com.tomac.meteoinfo.data.DataFetcher;
import com.tomac.meteoinfo.data.DayTimeInfo;
import com.tomac.meteoinfo.data.MeteoData;
import com.tomac.meteoinfo.data.MeteoInfoDataFetcher;
import com.tomac.meteoinfo.filter.AndFilter;
import com.tomac.meteoinfo.filter.DayFilter;
import com.tomac.meteoinfo.filter.DayFilter.Day;
import com.tomac.meteoinfo.filter.Filter;
import com.tomac.meteoinfo.filter.NotFilter;
import com.tomac.meteoinfo.filter.OrFilter;
import com.tomac.meteoinfo.filter.PecritipationFilter;
import com.tomac.meteoinfo.filter.TimeFilter;
import com.tomac.meteoinfo.filter.WindFilter;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDateTime;

/**
 * Hello world!
 *
 */
public class App {

	public static void main(String[] args) throws IOException, InterruptedException {
		String[] emails = new String[]{
			"3007maks@gmail.com",
			"antonio.tomac1989@gmail.com"
		};
		Filter filter = AndFilter.allOf(
				WindFilter.above(4.0),
				PecritipationFilter.below(0.1),
				OrFilter.anyOf(
						TimeFilter.fromTo(18, 23),
						DayFilter.forDays(Day.SATURDAY, Day.SUNDAY)
				),
				NotFilter.not(
						DayFilter.forDays(Day.WEDNESDAY)
				)
		);
		List<Integer> hoursWhenCheck = Arrays.asList(0, 8, 16);
		DataFetcher dataFetcher = new MeteoInfoDataFetcher();
		while (true) {
			long currentTime = System.currentTimeMillis();
			long timeNextFullHour = currentTime - currentTime % (1000L * 3600) + 1000L * 3600;
			long sleepTime = timeNextFullHour - currentTime;
			System.out.println("Sleeping for " + (sleepTime / 1000.) + " seconds...");
			Thread.sleep(sleepTime);
			LocalDateTime dateTime = new LocalDateTime(timeNextFullHour, DateTimeZone.forID("Europe/Zagreb"));
			if (hoursWhenCheck.contains(dateTime.getHourOfDay())) {
				System.out.println("Processing time: " + dateTime);
				process(dataFetcher, filter, emails);
			}
		}
	}

	protected static void process(DataFetcher dataFetcher, Filter filter, String[] emails) throws IOException {
		MeteoData meteoData = dataFetcher.getMeteoData();
		List<DayTimeInfo> matching = new LinkedList<>();
		for (DayTimeInfo dayTimeInfo : meteoData.getDayInfos().values()) {
			if (dayTimeInfo.getWind() >= 4.0 && dayTimeInfo.getDateTime().getHourOfDay() == 18) {
				matching.add(dayTimeInfo);
				System.out.println(dayTimeInfo);
			}
		}
		if (!matching.isEmpty()) {
			DayTimeInfo[] dayTimeInfos = matching.toArray(new DayTimeInfo[matching.size()]);
			AlertTrigger.emailNotification(emails, filter, dayTimeInfos);
		} else {
			System.out.println("no matches");
		}
	}
}
