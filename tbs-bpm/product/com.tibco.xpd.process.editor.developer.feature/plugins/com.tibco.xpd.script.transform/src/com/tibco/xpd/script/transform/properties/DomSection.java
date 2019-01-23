/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.script.transform.properties;

import org.eclipse.emf.common.command.Command;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;

import com.tibco.xpd.script.transform.document.TransformDirection;
import com.tibco.xpd.script.transform.util.TransformUtil;
import com.tibco.xpd.ui.properties.AbstractFilteredTransactionalSection;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Dom section
 * 
 * @author mtorres
 */
public class DomSection extends AbstractFilteredTransactionalSection {
    
    private Composite sectionComposite;

    private Text domText;
    
    private TransformDirection transformDirection;
    
    public DomSection(TransformDirection transformDirection) {
        super(null);
        this.transformDirection = transformDirection;
    }

    @Override
    protected Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {
        sectionComposite = toolkit.createComposite(parent);
        GridLayout gl = new GridLayout();
        sectionComposite.setLayout(gl);
        GridData gData = new GridData(SWT.FILL, SWT.FILL, true, true);
        sectionComposite.setLayoutData(gData);
    
        domText =
                toolkit.createText(sectionComposite, "", SWT.READ_ONLY //$NON-NLS-1$
                        | SWT.WRAP | SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL
                        | SWT.BORDER_DASH | SWT.FLAT, "dom_section.instrumentationName");//$NON-NLS-1$
        gData.minimumHeight = 200;
        domText.setLayoutData(gData);
        return sectionComposite;
    }

    @Override
    protected Command doGetCommand(Object obj) {
        // Do nothing
        return null;
    }

    @Override
    protected void doRefresh() {
        if (domText != null) {
            refreshDomSection(domText);
        }
    }

    private void refreshDomSection(Text domText) {
        if (getActivity() != null) {
            String domStr =
                    TransformUtil.getTransformXMLStr(transformDirection,
                            getActivity(), true);
            if (domStr == null) {
                domStr = "";//$NON-NLS-1$
            }
            domText.setText(domStr);
        }
    }
    
    private Activity getActivity(){
        if(getInput() != null){
            return Xpdl2ModelUtil.getParentActivity(getInput());
        }
        return null;
    }
    

    /**
     * @return
     */
    public Control getControl() {
        return sectionComposite;
    }

}
