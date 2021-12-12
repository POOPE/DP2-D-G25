package acme.forms;

import java.util.List;

import acme.entities.duties.Duty;
import acme.entities.endeavours.Endeavour;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter 
@Setter
@NoArgsConstructor
public class EndeavourDto {
	
	
	private Endeavour endeavour;
	
	private String dutyIds;
	
	private List<Duty> options;
	
}
