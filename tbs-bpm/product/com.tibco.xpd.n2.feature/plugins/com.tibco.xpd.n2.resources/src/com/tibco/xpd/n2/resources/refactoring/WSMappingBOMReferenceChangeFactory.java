/*
 * Copyright (c) TIBCO Software Inc. 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.n2.resources.refactoring;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.ltk.core.refactoring.participants.RenameArguments;

import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Creates the Changes for the WebService Data mappings
 * 
 * @author mtorres
 * 
 */
public class WSMappingBOMReferenceChangeFactory {

    private EObject mappingContainer;

    private List<WSMappingBOMReferenceChange> changes;

    private RenameArguments args = null;

    private EObject element = null;

    public WSMappingBOMReferenceChangeFactory(EObject mappingContainer,
            RenameArguments args, EObject element) {
        this.mappingContainer = mappingContainer;
        this.args = args;
        this.element = element;
    }

    public List<WSMappingBOMReferenceChange> getChanges() {
        if (changes == null) {
            TransactionalEditingDomain editingDomain =
                    XpdResourcesPlugin.getDefault().getEditingDomain();
            changes = new ArrayList<WSMappingBOMReferenceChange>();
            if (mappingContainer instanceof Activity) {
                Activity activity = (Activity) mappingContainer;
                List<DataMapping> inDataMappings =
                        Xpdl2ModelUtil.getDataMappings(activity,
                                DirectionType.IN_LITERAL);
                if (inDataMappings != null && !inDataMappings.isEmpty()) {
                    for (DataMapping inDataMapping : inDataMappings) {
                        if (WSMappingBOMElementRefactorHelper.isAffectedMapping(inDataMapping, element)) {
                            changes.add(new WSMappingBOMReferenceChange(args,
                                    element, inDataMapping, editingDomain));
                        }
                    }
                }
                List<DataMapping> outDataMappings =
                        Xpdl2ModelUtil.getDataMappings(activity,
                                DirectionType.OUT_LITERAL);
                if (outDataMappings != null && !outDataMappings.isEmpty()) {
                    for (DataMapping outDataMapping : outDataMappings) {
                        if (WSMappingBOMElementRefactorHelper.isAffectedMapping(outDataMapping, element)) {
                            changes.add(new WSMappingBOMReferenceChange(args,
                                    element, outDataMapping, editingDomain));
                        }
                    }
                }
            }
        }
        return changes;
    }

}
