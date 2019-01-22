UAT Project:  UC6HXPathElemInheritance
-------------------------------------------------------------------
This UAT is used to test XPath mappings. The WSDL has DerivedType [extend] Derived2Type [extend] Derived3Type

When this is mapped in the message started of the provider looks like there is some problem.

I've added only the Provider process to start with. 
___________________________________________________________________

Update 20110316: 
The project works now with soap-ui project checked in under GoldFiles. Don't forget to add authentication soap header and change URL.

The problems with this UAT project
-------------------------------------------------------------------
* <tibco:myFaultDetail xmlns:tibco="http://tibcouri/">org.osoa.sca.ServiceRuntimeException: java.lang.IllegalStateException: Invalid message part [parameters] does not contain an element [[d3El: null]] with valid namespace (null)
	at com.tibco.amf.platform.runtime.componentframework.internal.proxies.operation.AsyncToAsyncOperationHandler.handleException(AsyncToAsyncOperationHandler.java:249)
	at com.tibco.amf.platform.runtime.componentframework.internal.proxies.operation.AsyncToAsyncOperationHandler.invoke(AsyncToAsyncOperationHandler.java:156)
	at com.tibco.amf.platform.runtime.componentframework.internal.proxies.ProxyInvocationHandlerRegistry$ProxyInvocationContext.invoke(ProxyInvocationHandlerRegistry.java:421)
	at $Proxy47.invoke(Unknown Source)
	at com.tibco.amf.binding.soap.runtime.transport.http.SoapHttpInboundEndpoint.processHttpPost(SoapHttpInboundEndpoint.java:463)
	at com.tibco.amf.binding.soap.runtime.transport.http.SoapHttpServer.doPost(SoapHttpServer.java:137)
	at javax.servlet.http.HttpServlet.service(HttpServlet.java:710)
	at javax.servlet.http.HttpServlet.service(HttpServlet.java:803)
	at org.mortbay.jetty.servlet.ServletHolder.handle(ServletHolder.java:502)
	at org.mortbay.jetty.servlet.ServletHandler$Chain.doFilter(ServletHandler.java:1213)
	at com.tibco.governance.pa.amxcomponent.pep.http.HttpPepFilter.doFilter(HttpPepFilter.java:124)
	at org.mortbay.jetty.servlet.ServletHandler$Chain.doFilter(ServletHandler.java:1205)
	at com.tibco.amf.implementation.common.httpfilter.GenericComponentFilter.doFilter(GenericComponentFilter.java:65)
	at org.mortbay.jetty.servlet.ServletHandler$Chain.doFilter(ServletHandler.java:1205)
	at com.tibco.amf.hpa.tibcohost.jetty.internal.ConnectorFilter.doFilter(ConnectorFilter.java:49)
	at org.mortbay.jetty.servlet.ServletHandler$Chain.doFilter(ServletHandler.java:1205)
	at org.mortbay.jetty.servlet.ServletHandler.handle(ServletHandler.java:388)
	at org.mortbay.jetty.servlet.SessionHandler.handle(SessionHandler.java:182)
	at org.mortbay.jetty.handler.ContextHandler.handle(ContextHandler.java:765)
	at org.mortbay.jetty.handler.ContextHandlerCollection.handle(ContextHandlerCollection.java:230)
	at org.mortbay.jetty.handler.HandlerCollection.handle(HandlerCollection.java:114)
	at org.mortbay.jetty.handler.HandlerWrapper.handle(HandlerWrapper.java:152)
	at org.mortbay.jetty.Server.handle(Server.java:326)
	at org.mortbay.jetty.HttpConnection.handleRequest(HttpConnection.java:536)
	at org.mortbay.jetty.HttpConnection$RequestHandler.content(HttpConnection.java:928)
	at org.mortbay.jetty.HttpParser.parseNext(HttpParser.java:747)
	at org.mortbay.jetty.HttpParser.parseAvailable(HttpParser.java:218)
	at org.mortbay.jetty.HttpConnection.handle(HttpConnection.java:405)
	at org.mortbay.jetty.bio.SocketConnector$Connection.run(SocketConnector.java:228)
	at java.util.concurrent.ThreadPoolExecutor$Worker.runTask(ThreadPoolExecutor.java:886)
	at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:908)
	at java.lang.Thread.run(Thread.java:662)
Caused by: java.lang.IllegalStateException: Invalid message part [parameters] does not contain an element [[d3El: null]] with valid namespace (null)
	at com.tibco.amf.spline.impl.utils.SplineUtils.createAbstractMessageFromSOAP11Envelope(SplineUtils.java:225)
	at com.tibco.amf.spline.impl.context.helpers.ConversionHelpers.soap2AbstractMessageConversion(ConversionHelpers.java:104)
	at com.tibco.amf.spline.impl.context.helpers.ConversionHelpers.convert(ConversionHelpers.java:66)
	at com.tibco.amf.binding.soap.runtime.databinding.SOAP2AbstractMsgPullConverter.convert(SOAP2AbstractMsgPullConverter.java:119)
	at com.tibco.amf.binding.soap.runtime.databinding.SOAP2AbstractMsgPullConverter.convert(SOAP2AbstractMsgPullConverter.java:37)
	at com.tibco.amf.binding.soap.runtime.databinding.SOAP2AbstractMsgPullConverter.convert(SOAP2AbstractMsgPullConverter.java:1)
	at com.tibco.amf.platform.runtime.componentframework.internal.core.DataBindingController.convert(DataBindingController.java:621)
	at com.tibco.amf.platform.runtime.componentframework.internal.proxies.operation.OperationHandler.beforeTargetRequest(OperationHandler.java:188)
	at com.tibco.amf.platform.runtime.componentframework.internal.proxies.operation.AsyncToAsyncOperationHandler.invoke(AsyncToAsyncOperationHandler.java:82)
	... 30 more</tibco:myFaultDetail>

___________________________________________________________________


WSDL structure description
-------------------------------------------------------------------


___________________________________________________________________	
  

