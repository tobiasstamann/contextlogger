package io.tracee.contextlogger.contextprovider.api;

/**
 * Order of known providers is defined here.
 */
public final class Order {

	@SuppressWarnings("unused")
	private Order() {
		// hide constructor
	}

	public static final int MESSAGE = 0;
	public static final int COMMON = 5;
	public static final int TRACEE = 10;
	public static final int WATCHDOG = 15;
	public static final int AGENT = 15;
	public static final int SERVLET = 20;
	public static final int JAXWS = 30;
	public static final int EJB = 40;
	public static final int JMS = 41;
	public static final int EXCEPTION = 80;

}
