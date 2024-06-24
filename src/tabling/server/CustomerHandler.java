package tabling.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.sql.SQLException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import tabling.dao.CustomerDAO;
import tabling.server.json.JsonCustomerInsertDTO;
import tabling.server.json.JsonDTO;

public class CustomerHandler implements HttpHandler {

	private CustomerDAO dao;
	
	public CustomerHandler() {
		dao = new CustomerDAO();
	}
	
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
			while ((inputLine = br.readLine()) != null) {
				bufferStr.append(inputLine);
			}
			br.close();
			System.out.println(bufferStr);
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			JsonDTO dto = gson.fromJson(bufferStr.toString(), JsonDTO.class);
			System.out.println(dto.getType());
			if (dto.getType().equals("insert")) {
				System.out.println("insert값 들어옴");
				JsonCustomerInsertDTO insertDTO = gson.fromJson(bufferStr.toString(), JsonCustomerInsertDTO.class);
				try {
					dao.addCustomer(insertDTO.getName(), insertDTO.getPhone(), insertDTO.getLocationId());
				} catch (SQLException e) {
					e.printStackTrace();
				}
				System.out.println(insertDTO);
			}
			String response = "회원가입 성공";
			byte[] bytes = response.getBytes();
			exchange.setAttribute("Content-Type", "text/plain; charset=UTF-8");
			exchange.sendResponseHeaders(200, bytes.length);
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(exchange.getResponseBody()));
			bw.write(response);
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
