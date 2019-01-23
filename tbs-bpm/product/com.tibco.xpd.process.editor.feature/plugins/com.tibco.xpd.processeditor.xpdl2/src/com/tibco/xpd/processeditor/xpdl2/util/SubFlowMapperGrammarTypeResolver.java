package com.tibco.xpd.processeditor.xpdl2.util;

import com.tibco.xpd.processeditor.xpdl2.properties.script.IMapperGrammarTypeResolver;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptGrammarFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.SubFlow;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Mapper grammar resolver for Call Sub-Process activity.
 * 
 * @author sajain
 * @since Jan 19, 2016
 */
public class SubFlowMapperGrammarTypeResolver implements
        IMapperGrammarTypeResolver {

    public SubFlowMapperGrammarTypeResolver() {
    }

    @Override
    public String getMapperGrammarType(Activity activity,
            DirectionType direction) {

        SubFlow sf = SubProcUtil.getSubFlow(activity);

        if (sf != null) {

            if (DirectionType.IN_LITERAL.equals(direction)
                    && null != Xpdl2ModelUtil.getOtherElement(sf,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_InputMappings())) {

                return ScriptGrammarFactory.DATAMAPPER;

            } else if (DirectionType.OUT_LITERAL.equals(direction)
                    && null != Xpdl2ModelUtil.getOtherElement(sf,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_OutputMappings())) {

                return ScriptGrammarFactory.DATAMAPPER;
            }
        }

        return null;
    }

}
