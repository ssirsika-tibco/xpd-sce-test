package com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.refactor;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

/**
 * ProcessRefactorConfigContentProvider
 * 
 */
class ProcessRefactorConfigContentProvider implements
        ITreeContentProvider {

    /**
     * 
     */
    private final ProcessRefactorConfigurationPage configContentProvider;

    /**
     * @param page
     */
    ProcessRefactorConfigContentProvider(ProcessRefactorConfigurationPage page) {
        configContentProvider = page;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.viewers.ITreeContentProvider#getChildren(java.lang.Object)
     */
    public Object[] getChildren(Object parentElement) {
        if (parentElement instanceof ProcessRefactorConfigurationItem) {
            ProcessRefactorConfigurationItem item = (ProcessRefactorConfigurationItem) parentElement;

            return item.getChildItems().toArray();
        }

        return new Object[0];
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.viewers.ITreeContentProvider#getParent(java.lang.Object)
     */
    public Object getParent(Object element) {
        if (element instanceof ProcessRefactorConfigurationItem) {
            ProcessRefactorConfigurationItem item = (ProcessRefactorConfigurationItem) element;

            return item.getParent();
        }
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.viewers.ITreeContentProvider#hasChildren(java.lang.Object)
     */
    public boolean hasChildren(Object element) {
        if (element instanceof ProcessRefactorConfigurationItem) {
            ProcessRefactorConfigurationItem item = (ProcessRefactorConfigurationItem) element;

            if (item.getChildItems().size() > 0) {
                return true;
            }
        }
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java.lang.Object)
     */
    public Object[] getElements(Object inputElement) {
        if (inputElement instanceof ProcessRefactorConfigurationPage) {
            ProcessRefactorConfigurationPage configPage = (ProcessRefactorConfigurationPage) inputElement;

            Object[] items = configPage.getConfigItems().toArray();

            return items;
        }
        return new Object[0];
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.viewers.IContentProvider#dispose()
     */
    public void dispose() {
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer,
     *      java.lang.Object, java.lang.Object)
     */
    public void inputChanged(Viewer viewer, Object oldInput,
            Object newInput) {
    }

}