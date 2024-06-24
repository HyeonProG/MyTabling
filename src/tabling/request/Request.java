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

import tabling.dto.JsonDTO;

public class Request {

	public static String getRequest(String urlStr) throws IOException {
		String str = null;
		URL url = new URL(urlStr);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("content-type", "application/json");

		int responseCode = conn.getResponseCode();
		System.out.println("response code : " + responseCode);

		BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
		while ((inputLine = reader.readLine()) != null) {
			response.append(inputLine);
		}
		reader.close();
		str = response.toString();
		conn.disconnect();
		return str;
	}

	public static String postRequest(String urlStr, JsonDTO dto) throws IOException {

		String str = null;
		URL url = new URL(urlStr);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("POST");
		conn.setDoOutput(true);
		conn.setRequestProperty("content-type", "application/json");
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
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
		str = response.toString();
		return str;
	}
}
