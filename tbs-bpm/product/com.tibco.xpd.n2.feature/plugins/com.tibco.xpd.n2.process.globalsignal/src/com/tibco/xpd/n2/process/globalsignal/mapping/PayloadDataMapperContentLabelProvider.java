/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.n2.process.globalsignal.mapping;

import org.eclipse.swt.graphics.Image;

import com.tibco.xpd.globalSignalDefinition.PayloadDataField;
import com.tibco.xpd.n2.process.globalsignal.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptLabelProvider;
import com.tibco.xpd.processeditor.xpdl2.properties.CorrelationDataFolder;
import com.tibco.xpd.processwidget.ProcessWidgetConstants;
import com.tibco.xpd.processwidget.ProcessWidgetPlugin;
import com.tibco.xpd.resources.util.WorkingCopyUtil;

/**
 * Label Provider for Global Signal Payload.
 * 
 * @author kthombar
 * @since Feb 3, 2015
 */
public class PayloadDataMapperContentLabelProvider extends ConceptLabelProvider {

    /**
     * @see org.eclipse.jface.viewers.ILabelProvider#getImage(java.lang.Object)
     * 
     * @param element
     * @return
     */
    @Override
    public Image getImage(Object element) {
        Image image = null;
        if (element instanceof String) {
            image =
                    ProcessWidgetPlugin.getDefault().getImageRegistry()
                            .get(ProcessWidgetConstants.IMG_INFO_ICON);
        } else if (element instanceof PayloadConceptPath) {
            /*
             * get and show payload icons
             */
            PayloadDataField payloadDataField =
                    ((PayloadConceptPath) element).getPayloadDataField();

            if (payloadDataField != null) {

                image = WorkingCopyUtil.getImage(payloadDataField);
            }
        } else if (element instanceof CorrelationDataFolder) {
            image = ((CorrelationDataFolder) element).getImage();
        }
        return image;
    }

    /**
     * @see org.eclipse.jface.viewers.ILabelProvider#getText(java.lang.Object)
     * 
     * @param element
     * @return
     */
    @Override
    public String getText(Object element) {
        String text = ""; //$NON-NLS-1$
        if (element instanceof CorrelationDataFolder) {
            /*
             * If the element is Correlation Data Folder then set its text as
             * "Correlation Parameters"
             */
            text =
                    Messages.PayloadDataMapperContentLabelProvider_CorrelationParameters_label;

        } else if (element instanceof PayloadConceptPath) {

            PayloadConceptPath payLoadCP = (PayloadConceptPath) element;

            text = super.getText(payLoadCP.getPayloadDataField());

        } else if (element instanceof String) {
            text = (String) element;
        }
        return text;
    }
}
