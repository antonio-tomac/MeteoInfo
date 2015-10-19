
package com.tomac.meteoinfo.filter;

import com.tomac.meteoinfo.data.DayTimeInfo;
import org.json.JSONObject;

/**
 *
 * @author Antonio Tomac <antonio.tomac@mediatoolkit.com>
 */
public class NotFilter implements Filter {

	private final Filter filter;

	public static NotFilter not(Filter filter) {
		return new NotFilter(filter);
	}
	
	private NotFilter(Filter filter) {
		this.filter = filter;
	}
	
	@Override
	public boolean matchesDayInfo(DayTimeInfo dayInfo) {
		return !filter.matchesDayInfo(dayInfo);
	}

	@Override
	public JSONObject explain() {
		return new JSONObject()
				.put("not", filter.explain());
	}
	
}
