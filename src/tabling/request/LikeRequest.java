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

public class LikeRequest {
	private URL url;
	private String urlStr;
	private HttpURLConnection conn;
	private Gson gson;

	public LikeRequest() {
		urlStr = "http://localhost:8080/like";
		gson = new GsonBuilder().setPrettyPrinting().create();
	}

	public int insert(int customerId, int restaurantId) {
		int likeCount = 0;
		try {
			String insertUrl = urlStr + "/" + "insert";
			url = new URL(insertUrl);
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
			likeCount = Integer.parseInt(response.toString());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			conn.disconnect();
		}
		return likeCount;
	}

	public boolean getLike(int customerId, int restaurantId) {
		boolean like = false;
		try {
			String selectUrl = urlStr + "/getLike/" + "customerId=" + String.valueOf(customerId) + "&" + "restaurantId="
					+ String.valueOf(restaurantId);
			url = new URL(selectUrl);
			conn = (HttpURLConnection) url.openConnection();
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
			like = Boolean.parseBoolean(bufferStr.toString());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			conn.disconnect();
		}
		return like;
	}

	public int delete(int customerId, int restaurantId) {
		int likeCount = 0;
		try {
			String deleteUrl = urlStr + "/delete/" + "customerId=" + String.valueOf(customerId) + "&" + "restaurantId="
					+ String.valueOf(restaurantId);
			url = new URL(deleteUrl);
			conn = (HttpURLConnection) url.openConnection();
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
			likeCount = Integer.parseInt(bufferStr.toString());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			conn.disconnect();
		}
		return likeCount;
	}

	public int getLikeCount(int restaurantId) {
		int likeCount = 0;
		try {
			String selectUrl = urlStr + "/getLikeCount/" + "customerId=" + String.valueOf(0) + "&" + "restaurantId="
					+ String.valueOf(restaurantId);
			url = new URL(selectUrl);
			conn = (HttpURLConnection) url.openConnection();
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
			likeCount = Integer.parseInt(bufferStr.toString());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			conn.disconnect();
		}
		return likeCount;
	}
}
