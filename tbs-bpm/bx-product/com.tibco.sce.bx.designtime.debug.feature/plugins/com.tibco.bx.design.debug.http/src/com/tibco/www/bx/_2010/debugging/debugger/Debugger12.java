package com.tibco.www.bx._2010.debugging.debugger;

import javax.jws.WebService;
import javax.xml.ws.BindingType;

@WebService(name = "Debugger12", targetNamespace = "http://www.tibco.com/bx/2010/debugging/debugger")
// @SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
@BindingType(javax.xml.ws.soap.SOAPBinding.SOAP12HTTP_BINDING)
public interface Debugger12 extends Debugger {

}
