/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.n2.globalsignal.resource.properties.general;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.viewers.IFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.tibco.xpd.globalSignalDefinition.GlobalSignalDefinitionPackage;
import com.tibco.xpd.globalSignalDefinition.GlobalSignalDefinitions;
import com.tibco.xpd.n2.globalsignal.resource.internal.Messages;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.ui.properties.AbstractTransactionalSection;
import com.tibco.xpd.ui.properties.XpdFormToolkit;

/**
 * General property section for GSD file.
 * 
 * @author sajain
 * @since Feb 5, 2015
 */
public class GSDFileProperySection extends AbstractTransactionalSection
        implements IFilter {

    /**
     * Label for GSD description.
     */
    private Label lblDescription;

    /**
     * Text control for GSD file description.
     */
    private Text txtDescription;

    /**
     * GSD file extension.
     */
    private static final String GSD_EXTENSION = "gsd"; //$NON-NLS-1$

    /**
     * General property section for GSD file.
     */
    public GSDFileProperySection() {

    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doCreateControls(org.eclipse.swt.widgets.Composite,
     *      com.tibco.xpd.ui.properties.XpdFormToolkit)
     * 
     * @param parent
     * @param toolkit
     * @return
     */
    @Override
    protected Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {

        /*
         * Create root composite.
         */
        Composite root = toolkit.createComposite(parent);
        GridLayout gl = new GridLayout(2, false);
        root.setLayout(gl);

        /*
         * Description label.
         */
        lblDescription =
                toolkit.createLabel(root,
                        Messages.GSDFilePropertSection_DescriptionLabel);
        lblDescription.setLayoutData(new GridData(SWT.BEGINNING, SWT.BEGINNING,
                false, false));

        /*
         * Description text.
         */
        txtDescription = toolkit.createText(root, "", SWT.MULTI); //$NON-NLS-1$
        txtDescription.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,
                true));
        manageControl(txtDescription);

        return root;
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doRefresh()
     * 
     */
    @Override
    protected void doRefresh() {

        GlobalSignalDefinitions gsds = getGlobalSignalDefinitions();

        /*
         * Refresh description text control.
         */
        if (gsds != null) {

            if (gsds.getDescription() != null) {
                updateText(txtDescription, gsds.getDescription());
            }
        }
    }

    /**
     * Get GlobalSignalDefinitions from the input.
     * 
     * @return <code>GlobalSignalDefinitions</code> if input is valid,
     *         <b>null</b> otherwise.
     */
    private GlobalSignalDefinitions getGlobalSignalDefinitions() {

        Object o = getInput();

        if (o instanceof GlobalSignalDefinitions) {

            GlobalSignalDefinitions gsds = (GlobalSignalDefinitions) o;

            return gsds;
        }
        return null;
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractTransactionalSection#resollveInput(java.lang.Object)
     * 
     * @param object
     * @return
     */
    @Override
    protected EObject resollveInput(Object object) {

        if (object instanceof IFile) {

            IFile gsdFile = (IFile) object;

            if (GSD_EXTENSION.equals(gsdFile.getFileExtension())) {

                WorkingCopy workingCopy =
                        WorkingCopyUtil.getWorkingCopy(gsdFile);

                return workingCopy.getRootElement();
            }
        }
        return super.resollveInput(object);
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doGetCommand(java.lang.Object)
     * 
     * @param obj
     * @return
     */
    @Override
    protected Command doGetCommand(Object obj) {

        Command cmd = null;

        EditingDomain ed = getEditingDomain();

        GlobalSignalDefinitions gsds = getGlobalSignalDefinitions();

        /*
         * Handle description text control.
         */
        if (obj == txtDescription) {

            if (txtDescription.getText() != null) {

                cmd = new CompoundCommand();

                ((CompoundCommand) cmd).append(SetCommand.create(ed,
                        gsds,
                        GlobalSignalDefinitionPackage.eINSTANCE
                                .getGlobalSignalDefinitions_Description(),
                        txtDescription.getText()));
            }

        }

        return cmd;
    }

    /**
     * @see org.eclipse.jface.viewers.IFilter#select(java.lang.Object)
     * 
     * @param toTest
     * @return
     */
    @Override
    public boolean select(Object toTest) {

        if (toTest instanceof IFile) {

            IFile file = (IFile) toTest;

            if (GSD_EXTENSION.equals(file.getFileExtension())) {
                return true;
            }

        } else if (toTest instanceof GlobalSignalDefinitions) {

            /*
             * XPD-7482: Saket: When we select a control in the GSD form editor,
             * then the input turns out to be GlobalSignalDefinitions.
             */
            return true;

        }

        return false;
    }

}
