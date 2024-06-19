package ver01;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface UserDAO {

	// 회원가입
	public void addUser(CustomerDTO dto) throws SQLException;

	// 가입시 전화번호 중복 조회
	public boolean authenticatePhone(String phone)throws SQLException;
	
	// 로그인 - 유저 전체 조회
	public List<CustomerDTO> getAllUsers() throws SQLException;

	// TODO
	// 회원정보 수정

	// TODO
	// 회원정보 삭제

}
