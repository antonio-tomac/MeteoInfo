package com.tomac.meteoinfo.filter;

import com.tomac.meteoinfo.data.DayTimeInfo;
import org.json.JSONObject;

/**
 *
 * @author Antonio Tomac <antonio.tomac@mediatoolkit.com>
 */
public class WindFilter implements Filter {

	private final Double aboveOrEqual;
	private final Double belowOrEqual;

	public static final WindFilter ANY_SPEED = new WindFilter(null, null);

	public static WindFilter below(Double belowOrEqual) {
		return new WindFilter(null, belowOrEqual);
	}

	public static WindFilter above(Double aboveOrEqual) {
		return new WindFilter(aboveOrEqual, null);
	}

	public static WindFilter aboveAndBelow(Double aboveOrEqual, Double belowOrEqual) {
		return new WindFilter(aboveOrEqual, belowOrEqual);
	}

	private WindFilter(Double aboveOrEqual, Double belowOrEqual) {
		this.aboveOrEqual = aboveOrEqual;
		this.belowOrEqual = belowOrEqual;
	}

	@Override
	public boolean matchesDayInfo(DayTimeInfo dayInfo) {
		return (aboveOrEqual == null || dayInfo.getWind() >= aboveOrEqual)
				&& (belowOrEqual == null || dayInfo.getWind() <= belowOrEqual);
	}

	@Override
	public JSONObject explain() {
		return new JSONObject()
				.put("wind", new JSONObject()
						.put("above", aboveOrEqual)
						.put("below", belowOrEqual)
				);
	}

}
