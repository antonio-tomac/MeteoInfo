package com.tomac.meteoinfo.data;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.json.JSONArray;

/**
 *
 * @author Antonio Tomac <antonio.tomac@mediatoolkit.com>
 */
public class MeteoInfoDataFetcher implements DataFetcher {

	private static final String jsUrl = "http://www.meteo-info.hr/javascript/grad/zagreb.js";

	@Override
	public MeteoData getMeteoData() throws IOException {
		HttpClient httpClient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(jsUrl);
		HttpResponse httpResponse = httpClient.execute(httpGet);
		HttpEntity httpEntity = httpResponse.getEntity();
		InputStream contentStream = httpEntity.getContent();
		String content = IOUtils.toString(contentStream);
		Pattern jsonRegex = Pattern.compile("new Highcharts.Chart\\((.*?)\\);", Pattern.DOTALL);
		Pattern chartTypeRegex = Pattern.compile("chart.*?renderTo.*?'(.*?)'", Pattern.DOTALL);
		Pattern dataRegex = Pattern.compile("series.*?data.*?(\\[.*?\\])", Pattern.DOTALL);
		Matcher matcher = jsonRegex.matcher(content);
		Map<String, JSONArray> rawdata = new HashMap<>();
		while (matcher.find()) {
			String group = matcher.group(1);
			Matcher chartMatcher = chartTypeRegex.matcher(group);
			chartMatcher.find();
			String chartType = chartMatcher.group(1);
			Matcher dataMatcher = dataRegex.matcher(group);
			dataMatcher.find();
			JSONArray data = new JSONArray(dataMatcher.group(1));
			rawdata.put(chartType, data);
		}
		Map<DateTime, DayTimeInfo> daysInfos = new TreeMap<>();
		DateTime currentDateTime = new DateTime(System.currentTimeMillis(), DateTimeZone.forID("Europe/Zagreb"));
		DateTime todayStart = currentDateTime
				.withMillisOfSecond(0)
				.withSecondOfMinute(0)
				.withMinuteOfHour(0)
				.withHourOfDay(0);
		for (int i = 0; i < 8 * 3; i++) {
			DateTime dateTime = todayStart.plusHours(i * 3);
			Double wind = rawdata.get("vjetar").getDouble(i);
			Double temperature = rawdata.get("temperatura").getDouble(i);
			Double precipitation = rawdata.get("oborine").getDouble(i);
			Double humidity = rawdata.get("vlaznost").getDouble(i);
			Double pressure = rawdata.get("tlak").getDouble(i);
			DayTimeInfo dayInfo = new DayTimeInfo(
					dateTime, wind, temperature, precipitation, humidity, pressure
			);
			daysInfos.put(dateTime, dayInfo);
		}
		return new MeteoData(daysInfos);
	}

	public static void main(String[] args) throws IOException {
		DataFetcher dataFetcher = new MeteoInfoDataFetcher();
		MeteoData meteoData = dataFetcher.getMeteoData();
		Map<DateTime, DayTimeInfo> dayInfos = meteoData.getDayInfos();
		for (Map.Entry<DateTime, DayTimeInfo> entry : dayInfos.entrySet()) {
			DateTime dateTime = entry.getKey();
			DayTimeInfo dayInfo = entry.getValue();
			System.out.println(dateTime.getDayOfWeek()+") "+dayInfo);
		}
	}

}
