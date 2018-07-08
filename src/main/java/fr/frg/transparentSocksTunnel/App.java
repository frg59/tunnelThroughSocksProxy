package fr.frg.transparentSocksTunnel;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;

/**
 * Hello world!
 *
 */
public class App {
	public static int localPort = 0;
	public static String targetAddress = "";
	public static int targetPort = 0;
	public static String proxyAddress = "";
	public static int proxyPort = 0;

	public static void main(String[] args) throws IOException {
		
		initProps();
		System.out.println("Hello World!");
		ServerSocket serverSocket = new ServerSocket(localPort);

		while (true) {
			Socket socket = serverSocket.accept();

			Writer out = new PrintWriter(socket.getOutputStream(), true);
			Reader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			SocketThread socketThread = new SocketThread(in, out);
			socketThread.start();
		}
	}

	public static void initProps() {

		Properties prop = new Properties();
		InputStream input = null;

		try {

//			input = new FileInputStream("classpath:application.properties");
			prop.load(App.class.getClassLoader().getResourceAsStream("application.properties"));

			// load a properties file
//			prop.load(input);

			// get the property value and print it out
			localPort = Integer.parseInt(prop.getProperty("local.port"));
			targetAddress = prop.getProperty("target.address");
			targetPort = Integer.parseInt(prop.getProperty("target.port"));
			proxyAddress = prop.getProperty("proxy.address");
			proxyPort = Integer.parseInt(prop.getProperty("proxy.port"));

		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}
}
