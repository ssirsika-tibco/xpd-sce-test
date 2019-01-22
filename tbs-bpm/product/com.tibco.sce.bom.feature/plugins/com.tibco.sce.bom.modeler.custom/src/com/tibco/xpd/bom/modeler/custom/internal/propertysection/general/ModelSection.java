/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.custom.internal.propertysection.general;

import java.text.DateFormat;
import java.util.Date;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;
import org.eclipse.uml2.uml.Model;

import com.tibco.xpd.bom.modeler.custom.internal.Messages;
import com.tibco.xpd.bom.modeler.custom.internal.propertysection.AbstractGeneralSection;
import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.bom.resources.ui.util.BomUIUtil;
import com.tibco.xpd.ui.properties.XpdFormToolkit;

/**
 * BOM property section for a {@link Model}. This will allow the setting of an
 * Author.
 * 
 * @author njpatel
 * 
 */
public class ModelSection extends AbstractGeneralSection {

    private Text authorTxt;

    private Text dateCreatedTxt;

    
    @Override
    protected boolean shouldDisplay(EObject eo) {
        return eo instanceof Model || eo instanceof Diagram;
    }

    @Override
    protected EObject resollveInput(Object object) {

        /*
         * If the input is a user-diagram then disable the author input
         */
        Diagram diagram = null;
        if (object instanceof EditPart) {
            Object model = ((EditPart) object).getModel();
            if (model instanceof Diagram) {
                diagram = (Diagram) model;
            } else if (model instanceof View) {
                // For badge edit part in the user diagram
                EObject element = ((View) model).getElement();
                if (element instanceof Diagram) {
                    diagram = (Diagram) element;
                }
            }
        } else if (object instanceof Diagram) {
            diagram = (Diagram) object;
        }

        if (diagram != null) {
            if (BomUIUtil.isUserDiagram(diagram) && authorTxt != null
                    && !authorTxt.isDisposed()) {
                authorTxt.setEnabled(false);
            }
            return diagram;
        }
        return super.resollveInput(object);
    }

    @Override
    protected Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {
        Composite root = (Composite) super.doCreateControls(parent, toolkit);

        createLabel(root, toolkit, Messages.ModelSection_author_label);
        authorTxt = toolkit.createText(root, ""); //$NON-NLS-1$
        setLayoutData(authorTxt);
        manageControlUpdateOnDeactivate(authorTxt);

        createLabel(root,
                        toolkit,
                        Messages.ModelSection_dateCreated_label);
        dateCreatedTxt = toolkit.createText(root, "", SWT.READ_ONLY); //$NON-NLS-1$
        setLayoutData(dateCreatedTxt);
        dateCreatedTxt.setEnabled(false);

        return root;
    }

    @Override
    protected Command doGetCommand(Object obj) {
        if (obj == authorTxt) {
            EObject input = getSemanticInput();
            if (input instanceof Model) {
                final Model model = (Model) input;

                final EMap<String, String> annotations =
                        getAnnotation((Model) input);

                if (annotations != null) {
                    String author =
                            annotations
                                    .get(BOMResourcesPlugin.ModelEannotationMetaSource_author);

                    if (!authorTxt.getText().equals(author)) {
                        return new RecordingCommand(
                                (TransactionalEditingDomain) getEditingDomain()) {
                            @Override
                            protected void doExecute() {
                                annotations
                                        .put(BOMResourcesPlugin.ModelEannotationMetaSource_author,
                                                authorTxt.getText());
                            }
                        };
                    }

                } else {
                    // We're going to have to create the EAnnotation for the
                    // metadata as this BOM was created before 3.3
                    return new RecordingCommand(
                            (TransactionalEditingDomain) getEditingDomain()) {
                        @Override
                        protected void doExecute() {

                            EAnnotation eAnnot =
                                    model.createEAnnotation(BOMResourcesPlugin.ModelEannotationMetaSource);
                            eAnnot.getDetails()
                                    .put(BOMResourcesPlugin.ModelEannotationMetaSource_author,
                                            authorTxt.getText());
                        }
                    };
                }
            }
        }
        return null;
    }

    @Override
    protected void doRefresh() {
        EObject input = getSemanticInput();
        if (input instanceof Model) {
            if (authorTxt != null && !authorTxt.isDisposed()) {
                EMap<String, String> details = getAnnotation((Model) input);

                if (details != null) {
                    updateText(authorTxt,
                            details.get(BOMResourcesPlugin.ModelEannotationMetaSource_author));

                    String dateCreated =
                            details.get(BOMResourcesPlugin.ModelEannotationMetaSource_datecreated);

                    if (dateCreated != null) {
                        try {
                            long timeStamp = Long.valueOf(dateCreated);
                            Date d1 = new Date(timeStamp);
                            DateFormat df = DateFormat.getInstance();
                            updateText(dateCreatedTxt, df.format(d1));
                        } catch (Exception e) {
                            updateText(dateCreatedTxt,
                                    Messages.ModelSection_unknownValue_label);
                        }
                    } else {
                        updateText(dateCreatedTxt,
                                Messages.ModelSection_unknownValue_label);
                    }
                } else {
                    // Author left blank and created date set to "Unknown" as no
                    // extended meta data set
                    updateText(dateCreatedTxt,
                            Messages.ModelSection_unknownValue_label);
                }
            }
        }
    }

    /**
     * Get the semantic model of the input of this section. If this is the
     * Diagram then the Model will be returned.
     * 
     * @return
     */
    private EObject getSemanticInput() {
        EObject input = getInput();
        if (input instanceof Diagram) {
            input = ((Diagram) input).getElement();
        }
        return input;
    }

    /**
     * Get the extended metadata from the model.
     * 
     * @param model
     * @return
     */
    private EMap<String, String> getAnnotation(Model model) {
        if (model != null) {
            EAnnotation annot =
                    model
                            .getEAnnotation(BOMResourcesPlugin.ModelEannotationMetaSource);
            if (annot != null) {
                return annot.getDetails();
            }
        }
        return null;
    }
}
