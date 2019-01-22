package com.tibco.xpd.om.modeler.diagram.view.factories;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.diagram.core.util.ViewUtil;
import org.eclipse.gmf.runtime.diagram.ui.view.factories.DiagramViewFactory;
import org.eclipse.gmf.runtime.draw2d.ui.figures.FigureUtilities;
import org.eclipse.gmf.runtime.emf.core.util.EObjectAdapter;
import org.eclipse.gmf.runtime.notation.Bounds;
import org.eclipse.gmf.runtime.notation.FillStyle;
import org.eclipse.gmf.runtime.notation.LayoutConstraint;
import org.eclipse.gmf.runtime.notation.MeasurementUnit;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.NotationFactory;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceConverter;

import com.tibco.xpd.om.core.om.OrgModel;
import com.tibco.xpd.om.modeler.diagram.edit.parts.custom.OrgModelGroupsEditPart;
import com.tibco.xpd.om.modeler.diagram.part.OrganizationModelVisualIDRegistry;
import com.tibco.xpd.om.modeler.subdiagram.preferences.custom.IOMSubPreferenceConstants;

/**
 * @generated
 */
public class OrgModelViewFactory extends DiagramViewFactory {

    /**
     * @generated NOT
     */
    protected List createStyles(View view) {
        List styles = new ArrayList();
        styles.add(NotationFactory.eINSTANCE.createDiagramStyle());
        styles.add(NotationFactory.eINSTANCE.createFillStyle());
        return styles;
    }

    /**
     * @generated
     */
    protected MeasurementUnit getMeasurementUnit() {
        return MeasurementUnit.PIXEL_LITERAL;
    }

    @Override
    protected void initializeFromPreferences(View view) {
        // TODO Auto-generated method stub
        super.initializeFromPreferences(view);

        // Set the diagram background colour
        FillStyle style =
                (FillStyle) view.getStyle(NotationPackage.eINSTANCE
                        .getFillStyle());

        if (style != null) {
            IPreferenceStore store =
                    (IPreferenceStore) getPreferencesHint()
                            .getPreferenceStore();
            style.setFillColor(FigureUtilities.RGBToInteger(PreferenceConverter
                    .getColor(store,
                            IOMSubPreferenceConstants.PREF_DIAGRAM_BG_COLOR)));
        }

    }

    @Override
    protected void decorateView(View view, IAdaptable semanticAdapter,
            String diagramKind) {

        IAdaptable eObjectAdapter = null;
        EObject eObject = (EObject) semanticAdapter.getAdapter(EObject.class);
        if (eObject != null) {
            eObjectAdapter = new EObjectAdapter(eObject);
        }

        if (eObject instanceof OrgModel) {

            // Create the custom groups container view
            Node node =
                    getViewService().createNode(eObjectAdapter,
                            view,
                            OrganizationModelVisualIDRegistry
                                    .getType(OrgModelGroupsEditPart.VISUAL_ID),
                            ViewUtil.APPEND,
                            true,
                            getPreferencesHint());

            LayoutConstraint constNode = node.getLayoutConstraint();

            if (constNode instanceof Bounds) {
                ((Bounds) constNode).setX(0);
                ((Bounds) constNode).setY(145);

            }
        }
        super.decorateView(view, semanticAdapter, diagramKind);
    }
}
