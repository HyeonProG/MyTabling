package ver01frame;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class LocationFrame extends JFrame {
	
private JLabel background;
	
	private JButton button1;
	private JButton button2;
	private JButton button3;
	private JButton button4;
	private JButton button5;
	private JButton button6;
	private JButton button7;
	private JButton button8;
	private JButton button9;
	private JButton button10;
	private JButton button11;
	private JButton button12;
	private JButton button13;
	private JButton button14;
	private JButton button15;
	private JButton button16;
	
	public LocationFrame() {
		initData();
		setInitLayout();
	}
	
	private void initData() {
		setTitle("지역 찾기");
		setSize(500, 700);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Icon icon = new ImageIcon("img/location.png");
		background = new JLabel(icon);
		// 413, 355
		background.setSize(413, 355);
		background.setLocation(35,50);
		
		button1 = new JButton("강서구");
		button1.setBounds(40,220, 70, 50);
		button1.setBorderPainted(true); 
		button1.setContentAreaFilled(true); 
		button1.setFocusPainted(true); 
		
		button2 = new JButton("북구");
		button2.setBounds(160, 120, 40, 50);
		
		button3 = new JButton("금정구");
		button3.setBounds(220, 110, 50, 40);
		
		button4 = new JButton("기장군");
		button4.setBounds(320, 50, 80, 80);
		
		button5 = new JButton("해운대구");
		button5.setBounds(290, 190, 40, 30);
		
		button6 = new JButton("동래구");
		button6.setBounds(210, 160, 30, 30);
		
		button7 = new JButton("연제구");
		button7.setBounds(220, 190, 25, 20);
		
		button8 = new JButton("수영구");
		button8.setBounds(256, 220, 23, 23);
		
		button9 = new JButton("남구");
		button9.setBounds(230, 260, 23, 23);
		
		button10 = new JButton("동구");
		button10.setBounds(195, 260, 23, 23);
		
		button11 = new JButton("부산진구");
		button11.setBounds(180, 210, 40, 40);
		
		button12 = new JButton("사상구");
		button12.setBounds(125, 210, 40, 40);
		
		button13 = new JButton("서구");
		button13.setBounds(165, 270, 20, 20);
		
		button14 = new JButton("중구");
		button14.setBounds(185, 300, 20, 20);
		
		button15 = new JButton("영도구");
		button15.setBounds(210, 320, 20, 20);
		
		button16 = new JButton("사하구");
		button16.setBounds(110, 300, 50, 50);
		
	}
	
	private void setInitLayout() {
		
		setLayout(null);
		add(background);
		setVisible(true);
		
		background.add(button1);
		background.add(button2);
		background.add(button3);
		background.add(button4);
		background.add(button5);
		background.add(button6);
		background.add(button7);
		background.add(button8);
		background.add(button9);
		background.add(button10);
		background.add(button11);
		background.add(button12);
		background.add(button13);
		background.add(button14);
		background.add(button15);
		background.add(button16);
		
	}
	
	public static void main(String[] args) {
		new LocationFrame();
	}

}