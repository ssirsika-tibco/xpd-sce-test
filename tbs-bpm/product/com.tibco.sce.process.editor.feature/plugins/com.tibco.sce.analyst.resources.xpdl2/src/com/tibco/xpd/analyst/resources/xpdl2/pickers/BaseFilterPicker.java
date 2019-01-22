/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.analyst.resources.xpdl2.pickers;

import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.IFilter;
import org.eclipse.swt.widgets.Shell;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesPlugin;
import com.tibco.xpd.ui.dialogs.FilteredMultiSelectionDialog;
/**
 * @author Miguel Torres
 */
public abstract class BaseFilterPicker extends FilteredMultiSelectionDialog {

    private EObject scope = null;
    
    private boolean multi;

    public BaseFilterPicker(Shell shell, boolean multi) {
        super(shell, multi);
        this.multi = multi;
    }
    
    public BaseFilterPicker(Shell shell){
        super(shell);
    }

    private void setInitialPatternFromSingleSelection() {
        List sel = getInitialElementSelections();
        if (sel != null && sel.size() == 1 && !this.multi) {
            String label = getElementName(sel.get(0));
            if (label != null) {
                setInitialPattern(label);
            }
        }
    }
    
    @Override
    public void setInitialSelections(Object[] selectedElements) {
        super.setInitialSelections(selectedElements);
        
        setInitialPatternFromSingleSelection();
    }
  
    @Override
    public void setInitialElementSelections(List selectedElements) {
        super.setInitialElementSelections(selectedElements);

        setInitialPatternFromSingleSelection();
    }
    
    /**
     * Get the filters.
     * 
     * @param monitor
     * @return
     */
    protected abstract Set<IFilter> getFilters();
    

    @Override
    protected IStatus validateItem(Object item) {
        // Assume valid as it's already in the picker
        return new Status(IStatus.OK, Xpdl2ResourcesPlugin.PLUGIN_ID, IStatus.OK, "", null); //$NON-NLS-1$
    }
    

    /**
     * Check whether the given item should be included in the picker. The added
     * set of filters will be used to determine whether the item should be
     * included, if no filters are available then all items will be added.
     * 
     * @param item
     *            item to test
     * @return <code>true</code> if it should be included in the picker,
     *         <code>false</code> otherwise.
     */
    protected boolean include(Object item) {
        boolean include = true;
        if(getFilters() != null){
            for (IFilter filter : getFilters()) {
                include = filter.select(item);
    
                if (!include) {
                    // Filtered out
                    break;
                }
            }
        }
        return include;
    }
    
    /**
     * Set the scope from where 
     * the data is going to be taken
     * 
     * @param scope
     */
    public void setScope(EObject scope) {
        this.scope = scope;
    }

    public EObject getScope() {
        return scope;
    }

}
