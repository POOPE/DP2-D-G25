package acme.entities.endeavours;

import java.time.LocalDateTime;
import java.util.List;

import acme.entities.duties.DutyDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EndeavourDto {
	
	public int id;
	public int version;
	public List<DutyDto> duties;
	public boolean isPublic;
	public LocalDateTime executionStart;
	public LocalDateTime executionEnd;
}
