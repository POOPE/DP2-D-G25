
package acme.components.converters;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.springframework.format.Formatter;

import acme.framework.helpers.MessageHelper;

public class LocalDateTimeFormatter implements Formatter<LocalDateTime> {

	@Override
	public String print(final LocalDateTime object, final Locale locale) {
		String res;
		try {
			final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(MessageHelper.getMessage("default.format.moment"));
			res = object.format(formatter);
		} catch (final Exception e) {
			res = "";
		}
		return res;
	}

	@Override
	public LocalDateTime parse(final String text, final Locale locale) throws ParseException {
		LocalDateTime res;
		try {
			final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(MessageHelper.getMessage("default.format.moment"));
			formatter.withLocale(locale);
			res = LocalDateTime.parse(text, formatter);
		} catch (final Exception e) {
			res = null;
		}
		return res;
	}

}
