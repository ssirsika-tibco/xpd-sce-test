/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.wm.tasklibrary.editor.properties;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.IFilter;

import com.tibco.xpd.wm.tasklibrary.editor.resources.TaskLibraryPropertyTester;
import com.tibco.xpd.wm.tasklibrary.editor.resources.TasksGroup;
import com.tibco.xpd.xpdl2.Lane;
import com.tibco.xpd.xpdl2.Process;

public class TasksPropertiesInputFilter implements IFilter {
    public boolean select(Object toTest) {
        if (toTest instanceof TasksGroup) {
            return true;
        } else {
            EObject baseSelectObject =
                    TaskPropertySection.getBaseSelectObject(toTest);
            if (baseSelectObject instanceof Lane
                    || baseSelectObject instanceof Process) {
                if (TaskLibraryPropertyTester
                        .isTaskLibraryContent(baseSelectObject)) {
                    return true;
                }
            }
        }

        return false;
    }

}