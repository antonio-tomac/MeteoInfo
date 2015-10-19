package com.tomac.meteoinfo.data;

import java.io.IOException;


/**
 *
 * @author Antonio Tomac <antonio.tomac@mediatoolkit.com>
 */
public interface DataFetcher {
	
	MeteoData getMeteoData() throws IOException;
	
}
