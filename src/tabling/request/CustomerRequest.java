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

	public void deleteCustomer(String phone) {

		try {
			String selectUrl = urlStr + "/delete/" + phone;
			String str = Request.getRequest(selectUrl);
			System.out.println(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
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
	
	public void logoutCustomer(String phone) {
		try {
			String selectUrl = urlStr + "/logout/" + phone;
			String str = Request.getRequest(selectUrl);
			System.out.println(str);
		} catch (IOException e) {
		}
	}
}
