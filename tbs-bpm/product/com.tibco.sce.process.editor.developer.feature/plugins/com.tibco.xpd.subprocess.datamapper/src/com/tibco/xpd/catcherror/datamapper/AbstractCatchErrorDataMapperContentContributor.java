/*
 * Copyright (c) TIBCO Software Inc 2004, 2016. All rights reserved.
 */

package com.tibco.xpd.catcherror.datamapper;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.swt.graphics.Image;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesConsts;
import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesPlugin;
import com.tibco.xpd.implementer.resources.xpdl2.properties.ParameterLabelProvider;
import com.tibco.xpd.process.datamapper.common.AbstractProcessDataMapperContentContributor;
import com.tibco.xpd.process.datamapper.common.ProcessDataMapperInfoProvider;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.processeditor.xpdl2.properties.StandardMappingUtil;
import com.tibco.xpd.xpdExtension.CatchErrorMappings;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Event;
import com.tibco.xpd.xpdl2.IntermediateEvent;
import com.tibco.xpd.xpdl2.Message;
import com.tibco.xpd.xpdl2.ResultError;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * This abstract class provides basic implementation for catch error related
 * content contribution. This provides contents fro Catch All and Catch
 * Sub-Process
 * 
 * @author ssirsika
 * @since 04-Mar-2016
 */
public abstract class AbstractCatchErrorDataMapperContentContributor extends
        AbstractProcessDataMapperContentContributor {

    /**
     * @see com.tibco.xpd.n2.pe.datamapper.AbstractProcessDataMapperContentContributor#createItemProvider()
     * 
     * @return
     */
    @Override
    protected ITreeContentProvider createItemProvider() {
        return new CatchErrorDataMapperSourceContentProvider();
    }

    /**
     * Provides info provide which gives information related to 'Error Code' and
     * 'Error Detail' attributes.
     * 
     * @param contentProvider
     * @param labelProvider
     * @return
     */
    @Override
    protected ProcessDataMapperInfoProvider createProcessDataInfoProvider(
            ITreeContentProvider contentProvider, ILabelProvider labelProvider) {
        return new ProcessDataMapperInfoProvider(contentProvider, labelProvider) {
            /**
             * @see com.tibco.xpd.process.datamapper.common.ProcessDataMapperInfoProvider#getObjectForPath(java.lang.String,
             *      java.lang.Object)
             * 
             * @param objectPath
             * @param mapperInput
             * @return
             */
            @Override
            public Object getObjectForPath(String objectPath, Object mapperInput) {

                if (mapperInput instanceof Activity) {

                    Activity activity = (Activity) mapperInput;

                    Event event = activity.getEvent();

                    if (event instanceof IntermediateEvent) {
                        IntermediateEvent intermediateEvent =
                                (IntermediateEvent) event;

                        ResultError resErr = intermediateEvent.getResultError();

                        if (resErr != null) {
                            Object catchErrMappingsObj =
                                    Xpdl2ModelUtil
                                            .getOtherElement(resErr,
                                                    XpdExtensionPackage.eINSTANCE
                                                            .getDocumentRoot_CatchErrorMappings());

                            if (catchErrMappingsObj instanceof CatchErrorMappings) {
                                CatchErrorMappings cem =
                                        (CatchErrorMappings) catchErrMappingsObj;

                                Message msg = cem.getMessage();

                                if (msg != null) {

                                    if (StandardMappingUtil.CATCH_ERRORCODE_FORMALPARAMETER
                                            .getName().equals(objectPath)) {

                                        return new ConceptPath(
                                                StandardMappingUtil.CATCH_ERRORCODE_FORMALPARAMETER,
                                                null);

                                    } else if (StandardMappingUtil.CATCH_ERRORDETAIL_FORMALPARAMETER
                                            .getName().equals(objectPath)) {

                                        return new ConceptPath(
                                                StandardMappingUtil.CATCH_ERRORDETAIL_FORMALPARAMETER,
                                                null);

                                    }
                                }
                            }
                        }
                    }
                }

                return super.getObjectForPath(objectPath, mapperInput);
            }
        };

    }

    /**
     * @see com.tibco.xpd.n2.pe.datamapper.AbstractProcessDataMapperContentContributor#createLabelProvider()
     * 
     * @return
     */
    @Override
    protected ParameterLabelProvider createLabelProvider() {
        return new ParameterLabelProvider() {
            /**
             * @see com.tibco.xpd.implementer.resources.xpdl2.properties.ParameterLabelProvider#getImage(java.lang.Object)
             * 
             * @param element
             * @return
             */
            @Override
            public Image getImage(Object element) {
                // override the getImage method to provide correct error images
                // fir 'Error Code' and 'Error Detail'.
                if (element instanceof ConceptPath) {
                    ConceptPath cp = (ConceptPath) element;
                    if (cp.getItem() == StandardMappingUtil.CATCH_ERRORCODE_FORMALPARAMETER
                            || cp.getItem() == StandardMappingUtil.CATCH_ERRORDETAIL_FORMALPARAMETER) {
                        return Xpdl2ResourcesPlugin
                                .getDefault()
                                .getImageRegistry()
                                .get(Xpdl2ResourcesConsts.IMG_ERROR_EVENT_ICON12);
                    }
                }
                return super.getImage(element);
            }
        };
    }

}
