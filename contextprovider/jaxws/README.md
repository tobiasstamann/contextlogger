> This document contains documentation for the tracee contextprovider-jaxws module. Click [here](../../README.md) to get an overview that TracEE jaxws context logger is about.

# contextprovider-jaxws

> The TracEE contextprovider-jaxws project offers client and server side webservice handlers that will output webservice invocation related data if an exception is thrown during the invocation of the webservice. 

Therefore the contextprovider-jaxws module provides SOAPHandlers that detect exceptions and log contextual infomation to your log system.


## Example output
Depending on the selected context logger profile the output consists of invoked webservice method name, Soap request, thrown exception and other webservice call related data.

    TODO

## Maven artifacts
You need to add the following Dependencies to your projects pom.xml:
   
```xml
<!-- Binds the TracEE api -->
<dependency>
    <groupId>io.tracee</groupId>
    <artifactId>tracee-api</artifactId>
    <version>RELEASE</version>
</dependency>

<!-- Log Backend depending on your logging configuration-->
<dependency>
    <groupId>io.tracee.backend</groupId>
    <artifactId>tracee-slf4j</artifactId>
    <version>RELEASE</version>
</dependency>

<!-- Optional - used for context data propagation -->
<dependency>
    <groupId>io.tracee.binding</groupId>
    <artifactId>tracee-jaxws</artifactId>
    <version>RELEASE</version>
</dependency>
    
<!-- Binds context logging -->
<dependency>
    <groupId>io.tracee.contextlogger.contextprovider</groupId>
    <artifactId>contextprovider-jaxws</artifactId>
    <version>RELEASE</version>
</dependency>
```
         
## Using server side handlers
You can use the context logger by annotating your jax-ws webservice with the @HandlerChain annotation.

    @Stateless
    @WebService(serviceName = "TraceeJaxWsTestService", portName = "TraceeJaxWsTestPort",
            targetNamespace = "https://github.com/tracee/tracee/examples/jaxws/service/wsdl")
    @HandlerChain(file = "/traceeHandlerChain.xml")
    public class TraceeJaxWsTestService implements TraceeJaxWsTestWS {
    ...
}


Therefore you have to add the referenced traceeHandlerChain.xml file to your classpath (i.e. /src/main/resources). The file must have the following content:

```xml
<?xml version="1.0" encoding="UTF-8" standalone="yes" ?>
<javaee:handler-chains xmlns:javaee="http://java.sun.com/xml/ns/javaee">
    <javaee:handler-chain>
        <javaee:handler>
            <javaee:handler-class>io.tracee.contextlogger.contextprovider.jaxws.TraceeServerErrorLoggingHandler
			</javaee:handler-class>
        </javaee:handler>
        <javaee:handler>
            <javaee:handler-class>io.tracee.jaxws.container.TraceeServerHandler</javaee:handler-class>
        </javaee:handler>
    </javaee:handler-chain>
</javaee:handler-chains>
```

## Using client side handlers
First you have to create the client stub classes for the webservice wsdl. 
Then you are able to bind the error context logger by using the services handler resolver mechanism:

```java
// Example 1 : Use solely the context logger service handler
final TraceeJaxWsTestService testWebservice = new TraceeJaxWsTestService(
    new URL("http://localhost:8080/yourTestService/webservices/YourTestService?wsdl"));
testWebservice.setHandlerResolver(TraceeClientHandlerResolver.createSimpleHandlerResolver());
final YourTestWS ws = testWebservice.getPort(YourTestWS.class);

// Example 2 : Use error context logger next to tracee context propagation (or with any other service handler)
final TraceeJaxWsTestService testWebservice = new TraceeJaxWsTestService(
    new URL("http://localhost:8080/yourTestService/webservices/YourTestService?wsdl"));
testWebservice.setHandlerResolver(TraceeClientHandlerResolver.buildHandlerResolver().add(TraceeClientHandler.class).build());
final YourTestWS ws = testWebservice.getPort(YourTestWS.class);
```
