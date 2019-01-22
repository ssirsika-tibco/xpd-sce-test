/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.implementer.nativeservices.globaldataservice;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.tibco.xpd.implementer.nativeservices.internal.Messages;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.xpdExtension.CaseReferenceOperationsType;

/**
 * Delete case object(s) operation properties section of the Global Data Task.
 * 
 * @author njpatel
 */
public class CaseRefDeleteOperationPage extends
        AbstractOperationPage<CaseReferenceOperationsType> {

    /**
     * Delete case object(s) operation properties section of the Global Data
     * Task.
     * 
     * @param section
     */
    public CaseRefDeleteOperationPage(GlobalDataTaskServiceSection section) {
        super(Messages.CaseRefDeleteOperationPage_label, section);
    }

    /**
     * @see com.tibco.xpd.implementer.nativeservices.globaldataservice.AbstractOperationPage#createPage(org.eclipse.swt.widgets.Composite,
     *      com.tibco.xpd.ui.properties.XpdFormToolkit)
     * 
     * @param parent
     * @param toolkit
     * @return
     */
    @Override
    public Control createPage(Composite parent, XpdFormToolkit toolkit) {
        GridLayout gl;

        Composite deleteCtrlsContainer = toolkit.createComposite(parent);

        gl = new GridLayout(1, false);
        gl.marginWidth = 0;
        gl.marginHeight = 0;
        deleteCtrlsContainer.setLayout(gl);

        Control c =
                createWrappedDescriptionText(toolkit,
                        deleteCtrlsContainer,
                        Messages.GlobalDataTaskServiceSection_deleteCaseObjByCaseId_longdesc);
        c.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        return deleteCtrlsContainer;
    }

    /**
     * @see com.tibco.xpd.implementer.nativeservices.globaldataservice.AbstractCaseRefOperationPage#update(com.tibco.xpd.xpdExtension.CaseReferenceOperationsType)
     * 
     * @param opType
     */
    @Override
    public void update(CaseReferenceOperationsType opType) {
        // No controls to update

    }

    /**
     * @see com.tibco.xpd.implementer.nativeservices.globaldataservice.AbstractCaseRefOperationPage#getCommand(com.tibco.xpd.xpdExtension.CaseReferenceOperationsType,
     *      java.lang.Object)
     * 
     * @param opType
     * @param control
     * @return
     */
    @Override
    public Command getCommand(EditingDomain editingDomain,
            CaseReferenceOperationsType opType, Object control) {
        // No controls in this section
        return null;
    }

}
