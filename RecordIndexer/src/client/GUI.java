package client;

import java.awt.EventQueue;

import client.communication.ServerCommunicator;
import client.views.dialogs.LoginDialog;



public class GUI{
	
	private static ServerCommunicator sc = ServerCommunicator.getInstance();
	private static String PORT = "8080";
	private static String HOST = "localhost";
	
	public static void main(String[] args) {
		if(args.length != 0){
			HOST = args[0];
			PORT = args[1];
			sc.setHostandPort(HOST, PORT);	
		}
		EventQueue.invokeLater(
			new Runnable() {
				public void run() {
					LoginDialog login = new LoginDialog();
					login.setVisible(true);
				}
			}
			
		);

	}

}