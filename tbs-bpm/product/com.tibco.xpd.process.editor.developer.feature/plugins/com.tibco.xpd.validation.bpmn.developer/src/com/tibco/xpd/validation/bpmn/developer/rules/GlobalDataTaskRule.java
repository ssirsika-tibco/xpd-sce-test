/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */
package com.tibco.xpd.validation.bpmn.developer.rules;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.AggregationKind;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ActivityInterfaceData;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ActivityInterfaceDataUtil;
import com.tibco.xpd.analyst.resources.xpdl2.utils.BasicTypeConverterFactory;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessDataUtil;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessUIUtil;
import com.tibco.xpd.bom.globaldata.api.BOMGlobalDataUtils;
import com.tibco.xpd.implementer.nativeservices.utilities.TaskServiceExtUtil;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.validation.xpdl2.rules.ProcessActivitiesValidationRule;
import com.tibco.xpd.xpdExtension.AddLinkAssociationsType;
import com.tibco.xpd.xpdExtension.CaseAccessOperationsType;
import com.tibco.xpd.xpdExtension.CaseReferenceOperationsType;
import com.tibco.xpd.xpdExtension.CreateCaseOperationType;
import com.tibco.xpd.xpdExtension.GlobalDataOperation;
import com.tibco.xpd.xpdExtension.RemoveAllLinksByNameType;
import com.tibco.xpd.xpdExtension.RemoveLinkAssociationsType;
import com.tibco.xpd.xpdExtension.UpdateCaseOperationType;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.BasicType;
import com.tibco.xpd.xpdl2.DataType;
import com.tibco.xpd.xpdl2.ExternalReference;
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.Member;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.RecordType;
import com.tibco.xpd.xpdl2.Task;

/**
 * Validate a Global Data task.
 * 
 * @author njpatel
 */
public class GlobalDataTaskRule extends ProcessActivitiesValidationRule {

    /**
     * Additional info for marker to hold the case class id.
     */
    public static final String CASE_CLASS_ID = "caseClassId"; //$NON-NLS-1$

    /**
     * Additional info for marker to hold the case identifier name.
     */
    public static final String ID = "id"; //$NON-NLS-1$

    private static final String MUST_HAVE_OPERATION =
            "bpmn.dev.globalDataTask.mustHaveOperationSelected"; //$NON-NLS-1$

    private static final String CASE_REF_FIELD_REQUIRED =
            "bpmn.dev.globalDataTask.caseReferenceFieldRequired"; //$NON-NLS-1$

    private static final String INVALID_CASE_REF_FIELD =
            "bpmn.dev.globalDataTask.invalidCaseReferenceField"; //$NON-NLS-1$

    private static final String DELETE_ARRAY_CASE_REF_FIELD = "bpmn.dev.globalDataTask.deleteArrayCaseReferenceField"; //$NON-NLS-1$

    private static final String UPDATE_OP_INVALID_LOCAL_DATA =
            "bpmn.dev.globalDataTask.updateOp.invalidLocalData"; //$NON-NLS-1$

    private static final String UPDATE_OP_MULTIPLICITY =
            "bpmn.dev.globalDataTask.updateOp.multiplicity"; //$NON-NLS-1$

    private static final String ADDLINK_OP_NOARRAYCASEREF =
            "bpmn.dev.globalDataTask.addLinkOp.noArrayCaseRef"; //$NON-NLS-1$

    private static final String ADDLINK_OP_VALID_ASSOC =
            "bpmn.dev.globalDataTask.addLinkOp.association"; //$NON-NLS-1$

    private static final String ADDLINK_OP_VALID_CASEOBJREF_TYPE =
            "bpmn.dev.globalDataTask.addLinkOp.caseObjectRefType"; //$NON-NLS-1$

    private static final String ADDLINK_OP_VALID_CASEOBJREF_MULTIPLICITY =
            "bpmn.dev.globalDataTask.addLinkOp.caseObjectRefTypeMultiplicity"; //$NON-NLS-1$

    private static final String REMOVELINK_OP_NOARRAY_CASEREF =
            "bpmn.dev.globalDataTask.removeLinkOp.noArrayCaseRef"; //$NON-NLS-1$

    private static final String REMOVELINK_OP_VALID_ASSOC =
            "bpmn.dev.globalDataTask.removeLinkOp.association"; //$NON-NLS-1$

    private static final String REMOVEALLLINK_OP_NOARRAY_CASEREF =
            "bpmn.dev.globalDataTask.removeAllLinkOp.noArrayCaseRef"; //$NON-NLS-1$

    private static final String REMOVEALLLINK_OP_VALID_ASSOC = "bpmn.dev.globalDataTask.removeAllLinkOp.association"; //$NON-NLS-1$

    private static final String REMOVELINK_OP_VALID_CASEOBJREF_MULTIPLICITY =
            "bpmn.dev.globalDataTask.removeLinkOp.caseObjectRefTypeMultiplicity"; //$NON-NLS-1$

    private static final String REMOVELINK_OP_VALID_CASEOBJREF_TYPE =
            "bpmn.dev.globalDataTask.removeLinkOp.caseObjectRefType"; //$NON-NLS-1$

    private static final String CASEACCESSOPS_VALIDCASECLASS =
            "bpmn.dev.globalDataTask.caseAccessOps.validCaseClass"; //$NON-NLS-1$

    private static final String CREATE_OP_VALIDLOCALDATA =
            "bpmn.dev.globalDataTask.createOp.validLocalData"; //$NON-NLS-1$

    private static final String CREATE_OP_VALIDLOCALDATATYPE =
            "bpmn.dev.globalDataTask.createOp.onlyBOMDataTypes";//$NON-NLS-1$

    private static final String CREATE_OP_VALIDCASEREF =
            "bpmn.dev.globalDataTask.createOp.validCaseRef"; //$NON-NLS-1$

    private static final String CREATE_OP_VALIDMULTIPLICITY =
            "bpmn.dev.globalDataTask.createOp.validMultiplicity"; //$NON-NLS-1$

    private static final String FIELD_NOT_EXIST_OR_NOT_ASSOCIATED =
            "bpmn.dev.globalDataTask.dataNotExistOrNotAssociatedWithActivity"; //$NON-NLS-1$

    private static final String CASEACCESSOPS_INVALID_OPERATION =
            "bpmn.dev.globalDataTask.caseAccessOps.invalidOperation"; //$NON-NLS-1$

    @Override
    public void validate(Activity activity) {
        if (activity != null) {
            GlobalDataOperation globalDataOp = getGlobalDataOperation(activity);
            if (globalDataOp != null) {
                if (isOperationSelected(globalDataOp)) {
                    CaseAccessOperationsType caseAccessOps =
                            globalDataOp.getCaseAccessOperations();
                    CaseReferenceOperationsType caseRefOps =
                            globalDataOp.getCaseReferenceOperations();

                    if (caseAccessOps != null) {

                        validateCaseAccessOperations(activity, caseAccessOps);
                    } else if (caseRefOps != null) {
                        validateCaseReferenceOperations(activity, caseRefOps);
                    }

                } else {
                    addIssue(MUST_HAVE_OPERATION, activity);
                }
            }
        }
    }

    /**
     * Validate Case Access operations.
     * 
     * @param activity
     * @param caseAccessOps
     */
    private void validateCaseAccessOperations(Activity activity,
            CaseAccessOperationsType caseAccessOps) {

        /*
         * Validate selected case class
         */
        ExternalReference extRef =
                caseAccessOps.getCaseClassExternalReference();
        boolean isValidCaseClass = false;
        Class caseClass = null;
        if (extRef != null) {
            caseClass =
                    getReferencedClass(WorkingCopyUtil.getProjectFor(activity),
                            extRef);
            isValidCaseClass =
                    caseClass != null
                            && BOMGlobalDataUtils.isCaseClass(caseClass);
        }

        if (isValidCaseClass) {
            if (caseAccessOps.getCreate() != null) {
                /*
                 * Validate create operation
                 */
                validateCreateOperation(activity,
                        caseClass,
                        caseAccessOps.getCreate());
            } else if (caseAccessOps.getDeleteByCaseIdentifier() != null) {
                addIssue(CASEACCESSOPS_INVALID_OPERATION,
                        activity,
                        Collections.singletonList("Delete by Case Identifier"));
            } else if (caseAccessOps.getDeleteByCompositeIdentifiers() != null) {
                addIssue(CASEACCESSOPS_INVALID_OPERATION,
                        activity,
                        Collections.singletonList("Delete by Composite Identifier"));
            }
        } else {
            // Not a valid case class
            addIssue(CASEACCESSOPS_VALIDCASECLASS, activity);
        }
    }

    /**
     * Get all the composite case ids from the given case class.
     * 
     * @param caseClass
     * @return
     */
    private List<Property> getCompositeCaseIds(Class caseClass) {
        List<Property> properties = new ArrayList<Property>();

        for (Property prop : caseClass.getAllAttributes()) {
            if (BOMGlobalDataUtils.isCompositeCID(prop)) {
                properties.add(prop);
            }
        }

        return properties;
    }

    /**
     * Get all the auto/custom case ids from the given case class.
     * 
     * @param caseClass
     * @return
     */
    private List<Property> getAutoORCustomCaseIds(Class caseClass) {
        List<Property> properties = new ArrayList<Property>();

        for (Property prop : caseClass.getAllAttributes()) {
            if (BOMGlobalDataUtils.isAutoCID(prop)
                    || BOMGlobalDataUtils.isCustomCID(prop)) {
                properties.add(prop);
            }
        }

        return properties;
    }

    /**
     * Validate the type of the equals data field against the selected case
     * identifier type.
     * 
     * @param activity
     * @param caseId
     * @param isValidEqualsValue
     * @param equalsValueFieldPath
     * @param allowArray
     *            <code>true</code> if property can be an array
     * @return
     */
    private boolean validateEqualsParameter(Activity activity, Property caseId,
            String equalsValueFieldPath, boolean allowArray) {
        boolean isValidEqualsValue = false;
        if (equalsValueFieldPath != null && !equalsValueFieldPath.isEmpty()) {
            if (equalsValueFieldPath
                    .contains(ConceptPath.CONCEPTPATH_SEPARATOR)) {
                ConceptPath path =
                        resolveConceptPath(activity, equalsValueFieldPath);

                if (path != null) {
                    isValidEqualsValue =
                            caseId.getType().equals(path.getType());

                    /*
                     * Check that the root of this concept path is in scope of
                     * the activity
                     */
                    validateActivityRelevantData(activity, path.getRoot()
                            .getName());

                }
            } else {
                ProcessRelevantData data =
                        validateActivityRelevantData(activity,
                                equalsValueFieldPath);
                if (data != null) {
                    // Check if the type of the data matches the type of the
                    // case id
                    DataType dataType = data.getDataType();
                    if (dataType != null) {
                        BasicType equalsValueBasicType =
                                BasicTypeConverterFactory.INSTANCE
                                        .getBasicType(dataType);
                        BasicType caseIdType =
                                BasicTypeConverterFactory.INSTANCE
                                        .getBasicType(caseId.getType());

                        if (equalsValueBasicType != null
                                && caseIdType != null
                                && equalsValueBasicType.getType()
                                        .equals(caseIdType.getType())) {
                            // Also, equals value type cannot be an array
                            isValidEqualsValue =
                                    allowArray || !data.isIsArray();
                        }
                    }
                }
            }
        }
        return isValidEqualsValue;
    }

    /**
     * Resolve the given concept path.
     * 
     * @param activity
     * @param path
     * @return
     */
    private ConceptPath resolveConceptPath(Activity activity, String path) {
        if (path != null && !path.isEmpty()
                && !path.endsWith(ConceptPath.CONCEPTPATH_SEPARATOR)) {
            return ConceptUtil.resolveConceptPath(activity, path);
        }
        return null;
    }

    /**
     * Validate the case reference operations.
     * 
     * @param activity
     * @param caseRefOps
     */
    private void validateCaseReferenceOperations(Activity activity,
            CaseReferenceOperationsType caseRefOps) {
        String caseRefField = caseRefOps.getCaseRefField();

        Class caseClass = null;
        ProcessRelevantData caseField = null;
        if (caseRefField != null && !caseRefField.isEmpty()) {
            caseField =
                    validateActivityRelevantData(activity, caseRefField);
            if (caseField != null) {
                caseClass = getCaseClassReferencedByField(activity, caseField);

                if (caseClass == null) {
                    addIssue(INVALID_CASE_REF_FIELD,
                            activity,
                            Collections.singletonList(caseRefField));
                }
            }
        } else {
            addIssue(CASE_REF_FIELD_REQUIRED, activity);
        }

        if (caseClass != null) {
            if (caseRefOps.getUpdate() != null) {
                /*
                 * Validate update operation's from field
                 */
                if (validateUpdateOperationFromField(activity,
                        caseClass,
                        caseRefOps.getUpdate())) {
                    // Validate the multiplicity of the params
                    validateUpdateOperationDataMultiplicity(activity,
                            caseRefField,
                            caseRefOps.getUpdate());
                }
            } else if (caseRefOps.getAddLinkAssociations() != null) {
                /*
                 * Validate add link operation
                 */
                validateAddLinkOperation(activity,
                        caseRefField,
                        caseRefOps.getAddLinkAssociations());
            } else if (caseRefOps.getRemoveLinkAssociations() != null) {
                /*
                 * Validate remove link operation
                 */
                validateRemoveLinkOperation(activity,
                        caseRefField,
                        caseRefOps.getRemoveLinkAssociations());
            } else if (caseRefOps.getRemoveAllLinksByName() != null) {
                /*
                 * Validate remove all links operation
                 */
                validateRemoveAllLinksOperation(activity, caseRefField, caseRefOps.getRemoveAllLinksByName());
            } else if (caseRefOps.getDelete() != null) {
                if (caseField != null && caseField.isIsArray()) {
                    addIssue(DELETE_ARRAY_CASE_REF_FIELD, activity, Collections.singletonList(caseRefField));
                }
            }
        }
    }

    /**
     * Validate the remove link operation of a case reference field.
     * 
     * @param activity
     * @param caseRefField
     * @param removeLinkAssociations
     */
    private void validateRemoveLinkOperation(Activity activity,
            String caseRefField,
            RemoveLinkAssociationsType removeLinkAssociations) {
        ProcessRelevantData caseRefData =
                validateActivityRelevantData(activity, caseRefField);
        if (caseRefData != null) {
            if (caseRefData.isIsArray()) {
                // Array case reference is not allowed for a remove link
                // operation
                addIssue(REMOVELINK_OP_NOARRAY_CASEREF, activity);
            } else {
                String associationName =
                        removeLinkAssociations.getAssociationName();
                Class caseClass =
                        getCaseClassReferencedByField(activity, caseRefData);

                /*
                 * Check for valid association
                 */
                validateLinkAssociation(activity,
                        caseClass,
                        associationName,
                        removeLinkAssociations.getRemoveCaseRefField(),
                        REMOVELINK_OP_VALID_ASSOC,
                        REMOVELINK_OP_VALID_CASEOBJREF_MULTIPLICITY,
                        REMOVELINK_OP_VALID_CASEOBJREF_TYPE);
            }
        }
    }

    /**
     * Validate the remove all links operation of a case reference field.
     * 
     * @param activity
     * @param caseRefField
     * @param removeAllLinksByName
     */
    private void validateRemoveAllLinksOperation(Activity activity, String caseRefField,
            RemoveAllLinksByNameType removeAllLinksByName) {
        ProcessRelevantData caseRefData = validateActivityRelevantData(activity, caseRefField);
        if (caseRefData != null) {
            if (caseRefData.isIsArray()) {
                // Array case reference is not allowed for a remove link
                // operation
                addIssue(REMOVEALLLINK_OP_NOARRAY_CASEREF, activity);
            } else {
                String associationName = removeAllLinksByName.getAssociationName();
                Class caseClass = getCaseClassReferencedByField(activity, caseRefData);

                /*
                 * Check for valid association
                 */
                if (caseClass != null) {

                    if (associationName == null || !isValidCaseAssociation(caseClass, associationName)) {
                        addIssue(REMOVEALLLINK_OP_VALID_ASSOC, activity);
                    }
                }
            }
        }
    }

    /**
     * Validate the Add Link operation of a case reference.
     * 
     * @param activity
     * @param caseRefField
     * @param addLinkAssociations
     */
    private void validateAddLinkOperation(Activity activity,
            String caseRefField, AddLinkAssociationsType addLinkAssociations) {
        /*
         * For this operation type the case ref field cannot be an array
         */
        ProcessRelevantData caseRefData =
                validateActivityRelevantData(activity, caseRefField);
        if (caseRefData != null) {
            if (caseRefData.isIsArray()) {
                addIssue(ADDLINK_OP_NOARRAYCASEREF, activity);
            } else {
                String associationName =
                        addLinkAssociations.getAssociationName();
                Class caseClass =
                        getCaseClassReferencedByField(activity, caseRefData);

                /*
                 * Check for valid association
                 */
                validateLinkAssociation(activity,
                        caseClass,
                        associationName,
                        addLinkAssociations.getAddCaseRefField(),
                        ADDLINK_OP_VALID_ASSOC,
                        ADDLINK_OP_VALID_CASEOBJREF_MULTIPLICITY,
                        ADDLINK_OP_VALID_CASEOBJREF_TYPE);
            }
        }
    }

    /**
     * Validate the link association
     * 
     * @param activity
     * @param caseClass
     *            the case reference case class
     * @param associationName
     *            name of the association of the case class
     * @param caseObjRefField
     *            case object reference field name
     * @param linkValidAssocIssue
     *            issue id of invalid association
     * @param caseObjMultiplicityIssue
     *            issue id of invalid multiplicity issue
     * @param invalidCaseObjRefType
     *            issue id of invalid case object reference issue
     */
    private void validateLinkAssociation(Activity activity, Class caseClass,
            String associationName, String caseObjRefField,
            String linkValidAssocIssue, String caseObjMultiplicityIssue,
            String invalidCaseObjRefType) {

        if (caseClass != null) {

            if (associationName == null
                    || !isValidCaseAssociation(caseClass, associationName)) {

                addIssue(linkValidAssocIssue, activity);
            } else {
                /*
                 * Check for valid case object reference
                 */
                Property property = null;
                boolean isCaseObjRefValid = false;
                Type type = null;

                EList<Property> allAttributes = caseClass.getAllAttributes();
                for (Property prop : allAttributes) {

                    if (prop.getName().equals(associationName)) {

                        property = prop;
                        break;
                    }
                }

                ConceptPath caseObjectRefPath = null;
                caseObjectRefPath =
                        resolveConceptPath(activity, caseObjRefField);
                if (null != property) {

                    type = property.getType();
                    if (caseObjectRefPath != null) {
                        /*
                         * Validate that the root of the concept path is in
                         * scope of this activity
                         */
                        if (validateActivityRelevantData(activity,
                                caseObjectRefPath.getRoot().getName()) == null) {

                            return;
                        }

                        /*
                         * XPD-6077: Saket: Need to consider superclasses as
                         * well while validating 'type'.
                         */

                        List<Classifier> allGenerals =
                                new ArrayList<Classifier>();

                        /*
                         * XPD-6418: Saket: Need to consider generals at every
                         * level. The Classifier.getGenerals() method returns
                         * the DIRECT generals only.
                         */
                        fetchAllGenerals(caseObjectRefPath.getType(),
                                allGenerals);

                        isCaseObjRefValid =
                                caseObjectRefPath.getType() != null
                                        && type != null
                                        && (caseObjectRefPath.getType()
                                                .equals(type) || allGenerals
                                                .contains(type));
                    }
                }

                if (isCaseObjRefValid) {

                    /* Check for multiplicity */
                    if (caseObjectRefPath != null && !property.isMultivalued()
                            && caseObjectRefPath.isArray()) {

                        addIssue(caseObjMultiplicityIssue,
                                activity,
                                Collections.singletonList(associationName));
                    }
                } else {

                    if (null == type) {

                        addIssue(invalidCaseObjRefType,
                                activity,
                                Collections.singletonList(associationName));
                    } else {

                        addIssue(invalidCaseObjRefType,
                                activity,
                                Collections.singletonList(type.getName()));
                    }
                }
            }
        }
    }

    /**
     * Populate the list specified in the parameters by generals at every level
     * for the specified type.
     * 
     * @param type
     *            The type for which generals are to be figured out.
     * @param allGenerals
     *            List to be populated, need to pass an empty list here since
     *            it's a recursive method and this list will be populated at
     *            each and every level of the tree hierarchy.
     */
    private void fetchAllGenerals(Classifier type, List<Classifier> allGenerals) {

        /*
         * See if there's at least one general for the type specified.
         */
        if (type.getGenerals() != null && !type.getGenerals().isEmpty()) {

            /*
             * Go through the DIRECT generals of the type.
             */
            for (Classifier eachGeneral : type.getGenerals()) {

                /*
                 * If if we haven't already added it, then add it.
                 */
                if (!allGenerals.contains(eachGeneral)) {

                    allGenerals.add(eachGeneral);
                }

                /*
                 * Recursively call this method to collect generals at all
                 * levels.
                 */
                fetchAllGenerals(eachGeneral, allGenerals);
            }

        } else {

            /*
             * Return if we've reached the root.
             */
            return;
        }
    }

    /**
     * Check if the given association name is a valid association of the given
     * case class.
     * 
     * @param caseClass
     * @param assocName
     * @return
     */
    private boolean isValidCaseAssociation(Class caseClass, String assocName) {
        if (caseClass != null && assocName != null && !assocName.isEmpty()) {
            Property assocProperty = null;
            /*
             * XPD-6049: Saket: Need to consider superclass attributes as well.
             */
            for (Property prop : caseClass.getAllAttributes()) {
                if (assocName.equals(prop.getName())) {
                    assocProperty = prop;
                    break;
                }
            }

            return assocProperty != null
                    && assocProperty.getAssociation() != null
                    && (assocProperty.getAggregation() == AggregationKind.NONE_LITERAL || assocProperty
                            .getAggregation() == AggregationKind.SHARED_LITERAL)
                    && assocProperty.getType() instanceof Class;
        }
        return false;
    }

    /**
     * Validate the update operation data multiplicity.
     * 
     * @param activity
     * @param caseRefField
     * @param updateCaseOperationType
     */
    private void validateUpdateOperationDataMultiplicity(Activity activity,
            String caseRefField, UpdateCaseOperationType updateCaseOperationType) {
        ProcessRelevantData caseRefData =
                findProcessRelevantData(activity, caseRefField);
        String fromFieldPath = updateCaseOperationType.getFromFieldPath();
        boolean isMultiplicityMatch = false;
        if (caseRefData != null && fromFieldPath != null) {
            boolean isFieldPathArray =
                    isFieldPathArray(activity, fromFieldPath);
            isMultiplicityMatch = isFieldPathArray == caseRefData.isIsArray();
        }

        if (!isMultiplicityMatch) {
            addIssue(UPDATE_OP_MULTIPLICITY, activity);
        }
    }

    /**
     * Check if the given path represents an array. Only the last attribute in
     * the path can be an array (all other segments have to be single instances)
     * 
     * @param activity
     * @param fieldPath
     * @return <code>true</code> if the field path is an array
     * @throws IllegalArgumentException
     *             if any of the segments other than the last segment is an
     *             array or the field path is invalid.
     */
    private boolean isFieldPathArray(Activity activity, String fieldPath) {
        if (fieldPath.contains(ConceptPath.CONCEPTPATH_SEPARATOR)) {
            ConceptPath path = resolveConceptPath(activity, fieldPath);
            if (path != null) {
                // Ancestors of the path cannot be array
                ConceptPath parent = path.getParent();

                while (parent != null) {
                    if (parent.isArray()) {
                        throw new IllegalArgumentException();
                    }
                    parent = parent.getParent();
                }

                return path.isArray();
            }
        } else {
            ProcessRelevantData data =
                    findProcessRelevantData(activity, fieldPath);
            return data != null && data.isIsArray();
        }
        return false;
    }

    /**
     * Validate the case ref update operation
     * 
     * @param activity
     * @param caseClass
     * @param updateCaseOperationType
     * @return <code>true</code> if validated ok.
     */
    private boolean validateUpdateOperationFromField(Activity activity,
            Class caseClass, UpdateCaseOperationType updateCaseOperationType) {
        String fieldPath = updateCaseOperationType.getFromFieldPath();

        boolean isValidField = false;

        ConceptPath path = resolveConceptPath(activity, fieldPath);
        if (path != null) {
            isValidField = caseClass.equals(path.getType());
        }

        if (!isValidField) {
            // local data is invalid
            addIssue(UPDATE_OP_INVALID_LOCAL_DATA,
                    activity,
                    Collections.singletonList(caseClass.getName()));
            return false;
        } else {
            /*
             * Ensure the local data is in scope of this activity
             */
            validateActivityRelevantData(activity, path.getRoot().getName());
        }

        return true;

    }

    /**
     * Get the class referenced in the given external reference.
     * 
     * @param project
     * @param extRef
     * @return
     */
    private Class getReferencedClass(IProject project, ExternalReference extRef) {
        if (project != null && extRef != null) {
            return (Class) ProcessUIUtil.getReferencedClassifier(extRef,
                    project);
        }
        return null;
    }

    /**
     * Get the case class referenced by the field with the given name.
     * 
     * @param activity
     * @param caseRefField
     * @return case class or <code>null</code> if this field does not exist or
     *         is not a case class reference.
     */
    private Class getCaseClassReferencedByField(Activity activity,
            ProcessRelevantData caseRefField) {

        if (caseRefField != null) {
            DataType dataType = caseRefField.getDataType();
            if (dataType instanceof RecordType) {
                EList<Member> member = ((RecordType) dataType).getMember();
                if (member != null && !member.isEmpty()) {
                    ExternalReference ref =
                            member.get(0).getExternalReference();
                    if (ref != null) {
                        EObject obj =
                                ProcessUIUtil
                                        .getReferencedClassifier(ref,
                                                WorkingCopyUtil
                                                        .getProjectFor(activity));
                        if (obj != null && obj instanceof Class
                                && BOMGlobalDataUtils.isCaseClass((Class) obj)) {
                            return (Class) obj;
                        }
                    }
                }
            }

        }

        return null;
    }

    /**
     * Find the process relevant data in scope of the given activity with the
     * given name.
     * 
     * @param activity
     * @param fieldName
     * @return
     */
    private ProcessRelevantData findProcessRelevantData(Activity activity,
            String fieldName) {
        List<ProcessRelevantData> relevantData =
                ProcessDataUtil.getProcessRelevantData(activity);
        for (ProcessRelevantData data : relevantData) {
            if (fieldName.equals(data.getName())) {
                return data;
            }
        }
        return null;
    }

    /**
     * Find the data with the given name in scope of the activity (takes into
     * account the activity interface). If the field is defined in the process
     * but is not in scope of this activity then an issue will be created.
     * 
     * @param activity
     * @param fieldName
     * @return
     */
    private ProcessRelevantData validateActivityRelevantData(Activity activity,
            String fieldName) {
        ProcessRelevantData data = null;
        Collection<ActivityInterfaceData> interfaceData =
                ActivityInterfaceDataUtil.getActivityInterfaceData(activity);
        for (ActivityInterfaceData iData : interfaceData) {
            if (fieldName.equals(iData.getName())) {
                data = iData.getData();
                break;
            }
        }

        if (data == null) {
            /*
             * Check if this field is defined in the process - if it is then
             * that means that it is not in scope of this activity.
             * 
             * Sid XPD-6061: This change (XPD-5883) meant that we only check
             * whether field was associated implicitly/explicitly with activity
             * and stopped caring if the named field actually existed AT ALL. So
             * changed the error to say "not exist or not associated".
             */
            addIssue(FIELD_NOT_EXIST_OR_NOT_ASSOCIATED,
                    activity,
                    Collections.singletonList(fieldName));
        }

        return data;
    }

    /**
     * @param activity
     * @param caseClass
     * @param create
     */
    private void validateCreateOperation(Activity activity, Class caseClass,
            CreateCaseOperationType create) {
        String fromFieldPath = create.getFromFieldPath();

        if (fromFieldPath != null) {
            /*
             * The local data from field should be of the caseClass type
             */
            ConceptPath fromFieldConceptPath = null;
            fromFieldConceptPath = resolveConceptPath(activity, fromFieldPath);

            boolean isValidFromField =
                    fromFieldConceptPath != null
                            && caseClass.equals(fromFieldConceptPath.getType());

            if (isValidFromField) {
                ProcessRelevantData data =
                        validateActivityRelevantData(activity,
                                fromFieldConceptPath.getRoot().getBaseName());

                /*
                 * XPD-5853: Saket: "Local Data Value" should only be allowing
                 * BOM Data Types (Not references).
                 */
                if (null != data) {
                    if (((data.getDataType()) instanceof RecordType)) {
                        // Invalid from local field type
                        addIssue(CREATE_OP_VALIDLOCALDATATYPE, activity);

                    }
                }
                /*
                 * The case reference field should also be of the case class
                 * type
                 */
                boolean isValidCaseRef = false;

                String caseRefField = create.getToCaseRefField();
                ProcessRelevantData caseRefData = null;
                if (caseRefField != null && !caseRefField.isEmpty()) {
                    caseRefData =
                            validateActivityRelevantData(activity, caseRefField);
                    if (caseRefData != null
                            && caseRefData.getDataType() instanceof RecordType) {
                        RecordType recordType =
                                (RecordType) caseRefData.getDataType();
                        if (!recordType.getMember().isEmpty()) {
                            ExternalReference extRef =
                                    recordType.getMember().get(0)
                                            .getExternalReference();
                            if (extRef != null) {
                                Class refClass =
                                        getReferencedClass(WorkingCopyUtil.getProjectFor(activity),
                                                extRef);

                                isValidCaseRef =
                                        refClass != null
                                                && refClass.equals(caseClass);
                            }
                        }
                    }
                }

                if (isValidCaseRef) {
                    /*
                     * Now check that the multiplicity of the local data and
                     * case object reference match
                     */
                    if (caseRefData != null && fromFieldConceptPath != null) {
                        if (caseRefData.isIsArray() != fromFieldConceptPath
                                .isArray()) {
                            addIssue(CREATE_OP_VALIDMULTIPLICITY, activity);
                        }
                    }
                } else {
                    /*
                     * Case object reference type does not match the selected
                     * case class
                     */
                    addIssue(CREATE_OP_VALIDCASEREF,
                            activity,
                            Collections.singletonList(caseClass.getName()));

                }
            } else {
                // Invalid from local field
                addIssue(CREATE_OP_VALIDLOCALDATA,
                        activity,
                        Collections.singletonList(caseClass.getName()));
            }
        } else {
            // No from local field specified
            addIssue(CREATE_OP_VALIDLOCALDATA,
                    activity,
                    Collections.singletonList(caseClass.getName()));
        }
    }

    /**
     * Check if an operation is selected in the given
     * {@link GlobalDataOperation}.
     * 
     * @param globalDataOp
     * @return
     */
    private boolean isOperationSelected(GlobalDataOperation globalDataOp) {
        CaseAccessOperationsType caseAccessOps =
                globalDataOp.getCaseAccessOperations();
        CaseReferenceOperationsType caseRefOps =
                globalDataOp.getCaseReferenceOperations();

        return (caseAccessOps != null && (caseAccessOps.getCreate() != null
                || caseAccessOps.getDeleteByCaseIdentifier() != null || caseAccessOps
                .getDeleteByCompositeIdentifiers() != null))
                || (caseRefOps != null && (caseRefOps.getUpdate() != null
                        || caseRefOps.getAddLinkAssociations() != null
                        || caseRefOps.getRemoveLinkAssociations() != null
                        || caseRefOps.getRemoveAllLinksByName() != null || caseRefOps.getDelete() != null));
    }

    /**
     * Get the GlobalDataOperation from the given task service.
     * 
     * @return {@link GlobalDataOperation} or <code>null</code> if not set.
     */
    private GlobalDataOperation getGlobalDataOperation(Activity activity) {
        Implementation implementation = activity.getImplementation();
        if (implementation instanceof Task) {
            Task task = (Task) implementation;
            if (task.getTaskService() != null) {
                return (GlobalDataOperation) TaskServiceExtUtil
                        .getExtendedModel(task.getTaskService(),
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_GlobalDataOperation());
            }
        }
        return null;
    }

}
