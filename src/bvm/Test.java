package bvm;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import bvm.screen.Screen;

public class Test {
	public static void main(String[] args) throws IOException {
		System.out.println("Loading disk into RAM");
		InputStream in = new BufferedInputStream(new FileInputStream("test.iso"));
		long l = 0;
		while(in.available() > 0) {
			Memory.set(l, (byte) in.read());
			l += 1;
		}
		in.close();
		
		System.out.println("Starting screen");
		Screen.startScreen();
		
		System.out.println("Starting Core");
		Core c = new Core(0);
		c.start();
		c.enable(0);
	}
}
