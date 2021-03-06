package io.tracee.contextlogger.contextprovider.jaxws;

import javax.xml.ws.handler.HandlerResolver;
import javax.xml.ws.handler.PortInfo;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import io.tracee.contextlogger.TraceeContextLogger;

/**
 * Test class for {@link TraceeClientHandlerResolver} and fluent api ( {@link TraceeClientHandlerResolverBuilder}).
 */

@RunWith(PowerMockRunner.class)
@PrepareForTest({ TraceeContextLogger.class })
public class TraceeClientHandlerResolverTest {

    private TraceeContextLogger contextLogger;
    private TraceeClientErrorLoggingHandler unit;

    @Mock
    private PortInfo portInfo;

    @Test
    public void setup() {

        unit = new TraceeClientErrorLoggingHandler();
        AbstractTraceeErrorLoggingHandler.THREAD_LOCAL_SOAP_MESSAGE_STR.remove();

    }

    @Test
    public void shouldCreateSimpleHandlerResolver() {

        HandlerResolver handlerResolver = TraceeClientHandlerResolver.createSimpleHandlerResolver();

        MatcherAssert.assertThat(handlerResolver, Matchers.notNullValue());
        MatcherAssert.assertThat(handlerResolver.getHandlerChain(portInfo), Matchers.notNullValue());
        MatcherAssert.assertThat(handlerResolver.getHandlerChain(portInfo).size(), Matchers.is(1));
        MatcherAssert.assertThat(handlerResolver.getHandlerChain(portInfo).get(0).getClass(),
                Matchers.typeCompatibleWith(TraceeClientErrorLoggingHandler.class));

    }

    @Test
    public void shouldCreateHandlerResolverWithOtherHandlerByType() {

        HandlerResolver handlerResolver = TraceeClientHandlerResolver.buildHandlerResolver().add(TestHandler.class).build();

        MatcherAssert.assertThat(handlerResolver, Matchers.notNullValue());
        MatcherAssert.assertThat(handlerResolver.getHandlerChain(portInfo), Matchers.notNullValue());
        MatcherAssert.assertThat(handlerResolver.getHandlerChain(portInfo).size(), Matchers.is(2));
        MatcherAssert.assertThat(handlerResolver.getHandlerChain(portInfo).get(0).getClass(), Matchers.typeCompatibleWith(TestHandler.class));
        MatcherAssert.assertThat(handlerResolver.getHandlerChain(portInfo).get(1).getClass(),
                Matchers.typeCompatibleWith(TraceeClientErrorLoggingHandler.class));

    }

    @Test
    public void shouldCreateHandlerResolverWithOtherHandlerByInstance() {

        HandlerResolver handlerResolver = TraceeClientHandlerResolver.buildHandlerResolver().add(new TestHandler()).build();

        MatcherAssert.assertThat(handlerResolver, Matchers.notNullValue());
        MatcherAssert.assertThat(handlerResolver.getHandlerChain(portInfo), Matchers.notNullValue());
        MatcherAssert.assertThat(handlerResolver.getHandlerChain(portInfo).size(), Matchers.is(2));
        MatcherAssert.assertThat(handlerResolver.getHandlerChain(portInfo).get(0).getClass(), Matchers.typeCompatibleWith(TestHandler.class));
        MatcherAssert.assertThat(handlerResolver.getHandlerChain(portInfo).get(1).getClass(),
                Matchers.typeCompatibleWith(TraceeClientErrorLoggingHandler.class));

    }

    @Test
    public void shouldCreateHandlerResolverAndIgnoreHandlerWithoutDefaultConstructor() {

        HandlerResolver handlerResolver = TraceeClientHandlerResolver.buildHandlerResolver().add(TestHandlerWithoutDefaultConstructor.class).build();

        MatcherAssert.assertThat(handlerResolver, Matchers.notNullValue());
        MatcherAssert.assertThat(handlerResolver.getHandlerChain(portInfo), Matchers.notNullValue());
        MatcherAssert.assertThat(handlerResolver.getHandlerChain(portInfo).size(), Matchers.is(1));
        MatcherAssert.assertThat(handlerResolver.getHandlerChain(portInfo).get(0).getClass(),
                Matchers.typeCompatibleWith(TraceeClientErrorLoggingHandler.class));

    }

    @Test
    public void shouldCreateHandlerResolverAndIgnoreNullValuedHandlerInstance() {

        HandlerResolver handlerResolver = TraceeClientHandlerResolver.buildHandlerResolver().add((SOAPHandler)null).build();

        MatcherAssert.assertThat(handlerResolver, Matchers.notNullValue());
        MatcherAssert.assertThat(handlerResolver.getHandlerChain(portInfo), Matchers.notNullValue());
        MatcherAssert.assertThat(handlerResolver.getHandlerChain(portInfo).size(), Matchers.is(1));
        MatcherAssert.assertThat(handlerResolver.getHandlerChain(portInfo).get(0).getClass(),
                Matchers.typeCompatibleWith(TraceeClientErrorLoggingHandler.class));

    }

    @Test
    public void shouldCreateHandlerResolverAndIgnoreNullValuedHandlerType() {

        HandlerResolver handlerResolver = TraceeClientHandlerResolver.buildHandlerResolver().add((Class<SOAPHandler<SOAPMessageContext>>)null).build();

        MatcherAssert.assertThat(handlerResolver, Matchers.notNullValue());
        MatcherAssert.assertThat(handlerResolver.getHandlerChain(portInfo), Matchers.notNullValue());
        MatcherAssert.assertThat(handlerResolver.getHandlerChain(portInfo).size(), Matchers.is(1));
        MatcherAssert.assertThat(handlerResolver.getHandlerChain(portInfo).get(0).getClass(),
                Matchers.typeCompatibleWith(TraceeClientErrorLoggingHandler.class));

    }

}
