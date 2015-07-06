package io.tracee.contextlogger.contextprovider.jaxws;

import io.tracee.contextlogger.contextprovider.api.TraceeContextProviderServiceProvider;

/**
 * Provides all javaee (CDI, ...) related service providers
 */
public class JaxwsContextProviderServiceProvider implements TraceeContextProviderServiceProvider {

	public static final Class[] IMPLICIT_CONTEXT_PROVIDER = {};
	public static final Class[] CONTEXT_PROVIDER = {JaxwsContextProviderServiceProvider.class};

	@Override
	public Class[] getImplicitContextProvider() {
		return IMPLICIT_CONTEXT_PROVIDER;
	}

	@Override
	public Class[] getContextProvider() {
		return CONTEXT_PROVIDER;
	}


}
