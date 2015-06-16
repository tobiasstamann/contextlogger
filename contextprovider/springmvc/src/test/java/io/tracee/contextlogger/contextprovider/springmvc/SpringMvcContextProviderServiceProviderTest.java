package io.tracee.contextlogger.contextprovider.springmvc;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.hamcrest.Matchers;
import org.junit.Test;

import io.tracee.contextlogger.contextprovider.ContextProviderServiceLoader;
import io.tracee.contextlogger.contextprovider.java.JavaThrowableContextProvider;
import io.tracee.contextlogger.contextprovider.springmvc.contextprovider.HandlerMethodContextProvider;
import io.tracee.contextlogger.contextprovider.springmvc.contextprovider.MethodParameterContextProvider;

/**
 * Unit test for {@link SpringMvcContextProviderServiceProvider}.
 */
public class SpringMvcContextProviderServiceProviderTest {

    @Test
    public void should_load_core_and_aspectj_context_providers() {

        Class[] expectedContextProviders = { JavaThrowableContextProvider.class, HandlerMethodContextProvider.class, MethodParameterContextProvider.class };

        Class[] contextProviders = ContextProviderServiceLoader.getServiceLocator().getContextProvider();

        assertThat(contextProviders, notNullValue());

        Set<Class> contextProviderSet = new HashSet<Class>(Arrays.asList(contextProviders));

        assertThat(contextProviderSet.containsAll(Arrays.asList(expectedContextProviders)), Matchers.is(true));

    }
}
