package com.tibco.xpd.om.modeler.diagram.view.factories;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.diagram.core.util.ViewUtil;
import org.eclipse.gmf.runtime.diagram.ui.view.factories.ConnectionViewFactory;
import org.eclipse.gmf.runtime.draw2d.ui.figures.FigureUtilities;
import org.eclipse.gmf.runtime.emf.core.util.EObjectAdapter;
import org.eclipse.gmf.runtime.notation.ConnectorStyle;
import org.eclipse.gmf.runtime.notation.LineStyle;
import org.eclipse.gmf.runtime.notation.LineType;
import org.eclipse.gmf.runtime.notation.LineTypeStyle;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.NotationFactory;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.Routing;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceConverter;

import com.tibco.xpd.om.core.om.OrgUnitRelationship;
import com.tibco.xpd.om.modeler.diagram.edit.parts.OrgUnitRelationshipDisplayNameEditPart;
import com.tibco.xpd.om.modeler.diagram.edit.parts.OrgUnitRelationshipEditPart;
import com.tibco.xpd.om.modeler.diagram.part.OrganizationModelVisualIDRegistry;
import com.tibco.xpd.om.modeler.diagram.view.factories.custom.IOMViewConstants;
import com.tibco.xpd.om.modeler.subdiagram.preferences.custom.IOMSubPreferenceConstants;

/**
 * @generated
 */
public class OrgUnitRelationshipViewFactory extends ConnectionViewFactory {

    /**
     * @generated NOT
     */
    protected List createStyles(View view) {
        List styles = new ArrayList();
        styles.add(NotationFactory.eINSTANCE.createConnectorStyle());
        styles.add(NotationFactory.eINSTANCE.createFontStyle());
        styles.add(NotationFactory.eINSTANCE.createLineStyle());
        styles.add(NotationFactory.eINSTANCE.createLineTypeStyle());
        return styles;
    }

    /**
     * @generated NOT
     */
    protected void decorateView(View containerView, View view,
            IAdaptable semanticAdapter, String semanticHint, int index,
            boolean persisted) {
        if (semanticHint == null) {
            semanticHint = OrganizationModelVisualIDRegistry
                    .getType(OrgUnitRelationshipEditPart.VISUAL_ID);
            view.setType(semanticHint);
        }
        super.decorateView(containerView, view, semanticAdapter, semanticHint,
                index, persisted);

        IAdaptable eObjectAdapter = null;
        EObject eObject = (EObject) semanticAdapter.getAdapter(EObject.class);
        if (eObject != null) {
            eObjectAdapter = new EObjectAdapter(eObject);
        }
        Node displayNameNode = getViewService()
                .createNode(
                        eObjectAdapter,
                        view,
                        OrganizationModelVisualIDRegistry
                                .getType(OrgUnitRelationshipDisplayNameEditPart.VISUAL_ID),
                        ViewUtil.APPEND, true, getPreferencesHint());

        if (displayNameNode != null) {
            if (eObject instanceof OrgUnitRelationship) {
                OrgUnitRelationship rel = (OrgUnitRelationship) eObject;

                if (rel.getType() != null) {
                    displayNameNode.setVisible(true);
                } else {
                    displayNameNode.setVisible(false);
                }
            }
        }

        // TODO: Should the connections be tree style

        // Object obj = view.getElement();
        //
        // if (obj instanceof OrgUnitRelationship) {
        // OrgUnitRelationship rel = (OrgUnitRelationship) obj;
        // RoutingStyle rstyle = (RoutingStyle) view
        // .getStyle(NotationPackage.eINSTANCE.getRoutingStyle());
        //
        // if (rel.isIsHierarchical()) {
        // rstyle.setRouting(Routing.TREE_LITERAL);
        // } else {
        // rstyle.setRouting(Routing.MANUAL_LITERAL);
        // }
        // }
    }

    @Override
    protected void initializeFromPreferences(View view) {
        // TODO Auto-generated method stub
        super.initializeFromPreferences(view);

        IPreferenceStore store = (IPreferenceStore) getPreferencesHint()
                .getPreferenceStore();

        Object obj = view.getElement();

        if (obj instanceof OrgUnitRelationship) {
            OrgUnitRelationship rel = (OrgUnitRelationship) obj;
            ConnectorStyle connStyle = (ConnectorStyle) view
                    .getStyle(NotationPackage.eINSTANCE.getConnectorStyle());

            if (connStyle != null) {
                if (rel.isIsHierarchical()) {
                    connStyle.setRouting(Routing.MANUAL_LITERAL);
                } else {
                    connStyle.setRouting(Routing.MANUAL_LITERAL);
                }
            }

            LineStyle lineStyle = (LineStyle) view
                    .getStyle(NotationPackage.Literals.LINE_STYLE);

            if (lineStyle != null) {

                lineStyle
                        .setLineWidth(IOMViewConstants.OM_VIEW_CONSTANTS_DEFAULT_ORGUNITREL_WIDTH);

                if (rel.isIsHierarchical()) {
                    lineStyle
                            .setLineColor(FigureUtilities
                                    .RGBToInteger(PreferenceConverter
                                            .getColor(
                                                    store,
                                                    IOMSubPreferenceConstants.PREF_HIERARCHY_LINE_COLOR)));
                } else {
                    lineStyle
                            .setLineColor(FigureUtilities
                                    .RGBToInteger(PreferenceConverter
                                            .getColor(
                                                    store,
                                                    IOMSubPreferenceConstants.PREF_ASSOCIATION_LINE_COLOR)));
                    lineStyle.setLineWidth(2);
                }

            }

            LineTypeStyle lineTypeStyle = (LineTypeStyle) view
                    .getStyle(NotationPackage.Literals.LINE_TYPE_STYLE);

            if (lineTypeStyle != null) {

                if (!rel.isIsHierarchical()) {

                    EObject containerTrg = rel.getTo().eContainer();
                    EObject containerSrc = rel.getFrom().eContainer();

                    if (containerSrc == containerTrg) {
                        lineTypeStyle.setLineType(LineType.DOT_LITERAL);
                    } else {
                        lineTypeStyle.setLineType(LineType.DASH_LITERAL);

                    }
                } else {
                    lineTypeStyle.setLineType(LineType.SOLID_LITERAL);

                }

            }
        }
    }
}
