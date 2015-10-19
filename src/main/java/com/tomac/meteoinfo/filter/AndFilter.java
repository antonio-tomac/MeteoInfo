
package com.tomac.meteoinfo.filter;

import com.tomac.meteoinfo.data.DayTimeInfo;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Antonio Tomac <antonio.tomac@mediatoolkit.com>
 */
public class AndFilter implements Filter {
	
	private final Filter[] subFilters;

	public static AndFilter allOf(Filter... filters) {
		return new AndFilter(filters);
	}
	
	private AndFilter(Filter[] subFilters) {
		this.subFilters = subFilters;
	}

	@Override
	public boolean matchesDayInfo(DayTimeInfo dayInfo) {
		for (Filter filter : subFilters) {
			if (!filter.matchesDayInfo(dayInfo)) {
				return false;
			}
		}
		return true;
	}
		
	@Override
	public JSONObject explain() {
		JSONArray all = new JSONArray();
		for (Filter filter : subFilters) {
			all.put(filter.explain());
		}
		return new JSONObject()
				.put("all_of", all);
	}
	

}
