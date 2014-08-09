package com.example.smstest;

import java.util.ArrayList;

import android.app.PendingIntent;
import android.app.Service;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.IBinder;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

public class SmsService extends Service {

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}	
	
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		
		Log.i("INFO", "短信发送服务真的启动了！");
		Runnable run = new Runnable() {
			@Override
			public void run() {
				while (true) {
					
					try {
						sendSMS("13381258105","test！");
						Log.i("","短信已经发送");
						Thread.sleep(30000);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		};

		new Thread(run).start();
		return Service.START_STICKY;		
	}

	
	private void sendSMS(String phoneNumber, String message) {
		ContextWrapper mContext = this;
	    ArrayList<PendingIntent> sentPendingIntents = new ArrayList<PendingIntent>();
	    ArrayList<PendingIntent> deliveredPendingIntents = new ArrayList<PendingIntent>();
	    PendingIntent sentPI = PendingIntent.getBroadcast(mContext, 0,
	            new Intent(mContext, SmsSentReceiver.class), 0);
	    PendingIntent deliveredPI = PendingIntent.getBroadcast(mContext, 0,
	            new Intent(mContext, SmsDeliveredReceiver.class), 0);
	    try {
	        SmsManager sms = SmsManager.getDefault();
	        ArrayList<String> mSMSMessage = sms.divideMessage(message);
	        for (int i = 0; i < mSMSMessage.size(); i++) {
	            sentPendingIntents.add(i, sentPI);
	            deliveredPendingIntents.add(i, deliveredPI);
	        }
	        sms.sendMultipartTextMessage(phoneNumber, null, mSMSMessage,
	                sentPendingIntents, deliveredPendingIntents);

	    } catch (Exception e) {

	        e.printStackTrace();
	        Toast.makeText(getBaseContext(), "SMS sending failed...",Toast.LENGTH_SHORT).show();
	    }

	}	
}



