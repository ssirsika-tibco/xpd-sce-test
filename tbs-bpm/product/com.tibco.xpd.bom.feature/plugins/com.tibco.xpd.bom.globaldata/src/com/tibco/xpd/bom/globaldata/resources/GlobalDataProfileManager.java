/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */

package com.tibco.xpd.bom.globaldata.resources;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.Resource.Diagnostic;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;

import com.tibco.xpd.bom.globaldata.GlobalDataActivator;
import com.tibco.xpd.bom.globaldata.api.BOMGlobalDataUtils;
import com.tibco.xpd.bom.globaldata.api.BOMGlobalDataUtils.AutoCidProperty;
import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.bom.resources.utils.UML2ModelUtil;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.logger.Logger;

/**
 * The Global Data BOM profile manager (initially conceived for use with
 * Persistent BOM). Use {@link #getInstance()} to get the singleton instanceof
 * of this class.
 * 
 * @author rgreen
 * 
 */
public class GlobalDataProfileManager {

    private static final String PROFILE_PATH =
            "pathmap://GLOBALDATA_PROFILES/BOMGlobalData.profile.uml"; //$NON-NLS-1$

    private Profile globalDataProfile;

    private final TransactionalEditingDomain editingDomain;

    private final Logger logger;

    public static final int LENGTH_AUTO_CASE_IDENTIFIER = 19;

    /**
     * Cache to store all the stereotypes from the profile. This is fully loaded
     * on instantiation.
     */
    private final Map<StereotypeKind, Stereotype> stereotypesMap =
            new HashMap<StereotypeKind, Stereotype>();

    /**
     * Reversed (K<->V) map of <code>stereotypesMap</code> (as no bimap
     * collection type handy). This is fully loaded on instantiation.
     */
    private final Map<Stereotype, StereotypeKind> stereotypesRMap =
            new HashMap<Stereotype, StereotypeKind>();

    /**
     * Stereotypes available in the Global Data profile.
     */
    public enum StereotypeKind {
        CASE("Case"), //$NON-NLS-1$ 
        GLOBAL("Global"), //$NON-NLS-1$ 
        CID("CaseIdentifier"), //$NON-NLS-1$ 
        SEARCHABLE("Searchable"), //$NON-NLS-1$
        TAG("Tag"), //$NON-NLS-1$
        AUTO_CASE_IDENTIFIER("AutoCaseIdentifier"), //$NON-NLS-1$
        COMPOSITE_CASE_IDENTIFIER("CompositeCaseIdentifier"), //$NON-NLS-1$
        FETCHING("Fetching"), //$NON-NLS-1$
        CASE_STATE("CaseState"), //$NON-NLS-1$
        CASE_DOCUMENTATION_ENABLED("CaseDocumentationEnabled"); //$NON-NLS-1$
        private final String id;

        StereotypeKind(String id) {
            this.id = id;
        }

        public String getId() {
            return id;
        }

        /**
         * @see java.lang.Enum#toString()
         * 
         * @return
         */
        @Override
        public String toString() {
            return id;
        }
    }

    private GlobalDataProfileManager() {
        // Private constructor
        editingDomain = XpdResourcesPlugin.getDefault().getEditingDomain();
        Assert.isNotNull(editingDomain);
        logger = GlobalDataActivator.getDefault().getLogger();
        setProfileStereotypeMaps(getProfile());
    }

    /**
     * Ensure GlobalDataProfileManager lazy initialising will always be
     * thread-safe - with holder class idiom
     */
    private static class GlobalDataProfileManagerHolder {
        static final GlobalDataProfileManager instance =
                new GlobalDataProfileManager();
    }

    public static GlobalDataProfileManager getInstance() {
        return GlobalDataProfileManagerHolder.instance;
    }

    /**
     * @param profile
     * @param map
     * @param rMap
     */
    private void setProfileStereotypeMaps(Profile profile) {

        if (profile != null) {
            for (StereotypeKind kind : StereotypeKind.values()) {
                Stereotype profileStereotype =
                        profile.getOwnedStereotype(kind.getId());
                if (profileStereotype != null) {
                    stereotypesMap.put(kind, profileStereotype);
                    stereotypesRMap.put(profileStereotype, kind);
                }
            }
        }
    }

    /**
     * Apply the global data profile to the given model. If it already has the
     * profile applied then the call to this method will not do anything.
     * 
     * @param model
     * @return <code>true</code> if the profile was applied.
     */
    public boolean applyGlobalDataProfile(Model model) {
        Profile profile = getProfile();
        if (model != null && profile != null
                && !model.getAppliedProfiles().contains(profile)) {
            model.applyProfile(profile);
            return true;
        }

        return false;
    }

    /**
     * Get the Global Data BOM profile. The profile will be cached after the
     * first call to this method.
     * 
     * @return Profile
     */
    public Profile getProfile() {
        if (globalDataProfile == null) {
            // Load the profile
            Resource res = editingDomain.loadResource(PROFILE_PATH);
            if (res != null && res.getErrors().isEmpty()) {
                EList<EObject> contents = res.getContents();
                if (contents != null) {
                    for (EObject content : contents) {
                        if (content instanceof Profile) {
                            globalDataProfile = (Profile) content;
                            break;
                        }
                    }
                }
            } else {
                if (res != null && !res.getErrors().isEmpty()) {
                    List<IStatus> errors = new ArrayList<IStatus>();
                    // If there are errors then log them
                    for (Diagnostic diagnostic : res.getErrors()) {
                        errors.add(new Status(IStatus.ERROR,
                                GlobalDataActivator.PLUGIN_ID,
                                String.format("%1$s (Line: %2$d, Column: %3$d", //$NON-NLS-1$
                                        diagnostic.getMessage(),
                                        diagnostic.getLine(),
                                        diagnostic.getColumn())));
                    }
                    logger.log(new MultiStatus(GlobalDataActivator.PLUGIN_ID,
                            IStatus.ERROR, errors.toArray(new IStatus[errors
                                    .size()]),
                            "Failed to load the BOM profile", null)); //$NON-NLS-1$
                } else {
                    logger.error("Failed to find the BOM profile in the profile resource."); //$NON-NLS-1$
                }
            }
        }
        return globalDataProfile;
    }

    /**
     * Get the stereotype of the given kind from the BOM profile.
     * 
     * @param kind
     * @return Stereotype
     */
    public Stereotype getStereotype(StereotypeKind kind) {
        if (kind != null) {
            Stereotype stereotype = stereotypesMap.get(kind);

            Profile profile = getProfile();
            if (stereotype == null && profile != null) {
                stereotype = profile.getOwnedStereotype(kind.getId());
                if (stereotype == null) {
                    logger.error(String
                            .format("'%s' profile does not contain stereotype '%s'", //$NON-NLS-1$
                                    profile.getName(),
                                    kind.getId()));
                }
            }
            return stereotype;
        }
        return null;
    }

    /**
     * Returns true if the BOM has any global data elements, i.e., a Global or a
     * Case class
     * 
     * @param model
     * @return boolean
     * @deprecated use {@link hasGlobalDataProfile} to check whether model has
     *             Global Profile applied or
     *             {@link BOMGlobalDataUtils#hasGlobalDataElements} to check
     *             whether model contains any Global Data entities within it.
     *             Did not remove as at time of writing in an exported package.
     */
    @Deprecated
    public boolean isGlobalDataBOM(Model model) {
        return BOMGlobalDataUtils.isGlobalDataBOM(model);
    }

    /**
     * Applies the Case class stereotype to the supplied class, but only if the
     * Global Data profile has been applied to the containing Model
     * 
     * @param clazz
     */
    public void toCase(Class clazz) {
        if (BOMGlobalDataUtils.hasGlobalDataProfile(clazz.getModel())) {

            // If Class is already a Global we need to unapply the stereotype
            if (isGlobal(clazz)) {
                Stereotype stGlobal = getStereotype(StereotypeKind.GLOBAL);
                clazz.unapplyStereotype(stGlobal);
            }

            Stereotype stereotype = getStereotype(StereotypeKind.CASE);
            UML2ModelUtil.safeApplyStereotype(clazz, stereotype);
        }
    }

    /**
     * Returns true if the supplied Class has the Global Data stereotype "Case"
     * applied.
     * 
     * @param clazz
     * @return
     */
    public boolean isCase(Class clazz) {
        return checkForStereotype(clazz, StereotypeKind.CASE);
    }

    /**
     * Applies the Global class stereotype to the supplied class, but only if
     * the Global Data profile has been applied to the containing Model
     * 
     * @param clazz
     */
    public void toGlobal(Class clazz) {
        if (BOMGlobalDataUtils.hasGlobalDataProfile(clazz.getModel())) {
            Stereotype stereotype = getStereotype(StereotypeKind.GLOBAL);
            UML2ModelUtil.safeApplyStereotype(clazz, stereotype);
        }
    }

    /**
     * Returns true if the supplied Class has the Global Data stereotype
     * "Global" applied.
     * 
     * @param clazz
     * @return boolean
     */
    public boolean isGlobal(Class clazz) {
        return checkForStereotype(clazz, StereotypeKind.GLOBAL);
    }

    /**
     * Returns true if the supplied Class does NOT have the Global Data
     * stereotype "Global" applied.
     * 
     * @param clazz
     * @return boolean
     */
    public boolean isLocal(Class clazz) {
        boolean isLocalClass = true;

        if (isGlobal(clazz) || isCase(clazz)) {
            isLocalClass = false;
        }

        return isLocalClass;
    }

    /**
     * Returns true if the case class has case documentation enabled
     * 
     * @param clazz
     *            Class to check
     * @return
     */
    public boolean isCaseDocumentationEnabled(Class clazz) {
        // If it is not a case class, then it is not possible to enable for Case
        // Documentation
        if (!isCase(clazz)) {
            return false;
        }
        return checkForStereotype(clazz,
                StereotypeKind.CASE_DOCUMENTATION_ENABLED);
    }

    /**
     * Returns true if the supplied Property has the Global Data stereotype
     * "CaseIdentifier" applied.
     * 
     * @param clazz
     * @return boolean
     */
    public boolean isCID(Property prop) {
        return checkForStereotype(prop, StereotypeKind.CID);
    }

    /**
     * Returns true if the supplied Property has the Global Data stereotype
     * "AutoCaseIdentifier" applied.
     * 
     * @param clazz
     * @return boolean
     */
    public boolean isAutoCaseIdentifier(Property prop) {
        return checkForStereotype(prop, StereotypeKind.AUTO_CASE_IDENTIFIER);
    }

    /**
     * Returns true if the supplied Property has the Global Data stereotype
     * "AutoCaseIdentifier" applied.
     * 
     * @param clazz
     * @return boolean
     */
    public boolean isCaseState(Property prop) {
        return checkForStereotype(prop, StereotypeKind.CASE_STATE);
    }

    /**
     * Returns true if the supplied Property has the Global Data stereotype
     * "CompositeCaseIdentifier" applied.
     * 
     * @param clazz
     * @return boolean
     */
    public boolean isCompositeCaseIdentifier(Property prop) {
        return checkForStereotype(prop,
                StereotypeKind.COMPOSITE_CASE_IDENTIFIER);
    }

    /**
     * Returns true if the supplied Property has the Global Data stereotype
     * "Searchable" applied.
     * 
     * @param clazz
     * @return boolean
     */
    public boolean isSearchable(Property prop) {

        boolean isApplied = false;

        EList<Stereotype> appliedStereotypes = prop.getAppliedStereotypes();

        Stereotype st = getStereotype(StereotypeKind.SEARCHABLE);

        for (Stereotype stereotype : appliedStereotypes) {
            if (st == stereotype) {
                isApplied = true;
                break;
            }
            if (stereotype == getStereotype(StereotypeKind.CID)) {
                EList<Generalization> generalizations =
                        stereotype.getGeneralizations();

                if (!generalizations.isEmpty()) {
                    Generalization gen = generalizations.get(0);

                    if (gen.getGeneral() == st) {
                        isApplied = true;
                        break;
                    }

                }
            }
        }

        return isApplied;

    }

    /**
     * @param element
     * @param kind
     * @return
     */
    public boolean checkForStereotype(Element element, StereotypeKind kind) {
        boolean isApplied = false;
        Stereotype gDataStereotype = getStereotype(kind);
        EList<Stereotype> appliedStereotypes = element.getAppliedStereotypes();

        for (Stereotype st : appliedStereotypes) {
            if (st == gDataStereotype) {
                isApplied = true;
                break;
            }
        }

        return isApplied;
    }

    /**
     * @param element
     * @return All {@link StereotypeKind}s applied to the passed in
     *         {@link Element}
     */
    public Set<StereotypeKind> getAppliedStereotypeKinds(Element element) {

        Set<StereotypeKind> ret = new HashSet<StereotypeKind>();

        for (Stereotype stereotype : element.getAppliedStereotypes()) {
            if (stereotypesRMap.containsKey(stereotype)) {
                ret.add(stereotypesRMap.get(stereotype));
            }
        }

        return ret;
    }

    /**
     * Records, in the passed in {@Set}s, all the {@link StereotypeKind}s
     * that are applied to parent and child classes of the passed in
     * {@link Generalization}.
     * 
     * @param generalization
     * @param superclassTypes
     * @param subclassTypes
     * @return <code>true</code> if the {@link Class}es of the passed in
     *         relationship were successfully determined.
     */
    public boolean getClassStereotypeKinds(Generalization generalization,
            Set<StereotypeKind> superclassTypes,
            Set<StereotypeKind> subclassTypes) {

        Classifier general = generalization.getGeneral();
        Classifier specific = generalization.getSpecific();

        if (general instanceof Class && specific instanceof Class) {
            Class subClass = (Class) specific;
            Class superClass = (Class) general;

            subclassTypes.addAll(getAppliedStereotypeKinds(subClass));
            superclassTypes.addAll(getAppliedStereotypeKinds(superClass));

            return true;
        }

        return false;
    }

    /**
     * Records, in the passed in {@Set}s, all the {@link StereotypeKind}s
     * that are applied to source and target classes of the passed in
     * {@link Association}.
     * 
     * @param association
     * @param sourceclassTypes
     * @param targetclassTypes
     * @return <code>true</code> if the {@link StereotypeKind}s for the ends of
     *         the passed in relationship were successfully determined.
     */
    public boolean getClassStereotypeKinds(Association association,
            Set<StereotypeKind> sourceclassTypes,
            Set<StereotypeKind> targetclassTypes) {

        List<Class> associationEnds =
                UML2ModelUtil.getAssociationEnds(association);

        if (associationEnds.size() >= 2) {
            sourceclassTypes.addAll(getAppliedStereotypeKinds(associationEnds
                    .get(0)));
            targetclassTypes.addAll(getAppliedStereotypeKinds(associationEnds
                    .get(1)));

            return true;
        } else {
            String fmtStr =
                    "Failed to obtain Stereotype kinds for ends of association %s."; //$NON-NLS-1$
            logger.debug(String.format(fmtStr, association.getName()));
        }

        return false;
    }

    /**
     * Attempts to remove Stereotype from the passed in {@link Class}
     * 
     * @param clazz
     * @param kind
     * @return <code>true</code> if removal appears to have been successful
     */
    public boolean remove(Class clazz, StereotypeKind kind) {

        if (BOMGlobalDataUtils.hasGlobalDataProfile(clazz.getModel())) {
            // Check that the stereotype is set before removing it
            if (checkForStereotype(clazz, kind)) {
                clazz.unapplyStereotype(getStereotype(kind));
                return true;
            } else {
                String fmtStr =
                        "Attempt made to remove Stereotype '%s' from Class element'%s' but class found not to have Stereotype."; //$NON-NLS-1$
                logger.debug(String.format(fmtStr,
                        kind.toString(),
                        clazz.getLabel()));
            }
        }

        return false;
    }

    /**
     * Attempts to remove Stereotype from the passed in {@link Property}
     * 
     * @param prop
     * @param kind
     * @return <code>true</code> if removal appears to have been successful
     */
    public boolean remove(Property prop, StereotypeKind kind) {

        if (BOMGlobalDataUtils.hasGlobalDataProfile(prop.getModel())) {
            // Check that the stereotype is set before removing it
            if (checkForStereotype(prop, kind)) {
                prop.unapplyStereotype(getStereotype(kind));
                return true;
            } else {
                String fmtStr =
                        "Attempt made to remove Stereotype '%s' from Property element'%s' but property found not to have Stereotype."; //$NON-NLS-1$
                logger.debug(String.format(fmtStr,
                        kind.toString(),
                        prop.getLabel()));
            }
        }

        return false;
    }

    /**
     * Attempts to add Stereotype to the passed in {@link Class}. If Stereotypes
     * that are incompatible with the one passed in are present then they are
     * removed.
     * 
     * @param clazz
     * @param kind
     * @return <code>true</code> if setting appears to have been successful
     */
    public boolean add(Class clazz, StereotypeKind kind) {

        if (BOMGlobalDataUtils.hasGlobalDataProfile(clazz.getModel())) {

            if (checkForStereotype(clazz, kind)) {
                String fmtStr =
                        "Attempt made to add Stereotype '%s' to Class element'%s' but class found to already have Stereotype applied."; //$NON-NLS-1$
                logger.debug(String.format(fmtStr,
                        kind.toString(),
                        clazz.getLabel()));
            } else {
                removeIncompatibleStereotypes(clazz, kind);
                UML2ModelUtil.safeApplyStereotype(clazz, getStereotype(kind));
                return true;
            }
        }
        return false;
    }

    /**
     * Attempts to add Stereotype to the passed in {@link Property}.
     * 
     * @param prop
     * @param kind
     * @return <code>true</code> if setting appears to have been successful
     */
    public boolean add(Property prop, StereotypeKind kind) {

        if (BOMGlobalDataUtils.hasGlobalDataProfile(prop.getModel())) {

            if (checkForStereotype(prop, kind)) {
                String fmtStr =
                        "Attempt made to add Stereotype '%s' to Property element'%s' but property found to already have Stereotype applied."; //$NON-NLS-1$
                logger.debug(String.format(fmtStr,
                        kind.toString(),
                        prop.getLabel()));
            } else {
                // If adding a stereotype for case state or case identifier then
                // make sure there is no stereotype for searchable
                if ((kind == StereotypeKind.CASE_STATE)
                        || (kind == StereotypeKind.CID)
                        || (kind == StereotypeKind.AUTO_CASE_IDENTIFIER)
                        || (kind == StereotypeKind.COMPOSITE_CASE_IDENTIFIER)) {
                    if (checkForStereotype(prop, StereotypeKind.SEARCHABLE)) {
                        remove(prop, StereotypeKind.SEARCHABLE);
                    }
                }
                UML2ModelUtil.safeApplyStereotype(prop, getStereotype(kind));
                return true;
            }
        }
        return false;
    }

    /**
     * @param clazz
     * @param kind
     * @return <code>true</code> if <@link StereotypeKind>s needed to be removed
     */
    private boolean removeIncompatibleStereotypes(Class clazz,
            StereotypeKind kind) {

        Set<StereotypeKind> setOfExclusives =
                EnumSet.of(StereotypeKind.CASE, StereotypeKind.GLOBAL);
        Set<StereotypeKind> appliedStereotypeKinds =
                getAppliedStereotypeKinds(clazz);

        setOfExclusives.retainAll(appliedStereotypeKinds);
        setOfExclusives.remove(kind);

        if (!setOfExclusives.isEmpty()) {
            for (StereotypeKind exclusiveKind : setOfExclusives) {
                Stereotype st = getStereotype(exclusiveKind);
                clazz.unapplyStereotype(st);
            }
            return true;
        }
        return false;
    }

    /**
     * Returns the Global Data StereoType applied to this property, CID,
     * AutoCaseIdentifier, Searchable. Returns null when none are applied.
     * 
     * @param property
     * @return
     */
    public Stereotype getAppliedGlobalDataStereotype(Property property) {
        // Note: Auto and composite extend CID so should be checked first
        if (isAutoCaseIdentifier(property)) {
            return getStereotype(StereotypeKind.AUTO_CASE_IDENTIFIER);
        } else if (isCompositeCaseIdentifier(property)) {
            return getStereotype(StereotypeKind.COMPOSITE_CASE_IDENTIFIER);
        } else if (isCID(property)) {
            return getStereotype(StereotypeKind.CID);
        } else if (isSearchable(property)) {
            return getStereotype(StereotypeKind.SEARCHABLE);
        } else if (isCaseState(property)) {
            return getStereotype(StereotypeKind.CASE_STATE);
        }
        return null;
    }

    /**
     * Returns the batch size if the stereotype is applied Otherwise returns
     * null
     * 
     * @param namedElement
     * @return
     */
    public Integer getFetchingBatchSize(NamedElement namedElement) {
        Integer batchSize = null;

        if (namedElement.hasValue(getStereotype(StereotypeKind.FETCHING),
                BOMResourcesPlugin.ModelGlobalDataProfile_Fetching_BatchSize)) {
            Object value =
                    namedElement
                            .getValue(getStereotype(StereotypeKind.FETCHING),
                                    BOMResourcesPlugin.ModelGlobalDataProfile_Fetching_BatchSize);
            if (value instanceof Integer) {
                batchSize = (Integer) value;
            }
        }
        return batchSize;
    }

    /**
     * Gets the value of the Auto Case Id meta property.
     * 
     * @param property
     *            the auto case id attribute.
     * @param autoCidProperty
     *            the enum for the meta property.
     * @return the value of the meta property for Auto Case ID.
     */
    public Object getAutoCidPropetyValueInternal(Property property,
            AutoCidProperty autoCidProperty) {
        return property.getValue(
                    getStereotype(StereotypeKind.AUTO_CASE_IDENTIFIER),
                    autoCidProperty.getName());
    }

    /**
     * Sets the value of the Auto Case Id meta property.
     * 
     * @param property
     *            the auto case id attribute.
     * @param autoCidProperty
     *            the enum for the meta property.
     * @param value
     *            the value of the meta property to be set.
     */
    public void setAutoCidPropetyValueInternal(Property property,
            AutoCidProperty autoCidProperty, Object value) {
        property.setValue(
                getStereotype(StereotypeKind.AUTO_CASE_IDENTIFIER),
                autoCidProperty.getName(),
                value);
    }

}
