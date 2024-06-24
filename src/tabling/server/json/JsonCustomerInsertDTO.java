package tabling.server.json;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JsonCustomerInsertDTO extends JsonDTO {
	private String name;
	private String phone;
	private int locationId;
}
