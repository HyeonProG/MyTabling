package tabling.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URI;
import java.sql.SQLException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import tabling.dao.CustomerDAO;
import tabling.dto.CustomerDTO;
import tabling.dto.JsonDTO;

public class CustomerHandler implements HttpHandler {

	private CustomerDAO dao;
	private Gson gson;

	public CustomerHandler() {
		dao = new CustomerDAO();
		gson = new GsonBuilder().setPrettyPrinting().create();
	}

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		String method = exchange.getRequestMethod();
		if ("GET".equalsIgnoreCase(method)) {
			// GET 요청시 여기서 동작
			handleGetRequest(exchange);
		} else if ("POST".equalsIgnoreCase(method)) {
			// POST 요청시 여기서 동작
			handlePostRequest(exchange);
		}
	}

	// GET 요청시 동작
	private void handleGetRequest(HttpExchange exchange) {
		URI uri = exchange.getRequestURI();
		String path = uri.getPath();
		String response = null;
		String[] pathSegments = path.split("/");
		if (pathSegments.length >= 4) {
			String type = pathSegments[2];
			String customerPhone = pathSegments[3];
			try {
				// 회원 정보 요청 응답
				if (type.equalsIgnoreCase("select")) {
					CustomerDTO dto = dao.getCustomerByPhone(customerPhone);
					response = gson.toJson(dto);
					// 회원 삭제 요청 응답
				} else if (type.equalsIgnoreCase("delete")) {
					dao.deleteCustomer(customerPhone);
					OnlineCustomer.getCustomerPhone().remove(customerPhone);
					response = "회원 삭제 성공";
					// 로그인 요청 응답
				} else if (type.equalsIgnoreCase("login")) {
					if (dao.getCustomerByPhone(customerPhone) == null) {
						response = "no";
					} else {
						response = String.valueOf(OnlineCustomer.getCustomerPhone().add(customerPhone));
					}
					// 로그아웃 요청 응답
				} else if (type.equalsIgnoreCase("logout")) {
					if (OnlineCustomer.getCustomerPhone().contains(customerPhone)) {
						OnlineCustomer.getCustomerPhone().remove(customerPhone);
						response = "로그아웃 성공";
					}
				}
				try {
					byte[] bytes = response.getBytes();
					exchange.setAttribute("Content-Type", "text/plain; charset=UTF-8");
					exchange.sendResponseHeaders(200, bytes.length);
					BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(exchange.getResponseBody()));
					writer.write(response);
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	// POST 요청시 동작
	private void handlePostRequest(HttpExchange exchange) {
		URI uri = exchange.getRequestURI();
		String path = uri.getPath();
		String[] pathSegments = path.split("/");
		String protocol = null;
		if (pathSegments.length >= 3) {
			protocol = pathSegments[2];
		}
		BufferedReader br = new BufferedReader(new InputStreamReader(exchange.getRequestBody()));
		String response = null;
		String inputLine;
		StringBuffer bufferStr = new StringBuffer();
		try {
			while ((inputLine = br.readLine()) != null) {
				bufferStr.append(inputLine);
			}
			br.close();
			// 회원 가입 요청 응답
			if (protocol.equalsIgnoreCase("insert")) {
				JsonDTO insertDTO = gson.fromJson(bufferStr.toString(), JsonDTO.class);
				try {
					dao.addCustomer(insertDTO.getCustomerName(), insertDTO.getCustomerPhone(), insertDTO.getLocationId());
					response = "회원가입 성공";
				} catch (SQLException e) {
					e.printStackTrace();
				}
				// 회원 정보 수정 요청 응답
			} else if (protocol.equalsIgnoreCase("update")) {
				JsonDTO updateDTO = gson.fromJson(bufferStr.toString(), JsonDTO.class);
				try {
					dao.updateCustomer(updateDTO.getCustomerName(), updateDTO.getCustomerPhone(), updateDTO.getLocationId());
					response = "회원정보 수정 성공";
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			byte[] bytes = response.getBytes();
			exchange.setAttribute("Content-Type", "text/plain; charset=UTF-8");
			exchange.sendResponseHeaders(200, bytes.length);
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(exchange.getResponseBody()));
			writer.write(response);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
