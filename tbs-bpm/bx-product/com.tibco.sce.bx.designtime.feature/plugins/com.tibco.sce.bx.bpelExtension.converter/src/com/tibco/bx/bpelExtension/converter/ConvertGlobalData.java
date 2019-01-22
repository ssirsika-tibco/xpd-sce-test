package com.tibco.bx.bpelExtension.converter;

import java.util.Map;

import org.eclipse.emf.ecore.EObject;

import com.tibco.bx.bpelExtension.extensions.ExtensionsFactory;
import com.tibco.bx.bpelExtension.extensions.Script;
import com.tibco.bx.xpdl2bpel.N2PEConstants;
import com.tibco.bx.xpdl2bpel.converter.ConversionException;
import com.tibco.bx.xpdl2bpel.extensions.IActivityConfigurationModelBuilder;
import com.tibco.xpd.implementer.nativeservices.utilities.GlobalDataTaskJavaScriptUtil;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Participant;

public class ConvertGlobalData implements IActivityConfigurationModelBuilder {

	@Override
	public EObject transformConfigModel(Activity xpdlActivity, Map<String, Participant> participantMap) throws ConversionException {
    	String text = GlobalDataTaskJavaScriptUtil.getGlobalDataTaskJavaScript(xpdlActivity);
		Script script = ExtensionsFactory.eINSTANCE.createScript();
		script.setExpressionLanguage(N2PEConstants.JSCRIPT_LANGUAGE);
		script.setExpression(text);
		return script;
	}

}
