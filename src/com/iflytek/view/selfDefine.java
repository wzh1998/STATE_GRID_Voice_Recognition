package com.iflytek.view;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import com.iflytek.util.JsonParser;
import com.iflytek.util.pcmPlay;
import com.iflytek.util.ttsTool;
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
    // 根据语音转文字的结果判断该执行什么任务，当特定关键词输入时执行RPA，其他情况图灵机器人。
    public static String produceAction(String str) throws IOException {
        String ret = "";
        String base = "\"C:\\Users\\张丽雅\\AppData\\Local\\UiPath\\app-19.7.0\\UiRobot.exe\"";
        String executeLocation = "";
        //语音中包含早上吊起本地的RPA相对应的项目
        if(str.contains("发票")) {
            executeLocation = "/file:\"D:\\software\\uipath\\UiPath\\UiPath\\BlankProcess\\Flowchart-1.xaml\"";
            ret = "机器人开始运行...";
            Runtime.getRuntime().exec(new String[]{base, executeLocation});
            ttsTool.startSyn(ret);
        }
        //语音中包含中午吊起本地的RPA相对应的项目
        else if (str.contains("审计")) {
            executeLocation = "/file:\"D:\\software\\uipath\\UiPath\\UiPath\\BlankProcess\\Flowchart-2.xaml\"";
            ret = "机器人开始运行...";
            Runtime.getRuntime().exec(new String[]{base, executeLocation});
            ttsTool.startSyn(ret);
        }
        //打开
        else if(str != null && str.length() > 1){
            String url = "http://openapi.tuling123.com/openapi/api/v2";
            try {
                String data = turningCommunication.getJsonData(str,url);
                ret = JsonParser.parseTurningResult(data);
                ttsTool.startSyn(ret);
            }catch (Exception e) {
                e.printStackTrace();
            }
        }

        return ret;
    }
}