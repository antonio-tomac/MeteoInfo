package com.tomac.meteoinfo;

import com.tomac.meteoinfo.data.DayTimeInfo;
import com.tomac.meteoinfo.filter.Filter;
import javax.mail.MessagingException;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;

/**
 *
 * @author Antonio Tomac <antonio.tomac@mediatoolkit.com>
 */
public class AlertTrigger {

	private static final String from = "kite-forecast@mediatoolkit.com";
	
	private static final DateTimeFormatter timeFormat = new DateTimeFormatterBuilder()
			.appendPattern("dd.MM.yyyy. HH:mm").toFormatter();
	
	private static String toTable(DayTimeInfo[] dayTimeInfos) {
		StringBuilder rows = new StringBuilder();
		rows.append("<tr><td>Time</td><td>Wind</td><td>Precipitation</td><td>Temperature</td></tr>");
		for (DayTimeInfo dayTimeInfo : dayTimeInfos) {
			rows.append("<tr>")
					.append("<td>").append(dayTimeInfo.getDateTime().toString(timeFormat)).append("</td>")
					.append("<td>").append(dayTimeInfo.getWind()).append("</td>")
					.append("<td>").append(dayTimeInfo.getPrecipitation()).append("</td>")
					.append("<td>").append(dayTimeInfo.getTemperature()).append("</td>")
					.append("</tr>");
		}
		return "<table border=\"1px\">" + rows + "</table>";
	}

	public static void emailNotification(String[] emails, Filter filter, DayTimeInfo... dayTimeInfos) {
		String title = "Possible kitting action";
		String table = toTable(dayTimeInfos);
				
		String body
				= "<h2>Founded good conditions</h2>"
				+ table
				+ "<br>"
				+ "<p>Results matches filter:</p>"
				+ "<pre>"
				+ filter.explain().toString(6)
				+ "</pre>";
		Email email = new Email(from, title, body, emails);
		System.out.println(body);
		try {
			email.send();
		} catch (MessagingException ex) {
			throw new RuntimeException(ex);
		}
		System.out.println("sent sucessfully");
	}

}
