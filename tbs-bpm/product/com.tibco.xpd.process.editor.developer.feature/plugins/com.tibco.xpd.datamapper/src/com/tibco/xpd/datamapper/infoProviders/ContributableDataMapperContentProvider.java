/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.datamapper.infoProviders;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.tibco.xpd.datamapper.api.AbstractDataMapperContentContributor;
import com.tibco.xpd.datamapper.scripts.IScriptDataMapperProvider;
import com.tibco.xpd.resources.util.XpdUtil;
import com.tibco.xpd.xpdExtension.ScriptInformation;

/**
 * Content provider for the Data Mapper that wraps all the contributed content
 * (of different types) from various contributors into wrapped objects. It also
 * provides Scripts content as its not contributed through extension point
 * (because the Mapper only understands ScriptInformation and can't handle
 * wrapped objects)
 * 
 * @author Ali
 * @since 20 Feb 2015
 */
class ContributableDataMapperContentProvider implements ITreeContentProvider {

    List<AbstractDataMapperContentContributor> contributionsList;

    private String mappingContext;

    private boolean isRightHandSide;

    private DataMapperScriptsContentProvider scriptsContentProvider;

    private IScriptDataMapperProvider scriptDataMapperProvider;

    private Object input;

    /**
     * @param sdmProvider The ScriptDataMapper provider.
     * @param mappingContext The mapping context.
     * @param isRightHandSide true if this is for target mappings.
     * @param isInputMapping true if this is for input mappings.
     */
    public ContributableDataMapperContentProvider(
            IScriptDataMapperProvider sdmProvider, String mappingContext,
            boolean isRightHandSide, boolean isInputMapping) {
        super();
        this.mappingContext = mappingContext;
        this.isRightHandSide = isRightHandSide;

        this.scriptDataMapperProvider = sdmProvider;

        if (!isRightHandSide) {
            scriptsContentProvider =
                    new DataMapperScriptsContentProvider(sdmProvider,
                            isInputMapping);
        }
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
		Object currentInput = input;

        input = newInput;

		/* Sid ACE-8742 Should not need to rebuild the mapper contributions list if the input isn't changing */
		if (contributionsList == null || contributionsList.isEmpty() || !XpdUtil.safeEquals(newInput, currentInput))
		{
			contributionsList = new ArrayList<>();

			contributionsList.addAll(
					DataMapperContentContributionHelper.getApplicableContributions(mappingContext, isRightHandSide));
		}
    }

    /**
     * @see org.eclipse.jface.viewers.ITreeContentProvider#getElements(java.lang.Object)
     * 
     * @param inputElement
     * @return
     */
    @Override
    public Object[] getElements(Object inputElement) {

        List<Object> contribContents = new ArrayList<>();
        if (scriptsContentProvider != null) {
            Object scripts[] = scriptsContentProvider.getElements(inputElement);
            for (Object s : scripts) {
                if (s instanceof ScriptInformation) {
                    contribContents.add(s);
                }
            }
        }

        if (contributionsList == null) {
            contributionsList = new ArrayList<>();

            contributionsList
                    .addAll(DataMapperContentContributionHelper
                            .getApplicableContributions(mappingContext,
                                    isRightHandSide));
        }
        for (AbstractDataMapperContentContributor contrib : contributionsList) {

            if (contrib.getInfoProvider() != null
                    && contrib.getInfoProvider().getContentProvider() != null) {

                Object[] contibutionElements =
                        contrib.getInfoProvider().getContentProvider()
                                .getElements(inputElement);
                if (contibutionElements != null) {
                    for (Object elem : contibutionElements) {

                        if (elem instanceof ScriptInformation) {
                            contribContents.add(elem);
                        } else {
                            WrappedContributedContent cont =
                                    new WrappedContributedContent(contrib,
                                            elem, null,
                                            scriptDataMapperProvider, input);
                            contribContents.add(cont);
                        }
                    }
                }
            }
        }
        return contribContents.toArray();
    }

    /**
     * @see org.eclipse.jface.viewers.ITreeContentProvider#getChildren(java.lang.Object)
     * 
     * @param parentElement
     * @return
     */
    @Override
    public Object[] getChildren(Object parentElement) {

        List<WrappedContributedContent> contribContents = new ArrayList<>();
        if (parentElement instanceof WrappedContributedContent) {

            WrappedContributedContent parent =
                    (WrappedContributedContent) parentElement;

            if (parent.getContributor().getInfoProvider() != null
                    && parent.getContributor().getInfoProvider()
                            .getContentProvider() != null) {
                Object[] contributedElements =
                        parent.getContributor().getInfoProvider()
                                .getContentProvider()
                                .getChildren(parent.getContributedObject());

                if (contributedElements != null) {
                    for (Object elem : contributedElements) {

                        WrappedContributedContent cont =
                                new WrappedContributedContent(
                                        parent.getContributor(), elem, parent,
                                        scriptDataMapperProvider, input);

                        contribContents.add(cont);

                    }
                }
            }
        }
        return contribContents.toArray();

    }

    /**
     * @see org.eclipse.jface.viewers.ITreeContentProvider#getParent(java.lang.Object)
     * 
     * @param element
     * @return
     */
    @Override
    public Object getParent(Object element) {
        if (element instanceof WrappedContributedContent) {

            WrappedContributedContent parent =
                    (WrappedContributedContent) element;

            return parent.getParent();
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
        if (element instanceof WrappedContributedContent) {

            WrappedContributedContent parent =
                    (WrappedContributedContent) element;

            if (parent.getContributor().getInfoProvider() != null
                    && parent.getContributor().getInfoProvider()
                            .getContentProvider() != null) {

                return parent.getContributor().getInfoProvider()
                        .getContentProvider()
                        .hasChildren(parent.getContributedObject());
            }
        }
        return false;
    }

}
