/**
 * SetFillColorAction.java
 *
 * 
 *
 * @author aallway
 * @copyright TIBCO Software Inc. (c) 2006
 */
package com.tibco.xpd.processeditor.xpdl2.testprogression;

import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.RootEditPart;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.actions.ActionDelegate;

import com.tibco.xpd.processwidget.ProcessWidget;
import com.tibco.xpd.processwidget.ProcessWidgetConstants;
import com.tibco.xpd.processwidget.parts.WidgetRootEditPart;

/**
 * SetTaskIconAction
 * 
 * 
 * @author aallway
 * @since 3.3 (2 Oct 2009)
 */
public class StartProgressionAction extends ActionDelegate {

    public static final String TEST_AUTO_PROGRESSION_PROPERTY =
            "TEST_AUTO_PROGRESSION_PROPERTY"; //$NON-NLS-1$

    boolean cmd_ok = false;

    ProcessWidget processWidgetImpl;

    @Override
    public void run(IAction action) {
        if (cmd_ok && processWidgetImpl != null) {
            TestAutoProgression test =
                    new TestAutoProgression(processWidgetImpl);
            processWidgetImpl.getControl()
                    .setData(TEST_AUTO_PROGRESSION_PROPERTY, test);

            test.startProgression();
        }

        return;
    }

    @Override
    public void selectionChanged(IAction action, ISelection selection) {
        cmd_ok = false;

        if (selection instanceof IStructuredSelection) {
            IStructuredSelection sel = (IStructuredSelection) selection;
            if (sel.size() == 1) {
                if (sel.getFirstElement() instanceof GraphicalEditPart) {

                    RootEditPart rep =
                            ((GraphicalEditPart) sel.getFirstElement())
                                    .getViewer().getRootEditPart();

                    if (rep instanceof WidgetRootEditPart) {
                        processWidgetImpl =
                                (ProcessWidget) rep
                                        .getViewer()
                                        .getProperty(ProcessWidgetConstants.PROP_WIDGET);

                        TestAutoProgression testProgression =
                                (TestAutoProgression) processWidgetImpl
                                        .getControl()
                                        .getData(TEST_AUTO_PROGRESSION_PROPERTY);

                        if (testProgression == null
                                || !testProgression.isProgressionRunning()) {
                            cmd_ok = true;
                        }
                    }
                }
            }
        }

        // If everything is ok create the command to change the transactional
        // status of the selected subprocess activity(s)
        if (cmd_ok) {
            action.setEnabled(true);
        } else {
            action.setEnabled(false);
        }

    }

    @Override
    public void dispose() {
        super.dispose();
    }

}
