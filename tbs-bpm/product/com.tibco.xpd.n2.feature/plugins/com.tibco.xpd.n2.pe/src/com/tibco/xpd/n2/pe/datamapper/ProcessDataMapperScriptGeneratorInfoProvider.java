/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.n2.pe.datamapper;

import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.PrimitiveType;

import com.tibco.bds.designtime.generator.CDSBOMIndexerService;
import com.tibco.xpd.analyst.resources.xpdl2.ReservedWords;
import com.tibco.xpd.analyst.resources.xpdl2.utils.BasicTypeConverterFactory;
import com.tibco.xpd.bom.modeler.custom.enumlitext.util.EnumLitValueUtil;
import com.tibco.xpd.datamapper.api.IScriptGeneratorInfoProvider;
import com.tibco.xpd.processeditor.xpdl2.properties.ChoiceConceptPath;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.xpdExtension.ScriptDataMapper;
import com.tibco.xpd.xpdl2.BasicType;
import com.tibco.xpd.xpdl2.BasicTypeType;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.RecordType;

/**
 * Script generation info provider. For process data data-mapper content.
 * 
 * @author Ali
 * @since 6 Mar 2015
 */
public class ProcessDataMapperScriptGeneratorInfoProvider
        implements IScriptGeneratorInfoProvider {

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
        String assignment = null;
        if (object instanceof ConceptPath) {

            ConceptPath cp = (ConceptPath) object;

            if (jsVarAlias == null) {
                /*
                 * Here we are Using the original content object (field / param
                 * etc)
                 */
                jsVarAlias = cp.getPath();

            } else {
                /*
                 * Here we are Using a temporary JS variable created to
                 * represent a particular array element instance (inside a loop
                 * etc)
                 */
                jsVarAlias = jsVarAlias + "." + cp.getName(); //$NON-NLS-1$
            }

            /* Sid XPD-7996 Allow subclass to adjust final path */
            jsVarAlias = internalFinaliseObjectPath(cp, jsVarAlias);

            /*
             * Sid ACE-564 Enumerations (even enumeration literals) and just string properties now.
             * 
             * So we can just do straight assigns - removed special enum stuff from here.
             */

            assignment = jsVarAlias + " = " + rhsObjectStatement + ";"; //$NON-NLS-1$ //$NON-NLS-2$
        }
        return assignment;
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
        String getter = null;
        if (object instanceof ConceptPath) {
            ConceptPath cp = (ConceptPath) object;

            if (jsVarAlias == null || jsVarAlias.isEmpty()) {
                /*
                 * Here we are Using the original content object (field / param
                 * etc)
                 */
                jsVarAlias = cp.getPath();

            } else if (!cp.isArray()) {
                /*
                 * Here we are Using a temporary JS variable created to
                 * represent a particular array element instance (inside a loop
                 * etc)
                 */
                jsVarAlias = jsVarAlias + "." + cp.getName(); //$NON-NLS-1$
            }

            /* Sid XPD-7996 Allow subclass to adjust final path */
            jsVarAlias = internalFinaliseObjectPath(cp, jsVarAlias);

            /*
             * Sid ACE-564 Enumerations (even enumeration literals) and just
             * string properties now.
             * 
             * So we can just treat them the same as simple type
             */
            if (isSimpleType(cp) || isEnumeration(cp)) {
                getter = jsVarAlias;

            } else {
                /*
                 * Sid ACE-1318 In AMX BPM used to have to copy source object
                 * because the objects are backed by EMF and therefore if you
                 * did Customer2.Address = Customer1.Address the Address object
                 * would get re-parented from Customer 1 to Customer 2 and
                 * Customer1.Address would be null.
                 * 
                 * In the now purely JavaScript representation Customer1 and
                 * Customer2 can be allowed to reference the same Address (this
                 * will only be temporary because the two values will be
                 * serialised to their repsecitvie data fields after script
                 * execution and when reloaded therefore will be separate
                 * copies.
                 */
                getter = jsVarAlias;
            }
        }
        return getter;
    }

    /**
     * shared function that wraps the original 'value getter' statement, with an
     * appropriate enum conversion.
     * 
     * @param getEnumStatement
     * @param enumerationType
     * 
     * @return The original getter converted to an enum conversion string.
     */
    private String getEnumConversionGetterStatement(String getEnumStatement,
            Enumeration enumerationType) {
        String getter;
        PrimitiveType pt = EnumLitValueUtil.getBaseType(enumerationType);
        BasicType basic = BasicTypeConverterFactory.INSTANCE.getBasicType(pt);

        /*
         * TODO When we do Ace-559 Don't forget that we don't support anything
         * BUT text enums now - (plus the fact that there's no such thing as
         * DateTimeUtil JS class etc.
         */

        BasicTypeType base = basic.getType();
        switch (base) {
        case INTEGER_LITERAL:
        case FLOAT_LITERAL:
            getter = "Number(" + getEnumStatement + ")"; //$NON-NLS-1$ //$NON-NLS-2$
            break;
        case DATE_LITERAL:
            getter = "DateTimeUtil.createDate(String(" + getEnumStatement //$NON-NLS-1$
                    + "))"; //$NON-NLS-1$
            break;
        case DATETIME_LITERAL:
            getter = "DateTimeUtil.createDatetimetz(String(" + getEnumStatement //$NON-NLS-1$
                    + "))"; //$NON-NLS-1$
            break;
        case TIME_LITERAL:
            getter = "DateTimeUtil.createTime(String(" + getEnumStatement //$NON-NLS-1$
                    + "))"; //$NON-NLS-1$
            break;
        default:
            getter = "String(" + getEnumStatement + ")"; //$NON-NLS-1$ //$NON-NLS-2$
        }
        return getter;
    }

    /**
     * @param cp
     * @return <code>true</code> if the content type is a BOM enumeration
     */
    private boolean isEnumeration(ConceptPath cp) {
        return cp.getType() instanceof Enumeration;
    }

    /**
     * @param cp
     * @return <code>true</code> the the content object represents a process
     *         data / BOM attribute simple type.
     */
    private boolean isSimpleType(ConceptPath cp) {
        if (cp.getItem() != null) {
            /*
             * Sid XPD-7600. There are some primitive types that do not map onto
             * a Process Data basic type (Duration) for instance. But they are
             * still simple type (as are all primitive types).
             */
            if (cp.getType() instanceof PrimitiveType) {
                return true;
            }
            /*
             * Sid XPD-7723 - Case Reference should be treated as simple type as
             * far as we're concerned.
             */
            else if (cp.getItem() instanceof ProcessRelevantData
                    && ((ProcessRelevantData) cp.getItem())
                            .getDataType() instanceof RecordType) {
                return true;
            }

            /*
             * Any process data of primitive type should be counted as simple
             * content
             */
            BasicType basicType = BasicTypeConverterFactory.INSTANCE
                    .getBasicType(cp.getItem());
            if (basicType != null) {
                return true;
            }
        }
        return false;
    }

    /**
     * Sid XPD-7712
     * 
     * @param cp
     * @return <code>true</code> if concept path is a complex type object.
     */
    private boolean isComplexType(ConceptPath cp) {
        return !isSimpleType(cp) && !isEnumeration(cp);
    }

    /**
     * @see com.tibco.xpd.datamapper.api.IScriptGeneratorInfoProvider#getScriptsToAppend()
     * 
     * @return null, no scripts to append.
     */
    @Override
    public String getScriptsToAppend(ScriptDataMapper container,
            boolean isSource) {
        return null;
    }

    /**
     * @see com.tibco.xpd.datamapper.api.IScriptGeneratorInfoProvider#getScriptsToPrepend()
     * 
     * @return null, no scripts to prepend.
     */
    @Override
    public String getScriptsToPrepend(ScriptDataMapper container,
            boolean isSource) {
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
        if (object instanceof ConceptPath) {
            ConceptPath cp = (ConceptPath) object;
            StringBuilder script = new StringBuilder();

            if (objectParentJsVar != null && !objectParentJsVar.isEmpty()) {
                /*
                 * Here we are Using a temporary JS variable created to
                 * represent a particular array element instance (inside a loop
                 * etc)
                 */
                /* Sid XPD-7996 Allow subclass to adjust final path */

                script.append(internalFinaliseObjectPath(cp,
                        objectParentJsVar + "." + cp.getName())); //$NON-NLS-1$
            } else {
                /*
                 * Here we are Using the original content object (field / param
                 * etc)
                 */
                /* Sid XPD-7996 Allow subclass to adjust final path */
                script.append(internalFinaliseObjectPath(cp, cp.getPath()));

            }

            /* Sid ACE-1318 - Use JS array notation */
            script.append(".length"); //$NON-NLS-1$
            return script.toString();
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
        if (complexObject instanceof ConceptPath) {
            ConceptPath cp = (ConceptPath) complexObject;

            if (cp.getType() != null
                    && cp.getType().getNearestPackage() != null) {
                Package pkg = cp.getType().getNearestPackage();

                String factoryForPackage = CDSBOMIndexerService.getInstance()
                        .getCDSFactoryForPackage(pkg);

                if (factoryForPackage != null
                        && factoryForPackage.length() > 0) {
                    String clazzName = cp.getType().getName();

                    /*
                     * Sid ACE-564 BOM JS class factories are now wrapped in a
                     * "factory" class.
                     */
                    return ReservedWords.BOM_FACTORY_WRAPPER_OBJECT_NAME + "." //$NON-NLS-1$
                            + factoryForPackage + ".create" + clazzName + "()"; //$NON-NLS-1$//$NON-NLS-2$
                }
            }
        }

        return String.format("?? /* Can't create script to create '%1$s' */", //$NON-NLS-1$
                complexObject.toString());
    }

    /**
     * @see com.tibco.xpd.datamapper.api.IScriptGeneratorInfoProvider#getArrayCreationScript(java.lang.Object)
     * 
     * @param complexObject
     * @return
     */
    @Override
    public String getArrayCreationScript(Object complexObject) {
        /*
         * Process data arrays (array fields and Bom.childArray) are statically
         * created (always available) so we don't need to create.
         * 
         * In fact we MUST NOT because these are now special objects created by
         * the dataField descriptor script and must not be initialised as
         * generic JavaScript arrays.
         */
        return null;
    }

    /**
     * Sid XPD-7712: Handle script creation for both
     * {@link #getCollectionElementScript(Object, String, String)} and
     * {@link #getCollectionElementScriptForTargetMerge(Object, String, String)}
     * The difference between these two are whether we're doing a
     * collection.get(i) from LHS source element OR a colleciton.get(i) for a
     * target element.
     * <p>
     * Sid ACE-1318 - In ACE data lists are JavaScript array equivalents and
     * therefore we do not have to ScriptUtil.copy(sourceListElement) (in AMX
     * BPM we had to as the lists were backed by EMF lists and it would have
     * re-parented the source list element to the target list in we'd not copied
     * it).
     * 
     * In ACE there's no need to copy As FAR AS I KNOW AT TIME OF CODING.
     * 
     * HOWEVER just to be on the safe side I will keep the option to copy and
     * simply not use it from the invoking functions (they will always ask not
     * to copy.
     * 
     * Later if we decide we need to then we can implement the alternative to
     * ScriptUtil.copy for ACE
     * 
     * @param collection
     * @param indexVarName
     * @param objectParentJsVar
     * @param dontCreateCopyOfSource
     *            see comment above - currently should always be TRUE for ACE at
     *            the moment.
     * 
     * @return Script to get an element from a process array field / bom multi
     *         instance property.
     */
    private String internalGetCollectionElementScript(Object collection,
            String indexVarName, String objectParentJsVar,
            boolean dontCreateCopyOfSource) {
        if (collection instanceof ConceptPath) {
            ConceptPath cp = (ConceptPath) collection;

            StringBuilder script = new StringBuilder();

            /**
             * Sid XPD-7712: If we are doing a GET from a complex type array
             * then WE MUST copy the element extracted from script.
             * 
             * BDS implements the array as an EMF list and that means an object
             * can only be parented by one list.
             * 
             * So if you just do targetList.add(sourceList.get(i)) the element
             * source(i) will be MOVED from source to target NOT copied (it will
             * disappear from source list!).
             * 
             * So we have to do a ScriptUtil copy.
             * 
             * ACE-1318 EXCEPT that in ACE we aren't using EMF and therefore our
             * callers should pass in true for contCreateCopy
             */
            if (!dontCreateCopyOfSource && isComplexType(cp)) {
                script.append("ScriptUtil.copy("); //$NON-NLS-1$

                throw new RuntimeException(
                        "If we need to create copy of mapped source complex list elements then need to update the copy code here."); //$NON-NLS-1$

            }

            if (objectParentJsVar != null && !objectParentJsVar.isEmpty()) {
                /*
                 * Here we are Using a temporary JS variable created to
                 * represent a particular array element instance (inside a loop
                 * etc)
                 */
                /* Sid XPD-7996 Allow subclass to adjust final path */

                script.append(internalFinaliseObjectPath(cp,
                        objectParentJsVar + "." + cp.getName())); //$NON-NLS-1$
            } else {
                /*
                 * Here we are Using the original content object (field / param
                 * etc)
                 */
                script.append(internalFinaliseObjectPath(cp, cp.getPath()));
            }

            /* SId ACE-1318 Use JS array notation. */
            script.append("["); //$NON-NLS-1$
            script.append(indexVarName);
            script.append("]"); //$NON-NLS-1$

            if (!dontCreateCopyOfSource && isComplexType(cp)) {
                script.append(")"); //$NON-NLS-1$
            }

            /*
             * Sid ACE-564 Enumerations (even enumeration literals) and just
             * string properties now.
             * 
             * So we can just treat them the same as simple type
             */
            return script.toString();
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
        /*
         * Sid ACE-1318 As we're using JS arrays, shouldn't need to create copy
         * of source element any more. see internalGetCollectionElementScript()
         * description for more details.
         */
        return internalGetCollectionElementScript(collection,
                indexVarName,
                objectParentJsVar,
                true);
    }

    /**
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
        return internalGetCollectionElementScript(collection,
                indexVarName,
                objectParentJsVar,
                true);
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
        if (collection instanceof ConceptPath) {
            ConceptPath cp = (ConceptPath) collection;

            StringBuilder script = new StringBuilder();

            if (objectParentJsVar != null && !objectParentJsVar.isEmpty()) {
                /*
                 * Here we are Using a temporary JS variable created to
                 * represent a particular array element instance (inside a loop
                 * etc)
                 */
                /* Sid XPD-7996 Allow subclass to adjust final path */
                script.append(internalFinaliseObjectPath(cp,
                        objectParentJsVar + "." + cp.getName())); //$NON-NLS-1$

            } else {
                /*
                 * Here we are Using the original content object (field / param
                 * etc)
                 */
                /* Sid XPD-7996 Allow subclass to adjust final path */
                script.append(internalFinaliseObjectPath(cp, cp.getPath()));
            }

            /* Sid ACE-1318 Use JS array notation. */
            script.append(".push("); //$NON-NLS-1$

            /*
             * Sid ACE-564 Enumerations (even enumeration literals) and just
             * string properties now.
             * 
             * So we can just treat them the same as simple type
             */
            script.append(jsVarName);

            script.append(");"); //$NON-NLS-1$

            return script.toString();
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
     * @param loopIndexJsVar
     * @return
     */
    @Override
    public String getCollectionSetElementScript(Object collection,
            String jsVarName, String objectParentJsVar, String loopIndexJsVar) {
        if (collection instanceof ConceptPath) {
            ConceptPath cp = (ConceptPath) collection;

            StringBuilder script = new StringBuilder();

            if (objectParentJsVar != null && !objectParentJsVar.isEmpty()) {
                /*
                 * Here we are Using a temporary JS variable created to
                 * represent a particular array element instance (inside a loop
                 * etc)
                 */
                /* Sid XPD-7996 Allow subclass to adjust final path */
                script.append(internalFinaliseObjectPath(cp,
                        objectParentJsVar + "." + cp.getName())); //$NON-NLS-1$

            } else {
                /*
                 * Here we are Using the original content object (field / param
                 * etc)
                 */
                /* Sid XPD-7996 Allow subclass to adjust final path */
                script.append(internalFinaliseObjectPath(cp, cp.getPath()));
            }

            /* Sid ACE-1318 Use JS Array notation. */
            script.append("["); //$NON-NLS-1$
            script.append(loopIndexJsVar);
            script.append("] = "); //$NON-NLS-1$
            script.append(jsVarName);
            script.append(";"); //$NON-NLS-1$
            return script.toString();
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
        if (collectionObject instanceof ConceptPath) {
            ConceptPath cp = (ConceptPath) collectionObject;
            StringBuilder script = new StringBuilder();

            if (jsVarAlias != null && !jsVarAlias.isEmpty()) {
                /*
                 * Here we are Using a temporary JS variable created to
                 * represent a particular array element instance (inside a loop
                 * etc)
                 */
                /* Sid XPD-7996 Allow subclass to adjust final path */
                script.append(internalFinaliseObjectPath(cp,
                        jsVarAlias + "." + cp.getName())); //$NON-NLS-1$

            } else {
                /*
                 * Here we are Using the original content object (field / param
                 * etc)
                 */
                /* Sid XPD-7996 Allow subclass to adjust final path */
                script.append(internalFinaliseObjectPath(cp, cp.getPath()));
            }

            /*
             * Sid ACE-1318 In ACE process data lists are actually arrays, so
             * set length = 0; instead of clear
             */
            script.append(".length = 0"); //$NON-NLS-1$

            return script.toString();
        }
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

        /**
         * Sid XPD-7975: Note that for process data, if we are null checking
         * source object for for loop iterator, then we check right down to and
         * including the source array itself.
         * 
         * On the other hand, if this is a single instance mapping then we only
         * bother checking down to **but not including** the mapped object
         * itself.
         * 
         * This is because, in the process-data type system all fields and
         * child-properties *are always defined in scope* so will always exist
         * in scope and may be either a value or null. Therefore for exception
         * free execution we only need to null check the ancestor tree of the
         * mapped object to make sure we're not referencing something inside a
         * null object.
         * 
         * This saves unnecessary bloat of generated script doing... <code>
         * if (SrcField != null) {
         *     TgtField = SrcField;
         * } else {
         *     TgtField = null;
         * }
         * 
         * if (SrcBOMField != null && SrcBOMField.property != null) {
         *     TgtField2 = SrcBOMField.property;
         * } else {
         *     TgtField2 = null;
         * }          
         * </code>
         * <p>
         * Instead of... <code>
         * TgtField = SrcField;
         * 
         * if (SrcBOMField != null) {
         *     TgtField2 = SrcBOMField.property;
         * } else {
         *     TgtField2 = null;
         * }          
         * </code>
         * <p>
         * Which is effectively the same because in the first case the else
         * clause is unnecessary becasuie SrcField either has a value OR is null
         * AND in the second case, if srcBOMField is set, then
         * SrcBOMField.property will either have a value or it will be null (and
         * therefore set the target to null anyway).
         */
        boolean checkLastElementInPath = true;

        if (CheckNullTreeExpressionType.SINGLE_INSTANCE_MAPPING
                .equals(checkType)) {

            /*
             * Sid ACE-564 Enumerations (even enumeration literals) and just
             * string properties now.
             * 
             * So we can just treat them the same as simple type
             */
            checkLastElementInPath = false;
        }

        String pathToCheck = null;

        if (jsVarAlias != null && jsVarAlias.length() > 0) {
            /*
             * Here we are Using a temporary JS variable created to represent a
             * particular array element instance (inside a loop etc)
             */
            pathToCheck = jsVarAlias;

        } else if (object instanceof ConceptPath) {
            /*
             * Here we are Using the original content object (field / param etc)
             */
            pathToCheck = internalFinaliseObjectPath((ConceptPath) object,
                    ((ConceptPath) object).getPath());
        }

        /* Sid XPD-7996 Allow subclass to adjust final path */

        if (pathToCheck != null && pathToCheck.length() > 0) {
            /*
             * If there's an alias then we have to check it for null.
             * 
             * **Note though that in situations where we're mapping nested
             * content from within arrays jsVarAlis may be...
             * 
             * srcVi1.child.grandchild
             * 
             * Therefore nothing may have checked the null-ness of srcVi1.child
             * yet. So we'll have to chop and check each part.
             */
            String[] parts = pathToCheck.split("\\."); //$NON-NLS-1$

            StringBuilder sb = new StringBuilder();

            String pathTilNow = ""; //$NON-NLS-1$

            int partsToIterate = parts.length;

            if (!checkLastElementInPath) {
                partsToIterate--;
            }

            for (int i = 0; i < partsToIterate; i++) {
                String part = parts[i];
                /*
                 * The only reason for having source alias is for temp var
                 * within source tree (which we will have copied from a source
                 * list, so not need to check the root of the tree in this
                 * case).
                 */
                if (jsVarAlias != null && jsVarAlias.length() > 0 && i == 0) {
                    /* skip alias variable itself. */
                    pathTilNow = part;
                    continue;
                }

                /*
                 * Sid XPD-7932 Ignore special 'choice identifier elements in
                 * path'
                 */
                if (part.startsWith(ChoiceConceptPath.CHOICE_PATH_PREFIX)) {
                    continue;
                }

                if (sb.length() > 0) {
                    /* Not the first condition we've added so AND it. */
                    sb.append(" && "); //$NON-NLS-1$
                }

                if (i == 0) {
                    pathTilNow = part;
                } else {
                    pathTilNow = pathTilNow + "." + part; //$NON-NLS-1$
                }

                sb.append(pathTilNow);
                sb.append(" != null"); //$NON-NLS-1$
            }

            String expression = sb.toString();

            if (expression.trim().length() > 0) {
                return expression;
            }

        }

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
        /*
         * Sid XPD-7996. Give the sub-class an opportunity to fiddle with the
         * path to finalise it. But only if it's not a temporary variable
         */
        if (object instanceof ConceptPath && path != null
                && !path.startsWith(SOURCE_VAR_PREFIX)
                && !path.startsWith(TARGET_VAR_PREFIX)) {
            return internalFinaliseObjectPath((ConceptPath) object, path);
        }
        return path;
    }

    /**
     * 
     * @see com.tibco.xpd.datamapper.api.IScriptGeneratorInfoProvider#getSingleToMultiInstanceAssignmentStatement(java.lang.Object,
     *      java.lang.String, java.lang.String)
     * 
     * @param targetItem
     * @param rhsObjectStatement
     * @param jsVarAlias
     * @return
     */
    @Override
    public String getSingleToMultiInstanceAssignmentStatement(Object targetItem,
            String rhsObjectStatement, String jsVarAlias) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * In some circumstances the script generation for a particular scenario may
     * need to alter the path used for a given source / target element because
     * the design time representation of a object path (say BOMField.sub.attr1)
     * may be different from the runtime object in scope of the generated script
     * (say _BX_Param.sub.attr1
     * <p>
     * This method gives any subclass the opportunity to adjust the object path
     * prior to it being used in a script.
     * 
     * @param designTimePath
     * @param pathOrJsVarAlias
     *            The default path for designTimePath. When used by this class
     *            then this CANNOT be the a temporary variable for loop
     *            iteration (prefixed with
     *            {@link IScriptGeneratorInfoProvider#SOURCE_VAR_PREFIX}
     *            {@link IScriptGeneratorInfoProvider#TARGET_VAR_PREFIX}) as
     *            this is prevented by
     *            {@link #internalFinaliseObjectPath(ConceptPath, String)}
     * 
     * @return The original path or adjusted path if necessary.
     */
    protected String finaliseObjectPath(ConceptPath designTimePath,
            String pathOrJsVarAlias) {
        /* Sid ACE-1318 All process data is now wrapped in a "data" object. */
        return ReservedWords.PROCESS_DATA_WRAPPER_OBJECT_NAME
                + ConceptPath.CONCEPTPATH_SEPARATOR + pathOrJsVarAlias;
    }

    /**
     * In some circumstances the script generation for a particular scenario may
     * need to alter the path used for a given source / target element because
     * the design time representation of a object path (say BOMField.sub.attr1)
     * may be different from the runtime object in scope of the generated script
     * (say _BX_Param.sub.attr1
     * <p>
     * This method gives any subclass the opportunity to adjust the object path
     * prior to it being used in a script.
     * 
     * @param designTimePath
     * @param pathOrJsVarAlias
     *            The default path for designTimePath or potentially a temporary
     *            variable for loop iteration (in which case it will be prefixed
     *            with {@link IScriptGeneratorInfoProvider#SOURCE_VAR_PREFIX}
     *            {@link IScriptGeneratorInfoProvider#TARGET_VAR_PREFIX}
     * 
     * @return The original path or adjusted path if necessary.
     */
    private String internalFinaliseObjectPath(ConceptPath designTimePath,
            String pathOrJsVarAlias) {
        if (!pathOrJsVarAlias.startsWith(SOURCE_VAR_PREFIX)
                && !pathOrJsVarAlias.startsWith(TARGET_VAR_PREFIX)) {
            return finaliseObjectPath(designTimePath, pathOrJsVarAlias);
        }
        return pathOrJsVarAlias;
    }
}
