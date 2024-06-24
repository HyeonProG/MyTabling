package tabling.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class CustomerHandler implements HttpHandler {

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		String method = exchange.getRequestMethod();
		if ("GET".equalsIgnoreCase(method)) {
			// GET 요청시 여기서 동작
		} else if ("POST".equalsIgnoreCase(method)) {
			// POST 요청시 여기서 동작
			handlePostRequest(exchange);
		}
	}

	// POST 요청시 동작
	private void handlePostRequest(HttpExchange exchange) {
		BufferedReader br = new BufferedReader(new InputStreamReader(exchange.getRequestBody()));
		String inputLine;
		StringBuffer bufferStr = new StringBuffer();
		try {
			while((inputLine = br.readLine()) != null) {
				bufferStr.append(inputLine);
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(bufferStr);
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		JsonDTO dto = gson.fromJson(bufferStr.toString(), JsonDTO.class);
		System.out.println(dto.getType());
	}
	
}
