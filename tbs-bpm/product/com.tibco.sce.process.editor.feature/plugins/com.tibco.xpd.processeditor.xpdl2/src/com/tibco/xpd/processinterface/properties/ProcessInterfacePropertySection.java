/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.processinterface.properties;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.ui.properties.AbstractFilteredTransactionalSection;
import com.tibco.xpd.ui.properties.ExpandableSectionStacker;
import com.tibco.xpd.ui.properties.ExpandableSectionStacker.ISectionContentCreator;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.ui.util.NameUtil;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.edit.ui.properties.general.ObjectReferencesSection;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * @author rsomayaj
 * 
 * 
 */
public class ProcessInterfacePropertySection extends
        AbstractFilteredTransactionalSection implements ISectionContentCreator {

    private ExpandableSectionStacker expandableSectionStacker;

    private Control expandablesContainer;

    private ObjectReferencesSection referencesSection = null;

    private final static String REFERENCES_SECTION = "referencesSection"; //$NON-NLS-1$

    private static final Logger LOG = XpdResourcesPlugin.getDefault()
            .getLogger();

    /**
     * Process properties
     */
    public ProcessInterfacePropertySection() {
        super(XpdExtensionPackage.eINSTANCE.getProcessInterface());
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.ui.properties.BaseXpdSection#doCreateControls(org.eclipse
     * .swt.widgets.Composite, com.tibco.xpd.ui.properties.XpdFormToolkit)
     */
    @Override
    public Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {

        Composite root = toolkit.createComposite(parent);
        GridLayout gl = new GridLayout(2, false);
        gl.marginLeft = gl.marginWidth + 3;
        gl.marginWidth = 0;
        root.setLayout(gl);

        if (ContainerType.PROPERTYVIEW == getSectionContainerType()) {

            /* Expandable section header preferences. */
            String sectPrefId =
                    getSectionContainerType()
                            + "." + "ExpandableServiceProcessInterfaceSection"; //$NON-NLS-1$ //$NON-NLS-2$

            expandableSectionStacker = new ExpandableSectionStacker(sectPrefId);

            addExpandableSections(expandableSectionStacker);

            expandablesContainer =
                    expandableSectionStacker.createExpandableSections(root,
                            toolkit,
                            this);

            GridData gd =
                    new GridData(GridData.FILL_BOTH | GridData.GRAB_VERTICAL
                            | GridData.GRAB_HORIZONTAL);
            expandablesContainer.setLayoutData(gd);
        }

        return root;

    }

    /**
     * @param expandableSectionStacker
     */
    protected void addExpandableSections(
            ExpandableSectionStacker expandableSectionStacker) {

        expandableSectionStacker.addSection(REFERENCES_SECTION,
                Messages.ProcessPropertySection_ReferencesSection_title,
                40,
                true,
                true);

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.ui.properties.BaseXpdSection#doRefresh()
     */
    @Override
    public void doRefresh() {

        ProcessInterface processInterface = (ProcessInterface) getInput();
        if (processInterface != null) {

            List<?> processes = Collections.EMPTY_LIST;
            try {
                processes =
                        ProcessInterfaceUtil
                                .getProcessInterfaceObjectRefDependents(processInterface);
            } catch (CoreException e) {
                LOG.error(e);
            }
            if (referencesSection != null) {
                referencesSection.setContent(processes);
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.ui.properties.BaseXpdSection#doGetCommand(java.lang.Object)
     */
    @Override
    public Command doGetCommand(Object obj) {
        return null;
    }

    @Override
    public void setInput(Collection<?> items) {
        super.setInput(items);

        ProcessInterface processInterface = (ProcessInterface) getInput();
        if (processInterface != null && processInterface.eContainer() == null) {
            EObject container = getInputContainer();
            if (container instanceof Package) {
                String baseName =
                        ((Package) container).getName()
                                + "-" + Messages.ProcessInterfacePropertySection_Interface_value; //$NON-NLS-1$
                String finalName = baseName;
                int idx = 1;
                while (Xpdl2ModelUtil
                        .getDuplicateDisplayProcessInterface(container,
                                processInterface,
                                finalName) != null
                        || Xpdl2ModelUtil
                                .getDuplicateProcessInterface(container,
                                        processInterface,
                                        NameUtil.getInternalName(finalName,
                                                false)) != null) {
                    idx++;
                    finalName = baseName + idx;
                }
                processInterface.setName(NameUtil.getInternalName(finalName,
                        false));
                Xpdl2ModelUtil.setOtherAttribute(processInterface,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_DisplayName(),
                        finalName);
            }
        }

    }

    /**
     * @see com.tibco.xpd.ui.properties.ExpandableSectionStacker.ISectionContentCreator#createExpandableSectionContent(java.lang.String,
     *      org.eclipse.swt.widgets.Composite,
     *      com.tibco.xpd.ui.properties.XpdFormToolkit)
     * 
     * @param sectionId
     * @param container
     * @param toolkit
     * @return
     */
    @Override
    public Control createExpandableSectionContent(String sectionId,
            Composite container, XpdFormToolkit toolkit) {

        if (REFERENCES_SECTION.equals(sectionId)) {

            return createReferencesControls(toolkit, container);
        }
        return null;
    }

    /**
     * @param toolkit
     * @param container
     * @return
     */
    private Control createReferencesControls(XpdFormToolkit toolkit,
            Composite container) {

        Composite parent = toolkit.createComposite(container);
        GridLayout gridLayout = new GridLayout(1, false);
        parent.setLayout(gridLayout);

        referencesSection = new ObjectReferencesSection();

        Composite comp = referencesSection.createControls(parent, toolkit);
        comp.setLayoutData(new GridData(GridData.FILL_BOTH));

        return parent;
    }

    /**
     * @see com.tibco.xpd.ui.properties.ExpandableSectionStacker.ISectionContentCreator#expandableSectionStateChanged(java.lang.String)
     * 
     * @param sectionId
     */
    @Override
    public void expandableSectionStateChanged(String sectionId) {

    }

    /**
     * This section (without being subclassed) is for base Process Interface
     * only.
     * 
     * @see com.tibco.xpd.ui.properties.AbstractFilteredTransactionalSection#select(java.lang.Object)
     * 
     * @param toTest
     * @return
     */
    @Override
    public boolean select(Object toTest) {

        EObject baseSelectObject = getBaseSelectObject(toTest);
        if (baseSelectObject instanceof ProcessInterface) {

            ProcessInterface processInterface =
                    (ProcessInterface) baseSelectObject;
            /*
             * This section (without being subclassed) is for base Process
             * Interface only.
             */
            if (Xpdl2ModelUtil.isProcessInterface(processInterface)) {

                return true;
            }
        }
        return false;
    }
}
