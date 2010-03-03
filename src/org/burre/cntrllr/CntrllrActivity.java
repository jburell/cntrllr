package org.burre.cntrllr;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CntrllrActivity extends Activity{
	CntrllrView m_myView;
	View m_connView;

	protected final int MENU_CONNECT = 0;
	protected final int MENU_DISCONNECT = 1;

	public static CntrllrActivity m_instance;

	public static CntrllrActivity getInstance(){
		return m_instance;
	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
			WindowManager.LayoutParams.FLAG_FULLSCREEN);
		m_myView = new CntrllrView(this);
		setContentView(R.layout.connection_view);
		
		m_connView = findViewById(R.layout.connection_view);
		Button connButton = (Button)findViewById(R.id.ConnectButton);
		connButton.setOnClickListener(new View.OnClickListener(){
			public void onClick(View v){
				EditText editText = (EditText)findViewById(R.id.AddressTextInput);
				String text = editText.getText().toString();
				String[] parts = text.split(":");
				if(parts.length == 0){
					Toast.makeText(getBaseContext(),
						"Error: No port supplied",
						Toast.LENGTH_LONG);
				}
				if(parts.length == 2){
					m_myView.connectToSocket(parts[0], Integer.parseInt(parts[1]));
				}
				setContentView(m_myView);
			}
		});

		setContentView(m_myView);

		Toaster.getInstance().setContext(getApplication().getApplicationContext());

		m_instance = this;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		menu.add(0, MENU_CONNECT, 0, "Connect...");
		menu.add(0, MENU_DISCONNECT, 0, "Disconnect");
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		switch(item.getItemId()){
		case MENU_CONNECT:
			setContentView(m_connView);
			return true;
		case MENU_DISCONNECT:
			m_myView.disconnectSocket();
			Toaster.getInstance().showToast("Disconnected", Toast.LENGTH_LONG);
			return true;
		}
		return false;
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev){
		float x, y;
//		if(ev.getPointerCount() > 1){
//			Log.d("touch", "mulitouch");
//			x = ev.getX(0);
//			y = ev.getY(0);
//			m_myView.setMarkerPos(x, y, 0);
//			x = ev.getX(1);
//			y = ev.getY(1);
//			m_myView.setMarkerPos(x, y, 1);
//		}
		for(int i = 0; i < ev.getPointerCount(); i++){
			x = ev.getX(i);
			y = ev.getY(i);
			if(ev.getAction() == MotionEvent.ACTION_UP){
				m_myView.viewReleased(x, y);
			}else if(ev.getAction() == MotionEvent.ACTION_DOWN){
				m_myView.viewTouched(x, y);
			}else{
				m_myView.viewMoved(x, y);
			}
		}
		m_myView.invalidate();
		return true; //super.dispatchTouchEvent(ev);
	}

}