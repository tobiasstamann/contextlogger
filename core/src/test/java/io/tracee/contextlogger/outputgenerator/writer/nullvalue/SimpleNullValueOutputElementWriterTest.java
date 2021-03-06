package io.tracee.contextlogger.outputgenerator.writer.nullvalue;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

import io.tracee.contextlogger.outputgenerator.outputelements.NullValueOutputElement;

/**
 * Unit test for {@link io.tracee.contextlogger.outputgenerator.writer.nullvalue.SimpleNullValueOutputElementWriter}.
 */
public class SimpleNullValueOutputElementWriterTest {

    @Test
    public void should_generate_output_for_fully_defined_null_valued_output_element_correctly() {

        NullValueOutputElement nullValueOutputElement = NullValueOutputElement.INSTANCE;

        String output = new SimpleNullValueOutputElementWriter().produceOutput(nullValueOutputElement);

        MatcherAssert.assertThat(output, Matchers.is("null"));

    }

    @Test
    public void should_generate_output_for_passed_null_value_correctly() {

        NullValueOutputElement nullValueOutputElement = null;

        String output = new SimpleNullValueOutputElementWriter().produceOutput(nullValueOutputElement);

        MatcherAssert.assertThat(output, Matchers.is("null"));

    }

    @Test
    public void should_generate_output_for_encapsulted_null_valued_type_correctly() {

        NullValueOutputElement nullValueOutputElement = NullValueOutputElement.INSTANCE;

        String output = new SimpleNullValueOutputElementWriter().produceOutput(nullValueOutputElement);

        MatcherAssert.assertThat(output, Matchers.is("null"));

    }

}
