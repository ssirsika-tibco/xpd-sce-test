/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.implementer.resources.xpdl2.properties;

import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.implementer.script.ActivityMessageProvider;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.xpdExtension.CorrelationDataMappings;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.Message;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * A factory for commands to create or remove mappings through an
 * {@link ActivityMessageProvider} for a Request activity.
 * 
 */
public class RequestActivityMessageMappingCommandFactory extends
        ActivityMessageMappingCommandFactory {

    public RequestActivityMessageMappingCommandFactory(
            MappingDirection direction) {
        super(direction);
    }

    @Override
    protected void appendAddMappingCommand(EditingDomain ed,
            CompoundCommand cmd, Message message, DataMapping mapping,
            Object source, Object target) {
        if (isCorrelationData(target)) {
            Object other =
                    Xpdl2ModelUtil.getOtherElement(message,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_CorrelationDataMappings());
            if (other instanceof CorrelationDataMappings) {
                CorrelationDataMappings correlationMappings =
                        (CorrelationDataMappings) other;
                cmd.append(AddCommand.create(ed,
                        correlationMappings,
                        XpdExtensionPackage.eINSTANCE
                                .getCorrelationDataMappings_DataMappings(),
                        mapping));
            } else {
                CorrelationDataMappings correlationMappings =
                        XpdExtensionFactory.eINSTANCE
                                .createCorrelationDataMappings();
                correlationMappings.getDataMappings().add(mapping);
                cmd.append(Xpdl2ModelUtil.getSetOtherElementCommand(ed,
                        message,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_CorrelationDataMappings(),
                        correlationMappings));
            }
        } else {
            super.appendAddMappingCommand(ed,
                    cmd,
                    message,
                    mapping,
                    source,
                    target);
        }
    }

    private boolean isCorrelationData(Object target) {
        boolean isCorrealation = false;
        if (target instanceof ConceptPath) {
            target = ((ConceptPath) target).getItem();
        }
        if (target instanceof DataField) {
            isCorrealation = ((DataField) target).isCorrelation();
        }
        return isCorrealation;
    }

}
