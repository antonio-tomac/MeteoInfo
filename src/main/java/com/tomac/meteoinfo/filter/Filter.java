
package com.tomac.meteoinfo.filter;

import com.tomac.meteoinfo.data.DayTimeInfo;
import org.json.JSONObject;

/**
 *
 * @author Antonio Tomac <antonio.tomac@mediatoolkit.com>
 */
public interface Filter {
	
	boolean matchesDayInfo(DayTimeInfo dayInfo);
	
	JSONObject explain();
}
