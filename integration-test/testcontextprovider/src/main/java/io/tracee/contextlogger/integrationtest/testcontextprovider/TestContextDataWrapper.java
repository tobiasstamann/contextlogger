package io.tracee.contextlogger.integrationtest.testcontextprovider;

import io.tracee.contextlogger.contextprovider.api.ProfileConfig;
import io.tracee.contextlogger.contextprovider.api.TraceeContextProvider;
import io.tracee.contextlogger.contextprovider.api.TraceeContextProviderMethod;
import io.tracee.contextlogger.contextprovider.api.WrappedContextData;

/**
 * Test wrapper class that wraps type {@link io.tracee.contextlogger.integrationtest.WrappedTestContextData}.
 */
@TraceeContextProvider(displayName = "testdata", order = 50)
@ProfileConfig(basic = true, enhanced = true, full = true)
public class TestContextDataWrapper implements WrappedContextData<WrappedTestContextData> {

	public static final String PROPERTY_NAME = "io.tracee.contextlogger.integrationtest.testcontextprovider.TestContextDataWrapper.output";

	private WrappedTestContextData contextData;

	public TestContextDataWrapper() {

	}

	public TestContextDataWrapper(final WrappedTestContextData contextData) {
		this.contextData = contextData;
	}

	@Override
	public void setContextData(Object instance) throws ClassCastException {
		this.contextData = (WrappedTestContextData) instance;
	}

	@Override
	public WrappedTestContextData getContextData() {
		return this.contextData;
	}

	@Override
	public Class<WrappedTestContextData> getWrappedType() {
		return WrappedTestContextData.class;
	}

	@SuppressWarnings("unused")
	@TraceeContextProviderMethod(displayName = "testoutput", order = 10)
	@ProfileConfig(basic = false, enhanced = true, full = true)
	public String getOutput() {
		return contextData != null ? contextData.getOutput() : null;
	}
}
