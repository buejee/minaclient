package com.xxx.mina.common;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.HandlerThread;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

public class MinaService extends Service {

	private ConnectionThread thread;
	@Override
	public void onCreate() {
		super.onCreate();
		thread = new ConnectionThread("mina", getApplicationContext());
		thread.start();
		Log.e("tag", "start thread to connect");
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		thread.disConnect();
		thread = null;
	}
	@Nullable
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	class ConnectionThread extends HandlerThread {
		boolean isConnected;
		ConnectionManager manager;

		public ConnectionThread(String name, Context context) {
			super(name);
			ConnectionConfig config = new ConnectionConfig.Builder(context)
					.setIp("123.207.70.98").setPort(9123)
					.setReadBufferSize(10240).setConnectionTimeout(10000)
					.builder();
			manager = new ConnectionManager(config);
		}

		@Override
		protected void onLooperPrepared() {
			while(true) {
				isConnected = manager.connect();
				if (isConnected){
					Log.e("tag", "connect successfully.");
					break;
				}
				try {
					Log.e("tag", "connect fail.");
					Thread.sleep(3000);
				} catch (Exception e) {
					Log.e("tag", "fail with error");
				}
			}
		}
		
		public void disConnect(){
			manager.disConnect();
		}
	}
}
