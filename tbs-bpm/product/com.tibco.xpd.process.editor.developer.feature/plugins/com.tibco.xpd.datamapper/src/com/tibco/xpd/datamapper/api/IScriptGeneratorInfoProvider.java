/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.datamapper.api;

import com.tibco.xpd.datamapper.scripts.DataMapperJavascriptGenerator;
import com.tibco.xpd.xpdExtension.ScriptDataMapper;

/**
 * Interface for Data Mapper Script Generation Contributions.
 * <p>
 * The content contributors for a given Data Mapper scenario also contribute a
 * script generator info provider. The base framework uses this to generate
 * certain parts of the script that may be done in a specific way for different
 * scenarios/content.
 * 
 * @author Ali
 * @since 6 Mar 2015
 */
public interface IScriptGeneratorInfoProvider {

    /**
     * The prefix for temporary variables (representing looping target array
     * elements) used when generating array mappings scripts.
     */
    public static final String TARGET_VAR_PREFIX = "$tV"; //$NON-NLS-1$

    /**
     * The prefix for temporary variables (representing looping source array
     * elements) used when generating array mappings scripts.
     */
    public static final String SOURCE_VAR_PREFIX = "$sV"; //$NON-NLS-1$

    /**
     * 
     * @param object
     * @param rhsObjectStatement
     * @param jsVarAlias
     *            Override JavaScript variable name if required (else <code>null
     *            </code> or <code>""</code> if the original source item
     *            name/path should be used. Alias variables are used to
     *            represent elements in a source/target item collection.
     * 
     * @return Assignment javascript to assign the rhsObjectStatement to the
     *         given object and assignmned to the given alias
     */
    public String getAssignmentStatement(Object object,
            String rhsObjectStatement, String jsVarAlias);

    /**
     * 
     * @param object
     *            The mapping source object.
     * 
     * @param jsVarAlias
     *            Override JavaScript variable name if required (else <code>null
     *            </code> or <code>""</code> if the original source item
     *            name/path should be used. Alias variables are used to
     *            represent elements in a source/target item collection.
     * 
     * @return The getter statement for the given LHS srcItem
     */
    public String getGetterStatement(Object object, String jsVarAlias);

    /**
     * This enumeration is a type indicator for the
     * {@link IScriptGeneratorInfoProvider#getCheckNullTreeExpression(Object, String)}
     * which may wish different behaviours depending on what it is called for to
     * check.
     * 
     * @author aallway
     * @since 22 Oct 2015
     */
    public enum CheckNullTreeExpressionType {
        /**
         * The call to getCheckNullTreeExpression() is to protect the creation
         * of a for loop against the source array being undefined.
         * 
         * The passed object is the multi-instance source object we will iterate
         * thru.
         */
        ARRAY_ITERATOR,
        /**
         * The call to getCheckNullTreeExpression() is to protect the creation
         * of an assignment from a mapped source object that is undefined or has
         * an undefined ancestor.
         * 
         * The passed object is the single instance source object that we will
         * assign to target object
         */
        SINGLE_INSTANCE_MAPPING
    }

    /**
     * Get the condition expression that can be used inside an
     * <code>if ( xxxx ) {</code> to check whether the given tree object or any
     * of it's ancestors are null or undefined. This is used for the prevention
     * of assigning from a child of a null parent from source tree and for
     * prevention of looping around a null array.
     * <p>
     * For instance if a mapping is made from Parent.child.grandchild then the
     * framework will pass the parent object for Parent.child and the expression
     * would be <code>"Parent && Parent.child"<code/>
     * 
     * @param object
     *            The object to create <code>null</code> expression check for.
     * 
     * @param jsVarAlias
     *            Override JavaScript variable name if required (else <code>null
     *            </code> or <code>""</code> if the original source item
     *            name/path should be used. Alias variables are used to
     *            represent elements in a source/target item collection.
     * @param checkType
     *            The purpose for which the null check is being made.
     * 
     * @return <code>null</code> protection expression or <code>null</code> /
     *         <code>""</code> if none required.
     */
    public String getCheckNullTreeExpression(Object object, String jsVarAlias,
            CheckNullTreeExpressionType checkType);

    /**
     * Method to allow a script to be added before any of the other generated
     * scripts. This can be used as a place to declare local variables or
     * extract data from variables in the scope.
     * 
     * @param sdm
     *            The script data mapper.
     * @param isSource
     *            true if this info provider is for mapping sources.
     * 
     * @return javascript text that a contributor would like to prepend at the
     *         start of the generated script.
     */
    public String getScriptsToPrepend(ScriptDataMapper sdm, boolean isSource);

    /**
     * Method to allow a script to be added at the end of the other generated
     * scripts. This can be used to assign data from the generated script to
     * scope variables.
     * 
     * @param sdm
     *            The script data mapper.
     * @param isSource
     *            true if this info provider is for mapping sources.
     * @return javascript text that a contributor would like to append at the
     *         end of the generated script.
     */
    public String getScriptsToAppend(ScriptDataMapper sdm, boolean isSource);

    /**
     * Method to get the JavaScript statement for a single-to-multi instance
     * mapping which we generally support when multi-instance loop is enabled.
     * This will be called for the target content contribnutor where target is a
     * multi-instance and source is a single instance. Each instance of the
     * multi-instanbce task is returning a single value which should be assigned
     * to the target array element that corresponds to the task instance (e.g.
     * each task instance returns data that is put into the corresponding array
     * element instance of target data.
     * 
     * @return The JavaScript statement for a single-to-multi instance mapping
     *         which we generally support when multi-instance loop is enabled.
     */
    public String getSingleToMultiInstanceAssignmentStatement(
            Object targetItem, String rhsObjectStatement, String jsVarAlias);

    /**
     * 
     * @param object
     * @param objectParentJsVar
     * 
     * @return javascript text for the given array/list that will be used in the
     *         for loop of the generated script to iterate over the given
     *         collection elements.
     */
    public String getCollectionSizeScript(Object object,
            String objectParentJsVar);

    /**
     * 
     * @param collection
     * @param indexVarName
     * @param objectParentJsVar
     *            Override JavaScript variable name if required (else
     *            <code>null</code> or <code>""</code> if the original source
     *            item name/path should be used.
     * 
     * @return javascript text to get the given element from the given
     *         collection (array/list).
     */
    public String getCollectionElementScript(Object collection,
            String indexVarName, String objectParentJsVar);

    /**
     * Sid XPD-7712.
     * 
     * @param collection
     * @param indexVarName
     * @param objectParentJsVar
     *            Override JavaScript variable name if required (else
     *            <code>null</code> or <code>""</code> if the original source
     *            item name/path should be used.
     * 
     * @return javascript text to get the given element from the given
     *         collection (array/list) <b>in the context of getting an element
     *         from a target collection for the purpose of merging content into
     *         that existing target array element (for target array merge
     *         inflation mode).</b>
     */
    public String getCollectionElementScriptForTargetMerge(Object collection,
            String indexVarName, String objectParentJsVar);

    /**
     * 
     * @param collectionObject
     * @param objectParentJsVar
     *            Override JavaScript variable name if required (else
     *            <code>null</code> or <code>""</code> if the original source
     *            item name/path should be used.
     * 
     * @return javascript text to clear the given collection
     */
    public String getClearCollectionScript(Object collectionObject,
            String objectParentJsVar);

    /**
     * @param collection
     *            The target collection to add to.
     * @param jsElementToAdd
     *            The element to add.
     * @param objectParentJsVar
     *            Override JavaScript variable name if required (else
     *            <code>null</code> or <code>""</code> if the original source
     *            item name/path should be used.
     * 
     * @return javascript text to add the given javascript variable to the given
     *         collection (array/list).
     */
    public String getCollectionAddElementScript(Object collection,
            String jsElementToAdd, String objectParentJsVar);

    /**
     * @param collection
     *            The target collection to add to.
     * @param jsElementToAdd
     *            The element to add.
     * @param objectParentJsVar
     *            Override JavaScript variable name if required (else
     *            <code>null</code> or <code>""</code> if the original source
     *            item name/path should be used.
     * @param loopIndexJsVar
     *            The string with the loop index variable
     * 
     * @return javascript text to add the given javascript variable to the given
     *         collection (array/list).
     */
    public String getCollectionSetElementScript(Object collection,
            String jsElementToAdd, String objectParentJsVar,
            String loopIndexJsVar);

    /**
     * Return the javaScript fragment required for creation of the target
     * object; This is used by the data mapper script generator to create a
     * parent object of a mapped child. <[p> This method can be passed single
     * instance items or array items - in both cases <b>it will only be called
     * when a single instance of the item type is required to be created.</b>
     * <p>
     * If the target object does not require creation (it represents some
     * statically available object) then the implementation should return
     * <code>null</code>.
     * 
     * @param complexObject
     *            The complex object to create.
     * 
     * @return the javaScript fragment required for creation of the target
     *         object or <code>null</code> if target object is static and does
     *         not need creation.
     */
    public String getComplexObjectCreationScript(Object complexObject);

    /**
     * Sid XPD-7660 - don't need differnet 'create instance of object within
     * array' method, but DO need separate 'create target array' for some
     * targets.
     * 
     * Return the javascript fragment for creation an array type.
     * <p>
     * If the target object does not require creation (it represents some
     * statically available object) then the implementation should return
     * <code>null</code>.
     * 
     * @param arrayObject
     *            The array object to create an instance of.
     * 
     * @return the javaScript fragment required for creation of the target
     *         object or <code>null</code> if target object is static and does
     *         not need creation.
     */
    public String getArrayCreationScript(Object arrayObject);

    /**
     * Resolves a path using the info provider associated with the given object.
     * This is used by the {@link DataMapperJavascriptGenerator} when it needs
     * to directly reference an object in script that it generates (such as for
     * null checking etc)
     * 
     * @param object
     *            The object used to identify the info provider.
     * @param path
     *            The default path. This path might however be a temporary
     *            variable (used for iterating source/target arrays). In which
     *            case it will start with one of {@link #SOURCE_VAR_PREFIX} or
     *            {@link #TARGET_VAR_PREFIX}
     * 
     * @return The resolved path.
     */
    public String resolvePath(Object object, String path);

}
