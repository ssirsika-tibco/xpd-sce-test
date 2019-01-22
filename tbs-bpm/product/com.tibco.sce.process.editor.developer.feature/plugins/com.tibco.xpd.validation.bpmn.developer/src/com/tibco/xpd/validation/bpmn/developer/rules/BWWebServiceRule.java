/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.validation.bpmn.developer.rules;

import java.util.List;
import java.util.Stack;
import java.util.StringTokenizer;

import javax.wsdl.Port;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.wst.wsdl.WSDLPackage;
import org.eclipse.wst.wsdl.binding.soap.SOAPAddress;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import com.tibco.xpd.implementer.script.Xpdl2WsdlUtil;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.indexer.IndexerItem;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.util.WsdlIndexerUtil;
import com.tibco.xpd.validation.xpdl2.rules.ActivityValidationRule;
import com.tibco.xpd.wsdl.WsdlServiceKey;
import com.tibco.xpd.xpdExtension.PortTypeOperation;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.Service;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TaskService;
import com.tibco.xpd.xpdl2.WebServiceOperation;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * @author nwilson
 */
public class BWWebServiceRule extends ActivityValidationRule {

    /** BW service identifier. */
    private static final String BW_SERVICE = "BW Service"; //$NON-NLS-1$

    /** Not a BW web service issue id. */
    private static final String NOT_BW = "bpmn.dev.bwServiceOnNonBwTask"; //$NON-NLS-1$

    /** Not a BW web service issue id. */
    private static final String BW = "bpmn.dev.nonBwServiceOnBwTask"; //$NON-NLS-1$

    /** No alias provided for the BW service * */
    private static final String NO_BW_ENDPOINT =
            "bpmn.dev.bwNoEndpointProvided"; //$NON-NLS-1$

    /**
     * @param activity
     *            The activity.
     * @see com.tibco.xpd.validation.xpdl2.rules.ActivityValidationRule#
     *      validate(com.tibco.xpd.xpdl2.Activity)
     */
    @Override
    public void validate(Activity activity) {
        WorkingCopy wc = WorkingCopyUtil.getWorkingCopyFor(activity);
        IProject project = wc.getEclipseResources().get(0).getProject();
        Implementation implementation = activity.getImplementation();
        if (implementation instanceof Task) {
            Task task = (Task) implementation;
            TaskService service = task.getTaskService();
            if (service != null) {
                WebServiceOperation wso = service.getWebServiceOperation();
                if (wso != null) {
                    String operation = wso.getOperationName();
                    String portType = null;
                    String portOperation = null;
                    PortTypeOperation portTypeOperation =
                            (PortTypeOperation) Xpdl2ModelUtil
                                    .getOtherElement(service,
                                            XpdExtensionPackage.eINSTANCE
                                                    .getDocumentRoot_PortTypeOperation());
                    if (portTypeOperation != null) {
                        portType = portTypeOperation.getPortTypeName();
                        portOperation = portTypeOperation.getOperationName();
                    }
                    Service svc = wso.getService();
                    if (svc != null) {
                        String port = svc.getPortName();
                        String svcName = svc.getServiceName();
                        WsdlServiceKey serviceKey =
                                new WsdlServiceKey(svcName, port, operation,
                                        portType, portOperation,
                                        Xpdl2WsdlUtil.getLocation(svc));
                        String alias = getAlias(project, serviceKey);
                        String type =
                                (String) Xpdl2ModelUtil
                                        .getOtherAttribute(service,
                                                XpdExtensionPackage.eINSTANCE
                                                        .getDocumentRoot_ImplementationType());
                        if (alias == null && BW_SERVICE.equals(type)) {
                            addIssue(BW, activity);
                        } else if (alias != null && !BW_SERVICE.equals(type)) {
                            /*
                             * XPD-1778: this issue is to be relaxed. need to
                             * allow bw operation to be selected for a web
                             * service implementation for BPM destination
                             */
                            // addIssue(NOT_BW, activity);
                        } else if (BW_SERVICE.equals(type)) {
                            // This is a BW Service so an alias should have been
                            // provided
                            String aliasInXpdl =
                                    (String) Xpdl2ModelUtil
                                            .getOtherAttribute(wso,
                                                    XpdExtensionPackage.eINSTANCE
                                                            .getDocumentRoot_Alias());
                            String participantNameForPassedId =
                                    TaskObjectUtil
                                            .getParticipantNameForPassedId(aliasInXpdl,
                                                    activity);
                            if ((participantNameForPassedId == null || participantNameForPassedId
                                    .trim().length() < 1)) {
                                addIssue(NO_BW_ENDPOINT, activity);
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * @param project
     *            The project.
     * @param key
     *            The WSDL service key.
     * @return The default alias.
     */
    private String getAlias(IProject project, WsdlServiceKey key) {
        String target = null;

        IndexerItem opItem =
                WsdlIndexerUtil.getOperationItem(project, key, true, true);
        if (opItem != null) {
            // Get the port item
            IndexerItem portItem = WsdlIndexerUtil.getContainer(opItem);
            if (portItem != null) {
                EObject portEO = WsdlIndexerUtil.resolve(portItem);
                if (portEO instanceof Port) {

                    Port port = (Port) portEO;
                    List<?> elems = port.getExtensibilityElements();

                    for (Object elem : elems) {
                        if (elem instanceof EObject) {
                            EObject eo = (EObject) elem;

                            Object eoValue =
                                    eo.eGet(WSDLPackage.eINSTANCE
                                            .getWSDLElement_Element());

                            if (eoValue instanceof Element) {
                                Element domElement = (Element) eoValue;
                                if ("targetAddress".equals(domElement //$NON-NLS-1$
                                        .getLocalName())) {
                                    if (domElement.getFirstChild() instanceof Text) {
                                        Text textContent =
                                                (Text) domElement
                                                        .getFirstChild();

                                        target = textContent.getData();
                                        break;
                                    }
                                }
                            }
                        }
                    }
                    if (WsdlIndexerUtil.isBW(portItem)) {
                        if (target == null) {
                            List extensibilityElements =
                                    port.getExtensibilityElements();
                            for (Object object : extensibilityElements) {
                                if (object instanceof SOAPAddress) {
                                    SOAPAddress address = (SOAPAddress) object;

                                    String locationURI =
                                            address.getLocationURI();
                                    target = getTargetAddress(locationURI);

                                }
                            }
                        }
                    }
                }
            }
        }
        return target;
    }

    /**
     * @param locationURI
     * @return
     */
    private String getTargetAddress(String locationURI) {
        StringTokenizer tokenizer = new StringTokenizer(locationURI, "/"); //$NON-NLS-1$
        Stack<String> tokens = new Stack<String>();
        while (tokenizer.hasMoreTokens()) {
            tokens.add(tokenizer.nextToken());
        }
        String lastToken = tokens.pop();
        if (lastToken.indexOf(".") != -1) { //$NON-NLS-1$
            return null;
        }
        return lastToken;

    }
}
