
package acme.features.officer.endeavour.async;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import acme.entities.duties.Duty;
import acme.features.officer.duty.OfficerDutyRepository;

@Controller
@RequestMapping("/officer/endeavour/async")
public class EndeavourAsyncController {

	@Autowired
	OfficerDutyRepository	repo;
	@Autowired
	ModelMapper				mapper;


	@GetMapping("/exec-period")
	public @ResponseBody ExecutionPeriodResponse getDuties(@RequestParam(value="dutyIds",required=true) final String dutyIds,@RequestParam(value="dateFormat",required=true) final String dateFormat) throws Exception {
		final List<Integer> ids = Arrays.asList(dutyIds.split(",")).stream().map(s->s.substring(5)).mapToInt(Integer::valueOf).boxed().collect(Collectors.toList());
		final List<Duty> duties = this.repo.findAllById(ids);
		
		final Optional<LocalDateTime> executionStart = duties.stream().map(Duty::getExecutionStart).min(LocalDateTime::compareTo);
		final Optional<LocalDateTime> executionEnd = duties.stream().map(Duty::getExecutionEnd).max(LocalDateTime::compareTo);
		
		final DateTimeFormatter  formatter = DateTimeFormatter .ofPattern(dateFormat);
		if(executionStart.isPresent() && executionEnd.isPresent()) {
			final String execStartStr = formatter.format(executionStart.get().minus(1, ChronoUnit.DAYS).with(LocalTime.of(8, 0)));
			final String execEndtStr = formatter.format(executionEnd.get().plus(1,ChronoUnit.DAYS).with(LocalTime.of(17, 0)));
			return new ExecutionPeriodResponse(execStartStr,execEndtStr);
		}else {
			throw new Exception("Imposible to compute execution period");
		}
	}
}
