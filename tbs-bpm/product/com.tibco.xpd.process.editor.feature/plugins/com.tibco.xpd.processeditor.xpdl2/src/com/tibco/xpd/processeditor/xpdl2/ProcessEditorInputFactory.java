/*
 ** 
 **  MODULE:             $RCSfile: $ 
 **                      $Revision: $ 
 **                      $Date: $ 
 ** 
 **  ENVIRONMENT:  Java - Platform independent 
 ** 
 **  COPYRIGHT:    (c) 2005 TIBCO Software Inc., All Rights Reserved. 
 ** 
 **  MODIFICATION HISTORY: 
 ** 
 **    $Log: $ 
 ** 
 */

package com.tibco.xpd.processeditor.xpdl2;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.ui.IEditorInput;

import com.tibco.xpd.navigator.packageexplorer.editors.EditorInputFactory;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Editor Input factory for xpdl process editor. It create EditorInput for
 * XPDL's Process model element.
 * 
 * @author wzurek
 */
public class ProcessEditorInputFactory implements EditorInputFactory {

    public static final ProcessEditorInputFactory INSTANCE =
            new ProcessEditorInputFactory();

    /**
     * It create EditorInput for XPDL's Process model element.
     */
    @Override
    public IEditorInput getEditorInputFor(Object obj) {
        ProcessEditorInput ei = null;
        if (obj instanceof EObject) {
            EObject actualInput = (EObject) obj;
            if (!(obj instanceof Process)) {
                obj = Xpdl2ModelUtil.getProcess((EObject) obj);
            }

            if (obj instanceof Process) {
                Process proc = (Process) obj;
                WorkingCopy wc = WorkingCopyUtil.getWorkingCopyFor(proc);

                // check working copy cache
                if (null != wc) {

                    ei = (ProcessEditorInput) wc.getAttributes().get(proc);
                    if (ei == null) {

                        // create new editor input
                        ei = new ProcessEditorInput(wc, proc);
                        wc.getAttributes().put(proc, ei);
                    }
                }

            }
        }
        return ei;
    }

}
