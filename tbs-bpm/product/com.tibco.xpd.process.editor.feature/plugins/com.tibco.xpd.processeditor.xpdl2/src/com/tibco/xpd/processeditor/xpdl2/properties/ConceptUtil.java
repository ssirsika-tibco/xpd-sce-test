/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.processeditor.xpdl2.properties;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.uml2.uml.AggregationKind;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.EnumerationLiteral;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;

import com.tibco.xpd.analyst.resources.xpdl2.utils.BasicTypeConverterFactory;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessDataUtil;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessUIUtil;
import com.tibco.xpd.bom.types.PrimitivesUtil;
import com.tibco.xpd.destinations.ui.DestinationUtil;
import com.tibco.xpd.processeditor.xpdl2.Xpdl2ProcessEditorPlugin;
import com.tibco.xpd.processeditor.xpdl2.fields.DataFieldContributor;
import com.tibco.xpd.processeditor.xpdl2.util.ProcessScriptUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.util.SpecialFolderUtil;
import com.tibco.xpd.ui.complexdatatype.ComplexDataTypeReference;
import com.tibco.xpd.xpdExtension.InterfaceMethod;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdExtension.XpdExtAttribute;
import com.tibco.xpd.xpdExtension.XpdExtAttributes;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ExternalReference;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.RecordType;
import com.tibco.xpd.xpdl2.extension.EMFSearchUtil;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * @author nwilson
 */
public class ConceptUtil {
    /**
     * *****************Temporal set of methods, most of them duplication of
     * existing ones in the core**********************
     */
    /**
     * Ecore util URI separator.
     */
    private static final String URI_SEPARATOR = "/"; //$NON-NLS-1$

    private static final String ARRAY_TERMINATION = "[]"; //$NON-NLS-1$

    /**
     * 
     */
    private static final String COLON_DELIMITER = ":"; //$NON-NLS-1$

    /**
     * Prefix of EcoreUtil returned URI to remove.
     */
    private static final String PLATFORM_RESOURCE = "platform:/resource"; //$NON-NLS-1$

    /** Lists of field conributors keyed by process destination id. */
    private static Map<String, List<DataFieldContributor>> fieldContributors;

    public static Class getComplexDataTypeModel(
            ComplexDataTypeReference complexDataTypeRef, IProject project) {
        Classifier umlClassifier =
                getComplexDataTypeClassfier(complexDataTypeRef, project);
        if (umlClassifier instanceof Class) {
            return (Class) umlClassifier;
        }
        return null;
    }

    public static Classifier getComplexDataTypeClassfier(
            ComplexDataTypeReference complexDataTypeRef, IProject project) {
        Classifier umlClassifier = null;
        Object obj =
                ProcessUIUtil.getReferencedClassifier(complexDataTypeRef,
                        project);
        if (obj instanceof Classifier) {
            umlClassifier = (Classifier) obj;
        }
        return umlClassifier;
    }

    /**
     * @param referenceActivity
     * @param conceptPath
     * @return
     */
    public static ProcessRelevantData resolveParameter(
            Activity referenceActivity, ConceptPath conceptPath) {
        String path = conceptPath.getPath();
        if (path != null) {
            if (path.startsWith("process.")) { //$NON-NLS-1$
                path = path.substring(8);
            }
            StringTokenizer tokenizer = new StringTokenizer(path, "."); //$NON-NLS-1$
            if (tokenizer.hasMoreTokens()) {
                String field = tokenizer.nextToken();
                ProcessRelevantData data =
                        resolveParameter(referenceActivity, field);
                return data;
            }
        }
        return null;
    }

    public static ConceptPath resolveConceptPath(Activity activity, String path) {

        return resolveConceptPath(activity, path, false);
    }

    /**
     * Returns ConceptPath for the given path. If the given
     * 'showChildrenOfArrays' is true, it also includes children of complex type
     * Arrays when looking for concept path for the given path.
     * 
     * @param activity
     * @param path
     * @param showChildrenOfArrays
     * @return
     */
    public static ConceptPath resolveConceptPath(Activity activity,
            String path, boolean showChildrenOfArrays) {
        ConceptPath concept = null;
        if (path != null) {
            if (path.startsWith("process.")) { //$NON-NLS-1$
                path = path.substring(8);
            }
            StringTokenizer tokenizer = new StringTokenizer(path, "."); //$NON-NLS-1$
            if (tokenizer.hasMoreTokens()) {
                String field = tokenizer.nextToken();
                ProcessRelevantData data = resolveParameter(activity, field);
                if (data != null) {
                    Class clss = getConceptClass(data);
                    ConceptPath current = new ConceptPath(data, clss);

                    current.setIncludeChi1ldrenOfArrays(showChildrenOfArrays);

                    while (tokenizer.hasMoreTokens()) {
                        String token = tokenizer.nextToken();
                        current = resolveConcept(current, token);
                    }
                    concept = current;
                } else {
                    Process process = activity.getProcess();
                    if (process != null) {
                        String context =
                                DataFieldContributor.CONTEXT_ALL_POSSIBLE;
                        if (ProcessScriptUtil.isBwService(activity)) {
                            context =
                                    DataFieldContributor.CONTEXT_BW_SERVICE_TASK;
                        }
                        Collection<ConceptPath> contributions =
                                getContributedFields(process, context);
                        for (ConceptPath contribution : contributions) {
                            if (field.equals(contribution.getName())) {
                                ConceptPath current = contribution;
                                while (tokenizer.hasMoreTokens()) {
                                    String token = tokenizer.nextToken();
                                    current = resolveConcept(current, token);
                                }
                                concept = current;
                                break;
                            }
                        }
                    }
                }
            }
        }
        return concept;
    }

    /**
     * Resolve concept path for the specified process according to the path
     * specified.
     * 
     * @param xpdlProcess
     * @param path
     * 
     * @return Concept path for the specified process according to the path
     *         specified.
     */
    public static ConceptPath resolveConceptPath(Process xpdlProcess,
            String path) {

        return resolveConceptPath(xpdlProcess, path, false);
    }

    /**
     * Resolve concept path for the specified process according to the path
     * specified.
     * 
     * @param xpdlProcess
     * @param path
     * @param showArrayChildren
     * 
     * @return Concept path for the specified process according to the path
     *         specified.
     */
    public static ConceptPath resolveConceptPath(Process xpdlProcess,
            String path, boolean showArrayChildren) {
        ConceptPath concept = null;
        if (path != null && xpdlProcess != null) {
            if (path.startsWith("process.")) { //$NON-NLS-1$
                path = path.substring(8);
            }
            StringTokenizer tokenizer = new StringTokenizer(path, "."); //$NON-NLS-1$
            if (tokenizer.hasMoreTokens()) {
                String field = tokenizer.nextToken();
                ProcessRelevantData data = resolveParameter(xpdlProcess, field);
                if (data != null) {
                    Class clss = getConceptClass(data);
                    ConceptPath current = new ConceptPath(data, clss);

                    current.setIncludeChi1ldrenOfArrays(showArrayChildren);

                    while (tokenizer.hasMoreTokens()) {
                        String token = tokenizer.nextToken();
                        current = resolveConcept(current, token);
                    }
                    concept = current;
                } else {
                    String context = DataFieldContributor.CONTEXT_ALL_POSSIBLE;
                    Collection<ConceptPath> contributions =
                            getContributedFields(xpdlProcess, context);
                    for (ConceptPath contribution : contributions) {
                        if (field.equals(contribution.getName())) {
                            ConceptPath current = contribution;
                            while (tokenizer.hasMoreTokens()) {
                                String token = tokenizer.nextToken();
                                current = resolveConcept(current, token);
                            }
                            concept = current;
                            break;
                        }
                    }
                }
            }
        }
        return concept;
    }

    /**
     * Resolve concept path for the formal parameter specified by the path which
     * is in the specified Process Interface.
     * <p>
     * 
     * @param procIfc
     * @param dataPath
     * 
     * @return Concept path for the formal parameter specified by the path which
     *         is in the specified Process Interface.
     */
    public static ConceptPath resolveConceptPath(ProcessInterface procIfc,
            String dataPath) {

        return resolveConceptPath(procIfc, dataPath, false);
    }

    /**
     * 
     * Resolve concept path for the formal parameter specified by the path which
     * is in the specified Process Interface.
     * <p>
     * 
     * @param procIfc
     * @param dataPath
     * @param showArrayChildren
     * 
     * @return Concept path for the formal parameter specified by the path which
     *         is in the specified Process Interface.
     * 
     */
    public static ConceptPath resolveConceptPath(ProcessInterface procIfc,
            String dataPath, boolean showArrayChildren) {
        ConceptPath concept = null;
        if (dataPath != null && procIfc != null) {
            StringTokenizer tokenizer = new StringTokenizer(dataPath, "."); //$NON-NLS-1$
            if (tokenizer.hasMoreTokens()) {

                String field = tokenizer.nextToken();
                ProcessRelevantData data = resolveParameter(procIfc, field);

                if (data != null) {
                    Class clss = getConceptClass(data);
                    ConceptPath current = new ConceptPath(data, clss);

                    current.setIncludeChi1ldrenOfArrays(showArrayChildren);

                    while (tokenizer.hasMoreTokens()) {
                        String token = tokenizer.nextToken();
                        if (null != resolveConcept(current, token)) {
                            current = resolveConcept(current, token);
                        }
                    }
                    concept = current;
                }

            }
        }
        return concept;
    }

    /**
     * Resolve parameter in the specified Process Interface.
     * 
     * @param proc
     * @param field
     * @return
     */
    private static ProcessRelevantData resolveParameter(
            ProcessInterface procIfc, String field) {

        List<FormalParameter> allParams = new ArrayList<FormalParameter>();

        allParams.addAll(procIfc.getFormalParameters());

        return ProcessDataUtil.findFieldInList(allParams, field);
    }

    public static ConceptPath resolveConceptPath(InterfaceMethod method,
            String path) {
        ConceptPath concept = null;
        if (path != null) {
            if (path.startsWith("process.")) { //$NON-NLS-1$
                path = path.substring(8);
            }
            StringTokenizer tokenizer = new StringTokenizer(path, "/"); //$NON-NLS-1$
            if (tokenizer.hasMoreTokens()) {
                String field = tokenizer.nextToken();
                ProcessRelevantData data = resolveParameter(method, field);
                if (data != null) {
                    Class clss = getConceptClass(data);
                    ConceptPath current = new ConceptPath(data, clss);
                    while (tokenizer.hasMoreTokens()) {
                        String token = tokenizer.nextToken();
                        current = resolveConcept(current, token);
                    }
                    concept = current;
                }
            }
        }
        return concept;
    }

    public static ConceptPath resolveContributedConceptPath(Activity activity,
            String path) {
        ConceptPath concept = null;
        if (path.startsWith("process.")) { //$NON-NLS-1$
            path = path.substring(8);
        }
        StringTokenizer tokenizer = new StringTokenizer(path, "/"); //$NON-NLS-1$
        if (tokenizer.hasMoreTokens()) {
            String field = tokenizer.nextToken();
            Process process = activity.getProcess();
            if (process != null) {
                String context = DataFieldContributor.CONTEXT_ALL_POSSIBLE;
                if (ProcessScriptUtil.isBwService(activity)) {
                    context = DataFieldContributor.CONTEXT_BW_SERVICE_TASK;
                }
                Collection<ConceptPath> contributions =
                        getContributedFields(process, context);
                for (ConceptPath contribution : contributions) {
                    if (field.equalsIgnoreCase(contribution.getName())) {
                        ConceptPath current = contribution;
                        while (tokenizer.hasMoreTokens()) {
                            String token = tokenizer.nextToken();
                            current = resolveConcept(current, token);
                        }
                        concept = current;
                        break;
                    }
                }
            }
        }
        return concept;
    }

    /**
     * @param activity
     *            The activity.
     * @param name
     *            The parameter name to resolve.
     * @return The parameter.
     */
    public static ProcessRelevantData resolveParameter(Activity activity,
            String name) {
        if (name.startsWith("process.")) { //$NON-NLS-1$
            name = name.substring(8);
        }
        return ProcessDataUtil.resolveData(activity, name);
    }

    /**
     * @param process
     *            The process.
     * @param name
     *            The parameter name to resolve.
     * @return The parameter.
     */
    public static ProcessRelevantData resolveParameter(Process xpdlProcess,
            String name) {
        if (name.startsWith("process.")) { //$NON-NLS-1$
            name = name.substring(8);
        }
        return ProcessDataUtil.resolveData(xpdlProcess, name);
    }

    /**
     * @param activity
     *            The interface method.
     * @param name
     *            The parameter name to resolve.
     * @return The parameter.
     */
    public static ProcessRelevantData resolveParameter(
            InterfaceMethod interfaceMethod, String name) {
        if (name.startsWith("process.")) { //$NON-NLS-1$
            name = name.substring(8);
        }
        return ProcessDataUtil.resolveData(interfaceMethod, name);
    }

    /**
     * @param current
     * @param token
     * @return
     */
    private static ConceptPath resolveConcept(ConceptPath current, String token) {
        ConceptPath concept = null;
        if (current != null) {
            for (ConceptPath child : current.getChildren()) {
                if (child != null) {
                    concept = resolveConceptPathChild(child, token);
                    if (concept != null) {
                        break;
                    }

                }
            }
        }
        return concept;
    }

    private static ConceptPath resolveConceptPathChild(ConceptPath child,
            String token) {
        ConceptPath concept = null;
        if (child instanceof ChoiceConceptPath) {
            for (ConceptPath cp : child.getChildren()) {

                if (token.equals(cp.getName())) {
                    concept = cp;
                    break;
                } else if (cp.isArray()) {
                    String tokenBase = getBaseName(token);
                    if (tokenBase != null && tokenBase.equals(cp.getName())) {
                        if (cp.getChildren() != null
                                && child.getChildren().size() > 0) {
                            ConceptPath deepChild =
                                    cp.getChildren().iterator().next();
                            int tokenPosition = getPosition(token, true);
                            if (tokenPosition >= 0 && deepChild != null) {
                                ConceptPath newConcept = deepChild.getCopy();
                                newConcept.setIndex(tokenPosition);
                                concept = newConcept;
                                break;
                            }
                        }
                    }
                }
            }
        } else {
            if (token.equals(child.getName())) {
                concept = child;
            } else if (child.isArray()) {
                String tokenBase = getBaseName(token);
                if (tokenBase != null && tokenBase.equals(child.getName())) {
                    int tokenPosition = getPosition(token, true);
                    if (tokenPosition >= 0) {
                        ConceptPath newConcept =
                                new ConceptPath(child, child.getItem(),
                                        child.getType(), tokenPosition);
                        concept = newConcept;
                    }
                }
            }
        }
        return concept;
    }

    /**
     * @param element
     *            The element name.
     * @return The array index position.
     */
    public static int getPosition(String element, boolean zeroBased) {
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

    public static Class getConceptClass(ProcessRelevantData data) {
        if (data != null) {
            Object baseType =
                    BasicTypeConverterFactory.INSTANCE.getBaseType(data, false);
            if (baseType instanceof Class) {
                return (Class) baseType;
            }
        }
        return null;
    }

    private static final Object referenceToComplexDataTypeModel(
            ComplexDataTypeReference complexDataTypeReference, IProject project) {

        String location = complexDataTypeReference.getLocation();

        location = getSpecialFolderRelativeURI(location, project);
        IResource res = getResource(location, project);
        if (res != null) {
            WorkingCopy wCopy =
                    XpdResourcesPlugin.getDefault().getWorkingCopy(res);

            if (wCopy != null) {
                EObject eExternalRef =
                        wCopy.getRootElement().eResource()
                                .getEObject(complexDataTypeReference.getXRef());

                if (isCorrectEObjectType(eExternalRef)) {
                    return eExternalRef;
                }
            }
        }

        return null;
    }

    protected static String getSpecialFolderRelativeURI(String location,
            IProject project) {
        // If the location URI starts with the following string then
        // remove it from the string
        if (location.startsWith(PLATFORM_RESOURCE)) {
            location = location.substring(PLATFORM_RESOURCE.length() + 1);
            // And remove the next 2 fragments which will be the project and
            // special folder
            int idx = location.indexOf(URI_SEPARATOR);
            if (idx >= 0) {
                int lastIndex = location.indexOf(URI_SEPARATOR, idx + 1);
                location = location.substring(lastIndex + 1);
            }
        } else {
            // Check if first part is project name.
        }
        return location;
    }

    /**
     * 
     * @param relativePath
     *            it is the path relative to the special folder
     * @param project
     * @return
     */
    protected static IResource getResource(String relativePath, IProject project) {
        IFile resolveSpecialFolderRelativePath =
                SpecialFolderUtil.resolveSpecialFolderRelativePath(project,
                        getSpecialFolderKind(),
                        relativePath);
        if (project.isOpen() && resolveSpecialFolderRelativePath == null) {
            try {
                for (IProject referenced : project.getReferencedProjects()) {
                    resolveSpecialFolderRelativePath =
                            SpecialFolderUtil
                                    .resolveSpecialFolderRelativePath(referenced,
                                            getSpecialFolderKind(),
                                            relativePath);
                    if (resolveSpecialFolderRelativePath != null) {
                        break;
                    }
                }
            } catch (CoreException e) {
                Xpdl2ProcessEditorPlugin.getDefault().getLogger().error(e);
            }
        }
        return resolveSpecialFolderRelativePath;
    }

    /**
     * The SpecialFolder kind for the concept folder
     */
    public static final String BOM_SPECIAL_FOLDER_KIND = "bom"; //$NON-NLS-1$

    /**
     * 
     */
    protected static String getSpecialFolderKind() {
        return BOM_SPECIAL_FOLDER_KIND;
    }

    protected static boolean isCorrectEObjectType(Object complexDataType) {
        if (complexDataType instanceof org.eclipse.uml2.uml.Class) {
            return true;
        }

        return false;
    }

    public static Collection<ConceptPath> getContributedFields(Process process,
            String context) {
        Set<ConceptPath> fields = new HashSet<ConceptPath>();
        ensureContributorsLoaded();
        Set<String> destinations =
                DestinationUtil.getEnabledValidationDestinations(process);
        for (String destination : destinations) {
            List<DataFieldContributor> contributors =
                    fieldContributors.get(destination);
            if (contributors != null) {
                for (DataFieldContributor contributor : contributors) {
                    fields.addAll(contributor.getDataFields(context));
                }
            }
        }
        return fields;
    }

    /**
     * 
     */
    private static void ensureContributorsLoaded() {
        if (fieldContributors == null) {
            fieldContributors =
                    new HashMap<String, List<DataFieldContributor>>();
            IExtensionPoint extensionPoint =
                    Platform.getExtensionRegistry()
                            .getExtensionPoint(Xpdl2ProcessEditorPlugin.ID,
                                    "dataFieldContributor"); //$NON-NLS-1$
            IConfigurationElement[] configs =
                    extensionPoint.getConfigurationElements();
            for (IConfigurationElement config : configs) {
                try {
                    Object contributor =
                            config.createExecutableExtension("class"); //$NON-NLS-1$
                    String processDestination =
                            config.getAttribute("processDestinationId"); //$NON-NLS-1$
                    if (contributor instanceof DataFieldContributor
                            && processDestination != null
                            && processDestination.length() != 0) {
                        List<DataFieldContributor> contributors =
                                fieldContributors.get(processDestination);
                        if (contributors == null) {
                            contributors =
                                    new ArrayList<DataFieldContributor>();
                            fieldContributors.put(processDestination,
                                    contributors);
                        }
                        contributors.add((DataFieldContributor) contributor);
                    }
                } catch (CoreException e) {
                    Xpdl2ProcessEditorPlugin.getDefault().getLogger().error(e);
                }
            }
        }
    }

    /**
     * @param path
     * @return
     */
    public static boolean isArray(ConceptPath path) {
        boolean array = false;
        if (path != null) {
            Object item = path.getItem();
            if (item instanceof ProcessRelevantData) {
                ProcessRelevantData prd = (ProcessRelevantData) item;
                array = prd.isIsArray();
            }
        }
        return array;
    }

    /**
     * Given a process data field / formal parameter, convert it to a concept
     * path.
     * 
     * @param data
     * @return ConceptPath representing given data
     */
    public static ConceptPath getConceptPath(ProcessRelevantData data) {
        Class clss = ConceptUtil.getConceptClass(data);
        return new ConceptPath(data, clss);
    }

    public static List<ConceptPath> getConceptParentHierarchy(
            ConceptPath conceptPath, List<ConceptPath> parentList) {
        if (parentList == null) {
            parentList = new ArrayList<ConceptPath>();
        }
        if (conceptPath != null) {
            // Add the element to the list
            parentList.add(conceptPath);
            ConceptPath parent = conceptPath.getParent();
            if (parent != null) {
                ConceptUtil.getConceptParentHierarchy(conceptPath.getParent(),
                        parentList);
            }
        }
        return parentList;
    }

    public static List<ConceptPath> getConceptPathChildren(
            ConceptPath conceptPath) {
        return getConceptPathChildren(conceptPath, false);
    }

    /**
     * 
     * @param conceptPath
     * @param showChildAttribtuesForArrayTypes
     *            <code>true</code> if you wish child attributes of array type
     *            objects returned (not array elements though, just child
     *            properties of the type)
     * @return children
     */
    public static List<ConceptPath> getConceptPathChildren(
            ConceptPath conceptPath, boolean showChildAttribtuesForArrayTypes) {
        if (conceptPath != null) {
            List<ConceptPath> attributes = new ArrayList<ConceptPath>();
            List<org.eclipse.uml2.uml.DataType> allUnionMemberTypes =
                    new ArrayList<org.eclipse.uml2.uml.DataType>();

            Classifier type = conceptPath.getType();
            if (type instanceof PrimitiveType) {
                org.eclipse.uml2.uml.DataType dt =
                        (org.eclipse.uml2.uml.DataType) type;

                /*
                 * Sid ACE-194 - we don't support XSD based BOMs in ACE
                 */

                boolean isUnion = false;
                // XSDUtil.isUnion(dt);
                // if (isUnion) {
                // List<org.eclipse.uml2.uml.DataType> unionMemberTypes =
                // XSDUtil.getUnionMemberTypes(dt);
                // allUnionMemberTypes.addAll(unionMemberTypes);
                // }
            }

            // Multiplicity not considered for choices.
            if (!showChildAttribtuesForArrayTypes
                    && (conceptPath.getItem() instanceof Property || conceptPath
                    // XPD-3531 should not be possible to map to individual
                    // elements of an array.
                            .getItem() instanceof ProcessRelevantData)
                    && conceptPath.isArrayHeader()) {
                /*
                 * SID XPD-1666: Do not return the first element instance in
                 * arrays for BOM/Field data, this was never implemented
                 * particularly well or consistently and AMX BPM (the only
                 * destination that can handle array javascript doesn't handle
                 * mapping to / from individual array elements.
                 * 
                 * Property property = (Property) conceptPath.getItem(); Type
                 * propertyType = property.getType(); Class clss = null; if
                 * (propertyType instanceof Class) { clss = (Class)
                 * propertyType; } if (shouldIncludeAttribute(clss,
                 * conceptPath)) { attributes.add(new ConceptPath(conceptPath,
                 * conceptPath .getItem(), conceptPath.getType(), 0)); }
                 */
            }
            /*
             * Sid XPD-3597. Do not continue if the class is a proxy reference
             * to a BM that does not exist, if it is then everything inside it
             * will be null!
             */
            else if (conceptPath.getType() != null
                    && !conceptPath.getType().eIsProxy()) {

                List<Property> allAttributes = new ArrayList<Property>();
                if (type instanceof Class) {
                    Object itemType = null;
                    if (conceptPath.getItem() instanceof ProcessRelevantData) {
                        ProcessRelevantData prd =
                                (ProcessRelevantData) conceptPath.getItem();
                        itemType = prd.getDataType();

                    }
                    // XPD-5766: Case Ref types should not show the attributes
                    // of its BOM Type.
                    if (!(itemType instanceof RecordType)) {
                        Class cl = (Class) type;

                        /*
                         * The representation of XSD constructs by BOM classes
                         * means that there are now some specific conditions as
                         * to what attributes should be collected from super
                         * classes.
                         */
                        /* XPD-8147: Additional param for collectAllClassAttribtues() */
                        collectAllClassAttributes(cl, allAttributes, false);
                    }

                } else {
                    allAttributes.addAll(type.getAllAttributes());
                }

                if (allAttributes != null) {
                    for (Object next : allAttributes) {
                        if (next instanceof Property) {
                            Property property = (Property) next;

                            /**
                             * Sid XPD-7891: Do not show associations in
                             * mappers. It is not allowed to do direct
                             * assignments of these.
                             */
                            if (isAssociationNotCompositionProperty(property)) {
                                continue; /* Next property */
                            }

                            Type propertyType = property.getType();
                            Classifier clss = null;
                            if (propertyType instanceof Class) {
                                clss = (Class) propertyType;
                            } else if (propertyType instanceof PrimitiveType) {
                                clss = (PrimitiveType) propertyType;
                            } else if (propertyType instanceof Enumeration) {
                                clss = (Enumeration) propertyType;
                            }

                            if (shouldIncludeAttribute(clss, conceptPath)) {

                                /*
                                 * Sid ACE-194 - we don't support XSD based BOMs
                                 * in ACE
                                 */
                                // boolean isXsdChoice = false;
                                // XSDUtil.isPropertyXsdChoice(property);
                                //
                                // if (isXsdChoice) {
                                // /**
                                // * If the property is equivalent to an
                                // * xsd:choice - it is better to wrap this up
                                // * in the <code>ChoiceConceptPath</code> and
                                // * let it deal with it.
                                // */
                                // ChoiceConceptPath choiceConceptPath =
                                // new ChoiceConceptPath(conceptPath,
                                // property);
                                // choiceConceptPath
                                // .setIncludeChi1ldrenOfArrays(showChildAttribtuesForArrayTypes);
                                //
                                // if (!attributes.contains(choiceConceptPath))
                                // {
                                // attributes.add(choiceConceptPath);
                                // }
                                // } else
                                {
                                    /*
                                     * XPD-2128: add if the property is not a
                                     * union member type
                                     */
                                    if (!allUnionMemberTypes
                                            .contains(propertyType)) {

                                        ConceptPath cp =
                                                new ConceptPath(conceptPath,
                                                        property, clss);
                                        cp.setIncludeChi1ldrenOfArrays(showChildAttribtuesForArrayTypes);
                                        attributes.add(cp);
                                    }
                                }
                            }

                        }
                    }
                }
                // attributes = filterAttributes(attributes);
            }
            Collections.sort(attributes, conceptPathComparator);
            return attributes;
        }
        return Collections.emptyList();
    }

    /**
     * Check if the given property is an associaiton reference that is not a
     * composition.
     * 
     * @param propertyType
     * 
     * @return <code>true</code> if the given property is an associaiton
     *         reference that is not a composition.
     */
    public static boolean isAssociationNotCompositionProperty(Property property) {

        boolean isAssociationRatherThanComposition = false;

        if (null != property.getAssociation()) {

            /*
             * We know it's an association of some kind, Will reset to false if
             * we find it's a composition one.
             */
            isAssociationRatherThanComposition = true;

            Association association = property.getAssociation();
            List<Property> memberEnds = association.getMemberEnds();

            for (Property memProperty : memberEnds) {

                if (memProperty.getAggregation() == AggregationKind.COMPOSITE_LITERAL) {
                    isAssociationRatherThanComposition = false;
                    break;
                }
            }
        }

        return isAssociationRatherThanComposition;
    }

    /**
     * XPD-8147 amended and commented.
     * Collect all of the class properties including ones from base classes that
     * are generalised by this class.
     * 
     * @param cl
     * @param allAttributes
     * @param processingComplexTypeRestriction
     *            <code>true</code> if the class properties are being collected
     *            from super-classes that are inherited via an xsd:restriction.
     *            In this case, then only super-class attributes are collected
     *            (from the initial point of restriction down).
     * 
     */
    public static void collectAllClassAttributes(Class cl,
            List<Property> allAttributes,
            boolean processingComplexTypeRestriction) {

        /**
         * XPD-8147 ComplexType's that are xsd restrictions of another
         * complexType (the 'supertype') include only the *elements* they
         * explicitly define (in the subtype).
         * 
         * Hence we USED TO ignore everything in supertypes if the subtype was
         * 'by restriction' rather than 'by extension'.
         * 
         * **HOWEVER** according to w3c, the **attributes** of the
         * restriction-base-type (the supertype) are always inherited and DO NOT
         * have to be explicitly defined in the subtype...
         * 
         * <pre>
         * https://www.w3.org/TR/xmlschema-0/#DerivByRestrict
         * 
         * Section 4.4 "... However, attribute declarations do not need to be repeated in the derived 
         *              type definition; in this example, RestrictedPurchaseOrderType will inherit the orderDate 
         *              attribute declaration from PurchaseOrderType."
         * </pre>
         */

        EList<Property> ownedAttributes = cl.getOwnedAttributes();

        for (Iterator iterator = ownedAttributes.iterator(); iterator.hasNext();) {
            Property ownedProperty = (Property) iterator.next();

            if (processingComplexTypeRestriction) {
                /*
                 * Sid ACE-194 - we don't support XSD based BOMs in ACE
                 */
                //
                // /*
                // * For complextype that is a restricted subtype of a basetype
                // * then only include attributes (elements have to be
                // explicitly
                // * declared in top level subtype).
                // */
                // if (XSDUtil.isPropertyXsdAttribute(ownedProperty)) {
                // /*
                // * Only include if not already included (explcitly defined
                // * and changed in subtype above this supertype.
                // */
                // boolean isDefinedAlready = false;
                // for (Property existingProperty : allAttributes) {
                // if (existingProperty.getName()
                // .equals(ownedProperty.getName())) {
                // isDefinedAlready = true;
                // break;
                // }
                // }
                //
                // if (!isDefinedAlready) {
                // allAttributes.add(ownedProperty);
                // }
                // }

            } else {
                /*
                 * If it's the top level class or an subtype by extension then
                 * collect everything.
                 */
                allAttributes.add(ownedProperty);
            }
        }

        /**
         * Once we have started 'collecting super-class attributes inherited via
         * an XSD restriction' mode we should continue with that regardless of
         * how any super-super types are configured (as extension or
         * restriction).
         */
        /*
         * Sid ACE-194 - we don't support XSD based BOMs in ACE
         */
        //
        // if (!processingComplexTypeRestriction) {
        // processingComplexTypeRestriction =
        // XSDUtil.isClassXsdComplexTypeRestriction(cl);
        // }

        // Collect attributes and recurse if necessary
        EList<Classifier> generals = cl.getGenerals();
        if (!generals.isEmpty()) {
            Classifier classifier = generals.get(0);
            if (classifier instanceof Class) {
                collectAllClassAttributes((Class) classifier,
                        allAttributes,
                        processingComplexTypeRestriction);
            }
        }
    }

    /**
     * @param choiceConceptPath
     * @return
     */
    public static List<ConceptPath> getConceptPathChildren(
            ChoiceConceptPath choiceConceptPath) {
        /*
         * Sid ACE-194 - we don't support XSD based BOMs in ACE - nothing should
         * ever attempt to construct this.
         */
        throw new RuntimeException(
                "Unexpected construction of ChoiceConceptPath as no support for WSDL.");

        //
        // String choiceGroup = choiceConceptPath.getChoiceGroup();
        // Classifier type = choiceConceptPath.getParent().getType();
        //
        // List<ConceptPath> attributes = new ArrayList<ConceptPath>();
        // List<Property> allAttributes = new ArrayList<Property>();
        //
        // /*
        // * XPD-3647: if a complex type restriction has a group/choice mappings
        // * section must handle it correctly
        // */
        // if (type instanceof Class) {
        // Class cl = (Class) type;
        // /*
        // * The representation of XSD constructs by BOM classes means that
        // * there are now some specific conditions as to what attributes
        // * should be collected from super classes.
        // */
        // /* XPD-8147: Additional param for collectAllClassAttribtues() */
        // collectAllClassAttributes(cl, allAttributes, false);
        //
        // } else {
        // allAttributes.addAll(type.getAllAttributes());
        // }
        //
        // if (allAttributes != null) {
        // for (Object next : allAttributes) {
        // if (next instanceof Property) {
        // Property property = (Property) next;
        // Type propertyType = property.getType();
        // Classifier clss = null;
        // if (propertyType instanceof Class) {
        // clss = (Class) propertyType;
        // } else if (propertyType instanceof PrimitiveType) {
        // clss = (PrimitiveType) propertyType;
        // } else if (propertyType instanceof Enumeration) {
        // clss = (Enumeration) propertyType;
        // }
        // /*
        // * Sid XPD-3597. Do not continue if the class is a proxy
        // * reference to a BM that does not exist, if it is then
        // * everything inside it will be null!
        // */
        // if (clss != null && !clss.eIsProxy()) {
        // if (shouldIncludeAttribute(clss, choiceConceptPath)) {
        // String xsdExplictGroupHierarchy =
        // getRootOfExplicitChoiceHierarchy(XSDUtil
        // .getXsdExplictGroupHierarchy(property));
        // if (choiceGroup.equals(xsdExplictGroupHierarchy)) {
        // attributes.add(new ConceptPath(
        // choiceConceptPath, property, clss));
        // }
        // }
        // }
        // }
        // }
        // // attributes = filterAttributes(attributes);
        // }
        // Collections.sort(attributes, conceptPathComparator);
        // return attributes;

    }

    /**
     * For Javascript, a choice within a choice makes absolutely no sense. It is
     * better to flatten the list and present it to the user.
     * 
     * i.e. this accounts for a choice that contains a choice as one of it's
     * choices - effectively all items within the sub-choice are part of the
     * same parent choice group
     * 
     * @param xsdExplicitGroupHierarcy
     * @return
     */
    public static String getRootOfExplicitChoiceHierarchy(
            String xsdExplicitGroupHierarcy) {

        /*
         * If there are nested choices all the values hanging of them are part
         * of the root choice (they all belong to the same choice).
         * 
         * So for instance if there is a sequence with a choice that has A, B,
         * and a sub-choice that has X,Y then there group hierarchy string from
         * BOM will be:
         * 
         * - A = S1:C1
         * 
         * - B = S1:C1
         * 
         * - X = S1:C1:C2
         * 
         * - Y = S1:C1:C2
         * 
         * So all we need to do is return the first Choice ('C') part segment of
         * the string and we can use that as the choice group id.
         */

        if (xsdExplicitGroupHierarcy != null) {
            StringTokenizer strTkn =
                    new StringTokenizer(xsdExplicitGroupHierarcy,
                            COLON_DELIMITER);
            while (strTkn.hasMoreTokens()) {
                String tok = strTkn.nextToken();

                if (tok.startsWith("C") || tok.startsWith("c")) { //$NON-NLS-1$ //$NON-NLS-2$
                    return tok;
                }
            }
        }

        return ""; //$NON-NLS-1$
    }

    private static Comparator<ConceptPath> conceptPathComparator =
            new Comparator<ConceptPath>() {

                @Override
                public int compare(ConceptPath conceptPath1,
                        ConceptPath conceptPath2) {
                    int value = 0;
                    if (conceptPath1.isAttribute()
                            && conceptPath2.isAttribute()) {
                        value =
                                conceptPath1.getName()
                                        .compareTo(conceptPath2.getName());
                    } else if (!conceptPath1.isAttribute()
                            && !conceptPath2.isAttribute()) {
                        value =
                                conceptPath1.getName()
                                        .compareTo(conceptPath2.getName());
                    } else if (!conceptPath1.isAttribute()
                            && conceptPath2.isAttribute()) {
                        value = 1;
                    } else if (conceptPath1.isAttribute()
                            && !conceptPath2.isAttribute()) {
                        value = -1;
                    }
                    return value;
                }

            };

    /**
     * @param attributes
     * @return
     */
    private static List<ConceptPath> filterAttributes(
            List<ConceptPath> attributes) {
        List<ConceptPath> filteredAttributes = new ArrayList<ConceptPath>();
        Map<String, ConceptPath> filteredMap =
                new HashMap<String, ConceptPath>();
        for (ConceptPath attrib : attributes) {
            String propertyName = null;
            if (attrib.getItem() instanceof Property) {
                Property property = (Property) attrib.getItem();
                propertyName = property.getName();
            }
            if (propertyName != null
                    && !filteredMap.keySet().contains(propertyName)) {
                filteredMap.put(propertyName, attrib);
            } else {
                // check hierarchy for most recent ancestor
            }
        }

        if (!filteredMap.isEmpty()) {
            for (String key : filteredMap.keySet()) {
                filteredAttributes.add(filteredMap.get(key));
            }
        }
        if (filteredAttributes.isEmpty()) {
            filteredAttributes = attributes;
        }

        return filteredAttributes;
    }

    /**
     * Sid: Came across this method that was uncommented <b>I think</b> that
     * it's purpose is to ensure against recursive cycles in the type hierarchy.
     * 
     * @param umlClass
     * @param conceptPath
     * 
     * @return (I think) <code>true</code> if the umlClass type does not already
     *         appear in the parent hierarchy.
     */
    public static boolean shouldIncludeAttribute(Classifier umlClass,
            ConceptPath conceptPath) {
        if (conceptPath != null) {
            ConceptPath parent = conceptPath.getParent();
            if (conceptPath.isArrayItem()) {
                parent = conceptPath.getParent().getParent();
            }
            List<ConceptPath> conceptPathHierarchy =
                    ConceptUtil.getConceptParentHierarchy(parent,
                            new ArrayList<ConceptPath>());
            if (conceptPathHierarchy != null && !conceptPathHierarchy.isEmpty()) {
                for (ConceptPath conceptPathAncestor : conceptPathHierarchy) {
                    if (conceptPathAncestor != null
                            && conceptPathAncestor.getType() != null
                            && umlClass != null
                            && conceptPathAncestor.getType().equals(umlClass)) {
                        return false;
                    }
                }
            }

            return true;
        }
        return false;
    }

    /**
     * Returns true if the given conceptPath is root
     * 
     * @param conceptPath
     * @return
     */
    public static boolean isRoot(ConceptPath conceptPath) {
        if (conceptPath != null) {
            ConceptPath root = conceptPath.getRoot();
            if (root != null && root.equals(conceptPath)) {
                return true;
            }
        }
        return false;
    }

    /*
     * SID XPD-1666: Do not expand arrays for BOM/Field data, this was never
     * implemented particularly well or consistently and AMX BPM (the only
     * destination that can handle array javascript doesn't handle mapping to /
     * from individual array elements.
     * 
     * REMOVED expandArrays(List<ConceptPath> children, Activity activity,
     * List<DataMapping> mappings, Object parentElement); AND ASSOCIATED LOCAL
     * METHODS.
     */

    /**
     * @param cp
     * @param type
     * @return SubType name of Object type primitive type if that subtype is
     *         Any, AntType, AnySimpleType or AnyAttribute. Otherwise returns ""
     */
    public static String getObjectSubType(ConceptPath cp, Classifier type) {
        String subType = null;

        if (type instanceof PrimitiveType) {

            PrimitiveType srcPrimType = (PrimitiveType) type;
            PrimitiveType basePrimitiveType =
                    PrimitivesUtil.getBasePrimitiveType(srcPrimType);

            if (basePrimitiveType != null) {
                ResourceSet rSet =
                        XpdResourcesPlugin.getDefault().getEditingDomain()
                                .getResourceSet();

                /* Confirm that the base type is Object */
                if (basePrimitiveType == PrimitivesUtil
                        .getStandardPrimitiveTypeByName(rSet,
                                PrimitivesUtil.BOM_PRIMITIVE_OBJECT_NAME)) {

                    Object item = cp.getItem();
                    if (item instanceof Property) {
                        Property property = (Property) item;
                        Object value =
                                PrimitivesUtil
                                        .getFacetPropertyValue(srcPrimType,
                                                PrimitivesUtil.BOM_PRIMITIVE_FACET_OBJECT_SUBTYPE,
                                                property);

                        if (value != null
                                && value instanceof EnumerationLiteral) {
                            String litName =
                                    ((EnumerationLiteral) value).getName();

                            if (litName
                                    .equals(PrimitivesUtil.OBJECT_SUBTYPE_XSD_ANYSIMPLETYPE)
                                    || litName
                                            .equals(PrimitivesUtil.OBJECT_SUBTYPE_XSD_ANY)
                                    || litName
                                            .equals(PrimitivesUtil.OBJECT_SUBTYPE_XSD_ANYATTRIBUTE)
                                    || litName
                                            .equals(PrimitivesUtil.OBJECT_SUBTYPE_XSD_ANYTYPE)) {
                                subType = litName;
                            }
                        }
                    } else if (item instanceof FormalParameter) {
                        /*
                         * XPD-2253: if it is a top level element then item is
                         * not an instance of Property but instance of formal
                         * parameter. get the object sub type for a classifier
                         */
                        FormalParameter param = (FormalParameter) item;
                        if (param.getDataType() instanceof ExternalReference) {
                            Object clss =
                                    ConceptUtil.getExternalRefClass(param);
                            if (clss instanceof Classifier) {
                                Classifier classifier = (Classifier) clss;
                                if (classifier instanceof PrimitiveType) {
                                    String objectSubType =
                                            ConceptUtil
                                                    .getObjectSubType(classifier);
                                    if (null != objectSubType
                                            && objectSubType.length() > 0) {
                                        return objectSubType;
                                    }
                                }
                                if (classifier != null) {
                                    return classifier.getName();
                                }
                            }
                        }
                    }
                }
            }
        }

        return subType != null ? subType : ""; //$NON-NLS-1$
    }

    /**
     * 
     * @param property
     * @return
     */
    public static String getObjectSubType(Property property) {
        String subType = null;

        // Determine the base primitive type. This is one of the BOM base types.
        Type type = property.getType();

        if (type instanceof PrimitiveType) {
            PrimitiveType pt = (PrimitiveType) type;
            PrimitiveType basePrimitiveType =
                    PrimitivesUtil.getBasePrimitiveType(pt);

            if (basePrimitiveType != null) {
                ResourceSet rSet =
                        XpdResourcesPlugin.getDefault().getEditingDomain()
                                .getResourceSet();

                // Confirm that the base type is Object
                if (basePrimitiveType == PrimitivesUtil
                        .getStandardPrimitiveTypeByName(rSet,
                                PrimitivesUtil.BOM_PRIMITIVE_OBJECT_NAME)) {

                    Object value =
                            PrimitivesUtil
                                    .getFacetPropertyValue(pt,
                                            PrimitivesUtil.BOM_PRIMITIVE_FACET_OBJECT_SUBTYPE,
                                            property);

                    if (value != null && value instanceof EnumerationLiteral) {
                        String litName = ((EnumerationLiteral) value).getName();

                        if (litName
                                .equals(PrimitivesUtil.OBJECT_SUBTYPE_XSD_ANYSIMPLETYPE)
                                || litName
                                        .equals(PrimitivesUtil.OBJECT_SUBTYPE_XSD_ANY)
                                || litName
                                        .equals(PrimitivesUtil.OBJECT_SUBTYPE_XSD_ANYATTRIBUTE)
                                || litName
                                        .equals(PrimitivesUtil.OBJECT_SUBTYPE_XSD_ANYTYPE)) {
                            subType = litName;
                        }
                    }
                }
            }
        }
        return subType != null ? subType : ""; //$NON-NLS-1$
    }

    /**
     * @param classifier
     */
    public static String getObjectSubType(Classifier classifier) {
        String subType = null;
        if (classifier instanceof PrimitiveType) {
            Object value =
                    PrimitivesUtil
                            .getFacetPropertyValue((PrimitiveType) classifier,
                                    PrimitivesUtil.BOM_PRIMITIVE_FACET_OBJECT_SUBTYPE);

            if (value != null && value instanceof EnumerationLiteral) {
                String litName = ((EnumerationLiteral) value).getName();

                if (litName
                        .equals(PrimitivesUtil.OBJECT_SUBTYPE_XSD_ANYSIMPLETYPE)
                        || litName
                                .equals(PrimitivesUtil.OBJECT_SUBTYPE_XSD_ANY)
                        || litName
                                .equals(PrimitivesUtil.OBJECT_SUBTYPE_XSD_ANYATTRIBUTE)
                        || litName
                                .equals(PrimitivesUtil.OBJECT_SUBTYPE_XSD_ANYTYPE)) {
                    subType = litName;
                }
            }
        }

        return subType != null ? subType : ""; //$NON-NLS-1$;
    }

    /**
     * 
     * @param classifier
     * @return
     */
    public static String getUnionLabel(Classifier classifier) {
        /*
         * Sid ACE-194 - we don't support XSD based BOMs in ACE - nothing should
         * ever attempt to construct this.
         */

        // if (classifier instanceof PrimitiveType) {
        // org.eclipse.uml2.uml.DataType dt =
        // (org.eclipse.uml2.uml.DataType) classifier;
        // boolean isUnion = XSDUtil.isUnion(dt);
        // if (isUnion) {
        // String text = classifier.getName();
        // List<DataType> unionMemberTypes =
        // XSDUtil.getUnionMemberTypes(dt);
        // if (unionMemberTypes.size() > 0) {
        // text += " (Union: "; //$NON-NLS-1$
        // for (DataType dataType : unionMemberTypes) {
        // text += dataType.getName() + ", "; //$NON-NLS-1$;
        // }
        // text = text.substring(0, text.length() - 2);
        // text += ")"; //$NON-NLS-1$
        // }
        // return text;
        // }
        // }
        return null;
    }

    public static Object getExternalRefClass(FormalParameter param) {
        Object clss = null;
        if (param.getDataType() instanceof ExternalReference) {
            ExternalReference extRef = (ExternalReference) param.getDataType();
            ComplexDataTypeReference complex =
                    new ComplexDataTypeReference(extRef.getLocation(),
                            extRef.getXref(), extRef.getNamespace());
            /*
             * Cannot do a WorkingCopyUtil on this data object because the //
             * data is not contained in the eResource.
             */
            // IProject project = WorkingCopyUtil.getProjectFor(data);
            Object extAttribs =
                    Xpdl2ModelUtil.getOtherElement(param,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_ExtendedAttributes());
            if (extAttribs instanceof XpdExtAttributes) {
                EObject inList =
                        EMFSearchUtil
                                .findInList(((XpdExtAttributes) extAttribs)
                                        .getAttributes(),
                                        XpdExtensionPackage.eINSTANCE
                                                .getXpdExtAttribute_Name(),
                                        "Project"); //$NON-NLS-1$

                if (inList instanceof XpdExtAttribute) {
                    String projName = ((XpdExtAttribute) inList).getValue();
                    IProject project =
                            ResourcesPlugin.getWorkspace().getRoot()
                                    .getProject(projName);
                    if (project != null) {
                        clss =
                                ConceptUtil
                                        .getComplexDataTypeClassfier(complex,
                                                project);
                    }
                }
            }
        }
        return clss;
    }
}
