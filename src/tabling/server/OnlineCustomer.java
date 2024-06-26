package tabling.server;

import java.util.HashSet;
import java.util.Set;

public class OnlineCustomer {
	private static Set<String> customerPhone = new HashSet<>();
	public static Set<String> getCustomerPhone(){
		return customerPhone;
	}
}
