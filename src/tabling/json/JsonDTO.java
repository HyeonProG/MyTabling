package tabling.json;

import lombok.Data;

@Data
public class JsonDTO {
	private int customerId;
	private String customerName;
	private String customerPhone;
	private int locationId;
	private int categoryId;
	private int restaurantId;
	private int foodId;
	private int reservationId;
	
	public JsonDTO(String customerName, String customerPhone, int locationId) {
		this.customerName = customerName;
		this.customerPhone = customerPhone;
		this.locationId = locationId;
	}
	
	public JsonDTO(int customerId, int restaurantId) {
		this.customerId = customerId;
		this.restaurantId = restaurantId;
	}
}
