package io.tracee.contextlogger.contextprovider.servlet;

import io.tracee.contextlogger.MessagePrefixProvider;
import io.tracee.contextlogger.TraceeContextLogger;
import io.tracee.contextlogger.api.ConfigBuilder;
import io.tracee.contextlogger.api.ContextLogger;
import io.tracee.contextlogger.connector.LogLevel;
import io.tracee.contextlogger.contextprovider.core.CoreImplicitContextProviders;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.sameInstance;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest({TraceeContextLogger.class, MessagePrefixProvider.class})
public class TraceeErrorLoggingFilterTest {

	private final static String LOG_MESSAGE_PREFIX = "XX";

	private final TraceeErrorLoggingFilter unit = new TraceeErrorLoggingFilter();
	private final HttpServletRequest request = mock(HttpServletRequest.class);
	private final HttpServletResponse response = mock(HttpServletResponse.class);
	private final HttpSession session = mock(HttpSession.class);
	private final FilterChain filterChain = mock(FilterChain.class);
	private TraceeContextLogger traceeContextLogger = mock(TraceeContextLogger.class);
	private final ConfigBuilder<ContextLogger> configBuilder = mock(ConfigBuilder.class);

	@Before
	public void setUpMocks() {
		// mock log message prefix creation
		mockStatic(MessagePrefixProvider.class);
		when(MessagePrefixProvider.provideLogMessagePrefix(Mockito.any(LogLevel.class), Mockito.any(String.class))).thenReturn(LOG_MESSAGE_PREFIX);
		when(MessagePrefixProvider.provideLogMessagePrefix(Mockito.any(LogLevel.class), Mockito.any(Class.class))).thenReturn(LOG_MESSAGE_PREFIX);

		mockStatic(TraceeContextLogger.class);
		when(TraceeContextLogger.create()).thenReturn(configBuilder);
		when(configBuilder.enforceOrder()).thenReturn(configBuilder);
		when(configBuilder.apply()).thenReturn(traceeContextLogger);
		when(request.getSession(false)).thenReturn(session);
	}

	@Test
	public void isTransparentWhenEverythingIsFine() throws Exception {
		unit.doFilter(request, response, filterChain);
		verify(filterChain).doFilter(request, response);
	}

	@Test(expected = ServletException.class)
	public void logWholeContextOnException() throws Exception {
		final ServletException expectedException = new ServletException("test");
		try {
			doThrow(expectedException).when(filterChain).doFilter(request, response);
			unit.doFilter(request, response, filterChain);
		} catch (Exception e) {
			verify(traceeContextLogger).logWithPrefixedMessage(LogLevel.ERROR, LOG_MESSAGE_PREFIX, CoreImplicitContextProviders.COMMON, CoreImplicitContextProviders.TRACEE, request, response,
					session, expectedException);
			throw e;
		}
	}

	@Test(expected = ServletException.class)
	public void rethrowRuntimeException() throws Exception {
		final RuntimeException expectedException = new RuntimeException();
		try {
			doThrow(expectedException).when(filterChain).doFilter(request, response);
			unit.doFilter(request, response, filterChain);
		} catch (ServletException e) {
			assertThat(e.getRootCause(), sameInstance((Throwable) expectedException));
			throw e;
		}
	}

	@Test(expected = IOException.class)
	public void rethrowIOException() throws Exception {
		final IOException expectedException = new IOException();
		try {
			doThrow(expectedException).when(filterChain).doFilter(request, response);
			unit.doFilter(request, response, filterChain);
		} catch (IOException e) {
			assertThat(e, sameInstance(expectedException));
			throw e;
		}
	}

	@Test(expected = ServletException.class)
	public void rethrowServletException() throws Exception {
		final ServletException expectedException = new ServletException();
		try {
			doThrow(expectedException).when(filterChain).doFilter(request, response);
			unit.doFilter(request, response, filterChain);
		} catch (ServletException e) {
			assertThat(e, sameInstance(expectedException));
			throw e;
		}
	}

}
