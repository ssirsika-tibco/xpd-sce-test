package com.tibco.xpd.om.modeler.diagram.view.factories.custom;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.common.util.EList;
import org.eclipse.gmf.runtime.diagram.ui.view.factories.BasicNodeViewFactory;
import org.eclipse.gmf.runtime.notation.FontStyle;
import org.eclipse.gmf.runtime.notation.NotationFactory;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.View;

/**
 * @generated
 */
public class OrgModelNameBadgeViewFactory extends BasicNodeViewFactory {

    /**
     * @generated
     */
    protected List createStyles(View view) {
        List styles = new ArrayList();
        styles.add(NotationFactory.eINSTANCE.createFontStyle());
        return styles;
    }

    @Override
    protected void decorateView(View containerView, View view,
            IAdaptable semanticAdapter, String semanticHint, int index,
            boolean persisted) {
        // TODO Auto-generated method stub
        super.decorateView(containerView, view, semanticAdapter, semanticHint,
                index, persisted);



//         FontStyle style = (FontStyle) getFontStyleOwnerView().getStyle(
//         NotationPackage.eINSTANCE.getFontStyle());

    }
    
    @Override
    protected void initializeFromPreferences(View view) {
        // TODO Auto-generated method stub
        super.initializeFromPreferences(view);
        
        EList styles = view.getStyles();

        for (Object object : styles) {
            if (object instanceof FontStyle){
                FontStyle fs = (FontStyle)object;
                fs.setBold(true);
            }
        }
        
        
    }

}