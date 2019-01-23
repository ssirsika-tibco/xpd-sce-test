/**
 * 
 */
package com.tibco.xpd.bom.modeler.diagram.edit.parts.customfigures;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.gmf.runtime.diagram.ui.editparts.GraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.l10n.DiagramColorRegistry;
import org.eclipse.gmf.runtime.draw2d.ui.figures.FigureUtilities;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.swt.graphics.Color;

import com.tibco.xpd.bom.modeler.diagram.preferences.IBOMPreferenceConstants;
import com.tibco.xpd.bom.resources.ui.bomnotation.BomNotationPackage;
import com.tibco.xpd.bom.resources.ui.bomnotation.ShapeGradientStyle;

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
    public static Object getPreferedClassGradientValue(GraphicalEditPart part,
            EStructuralFeature feature) {
        return getPreferedGradientValue(part,
                feature,
                IBOMPreferenceConstants.PREF_CLASS_GRAD_COLOR1,
                IBOMPreferenceConstants.PREF_CLASS_GRAD_COLOR2);
    }

    /**
     * Returns preferred value for Case Class gradient, or null it the feature
     * doesn't represent gradient colour.
     * 
     * @param part      Edit part for the colour
     * @param feature
     * @return
     */
    public static Object getPreferedCaseClassGradientValue(GraphicalEditPart part,
            EStructuralFeature feature) {
        return getPreferedGradientValue(part,
                feature,
                IBOMPreferenceConstants.PREF_CLASS_GRAD_COLOR4,
                IBOMPreferenceConstants.PREF_CLASS_GRAD_COLOR2);
    }

    /**
     * Returns preferred value for Global Class gradient, or null it the feature
     * doesn't represent gradient colour.
     * 
     * @param part      Edit part for the colour
     * @param feature
     * @return
     */
    public static Object getPreferedGlobalClassGradientValue(GraphicalEditPart part,
            EStructuralFeature feature) {
        return getPreferedGradientValue(part,
                feature,
                IBOMPreferenceConstants.PREF_CLASS_GRAD_COLOR3,
                IBOMPreferenceConstants.PREF_CLASS_GRAD_COLOR2);
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
    public static Object getPreferedPrimitiveTypeGradientValue(
            GraphicalEditPart part, EStructuralFeature feature) {
        return getPreferedGradientValue(part,
                feature,
                IBOMPreferenceConstants.PREF_PRIMTYPE_GRAD_COLOR1,
                IBOMPreferenceConstants.PREF_PRIMTYPE_GRAD_COLOR2);
    }

    /**
     * Returns preferred value for package gradient, or null it the feature
     * doesn't represent gradient color.
     * 
     * @param part
     *            that have preference store
     * @param feature
     * @return
     */
    public static Object getPreferedPackageGradientValue(
            GraphicalEditPart part, EStructuralFeature feature) {
        return getPreferedGradientValue(part,
                feature,
                IBOMPreferenceConstants.PREF_PACKAGE_GRAD_COLOR1,
                IBOMPreferenceConstants.PREF_PACKAGE_GRAD_COLOR2);
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
                .equals(BomNotationPackage.Literals.SHAPE_GRADIENT_STYLE__GRAD_START_COLOR)) {
            return FigureUtilities.RGBToInteger(PreferenceConverter
                    .getColor(store, c1));
        }
        if (feature
                .equals(BomNotationPackage.Literals.SHAPE_GRADIENT_STYLE__GRAD_END_COLOR)) {
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
                        .getStyle(BomNotationPackage.Literals.SHAPE_GRADIENT_STYLE);
        if (gradStyle != null) {
            DiagramColorRegistry registry = DiagramColorRegistry.getInstance();

            /*
             * If the notation model doesn't exist (e.g., in case of generated ones) then the gradStyle
             * will return 0 causing the figure to be black, so, we should
             * use the default colors provided instead of 0 (black)
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
     * Check if given notation affects gradient.
     * 
     * @param notification
     * @return
     */
    public static boolean isGradientChange(Notification notification) {
        Object feature = notification.getFeature();
        return (BomNotationPackage.eINSTANCE
                .getShapeGradientStyle_GradStartColor().equals(feature))
                || (BomNotationPackage.eINSTANCE
                        .getShapeGradientStyle_GradEndColor().equals(feature));
    }
}
