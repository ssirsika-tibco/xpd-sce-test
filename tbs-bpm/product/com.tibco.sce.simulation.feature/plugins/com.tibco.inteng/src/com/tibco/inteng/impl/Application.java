/* 
 ** 
 **  MODULE:             $RCSfile: IXpdlManualApplication.java $ 
 **                      $Revision: 1.30 $ 
 **                      $Date: 2005/05/12 09:38:34Z $ 
 ** 
 ** DESCRIPTION    :           
 **                                              
 ** 
 **  ENVIRONMENT:  Java - Platform independent 
 ** 
 **  COPYRIGHT:    (c) 2003 Staffware plc, All Rights Reserved. 
 ** 
 **  MODIFICATION HISTORY: 
 ** 
 **    $Log: IXpdlManualApplication.java $ 
 **    Revision 1.30  2005/05/12 09:38:34Z  wzurek 
 **    defect #21724 
 **    Revision 1.29  2005/04/12 14:40:42Z  KamleshU 
 **    Added change to invoke so that InteractionImpl class can invoke the scriptValidation method 
 **    Revision 1.28  2005/03/18 17:16:50Z  KamleshU 
 **    Changed the place where the validation script runs for the application 
 **    Revision 1.27  2005/03/04 11:47:22Z  KamleshU 
 **    Changed the way ScriptValidation is being accessed from the application 
 **    Revision 1.26  2005/02/04 17:27:53Z  KamleshU 
 **    Changed the paramater to the method which registers the objects on Context 
 **    Revision 1.25  2005/01/26 16:32:43Z  KamleshU 
 **    Revision 1.24  2004/09/03 11:47:45Z  WojciechZ 
 **    fix: do not validate read only fields 
 **    Revision 1.23  2004/08/09 08:52:34Z  WojciechZ 
 **    added: Extenrnal Package References 
 **    Revision 1.22  2004/08/02 16:13:09Z  WojciechZ 
 **    New Features: 
 **    - resource locator 
 **    - interaction factory 
 **    - different interface to automatic application (possibility to mix: install predefined aplication and configure it for every call using extended attributes) 
 **    Revision 1.21  2004/07/27 16:46:01Z  WojciechZ 
 **    fix: npe when validation form in subFlow 
 **    Revision 1.20  2004/07/26 10:34:00Z  KamleshU 
 **    Specific Implementation for Validate method as validate and sibmit functionality has been separated in AbstractInvocable 
 **    Revision 1.19  2004/07/21 15:52:18Z  WojciechZ 
 **    new feature: SubFlows 
 **    Revision 1.18  2004/07/19 12:51:51Z  WojciechZ 
 **    remove dependency of InteractionImpl class to praticular WorkflowProcess in xpdl file. Now InteractionImpl class represent the whole xpdl package, and ProcessStateImpl carry name of it WorkflowProcess (first step to SubFlows) 
 **    Revision 1.17  2004/07/16 09:47:31Z  WojciechZ 
 **    fixed: script validation 
 **    Revision 1.16  2004/07/14 13:51:59Z  WojciechZ 
 **    script business validation 
 **    Revision 1.15  2004/07/08 16:09:58Z  WojciechZ 
 **    fixed condition when to throw error if validation fails 
 **    Revision 1.14  2004/06/28 14:13:52Z  WojciechZ 
 **    multiple interaction threads 
 **    Revision 1.13  2004/06/21 08:10:37Z  WojciechZ 
 **    remove private method getActualField and use state.getPath() insted 
 **    Revision 1.12  2004/06/10 10:55:12Z  WojciechZ 
 **    fix: readonly XpdlData 
 **    Revision 1.11  2004/06/09 13:26:23Z  WojciechZ 
 **    fix: returning values are assigned to xpath slection properly 
 **    Revision 1.10  2004/06/07 10:02:45Z  WojciechZ 
 **    better error messages, passing only result from XPath 
 **    Revision 1.9  2004/05/20 09:29:27Z  WojciechZ 
 **    new helper method to get names of formal parameters 
 **    Revision 1.8  2004/05/17 16:11:10Z  WojciechZ 
 **    xslt feature goes to formApp 
 **    Revision 1.7  2004/05/06 11:02:27Z  WojciechZ 
 **    can load xslt transformation from external file 
 **    Revision 1.6  2004/04/16 08:53:37Z  WojciechZ 
 **    work up to 16/04/2004 
 **    Revision 1.5  2004/04/14 14:40:37Z  WojciechZ 
 **    added: date picker, xslt formating for data fields 
 **    Revision 1.4  2004/04/13 16:32:51Z  WojciechZ 
 **    work up to 13/04/2004 
 **    Revision 1.3  2004/04/08 16:02:11Z  WojciechZ 
 **    code review 
 **    move to apache xml beans 
 **    xpdl data use xml beans to hold the data 
 **    Revision 1.2  2004/04/02 14:19:42Z  WojciechZ 
 **    work up to 02/04/2004 
 **    Revision 1.1  2004/03/26 15:07:46Z  WojciechZ 
 **    Initial revision 
 **    Revision 1.1  2004/03/18 13:01:21Z  WojciechZ 
 **    Initial revision 
 **    Revision 1.0  18-Mar-2004 WojciechZ 
 **    Initial revision 
 ** 
 */
package com.tibco.inteng.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;
import org.wfmc.x2002.xpdl10.ActivityDocument.Activity;
import org.wfmc.x2002.xpdl10.ExtendedAttributeDocument.ExtendedAttribute;
import org.wfmc.x2002.xpdl10.FormalParameterDocument.FormalParameter;
import org.wfmc.x2002.xpdl10.ImplementationDocument.Implementation;
import org.wfmc.x2002.xpdl10.ToolDocument.Tool;

import com.tibco.inteng.ProcessState;
import com.tibco.inteng.exceptions.XpdlException;
import com.tibco.inteng.xpdldata.XpdlData;
import com.tibco.inteng.xpdldata.XpdlDataFactory;
import com.tibco.inteng.xpdldata.scriptable.ScriptableLogger;
import com.tibco.inteng.xpdldata.scriptable.ScriptableXpdlData;

/**
 * Description of application with convenience method to retrive initialized
 * list of formal parameters and submit list of parameters to workflow. Hides
 * xbean description of Application.
 * 
 * @author WojciechZ
 */
public class Application extends AbstractInvocable {
	/** log4j */
	private static Logger log = Logger.getLogger(Application.class);

	/** application description */
	private final org.wfmc.x2002.xpdl10.ApplicationDocument.Application appDef;

	private final com.tibco.inteng.Package xpdlPackage;

	/**
	 * Application description
	 * 
	 * @return appliccation description
	 */
	public org.wfmc.x2002.xpdl10.ApplicationDocument.Application getApplicationDef() {
		return appDef;
	}

	/**
	 * The constructor
	 * 
	 * @param appDef -
	 *            xmlbeans definition of application
	 * @param locator -
	 *            locator to locate validation script when needed
	 */
	public Application(
			org.wfmc.x2002.xpdl10.ApplicationDocument.Application appDef,
			com.tibco.inteng.Package xpdlPackage) {
		this.appDef = appDef;
		this.xpdlPackage = xpdlPackage;
	}

	/**
	 * @param script
	 * @param state
	 * @param args
	 * @return
	 */
	public static boolean scriptValidation(String script, ProcessState state,
			List args) {
		Context cx = Context.enter();
		try {
			boolean scriptConditionResult = false;
			Scriptable scope = cx.initStandardObjects(null, false);
			// adding the process level data fields and formal parameters to the
			// context
			for (Iterator iter = state.getFields().entrySet().iterator(); iter
					.hasNext();) {
				Map.Entry element = (Map.Entry) iter.next();
				ScriptableXpdlData data = new ScriptableXpdlData(
						(XpdlData) element.getValue(), scope, cx);
				scope.put((String) element.getKey(), scope, data);
			}
			// adding the argument list to the context
			if (args != null) {
				for (Iterator iter = args.iterator(); iter.hasNext();) {
					XpdlData xData = (XpdlData) iter.next();
					String name = xData.getName();
					ScriptableXpdlData data = new ScriptableXpdlData(xData,
							scope, cx);
					scope.put(name, scope, data);
				}
			}
			ScriptableLogger logger = new ScriptableLogger(scope, cx);
			scope.put("log", scope, logger);
			Object result = cx.evaluateString(scope, script, "<cmd>", 1, null);
			if (result != null) {
				scriptConditionResult = Boolean.valueOf(result.toString())
						.booleanValue();
			}
			if (log.isDebugEnabled()) {
				log.debug("Evaluate script result: '" + result + "' -> "
						+ scriptConditionResult);
			}
			return scriptConditionResult;
		} catch (Exception e) {
			XpdlException e1 = new XpdlException(
					"Error when evaluation condition: "
							+ (e.getCause() != null ? e.getCause().getMessage()
									: e.getMessage()), e);
			log.error(e1.getMessage(), e);
			throw e1;
		} finally {
			Context.exit();
		}
	}

	/**
	 * @return application Id
	 */
	public String getId() {
		return appDef.getId();
	}

	/**
	 * @param act
	 */
	protected void checkActivity(Activity act) {
		Implementation impl = act.getImplementation();
		if (impl == null || impl.sizeOfToolArray() != 1
				|| !appDef.getId().equals(impl.getToolArray(0).getId())) {
			XpdlException e = new XpdlException(
					"Try to submit application '"
							+ appDef.getId()
							+ "' to activity '"
							+ act.getId()
							+ "', which has no implementation or is implemented by different application");
			log.error(e);
			throw e;
		}

		Tool tool = impl.getToolArray(0);
		// check if there is no parameter application
		if (appDef.getFormalParameters() == null
				|| tool.getActualParameters() == null) {
			if (appDef.getFormalParameters() != tool.getActualParameters()) {
				XpdlException e = new XpdlException(
						"Formal and Actual parameters for applicatoin have to be identical! (on activity: "
								+ act.getId() + ", application: " + getId());
				log.error(e);
				throw e;
			}
		}
	}

	/**
	 * @see com.tibco.inteng.impl.AbstractInvocable#getFormalParameters()
	 */
	public List getFormalParameters() {
		List parameterList = getParameters();
		ArrayList toReturn = new ArrayList(parameterList.size());		
		for (int i = 0; i < parameterList.size(); i++) {
			FormalParameter fp = (FormalParameter)parameterList.get(i);
			XpdlData field = XpdlDataFactory.getDataType(
					fp.getId(), fp.getDataType(), xpdlPackage, false);
			toReturn.add(field);
		}
		return toReturn;

	}

	/**
	 * 
	 */
	protected List getParameters() {
		FormalParameter[] formalParameterArray = appDef.getFormalParameters()
				.getFormalParameterArray();
		ArrayList toReturn = new ArrayList(formalParameterArray.length);
		for (int i = 0; i < formalParameterArray.length; i++) {			
			toReturn.add(formalParameterArray[i]);
		}
		return toReturn;
	}

	/**
	 * @see com.tibco.inteng.impl.AbstractInvocable#getExtendedAttributes()
	 */
	public Map getExtendedAttributes() {
		ExtendedAttribute[] extendedAttributeArray = appDef
				.getExtendedAttributes().getExtendedAttributeArray();
		Map toReturn = new HashMap();
		for (int i = 0; i < extendedAttributeArray.length; i++) {
			String attribName = extendedAttributeArray[i].getName();
			toReturn.put(attribName,
					new com.tibco.inteng.impl.ExtendedAttributeImpl(
							extendedAttributeArray[i]));
		}
		return toReturn;
	}

	/**
	 * @param args
	 * @param state
	 * @see com.tibco.inteng.impl.AbstractInvocable#validate(ProcessStateImpl,List)
	 */
	public boolean validate(ProcessState state, List args) {
		boolean result = super.validate(state, args);
		if (!result) {
			return result;
		}
		for (int i = 0; i < args.size(); i++) {
			XpdlData data = (XpdlData) args.get(i);
			if (!data.isReadonly() && !data.validate()) {
				result = false;
			}
		}
		return result;
	}
}