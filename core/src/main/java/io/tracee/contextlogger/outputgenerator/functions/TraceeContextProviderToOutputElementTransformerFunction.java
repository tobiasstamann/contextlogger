package io.tracee.contextlogger.outputgenerator.functions;

import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.tracee.contextlogger.contextprovider.api.TraceeContextProvider;
import io.tracee.contextlogger.contextprovider.core.utility.NameValuePair;
import io.tracee.contextlogger.impl.MethodAnnotationPair;
import io.tracee.contextlogger.impl.MethodAnnotationPairComparator;
import io.tracee.contextlogger.outputgenerator.RecursiveOutputElementTreeBuilder;
import io.tracee.contextlogger.outputgenerator.RecursiveOutputElementTreeBuilderState;
import io.tracee.contextlogger.outputgenerator.outputelements.NullValueOutputElement;
import io.tracee.contextlogger.outputgenerator.outputelements.OutputElement;
import io.tracee.contextlogger.outputgenerator.outputelements.TraceeContextProviderOutputElement;
import io.tracee.contextlogger.utility.ListUtilities;
import io.tracee.contextlogger.utility.TraceeContextLogAnnotationUtilities;

/**
 * Transforms a tracee context provider instance to a {@link io.tracee.contextlogger.outputgenerator.outputelements.CollectionOutputElement}.
 */
public class TraceeContextProviderToOutputElementTransformerFunction extends ToComplexOutputElementTransformerFunction<Object> {

    private static final TraceeContextProviderToOutputElementTransformerFunction instance = new TraceeContextProviderToOutputElementTransformerFunction();

    private static final Logger logger = LoggerFactory.getLogger(TraceeContextProviderToOutputElementTransformerFunction.class);

    public static TraceeContextProviderToOutputElementTransformerFunction getInstance() {
        return instance;
    }

    @Override
    public OutputElement apply(final RecursiveOutputElementTreeBuilder recursiveOutputElementTreeBuilder,
            final RecursiveOutputElementTreeBuilderState state, final Object instance) {
        TraceeContextProviderOutputElement complexOutputElement = new TraceeContextProviderOutputElement(instance.getClass(), instance);

        TraceeContextProvider annotation = TraceeContextLogAnnotationUtilities.getAnnotationFromType(instance);
        if (annotation == null) {

            // should not happen - but will be ignored
            logger.debug("TRACEE-CONTEXTLOGGER-DESERIALIZER - Got non annotated class");
            return NullValueOutputElement.INSTANCE;

        }
        else {

            // get those annotated methods
            final List<MethodAnnotationPair> entriesToPrint = TraceeContextLogAnnotationUtilities.getAnnotatedMethodsOfInstance(instance);

            // sort those methods
            Collections.sort(entriesToPrint, new MethodAnnotationPairComparator());

            for (MethodAnnotationPair singleEntry : entriesToPrint) {

                if (!singleEntry.shouldBeProcessed(recursiveOutputElementTreeBuilder.getProfileSettings())) {
                    continue;
                }

                try {

                    Object returnValue = singleEntry.getMethod().invoke(instance, null);

                    if (TraceeContextLogAnnotationUtilities.isFlatable(singleEntry.getMethod()) && isNameValuePair(returnValue)) {

                        // returnValue is single NameStringValuePair
                        final NameValuePair nameValuePair = (NameValuePair)returnValue;
                        addChildToComplexOutputElement(recursiveOutputElementTreeBuilder, state, complexOutputElement, nameValuePair.getName(),
                                nameValuePair.getValue());

                    }
                    else if (TraceeContextLogAnnotationUtilities.isFlatable(singleEntry.getMethod())
                            && (ListUtilities.isListOfType(returnValue, NameValuePair.class))) {

                        // returnValue is List of NameValuePairs
                        final List<NameValuePair> list = (List<NameValuePair>)returnValue;

                        for (NameValuePair nameValuePair : list) {
                            addChildToComplexOutputElement(recursiveOutputElementTreeBuilder, state, complexOutputElement, nameValuePair.getName(),
                                    nameValuePair.getValue());
                        }

                    }
                    else {

                        addChildToComplexOutputElement(recursiveOutputElementTreeBuilder, state, complexOutputElement, singleEntry.getAnnotation()
                                .displayName(), returnValue);

                    }

                }
                catch (Exception e) {

                    // to be ignored
                    logger.debug("TRACEE-CONTEXTLOGGER-DESERIALIZER - Exception during serialization.", e);

                }

            }

        }

        return complexOutputElement.isEmpty() ? null : complexOutputElement;
    }

    /**
     * Checks if the passed instance is of type {@link NameValuePair}.
     *
     * @param instance the instance to check
     * @return returns true if the instance is of type {@link NameValuePair}, otherwise false
     */
    static boolean isNameValuePair(Object instance) {

        return instance != null && NameValuePair.class.isInstance(instance);

    }

}
