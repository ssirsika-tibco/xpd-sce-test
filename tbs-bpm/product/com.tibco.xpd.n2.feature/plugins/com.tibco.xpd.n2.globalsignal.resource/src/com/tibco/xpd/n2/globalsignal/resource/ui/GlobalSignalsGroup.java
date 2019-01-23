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
import com.tibco.xpd.globalSignalDefinition.GlobalSignal;
import com.tibco.xpd.globalSignalDefinition.GlobalSignalDefinitionFactory;
import com.tibco.xpd.globalSignalDefinition.GlobalSignalDefinitionPackage;
import com.tibco.xpd.n2.globalsignal.resource.internal.Messages;
import com.tibco.xpd.resources.util.WorkingCopyUtil;

/**
 * Group for all global signals in a GSD.
 * 
 * @author sajain
 * @since Feb 21, 2015
 */
public class GlobalSignalsGroup extends AbstractAssetGroup {

    private static final String TITLE = Messages.GlobalSignalsGroup_title;

    /**
     * Default constructor
     * 
     * @param parent
     */
    public GlobalSignalsGroup(EObject parent) {
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
                .getGlobalSignalDefinitions_GlobalSignals();
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
                .createGlobalSignal());
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
        return GlobalSignalDefinitionPackage.eINSTANCE.getGlobalSignal();
    }

    @Override
    public List<?> getChildren() {

        List<Object> filtered = new ArrayList<Object>();

        List<?> allChildren = super.getChildren();

        for (Object next : allChildren) {

            if (next instanceof GlobalSignal) {

                GlobalSignal gs = (GlobalSignal) next;

                filtered.add(gs);

            }
        }
        return filtered;
    }

}