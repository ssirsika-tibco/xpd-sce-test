/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.refactor;

import org.eclipse.swt.graphics.Image;

/**
 * Configuration item designed to have a single mutually exclusive child item
 * checked.
 * 
 * @author aallway
 * @since 8 Apr 2011
 */
public class SingleChildSelectionRefactorConfigItem extends
        ProcessRefactorConfigurationItem {

    private boolean childSelectionRequired;

    private CheckStateChangeListener selchildSelectionListener;

    /**
     * Construct a configuration item to appear in the ProcessRefactorWizard's
     * configuration list that shows as valid only if a single child item is
     * selected.
     * 
     * @param inputObject
     *            Allows constructor to associate own data with item.
     * @param itemText
     *            Tree list item text
     * @param itemDesc
     *            Full item description
     * @param childSelectionRequired
     *            <code>true</code> if at least one child selection is required
     * @param decorator
     *            Decorator image (recommend 16x16 pixels) can be <b>null</b>
     * 
     */
    public SingleChildSelectionRefactorConfigItem(Object inputObject,
            String itemText, String itemDesc, boolean childSelectionRequired,
            Image decorator) {
        super(inputObject, itemText, itemDesc, false,
                childSelectionRequired ? true : false, decorator);
        this.childSelectionRequired = childSelectionRequired;
        selchildSelectionListener = new CheckStateChangeListener() {

            @Override
            public void checkStateChanged(
                    ProcessRefactorConfigurationItem item,
                    boolean newIsCheckedState) {
                childSelectionChanged(item, newIsCheckedState);
            }
        };
    }

    /**
     * @param childSelectionRequired
     *            the childSelectionRequired to set
     */
    public void setChildSelectionRequired(boolean childSelectionRequired) {
        this.childSelectionRequired = childSelectionRequired;
        this.setProblemIfUnchecked(true);
    }

    /**
     * @return the childSelectionRequired
     */
    public boolean isChildSelectionRequired() {
        return childSelectionRequired;
    }

    /**
     * Get the single selected child.
     * 
     * @return single selected child.
     */
    public ProcessRefactorConfigurationItem getCheckedChild() {
        for (ProcessRefactorConfigurationItem child : getChildItems()) {
            if (child.isChecked()) {
                return child;
            }
        }
        return null;
    }

    /**
     * @return The number of children checked.
     */
    public int getCheckedChildCount() {
        int count = 0;
        for (ProcessRefactorConfigurationItem child : getChildItems()) {
            if (child.isChecked()) {
                count++;
            }
        }
        return count;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.refactor.ProcessRefactorConfigurationItem#doAddChildItem(com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.refactor.ProcessRefactorConfigurationItem)
     * 
     * @param childItem
     */
    @Override
    void doAddChildItem(ProcessRefactorConfigurationItem childItem) {
        super.doAddChildItem(childItem);

        childItem.addSelectionListener(selchildSelectionListener);
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.refactor.ProcessRefactorConfigurationItem#setChecked(boolean)
     * 
     * @param isChecked
     */
    @Override
    public void setChecked(boolean isChecked) {
        super.setChecked(isChecked);
        if (isChecked) {
            if (getCheckedChildCount() == 0 && !getChildItems().isEmpty()) {
                /* Select first child by default. */
                getChildItems().get(0).setChecked(true);
            }
        }
    }

    /**
     * Make sure the child selection is mutually exclusive
     * 
     * @param e
     */
    private void childSelectionChanged(ProcessRefactorConfigurationItem item,
            boolean newIsCheckedState) {
        if (newIsCheckedState) {
            for (ProcessRefactorConfigurationItem sibling : getChildItems()) {
                if (item != sibling && sibling.isChecked()) {
                    sibling.setChecked(false);
                }
            }
        }

    }

}
