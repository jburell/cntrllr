package org.burre.cntrllr;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;

public class SliderControl extends BaseController{
	private static final float CONTROL_MAX_VALUE = 127;

	private static final int KNOB_HEIGHT = 20;

	int m_value;
	int m_controllerOffs = 0;

	ShapeDrawable m_knob;
	ShapeDrawable m_bgShape;

	public int getX(){
		return m_xPos;
	}

	public int getY(){
		return m_yPos;
	}

	public int getWidth(){
		return m_width;
	}

	public int getHeight(){
		return m_height;
	}

	public SliderControl(int controllerOffs, int x, int y, int width, int height){
		super(x, y, width, height);
		m_controllerOffs = controllerOffs;
		m_value = 0;

		RectShape rect = new RectShape();
		rect.resize(width, height);
		m_bgShape = new ShapeDrawable(rect);
		m_bgShape.getPaint().setColor(0xff74ACff);
		m_bgShape.setBounds(m_xPos, m_yPos, m_xPos + width, m_yPos + height);

		rect = new RectShape();
		rect.resize(width, height);
		m_knob = new ShapeDrawable(rect);
		m_knob.getPaint().setColor(0xffaa0000);
		m_knob.setBounds(m_xPos, m_yPos + m_value, m_xPos + width, m_yPos + m_value + 20);
	}

	@Override
	public void draw(Canvas canvas){
		m_bgShape.draw(canvas);
		m_knob.draw(canvas);
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
	
	@Override
	public void move(int x, int y){
		touch(x,y);
	}

	@Override
	public void touch(int x, int y){
		m_value = (int)(((m_height - y) / (float)m_height) * CONTROL_MAX_VALUE);
		
		// Send the new value to all listeners
		for(IValueListener listener: m_listeners){
			listener.updateValue(176, m_controllerOffs, m_value);
		}
		
		m_knob.setBounds(m_xPos,
			m_yPos + m_height - (int)((m_value / CONTROL_MAX_VALUE) * (m_height - KNOB_HEIGHT)) - KNOB_HEIGHT,
			m_xPos + m_width,
			m_yPos + m_height - (int)((m_value / CONTROL_MAX_VALUE) * (m_height - KNOB_HEIGHT)));
	}
}
