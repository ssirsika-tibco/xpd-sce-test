package com.tibco.xpd.processwidget.dragdrop;

import org.eclipse.jface.util.TransferDragSourceListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;

import com.tibco.xpd.fragments.IFragment;
import com.tibco.xpd.fragments.dnd.FragmentLocalSelectionTransfer;
import com.tibco.xpd.processwidget.impl.ProcessWidgetImpl;

/**
 * ProcessFragmentDragSourceListener
 */
public class ProcessFragmentDragSourceListener implements
        TransferDragSourceListener {
    /**
     * 
     */
    private ProcessWidgetImpl processWidget;

    // For keeping track of CTRL key (for native drag-n-drop enablement).
    boolean ctrlKeyDown = false;

    /**
     * @param impl
     */
    public ProcessFragmentDragSourceListener(ProcessWidgetImpl impl) {
        this.processWidget = impl;

        //
        // Setup key listener for keeping track of CTRL key (for native
        // drag-n-drop enablement).
        //

        impl.getGraphicalViewer().getControl()
                .addKeyListener(new KeyListener() {

                    public void keyPressed(KeyEvent e) {
                        if (e.keyCode == SWT.CTRL) {
                            ctrlKeyDown = true;
                        }
                    }

                    public void keyReleased(KeyEvent e) {
                        if (e.keyCode == SWT.CTRL) {
                            ctrlKeyDown = false;
                        }
                    }
                });

    }

    public Transfer getTransfer() {
        return FragmentLocalSelectionTransfer.getTransfer();
    }
    
    public void dragFinished(DragSourceEvent event) {

        // Note: we loose Ctrl key UP once drag-n-drop takes over.
        ctrlKeyDown = false;

    }

    public void dragSetData(DragSourceEvent event) {
        event.data = null;
        if (FragmentLocalSelectionTransfer.getTransfer().getSelection() instanceof IStructuredSelection) {
            if (((IStructuredSelection) FragmentLocalSelectionTransfer
                    .getTransfer().getSelection()).getFirstElement() instanceof IFragment) {
                IFragment fragment =
                        (IFragment) ((IStructuredSelection) FragmentLocalSelectionTransfer
                                .getTransfer().getSelection())
                                .getFirstElement();
                event.data = fragment.getData();
            }
        }
    }

    public void dragStart(DragSourceEvent event) {
        event.doit = false;

        // Only enable native drag-n-drop when ctrl key is down. This is
        // because the normal GEF drag-move objects is disable if any
        // drag source listener returns with event.doit = true.
        if (ctrlKeyDown) {

            //
            // Set up the transfer data.
            event.doit =
                    processWidget
                            .setupProcessFragmentTransfer(FragmentLocalSelectionTransfer
                                    .getTransfer(),
                                    null);

        }
    }

}
