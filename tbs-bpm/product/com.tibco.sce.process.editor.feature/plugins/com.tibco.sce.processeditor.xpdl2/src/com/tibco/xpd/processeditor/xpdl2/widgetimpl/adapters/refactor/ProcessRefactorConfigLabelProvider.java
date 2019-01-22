package com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.refactor;

import java.util.List;

import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;

import com.tibco.xpd.processeditor.xpdl2.ProcessEditorConstants;
import com.tibco.xpd.processeditor.xpdl2.Xpdl2ProcessEditorPlugin;
import com.tibco.xpd.processeditor.xpdl2.internal.Messages;

/**
 * ConfigLabelProvider
 * 
 */
public class ProcessRefactorConfigLabelProvider implements ILabelProvider {

    /**
     * 
     */
    private final ProcessRefactorConfigurationPage configLabelProvider;

    private Image problemItemImage;

    public ProcessRefactorConfigLabelProvider(
            ProcessRefactorConfigurationPage page) {
        configLabelProvider = page;
        ImageRegistry ir =
                Xpdl2ProcessEditorPlugin.getDefault().getImageRegistry();

        problemItemImage = ir.get(ProcessEditorConstants.IMG_ERROR_DECORATOR);

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.viewers.ILabelProvider#getImage(java.lang.Object)
     */
    @Override
    public Image getImage(Object element) {
        if (element instanceof ProcessRefactorConfigurationItem) {
            ProcessRefactorConfigurationItem ci =
                    (ProcessRefactorConfigurationItem) element;

            if (ci instanceof SingleChildSelectionRefactorConfigItem) {
                SingleChildSelectionRefactorConfigItem item =
                        (SingleChildSelectionRefactorConfigItem) ci;

                int count = item.getCheckedChildCount();
                if (count > 1) {
                    return problemItemImage;
                }

                if (item.isChildSelectionRequired() && count == 0) {
                    return problemItemImage;
                }
                return ci.getDecorator();
            }

            if (ci.isProblemIfUnchecked() && !ci.isChecked()) {
                return problemItemImage;
            }

            // If any children of this config item are problem children
            // ( :o) ) then mark the parent
            if (hasProblemChild(ci)) {
                return problemItemImage;
            }

            return ci.getDecorator();

        }
        return null;
    }

    /**
     * Recursively check for child configuration items that have the
     * "problem if unchecked" marker set.
     * 
     * @param ci
     * @return
     */
    private boolean hasProblemChild(ProcessRefactorConfigurationItem ci) {
        List<ProcessRefactorConfigurationItem> children = ci.getChildItems();

        if (children != null && children.size() > 0) {
            for (ProcessRefactorConfigurationItem child : children) {
                if (child.isProblemIfUnchecked() && !child.isChecked()) {
                    // No need to go any further, we've found one.
                    return true;

                } else {
                    // Check if this child has any problem children.
                    if (hasProblemChild(child)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.viewers.ILabelProvider#getText(java.lang.Object)
     */
    @Override
    public String getText(Object element) {
        if (element instanceof ProcessRefactorConfigurationItem) {
            ProcessRefactorConfigurationItem ci =
                    (ProcessRefactorConfigurationItem) element;

            return ci.getItemText();
        }
        return Messages.ProcessRefactorConfigLabelProvider_InvalidEntryRefactorAsSubproc_message1;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.jface.viewers.IBaseLabelProvider#addListener(org.eclipse.
     * jface.viewers.ILabelProviderListener)
     */
    @Override
    public void addListener(ILabelProviderListener listener) {
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.viewers.IBaseLabelProvider#dispose()
     */
    @Override
    public void dispose() {
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.jface.viewers.IBaseLabelProvider#isLabelProperty(java.lang
     * .Object, java.lang.String)
     */
    @Override
    public boolean isLabelProperty(Object element, String property) {
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.jface.viewers.IBaseLabelProvider#removeListener(org.eclipse
     * .jface.viewers.ILabelProviderListener)
     */
    @Override
    public void removeListener(ILabelProviderListener listener) {
    }

}