package ver01;

import java.sql.SQLException;

import ver01frame.LoginFrame;
import ver01frame.SignInFrame;

public class Start {

	SignInFrame signInFrame; // 회원가입 화면
	LoginFrame loginFrame; // 로그인 화면
	UserDAOImpl userDao; // 이용자 데이터 CRUD

	public Start() {
		signInFrame = new SignInFrame();
		loginFrame = new LoginFrame();
		userDao = new UserDAOImpl();
	}

	// test
	public static void main(String[] args) {

		
		
		
		
		
	} // end of main
	
} // end of class
