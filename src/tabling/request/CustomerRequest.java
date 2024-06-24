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

import tabling.dto.CustomerDTO;
import tabling.json.JsonDTO;

public class CustomerRequest {
	private URL url;
	private String urlStr;
	private HttpURLConnection conn;
	private Gson gson;

	public CustomerRequest() {
		urlStr = "http://localhost:8080/customer";
		gson = new GsonBuilder().setPrettyPrinting().create();
	}

	public void insert(String customerName, String customerPhone, int locationId) {
		try {
			String insertUrl = urlStr + "/" + "insert";
			url = new URL(insertUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.setRequestProperty("content-type", "application/json");
			JsonDTO dto = new JsonDTO(customerName, customerPhone, locationId);
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
		}
	}

	public CustomerDTO select(String phone) {
		CustomerDTO dto = null;
		try {
			String selectUrl = urlStr + "/" + phone;
			url = new URL(selectUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("content-type", "application/json");
			
			int responseCode = conn.getResponseCode();
			System.out.println("response code : " + responseCode);
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String inputLine;
			StringBuffer bufferStr = new StringBuffer();
			while ((inputLine = reader.readLine()) != null) {
				bufferStr.append(inputLine);
			}
			reader.close();
			dto = gson.fromJson(bufferStr.toString(), CustomerDTO.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return dto;
	}
	
	public void update(String customerName, String customerPhone, int locationId) {
		try {
			String updateUrl = urlStr + "/" + "update";
			url = new URL(updateUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.setRequestProperty("content-type", "application/json");
			JsonDTO dto = new JsonDTO(customerName, customerPhone, locationId);
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
		}
	
		
	}
}
