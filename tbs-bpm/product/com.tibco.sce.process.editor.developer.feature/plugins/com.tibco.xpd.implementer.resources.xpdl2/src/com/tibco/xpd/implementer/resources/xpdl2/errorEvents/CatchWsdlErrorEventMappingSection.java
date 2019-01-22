/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.implementer.resources.xpdl2.errorEvents;

import javax.wsdl.Fault;
import javax.wsdl.Message;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;

import com.tibco.xpd.implementer.resources.xpdl2.internal.Messages;
import com.tibco.xpd.implementer.resources.xpdl2.properties.AbstractMessageMappingOutSection;
import com.tibco.xpd.implementer.resources.xpdl2.properties.JavaScriptBomContentProvider;
import com.tibco.xpd.implementer.resources.xpdl2.properties.ParameterLabelProvider;
import com.tibco.xpd.implementer.resources.xpdl2.properties.WebServiceScriptProperties;
import com.tibco.xpd.mapper.MapperLabelProvider;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptGrammarFactory;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptableLabelProvider;
import com.tibco.xpd.script.ui.internal.BaseScriptSection;
import com.tibco.xpd.xpdl2.Activity;

/**
 * Mapping section for Catch Error Events that catch WSDL faults thrown by
 * operations invoked by another task/event.
 * 
 * @author aallway
 * @since 3.2
 */
public class CatchWsdlErrorEventMappingSection extends
        AbstractMessageMappingOutSection {

    /**
     * 
     */
    public CatchWsdlErrorEventMappingSection() {
        super();
        ILabelProvider labelProvider =
                new ScriptableLabelProvider(new ParameterLabelProvider());
        /* XPD-6974: Label provider to handle ERROR_DETAIL and ERROR_CODE nodes */
        setMapperLabelProvider(new MapperLabelProvider(
                JavaScriptBomContentProvider.getErrorParamSourceLabelProvider(),
                labelProvider));

    }

    @Override
    protected String getTitle() {
        return Messages.CatchErrorMapperSection_CatchErrorMapperSection_title;
    }

    @Override
    protected ITreeContentProvider createWsdlPartsSourceContentProvider() {
        return new CatchWsdlErrorEventWsdlFaultItemProvider();
    }

    @Override
    protected BaseScriptSection getScriptSection() {
        return new WebServiceScriptProperties(MappingDirection.OUT) {
            @Override
            protected Message getWsdlMessage() {
                Fault fault =
                        CatchWsdlErrorEventUtil
                                .getCaughtWsdlFault((Activity) getInput());
                if (fault != null) {
                    return fault.getMessage();
                }
                return null;
            }
        };
    }

    /**
     * @see com.tibco.xpd.implementer.resources.xpdl2.properties.AbstractMessageMappingOutSection#isValidTransfer(java.lang.Object,
     *      java.lang.Object)
     * 
     * @param source
     * @param target
     * @return
     */
    @Override
    public boolean isValidTransfer(Object source, Object target) {
        boolean validTransfer = super.isValidTransfer(source, target);
        if (!validTransfer) {
            // If the script grammar is JavaScript and both the target and
            // source are ConceptPaths then it should be valid to map
            String scriptGrammar = getGrammar();
            if (ScriptGrammarFactory.JAVASCRIPT.equals(scriptGrammar)
                    && source instanceof ConceptPath
                    && target instanceof ConceptPath) {
                return true;
            }

        }
        return validTransfer;
    }
}
