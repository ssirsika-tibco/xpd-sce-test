/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.n2.globalsignal.resource.ui;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.swt.graphics.Image;

import com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.groups.AbstractAssetGroup;
import com.tibco.xpd.globalSignalDefinition.GlobalSignalDefinitionFactory;
import com.tibco.xpd.globalSignalDefinition.GlobalSignalDefinitionPackage;
import com.tibco.xpd.globalSignalDefinition.PayloadDataField;
import com.tibco.xpd.n2.globalsignal.resource.internal.Messages;
import com.tibco.xpd.resources.util.WorkingCopyUtil;

/**
 * Payload data group.
 * 
 * @author sajain
 * @since Feb 24, 2015
 */
public class PayloadDataGroup extends AbstractAssetGroup {

    private static final String TITLE = Messages.PayloadDataGroup_title;

    /**
     * Default constructor
     * 
     * @param parent
     */
    public PayloadDataGroup(EObject parent) {
        super(parent);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.groups.BpmArtefactGroup
     * #getFeature()
     */
    @Override
    public EStructuralFeature getFeature() {
        return GlobalSignalDefinitionPackage.eINSTANCE
                .getGlobalSignal_PayloadDataFields();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.groups.BpmArtefactGroup
     * #getImage()
     */
    @Override
    public Image getImage() {
        return WorkingCopyUtil.getImage(GlobalSignalDefinitionFactory.eINSTANCE
                .createPayloadDataField());
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.groups.BpmArtefactGroup
     * #getText()
     */
    @Override
    public String getText() {
        return TITLE;
    }

    @Override
    public EClass getReferenceType() {
        return GlobalSignalDefinitionPackage.eINSTANCE.getPayloadDataField();
    }

    @Override
    public List<?> getChildren() {

        List<Object> filtered = new ArrayList<Object>();

        List<?> allChildren = super.getChildren();

        for (Object next : allChildren) {

            if (next instanceof PayloadDataField) {

                PayloadDataField pdf = (PayloadDataField) next;

                filtered.add(pdf);

            }
        }
        return filtered;
    }

}