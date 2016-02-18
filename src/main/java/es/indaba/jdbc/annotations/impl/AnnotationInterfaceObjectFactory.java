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
package es.indaba.jdbc.annotations.impl;

import java.lang.reflect.Method;

import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyFactory;

import javax.persistence.EntityManager;

import org.apache.deltaspike.core.api.provider.BeanProvider;
import org.hibernate.Session;

@SuppressWarnings("rawtypes")
public class AnnotationInterfaceObjectFactory<T> {

	public Class buildClass(Class<T> type) {

		ProxyFactory factory = new ProxyFactory();
		factory.setInterfaces(new Class[] { type });
		factory.setHandler(new MethodHandler() {

			public Object invoke(Object arg0, Method method, Method arg2, Object[] parameters) throws Throwable {

				GenericWork callWork = AnnotationProcessor.buildWork(method, parameters);
				if (callWork == null)
					return null;
				// Recuperar EM
				EntityManager manager = BeanProvider.getContextualReference(EntityManager.class);
				Session delegate = (Session) manager.getDelegate();

				// Ejecutar procedimiento
				delegate.doWork(callWork);

				return callWork.getResultObject();
			}
		});

		Class c = factory.createClass();
		return c;
	}

	@SuppressWarnings("unchecked")
	public T getInstance(Class<T> c) throws InstantiationException, IllegalAccessException {
		Class derived = buildClass(c);
		return (T) derived.newInstance();
	}
}