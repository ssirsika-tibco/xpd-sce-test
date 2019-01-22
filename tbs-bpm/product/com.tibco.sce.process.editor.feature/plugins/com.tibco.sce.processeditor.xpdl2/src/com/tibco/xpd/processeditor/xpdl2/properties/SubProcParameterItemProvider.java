/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.processeditor.xpdl2.properties;

import java.util.HashMap;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.uml2.uml.Class;

import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.processeditor.xpdl2.util.SubProcUtil;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.ProcessRelevantData;

/**
 * @author scrossle
 */
public class SubProcParameterItemProvider extends
        AbstractInvokedProcessParameterItemProvider implements
        ITreeContentProvider {

    /**
     * @param adapterFactory
     *            The adapter factory.
     * @param direction
     *            The mapping direction.
     * @param dest
     *            The destination.
     */
    public SubProcParameterItemProvider(MappingDirection direction) {
        super(direction);
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.AbstractInvokedProcessParameterItemProvider#getInvokedProcessOrInterface(com.tibco.xpd.xpdl2.Activity)
     * 
     * @param activity
     * @return
     */
    @Override
    protected EObject getInvokedProcessOrInterface(Activity activity) {
        EObject processOrInterface =
                TaskObjectUtil.getSubProcessOrInterface(activity);
        return processOrInterface;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.AbstractInvokedProcessParameterItemProvider#getElements(java.lang.Object)
     * 
     * @param inputElement
     * @return
     */
    @Override
    public Object[] getElements(Object inputElement) {

        if (SubProcUtil.isAsyncCall(inputElement)
                && MappingDirection.OUT.equals(direction)) {

            FormalParameter fp =
                    StandardMappingUtil.REPLY_IMMEDIATE_PROCESS_ID_FORMALPARAMETER;

            cachedParameterSet = new HashMap<ConceptPath, Boolean>();

            cachedParameterSet.put(createConceptPath(fp), false);

            ConceptPath[] paramsArray =
                    cachedParameterSet.keySet().toArray(new ConceptPath[0]);

            return paramsArray;
        }

        return super.getElements(inputElement);
    }

    /**
     * @param data
     * @return concept path for given parameter.
     */
    private ConceptPath createConceptPath(ProcessRelevantData data) {
        Class cls = ConceptUtil.getConceptClass(data);
        ConceptPath conceptPath = new ConceptPath(data, cls);
        return conceptPath;
    }
}
