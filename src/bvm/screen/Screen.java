package bvm.screen;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.awt.image.IndexColorModel;

import javax.swing.JFrame;
import javax.swing.JPanel;

import bvm.Memory;

public class Screen extends Thread {
	public static Screen screen;
	private BufferedImage img;
	private ScreenMode mode = ScreenMode.CGA_TEXT;
	@Override
	public void run() {
		img = new BufferedImage(730, 350, BufferedImage.TYPE_BYTE_BINARY,
				new IndexColorModel(4, 16,
						new byte[] { (byte) 196, (byte) 255, (byte) 128, (byte) 255, (byte) 128, (byte) 0,
								(byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 255, (byte) 128,
								(byte) 255, (byte) 128, (byte) 0, },
						new byte[] { (byte) 196, (byte) 0, (byte) 0, (byte) 255, (byte) 128, (byte) 255,
								(byte) 128, (byte) 255, (byte) 128, (byte) 0, (byte) 0, (byte) 0, (byte) 0,
								(byte) 255, (byte) 128, (byte) 0, },
						new byte[] { (byte) 196, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0,
								(byte) 255, (byte) 128, (byte) 255, (byte) 128, (byte) 255, (byte) 128,
								(byte) 255, (byte) 128, (byte) 0, }));
		
		JFrame f = new JFrame("BVM");
		
		JPanel c = new JPanel(){
			private static final long serialVersionUID = 5457304033665694571L;
			@Override
			public void paintComponent(Graphics g) {
				g.drawImage(img, 0, 0, getWidth(), getHeight(), null);
			}
		};
		c.setPreferredSize(new Dimension(img.getWidth(), img.getHeight()));
		f.add(c);
		
		f.pack();
		f.addWindowListener(new WindowListener(){

			@Override
			public void windowOpened(WindowEvent e) {
			}

			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}

			@Override
			public void windowClosed(WindowEvent e) {
			}

			@Override
			public void windowIconified(WindowEvent e) {
			}

			@Override
			public void windowDeiconified(WindowEvent e) {
			}

			@Override
			public void windowActivated(WindowEvent e) {
			}

			@Override
			public void windowDeactivated(WindowEvent e) {
			}
		});
		f.setVisible(true);
		
		while(true) {
			switch(mode) {
			case CGA_TEXT:
				paintCGAText();
				break;
			}
			c.repaint();
			try {
				Thread.sleep(1000 / 5);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	private void paintCGAText() {
		Graphics2D g = img.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_SPEED);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
		g.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_SPEED);
		g.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_DISABLE);
		g.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_OFF);
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
		g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
		g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_NORMALIZE);
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
		
		g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 14));
		
		for(int y = 0; y < 25; y++) {
			for(int x = 0; x < 80; x++) {
				long pointer = 0xB8000 + (x + (y * 80)) * 2;
				int color = Memory.directGet(pointer);
				
				int bg = (color & 0xF0 >> 4);
				int fg = (color & 0x0F);
				boolean blink = (bg & 0b1000) == 0b1000;
				
				g.setColor(decodeCGA(bg));
				g.fillRect(x * 9, y * 14, 9, 14);
				
				if((!blink) || (System.currentTimeMillis() / 500 % 2 == 0)) {
					int c = Memory.directGet(pointer + 1);
					g.setColor(decodeCGA(fg));
					g.drawString((char) mapIBM(c) + "", x * 9, y * 14 + g.getFontMetrics().getHeight() / 2);
				}
			}
		}
	}
	
	private int mapIBM(int c) {
		if (c > 126) {
			char[] chars = {
					'\u2302',
					'\u00C7',
					'\u00FC',
					'\u00e9',
					'\u00e2',
					'\u00e4',
					'\u00e0',
					'\u00e5',
					'\u00e7',
					'\u00ea',
					'\u00eb',
					'\u00e8',
					'\u00ef',
					'\u00ee',
					'\u00ee',
					'\u00ec',
					'\u00c4',
					'\u00c5',
					'\u00c9',
					'\u00e6',
					'\u00c6',
					'\u00f4',
					'\u00f6',
					'\u00f2',
					'\u00fb',
					'\u00f9',
					'\u00ff',
					'\u00d6',
					'\u00dc',
					'\u00a2',
					'\u00a3',
					'\u00a5',
					'\u00a7',
					'\u0192',
					'\u00e1',
					'\u00ed',
					'\u00f3',
					'\u00fa',
					'\u00f1',
					'\u00d1',
					'\u00aa',
					'\u00ba',
					'\u00bf',
					'\u2310',
					'\u00ac',
					'\u00bd',
					'\u00bc',
					'\u00a1',
					'\u00ab',
					'\u00bb',
					'\u2591',
					'\u2592',
					'\u2593',
					'\u2502',
					'\u2524',
					'\u2561',
					'\u2562',
					'\u2556',
					'\u2555',
					'\u2563',
					'\u2551',
					'\u2557',
					'\u255d',
					'\u255c',
					'\u255b',
					'\u2510',
					'\u2514',
					'\u2534',
					'\u252c',
					'\u251c',
					'\u2500',
					'\u253c',
					'\u255e',
					'\u255f',
					'\u255a',
					'\u2554',
					'\u2569',
					'\u2566',
					'\u2560',
					'\u2550',
					'\u256C',
					'\u2567',
					'\u2568',
					'\u2564',
					'\u2565',
					'\u2559',
					'\u2558',
					'\u2552',
					'\u2553',
					'\u256b',
					'\u256a',
					'\u2518',
					'\u250c',
					'\u2588',
					'\u2584',
					'\u258c',
					'\u2590',
					'\u2580',
					'\u03b1',
					'\u00df',
					'\u0393',
					'\u03c0',
					'\u03a3',
					'\u03c3',
					'\u00b5',
					'\u03c4',
					'\u03a6',
					'\u0398',
					'\u03a9',
					'\u03b4',
					'\u221e',
					'\u03c6',
					'\u03b5',
					'\u2229',
					'\u2261',
					'\u00b1',
					'\u2265',
					'\u2264',
					'\u2320',
					'\u2321',
					'\u00f7',
					'\u2248',
					'\u00b0',
					'\u2219',
					'\u00b7',
					'\u221a',
					'\u207f',
					'\u00b2',
					'\u25a0',
					'\u00a0',
			};
			return chars[c - 127];
		} else {
			return c;
		}
	}
	
	private Color decodeCGA(int colorNumber) {
		float red = 2.0f/3.0f*((float) (colorNumber & 4))/4f + 1f/3f*((float) (colorNumber & 8))/8f;
		float green = 2.0f/3.0f*((float) (colorNumber & 2))/2f + 1f/3f*((float) (colorNumber & 8))/8f;
		float blue = 2f/3f*((float) (colorNumber & 1))/1f + 1f/3f*((float) (colorNumber & 8))/8f;
		
		return new Color(red, green, blue);
	}

	public static void startScreen() {
		screen = new Screen();
		screen.start();
	}
}
