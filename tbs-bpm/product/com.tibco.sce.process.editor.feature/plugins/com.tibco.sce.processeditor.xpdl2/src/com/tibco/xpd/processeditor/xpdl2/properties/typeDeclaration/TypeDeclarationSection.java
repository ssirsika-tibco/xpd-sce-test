/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.properties.typeDeclaration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.groups.TypeDeclarationGroup;
import com.tibco.xpd.ui.properties.AbstractTransactionalSection;
import com.tibco.xpd.ui.properties.XpdFormToolkit;

/**
 * TypeDeclarationSection
 * 
 * 
 * @author bharge
 * @since 3.3 (14 Oct 2009)
 */
public class TypeDeclarationSection extends AbstractTransactionalSection {
    TypeDeclarationTable typeDeclarationTable;

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.ui.properties.AbstractXpdSection#doCreateControls(org.eclipse
     * .swt.widgets.Composite, com.tibco.xpd.ui.properties.XpdFormToolkit)
     */
    @Override
    protected Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {
        Composite root = toolkit.createComposite(parent);
        root.setLayout(new GridLayout());

        GridData data = new GridData(SWT.FILL, SWT.TOP, true, false);
        data.horizontalIndent = 5;

        typeDeclarationTable =
                new TypeDeclarationTable(root, toolkit, getEditingDomain());
        typeDeclarationTable.setLayoutData(new GridData(SWT.FILL, SWT.FILL,
                true, true));

        return root;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.ui.properties.AbstractXpdSection#doGetCommand(java.lang
     * .Object)
     */
    @Override
    protected Command doGetCommand(Object obj) {
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doRefresh()
     */
    @Override
    protected void doRefresh() {
        EObject input = getInput();
        if (typeDeclarationTable != null
                && typeDeclarationTable.getViewer() != null) {
            typeDeclarationTable.getViewer().cancelEditing();
            typeDeclarationTable.getViewer().setInput(input);
            typeDeclarationTable.reloadDeclaredTypes();
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.ui.properties.AbstractTransactionalSection#setInput(java
     * .util.Collection)
     */
    @Override
    public void setInput(Collection<?> items) {
        List<EObject> inputList = new ArrayList<EObject>();
        for (Object obj : items) {
            if (obj instanceof TypeDeclarationGroup) {
                TypeDeclarationGroup typeDeclarationGroup =
                        (TypeDeclarationGroup) obj;
                if (typeDeclarationGroup.getParent() instanceof EObject) {
                    inputList.add((EObject) typeDeclarationGroup.getParent());
                }
            }
        }
        super.setInput(inputList);
    }
}
