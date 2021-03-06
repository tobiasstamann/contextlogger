> This document contains documentation for the contextlogger-provider-api module. Click [here](/README.md) to get an overview that TracEE is about.

# contextprovider-api

> The TracEE contextprovider-api project offers all interfaces and annotations needed to define custom context data provider.

## Example: Cookie context data provider 

```java
package io.tracee.contextlogger.contextprovider.servlet.contextprovider

import javax.servlet.http.Cookie;

import io.tracee.contextlogger.contextprovider.api.Order;
import io.tracee.contextlogger.contextprovider.api.TraceeContextProvider;
import io.tracee.contextlogger.contextprovider.api.TraceeContextProviderMethod;
import io.tracee.contextlogger.contextprovider.api.WrappedContextData;

/**
 * Context provider for ServletCookieContextProvider.
 * Created by Tobias Gindler, holisticon AG on 24.01.14.
 */
@TraceeContextProvider(displayName = "servletCookies", order = Order.SERVLET)
public final class ServletCookieContextProvider implements WrappedContextData<Cookie> {

    private Cookie cookie;

    public ServletCookieContextProvider() {
    }

    public ServletCookieContextProvider(Cookie cookie) {
        this.cookie = cookie;
    }

    @Override
    public void setContextData(Object instance) throws ClassCastException {
        this.cookie = (Cookie) instance;
    }

    @Override
    public Class<Cookie> getWrappedType() {
        return Cookie.class;
    }

    @SuppressWarnings("unused")
    @TraceeContextProviderMethod(
            displayName = "name",
            order = 10)
    public String getName() {
        if (cookie != null) {
            return cookie.getName();
        }
        return null;
    }
    
    @SuppressWarnings("unused")
    @TraceeContextProviderMethod(
            displayName = "value",
            order = 20)
    public String getValue() {
        if (cookie != null) {
            return cookie.getValue();
        }
        return null;
    }

    // ...
}
```

## How to create a custom data provider
A custom data provider class must have the following characteristics

+ The class must beu annotated with @TraceeContextProvider annotation at class
+ The class must implement the WrappedContextData<YOUR_WRAPPED_CLASS>
+ The class should offer methods that provide the context data. Those methods should be annotated with @TraceeContextProviderMethod method and must be public and return a value of type String
