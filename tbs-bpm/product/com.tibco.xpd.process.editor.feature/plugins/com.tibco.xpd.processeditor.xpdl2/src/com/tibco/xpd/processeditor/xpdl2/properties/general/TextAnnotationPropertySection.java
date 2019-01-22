/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.properties.general;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.tibco.xpd.processeditor.xpdl2.properties.messages.Messages;
import com.tibco.xpd.ui.properties.AbstractFilteredTransactionalSection;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.xpdl2.Artifact;
import com.tibco.xpd.xpdl2.ArtifactType;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * @author aallway
 * 
 */
public class TextAnnotationPropertySection extends
        AbstractFilteredTransactionalSection {
    private String instrumentationPrefixName;

    private Text nameText;

    public TextAnnotationPropertySection() {
        super(Xpdl2Package.eINSTANCE.getArtifact());
        instrumentationPrefixName = "TextAnnotation"; //$NON-NLS-1$
    }

    @Override
    protected Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {
        Composite root = toolkit.createComposite(parent);
        root.setLayout(new GridLayout(2, false));

        Label lab = toolkit.createLabel(root,
                Messages.TextAnnotationPropertySection_Text_label, SWT.NONE);
        lab.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING));

        nameText = toolkit.createText(root, "", SWT.MULTI | SWT.WRAP //$NON-NLS-1$
                | SWT.V_SCROLL, instrumentationPrefixName);
        nameText.setData("name", "textNote"); //$NON-NLS-1$ //$NON-NLS-2$
        nameText.setLayoutData(new GridData(GridData.FILL_BOTH));
        manageControl(nameText);

        return root;
    }

    @Override
    protected Command doGetCommand(Object obj) {
        CompoundCommand cmd = null;

        Artifact artifact = getArtifact();

        EditingDomain editingDomain = getEditingDomain();

        if (artifact != null && editingDomain != null) {
            // Update the name
            if (obj == nameText) {
                cmd = new CompoundCommand();
                cmd
                        .setLabel(Messages.TextAnnotationPropertySection_SetText_menu);
                cmd.append(SetCommand.create(editingDomain, artifact,
                        Xpdl2Package.eINSTANCE.getArtifact_TextAnnotation(),
                        nameText.getText()));
            }
        }

        return cmd;
    }

    @Override
    protected void doRefresh() {
        // If controls have been disposed then unregister adapter
        if (!nameText.isDisposed()) {
            Artifact artifact = getArtifact();

            if (artifact != null) {
                // Update the name
                updateText(nameText, artifact.getTextAnnotation());
            }
        }
    }

    /**
     * Get the xpdl2 Artifact element for the current input
     * 
     * @return Artifact input or null on error.
     */
    public Artifact getArtifact() {
        Object o = getInput();
        if (o instanceof Artifact) {
            return (Artifact) o;
        }
        return null;
    }

    @Override
    public boolean select(Object toTest) {
        if (super.select(toTest)) {
            Artifact art = (Artifact) getBaseSelectObject(toTest);

            if (ArtifactType.ANNOTATION_LITERAL.equals(art.getArtifactType())) {
                return true;
            }
        }
        return false;
    }
}
