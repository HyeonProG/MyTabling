package ver01frame;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ReservationStatusFrame extends JFrame {

	private BackgroundPanel backgroundPanel;
	private JPanel reservationPanel;
	private JButton backBtn;
	private JButton cancelBtn;

	public ReservationStatusFrame() {
		initData();
		setInitLayout();
		initListener();
	}

	private void initData() {
		setTitle("예약 현황");
		setSize(500, 700);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
		
		backgroundPanel = new BackgroundPanel();
		reservationPanel = new JPanel();
		backBtn = new JButton("뒤로가기");
		cancelBtn = new JButton("예약 취소");
		
	}

	private void setInitLayout() {

		backgroundPanel.setSize(getWidth(), getHeight());
		backgroundPanel.setLayout(null);
		add(backgroundPanel);
		
		reservationPanel.setBounds(40, 200, 300, 300);
		reservationPanel.setSize(410, 300);
		backgroundPanel.add(reservationPanel);
		
		backBtn.setBounds(350, 20, 50, 50);
		backBtn.setSize(100, 50);
		backgroundPanel.add(backBtn);
		
		cancelBtn.setBounds(350, 520, 50, 50);
		cancelBtn.setSize(100, 50);
		backgroundPanel.add(cancelBtn);

	}

	private void initListener() {
		backBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				new MainMenuFrame();
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
		new ReservationStatusFrame();
	}

}
