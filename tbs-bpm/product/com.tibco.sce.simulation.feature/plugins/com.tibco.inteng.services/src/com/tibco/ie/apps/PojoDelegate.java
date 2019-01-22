/* 
 ** 
 **  MODULE:             $RCSfile: PojoDelegate.java $ 
 **                      $Revision: 1.6 $ 
 **                      $Date: 2005/05/19 12:47:05Z $ 
 ** 
 ** DESCRIPTION    :           
 **                                              
 ** 
 **  ENVIRONMENT:  Java - Platform independent 
 ** 
 **  COPYRIGHT:    (c) 2004 Staffware plc, All Rights Reserved. 
 ** 
 **  MODIFICATION HISTORY: 
 ** 
 **    $Log: PojoDelegate.java $ 
 **    Revision 1.6  2005/05/19 12:47:05Z  KamleshU 
 **    Code change to catch Throwable, which will help to save the state from the error.jsp 
 **    Revision 1.5  2005/04/05 16:55:41Z  pburton 
 **    Added explicit cursor cleanup. 
 **    Revision 1.4  2005/03/01 19:27:05Z  KamleshU 
 **    Changes made due to change in the structure of the extended attributes 
 **    Revision 1.3  2004/12/06 12:53:17Z  TimST 
 **    javadoc only  
 **    Revision 1.2  2004/11/15 09:58:08Z  Timst 
 **    fixed bugs introduced by refactoring out the Pojo Delegate, specifically : business exception handling  
 **    Revision 1.1  2004/11/09 18:19:34Z  Timst 
 **    Initial revision 
 */
package com.tibco.ie.apps;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.rmi.RemoteException;

import org.apache.log4j.Logger;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlObject;
import org.apache.xmlbeans.XmlOptions;

import com.tibco.intEng.PojoApplicationType;
import com.tibco.inteng.ExtendedAttribute;
import com.tibco.inteng.apps.AbstractApplication;
import com.tibco.inteng.apps.AutomaticApplication;
import com.tibco.inteng.exceptions.DelegateException;
import com.tibco.inteng.exceptions.XpdlBusinessException;
import com.tibco.inteng.exceptions.XpdlException;
import com.tibco.inteng.utils.XmlUtils;
import com.tibco.inteng.xpdldata.SchemaTypedBase;
import com.tibco.inteng.xpdldata.XpdlData;

/**
 * PojoDelegate is a XpdlAutomaticApplication that is a single call to method on
 * plain old Java class component.<br>
 * Parameters conversion:
 * <ul>
 * <li>parameters are passed as arguments to POJO's method
 * <li>all parameters except last one must be IN parameters
 * <li>if last parameter is OUT, it is not passed to the method, but it is
 * initialized from return value of the method
 * <li>if last parameter is IN, it is passed as last argument, and no return
 * value is returned
 * <li>for all parameters, result of getXml is passed to POJO
 * <li>return value is passed as argument to setValue of return parameter
 * </ul>
 * <br>
 * <br>
 * Required extended attributes:
 * <ul>
 * <li><code>implementation</code> - must be <code>'pojoDelegate'</code>
 * <li><code>methodName</code> - Name of the method
 * <li><code>className</code> - Full name of the pojo class
 * </ul>
 * 
 * @author Tim
 */
public class PojoDelegate extends AbstractApplication implements
		AutomaticApplication {

	/** log4j */
	private final static Logger log = Logger.getLogger(PojoDelegate.class);

	/**
	 * Invoke method on EJB object. <br>
	 * 
	 * @see EjbDelegate - description of convention for parameters
	 * @see com..apps.XpdlAutomaticApplication#invoke(String,
	 *      ExtendedAttribute[], XpdlData[])
	 * 
	 * @param args
	 *            array of XpdlData objects
	 * @throws XpdlBusinessException
	 */
	public void invoke(String applicationId, ExtendedAttribute[] attribs,
			XpdlData[] args) throws XpdlBusinessException {

		if (log.isDebugEnabled()) {
			log.debug("enter: invoke");
		}
		boolean hasParameters;
		boolean hasReturn;
		if (args.length > 0) {
			if (args.length == 1) {
				// only one parameter, either IN or OUT
				if (args[0].isReadonly()) {
					hasParameters = true;
					hasReturn = false;
				} else {
					hasParameters = false;
					hasReturn = true;
				}
			} else {
				// multiple parameters
				if (args[args.length - 1].isReadonly()) {
					hasParameters = true;
					hasReturn = false;
				} else {
					hasParameters = true;
					hasReturn = true;
				}
			}
		} else {
			hasParameters = false;
			hasReturn = false;
		}
		if (log.isInfoEnabled()) {
			log.info("Flags for " + applicationId + ": hasParameters:"
					+ hasParameters + ", hasReturn:" + hasReturn);
		}

		Object pojo = getDelegate(applicationId, attribs, args);
		String methodName = getMethodName(attribs, applicationId);
		if (methodName == null || methodName.trim().length() < 1) {
			XpdlException e = new XpdlException(
					"The methodName for the Application could not be read, applicationId = "
							+ applicationId);
			log.error(e.getMessage(), e);
			throw e;
		}

		try {
			Class[] argType = null;
			Object[] inArgs = null;
			if (hasParameters) {
				int length;
				if (hasReturn) {
					length = args.length - 1;
				} else {
					length = args.length;
				}
				inArgs = new Object[length];
				argType = new Class[length];
				XmlOptions opt = new XmlOptions();
				for (int i = 0; i < length; i++) {
					XpdlData data = args[i];
					if (data instanceof SchemaTypedBase) {
						((SchemaTypedBase) data).stripEmptyOptionalFields();
					}
					inArgs[i] = data.getXml().xmlText(opt);
					if (log.isDebugEnabled()) {
						log.debug("ARGUMENT " + i + ":\n" + inArgs[i]);
					}
					argType[i] = String.class;
				}
			}
			Class delegateType = pojo.getClass();
			Method meth = delegateType.getMethod(methodName, argType);

			if (log.isDebugEnabled()) {
				if (hasParameters) {
					log.debug("Invoke delegate with " + inArgs.length
							+ " argument(s)");
				} else {
					log.debug("Invoke delegate without arguments");
				}
			}
			Object result = meth.invoke(pojo, inArgs);
			if (log.isInfoEnabled()) {
				log.info("Result from invocation: " + result);
			}

			String errorMessage = null;
			XmlObject xResult = null;
			if (result != null) {
				try {
					xResult = XmlObject.Factory.parse((String) result);
					XmlObject[] msg = xResult.selectPath("/fault/msg");
					if (msg != null && msg.length == 1) {
						errorMessage = XmlUtils.getInnerText(msg[0].xmlText());
						XmlObject[] xRes = xResult.selectPath("/fault/result");
						if (xRes != null && xRes.length == 1) {
							xResult = xRes[0];
						} else {
							xResult = null;
						}
					}
				} catch (XmlException e) {
					String message = "Error when parsing return type (fault or not): "
							+ e.getMessage()
							+ "; return value from delegate: "
							+ result;
					if (hasReturn) {
						DelegateException e1 = new DelegateException(message, e);
						log.error(e1.getMessage(), e);
						throw e1;
					}
					log.debug(message);
				}
			}

			if (hasReturn && xResult != null) {

				XmlCursor cur = xResult.newCursor();

				try {
					cur.toFirstChild();
					xResult = cur.getObject();
				} finally {
					cur.dispose();
				}

				args[args.length - 1].setValue(xResult);
				if (log.isInfoEnabled()) {
					log.info("Result from invocation: "
							+ args[args.length - 1].getXml());
				}
			}

			if (errorMessage != null) {
				log.warn("Business exception (returned in 'fault') in "
						+ "application (ID: " + applicationId + "): "
						+ errorMessage);
				throw new XpdlBusinessException(errorMessage);
			}

		} catch (NoSuchMethodException e) {
			XpdlException e1 = new XpdlException(
					"Method Not Found. Delegate Application (ID:'"
							+ applicationId
							+ "') cannot find method '"
							+ methodName
							+ "' in selected delegate (or parameters does not match)",
					e);
			log.error(e1.getMessage(), e);
			throw e1;
		} catch (InvocationTargetException targetEx) {

			Throwable srcEx = targetEx.getCause();
			if (srcEx instanceof RemoteException) {
				srcEx = srcEx.getCause();
			}
			if (srcEx instanceof RuntimeException) {
				log.warn("Unchecked exception in application (ID: "
						+ applicationId + "): " + srcEx.getClass().getName()
						+ " with message " + srcEx.getMessage());
				throw new XpdlBusinessException(srcEx.getMessage(), srcEx);
			}
			String msg = "Business exception (returned in 'fault') in "
					+ "application (ID: " + applicationId + "): "
					+ srcEx.getMessage();
			XpdlBusinessException e1 = new XpdlBusinessException(srcEx
					.getClass().getName(), srcEx);
			log.warn(msg);
			if (log.isDebugEnabled()) {
				log.warn("detail", srcEx);
			}
			throw e1;
		} catch (XpdlBusinessException e) {
			// rethrow Business Exception
			throw e;
		} catch (Throwable e) {
			// TS 14/11/04 Typed exceptions should be handled as user messages
			String msg = "Exception (returned in 'fault') in "
					+ "application (ID: " + applicationId + "): "
					+ e.getMessage();
			log.error(msg, e);
			XpdlBusinessException e1 = new XpdlBusinessException(msg, e);
			throw e1;
		}
		if (log.isDebugEnabled()) {
			log.debug("exit: invoke");
		}
	}

	/**
	 * 
	 * @param applicationId
	 * @param attribs
	 * @param args
	 * @return Object, the delegate object
	 * @throws DelegateException
	 */
	protected Object getDelegate(String applicationId,
			ExtendedAttribute[] attribs, XpdlData[] args)
			throws DelegateException {
		log.info("entry: getDelegate");
		Object obj = null;
		try {
			ExtendedAttribute extAttribute = getExtendedAttribute(
					ServicesConsts.POJO_APP_NAME, attribs);
			if (extAttribute == null) {
				XpdlException e = new XpdlException(
						"There should be an extended attribute with name 'PojoApplication' "
								+ "for the PojoApplication with id = "
								+ applicationId);
				log.error(e.getMessage(), e);
				throw e;
			}
			String xmlString = extAttribute.getXmlString();
			String[] childElements = readChildElements(xmlString, applicationId);
			if (childElements == null) {
				XpdlException e = new XpdlException(
						"The ClassName and MethodName childElements could "
								+ "not be read from the extended attribute for "
								+ "application " + applicationId);
				log.error(e.getMessage(), e);
				throw e;
			}
			String className = childElements[0];
			log.warn("Instantiating class: " + className);
			Class type = getClass().getClassLoader().loadClass(className);
			obj = type.newInstance();
		} catch (Exception e) {
			log.error(e.getClass().getName() + ":" + e.getMessage(), e);
			throw new DelegateException(e.getClass().getName() + ":"
					+ e.getMessage());
		}
		log.info("exit: getDelegate, returning: " + obj);
		return obj;
	}

	/**
	 * This method will read the childElements for the application
	 * 
	 * @param xmlObject
	 * @param applicationId
	 * @return
	 */
	private String[] readChildElements(String xmlString, String applicationId) {
		String[] toReturn = null;
		XmlCursor cur = null;
		try {
			XmlObject xmlObject = XmlObject.Factory.parse(xmlString);
			cur = xmlObject.newCursor();
			if (!cur.toFirstChild()) {
				XpdlException e = new XpdlException(
						"The PojoApplication element is not present under the  "
								+ "the extended attribute with name 'PojoApplication' "
								+ "for applicationId= " + applicationId);
				log.error(e.getMessage(), e);
				throw e;
			}
			PojoApplicationType type = (PojoApplicationType) cur.getObject()
					.changeType(PojoApplicationType.type);
			toReturn = new String[2];
			if (type.getClassName() == null
					|| type.getClassName().trim().length() < 1
					|| type.getMethodName() == null
					|| type.getMethodName().trim().length() < 1) {
				XpdlException e = new XpdlException(
						"Either ClassName or MethodName element do not have a value "
								+ "specified for the 'PojoApplication' "
								+ "with applicationId= " + applicationId);
				log.error(e.getMessage(), e);
				throw e;
			}
			toReturn[0] = type.getClassName().trim();
			toReturn[1] = type.getMethodName().trim();
		} catch (ClassCastException xe) {
			XpdlException e = new XpdlException(
					"The content of the extended attribute with name='PojoApplication' "
							+ "does not adhere to the schemaType of the PojoApplicationType "
							+ "for applicationId= " + applicationId);
			log.error(e.getMessage(), e);
			throw e;
		} catch(XmlException ex){
        	XpdlException e = new XpdlException(
                    "The content of the extended attribute with name='PojoApplication' "
                            + "could not be parsed into xml for applicationId= " + applicationId);
            log.error(e.getMessage(), e);
            throw e;
        }finally {
			if (cur != null) {
				cur.dispose();
			}
		}
		return toReturn;
	}

	/**
	 * 
	 * @param attribs
	 * @param applicationId
	 * @return
	 */
	protected String getMethodName(ExtendedAttribute[] attribs,
			String applicationId) {
		String toReturn = null;
		ExtendedAttribute extImpl = (ExtendedAttribute) getExtendedAttribute(
				ServicesConsts.POJO_APP_NAME, attribs);
		String xmlString = extImpl.getXmlString();	
		String[] children = readChildElements(xmlString, applicationId);
		if (children != null && children.length > 1) {
			toReturn = children[1];
		}
		return toReturn;
	}

}