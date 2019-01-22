/**
 * ProcessRefactorConfigurationItem.java
 *
 * 
 *
 * @author aallway
 * @copyright TIBCO Software Inc. (c) 2006
 */
package com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.refactor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.swt.graphics.Image;

/**
 * ProcessRefactorConfigurationItem
 * 
 */
public class ProcessRefactorConfigurationItem {

    private String itemText;

    private String itemDesc;

    private Image decorator;

    private boolean checked = false;

    private boolean problemIfUnchecked = false;

    private List<ProcessRefactorConfigurationItem> childItems;

    private Object inputObject;

    private ProcessRefactorConfigurationItem parent = null;

    private boolean checkIsEnabled = true;

    private Set<CheckStateChangeListener> listeners =
            new HashSet<CheckStateChangeListener>();

    /**
     * Construct a configuration item to appear in the ProcessRefactorWizard's
     * configuration list.
     * 
     * @param inputObject
     *            Allows conbstructor to associate own data with item.
     * @param itemText
     *            Tree list item text
     * @param itemDesc
     *            Full item description
     * @param isChecked
     *            Initial check state
     * @param isProblemIfUnchecked
     *            Whether a problem decoration should appear for item if
     *            unchecked
     * @param decorator
     *            Decorator image (recommend 16x16 pixels) can be <b>null</b>
     * 
     */
    public ProcessRefactorConfigurationItem(Object inputObject,
            String itemText, String itemDesc, boolean isChecked,
            boolean isProblemIfUnchecked, Image decorator) {

        this.inputObject = inputObject;
        this.itemText = itemText;
        this.itemDesc = itemDesc;
        this.checked = isChecked;
        this.problemIfUnchecked = isProblemIfUnchecked;
        this.decorator = decorator;

        return;
    }

    public final void addChildItem(ProcessRefactorConfigurationItem childItem) {
        doAddChildItem(childItem);

        return;
    }

    /**
     * @param childItem
     */
    void doAddChildItem(ProcessRefactorConfigurationItem childItem) {
        if (childItems == null) {
            childItems = new ArrayList<ProcessRefactorConfigurationItem>();
        }

        childItems.add(childItem);
        childItem.parent = this;
    }

    /**
     * @return the childItems
     */
    public final List<ProcessRefactorConfigurationItem> getChildItems() {
        if (childItems != null) {
            return childItems;
        }

        return Collections.EMPTY_LIST;
    }

    /**
     * @return the parent
     */
    public final ProcessRefactorConfigurationItem getParent() {
        return parent;
    }

    /**
     * @return the inputObject
     */
    public final Object getInputObject() {
        return inputObject;
    }

    /**
     * @return the itemText
     */
    public String getItemText() {
        return itemText;
    }

    /**
     * @return the itemDesc
     */
    public String getItemDesc() {
        return itemDesc;
    }

    /**
     * @return the isChecked
     */
    public boolean isChecked() {
        return checked;
    }

    /**
     * @param isChecked
     *            the isChecked to set
     */
    public void setChecked(boolean isChecked) {
        if (isChecked != this.isChecked()) {
            this.checked = isChecked;

            fireSelectionEvent();
        }
    }

    /**
     * @return the problemIfUnchecked
     */
    public boolean isProblemIfUnchecked() {
        return problemIfUnchecked;
    }

    /**
     * @return the decorator
     */
    public Image getDecorator() {
        return decorator;
    }

    /**
     * @param decorator
     *            the decorator to set
     */
    public void setDecorator(Image decorator) {
        this.decorator = decorator;
    }

    /**
     * @param problemIfUnchecked
     *            the problemIfUnchecked to set
     */
    public void setProblemIfUnchecked(boolean problemIfUnchecked) {
        this.problemIfUnchecked = problemIfUnchecked;
    }

    /**
     * Override this method to provide image that contains preview information
     * about the configuration parameter.
     * 
     * @param sizeHint
     *            Hint of size to fit image to or <b>null</b> if full size image
     *            required.
     * @return
     */
    public Image getConfigItemPreviewImage(Dimension sizeHint) {
        return null;
    };

    /**
     * Override this method to provide text to go in a FormText control in place
     * of a configItemPreviewImage.
     * <p>
     * Each config item can have either an image OR detail text. If
     * getConfigItemPreviewImage() returns null then this method will be called
     * to retrieve detailed text about the given config item.
     * 
     * @return null if no detail text or a String containing text for a FormText
     *         control.
     */
    public String getConfigItemTextDetails() {

        return null;
    }

    /**
     * @param itemText
     *            the itemText to set
     */
    public void setItemText(String itemText) {
        this.itemText = itemText;
    }

    /**
     * @param itemDesc
     *            the itemDesc to set
     */
    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
    }

    /**
     * @return the checkIsEnabled
     */
    public boolean isCheckIsEnabled() {
        return checkIsEnabled;
    }

    /**
     * @param checkIsEnabled
     *            the checkIsEnabled to set
     */
    public void setCheckIsEnabled(boolean checkIsEnabled) {
        this.checkIsEnabled = checkIsEnabled;
    }

    public void addSelectionListener(CheckStateChangeListener selectionListener) {
        listeners.add(selectionListener);
    }

    public void removeSelectionListener(
            CheckStateChangeListener selectionListener) {
        listeners.remove(selectionListener);
    }

    private void fireSelectionEvent() {
        for (CheckStateChangeListener selectionListener : listeners) {
            selectionListener.checkStateChanged(this, isChecked());
        }
    }

    public interface CheckStateChangeListener {
        public void checkStateChanged(ProcessRefactorConfigurationItem item,
                boolean newIsCheckedState);
    }

}
