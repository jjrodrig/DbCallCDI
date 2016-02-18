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

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.persistence.EntityManager;

import org.apache.deltaspike.core.api.provider.BeanProvider;
import org.hibernate.Session;

import es.indaba.jdbc.annotations.api.DatabaseCall;

@DatabaseCall
@Interceptor
public class StoredProcedureInterceptor {

	@AroundInvoke
	public Object callProcedure(InvocationContext invocationContext)
			throws Exception {

		GenericWork callWork = AnnotationProcessor.buildWork(invocationContext.getMethod(), invocationContext.getParameters());

		// Recuperar EM
		EntityManager manager = BeanProvider.getContextualReference(EntityManager.class);
		Session delegate = (Session) manager.getDelegate();

		// Ejecutar procedimiento
		delegate.doWork(callWork);

		invocationContext.proceed();

		return callWork.getResultObject();
	}
}