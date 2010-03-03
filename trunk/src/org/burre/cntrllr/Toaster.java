package org.burre.cntrllr;

import android.content.Context;
import android.widget.Toast;

public class Toaster{
	private Context m_context = null;
	private Toast m_toast; 
	private static Toaster m_instance = null;
	
	public static Toaster getInstance(){
		if(m_instance == null){
			m_instance = new Toaster();
		}
		return m_instance;
	}
	
	private Toaster(){
	}
	
	public void setContext(Context context){
		m_context = context;
		m_toast = Toast.makeText(m_context, "", Toast.LENGTH_LONG);
	}
	
	public void showToast(String message, int type){
		m_toast.setText(message);
		m_toast.setDuration(type);
		m_toast.show();
	}
}
