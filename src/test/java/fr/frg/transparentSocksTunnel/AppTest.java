package fr.frg.transparentSocksTunnel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;

import org.junit.Test;

import junit.framework.Assert;

/**
 * Unit test for simple App.
 */
public class AppTest {

	@Test
	public void test1() throws IOException {
		int port = 10000;
		ServerSocket serverSocket = new ServerSocket(port);

		while (true) {
			Socket socket = serverSocket.accept();

			Writer out = new PrintWriter(socket.getOutputStream(), true);
			Reader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			SocketThread socketThread = new SocketThread(in, out);
			socketThread.start();
		}

	}

}
