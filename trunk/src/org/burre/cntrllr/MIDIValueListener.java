package org.burre.cntrllr;

import org.burre.cntrllr.net.MIDISocket;

public class MIDIValueListener implements IValueListener{
	//int m_midiCommand;
	int m_midiChannel;
	//int m_control;
	MIDISocket m_socket;

	public MIDIValueListener(MIDISocket socket, int midiChannel){
		m_socket = socket;
		//m_midiCommand = midiCommand;
		m_midiChannel = midiChannel;
		//m_control = control;
	}

	@Override
	public void updateValue(int midiCommand, int data1, int data2){
		byte[] data = {(byte)((0xf0 & midiCommand) + (0xf & m_midiChannel)),
			(byte)data1,
			(byte)data2};
		m_socket.writeMIDIMessage(data);
	}
}
