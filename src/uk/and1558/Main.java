package uk.and1558;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLaf;

public class Main {
	public Utils utils = new Utils();
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FlatLaf.setUseNativeWindowDecorations(true);
		new MainGUI();
	}

}
