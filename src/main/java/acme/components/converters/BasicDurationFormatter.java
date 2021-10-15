package acme.components.converters;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.format.Formatter;

import acme.datatypes.BasicDuration;

public class BasicDurationFormatter implements Formatter<BasicDuration>{

	@Override
	public String print(final BasicDuration object, final Locale locale) {
		return object.toString();
	}

	@Override
	public BasicDuration parse(final String text, final Locale locale) throws ParseException {
		Integer hours =0;
		Integer minutes=0;
		try {
			if(text.contains(":")) {
				final String[] split = text.split(":");
				hours = Integer.valueOf(split[0]);
				minutes = Integer.valueOf(split[1]);
			}else {
				hours  = Integer.valueOf(text);
			}
			return new BasicDuration(hours,minutes);
		}catch(final Exception e) {
			return null;
		}
		
		
	}

}
