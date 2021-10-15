
package acme.features.spamfilter;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import acme.features.administrator.configuration.AdministratorConfigurationRepository;

public class SpamFilter {

	@Autowired
	private AdministratorConfigurationRepository	repo;

	private final Object							entity;
	private final Class<?>							clazz;


	public SpamFilter(final Class<?> clazz, final Object entity) {
		super();
		this.entity = entity;
		this.clazz = clazz;
	}

	public static SpamFilter of(final Object entity) {
		return new SpamFilter(entity.getClass(), entity);
	}

	public void validate() {
		//get spam words
		final List<String> spamWordsList = Arrays.asList(this.repo.findAll().get(0).getSpamWords().split(","));

		BeanInfo beanInfo;
		try {
			beanInfo = Introspector.getBeanInfo(this.clazz);

			for (final PropertyDescriptor propertyDesc : beanInfo.getPropertyDescriptors()) {
				//				final String propertyName = propertyDesc.getName();
				final Object value = propertyDesc.getReadMethod().invoke(this.entity);

				if (value instanceof String && spamWordsList.contains(value)) {
					Assert.isTrue(false, "contains spam (add i18n mssg)");
				}
			}

		} catch (final Exception e) {
			e.printStackTrace();
		}
	}
}
