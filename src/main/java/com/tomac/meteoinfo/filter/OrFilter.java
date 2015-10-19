
package com.tomac.meteoinfo.filter;

import com.tomac.meteoinfo.data.DayTimeInfo;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Antonio Tomac <antonio.tomac@mediatoolkit.com>
 */
public class OrFilter implements Filter {
	
	private final Filter[] subFilters;

	public static OrFilter anyOf(Filter... filters) {
		return new OrFilter(filters);
	}
	
	private OrFilter(Filter[] subFilters) {
		this.subFilters = subFilters;
	}

	@Override
	public boolean matchesDayInfo(DayTimeInfo dayInfo) {
		for (Filter filter : subFilters) {
			if (filter.matchesDayInfo(dayInfo)) {
				return true;
			}
		}
		return false;
	}
			
	@Override
	public JSONObject explain() {
		JSONArray any = new JSONArray();
		for (Filter filter : subFilters) {
			any.put(filter.explain());
		}
		return new JSONObject()
				.put("any_of", any);
	}

}
