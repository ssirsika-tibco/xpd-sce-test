/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.datamapper.infoProviders;

import java.util.Collection;

import com.tibco.xpd.datamapper.api.AbstractDataMapperContentContributor;
import com.tibco.xpd.datamapper.api.IScriptGeneratorInfoProvider;
import com.tibco.xpd.datamapper.api.JavaScriptStringBuilder;
import com.tibco.xpd.xpdExtension.ScriptDataMapper;

/**
 * Data Mapper script generation info provider that deals with WrappedItems and
 * delegates all methods to appropriate scriptInforProvider
 * 
 * @author Ali
 * @since 25 Mar 2015
 */
public class DataMapperScriptGeneratorInfoProvider implements
        IScriptGeneratorInfoProvider {

    private String mappingContext;

    private boolean isRightHandSide;

    /**
     * @param mappingContext
     * @param isRightHandSide
     */
    public DataMapperScriptGeneratorInfoProvider(String mappingContext,
            boolean isRightHandSide) {
        this.mappingContext = mappingContext;
        this.isRightHandSide = isRightHandSide;
    }

    /**
     * @see com.tibco.xpd.datamapper.api.IScriptGeneratorInfoProvider#getAssignmentStatement(java.lang.Object,
     *      java.lang.String)
     * 
     * @param object
     * @param rhsObjectStatement
     * @return
     */
    @Override
    public String getAssignmentStatement(Object object,
            String rhsObjectStatement, String jsVarAlias) {
        if (object instanceof WrappedContributedContent) {

            WrappedContributedContent wrappedElement =
                    ((WrappedContributedContent) object);
            AbstractDataMapperContentContributor contrib =
                    wrappedElement.getContributor();

            if (contrib != null) {
                IScriptGeneratorInfoProvider scriptGeneratorInfoProvider =
                        getScriptGeneratorInfoProvider(contrib
                                .getContributorId());

                if (scriptGeneratorInfoProvider != null) {

                    return scriptGeneratorInfoProvider
                            .getAssignmentStatement(wrappedElement
                                    .getContributedObject(),
                                    rhsObjectStatement,
                                    jsVarAlias);
                }
            }
        }
        return null;
    }

    /**
     * @see com.tibco.xpd.datamapper.api.IScriptGeneratorInfoProvider#getGetterStatement(java.lang.Object)
     * 
     * @param object
     * @return
     */
    @Override
    public String getGetterStatement(Object object, String jsVarAlias) {
        if (object instanceof WrappedContributedContent) {

            WrappedContributedContent wrappedElement =
                    ((WrappedContributedContent) object);
            AbstractDataMapperContentContributor contrib =
                    wrappedElement.getContributor();

            if (contrib != null) {
                IScriptGeneratorInfoProvider scriptGeneratorInfoProvider =
                        getScriptGeneratorInfoProvider(contrib
                                .getContributorId());

                if (scriptGeneratorInfoProvider != null) {

                    return scriptGeneratorInfoProvider
                            .getGetterStatement(wrappedElement
                                    .getContributedObject(), jsVarAlias);
                }
            }
        }

        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getCheckNullTreeExpression(Object object, String jsVarAlias,
            CheckNullTreeExpressionType checkType) {
        if (object instanceof WrappedContributedContent) {

            WrappedContributedContent wrappedElement =
                    ((WrappedContributedContent) object);
            AbstractDataMapperContentContributor contrib =
                    wrappedElement.getContributor();

            if (contrib != null) {
                IScriptGeneratorInfoProvider scriptGeneratorInfoProvider =
                        getScriptGeneratorInfoProvider(contrib
                                .getContributorId());

                if (scriptGeneratorInfoProvider != null) {
                    return scriptGeneratorInfoProvider
                            .getCheckNullTreeExpression(wrappedElement
                                    .getContributedObject(),
                                    jsVarAlias,
                                    checkType);
                }
            }
        }

        return null;
    }

    /**
     * @see com.tibco.xpd.datamapper.api.IScriptGeneratorInfoProvider#getScriptsToAppend()
     * 
     * @return
     */
    @Override
    public String getScriptsToAppend(ScriptDataMapper sdm, boolean isSource) {
        JavaScriptStringBuilder jssb = new JavaScriptStringBuilder();
        Collection<AbstractDataMapperContentContributor> contributions =
                DataMapperContentContributionHelper
                        .getApplicableContributions(mappingContext,
                                isRightHandSide);
        for (AbstractDataMapperContentContributor contribution : contributions) {
            String scripts =
                    getScriptGeneratorInfoProvider(contribution.getContributorId())
                            .getScriptsToAppend(sdm, isSource);
            if (scripts != null) {
                jssb.addLine(scripts);
            }
        }
        return jssb.toString();
    }

    /**
     * @see com.tibco.xpd.datamapper.api.IScriptGeneratorInfoProvider#getScriptsToPrepend()
     * 
     * @return
     */
    @Override
    public String getScriptsToPrepend(ScriptDataMapper sdm, boolean isSource) {
        JavaScriptStringBuilder jssb = new JavaScriptStringBuilder();
        Collection<AbstractDataMapperContentContributor> contributions =
                DataMapperContentContributionHelper
                        .getApplicableContributions(mappingContext,
                                isRightHandSide);
        for (AbstractDataMapperContentContributor contribution : contributions) {
            String scripts =
                    getScriptGeneratorInfoProvider(contribution.getContributorId())
                            .getScriptsToPrepend(sdm, isSource);
            if (scripts != null) {
                jssb.append(scripts);
            }
        }
        return jssb.toString();
    }

    /**
     * @see com.tibco.xpd.datamapper.api.IScriptGeneratorInfoProvider#getCollectionSizeScript(java.lang.Object,
     *      String)
     * 
     * @param object
     * @return
     */
    @Override
    public String getCollectionSizeScript(Object object,
            String objectParentJsVar) {
        if (object instanceof WrappedContributedContent) {

            WrappedContributedContent wrappedElement =
                    ((WrappedContributedContent) object);
            AbstractDataMapperContentContributor contrib =
                    wrappedElement.getContributor();

            if (contrib != null) {
                IScriptGeneratorInfoProvider scriptGeneratorInfoProvider =
                        getScriptGeneratorInfoProvider(contrib
                                .getContributorId());

                if (scriptGeneratorInfoProvider != null) {

                    return scriptGeneratorInfoProvider
                            .getCollectionSizeScript(wrappedElement
                                    .getContributedObject(), objectParentJsVar);
                }
            }
        }
        return null;
    }

    /**
     * @see com.tibco.xpd.datamapper.api.IScriptGeneratorInfoProvider#getCollectionElementScript(java.lang.Object,
     *      java.lang.String, String)
     * 
     * @param collection
     * @param indexVarName
     * @return
     */
    @Override
    public String getCollectionElementScript(Object collection,
            String indexVarName, String objectParentJsVar) {
        if (collection instanceof WrappedContributedContent) {

            WrappedContributedContent wrappedElement =
                    ((WrappedContributedContent) collection);
            AbstractDataMapperContentContributor contrib =
                    wrappedElement.getContributor();

            if (contrib != null) {
                IScriptGeneratorInfoProvider scriptGeneratorInfoProvider =
                        getScriptGeneratorInfoProvider(contrib
                                .getContributorId());

                if (scriptGeneratorInfoProvider != null) {

                    return scriptGeneratorInfoProvider
                            .getCollectionElementScript(wrappedElement
                                    .getContributedObject(),
                                    indexVarName,
                                    objectParentJsVar);
                }
            }
        }
        return null;
    }

    /**
     * Sid XPD-7712
     * 
     * @see com.tibco.xpd.datamapper.api.IScriptGeneratorInfoProvider#getCollectionElementScriptForTargetMerge(java.lang.Object,
     *      java.lang.String, String)
     * 
     * @param collection
     * @param indexVarName
     * @return
     */
    @Override
    public String getCollectionElementScriptForTargetMerge(Object collection,
            String indexVarName, String objectParentJsVar) {
        if (collection instanceof WrappedContributedContent) {

            WrappedContributedContent wrappedElement =
                    ((WrappedContributedContent) collection);
            AbstractDataMapperContentContributor contrib =
                    wrappedElement.getContributor();

            if (contrib != null) {
                IScriptGeneratorInfoProvider scriptGeneratorInfoProvider =
                        getScriptGeneratorInfoProvider(contrib
                                .getContributorId());

                if (scriptGeneratorInfoProvider != null) {

                    return scriptGeneratorInfoProvider
                            .getCollectionElementScriptForTargetMerge(wrappedElement
                                    .getContributedObject(),
                                    indexVarName,
                                    objectParentJsVar);
                }
            }
        }
        return null;
    }

    /**
     * @see com.tibco.xpd.datamapper.api.IScriptGeneratorInfoProvider#getClearCollectionScript(java.lang.Object)
     * 
     * @param collectionObject
     * @return
     */
    @Override
    public String getClearCollectionScript(Object collectionObject,
            String jsVarAlias) {
        if (collectionObject instanceof WrappedContributedContent) {

            WrappedContributedContent wrappedElement =
                    ((WrappedContributedContent) collectionObject);
            AbstractDataMapperContentContributor contrib =
                    wrappedElement.getContributor();

            if (contrib != null) {
                IScriptGeneratorInfoProvider scriptGeneratorInfoProvider =
                        getScriptGeneratorInfoProvider(contrib
                                .getContributorId());

                if (scriptGeneratorInfoProvider != null) {

                    return scriptGeneratorInfoProvider
                            .getClearCollectionScript(wrappedElement
                                    .getContributedObject(), jsVarAlias);
                }
            }
        }
        return null;
    }

    /**
     * @see com.tibco.xpd.datamapper.api.IScriptGeneratorInfoProvider#getCollectionAddElementScript(java.lang.Object,
     *      java.lang.String, String)
     * 
     * @param collection
     * @param jsVarName
     * @return
     */
    @Override
    public String getCollectionAddElementScript(Object collection,
            String jsVarName, String objectParentJsVar) {
        if (collection instanceof WrappedContributedContent) {

            WrappedContributedContent wrappedElement =
                    ((WrappedContributedContent) collection);
            AbstractDataMapperContentContributor contrib =
                    wrappedElement.getContributor();

            if (contrib != null) {
                IScriptGeneratorInfoProvider scriptGeneratorInfoProvider =
                        getScriptGeneratorInfoProvider(contrib
                                .getContributorId());

                if (scriptGeneratorInfoProvider != null) {

                    return scriptGeneratorInfoProvider
                            .getCollectionAddElementScript(wrappedElement
                                    .getContributedObject(),
                                    jsVarName,
                                    objectParentJsVar);
                }
            }
        }
        return null;
    }

    /**
     * @see com.tibco.xpd.datamapper.api.IScriptGeneratorInfoProvider#getCollectionSetElementScript(java.lang.Object,
     *      java.lang.String, java.lang.String, java.lang.String)
     * 
     * @param collection
     * @param jsVarName
     * @param objectParentJsVar
     * @param loopIndexJsVarName
     * @return
     */
    @Override
    public String getCollectionSetElementScript(Object collection,
            String jsVarName, String objectParentJsVar,
            String loopIndexJsVarName) {
        if (collection instanceof WrappedContributedContent) {

            WrappedContributedContent wrappedElement =
                    ((WrappedContributedContent) collection);
            AbstractDataMapperContentContributor contrib =
                    wrappedElement.getContributor();

            if (contrib != null) {
                IScriptGeneratorInfoProvider scriptGeneratorInfoProvider =
                        getScriptGeneratorInfoProvider(contrib
                                .getContributorId());

                if (scriptGeneratorInfoProvider != null) {

                    return scriptGeneratorInfoProvider
                            .getCollectionSetElementScript(wrappedElement
                                    .getContributedObject(),
                                    jsVarName,
                                    objectParentJsVar,
                                    loopIndexJsVarName);
                }
            }
        }
        return null;
    }

    /**
     * @see com.tibco.xpd.datamapper.api.IScriptGeneratorInfoProvider#getComplexObjectCreationScript(java.lang.Object)
     * 
     * @param complexObject
     * @return
     */
    @Override
    public String getComplexObjectCreationScript(Object complexObject) {
        if (complexObject instanceof WrappedContributedContent) {

            WrappedContributedContent wrappedElement =
                    ((WrappedContributedContent) complexObject);
            AbstractDataMapperContentContributor contrib =
                    wrappedElement.getContributor();

            if (contrib != null) {
                IScriptGeneratorInfoProvider scriptGeneratorInfoProvider =
                        getScriptGeneratorInfoProvider(contrib
                                .getContributorId());

                if (scriptGeneratorInfoProvider != null) {

                    return scriptGeneratorInfoProvider
                            .getComplexObjectCreationScript(wrappedElement
                                    .getContributedObject());
                }
            }
        }
        return null;
    }

    /**
     * Sid XPD-7660 - don't need differnet 'create instance of object within
     * array' method, but DO need separate 'create target array' for some
     * targets.
     * 
     * @see com.tibco.xpd.datamapper.api.IScriptGeneratorInfoProvider#getArrayCreationScript(java.lang.Object)
     * 
     * @param arrayObject
     * @return
     */
    @Override
    public String getArrayCreationScript(Object arrayObject) {
        if (arrayObject instanceof WrappedContributedContent) {

            WrappedContributedContent wrappedElement =
                    ((WrappedContributedContent) arrayObject);
            AbstractDataMapperContentContributor contrib =
                    wrappedElement.getContributor();

            if (contrib != null) {
                IScriptGeneratorInfoProvider scriptGeneratorInfoProvider =
                        getScriptGeneratorInfoProvider(contrib
                                .getContributorId());

                if (scriptGeneratorInfoProvider != null) {

                    return scriptGeneratorInfoProvider
                            .getArrayCreationScript(wrappedElement
                                    .getContributedObject());
                }
            }
        }
        return null;
    }

    /**
     * Resolves a path using the relevant info provider.
     * 
     * @param object
     *            The object used to identify the info provider.
     * @param path
     *            The path.
     * @return The resolved path.
     */
    @Override
    public String resolvePath(Object object, String path) {
        if (object instanceof WrappedContributedContent) {

            WrappedContributedContent wrappedElement =
                    ((WrappedContributedContent) object);
            AbstractDataMapperContentContributor contrib =
                    wrappedElement.getContributor();

            if (contrib != null) {
                IScriptGeneratorInfoProvider scriptGeneratorInfoProvider =
                        getScriptGeneratorInfoProvider(contrib
                                .getContributorId());

                if (scriptGeneratorInfoProvider != null) {

                    return scriptGeneratorInfoProvider
                            .resolvePath(wrappedElement.getContributedObject(),
                                    path);
                }
            }
        }
        return null;
    }

    @Override
    public String getSingleToMultiInstanceAssignmentStatement(
            Object targetItem, String rhsObjectStatement, String jsVarAlias) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Provides {@link IScriptGeneratorInfoProvider} for passed contributionID.
     * com.tibco.xpd.datamapper.scriptGeneratorInfoProviderContribution
     * extension point is used for contributing
     * {@link IScriptGeneratorInfoProvider} against specific contributionID.
     * 
     * @param contributionID
     * @return {@link IScriptGeneratorInfoProvider}
     */
    IScriptGeneratorInfoProvider getScriptGeneratorInfoProvider(
            String contributionID) {
        return ScriptGeneratorInfoProviderContributionHelper
                .getScriptGeneratorInfoProvider(contributionID);
    }
}
