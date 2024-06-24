package tabling.request;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import tabling.json.JsonDTO;

public class ReservationRequest {
	private URL url;
	private String urlStr;
	private HttpURLConnection conn;
	private Gson gson;
	
	public ReservationRequest() {
		urlStr = "http://localhost:8080/reservation";
		gson = new GsonBuilder().setPrettyPrinting().create();
	}
	
	public void reservation(int customerId, int restaurantId) {
		try {
			String reservationUrl = urlStr + "/" + "reservation";
			url = new URL(reservationUrl);
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.setRequestProperty("content-type", "application/json");
			JsonDTO dto = new JsonDTO(customerId, restaurantId);
			String json = gson.toJson(dto);
			
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
			writer.write(json);
			writer.flush();
			writer.close();
			
			int responseCode = conn.getResponseCode();
			System.out.println("response code : " + responseCode);
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
			while ((inputLine = reader.readLine()) != null) {
				response.append(inputLine);
			}
			reader.close();
			System.out.println(response);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			conn.disconnect();
		}
	}
	
	public void cancel(int customerId, int restaurantId) {

		try {
			String cancelUrl = urlStr + "/" + "cancel";
			url = new URL(cancelUrl);
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.setRequestProperty("content-type", "application/json");
			JsonDTO dto = new JsonDTO(customerId, restaurantId);
			String json = gson.toJson(dto);
			
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
			writer.write(json);
			writer.flush();
			writer.close();
			
			int responseCode = conn.getResponseCode();
			System.out.println("response code : " + responseCode);
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
			while ((inputLine = reader.readLine()) != null) {
				response.append(inputLine);
			}
			reader.close();
			System.out.println(response);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			conn.disconnect();
		}
	
	}
}
