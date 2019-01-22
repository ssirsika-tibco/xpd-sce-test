/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.contributors;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdExtension.FormImplementation;
import com.tibco.xpd.xpdExtension.FormImplementationType;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ExtendedAttribute;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.resolvers.AbstractUniqueIdsReassignedListener;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Fixes the Pageflow {@link FormImplementation} references which will be broken
 * after the ID's are regenerated.
 * 
 * @author kthombar
 * @since Apr 16, 2015
 */
public class FixUserTaskPageflowReferences extends
        AbstractUniqueIdsReassignedListener {

    /**
     * @see com.tibco.xpd.xpdl2.resolvers.AbstractUniqueIdsReassignedListener#updateReassignedIdReferences(java.util.Collection,
     *      java.util.Map)
     * 
     * @param copyOfElementsToFixReferencesFor
     * @param oldIdToNewEojectMap
     */
    @SuppressWarnings("rawtypes")
    @Override
    public void updateReassignedIdReferences(
            Collection copyOfElementsToFixReferencesFor, Map oldIdToNewEojectMap) {

        for (Object object : copyOfElementsToFixReferencesFor) {

            if (object instanceof FormImplementation) {
                /*
                 * If element is FormImplementation then check if references
                 * pageflow and update the references.
                 */
                checkAndUpdatePageflowFormReferences((FormImplementation) object,
                        oldIdToNewEojectMap);
            } else {
                /*
                 * Iterate through all the child elements and check if they are
                 * instance of FormImplementation
                 */
                for (Iterator treeIt = ((EObject) object).eAllContents(); treeIt
                        .hasNext();) {

                    Object element = treeIt.next();

                    if (element instanceof FormImplementation) {
                        /*
                         * If element is FormImplementation then check if
                         * references pageflow and update the references.
                         */
                        checkAndUpdatePageflowFormReferences((FormImplementation) element,
                                oldIdToNewEojectMap);
                    }
                }
            }
        }
    }

    /**
     * Check is the {@link FormImplementation} type is pageflow, if so then
     * checks if the passed map has the corresponding Eobject for the old
     * pageflow id, if yes then updates the pageflow references.
     * 
     * @param formImpl
     *            the FormImplementation
     * @param oldIdToNewEojectMap
     *            old Id to New Eobject Map
     */
    @SuppressWarnings("rawtypes")
    private void checkAndUpdatePageflowFormReferences(
            FormImplementation formImpl, Map oldIdToNewEojectMap) {

        if (formImpl != null
                && FormImplementationType.PAGEFLOW.equals(formImpl
                        .getFormType())) {

            String oldFormURI = formImpl.getFormURI();

            if (oldFormURI != null && !oldFormURI.isEmpty()) {

                /*
                 * the form uri format is like this eg:
                 * 'MyBpmProjectXpdl.xpdl#_oxVFMuQ0EeSDVq5yaU3ABA'
                 */
                int indexOfHash = oldFormURI.indexOf('#');

                if (indexOfHash != -1) {
                    /*
                     * get the relative path i.e. the string before #
                     */
                    String pageflowsRelativePath =
                            oldFormURI.substring(0, indexOfHash);
                    /*
                     * get the pageflow ID
                     */
                    String oldPageflowId =
                            oldFormURI.substring(indexOfHash + 1,
                                    oldFormURI.length());
                    /*
                     * get the pageflow copy from the map
                     */
                    Object objectFromMap =
                            oldIdToNewEojectMap.get(oldPageflowId);

                    if (objectFromMap instanceof Process) {

                        Process pageflowProcess = (Process) objectFromMap;

                        /*
                         * get the file for the pageflow with new ID.
                         */
                        IFile xpdlFile =
                                WorkingCopyUtil.getFile(pageflowProcess);

                        if (xpdlFile != null) {
                            /*
                             * If the file is not null that means that the
                             * pageflow is attached to the model and hence use
                             * the file name instead.
                             */
                            pageflowsRelativePath = xpdlFile.getName();
                        }
                        /*
                         * get the new pageflow id
                         */
                        String newPageflowID = pageflowProcess.getId();
                        /*
                         * create new Form Uri
                         */
                        String newFormUri =
                                pageflowsRelativePath + "#" + newPageflowID; //$NON-NLS-1$
                        /*
                         * ser the form uri.
                         */
                        formImpl.setFormURI(newFormUri);

                        /*
                         * The Parent activity of the FormImplementation stores
                         * the Pageflow form implementation info in the extended
                         * attributes, update that reference as well.
                         */
                        EObject ancestor =
                                Xpdl2ModelUtil.getAncestor(formImpl,
                                        Activity.class);

                        if (ancestor != null) {
                            EList<ExtendedAttribute> extendedAttributes =
                                    ((Activity) ancestor)
                                            .getExtendedAttributes();

                            for (ExtendedAttribute extendedAttribute : extendedAttributes) {

                                if (TaskObjectUtil.USER_TASK_ATTR
                                        .equals(extendedAttribute.getName())
                                        && oldFormURI.equals(extendedAttribute
                                                .getValue())) {

                                    extendedAttribute.setValue(newFormUri);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
