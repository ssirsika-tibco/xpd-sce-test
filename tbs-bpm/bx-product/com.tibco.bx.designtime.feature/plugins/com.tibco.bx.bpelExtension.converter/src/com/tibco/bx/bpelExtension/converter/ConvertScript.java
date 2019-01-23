package com.tibco.bx.bpelExtension.converter;

import java.util.Map;

import org.eclipse.emf.ecore.EObject;

import com.tibco.bx.bpelExtension.extensions.Script;
import com.tibco.bx.bpelExtension.extensions.ExtensionsFactory;
import com.tibco.bx.xpdl2bpel.converter.ConversionException;
import com.tibco.bx.xpdl2bpel.extensions.IActivityConfigurationModelBuilder;
import com.tibco.bx.xpdl2bpel.util.XPDLUtils;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TaskScript;

public class ConvertScript implements IActivityConfigurationModelBuilder {

	public static final String JSCRIPT_LANGUAGE = "urn:tibco:wsbpel:2.0:sublang:javascript";

	public EObject transformConfigModel(Activity xpdlActivity, Map<String, Participant> participantMap) throws ConversionException {
		//System.out.println("***in ConvertScript ***");
		Implementation impl = xpdlActivity.getImplementation();
		Task t = (Task) impl;
		TaskScript tScript = t.getTaskScript();
		Expression expr = tScript.getScript();
		String text = expr != null ? expr.getText() : "";
		if("DataMapper".equals(expr.getScriptGrammar())){
			text = XPDLUtils.getDataMapperScript(expr);
		}
		Script script = ExtensionsFactory.eINSTANCE.createScript();
		script.setExpressionLanguage(JSCRIPT_LANGUAGE);  // only supports java script at this time
		script.setExpression(text);
		return script;
	}

}
