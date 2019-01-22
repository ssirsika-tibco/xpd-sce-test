/**
 * Copyright (c) TIBCO Software Inc 2004-2010. All rights reserved.
 */

package com.tibco.xpd.validation.bpmn.rules.baserules;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.tibco.xpd.mapper.MapperContentProvider;

/**
 * This class is provides the necessary information for
 * {@link AbstractMappingRuleBase} for either the source or the target content
 * when checking the mapping rules. Previously the base class for this was
 * {@link MappingRuleContentInfoProvider} but that was specific for mappings
 * contained by {@link Activity}. Now mapping validation is required for
 * mappings in different containers, we need this generic EObject based base
 * class.
 * <p>
 * Further information can be found in the specification:
 * https://emea-swi-svn.emea.tibco.com/svn/ProductDocumentation/studio/
 * Components/Process%20Modeler/PM-ConsistentMappingValidaton.doc
 * </p>
 * <p>
 * When using the {@link AbstractMappingRuleBase} the sub-class will provide
 * source, target and mappings content provider (via this class). For all of
 * these it should be able to use exactly the same providers as the UI mapper
 * section, hence the UI and validation rule should largely agree on issues such
 * as missing source/target data and so on.
 * </p>
 * <p>
 * This class abstracts the knowledge of the object types used by these content
 * providers (i.e. {@link AbstractMappingRuleBase} should not know the types of
 * object used for source/target/mapping.
 * </p>
 * <p>
 * Each instance of {@link MappingRuleContentInfoProviderBase} handles EITHER
 * the source-side data OR the target-side data. Hence it should be possible to
 * create a single class for handling source/target content dependent on the
 * content type RATHER than the context in which it is used For example for the
 * purpose of this interface, process data concept paths should be able to be
 * treated the same regardless of whether they are used as source or target
 * content and regardless of whether its a web service task or sub-process task.
 * </p>
 * 
 * @author aallway
 * @since 3.3 (16 Jun 2010)
 */
public abstract class MappingRuleContentInfoProviderBase {

    /**
     * The content provider for the tree of data representing this side of the
     * mapping data
     */
    private ITreeContentProvider contentProvider;

    /**
     * An object that signifies the context in which this info provider is being
     * used. This should be set prior to any use (or change of context after
     * forst use) of this class.
     */
    private EObject contextObject = null;

    /**
     * Construct a mapping rule info provider. The provider wraps a tree content
     * provider (for either the source or target side objects in a given mapping
     * situation.
     * <p>
     * In order that the mapping validations exactly agree with what the user
     * sees when viewing the mappings in the UI, the mapping content provider
     * should be exactly the same class as that used by the relevant mapping
     * section.
     * </p>
     * 
     * @param contentProvider
     *            The content provider for the tree of data representing this
     *            side of the mapping data
     */
    protected MappingRuleContentInfoProviderBase(
            ITreeContentProvider contentProvider, boolean caching) {
        super();
        if (caching) {
            this.contentProvider = new CachingContentProvider(contentProvider);
        } else {
            this.contentProvider = contentProvider;
        }
    }

    /**
     * Construct a mapping rule info provider. The provider wraps a tree content
     * provider (for either the source or target side objects in a given mapping
     * situation.
     * <p>
     * In order that the mapping validations exactly agree with what the user
     * sees when viewing the mappings in the UI, the mapping content provider
     * should be exactly the same class as that used by the relevant mapping
     * section.
     * </p>
     * 
     * @param contentProvider
     *            The content provider for the tree of data representing this
     *            side of the mapping data
     */
    protected MappingRuleContentInfoProviderBase(
            ITreeContentProvider contentProvider) {
        this(contentProvider, true);
    }

    /**
     * An object that signifies the context in which this info provider is being
     * used. This should be set prior to any use (or change of context after
     * first use) of this class.
     * 
     * @param contextObject
     */
    public void setContextObject(EObject contextObject) {
        this.contextObject = contextObject;
        return;
    }

    /**
     * Get the context object that has been set on this info provider prior to
     * it's use in any given context (for instance mapping validation rule) will
     * set the context to teh object it is validating.
     * 
     * @return the contextObject
     */
    protected EObject getContextObject() {
        return contextObject;
    }

    /**
     * @return the contentProvider
     */
    public ITreeContentProvider getContentProvider() {
        return contentProvider;
    }

    /**
     * Get the content path for the object extracted from appropriate side of a
     * mapping or from the content provider's tree
     * <p>
     * This may be added to problem marker
     * {@link MapperContentProvider#SOURCE_URI_ISSUEINFO} or
     * {@link MapperContentProvider#DATAMAPPING_SOURCE_URI_ISSUEINFO} additional
     * info for issues that wish to put markers on source tree content of
     * mapper.
     * 
     * @param objectFromMappingOrContent
     *            Object extracted from mapping
     * 
     * @return Content path for object
     */
    public abstract String getObjectPath(Object objectFromMappingOrContent);

    /**
     * Get the human readable content path for the object extracted from
     * appropriate side of a mapping or from the content provider's tree (for
     * use in validation messages)
     * 
     * @param objectFromMappingOrContent
     * 
     * @return Object path description or <code>null</code> if cannot be
     *         ascertained.
     */
    public abstract String getObjectPathDescription(
            Object objectFromMappingOrContent);

    /**
     * Check if the given object is read only.
     * 
     * @param targetObjectInTree
     *            object from content provider tree data.
     * 
     * @return <code>true</code> if read only else <code>false</code>
     */
    public abstract boolean isReadOnlyTarget(Object targetObjectInTree);

    /**
     * Check if the given object from content provider tree is single or
     * multiple instance (i.e. array/multi sequence etc).
     * <p>
     * Note that this should only be true for the header element for a
     * multi-instance object (not for expanded individual elements).
     * 
     * @param objectInTree
     *            object from content provider tree data.
     * @return <code>true</code> if it is multi-instance
     */
    public abstract boolean isMultiInstance(Object objectInTree);

    /**
     * Return the minimum number of instances of the given object that should be
     * mapped.
     * <p>
     * If the object is a multi-instance header then you should return the
     * minimum number of elements that should be mapped.
     * 
     * @param objectInTree
     *            object from content provider tree data.
     * @return Minimum number of elements that should be mapped.
     */
    public abstract int getMinimumInstances(Object objectInTree);

    /**
     * Return the maximum number of individual instance elements that can be
     * assigned to the given multi instance object.
     * <p>
     * This method is only used if the given objectInTree is multi-instance
     * (according to {@link #isMultiInstance(Object)}.
     * <p>
     * If there is no maximum then <code>-1</code> should be returned.
     * 
     * @param objectInTree
     *            object from content provider tree data.
     * 
     * @return Maximum number of elements that can be individually mapped or
     *         <code>-1</code> if there is no maximum defined
     */
    public abstract int getMaximumInstances(Object objectInTree);

    /**
     * Get the instance index (zero-based) of the given object IF it is an
     * element in a multi-instance array.
     * 
     * @param objectInTree
     *            object from content provider tree data.
     * 
     * @return index of object (zero-based) or <code>-1</code> if it is not.
     */
    public abstract int getInstanceIndex(Object objectInTree);

    /**
     * Check whether the given object has simple content type.
     * <p>
     * If the object has simple content type <i>and</i> child attributes (which
     * is possible in some data types like xsd schema) then it should
     * <i>still</i> be counted as simple type content.
     * 
     * @param objectInTree
     *            object from content provider tree data.
     * 
     * @return <code>true</code> if the given target object has simple type
     *         content
     */
    public abstract boolean isSimpleTypeContent(Object objectInTree);

    /**
     * Check whether the given object with simple content type can have its
     * value set as null.
     * 
     * This check is only called after {@link #isSimpleTypeContent(Object)} is
     * called.
     * 
     * The significant usage of this check is when the object is of type with
     * base restriction to String but has no restrictions, or is an element of
     * type which has a base restriction but the element has a default value
     * specified.
     * 
     * @param objectInTree
     * @return
     */
    public abstract boolean isNullSimpleContentAllowed(Object objectInTree);

    /**
     * Some mapper lhs / rhs content providers have artificial entries in the
     * tree.
     * <p>
     * In order for things like {@link AbstractMappingRuleBase} to ascertain
     * missing mandatory parameters these need to be ignored whilst recursing
     * down thru the tree.
     * 
     * @param objectInTree
     * 
     * @return <code>true</code> if this is an artificial tree entry (provided
     *         for user clarity only.
     */
    public abstract boolean isArtificialObject(Object objectInTree);

    /**
     * Is this object a grouptype object (the parent of a set of children in
     * which only one child should be set).
     * 
     * @param objectFromMappingOrContent
     *            Object extracted from mapping
     * 
     * @return Content path for object
     */
    public abstract boolean isChoiceObject(Object objectFromMappingOrContent);

    /**
     * Check whether the given mapping object is equivalent to the given content
     * provider tree data item.
     * <p>
     * By default this class assumes that the objectFromMapping (provided via
     * mapping content provider given to {@link AbstractMappingRuleBase} is
     * exactly equivalent to the content in the tree so does a simple equals.
     * <p>
     * If the mapper content object do not match the content provider content
     * then override this method.
     * 
     * @param objectFromMapping
     * @param objectFromTreeContent
     * 
     * @return true if the objectFromMapping is the objectFromTreeContent.
     */
    protected boolean mappingObjectEqualsContentObject(
            Object objectFromMapping, Object objectFromTreeContent) {
        return safeEquals(objectFromMapping, objectFromTreeContent);
    }

    /**
     * A safe "equals" implementation that allows either or both objects to be
     * assigned or null.
     * 
     * @param object1
     * @param object2
     * @return true if the objects are equal.
     */
    protected boolean safeEquals(Object object1, Object object2) {
        if (object1 == null) {
            if (object2 == null) {
                return true;
            } else {
                return false;
            }
        } else if (object2 == null) {
            if (object1 == null) {
                return true;
            } else {
                return false;
            }
        } else {
            return object1.equals(object2);
        }
    }

    /**
     * Noticed some hideous performance problems because mapping rule tends to
     * have to get and re-get children.
     * <p>
     * This content provider delegates to an XpathContentProvider but caches the
     * content when it is loaded.
     * 
     * @author aallway
     * @since 3.4.2 (21 Jul 2010)
     */
    private class CachingContentProvider implements ITreeContentProvider {
        private ITreeContentProvider delegateProvider;

        private Object[] topLevelElements = null;

        private Map<Object, Object[]> parentObjectToChildrenMap = null;

        /**
         * @param delegateProvider
         */
        public CachingContentProvider(ITreeContentProvider delegateProvider) {
            this.delegateProvider = delegateProvider;
        }

        /**
         * 
         * @see org.eclipse.jface.viewers.IContentProvider#dispose()
         */
        @Override
        public void dispose() {
            delegateProvider.dispose();
        }

        /**
         * @param parentElement
         * @return
         * @see org.eclipse.jface.viewers.ITreeContentProvider#getChildren(java.lang.Object)
         */
        @Override
        public Object[] getChildren(Object parentElement) {
            Object[] children = getAndCacheChildren(parentElement);
            return children;
        }

        /**
         * @param parentElement
         * @return children of object
         */
        private Object[] getAndCacheChildren(Object parentElement) {
            if (parentObjectToChildrenMap == null) {
                parentObjectToChildrenMap = new HashMap<Object, Object[]>();
            }

            Object[] children;
            if (parentObjectToChildrenMap.containsKey(parentElement)) {
                children = parentObjectToChildrenMap.get(parentElement);
            } else {
                children = delegateProvider.getChildren(parentElement);

                parentObjectToChildrenMap.put(parentElement, children);
            }
            return children;
        }

        /**
         * @param inputElement
         * @return
         * @see org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java.lang.Object)
         */
        @Override
        public Object[] getElements(Object inputElement) {
            if (topLevelElements == null) {
                topLevelElements = delegateProvider.getElements(inputElement);
            }
            return topLevelElements;
        }

        /**
         * @param element
         * @return
         * @see org.eclipse.jface.viewers.ITreeContentProvider#getParent(java.lang.Object)
         */
        @Override
        public Object getParent(Object element) {
            return delegateProvider.getParent(element);
        }

        /**
         * @param element
         * @return
         * @see org.eclipse.jface.viewers.ITreeContentProvider#hasChildren(java.lang.Object)
         */
        @Override
        public boolean hasChildren(Object element) {
            Object[] children = getAndCacheChildren(element);
            if (children != null && children.length > 0) {
                return true;
            }
            return false;
        }

        /**
         * @param viewer
         * @param oldInput
         * @param newInput
         * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer,
         *      java.lang.Object, java.lang.Object)
         */
        @Override
        public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
            /* Throw away old caches. */
            topLevelElements = null;
            parentObjectToChildrenMap = null;
            delegateProvider.inputChanged(viewer, oldInput, newInput);
            return;
        }

    }
}
