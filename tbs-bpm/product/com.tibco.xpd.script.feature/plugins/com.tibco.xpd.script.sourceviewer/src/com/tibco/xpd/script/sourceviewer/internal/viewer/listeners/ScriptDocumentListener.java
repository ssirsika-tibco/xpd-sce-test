package com.tibco.xpd.script.sourceviewer.internal.viewer.listeners;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.text.DocumentEvent;
import org.eclipse.jface.text.IDocumentListener;
import org.eclipse.ui.progress.UIJob;

import com.tibco.xpd.script.sourceviewer.internal.util.Consts;
import com.tibco.xpd.script.sourceviewer.internal.viewer.SaveUtil;
import com.tibco.xpd.script.sourceviewer.viewer.ScriptSourceViewer;

public class ScriptDocumentListener implements IDocumentListener {
    private ScriptSourceViewer scriptSourceViewer;

    private CompilationJob compilationJob;

    private SaveJob saveJob;

    public ScriptDocumentListener(ScriptSourceViewer scriptSourceViewer) {
        this.scriptSourceViewer = scriptSourceViewer;
        compilationJob = new CompilationJob("Compile Script"); //$NON-NLS-1$
        compilationJob.setPriority(Job.LONG);
        saveJob = new SaveJob("Save Script"); //$NON-NLS-1$
        saveJob.setPriority(Job.LONG);
    }

    @Override
    public void documentAboutToBeChanged(DocumentEvent event) {
        // TODO Auto-generated method stub
    }

    @Override
    public void documentChanged(DocumentEvent event) {
        if (compilationJob.cancel()) {
            compilationJob.schedule(1000);
        }
        if (saveJob.cancel()) {
            // saveJob.setDisplay(jScriptSourceViewer.getControl().getDisplay());
            saveJob.schedule(1000);
        }
    }

    public void stop() {
        if (compilationJob != null) {
            compilationJob.cancel();
            compilationJob = null;
        }

        if (saveJob != null) {
            saveJob.cancel();
            saveJob = null;
        }
    }

    private class CompilationJob extends Job {

        public CompilationJob(String name) {
            super(name);
            // TODO Auto-generated constructor stub
        }

        @Override
        protected IStatus run(IProgressMonitor monitor) {
            // TODO Auto-generated method stub
            compileScript();
            return Status.OK_STATUS;
        }

        @Override
        public boolean belongsTo(Object family) {
            return family == Consts.SCRIPT_JOB_FAMILY;
        }

    }

    private class SaveJob extends UIJob {

        public SaveJob(String name) {
            super(name);
        }

        @Override
        public IStatus runInUIThread(IProgressMonitor monitor) {
            // TODO Auto-generated method stub
            saveScript();
            return Status.OK_STATUS;
        }

        @Override
        public boolean belongsTo(Object family) {
            return family == Consts.SCRIPT_JOB_FAMILY;
        }
    }

    private void compileScript() {
        SaveUtil.compileScript(scriptSourceViewer);
    }

    private void saveScript() {
        SaveUtil.saveScript(scriptSourceViewer);
    }
}
