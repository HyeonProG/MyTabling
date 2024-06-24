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

import tabling.server.json.JsonCustomerInsertDTO;

public class CustomerRequest {
	private URL url;
	private HttpURLConnection conn;
	private Gson gson;
	
	public CustomerRequest() {
		try {
			url = new URL("http://localhost:8080/customer");
			gson = new GsonBuilder().setPrettyPrinting().create();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void insert(String name, String phone, int locationId) {
		try {
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.setRequestProperty("content-type", "application/json");
			JsonCustomerInsertDTO dto = new JsonCustomerInsertDTO(name, phone, locationId);
			dto.setType("insert");
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
