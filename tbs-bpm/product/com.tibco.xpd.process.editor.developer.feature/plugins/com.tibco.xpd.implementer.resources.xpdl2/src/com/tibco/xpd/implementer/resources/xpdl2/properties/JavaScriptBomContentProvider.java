/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.implementer.resources.xpdl2.properties;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.graphics.Image;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesConsts;
import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesPlugin;
import com.tibco.xpd.implementer.resources.xpdl2.properties.filter.WsdlFilter.WsdlDirection;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptContentProvider;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptLabelProvider;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptUtil;
import com.tibco.xpd.processeditor.xpdl2.properties.StandardMappingUtil;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptableLabelProvider;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DirectionType;

/**
 * For Java Script mappings for a process with ActiveMatrixBPM set, the BOM
 * content equivalent to the WSDL is shown on the Service side of the WSDL
 * 
 * @author rsomayaj
 * 
 */
public class JavaScriptBomContentProvider implements ITreeContentProvider {

    private final MappingDirection direction;

    private Activity activity;

    private final DirectionType directionToService;

    private final WsdlDirection wsdlDirection;

    private ConceptContentProvider conceptContentProvider;

    /**
     * @param direction
     * @param directionToService
     */
    public JavaScriptBomContentProvider(MappingDirection direction,
            DirectionType directionToService, WsdlDirection wsdlDirection) {
        this.direction = direction;
        this.directionToService = directionToService;
        this.wsdlDirection = wsdlDirection;
        conceptContentProvider = new ConceptContentProvider(direction,
                !DirectionType.IN_LITERAL.equals(directionToService));
    }

    /**
     * @see org.eclipse.jface.viewers.ITreeContentProvider#getChildren(java.lang.Object)
     * 
     * @param parentElement
     * @return
     */
    @Override
    public Object[] getChildren(Object parentElement) {
        if (parentElement instanceof Activity) {
            activity = (Activity) parentElement;

            conceptContentProvider.setActivity(activity);

        } else if (parentElement instanceof ConceptPath) {
            return conceptContentProvider.getChildren(parentElement);

        }
        return Collections.EMPTY_LIST.toArray();
    }

    /**
     * Returns children for Catch Error Event set to catch
     * TimeoutException.Returns ERROR_CODE & ERROR_DETAIL elements.
     * 
     * @return children for Catch Error Event set to catch
     *         TimeoutException.Returns ERROR_CODE & ERROR_DETAIL elements.
     */
    private Object[] getChildrenForTimeoutException() {

        List<Object> errorParams = new ArrayList<Object>();

        errorParams.add(ConceptUtil.getConceptPath(
                StandardMappingUtil.CATCH_ERRORCODE_FORMALPARAMETER));

        errorParams.add(ConceptUtil.getConceptPath(
                StandardMappingUtil.CATCH_ERRORDETAIL_FORMALPARAMETER));

        return errorParams.toArray();
    }

    /**
     * @param clsType
     * @return
     */
    public static String getID(EObject eObject) {
        Resource resource = eObject.eResource();
        if (resource != null) {
            return resource.getURIFragment(eObject);

        }
        return null;

    }

    /**
     * @see org.eclipse.jface.viewers.ITreeContentProvider#getParent(java.lang.Object)
     * 
     * @param element
     * @return
     */
    @Override
    public Object getParent(Object element) {
        if (element instanceof ConceptPath) {
            return ((ConceptPath) element).getParent();
        }
        return null;
    }

    /**
     * @see org.eclipse.jface.viewers.ITreeContentProvider#hasChildren(java.lang.Object)
     * 
     * @param element
     * @return
     */
    @Override
    public boolean hasChildren(Object element) {
        boolean hasChildren = false;
        if (element instanceof ConceptPath) {
            return conceptContentProvider.hasChildren(element);
        }
        return hasChildren;
    }

    /**
     * @see org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java.lang.Object)
     * 
     * @param inputElement
     * @return
     */
    @Override
    public Object[] getElements(Object inputElement) {
        return getChildren(inputElement);
    }

    /**
     * @see org.eclipse.jface.viewers.IContentProvider#dispose()
     * 
     */
    @Override
    public void dispose() {

    }

    /**
     * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer,
     *      java.lang.Object, java.lang.Object)
     * 
     * @param viewer
     * @param oldInput
     * @param newInput
     */
    @Override
    public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {

    }

    /**
     * @return A label provider that will understand anything this content
     *         provider supplies as content for left hand side of error
     *         mappings.
     */
    public static ILabelProvider getErrorParamSourceLabelProvider() {
        ILabelProvider lp = new ConceptLabelProvider() {
            @Override
            public Image getImage(Object element) {
                if (element instanceof ConceptPath) {
                    ConceptPath cp = (ConceptPath) element;
                    if (cp.getItem() == StandardMappingUtil.CATCH_ERRORCODE_FORMALPARAMETER
                            || cp.getItem() == StandardMappingUtil.CATCH_ERRORDETAIL_FORMALPARAMETER) {
                        return Xpdl2ResourcesPlugin.getDefault()
                                .getImageRegistry()
                                .get(Xpdl2ResourcesConsts.IMG_ERROR_EVENT_ICON12);
                    }
                }
                return super.getImage(element);
            }
        };

        return new ScriptableLabelProvider(lp);
    }
}
