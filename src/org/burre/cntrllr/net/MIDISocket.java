package org.burre.cntrllr.net;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import org.apache.http.conn.util.InetAddressUtils;
import org.burre.cntrllr.Toaster;

import android.util.Log;
import android.widget.Toast;

public class MIDISocket{
	Socket m_socket;
	Thread m_socketThread;
	boolean m_socketReady = false;
	OutputStream m_outStream;
	String m_address;
	int m_port;

	public void connectToSocket(final String address, final int port){
		if(InetAddressUtils.isIPv4Address(address) == false){
			Toast.makeText(null,
				"Not a valid address: " + address + ":" + port,
				Toast.LENGTH_LONG);
			return;
		}

		if(m_socketReady){
			if(m_address == address && m_port == port){
				// We are already connected to this address/port, ignore request
				return;
			}else{
				// A request to change the connection has occurred, close and reconnect
				try{
					m_socket.close();
				}catch(IOException e){
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		// Reset the socket state
		m_socketReady = false;

		if(m_socketThread != null){
			try{
				if(m_socketThread.isAlive()){
					// Try to kill the thread gracefully first
					m_socketThread.join(1000);
					m_socketThread.interrupt();
				}
			}catch(InterruptedException e){
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		m_socketThread = new Thread(new Runnable(){
			@Override
			public void run(){
				try{
					Toaster.getInstance().showToast("Connecting to: " + address
						+ ":"
						+ port,
						Toast.LENGTH_LONG);
					m_socket = new Socket(); // address, port
					m_socket.connect(new InetSocketAddress(address, port), 10000);
					m_outStream = m_socket.getOutputStream();

					// Set this to the current address/port
					m_address = address;
					m_port = port;

					m_socketReady = true;

					Toaster.getInstance().showToast("Connected!",
						Toast.LENGTH_LONG);
				}catch(UnknownHostException e){
					Log.e("SOCKET", "Unknown host: " + e.getMessage());
					Toaster.getInstance().showToast("Unknown host: " + e.getMessage(),
						Toast.LENGTH_LONG);
					e.printStackTrace();
				}catch(Exception e){
					Toaster.getInstance().showToast("Error: " + e.getMessage(),
						Toast.LENGTH_LONG);
					e.printStackTrace();
				}
			}

		}, "MIDIDataSocket");
		m_socketThread.start();
	}

	public void disconnectSocket(){
		try{
			m_socket.close();
		}catch(IOException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Reset the socket state
		m_socketReady = false;

		// Kill the socket thread if alive
		if(m_socketThread != null){
			try{
				if(m_socketThread.isAlive()){
					// Try to kill the thread gracefully first
					m_socketThread.join(1000);
					m_socketThread.interrupt();
				}
			}catch(InterruptedException e){
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void writeMIDIMessage(byte[] message){
		if(message.length != 3){
			// TODO: for now we only support 3 byte MIDI-messages
			return;
		}

		if(m_socketReady){
			try{
				m_outStream.write(message);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
}
