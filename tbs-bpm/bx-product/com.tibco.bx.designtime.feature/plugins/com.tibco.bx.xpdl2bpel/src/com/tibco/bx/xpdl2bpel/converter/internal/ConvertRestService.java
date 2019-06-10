package com.tibco.bx.xpdl2bpel.converter.internal;

import java.io.IOException;
import java.util.Collections;

import javax.xml.namespace.QName;

import org.eclipse.bpel.model.partnerlinktype.PartnerLinkType;
import org.eclipse.bpel.model.partnerlinktype.PartnerlinktypeFactory;
import org.eclipse.bpel.model.partnerlinktype.Role;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.wst.wsdl.Definition;
import org.eclipse.wst.wsdl.WSDLFactory;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Element;

import com.tibco.bx.xpdl2bpel.ConverterActivator;
import com.tibco.bx.xpdl2bpel.N2PEConstants;
import com.tibco.bx.xpdl2bpel.converter.ConversionException;
import com.tibco.bx.xpdl2bpel.util.BPELUtils;
import com.tibco.bx.xpdl2bpel.util.WSDLUtils;
import com.tibco.bx.xpdl2bpel.util.XPDLUtils;
import com.tibco.xpd.datamapper.scripts.DataMapperJavascriptGenerator;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.rest.datamapper.RestScriptDataMapperProvider;
import com.tibco.xpd.xpdExtension.RestServiceResource;
import com.tibco.xpd.xpdExtension.ScriptDataMapper;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.Participant;

/**
 * Converter for REST service activity.
 *
 * @author jarciuch
 * @since 10 Jun 2019
 */
public class ConvertRestService {

    public static org.eclipse.bpel.model.Activity convertRestTask(
    		ConverterContext context, 
    		final Activity xpdlActivity,
    		DirectionType directionType) throws ConversionException {
		
        org.eclipse.bpel.model.Invoke invoke = createInvoke(context, xpdlActivity);

        if ((directionType.ordinal() == DirectionType.IN) || (directionType.ordinal() == DirectionType.INOUT)) {
            //add the request script to invoke
    		createScript(xpdlActivity, invoke, "restRequestScript", MappingDirection.IN); //$NON-NLS-1$
        }
        if ((directionType.ordinal() == DirectionType.OUT) || (directionType.ordinal() == DirectionType.INOUT)) {
            //add the response script to invoke
    		createScript(xpdlActivity, invoke, "restResponseScript", MappingDirection.OUT); //$NON-NLS-1$
        }

        return invoke;
	}

	public static void createScript(final Activity xpdlActivity,
			org.eclipse.bpel.model.Activity activity,
			String scriptName,
			MappingDirection direction) {
		ScriptDataMapper sdm = new RestScriptDataMapperProvider(direction).getScriptDataMapper(xpdlActivity);
        String script = new DataMapperJavascriptGenerator().convertMappingsToJavascript(sdm);
        Element ele = BPELUtils.makeExtensionElement(activity, scriptName);
		CDATASection cdata = ele.getOwnerDocument().createCDATASection(script);
		ele.appendChild(cdata);
		ele.setAttribute("expressionLanguage", N2PEConstants.JSCRIPT_LANGUAGE);
	}

    /**
     * Creates invoke element for REST service activity.
     * 
     * @param context
     *            the converter context.
     * @param xpdlActivity
     *            the rest service xpdl activity.
     * @return invoke element for REST service activity.
     */
    private static org.eclipse.bpel.model.Invoke createInvoke(
            ConverterContext context, final Activity xpdlActivity) {
        org.eclipse.bpel.model.Invoke invoke =
                org.eclipse.bpel.model.BPELFactory.eINSTANCE.createInvoke();
        invoke.setName(xpdlActivity.getName());

        RestServiceResource restServiceResource =
                XPDLUtils.getRestServiceResource(xpdlActivity);
        if (restServiceResource != null) {
            // Set shared resource configuration attributes.
            BPELUtils.addExtensionAttribute(invoke,
                    XPDLUtils.ATTR_INVOKE_TYPE,
                    XPDLUtils.InvokeType.REST.getName());
            BPELUtils.addExtensionAttribute(invoke,
                    XPDLUtils.ATTR_SHARED_RESOURCE_TYPE,
                    XPDLUtils.SharedResourceType.HTTP_CLIENT.getName());
            
            String sharedResourceName = restServiceResource.getResourceName();
            if (sharedResourceName != null) {
                BPELUtils.addExtensionAttribute(invoke,
                        XPDLUtils.ATTR_SHARED_RESOURCE_NAME,
                        sharedResourceName);
            }
            String sharedResourceDesc = restServiceResource.getDescription();
            if (sharedResourceDesc != null && !sharedResourceDesc.trim().isEmpty()) {
                BPELUtils.addExtensionAttribute(invoke,
                        XPDLUtils.ATTR_SHARED_RESOURCE_DESC,
                        sharedResourceDesc);
            }
        }

        XPDLUtils.configureRetry(invoke, xpdlActivity);
        return invoke;
    }

}
