package ver01frame;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import ver01.CustomerDTO;

public class MainMenuFrame extends JFrame {
	
	private BackgroundPanel backgroundPanel;
	
	private JButton categoryBtn;
	private JButton locationBtn;
	private JButton reservationBtn;
	private CustomerDTO customerDTO;

	public MainMenuFrame(CustomerDTO customerDTO) {
		this.customerDTO = customerDTO;
		initData();
		setInitLayout();
		initListener();
	}
	
	private void initData() {
		setTitle("메인 메뉴 " + customerDTO.getCustomerName() + "님");
		setSize(500, 700);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
		
		backgroundPanel = new BackgroundPanel();
		reservationBtn = new JButton("예약 현황");
		categoryBtn = new JButton("카테고리");
		locationBtn = new JButton("지역별");
		
	}
	
	private void setInitLayout() {
		
		backgroundPanel.setSize(getWidth(), getHeight());
		backgroundPanel.setLayout(null);
		add(backgroundPanel);
				
		reservationBtn.setBounds(80, 120, 120, 120);
		reservationBtn.setSize(330, 130);
		backgroundPanel.add(reservationBtn);
		
		categoryBtn.setBounds(80, 280, 120, 120);
		categoryBtn.setSize(330, 130);
		backgroundPanel.add(categoryBtn);
		
		locationBtn.setBounds(80, 440, 120, 120);
		locationBtn.setSize(330, 130);
		backgroundPanel.add(locationBtn);	
		
	}
	
	private void initListener() {
		reservationBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				new ReservationStatusFrame();
				setVisible(false);
			}
		});
		categoryBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				new CategoryFrame();
				setVisible(false);
			}
		});
		locationBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				new LocationFrame();
				setVisible(false);
			}
		});
	}
	
	private class BackgroundPanel extends JPanel {
		private JPanel backgroundPanel;
		private Image backgroundImage;
		
		public BackgroundPanel() {
			backgroundImage = new ImageIcon("img/backgroundimage.jpg").getImage();
			backgroundPanel = new JPanel();
			add(backgroundPanel);
		}
		
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
		}
		
	}
	
	public static void main(String[] args) {
		//new MainMenuFrame();
	}
	
}
