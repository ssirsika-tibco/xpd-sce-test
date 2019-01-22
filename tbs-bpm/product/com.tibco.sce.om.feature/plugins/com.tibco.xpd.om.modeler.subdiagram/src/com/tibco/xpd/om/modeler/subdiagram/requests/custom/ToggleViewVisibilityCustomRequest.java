/**
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.om.modeler.subdiagram.requests.custom;

import org.eclipse.gmf.runtime.diagram.ui.requests.ChangePropertyValueRequest;
import org.eclipse.gmf.runtime.notation.View;

/**
 * 
 * Use this custom request when the visiblity property (boolean) of a view needs
 * to be switched to its opposite state.
 * 
 * @author rgreen
 * 
 */
public class ToggleViewVisibilityCustomRequest extends
        ChangePropertyValueRequest {

    private View view;

    /**
     * @param type
     */
    public ToggleViewVisibilityCustomRequest(View view) {
        super("Visibility", "notation.View.visible"); //$NON-NLS-1$ //$NON-NLS-2$
        this.view = view;
        setType(IOMCustomRequestConstants.REQ_TOGGLE_VIEW_VISIBILITY);
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

}
