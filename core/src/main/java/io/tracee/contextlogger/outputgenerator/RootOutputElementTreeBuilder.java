package io.tracee.contextlogger.outputgenerator;

import io.tracee.contextlogger.outputgenerator.outputelements.CollectionOutputElement;
import io.tracee.contextlogger.outputgenerator.outputelements.NullValueOutputElement;
import io.tracee.contextlogger.outputgenerator.outputelements.OutputElement;
import io.tracee.contextlogger.profile.ProfileSettings;

/**
 * Converts given instances to an OutputElement instance hierarchy.
 */
public class RootOutputElementTreeBuilder {

    private final RecursiveOutputElementTreeBuilder recursiveOutputElementTreeBuilderImpl;

    /**
     * Hidden constructor.
     */
    protected RootOutputElementTreeBuilder(final RecursiveOutputElementTreeBuilder recursiveOutputElementTreeBuilder) {
        this.recursiveOutputElementTreeBuilderImpl = recursiveOutputElementTreeBuilder;
    }

    protected OutputElement buildOutputElementTreeMain(Object... instances) {

        if (instances == null || instances.length == 0) {

            return NullValueOutputElement.INSTANCE;

        }
        else if (instances.length == 1) {

            OutputElement outputElement = recursiveOutputElementTreeBuilderImpl.convertInstanceRecursively(new RecursiveOutputElementTreeBuilderState(),
                    instances[0]);
            return outputElement != null ? outputElement : NullValueOutputElement.INSTANCE;
        }
        else {

            // must wrap all passed objects inside a complex element
            CollectionOutputElement complexOutputElement = new CollectionOutputElement(Object[].class, instances);

            for (Object instance : instances) {

                OutputElement outputElement = recursiveOutputElementTreeBuilderImpl.convertInstanceRecursively(
                        new RecursiveOutputElementTreeBuilderState(), instance);
                if (outputElement != null) {
                    complexOutputElement.addElement(outputElement);
                }
            }

            return complexOutputElement;

        }
    }

    public static OutputElement buildOutputElementTree(final ProfileSettings profileSettings, final Object... objects) {
        return new RootOutputElementTreeBuilder(new RecursiveOutputElementTreeBuilderImpl(profileSettings)).buildOutputElementTreeMain(objects);
    }

}
