package server;

import java.io.IOException;

import server.database.Database;
import server.handler.*;
import java.net.*;

import com.sun.net.httpserver.*;


public class Server {

	private static int SERVER_PORT_NUMBER = 8080;
	private static final int MAX_WAITING_CONNECTIONS = 10;
	
	public static void main(String[] args){
		if(args.length != 0){
			SERVER_PORT_NUMBER = Integer.parseInt(args[0]);
		}
		new Server(SERVER_PORT_NUMBER).run();
	}
	
	private HttpServer server;
	
	private Server(int port){
		SERVER_PORT_NUMBER = port;
	}
	
	private Server(){
		return;
	}
	
	private void run(){
		Database.initialize();	
		try{
			server = HttpServer.create(new InetSocketAddress(SERVER_PORT_NUMBER), MAX_WAITING_CONNECTIONS);
		} 
		catch(IOException e){
			System.out.println("Could not create HTTP server");
			e.printStackTrace();
			return;
		}
		
		server.setExecutor(null); // use the default executor
		
		server.createContext("/", (HttpHandler)new DownloadFileHandler());
		server.createContext("/ValidateUser", (HttpHandler)new ValidateUserHandler());
		server.createContext("/GetProjects", (HttpHandler)new GetProjectsHandler());
		server.createContext("/GetSampleImage", (HttpHandler)new GetSampleImageHandler());
		server.createContext("/GetFields", (HttpHandler)new GetFieldsHandler());
		server.createContext("/SubmitBatch", (HttpHandler)new SubmitBatchHandler());
		server.createContext("/Search", (HttpHandler)new SearchHandler());
		server.createContext("/DownloadBatch", (HttpHandler)new DownloadBatchHandler());

		server.start();
	}
}
