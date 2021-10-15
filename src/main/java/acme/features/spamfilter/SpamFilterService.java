
package acme.features.spamfilter;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.Arrays;
import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import acme.features.administrator.configuration.AdministratorConfigurationRepository;
import acme.framework.components.Errors;
import acme.framework.components.Request;
import acme.framework.entities.UserRole;
import acme.framework.services.ValidateMethod;
import ch.qos.logback.classic.Logger;

public abstract class SpamFilterService<R extends UserRole, E> implements ValidateMethod<R, E> {

	private static final Logger						logger	= (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(SpamFilterService.class);

	private AdministratorConfigurationRepository	repo;


	@Autowired
	public final void setLogRepository(final AdministratorConfigurationRepository repo) {
		this.repo = repo;
	}

	public abstract void validateAndFilter(Request<E> request, E entity, Errors errors);

	@Override
	public void validate(final Request<E> request, final E entity, final Errors errors) {
		final List<String> spamWords = Arrays.asList(this.repo.findAll().get(0).getSpamWords().split("\\s*,\\s*"));
		assert request != null;
		assert entity != null;
		assert errors != null;

		Integer wordCount = 0;
		Integer spamWordCount = 0;
		final Double threshold = this.repo.findAll().get(0).getThreshold();

		BeanInfo beanInfo;
		try {
			beanInfo = Introspector.getBeanInfo(entity.getClass());

			for (final PropertyDescriptor propertyDesc : beanInfo.getPropertyDescriptors()) {
				final Object value = propertyDesc.getReadMethod().invoke(entity);

				if (value instanceof String && !((String) value).isBlank()) {
					final List<String> words = Arrays.asList(((String) value).split("\\s+"));
					wordCount += words.size();

					for (final String spamWord : spamWords) {
						if (((String) value).contains(spamWord)) {
							spamWordCount++;
						}
					}

				}

			}
			SpamFilterService.logger.info("SpamFilterService  - Word count: {0}", wordCount);
			SpamFilterService.logger.info("SpamFilterService  - Spam word count: {0}", spamWordCount);
			
			if ( spamWordCount / (wordCount * 1.0) >= threshold) {
				SpamFilterService.logger.warn("SpamFilterService - Spam words in entity exceeding permisible threshold. Found "+spamWordCount+"/"+wordCount+" words were spam.");
				errors.state(request, false, "*", "form.error.spamDetected");
			}

		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
		this.validateAndFilter(request, entity, errors);

	}

}
