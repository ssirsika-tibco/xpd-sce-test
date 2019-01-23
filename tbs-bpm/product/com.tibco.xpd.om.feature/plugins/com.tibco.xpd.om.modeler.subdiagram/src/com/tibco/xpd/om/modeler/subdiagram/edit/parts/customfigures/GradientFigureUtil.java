/**
 * 
 */
package com.tibco.xpd.om.modeler.subdiagram.edit.parts.customfigures;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.gmf.runtime.diagram.ui.editparts.GraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.l10n.DiagramColorRegistry;
import org.eclipse.gmf.runtime.draw2d.ui.figures.FigureUtilities;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.swt.graphics.Color;

import com.tibco.xpd.om.modeler.subdiagram.preferences.custom.IOMSubPreferenceConstants;
import com.tibco.xpd.om.resources.ui.omnotation.BorderGradientStyle;
import com.tibco.xpd.om.resources.ui.omnotation.OmNotationPackage;
import com.tibco.xpd.om.resources.ui.omnotation.ShapeGradientStyle;

/**
 * @author wzurek
 * 
 */
public class GradientFigureUtil {

    /**
     * Returns preferred value for class gradient, or null it the feature
     * doesn't represent gradient color.
     * 
     * @param part
     *            that have preference store
     * @param feature
     * @return
     */
    public static Object getPreferedOrgUnitGradientValue(
            GraphicalEditPart part, EStructuralFeature feature) {
        return getPreferedGradientValue(part,
                feature,
                IOMSubPreferenceConstants.PREF_ORGUNIT_GRAD_START_COLOR,
                IOMSubPreferenceConstants.PREF_ORGUNIT_GRAD_END_COLOR);
    }

    /**
     * Returns preferred value for primitive type gradient, or null it the
     * feature doesn't represent gradient color.
     * 
     * @param part
     *            that have preference store
     * @param feature
     * @return
     */
    public static Object getPreferedBadgeGradientValue(GraphicalEditPart part,
            EStructuralFeature feature) {
        return getPreferedGradientValue(part,
                feature,
                IOMSubPreferenceConstants.PREF_BADGE_GRAD_START_COLOR,
                IOMSubPreferenceConstants.PREF_BADGE_GRAD_END_COLOR);
    }

    /**
     * Get actual gradient preferences.
     * 
     * @param part
     * @param feature
     * @param c1
     * @param c2
     * @return
     */
    private static Object getPreferedGradientValue(GraphicalEditPart part,
            EStructuralFeature feature, String c1, String c2) {
        IPreferenceStore store =
                (IPreferenceStore) part.getDiagramPreferencesHint()
                        .getPreferenceStore();
        if (feature
                .equals(OmNotationPackage.Literals.SHAPE_GRADIENT_STYLE__GRAD_START_COLOR)) {
            return FigureUtilities.RGBToInteger(PreferenceConverter
                    .getColor(store, c1));
        }
        if (feature
                .equals(OmNotationPackage.Literals.SHAPE_GRADIENT_STYLE__GRAD_END_COLOR)) {
            return FigureUtilities.RGBToInteger(PreferenceConverter
                    .getColor(store, c2));
        }
        return null;
    }

    /**
     * Update gradient figure from the view style.
     * 
     * @param view
     * @param figure
     */
    public static void updateFigureGradient(View view, IGradientFigure figure,
            int gradStartColor, int gradEndColor) {
        ShapeGradientStyle gradStyle =
                (ShapeGradientStyle) view
                        .getStyle(OmNotationPackage.Literals.SHAPE_GRADIENT_STYLE);
        if (gradStyle != null) {
            DiagramColorRegistry registry = DiagramColorRegistry.getInstance();

            /*
             * If the notation model doesn't exist then the gradStyle will
             * return 0 causing the figure to be black, so, we should then use
             * the default colors provided instead of 0 (black)
             */
            if (gradStyle.getGradStartColor() > 0) {
                gradStartColor = gradStyle.getGradStartColor();
            }
            if (gradStyle.getGradEndColor() > 0) {
                gradEndColor = gradStyle.getGradEndColor();
            }

            Color gradStart = registry.getColor(gradStartColor);
            Color gradEnd = registry.getColor(gradEndColor);

            figure.setGradientStart(gradStart);
            figure.setGradientEnd(gradEnd);
        }
    }

    /**
     * Update gradient figure from the BorderGradienStyle.
     * 
     * @param view
     * @param figure
     */
    public static void updateBorderGradient(View view,
            IBorderGradientFigure figure) {
        BorderGradientStyle gradStyle =
                (BorderGradientStyle) view
                        .getStyle(OmNotationPackage.Literals.BORDER_GRADIENT_STYLE);
        if (gradStyle != null) {
            DiagramColorRegistry registry = DiagramColorRegistry.getInstance();

            Color gradStart = registry.getColor(gradStyle.getGradStartColor());
            Color gradEnd = registry.getColor(gradStyle.getGradEndColor());
            figure.setBorderGradientStart(gradStart);
            figure.setBorderGradientEnd(gradEnd);
        }
    }

    /**
     * Check if given notation affects gradient.
     * 
     * @param notification
     * @return
     */
    public static boolean isGradientChange(Notification notification) {
        Object feature = notification.getFeature();
        return (OmNotationPackage.eINSTANCE
                .getShapeGradientStyle_GradStartColor().equals(feature))
                || (OmNotationPackage.eINSTANCE
                        .getShapeGradientStyle_GradEndColor().equals(feature)
                        || OmNotationPackage.eINSTANCE
                                .getBorderGradientStyle_GradStartColor()
                                .equals(feature) || OmNotationPackage.eINSTANCE
                        .getBorderGradientStyle_GradEndColor().equals(feature));
    }
}
