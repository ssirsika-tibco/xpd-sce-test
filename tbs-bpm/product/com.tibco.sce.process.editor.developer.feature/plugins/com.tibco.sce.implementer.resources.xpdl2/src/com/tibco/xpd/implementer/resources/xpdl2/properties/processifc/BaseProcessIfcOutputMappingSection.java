/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.implementer.resources.xpdl2.properties.processifc;

import java.util.Collection;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IPluginContribution;
import org.eclipse.wst.wsdl.Part;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.implementer.resources.xpdl2.Activator;
import com.tibco.xpd.implementer.resources.xpdl2.ImageConstants;
import com.tibco.xpd.implementer.resources.xpdl2.internal.Messages;
import com.tibco.xpd.implementer.script.PartProxy;
import com.tibco.xpd.mapper.IMappingTransferValidator;
import com.tibco.xpd.mapper.ITransformSection;
import com.tibco.xpd.mapper.MapperLabelProvider;
import com.tibco.xpd.mapper.MapperViewerInput;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.processeditor.xpdl2.properties.AbstractActivityMappingProblemMarkerHandlingSection;
import com.tibco.xpd.processeditor.xpdl2.properties.IMappingCommandFactory;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdExtension.AssociatedParameter;
import com.tibco.xpd.xpdl2.DirectionType;

/**
 * @author NWilson
 * 
 */
public abstract class BaseProcessIfcOutputMappingSection extends
        AbstractActivityMappingProblemMarkerHandlingSection implements IPluginContribution {

    @Override
    protected IStructuredContentProvider createMappingContentProvider() {
        return new ProcessIfcMappingContentProvider(DirectionType.IN_LITERAL);
        // return new
        // ActivityMessageWsdlMappingItemProvider(MappingDirection.IN);
    }

    @Override
    protected ITreeContentProvider createSourceContentProvider() {
        return new ParamMappingContentProvider(DirectionType.IN_LITERAL);
    }

    @Override
    protected ITreeContentProvider createTargetContentProvider() {
        return new ProcIfcPartMappingContentProvider(DirectionType.IN_LITERAL);
    }

    @Override
    protected IMappingCommandFactory getMappingCommandFactory() {
        return new ProcessIfcMappingCmdFactory(MappingDirection.IN);
    }

    @Override
    protected String getTitle() {
        return Messages.ProcessIfcEndEventMappingSection_ProcessToService_title;
    }

    // The mapping section required to be shown as a read-only section.
    @Override
    protected IMappingTransferValidator getTransferValidator() {
        return new IMappingTransferValidator() {
            public boolean isValidTransfer(Object source, Object target) {
                return false;
            }

            public boolean canAcceptMultipleInputs(Object target) {
                return false;
            }
        };
    }

    public BaseProcessIfcOutputMappingSection() {
        super(MappingDirection.IN);
        ILabelProvider labelProvider = new ILabelProvider() {
            public Image getImage(Object element) {
                if (element instanceof AssociatedParameter) {
                    return WorkingCopyUtil
                            .getImage(ProcessInterfaceUtil
                                    .getProcessRelevantDataFromAssociatedParam((AssociatedParameter) element));
                } else if (element instanceof Part) {
                    return Activator.getDefault().getImageRegistry()
                            .get(ImageConstants.PART);
                } else if (element instanceof EObject) {
                    return WorkingCopyUtil.getImage((EObject) element);
                }
                return null;
            }

            public String getText(Object element) {
                String text;
                if (element instanceof Part) {
                    text = ((Part) element).getName();
                } else if (element instanceof EObject) {
                    text = WorkingCopyUtil.getText((EObject) element);
                } else {
                    text = element.toString();
                }
                return text;
            }

            public void addListener(ILabelProviderListener listener) {

            }

            public void dispose() {

            }

            public boolean isLabelProperty(Object element, String property) {
                return false;
            }

            public void removeListener(ILabelProviderListener listener) {
            }
        };

        setMapperLabelProvider(new MapperLabelProvider(labelProvider,
                labelProvider));

    }

    @Override
    public void setInput(Collection<?> items) {
        super.setInput(items);
        EObject input = getInput();
        if (input != null) {
            MapperViewerInput mapperInput =
                    new MapperViewerInput(input, input, input);
            setMapperViewerInput(mapperInput);
            ITransformSection transform = getCurrentTransformSection();
            if (transform != null) {
                transform.setOwner(input, MappingDirection.IN);
            }
        }
    }

    @Override
    public void dispose() {
        PartProxy.clearParts();
        super.dispose();
    }

    public String getLocalId() {
        return "developer.interfaceEndEventMapping"; //$NON-NLS-1$
    }

    public String getPluginId() {
        return Activator.PLUGIN_ID;
    }
}
