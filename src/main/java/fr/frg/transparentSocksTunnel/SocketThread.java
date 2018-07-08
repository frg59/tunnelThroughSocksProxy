package fr.frg.transparentSocksTunnel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Socket;
import java.net.SocketAddress;

public class SocketThread extends Thread {
	private Reader localReader = null;
	private Writer localWriter = null;

	public SocketThread(Reader reader, Writer writer) {
		this.localReader = reader;
		this.localWriter = writer;
	}

	public void run() {
		try {
			System.out.println("incoming connection");

			// connection to remote socket
			SocketAddress proxyAddress = new InetSocketAddress(App.proxyAddress, App.proxyPort);
			Proxy proxy = new Proxy(Proxy.Type.SOCKS, proxyAddress);
			Socket remoteSocket = new Socket(proxy);
			
			SocketAddress socketAddress = new InetSocketAddress(App.targetAddress, App.targetPort);
			remoteSocket.connect(socketAddress);
			Reader remoteIn = new BufferedReader(new InputStreamReader(remoteSocket.getInputStream()));
			PrintWriter remoteOut = new PrintWriter(remoteSocket.getOutputStream(), true);
			System.out.println("connected to remote");

			CopyStream copyStreamLocalToRemote = new CopyStream("copyStreamLocalToRemote", localReader, remoteOut);
			copyStreamLocalToRemote.start();

			CopyStream copyStreamRemoteToLocal = new CopyStream("copyStreamRemoteToLocal", remoteIn, localWriter);
			copyStreamRemoteToLocal.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
