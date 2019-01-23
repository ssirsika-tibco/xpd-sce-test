/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.diagram.edit.parts.custom;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.transaction.ResourceSetChangeEvent;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Extension;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.util.UMLUtil;

import com.tibco.xpd.bom.resources.utils.UML2ModelUtil;
import com.tibco.xpd.bom.types.PrimitivesUtil;
import com.tibco.xpd.resources.XpdResourcesPlugin;

/**
 * 
 * Utility methods for BOM editparts
 * 
 * @author rgreen
 * 
 */
public class BOMEditPartUtils {

    public static boolean shouldRebuildFigureToShowStereotype(EObject eo) {

        // We don't want to show a stereotype in the label if it is not a
        // platform resource. For example, the XSDNotationProfile or
        // PrimitiveTypeFacets profile which are not FirstClass profiles but
        // our own internal ones. These are plugin resources as opposed to
        // platform resources which are uml profiles existing in the
        // workspace i.e. user defined.

        // If the only profiles applied are plugin profiles then we won't
        // even bother to rebuild the figure with a label. If there is a
        // combination of resource and platform profiles then we do need to
        // rebuild the figure to display the label. However, we'll leave it
        // to the parser to filter out the which stereotypes should appear
        // in the display string.

        EList<Stereotype> appliedStereotypes = null;

        if (eo instanceof Classifier) {
            Classifier cls = (Classifier) eo;
            appliedStereotypes = cls.getAppliedStereotypes();
        } else if (eo instanceof Package) {
            Package pkg = (Package) eo;
            appliedStereotypes = pkg.getAppliedStereotypes();
        }

        if (appliedStereotypes != null) {
            for (Stereotype stereotype : appliedStereotypes) {
                Profile profile = stereotype.getProfile();

                if (profile != null && profile.eResource() != null) {
                    URI uri = profile.eResource().getURI();
                    if (uri != null && uri.isPlatformResource()) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    /**
     * 
     * Tests whether an event notification relates to the addition or removal of
     * a stereotype to or from the supplied model element.
     * 
     * @param event
     * @param element
     * 
     * @return boolean
     */
    public static boolean shouldRefreshStereotypeLabel(
            ResourceSetChangeEvent event, Element element) {

        Resource resource = element.eResource();

        // Loop through all the notification this event contains and
        // locate the one we are interested in.
        List<?> notifications = event.getNotifications();

        // First look for a SET event type which could signal the removal
        // of a stereotype. The removal of a stereotype consists of a SET
        // event issued by the DynamicEObject(to notify a change to the element
        // which is having the stereotype removed. This is followed by a REMOVE
        // event issued by the Resource notifying that the stereotype
        // has been removed. So we'll loop through firstly to look for a
        // potential SET event.
        for (Object object : notifications) {
            Notification n = (Notification) object;
            // This notification is relevant to this editparts resource
            if (n.getEventType() == Notification.SET) {
                if ((n.getOldValue() == element)
                        && (n.getNewValue() != element)) {
                    // The affected element is our element
                    // affectedElement = (EObject) n.getOldValue();
                    Stereotype stereotype =
                            UMLUtil.getStereotype((EObject) n.getNotifier());
                    if (stereotype != null) {
                        Property attrib =
                                stereotype.getOwnedAttribute(((ENamedElement) n
                                        .getFeature()).getName(), null);
                        if (UML2ModelUtil.isExtensionProperty(attrib)) {
                            return true;
                        }
                    }
                }
            }
        }

        for (Object object : notifications) {
            Notification n = (Notification) object;
            if (n.getNotifier() == resource) {
                // This notification is relevant to this editparts resource
                if ((n.getEventType() == Notification.ADD)
                        || (n.getEventType() == Notification.REMOVE)) {

                    if (((n.getNewValue() == null) && (n.getOldValue() instanceof EObject))
                            || n.getNewValue() instanceof EObject) {
                        // The stereotype info and its associated element
                        // is wrapped up inside a DynamicEObjectImpl class

                        EObject changedValue = null;

                        if (n.getNewValue() != null) {
                            changedValue = (EObject) n.getNewValue();
                        } else if (n.getOldValue() != null) {
                            changedValue = (EObject) n.getOldValue();
                        }

                        if (changedValue != null) {

                            List<EReference> refs =
                                    getExtensionReferences(changedValue);
                            for (EReference ref : refs) {
                                if (changedValue.eGet(ref) == element) {
                                    return true;
                                }
                            }
                        }
                    }
                }
            }

        }

        return false;
    }

    /**
     * Return all EReferences that are the stereotype extension.
     * 
     * @param stereotypeApplication
     * @return
     */
    private static List<EReference> getExtensionReferences(
            EObject stereotypeApplication) {
        List<EReference> result = new ArrayList<EReference>();
        Stereotype stereotype = UMLUtil.getStereotype(stereotypeApplication);
        if (stereotype == null) {
            return result;
        }
        EClass eclass = stereotypeApplication.eClass();

        EList<Property> attribs = stereotype.getAllAttributes();
        Set<String> names = new HashSet<String>();
        for (Property prop : attribs) {
            if (prop.getAssociation() instanceof Extension) {
                names.add(prop.getName());
            }
        }
        for (EReference ref : eclass.getEAllReferences()) {
            if (names.contains(ref.getName())) {
                result.add((EReference) ref);
            }
        }
        return result;
    }

    /**
     * 
     * Tests whether an event notification relates to the addition of a
     * stereotype to or from the supplied model element.
     * 
     * @param event
     * @param element
     * 
     * @return boolean
     */
    public static boolean isStereotypeAddition(ResourceSetChangeEvent event) {
        List<?> notifications = event.getNotifications();
        for (Object object : notifications) {
            Notification n = (Notification) object;
            if ((n.getEventType() == Notification.ADD)) {

                if (isStereotypeNotification(n)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 
     * Tests whether an event notification relates to the removal of a
     * stereotype to or from the supplied model element.
     * 
     * @param event
     * @param element
     * 
     * @return boolean
     */
    public static boolean isStereotypeRemoval(ResourceSetChangeEvent event) {
        // Loop through all the notification this event contains and
        // locate the one we are interested in.
        List<?> notifications = event.getNotifications();
        for (Object object : notifications) {
            Notification n = (Notification) object;
            if ((n.getEventType() == Notification.REMOVE)) {
                if (isStereotypeNotification(n)) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean isStereotypeNotification(Notification n) {
        if (((n.getNewValue() == null) && (n.getOldValue() instanceof EObject))
                || n.getNewValue() instanceof EObject) {
            // The stereotype info and its associated element
            // is wrapped up inside a DynamicEObjectImpl class

            EObject changedValue = null;

            if (n.getNewValue() != null) {
                changedValue = (EObject) n.getNewValue();
            } else if (n.getOldValue() != null) {
                changedValue = (EObject) n.getOldValue();
            }

            if (changedValue != null) {

                List<EReference> refs = getExtensionReferences(changedValue);

                if (!refs.isEmpty()) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isStereotypeBeingSet(Notification n) {
        if ((n.getEventType() == Notification.SET)
                && (n.getNotifier() instanceof EObject)) {
            Stereotype stereotype =
                    UMLUtil.getStereotype((EObject) n.getNotifier());
            if (stereotype != null) {
                Property attrib =
                        stereotype.getOwnedAttribute(((ENamedElement) n
                                .getFeature()).getName(), null);
                if (attrib != null && UML2ModelUtil.isExtensionProperty(attrib)) {
                    return true;
                } else {
                    if (stereotype == PrimitivesUtil
                            .getLabeledElementStereotype(XpdResourcesPlugin
                                    .getDefault().getEditingDomain()
                                    .getResourceSet())) {
                        return true;
                    }
                }
            }
        }

        return false;

    }

    public static Object getFeatureObjectAffected(Notification n) {
        Object element = null;

        if (((n.getNewValue() == null) && (n.getOldValue() instanceof EObject))
                || n.getNewValue() instanceof EObject) {
            // The stereotype info and its associated element
            // is wrapped up inside a DynamicEObjectImpl class

            EObject affectedValue = null;

            if (n.getNewValue() != null) {
                affectedValue = (EObject) n.getNewValue();
            } else if (n.getOldValue() != null) {
                affectedValue = (EObject) n.getOldValue();
            }

            element = affectedValue;

        }

        return element;
    }

}
