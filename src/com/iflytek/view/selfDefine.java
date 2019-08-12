package com.iflytek.view;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import com.iflytek.util.JsonParser;
import com.iflytek.util.turningCommunication;

public class selfDefine {

	// public static void produceAction(String str) throws IOException {
//        String fileAddress = "";
//  if(str.contains("早上")) {
////   fileAddress = "C:\\Users\\张丽雅\\Documents\\UiPath\\BlankProcess\\Flowchart-1.xaml";
//            fileAddress = "C:\\Users\\张丽雅\\Desktop\\1.txt";
//  }
//  else if (str.contains("中午")) {
//   fileAddress = "C:\\Users\\张丽雅\\Documents\\UiPath\\BlankProcess\\Flowchart-2.xaml";
//  }
//  if(fileAddress.length() > 0) {
//            File file = new File(fileAddress);
//            java.awt.Desktop.getDesktop().open(file);
//        }
// }

	public static String produceAction(String str) throws IOException {
		String ret = "";
		String base = "\"C:\\Users\\张丽雅\\AppData\\Local\\UiPath\\app-19.7.0\\UiRobot.exe\"";
		String executeLocation = "";
		if(str.contains("早上")) {
			executeLocation = "/file:\"C:\\Users\\张丽雅\\Documents\\UiPath\\BlankProcess\\Flowchart-1.xaml\"";
			ret = "RPA开始运行...";
			Runtime.getRuntime().exec(new String[]{base, executeLocation});
		}
		else if (str.contains("中午")) {
			executeLocation = "/file:\"C:\\Users\\张丽雅\\Documents\\UiPath\\BlankProcess\\Flowchart-2.xaml\"";
			ret = "RPA开始运行...";
			Runtime.getRuntime().exec(new String[]{base, executeLocation});
		}
		else {
			String url = "http://openapi.tuling123.com/openapi/api/v2";
			try {
				String data = turningCommunication.getJsonData(str,url);
				ret = JsonParser.parseTurningResult(data);
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		return ret;
	}
}