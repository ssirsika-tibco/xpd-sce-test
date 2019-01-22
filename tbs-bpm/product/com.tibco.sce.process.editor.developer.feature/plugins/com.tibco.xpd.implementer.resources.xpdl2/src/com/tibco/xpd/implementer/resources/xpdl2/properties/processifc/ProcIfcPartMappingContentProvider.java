/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.implementer.resources.xpdl2.properties.processifc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.wsdl.Input;
import javax.wsdl.Operation;
import javax.wsdl.Output;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.wst.wsdl.Part;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.implementer.script.IWsdlPath;
import com.tibco.xpd.implementer.script.PartProxy;
import com.tibco.xpd.implementer.script.WsdlPartPath;
import com.tibco.xpd.implementer.script.WsdlUtil;
import com.tibco.xpd.implementer.script.Xpdl2WsdlUtil;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptUtil;
import com.tibco.xpd.processeditor.xpdl2.util.DataMappingUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.util.WsdlIndexerUtil;
import com.tibco.xpd.wsdl.WsdlServiceKey;
import com.tibco.xpd.xpdExtension.AssociatedParameter;
import com.tibco.xpd.xpdExtension.InterfaceMethod;
import com.tibco.xpd.xpdExtension.PortTypeOperation;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.Message;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.TriggerResultMessage;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * 
 * @author rsomayaj
 * 
 */
public class ProcIfcPartMappingContentProvider implements ITreeContentProvider {

    private final DirectionType directionType;

    /**
     * @param inLiteral
     */
    public ProcIfcPartMappingContentProvider(DirectionType directionType) {
        this.directionType = directionType;

    }

    public Object[] getChildren(Object parentElement) {
        Collection<Object> childList = new ArrayList<Object>();
        if (parentElement instanceof Activity) {
            Activity activity = (Activity) parentElement;
            PortTypeOperation portTypeOperation =
                    getPortTypeOperation(activity);
            if (portTypeOperation != null) {
                Collection<WsdlPartPath> webServiceChildren =
                        getWebServiceChildren(portTypeOperation, activity);
                childList.addAll(webServiceChildren);

            }
        } else if (parentElement instanceof IWsdlPath) {
            IWsdlPath wsdlPath = (IWsdlPath) parentElement;
            childList.addAll(wsdlPath.getChildList());
        }
        return childList.toArray();
    }

    public Object getParent(Object element) {
        return null;
    }

    public boolean hasChildren(Object parentElement) {
        boolean hasChildren = false;
        if (parentElement instanceof Activity) {
            Activity activity = (Activity) parentElement;
            PortTypeOperation portTypeOp = getPortTypeOperation(activity);
            if (portTypeOp != null) {
                hasChildren = hasWebServiceChildren(portTypeOp);
            }
        } else if (parentElement instanceof IWsdlPath) {
            IWsdlPath wsdlPath = (IWsdlPath) parentElement;
            hasChildren = wsdlPath.hasChildren();
        }
        return hasChildren;
    }

    /**
     * @param activity
     * @return
     */
    private PortTypeOperation getPortTypeOperation(Activity activity) {
        InterfaceMethod implementedMethod =
                ProcessInterfaceUtil.getImplementedMethod(activity);
        if (implementedMethod != null) {
            TriggerResultMessage trm =
                    implementedMethod.getTriggerResultMessage();
            if (trm != null) {
                Object otherElement =
                        Xpdl2ModelUtil.getOtherElement(trm,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_PortTypeOperation());
                if (otherElement instanceof PortTypeOperation) {
                    return (PortTypeOperation) otherElement;
                }
            }
        }
        return null;
    }

    /**
     * @param webService
     *            The web service to check for children.
     * @return true if the web service has children, otherwise false.
     */
    private boolean hasWebServiceChildren(PortTypeOperation portTypeOperation) {
        boolean hasChildren = false;
        WorkingCopy wc = WorkingCopyUtil.getWorkingCopyFor(portTypeOperation);
        if (wc != null) {
            String portType = portTypeOperation.getPortTypeName();
            String portOperation = portTypeOperation.getOperationName();

            portType = portTypeOperation.getPortTypeName();
            portOperation = portTypeOperation.getOperationName();

            WsdlServiceKey key =
                    new WsdlServiceKey(null, null, null, portType,
                            portOperation, Xpdl2WsdlUtil
                                    .getLocation(portTypeOperation));
            IResource resource = wc.getEclipseResources().get(0);
            IProject project = resource.getProject();

            Operation op =
                    WsdlIndexerUtil.getOperation(project, key, true, true);
            if (op != null) {
                Input input = op.getInput();
                if (input != null) {
                    javax.wsdl.Message message = input.getMessage();
                    if (message != null) {
                        hasChildren = message.getParts().size() > 0;
                    }
                }
            }
        }
        return hasChildren;
    }

    public Object[] getElements(Object inputElement) {
        List parameterList = new ArrayList<AssociatedParameter>();

        if (inputElement instanceof InterfaceMethod) {
            InterfaceMethod interfaceMethod = (InterfaceMethod) inputElement;
            List<IWsdlPath> paths = new ArrayList<IWsdlPath>();
            TriggerResultMessage triggerResultMessage =
                    interfaceMethod.getTriggerResultMessage();
            if (triggerResultMessage != null) {
                Message message = triggerResultMessage.getMessage();
                if (message != null) {
                    List<DataMapping> dataMappings = message.getDataMappings();
                    for (DataMapping dataMapping : dataMappings) {
                        String script = DataMappingUtil.getScript(dataMapping);
                        IWsdlPath resolvedParam = null;
                        if (DirectionType.OUT_LITERAL.equals(directionType)) {
                            if (DirectionType.OUT_LITERAL.equals(dataMapping
                                    .getDirection())) {
                                resolvedParam =
                                        WsdlUtil
                                                .resolveParameter(interfaceMethod,
                                                        script,
                                                        true);
                                if (resolvedParam == null) {
                                    ProcessRelevantData prd =
                                            ConceptUtil
                                                    .resolveParameter(interfaceMethod,
                                                            dataMapping
                                                                    .getActual()
                                                                    .getText());
                                    if (prd instanceof FormalParameter) {
                                        Part part =
                                                PartProxy
                                                        .getPart((FormalParameter) prd);
                                        Object otherElement =
                                                Xpdl2ModelUtil
                                                        .getOtherElement(triggerResultMessage,
                                                                XpdExtensionPackage.eINSTANCE
                                                                        .getDocumentRoot_PortTypeOperation());
                                        if (otherElement instanceof PortTypeOperation) {
                                            resolvedParam =
                                                    new WsdlPartPath(
                                                            (PortTypeOperation) otherElement,
                                                            part, true, false);

                                        }
                                    }

                                }
                                if (resolvedParam != null) {
                                    paths.add(resolvedParam);
                                }
                            }
                        }
                        if (DirectionType.IN_LITERAL.equals(directionType)) {
                            if (DirectionType.IN_LITERAL.equals(dataMapping
                                    .getDirection())) {
                                resolvedParam =
                                        WsdlUtil
                                                .resolveParameter(interfaceMethod,
                                                        script,
                                                        false);
                                if (resolvedParam == null) {

                                    ProcessRelevantData prd =
                                            ConceptUtil
                                                    .resolveParameter(interfaceMethod,
                                                            dataMapping
                                                                    .getActual()
                                                                    .getText());
                                    if (prd instanceof FormalParameter) {
                                        Part part =
                                                PartProxy
                                                        .getPart((FormalParameter) prd);
                                        Object otherElement =
                                                Xpdl2ModelUtil
                                                        .getOtherElement(triggerResultMessage,
                                                                XpdExtensionPackage.eINSTANCE
                                                                        .getDocumentRoot_PortTypeOperation());
                                        if (otherElement instanceof PortTypeOperation) {
                                            resolvedParam =
                                                    new WsdlPartPath(
                                                            (PortTypeOperation) otherElement,
                                                            part, false, true);
                                        }
                                    }

                                }
                                if (resolvedParam != null) {
                                    paths.add(resolvedParam);
                                }
                            }
                        }

                    }
                }

            }
            return paths.toArray();
        }
        return getPartList(inputElement, parameterList).toArray();
    }

    private List<Part> getPartList(Object inputElement, List parameterList) {
        List<Part> partList = new ArrayList<Part>();
        for (Object o : parameterList) {
            if (o instanceof AssociatedParameter) {
                ProcessRelevantData procRelevantData =
                        ProcessInterfaceUtil
                                .getProcessRelevantDataFromAssociatedParam((AssociatedParameter) o);
                if (procRelevantData instanceof FormalParameter) {
                    Part part =
                            PartProxy
                                    .getPart((FormalParameter) procRelevantData);
                    partList.add(part);

                }
            } else if (o instanceof FormalParameter) {
                Part part = PartProxy.getPart(((FormalParameter) o));
                partList.add(part);
            }
        }
        return partList;
    }

    public void dispose() {

    }

    public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {

    }

    /**
     * @param webService
     *            The web service to get the children for.
     * @param activity
     * @return A collection of child Part objects.
     */
    private Collection<WsdlPartPath> getWebServiceChildren(
            PortTypeOperation portTypeOp, Activity activity) {

        Collection<WsdlPartPath> partPaths = new ArrayList<WsdlPartPath>();
        WorkingCopy wc = WorkingCopyUtil.getWorkingCopyFor(portTypeOp);
        if (wc != null) {
            String portType = portTypeOp.getPortTypeName();
            String portOperation = portTypeOp.getOperationName();

            WsdlServiceKey key =
                    new WsdlServiceKey(null, null, null, portType,
                            portOperation, Xpdl2WsdlUtil
                                    .getLocation(portTypeOp));
            IResource resource = wc.getEclipseResources().get(0);
            IProject project = resource.getProject();
            Operation op =
                    WsdlIndexerUtil.getOperation(project, key, true, true);

            Collection<?> inputParts = new ArrayList<Object>();
            Collection<?> outputParts = new ArrayList<Object>();
            if (op != null) {
                Input input = op.getInput();
                if (input != null) {
                    javax.wsdl.Message message = input.getMessage();
                    if (message != null) {
                        inputParts = message.getOrderedParts(null);
                    }
                }
                Output output = op.getOutput();
                if (output != null) {
                    javax.wsdl.Message message = output.getMessage();
                    if (message != null) {
                        outputParts = message.getOrderedParts(null);
                    }
                }
            }
            for (Object inputPart : inputParts) {
                if (inputPart instanceof Part) {
                    WsdlPartPath path =
                            new WsdlPartPath(portTypeOp, (Part) inputPart,
                                    true, false);
                    partPaths.add(path);
                }
            }
            for (Object outputPart : outputParts) {
                if (outputPart instanceof Part) {
                    WsdlPartPath path =
                            new WsdlPartPath(portTypeOp, (Part) outputPart,
                                    false, true);
                    partPaths.add(path);
                }
            }

        }
        return partPaths;
    }

}
