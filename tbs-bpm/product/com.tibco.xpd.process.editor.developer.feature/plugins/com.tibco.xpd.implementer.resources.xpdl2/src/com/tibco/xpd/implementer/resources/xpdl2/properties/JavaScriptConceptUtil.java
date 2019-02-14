/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.implementer.resources.xpdl2.properties;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesPlugin;
import com.tibco.xpd.bom.types.PrimitivesUtil;
import com.tibco.xpd.processeditor.xpdl2.properties.ChoiceConceptPath;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.processwidget.adapters.EventTriggerType;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.projectconfig.SpecialFolders;
import com.tibco.xpd.resources.util.ProjectUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.ui.complexdatatype.ComplexDataTypeExtPointHelper;
import com.tibco.xpd.ui.complexdatatype.ComplexDataTypesMergedInfo;
import com.tibco.xpd.xpdExtension.XpdExtAttribute;
import com.tibco.xpd.xpdExtension.XpdExtAttributes;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.EndEvent;
import com.tibco.xpd.xpdl2.EndPoint;
import com.tibco.xpd.xpdl2.ExternalReference;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.IntermediateEvent;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.Service;
import com.tibco.xpd.xpdl2.WebServiceOperation;
import com.tibco.xpd.xpdl2.extension.EMFSearchUtil;
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
            "http://www.eclipse.org/uml2/2.1.0/UML";// Searched //$NON-NLS-1$
                                                    // for this

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
                paramPath = internalRecursiveResolveConceptPath(pathElements,
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
                checkConceptPath = new ConceptPath(checkConceptPath,
                        checkConceptPath.getItem(), checkConceptPath.getType(),
                        index);
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
                concepPath = internalRecursiveResolveConceptPathFromPaths(
                        pathElements,
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
        Xpdl2ModelUtil.setOtherElement(param,
                XpdExtensionPackage.eINSTANCE
                        .getDocumentRoot_ExtendedAttributes(),
                xpdExtAttributes);
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
     * Gets a matching primitive type string value for an xsd type.
     * 
     * @param xsdType
     * @return
     */
    public static String getBOMPrimitiveType(String xsdType) {
        String searchBomElement = ""; //$NON-NLS-1$

        if (xsdType != null) {
            if (xsdType.equalsIgnoreCase("string")) { //$NON-NLS-1$
                searchBomElement = PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME;
            } else if (xsdType.equalsIgnoreCase("float")) { //$NON-NLS-1$
                searchBomElement = PrimitivesUtil.BOM_PRIMITIVE_DECIMAL_NAME;
            } else if (xsdType.equalsIgnoreCase("decimal")) { //$NON-NLS-1$
                searchBomElement = PrimitivesUtil.BOM_PRIMITIVE_DECIMAL_NAME;
            } else if (xsdType.equalsIgnoreCase("double")) { //$NON-NLS-1$
                searchBomElement = PrimitivesUtil.BOM_PRIMITIVE_DECIMAL_NAME;
            } else if (xsdType.equalsIgnoreCase("integer")) { //$NON-NLS-1$
                searchBomElement = PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME;
            } else if (xsdType.equalsIgnoreCase("int")) { //$NON-NLS-1$
                searchBomElement = PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME;
            } else if (xsdType.equalsIgnoreCase("boolean")) { //$NON-NLS-1$
                searchBomElement = PrimitivesUtil.BOM_PRIMITIVE_BOOLEAN_NAME;
            } else if (xsdType.equalsIgnoreCase("date")) { //$NON-NLS-1$
                searchBomElement = PrimitivesUtil.BOM_PRIMITIVE_DATE_NAME;
            } else if (xsdType.equalsIgnoreCase("time")) { //$NON-NLS-1$
                searchBomElement = PrimitivesUtil.BOM_PRIMITIVE_TIME_NAME;
            } else if (xsdType.equalsIgnoreCase("datetime")) { //$NON-NLS-1$
                searchBomElement = PrimitivesUtil.BOM_PRIMITIVE_DATETIME_NAME;
            } else if (xsdType.equalsIgnoreCase("id")) { //$NON-NLS-1$
                searchBomElement = PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME;
            } else if (xsdType.equalsIgnoreCase("anyuri")) { //$NON-NLS-1$
                searchBomElement = PrimitivesUtil.BOM_PRIMITIVE_URI_NAME;
            } else if (xsdType.equalsIgnoreCase("duration")) { //$NON-NLS-1$
                searchBomElement = PrimitivesUtil.BOM_PRIMITIVE_DURATION_NAME;
            } else if (xsdType.equalsIgnoreCase("base64binary")) { //$NON-NLS-1$
                searchBomElement = PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME;
            } else if (xsdType.equalsIgnoreCase("hexbinary")) { //$NON-NLS-1$
                searchBomElement = PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME;
            } else if (xsdType.equalsIgnoreCase("anytype")) { //$NON-NLS-1$
                searchBomElement = PrimitivesUtil.BOM_PRIMITIVE_OBJECT_NAME;
            } else if (xsdType.equalsIgnoreCase("anysimpletype")) { //$NON-NLS-1$
                searchBomElement = PrimitivesUtil.BOM_PRIMITIVE_OBJECT_NAME;
            } else if (xsdType.equalsIgnoreCase("byte")) { //$NON-NLS-1$
                searchBomElement = PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME;
            } else if (xsdType.equalsIgnoreCase("entities")) { //$NON-NLS-1$
                searchBomElement = PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME;
            } else if (xsdType.equalsIgnoreCase("entity")) { //$NON-NLS-1$
                searchBomElement = PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME;
            } else if (xsdType.equalsIgnoreCase("gday")) { //$NON-NLS-1$
                searchBomElement = PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME;
            } else if (xsdType.equalsIgnoreCase("gmonth")) { //$NON-NLS-1$
                searchBomElement = PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME;
            } else if (xsdType.equalsIgnoreCase("gmonthday")) { //$NON-NLS-1$
                searchBomElement = PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME;
            } else if (xsdType.equalsIgnoreCase("gyear")) { //$NON-NLS-1$
                searchBomElement = PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME;
            } else if (xsdType.equalsIgnoreCase("gyearmonth")) { //$NON-NLS-1$
                searchBomElement = PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME;
            } else if (xsdType.equalsIgnoreCase("idref")) { //$NON-NLS-1$
                searchBomElement = PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME;
            } else if (xsdType.equalsIgnoreCase("idrefs")) { //$NON-NLS-1$
                searchBomElement = PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME;
            } else if (xsdType.equalsIgnoreCase("language")) { //$NON-NLS-1$
                searchBomElement = PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME;
            } else if (xsdType.equalsIgnoreCase("long")) { //$NON-NLS-1$
                searchBomElement = PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME;
            } else if (xsdType.equalsIgnoreCase("name")) { //$NON-NLS-1$
                searchBomElement = PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME;
            } else if (xsdType.equalsIgnoreCase("ncname")) { //$NON-NLS-1$
                searchBomElement = PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME;
            } else if (xsdType.equalsIgnoreCase("negativeinteger")) { //$NON-NLS-1$
                searchBomElement = PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME;
            } else if (xsdType.equalsIgnoreCase("nmtoken")) { //$NON-NLS-1$
                searchBomElement = PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME;
            } else if (xsdType.equalsIgnoreCase("nmtokens")) { //$NON-NLS-1$
                searchBomElement = PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME;
            } else if (xsdType.equalsIgnoreCase("nonnegativeinteger")) { //$NON-NLS-1$
                searchBomElement = PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME;
            } else if (xsdType.equalsIgnoreCase("nonpositiveinteger")) { //$NON-NLS-1$
                searchBomElement = PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME;
            } else if (xsdType.equalsIgnoreCase("positiveinteger")) { //$NON-NLS-1$
                searchBomElement = PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME;
            } else if (xsdType.equalsIgnoreCase("normalizedstring")) { //$NON-NLS-1$
                searchBomElement = PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME;
            } else if (xsdType.equalsIgnoreCase("positiveinteger")) { //$NON-NLS-1$
                searchBomElement = PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME;
            } else if (xsdType.equalsIgnoreCase("qname")) { //$NON-NLS-1$
                searchBomElement = PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME;
            } else if (xsdType.equalsIgnoreCase("short")) { //$NON-NLS-1$
                searchBomElement = PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME;
            } else if (xsdType.equalsIgnoreCase("token")) { //$NON-NLS-1$
                searchBomElement = PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME;
            } else if (xsdType.equalsIgnoreCase("unsignedbyte")) { //$NON-NLS-1$
                searchBomElement = PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME;
            } else if (xsdType.equalsIgnoreCase("unsignedint")) { //$NON-NLS-1$
                searchBomElement = PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME;
            } else if (xsdType.equalsIgnoreCase("unsignedlong")) { //$NON-NLS-1$
                searchBomElement = PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME;
            } else if (xsdType.equalsIgnoreCase("unsignedshort")) { //$NON-NLS-1$
                searchBomElement = PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME;
            }

        }
        return searchBomElement;
    }

    /**
     * @return
     */
    public static ComplexDataTypesMergedInfo getComplexTypesInfo() {
        ComplexDataTypesMergedInfo _complexTypesInfo = null;
        if (_complexTypesInfo == null) {
            _complexTypesInfo = ComplexDataTypeExtPointHelper
                    .getAllComplexDataTypesMergedInfo();
        }
        return _complexTypesInfo;
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
        projects = ProjectUtil.getReferencedProjectsHierarchy(bpmProject,
                projects);
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
                EObject inList = EMFSearchUtil.findInList(attributes,
                        XpdExtensionPackage.eINSTANCE.getXpdExtAttribute_Name(),
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

}
