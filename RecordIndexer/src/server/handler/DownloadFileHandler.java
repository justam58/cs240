package server.handler;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.*;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;


public class DownloadFileHandler implements HttpHandler {
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		Path path = Paths.get(exchange.getRequestURI().getPath());
		exchange.sendResponseHeaders(200,0);
		OutputStream os = exchange.getResponseBody();
		os.write(Files.readAllBytes(path));
		os.close();
	}
}