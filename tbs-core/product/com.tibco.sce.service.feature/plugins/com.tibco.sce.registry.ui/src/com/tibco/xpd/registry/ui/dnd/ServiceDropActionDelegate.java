package com.tibco.xpd.registry.ui.dnd;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.IDropActionDelegate;

import com.tibco.xpd.registry.ui.actions.ImportAction;
import com.tibco.xpd.registry.ui.selector.RegistryServiceSelector;
import com.tibco.xpd.registry.ui.views.RegistryView;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;

public class ServiceDropActionDelegate implements IDropActionDelegate {

    private static final String VIEW_ID = "com.tibco.xpd.registry.ui.views.ServiceRegistryView"; //$NON-NLS-1$

    public boolean run(Object source, Object target) {

        if (target instanceof IContainer || target instanceof SpecialFolder
                || adapts(target, IFolder.class)) {
            RegistryServiceSelector registry = getRegistryViewer();
            if (registry != null) {
                ImportAction importAction = new ImportAction(getShell(),
                        registry);
                importAction.setSelection(new StructuredSelection(target));
                importAction.run();
                return true;
            }
        }
        return false;
    }

    private boolean adapts(Object target, Class<IFolder> type) {
        if (target == null) {
            return false;
        }
        Object adapter = null;
        if (target instanceof IAdaptable) {
            adapter = ((IAdaptable) target).getAdapter(type);
        }
        if (adapter == null) {
            adapter = Platform.getAdapterManager().getAdapter(target, type);
        }
        if (adapter != null) {
            return true;
        }
        return false;
    }

    /**
     * @return
     */
    private Shell getShell() {
        return PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
    }

    private RegistryServiceSelector getRegistryViewer() {
        RegistryView rv = (RegistryView) PlatformUI.getWorkbench()
                .getActiveWorkbenchWindow().getActivePage().findView(VIEW_ID);
        if (rv != null) {
            return rv.getRegistryServiceSelector();
        }
        return null;
    }

}
