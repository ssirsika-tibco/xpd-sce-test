/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2;

import org.eclipse.jface.viewers.IFilter;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;

import com.tibco.xpd.processwidget.ProcessWidget.ProcessWidgetType;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Process diagram editor (all the pre v3.2 stuff abstracted out to
 * AbstractProcessDiagramEditor so that it can be shared with task model editor.
 * 
 * @author aallway
 * @since 3.2
 */
public class ProcessDiagramEditor extends AbstractProcessDiagramEditor {

    public static String EDITOR_ID = "com.tibco.xpd.processeditor.xpdl2.editor";//$NON-NLS-1$

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.processeditor.xpdl2.AbstractProcessDiagramEditor#init(org
     * .eclipse.ui.IEditorSite, org.eclipse.ui.IEditorInput)
     */
    @Override
    public void init(IEditorSite site, IEditorInput input)
            throws PartInitException {
        super.init(site, input);

        if (input instanceof ProcessEditorInput) {

            ProcessEditorInput pei = (ProcessEditorInput) input;
            if (null != pei.getProcess()
                    && Xpdl2ModelUtil.isCaseService(pei.getProcess())) {

                setTitleImage(Xpdl2ProcessEditorPlugin
                        .getDefault()
                        .getImageRegistry()
                        .get(ProcessEditorConstants.IMG_CASE_SERVICE_PAGEFLOW_PROCESS));
            } else if (pei.getProcess() != null
                    && Xpdl2ModelUtil.isPageflowBusinessService(pei
                            .getProcess())) {
                setTitleImage(Xpdl2ProcessEditorPlugin
                        .getDefault()
                        .getImageRegistry()
                        .get(ProcessEditorConstants.IMG_BUSINESS_SERVICE_PAGEFLOW_PROCESS));
            } else if (pei.getProcess() != null
                    && Xpdl2ModelUtil.isPageflow(pei.getProcess())) {
                setTitleImage(Xpdl2ProcessEditorPlugin.getDefault()
                        .getImageRegistry()
                        .get(ProcessEditorConstants.IMG_PAGEFLOW_PROCESS));
            } else if (null != pei.getProcess()
                    && Xpdl2ModelUtil.isServiceProcess(pei.getProcess())) {

                setTitleImage(Xpdl2ProcessEditorPlugin.getDefault()
                        .getImageRegistry()
                        .get(ProcessEditorConstants.IMG_SERVICE_PROCESS));
            } else {
                setTitleImage(Xpdl2ProcessEditorPlugin.getDefault()
                        .getImageRegistry()
                        .get(ProcessEditorConstants.IMG_PROCESS));
            }
        }
        return;
    }

    @Override
    protected String getEditorId() {
        return EDITOR_ID;
    }

    @Override
    protected ProcessEditorInput adaptToProcessEditorInput(
            IEditorSite editorSite, IEditorInput editorInput) {
        if (editorInput instanceof ProcessEditorInput) {
            return (ProcessEditorInput) editorInput;
        }

        throw new IllegalStateException(
                "Unexpected EditorInput: " + editorInput); //$NON-NLS-1$

    }

    @Override
    protected ProcessWidgetType getProcessWidgetType() {

        Process input = ((ProcessEditorInput) getEditorInput()).getProcess();
        if (input != null) {

            if (Xpdl2ModelUtil.isCaseService(input)) {

                return ProcessWidgetType.CASE_SERVICE;
            } else if (Xpdl2ModelUtil.isPageflowBusinessService(input)) {

                return ProcessWidgetType.BUSINESS_SERVICE;
            } else if (Xpdl2ModelUtil.isPageflow(input)) {

                return ProcessWidgetType.PAGEFLOW_PROCESS;
            } else if (Xpdl2ModelUtil.isServiceProcess(input)) {

                return ProcessWidgetType.SERVICE_PROCESS;
            }
        }
        return ProcessWidgetType.BPMN_PROCESS;
    }

    public static class BpmnProcessFilter implements IFilter {

        @Override
        public boolean select(Object toTest) {
            if (toTest instanceof Process) {
                return BpmnProcessPropertyTester
                        .isBpmnProcess((Process) toTest);
            }
            return false;
        }
    }

}
