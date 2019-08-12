package com.iflytek.view;
import java.io.File;
import java.io.IOException;

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

    public static void produceAction(String str) throws IOException {
        String base = "\"C:\\Users\\张丽雅\\AppData\\Local\\UiPath\\app-19.7.0\\UiRobot.exe\"";
        String executeLocation = "";
        if(str.contains("早上")) {
            executeLocation = "/file:\"C:\\Users\\张丽雅\\Documents\\UiPath\\BlankProcess\\Flowchart-1.xaml\"";
        }
        else if (str.contains("中午")) {
            executeLocation = "/file:\"C:\\Users\\张丽雅\\Documents\\UiPath\\BlankProcess\\Flowchart-2.xaml\"";
        }
        // Execute Command
        Runtime.getRuntime().exec(new String[]{base, executeLocation});
    }
}