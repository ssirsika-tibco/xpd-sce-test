package com.tibco.xpd.om.modeler.diagram.view.factories;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.gmf.runtime.diagram.ui.view.factories.BasicNodeViewFactory;
import org.eclipse.gmf.runtime.notation.FontStyle;
import org.eclipse.gmf.runtime.notation.NotationFactory;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.View;

/**
 * @generated
 */
public class OrgUnitFeatureNameViewFactory extends BasicNodeViewFactory {

    /**
     * @generated NOT
     */
    protected List createStyles(View view) {
        List styles = new ArrayList();
        styles.add(NotationFactory.eINSTANCE.createFontStyle());
        return styles;
    }

    @Override
    protected void initializeFromPreferences(View view) {

        super.initializeFromPreferences(view);

        FontStyle fontStyle = (FontStyle) view
                .getStyle(NotationPackage.Literals.FONT_STYLE);

        fontStyle.setItalic(true);

    }
}
