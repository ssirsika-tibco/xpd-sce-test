/*
 * Copyright (c) TIBCO Software Inc 2007. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.diagram.part;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.AssociationClass;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.EnumerationLiteral;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.ProfileApplication;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.UMLPackage;

import com.tibco.xpd.bom.modeler.diagram.edit.parts.AssociationClassClassifierAttributesCompartmentEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.AssociationClassClassifierOperationsCompartmentEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.AssociationClassConnectorEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.AssociationClassDanglingNodeEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.AssociationClassEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.AssociationClassOperationEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.AssociationClassPropertyEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.AssociationEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.AssociationEndEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.BadgeEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.CanvasPackageEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.ClassClassAttributesCompartmentEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.ClassClassOperationsCompartmentEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.ClassEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.EnumerationEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.EnumerationEnumLitCompartmentEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.EnumerationLiteralEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.GeneralizationEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.OperationEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.PackageBadgeProfilesCompartmentEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.PackageEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.PackagePackageContentsCompartmentEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.PrimitiveTypeEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.ProfileApplicationEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.PropertyEditPart;
import com.tibco.xpd.bom.modeler.diagram.providers.UMLElementTypes;
import com.tibco.xpd.bom.resources.ui.util.BomUIUtil;

/**
 * @generated
 */
public class UMLDiagramUpdater {

    /**
     * @generated NOT
     */
    public static List getSemanticChildren(View view) {
        switch (UMLVisualIDRegistry.getVisualID(view)) {
        case PackagePackageContentsCompartmentEditPart.VISUAL_ID:
            return getPackagePackageContentsCompartment_5001SemanticChildren(view);
        }
        return getSemanticChildrenGen(view);
    }

    /**
     * @generated
     */
    public static List getSemanticChildrenGen(View view) {
        switch (UMLVisualIDRegistry.getVisualID(view)) {
        case ClassClassAttributesCompartmentEditPart.VISUAL_ID:
            return getClassAttributesCompartment_5002SemanticChildren(view);
        case ClassClassOperationsCompartmentEditPart.VISUAL_ID:
            return getClassOperationsCompartment_5003SemanticChildren(view);
        case EnumerationEnumLitCompartmentEditPart.VISUAL_ID:
            return getEnumerationEnumerationLiteralsCompartment_5004SemanticChildren(view);
        case AssociationClassClassifierAttributesCompartmentEditPart.VISUAL_ID:
            return getAssociationClassAttributesCompartment_5005SemanticChildren(view);
        case AssociationClassClassifierOperationsCompartmentEditPart.VISUAL_ID:
            return getAssociationClassOperationsCompartment_5006SemanticChildren(view);
        case PackageBadgeProfilesCompartmentEditPart.VISUAL_ID:
            return getPackageProfilesCompartment_5007SemanticChildren(view);
        case CanvasPackageEditPart.VISUAL_ID:
            return getPackage_79SemanticChildren(view);
        }
        return Collections.EMPTY_LIST;
    }

    /**
     * @generated NOT
     */
    public static List<?> getClassAttributesCompartment_5002SemanticChildren(
            View view) {
        if (false == view.eContainer() instanceof View) {
            return Collections.EMPTY_LIST;
        }
        View containerView = (View) view.eContainer();
        if (!containerView.isSetElement()) {
            return Collections.EMPTY_LIST;
        }
        Class modelElement = (Class) containerView.getElement();
        List<UMLNodeDescriptor> result = new LinkedList<UMLNodeDescriptor>();
        for (Iterator<Property> it =
                modelElement.getOwnedAttributes().iterator(); it.hasNext();) {
            Property childElement = it.next();
            int visualID =
                    UMLVisualIDRegistry.getNodeVisualID(view, childElement);
            if (visualID == PropertyEditPart.VISUAL_ID) {
                boolean ignore = false;
                if (childElement.getAssociation() != null) {
                    for (Edge edge : getEdges(view)) {
                        if (edge.getElement() == childElement.getAssociation()) {
                            ignore = true;
                            break;
                        }
                    }
                }

                if (!ignore) {
                    result.add(new UMLNodeDescriptor(childElement, visualID));
                }
            }
        }
        return result;
    }

    /**
     * Get all Edges connected to the given Class View. The view can be a child
     * of the class view.
     * 
     * @param view
     * @return Collection of Edges, empty collection if none found.
     */
    private static Collection<Edge> getEdges(View view) {
        List<Edge> edges = new ArrayList<Edge>();
        String classVisualId = String.valueOf(ClassEditPart.VISUAL_ID);
        while (view != null && !classVisualId.equals(view.getType())) {
            view =
                    (View) (view.eContainer() instanceof View ? view
                            .eContainer() : null);
        }

        if (view != null) {
            for (Object obj : view.getSourceEdges()) {
                if (obj instanceof Edge) {
                    edges.add((Edge) obj);
                }
            }
            for (Object obj : view.getTargetEdges()) {
                if (obj instanceof Edge) {
                    edges.add((Edge) obj);
                }
            }
        }

        return edges;
    }

    /**
     * @generated
     */
    public static List getClassOperationsCompartment_5003SemanticChildren(
            View view) {
        if (false == view.eContainer() instanceof View) {
            return Collections.EMPTY_LIST;
        }
        View containerView = (View) view.eContainer();
        if (!containerView.isSetElement()) {
            return Collections.EMPTY_LIST;
        }
        Class modelElement = (Class) containerView.getElement();
        List result = new LinkedList();
        for (Iterator it = modelElement.getOwnedOperations().iterator(); it
                .hasNext();) {
            Operation childElement = (Operation) it.next();
            int visualID =
                    UMLVisualIDRegistry.getNodeVisualID(view, childElement);
            if (visualID == OperationEditPart.VISUAL_ID) {
                result.add(new UMLNodeDescriptor(childElement, visualID));
                continue;
            }
        }
        return result;
    }

    /**
     * @generated NOT
     */
    public static List getPackagePackageContentsCompartment_5001SemanticChildren(
            View view) {
        if (false == view.eContainer() instanceof View) {
            return Collections.EMPTY_LIST;
        }
        View containerView = (View) view.eContainer();
        if (!containerView.isSetElement()) {
            return Collections.EMPTY_LIST;
        }
        Package modelElement = (Package) containerView.getElement();
        List result = new LinkedList();
        for (Iterator it = modelElement.getPackagedElements().iterator(); it
                .hasNext();) {
            PackageableElement childElement = (PackageableElement) it.next();
            int visualID =
                    UMLVisualIDRegistry.getNodeVisualID(view, childElement);
            if (visualID == PackageEditPart.VISUAL_ID) {
                result.add(new UMLNodeDescriptor(childElement, visualID));
                continue;
            }
            if (visualID == ClassEditPart.VISUAL_ID) {
                result.add(new UMLNodeDescriptor(childElement, visualID));
                continue;
            }
            if (visualID == PrimitiveTypeEditPart.VISUAL_ID) {
                result.add(new UMLNodeDescriptor(childElement, visualID));
                continue;
            }
            if (visualID == EnumerationEditPart.VISUAL_ID) {
                result.add(new UMLNodeDescriptor(childElement, visualID));
                continue;
            }

            if (visualID == AssociationClassEditPart.VISUAL_ID) {
                result.add(new UMLNodeDescriptor(childElement, visualID));
                result.add(new UMLNodeDescriptor(childElement,
                        AssociationClassDanglingNodeEditPart.VISUAL_ID));
                continue;
            }
            if (visualID == AssociationClassDanglingNodeEditPart.VISUAL_ID) {
                result.add(new UMLNodeDescriptor(childElement, visualID));
                result.add(new UMLNodeDescriptor(childElement,
                        AssociationClassEditPart.VISUAL_ID));
                continue;
            }

        }
        return result;
    }

    /**
     * @generated
     */
    public static List getEnumerationEnumerationLiteralsCompartment_5004SemanticChildren(
            View view) {
        if (false == view.eContainer() instanceof View) {
            return Collections.EMPTY_LIST;
        }
        View containerView = (View) view.eContainer();
        if (!containerView.isSetElement()) {
            return Collections.EMPTY_LIST;
        }
        Enumeration modelElement = (Enumeration) containerView.getElement();
        List result = new LinkedList();
        for (Iterator it = modelElement.getOwnedLiterals().iterator(); it
                .hasNext();) {
            EnumerationLiteral childElement = (EnumerationLiteral) it.next();
            int visualID =
                    UMLVisualIDRegistry.getNodeVisualID(view, childElement);
            if (visualID == EnumerationLiteralEditPart.VISUAL_ID) {
                result.add(new UMLNodeDescriptor(childElement, visualID));
                continue;
            }
        }
        return result;
    }

    /**
     * @generated
     */
    public static List getAssociationClassAttributesCompartment_5005SemanticChildren(
            View view) {
        if (false == view.eContainer() instanceof View) {
            return Collections.EMPTY_LIST;
        }
        View containerView = (View) view.eContainer();
        if (!containerView.isSetElement()) {
            return Collections.EMPTY_LIST;
        }
        AssociationClass modelElement =
                (AssociationClass) containerView.getElement();
        List result = new LinkedList();
        for (Iterator it = modelElement.getOwnedAttributes().iterator(); it
                .hasNext();) {
            Property childElement = (Property) it.next();
            int visualID =
                    UMLVisualIDRegistry.getNodeVisualID(view, childElement);
            if (visualID == AssociationClassPropertyEditPart.VISUAL_ID) {
                result.add(new UMLNodeDescriptor(childElement, visualID));
                continue;
            }
        }
        return result;
    }

    /**
     * @generated
     */
    public static List getAssociationClassOperationsCompartment_5006SemanticChildren(
            View view) {
        if (false == view.eContainer() instanceof View) {
            return Collections.EMPTY_LIST;
        }
        View containerView = (View) view.eContainer();
        if (!containerView.isSetElement()) {
            return Collections.EMPTY_LIST;
        }
        AssociationClass modelElement =
                (AssociationClass) containerView.getElement();
        List result = new LinkedList();
        for (Iterator it = modelElement.getOwnedOperations().iterator(); it
                .hasNext();) {
            Operation childElement = (Operation) it.next();
            int visualID =
                    UMLVisualIDRegistry.getNodeVisualID(view, childElement);
            if (visualID == AssociationClassOperationEditPart.VISUAL_ID) {
                result.add(new UMLNodeDescriptor(childElement, visualID));
                continue;
            }
        }
        return result;
    }

    /**
     * @generated
     */
    public static List getPackageProfilesCompartment_5007SemanticChildren(
            View view) {
        if (false == view.eContainer() instanceof View) {
            return Collections.EMPTY_LIST;
        }
        View containerView = (View) view.eContainer();
        if (!containerView.isSetElement()) {
            return Collections.EMPTY_LIST;
        }
        Package modelElement = (Package) containerView.getElement();
        List result = new LinkedList();
        for (Iterator it = modelElement.getProfileApplications().iterator(); it
                .hasNext();) {
            ProfileApplication childElement = (ProfileApplication) it.next();
            int visualID =
                    UMLVisualIDRegistry.getNodeVisualID(view, childElement);
            if (visualID == ProfileApplicationEditPart.VISUAL_ID) {
                result.add(new UMLNodeDescriptor(childElement, visualID));
                continue;
            }
        }
        return result;
    }

    /**
     * @param view
     * @return
     */
    public static List<?> getPackage_79SemanticChildren(View view) {

        // If this is not a user diagram then call the generated method
        if (!BomUIUtil.isUserDiagram(view.getDiagram())) {
            return getPackage_79SemanticChildrenGen(view);
        }

        List<UMLNodeDescriptor> descs = new ArrayList<UMLNodeDescriptor>();

        for (Object child : view.getChildren()) {
            if (child instanceof View) {
                View childView = (View) child;
                EObject element = childView.getElement();
                if (element != null) {
                    int visualID = Integer.parseInt(childView.getType());
                    switch (visualID) {
                    case PackageEditPart.VISUAL_ID:
                    case ClassEditPart.VISUAL_ID:
                    case PrimitiveTypeEditPart.VISUAL_ID:
                    case EnumerationEditPart.VISUAL_ID:
                        descs.add(new UMLNodeDescriptor(element, visualID));
                        break;
                    case AssociationClassEditPart.VISUAL_ID:
                        descs.add(new UMLNodeDescriptor(element, visualID));
                        descs
                                .add(new UMLNodeDescriptor(
                                        element,
                                        AssociationClassDanglingNodeEditPart.VISUAL_ID));
                        break;
                    case AssociationClassDanglingNodeEditPart.VISUAL_ID:
                        descs.add(new UMLNodeDescriptor(element, visualID));
                        descs.add(new UMLNodeDescriptor(element,
                                AssociationClassEditPart.VISUAL_ID));
                        break;
                    }
                }
            }
        }

        return descs;
    }

    /**
     * @generated
     */
    public static List getPackage_79SemanticChildrenGen(View view) {
        if (!view.isSetElement()) {
            return Collections.EMPTY_LIST;
        }

        Package modelElement = (Package) view.getElement();
        List<UMLNodeDescriptor> result = new LinkedList<UMLNodeDescriptor>();
        for (Iterator<PackageableElement> it =
                modelElement.getPackagedElements().iterator(); it.hasNext();) {
            PackageableElement childElement = it.next();
            int visualID =
                    UMLVisualIDRegistry.getNodeVisualID(view, childElement);

            switch (visualID) {
            case PackageEditPart.VISUAL_ID:
            case ClassEditPart.VISUAL_ID:
            case PrimitiveTypeEditPart.VISUAL_ID:
            case EnumerationEditPart.VISUAL_ID:
                result.add(new UMLNodeDescriptor(childElement, visualID));
                break;
            case AssociationClassEditPart.VISUAL_ID:
                result.add(new UMLNodeDescriptor(childElement, visualID));
                result.add(new UMLNodeDescriptor(childElement,
                        AssociationClassDanglingNodeEditPart.VISUAL_ID));
                break;
            case AssociationClassDanglingNodeEditPart.VISUAL_ID:
                result.add(new UMLNodeDescriptor(childElement, visualID));
                result.add(new UMLNodeDescriptor(childElement,
                        AssociationClassEditPart.VISUAL_ID));
                break;
            }
        }
        return result;
    }

    /**
     * @param view
     * @return
     */
    public static List getPackage_1001SemanticChildren(View view) {
        return getPackage_79SemanticChildren(view);
    }

    /**
     * @generated
     */
    public static List getContainedLinks(View view) {
        switch (UMLVisualIDRegistry.getVisualID(view)) {
        case CanvasPackageEditPart.VISUAL_ID:
            return getPackage_79ContainedLinks(view);
        case PackageEditPart.VISUAL_ID:
            return getPackage_1001ContainedLinks(view);
        case ClassEditPart.VISUAL_ID:
            return getClass_1002ContainedLinks(view);
        case PrimitiveTypeEditPart.VISUAL_ID:
            return getPrimitiveType_1003ContainedLinks(view);
        case EnumerationEditPart.VISUAL_ID:
            return getEnumeration_1004ContainedLinks(view);
        case AssociationClassEditPart.VISUAL_ID:
            return getAssociationClass_1005ContainedLinks(view);
        case AssociationClassDanglingNodeEditPart.VISUAL_ID:
            return getAssociationClass_1006ContainedLinks(view);
        case BadgeEditPart.VISUAL_ID:
            return getPackage_1007ContainedLinks(view);
        case PropertyEditPart.VISUAL_ID:
            return getProperty_2001ContainedLinks(view);
        case OperationEditPart.VISUAL_ID:
            return getOperation_2002ContainedLinks(view);
        case EnumerationLiteralEditPart.VISUAL_ID:
            return getEnumerationLiteral_2003ContainedLinks(view);
        case AssociationClassPropertyEditPart.VISUAL_ID:
            return getProperty_2004ContainedLinks(view);
        case AssociationClassOperationEditPart.VISUAL_ID:
            return getOperation_2005ContainedLinks(view);
        case ProfileApplicationEditPart.VISUAL_ID:
            return getProfileApplication_2006ContainedLinks(view);
        case GeneralizationEditPart.VISUAL_ID:
            return getGeneralization_3001ContainedLinks(view);
        case AssociationEditPart.VISUAL_ID:
            return getAssociation_3002ContainedLinks(view);
        case AssociationEndEditPart.VISUAL_ID:
            return getProperty_3003ContainedLinks(view);
        case AssociationClassConnectorEditPart.VISUAL_ID:
            return getAssociationClass_3004ContainedLinks(view);
        }
        return Collections.EMPTY_LIST;
    }

    /**
     * @generated
     */
    public static List getIncomingLinks(View view) {
        switch (UMLVisualIDRegistry.getVisualID(view)) {
        case PackageEditPart.VISUAL_ID:
            return getPackage_1001IncomingLinks(view);
        case ClassEditPart.VISUAL_ID:
            return getClass_1002IncomingLinks(view);
        case PrimitiveTypeEditPart.VISUAL_ID:
            return getPrimitiveType_1003IncomingLinks(view);
        case EnumerationEditPart.VISUAL_ID:
            return getEnumeration_1004IncomingLinks(view);
        case AssociationClassEditPart.VISUAL_ID:
            return getAssociationClass_1005IncomingLinks(view);
        case AssociationClassDanglingNodeEditPart.VISUAL_ID:
            return getAssociationClass_1006IncomingLinks(view);
        case BadgeEditPart.VISUAL_ID:
            return getPackage_1007IncomingLinks(view);
        case PropertyEditPart.VISUAL_ID:
            return getProperty_2001IncomingLinks(view);
        case OperationEditPart.VISUAL_ID:
            return getOperation_2002IncomingLinks(view);
        case EnumerationLiteralEditPart.VISUAL_ID:
            return getEnumerationLiteral_2003IncomingLinks(view);
        case AssociationClassPropertyEditPart.VISUAL_ID:
            return getProperty_2004IncomingLinks(view);
        case AssociationClassOperationEditPart.VISUAL_ID:
            return getOperation_2005IncomingLinks(view);
        case ProfileApplicationEditPart.VISUAL_ID:
            return getProfileApplication_2006IncomingLinks(view);
        case GeneralizationEditPart.VISUAL_ID:
            return getGeneralization_3001IncomingLinks(view);
        case AssociationEditPart.VISUAL_ID:
            return getAssociation_3002IncomingLinks(view);
        case AssociationEndEditPart.VISUAL_ID:
            return getProperty_3003IncomingLinks(view);
        case AssociationClassConnectorEditPart.VISUAL_ID:
            return getAssociationClass_3004IncomingLinks(view);
        }
        return Collections.EMPTY_LIST;
    }

    /**
     * @generated
     */
    public static List getOutgoingLinks(View view) {
        switch (UMLVisualIDRegistry.getVisualID(view)) {
        case PackageEditPart.VISUAL_ID:
            return getPackage_1001OutgoingLinks(view);
        case ClassEditPart.VISUAL_ID:
            return getClass_1002OutgoingLinks(view);
        case PrimitiveTypeEditPart.VISUAL_ID:
            return getPrimitiveType_1003OutgoingLinks(view);
        case EnumerationEditPart.VISUAL_ID:
            return getEnumeration_1004OutgoingLinks(view);
        case AssociationClassEditPart.VISUAL_ID:
            return getAssociationClass_1005OutgoingLinks(view);
        case AssociationClassDanglingNodeEditPart.VISUAL_ID:
            return getAssociationClass_1006OutgoingLinks(view);
        case BadgeEditPart.VISUAL_ID:
            return getPackage_1007OutgoingLinks(view);
        case PropertyEditPart.VISUAL_ID:
            return getProperty_2001OutgoingLinks(view);
        case OperationEditPart.VISUAL_ID:
            return getOperation_2002OutgoingLinks(view);
        case EnumerationLiteralEditPart.VISUAL_ID:
            return getEnumerationLiteral_2003OutgoingLinks(view);
        case AssociationClassPropertyEditPart.VISUAL_ID:
            return getProperty_2004OutgoingLinks(view);
        case AssociationClassOperationEditPart.VISUAL_ID:
            return getOperation_2005OutgoingLinks(view);
        case ProfileApplicationEditPart.VISUAL_ID:
            return getProfileApplication_2006OutgoingLinks(view);
        case GeneralizationEditPart.VISUAL_ID:
            return getGeneralization_3001OutgoingLinks(view);
        case AssociationEditPart.VISUAL_ID:
            return getAssociation_3002OutgoingLinks(view);
        case AssociationEndEditPart.VISUAL_ID:
            return getProperty_3003OutgoingLinks(view);
        case AssociationClassConnectorEditPart.VISUAL_ID:
            return getAssociationClass_3004OutgoingLinks(view);
        }
        return Collections.EMPTY_LIST;
    }

    /**
     * @generated NOT
     */
    @SuppressWarnings("unchecked")
    public static List getPackage_79ContainedLinks(View view) {
        Package modelElement = (Package) view.getElement();
        List result = new LinkedList();
        if (BomUIUtil.isUserDiagram(view.getDiagram())) {
            result.addAll(getUserDiagramFacetLinks_Association_3002(view));
        } else {
            result
                    .addAll(getContainedTypeModelFacetLinks_Association_3002(modelElement));
        }
        result
                .addAll(getContainedTypeModelFacetLinks_AssociationClass_3004(modelElement));
        return result;
    }

    /**
     * @generated NOT
     */
    public static List getPackage_1001ContainedLinks(View view) {
        return getPackage_79ContainedLinks(view);
    }

    /**
     * @generated
     */
    public static List getClass_1002ContainedLinks(View view) {
        Class modelElement = (Class) view.getElement();
        List result = new LinkedList();
        result
                .addAll(getContainedTypeModelFacetLinks_Generalization_3001(modelElement));
        return result;
    }

    /**
     * @generated
     */
    public static List getPrimitiveType_1003ContainedLinks(View view) {
        PrimitiveType modelElement = (PrimitiveType) view.getElement();
        List result = new LinkedList();
        result
                .addAll(getContainedTypeModelFacetLinks_Generalization_3001(modelElement));
        return result;
    }

    /**
     * @generated
     */
    public static List getEnumeration_1004ContainedLinks(View view) {
        Enumeration modelElement = (Enumeration) view.getElement();
        List result = new LinkedList();
        result
                .addAll(getContainedTypeModelFacetLinks_Generalization_3001(modelElement));
        return result;
    }

    /**
     * @generated
     */
    public static List getAssociationClass_1005ContainedLinks(View view) {
        AssociationClass modelElement = (AssociationClass) view.getElement();
        List result = new LinkedList();
        result
                .addAll(getContainedTypeModelFacetLinks_Generalization_3001(modelElement));
        result
                .addAll(getContainedTypeModelFacetLinks_Property_3003(modelElement));
        return result;
    }

    /**
     * @generated
     */
    public static List getAssociationClass_1006ContainedLinks(View view) {
        AssociationClass modelElement = (AssociationClass) view.getElement();
        List result = new LinkedList();
        result
                .addAll(getContainedTypeModelFacetLinks_Generalization_3001(modelElement));
        result
                .addAll(getContainedTypeModelFacetLinks_Property_3003(modelElement));
        return result;
    }

    /**
     * @generated NOT
     */
    public static List getPackage_1007ContainedLinks(View view) {
        return getPackage_79ContainedLinks(view);
    }

    /**
     * @generated
     */
    public static List getProperty_2001ContainedLinks(View view) {
        return Collections.EMPTY_LIST;
    }

    /**
     * @generated
     */
    public static List getOperation_2002ContainedLinks(View view) {
        return Collections.EMPTY_LIST;
    }

    /**
     * @generated
     */
    public static List getEnumerationLiteral_2003ContainedLinks(View view) {
        return Collections.EMPTY_LIST;
    }

    /**
     * @generated
     */
    public static List getProperty_2004ContainedLinks(View view) {
        return Collections.EMPTY_LIST;
    }

    /**
     * @generated
     */
    public static List getOperation_2005ContainedLinks(View view) {
        return Collections.EMPTY_LIST;
    }

    /**
     * @generated
     */
    public static List getProfileApplication_2006ContainedLinks(View view) {
        return Collections.EMPTY_LIST;
    }

    /**
     * @generated
     */
    public static List getGeneralization_3001ContainedLinks(View view) {
        return Collections.EMPTY_LIST;
    }

    /**
     * @generated
     */
    public static List getAssociation_3002ContainedLinks(View view) {
        Association modelElement = (Association) view.getElement();
        List result = new LinkedList();
        result
                .addAll(getContainedTypeModelFacetLinks_Generalization_3001(modelElement));
        result
                .addAll(getContainedTypeModelFacetLinks_Property_3003(modelElement));
        return result;
    }

    /**
     * @generated
     */
    public static List getProperty_3003ContainedLinks(View view) {
        return Collections.EMPTY_LIST;
    }

    /**
     * @generated
     */
    public static List getAssociationClass_3004ContainedLinks(View view) {
        AssociationClass modelElement = (AssociationClass) view.getElement();
        List result = new LinkedList();
        result
                .addAll(getContainedTypeModelFacetLinks_Generalization_3001(modelElement));
        result
                .addAll(getContainedTypeModelFacetLinks_Property_3003(modelElement));
        return result;
    }

    /**
     * @generated
     */
    public static List getPackage_1001IncomingLinks(View view) {
        return Collections.EMPTY_LIST;
    }

    /**
     * @generated
     */
    public static List getClass_1002IncomingLinks(View view) {
        Class modelElement = (Class) view.getElement();
        Map crossReferences =
                EcoreUtil.CrossReferencer.find(view.eResource()
                        .getResourceSet().getResources());
        List result = new LinkedList();
        result
                .addAll(getIncomingTypeModelFacetLinks_Generalization_3001(modelElement,
                        crossReferences));
        result
                .addAll(getIncomingTypeModelFacetLinks_Association_3002(modelElement,
                        crossReferences));
        result
                .addAll(getIncomingTypeModelFacetLinks_Property_3003(modelElement,
                        crossReferences));
        return result;
    }

    /**
     * @generated
     */
    public static List getPrimitiveType_1003IncomingLinks(View view) {
        PrimitiveType modelElement = (PrimitiveType) view.getElement();
        Map crossReferences =
                EcoreUtil.CrossReferencer.find(view.eResource()
                        .getResourceSet().getResources());
        List result = new LinkedList();
        result
                .addAll(getIncomingTypeModelFacetLinks_Generalization_3001(modelElement,
                        crossReferences));
        result
                .addAll(getIncomingTypeModelFacetLinks_Association_3002(modelElement,
                        crossReferences));
        result
                .addAll(getIncomingTypeModelFacetLinks_Property_3003(modelElement,
                        crossReferences));
        return result;
    }

    /**
     * @generated
     */
    public static List getEnumeration_1004IncomingLinks(View view) {
        Enumeration modelElement = (Enumeration) view.getElement();
        Map crossReferences =
                EcoreUtil.CrossReferencer.find(view.eResource()
                        .getResourceSet().getResources());
        List result = new LinkedList();
        result
                .addAll(getIncomingTypeModelFacetLinks_Generalization_3001(modelElement,
                        crossReferences));
        result
                .addAll(getIncomingTypeModelFacetLinks_Association_3002(modelElement,
                        crossReferences));
        result
                .addAll(getIncomingTypeModelFacetLinks_Property_3003(modelElement,
                        crossReferences));
        return result;
    }

    /**
     * @generated
     */
    public static List getAssociationClass_1005IncomingLinks(View view) {
        AssociationClass modelElement = (AssociationClass) view.getElement();
        Map crossReferences =
                EcoreUtil.CrossReferencer.find(view.eResource()
                        .getResourceSet().getResources());
        List result = new LinkedList();
        result
                .addAll(getIncomingTypeModelFacetLinks_Generalization_3001(modelElement,
                        crossReferences));
        result
                .addAll(getIncomingTypeModelFacetLinks_Association_3002(modelElement,
                        crossReferences));
        result
                .addAll(getIncomingTypeModelFacetLinks_Property_3003(modelElement,
                        crossReferences));
        return result;
    }

    /**
     * @generated
     */
    public static List getAssociationClass_1006IncomingLinks(View view) {
        AssociationClass modelElement = (AssociationClass) view.getElement();
        Map crossReferences =
                EcoreUtil.CrossReferencer.find(view.eResource()
                        .getResourceSet().getResources());
        List result = new LinkedList();
        result
                .addAll(getIncomingTypeModelFacetLinks_Generalization_3001(modelElement,
                        crossReferences));
        result
                .addAll(getIncomingTypeModelFacetLinks_Association_3002(modelElement,
                        crossReferences));
        result
                .addAll(getIncomingTypeModelFacetLinks_Property_3003(modelElement,
                        crossReferences));
        return result;
    }

    /**
     * @generated
     */
    public static List getPackage_1007IncomingLinks(View view) {
        return Collections.EMPTY_LIST;
    }

    /**
     * @generated
     */
    public static List getProperty_2001IncomingLinks(View view) {
        return Collections.EMPTY_LIST;
    }

    /**
     * @generated
     */
    public static List getOperation_2002IncomingLinks(View view) {
        return Collections.EMPTY_LIST;
    }

    /**
     * @generated
     */
    public static List getEnumerationLiteral_2003IncomingLinks(View view) {
        return Collections.EMPTY_LIST;
    }

    /**
     * @generated
     */
    public static List getProperty_2004IncomingLinks(View view) {
        return Collections.EMPTY_LIST;
    }

    /**
     * @generated
     */
    public static List getOperation_2005IncomingLinks(View view) {
        return Collections.EMPTY_LIST;
    }

    /**
     * @generated
     */
    public static List getProfileApplication_2006IncomingLinks(View view) {
        return Collections.EMPTY_LIST;
    }

    /**
     * @generated
     */
    public static List getGeneralization_3001IncomingLinks(View view) {
        return Collections.EMPTY_LIST;
    }

    /**
     * @generated
     */
    public static List getAssociation_3002IncomingLinks(View view) {
        Association modelElement = (Association) view.getElement();
        Map crossReferences =
                EcoreUtil.CrossReferencer.find(view.eResource()
                        .getResourceSet().getResources());
        List result = new LinkedList();
        result
                .addAll(getIncomingTypeModelFacetLinks_Generalization_3001(modelElement,
                        crossReferences));
        result
                .addAll(getIncomingTypeModelFacetLinks_Association_3002(modelElement,
                        crossReferences));
        result
                .addAll(getIncomingTypeModelFacetLinks_Property_3003(modelElement,
                        crossReferences));
        return result;
    }

    /**
     * @generated
     */
    public static List getProperty_3003IncomingLinks(View view) {
        return Collections.EMPTY_LIST;
    }

    /**
     * @generated
     */
    public static List getAssociationClass_3004IncomingLinks(View view) {
        AssociationClass modelElement = (AssociationClass) view.getElement();
        Map crossReferences =
                EcoreUtil.CrossReferencer.find(view.eResource()
                        .getResourceSet().getResources());
        List result = new LinkedList();
        result
                .addAll(getIncomingTypeModelFacetLinks_Generalization_3001(modelElement,
                        crossReferences));
        result
                .addAll(getIncomingTypeModelFacetLinks_Association_3002(modelElement,
                        crossReferences));
        result
                .addAll(getIncomingTypeModelFacetLinks_Property_3003(modelElement,
                        crossReferences));
        return result;
    }

    /**
     * @generated
     */
    public static List getPackage_1001OutgoingLinks(View view) {
        return Collections.EMPTY_LIST;
    }

    /**
     * @generated
     */
    public static List getClass_1002OutgoingLinks(View view) {
        Class modelElement = (Class) view.getElement();
        List result = new LinkedList();
        result
                .addAll(getContainedTypeModelFacetLinks_Generalization_3001(modelElement));
        result
                .addAll(getOutgoingTypeModelFacetLinks_Association_3002(modelElement));
        return result;
    }

    /**
     * @generated
     */
    public static List getPrimitiveType_1003OutgoingLinks(View view) {
        PrimitiveType modelElement = (PrimitiveType) view.getElement();
        List result = new LinkedList();
        result
                .addAll(getContainedTypeModelFacetLinks_Generalization_3001(modelElement));
        result
                .addAll(getOutgoingTypeModelFacetLinks_Association_3002(modelElement));
        return result;
    }

    /**
     * @generated
     */
    public static List getEnumeration_1004OutgoingLinks(View view) {
        Enumeration modelElement = (Enumeration) view.getElement();
        List result = new LinkedList();
        result
                .addAll(getContainedTypeModelFacetLinks_Generalization_3001(modelElement));
        result
                .addAll(getOutgoingTypeModelFacetLinks_Association_3002(modelElement));
        return result;
    }

    /**
     * @generated
     */
    public static List getAssociationClass_1005OutgoingLinks(View view) {
        AssociationClass modelElement = (AssociationClass) view.getElement();
        List result = new LinkedList();
        result
                .addAll(getContainedTypeModelFacetLinks_Generalization_3001(modelElement));
        result
                .addAll(getOutgoingTypeModelFacetLinks_Association_3002(modelElement));
        result
                .addAll(getContainedTypeModelFacetLinks_Property_3003(modelElement));
        return result;
    }

    /**
     * @generated
     */
    public static List getAssociationClass_1006OutgoingLinks(View view) {
        AssociationClass modelElement = (AssociationClass) view.getElement();
        List result = new LinkedList();
        result
                .addAll(getContainedTypeModelFacetLinks_Generalization_3001(modelElement));
        result
                .addAll(getOutgoingTypeModelFacetLinks_Association_3002(modelElement));
        result
                .addAll(getContainedTypeModelFacetLinks_Property_3003(modelElement));
        return result;
    }

    /**
     * @generated
     */
    public static List getPackage_1007OutgoingLinks(View view) {
        return Collections.EMPTY_LIST;
    }

    /**
     * @generated
     */
    public static List getProperty_2001OutgoingLinks(View view) {
        return Collections.EMPTY_LIST;
    }

    /**
     * @generated
     */
    public static List getOperation_2002OutgoingLinks(View view) {
        return Collections.EMPTY_LIST;
    }

    /**
     * @generated
     */
    public static List getEnumerationLiteral_2003OutgoingLinks(View view) {
        return Collections.EMPTY_LIST;
    }

    /**
     * @generated
     */
    public static List getProperty_2004OutgoingLinks(View view) {
        return Collections.EMPTY_LIST;
    }

    /**
     * @generated
     */
    public static List getOperation_2005OutgoingLinks(View view) {
        return Collections.EMPTY_LIST;
    }

    /**
     * @generated
     */
    public static List getProfileApplication_2006OutgoingLinks(View view) {
        return Collections.EMPTY_LIST;
    }

    /**
     * @generated
     */
    public static List getGeneralization_3001OutgoingLinks(View view) {
        return Collections.EMPTY_LIST;
    }

    /**
     * @generated
     */
    public static List getAssociation_3002OutgoingLinks(View view) {
        Association modelElement = (Association) view.getElement();
        List result = new LinkedList();
        result
                .addAll(getContainedTypeModelFacetLinks_Generalization_3001(modelElement));
        result
                .addAll(getOutgoingTypeModelFacetLinks_Association_3002(modelElement));
        result
                .addAll(getContainedTypeModelFacetLinks_Property_3003(modelElement));
        return result;
    }

    /**
     * @generated
     */
    public static List getProperty_3003OutgoingLinks(View view) {
        return Collections.EMPTY_LIST;
    }

    /**
     * @generated
     */
    public static List getAssociationClass_3004OutgoingLinks(View view) {
        AssociationClass modelElement = (AssociationClass) view.getElement();
        List result = new LinkedList();
        result
                .addAll(getContainedTypeModelFacetLinks_Generalization_3001(modelElement));
        result
                .addAll(getOutgoingTypeModelFacetLinks_Association_3002(modelElement));
        result
                .addAll(getContainedTypeModelFacetLinks_Property_3003(modelElement));
        return result;
    }

    /**
     * @generated
     */
    private static Collection getContainedTypeModelFacetLinks_Generalization_3001(
            Classifier container) {
        Collection result = new LinkedList();
        for (Iterator links = container.getGeneralizations().iterator(); links
                .hasNext();) {
            EObject linkObject = (EObject) links.next();
            if (false == linkObject instanceof Generalization) {
                continue;
            }
            Generalization link = (Generalization) linkObject;
            if (GeneralizationEditPart.VISUAL_ID != UMLVisualIDRegistry
                    .getLinkWithClassVisualID(link)) {
                continue;
            }
            Classifier dst = link.getGeneral();
            result.add(new UMLLinkDescriptor(container, dst, link,
                    UMLElementTypes.Generalization_3001,
                    GeneralizationEditPart.VISUAL_ID));
        }
        return result;
    }

    /**
     * Get the association links for the user diagram.
     * 
     * @param view
     * @return
     */
    private static List<UMLLinkDescriptor> getUserDiagramFacetLinks_Association_3002(
            View view) {
        List<UMLLinkDescriptor> descs = new ArrayList<UMLLinkDescriptor>();

        if (view != null) {
            Set<Class> classes = new HashSet<Class>();
            // Get all classes that have a view in this user diagram
            TreeIterator<Object> iter =
                    EcoreUtil.getAllProperContents(view, false);
            while (iter.hasNext()) {
                Object next = iter.next();
                if (next instanceof Node) {
                    EObject element = ((Node) next).getElement();
                    if (element instanceof Class) {
                        classes.add((Class) element);
                    }
                }
            }

            if (!classes.isEmpty()) {
                Set<Association> assocs = new HashSet<Association>();
                for (Class cl : classes) {
                    for (Association assoc : cl.getAssociations()) {
                        // If association has both ends present in this diagram
                        // then add to list
                        EList<Type> endTypes = assoc.getEndTypes();
                        if (endTypes.size() == 1) {
                            if (!assocs.contains(assoc)) {
                                descs.add(new UMLLinkDescriptor(
                                        endTypes.get(0), endTypes.get(0),
                                        assoc,
                                        UMLElementTypes.Association_3002,
                                        AssociationEditPart.VISUAL_ID));
                                assocs.add(assoc);
                            }
                        } else {
                            for (Type type : endTypes) {
                                if (type != cl && classes.contains(type)) {
                                    if (!assocs.contains(assoc)) {
                                        descs
                                                .add(new UMLLinkDescriptor(
                                                        endTypes.get(0),
                                                        endTypes.get(1),
                                                        assoc,
                                                        UMLElementTypes.Association_3002,
                                                        AssociationEditPart.VISUAL_ID));
                                        assocs.add(assoc);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return descs;
    }

    /**
     * @generated NOT
     */
    private static Collection getContainedTypeModelFacetLinks_Association_3002(
            Package container) {
        Collection result = new LinkedList();
        for (Iterator links = container.getPackagedElements().iterator(); links
                .hasNext();) {
            Object linkObject = links.next();
            if (false == linkObject instanceof Association) {
                continue;
            }
            Association link = (Association) linkObject;
            if (AssociationEditPart.VISUAL_ID != UMLVisualIDRegistry
                    .getLinkWithClassVisualID(link)) {
                continue;
            }

            // Need to get the list of memberEnds not endTypes.
            // If you have a self-referencing association then the
            // list of endTypes will only be 1. MemberEnds will
            // always be 2.
            List<Property> targets = link.getMemberEnds();
            Object theTarget =
                    targets.size() == 2 ? targets.get(1).getType() : null;
            if (false == theTarget instanceof Type) {
                continue;
            }
            Type dst = (Type) theTarget;

            // List sources = link.getEndTypes();
            List<Property> sources = link.getMemberEnds();
            // Object theSource = sources.size() == 2 ? sources.get(0) : null;
            Object theSource =
                    sources.size() == 2 ? sources.get(0).getType() : null;
            if (false == theSource instanceof Type) {
                continue;
            }
            Type src = (Type) theSource;
            result.add(new UMLLinkDescriptor(src, dst, link,
                    UMLElementTypes.Association_3002,
                    AssociationEditPart.VISUAL_ID));
        }
        return result;
    }

    /**
     * @generated NOT
     * 
     *            Added custom code for AssociationClass
     */
    private static Collection getContainedTypeModelFacetLinks_Property_3003(
            Association container) {
        Collection result = new LinkedList();
        for (Iterator links = container.getOwnedEnds().iterator(); links
                .hasNext();) {
            EObject linkObject = (EObject) links.next();
            if (false == linkObject instanceof Property) {
                continue;
            }
            Property link = (Property) linkObject;

            // Make sure that the Property's association is an AssociationClass
            // and not an Association. Otherwise a link will be drawn for a
            // Property
            // between the Class and the centre of an Association link!
            if (link.getAssociation() instanceof AssociationClass) {
                if (AssociationEndEditPart.VISUAL_ID != UMLVisualIDRegistry
                        .getLinkWithClassVisualID(link)) {
                    continue;
                }

                Association association = link.getAssociation();

                Type dst = link.getType();
                result.add(new UMLLinkDescriptor(container, dst, link,
                        UMLElementTypes.Property_3003,
                        AssociationEndEditPart.VISUAL_ID));
            }

        }
        return result;
    }

    /**
     * @generated
     */
    // private static Collection
    // getContainedTypeModelFacetLinks_AssociationClass_3004(
    // Package container) {
    // Collection result = new LinkedList();
    // for (Iterator links = container.getPackagedElements().iterator(); links
    // .hasNext();) {
    // EObject linkObject = (EObject) links.next();
    // if (false == linkObject instanceof AssociationClass) {
    // continue;
    // }
    // AssociationClass link = (AssociationClass) linkObject;
    // if (AssociationClassConnectorEditPart.VISUAL_ID != UMLVisualIDRegistry
    // .getLinkWithClassVisualID(link)) {
    // continue;
    // }
    // List targets = link.getCollaborationUses();
    // Object theTarget = targets.size() == 2 ? targets.get(1) : null;
    // if (false == theTarget instanceof CollaborationUse) {
    // continue;
    // }
    // CollaborationUse dst = (CollaborationUse) theTarget;
    // List sources = link.getCollaborationUses();
    // Object theSource = sources.size() == 2 ? sources.get(0) : null;
    // if (false == theSource instanceof CollaborationUse) {
    // continue;
    // }
    // CollaborationUse src = (CollaborationUse) theSource;
    // result.add(new UMLLinkDescriptor(src, dst, link,
    // UMLElementTypes.AssociationClass_3004,
    // AssociationClassConnectorEditPart.VISUAL_ID));
    // }
    // return result;
    // }
    /**
     * @generated
     */
    private static Collection getIncomingTypeModelFacetLinks_Generalization_3001(
            Classifier target, Map crossReferences) {
        Collection result = new LinkedList();
        Collection settings = (Collection) crossReferences.get(target);
        for (Iterator it = settings.iterator(); it.hasNext();) {
            EStructuralFeature.Setting setting =
                    (EStructuralFeature.Setting) it.next();
            if (setting.getEStructuralFeature() != UMLPackage.eINSTANCE
                    .getGeneralization_General()
                    || false == setting.getEObject() instanceof Generalization) {
                continue;
            }
            Generalization link = (Generalization) setting.getEObject();
            if (GeneralizationEditPart.VISUAL_ID != UMLVisualIDRegistry
                    .getLinkWithClassVisualID(link)) {
                continue;
            }
            if (false == link.eContainer() instanceof Classifier) {
                continue;
            }
            Classifier container = (Classifier) link.eContainer();
            result.add(new UMLLinkDescriptor(container, target, link,
                    UMLElementTypes.Generalization_3001,
                    GeneralizationEditPart.VISUAL_ID));

        }
        return result;
    }

    /**
     * @generated
     */
    private static Collection getIncomingTypeModelFacetLinks_Association_3002(
            Type target, Map crossReferences) {
        Collection result = new LinkedList();
        Collection settings = (Collection) crossReferences.get(target);
        for (Iterator it = settings.iterator(); it.hasNext();) {
            EStructuralFeature.Setting setting =
                    (EStructuralFeature.Setting) it.next();
            if (setting.getEStructuralFeature() != UMLPackage.eINSTANCE
                    .getAssociation_EndType()
                    || false == setting.getEObject() instanceof Association) {
                continue;
            }
            Association link = (Association) setting.getEObject();
            if (AssociationEditPart.VISUAL_ID != UMLVisualIDRegistry
                    .getLinkWithClassVisualID(link)) {
                continue;
            }
            List sources = link.getEndTypes();
            Object theSource = sources.size() == 2 ? sources.get(0) : null;
            if (false == theSource instanceof Type) {
                continue;
            }
            Type src = (Type) theSource;
            result.add(new UMLLinkDescriptor(src, target, link,
                    UMLElementTypes.Association_3002,
                    AssociationEditPart.VISUAL_ID));
        }
        return result;
    }

    /**
     * @generated
     */
    private static Collection getIncomingTypeModelFacetLinks_Property_3003(
            Type target, Map crossReferences) {
        Collection result = new LinkedList();
        Collection settings = (Collection) crossReferences.get(target);
        for (Iterator it = settings.iterator(); it.hasNext();) {
            EStructuralFeature.Setting setting =
                    (EStructuralFeature.Setting) it.next();
            if (setting.getEStructuralFeature() != UMLPackage.eINSTANCE
                    .getTypedElement_Type()
                    || false == setting.getEObject() instanceof Property) {
                continue;
            }
            Property link = (Property) setting.getEObject();
            if (AssociationEndEditPart.VISUAL_ID != UMLVisualIDRegistry
                    .getLinkWithClassVisualID(link)) {
                continue;
            }
            if (false == link.eContainer() instanceof Association) {
                continue;
            }
            Association container = (Association) link.eContainer();
            result.add(new UMLLinkDescriptor(container, target, link,
                    UMLElementTypes.Property_3003,
                    AssociationEndEditPart.VISUAL_ID));

        }
        return result;
    }

    /**
     * @generated
     */
    private static Collection getOutgoingTypeModelFacetLinks_Association_3002(
            Type source) {
        Package container = null;
        // Find container element for the link.
        // Climb up by containment hierarchy starting from the source
        // and return the first element that is instance of the container class.
        for (EObject element = source; element != null && container == null; element =
                element.eContainer()) {
            if (element instanceof Package) {
                container = (Package) element;
            }
        }
        if (container == null) {
            return Collections.EMPTY_LIST;
        }
        Collection result = new LinkedList();
        for (Iterator links = container.getPackagedElements().iterator(); links
                .hasNext();) {
            EObject linkObject = (EObject) links.next();
            if (false == linkObject instanceof Association) {
                continue;
            }
            Association link = (Association) linkObject;
            if (AssociationEditPart.VISUAL_ID != UMLVisualIDRegistry
                    .getLinkWithClassVisualID(link)) {
                continue;
            }
            List targets = link.getEndTypes();
            Object theTarget = targets.size() == 2 ? targets.get(1) : null;
            if (false == theTarget instanceof Type) {
                continue;
            }
            Type dst = (Type) theTarget;
            List sources = link.getEndTypes();
            Object theSource = sources.size() == 2 ? sources.get(0) : null;
            if (false == theSource instanceof Type) {
                continue;
            }
            Type src = (Type) theSource;
            if (src != source) {
                continue;
            }
            result.add(new UMLLinkDescriptor(src, dst, link,
                    UMLElementTypes.Association_3002,
                    AssociationEditPart.VISUAL_ID));
        }
        return result;
    }

    /**
     * This link is connector between association class' rhomb and rectangle
     * parts CollaborationUse's were selected in the gmfmap instead of not
     * allowed "null" features, because they are completely unrelated and can
     * not interfere with link-related editpolicies.
     * 
     * In the ideal world we would prefere to specify either custom relationship
     * between AssociationClass and source/target for this link or don't specify
     * these features at all.
     * 
     * In this method we are going to create link descriptor with identical
     * semantic elements (association class itself for source, target and link
     * itself). The selection of the node's for source/target is made in the
     * custom code of the PackageCanonicalEditPolicy#getSourceEditPart(...)/
     * PackageCanonicalEditPolicy#getTargetEditPart(...) methods.
     * 
     * @generated NOT
     */
    private static Collection getContainedTypeModelFacetLinks_AssociationClass_3004(
            Package container) {
        Collection result = new LinkedList();
        for (Iterator links = container.getPackagedElements().iterator(); links
                .hasNext();) {
            Object linkObject = links.next();
            if (false == linkObject instanceof AssociationClass) {
                continue;
            }
            AssociationClass link = (AssociationClass) linkObject;
            if (AssociationClassConnectorEditPart.VISUAL_ID != UMLVisualIDRegistry
                    .getLinkWithClassVisualID(link)) {
                continue;
            }

            // the same link-element, source and target
            AssociationClass src = link;
            AssociationClass dst = link;
            result.add(new UMLLinkDescriptor(src, dst, link,
                    UMLElementTypes.AssociationClass_3004,
                    AssociationClassConnectorEditPart.VISUAL_ID));
        }
        return result;
    }

}
