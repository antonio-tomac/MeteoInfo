package com.tomac.meteoinfo.filter;

import com.tomac.meteoinfo.data.DayTimeInfo;
import org.json.JSONObject;

/**
 *
 * @author Antonio Tomac <antonio.tomac@mediatoolkit.com>
 */
public class PecritipationFilter implements Filter {

	private final Double aboveOrEqual;
	private final Double belowOrEqual;

	public static final PecritipationFilter ANY = new PecritipationFilter(null, null);

	public static PecritipationFilter below(Double belowOrEqual) {
		return new PecritipationFilter(null, belowOrEqual);
	}

	public static PecritipationFilter above(Double aboveOrEqual) {
		return new PecritipationFilter(aboveOrEqual, null);
	}

	public static PecritipationFilter aboveAndBelow(Double aboveOrEqual, Double belowOrEqual) {
		return new PecritipationFilter(aboveOrEqual, belowOrEqual);
	}

	private PecritipationFilter(Double aboveOrEqual, Double belowOrEqual) {
		this.aboveOrEqual = aboveOrEqual;
		this.belowOrEqual = belowOrEqual;
	}

	@Override
	public boolean matchesDayInfo(DayTimeInfo dayInfo) {
		return (aboveOrEqual == null || dayInfo.getPrecipitation() >= aboveOrEqual)
				&& (belowOrEqual == null || dayInfo.getPrecipitation() <= belowOrEqual);
	}
		
	@Override
	public JSONObject explain() {
		return new JSONObject()
				.put("precritipation", new JSONObject()
						.put("above", aboveOrEqual)
						.put("below", belowOrEqual)
				);
	}

}
