package io.tracee.contextlogger.integrationtest;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

import io.tracee.contextlogger.TraceeContextLogger;
import io.tracee.contextlogger.contextprovider.api.Profile;
import io.tracee.contextlogger.outputgenerator.writer.BasicOutputWriterConfiguration;

/**
 * Integration test to check behavior for custom context data providers and wrapper that have no no args constructor.
 */
public class MissingConstructorIntegrationTest {

    @Test
    public void should_handle_missing_no_args_constructor_gently() {

        // should not break because of the missing no args constructor => type will be deserialized instead
        String result = TraceeContextLogger.create().enforceOutputWriterConfiguration(BasicOutputWriterConfiguration.JSON_INLINE)
                .enforceProfile(Profile.ENHANCED).apply().toString(TestBrokenImplicitContentDataProviderWithoutDefaultConstructor.class);
        MatcherAssert
                .assertThat(
                        result,
                        Matchers.equalTo("{\"TYPE\":\"Class\",\"enumConstants\":null,\"name\":\"String['io.tracee.contextlogger.integrationtest.TestBrokenImplicitContentDataProviderWithoutDefaultConstructor']\"}"));

    }

    @Test
    public void should_wrap_with_external_wrappers_correctly_using_enhanced_profile() {

        // should not default deserialization mechanism, because context data provider wrapper can't be created.
        String result = TraceeContextLogger.create().enforceProfile(Profile.ENHANCED).apply()
                .toString(new BrokenCustomContextDataWrapperWithMissingNoargsConstructor());
        MatcherAssert.assertThat(result, Matchers.startsWith("\"BrokenCustomContextDataWrapperWithMissingNoargsConstructor[]\""));

    }

}