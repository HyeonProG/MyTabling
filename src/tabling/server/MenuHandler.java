package tabling.server;

import java.io.IOException;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class MenuHandler implements HttpHandler {

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		String method = exchange.getRequestMethod();
		if ("GET".equalsIgnoreCase(method)) {
			// GET 요청시 여기서 동작
			handleGetRequest(exchange);
		} else {
			// GET이 아닌 요청시
		}
	}

	private void handleGetRequest(HttpExchange exchange) {
		
	}

}
