/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.n2.globalsignal.resource.wizards;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.events.ModifyListener;

import com.tibco.xpd.analyst.resources.xpdl2.wizards.pages.AbstractSpecialFolderFileSelectionPage;
import com.tibco.xpd.globalSignalDefinition.GlobalSignal;
import com.tibco.xpd.globalSignalDefinition.GlobalSignalDefinitionPackage;
import com.tibco.xpd.globalSignalDefinition.PayloadDataField;
import com.tibco.xpd.n2.globalsignal.resource.internal.Messages;
import com.tibco.xpd.n2.globalsignal.resource.ui.PayloadDataGroup;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdProjectResourceFactory;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.util.WorkingCopyUtil;

/**
 * GSD special folder file selection page.
 * 
 * @author aprasad
 * @since 10-Feb-2015
 */
public class GSDSpecialFolderFileSelectionPage extends
        AbstractSpecialFolderFileSelectionPage {

    private GlobalSignal selectedGlobalSignal = null;

    private PayloadDataField selectedPdf = null;

    /**
     * GSD special folder file selection page.
     * 
     * @param title
     * @param description
     */
    public GSDSpecialFolderFileSelectionPage(String title, String description) {

        super(title, description);
        setPageComplete(false);

    }

    /**
     * @see com.tibco.xpd.analyst.resources.xpdl2.wizards.pages.AbstractSpecialFolderFileSelectionPage#getFileNotExistMessage()
     * 
     * @return
     */
    @Override
    protected String getFileNotExistMessage() {

        return Messages.GSDSpecialFolderFileSelectionPage_Label_FileNotExist;
    }

    /**
     * @see com.tibco.xpd.analyst.resources.xpdl2.wizards.pages.AbstractSpecialFolderFileSelectionPage#getFileNameEmptyMessage()
     * 
     * @return
     */
    @Override
    protected String getFileNameEmptyMessage() {

        return Messages.GSDSpecialFolderFileSelectionPage_Label_FileNameEmpty;
    }

    /**
     * @see com.tibco.xpd.analyst.resources.xpdl2.wizards.pages.AbstractSpecialFolderFileSelectionPage#getFileTypeNameLabel()
     * 
     * @return
     */
    @Override
    protected String getFileTypeNameLabel() {

        return Messages.GSDSpecialFolderFileSelectionPage_Label_FileName;
    }

    /**
     * @see com.tibco.xpd.analyst.resources.xpdl2.wizards.pages.AbstractSpecialFolderFileSelectionPage#getFileSelectionMessage()
     * 
     * @return
     */
    @Override
    protected String getFileSelectionMessage() {

        return Messages.GSDSpecialFolderFileSelectionPage_Label_SelectFile;
    }

    /**
     * @see com.tibco.xpd.analyst.resources.xpdl2.wizards.pages.CreationWizardProjectSelectionPage#getSpecialFolderKind()
     * 
     * @return
     */
    @Override
    protected String getSpecialFolderKind() {
        return "gsd"; //$NON-NLS-1$
    }

    /**
     * @see com.tibco.xpd.analyst.resources.xpdl2.wizards.pages.CreationWizardProjectSelectionPage#getFileFolderLabelText()
     * 
     * @return
     */
    @Override
    protected String getFileFolderLabelText() {

        return Messages.GSDSpecialFolderFileSelectionPage_Label_FileFolder;
    }

    /**
     * @see com.tibco.xpd.analyst.resources.xpdl2.wizards.pages.CreationWizardProjectSelectionPage#getPageInternalDescription()
     * 
     * @return
     */
    @Override
    protected String getPageInternalDescription() {
        return Messages.GSDSpecialFolderFileSelectionPage_Label_PageInternalDesc;
    }

    /**
     * @see com.tibco.xpd.analyst.resources.xpdl2.wizards.pages.CreationWizardProjectSelectionPage#getNotCorrectSpecialFolderMessage()
     * 
     * @return
     */
    @Override
    protected String getNotCorrectSpecialFolderMessage() {
        return Messages.GSDSpecialFolderFileSelectionPage_Msg_SelectGSDFolder;
    }

    /**
     * @see com.tibco.xpd.analyst.resources.xpdl2.wizards.pages.CreationWizardProjectSelectionPage#getBrowseSpecialFolderTitle()
     * 
     * @return
     */
    @Override
    protected String getBrowseSpecialFolderTitle() {
        return Messages.GSDSpecialFolderFileSelectionPage_Msg_BrowseSpecialFolder;
    }

    @Override
    protected WorkingCopy getWorkingCopy(Object item) {
        WorkingCopy wc = null;

        if (item != null) {
            if (item instanceof IFile) {
                XpdProjectResourceFactory fact =
                        XpdResourcesPlugin
                                .getDefault()
                                .getXpdProjectResourceFactory(((IFile) item).getProject());

                if (fact != null) {
                    wc = fact.getWorkingCopy((IFile) item);

                    // If this is not GSD package then reset wc
                    if (wc != null
                            && wc.getWorkingCopyEPackage() != GlobalSignalDefinitionPackage.eINSTANCE) {
                        wc = null;
                    }
                }

            } else if (item instanceof EObject) {
                wc = WorkingCopyUtil.getWorkingCopyFor((EObject) item);
            }
        }

        return wc;
    }

    /**
     * @param txtModifyListener
     */
    public void addSignalModifyListeners(ModifyListener txtModifyListener) {
        txtPackagesFolder.addModifyListener(txtModifyListener);

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.ui.properties.CreationWizardContainerSelectionPage#init
     * (org.eclipse.jface.viewers.IStructuredSelection)
     */
    @Override
    public void init(IStructuredSelection selection) {

        super.init(selection);

        if (selection != null && !selection.isEmpty()) {

            Object selectedElement = selection.getFirstElement();

            if (selectedElement instanceof GlobalSignal) {
                selectedGlobalSignal = (GlobalSignal) selectedElement;
            }

            if (selectedElement instanceof PayloadDataField) {
                selectedPdf = (PayloadDataField) selectedElement;
            }

            if (selectedElement instanceof PayloadDataGroup) {

                PayloadDataGroup payloadsRoot =
                        (PayloadDataGroup) selectedElement;

                if (payloadsRoot.getParent() instanceof GlobalSignal) {

                    selectedGlobalSignal =
                            (GlobalSignal) (payloadsRoot.getParent());
                }
            }
        }
    }

    /**
     * @see com.tibco.xpd.analyst.resources.xpdl2.wizards.pages.AbstractSpecialFolderFileSelectionPage#getEContainer()
     * 
     * @return
     */
    @Override
    public EObject getEContainer() {

        if (selectedGlobalSignal != null) {
            return selectedGlobalSignal;
        } else if (selectedPdf != null) {
            return selectedPdf.eContainer();
        }

        return super.getEContainer();
    }
}
