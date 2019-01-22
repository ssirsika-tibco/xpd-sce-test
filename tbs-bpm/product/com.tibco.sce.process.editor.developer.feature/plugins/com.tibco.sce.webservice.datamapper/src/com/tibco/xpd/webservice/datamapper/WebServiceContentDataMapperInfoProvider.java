/*
 * Copyright (c) TIBCO Software Inc 2004, 2016. All rights reserved.
 */

package com.tibco.xpd.webservice.datamapper;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;

import com.tibco.xpd.implementer.resources.xpdl2.errorEvents.CatchWsdlErrorEventUtil;
import com.tibco.xpd.implementer.resources.xpdl2.properties.JavaScriptConceptUtil;
import com.tibco.xpd.implementer.resources.xpdl2.properties.filter.WsdlFilter.WsdlDirection;
import com.tibco.xpd.process.datamapper.common.ProcessDataMapperInfoProvider;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptUtil;
import com.tibco.xpd.processeditor.xpdl2.properties.StandardMappingUtil;
import com.tibco.xpd.validation.bpmn.developer.providers.WebSvcJavaScriptMappingRuleInfoProvider;
import com.tibco.xpd.validation.bpmn.rules.baserules.ProcessDataMappingRuleInfoProvider;
import com.tibco.xpd.xpdl2.Activity;

/**
 * Subclass of <code>ProcessDataMapperInfoProvider</code> to ensure that we can
 * use the existing info provider specific to Web Service.
 * 
 * @author sajain
 * @since Jan 20, 2016
 */
public class WebServiceContentDataMapperInfoProvider extends
        ProcessDataMapperInfoProvider {

    /**
     * WSDL direction.
     */
    private WsdlDirection wsdlDirection;

    /**
     * Object path for Error Code parameter.
     */
    public static final String ERROR_CODE_TOKEN = "$ERRORCODE"; //$NON-NLS-1$

    /**
     * Object path for Error Detail parameter.
     */
    public static final String ERROR_DETAIL_TOKEN = "$ERRORDETAIL"; //$NON-NLS-1$  

    /**
     * @param contentProvider
     * @param labelProvider
     */
    public WebServiceContentDataMapperInfoProvider(
            ITreeContentProvider contentProvider, ILabelProvider labelProvider,
            WsdlDirection wsdlDirection) {

        super(contentProvider, labelProvider);

        this.wsdlDirection = wsdlDirection;
    }

    /**
     * @see com.tibco.xpd.process.datamapper.common.ProcessDataMapperInfoProvider#createBaseInfoProvider(org.eclipse.jface.viewers.ITreeContentProvider)
     * 
     * @param contentProvider
     * @return
     */
    @Override
    protected ProcessDataMappingRuleInfoProvider createBaseInfoProvider(
            ITreeContentProvider contentProvider) {
        return new WebSvcJavaScriptMappingRuleInfoProvider(getContentProvider());
    }

    /**
     * @see com.tibco.xpd.datamapper.api.AbstractDataMapperInfoProvider#getObjectForPath(java.lang.Object,
     *      java.lang.Object)
     * 
     * @param objectPath
     * @param mapperInput
     * @return
     */
    @Override
    public Object getObjectForPath(String objectPath, Object mapperInput) {

        if (mapperInput instanceof Activity) {

            /*
             * XPD-8149: Saket: We need to specifically return the concept path
             * of ERROR_CODE/ERROR_DETAIL if TimeOutException is selected.
             */
            if (CatchWsdlErrorEventUtil
                    .isTimeoutExceptionSelectedForSoapJMSConsumer((Activity) mapperInput)) {

                if (ERROR_CODE_TOKEN.equals(objectPath)) {

                    return ConceptUtil
                            .getConceptPath(StandardMappingUtil.CATCH_ERRORCODE_FORMALPARAMETER);

                } else if (ERROR_DETAIL_TOKEN.equals(objectPath)) {

                    return ConceptUtil
                            .getConceptPath(StandardMappingUtil.CATCH_ERRORDETAIL_FORMALPARAMETER);
                }
            }

            ConceptPath formalConcept =
                    (new JavaScriptConceptUtil())
                            .resolveJavaScriptWSDLConceptPath((Activity) mapperInput,
                                    objectPath,
                                    wsdlDirection);

            return formalConcept;
        }

        return null;
    }
}
