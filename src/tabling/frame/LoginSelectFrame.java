package tabling.frame;

import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;

public class LoginSelectFrame extends JFrame {

	private JButton customerBtn;
	private JButton restaurantBtn;
	private LoginSelectFrame mainLoginFrame;

	public LoginSelectFrame() {
		initData();
		setInitLayout();
		initListener();
	}

	private void initData() {
		mainLoginFrame = this;
		setTitle("테이블링");
		setSize(400, 600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		setResizable(false);

		customerBtn = new JButton("고객 로그인");
		restaurantBtn = new JButton("점주 로그인");
		setVisible(true);
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
				new CustomerLoginFrame(mainLoginFrame).setVisible(true);
				;
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
		new LoginSelectFrame();
	}

}
