package com.iflytek.util;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class turningCommunication {
    // 通过字符串拼接图灵API接收的Json格式
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
    // 将Json格式的请求通过post上传到API接口，并接收返回的Json格式。
    public static String getJsonData(String msgToSend,String urls) {
        StringBuffer sb=new StringBuffer();
        try {

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
        } catch (Exception e) {
            System.out.print("...Connection Failed! Check your network connection!");

        }
        String sub = null;
        try {
            sub = new String(sb.toString().getBytes("UTF-8"),"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return sub;

    }
    // 测试用
    public static void main(String[] args) {
        String txtToSend = "";
        String url = "http://openapi.tuling123.com/openapi/api/v2";
        Scanner sc = new Scanner(System.in);
        try {
            while(true) {
                System.out.print("\nInput: ");
                txtToSend = sc.nextLine();
                String data = getJsonData(txtToSend,url);
                System.out.print(JsonParser.parseTurningResult(data));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
