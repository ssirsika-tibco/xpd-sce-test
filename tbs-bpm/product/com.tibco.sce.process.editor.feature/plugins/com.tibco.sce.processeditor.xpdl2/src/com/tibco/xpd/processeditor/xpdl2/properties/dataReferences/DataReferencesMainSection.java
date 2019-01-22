/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.properties.dataReferences;

import java.util.Collection;
import java.util.Collections;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbenchPart;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessDataContextReferenceResolver;
import com.tibco.xpd.processeditor.xpdl2.properties.messages.Messages;
import com.tibco.xpd.ui.properties.AbstractFilteredTransactionalSection;
import com.tibco.xpd.ui.properties.ExpandableSectionStacker;
import com.tibco.xpd.ui.properties.ExpandableSectionStacker.ISectionContentCreator;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.resolvers.ProcessDataReferenceAndContexts;

/**
 * Property section to show the process data references made by the input
 * activity.
 * 
 * @author aallway
 * @since 25 Jun 2012
 */
public class DataReferencesMainSection extends
        AbstractFilteredTransactionalSection implements ISectionContentCreator {

    private static final String GROUP_BY_DATA_SECTION = "GroupByData"; //$NON-NLS-1$

    private static final String GROUP_BY_CONTEXT_SECTION = "GroupByContext"; //$NON-NLS-1$

    private ExpandableSectionStacker sectionStacker;

    private DataReferencesByDataSection groupByDataSection;

    private DataReferencesByContextSection groupByContextSection;

    private Collection<ProcessDataReferenceAndContexts> cachedDataReferences;

    /**
     * @param selectionFilterClass
     * @param sectionId
     */
    public DataReferencesMainSection() {
        super(Xpdl2Package.eINSTANCE.getNamedElement());

        sectionStacker =
                new ExpandableSectionStacker(getClass().getName()
                        + ".sectionStacker"); //$NON-NLS-1$

        sectionStacker.addSection(GROUP_BY_DATA_SECTION,
                Messages.DataReferencesSection_GroupedByData_title,
                100,
                true,
                true);

        sectionStacker.addSection(GROUP_BY_CONTEXT_SECTION,
                Messages.DataReferencesSection_GroupedByContext_title,
                100,
                true,
                true);
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.SashContributionSection#select(java.lang.Object)
     * 
     * @param toTest
     * @return
     */
    @Override
    public boolean select(Object toTest) {
        EObject baseSelectObject = getBaseSelectObject(toTest);

        if (baseSelectObject instanceof Activity
                || baseSelectObject instanceof Transition) {
            return true;
        }

        return false;
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
        Composite root = toolkit.createComposite(parent);
        root.setLayout(new FillLayout());

        sectionStacker.createExpandableSections(root, toolkit, this);

        return root;
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doRefresh()
     * 
     */
    @Override
    protected void doRefresh() {
        /*
         * Reget the data references for the activity.
         */
        EObject input = getInput();

        long startTime = System.nanoTime();

        if (input instanceof Activity) {
            ProcessDataContextReferenceResolver referenceResolver =
                    new ProcessDataContextReferenceResolver();

            cachedDataReferences =
                    referenceResolver.getDataReferences((Activity) input);

        } else if (input instanceof Transition) {
            ProcessDataContextReferenceResolver referenceResolver =
                    new ProcessDataContextReferenceResolver();

            cachedDataReferences =
                    referenceResolver.getDataReferences((Transition) input);

        } else {
            cachedDataReferences = Collections.emptySet();
        }

        boolean debug = false;
        if (debug) {
            long time = System.nanoTime() - startTime;

            System.out.println(String
                    .format("%s.getDataReferences(): Took %d.%09d seconds", //$NON-NLS-1$
                            this.getClass().getSimpleName(),
                            (time / 1000000000),
                            (time % 1000000000)));
        }

        if (groupByDataSection != null) {
            groupByDataSection.refreshReferences(cachedDataReferences);
        }

        if (groupByContextSection != null) {
            groupByContextSection.refreshReferences(cachedDataReferences);
        }
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doGetCommand(java.lang.Object)
     * 
     * @param obj
     * @return
     */
    @Override
    protected Command doGetCommand(Object obj) {
        return null;
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#setInput(org.eclipse.ui.IWorkbenchPart,
     *      org.eclipse.jface.viewers.ISelection)
     * 
     * @param part
     * @param selection
     */
    @Override
    public void setInput(IWorkbenchPart part, ISelection selection) {
        super.setInput(part, selection);

        if (groupByDataSection != null) {
            groupByDataSection.setInput(part, selection);
        }

        if (groupByContextSection != null) {
            groupByContextSection.setInput(part, selection);
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

        if (GROUP_BY_DATA_SECTION.equals(sectionId)) {
            groupByDataSection = new DataReferencesByDataSection();

            return groupByDataSection.createControls(container, toolkit);

        } else if (GROUP_BY_CONTEXT_SECTION.equals(sectionId)) {
            groupByContextSection = new DataReferencesByContextSection();

            return groupByContextSection.createControls(container, toolkit);
        }
        return toolkit.createComposite(container);
    }

    /**
     * @see com.tibco.xpd.ui.properties.ExpandableSectionStacker.ISectionContentCreator#expandableSectionStateChanged(java.lang.String)
     * 
     * @param sectionId
     */
    @Override
    public void expandableSectionStateChanged(String sectionId) {
    }

}
