/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */
package com.tibco.xpd.script.model.client;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.swt.graphics.Image;
import org.eclipse.uml2.uml.AggregationKind;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.UMLFactory;

import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.script.model.Activator;
import com.tibco.xpd.script.model.JsConsts;
import com.tibco.xpd.script.model.internal.client.IContentAssistIconProvider;
import com.tibco.xpd.script.model.jscript.JScriptUtils;

/**
 * 
 * @author mtorres
 * 
 */
public class DefaultJsClass extends DefaultMultipleJsClassResolver implements
        JsClass, Cloneable {

    /**
     * Sid ACE-2419 - suffix dynamically created class names with this to have the content assist icon set to the BOM
     * package icon.
     */
    public static final String BOM_PKG_SUFFIX = "_$$package$$"; //$NON-NLS-1$

    /**
     * Sid ACE-2419 - suffix dynamically created class names with this to have the content assist icon set to the BOM
     * enumeration icon.
     */
    public static final String BOM_ENUM_SUFFIX = "_$$enumeration$$"; //$NON-NLS-1$

	/**
	 * Sid ACE-7330 Suffix for the container of PSL project classes for content assist.
	 */
	public static final String			PSL_PROJECTS_CONTAINER_CLASS_SUFFIX	= "_$$processScriptLibraryProjectContainer$$";	//$NON-NLS-1$

	/**
	 * Sid ACE-7330 Suffix for the container of PSL project classes for content assist.
	 */
	public static final String			PSL_FILES_CONTAINER_CLASS_SUFFIX	= "_$$processScriptLibraryFileContainer$$";		//$NON-NLS-1$

	/**
	 * Sid ACE-7330 Suffix for the container of PSL project classes for content assist.
	 */
	public static final String			PSL_FUNCTIONS_CONTAINER_CLASS_SUFFIX	= "_$$processScriptLibraryFunctionContainer$$";	//$NON-NLS-1$

    private Class umlClass;

    private Image image;

    private IContentAssistIconProvider contentAssistIconProvider;

    public DefaultJsClass(Class umlClass) {
        this.umlClass = umlClass;
    }

    public DefaultJsClass(Class umlClass, Class multipleClass) {
        this(umlClass);
        setMultipleClass(multipleClass);
    }

    @Override
    public List<JsMethod> getMethodList() {
        List<JsMethod> methodList = new ArrayList<JsMethod>();
        // Add the uml and dynamic methods to the list
        List<JsMethod> umlMethodList = getUmlMethodList();
        if (umlMethodList != null) {
            methodList.addAll(umlMethodList);
        }
        if (dynamicMethodList != null) {
            methodList.addAll(dynamicMethodList);
        }
        return methodList;
    }

    @SuppressWarnings("unchecked")
    private List<JsMethod> getUmlMethodList() {
        EList<Operation> allOperations = umlClass.getAllOperations();
        if (allOperations == null) {
            return Collections.EMPTY_LIST;
        }
        List<JsMethod> methodList = new ArrayList<JsMethod>();
        for (Object element : allOperations) {
            if (element instanceof Operation) {
                Operation operation = (Operation) element;
                JsMethod jsMethod = createJsMethod(operation);
                methodList.add(jsMethod);
            }
        }
        return methodList;
    }

    List<JsMethod> dynamicMethodList = null;

    @Override
    public void addMethod(JsMethod jsMethod) {
        if (dynamicMethodList == null) {
            dynamicMethodList = new ArrayList<JsMethod>();
        }
        dynamicMethodList.add(jsMethod);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<JsMethod> getMethodList(String methodName) {
        List<JsMethod> methodList = getMethodList();
        if (methodList == null) {
            return Collections.EMPTY_LIST;
        }
        List<JsMethod> toReturn = new ArrayList<JsMethod>();
        for (JsMethod jsMethod : methodList) {
            if (jsMethod.getName().equals(methodName)) {
                toReturn.add(jsMethod);
            }
        }
        return toReturn;
    }

    @Override
    public String getName() {
        return JScriptUtils.getUmlName(umlClass);
    }

    // TODO
    @Override
    public boolean isParameterCheckingStrict() {
        return false;
    }

    @Override
    public String getContentAssistString() {
        return getName();
    }

    @Override
    public String getComment() {
        return JScriptUtils.getUmlElementComment(umlClass);
    }
    
    /**
     * Return JsAttibute's for ONLY the primitive type child properties of the given class
     */
    @Override
    public List<JsAttribute> getAttributeList() {
		/*
		 * Sid ACE-8452 now just a wrapper around the original function which now takes param to include all attributes.
		 */
		return getAttributeList(false);
    }

	/**
	 * Return JsAttibute's for the child properties of the given class
	 * 
	 * Sid ACE-8452 Provide alternative to {@link #getAttributeList()} which for some reason I cannot fathom, ignores
	 * non-primitive type attributes.
	 * 
	 * @param includeClassAndEnumAttributes
	 *            if <code>true</code> then return JsAttribute for all attributes regardless of whether they are
	 *            primitive type or other types.
	 */
	public List<JsAttribute> getAttributeList(boolean includeClassAndEnumAttributes)
	{
        List<JsAttribute> attributeList = new ArrayList<JsAttribute>();
        // Add the uml and dynamic attributes to the list
		List<JsAttribute> umlAttributeList = getUmlAttributeList(includeClassAndEnumAttributes);
        if (umlAttributeList != null) {
            attributeList.addAll(umlAttributeList);
        }
        if (dynamicAttributeList != null) {
            attributeList.addAll(dynamicAttributeList);
        }
        return attributeList;
    }

	/**
	 * Return JsAttibute's for the child properties of the given class
	 * 
	 * Sid ACE-8452 Provide alternative to {@link #getAttributeList()} which for some reason I cannot fathom, ignores
	 * non-primitive type attributes.
	 * 
	 * @param includeClassAndEnumAttributes
	 *            if <code>true</code> then return JsAttribute for all attributes regardless of whether they are
	 *            primitive type or other types.
	 */
	private List<JsAttribute> getUmlAttributeList(boolean includeClassAndEnumAttributes) {
        List<Property> allAttributes = new ArrayList<Property>();

        /* XPD-8147: Additional param for collectAllClassAttribtues() */
        collectAllClassAttributes(umlClass,
                allAttributes,
                false,
                new HashSet<Classifier>());

        List<JsAttribute> attributeList = new ArrayList<JsAttribute>();
        for (Property property : allAttributes) {
            if (property != null
                    && (includeClassAndEnumAttributes || (!(property.getType() instanceof Class || property
							.getType() instanceof Enumeration))))
			{
                JsAttribute defaultJsAttribute = createJsAttribute(property);
                attributeList.add(defaultJsAttribute);
            }
		}
        return attributeList;
    }

    /**
     * XPD-8147 ammended and commented.
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
            boolean processingComplexTypeRestriction,
            HashSet<Classifier> alreadyProcessedClasses) {

        if (null != cl && !alreadyProcessedClasses.contains(cl)) {

            /**
             * XPD-8147 ComplexType's that are xsd restrictions of another
             * complexType (the 'supertype') include only the *elements* they
             * explicitly define (in the subtype).
             * 
             * Hence we USED TO ignore everything in supertypes if the subtype
             * was 'by restriction' rather than 'by extension'.
             * 
             * **HOWEVER** according to w3c, the **attributes** of the
             * restriction-base-type (the supertype) are always inherited and DO
             * NOT have to be explicitly defined in the subtype...
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

            for (Iterator iterator = ownedAttributes.iterator(); iterator
                    .hasNext();) {
                Property ownedProperty = (Property) iterator.next();

                if (processingComplexTypeRestriction) {
                    /*
                     * For complextype that is a restricted subtype of a
                     * basetype then only include attributes (elements have to
                     * be explicitly declared in top level subtype).
                     */
                    if (JScriptUtils.isPropertyXsdAttribute(ownedProperty)) {
                        /*
                         * Only include if not already included (explcitly
                         * defined and changed in subtype above this supertype.
                         */
                        boolean isDefinedAlready = false;
                        for (Property existingProperty : allAttributes) {
                            if (existingProperty.getName()
                                    .equals(ownedProperty.getName())) {
                                isDefinedAlready = true;
                                break;
                            }
                        }

                        if (!isDefinedAlready) {
                            allAttributes.add(ownedProperty);
                        }
                    }

                } else {
                    /*
                     * If it's the top level class or an subtype by extension
                     * then collect everything.
                     */
                    allAttributes.add(ownedProperty);
                }
            }

            /**
             * Once we have started 'collecting super-class attributes inherited
             * via an XSD restriction' mode we should continue with that
             * regardless of how any super-super types are configured (as
             * extension or restriction).
             */
            if (!processingComplexTypeRestriction) {
                processingComplexTypeRestriction =
                        JScriptUtils.isClassXsdComplexTypeRestriction(cl);
            }

            // Collect attributes and recurse if necessary
            EList<Classifier> generals = cl.getGenerals();
            if (!generals.isEmpty()) {
                Classifier classifier = generals.get(0);
                if (classifier instanceof Class) {
                    collectAllClassAttributes((Class) classifier,
                            allAttributes,
                            processingComplexTypeRestriction,
                            alreadyProcessedClasses);
                }
            }
        }
    }

    List<JsAttribute> dynamicAttributeList = null;

    @Override
    public void addAttribute(JsAttribute jsAttribute) {
        if (dynamicAttributeList == null) {
            dynamicAttributeList = new ArrayList<JsAttribute>();
        }
        dynamicAttributeList.add(jsAttribute);
    }

    /**
     * Checks whether global data is enabled using command line arg
     * "-Dglobaldata"
     * 
     * This check is expected to only be temporary; whilst Global Data is in
     * development.
     */
    public static boolean isGlobalDataEnabled() {
        return Boolean.parseBoolean(System.getProperty("globaldata", "true"));//$NON-NLS-1$ //$NON-NLS-2$
    }

    @Override
    public List<JsReference> getReferenceList() {
        List<JsReference> referenceList = new ArrayList<JsReference>();
        // Add the uml and dynamic references to the list
        List<JsReference> umlReferenceList = getUmlReferenceList();
        if (umlReferenceList != null) {
            referenceList.addAll(umlReferenceList);
        }
        if (dynamicReferenceList != null) {
            referenceList.addAll(dynamicReferenceList);
        }
        /*
         * properties in case classes must not be listed if they are
         * participating in associations (even if case classes are accessed as
         * local classes).
         */
        List<JsReference> filteredList =
                filterOutAssociationsForGlobalData(referenceList);
        return filteredList;
    }

    /**
     * Filters the references list for global data. As requirement, filters out
     * all the references which take part in association relationship.
     * 
     * XPD-5358: should not filter out composition relationships
     * 
     * @param referenceList
     * @return
     */
    private List<JsReference> filterOutAssociationsForGlobalData(
            List<JsReference> referenceList) {

        List<JsReference> filteredList = new ArrayList<JsReference>();
        for (JsReference jsReference : referenceList) {

            if (jsReference instanceof DefaultJsReference
                    && ((DefaultJsReference) jsReference).getElement() instanceof Property) {

                Property property =
                        (Property) ((DefaultJsReference) jsReference)
                                .getElement();
                /*
                 * if the property is participating in association and the kind
                 * is composite then it must be included in the filtered list
                 */
                if (null != property.getAssociation()) {

                    Association association = property.getAssociation();
                    List<Property> memberEnds = association.getMemberEnds();

                    for (Property memProperty : memberEnds) {

                        if (memProperty.getAggregation() == AggregationKind.COMPOSITE_LITERAL) {
                            filteredList.add(jsReference);
                            break;
                        }
                    }

                } else {
                    /*
                     * property is not participating in association (in terms of
                     * uml diagram notation) but the type of the property is a
                     * different class which means it is a composite relation;
                     * then it must be included in the filtered list
                     */
                    if (property.getType() instanceof Class
                            || property.getType() instanceof Enumeration) {

                        filteredList.add(jsReference);
                    }
                }
            }

        }
        return filteredList;
    }

    @SuppressWarnings("unchecked")
    private List<JsReference> getUmlReferenceList() {
        List<Property> allAttributes = umlClass.getAllAttributes();
        if (allAttributes == null) {
            return Collections.EMPTY_LIST;
        }
        List<JsReference> referenceList = new ArrayList<JsReference>();
        for (Property property : allAttributes) {
            if (property != null
                    && (property.getType() instanceof Class || property
                            .getType() instanceof Enumeration)) {
                JsReference defaultJsReference = createJsReference(property);
                referenceList.add(defaultJsReference);
            }
        }
        return referenceList;
    }

    List<JsReference> dynamicReferenceList = null;

    @Override
    public void addReference(JsReference jsReference) {
        if (dynamicReferenceList == null) {
            dynamicReferenceList = new ArrayList<JsReference>();
        }
        dynamicReferenceList.add(jsReference);
    }

    @Override
    public Image getIcon() {
        if (this.image != null) {
            return this.image;
        } else if (contentAssistIconProvider != null) {
            return contentAssistIconProvider.getIcon(this);
        }
        return null;
    }

    protected JsMethod createJsMethod(Operation operation) {
        DefaultJsMethod jsMethod =
                new DefaultJsMethod(operation, multipleClass);

		/*
		 * Sid ACE-7330 - If container class has suffix the project-level class to indicate it is a PSL
		 * projects/files/functions container, use PSL file icon.
		 */
		if (operation.eContainer() instanceof Class && ((Class) operation.eContainer()).getName() != null
				&& ((Class) operation.eContainer()).getName().endsWith(PSL_FUNCTIONS_CONTAINER_CLASS_SUFFIX))
		{
			Image icon = null;

			JsMethodParam returnType = jsMethod.getReturnType();
			if (returnType != null)
			{
				Parameter umlParam = returnType.getUMLParameter();
				if (umlParam != null)
				{
					Type umlType = umlParam.getType();

					if (umlType != null)
					{
						/*
						 * get-image only works property for UML properties (where icon is derived from type), so create
						 * a temporary one just to get proper icon.
						 */
						Property tmpProperty = UMLFactory.eINSTANCE.createProperty();
						tmpProperty.setType(umlType);
						icon = WorkingCopyUtil.getImage(tmpProperty);
						jsMethod.setIcon(icon);
					}
				}
			}

		}
		else
		{
			jsMethod.setIcon(WorkingCopyUtil.getImage(operation));
		}

        return jsMethod;
    }

	protected JsAttribute createJsAttribute(Property property)
	{
        DefaultJsAttribute jsAttribute =
                new DefaultJsAttribute(property, multipleClass);
        jsAttribute.setIcon(WorkingCopyUtil.getImage(property));
        return jsAttribute;
    }

    protected JsReference createJsReference(Property property) {
        DefaultJsReference jsReference =
                new DefaultJsReference(property, multipleClass);

        /*
         * Sid ACE-2419 Look for specially created properties that represent packages / enumerations etc.
         */
        if (property.getType() instanceof Class && property.getType().getName() != null
                && property.getType().getName().endsWith(BOM_PKG_SUFFIX)) {
            /* Any dynamcially created class ending in _$$package gets the package icon */
            jsReference.setIcon(Activator.getDefault().getImageRegistry().get(JsConsts.CDS_BOM_PACKAGE_ICON));

        } else if (property.getType() instanceof Class && property.getType().getName() != null
                && property.getType().getName().endsWith(BOM_ENUM_SUFFIX)) {
            jsReference.setIcon(Activator.getDefault().getImageRegistry().get(JsConsts.CDS_BOM_ENUMERATION_ICON));

        } else if (property.eContainer() instanceof Class && ((Class) property.eContainer()).getName() != null
                && ((Class) property.eContainer()).getName().endsWith(BOM_ENUM_SUFFIX)) {
            jsReference.setIcon(Activator.getDefault().getImageRegistry().get(JsConsts.CDS_BOM_ENUMERATION_LITERAL_ICON));

		}
		else if (property.eContainer() instanceof Class && ((Class) property.eContainer()).getName() != null
				&& ((Class) property.eContainer()).getName().endsWith(BOM_ENUM_SUFFIX))
		{
			jsReference
					.setIcon(Activator.getDefault().getImageRegistry().get(JsConsts.CDS_BOM_ENUMERATION_LITERAL_ICON));

		}
		/*
		 * Sid ACE-7330 - If container class has suffix the project-level class to indicate it is a PSL
		 * projects/files/functions container, use PSL file icon.
		 */
		else if (property.eContainer() instanceof Class && ((Class) property.eContainer()).getName() != null
				&& ((Class) property.eContainer()).getName().endsWith(PSL_PROJECTS_CONTAINER_CLASS_SUFFIX))
		{
			jsReference.setIcon(Activator.getDefault().getImageRegistry().get(JsConsts.PSL_PROJECT_LITERAL_ICON));
		}
		else if (property.eContainer() instanceof Class && ((Class) property.eContainer()).getName() != null
				&& ((Class) property.eContainer()).getName().endsWith(PSL_FILES_CONTAINER_CLASS_SUFFIX))
		{
			jsReference.setIcon(Activator.getDefault().getImageRegistry().get(JsConsts.PSL_FILE_LITERAL_ICON));
		}
		else
		{
            jsReference.setIcon(WorkingCopyUtil.getImage(property));
        }

        jsReference.setContentAssistIconProvider(contentAssistIconProvider);
        return jsReference;
    }

    @Override
    public void setIcon(Image image) {
        this.image = image;
    }

    public JsAttribute getAttribute(String name) {
        List<JsAttribute> attributeList = getAttributeList();
        if (attributeList != null) {
            for (Iterator<JsAttribute> iterator = attributeList.iterator(); iterator
                    .hasNext();) {
                JsAttribute jsAttribute = iterator.next();
                if (jsAttribute != null && jsAttribute.getName().equals(name)) {
                    return jsAttribute;
                }
            }
        }
        return null;
    }

    public JsMethod getMethod(String name) {
        List<JsMethod> methodList = getMethodList();
        if (methodList != null) {
            for (Iterator<JsMethod> iterator = methodList.iterator(); iterator
                    .hasNext();) {
                JsMethod jsMethod = iterator.next();
                if (jsMethod != null && jsMethod.getName().equals(name)) {
                    return jsMethod;
                }
            }
        }
        return null;
    }

    public JsReference getReference(String name) {
        List<JsReference> referenceList = new ArrayList<JsReference>();
        referenceList.addAll(getReferenceList());
        if (referenceList != null) {
            for (Iterator<JsReference> iterator = referenceList.iterator(); iterator
                    .hasNext();) {
                JsReference jsReference = iterator.next();
                if (jsReference != null && jsReference.getName().equals(name)) {
                    return jsReference;
                }
            }
        }
        return null;
    }

    @Override
    public JsDataType getDataTypeForJSExpression(JsExpression jsExpression,
            List<JsClass> supportedJsClasses) {
        JsDataType dataType = new JsDataType();
        dataType.setJsExpression(jsExpression);
        if (jsExpression != null) {
            if (JScriptUtils.hasMoreJSChildren(jsExpression)) {
                JsExpression nextJsExpression =
                        jsExpression.getNextExpression();
                if (nextJsExpression != null
                        && nextJsExpression.getName() != null) {
                    // Check the expression with the methods if it is a method
                    if (nextJsExpression instanceof JsExpressionMethod) {
                        if (getMethod(nextJsExpression.getName()) != null) {
                            JsMethod method =
                                    getMethod(nextJsExpression.getName());
                            dataType =
                                    method.getDataTypeForJSExpression(nextJsExpression,
                                            supportedJsClasses);
                        } else {
                            if (getName() != null
                                    && !getName()
                                            .equals(JsConsts.UNDEFINED_DATA_TYPE)) {
                                dataType.setUndefinedCause(JsConsts.UNKNOWN_METHOD_DATA_TYPE_CAUSE);
                            }
                        }
                    } else {
                        // Check the expression with the attributes
                        if (getAttribute(nextJsExpression.getName()) != null) {
                            JsAttribute attribute =
                                    getAttribute(nextJsExpression.getName());
                            dataType =
                                    attribute
                                            .getDataTypeForJSExpression(nextJsExpression,
                                                    supportedJsClasses);
                        } else if (getReference(nextJsExpression.getName()) != null) {
                            JsReference reference =
                                    getReference(nextJsExpression.getName());
                            dataType =
                                    reference
                                            .getDataTypeForJSExpression(nextJsExpression,
                                                    supportedJsClasses);
                        } else {
                            if (getName() != null
                                    && !getName()
                                            .equals(JsConsts.UNDEFINED_DATA_TYPE)) {
                                dataType.setUndefinedCause(JsConsts.UNKNOWN_PROPERTY_DATA_TYPE_CAUSE);
                            }
                        }
                    }
                }
            } else {
                if (this.getName().equals(getMultipleJsClassName())) {
                    boolean isObjectMultiple = false;
                    if (!JScriptUtils.isJsExpressionMultiple(jsExpression)) {
                        isObjectMultiple = true;
                    }
                    IScriptRelevantData objectType =
                            JScriptUtils
                                    .resolveJavaScriptNotMultipleArrayType(this
                                            .getName(),
                                            isObjectMultiple,
                                            supportedJsClasses,
                                            getMultipleClass());
                    dataType.setType(objectType);
                    return dataType;
                } else {
                    /*
                     * IScriptRelevantData scriptRelevantData = new
                     * DefaultUMLScriptRelevantData( this.getName(),
                     * this.getName(), false, this);
                     */
                    IScriptRelevantData scriptRelevantData =
                            JScriptUtils.getScriptRelevantData(this,
                                    this.getName(),
                                    false);
                    dataType.setType(scriptRelevantData);
                    return dataType;
                }
            }
        }
        return dataType;
    }

    @Override
    public Class getUmlClass() {
        return umlClass;
    }

    /** {@inheritDoc}. **/
    @Override
    public IScriptRelevantData getScriptRelevantData(String variableName,
            boolean isArray) {
        return null;
    }

    public void setContentAssistIconProvider(
            IContentAssistIconProvider contentAssistIconProvider) {
        this.contentAssistIconProvider = contentAssistIconProvider;
    }

    /**
     * @see java.lang.Object#toString()
     * 
     * @return
     */
    @Override
    public String toString() {
        if (umlClass != null) {
            return umlClass.getQualifiedName();
        } else {
            return super.toString();
        }

    }
}
