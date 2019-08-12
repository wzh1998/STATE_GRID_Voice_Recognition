package com.iflytek.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class turningCommunication {
	public static String buildJSON(String coreMsg) {
		String reqType = "\"reqType\":0,";
		String perception = "\"perception\": ";
		String inputText = "\"inputText\": ";
		String text = "\"text\": \"" + coreMsg + "\"";
		String inputImage = "\"inputImage\": ";
		String url = "\"url\": \"imageUrl\"";
		String selfInfo = "\"selfInfo\": ";
		String location = "\"location\": ";
		String city = "\"city\": \"北京\",";
		String province = "\"province\": \"北京\",";
		String street = "\"street\": \"信息路\"";
		String userInfo = "\"userInfo\": ";
		String apiKey = "\"apiKey\": \"347b39ee228b4b109dae7270cc08d3c8\",";
		String userID = "\"userId\": \"0001\"";
		String finMsg = "{" + 
						reqType + 
						perception + "{" +
							inputText + "{" + 
							text + 
							"}," + 
							inputImage + "{" + 
								url + 
							"}," + 
							selfInfo + "{" +
								location + "{" + 
								city + 
								province + 
								street + 
								"}" + 
							"}" + 
						"}," +
						userInfo + "{" +
							apiKey + 
							userID + 
						"}" + 
						"}";
//		Debug 
//		System.out.print(finMsg);
		return finMsg;
	}
	
	public static String getJsonData(String msgToSend,String urls) {
		StringBuffer sb=new StringBuffer();
		try {
			;
			URL url = new URL(urls);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Connection", "Keep-Alive");
			conn.setRequestProperty("Charset", "UTF-8");
			byte[] data = buildJSON(msgToSend).getBytes();
			conn.setRequestProperty("Content-Length", String.valueOf(data.length));
			conn.setRequestProperty("contentType", "application/json");
			conn.connect();		
			OutputStream out = new DataOutputStream(conn.getOutputStream()) ;
			out.write(buildJSON(msgToSend).getBytes());
			out.flush();
			out.close();
 
			System.out.print(conn.getResponseCode());
			
			// Request for returning status
			if (HttpURLConnection.HTTP_OK == conn.getResponseCode()){
				System.out.println("...Connection Established!");
				// Request for returning data
				InputStream in1 = conn.getInputStream();
				try {
					String readLine = new String();
					BufferedReader responseReader = new BufferedReader(new InputStreamReader(in1,"UTF-8"));
					while((readLine = responseReader.readLine()) != null){
					sb.append(readLine).append("\n");
					}
					responseReader.close();
//					System.out.println(sb.toString());
					
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			} else {
				System.out.println("error++");	
			}
		} catch (Exception e) {}
		
		return sb.toString();
	}
	public static void main(String[] args) throws JSONException {
		String txtToSend = "";
		String url = "http://openapi.tuling123.com/openapi/api/v2";
		Scanner sc = new Scanner(System.in); 

		while(true) {
			System.out.print("\nInput: ");
			txtToSend = sc.nextLine();
			String data = getJsonData(txtToSend,url);
			System.out.print(JsonParser.parseTurningResult(data));
		}
	}
}
