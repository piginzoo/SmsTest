package com.example.smstest;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BootBroadcast extends BroadcastReceiver { 
   
    @Override 
    public void onReceive(Context context, Intent mintent) { 
    	Log.i("","我收到开机自动启动的消息了！");
        if (Intent.ACTION_BOOT_COMPLETED.equals(mintent.getAction())) { 
        	Log.i("Action","Intent.ACTION_BOOT_COMPLETED");
    		Intent serviceIntent = new Intent(context, SmsService.class);
    		ComponentName name = context.startService(serviceIntent);
    		Log.i("","已经启动发送短信服务");
    		if(name==null){
    			Log.e("ERROR","短信发送服务启动失败");
    		}	
        } 
    } 

} 
