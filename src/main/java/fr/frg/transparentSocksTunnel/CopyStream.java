package fr.frg.transparentSocksTunnel;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

public class CopyStream extends Thread {
	private Reader reader;
	private Writer writer;
	private String name;

	public CopyStream(String name, Reader reader, Writer writer) {
		this.reader = reader;
		this.writer = writer;
		this.name = name;
	}

	public void run() {
		try {
			int i = reader.read();
			while (i != -1) {
				writer.write(i);
				writer.flush();
				char[] c = Character.toChars(i);
				System.out.print(c);
				i = reader.read();
			}
		} catch (IOException e) {
		}
		System.out.println("END " + name);
	}
}
