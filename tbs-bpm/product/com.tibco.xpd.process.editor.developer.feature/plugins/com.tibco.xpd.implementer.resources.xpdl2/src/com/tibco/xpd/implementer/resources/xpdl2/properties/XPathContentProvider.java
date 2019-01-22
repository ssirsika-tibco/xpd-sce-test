/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.implementer.resources.xpdl2.properties;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.wsdl.Input;
import javax.wsdl.Operation;
import javax.wsdl.Output;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.wst.wsdl.Part;
import org.eclipse.xsd.XSDComplexTypeDefinition;
import org.eclipse.xsd.XSDConcreteComponent;
import org.eclipse.xsd.XSDElementDeclaration;
import org.eclipse.xsd.XSDParticle;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.implementer.resources.xpdl2.errorEvents.CatchWsdlErrorEventUtil;
import com.tibco.xpd.implementer.resources.xpdl2.errorEvents.ThrowWsdlErrorEventUtil;
import com.tibco.xpd.implementer.resources.xpdl2.internal.Messages;
import com.tibco.xpd.implementer.resources.xpdl2.properties.filter.WsdlFilter.WsdlDirection;
import com.tibco.xpd.implementer.script.ActivityMessageProvider;
import com.tibco.xpd.implementer.script.ActivityMessageProviderFactory;
import com.tibco.xpd.implementer.script.ActivityMessageUtil;
import com.tibco.xpd.implementer.script.IWsdlPath;
import com.tibco.xpd.implementer.script.NamespacePrefixMapUtil;
import com.tibco.xpd.implementer.script.NamespacePrefixMapUtil.MappingStatus;
import com.tibco.xpd.implementer.script.PartProxy;
import com.tibco.xpd.implementer.script.WsdlPartPath;
import com.tibco.xpd.implementer.script.WsdlPartProblemPath;
import com.tibco.xpd.implementer.script.WsdlUtil;
import com.tibco.xpd.implementer.script.WsdlXsdRootPath;
import com.tibco.xpd.implementer.script.Xpdl2WsdlUtil;
import com.tibco.xpd.implementer.script.XsdPath;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.processeditor.xpdl2.properties.StandardMappingUtil;
import com.tibco.xpd.processeditor.xpdl2.properties.script.MultipleMappingCompositor;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptMappingCompositor;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptMappingCompositorFactory;
import com.tibco.xpd.processeditor.xpdl2.properties.script.SingleMappingCompositor;
import com.tibco.xpd.processeditor.xpdl2.util.DataMappingUtil;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.processwidget.adapters.EventTriggerType;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.util.WsdlIndexerUtil;
import com.tibco.xpd.wsdl.WsdlServiceKey;
import com.tibco.xpd.wsdlgen.WsdlgenPlugin;
import com.tibco.xpd.xpdExtension.PortTypeOperation;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.EndEvent;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.IntermediateEvent;
import com.tibco.xpd.xpdl2.Message;
import com.tibco.xpd.xpdl2.ModeType;
import com.tibco.xpd.xpdl2.OtherElementsContainer;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Service;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TaskService;
import com.tibco.xpd.xpdl2.WebServiceOperation;
import com.tibco.xpd.xpdl2.util.ReplyActivityUtil;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * @author rsomayaj
 * 
 */
public class XPathContentProvider implements ITreeContentProvider {
    /** The script grammar name for XsdPath mappings. */
    public static final String WSDL_PATH_SCRIPT = "WsdlPathScript"; //$NON-NLS-1$

    private MappingDirection direction;

    private DirectionType directionToService;

    private WsdlDirection wsdlDirection;

    /**
     * @param direction
     *            Direction of mapping.
     * @param directionToService
     *            Direction to service.
     * @param wsdlDirection
     *            Which message part (IN/OUT/FAULT) to use.
     */
    public XPathContentProvider(MappingDirection direction,
            DirectionType directionToService, WsdlDirection wsdlDirection) {
        this.direction = direction;
        this.directionToService = directionToService;
        this.wsdlDirection = wsdlDirection;
    }

    /**
     * Return activity which owns a Web Service Operation.
     * 
     * @param wso
     *            The web service operation.
     * @return The activity for the operation.
     */
    private Activity getActivity(WebServiceOperation wso) {
        // contained SOMEWHERE under activity
        EObject parent = wso.eContainer();
        while (parent != null) {
            if (parent instanceof Activity) {
                return (Activity) parent;
            }
            parent = parent.eContainer();
        }

        return null;
    }

    /**
     * Helper method to return correct adapter based on the input activity.
     * 
     * @param activity
     *            The activity to get the message provider for.
     * @return The message provider.
     */
    private ActivityMessageProvider getMessageProvider(Activity activity) {
        return ActivityMessageProviderFactory.INSTANCE
                .getMessageProvider(activity);
    }

    /**
     * @param parentElement
     *            The parent element.
     * @return A collection of children.
     * @see org.eclipse.emf.edit.provider.ItemProviderAdapter#getChildren(java.lang.Object)
     */
    @Override
    public Object[] getChildren(Object parentElement) {

        Collection<Object> childList = new ArrayList<Object>();
        WebServiceOperation wso;

        if (parentElement instanceof Activity) {
            Activity activity = (Activity) parentElement;

            /*
             * Before returning content, lets check for consistency of the
             * namespace prefix map. If it is not then we will add a dummy
             * problem part path to show user what's wrong (inconsistent
             * prefixes will almost certainly lead to broken mappings)
             */
            MappingStatus mappingStatus =
                    NamespacePrefixMapUtil.getMappingStatus(activity);

            /*
             * We only care about "can't access wsdl operation" and
             * "prefix's in WSDL are different from those last set in the prefix map"
             * - as these are the ones that will cuase mappings to appear broken
             * (and the mapping validation rule will not check for broken
             * mappings in these circumstances, so the mapping would appear
             * broken in UI with no problemmarkers on).
             */
            if (MappingStatus.WSDL_INACCESSIBLE.equals(mappingStatus)) {
                WsdlPartProblemPath wsdlPartProblemPath =
                        new WsdlPartProblemPath(
                                Messages.XPathContentProvider_WSDLNotAccessible_message,
                                WsdlDirection.IN.equals(wsdlDirection));
                childList.add(wsdlPartProblemPath);
                return childList.toArray();

            } else if (MappingStatus.INCONSISTENT.equals(mappingStatus)) {
                WsdlPartProblemPath wsdlPartProblemPath =
                        new WsdlPartProblemPath(
                                Messages.XPathContentProvider_NamespaceMapOutOfSynch_message,
                                WsdlDirection.IN.equals(wsdlDirection));
                childList.add(wsdlPartProblemPath);
                return childList.toArray();
            }

            try {
                // Map<String, String> wsdlNamespacePrefixMap =
                // NamespacePrefixMapUtil
                // .getWSDLNamespacePrefixMap(activity);
            } catch (Exception e) {
                WsdlPartProblemPath wsdlPartProblemPath =
                        new WsdlPartProblemPath(e.getMessage(),
                                WsdlDirection.IN.equals(wsdlDirection));

                childList.add(wsdlPartProblemPath);
                return childList.toArray();
            }

            ActivityMessageProvider messageProvider =
                    getMessageProvider(activity);
            if (messageProvider != null) {
                wso = messageProvider.getWebServiceOperation(activity);
                if (wso != null) {
                    Collection<WsdlPartPath> webServiceChildren =
                            getWebServiceChildren(wso, activity);
                    childList.addAll(webServiceChildren);
                    childList = expandArrays(childList, wso);
                }
            }
        } else if (parentElement instanceof IWsdlPath) {

            IWsdlPath wsdlPath = (IWsdlPath) parentElement;
            if (isProxyPart(wsdlPath.getPart())
                    && wsdlPath.getPart().getTypeDefinition() instanceof XSDComplexTypeDefinition) {
                childList
                        .add(Messages.ActivityMessageWsdlItemProvider_FileNeedsSaving_shortdesc);
            } else {
                wso = wsdlPath.getWebServiceOperation();
                List<IWsdlPath> partChildren = wsdlPath.getChildList();
                if (!partChildren.isEmpty()) {
                    childList.addAll(partChildren);
                    childList = expandArrays(childList, wso);
                }
            }
        }
        return childList.toArray();
    }

    /**
     * 
     * @param part
     * @return
     */
    private boolean isProxyPart(Part part) {
        return (part != null && part.eContainer() == null);
    }

    /**
     * Expands array items in a child list to ensure there is always a spare one
     * at the end of an array to map to.
     * 
     * @param childList
     *            The list of child elements to expand.
     * @param wso
     *            The web service operation.
     * @return The expanded list.
     */
    protected Collection<Object> expandArrays(Collection<Object> childList,
            WebServiceOperation wso) {

        Collection<Object> expanded = new ArrayList<Object>();
        List<DataMapping> mappings = getDataMappings(wso);
        for (Object next : childList) {
            expanded.add(next);
            if (next instanceof XsdPath) {
                XsdPath path = (XsdPath) next;
                XSDConcreteComponent component = path.getComponent();
                if (component instanceof XSDParticle) {
                    XSDParticle particle = (XSDParticle) component;
                    if (!(particle.getContent() instanceof XSDElementDeclaration)) {
                        expanded.addAll(expandParticle(wso,
                                mappings,
                                path,
                                particle));
                    }
                } else if (component instanceof XSDElementDeclaration) {
                    XSDConcreteComponent container = component.getContainer();
                    if (container instanceof XSDParticle) {
                        XSDParticle particle = (XSDParticle) container;
                        expanded.addAll(expandParticle(wso,
                                mappings,
                                path,
                                particle));
                    }
                }
            }
        }
        return expanded;
    }

    private Collection<Object> expandParticle(WebServiceOperation wso,
            List<DataMapping> mappings, XsdPath path, XSDParticle particle) {
        Collection<Object> expanded = new ArrayList<Object>();
        /*
         * XPD-726: Whether or not an XSDParticle (group:sequence or
         * group:choice) should be expandable as an array should be based on the
         * presence of MaxOccurs and MaxOccurs being not 1 (in which case it's >
         * 1 or unbounded).
         * 
         * It definitely has nothing to do with MinOccurs!
         */
        int max = particle.getMaxOccurs();
        if (max == -1 || max > 1) {
            XsdPath pathCopy = path.getCopy();
            Activity activity = Xpdl2ModelUtil.getParentActivity(wso);
            Integer count =
                    getMaxMappedArrayIndex(activity, pathCopy, mappings);
            path.setArrayIndex(0);
            if (count != null) {
                int toAdd = count + 1;
                if (max != -1) {
                    toAdd = Math.min(toAdd, max - 1);
                }
                for (int i = 1; i <= toAdd; i++) {
                    XsdPath copy = path.getCopy();
                    copy.setArrayIndex(i);
                    expanded.add(copy);
                }
            }
        }
        return expanded;
    }

    /**
     * @param activity
     *            The activity.
     * @param path
     *            The path.
     * @param mappings
     *            a list of mappings.
     * @return The max mapped array index.
     */
    private Integer getMaxMappedArrayIndex(Activity activity, XsdPath path,
            List<DataMapping> mappings) {
        List<Integer> arrayIndices = new ArrayList<Integer>();
        String matchPath = path.getPath();
        if (MappingDirection.IN.equals(direction)) {
            for (DataMapping mapping : mappings) {
                if (DirectionType.IN_LITERAL.equals(mapping.getDirection())) {
                    String target = DataMappingUtil.getTarget(mapping);
                    String script = DataMappingUtil.getScript(mapping);
                    String grammar = DataMappingUtil.getGrammar(mapping);
                    if (target != null && script != null && grammar != null) {
                        ScriptMappingCompositorFactory factory =
                                ScriptMappingCompositorFactory
                                        .getCompositorFactory(grammar,
                                                DirectionType.IN_LITERAL);
                        if (factory != null) {
                            ScriptMappingCompositor compositor =
                                    factory.getCompositor(activity,
                                            target,
                                            script);
                            if (compositor instanceof MultipleMappingCompositor) {
                                MultipleMappingCompositor multiple =
                                        (MultipleMappingCompositor) compositor;
                                Collection<Object> paths =
                                        multiple.getTargetItems();
                                for (Object wsdlPath : paths) {
                                    if (wsdlPath != null) {
                                        arrayIndices
                                                .add(getMatchingMappedIndex(ActivityMessageUtil
                                                        .getPath(wsdlPath),
                                                        matchPath));
                                    }
                                }
                            } else if (compositor instanceof SingleMappingCompositor) {
                                IWsdlPath wsdlPath =
                                        WsdlUtil.resolveParameter(activity,
                                                target,
                                                true);
                                if (wsdlPath != null) {
                                    arrayIndices
                                            .add(getMatchingMappedIndex(ActivityMessageUtil
                                                    .getPath(wsdlPath),
                                                    matchPath));
                                }
                            }
                        }
                    }
                } else if (DirectionType.OUT_LITERAL.equals(directionToService)) {
                    arrayIndices.addAll(getOutMappingArrayIndices(mapping,
                            activity,
                            matchPath));
                }
            }
        } else {
            for (DataMapping mapping : mappings) {
                if (DirectionType.OUT_LITERAL.equals(mapping.getDirection())) {
                    arrayIndices.addAll(getOutMappingArrayIndices(mapping,
                            activity,
                            matchPath));
                }
            }
        }
        return arrayIndices.isEmpty() ? -1 : Collections.max(arrayIndices);
    }

    private List<Integer> getOutMappingArrayIndices(DataMapping mapping,
            Activity activity, String matchPath) {
        List<Integer> arrayIndices = new ArrayList<Integer>();
        String target = DataMappingUtil.getTarget(mapping);
        String script = DataMappingUtil.getScript(mapping);
        String grammar = DataMappingUtil.getGrammar(mapping);
        boolean isScripted = DataMappingUtil.isScripted(mapping);
        if (!isScripted) {
            ScriptMappingCompositorFactory factory =
                    ScriptMappingCompositorFactory
                            .getCompositorFactory(grammar,
                                    DirectionType.OUT_LITERAL);
            if (factory != null) {
                ScriptMappingCompositor compositor =
                        factory.getCompositor(activity, target, script);
                if (compositor instanceof SingleMappingCompositor) {
                    SingleMappingCompositor scriptMapping =
                            (SingleMappingCompositor) compositor;
                    Collection<Object> paths =
                            scriptMapping.getSourceItems(MappingDirection.IN
                                    .equals(direction));
                    for (Object wsdlPath : paths) {
                        if (wsdlPath != null) {
                            arrayIndices
                                    .add(getMatchingMappedIndex(ActivityMessageUtil
                                            .getPath(wsdlPath),
                                            matchPath));
                        }
                    }
                }
            }
        }
        return arrayIndices;
    }

    /**
     * @param mappingPath
     *            The mapping path.
     * @param matchPath
     *            The path to match.
     * @return The index of the matchPath in the mappingPath or -1 if not found.
     */
    private int getMatchingMappedIndex(String mappingPath, String matchPath) {
        int index = -1;
        if (mappingPath.startsWith(matchPath)) {
            int indexOfIndex = mappingPath.indexOf('{', matchPath.length());
            if (indexOfIndex >= 0) {
                index =
                        Integer.parseInt(mappingPath
                                .substring(indexOfIndex + 1,
                                        mappingPath.indexOf('}',
                                                matchPath.length())));
            }
        }
        return index;
    }

    /**
     * @param wso
     *            The web service operation.
     * @return A list of data mappings.
     */
    private List<DataMapping> getDataMappings(WebServiceOperation wso) {
        Activity activity = getActivity(wso);
        ActivityMessageProvider messageProvider = getMessageProvider(activity);
        Message messageIn = messageProvider.getMessageIn(activity);
        Message messageOut = messageProvider.getMessageOut(activity);
        List<DataMapping> result = new ArrayList<DataMapping>();
        if (messageIn != null) {
            result.addAll(Xpdl2ModelUtil.getMessageMappings(messageIn));
        }
        if (messageOut != null) {
            result.addAll(Xpdl2ModelUtil.getMessageMappings(messageOut));
        }
        return result;
    }

    /**
     * @param element
     *            The element to get the parent for.
     * @return The parent object, or null if the parent could not be obtained.
     * @see org.eclipse.jface.viewers.ITreeContentProvider#
     *      getParent(java.lang.Object)
     */
    @Override
    public Object getParent(Object element) {
        Object parent = null;
        if (element instanceof WsdlXsdRootPath) {
            parent = ((WsdlXsdRootPath) element).getWsdlPartPath();
        } else if (element instanceof IWsdlPath) {
            parent = ((IWsdlPath) element).getParent();
        }
        return parent;
    }

    /**
     * @param parentElement
     *            The element to check.
     * @return true if the element has children, otherwise false.
     * @see org.eclipse.jface.viewers.ITreeContentProvider#hasChildren(java.lang.Object)
     */
    @Override
    public boolean hasChildren(Object parentElement) {
        boolean hasChildren = false;
        if (parentElement instanceof Activity) {
            Activity activity = (Activity) parentElement;
            ActivityMessageProvider messageProvider =
                    getMessageProvider(activity);
            WebServiceOperation wso =
                    messageProvider.getWebServiceOperation(activity);
            hasChildren = hasWebServiceChildren(wso);
        } else if (parentElement instanceof IWsdlPath) {
            IWsdlPath wsdlPath = (IWsdlPath) parentElement;

            hasChildren = wsdlPath.hasChildren();
            if (!hasChildren
                    && isProxyPart(wsdlPath.getPart())
                    && wsdlPath.getPart().getTypeDefinition() instanceof XSDComplexTypeDefinition) {
                hasChildren = true;
            }
        }
        return hasChildren;
    }

    /**
     * @param webService
     *            The web service to check for children.
     * @return true if the web service has children, otherwise false.
     */
    private boolean hasWebServiceChildren(WebServiceOperation webService) {
        boolean hasChildren = false;
        WorkingCopy wc = WorkingCopyUtil.getWorkingCopyFor(webService);
        if (wc != null) {
            String operation = webService.getOperationName();
            String port = webService.getService().getPortName();
            String service = webService.getService().getServiceName();
            String portType = null;// webService.getPortTypeName();
            String portOperation = null;// webService.getPortOperationName();
            String filePath =
                    Xpdl2WsdlUtil.getLocation(webService.getService());

            Implementation implementation =
                    getActivity(webService).getImplementation();
            TaskService taskService = null;
            if (implementation instanceof Task) {
                Task task = (Task) implementation;
                taskService = task.getTaskService();
                if (taskService != null) {
                    PortTypeOperation portTypeOperation =
                            (PortTypeOperation) Xpdl2ModelUtil
                                    .getOtherElement(taskService,
                                            XpdExtensionPackage.eINSTANCE
                                                    .getDocumentRoot_PortTypeOperation());
                    if (portTypeOperation != null) {
                        portType = portTypeOperation.getPortTypeName();
                        portOperation = portTypeOperation.getOperationName();
                        if (filePath == null) {
                            filePath =
                                    Xpdl2WsdlUtil
                                            .getLocation(portTypeOperation);
                        }
                    }
                }
            }

            WsdlServiceKey key =
                    new WsdlServiceKey(service, port, operation, portType,
                            portOperation, filePath);
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

    /**
     * @param webService
     *            The web service to get the children for.
     * @param activity
     * @return A collection of child Part objects.
     */
    private Collection<WsdlPartPath> getWebServiceChildren(
            WebServiceOperation webService, Activity activity) {
        boolean isPortTypeOpGenerated =
                Xpdl2ModelUtil.isGeneratedRequestActivity(activity);

        boolean isReplyImmediateStart =
                ReplyActivityUtil.isStartMessageWithReplyImmediate(activity);

        if (!isPortTypeOpGenerated
                && ReplyActivityUtil.isReplyActivity(activity)) {
            Activity requestActivityForReplyActivity =
                    ReplyActivityUtil
                            .getRequestActivityForReplyActivity(activity);
            if (requestActivityForReplyActivity != null) {
                isPortTypeOpGenerated =
                        Xpdl2ModelUtil
                                .isGeneratedRequestActivity(requestActivityForReplyActivity);
            }
        }
        Collection<WsdlPartPath> partPaths = new ArrayList<WsdlPartPath>();
        WorkingCopy wc = WorkingCopyUtil.getWorkingCopyFor(webService);
        if (wc != null) {
            String operation = webService.getOperationName();
            String portType = null;// webService.getPortTypeName();
            String portOperation = null;// webService.getPortOperationName();

            EObject parent = webService.eContainer();
            if (parent instanceof OtherElementsContainer) {
                OtherElementsContainer container =
                        (OtherElementsContainer) parent;
                PortTypeOperation portTypeOperation =
                        (PortTypeOperation) Xpdl2ModelUtil
                                .getOtherElement(container,
                                        XpdExtensionPackage.eINSTANCE
                                                .getDocumentRoot_PortTypeOperation());
                if (portTypeOperation != null) {
                    portType = portTypeOperation.getPortTypeName();
                    portOperation = portTypeOperation.getOperationName();
                }
            }

            Service svc = webService.getService();
            if (svc != null) {
                String port = webService.getService().getPortName();
                String service = webService.getService().getServiceName();
                WsdlServiceKey key =
                        new WsdlServiceKey(service, port, operation, portType,
                                portOperation,
                                Xpdl2WsdlUtil.getLocation(webService
                                        .getService()));
                IResource resource = wc.getEclipseResources().get(0);
                IProject project = resource.getProject();
                Operation op =
                        WsdlIndexerUtil.getOperation(project, key, true, true);

                Collection<?> inputParts = new ArrayList<Object>();
                Collection<?> outputParts = new ArrayList<Object>();

                if (op != null) {
                    if (isCatchFaultRelatedActivity(activity)) {
                        List<WsdlPartPath> wsdlFaultPartPaths =
                                CatchWsdlErrorEventUtil
                                        .getCaughtWsdlFaultParams(activity);
                        partPaths.addAll(wsdlFaultPartPaths);

                    } else if (isThrowFaultRelatedActivity(activity)) {
                        List<WsdlPartPath> wsdlFaultPartPaths =
                                ThrowWsdlErrorEventUtil
                                        .getWsdlFaultParams(activity);
                        partPaths.addAll(wsdlFaultPartPaths);

                    } else {
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
                }
                if (!isPortTypeOpGenerated
                        || (isPortTypeOpGenerated && !isWsdlFileDerived(Xpdl2ModelUtil
                                .getPackage(activity)))) {
                    if (WsdlDirection.IN.equals(wsdlDirection)) {
                        for (Object inputPart : inputParts) {
                            if (inputPart instanceof Part) {
                                WsdlPartPath path =
                                        new WsdlPartPath(webService,
                                                (Part) inputPart, true, false);
                                partPaths.add(path);
                            }
                        }
                    } else if (WsdlDirection.OUT.equals(wsdlDirection)) {
                        for (Object outputPart : outputParts) {
                            if (outputPart instanceof Part) {
                                WsdlPartPath path =
                                        new WsdlPartPath(webService,
                                                (Part) outputPart, false, true);
                                partPaths.add(path);
                            }
                        }
                    }
                } else if (isPortTypeOpGenerated
                        && isWsdlFileDerived(Xpdl2ModelUtil
                                .getPackage(activity))) {
                    List<FormalParameter> associatedFormalParameters =
                            ProcessInterfaceUtil
                                    .getAssociatedFormalParameters(activity);

                    for (FormalParameter formalParam : associatedFormalParameters) {
                        if (WsdlDirection.IN.equals(wsdlDirection)
                                && (ModeType.IN_LITERAL.equals(formalParam
                                        .getMode()) || ModeType.INOUT_LITERAL
                                        .equals(formalParam.getMode()))) {
                            // find the part corresponding to the formal param
                            // name
                            Part part =
                                    getPartInListForParam(inputParts,
                                            formalParam.getName());
                            if (part == null) {
                                part = PartProxy.getPart(formalParam);

                            }
                            WsdlPartPath path =
                                    new WsdlPartPath(webService, part, true,
                                            false);
                            partPaths.add(path);
                        }

                        /*
                         * Don't do parts for output parameters if this is
                         * generated reply immediate Start event (because that
                         * just has special ProcessId.
                         */
                        if (!isReplyImmediateStart) {
                            if (WsdlDirection.OUT.equals(wsdlDirection)
                                    && (ModeType.OUT_LITERAL.equals(formalParam
                                            .getMode()) || ModeType.INOUT_LITERAL
                                            .equals(formalParam.getMode()))) {
                                Part part =
                                        getPartInListForParam(outputParts,
                                                formalParam.getName());
                                if (part == null) {
                                    part = PartProxy.getPart(formalParam);

                                }
                                WsdlPartPath path =
                                        new WsdlPartPath(webService, part,
                                                false, true);
                                partPaths.add(path);
                            }
                        }
                    }

                    /*
                     * Return special ProcessId part for generated
                     * reply-immediate start event
                     */
                    if (isReplyImmediateStart) {
                        Part part =
                                getPartInListForParam(outputParts,
                                        StandardMappingUtil.REPLY_IMMEDIATE_PROCESS_PART_NAME);
                        if (part == null) {
                            part =
                                    PartProxy
                                            .getPart(StandardMappingUtil.REPLY_IMMEDIATE_PROCESS_ID_FORMALPARAMETER);
                            part.setName(StandardMappingUtil.REPLY_IMMEDIATE_PROCESS_PART_NAME);
                        }
                        WsdlPartPath path =
                                new WsdlPartPath(webService, part, false, true);
                        partPaths.add(path);
                    }
                }
            }
        }
        return partPaths;
    }

    /**
     * @param activity
     * @return true if the is a catch error event
     */
    private boolean isCatchFaultRelatedActivity(Activity activity) {
        if (activity.getEvent() instanceof IntermediateEvent) {
            EventTriggerType eventTriggerType =
                    EventObjectUtil.getEventTriggerType(activity);
            if (EventTriggerType.EVENT_ERROR_LITERAL.equals(eventTriggerType)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param activity
     * @return true if the is a throw error event
     */
    private boolean isThrowFaultRelatedActivity(Activity activity) {
        if (activity.getEvent() instanceof EndEvent) {
            EventTriggerType eventTriggerType =
                    EventObjectUtil.getEventTriggerType(activity);
            if (EventTriggerType.EVENT_ERROR_LITERAL.equals(eventTriggerType)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param process
     * @return
     */
    private boolean isWsdlFileDerived(Package pkg) {
        IFile xpdlFile = WorkingCopyUtil.getFile(pkg);
        if (xpdlFile != null) {
            String wsdlFileName = getWsdlFileName(xpdlFile.getName());
            IProject project = xpdlFile.getProject();
            // ProjectConfig projectConfig =
            // XpdResourcesPlugin.getDefault().getProjectConfig(project);
            // SpecialFolders specialFolders =
            // projectConfig.getSpecialFolders();
            IFolder folder =
                    project.getFolder(WsdlgenPlugin.GENERATED_SERVICES_FOLDER);
            if (folder.exists()) {
                IFile file = folder.getFile(wsdlFileName);
                if (file.exists() && !(file.isDerived())) {
                    return false;
                }
            }

        }

        return true;
    }

    /**
     * 
     * @param xpdlFileName
     * @return
     */
    public String getWsdlFileName(String xpdlFileName) {
        IPath xpdlWithoutExtn = new Path(xpdlFileName).removeFileExtension();
        String wsdlFileName =
                xpdlWithoutExtn
                        .addFileExtension(Messages.ActivityMessageWsdlItemProvider_2)
                        .toPortableString();

        return wsdlFileName;
    }

    /**
     * @param inputParts
     * @param name
     * @return
     */
    private Part getPartInListForParam(Collection<?> inputParts, String name) {
        for (Object inputPart : inputParts) {
            if (inputPart instanceof Part) {
                if (((Part) inputPart).getName().equals(name)) {
                    return (Part) inputPart;
                }
            }
        }
        return null;
    }

    /**
     * @param inputElement
     *            The element to get children for.
     * @return a collection of child elements.
     * @see org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java.lang.Object)
     */
    @Override
    public Object[] getElements(Object inputElement) {
        return getChildren(inputElement);

    }

    /**
     * 
     * @see org.eclipse.jface.viewers.IContentProvider#dispose()
     */
    @Override
    public void dispose() {
    }

    /**
     * @param viewer
     * @param oldInput
     * @param newInput
     * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer,
     *      java.lang.Object, java.lang.Object)
     */
    @Override
    public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
    }
}
