/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */
package com.tibco.xpd.implementer.resources.xpdl2.properties.replyimmediate;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptUtil;
import com.tibco.xpd.processeditor.xpdl2.properties.StandardMappingUtil;

/**
 * Mapping source content provider for "Output Process Id" mappings (the reply
 * mappings for a start event configured to reply-immediately).
 * <p>
 * Provides the special __PROCESS_ID__ virutal parameter as source content.
 * 
 * 
 * @author aallway
 * @since 31 Jul 2012
 */
public class ReplyImmediateMappingSourceContentProvider implements
        ITreeContentProvider {

    @Override
    public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
    }

    @Override
    public void dispose() {
    }

    @Override
    public Object[] getElements(Object inputElement) {
        ConceptPath replyProcessIdConceptPath =
                new ConceptPath(
                        StandardMappingUtil.REPLY_IMMEDIATE_PROCESS_ID_FORMALPARAMETER,
                        null);

        return new Object[] { replyProcessIdConceptPath };
    }

    @Override
    public boolean hasChildren(Object element) {
        if (element instanceof ConceptPath) {
            return !ConceptUtil.getConceptPathChildren((ConceptPath) element)
                    .isEmpty();
        }

        return false;
    }

    @Override
    public Object getParent(Object element) {
        if (element instanceof ConceptPath) {
            return ((ConceptPath) element).getParent();
        }
        return null;
    }

    @Override
    public Object[] getChildren(Object parentElement) {
        if (parentElement instanceof ConceptPath) {
            return ConceptUtil
                    .getConceptPathChildren((ConceptPath) parentElement)
                    .toArray();
        }
        return new Object[0];
    }
}