package tabling.request;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class LocationRequest {
	private URL url;
	private String urlStr;
	private HttpURLConnection conn;
	private Gson gson;

	public LocationRequest() {
		urlStr = "http://localhost:8080/location";
		gson = new GsonBuilder().setPrettyPrinting().create();
	}

	public String select(int locationId) {
		String str = null;
		try {
			String selectUrl = urlStr + "/" + locationId;
			url = new URL(selectUrl);
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("content-type", "application/json");

			int responseCode = conn.getResponseCode();
			System.out.println("response code:" + responseCode);

			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String inputLine;
			StringBuffer bufferStr = new StringBuffer();
			while ((inputLine = reader.readLine()) != null) {
				bufferStr.append(inputLine);
			}
			reader.close();
			str = bufferStr.toString();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.disconnect();
		}
		return str;
	}

}
