package tabling.request;

import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import tabling.dto.CustomerDTO;
import tabling.dto.JsonDTO;

public class CustomerRequest {
	private String urlStr;
	private Gson gson;

	public CustomerRequest() {
		urlStr = "http://" + Request.getIp()+ ":8080/customer";
		gson = new GsonBuilder().setPrettyPrinting().create();
	}

	// 회원 가입 요청
	public void addCustomer(String customerName, String customerPhone, int locationId) {
		try {
			String insertUrl = urlStr + "/insert";
			JsonDTO dto = new JsonDTO(customerName, customerPhone, locationId);
			String str = Request.postRequest(insertUrl, dto);
			System.out.println(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 해당 번호의 회원 정보 요청
	public CustomerDTO getCustomerByPhone(String phone) {
		CustomerDTO dto = null;
		try {
			String selectUrl = urlStr + "/select/" + phone;
			String str = Request.getRequest(selectUrl);
			dto = gson.fromJson(str, CustomerDTO.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return dto;
	}

	// 회원 정보 수정 요청
	public void updateCustomer(String customerName, String customerPhone, int locationId) {
		try {
			String updateUrl = urlStr + "/update";
			JsonDTO dto = new JsonDTO(customerName, customerPhone, locationId);
			String str = Request.postRequest(updateUrl, dto);
			System.out.println(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 회원 탈퇴 요청
	public void deleteCustomer(String phone) {

		try {
			String selectUrl = urlStr + "/delete/" + phone;
			String str = Request.getRequest(selectUrl);
			System.out.println(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// 로그인 요청
	public String loginCustomer(String phone) {
		String check = "no";
		try {
			String selectUrl = urlStr + "/login/" + phone;
			check = Request.getRequest(selectUrl);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return check;
	}
	
	// 로그아웃 요청
	public void logoutCustomer(String phone) {
		try {
			String selectUrl = urlStr + "/logout/" + phone;
			String str = Request.getRequest(selectUrl);
			System.out.println(str);
		} catch (IOException e) {
		}
	}
}
