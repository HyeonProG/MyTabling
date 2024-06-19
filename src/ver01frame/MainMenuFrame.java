package ver01frame;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class MainMenuFrame extends JFrame {
	

	
	private JButton categoryBtn;
	private JButton locationBtn;
	private JButton resataurantBtn;
	

	public MainMenuFrame() {
		initData();
		setInitLayout();
		initListener();
	}
	
	private void initData() {
		setTitle("메인 메뉴");
		setSize(400, 600);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
		
		categoryBtn = new JButton();
		locationBtn = new JButton();
		resataurantBtn = new JButton();
		
	}
	
	private void setInitLayout() {
		categoryBtn.setBounds(50, 50, 50, 50);
		categoryBtn.setSize(50, 50);
		add(categoryBtn);

		
	}
	
	private void initListener() {
		
	}
	
	public static void main(String[] args) {
		new MainMenuFrame();
	}
	
}
