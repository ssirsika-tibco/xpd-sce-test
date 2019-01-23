/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.rsd.ui.sections;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;

import com.tibco.xpd.rsd.Resource;
import com.tibco.xpd.rsd.RsdPackage;
import com.tibco.xpd.rsd.ui.RsdImage;
import com.tibco.xpd.rsd.ui.components.AddParamsFromPathHandler;
import com.tibco.xpd.rsd.ui.components.PathTemplateField;
import com.tibco.xpd.rsd.ui.internal.Messages;
import com.tibco.xpd.ui.properties.XpdFormToolkit;

/**
 * General section for RSD's resource.
 *
 * @author jarciuch
 * @since 2 Feb 2015
 */
public class ResourceGeneralSection extends AbstractRsdSection {

    private Composite resourceControls;

    private Text pathTemplateText;

    private PathTemplateField pathTemplateField;

    private Button extractParamsButton;

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doRefresh() {
        EObject input = getInput();

        if (input instanceof Resource && isValidControl(pathTemplateText)) {
            Resource resource = (Resource) input;
            updateText(pathTemplateText, resource.getPathTemplate());

            // Set context for content assist.
            pathTemplateField.setContextResource(resource);
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {
        Composite root = toolkit.createComposite(parent);
        GridLayoutFactory.swtDefaults().applyTo(root);

        resourceControls = toolkit.createComposite(root);
        GridDataFactory.swtDefaults().align(SWT.FILL, SWT.TOP)
                .grab(true, false).applyTo(resourceControls);
        getGroupControlLayoutFactory().numColumns(3).applyTo(resourceControls);

        createLabel(resourceControls,
                toolkit,
                Messages.ResourceGeneralSection_PathTemplate_label);
        pathTemplateText =
                toolkit.createText(resourceControls,
                        "", "Resource-pathTemplate"); //$NON-NLS-1$ //$NON-NLS-2$

        // adds decoration and content assist.
        pathTemplateField = new PathTemplateField(pathTemplateText);

        GridDataFactory.swtDefaults().align(SWT.FILL, SWT.CENTER)
                .grab(true, false).applyTo(pathTemplateText);

        extractParamsButton =
                toolkit.createButton(resourceControls, "", SWT.PUSH); //$NON-NLS-1$
        extractParamsButton.setImage(RsdImage
                .getImage(RsdImage.CREATE_PARAMS_FROM_PATH));
        extractParamsButton
                .setToolTipText(Messages.ResourceGeneralSection_AddParamsFromPath_tooltip);
        GridDataFactory.swtDefaults().applyTo(extractParamsButton);
        extractParamsButton.addSelectionListener(new SelectionAdapter() {
            /**
             * {@inheritDoc}
             */
            @Override
            public void widgetSelected(SelectionEvent e) {
                EObject input = getInput();
                if (input instanceof Resource) {
                    AddParamsFromPathHandler handler =
                            new AddParamsFromPathHandler(
                                    (Resource) input,
                                    pathTemplateText.getText(),
                                    (TransactionalEditingDomain) getEditingDomain());
                    handler.execute();
                }
            }

        });

        /*
         * Listen to the path changes and enable/disable extractParamsButton
         * accordingly. It's worth mentioning that this modify listener will
         * also be called on doRefresh() event (caused by related model changes)
         * and that's what's desired as we also want to handle the param's add
         * removal case.
         */
        pathTemplateText.addModifyListener(new ModifyListener() {
            @Override
            public void modifyText(ModifyEvent e) {
                EObject input = getInput();
                if (input instanceof Resource) {
                    AddParamsFromPathHandler handler =
                            new AddParamsFromPathHandler(
                                    (Resource) input,
                                    pathTemplateText.getText(),
                                    (TransactionalEditingDomain) getEditingDomain());
                    extractParamsButton.setEnabled(handler.hasNewParams());
                }
            }
        });

        manageControlUpdateOnDeactivate(pathTemplateText);
        return root;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Command doGetCommand(Object obj) {
        EObject input = getInput();
        Command cmd = null;
        if (input instanceof Resource) {
            Resource service = (Resource) input;
            if (obj == pathTemplateText) {
                String value = ((Text) obj).getText();
                if (!value.equals(service.getPathTemplate())) {
                    // Update the name
                    cmd =
                            SetCommand.create(getEditingDomain(),
                                    input,
                                    RsdPackage.eINSTANCE
                                            .getResource_PathTemplate(),
                                    value);
                }
            }
        }
        return cmd;
    }

}
