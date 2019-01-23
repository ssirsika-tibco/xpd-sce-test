/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.custom.internal.propertysection;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.diagram.core.util.ViewType;
import org.eclipse.gmf.runtime.diagram.core.util.ViewUtil;
import org.eclipse.gmf.runtime.notation.DescriptionStyle;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.viewers.IFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Comment;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.ProfileApplication;
import org.eclipse.uml2.uml.UMLFactory;

import com.tibco.xpd.bom.modeler.custom.Activator;
import com.tibco.xpd.bom.modeler.custom.internal.Messages;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.AssociationCardinalityAtSourceLabelEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.AssociationCardinalityAtTargetLabelEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.AssociationSourceLabelEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.AssociationTargetLabelEditPart;
import com.tibco.xpd.ui.properties.AbstractTransactionalSection;
import com.tibco.xpd.ui.properties.XpdFormToolkit;

/**
 * Property section for the Description tab.
 * 
 * @author wzurek
 * 
 */
public class DescriptionTabSection extends AbstractTransactionalSection
        implements IFilter {

    private static final String DESCRIPTION_TYPE = "Description"; //$NON-NLS-1$

    private Text desc;

    @Override
    protected Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {
        Composite root = toolkit.createComposite(parent);
        root.setLayout(new GridLayout(1, false));

        Label label =
                toolkit.createLabel(root,
                        Messages.DescriptionPropertySection_desc_label);
        label.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        desc = toolkit.createText(root, "", //$NON-NLS-1$
                SWT.WRAP | SWT.MULTI | SWT.V_SCROLL,
                Messages.DescriptionPropertySection_desc_text_label);
        GridData gd = new GridData(GridData.FILL_BOTH);
        gd.widthHint = 10;
        gd.heightHint = 10;
        desc.setLayoutData(gd);
        manageControlUpdateOnDeactivate(desc);
        return root;
    }

    @Override
    protected Command doGetCommand(Object obj) {
        if (obj == desc) {
            EObject input = getInput();
            if (input instanceof Element) {
                final Element elem = (Element) input;
                try {
                    String currDescription = getCurrentDescription(elem);

                    if ((currDescription == null && desc.getText().length() > 0)
                            || (currDescription != null && !currDescription
                                    .equals(desc.getText()))) {

                        // Description has changed
                        return getNewDescriptionCmd((TransactionalEditingDomain) getEditingDomain(),
                                elem,
                                desc.getText());
                    }

                } catch (InterruptedException e) {
                    Activator
                            .getDefault()
                            .getLogger()
                            .error(e,
                                    Messages.DescriptionPropertySection_errorInSettingDescription_shortdesc);
                }
            } else if (input instanceof View) {
                DescriptionStyle style = getDescriptionStyle((View) input);
                if (style != null) {
                    return SetCommand.create(getEditingDomain(),
                            style,
                            NotationPackage.eINSTANCE
                                    .getDescriptionStyle_Description(),
                            desc.getText());
                }
            }
        }
        return null;
    }

    /**
     * Get the command to change the description.
     * 
     * @param ed
     *            transactional editing domain
     * @param input
     *            element being updated
     * @param text
     *            new description
     * @return {@link Command} or <code>null</code> if the description cannot be
     *         changed.
     */
    private Command getNewDescriptionCmd(TransactionalEditingDomain ed,
            final Element input, final String text) {
        if (ed != null && input != null) {

            return new RecordingCommand(ed) {
                @Override
                protected void doExecute() {
                    if (text.length() > 0) {
                        if (!input.getOwnedComments().isEmpty()) {
                            input.getOwnedComments().get(0).setBody(desc
                                    .getText());
                        } else {
                            Comment comment =
                                    UMLFactory.eINSTANCE.createComment();
                            comment.setBody(desc.getText());
                            input.getOwnedComments().add(comment);
                        }
                    } else {
                        // Clear all comments
                        input.getOwnedComments().clear();
                    }
                }
            };
        }
        return null;
    }

    /**
     * Get the current set description if any. NOTE: This runs in a read-only
     * transaction.
     * 
     * @param input
     *            Element
     * @return current description or <code>null</code> if none set.
     * @throws InterruptedException
     */
    private String getCurrentDescription(final Element input)
            throws InterruptedException {
        final String[] desc = new String[] { null };

        if (input != null) {
            ((TransactionalEditingDomain) getEditingDomain())
                    .runExclusive(new Runnable() {

                        public void run() {
                            if (!input.getOwnedComments().isEmpty()) {
                                desc[0] =
                                        input.getOwnedComments().get(0)
                                                .getBody();
                            }
                        }

                    });
        }

        return desc[0];
    }

    @Override
    protected void doRefresh() {
        EObject eo = getInput();
        if (eo instanceof Element) {
            Element elem = (Element) eo;
            if (!elem.getOwnedComments().isEmpty()) {
                Comment comment = elem.getOwnedComments().get(0);
                String body = comment.getBody();
                desc.setText(body == null ? "" : body); //$NON-NLS-1$
            } else {
                desc.setText(""); //$NON-NLS-1$
            }
        } else if (eo instanceof View) {
            DescriptionStyle style = getDescriptionStyle((View) eo);
            if (style != null) {
                desc.setText(style.getDescription());
            }
        } else {
            desc.setText(""); //$NON-NLS-1$
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.viewers.IFilter#select(java.lang.Object)
     */
    public boolean select(Object toTest) {
        EObject eo = resollveInput(toTest);
        return (eo instanceof NamedElement || eo instanceof Generalization)
                && !(eo instanceof ProfileApplication);
    }

    @Override
    protected EObject resollveInput(Object object) {
        if (object instanceof IAdaptable) {
            EObject eo =
                    (EObject) ((IAdaptable) object).getAdapter(EObject.class);

            if (eo instanceof View) {
                View view = (View) eo;
                if (DESCRIPTION_TYPE.equals(view.getType())) {
                    view = ViewUtil.getViewContainer(view);
                }

                String type = view.getType();
                if (type != null
                        && (type.equals(ViewType.NOTE) || type
                                .equals(ViewType.TEXT))) {
                    eo = view;
                }
            } else {
                // If association source or target role then select the Property
                // as the input
                if (object instanceof AssociationSourceLabelEditPart
                        || object instanceof AssociationCardinalityAtSourceLabelEditPart) {

                    // Association source role
                    if (eo instanceof Association
                            && ((Association) eo).getMemberEnds().get(0) != null) {
                        eo = ((Association) eo).getMemberEnds().get(0);
                    }
                } else if (object instanceof AssociationTargetLabelEditPart
                        || object instanceof AssociationCardinalityAtTargetLabelEditPart) {

                    // association target role
                    if (eo instanceof Association
                            && ((Association) eo).getMemberEnds().get(1) != null) {
                        eo = ((Association) eo).getMemberEnds().get(1);
                    }
                }
            }
            return eo;
        }
        return super.resollveInput(object);
    }

    /**
     * Get the {@link DescriptionStyle} of the given view.
     * 
     * @param view
     * @return style or <code>null</code> if not found.
     */
    private DescriptionStyle getDescriptionStyle(View view) {
        if (view != null) {
            return (DescriptionStyle) view.getStyle(NotationPackage.eINSTANCE
                    .getDescriptionStyle());
        }
        return null;
    }
}
