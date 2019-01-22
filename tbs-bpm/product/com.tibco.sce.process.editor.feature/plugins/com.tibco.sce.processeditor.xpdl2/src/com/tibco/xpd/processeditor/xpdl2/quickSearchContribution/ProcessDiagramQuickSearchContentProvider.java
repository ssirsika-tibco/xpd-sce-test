/**
 * ProcessDiagramQuickSearchContentProvider.java
 *
 * 
 *
 * @author aallway
 * Copyright (c) TIBCO Software Inc 2004-2008. All rights reserved.
 */
package com.tibco.xpd.processeditor.xpdl2.quickSearchContribution;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.ui.IWorkbenchPartReference;

import com.tibco.xpd.processeditor.xpdl2.AbstractProcessDiagramEditor;
import com.tibco.xpd.processeditor.xpdl2.ProcessEditorInput;
import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.resources.ui.api.quicksearch.AbstractQuickSearchContentProvider;
import com.tibco.xpd.resources.ui.api.quicksearch.QuickSearchPopupCategory;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Artifact;
import com.tibco.xpd.xpdl2.Association;
import com.tibco.xpd.xpdl2.MessageFlow;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

class ProcessDiagramQuickSearchContentProvider extends
        AbstractQuickSearchContentProvider {

    private AbstractProcessDiagramEditor editor;

    private Process process;

    private static final String OBJECTS_QS_CATEGORY_ID =
            "process.diagram.objects"; //$NON-NLS-1$

    private static final String TASKS_QS_CATEGORY_ID =
            "process.diagram.objects.tasks"; //$NON-NLS-1$

    private static final String EVENTS_QS_CATEGORY_ID =
            "process.diagram.objects.events"; //$NON-NLS-1$

    private static final String GATEWAYS_QS_CATEGORY_ID =
            "process.diagram.objects.gateways"; //$NON-NLS-1$

    private static final String ARTIFACTS_QS_CATEGORY_ID =
            "process.diagram.objects.artifacts"; //$NON-NLS-1$

    private static final String CONNECTIONS_QS_CATEGORY_ID =
            "process.diagram.connections"; //$NON-NLS-1$

    private static final String SEQFLOW_QS_CATEGORY_ID =
            "process.diagram.connections.seqFlow"; //$NON-NLS-1$

    private static final String ASSOC_QS_CATEGORY_ID =
            "process.diagram.connections.association"; //$NON-NLS-1$

    private static final String MSGFLOW_QS_CATEGORY_ID =
            "process.diagram.connections.msgFlow"; //$NON-NLS-1$

    private List<QuickSearchPopupCategory> searchCategories;

    /**
     * @param partRef
     */
    public ProcessDiagramQuickSearchContentProvider(
            IWorkbenchPartReference partRef) {
        super(partRef);

        editor = (AbstractProcessDiagramEditor) partRef.getPart(false);

        ProcessEditorInput input = (ProcessEditorInput) editor.getEditorInput();
        process = input.getProcess();

        searchCategories = new ArrayList<QuickSearchPopupCategory>();

        QuickSearchPopupCategory diagramObjectsCat =
                new QuickSearchPopupCategory(
                        OBJECTS_QS_CATEGORY_ID,
                        Messages.ProcessDiagramQuickSearchContentProvider_DiagramObjects_menu);
        searchCategories.add(diagramObjectsCat);

        diagramObjectsCat.addChild(new QuickSearchPopupCategory(
                TASKS_QS_CATEGORY_ID,
                Messages.ProcessDiagramQuickSearchContentProvider_Tasks_menu));
        diagramObjectsCat.addChild(new QuickSearchPopupCategory(
                EVENTS_QS_CATEGORY_ID,
                Messages.ProcessDiagramQuickSearchContentProvider_Events_menu));
        diagramObjectsCat
                .addChild(new QuickSearchPopupCategory(
                        GATEWAYS_QS_CATEGORY_ID,
                        Messages.ProcessDiagramQuickSearchContentProvider_Gateways_menu));
        diagramObjectsCat
                .addChild(new QuickSearchPopupCategory(
                        ARTIFACTS_QS_CATEGORY_ID,
                        Messages.ProcessDiagramQuickSearchContentProvider_Artifacts_menu));

        QuickSearchPopupCategory connectionsCat =
                new QuickSearchPopupCategory(
                        CONNECTIONS_QS_CATEGORY_ID,
                        Messages.ProcessDiagramQuickSearchContentProvider_DIagramConnections_menu);
        searchCategories.add(connectionsCat);

        connectionsCat
                .addChild(new QuickSearchPopupCategory(
                        SEQFLOW_QS_CATEGORY_ID,
                        Messages.ProcessDiagramQuickSearchContentProvider_SeqFlows_menu));
        connectionsCat
                .addChild(new QuickSearchPopupCategory(
                        MSGFLOW_QS_CATEGORY_ID,
                        Messages.ProcessDiagramQuickSearchContentProvider_MsgFlows_menu));
        connectionsCat
                .addChild(new QuickSearchPopupCategory(
                        ASSOC_QS_CATEGORY_ID,
                        Messages.ProcessDiagramQuickSearchContentProvider_Associations_menu));

    }

    @Override
    public void dispose() {
    }

    @Override
    public Collection<QuickSearchPopupCategory> getCategories() {
        return searchCategories;
    }

    @Override
    public Collection<?> getElements() {
        return getElements(Collections.EMPTY_LIST);
    }

    @Override
    public Collection<?> getElements(
            Collection<QuickSearchPopupCategory> categories) {
        boolean wantTasks = isCategoryEnabled(categories, TASKS_QS_CATEGORY_ID);
        boolean wantEvents =
                isCategoryEnabled(categories, EVENTS_QS_CATEGORY_ID);
        boolean wantGateways =
                isCategoryEnabled(categories, GATEWAYS_QS_CATEGORY_ID);
        boolean wantArtifacts =
                isCategoryEnabled(categories, ARTIFACTS_QS_CATEGORY_ID);

        boolean wantSeqFlow =
                isCategoryEnabled(categories, SEQFLOW_QS_CATEGORY_ID);
        boolean wantMsgFlow =
                isCategoryEnabled(categories, MSGFLOW_QS_CATEGORY_ID);
        boolean wantAssocs =
                isCategoryEnabled(categories, ASSOC_QS_CATEGORY_ID);

        List<Object> elements = new ArrayList<Object>();

        if (wantTasks || wantEvents || wantGateways) {
            Collection<Activity> activities =
                    Xpdl2ModelUtil.getAllActivitiesInProc(process);

            for (Activity act : activities) {

                if (wantTasks
                        && ProcessEditorQuickSearchContribution.isTask(act)) {
                    elements.add(act);

                } else if (wantEvents
                        && ProcessEditorQuickSearchContribution.isEvent(act)) {
                    elements.add(act);

                } else if (wantGateways
                        && ProcessEditorQuickSearchContribution.isGateway(act)) {
                    elements.add(act);

                }
            }

        }

        if (wantArtifacts) {
            Collection<Artifact> artifacts =
                    Xpdl2ModelUtil.getAllArtifactsInProcess(process);

            for (Artifact art : artifacts) {
                elements.add(art);
            }
        }

        if (wantSeqFlow) {
            Collection<Transition> flows =
                    Xpdl2ModelUtil.getAllTransitionsInProc(process);

            elements.addAll(flows);
        }

        if (wantMsgFlow) {
            Collection<MessageFlow> flows =
                    Xpdl2ModelUtil.getAllMessageFlowsInProc(process);

            elements.addAll(flows);
        }

        if (wantAssocs) {
            Collection<Association> assocs =
                    Xpdl2ModelUtil.getAllAssociationsInProc(process);

            elements.addAll(assocs);
        }

        return elements;
    }


}