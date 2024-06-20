package tabling.frame;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import tabling.dao.RestaurantDAO;
import tabling.dto.RestaurantDTO;

public class ReservationFrame extends JFrame {
	
	RestaurantDTO restaurantDTO;
	
	private JButton reservationBtn;
	private JButton backBtn;
	private JLabel reservationStatusLabel;
	private JLabel restaurantNameLabel;

	public ReservationFrame(RestaurantDTO restaurantDTO) {
		this.restaurantDTO = restaurantDTO;
		initData();
		setInitLayout();
		initListener();
	}
	
	private void initData() {
		setTitle("예약하기");
		setSize(500, 700);
		setResizable(false);
		setLocationRelativeTo(null);
		setLayout(null);
		setVisible(true);
		
		restaurantNameLabel = new JLabel();
		restaurantNameLabel.setText(restaurantDTO.getRestaurantName());
		
		reservationStatusLabel = new JLabel();
		
		backBtn = new JButton("뒤로가기");
		
		reservationBtn = new JButton("예약하기");
		
	}
	
	private void setInitLayout() {
		restaurantNameLabel.setBounds(65, 45, 150, 50);
		restaurantNameLabel.setFont(new Font("Noto Sans KR", Font.BOLD, 15));
		restaurantNameLabel.setOpaque(true);
		restaurantNameLabel.setBackground(Color.WHITE);
		add(restaurantNameLabel);
		
		reservationStatusLabel.setBounds(65, 100, 350, 350);
		reservationStatusLabel.setOpaque(true);
		reservationStatusLabel.setBackground(Color.WHITE);
		add(reservationStatusLabel);
		
//		backBtn.setBounds(320, 50, 90, 30);
//		add(backBtn);
		
		reservationBtn.setBounds(65, 460, 350, 40);
		add(reservationBtn);
	}
	
	private void initListener() {
		reservationBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				int result = JOptionPane.showConfirmDialog(null, "예약하시겠습니까?", "예약", 2, 1);
				if (result == JOptionPane.YES_OPTION) {
					JOptionPane.showMessageDialog(null, "예약되었습니다.");
				}
			}
		});
	}
	
	public static void main(String[] args) {
		try {
			new ReservationFrame(new RestaurantDAO().getAllRestaurants().get(93));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
