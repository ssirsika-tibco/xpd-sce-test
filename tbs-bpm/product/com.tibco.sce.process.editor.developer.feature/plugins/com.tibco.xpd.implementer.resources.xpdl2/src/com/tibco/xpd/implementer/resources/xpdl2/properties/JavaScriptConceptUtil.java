/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.implementer.resources.xpdl2.properties;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.wsdl.Input;
import javax.wsdl.Operation;
import javax.wsdl.Output;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.wst.wsdl.Definition;
import org.eclipse.wst.wsdl.Fault;
import org.eclipse.wst.wsdl.Message;
import org.eclipse.wst.wsdl.Part;
import org.eclipse.wst.wsdl.util.WSDLConstants;
import org.eclipse.xsd.XSDElementDeclaration;
import org.eclipse.xsd.XSDMaxLengthFacet;
import org.eclipse.xsd.XSDNamedComponent;
import org.eclipse.xsd.XSDSimpleTypeDefinition;
import org.eclipse.xsd.XSDTypeDefinition;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesPlugin;
import com.tibco.xpd.analyst.resources.xpdl2.utils.BasicTypeConverterFactory;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.bom.types.PrimitivesUtil;
import com.tibco.xpd.bom.wsdltransform.api.WSDLTransformUtil;
import com.tibco.xpd.bom.xsdtransform.api.XSDUtil;
import com.tibco.xpd.implementer.resources.xpdl2.errorEvents.CatchWsdlErrorEventUtil;
import com.tibco.xpd.implementer.resources.xpdl2.errorEvents.ThrowWsdlErrorEventUtil;
import com.tibco.xpd.implementer.resources.xpdl2.internal.Messages;
import com.tibco.xpd.implementer.resources.xpdl2.properties.filter.WsdlFilter.WsdlDirection;
import com.tibco.xpd.implementer.script.ActivityMessageProvider;
import com.tibco.xpd.implementer.script.ActivityMessageProviderFactory;
import com.tibco.xpd.implementer.script.Xpdl2WsdlUtil;
import com.tibco.xpd.processeditor.xpdl2.properties.ChoiceConceptPath;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptUtil;
import com.tibco.xpd.processeditor.xpdl2.properties.StandardMappingUtil;
import com.tibco.xpd.processeditor.xpdl2.properties.WsdlPartConceptPath;
import com.tibco.xpd.processeditor.xpdl2.properties.WsdlPartConceptPath.PartMode;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.processwidget.adapters.EventTriggerType;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.indexer.IndexerItem;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.projectconfig.SpecialFolders;
import com.tibco.xpd.resources.util.ProjectUtil;
import com.tibco.xpd.resources.util.SpecialFolderUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.ui.complexdatatype.ComplexDataTypeExtPointHelper;
import com.tibco.xpd.ui.complexdatatype.ComplexDataTypeReference;
import com.tibco.xpd.ui.complexdatatype.ComplexDataTypesMergedInfo;
import com.tibco.xpd.util.WsdlIndexerUtil;
import com.tibco.xpd.wsdl.WsdlServiceKey;
import com.tibco.xpd.wsdltobom.indexer.WsdlBomIndexerProvider;
import com.tibco.xpd.wsdltobom.indexer.WsdlBomIndexerUtil;
import com.tibco.xpd.xpdExtension.PortTypeOperation;
import com.tibco.xpd.xpdExtension.XpdExtAttribute;
import com.tibco.xpd.xpdExtension.XpdExtAttributes;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.BasicType;
import com.tibco.xpd.xpdl2.EndEvent;
import com.tibco.xpd.xpdl2.EndPoint;
import com.tibco.xpd.xpdl2.ExternalReference;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.IntermediateEvent;
import com.tibco.xpd.xpdl2.Length;
import com.tibco.xpd.xpdl2.ModeType;
import com.tibco.xpd.xpdl2.OtherElementsContainer;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.Service;
import com.tibco.xpd.xpdl2.WebServiceOperation;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.edit.ui.ComplexDataUIUtil;
import com.tibco.xpd.xpdl2.extension.EMFSearchUtil;
import com.tibco.xpd.xpdl2.util.ReplyActivityUtil;
import com.tibco.xpd.xpdl2.util.ThrowErrorEventUtil;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * This is a similar to the ConceptUtil except that it resolves the concept to a
 * Concept Class of the WSDL equivalent BOM class.
 * 
 * @author rsomayaj
 * 
 */
public class JavaScriptConceptUtil {

    /**
     * XpdExtAttribute name for formal parameters created for WSDL part type
     * (contains original TypeDefinition name as a fallback for label provider
     * if it is needed. (Not guaranteed to be incldued in XpdExtAttributes
     * 
     */
    public static final String ORIGINAL_TYPE_DEF_EXTRATTR_NAME =
            "OriginalTypeDefName"; //$NON-NLS-1$

    /**
     * ID used in dummy formal parameters. This will be created when real BOM
     * class or input part cannot be found.
     */
    public static final String INVALID_PART_PARAMETER_ID = "$$_DUMMY"; //$NON-NLS-1$

    /**
     * 
     */
    private static final String UML_ECLIPSE_SCHEMA_NAMESPACE =
            "http://www.eclipse.org/uml2/2.1.0/UML";// Searched for this //$NON-NLS-1$

    // namespace in the code
    // base, couldn't find an
    // appropriate reference

    /**
     * 
     */
    public static final String PARAM_REPRESENTING_PART_EXT_ATTRIB_NAME =
            "ParamRepresentingPart"; //$NON-NLS-1$

    public static JavaScriptConceptUtil INSTANCE = new JavaScriptConceptUtil();

    /*
     * SID XPD-1666: Do not expand arrays for BOM/Field data, this was never
     * implemented particularly well or consistently and AMX BPM (the only
     * destination that can handle array javascript doesn't handle mapping to /
     * from individual array elements.
     * 
     * REMOVED expandArrays(List<ConceptPath> children, Activity activity,
     * Object parentElement, MappingDirection direction); AND ASSOCIATED LOCAL
     * METHODS.
     */

    /**
     * Resolve the given JavaScript grammar path that (should) reference an
     * element in the WSDL input / output part in the operation referenced by
     * the given activity.
     * 
     * @param activity
     * @param javaScriptWSDLMappingPath
     *            JavaScript mapping path referencing an element in WSDL
     *            input/output part.
     * @param wsdlDirection
     *            {@link WsdlDirection#IN} if path references input part
     *            {@link WsdlDirection#OUT} if path references output part.
     * 
     * @return The ConceptPath representing the given given part or
     *         <code>null</code> if the path cannot be resolved into ConceptPath
     *         for input/output part in web service referenced by activity.
     */
    public ConceptPath resolveJavaScriptWSDLConceptPath(Activity activity,
            String javaScriptWSDLMappingPath, WsdlDirection wsdlDirection) {
        ConceptPath conceptPath = null;

        ActivityMessageProvider activityMessageProvider =
                ActivityMessageProviderFactory.INSTANCE
                        .getMessageProvider(activity, false);

        if (activityMessageProvider != null) {
            WebServiceOperation webService =
                    activityMessageProvider.getWebServiceOperation(activity);

            if (webService != null) {

                Collection<ConceptPath> webServiceChildren =
                        getWebServiceChildren(webService,
                                activity,
                                wsdlDirection);

                if (webServiceChildren != null) {
                    String[] paramElems =
                            javaScriptWSDLMappingPath.split("\\."); //$NON-NLS-1$
                    int index = 0;
                    conceptPath =
                            internalRecursiveResolveConceptPathFromPaths(paramElems,
                                    index,
                                    webServiceChildren);
                }
            }
        }

        return conceptPath;
    }

    /**
     * This method resolves the path into a concept path.
     * <p>
     * Find the concept path from conceptPaths for the index'th entry from
     * pathElements[] and then recurs until have processed until we have
     * processed the last pathElement.
     * 
     * @param pathElements
     *            Elements in JavaScreipt path to resolve.
     * @param pathElementIndex
     *            The element in pathElements that we have recursed down to so
     *            far
     * @param conceptPaths
     *            The child concept paths for this level in tree.
     * @param direction
     * 
     * @return ConceptPath matching the last item in the paramElems.
     */
    private ConceptPath internalRecursiveResolveConceptPathFromPaths(
            String[] pathElements, int pathElementIndex,
            Collection<ConceptPath> conceptPaths) {
        ConceptPath paramPath = null;

        for (ConceptPath path : conceptPaths) {
            /* TODO RS - Comment */
            if (path instanceof ChoiceConceptPath) {
                List<ConceptPath> children =
                        ((ChoiceConceptPath) path).getChildren();
                for (ConceptPath choiceChildPath : children) {
                    paramPath =
                            internalRecursiveResolveConceptPath(pathElements,
                                    pathElementIndex,
                                    choiceChildPath);
                    if (paramPath != null) {
                        break;
                    }

                }
                if (paramPath != null) {
                    break;
                }
            }
            if (path instanceof ConceptPath) {
                ConceptPath javaScriptConceptPath = path;
                paramPath =
                        internalRecursiveResolveConceptPath(pathElements,
                                pathElementIndex,
                                javaScriptConceptPath);
                if (paramPath != null) {
                    break;
                }

            }
        }
        return paramPath;
    }

    /**
     * This method resolves the path into a concept path.
     * <p>
     * If the index'th entry in paramElems[] is represented by currentPath then
     * recurs into it until last pathElements element is reached.
     * 
     * @param pathElements
     *            Elements in JavaScreipt path to resolve.
     * @param pathElementIndex
     *            The element in pathElements that we have recursed down to so
     *            far
     * @param checkConceptPath
     *            The concept path to check if matches
     *            pathElements[pathElementIndex]
     * 
     * @return ConceptPath matching the last item in the paramElems.
     */
    private ConceptPath internalRecursiveResolveConceptPath(
            String[] pathElements, int pathElementIndex,
            ConceptPath checkConceptPath) {
        ConceptPath concepPath = null;
        String elem = pathElements[pathElementIndex];
        boolean arrayItem = false;
        if (Xpdl2ResourcesPlugin.shouldExpandMapperFields()
                && checkConceptPath.isArrayHeader()) {
            if (elem.contains("[")) { //$NON-NLS-1$
                arrayItem = true;
            }
            elem = getBaseName(elem);
        }
        String matchName = checkConceptPath.getName();
        if (elem.equals(matchName)) {
            if (arrayItem) {
                int index = getPosition(pathElements[pathElementIndex], true);
                checkConceptPath =
                        new ConceptPath(checkConceptPath,
                                checkConceptPath.getItem(),
                                checkConceptPath.getType(), index);
            }
            pathElementIndex++;
            if (pathElementIndex == pathElements.length) {
                concepPath = checkConceptPath;
            } else {
                /*
                 * If there are more pathElements to process then recurs into
                 * children.
                 */
                Collection<ConceptPath> children =
                        checkConceptPath.getChildren();
                // Collection<ConceptPath> children = new
                // ArrayList<ConceptPath>();
                // for (Object o : conceptContentProvider
                // .getChildren(checkConceptPath)) {
                // if (o instanceof ConceptPath) {
                // children.add((ConceptPath) o);
                // }
                // }
                concepPath =
                        internalRecursiveResolveConceptPathFromPaths(pathElements,
                                pathElementIndex,
                                children);
            }
        }

        return concepPath;
    }

    /**
     * @param element
     *            The element name.
     * @return The base name without the array index.
     */
    private static String getBaseName(String element) {
        String base = element;
        int open = element.indexOf('[');
        if (open != -1) {
            base = base.substring(0, open);
        }
        return base;
    }

    /**
     * @param activity
     *            The activity.
     * @param name
     *            The parameter name to resolve.
     * @param directionType
     * @return The parameter.
     */
    @SuppressWarnings("unchecked")
    public Part resolvePart(Activity activity, String name,
            WsdlDirection wsdlDirection) {

        ActivityMessageProvider messageProvider = getMessageProvider(activity);
        if (messageProvider != null) {
            WebServiceOperation wso =
                    messageProvider.getWebServiceOperation(activity);
            Operation operation = getWSDLOperation(wso);
            List parts = Collections.EMPTY_LIST;
            Fault fault = CatchWsdlErrorEventUtil.getCaughtWsdlFault(activity);
            if (fault != null) {
                Message message = fault.getEMessage();
                if (message != null) {
                    parts = message.getEParts();
                }
            } else if (isThrowFaultRelatedActivity(activity)) {
                Fault thrownFault =
                        ThrowWsdlErrorEventUtil.getWsdlFault(activity);
                if (thrownFault != null) {
                    Message message = thrownFault.getEMessage();
                    if (message != null) {
                        parts = message.getEParts();
                    }
                }
            } else if (WsdlDirection.IN.equals(wsdlDirection)) {
                parts = getInputParts(operation);
            } else if (WsdlDirection.OUT.equals(wsdlDirection)) {
                parts = getOutputParts(operation);
            }
            Part part = findPart(parts, name);
            return part;
        }
        return null;
    }

    /**
     * Gets the {@link Operation} for a given {@link WebServiceOperation}
     * 
     * @param webService
     * @return
     */
    public Operation getWSDLOperation(WebServiceOperation webService) {
        if (webService != null) {
            WorkingCopy wc = WorkingCopyUtil.getWorkingCopyFor(webService);
            if (wc != null) {
                String operation = webService.getOperationName();
                String portType = null;
                String portOperation = null;

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
                            new WsdlServiceKey(service, port, operation,
                                    portType, portOperation,
                                    Xpdl2WsdlUtil.getLocation(webService
                                            .getService()));

                    /*
                     * XPD-7183: adding null checks.
                     */
                    if (key != null) {

                        IResource resource = wc.getEclipseResources().get(0);
                        if (resource != null && svc.eResource() != null
                                && svc.eResource().getResourceSet() != null) {

                            IProject project = resource.getProject();
                            if (project != null) {

                                return WsdlIndexerUtil.getOperation(project,
                                        key,
                                        true,
                                        true);

                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * The BOM {@link Class} for a given {@link Part}
     * 
     * @param part
     * @return
     */
    public Class getConceptClass(Part part) {
        IFile bomFile = getCorrespondingBOMFile(part);
        Class cls1 = getBOMObject(part, bomFile);
        return cls1;
    }

    /**
     * @param element
     *            The element name.
     * @return The array index position.
     */
    public int getPosition(String element, boolean zeroBased) {
        int position = -1;
        int open = element.indexOf('[');
        int close = element.lastIndexOf(']');
        if (open != -1 && close != -1 && close > open) {
            String content = element.substring(open + 1, close);
            try {
                position = Integer.parseInt(content);
                if (!zeroBased) {
                    position--;
                }
            } catch (NumberFormatException e) {
                // Ignore
            }
        }
        return position;
    }

    /**
     * @param activity
     * @return
     */
    public ActivityMessageProvider getMessageProvider(Activity activity) {
        return ActivityMessageProviderFactory.INSTANCE
                .getMessageProvider(activity);
    }

    /**
     * @param webService
     *            The web service to get the children for.
     * @param activity
     * @return A collection of child Part objects.
     */
    public Collection<ConceptPath> getWebServiceChildren(
            WebServiceOperation webService, Activity activity,
            WsdlDirection wsdlDirection) {

        boolean isPortTypeOpOrThrowFaultGenerated =
                Xpdl2ModelUtil.isGeneratedRequestActivity(activity);

        if (!isPortTypeOpOrThrowFaultGenerated
                && ReplyActivityUtil.isReplyActivity(activity)) {
            Activity requestActivityForReplyActivity =
                    ReplyActivityUtil
                            .getRequestActivityForReplyActivity(activity);
            if (requestActivityForReplyActivity != null) {
                isPortTypeOpOrThrowFaultGenerated =
                        Xpdl2ModelUtil
                                .isGeneratedRequestActivity(requestActivityForReplyActivity);
            }
        } else if (ThrowErrorEventUtil.isThrowFaultMessageErrorEvent(activity)) {
            /*
             * Take 'WSDL fault' content from the associated parameters rather
             * than the generated WSDL (which may not have been gen'd yet!)
             */
            isPortTypeOpOrThrowFaultGenerated =
                    ThrowErrorEventUtil
                            .shouldGenerateMappingsForErrorEvent(activity);
        }

        boolean isReplyImmediateStart =
                ReplyActivityUtil.isStartMessageWithReplyImmediate(activity);

        /*
         * If activity is of type Error Catch then needs to be handled
         * differently. If the activity web service operation is a generated Web
         * Service Operation,
         */
        if (isPortTypeOpOrThrowFaultGenerated) {
            /*
             * If the is a reply-immediately start activity and we're asked for
             * OUT parameter then this means JUST the special part generated
             * from ProcessId
             */
            if (WsdlDirection.OUT.equals(wsdlDirection)
                    && isReplyImmediateStart) {

                return Collections
                        .singletonList(new ConceptPath(
                                StandardMappingUtil.REPLY_IMMEDIATE_PROCESS_ID_FORMALPARAMETER,
                                null));

            } else {
                /*
                 * if the activity has any associated parameter - return list of
                 * ConceptPaths - formal param for the assoc param else return
                 * concept paths for all formal params in the process.
                 */
                List<FormalParameter> formalParameters =
                        ProcessInterfaceUtil
                                .getAssociatedFormalParameters(activity);
                return getConceptPathsForFormalParams(formalParameters,
                        wsdlDirection);
            }
        } else {

            Operation op =
                    JavaScriptConceptUtil.INSTANCE.getWSDLOperation(webService);

            if (op != null) {
                if (isCatchFaultRelatedActivity(activity)) {
                    Fault fault =
                            CatchWsdlErrorEventUtil
                                    .getCaughtWsdlFault(activity);
                    if (fault != null) {
                        Message message = fault.getEMessage();
                        if (message != null) {
                            List parts = message.getEParts();
                            return getConceptPaths(parts);
                        }
                    }
                } else if (isThrowFaultRelatedActivity(activity)) {
                    Fault fault =
                            ThrowWsdlErrorEventUtil.getWsdlFault(activity);
                    if (fault != null) {
                        Message message = fault.getEMessage();
                        if (message != null) {
                            List parts = message.getEParts();
                            return getConceptPaths(parts);
                        }
                    }
                }

                else {
                    Collection<?> inputParts = Collections.EMPTY_LIST;
                    Collection<?> outputParts = Collections.EMPTY_LIST;
                    if (op != null) {
                        inputParts = getInputParts(op);
                        outputParts = getOutputParts(op);
                    }

                    IFile wsdlFile = getWSDLForWebService(webService);
                    if (!isPortTypeOpOrThrowFaultGenerated && wsdlFile != null
                            && Xpdl2ModelUtil.isWsdlStudioGenerated(wsdlFile)) {
                        // Covers the use case that a start event message refers
                        // to
                        // a
                        // WSDL operation that is generated from a start message
                        // of
                        // another process
                        // In this case, there will be no BOM file created that
                        // corresponds to the WSDL.

                        // Find the parts
                        if (WsdlDirection.IN.equals(wsdlDirection)) {
                            return getConceptPaths(inputParts);
                        } else {
                            return getConceptPaths(outputParts);
                        }
                    }

                    if (!isPortTypeOpOrThrowFaultGenerated) {
                        if (WsdlDirection.IN.equals(wsdlDirection)) {
                            return getConceptPaths(inputParts);
                        } else {
                            return getConceptPaths(outputParts);
                        }
                    }
                }
            }
        }
        return Collections.EMPTY_LIST;
    }

    /**
     * @param parts
     * @param name
     * @return
     */
    @SuppressWarnings("unchecked")
    private Part findPart(List parts, String name) {
        for (Object obj : parts) {
            if (obj instanceof Part) {
                Part part = (Part) obj;
                if (part.getName() != null && part.getName().equals(name)) {
                    return part;
                }
            }
        }
        return null;
    }

    /**
     * Create a formal parameter.
     * 
     * @param id
     *            Id for parameter. It is safe to pass <code>null</code>
     * @param name
     *            Name of parameter
     * @param type
     *            data type for parameter
     * @param bomFile
     *            BOM file
     * @param addRepresentingPartAttribute
     *            If representing part attribute should be included
     * @param originalTypeDefinition
     *            original XSD type name. It is safe to pass <code>null</code>
     * @return
     */
    public FormalParameter createFormalParam(String id, String name,
            Object type, IFile bomFile, boolean addRepresentingPartAttribute,
            XSDNamedComponent originalTypeDefinition) {
        // Creating a DataField, to ease the use of Concept Paths.
        FormalParameter param = Xpdl2Factory.eINSTANCE.createFormalParameter();

        if (id != null) {
            param.eSet(Xpdl2Package.eINSTANCE.getUniqueIdElement_Id(), id);
        }

        param.setName(name);
        // Adding extended attributes to identify this as a parameter
        // representing a part.
        XpdExtAttributes xpdExtAttributes = null;
        if (addRepresentingPartAttribute) {
            xpdExtAttributes = addExtendedAttributes(param);
            addExtendedAttributeRepresentingPart(xpdExtAttributes);
        }

        if (type instanceof BasicType) {
            BasicType basicType = (BasicType) type;
            param.setDataType(basicType);
        } else if (type instanceof Classifier) {
            Classifier classifier = (Classifier) type;
            ExternalReference extRef =
                    Xpdl2Factory.eINSTANCE.createExternalReference();
            Object classifierId =
                    classifier.eResource().getURIFragment(classifier);
            extRef.setNamespace(UML_ECLIPSE_SCHEMA_NAMESPACE);

            IPath specialFolderRelativePath =
                    SpecialFolderUtil.getSpecialFolderRelativePath(bomFile);
            if (specialFolderRelativePath != null) {
                // Setting the project name as it would become unavailable in
                // the content provider while showing up on the content tree
                if (addRepresentingPartAttribute) {
                    addProjectNameXpdExtAttribtoParam(bomFile.getProject()
                            .getName(), xpdExtAttributes);
                }
                extRef.setLocation(specialFolderRelativePath.toPortableString());

            }
            if (classifierId instanceof String) {
                extRef.setXref((String) classifierId);
            }
            param.setDataType(extRef);

        }
        /*
         * If cannot locate source BOM (for instance when this is a reference to
         * a base primitive type that doesn't have equivalent xpdl BasicType
         * then label provider will get a bit stuck.
         * 
         * So always store the original XSD type name so label provider can use
         * it as a fallback.
         */
        if (originalTypeDefinition != null && xpdExtAttributes != null) {
            XpdExtAttribute projExtAttrib =
                    XpdExtensionFactory.eINSTANCE.createXpdExtAttribute();
            projExtAttrib.setName(ORIGINAL_TYPE_DEF_EXTRATTR_NAME);

            projExtAttrib.setValue(originalTypeDefinition.getName());
            xpdExtAttributes.getAttributes().add(projExtAttrib);
        }

        return param;
    }

    /**
     * @param bomFile
     * @param xpdExtAttributes
     */
    private void addProjectNameXpdExtAttribtoParam(String projectName,
            XpdExtAttributes xpdExtAttributes) {
        XpdExtAttribute projExtAttrib =
                XpdExtensionFactory.eINSTANCE.createXpdExtAttribute();
        projExtAttrib.setName("Project"); //$NON-NLS-1$
        projExtAttrib.setValue(projectName);
        xpdExtAttributes.getAttributes().add(projExtAttrib);
    }

    /**
     * @param param
     * @return
     */
    private XpdExtAttributes addExtendedAttributes(FormalParameter param) {
        XpdExtAttributes xpdExtAttributes =
                XpdExtensionFactory.eINSTANCE.createXpdExtAttributes();
        Xpdl2ModelUtil.setOtherElement(param, XpdExtensionPackage.eINSTANCE
                .getDocumentRoot_ExtendedAttributes(), xpdExtAttributes);
        return xpdExtAttributes;
    }

    /**
     * @param xpdExtAttributes
     */
    private void addExtendedAttributeRepresentingPart(
            XpdExtAttributes xpdExtAttributes) {
        XpdExtAttribute xpdExtAttribute =
                XpdExtensionFactory.eINSTANCE.createXpdExtAttribute();
        xpdExtAttribute.setName(PARAM_REPRESENTING_PART_EXT_ATTRIB_NAME);
        xpdExtAttribute.setValue(Boolean.TRUE.toString());
        xpdExtAttributes.getAttributes().add(xpdExtAttribute);
    }

    @SuppressWarnings("unchecked")
    public static List getInputParts(Operation op) {
        if (op != null) {
            Input input = op.getInput();
            if (input != null) {
                javax.wsdl.Message message = input.getMessage();
                if (message != null) {
                    return message.getOrderedParts(null);
                }
            }
        }
        return Collections.EMPTY_LIST;
    }

    @SuppressWarnings("unchecked")
    public static List getOutputParts(Operation op) {
        if (op != null) {
            Output output = op.getOutput();
            if (output != null) {
                javax.wsdl.Message message = output.getMessage();
                if (message != null) {
                    return message.getOrderedParts(null);
                }
            }
        }
        return Collections.EMPTY_LIST;
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
     * Gets the Concept Paths from the input parts. Helper method in order to
     * get the Concept paths represented by the input part.
     * 
     * @param parts
     * @return
     */
    public Collection<ConceptPath> getConceptPaths(Collection<?> parts) {
        Collection<ConceptPath> partPaths = new ArrayList<ConceptPath>();
        for (Object inputPart : parts) {
            if (inputPart instanceof Part) {
                Part part = (Part) inputPart;
                XSDTypeDefinition typeDefinition = part.getTypeDefinition();
                if (typeDefinition != null) {
                    if (typeDefinition instanceof XSDSimpleTypeDefinition) {
                        // When the WSDL part is defined as a Type
                        // Simple type definitions for parts are not picked up
                        // from the BOM file. They are pseudo created for
                        // validation purposes.
                        resolveConceptPathsForSimpleTypeDefinition(partPaths,
                                part,
                                typeDefinition);
                    } else {
                        resolveComplexTypeDefinitions(partPaths, part);
                    }
                } else {
                    XSDElementDeclaration elementDeclaration =
                            part.getElementDeclaration();
                    if (elementDeclaration != null) {
                        XSDTypeDefinition elementTypeDefinition =
                                elementDeclaration.getTypeDefinition();
                        if (elementTypeDefinition instanceof XSDSimpleTypeDefinition) {
                            resolveConceptPathsForSimpleTypeDefinition(partPaths,
                                    part,
                                    elementTypeDefinition);
                        } else {
                            resolveComplexTypeDefinitions(partPaths, part);
                        }

                    }
                }
            }
        }

        return partPaths;
    }

    /**
     * @param partPaths
     * @param part
     */
    private void resolveComplexTypeDefinitions(
            Collection<ConceptPath> partPaths, Part part) {
        IFile bomFile = getCorrespondingBOMFile(part);

        Classifier cls1 = getBOMClassifier(part, bomFile);

        if (bomFile != null && bomFile.exists() && cls1 != null) {
            FormalParameter formalParam =
                    createFormalParam(null,
                            part.getName(),
                            cls1,
                            bomFile,
                            true,
                            null);
            formalParam.setMode(ModeType.OUT_LITERAL);
            WsdlPartConceptPath wsdlPartConceptPath =
                    new WsdlPartConceptPath(part, PartMode.INPUT, formalParam,
                            cls1);
            partPaths.add(wsdlPartConceptPath);
        } else {

            String message =
                    Messages.JavaScriptConceptUtil_awaitingGenerationOfBOM_error_shortdesc;

            IFile wsdlFile = WorkingCopyUtil.getFile(part);
            if (wsdlFile != null
                    && !Xpdl2WsdlUtil
                            .isWsdlFromWsdlToBomNatureProject(wsdlFile)) {
                message =
                        Messages.JavaScriptConceptUtil_UseXPathScriptGrammar_error_shortdesc;
            }

            FormalParameter formalParam =
                    createFormalParam(INVALID_PART_PARAMETER_ID,
                            message,
                            null,
                            null,
                            true,
                            null);
            formalParam.setMode(ModeType.OUT_LITERAL);
            WsdlPartConceptPath wsdlPartConceptPath =
                    new WsdlPartConceptPath(part, PartMode.INPUT, formalParam,
                            null);
            partPaths.add(wsdlPartConceptPath);
        }
    }

    /**
     * Check if the given ConceptPath represents a dummy FormalParameter
     * (created when a BOM class cannot be created / found).
     * 
     * @param conceptPath
     * @return
     */
    public static boolean isInvalidPartParameter(ConceptPath conceptPath) {
        Object item = conceptPath.getItem();
        if (item instanceof ProcessRelevantData) {
            String id = ((ProcessRelevantData) item).getId();
            return JavaScriptConceptUtil.INVALID_PART_PARAMETER_ID.equals(id);
        }
        return false;
    }

    /**
     * @param partPaths
     * @param part
     * @param typeDefinition
     */
    private void resolveConceptPathsForSimpleTypeDefinition(
            Collection<ConceptPath> partPaths, Part part,
            XSDTypeDefinition typeDefinition) {

        /*
         * XPD-2434: get the BOM type for part type definition FIRST and only
         * fall back on creating a equiv process simple data type if we don't
         * find specific bom type. So in this way we are saying if formal param
         * type is already set to a primitive type then DON'T get base primitive
         * type for it. and if we get BOM type from part for simple type defs it
         * does work when the part has a new type generated for it
         */

        FormalParameter param;
        Classifier formalParamType = null;
        IFile bomFile = getCorrespondingBOMFile(part);

        if (bomFile != null && bomFile.exists()) {
            formalParamType = getBOMClassifier(part, bomFile);
        }

        if (null != formalParamType) {
            param =
                    createFormalParam(null,
                            part.getName(),
                            formalParamType,
                            bomFile,
                            true,
                            null);
            ConceptPath path = new ConceptPath(param, formalParamType);
            partPaths.add(path);

        } else {

            Object equiStandardPrimType =
                    getEquiStandardPrimType(typeDefinition);
            if (null != equiStandardPrimType) {

                param =
                        createFormalParam(null,
                                part.getName(),
                                equiStandardPrimType,
                                bomFile,
                                true,
                                typeDefinition);
                PrimitiveType standardPrimitiveTypeByName =
                        getStandardPrimitiveType(typeDefinition);
                ConceptPath path =
                        new ConceptPath(param, standardPrimitiveTypeByName);
                partPaths.add(path);
            }
        }
    }

    public FormalParameter generateFPFromPart(Part part,
            XSDTypeDefinition typeDefinition) {
        return generateFPFromPart(part, typeDefinition, true);
    }

    /**
     * Generate Formal parameter for a given WSDL Part. This is required for the
     * content provider to resolve the WSDL content as BOM entities
     * 
     * @param part
     * @param typeDefinition
     * @param standardPrimitiveTypeByName
     * @param addRepresentingPartAttribute
     * @return
     */
    public FormalParameter generateFPFromPart(Part part,
            XSDTypeDefinition typeDefinition,
            boolean addRepresentingPartAttribute) {

        PrimitiveType standardPrimitiveType =
                getStandardPrimitiveType(typeDefinition);
        BasicType basicType = null;
        if (standardPrimitiveType != null) {
            Object baseType =
                    BasicTypeConverterFactory.INSTANCE
                            .getBaseType(standardPrimitiveType, true);

            if (baseType instanceof BasicType) {
                basicType = (BasicType) baseType;
            }
        }

        Object formalParamType = null;
        IFile bomFile = null;

        // XPD-1995
        if (basicType != null) {
            formalParamType = basicType;
        } else {
            /*
             * If we could not find equivalent xpdl basic type for primitive
             * type it may be that it is an xsd anytype / anysimpletype element.
             */
            bomFile = getCorrespondingBOMFile(part);
            if (bomFile != null && bomFile.exists()) {
                Classifier cls1 = getBOMClassifier(part, bomFile);

                if (cls1 != null) {
                    formalParamType = cls1;
                }
            }
        }

        FormalParameter param =
                createFormalParam(null,
                        part.getName(),
                        formalParamType,
                        bomFile,
                        addRepresentingPartAttribute,
                        typeDefinition);
        XSDMaxLengthFacet maxLengthFacet =
                ((XSDSimpleTypeDefinition) typeDefinition).getMaxLengthFacet();

        if (maxLengthFacet != null) {
            Length length = Xpdl2Factory.eINSTANCE.createLength();
            length.setValue(String.valueOf(maxLengthFacet.getValue()));
            param.setLength(length);
        }

        return param;
    }

    /**
     * @param typeDefinition
     * @param formalParamType
     * @param basicType
     * @return
     */
    protected Object getEquiStandardPrimType(XSDTypeDefinition typeDefinition) {
        Object formalParamType = null;
        BasicType basicType = null;
        /*
         * 
         * if we could not find corresponding BOM type for part type definition
         * then only fall back on creating a equiv process simple data type
         */
        PrimitiveType standardPrimitiveType =
                getStandardPrimitiveType(typeDefinition);

        if (standardPrimitiveType != null) {
            Object baseType =
                    BasicTypeConverterFactory.INSTANCE
                            .getBaseType(standardPrimitiveType, true);

            if (baseType == null) {
                formalParamType = standardPrimitiveType;
            }

            if (baseType instanceof BasicType) {
                basicType = (BasicType) baseType;
            }
        }
        // XPD-1995
        if (null != basicType) {
            formalParamType = basicType;
        }
        return formalParamType;
    }

    /**
     * @param typeDefinition
     * @return
     */
    private PrimitiveType getStandardPrimitiveType(
            XSDTypeDefinition typeDefinition) {

        XSDSimpleTypeDefinition baseTypeDefinition = null;
        /*
         * Need to reduce the type definition to its lowest type. However,
         * observed that the lowest level for an "integer" primitive type is
         * "decimal". This causes further problems while validating and
         * displaying label. Therefore, the logic below checks if the type
         * definition already is part of the XSD namespace then it won't reduce
         * it further to its lowest type.
         */

        if (WSDLConstants.XSD_NAMESPACE_URI.equals(typeDefinition
                .getTargetNamespace())) {
            baseTypeDefinition = (XSDSimpleTypeDefinition) typeDefinition;
        } else {

            if (typeDefinition instanceof XSDSimpleTypeDefinition) {
                /**
                 * XPD-1446: If the type definition is of simple type then we
                 * don't want to find the primitive type of it.
                 * 
                 * For eg: if the simple type has base restriction of
                 * xsd:integer, we want the base type definition to be integer
                 * and not decimal. If we use the getPrimitiveTypeDefinition we
                 * get decimal for integer.
                 */
                XSDSimpleTypeDefinition simpleTypeDefinition =
                        (XSDSimpleTypeDefinition) typeDefinition;

                baseTypeDefinition =
                        simpleTypeDefinition.getBaseTypeDefinition();
                if (baseTypeDefinition != null
                        && !WSDLConstants.XSD_NAMESPACE_URI
                                .equals(baseTypeDefinition.getTargetNamespace())) {
                    baseTypeDefinition =
                            baseTypeDefinition.getPrimitiveTypeDefinition();
                }
            } else {
                XSDSimpleTypeDefinition simpleTypeDefinition =
                        (XSDSimpleTypeDefinition) typeDefinition;

                baseTypeDefinition =
                        simpleTypeDefinition.getPrimitiveTypeDefinition();
            }
        }

        if (baseTypeDefinition != null) {
            String typeDefName = null;
            String baseTypeDefName = baseTypeDefinition.getName();
            if ("anySimpleType".equals(baseTypeDefName) //$NON-NLS-1$
                    || "anyType".equals(baseTypeDefName)) { //$NON-NLS-1$
                typeDefName = typeDefinition.getName();

                if (null == typeDefName) {
                    typeDefName = baseTypeDefName;
                }
            } else {
                typeDefName = baseTypeDefName;
            }

            String primitiveType = XSDUtil.getBOMPrimitiveType(typeDefName);
            PrimitiveType standardPrimitiveTypeByName =
                    PrimitivesUtil
                            .getStandardPrimitiveTypeByName(XpdResourcesPlugin
                                    .getDefault().getEditingDomain()
                                    .getResourceSet(), primitiveType);
            return standardPrimitiveTypeByName;
        }
        return null;
    }

    /**
     * @return
     */
    public static ComplexDataTypesMergedInfo getComplexTypesInfo() {
        ComplexDataTypesMergedInfo _complexTypesInfo = null;
        if (_complexTypesInfo == null) {
            _complexTypesInfo =
                    ComplexDataTypeExtPointHelper
                            .getAllComplexDataTypesMergedInfo();
        }
        return _complexTypesInfo;
    }

    /**
     * @param formalParameters
     * @param wsdlInput
     * @return
     */
    private List<ConceptPath> getConceptPathsForFormalParams(
            List<FormalParameter> formalParameters, WsdlDirection wsdlDirection) {

        List<ConceptPath> conceptPaths = new ArrayList<ConceptPath>();
        for (FormalParameter formalParameter : formalParameters) {

            FormalParameter copyOfFormalParam = EcoreUtil.copy(formalParameter);

            XpdExtAttributes extAttributes =
                    addExtendedAttributes(copyOfFormalParam);
            addExtendedAttributeRepresentingPart(extAttributes);

            if (copyOfFormalParam.getDataType() instanceof ExternalReference) {

                ExternalReference externalReference =
                        (ExternalReference) copyOfFormalParam.getDataType();
                Classifier complexDataTypeModel =
                        ConceptUtil
                                .getComplexDataTypeClassfier(new ComplexDataTypeReference(
                                        externalReference.getLocation(),
                                        externalReference.getXref(),
                                        externalReference.getNamespace()),
                                        WorkingCopyUtil
                                                .getProjectFor(formalParameter));

                if (complexDataTypeModel != null) {
                    IProject projectForBOMClass =
                            WorkingCopyUtil.getProjectFor(complexDataTypeModel);
                    addProjectNameXpdExtAttribtoParam(projectForBOMClass.getName(),
                            extAttributes);

                }
            }
            if (WsdlDirection.IN.equals(wsdlDirection)) {
                if (ModeType.IN_LITERAL.equals(formalParameter.getMode())
                        || ModeType.INOUT_LITERAL.equals(formalParameter
                                .getMode())) {
                    conceptPaths.add(new ConceptPath(copyOfFormalParam,
                            ConceptUtil.getConceptClass(formalParameter)));
                }
            } else {
                if (ModeType.OUT_LITERAL.equals(formalParameter.getMode())
                        || ModeType.INOUT_LITERAL.equals(formalParameter
                                .getMode())) {
                    conceptPaths.add(new ConceptPath(copyOfFormalParam,
                            ConceptUtil.getConceptClass(formalParameter)));
                }
            }

        }
        return conceptPaths;
    }

    /**
     * @param webService
     * @return
     */
    private IFile getWSDLForWebService(WebServiceOperation webService) {
        Service service = webService.getService();
        if (service != null) {
            EndPoint endPoint = service.getEndPoint();
            if (endPoint != null) {
                ExternalReference externalReference =
                        endPoint.getExternalReference();
                if (externalReference != null) {

                    String location = externalReference.getLocation();
                    IFile xpdlFile = WorkingCopyUtil.getFile(webService);

                    /* Sid XPD-2784: Protect against null pointer */
                    if (xpdlFile != null && location != null) {
                        IProject bpmProject = xpdlFile.getProject();
                        if (bpmProject != null) {
                            IFile wsdlFile =
                                    searchForWsdlFileInProject(bpmProject,
                                            location);

                            return wsdlFile;
                        }
                    }
                }
            }

        }
        return null;
    }

    /**
     * @param bpmProject
     * @param location
     * @return
     */
    private IFile searchForWsdlFileInProject(IProject bpmProject,
            String location) {
        Set<IProject> projects = new HashSet<IProject>();
        projects.add(bpmProject);
        projects =
                ProjectUtil
                        .getReferencedProjectsHierarchy(bpmProject, projects);
        for (IProject iProject : projects) {
            ProjectConfig projectConfig =
                    XpdResourcesPlugin.getDefault().getProjectConfig(iProject);
            if (projectConfig == null) {
                continue;
            }
            SpecialFolders specialFolders = projectConfig.getSpecialFolders();
            List<SpecialFolder> wsdlSpecialFolders =
                    specialFolders.getFoldersOfKind("wsdl"); //$NON-NLS-1$

            for (SpecialFolder wsdlFolder : wsdlSpecialFolders) {
                IFolder folder = wsdlFolder.getFolder();
                if (folder != null) {
                    IResource member = folder.findMember(location);
                    if (member != null && member.exists()
                            && member instanceof IFile) {
                        return (IFile) member;
                    }
                }
            }

        }
        return null;
    }

    /**
     * @param inputPart
     * @return
     */
    public IFile getCorrespondingBOMFile(Part part) {

        String typeName = getTypeName(part);
        String targetNamespace = getTargetNamespace(part);

        boolean isAutoGeneratedWSDL = isWSDLAutoGenerated(part);
        /*
         * If WSDL file is generated
         */
        IndexerItem indexedItem = null;
        if (isAutoGeneratedWSDL) {
            indexedItem =
                    WsdlBomIndexerUtil
                            .queryBOMElementForGeneratedWSDL(targetNamespace,
                                    typeName,
                                    WorkingCopyUtil.getFile(part));
        } else {
            // WSDL not generated

            /*
             * Look for the BOM class generated for the TYPE of WSDL Part (which
             * may be in the WSDL or may be in an imported/included schema.
             */
            IFile fileForPartType = null;

            XSDTypeDefinition partType = part.getTypeDefinition();
            if (partType != null) {
                fileForPartType = WorkingCopyUtil.getFile(partType);
            }

            if (fileForPartType == null) {
                /*
                 * if we could not locate the file for part's type, then look up
                 * the file for part's element
                 */
                XSDElementDeclaration elementDeclaration =
                        part.getElementDeclaration();
                if (null != elementDeclaration) {
                    fileForPartType =
                            WorkingCopyUtil.getFile(elementDeclaration);
                }
                /*
                 * Just in case we couldn't locate the file for the part's type
                 * or part's element, look up the file for the WSDL Part itself.
                 */
                if (null == fileForPartType) {
                    fileForPartType = WorkingCopyUtil.getFile(part);
                }
            }

            indexedItem =
                    WsdlBomIndexerUtil
                            .queryBOMElementUserDefinedWSDLForGivenNameAndNS(targetNamespace,
                                    typeName,
                                    part.getElementDeclaration() != null,
                                    fileForPartType);
        }

        if (indexedItem != null) {
            String bomFileName =
                    indexedItem
                            .get(WsdlBomIndexerProvider.ATTRIBUTE_BOM_FILE_NAME);
            if (bomFileName != null) {
                IFile bomFile =
                        ResourcesPlugin.getWorkspace().getRoot()
                                .getFile(new Path(bomFileName));
                return bomFile;
            }
        }
        return null;
    }

    /**
     * @param part
     * @param typeName
     * @return
     */
    private static String getTypeName(Part part) {
        String typeName = null;
        if (part != null) {
            XSDTypeDefinition typeDefinition = part.getTypeDefinition();
            if (typeDefinition != null) {
                typeName = typeDefinition.getName();
            } else {
                XSDElementDeclaration elementDeclaration =
                        part.getElementDeclaration();
                if (elementDeclaration != null) {
                    /*
                     * XPD-3768: return element's type definition name if it is
                     * not null else element name, for the part referring
                     * element declaration.
                     */
                    // XSDTypeDefinition elementTypeDef =
                    // elementDeclaration.getTypeDefinition();
                    // if (null != elementTypeDef
                    // && null != elementTypeDef.getName()) {
                    // typeName = elementTypeDef.getName();
                    // } else {
                    typeName = elementDeclaration.getName();
                    // }
                }
            }
        }
        return typeName;
    }

    /**
     * @param part
     * @param typeName
     * @return
     */
    private String getTargetNamespace(Part part) {
        String targetNamespace = null;
        if (part != null) {
            XSDTypeDefinition typeDefinition = part.getTypeDefinition();
            if (typeDefinition != null) {
                targetNamespace = typeDefinition.getTargetNamespace();
            } else {
                XSDElementDeclaration elementDeclaration =
                        part.getElementDeclaration();
                if (elementDeclaration != null) {
                    targetNamespace = elementDeclaration.getTargetNamespace();
                }
            }
        }
        return targetNamespace;
    }

    /**
     * @param typeName
     * @param bomFile
     * @param isPartReferringToElement
     */
    private Class getBOMObject(Part part, IFile bomFile) {
        Classifier bomClassifier = getBOMClassifier(part, bomFile);
        if (bomClassifier instanceof Class) {
            return (Class) bomClassifier;
        }
        return null;
    }

    public Classifier getBOMClassifier(Part part, IFile bomFile) {
        String typeName = getTypeName(part);

        boolean isWSDLFileAutoGenerated = isWSDLAutoGenerated(part);

        if (bomFile != null && bomFile.exists()) {
            WorkingCopy bomWC =
                    XpdResourcesPlugin.getDefault().getWorkingCopy(bomFile);
            if (bomWC != null && bomWC.getRootElement() != null) {
                IndexerItem indexerItem = null;
                indexerItem =
                        WsdlBomIndexerUtil
                                .queryElementIdForGivenBOMFileAndTypeName(bomFile
                                        .getFullPath().toPortableString(),
                                        typeName,
                                        isWSDLFileAutoGenerated,
                                        part.getElementDeclaration() != null);
                if (indexerItem != null) {
                    String bomId =
                            indexerItem
                                    .get(WsdlBomIndexerProvider.ATTRIBUTE_BOM_ID);
                    if (bomId != null) {
                        EObject object =
                                bomWC.getRootElement().eResource()
                                        .getEObject(bomId);
                        if (object instanceof Classifier) {
                            return (Classifier) object;
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * @param part
     * @return
     */
    private boolean isWSDLAutoGenerated(Part part) {

        Definition definition = part.getEnclosingDefinition();
        if (definition != null) {
            return WSDLTransformUtil.isGeneratedWsdl(definition);
        }
        return false;

    }

    public static boolean isWsdlConceptPathContainer(ConceptPath conceptPath) {
        if (conceptPath != null) {
            ConceptPath root = conceptPath.getRoot();
            if (root instanceof WsdlPartConceptPath) {
                return true;
            } else if (root.getItem() != null) {
                Object item = root.getItem();
                return JavaScriptConceptUtil.isParameterRepresentingPart(item);
            }
        }
        return false;
    }

    /**
     * @param element
     * @return
     */
    public static boolean isParameterRepresentingPart(Object element) {
        if (element instanceof FormalParameter) {
            FormalParameter formalParameter = (FormalParameter) element;
            Object extendedAttribsOtherElements =
                    Xpdl2ModelUtil.getOtherElement(formalParameter,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_ExtendedAttributes());
            if (extendedAttribsOtherElements instanceof XpdExtAttributes) {
                List<XpdExtAttribute> attributes =
                        ((XpdExtAttributes) extendedAttribsOtherElements)
                                .getAttributes();
                EObject inList =
                        EMFSearchUtil
                                .findInList(attributes,
                                        XpdExtensionPackage.eINSTANCE
                                                .getXpdExtAttribute_Name(),
                                        JavaScriptConceptUtil.PARAM_REPRESENTING_PART_EXT_ATTRIB_NAME);
                if (inList instanceof XpdExtAttribute) {
                    // Double check
                    if ("true".equals(((XpdExtAttribute) inList).getValue())) { //$NON-NLS-1$
                        return true;
                    }
                }

            }
        }
        return false;
    }

    /**
     * Creates a formal parameter for the given part. Returns parameter with
     * INVALID_PART_PARAMETER_ID if its a user-defined wsdl part and its
     * referenced BOM file is missing. Optionally adds an attribute to the
     * parameter if it represents a wsdl part
     * 
     * @param part
     * @param paramModeType
     * @param addRepresentingPartAttribute
     * @return formalParameter
     */
    public FormalParameter createFormalParamForPart(Part part,
            ModeType paramModeType, boolean addRepresentingPartAttribute) {

        FormalParameter formalParameter = null;

        XSDTypeDefinition typeDefinition = part.getTypeDefinition();
        /*
         * XPD-1247: if the binding type is Document Literal part type
         * definition would be available in element declaration
         */
        if (null == typeDefinition) {
            if (part.getElementDeclaration() instanceof XSDElementDeclaration) {
                typeDefinition =
                        part.getElementDeclaration().getTypeDefinition();
            }
        }
        if (typeDefinition instanceof XSDSimpleTypeDefinition) {
            /*
             * Simple type definitions for parts are not picked up from the BOM
             * file. They are pseudo created for validation purposes.
             */
            formalParameter =
                    generateFPFromPart(part,
                            typeDefinition,
                            addRepresentingPartAttribute);

        } else {
            /*
             * get the corresponding BOM file for the given part and create the
             * parameter
             */
            IFile bomFile = getCorrespondingBOMFile(part);

            Classifier cls1 = getBOMClassifier(part, bomFile);

            if (bomFile != null && bomFile.exists() && cls1 != null) {
                formalParameter =
                        createFormalParam(null,
                                part.getName(),
                                cls1,
                                bomFile,
                                addRepresentingPartAttribute,
                                null);
            } else {
                /*
                 * if BOM file doesn't exist or the type couldn't be found,
                 * create a parameter with dummy ID
                 */
                String message =
                        Messages.JavaScriptConceptUtil_awaitingGenerationOfBOM_error_shortdesc;

                IFile wsdlFile = WorkingCopyUtil.getFile(part);
                if (wsdlFile != null
                        && !Xpdl2WsdlUtil
                                .isWsdlFromWsdlToBomNatureProject(wsdlFile)) {
                    message =
                            Messages.JavaScriptConceptUtil_UseXPathScriptGrammar_error_shortdesc;
                }

                formalParameter =
                        createFormalParam(INVALID_PART_PARAMETER_ID,
                                message,
                                null,
                                null,
                                addRepresentingPartAttribute,
                                null);
            }

            ExternalReference externalReference =
                    (ExternalReference) formalParameter.getDataType();

            ComplexDataTypeReference complexDataTypeReference =
                    ComplexDataUIUtil.resolveSelectionToReference(cls1,
                            WorkingCopyUtil.getProjectFor(formalParameter),
                            getComplexTypesInfo());
            if (complexDataTypeReference != null) {
                externalReference.setLocation(complexDataTypeReference
                        .getLocation());
                externalReference.setNamespace(complexDataTypeReference
                        .getNameSpace());
                externalReference.setXref(complexDataTypeReference.getXRef());
            }

        }

        formalParameter.setMode(paramModeType);

        return formalParameter;
    }

}
