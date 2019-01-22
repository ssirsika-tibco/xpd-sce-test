/*
 * Copyright (c) TIBCO Software Inc 2007. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.diagram.view.factories;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.gmf.runtime.diagram.ui.view.factories.BasicNodeViewFactory;
import org.eclipse.gmf.runtime.notation.FontStyle;
import org.eclipse.gmf.runtime.notation.NotationFactory;
import org.eclipse.gmf.runtime.notation.View;

/**
 * @generated
 */
public class BadgeModelNameViewFactory extends BasicNodeViewFactory {

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

        EList styles = view.getStyles();

        for (Object object : styles) {
            if (object instanceof FontStyle) {
                FontStyle fs = (FontStyle) object;
                fs.setBold(true);
            }
        }

    }
}
