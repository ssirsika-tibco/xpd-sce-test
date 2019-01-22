/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.n2.cds.internal.script;



/**
 * @author mtorres
 */
public class CDSAdditionalInfoLabelProvider {//extends ProcessAdditionalInfoLabelProvider{
    
//    public CDSAdditionalInfoLabelProvider(Object input, IScriptRelevantData dataType) {
//        super(input, dataType);
//    }
//    
//    public String getAdditionalInfoLabel(){
//        if(getInput() instanceof JsClass){
//            return getJsClassAdditionalInfoLabel((JsClass) getInput());
//        } else {
//            return super.getAdditionalInfoLabel();
//        }
//    }   
//   
//    
//    public String getJsClassAdditionalInfoLabel(JsClass jsClass) {
//        if (jsClass != null) {
//            String template =
//                    Messages.ProcessAdditionalInfo_processData_template;
//            List<String> additionalAttributes = new ArrayList<String>();
//            //Add the name
//            additionalAttributes.add(jsClass.getName());
//            //Add the data type
//            String dataTypeStr = "";//$NON-NLS-1$
//            if(getDataType() != null){
//                dataTypeStr = getDataType().getType();
//            }
//            additionalAttributes.add(dataTypeStr);
//            //ReadOnly
//            String readOnly = Boolean.toString(false);
//            if(getDataType() instanceof ITypeResolution){
//                readOnly = Boolean.toString(((ITypeResolution)getDataType()).isReadOnly());
//            }
//            additionalAttributes.add(readOnly);
//            //Is Array
//            String isArray = Boolean.toString(false);
//            if(getDataType() != null){
//                isArray = Boolean.toString(getDataType().isArray());
//            }
//            additionalAttributes.add(isArray);
//            //Add the description
//            String description = "";//$NON-NLS-1$
//            additionalAttributes.add(description);
//            return formatMessage(template, additionalAttributes);
//        }
//        return null;
//    }
    

}
