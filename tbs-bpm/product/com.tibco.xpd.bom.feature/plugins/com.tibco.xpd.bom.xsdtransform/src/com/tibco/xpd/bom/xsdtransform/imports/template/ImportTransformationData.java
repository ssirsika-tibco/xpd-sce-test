/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.bom.xsdtransform.imports.template;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.Type;
import org.openarchitectureware.emf.EcoreUtil2;
import org.openarchitectureware.xsd.XMLReaderImpl;
import org.openarchitectureware.xsd.util.XSDUtil;

import com.tibco.xpd.bom.resources.utils.UML2ModelUtil;
import com.tibco.xpd.bom.xsdtransform.Activator;
import com.tibco.xpd.bom.xsdtransform.imports.progress.Xsd2BomMonitorMessage;
import com.tibco.xpd.bom.xsdtransform.internal.BaseBOMXtendTransformer;
import com.tibco.xpd.bom.xsdtransform.internal.resources.BomTransformContext;
import com.tibco.xpd.bom.xsdtransform.utils.NamespaceURIToJavaPackageMapper;

/**
 * Data class used in the export transformation of BOM to WSDL/XSD. This will be
 * passed to the OAW transformation.
 * 
 * @author njpatel
 * 
 */
public class ImportTransformationData {

    private BomTransformContext ctx;

    private BaseBOMXtendTransformer baseTransformer;

    private Map<String, EList<Stereotype>> elementStereotypes =
            new HashMap<String, EList<Stereotype>>();

    private Map<String, Object> uniqueIds = new HashMap<String, Object>();

    private final List<Type> packagedElements = UML2ModelUtil.getPrimitives();

    private List<Association> createdCompositions =
            new ArrayList<Association>();

    private List<Object> schemaTypes = new ArrayList<Object>();

    private List<Object> wsdlSchemaTypes = new ArrayList<Object>();

    private String sourceLocation = null;

    private Resource diagramResource = null;

    private Model resultModel = null;

    private Resource xsdProfileResource = null;

    private File sourceFile = null;

    private IFolder destinationFolder = null;

    /*
     * SID XPD-1741: DOn't need a list of source locations because we resolve
     * import schema locations properly.
     */

    private Map<Model, Resource> modelResourceMap =
            new HashMap<Model, Resource>();

    private Map<String, HashMap<String, Object>> elementStereotypeValues =
            new HashMap<String, HashMap<String, Object>>();

    private Object parentPrefixMap = null;

    private List<Model> otherResultModels = new ArrayList<Model>();

    private List<String> attrUndeclaredForms = new ArrayList<String>();

    private List<String> elemUndeclaredForms = new ArrayList<String>();

    private Map<String, String> innerWSDLSchemaPrefixMap =
            new HashMap<String, String>();

    private Map<Object, String> attributeTypeLocalPartsMap =
            new HashMap<Object, String>();

    private Map<Object, String> simpleTypeBaseMap =
            new HashMap<Object, String>();

    private Map<Object, String> attributeSimpleTypeBaseMap =
            new HashMap<Object, String>();

    private Map<Object, String> elementTypeLocalPartsMap =
            new HashMap<Object, String>();

    private Map<Object, String> elementComplexContentLocalPartsMap =
            new HashMap<Object, String>();

    private Map<Object, String> elementSimpleContentLocalPartsMap =
            new HashMap<Object, String>();

    private Map<Object, String> elementSimpleTypeBaseMap =
            new HashMap<Object, String>();

    private Map<Object, String> attributeRefLocalPartsMap =
            new HashMap<Object, String>();

    private Map<Object, String> elementRefLocalPartsMap =
            new HashMap<Object, String>();

    private Map<Object, String> complexTypeNamesMap =
            new HashMap<Object, String>();

    private Map<Object, String> simpleTypeNamesMap =
            new HashMap<Object, String>();

    private Map<Object, String> anyTypeNamesMap = new HashMap<Object, String>();

    private Map<Object, String> anyAttributeNamesMap =
            new HashMap<Object, String>();

    private Map<Object, String> elementNamesMap = new HashMap<Object, String>();

    private Map<Object, String> attributeNamesMap =
            new HashMap<Object, String>();

    private List<String> xsdTypesArrayList = new ArrayList<String>();

    /** Resource set used by the XML reader to load OAW resources. */
    private ResourceSet oawResourceSet = new ResourceSetImpl();

    /* XPD-4034: changed it from String to EObject */
    private List<EObject> parsedTopLevelElementSchemas =
            new ArrayList<EObject>();

    private List<Object> alreadyRenamedSchemaTypes = new ArrayList<Object>();

    private List<String> anonUnionSimpleTypeNames = new ArrayList<String>();

    private Map<Object, String> parsedAnonUnionSimpleTypes =
            new HashMap<Object, String>();

    private Map<Object, Object> topLevelConstructs =
            new HashMap<Object, Object>();

    private Map<Object, Map<Object, Object>> xsdSequences =
            new HashMap<Object, Map<Object, Object>>();

    private Map<Object, Object> xsdGroupRefParents =
            new HashMap<Object, Object>();

    /**
     * SID XPD-1741: List representing the stack of nested schema import/include
     * locations that has been done up to this point.
     * <p>
     * Each time Xsd2Bom.ext 'steps down into' another schema/wsdl for an import
     * or include then the the absolute/relative schemaLocation is added to the
     * end of the stack (and removed again when that schema transform is
     * complete.
     * <p>
     * Therefore if you have a stack of schema includes like...
     * <p>
     * 
     * <pre>
     * c:/temp/A.wsdl
     *   import &quot;subFolder/B.wsdl&quot;
     *   
     *   c:/temp/subFolder/B.wsdl
     *      import &quot;C.wsdl&quot;
     *      
     *      c:/temp/subFolder/C.wsdl
     * </pre>
     * 
     * When importing/including C.wsdl we must treat ITS schemalocation as
     * relative to B.wsdl not A.wsdl.
     * 
     */
    private List<URI> importSchemaLocationStack = new ArrayList<URI>();

    private Xsd2BomMonitorMessage monitorMessages = new Xsd2BomMonitorMessage();

    /**
     * Sid XPD-5079 Cache of all the schema objects read via readXML(keyed on
     * schema files complete file location URI)
     */
    private Map<URI, EObject> readXMLCache = new HashMap<URI, EObject>();

    public ImportTransformationData(IProgressMonitor monitor,
            BaseBOMXtendTransformer transformer) {
        this();
        baseTransformer = transformer;
        monitorMessages.setParentMonitor(monitor);
    }

    public ImportTransformationData(IProgressMonitor monitor) {
        this();
        monitorMessages.setParentMonitor(monitor);
    }

    public ImportTransformationData() {
        ctx = new BomTransformContext(this);
    }

    /**
     * @return the monitorMessages
     */
    public Xsd2BomMonitorMessage getMonitor() {
        return monitorMessages;
    }

    /**
     * Get the transformation editing domain.
     * 
     * @return
     */
    public TransactionalEditingDomain getEditingDomain() {
        return Activator.getDefault().getTransformationEditingDomain();
    }

    /**
     * Get the resourceset used to store OAW resources.
     * 
     * @return
     */
    public ResourceSet getOAWResourceSet() {
        return oawResourceSet;
    }

    /**
     * @param elementStereotypes
     */
    public void setElementStereotypes(
            Map<String, EList<Stereotype>> elementStereotypes) {
        this.elementStereotypes = elementStereotypes;
    }

    /**
     * @return
     */
    public Map<String, EList<Stereotype>> getElementStereotypes() {
        return elementStereotypes;
    }

    /**
     * @param uniqueIds
     */
    public void setUniqueIds(Map<String, Object> uniqueIds) {
        this.uniqueIds = uniqueIds;
    }

    /**
     * @return
     */
    public Map<String, Object> getUniqueIds() {
        return uniqueIds;
    }

    /**
     * @return
     */
    public List<Type> getPackagedElements() {
        return packagedElements;
    }

    /**
     * @param createdCompositions
     */
    public void setCreatedCompositions(List<Association> createdCompositions) {
        this.createdCompositions = createdCompositions;
    }

    /**
     * @return
     */
    public List<Association> getCreatedCompositions() {
        return createdCompositions;
    }

    /**
     * @param schemaTypes
     */
    public void setSchemaTypes(List<Object> schemaTypes) {
        this.schemaTypes = schemaTypes;
    }

    /**
     * @return
     */
    public List<Object> getSchemaTypes() {
        return schemaTypes;
    }

    /**
     * @param schemaTypes
     */
    public void setParsedTopLevelElementSchemas(
            List<EObject> parsedTopLevelElementSchemas) {
        this.parsedTopLevelElementSchemas = parsedTopLevelElementSchemas;
    }

    /**
     * @return
     */
    public List<String> getAnonUnionSimpleTypeNames() {
        return anonUnionSimpleTypeNames;
    }

    /**
     * @param schemaTypes
     */
    public void setAnonUnionSimpleTypeNames(
            List<String> anonUnionSimpleTypeNames) {
        this.anonUnionSimpleTypeNames = anonUnionSimpleTypeNames;
    }

    public void addParsedAnonUnionSimpleType(Object simpleType,
            String anonUnionSimpleTypeName) {
        parsedAnonUnionSimpleTypes.put(simpleType, anonUnionSimpleTypeName);
    }

    /**
     * @return
     */
    public Set<Object> getParsedAnonUnionSimpleTypeKeys() {
        return parsedAnonUnionSimpleTypes.keySet();
    }

    /**
     * @return
     */
    public String getParsedAnonUnionSimpleTypeValue(Object key) {
        return parsedAnonUnionSimpleTypes.get(key);
    }

    /**
     * @return
     */
    public List<EObject> getParsedTopLevelElementSchemas() {
        return parsedTopLevelElementSchemas;
    }

    /**
     * @return
     */
    public List<Object> getAlreadyRenamedSchemaTypes() {
        return alreadyRenamedSchemaTypes;
    }

    /**
     * @param wsdlSchemaTypes
     */
    public void setWsdlSchemaTypes(List<Object> wsdlSchemaTypes) {
        this.wsdlSchemaTypes = wsdlSchemaTypes;
    }

    /**
     * @return
     */
    public List<Object> getWsdlSchemaTypes() {
        return wsdlSchemaTypes;
    }

    /**
     * Set the location of where the source file to import is.
     * 
     * @param sourceLocation
     */
    public void setSourceLocation(String sourceLocation) {
        this.sourceLocation = sourceLocation;
    }

    /**
     * Get the location of where the source file to import is.
     * 
     * @return
     */
    public String getSourceLocation() {
        return sourceLocation;
    }

    /**
     * Set the BOM Diagram resource that will be produced by this import.
     * 
     * @param diagramResource
     */
    public void setDiagramResource(Resource diagramResource) {
        this.diagramResource = diagramResource;
    }

    /**
     * Get the BOM Diagram resource that will be produced by this import.
     * 
     * @return
     */
    public Resource getDiagramResource() {
        return diagramResource;
    }

    /**
     * @param xsdProfileResource
     */
    public void setXsdProfileResource(Resource xsdProfileResource) {
        this.xsdProfileResource = xsdProfileResource;
    }

    /**
     * @return
     */
    public Resource getXsdProfileResource() {
        return xsdProfileResource;
    }

    /**
     * Set the source file that is being imported.
     * 
     * @param sourceFile
     */
    public void setSourceFile(File sourceFile) {
        this.sourceFile = sourceFile;
    }

    /**
     * Get the source file that is being imported.
     * 
     * @return
     */
    public File getSourceFile() {
        return sourceFile;
    }

    /**
     * @param modelResourceMap
     */
    public void setModelResourceMap(Map<Model, Resource> modelResourceMap) {
        this.modelResourceMap = modelResourceMap;
    }

    /**
     * @return
     */
    public Map<Model, Resource> getModelResourceMap() {
        return modelResourceMap;
    }

    /**
     * @param elementStereotypeValues
     */
    public void setElementStereotypeValues(
            Map<String, HashMap<String, Object>> elementStereotypeValues) {
        this.elementStereotypeValues = elementStereotypeValues;
    }

    /**
     * @return
     */
    public Map<String, HashMap<String, Object>> getElementStereotypeValues() {
        return elementStereotypeValues;
    }

    /**
     * @param parentPrefixMap
     */
    public void setParentPrefixMap(Object parentPrefixMap) {
        this.parentPrefixMap = parentPrefixMap;
    }

    /**
     * @return
     */
    public Object getParentPrefixMap() {
        return parentPrefixMap;
    }

    /**
     * @param otherResultModels
     */
    public void setOtherResultModels(List<Model> otherResultModels) {
        this.otherResultModels = otherResultModels;
    }

    /**
     * @return
     */
    public List<Model> getOtherResultModels() {
        return otherResultModels;
    }

    /**
     * @param attrUndeclaredForms
     */
    public void setAttrUndeclaredForms(List<String> attrUndeclaredForms) {
        this.attrUndeclaredForms = attrUndeclaredForms;
    }

    /**
     * @return
     */
    public List<String> getAttrUndeclaredForms() {
        return attrUndeclaredForms;
    }

    /**
     * @param elemUndeclaredForms
     */
    public void setElemUndeclaredForms(List<String> elemUndeclaredForms) {
        this.elemUndeclaredForms = elemUndeclaredForms;
    }

    /**
     * @return
     */
    public List<String> getElemUndeclaredForms() {
        return elemUndeclaredForms;
    }

    /**
     * @param obj
     * @param localPart
     */
    public void addNewAttributeType(Object obj, String localPart) {
        attributeTypeLocalPartsMap.put(obj, localPart);
    }

    /**
     * @param obj
     * @param localPart
     */
    public void addNewSimpleTypeBase(Object obj, String localPart) {
        simpleTypeBaseMap.put(obj, localPart);
    }

    /**
     * @param obj
     * @param localPart
     */
    public void addNewAttributeSimpleTypeBase(Object obj, String localPart) {
        attributeSimpleTypeBaseMap.put(obj, localPart);
    }

    /**
     * @param obj
     * @param localPart
     */
    public void addNewElementType(Object obj, String localPart) {
        elementTypeLocalPartsMap.put(obj, localPart);
    }

    /**
     * @param obj
     * @param localPart
     */
    public void addNewElementSimpleTypeBase(Object obj, String localPart) {
        elementSimpleTypeBaseMap.put(obj, localPart);
    }

    /**
     * @param obj
     * @param localPart
     */
    public void addNewElementComplexContent(Object obj, String localPart) {
        elementComplexContentLocalPartsMap.put(obj, localPart);
    }

    /**
     * @param obj
     * @param localPart
     */
    public void addNewElementSimpleContent(Object obj, String localPart) {
        elementSimpleContentLocalPartsMap.put(obj, localPart);
    }

    /**
     * @return
     */
    public Map<Object, String> getAttributeTypeLocalPartsMap() {
        return attributeTypeLocalPartsMap;
    }

    /**
     * @return
     */
    public Map<Object, String> getSimpleTypeBaseMap() {
        return simpleTypeBaseMap;
    }

    /**
     * @return
     */
    public Map<Object, String> getAttributeSimpleTypeBaseMap() {
        return attributeSimpleTypeBaseMap;
    }

    /**
     * @return
     */
    public Map<Object, String> getElementTypeLocalPartsMap() {
        return elementTypeLocalPartsMap;
    }

    /**
     * @return
     */
    public Map<Object, String> getElementSimpleTypeBaseMap() {
        return elementSimpleTypeBaseMap;
    }

    /**
     * @return
     */
    public Map<Object, String> getElementComplexContentLocalPartsMap() {
        return elementComplexContentLocalPartsMap;
    }

    /**
     * @return
     */
    public Map<Object, String> getElementSimpleContentLocalPartsMap() {
        return elementSimpleContentLocalPartsMap;
    }

    /**
     * @param obj
     * @param localPart
     */
    public void addNewAttributeRef(Object obj, String localPart) {
        attributeRefLocalPartsMap.put(obj, localPart);
    }

    /**
     * @return
     */
    public Map<Object, String> getAttributeRefLocalPartsMap() {
        return attributeRefLocalPartsMap;
    }

    /**
     * @param obj
     * @param localPart
     */
    public void addNewElementRef(Object obj, String localPart) {
        elementRefLocalPartsMap.put(obj, localPart);
    }

    /**
     * @return
     */
    public Map<Object, String> getElementRefLocalPartsMap() {
        return elementRefLocalPartsMap;
    }

    /**
     * @param obj
     * @param name
     */
    public void addNewTopLevelConstruct(Object constructObj, Object resultObj2) {
        topLevelConstructs.put(constructObj, resultObj2);
    }

    /**
     * @return
     */
    public Object getTopLevelConstruct(Object constructObj) {
        Object tleConstruct = topLevelConstructs.get(constructObj);
        return tleConstruct;
    }

    private Set<String> getAllNames() {
        Set<String> values = new HashSet<String>();
        values.addAll(complexTypeNamesMap.values());
        values.addAll(simpleTypeNamesMap.values());
        values.addAll(attributeNamesMap.values());
        values.addAll(elementNamesMap.values());
        values.addAll(anyTypeNamesMap.values());
        values.addAll(anyAttributeNamesMap.values());
        return values;
    }

    /**
     * @param obj
     * @param name
     */
    public String addNewComplexName(Object obj, String name, String postfix) {
        Set<String> values = getAllNames();
        String newName = getUniqueName(values, name, postfix);
        complexTypeNamesMap.put(obj, newName);
        return newName;
    }

    /**
     * @param obj
     * @param name
     */
    public String addNewSimpleName(Object obj, String name, String postfix) {
        Set<String> values = getAllNames();
        String newName = getUniqueName(values, name, postfix);
        simpleTypeNamesMap.put(obj, newName);
        return newName;
    }

    private String getUniqueName(Collection<String> values, String name,
            String postfix) {
        String newName = name + postfix;
        if (values.contains(newName)) {
            return getUniqueName(values, name, ("*" + postfix)); //$NON-NLS-1$
        } else {
            return newName;
        }
    }

    /**
     * @return
     */
    public Map<Object, String> getComplexTypeNamesMap() {
        return complexTypeNamesMap;
    }

    /**
     * @return
     */
    public Map<Object, String> getSimpleTypeNamesMap() {
        return simpleTypeNamesMap;
    }

    /**
     * @param obj
     * @param name
     */
    public String addNewAnyTypeName(Object obj, String name, String postfix) {
        Set<String> values = getAllNames();
        String newName = getUniqueName(values, name, postfix);
        anyTypeNamesMap.put(obj, newName);
        return newName;
    }

    /**
     * @return
     */
    public Map<Object, String> getAnyTypeNamesMap() {
        return anyTypeNamesMap;
    }

    /**
     * @param obj
     * @param name
     */
    public String addNewAnyAttributeName(Object obj, String name, String postfix) {
        Set<String> values = getAllNames();
        String newName = getUniqueName(values, name, postfix);
        anyAttributeNamesMap.put(obj, newName);
        return newName;
    }

    /**
     * @return
     */
    public Map<Object, String> getAnyAttributeNamesMap() {
        return anyAttributeNamesMap;
    }

    /**
     * @param obj
     * @param name
     */
    public String addNewElementName(Object obj, String name, String postfix) {
        Set<String> values = getAllNames();
        String newName = getUniqueName(values, name, postfix);
        elementNamesMap.put(obj, newName);
        return newName;
    }

    /**
     * @return
     */
    public Map<Object, String> getElementNamesMap() {
        return elementNamesMap;
    }

    /**
     * @param obj
     * @param name
     */
    public String addNewAttributeName(Object obj, String name, String postfix) {
        Set<String> values = getAllNames();
        String newName = getUniqueName(values, name, postfix);
        attributeNamesMap.put(obj, newName);
        return newName;
    }

    /**
     * @return
     */
    public Map<Object, String> getAttributeNamesMap() {
        return attributeNamesMap;
    }

    /**
     * @param xsdTypesArrayList
     */
    public void setXsdTypesArrayList(List<String> xsdTypesArrayList) {
        this.xsdTypesArrayList = xsdTypesArrayList;
    }

    /**
     * @return
     */
    public List<String> getXsdTypesArrayList() {
        return xsdTypesArrayList;
    }

    /**
     * 
     */
    public void initialiseFormDefaultsAndWSDLSchemaPrefixMaps() {
        attrUndeclaredForms.clear();
        elemUndeclaredForms.clear();
        getInnerWSDLSchemaPrefixMap().clear();
    }

    /**
     * @return
     */
    public Map<String, String> getInnerWSDLSchemaPrefixMap() {
        return innerWSDLSchemaPrefixMap;
    }

    /**
     * @param simpleTypes
     * @param simpleNames
     * @param attributes
     * @param attrNames
     * @return
     */
    public Set<Object> changeSimpleNameIfSameAsAttribute(
            List<Object> simpleTypes, List<String> simpleNames,
            List<Object> attributes, List<String> attrNames) {
        int currentIdx = 0;
        for (Object currentAttr : attributes) {
            int tmpIdx = 0;
            for (Object parseSimple : simpleTypes) {
                if (getAttributeName(currentAttr, attrNames.get(currentIdx))
                        .equals(getSimpleName(parseSimple,
                                simpleNames.get(tmpIdx)))) {
                    addNewSimpleName(parseSimple,
                            getSimpleName(parseSimple, simpleNames.get(tmpIdx)),
                            "_type"); //$NON-NLS-1$
                    break;
                }
                tmpIdx++;
            }
            currentIdx++;
        }
        return simpleTypeNamesMap.keySet();
    }

    /**
     * @param simpleTypes
     * @param simpleNames
     * @param elements
     * @param elemNames
     * @return
     */
    public Set<Object> changeSimpleNameIfSameAsElement(
            List<Object> simpleTypes, List<String> simpleNames,
            List<Object> elements, List<String> elemNames) {
        int currentIdx = 0;
        for (Object currentElem : elements) {
            int tmpIdx = 0;
            for (Object parseSimple : simpleTypes) {
                if (getElementName(currentElem, elemNames.get(currentIdx))
                        .equals(getSimpleName(parseSimple,
                                simpleNames.get(tmpIdx)))) {
                    addNewSimpleName(parseSimple,
                            getSimpleName(parseSimple, simpleNames.get(tmpIdx)),
                            "_type"); //$NON-NLS-1$
                    break;
                }
                tmpIdx++;
            }
            currentIdx++;
        }
        return simpleTypeNamesMap.keySet();
    }

    /**
     * @param complexTypes
     * @param complexNames
     * @param attributes
     * @param attrNames
     * @return
     */
    public Set<Object> changeComplexNameIfSameAsAttribute(
            List<Object> complexTypes, List<String> complexNames,
            List<Object> attributes, List<String> attrNames) {
        int currentIdx = 0;
        for (Object currentAttr : attributes) {
            int tmpIdx = 0;
            for (Object parseComplex : complexTypes) {
                if (getAttributeName(currentAttr, attrNames.get(currentIdx))
                        .equals(getComplexName(parseComplex,
                                complexNames.get(tmpIdx)))) {
                    addNewComplexName(parseComplex,
                            getComplexName(parseComplex,
                                    complexNames.get(tmpIdx)),
                            "_type"); //$NON-NLS-1$
                    break;
                }
                tmpIdx++;
            }
            currentIdx++;
        }
        return complexTypeNamesMap.keySet();
    }

    /**
     * @param complexTypes
     * @param complexNames
     * @param elements
     * @param elemNames
     * @return
     */
    public Set<Object> changeComplexNameIfSameAsElement(
            List<Object> complexTypes, List<String> complexNames,
            List<Object> elements, List<String> elemNames) {
        int currentIdx = 0;
        for (Object currentElem : elements) {
            int tmpIdx = 0;
            for (Object parseComplex : complexTypes) {
                if (getElementName(currentElem, elemNames.get(currentIdx))
                        .equals(getComplexName(parseComplex,
                                complexNames.get(tmpIdx)))) {
                    addNewComplexName(parseComplex,
                            getComplexName(parseComplex,
                                    complexNames.get(tmpIdx)),
                            "_type"); //$NON-NLS-1$
                    break;
                }
                tmpIdx++;
            }
            currentIdx++;
        }
        return complexTypeNamesMap.keySet();
    }

    /**
     * @param elements
     * @param elemNames
     * @param attributes
     * @param attrNames
     * @return
     */
    public Set<Object> changeAttributesIfSameName(List<Object> elements,
            List<String> elemNames, List<Object> attributes,
            List<String> attrNames) {
        int currentIdx = 0;
        for (Object currentElement : elements) {
            int tmpIdx = 0;
            for (Object parseAttr : attributes) {
                if (getElementName(currentElement, elemNames.get(currentIdx))
                        .equals(getAttributeName(parseAttr,
                                attrNames.get(tmpIdx)))) {
                    addNewAttributeName(currentElement,
                            getAttributeName(parseAttr, attrNames.get(tmpIdx)),
                            "*"); //$NON-NLS-1$
                    break;
                }
                tmpIdx++;
            }
            currentIdx++;
        }
        return elementNamesMap.keySet();
    }

    /**
     * @param attribute
     * @param origName
     * @return
     */
    private String getAttributeName(Object attribute, String origName) {
        String tmpName = attributeNamesMap.get(attribute);
        return tmpName == null ? origName : tmpName;
    }

    /**
     * @param complexTypes
     * @param complexNames
     * @return
     */
    public Set<Object> changeComplexNamesIfSame(List<Object> complexTypes,
            List<String> complexNames) {
        int currentIdx = 0;
        for (Object currentComplex : complexTypes) {
            boolean found = false;
            int tmpIdx = 0;
            for (Object parseComplex : complexTypes) {
                if (!parseComplex.equals(currentComplex)) {
                    if (getComplexName(currentComplex,
                            complexNames.get(currentIdx))
                            .equals(getComplexName(parseComplex,
                                    complexNames.get(tmpIdx)))) {
                        found = true;
                        break;
                    }
                }
                tmpIdx++;
            }
            if (found) {
                addNewComplexName(currentComplex,
                        getComplexName(currentComplex,
                                complexNames.get(currentIdx)),
                        "_type"); //$NON-NLS-1$
            }
            currentIdx++;
        }
        return complexTypeNamesMap.keySet();
    }

    /**
     * @param complex
     * @param origName
     * @return
     */
    private String getComplexName(Object complex, String origName) {
        String tmpName = complexTypeNamesMap.get(complex);
        return tmpName == null ? origName : tmpName;
    }

    /**
     * @param attributes
     * @param attrNames
     * @return
     */
    public Set<Object> changeAttributeNamesIfSame(List<Object> attributes,
            List<String> attrNames) {
        int currentIdx = 0;
        for (Object currentAttr : attributes) {
            boolean found = false;
            int tmpIdx = 0;
            for (Object parseAttr : attributes) {
                if (!parseAttr.equals(currentAttr)) {
                    if (getAttributeName(currentAttr, attrNames.get(currentIdx))
                            .equals(getAttributeName(parseAttr,
                                    attrNames.get(tmpIdx)))) {
                        found = true;
                        break;
                    }
                }
                tmpIdx++;
            }
            if (found) {
                addNewAttributeName(currentAttr,
                        getAttributeName(currentAttr, attrNames.get(currentIdx)),
                        "*"); //$NON-NLS-1$
            }
            currentIdx++;
        }
        return attributeNamesMap.keySet();
    }

    /**
     * @param elements
     * @param elemNames
     * @return
     */
    public Set<Object> changeElementNamesIfSame(List<Object> elements,
            List<String> elemNames) {
        int currentIdx = 0;
        for (Object currentElement : elements) {
            boolean found = false;
            int tmpIdx = 0;
            for (Object parseElem : elements) {
                if (!parseElem.equals(currentElement)) {
                    if (getElementName(currentElement,
                            elemNames.get(currentIdx))
                            .equals(getElementName(parseElem,
                                    elemNames.get(tmpIdx)))) {
                        found = true;
                        break;
                    }
                }
                tmpIdx++;
            }
            if (found) {
                addNewElementName(currentElement,
                        getElementName(currentElement,
                                elemNames.get(currentIdx)),
                        "*"); //$NON-NLS-1$
            }
            currentIdx++;
        }
        return elementNamesMap.keySet();
    }

    /**
     * @param element
     * @param origName
     * @return
     */
    private String getElementName(Object element, String origName) {
        String tmpName = elementNamesMap.get(element);
        return tmpName == null ? origName : tmpName;
    }

    /**
     * @param simpleTypes
     * @param simpleNames
     * @return
     */
    public Set<Object> simpleChangeNameIfSame(List<Object> simpleTypes,
            List<String> simpleNames) {
        int currentIdx = 0;
        for (Object currentSimple : simpleTypes) {
            boolean found = false;
            int tmpIdx = 0;
            for (Object parseSimple : simpleTypes) {
                if (!parseSimple.equals(currentSimple)) {
                    if (getSimpleName(currentSimple,
                            simpleNames.get(currentIdx))
                            .equals(getSimpleName(parseSimple,
                                    simpleNames.get(tmpIdx)))) {
                        found = true;
                        break;
                    }
                }
                tmpIdx++;
            }
            if (found) {
                addNewSimpleName(currentSimple,
                        getSimpleName(currentSimple,
                                simpleNames.get(currentIdx)),
                        "_type"); //$NON-NLS-1$
            }
            currentIdx++;
        }
        return simpleTypeNamesMap.keySet();
    }

    /**
     * @param simple
     * @param origName
     * @return
     */
    private String getSimpleName(Object simple, String origName) {
        String tmpName = simpleTypeNamesMap.get(simple);
        return tmpName == null ? origName : tmpName;
    }

    /**
     * SID XPD-1741: List representing the stack of nested schema import/include
     * locations that has been done up to this point.
     * <p>
     * Each time Xsd2Bom.ext 'steps down into' another schema/wsdl for an import
     * or include then the the absolute/relative schemaLocation is added to the
     * end of the stack (and removed again when that schema transform is
     * complete.
     * <p>
     * Therefore if you have a stack of schema includes like...
     * <p>
     * 
     * <pre>
     * c:/temp/A.wsdl
     *   import &quot;subFolder/B.wsdl&quot;
     *   
     *   c:/temp/subFolder/B.wsdl
     *      import &quot;C.wsdl&quot;
     *      
     *      c:/temp/subFolder/C.wsdl
     * </pre>
     * 
     * When importing/including C.wsdl we must treat ITS schemalocation as
     * relative to B.wsdl not A.wsdl.
     * <p>
     * The base schema location is always provided by the sourceFile's URI.
     * <p>
     * When transformation of the import schema is complete then the URI must be
     * popped back of of the stack using {@link #popImportSchemaLocation()}
     * 
     * @param importOrIncludeSchemaLocation
     *            The import or include schema's schemaLocation (this may be
     *            relative to the previous object on stack)
     * 
     */
    public void pushImportSchemaLocation(String importOrIncludeSchemaLocation) {
        if (importOrIncludeSchemaLocation != null
                && importOrIncludeSchemaLocation.length() > 0) {
            URI importOrIncludeURI =
                    URI.createURI(importOrIncludeSchemaLocation, true);

            importSchemaLocationStack.add(importOrIncludeURI);
        }
        return;
    }

    /**
     * Pop the last import/include schema locatrion off of the end of the
     * schemaLocaiton stack (see {@link #pushImportSchemaLocation(String)})
     */
    public void popImportSchemaLocation() {
        int lastEntryIdx = importSchemaLocationStack.size() - 1;

        if (lastEntryIdx >= 0) {
            importSchemaLocationStack.remove(lastEntryIdx);
        }
        return;
    }

    /**
     * Get the current URI that can be used as the base for any import/include.
     * This is the URI of the wsdl/xsd file CURERNTLY being transformed
     * (releactive import schemaLocations are relative to THIS file NOT the file
     * at the top of a multi-nested import hierarchy.
     * 
     * 
     * @return schemaLocation URI
     */
    public URI getCurrentBaseURIFromImportsAndIncludes() {

        /*
         * Starting with the source file as the baseURI the resolve each level
         * in the stack of imports/includes
         */
        URI nextBaseURI = URI.createFileURI(sourceFile.getAbsolutePath());

        for (URI nextImportSchemaLocationLevel : importSchemaLocationStack) {

            /*
             * Reset the next import schema location with the current as the
             * base.
             * 
             * This will remove everything after the common prefix part and
             * append the new bit to the end.
             * 
             * Therefore if base is c:\temp\A.xsd and import locaiton is B.xsd
             * it will treat c:\temp\ as the common part and replace A.xsd with
             * B.xsd.
             * 
             * If left is c:\temp\A.xsd and import is c:\XXX\B.xsd the result
             * should be c:\XXX\B.xsd
             */
            nextBaseURI = nextImportSchemaLocationLevel.resolve(nextBaseURI);
        }

        return nextBaseURI;
    }

    /**
     * Create an Object -> String map that can be used for passing information
     * around the OWE ext methods.
     * 
     * @return Map
     */
    public HashMap<Object, String> createObjectToStringMap() {
        return new HashMap<Object, String>();
    }

    /**
     * a map to store wsdl definitions to namespace
     * 
     * @return Map
     */
    public HashMap<Object, String> createWsdlDefToNSMap() {
        return new HashMap<Object, String>();
    }

    /**
     * Get the destination folder of the generated BOMs.
     * 
     * @return
     */
    public IFolder getDestinationFolder() {
        return destinationFolder;
    }

    /**
     * Set the destination folder of the generated BOMs.
     * 
     * @param destinationFolder
     */
    public void setDestinationFolder(IFolder destinationFolder) {
        this.destinationFolder = destinationFolder;
    }

    public Object getSequenceForExplicitGroup(Object explicitGroup, Class cls) {
        Map<Object, Object> map = xsdSequences.get(cls);
        if (map != null) {
            return map.get(explicitGroup);
        }
        return null;
    }

    public void addXsdSequences(Object explicitGroup, Object sequenceCls,
            Class cls) {
        Map<Object, Object> map = xsdSequences.get(cls);
        if (map == null) {
            map = new HashMap<Object, Object>();
            xsdSequences.put(cls, map);
        }
        map.put(explicitGroup, sequenceCls);
    }

    public Object getGroupRefParent(Object topGroupExplicitGroup) {
        Iterator<Entry<Object, Object>> iterator =
                xsdGroupRefParents.entrySet().iterator();
        while (iterator.hasNext()) {
            Entry<Object, Object> entry = iterator.next();
            if (topGroupExplicitGroup.equals(entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }

    public void removeGroupRefParent(Object parentGroupRefExplicitGroup) {
        xsdGroupRefParents.remove(parentGroupRefExplicitGroup);
    }

    public void addGroupRefParent(Object parentGroupRefExplicitGroup,
            Object topGroupExplicitGroup) {
        xsdGroupRefParents.put(parentGroupRefExplicitGroup,
                topGroupExplicitGroup);
    }

    public List<Object> reverse(List<Object> objects) {
        Collections.reverse(objects);
        return objects;
    }

    /**
     * @return the ctx
     */
    public BomTransformContext getCtx() {
        return ctx;
    }

    /**
     * @param ctx
     *            the ctx to set
     */
    public void setCtx(BomTransformContext ctx) {
        this.ctx = ctx;
    }

    /**
     * @return the importSchemaLocationStack
     */
    public List<URI> getImportSchemaLocationStack() {
        return importSchemaLocationStack;
    }

    /**
     * @param importSchemaLocationStack
     *            the importSchemaLocationStack to set
     */
    public void setImportSchemaLocationStack(List<URI> importSchemaLocationStack) {
        this.importSchemaLocationStack = importSchemaLocationStack;
    }

    /**
     * @return the baseTransformer
     */
    public BaseBOMXtendTransformer getBaseTransformer() {
        return baseTransformer;
    }

    /**
     * Load the model from the given schema at the source uri. Uses
     * {@link XMLReaderImpl} to load the resource.
     * <p>
     * 
     * Sid XPD-5079. Moved this method from TransformHelper because we can't
     * rely on OAW function return caching to return the same schema BECAUSE we
     * do not pass the same arguments to teh Xsd2Bom.ext.resolveAndReadXML
     * function we sometimes pass the import/include statement referencing the
     * schema. This was ultimately causing multiple loads on schema and
     * therefore multiple entries in {@link #schemaTypes}. THAT means MULTIPLE
     * completely different sets of DynamicEObject's for the same schemas THAT
     * means that any kind of caching / re-use based on source objects could
     * FAIL.
     * 
     * @param baseUri
     *            the base uri to resolve the source uri against
     * @param srcLocation
     *            the source location URI of the schema file
     * @param useDocumentRoot
     *            set the use document root flag in the {@link XMLReaderImpl}.
     * @return the OAW resource schema document root if successful,
     *         <code>null</code> otherwise.
     */
    public EObject readXML(URI baseUri, String srcLocation,
            boolean useDocumentRoot) {
        /*
         * The wsdl:definitions element for WSDL
         * 
         * The xsd:schema for XSD
         * 
         * OR the document root object if absolute document root of model
         * requested.
         */
        EObject schemaOrWsdlDefOrDocumentRoot = null;

        ResourceSet rSet = getOAWResourceSet();

        if (rSet != null && baseUri != null && srcLocation != null) {

            URI schemaCacheURI = getSchemaURI(baseUri, srcLocation);

            schemaOrWsdlDefOrDocumentRoot =
                    getCachedReadXMLForSchemaURI(schemaCacheURI);

            if (schemaOrWsdlDefOrDocumentRoot != null) {
                TransformHelper
                        .traceMe(String
                                .format("** ImportTansformationData.readXML() RE-USING schema srcLocation '%s' for baseURI '%s' (URI: %s)", //$NON-NLS-1$
                                        srcLocation,
                                        baseUri,
                                        schemaCacheURI.toString()));

                return schemaOrWsdlDefOrDocumentRoot;
            }

            TransformHelper
                    .traceMe(String
                            .format("** ImportTansformationData.readXML() LOADING schema srcLocation '%s' for baseURI '%s' (URI: %s)", //$NON-NLS-1$
                                    srcLocation,
                                    baseUri,
                                    schemaCacheURI.toString()));

            XMLReaderImpl reader =
                    new XMLReaderImpl(rSet,
                            BaseBOMXtendTransformer.getXsdMetaModel());
            reader.getOptions().put(XMLResource.OPTION_RECORD_UNKNOWN_FEATURE,
                    Boolean.TRUE);
            reader.getOptions().put(XMLResource.OPTION_ENCODING, "UTF-8"); //$NON-NLS-1$
            reader.setUseDocumentRoot(useDocumentRoot);
            /*
             * Need to set base URI otherwise the load takes a very long time as
             * it tries to resolve the uri by checking if the file exists and
             * loading the contents to see if it's empty or not!
             * 
             * Sid XPD-1741: Provided tha the base URI is the URI of the file
             * that is doing the import/include then the setURI can be the
             * relative / absolute URL in the schemaLocaiton and the XMLReader
             * will resolve the URI correctly.
             */
            reader.setBaseURI(baseUri);
            reader.setUri(srcLocation);
            schemaOrWsdlDefOrDocumentRoot = reader.readXML();

            if (schemaOrWsdlDefOrDocumentRoot != null) {
                /*
                 * XPD-5118 - If we are asked for documentRoot then we need to
                 * get the actual schema out of it rather than the
                 * XsdSchemaDocumentRoot dynamic Eobject that reader will return
                 * in that case.
                 */
                EObject schemaOrWsdlDefToCache = null;
                if (useDocumentRoot) {
                    Collection<EObject> allContents =
                            EcoreUtil2
                                    .allContents(schemaOrWsdlDefOrDocumentRoot);

                    for (EObject element : allContents) {
                        String elementName = element.eClass().getName();

                        if (srcLocation.endsWith(".wsdl")) { //$NON-NLS-1$
                            if ("TDefinitions".equals(elementName)) { //$NON-NLS-1$
                                schemaOrWsdlDefToCache = element;
                                break;
                            }

                        } else {
                            if ("SchemaType".equals(elementName)) { //$NON-NLS-1$ 
                                schemaOrWsdlDefToCache = element;
                                break;
                            }
                        }
                    }

                    if (schemaOrWsdlDefToCache == null) {
                        /* It is valid for WSDL not to have a schema. */
                        if (!srcLocation.endsWith(".wsdl")) { //$NON-NLS-1$
                            Activator
                                    .getDefault()
                                    .getLogger()
                                    .error("Could not access schema from schema document root for: " //$NON-NLS-1$
                                            + srcLocation);
                        } else {
                            Activator
                                    .getDefault()
                                    .getLogger()
                                    .error("Could not access wsdl:definitions from WSDL document root for: " //$NON-NLS-1$
                                            + srcLocation);
                        }
                    }

                } else {
                    /*
                     * We were asked for the schema so 'schema' should already
                     * be SchemaType
                     */
                    schemaOrWsdlDefToCache = schemaOrWsdlDefOrDocumentRoot;
                }

                /*
                 * Cache teh schema so that if we're asked for same schema again
                 * we return EXACTLY the same DyanmicEObject for same Schema.
                 */
                if (schemaOrWsdlDefToCache != null) {
                    readXMLCache.put(schemaCacheURI, schemaOrWsdlDefToCache);
                }
            }
        }
        return schemaOrWsdlDefOrDocumentRoot;
    }

    /**
     * @param baseUri
     * @param srcLocation
     * @return Already Cached xs:schema or wsdl:definitions element for given
     *         baseURI and schemaLocation or <code>null</code> if not cached.
     */
    public EObject getCachedReadXMLFromData(URI baseUri, String srcLocation) {
        return getCachedReadXMLForSchemaURI(getSchemaURI(baseUri, srcLocation));
    }

    /**
     * @param schemaCacheURI
     * @return Already Cached xs:schema or wsdl:definitions element for given
     *         resolved schemaURI or <code>null</code> if not cached.
     */
    private EObject getCachedReadXMLForSchemaURI(URI schemaCacheURI) {

        EObject schemaOrDocumentRoot = readXMLCache.get(schemaCacheURI);
        if (schemaOrDocumentRoot != null) {

            return schemaOrDocumentRoot;
        }
        return null;
    }

    /**
     * Shameless copy of the OAW XMLReaderImpl.resolveURI() We need this in
     * order to use with the ReadXML cache.
     * 
     * @return URI for schemaLocation (which will be relative to baseURI if it
     *         is a relative schemaLocation).
     */
    public URI getSchemaURI(URI baseURI, String schemaLocation) {
        if (baseURI == null) {
            return EcoreUtil2.getURI(schemaLocation);
        }

        return XSDUtil.resolve(getOAWResourceSet().getURIConverter(),
                baseURI,
                schemaLocation);
    }

    /**
     * @return The project for the destination folder.
     */
    public IProject getProject() {
        if (destinationFolder != null) {
            return destinationFolder.getProject();
        }
        return null;
    }

    /**
     * @param nsURI
     *            The namespace URI.
     * @return The resolved package name.
     */
    public String getResolvedPackageNamespace(String nsURI) {
        if (nsURI == null) {
            return null;
        }
        return NamespaceURIToJavaPackageMapper
                .getJavaPackageNameFromNamespaceURI(getProject(), nsURI);
    }
}
