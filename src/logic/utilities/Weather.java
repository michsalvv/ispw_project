package logic.utilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.nio.charset.Charset;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Weather {
	
	public static double convert(double kelvin) {
		BigDecimal bd = new BigDecimal(kelvin-273.15).setScale(1, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}
	
	private static String readAll(Reader rd) throws IOException {
	    StringBuilder sb = new StringBuilder();
	    int cp;
	    while ((cp = rd.read()) != -1) {
	      sb.append((char) cp);
	    }
	    return sb.toString();
	}

	public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
		InputStream is = new URL(url).openStream();
		try {
	      BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
	      String jsonText = readAll(rd);
	      JSONObject json = new JSONObject(jsonText);
	      return json;
	    } finally {
	      is.close();
	    }
	}
	
	public static JSONArray getInfo() {

		String API_KEY = "a4f22e032f9d48ee8fd3a2dfe5101878";
		//String LOCATION = "Rome, IT";
		String LAT = "41.89";
		String LON = "12.48";
		//String urlString = "http://api.openweathermap.org/data/2.5/weather?q=" + LOCATION + "&appid=" + API_KEY + "&units=imperial";
		String urlString = "https://api.openweathermap.org/data/2.5/onecall?lat=" + LAT + "&lon=" + LON +"&exclude=daily,minutely,current,alerts&appid=" + API_KEY;

		JSONObject json;
		
		try {
			json = readJsonFromUrl(urlString);
		    JSONArray array = json.getJSONArray("hourly");
		    return array;
		    
		} catch (JSONException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
