/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.wm.tasklibrary.editor.properties;

import java.util.Collection;
import java.util.Collections;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.wm.tasklibrary.editor.resources.TaskLibraryFactory;
import com.tibco.xpd.wm.tasklibrary.editor.resources.TaskLibraryPropertyTester;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.edit.ui.properties.general.PackageInformationSection;

/**
 * Task library section information section
 * 
 * @author aallway
 * @since 3.2
 */
public class TaskLibraryPackageInformationSection extends
        PackageInformationSection {

    @Override
    public void setInput(Collection<?> items) {
        //
        // Convert from task library input to package .
        if (items != null && items.size() == 1) {
            Object element = items.iterator().next();
            
            EObject eo = null;
            if (element instanceof EObject) {
                eo = (EObject) element;
            } else if (element instanceof IAdaptable) {
                eo = (EObject) ((IAdaptable) element).getAdapter(EObject.class);
            }

            if (eo instanceof Process) {
                Process taskLib = (Process)eo;
                if (taskLib.getPackage() != null) {
                    super.setInput(Collections.singletonList(taskLib
                            .getPackage()));
                    return;
                }
            }
        }

        super.setInput(items);
    }

    @Override
    public boolean select(Object toTest) {
        EObject eo = null;
        
        if (toTest instanceof EObject) {
            eo = (EObject) toTest;
        } else if (toTest instanceof IAdaptable) {
            eo = (EObject) ((IAdaptable) toTest).getAdapter(EObject.class);
        }

        if (eo instanceof Process) {
            if (TaskLibraryPropertyTester
                    .isTaskLibraryProcess((Process) eo)) {
                return true;
            }

        }
        return false;
    }
}
