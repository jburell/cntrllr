package org.burre.cntrllr;

import java.util.Vector;

import org.apache.http.client.CircularRedirectException;
import org.burre.cntrllr.net.MIDISocket;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Point;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.RectShape;
import android.telephony.ServiceState;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class CntrllrView extends View{
	private Vector<BaseController> m_controllers = new Vector<BaseController>();
	int m_width = 50;
	int m_height = 50;
	int m_numSliders = 5;
	Vector<MIDIValueListener> m_listeners = new Vector<MIDIValueListener>();
	MIDISocket m_midiSocket;

	ShapeDrawable rect1, rect2;
	int touch1X, touch1Y;
	int touch2X, touch2Y;
	int sideLen = 50;

	public CntrllrView(Context context){
		super(context);

		setKeepScreenOn(true);

		RectShape rect = new RectShape();
		rect.resize(sideLen, sideLen);
		rect1 = new ShapeDrawable(rect);
		rect1.getPaint().setColor(0xff0000ff);
		rect1.setBounds(touch1X, touch1Y, touch1X + sideLen, touch1Y + sideLen);
		rect2 = new ShapeDrawable(rect);
		rect2.getPaint().setColor(0xffff00ff);
		rect2.setBounds(touch2X, touch2Y, touch2X + sideLen, touch2Y + sideLen);

		int startX = 80;
		int offsY = 5;
		int sliderW = 70;
		int sliderH = 220;
		int sliderSpacer = 4;

		m_midiSocket = new MIDISocket();
		for(int i = 0; i < m_numSliders; i++){
			m_controllers.add(new SliderControl(i, startX + (i * sliderSpacer)
				+ (i * sliderW), offsY, sliderW, sliderH));
			MIDIValueListener listener = new MIDIValueListener(m_midiSocket, 0);

			if(m_listeners.contains(listener) == false){
				m_listeners.add(listener);
			}

			m_controllers.elementAt(i).registerListener(listener);
		}

		// Add button 1
		ButtonControl button = new ButtonControl(0, 0, 0, 50, 50);
		m_controllers.add(button);
		MIDIValueListener listener = new MIDIValueListener(m_midiSocket, 0);

		if(m_listeners.contains(listener) == false){
			m_listeners.add(listener);
		}
		button.registerListener(listener);

		// Add button 2
		button = new ButtonControl(2, 0, 60, 50, 50);
		m_controllers.add(button);
		listener = new MIDIValueListener(m_midiSocket, 0);

		if(m_listeners.contains(listener) == false){
			m_listeners.add(listener);
		}
		button.registerListener(listener);

		// Add button 3
		button = new ButtonControl(4, 0, 120, 50, 50);
		m_controllers.add(button);
		listener = new MIDIValueListener(m_midiSocket, 0);

		if(m_listeners.contains(listener) == false){
			m_listeners.add(listener);
		}
		button.registerListener(listener);

		// Add button 4
		button = new ButtonControl(6, 0, 180, 50, 50);
		m_controllers.add(button);
		listener = new MIDIValueListener(m_midiSocket, 0);

		if(m_listeners.contains(listener) == false){
			m_listeners.add(listener);
		}
		button.registerListener(listener);

		// Add button 5
		button = new ButtonControl(8, 0, 240, 50, 50);
		m_controllers.add(button);
		listener = new MIDIValueListener(m_midiSocket, 0);

		if(m_listeners.contains(listener) == false){
			m_listeners.add(listener);
		}
		button.registerListener(listener);
	}

	//	@Override
	//	public boolean onTouchEvent(MotionEvent event){
	//		viewTouched(event.getX(), event.getY());
	//		return super.onTouchEvent(event);
	//	}

	public void viewTouched(float xPos, float yPos){
		int x = (int)xPos;
		int y = (int)yPos;
		for(int i = 0; i < m_controllers.size(); i++){
			if(m_controllers.elementAt(i).collide(x, y)){
				Point p = m_controllers.elementAt(i).getClientPos(x, y);
				m_controllers.elementAt(i).touch(p.x, p.y);
			}
		}
	}
	
	public void viewMoved(float xPos, float yPos){
		int x = (int)xPos;
		int y = (int)yPos;
		for(int i = 0; i < m_controllers.size(); i++){
			if(m_controllers.elementAt(i).collide(x, y)){
				Point p = m_controllers.elementAt(i).getClientPos(x, y);
				m_controllers.elementAt(i).move(p.x, p.y);
			}
		}
	}

	public void viewReleased(float xPos, float yPos){
		int x = (int)xPos;
		int y = (int)yPos;
		for(int i = 0; i < m_controllers.size(); i++){
			if(m_controllers.elementAt(i).collide(x, y)){
				Point p = m_controllers.elementAt(i).getClientPos(x, y);
				m_controllers.elementAt(i).release(x, y);
			}
		}
	}

	public void connectToSocket(String address, int port){
		m_midiSocket.connectToSocket(address, port);
	}

	public void disconnectSocket(){
		m_midiSocket.disconnectSocket();
	}

	public void setMarkerPos(float x, float y, int idx){
		if(idx == 0){
			rect1.setBounds((int)x, (int)y, (int)x + sideLen, (int)y + sideLen);
		}else if(idx == 1){
			rect2.setBounds((int)x, (int)y, (int)x + sideLen, (int)y + sideLen);
		}
	}

	protected void onDraw(Canvas canvas){
		for(int i = 0; i < m_controllers.size(); i++){
			m_controllers.elementAt(i).draw(canvas);
		}
//		rect1.draw(canvas);
//		rect2.draw(canvas);
	}
}
