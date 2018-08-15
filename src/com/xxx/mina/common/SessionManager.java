package com.xxx.mina.common;

import org.apache.mina.core.session.IoSession;

import android.util.Log;

public class SessionManager {
	private static SessionManager instance = null;
	
	private IoSession session;
	public static SessionManager getInstance(){
		if(instance==null){
			synchronized (SessionManager.class) {
				if(instance==null){
					instance = new SessionManager();
				}
			}
		}
		return instance;
	}
	private SessionManager(){}
	public void setSession(IoSession session){this.session = session;}
	public void writeToServer(Object msg){
		if(session != null){
			Log.e("tag", "mina client ready to send message:"+msg);
			session.write(msg);
		}
	}
	
	public void closeSession(){
		if(session != null){
			session.closeOnFlush();
		}
	}
	
	public void removeSession(){
		this.session  = null;
	}
}
