package com.iflytek.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import com.iflytek.cloud.speech.LexiconListener;
import com.iflytek.cloud.speech.RecognizerListener;
import com.iflytek.cloud.speech.RecognizerResult;
import com.iflytek.cloud.speech.Setting;
import com.iflytek.cloud.speech.SpeechConstant;
import com.iflytek.cloud.speech.SpeechError;
import com.iflytek.cloud.speech.SpeechEvent;
import com.iflytek.cloud.speech.SpeechRecognizer;
import com.iflytek.cloud.speech.SpeechSynthesizer;
import com.iflytek.cloud.speech.SpeechUtility;
import com.iflytek.cloud.speech.SynthesizeToUriListener;
import com.iflytek.cloud.speech.UserWords;

public class ttsTool {

    private static final String APPID = "5b4efb32";

    private static final String USER_WORDS = "{\"userword\":[{\"name\":\"计算机词汇\",\"words\":[\"随机存储器\",\"只读存储器\",\"扩充数据输出\",\"局部总线\",\"压缩光盘\",\"十七寸显示器\"]},{\"name\":\"我的词汇\",\"words\":[\"槐花树老街\",\"王小贰\",\"发炎\",\"公事\"]}]}";

    private static ttsTool mObject;

    private static StringBuffer mResult = new StringBuffer();

    private boolean mIsLoop = true;

    // 将文字合成pcm音频文件
    public static void startSyn(String synMsg) {

        SpeechUtility.createUtility("appid=" + APPID);
        getMscObj().loop(synMsg);
    }

    private static ttsTool getMscObj() {
        if (mObject == null)
            mObject = new ttsTool();
        return mObject;
    }

    private boolean onLoop(String synMsg) {
        boolean isWait = true;
        try {
            Synthesize(synMsg);
        } catch (Exception e) {}

        return isWait;
    }

    // *************************************无声合成*************************************

    /**
     * 合成
     */
    private void Synthesize(String synMsg) {
        SpeechSynthesizer speechSynthesizer = SpeechSynthesizer
                .createSynthesizer();
        // 设置发音人
        speechSynthesizer.setParameter(SpeechConstant.VOICE_NAME, "xiaoyan");

        //启用合成音频流事件，不需要时，不用设置此参数
        speechSynthesizer.setParameter( SpeechConstant.TTS_BUFFER_EVENT, "1" );
        // 设置合成音频保存位置（可自定义保存位置），默认不保存
        speechSynthesizer.synthesizeToUri(synMsg , "D:\\software\\IDEA\\zhang-1\\tts_result.pcm",
                synthesizeToUriListener);
    }

    /**
     * 合成监听器
     */
    SynthesizeToUriListener synthesizeToUriListener = new SynthesizeToUriListener() {

        public void onBufferProgress(int progress) {
            DebugLog.Log("*************合成进度*************" + progress);

        }

        public void onSynthesizeCompleted(String uri, SpeechError error) {
            if (error == null) {
                DebugLog.Log("*************合成成功*************");
                DebugLog.Log("合成音频生成路径：" + uri);
            } else
                DebugLog.Log("*************" + error.getErrorCode()
                        + "*************");
            waitupLoop();

        }

        @Override
        public void onEvent(int eventType, int arg1, int arg2, int arg3, Object obj1, Object obj2) {
            if( SpeechEvent.EVENT_TTS_BUFFER == eventType ){
                DebugLog.Log( "onEvent: type="+eventType
                        +", arg1="+arg1
                        +", arg2="+arg2
                        +", arg3="+arg3
                        +", obj2="+(String)obj2 );
                ArrayList<?> bufs = null;
                if( obj1 instanceof ArrayList<?> ){
                    bufs = (ArrayList<?>) obj1;
                }else{
                    DebugLog.Log( "onEvent error obj1 is not ArrayList !" );
                }//end of if-else instance of ArrayList

                if( null != bufs ){
                    for( final Object obj : bufs ){
                        if( obj instanceof byte[] ){
                            final byte[] buf = (byte[]) obj;
                            DebugLog.Log( "onEvent buf length: "+buf.length );
                        }else{
                            DebugLog.Log( "onEvent error element is not byte[] !" );
                        }
                    }//end of for
                }//end of if bufs not null
            }//end of if tts buffer event
        }

    };

    private void waitupLoop(){
        synchronized(this){
            ttsTool.this.notify();
        }
    }

    public void loop(String synMsg) {

        try {
            if (onLoop(synMsg)) {
                synchronized(this){
                    this.wait();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

