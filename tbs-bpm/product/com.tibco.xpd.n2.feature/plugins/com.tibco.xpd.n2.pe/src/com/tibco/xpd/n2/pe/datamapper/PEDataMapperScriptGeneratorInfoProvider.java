/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.n2.pe.datamapper;

import com.tibco.xpd.datamapper.api.IScriptGeneratorInfoProvider;
import com.tibco.xpd.datamapper.staticcontent.StaticContentDataMapperElement;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.xpdExtension.ScriptDataMapper;

/**
 * Data Mapper script generation info provider for PE Data Mapper contribution.
 * This is the contribution of the "Process" JavaScript class equivalent mapper
 * tree content.
 * 
 * @author Ali
 * @since 6 Mar 2015
 */
public class PEDataMapperScriptGeneratorInfoProvider implements
        IScriptGeneratorInfoProvider {

    /**
     * @see com.tibco.xpd.datamapper.api.IScriptGeneratorInfoProvider#getAssignmentStatement(java.lang.Object,
     *      java.lang.String, java.lang.String)
     * 
     * @param object
     * @param rhsObjectStatement
     * @param jsVarAlias
     * @return
     */
    @Override
    public String getAssignmentStatement(Object object,
            String rhsObjectStatement, String jsVarAlias) {

        if (object instanceof StaticContentDataMapperElement) {
            return ((StaticContentDataMapperElement) object)
                    .getJavascriptText() + " = " + rhsObjectStatement + ";"; //$NON-NLS-1$ //$NON-NLS-2$
        }
        return null;
    }

    /**
     * 
     * @see com.tibco.xpd.datamapper.api.IScriptGeneratorInfoProvider#getAssignmentElseStatement(java.lang.Object,
     *      java.lang.String)
     *
     * @param object
     * @param jsVarAlias
     * @return
     */
    @Override
    public String getAssignmentElseStatement(Object object, String jsVarAlias) {
        /*
         * Sid ACE-6367 Just set to null as we always used to for non-REST input data (BPM data is ok to set to null).
         */
        return getAssignmentStatement(object, "null", jsVarAlias); //$NON-NLS-1$
    }

    /**
     * @see com.tibco.xpd.datamapper.api.IScriptGeneratorInfoProvider#getGetterStatement(java.lang.Object,
     *      java.lang.String)
     * 
     * @param object
     * @param jsVarAlias
     * @return
     */
    @Override
    public String getGetterStatement(Object object, String jsVarAlias) {
        if (object instanceof StaticContentDataMapperElement) {
            return ((StaticContentDataMapperElement) object)
                    .getJavascriptText();
        }
        return null;
    }

    /**
     * @see com.tibco.xpd.datamapper.api.IScriptGeneratorInfoProvider#getScriptsToAppend()
     * 
     * @return
     */
    @Override
    public String getScriptsToAppend(ScriptDataMapper container,
            boolean isSource) {
        // Not expecting this to be called
        return null;
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
        // Not expecting this to be called
        return null;
    }

    /**
     * @see com.tibco.xpd.datamapper.api.IScriptGeneratorInfoProvider#getCollectionElementScript(java.lang.Object,
     *      java.lang.Object, String)
     * 
     * @param collection
     * @param elemenet
     * @return
     */
    public String getCollectionElementScript(Object collection, Object elemenet) {
        // Not expecting this to be called
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
        // Not expecting this to be called
        return null;
    }

    /**
     * Sid XPD-7660 - method name change
     * 
     * @see com.tibco.xpd.datamapper.api.IScriptGeneratorInfoProvider#getArrayCreationScript(java.lang.Object)
     * 
     * @param complexObject
     * @return
     */
    @Override
    public String getArrayCreationScript(Object complexObject) {
        // Not expecting this to be called
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
        // Not expecting this to be called
        return null;
    }

    /**
     * 
     * @see com.tibco.xpd.datamapper.api.IScriptGeneratorInfoProvider#getCollectionAddElementScript(java.lang.Object,
     *      java.lang.String, java.lang.String, boolean)
     *
     * @param collection
     * @param jsElementToAdd
     * @param objectParentJsVar
     * @param excludeEmptyObjects
     *            Sid ACE-6583 NOTE: Exclusion of empty objects from target arrays not needed for bpm.process util class
     *            properties  - so this flag is ignored
     * @return
     */
    @Override
    public String getCollectionAddElementScript(Object collection,
            String jsElementToAdd, String objectParentJsVar, boolean excludeEmptyObjects) {
        // Not expecting this to be called
        return null;
    }

    /**
     * @see com.tibco.xpd.datamapper.api.IScriptGeneratorInfoProvider#getCollectionSetElementScript(java.lang.Object,
     *      java.lang.String, java.lang.String, java.lang.String)
     * 
     * @param collection
     * @param jsVarName
     * @param objectParentJsVar
     * @param loopIndexJsVar
     * @return
     */
    @Override
    public String getCollectionSetElementScript(Object collection,
            String jsVarName, String objectParentJsVar, String loopIndexJsVar) {
        // Not expecting this to be called
        return null;
    }

    /**
     * Sid XPD-7712
     * 
     * @see com.tibco.xpd.datamapper.api.IScriptGeneratorInfoProvider#getCollectionElementScriptForTargetMerge(java.lang.Object,
     *      java.lang.String, java.lang.String)
     * 
     * @param collection
     * @param indexVarName
     * @param objectParentJsVar
     * @return
     */
    @Override
    public String getCollectionElementScriptForTargetMerge(Object collection,
            String indexVarName, String objectParentJsVar) {
        // Not expecting this to be called
        return null;
    }

    /**
     * @see com.tibco.xpd.datamapper.api.IScriptGeneratorInfoProvider#getClearCollectionScript(java.lang.Object,
     *      java.lang.String)
     * 
     * @param collectionObject
     * @param jsVarAlias
     * @return
     */
    @Override
    public String getClearCollectionScript(Object collectionObject,
            String jsVarAlias) {
        // Not expecting this to be called
        return null;
    }

    /**
     * @see com.tibco.xpd.datamapper.api.IScriptGeneratorInfoProvider#getObjectScriptName(java.lang.Object)
     * 
     * @param object
     * @return
     */
    public String getObjectScriptName(Object object) {
        if (object instanceof ConceptPath) {
            ConceptPath cp = (ConceptPath) object;
            return cp.getName();
        }
        return null;
    }

    /**
     * @see com.tibco.xpd.datamapper.api.IScriptGeneratorInfoProvider#getScriptsToPrepend()
     * 
     * @return
     */
    @Override
    public String getScriptsToPrepend(ScriptDataMapper container,
            boolean isSource) {
        // Not expecting this to be called
        return null;
    }

    /**
     * 
     * @see com.tibco.xpd.datamapper.api.IScriptGeneratorInfoProvider#getCheckNullTreeExpression(java.lang.Object,
     *      java.lang.String,
     *      com.tibco.xpd.datamapper.api.IScriptGeneratorInfoProvider.CheckNullTreeExpressionType)
     * 
     * @param object
     * @param jsVarAlias
     * @param checkType
     * @return
     */
    @Override
    public String getCheckNullTreeExpression(Object object, String jsVarAlias,
            CheckNullTreeExpressionType checkType) {
        /*
         * No object within the "Process" javaScript class content can be null
         * so no need for protection
         */
        return null;
    }

    /**
     * @see com.tibco.xpd.datamapper.api.IScriptGeneratorInfoProvider#resolvePath(java.lang.Object,
     *      java.lang.String)
     * 
     * @param object
     * @param path
     * @return
     */
    @Override
    public String resolvePath(Object object, String path) {
        return path;
    }

    public String getSingleToMultiInstanceAssignmentStatement(
            Object targetItem, String rhsObjectStatement, String jsVarAlias) {
        /*
         * As we have no arrays to assign to then we should never be called to
         * do an assigment to
         */
        throw new RuntimeException(
                "Not expecting getSingleToMultiInstanceAssignmentStatement() to be called for Process JS class mapper content."); //$NON-NLS-1$
    }

    /**
     * @see com.tibco.xpd.datamapper.api.IScriptGeneratorInfoProvider#getDeleteEmptyObjectScript(java.lang.Object,
     *      java.lang.String)
     *
     * @param object
     * @param jsVarAlias
     * @return
     */
    @Override
    public String getDeleteEmptyObjectScript(Object object, String jsVarAlias) {
        /*
         * Sid ACE-6583 Exclusion of empty target objects not currently needed for anything in the bpm.process util
         * class properties.
         */
        return null;
    }

    /**
     * @see com.tibco.xpd.datamapper.api.IScriptGeneratorInfoProvider#getDeleteEmptyArrayScript(java.lang.Object,
     *      java.lang.String)
     *
     * @param object
     * @param jsVarAlias
     * @return
     */
    @Override
    public String getDeleteEmptyArrayScript(Object object, String jsVarAlias) {
        /*
         * Sid ACE-6583 Exclusion of empty target arrays not currently needed for anything in the bpm.process util class
         * properties.
         */
        return null;
    }
}
