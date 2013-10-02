package client.communication;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import shared.communication.*;


/**Class that allows client to communicate with the server */
public class ServerCommunicator {
	
	private String URLPrefix = null;
	
    private static ServerCommunicator instance = new ServerCommunicator();

    public static ServerCommunicator getInstance() {
        return instance;
    }
    
	public ServerCommunicator(String h, String p){
		URLPrefix = "http://" + h + ":" + p;
	}
  
	
	public ServerCommunicator(){
		InetAddress address;
		try {
			address = InetAddress.getLocalHost();
			String host = address.getHostAddress();
			URLPrefix = "http://" + host + ":8080";
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
	
	public void setHostandPort(String host, String port){
		URLPrefix = "http://" + host + ":" + port;
	}
	
	/** ValidateUser
	 * 
	 * @param input	ValidateUser_Input
	 * @return ValidateUser_Output
	 * @throws ClientException
	 */
	public ValidateUser_Output validateUser(ValidateUser_Input input){
		return (ValidateUser_Output)doPost("/ValidateUser", input);
	}
	
	/** GetProjects
	 * 
	 * @param input	GetProjects_Output
	 * @return GetProjects_Output
	 * @throws ClientException
	 */
	public GetProjects_Output getProjects(GetProjects_Input input){
		return (GetProjects_Output)doPost("/GetProjectst", input);
	}
	
	/** GetFields
	 * 
	 * @param input	GetFields_Input
	 * @return GetFields_Output
	 * @throws ClientException
	 */
	public GetFields_Output getFields(GetFields_Input input){
		return (GetFields_Output)doPost("/GetFields", input);
	}
	
	/** GetSampleImage
	 * 
	 * @param input	GetSampleImage_Input
	 * @return GetSampleImage_Output
	 * @throws ClientException
	 */
	public GetSampleImage_Output getSampleImage(GetSampleImage_Input input){
		return (GetSampleImage_Output)doPost("/GetSampleImage", input);
	}
	
	/** Search
	 * 
	 * @param input	Search_Input
	 * @return Search_Output
	 * @throws ClientException
	 */
	public Search_Output search(Search_Input input){
		return (Search_Output)doPost("/Search", input);
	}
	
	/** DownloadBatch
	 * 
	 * @param input	DownloadBatch_Input
	 * @return DownloadBatch_Output
	 * @throws ClientException
	 */
	public DownloadBatch_Output downloadBatch(DownloadBatch_Input input){
		return (DownloadBatch_Output)doPost("/DownloadBatch", input);
	}
	
	/** SubmitBatch
	 * 
	 * @param input	SubmitBatch_Input
	 * @return SubmitBatch_Output
	 * @throws ClientException
	 */
	public SubmitBatch_Output submitBatch(SubmitBatch_Input input){
		return (SubmitBatch_Output)doPost("/SubmitBatch", input);
	}

	/** Make HTTP POST request to the specified URL 
	 * and return the object returned by the server
	 * 
	 * @param urlPath
	 * @return output by the server
	 * @throws ClientException
	 * @throws IOException 
	 */
	private Object doPost(String urlPath, Object input){
		Object response = null;
		try{
			URL url = new URL(URLPrefix + urlPath);	   
			HttpURLConnection connection = (HttpURLConnection)url.openConnection();
			connection.setRequestMethod("POST");
			connection.setDoOutput(true);
			
			connection.connect();
			OutputStream requestBody = connection.getOutputStream();
			XStream xStream = new XStream(new DomDriver());
			xStream.toXML(input, requestBody);
			requestBody.close();
			
			if(connection.getResponseCode() == HttpURLConnection.HTTP_OK){
				InputStream responseBody = connection.getInputStream();
				response = xStream.fromXML(responseBody);
			}
			else if(connection.getResponseCode() == HttpURLConnection.HTTP_NOT_ACCEPTABLE){
				// incorrect password
				return new ValidateUser_Output(null,false);
			}
			else{
				// not valid input
				return null;
			}
		}
		catch(IOException e){
			System.out.println("Can't connect to the server");
			e.printStackTrace();
			return null;
		}
		return response;
	}

	public String getURLPrefix() {
		return URLPrefix;
	}

	public void setURLPrefix(String uRLPrefix) {
		URLPrefix = uRLPrefix;
	}
	
	
}
