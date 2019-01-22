/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.properties.general;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

import com.tibco.xpd.processeditor.xpdl2.properties.messages.Messages;
import com.tibco.xpd.processeditor.xpdl2.properties.util.AbstractNamedDiagramObjectSection;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.xpdl2.Artifact;
import com.tibco.xpd.xpdl2.ArtifactType;
import com.tibco.xpd.xpdl2.NamedElement;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * @author aallway
 * 
 */
public class GroupPropertySection extends AbstractNamedDiagramObjectSection {

    protected CLabel nameLabel;

    protected Text name;

    public GroupPropertySection() {
        super(Xpdl2Package.eINSTANCE.getArtifact());
        instrumentationPrefixName = "Group"; //$NON-NLS-1$
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.util.AbstractNamedDiagramObjectSection#objectTypeCreateControls(org.eclipse.swt.widgets.Composite,
     *      com.tibco.xpd.ui.properties.XpdFormToolkit)
     * 
     * @param parent
     * @param toolkit
     */
    @Override
    protected void objectTypeCreateControls(Composite parent,
            XpdFormToolkit toolkit) {
        // Groups only have a name control at the moment.
        nameLabel =
                toolkit
                        .createCLabel(parent,
                                com.tibco.xpd.processeditor.xpdl2.internal.Messages.NamedElementSection_nameLabel);
        nameLabel
                .setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));
        name = toolkit.createText(parent, "", "nameElement_name_text"); //$NON-NLS-1$ //$NON-NLS-2$
        name.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
        manageControlUpdateOnDeactivate(name);
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.util.AbstractNamedDiagramObjectSection#objectTypeGetCommand(java.lang.Object)
     * 
     * @param obj
     * @return
     */
    @Override
    protected Command objectTypeGetCommand(Object obj) {
        Command cmd = null;
        EObject input = getInput();
        if (input instanceof NamedElement) {
            NamedElement named = (NamedElement) input;
            EditingDomain ed = getEditingDomain();
            if (obj == name) {
                cmd =
                        SetCommand.create(ed, named, Xpdl2Package.eINSTANCE
                                .getNamedElement_Name(), name.getText());
            }
        }
        return cmd;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.util.AbstractNamedDiagramObjectSection#objectTypeGetDescriptor()
     * 
     * @return
     */
    @Override
    protected String objectTypeGetDescriptor() {
        return Messages.GroupPropertySection_GroupTypeName_label;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.util.AbstractNamedDiagramObjectSection#objectTypeRefresh()
     * 
     */
    @Override
    protected void objectTypeRefresh() {
        EObject input = getInput();
        if (input instanceof NamedElement) {
            NamedElement named = (NamedElement) input;
            if (name != null && !name.isDisposed()) {
                updateText(name, named.getName());
            }
        }
    }

    @Override
    public boolean select(Object toTest) {
        // Super will test that it's an Artifact for us.
        if (super.select(toTest)) {
            // It's an artifact - checks its a Group type.
            Artifact art = (Artifact) getBaseSelectObject(toTest);

            if (ArtifactType.GROUP_LITERAL.equals(art.getArtifactType())) {
                return true;
            }
        }
        return false;
    }

}
