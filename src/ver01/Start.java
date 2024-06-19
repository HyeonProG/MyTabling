package ver01;

import ver01frame.MainLoginFrame;
import ver01frame.SignInFrame;

public class Start {

	SignInFrame signInFrame; // 회원가입 화면
	UserDAOImpl userDao; // 이용자 데이터 CRUD
	static MainLoginFrame main; // TODO

	public Start() {
	}

	// test
	public static void main(String[] args) {
		new MainLoginFrame();
		
	} // end of main
	
} // end of class
