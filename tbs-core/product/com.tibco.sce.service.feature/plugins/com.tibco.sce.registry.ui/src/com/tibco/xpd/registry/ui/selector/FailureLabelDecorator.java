package com.tibco.xpd.registry.ui.selector;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IDecoration;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ILightweightLabelDecorator;

import com.tibco.xpd.registry.ui.Activator;
import com.tibco.xpd.registry.ui.internal.Messages;

/**
 * Decorates RegistrySearch elements to indicate search failure.
 * <p>
 * <i>Created: 17 Jul 2007</i>
 * </p>
 * 
 * @author Jan Arciuchiewicz
 * 
 */
public class FailureLabelDecorator implements ILightweightLabelDecorator {

    /** Decorator ID */
    public static final String SEARCH_FAILURE_DECORATOR_ID = "com.tibco.xpd.registry.ui.searchFailureDecorator"; //$NON-NLS-1$

    /** Error overlay image */
    public static final String ERROR_OVERLAY = "icons/overlay/error.gif"; //$NON-NLS-1$
    /** Warning overlay image */
    public static final String WARNING_OVERLAY = "icons/overlay/warning.gif"; //$NON-NLS-1$

    /*
     * @see org.eclipse.jface.viewers.ILightweightLabelDecorator#decorate(java.lang.Object,
     *      org.eclipse.jface.viewers.IDecoration)
     */
    public void decorate(Object element, IDecoration decoration) {
        if (element instanceof RegistrySearch) {
            RegistrySearch regSearch = ((RegistrySearch) element);
            synchronized (regSearch) {
                if (regSearch.getException() != null) {
                    ImageDescriptor errorOverlay = Activator
                            .getImageDescriptor(ERROR_OVERLAY);
                    decoration
                            .addOverlay(errorOverlay, IDecoration.BOTTOM_LEFT);
                    decoration
                            .addSuffix(' ' + Messages.FailureLabelDecorator_Failed_label);
                } else if (regSearch.getChildren() != null) {
                    if (regSearch.getChildren().length == 0) {
                        decoration
                                .addSuffix(' ' + Messages.FailureLabelDecorator_NoMatchesFound_label);
                    }
                }
            }
        }
    }

    /*
     * @see org.eclipse.jface.viewers.IBaseLabelProvider#addListener(org.eclipse.jface.viewers.ILabelProviderListener)
     */
    public void addListener(ILabelProviderListener listener) {
    }

    /*
     * @see org.eclipse.jface.viewers.IBaseLabelProvider#dispose()
     */
    public void dispose() {
    }

    /*
     * @see org.eclipse.jface.viewers.IBaseLabelProvider#isLabelProperty(java.lang.Object,
     *      java.lang.String)
     */
    public boolean isLabelProperty(Object element, String property) {
        return true;
    }

    /*
     * @see org.eclipse.jface.viewers.IBaseLabelProvider#removeListener(org.eclipse.jface.viewers.ILabelProviderListener)
     */
    public void removeListener(ILabelProviderListener listener) {
    }

}
