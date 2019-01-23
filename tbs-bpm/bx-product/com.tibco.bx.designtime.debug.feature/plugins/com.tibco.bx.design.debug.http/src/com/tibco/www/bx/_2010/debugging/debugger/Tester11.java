package com.tibco.www.bx._2010.debugging.debugger;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.ws.BindingType;

@WebService(name = "Tester11", targetNamespace = "http://www.tibco.com/bx/2010/debugging/debugger")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
@BindingType(javax.xml.ws.soap.SOAPBinding.SOAP11HTTP_BINDING)
public interface Tester11 extends Tester {

}
