package tabling;

import java.awt.Font;

import javax.swing.UIManager;

import tabling.frame.LoginSelectFrame;

public class Tabling {

	public static void main(String[] args) {
		UIManager.put("OptionPane.messageFont", new Font("Noto Sans KR", Font.PLAIN, 12));
		new LoginSelectFrame();
	}

}