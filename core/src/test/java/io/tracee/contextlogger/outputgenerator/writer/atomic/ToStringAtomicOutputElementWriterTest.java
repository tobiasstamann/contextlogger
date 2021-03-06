package io.tracee.contextlogger.outputgenerator.writer.atomic;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

import io.tracee.contextlogger.outputgenerator.outputelements.AtomicOutputElement;

/**
 * Unit test for {@link io.tracee.contextlogger.outputgenerator.writer.atomic.ToStringAtomicOutputElementWriter}.
 */
public class ToStringAtomicOutputElementWriterTest {

    @Test
    public void should_produce_output_correctly() {

        final String value = "ABC";

        AtomicOutputElement atomicOutputElement = new AtomicOutputElement(String.class, value);

        String result = new ToStringAtomicOutputElementWriter().produceOutput(atomicOutputElement);

        MatcherAssert.assertThat(result, Matchers.is(value));

    }

    @Test
    public void should_produce_output_for_null_value_correctly() {

        final String value = null;

        AtomicOutputElement atomicOutputElement = new AtomicOutputElement(String.class, value);

        String result = new ToStringAtomicOutputElementWriter().produceOutput(atomicOutputElement);

        MatcherAssert.assertThat(result, Matchers.is("<NULL>"));

    }

}
