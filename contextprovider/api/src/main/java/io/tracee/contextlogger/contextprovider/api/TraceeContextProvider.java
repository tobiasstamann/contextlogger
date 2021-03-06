package io.tracee.contextlogger.contextprovider.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to mark classes that can be processed by a context toJson provider implementation
 * ( for example the TraceeGenericGsonSerializer).
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.TYPE})
public @interface TraceeContextProvider {

	String displayName();

	int order() default 100;

	boolean suppressTypeInOutput() default false;

}
