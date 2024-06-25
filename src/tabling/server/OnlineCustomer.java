package tabling.server;

import java.util.Vector;

public class OnlineCustomer {
	private static Vector<String> customerPhone = new Vector<>();
	public static Vector<String> getCustomerPhone(){
		return customerPhone;
	}
}
