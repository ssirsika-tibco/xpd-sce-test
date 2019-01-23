package com.tibco.xpd.n2.live.internal;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;

public class OpenspacePropertyLabelProvider implements ILabelProvider {

    @Override
    public void addListener(ILabelProviderListener listener) {
    }

    @Override
    public void dispose() {
    }

    @Override
    public boolean isLabelProperty(Object element, String property) {
        return false;
    }

    @Override
    public void removeListener(ILabelProviderListener listener) {
    }

    @Override
    public Image getImage(Object element) {
        return Activator.getDefault().getImageRegistry()
                .get(Activator.LIVE_DEV_ICON);
    }

    @Override
    public String getText(Object element) {
        return Messages.Activator_PerspectiveDialogTitle;
    }

}
