package io.tracee.contextlogger.output.internal.writer.circular;

import io.tracee.contextlogger.output.internal.outputelements.OutputElement;

/**
 * Public interface for all writers of already plotted output elements.
 */
public interface CircularReferenceOutputElementWriter {

    /**
     * Produces output for the passed CircularReferenceOutputElement
     *
     * @param outputElement the output element reference to be processed
     * @return the String representation of the atomic output element
     */
    String produceOutput(OutputElement outputElement);
}
