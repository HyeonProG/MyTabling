package tabling.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerDTO {

	private int customerId;
	private String customerName;
	private String phone;
	private String state;
	private int locationId;
	
}
