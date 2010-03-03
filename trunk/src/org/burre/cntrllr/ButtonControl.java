package org.burre.cntrllr;

import android.graphics.Canvas;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;

public class ButtonControl extends BaseController{
	boolean buttonStatus = false;
	int m_noteOffs = 0;
	ShapeDrawable m_greenButton;
	ShapeDrawable m_redButton;
	
	protected ButtonControl(int noteOffs, int x, int y, int w, int h){
		super(x, y, w, h);
		m_noteOffs = noteOffs;
		
		RectShape rect = new RectShape();
		rect.resize(w, h);
		
		m_greenButton = new ShapeDrawable(rect);
		m_greenButton.getPaint().setColor(0xff00ff00);
		m_greenButton.setBounds(m_xPos, m_yPos, m_xPos + w, m_yPos + h);
		
		m_redButton = new ShapeDrawable(rect);
		m_redButton.getPaint().setColor(0xffff0000);
		m_redButton.setBounds(m_xPos, m_yPos, m_xPos + w, m_yPos + h);
	}
	
	@Override
	public void draw(Canvas canvas){
		if(buttonStatus){
			m_greenButton.draw(canvas);
		}else{
			m_redButton.draw(canvas);
		}
	}

	@Override
	public void touch(int x, int y){
		// Toggle button
//		if(buttonStatus){
//			//buttonStatus = false;
//			
//			// Send the new value to all listeners
//			for(IValueListener listener: m_listeners){
//				listener.updateValue(0x90, m_noteOffs + 1, 120);
//				//listener.updateValue(0x80, m_noteOffs + 1, 120);
//			}
//		}else{
//			//buttonStatus = true;
			
			// Send the new value to all listeners
			for(IValueListener listener: m_listeners){
				listener.updateValue(0x90, m_noteOffs, 120);
				//listener.updateValue(0x80, m_noteOffs, 120);
			}
//		}
	}
	
	@Override
	public void release(int x, int y){
	// Toggle button
//		if(buttonStatus){
//			buttonStatus = false;
//			
//			// Send the new value to all listeners
//			for(IValueListener listener: m_listeners){
//				listener.updateValue(0x80, m_noteOffs + 1, 120);
//			}
//		}else{
//			buttonStatus = true;
			
			// Send the new value to all listeners
			for(IValueListener listener: m_listeners){
				listener.updateValue(0x80, m_noteOffs, 120);
			}
//		}
	}
}
