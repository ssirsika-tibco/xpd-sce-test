/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.datamapper.infoProviders;

import org.eclipse.jface.viewers.ILabelProvider;

import com.tibco.xpd.datamapper.DataMapperPlugin;
import com.tibco.xpd.datamapper.api.AbstractDataMapperContentContributor;
import com.tibco.xpd.datamapper.api.AbstractDataMapperInfoProvider;
import com.tibco.xpd.datamapper.scripts.IScriptDataMapperProvider;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptableLabelProvider;
import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.xpdExtension.ScriptInformation;

/**
 * Info provider for the Data Mapper. It asks the actual contributor to provide
 * the content as its methods will expect WrappedContributedContent (and it uses
 * the contributed object and contributor inside the wrapped object to provide
 * the required information ).
 * 
 * @author Ali
 * @since 20 Feb 2015
 */
public class ContributableDataMapperInfoProvider extends
        AbstractDataMapperInfoProvider {

    /**
     * the script generator info provider. This will delegate to all of the
     * script generator info providers contributed by
     * {@link AbstractDataMapperContentContributor}
     */
    private DataMapperScriptGeneratorInfoProvider delegatingScriptGenInfoProvider;

    /**
     * @param contentProvider
     * @param labelProvider
     * @param caching
     *            caches retrieved children, should only be used from validation
     *            rules.
     * @param isInputMapping
     *            true if this is for input mappings.
     */
    public ContributableDataMapperInfoProvider(
            IScriptDataMapperProvider sdmProvider, String mappingContext,
            boolean isRightHandSide, boolean caching, boolean isInputMapping) {
        super(new ContributableDataMapperContentProvider(sdmProvider,
                mappingContext, isRightHandSide, isInputMapping),
                new ScriptableLabelProvider(
                        new ContributableDataMapperLabelProvider(
                                isRightHandSide)), caching);

        delegatingScriptGenInfoProvider =
                new DataMapperScriptGeneratorInfoProvider(mappingContext,
                        isRightHandSide);
    }

    /**
     * @see com.tibco.xpd.datamapper.api.AbstractDataMapperInfoProvider#getObjectPath(java.lang.Object)
     * 
     * @param objectFromMappingOrContent
     * @return
     */
    @Override
    public String getObjectPath(Object objectFromMappingOrContent) {

        if (objectFromMappingOrContent instanceof WrappedContributedContent) {

            WrappedContributedContent wrappedElement =
                    ((WrappedContributedContent) objectFromMappingOrContent);
            AbstractDataMapperContentContributor contrib =
                    wrappedElement.getContributor();
            if (contrib != null) {

                AbstractDataMapperInfoProvider infoProvider =
                        contrib.getInfoProvider();

                if (infoProvider != null) {
                    return infoProvider.getObjectPath(wrappedElement
                            .getContributedObject());
                }
            }

        } else if (objectFromMappingOrContent instanceof ScriptInformation) {
            ScriptInformation si =
                    (ScriptInformation) objectFromMappingOrContent;
            return si.getName();
        }

        return ""; //$NON-NLS-1$
    }

    /**
     * @see com.tibco.xpd.datamapper.api.AbstractDataMapperInfoProvider#getObjectPathDescription(java.lang.Object)
     * 
     * @param objectFromMappingOrContent
     * @return
     */
    @Override
    public String getObjectPathDescription(Object objectFromMappingOrContent) {
        if (objectFromMappingOrContent instanceof WrappedContributedContent) {

            WrappedContributedContent wrappedElement =
                    ((WrappedContributedContent) objectFromMappingOrContent);
            AbstractDataMapperContentContributor contrib =
                    wrappedElement.getContributor();
            if (contrib != null) {

                AbstractDataMapperInfoProvider infoProvider =
                        contrib.getInfoProvider();
                if (infoProvider != null) {
                    return infoProvider.getObjectPathDescription(wrappedElement
                            .getContributedObject());
                }
            }
        } else if (objectFromMappingOrContent instanceof ScriptInformation) {
            ScriptInformation si =
                    (ScriptInformation) objectFromMappingOrContent;
            return si.getName();
        }
        return ""; //$NON-NLS-1$
    }

    /**
     * @see com.tibco.xpd.datamapper.api.AbstractDataMapperInfoProvider#isReadOnlyTarget(java.lang.Object)
     * 
     * @param targetObjectInTree
     * @return
     */
    @Override
    public boolean isReadOnlyTarget(Object targetObjectInTree) {
        if (targetObjectInTree instanceof WrappedContributedContent) {

            WrappedContributedContent wrappedElement =
                    ((WrappedContributedContent) targetObjectInTree);
            AbstractDataMapperContentContributor contrib =
                    wrappedElement.getContributor();
            if (contrib != null) {

                AbstractDataMapperInfoProvider infoProvider =
                        contrib.getInfoProvider();

                if (infoProvider != null) {
                    return infoProvider.isReadOnlyTarget(wrappedElement
                            .getContributedObject());
                }
            }
        }
        return false;
    }

    /**
     * @see com.tibco.xpd.datamapper.api.AbstractDataMapperInfoProvider#isMultiInstance(java.lang.Object)
     * 
     * @param objectInTree
     * @return
     */
    @Override
    public boolean isMultiInstance(Object objectInTree) {
        if (objectInTree instanceof WrappedContributedContent) {

            WrappedContributedContent wrappedElement =
                    ((WrappedContributedContent) objectInTree);

            AbstractDataMapperContentContributor contrib =
                    wrappedElement.getContributor();
            if (contrib != null) {

                AbstractDataMapperInfoProvider infoProvider =
                        contrib.getInfoProvider();

                if (infoProvider != null) {
                    return infoProvider.isMultiInstance(wrappedElement
                            .getContributedObject());
                }
            }
        }
        /*
         * Base mapping rule coerces ScriptInformation to ScriptRelevantData for
         * scripts so as we contribute scripts to the content from this class we
         * need to deal with the ScriptRelevantData too
         */
        else if (objectInTree instanceof IScriptRelevantData) {
            return ((IScriptRelevantData) objectInTree).isArray();
        }
        return false;
    }

    /**
     * @see com.tibco.xpd.datamapper.api.AbstractDataMapperInfoProvider#getMinimumInstances(java.lang.Object)
     * 
     * @param objectInTree
     * @return
     */
    @Override
    public int getMinimumInstances(Object objectInTree) {
        if (objectInTree instanceof WrappedContributedContent) {

            WrappedContributedContent wrappedElement =
                    ((WrappedContributedContent) objectInTree);
            AbstractDataMapperContentContributor contrib =
                    wrappedElement.getContributor();
            if (contrib != null) {

                AbstractDataMapperInfoProvider infoProvider =
                        contrib.getInfoProvider();
                if (infoProvider != null) {
                    return infoProvider.getMinimumInstances(wrappedElement
                            .getContributedObject());
                }
            }
        }
        return -1;
    }

    /**
     * @see com.tibco.xpd.datamapper.api.AbstractDataMapperInfoProvider#getMaximumInstances(java.lang.Object)
     * 
     * @param objectInTree
     * @return
     */
    @Override
    public int getMaximumInstances(Object objectInTree) {
        if (objectInTree instanceof WrappedContributedContent) {

            WrappedContributedContent wrappedElement =
                    ((WrappedContributedContent) objectInTree);

            AbstractDataMapperContentContributor contrib =
                    wrappedElement.getContributor();
            if (contrib != null) {

                AbstractDataMapperInfoProvider infoProvider =
                        contrib.getInfoProvider();

                if (infoProvider != null) {
                    return infoProvider.getMaximumInstances(wrappedElement
                            .getContributedObject());
                }
            }
        }
        return -1;
    }

    /**
     * @see com.tibco.xpd.datamapper.api.AbstractDataMapperInfoProvider#getInstanceIndex(java.lang.Object)
     * 
     * @param objectInTree
     * @return
     */
    @Override
    public int getInstanceIndex(Object objectInTree) {
        if (objectInTree instanceof WrappedContributedContent) {

            WrappedContributedContent wrappedElement =
                    ((WrappedContributedContent) objectInTree);

            AbstractDataMapperContentContributor contrib =
                    wrappedElement.getContributor();
            if (contrib != null) {

                AbstractDataMapperInfoProvider infoProvider =
                        contrib.getInfoProvider();

                if (infoProvider != null) {
                    return infoProvider.getInstanceIndex(wrappedElement
                            .getContributedObject());
                }
            }
        }
        return -1;
    }

    /**
     * @see com.tibco.xpd.datamapper.api.AbstractDataMapperInfoProvider#isSimpleTypeContent(java.lang.Object)
     * 
     * @param objectInTree
     * @return
     */
    @Override
    public boolean isSimpleTypeContent(Object objectInTree) {
        if (objectInTree instanceof WrappedContributedContent) {
            WrappedContributedContent wrappedElement =
                    ((WrappedContributedContent) objectInTree);

            AbstractDataMapperContentContributor contrib =
                    wrappedElement.getContributor();
            if (contrib != null) {

                AbstractDataMapperInfoProvider infoProvider =
                        contrib.getInfoProvider();

                if (infoProvider != null) {
                    return infoProvider.isSimpleTypeContent(wrappedElement
                            .getContributedObject());
                }
            }
        }
        return false;
    }

    /**
     * @see com.tibco.xpd.datamapper.api.AbstractDataMapperInfoProvider#isNullSimpleContentAllowed(java.lang.Object)
     * 
     * @param objectInTree
     * @return
     */
    @Override
    public boolean isNullSimpleContentAllowed(Object objectInTree) {
        if (objectInTree instanceof WrappedContributedContent) {

            WrappedContributedContent wrappedElement =
                    ((WrappedContributedContent) objectInTree);

            AbstractDataMapperContentContributor contrib =
                    wrappedElement.getContributor();
            if (contrib != null) {

                AbstractDataMapperInfoProvider infoProvider =
                        contrib.getInfoProvider();

                if (infoProvider != null) {
                    return infoProvider
                            .isNullSimpleContentAllowed(wrappedElement
                                    .getContributedObject());
                }
            }
        }
        return false;
    }

    /**
     * @see com.tibco.xpd.datamapper.api.AbstractDataMapperInfoProvider#isArtificialObject(java.lang.Object)
     * 
     * @param objectInTree
     * @return
     */
    @Override
    public boolean isArtificialObject(Object objectInTree) {
        if (objectInTree instanceof WrappedContributedContent) {
            WrappedContributedContent wrappedElement =
                    ((WrappedContributedContent) objectInTree);

            AbstractDataMapperContentContributor contrib =
                    wrappedElement.getContributor();

            if (contrib != null) {
                AbstractDataMapperInfoProvider infoProvider =
                        contrib.getInfoProvider();

                if (infoProvider != null) {
                    return infoProvider.isArtificialObject(wrappedElement
                            .getContributedObject());
                }
            }
        }
        return false;
    }

    /**
     * @see com.tibco.xpd.datamapper.api.AbstractDataMapperInfoProvider#isChoiceObject(java.lang.Object)
     * 
     * @param objectFromMappingOrContent
     * @return
     */
    @Override
    public boolean isChoiceObject(Object objectFromMappingOrContent) {
        if (objectFromMappingOrContent instanceof WrappedContributedContent) {

            WrappedContributedContent wrappedElement =
                    ((WrappedContributedContent) objectFromMappingOrContent);

            AbstractDataMapperContentContributor contrib =
                    wrappedElement.getContributor();

            if (contrib != null) {
                AbstractDataMapperInfoProvider infoProvider =
                        contrib.getInfoProvider();

                if (infoProvider != null) {
                    return infoProvider.isChoiceObject(wrappedElement
                            .getContributedObject());
                }
            }
        }
        return false;
    }

    /**
     * @see com.tibco.xpd.datamapper.api.AbstractDataMapperInfoProvider#getLabelProvider()
     * 
     * @return
     */
    @Override
    public ILabelProvider getLabelProvider() {
        return super.getLabelProvider();
    }

    /**
     * @see com.tibco.xpd.datamapper.api.AbstractDataMapperInfoProvider#getObjectForPath(java.lang.Object,
     *      java.lang.Object)
     * 
     * @param path
     * @param mapperInput
     * @return
     */
    @Override
    public Object getObjectForPath(String objectPath, Object mapperInput) {
        DataMapperPlugin
                .getDefault()
                .getLogger()
                .error(this.getClass().getCanonicalName()
                        + ".getObjectForPath() cannot be called directly.\n" //$NON-NLS-1$
                        + "Instead find the contributorId for the data mapping source / target from the model and then use...\n" //$NON-NLS-1$
                        + "  contributor = DataMapperContentContributionHelper.getContributor(contributorId)\n" //$NON-NLS-1$
                        + "to get the individual specific content contributor and use...\n" //$NON-NLS-1$
                        + "    contributor.getInfoProvider().getObjectForPath()"); //$NON-NLS-1$
        return null;
    }

    /**
     * @see com.tibco.xpd.datamapper.api.AbstractDataMapperInfoProvider#getObjectType(java.lang.Object)
     * 
     * @param object
     * @return
     */
    @Override
    public String getObjectType(Object object) {
        if (object instanceof WrappedContributedContent) {

            WrappedContributedContent wrappedElement =
                    ((WrappedContributedContent) object);

            AbstractDataMapperContentContributor contrib =
                    wrappedElement.getContributor();

            if (contrib != null) {
                AbstractDataMapperInfoProvider infoProvider =
                        contrib.getInfoProvider();

                if (infoProvider != null) {
                    return infoProvider.getObjectType(wrappedElement
                            .getContributedObject());
                }
            }
        }
        return null;
    }

    /**
     * @see com.tibco.xpd.datamapper.api.AbstractDataMapperInfoProvider#getObjectName(java.lang.Object)
     * 
     * @param child
     * @return
     */
    @Override
    public String getObjectName(Object child) {
        if (child instanceof WrappedContributedContent) {

            WrappedContributedContent wrappedElement =
                    ((WrappedContributedContent) child);

            AbstractDataMapperContentContributor contrib =
                    wrappedElement.getContributor();

            if (contrib != null) {
                AbstractDataMapperInfoProvider infoProvider =
                        contrib.getInfoProvider();

                if (infoProvider != null) {
                    return infoProvider.getObjectName(wrappedElement
                            .getContributedObject());
                }
            }
        }

        return ""; //$NON-NLS-1$
    }

    /**
     * Return the script generator info provider. This will delegate to all of
     * the script generator info providers contributed by
     * {@link AbstractDataMapperContentContributor}
     * 
     * @return the delegatingScriptGenInfoProvider
     */
    public DataMapperScriptGeneratorInfoProvider getContribDelegatingScriptGenInfoProvider() {
        return delegatingScriptGenInfoProvider;
    }

}
