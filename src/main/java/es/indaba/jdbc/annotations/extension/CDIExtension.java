/*******************************************************************************
 *  This program is free software: you can redistribute it and/or modify it under
 *  the terms of the GNU Lesser General Public License as published by the Free
 *  Software Foundation, either version 3 of the License, or (at your option) any
 *  later version. This program is distributed in the hope that it will be
 *  useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General
 *  Public License for more details. You should have received a copy of the GNU
 *  Lesser General Public License along with this program. If not, see
 *  <http://www.gnu.org/licenses/>
 *  
 *******************************************************************************/
package es.indaba.jdbc.annotations.extension;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.AfterBeanDiscovery;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.ProcessAnnotatedType;
import javax.enterprise.inject.spi.WithAnnotations;

import org.apache.deltaspike.core.util.bean.BeanBuilder;

import es.indaba.jdbc.annotations.api.DatabaseCall;
import es.indaba.jdbc.annotations.impl.AnnotationInterfaceObjectFactory;

/**
 * JDBC CDI extension
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class CDIExtension implements javax.enterprise.inject.spi.Extension {

	private static final Logger logger = Logger.getLogger(CDIExtension.class.getName());

	private final Set<Bean> beans = new HashSet<Bean>();

	public <T> void processAnnotatedType(@WithAnnotations({ DatabaseCall.class }) @Observes ProcessAnnotatedType<T> pat, BeanManager bm) {
		Class type = pat.getAnnotatedType().getJavaClass();
		if (type.isInterface()) {
			logger.log(Level.INFO, "JDBC CDI Module - Type detected!! " + pat.getAnnotatedType().getBaseType());
			System.out.println("JDBC CDI Module - Type detected!!" + pat.getAnnotatedType().getAnnotations());

			AnnotationInterfaceObjectFactory factory = new AnnotationInterfaceObjectFactory();
			try {

				Class clazz = factory.buildClass(type);
				final BeanBuilder<Object> beanBuilder = new BeanBuilder<Object>(
						bm).passivationCapable(false).beanClass(clazz)
						.name(type.getName())
						.types(pat.getAnnotatedType().getBaseType())
						.beanLifecycle(new ContextualFactory<Object>(clazz));
				beans.add(beanBuilder.create());

			} catch (Exception e) {
				logger.log(Level.SEVERE, e.getMessage(), e);
			}
		}
	}

	public void afterBeanDiscovery(@Observes AfterBeanDiscovery abd, BeanManager bm) {
		logger.log(Level.INFO, "JDBC CDI Module - Activated");
		for (Bean bean : this.beans) {
			logger.log(Level.INFO, "JDBC CDI Module - DBCall discovered: {0}", bean.getName());
			abd.addBean(bean);
		}
		this.beans.clear();
	}
}