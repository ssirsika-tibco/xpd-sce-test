package com.tibco.xpd.bom.validator.rules.globaldata;

import static com.tibco.xpd.bom.globaldata.resources.GlobalDataProfileManager.StereotypeKind.CASE;
import static com.tibco.xpd.bom.globaldata.resources.GlobalDataProfileManager.StereotypeKind.GLOBAL;
import static com.tibco.xpd.bom.validator.internal.IAdditionalInfoMarkerKeys.ADDITIONAL_STEREOTYPE_KIND_NAME;
import static com.tibco.xpd.bom.validator.internal.IAdditionalInfoMarkerKeys.RELATIONSHIP_LOCATION;
import static com.tibco.xpd.bom.validator.internal.IAdditionalInfoMarkerKeys.RELATIONSHIP_NAME;
import static com.tibco.xpd.bom.validator.internal.IAdditionalInfoMarkerKeys.TARGETS_FRAGMENT_URI_LOCATION;
import static com.tibco.xpd.bom.validator.internal.IAdditionalInfoMarkerKeys.TARGETS_RESOURCE_URI_LOCATION;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.emf.workspace.util.WorkspaceSynchronizer;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Generalization;

import com.tibco.xpd.bom.globaldata.resources.GlobalDataProfileManager;
import com.tibco.xpd.bom.globaldata.resources.GlobalDataProfileManager.StereotypeKind;
import com.tibco.xpd.bom.resources.utils.BOMUtils;
import com.tibco.xpd.bom.validator.internal.Messages;
import com.tibco.xpd.bom.validator.util.BOMValidationUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.validation.provider.IValidationScope;
import com.tibco.xpd.validation.rules.IValidationRule;

/**
 * Validates an Generialization's end classes' applied stereotypes within BOMs
 * 
 * @author patkinso
 */
public class GeneralizationEndsRule implements IValidationRule {

    protected final static String INTRABOM_ISSUE_ID =
            "bom.globaldata.generalization.intrabom.issue"; //$NON-NLS-1$

    protected static String INTERBOM_ISSUE_ID =
            "bom.globaldata.generalization.interbom.issue"; //$NON-NLS-1$

    protected static String INTERBOM_BDP_NON_BDP_ISSUE_ID =
            "bom.globaldata.generalization.interbom.bdpnonbdp.issue"; //$NON-NLS-1$

    protected static String INTRABOM_HAS_MUTABLE_SOLUTION_ISSUE_ID =
            "bom.globaldata.generalization.intrabom.mutable.issue"; //$NON-NLS-1$

    protected static String INTERBOM_HAS_MUTABLE_SOLUTION_ISSUE_ID =
            "bom.globaldata.generalization.interbom.mutable.extraneous.issue"; //$NON-NLS-1$   

    protected static String GENERALIZATION_LABEL =
            Messages.GeneralizationEndsRule_generalization_relationship_label;

    protected final String SUPERCLASS_ARG =
            Messages.GeneralizationTypesRule_superclass_issue_arg_message;

    protected final String SUBCLASS_ARG =
            Messages.GeneralizationTypesRule_subclass_issue_arg_message;

    protected final String CASETYPE_ARG =
            Messages.ClassStereotypes_case_type_issue_arg_message;

    protected final String GLOBALTYPE_ARG =
            Messages.ClassStereotypes_global_type_issue_arg_message;

    protected final String LOCALTYPE_ARG =
            Messages.ClassStereotypes_local_type_issue_arg_message;

    protected GlobalDataProfileManager gdManager = GlobalDataProfileManager
            .getInstance();

    /**
     * Holder for data relating to suggested class mutation solution
     * 
     * @author patkinso
     * @since 27 Sep 2013
     */
    private class ClassMutationSolution {

        StereotypeKind viableMutationToType = null;

        Classifier viableClassToMutate = null;

        String issueID;

        /**
         * @param viableMutationToType
         * @param viableClassToMutate
         * @param issueID
         */
        private ClassMutationSolution(StereotypeKind viableMutationToType,
                Classifier viableClassToMutate, String issueID) {
            super();
            this.viableMutationToType = viableMutationToType;
            this.viableClassToMutate = viableClassToMutate;
            this.issueID = issueID;
        }

    }

    /**
     * @see com.tibco.xpd.validation.rules.IValidationRule#validate(com.tibco.xpd.validation.provider.IValidationScope,
     *      java.lang.Object)
     * 
     * @param scope
     * @param o
     */
    @Override
    public void validate(IValidationScope scope, Object o) {

        Generalization generalization = (Generalization) o;

        Set<StereotypeKind> superclassTypes = new HashSet<StereotypeKind>();
        Set<StereotypeKind> subclassTypes = new HashSet<StereotypeKind>();

        boolean generalizationHasValidClasses =
                gdManager.getClassStereotypeKinds(generalization,
                        superclassTypes,
                        subclassTypes);

        if (generalizationHasValidClasses) {
            if (BOMUtils.isIntraBomRelationship(generalization)) {
                doIntraBomValidation(scope,
                        generalization,
                        superclassTypes,
                        subclassTypes);
            } else {
                doInterBomValidation(scope,
                        generalization,
                        superclassTypes,
                        subclassTypes);
            }
        }
    }

    /**
     * @param scope
     * @param generalization
     * @param superclassTypes
     * @param subclassTypes
     */
    private void doInterBomValidation(IValidationScope scope,
            Generalization generalization, Set<StereotypeKind> superclassTypes,
            Set<StereotypeKind> subclassTypes) {

        Classifier subClass = generalization.getSpecific();
        Classifier superClass = generalization.getGeneral();

        ClassMutationSolution mutationSolution = null;
        String[] msgParams = null;
        boolean subBDPAndSuperNonBDP = false;

        if (superclassTypes.contains(CASE)) {
            if (subclassTypes.contains(CASE)) {
                // Add a check to see if it is in the same project
                if (!BOMUtils.hasSameProject(subClass, superClass)) {
                    // disallowed!
                    msgParams =
                            new String[] { SUPERCLASS_ARG, CASETYPE_ARG,
                                    SUBCLASS_ARG, CASETYPE_ARG };
                }
            } else if (subclassTypes.contains(GLOBAL)) {
                // disallowed!
                msgParams =
                        new String[] { SUPERCLASS_ARG, CASETYPE_ARG,
                                SUBCLASS_ARG, GLOBALTYPE_ARG };
            } else { // subClass is LOCAL
                // disallowed!
                msgParams =
                        new String[] { SUPERCLASS_ARG, CASETYPE_ARG,
                                SUBCLASS_ARG, LOCALTYPE_ARG };
            }
        } else if (superclassTypes.contains(GLOBAL)) {
            if (subclassTypes.contains(CASE)) {
                // disallowed!
                msgParams =
                        new String[] { SUPERCLASS_ARG, GLOBALTYPE_ARG,
                                SUBCLASS_ARG, CASETYPE_ARG };
            } else if (subclassTypes.contains(GLOBAL)) {
                // Add a check to see if it is in the same project
                if (!BOMUtils.hasSameProject(subClass, superClass)) {
                    // disallowed!
                    msgParams =
                            new String[] { SUPERCLASS_ARG, GLOBALTYPE_ARG,
                                    SUBCLASS_ARG, GLOBALTYPE_ARG };
                }
            } else { // subClass is LOCAL
                // disallowed!
                msgParams =
                        new String[] { SUPERCLASS_ARG, GLOBALTYPE_ARG,
                                SUBCLASS_ARG, LOCALTYPE_ARG };
            }
        } else { // superClass is LOCAL
            if (subclassTypes.contains(CASE)) {
                // disallowed!
                msgParams =
                        new String[] { SUPERCLASS_ARG, LOCALTYPE_ARG,
                                SUBCLASS_ARG, CASETYPE_ARG };
            } else if (subclassTypes.contains(GLOBAL)) {
                // disallowed!
                msgParams =
                        new String[] { SUPERCLASS_ARG, LOCALTYPE_ARG,
                                SUBCLASS_ARG, GLOBALTYPE_ARG };
            } else { // subClass is LOCAL
                // A local class in a Business Data Project (BDP)
                // is not allowed to generalize a local class in
                // a non-BDP. Note: The superclass could only
                // be a local class in this scenario.
                IProject subProj = WorkingCopyUtil.getProjectFor(subClass);
                if (BOMUtils.isBusinessDataProject(subProj)) {
                    // Sub-class is in a BDP, so this is illegal if the
                    // super-class isn't also in a BDP.
                    // Force the working copy for the superclass's BOM to be
                    // loaded.
                    IFile file =
                            WorkspaceSynchronizer.getFile(superClass
                                    .eResource());
                    // In the case where a BOM has been moved from one project
                    // to another where they referenced other projects
                    // previously. If the new target project does not have the
                    // correct dependency on the required project, then the
                    // reference will be broken. This will result in null being
                    // returned, there is another validation that deals with a
                    // broken reference so we do not need to highlight it here
                    if (file != null) {
                        WorkingCopy wc = WorkingCopyUtil.getWorkingCopy(file);
                        assert wc != null;
                        wc.getRootElement();

                        IProject superProj =
                                WorkingCopyUtil.getProjectFor(superClass);
                        if (!BOMUtils.isBusinessDataProject(superProj)) {
                            // Super-class in non-BDP, so illegal
                            // (Different message format than other scenarios,
                            // as per spec)
                            msgParams =
                                    new String[] { subClass.getName(),
                                            superClass.getName() };
                            // Set flag so we can use an appropriate issue id
                            subBDPAndSuperNonBDP = true;
                        }
                    }
                }

                // allowed
            }
        }

        if (msgParams != null) {

            /**
             * bharti: TODO: currently we dont support case classes depending on
             * case classes in other boms (by way of
             * generalisation/composition). but when we remove this validation
             * we might/might not (might not because we may still want to have
             * cac entries only for those that are referenced by ref from a
             * process) have to re-visit the script descriptor generation for
             * cac entries and/or requirements file requires-bundles
             */
            String issueID =
                    (mutationSolution == null) ? (subBDPAndSuperNonBDP ? INTERBOM_BDP_NON_BDP_ISSUE_ID
                            : INTERBOM_ISSUE_ID)
                            : mutationSolution.issueID;

            scope.createIssue(issueID,
                    BOMValidationUtil.getLocation(subClass),
                    subClass.eResource().getURIFragment(subClass),
                    Arrays.asList(msgParams),
                    getAdditionalInfo(mutationSolution, generalization));
        }
    }

    /**
     * @param scope
     * @param generalization
     * @param superclassTypes
     * @param subclassTypes
     */
    private void doIntraBomValidation(IValidationScope scope,
            Generalization generalization, Set<StereotypeKind> superclassTypes,
            Set<StereotypeKind> subclassTypes) {

        Classifier superClass = generalization.getGeneral();
        Classifier subClass = generalization.getSpecific();

        ClassMutationSolution mutationSolution = null;
        String[] msgParams = null;

        if (superclassTypes.contains(CASE)) {
            if (subclassTypes.contains(CASE)) {
                // allowed
            } else if (subclassTypes.contains(GLOBAL)) {
                // disallowed
                msgParams =
                        new String[] { SUPERCLASS_ARG, CASETYPE_ARG,
                                SUBCLASS_ARG, GLOBALTYPE_ARG };
                mutationSolution =
                        new ClassMutationSolution(CASE, subClass,
                                INTRABOM_HAS_MUTABLE_SOLUTION_ISSUE_ID);
            } else { // subClass is LOCAL
                // disallowed
                msgParams =
                        new String[] { SUPERCLASS_ARG, CASETYPE_ARG,
                                SUBCLASS_ARG, LOCALTYPE_ARG };
                mutationSolution =
                        new ClassMutationSolution(CASE, subClass,
                                INTRABOM_HAS_MUTABLE_SOLUTION_ISSUE_ID);
            }
        } else if (superclassTypes.contains(GLOBAL)) {
            if (subclassTypes.contains(CASE)) {
                // disallowed
                msgParams =
                        new String[] { SUPERCLASS_ARG, GLOBALTYPE_ARG,
                                SUBCLASS_ARG, CASETYPE_ARG };
            } else if (subclassTypes.contains(GLOBAL)) {
                // allowed
            } else { // subClass is LOCAL
                // disallowed
                msgParams =
                        new String[] { SUPERCLASS_ARG, GLOBALTYPE_ARG,
                                SUBCLASS_ARG, LOCALTYPE_ARG };
                mutationSolution =
                        new ClassMutationSolution(GLOBAL, subClass,
                                INTRABOM_HAS_MUTABLE_SOLUTION_ISSUE_ID);
            }
        } else { // superClass is LOCAL
            if (subclassTypes.contains(CASE)) {
                // disallowed
                msgParams =
                        new String[] { SUPERCLASS_ARG, LOCALTYPE_ARG,
                                SUBCLASS_ARG, CASETYPE_ARG };
            } else if (subclassTypes.contains(GLOBAL)) {
                // disallowed
                msgParams =
                        new String[] { SUPERCLASS_ARG, LOCALTYPE_ARG,
                                SUBCLASS_ARG, GLOBALTYPE_ARG };
                mutationSolution =
                        new ClassMutationSolution(GLOBAL, superClass,
                                INTRABOM_HAS_MUTABLE_SOLUTION_ISSUE_ID);
            } else { // subClass is LOCAL
                // allowed
            }
        }

        if (msgParams != null && msgParams.length == 4) {

            String issueID =
                    (mutationSolution == null) ? INTRABOM_ISSUE_ID
                            : mutationSolution.issueID;

            scope.createIssue(issueID,
                    generalization.eClass().getName(),
                    generalization.eResource().getURIFragment(generalization),
                    Arrays.asList(msgParams),
                    getAdditionalInfo(mutationSolution, generalization));
        }
    }

    /**
     * @param mutationSolution
     * @param generalization
     * @param additionalInfo
     */
    private Map<String, String> getAdditionalInfo(
            ClassMutationSolution mutationSolution,
            Generalization generalization) {

        Map<String, String> additionalInfo = new HashMap<String, String>();

        additionalInfo.put(RELATIONSHIP_LOCATION, generalization.eResource()
                .getURIFragment(generalization));

        if (mutationSolution != null) {
            additionalInfo.put(RELATIONSHIP_NAME, GENERALIZATION_LABEL);

            // populate with info for class mutation
            String stereotypeKindName =
                    mutationSolution.viableMutationToType.name();
            Classifier classToMutate = mutationSolution.viableClassToMutate;
            additionalInfo.put(ADDITIONAL_STEREOTYPE_KIND_NAME,
                    stereotypeKindName);
            additionalInfo.put(TARGETS_RESOURCE_URI_LOCATION, classToMutate
                    .eResource().getURI().toPlatformString(true));
            additionalInfo.put(TARGETS_FRAGMENT_URI_LOCATION, classToMutate
                    .eResource().getURIFragment(classToMutate));
        }

        return additionalInfo;
    }

    /**
     * @see com.tibco.xpd.validation.rules.IValidationRule#getTargetClass()
     * 
     * @return
     */
    @Override
    public java.lang.Class<?> getTargetClass() {
        return Generalization.class;
    }
}
