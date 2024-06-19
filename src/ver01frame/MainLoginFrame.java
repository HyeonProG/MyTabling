package ver01frame;

import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;

public class MainLoginFrame extends JFrame {
	
	private JButton customerBtn;
	private JButton restaurantBtn;
	// TODO
	private CustomerLoginFrame customerLoginFrame;
	
	public MainLoginFrame() {
		initData();
		setInitLayout();
		initListener();
	}
	
	private void initData() {
		setTitle("테이블링");
		setSize(400, 600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		setVisible(true);
		setResizable(false);
		
		// TODO
		customerLoginFrame = new CustomerLoginFrame(this);
		
		customerBtn = new JButton("고객 로그인");
		restaurantBtn = new JButton("점주 로그인");
	}
	
	private void setInitLayout() {
		customerBtn.setBounds(40, 120, 50, 50);
		customerBtn.setSize(300, 200);
		customerBtn.setFont(new Font("Noto Sans KR", Font.BOLD, 15));
		add(customerBtn);

		restaurantBtn.setBounds(40, 320, 50, 50);
		restaurantBtn.setSize(300, 200);
		restaurantBtn.setFont(new Font("Noto Sans KR", Font.BOLD, 15));
		add(restaurantBtn);
	}
	
	private void initListener() {
		customerBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
//				new CustomerLoginFrame();
				customerLoginFrame.setVisible(true);
			}
		});
		
		restaurantBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				new RestaurantLoginFrame();
				setVisible(false);
			}
		});
		
	}
	
	public static void main(String[] args) {
		new MainLoginFrame();
	}
	
}
