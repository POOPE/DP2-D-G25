
package acme.components.converters;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.springframework.format.Formatter;

import acme.framework.helpers.MessageHelper;

public class LocalDateFormatter implements Formatter<LocalDate> {

	@Override
	public String print(final LocalDate object, final Locale locale) {
		String res;
		try {
			final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(MessageHelper.getMessage("default.format.moment.date"));
			res = object.format(formatter);
		} catch (final Exception e) {
			res = "";
		}
		return res;
	}

	@Override
	public LocalDate parse(final String text, final Locale locale) throws ParseException {
		LocalDate res;
		try {
			final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(MessageHelper.getMessage("default.format.moment.date"));
			formatter.withLocale(locale);
			res = LocalDate.parse(text, formatter);
		} catch (final Exception e) {
			res = null;
		}
		return res;
	}

}
