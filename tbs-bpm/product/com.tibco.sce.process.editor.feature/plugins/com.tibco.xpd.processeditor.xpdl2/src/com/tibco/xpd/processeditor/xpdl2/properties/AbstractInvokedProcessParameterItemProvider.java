/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.properties;

import java.util.Arrays;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.processeditor.xpdl2.util.SubProcUtil;
import com.tibco.xpd.xpdl2.Activity;

/**
 * Mapping section item provider for situations where source/target of mapping
 * is an invoked process (such as re-usable sub-process task, decision service
 * etc)
 * 
 * @author aallway
 * @since 1 Aug 2011
 */
public abstract class AbstractInvokedProcessParameterItemProvider implements
        ITreeContentProvider {

    /** The mapping direction. */
    protected MappingDirection direction;

    protected Map<ConceptPath, Boolean> cachedParameterSet = null;

    /**
     * @param direction
     */
    public AbstractInvokedProcessParameterItemProvider(
            MappingDirection direction) {
        super();
        this.direction = direction;
    }

    @Override
    public Object[] getElements(Object inputElement) {
        cachedParameterSet = null;

        if (inputElement instanceof Activity) {
            Activity activity = (Activity) inputElement;

            EObject processOrInterface = getInvokedProcessOrInterface(activity);
            if (processOrInterface != null) {
                cachedParameterSet =
                        SubProcUtil
                                .getSubProcessParametersAndMandatory(processOrInterface,
                                        direction);

                ConceptPath[] paramsArray =
                        cachedParameterSet.keySet().toArray(new ConceptPath[0]);

                Arrays.sort(paramsArray, new ConceptPathComparator());

                return paramsArray;
            }
        }
        return new Object[0];
    }

    /**
     * @param activity
     * @return The invoked process or process-interface that is the
     *         source/target of task.
     */
    protected abstract EObject getInvokedProcessOrInterface(Activity activity);

    /**
     * 
     * @param conceptPath
     * @return Whether the given concept path (returned by previous call to
     *         getElements()) is mandatory.
     */
    public boolean isMandatoryParameter(ConceptPath conceptPath) {
        if (cachedParameterSet != null) {
            Boolean mandatory = cachedParameterSet.get(conceptPath);
            if (mandatory != null) {
                return mandatory;
            }
        }
        return false;
    }

    @Override
    public boolean hasChildren(Object object) {
        return false;
    }

    @Override
    public Object[] getChildren(Object object) {
        return new Object[0];
    }

    @Override
    public Object getParent(Object object) {
        return null;
    }

    /**
     * 
     * @see org.eclipse.jface.viewers.IContentProvider#dispose()
     */
    @Override
    public void dispose() {
    }

    /**
     * @param viewer
     * @param oldInput
     * @param newInput
     * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer,
     *      java.lang.Object, java.lang.Object)
     */
    @Override
    public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
    }

}
