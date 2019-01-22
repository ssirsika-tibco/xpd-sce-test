/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.implementer.resources.xpdl2.properties.replyimmediate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.jface.viewers.IStructuredContentProvider;

import com.tibco.xpd.implementer.resources.xpdl2.properties.ActivityMessageWsdlMappingItemProvider;
import com.tibco.xpd.implementer.resources.xpdl2.properties.JavaScriptMappingItemProvider;
import com.tibco.xpd.implementer.resources.xpdl2.properties.XPathMappingItemProvider;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.processeditor.xpdl2.properties.StandardMappingUtil;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptGrammarFactory;
import com.tibco.xpd.processeditor.xpdl2.properties.script.SingleMappingCompositor;
import com.tibco.xpd.xpdl2.Activity;

/**
 * Mappings content provider for "Output Process Id" mappings (the reply
 * mappings for a start event configured to reply-immediately).
 * <p>
 * Provides the normasl JavaScript/XPath mapping grammar handling of
 * {@link ActivityMessageWsdlMappingItemProvider} with the addition of taking
 * special __PROCESS_ID__ into account.
 * 
 * @author aallway
 * @since 1 Aug 2012
 */
public class ReplyImmediateMappingContentProvider extends
        ActivityMessageWsdlMappingItemProvider {

    public ReplyImmediateMappingContentProvider() {
        /*
         * Direction is IN because it is from perspective of "WSDL DATA" so in a
         * "reply to service" mapping we are mapping OUT from process data IN to
         * WSDL output message data.
         */
        super(MappingDirection.IN, false);
    }

    /**
     * @see com.tibco.xpd.implementer.resources.xpdl2.properties.ActivityMessageWsdlMappingItemProvider#getDefaultScriptGrammar(com.tibco.xpd.xpdl2.Activity)
     * 
     * @param activity
     * @return
     */
    @Override
    protected String getDefaultScriptGrammar(Activity activity) {
        /*
         * For the moment at least we must force default grammar to be XPath as
         * process engine cannot deal with anything else.
         */
        return ScriptGrammarFactory.XPATH;
    }

    /**
     * @see com.tibco.xpd.implementer.resources.xpdl2.properties.ActivityMessageWsdlMappingItemProvider#createJavascriptMappingItemProvider()
     * 
     * @return
     */
    @Override
    protected IStructuredContentProvider createJavascriptMappingItemProvider() {
        return new JavaScriptMappingItemProvider(getDirection(), isWsdlInput()) {

            /**
             * @see com.tibco.xpd.implementer.resources.xpdl2.properties.BaseJavaScriptMappingItemProvider#resolveSourceItem(com.tibco.xpd.xpdl2.Activity,
             *      java.lang.String, com.tibco.xpd.mapper.MappingDirection)
             * 
             * @param activity
             * @param name
             * @param direction
             * @return
             */
            @Override
            protected Object resolveSourceItem(Activity activity, String name,
                    MappingDirection direction) {
                if (MappingDirection.IN.equals(direction)) {

                    if (StandardMappingUtil.REPLY_IMMEDIATE_PROCESS_ID_FORMALPARAMETER
                            .getName().equals(name)) {
                        return new ConceptPath(
                                StandardMappingUtil.REPLY_IMMEDIATE_PROCESS_ID_FORMALPARAMETER,
                                null);
                    }
                }

                return super.resolveSourceItem(activity, name, direction);
            }

        };
    }

    /**
     * @see com.tibco.xpd.implementer.resources.xpdl2.properties.ActivityMessageWsdlMappingItemProvider#createXpathMappingItemProvider()
     * 
     * @return
     */
    @Override
    protected IStructuredContentProvider createXpathMappingItemProvider() {
        return new XPathMappingItemProvider(getDirection(), isWsdlInput()) {
            /**
             * @see com.tibco.xpd.implementer.resources.xpdl2.properties.XPathMappingItemProvider#getMappingSourceItems(com.tibco.xpd.xpdl2.Activity,
             *      com.tibco.xpd.processeditor.xpdl2.properties.script.SingleMappingCompositor,
             *      com.tibco.xpd.mapper.MappingDirection)
             * 
             * @param activity
             * @param scriptMappingCompositor
             * @param direction
             * @return
             */
            @Override
            protected Collection<Object> getMappingSourceItems(
                    Activity activity,
                    SingleMappingCompositor scriptMappingCompositor,
                    MappingDirection direction) {

                if (MappingDirection.IN.equals(direction)) {
                    /*
                     * check for special process id field and return it's
                     * concept path (because super class will only handle real
                     * process data)
                     */
                    List<Object> items = new ArrayList<Object>();
                    for (String sourceItemName : scriptMappingCompositor
                            .getSourceItemNames()) {

                        if (StandardMappingUtil.REPLY_IMMEDIATE_PROCESS_ID_FORMALPARAMETER
                                .getName().equals(sourceItemName)) {
                            items.add(new ConceptPath(
                                    StandardMappingUtil.REPLY_IMMEDIATE_PROCESS_ID_FORMALPARAMETER,
                                    null));
                        }
                    }

                    return items;
                }

                return super.getMappingSourceItems(activity,
                        scriptMappingCompositor,
                        direction);
            }

        };
    }
}
