package com.tibco.xpd.core.builder.test.builder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

public class TestProjectBuilder extends IncrementalProjectBuilder {

    public static class BuildData {
        public int buildKind;
        public IResourceDelta[] deltas;

        public BuildData(int kind, IResourceDelta[] deltas) {
            this.buildKind = kind;
            this.deltas = deltas;
        }
    }

    public static class BuildLog {
        public int buildCount = 0;
        public List<BuildData> data = new ArrayList<BuildData>();

        public void add(int buildKind, IResourceDelta[] deltas) {
            ++buildCount;
            data.add(new BuildData(buildKind, deltas));
        }

        public void addFullBuild() {
            ++buildCount;
            data.add(new BuildData(FULL_BUILD, new IResourceDelta[0]));
        }
    }

    private static BuildLog log = new BuildLog();

    public TestProjectBuilder() {
        // TODO Auto-generated constructor stub
    }

    public static BuildLog getLog() {
        return log;
    }

    public static void resetLog() {
        log.buildCount = 0;
        log.data.clear();
    }

    @SuppressWarnings("unchecked")
    @Override
    protected IProject[] build(int kind, Map args, IProgressMonitor monitor)
            throws CoreException {

        msg("Build Kind = " + getBuildKind(kind)); //$NON-NLS-1$

        if (kind == FULL_BUILD) {
            log.addFullBuild();
        } else {
            IResourceDelta delta = getDelta(getProject());

            if (delta != null) {
                IResourceDelta[] children = delta.getAffectedChildren();

                System.out.println("----Affected Deltas--------"); //$NON-NLS-1$
                for (IResourceDelta child : children) {
                    System.out.println("+ " //$NON-NLS-1$
                            + child.getResource().getProjectRelativePath());
                }
                System.out.println("---------------------------"); //$NON-NLS-1$

                log.add(kind, children);
            } else {
                System.err.println("Delta is null"); //$NON-NLS-1$
            }
        }

        return null;
    }

    /**
     * Print message to SysOut
     * 
     * @param message
     */
    private void msg(String message) {
        if (message != null) {
            System.out.println("+++TEST_PROJECT_BUILDER: " + message); //$NON-NLS-1$
        }
    }

    /**
     * Get the build kind
     * 
     * @param kind
     * @return
     */
    public static String getBuildKind(int kind) {
        String strKind = ""; //$NON-NLS-1$

        switch (kind) {
        case FULL_BUILD:
            strKind = "Full Build"; //$NON-NLS-1$
            break;
        case AUTO_BUILD:
            strKind = "Auto Build"; //$NON-NLS-1$
            break;
        case INCREMENTAL_BUILD:
            strKind = "Incremental Build"; //$NON-NLS-1$
            break;
        default:
            strKind = String.format("Unknown build kind: %d", kind); //$NON-NLS-1$
            break;
        }

        return strKind;
    }

}
