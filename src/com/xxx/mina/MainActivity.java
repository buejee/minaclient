package com.xxx.mina;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.xxx.mina.common.ConnectionManager;
import com.xxx.mina.common.MinaService;
import com.xxx.mina.common.SessionManager;

public class MainActivity extends Activity implements OnClickListener{
	
	protected TextView dateView;
	protected Button connectBtn;
	protected Button sendBtn;
	
	MessageBroadcastReceiver receiver = new MessageBroadcastReceiver();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
		registerBroadcast();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		stopService(new Intent(this,MinaService.class));
		unregisterBroadcast();
	}
	
	//receive message and update ui
	private class MessageBroadcastReceiver extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			dateView.setText(intent.getStringExtra(ConnectionManager.MESSAGE));
		}
		
	}
	
	public void registerBroadcast(){
		IntentFilter filter = new IntentFilter(ConnectionManager.BROADCAST_ACTION);
		LocalBroadcastManager.getInstance(this).registerReceiver(receiver, filter);
	}
	
	public void unregisterBroadcast(){
		LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
	}
	
	public void initView(){
		connectBtn = (Button)findViewById(R.id.connectBtn);
		sendBtn = (Button)findViewById(R.id.sendBtn);
		dateView = (TextView)findViewById(R.id.datetxt);
		connectBtn.setOnClickListener(this);
		sendBtn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.connectBtn:
				Intent intent = new Intent(this,MinaService.class);
				startService(intent);
				Log.e("tag", "connect to server");	
				break;
			case R.id.sendBtn:
				SessionManager.getInstance().writeToServer("123");
				Log.e("tag", "send message to server");
				break;
		}
	}
}
