package com.tibco.xpd.om.modeler.diagram.view.factories;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.gmf.runtime.diagram.core.preferences.PreferencesHint;
import org.eclipse.gmf.runtime.diagram.ui.view.factories.BasicNodeViewFactory;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.View;

/**
 * @generated
 */
public class DynamicOrgUnitDisplayNameViewFactory extends BasicNodeViewFactory {

    /**
     * @generated
     */
    @Override
    protected List createStyles(View view) {
        List styles = new ArrayList();
        return styles;
    }

    /**
     * @see org.eclipse.gmf.runtime.diagram.ui.view.factories.BasicNodeViewFactory#createNode()
     * 
     * @return
     */
    @Override
    protected Node createNode() {
        // TODO Auto-generated method stub
        return super.createNode();
    }

    /**
     * @see org.eclipse.gmf.runtime.diagram.ui.view.factories.BasicNodeViewFactory#createView(org.eclipse.core.runtime.IAdaptable,
     *      org.eclipse.gmf.runtime.notation.View, java.lang.String, int,
     *      boolean,
     *      org.eclipse.gmf.runtime.diagram.core.preferences.PreferencesHint)
     * 
     * @param semanticAdapter
     * @param containerView
     * @param semanticHint
     * @param index
     * @param persisted
     * @param preferencesHint
     * @return
     */
    @Override
    public View createView(IAdaptable semanticAdapter, View containerView,
            String semanticHint, int index, boolean persisted,
            PreferencesHint preferencesHint) {
        // TODO Auto-generated method stub
        return super.createView(semanticAdapter,
                containerView,
                semanticHint,
                index,
                persisted,
                preferencesHint);
    }
}
