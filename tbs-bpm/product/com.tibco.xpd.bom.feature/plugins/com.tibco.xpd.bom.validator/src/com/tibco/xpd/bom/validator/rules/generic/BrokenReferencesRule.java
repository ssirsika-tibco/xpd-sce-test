/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.bom.validator.rules.generic;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.XMLResource.URIHandler;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.TypedElement;
import org.eclipse.uml2.uml.UMLPackage;

import com.tibco.xpd.bom.validator.GenericIssueIds;
import com.tibco.xpd.bom.validator.internal.Messages;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.util.XpdConsts;
import com.tibco.xpd.validation.provider.IValidationScope;
import com.tibco.xpd.validation.rules.IValidationRule;

/**
 * @author wzurek
 * 
 */
public class BrokenReferencesRule implements IValidationRule {

    /**
     * The additional information key for URI of operation parameter (or return
     * value parameter) causing problem.
     */
    public static final String OWNER_PARAMETER = "parameter"; //$NON-NLS-1$

    Set<EReference> reportedReferences;

    /**
     * 
     */
    public BrokenReferencesRule() {
        reportedReferences = new HashSet<EReference>();

        UMLPackage p = UMLPackage.eINSTANCE;
        reportedReferences.addAll(Arrays.asList(p.getGeneralization_General()));

    }

    @SuppressWarnings("unchecked")
    public Class getTargetClass() {
        return org.eclipse.uml2.uml.Model.class;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.validation.rules.IValidationRule#validate(com.tibco.xpd
     * .validation.provider.IValidationScope, java.lang.Object)
     */
    public void validate(IValidationScope scope, Object o) {
        Model model = (Model) o;

        Resource resource = model.eResource();
        if (resource != null) {

            /*
             * XPD-4699 First, if there are any broken references then try to
             * re-resolve these references before looking again. This is
             * required because when a referenced file is restored it may not
             * have been re-resolved by the working copy before this validation
             * rule gets invoked.
             */
            Map<EObject, Collection<Setting>> find =
                    EcoreUtil.CrossReferencer
                            .find(Collections.singleton(model));

            boolean hasBrokenReferences = false;

            for (Entry<EObject, Collection<Setting>> entry : find.entrySet()) {
                if (entry.getKey().eIsProxy()) {
                    hasBrokenReferences = true;
                    break;
                }
            }

            if (hasBrokenReferences) {
                // Re-resolve this resource and try again
                XpdResourcesPlugin.getDefault().revalidateReferences(resource);
                /*
                 * Now, once the references have been re-resolved have a look
                 * for broken references again, and if found report them.
                 */
                find =
                        EcoreUtil.CrossReferencer.find(Collections
                                .singleton(model));

                for (Entry<EObject, Collection<Setting>> entry : find
                        .entrySet()) {
                    if (entry.getKey().eIsProxy()) {
                        for (Setting setting : entry.getValue()) {
                            featureSwitch(scope, entry.getKey(), setting);
                        }
                    }
                }
            }
        }
    }

    private void featureSwitch(IValidationScope scope, EObject object,
            Setting setting) {
        EStructuralFeature feature = setting.getEStructuralFeature();
        UMLPackage p = UMLPackage.eINSTANCE;

        if (feature.equals(p.getGeneralization_General())) {
            createBrokenGeneralizationMarker(scope, object, setting);
        } else if (feature.equals(p.getTypedElement_Type())) {
            createBrokenAttributeTypeMarker(scope, object, setting);
        } else if (feature.equals(p.getProfile())) {
            createBrokenProfileMarker(scope, object, setting);
        } else if (feature.equals(p.getProfileApplication_AppliedProfile())) {
            createBrokenProfileMarker(scope, object, setting);
        }
    }

    private void createBrokenAttributeTypeMarker(IValidationScope scope,
            EObject object, Setting setting) {

        EObject owner = setting.getEObject();

        Map<String, String> additionalInfo = getBrokenRefInfo(
                owner.eResource(), object);
        // special case for parameters: set the problem on operation
        if (owner instanceof Parameter) {
            Parameter parameter = ((Parameter) owner);
            owner = ((Parameter) owner).getOperation();
            if (additionalInfo == null) {
                additionalInfo = new HashMap<String, String>(1);
            }
            additionalInfo.put(OWNER_PARAMETER, EcoreUtil.getURI(parameter)
                    .toString());
        }

        TypedElement te = (TypedElement) setting.getEObject();
        String qualifiedName = te.getQualifiedName();
        qualifiedName = qualifiedName != null ? qualifiedName : ""; //$NON-NLS-1$
        String loc = te.eResource().getURI().path() + " " //$NON-NLS-1$
                + qualifiedName;
        String uri = te.eResource().getURIFragment(owner);

        Collection<String> message = Arrays.asList(
                Messages.BrokenReferencesRule_elementType_label, qualifiedName);

        scope.createIssue(GenericIssueIds.BROKEN_REFERENCE, loc, uri, message,
                additionalInfo);
    }

    private void createBrokenGeneralizationMarker(IValidationScope scope,
            EObject object, Setting setting) {

        Generalization gen = (Generalization) setting.getEObject();
        Classifier specific = gen.getSpecific();
        if (specific != null) {
            String loc = specific.eResource().getURI().path() + " " //$NON-NLS-1$
                    + specific.getQualifiedName();
            String uri = specific.eResource().getURIFragment(specific);

            Collection<String> message = Arrays.asList(
                    Messages.BrokenReferencesRule_generalization_label,
                    specific.getQualifiedName());

            scope.createIssue(GenericIssueIds.BROKEN_REFERENCE, loc, uri,
                    message, getBrokenRefInfo(gen.eResource(), object));
        }
    }

    private void createBrokenProfileMarker(IValidationScope scope,
            EObject object, Setting setting) {

        Profile prof = (Profile) object;

        if (prof != null) {
            String name = prof.getQualifiedName();

            Collection<String> message = Arrays.asList("profile", prof //$NON-NLS-1$
                    .getQualifiedName());

            scope.createIssue(GenericIssueIds.BROKEN_REFERENCE_PROFILE, name,
                    "", message, getBrokenRefInfo( //$NON-NLS-1$
                            setting.getEObject() != null ? setting.getEObject()
                                    .eResource() : null, object));
        }

    }

    /**
     * Get additional info to add for broken references. This will add the path
     * to the resource that is being refernced.
     * 
     * @param eo
     * @return
     */
    private Map<String, String> getBrokenRefInfo(Resource res, EObject eo) {
        if (eo != null && eo.eIsProxy()) {
            URI proxyUri = ((InternalEObject) eo).eProxyURI();

            if (proxyUri != null) {
                if (proxyUri.isPlatform()) {
                    proxyUri = deresolveUri(res, proxyUri);
                }
                Map<String, String> info = new HashMap<String, String>();
                info.put(XpdConsts.VALIDATION_BROKEN_REF_INFO_KEY, proxyUri
                        .trimFragment().path());
                return info;
            }
        }
        return null;
    }

    /**
     * Deresolve the given <code>URI</code>.
     * 
     * @param res
     * @param uri
     * @return
     */
    private URI deresolveUri(Resource res, URI uri) {
        URI deresolved = uri;

        if (uri != null && res instanceof XMLResource) {
            URIHandler handler = (URIHandler) ((XMLResource) res)
                    .getDefaultLoadOptions()
                    .get(XMIResource.OPTION_URI_HANDLER);
            if (handler != null) {
                deresolved = handler.deresolve(uri);
            }
        }

        return deresolved;
    }
}
