/**
 * CheckboxCellEditor.java
 *
 * 
 *
 * @author aallway
 * @copyright TIBCO Software Inc. (c) 2008
 */
package com.tibco.xpd.ui.properties.table;

import org.eclipse.jface.viewers.ColumnViewerEditorActivationEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;

import com.tibco.xpd.ui.properties.PropertiesPlugin;
import com.tibco.xpd.ui.properties.PropertiesPluginConstants;

/**
 * CheckboxCellEditor
 * 
 * This version of CheckboxCellEditor provides a couple of extra things over
 * jface version.
 * <li>It provides two methods getImgChecked/Unchecked() for you to use from
 * your ITableItemLabelProvider</li>
 * <li>It provides <i>some</i> keyboard access - if user traverses onto cell
 * with keyboard and presses space the selection is toggle (currently though,
 * the cell is deactivated when this happens)</li>
 */
public class CheckboxCellEditor extends
        org.eclipse.jface.viewers.CheckboxCellEditor {

    private static final Image imgUnchecked =
            PropertiesPlugin.getDefault().getImageRegistry()
                    .get(PropertiesPluginConstants.IMG_CHECKBOX_UNTICKED);

    private static final Image imgChecked =
            PropertiesPlugin.getDefault().getImageRegistry()
                    .get(PropertiesPluginConstants.IMG_CHECKBOX_TICKED);

    private Composite dummyComp;

    private boolean active = false;

    /**
     * Creates a new checkbox cell editor parented under the given control. The
     * cell editor value is a boolean value, which is initially
     * <code>false</code>. Initially, the cell editor has no cell validator.
     * 
     * @param parent
     *            the parent control
     * @param style
     *            the style bits
     * @since 2.1
     */
    public CheckboxCellEditor(Composite parent, int style) {
        super(parent, style);
    }

    @Override
    protected Control createControl(Composite parent) {
        dummyComp = new Composite(parent, SWT.NONE);
        dummyComp.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (active && e.character == ' ') {
                    boolean val = ((Boolean) getValue()).booleanValue();
                    setValue(!val);

                    // TODO Find a better way of updating the owner as this
                    // deactivates the cell editor.
                    fireApplyEditorValue();
                }
            }
        });

        return dummyComp;
    }

    @Override
    public void activate(ColumnViewerEditorActivationEvent activationEvent) {
        active = true;

        // If activating via a mouse event then allow super to set activation.
        if (activationEvent.sourceEvent instanceof MouseEvent) {

            // TODO - Have to do this on asynch thread because super activate
            // does a fireApplyEditorValue() to toggle selection on mouse click
            // AND THAT does a deactivate which destroiys the cell editor
            // (effectively)
            Display.getCurrent().asyncExec(new Runnable() {
                public void run() {
                    CheckboxCellEditor.this.activate();

                }
            });

        } else {
            // If activated by keyboard then DO NOT use super's activate because
            // THAT toggles the button.
        }
    }

    @Override
    public void deactivate() {
        active = false;
        super.deactivate();
    }

    @Override
    protected void doSetFocus() {
        dummyComp.setFocus();
    }

    /**
     * The label provider for this cell (when editor is not active) can use this
     * image to show status.
     * 
     * @return the imgUnchecked
     */
    public static Image getImgUnchecked() {
        return imgUnchecked;
    }

    /**
     * The label provider for this cell (when editor is not active) can use this
     * image to show status.
     * 
     * @return the imgChecked
     */
    public static Image getImgChecked() {
        return imgChecked;
    }

}
