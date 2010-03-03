package org.burre.cntrllr;

import java.util.Vector;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Point;
import android.graphics.drawable.Drawable;

public class BaseController extends Drawable{
	int m_xPos, m_yPos;
	int m_height, m_width;
	
	Vector<IValueListener> m_listeners = new Vector<IValueListener>();
	
	@SuppressWarnings("unused")
	private BaseController(){
	}
	
	protected BaseController(int x, int y, int w, int h){
		m_xPos = x;
		m_yPos = y;
		m_width = w;
		m_height = h;
	}
	
	public void registerListener(IValueListener listener){
		if(m_listeners.contains(listener) == false){
			m_listeners.add(listener);
		}
	}
	
	public void unregisterListener(IValueListener listener){
		m_listeners.remove(listener);
	}
	
	public Point getClientPos(int globalX, int globalY){
		return new Point(globalX - m_xPos, globalY - m_yPos);
	}
	
	public boolean collide(int x, int y){
		if(x > m_xPos && x < (m_xPos + m_width)){
			if(y > m_yPos && y < (m_yPos + m_height)){
				return true;
			}
		}
		return false;
	}
	
	public void touch(int x, int y){
		// TODO Auto-generated method stub
		
	}
	
	public void move(int x, int y){
		// TODO Auto-generated method stub
		
	}
	
	public void release(int x, int y){
		
	}

	@Override
	public void draw(Canvas canvas){
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getOpacity(){
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setAlpha(int alpha){
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setColorFilter(ColorFilter cf){
		// TODO Auto-generated method stub
		
	}
}
