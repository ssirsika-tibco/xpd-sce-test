package com.tibco.xpd.n2.cds.internal.customfeature;

import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.NullChange;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;

import com.tibco.xpd.bom.gen.internal.BOMGenDeleteParticipant;
import com.tibco.xpd.n2.cds.internal.Messages;
import com.tibco.xpd.n2.daa.internal.packager.DAAExportUtils;

/**
 * Delete participant to clear jar cache
 * 
 * @author jarciuch
 * @since 5 Oct 2012
 */
public class BOMCacheDeleteParticipant extends BOMGenDeleteParticipant {

    @Override
    public String getName() {
        return Messages.BOMCacheDeleteParticipant_DeleteParticipant_name;
    }

    @Override
    public Change createChange(IProgressMonitor pm) throws CoreException,
            OperationCanceledException {
        return new CleanCacheChange(bomFilesToClean);
    }

    /**
     * The refactor change that will clean all cached JARs of the BOM resource
     * being deleted.
     * 
     * @author jarciuch
     * 
     */
    private static class CleanCacheChange extends Change {

        private List<IFile> bomsToClean;

        public CleanCacheChange(List<IFile> bomFilesToClean) {
            this.bomsToClean = bomFilesToClean;
        }

        @Override
        public Object getModifiedElement() {
            return null;
        }

        @Override
        public String getName() {
            return Messages.BOMCacheDeleteParticipant_DeleteParticipantChange_name;
        }

        @Override
        public void initializeValidationData(IProgressMonitor pm) {
        }

        @Override
        public RefactoringStatus isValid(IProgressMonitor pm)
                throws CoreException, OperationCanceledException {
            return new RefactoringStatus();
        }

        @Override
        public Change perform(IProgressMonitor pm) throws CoreException {
            if (bomsToClean != null) {
                for (IFile f : DAAExportUtils.getBomCachedJars(bomsToClean)) {
                    if (f.exists()) {
                        f.delete(true, new NullProgressMonitor());
                    }
                }
            }
            return new NullChange();
        }
    }
}
