/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */
package com.tibco.xpd.n2.pe.rest.datamapper;

import java.util.List;

import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Property;

import com.tibco.xpd.analyst.resources.xpdl2.utils.BasicTypeConverterFactory;
import com.tibco.xpd.bom.types.PrimitivesUtil;
import com.tibco.xpd.datamapper.api.IScriptGeneratorInfoProvider;
import com.tibco.xpd.datamapper.api.JavaScriptStringBuilder;
import com.tibco.xpd.implementer.resources.xpdl2.mappings.RestMapperTreeItem;
import com.tibco.xpd.implementer.resources.xpdl2.mappings.RestMapperTreeItemFactory;
import com.tibco.xpd.implementer.resources.xpdl2.mappings.RestMappingPrefix;
import com.tibco.xpd.implementer.resources.xpdl2.mappings.RestParamContainerTreeItem;
import com.tibco.xpd.implementer.resources.xpdl2.mappings.RestParamTreeItem;
import com.tibco.xpd.implementer.resources.xpdl2.mappings.UnprocessedTextRestMapperTreeItem;
import com.tibco.xpd.implementer.resources.xpdl2.properties.RestServiceTaskAdapter;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.processeditor.xpdl2.properties.RestConceptPath;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.rest.schema.JsonSchemaUtil;
import com.tibco.xpd.rsd.DataType;
import com.tibco.xpd.rsd.Fault;
import com.tibco.xpd.rsd.HttpMethod;
import com.tibco.xpd.rsd.Method;
import com.tibco.xpd.rsd.Parameter;
import com.tibco.xpd.rsd.ParameterStyle;
import com.tibco.xpd.rsd.PayloadRefContainer;
import com.tibco.xpd.rsd.PayloadReference;
import com.tibco.xpd.rsd.Request;
import com.tibco.xpd.rsd.Resource;
import com.tibco.xpd.rsd.Service;
import com.tibco.xpd.ui.util.NameUtil;
import com.tibco.xpd.xpdExtension.ScriptDataMapper;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.BasicType;
import com.tibco.xpd.xpdl2.ResultError;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Data Mapper script generator info provider for REST Invocation services.
 * 
 * @author nwilson
 * @since 1 May 2015
 */
@SuppressWarnings("nls")
public class RestScriptGeneratorInfoProvider
        implements IScriptGeneratorInfoProvider {

    /**
     * @see com.tibco.xpd.datamapper.api.IScriptGeneratorInfoProvider#getAssignmentStatement(java.lang.Object,
     *      java.lang.String, java.lang.String)
     * 
     * @param object
     *            The object for the LHS statement.
     * @param rhsObjectStatement
     *            The RHS statement.
     * @param jsVarAlias
     *            parent alias var.
     * @return The assignment statement.
     */
    @Override
    public String getAssignmentStatement(Object object,
            String rhsObjectStatement, String jsVarAlias) {
        StringBuilder statement = new StringBuilder();
        
        String lhsRestPath;
        
        if (jsVarAlias == null || jsVarAlias.isEmpty()) {
            lhsRestPath = convertPath(getPath(object));
            
        } else {
            lhsRestPath =
                    convertPath(jsVarAlias) + "['" + getName(object) + "']"; //$NON-NLS-1$ //$NON-NLS-2$
        }
        
        statement.append(lhsRestPath);
        statement.append(" = "); //$NON-NLS-1$
        appendRhsObjectStatement(object, rhsObjectStatement, statement);
        statement.append(";"); //$NON-NLS-1$
        
        /* 
         * Sid ACE-6367 Delete REST input data rather that setting properties to null
         * 
         * BUT ONLY If not a Query/Header/Path parameter as these are all top level temp vars and we can't delete those. 
         */
        if (getParameterStyle(object) == null) {
            statement.append("if (");
            statement.append(lhsRestPath);
            statement.append(" === null) { ");
            statement.append("delete ");
            statement.append(lhsRestPath);
            statement.append("; }");
        }
         
        return statement.toString();
    }

    /**
     * @see com.tibco.xpd.datamapper.api.IScriptGeneratorInfoProvider#getAssignmentElseStatement(java.lang.Object, java.lang.String, java.lang.String)
     *
     * @param object
     * @param rhsObjectStatement
     * @param jsVarAlias
     * @return
     */
    @Override
    public String getAssignmentElseStatement(Object object, String jsVarAlias) {
        /* 
         * Sid ACE-6367 Delete REST input data rather that setting properties to null
         * 
         * Delete the target data (1st set to null so that the delete will always work even if target did not exist).
         * i.e...
         * 
         *  $REST_PAYLOAD['consumerRequestType'] = null;
         *  delete $REST_PAYLOAD['consumerRequestType'];
         */
        StringBuilder statement = new StringBuilder();
        
        String lhsRestPath;
        
        if (jsVarAlias == null || jsVarAlias.isEmpty()) {
            lhsRestPath = convertPath(getPath(object));
            
        } else {
            lhsRestPath =
                    convertPath(jsVarAlias) + "['" + getName(object) + "']"; //$NON-NLS-1$ //$NON-NLS-2$
        }
        
        /* $REST_PAYLOAD['xxxxx'] = null; */
        statement.append(lhsRestPath);
        statement.append(" = "); //$NON-NLS-1$
        statement.append("null"); //$NON-NLS-1$
        statement.append(";"); //$NON-NLS-1$
        
        /*  delete $REST_PAYLOAD['xxxxx']; // BUT ONLY If not a Query/Header/Path parameter as these are all top level temp vars and we can't delete those. */
        if (getParameterStyle(object) == null) {
            statement.append("delete "); //$NON-NLS-1$
            statement.append(lhsRestPath);
            statement.append(";"); //$NON-NLS-1$
        }
        
        return statement.toString();
    }
    
    /**
     * 
     * @param object
     * @return The {@link ParameterStyle} if the given object is a {@link RestParamTreeItem}
     */
    private ParameterStyle getParameterStyle(Object object) {
        if (object instanceof RestParamTreeItem) {
            Parameter param = ((RestParamTreeItem) object).getParam();
            
            if (param != null) {
                return param.getStyle();
            }
        }
        return null;
    }
    
    /**
     * Sid: ACE-6853 Confusingly named as RHS (it means RHS of an assignment statemet not RHS of mappings) 
     * 
     * This  Appends the statement that will get value of he SOURCE of a mapping and perform any necessary pre-processing on it.
     *   
     * @param object
     * @param rhsObjectStatement
     * @param statement
     */
    private void appendRhsObjectStatement(Object object,
            String rhsObjectStatement, StringBuilder statement) {
        String suffix = ""; //$NON-NLS-1$

        if ("null".equals(rhsObjectStatement)) { //$NON-NLS-1$
            /**
             * Sid XPD-7814 - The script generator framework may pass us
             * literally "null" so in this case we just want to return it.
             */

        } else if (object instanceof RestParamTreeItem) {
            RestParamTreeItem item = (RestParamTreeItem) object;
            Parameter param = item.getParam();
            DataType type = param.getDataType();
            switch (type) {
            case TEXT:
                suffix = " ? new String(" + rhsObjectStatement + ") : null"; //$NON-NLS-1$ //$NON-NLS-2$
                break;
            case BOOLEAN:
            case DECIMAL:
                suffix = " ? " + rhsObjectStatement //$NON-NLS-1$
                        + " : null"; //$NON-NLS-1$
                break;
            case INTEGER:
                suffix = " ? Math.round(" + rhsObjectStatement //$NON-NLS-1$
                        + ") : null"; //$NON-NLS-1$
                break;
            case DATE:
                suffix = " ? " + rhsObjectStatement //$NON-NLS-1$
                        + ".toJSON().substring(0,10) : null"; //$NON-NLS-1$
                break;
            case DATE_TIME:
                suffix = " ? " + rhsObjectStatement //$NON-NLS-1$
                        + ".toJSON() : null"; //$NON-NLS-1$
                break;
            case TIME:
                suffix = " ? " + rhsObjectStatement //$NON-NLS-1$
                        + ".toJSON().substring(11,16) : null"; //$NON-NLS-1$
                break;
            }
        } else if (object instanceof ConceptPath) {
            ConceptPath cp = (ConceptPath) object;
            Classifier type = cp.getType();
            if (type instanceof PrimitiveType) {
                PrimitiveType pt = (PrimitiveType) type;
                PrimitiveType basePt = PrimitivesUtil.getBasePrimitiveType(pt);
                String name = basePt.getName();
                if (PrimitivesUtil.BOM_PRIMITIVE_DATE_NAME.equals(name)) {
                    suffix = " ? " + rhsObjectStatement //$NON-NLS-1$
                            + ".toJSON().substring(0,10) : null"; //$NON-NLS-1$
                } else if (PrimitivesUtil.BOM_PRIMITIVE_DATETIMETZ_NAME.equals(name)) {
                    suffix = " ? " + rhsObjectStatement //$NON-NLS-1$
                            + ".toJSON() : null"; //$NON-NLS-1$
                } else if (PrimitivesUtil.BOM_PRIMITIVE_TIME_NAME.equals(name)) {
                    suffix = " ? " + rhsObjectStatement //$NON-NLS-1$
                            + ".toJSON().substring(11,16) : null"; //$NON-NLS-1$
                } else if (PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME
                        .equals(name)) {
                    suffix = " ? Math.round(" + rhsObjectStatement //$NON-NLS-1$
                            + ") : null"; //$NON-NLS-1$

                } else if (PrimitivesUtil.BOM_PRIMITIVE_BOOLEAN_NAME
                        .equals(name)) {
                    /*
                     * Sid XPD-8277 "!= null" is now done for all types at end
                     * of method now.
                     */
                    suffix = " ? " + rhsObjectStatement //$NON-NLS-1$
                            + " : null"; //$NON-NLS-1$
                } else if (PrimitivesUtil.BOM_PRIMITIVE_DECIMAL_NAME
                        .equals(name)) {
                    suffix = " ? " + rhsObjectStatement //$NON-NLS-1$
                            + " : null"; //$NON-NLS-1$
                } else if (PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME
                        .equals(name)) {
                    
                    
                    /*
                     * Sid ACE-6809 (ACE-6583) If the target input property is optional, then exclude value if source field is empty.
                     * 
                     * This is to work around 'text attributes are set to empty string by Forms/Case-Data-manager even when user hasn't entered any value'.
                     */
                    boolean excludeEmptyString = false;
                    
                    if (cp.getItem() instanceof Property) {
                        Property property = (Property) cp.getItem();
                        
                        if (property.getLower() == 0 && !cp.isArray()) {
                            // Minimum instances == 0 == optional REST property.
                            // For arrays, best just leave as is.
                            excludeEmptyString = true;
                        }
                    }

                    if (excludeEmptyString) {
                        suffix = " && ("+rhsObjectStatement + ".length > 0)" + " ? new String(" + rhsObjectStatement + ") : null"; //$NON-NLS-1$ //$NON-NLS-2$
                        
                    } else {
                        // For required target REST property always map EVEN in empty string.
                        suffix = " ? new String(" + rhsObjectStatement + ") : null"; //$NON-NLS-1$ //$NON-NLS-2$
                    }
                }
            }
        }

        /*
         * Sid XPD-8277 - Can't just use "SRC ? (typeof == 'object' ......"
         * because if SRC is a number or boolean then "SRC" on it's own
         * evaluates to false and therefore the 'null' else clause would be
         * used.
         * 
         * hence if source value was zero or false then the target would get set
         * to null. So instead we do a definitive check for not null and not
         * undefined (which != will do).
         * 
         * Sid XPD-8277 FIXED - Except that IF there is no suffix we should just
         * use the rhsObjectStatement itself. Otherwise (for instance when doing
         * the covalue ntent of a tgt.push(value) statement we would put
         * tgt.push((value != null)) which always pushes a boolean. So if there
         * is no suffix " ? xxxxxx" then we should not be creating a condition
         * to put in front of it!
         */
        if (suffix != null && suffix.length() > 0) {
            statement.append("(" + rhsObjectStatement + " != null)"); //$NON-NLS-1$ //$NON-NLS-2$
            statement.append(suffix);
        } else {
            statement.append(rhsObjectStatement);
        }
    }

    /**
     * @see com.tibco.xpd.datamapper.api.IScriptGeneratorInfoProvider#getGetterStatement(java.lang.Object,
     *      java.lang.String)
     * 
     * @param object
     *            The object to get the value for.
     * @param jsVarAlias
     *            The parent alias var.
     * @return The statement to get the value.
     */
    @Override
    public String getGetterStatement(Object object, String jsVarAlias) {
        String statement = null;
        String suffix = ""; //$NON-NLS-1$
        if (jsVarAlias == null || jsVarAlias.isEmpty()) {
            jsVarAlias = convertPath(getPath(object));
        } else {
            jsVarAlias =
                    convertPath(jsVarAlias) + "['" + getName(object) + "']"; //$NON-NLS-1$ //$NON-NLS-2$
        }
        if (object instanceof ConceptPath) {
            ConceptPath cp = (ConceptPath) object;
            Classifier type = cp.getType();
            if (type instanceof PrimitiveType) {
                PrimitiveType pt = (PrimitiveType) type;
                PrimitiveType basePt = PrimitivesUtil.getBasePrimitiveType(pt);
                String name = basePt.getName();
                if (PrimitivesUtil.BOM_PRIMITIVE_DATE_NAME.equals(name)
                        || PrimitivesUtil.BOM_PRIMITIVE_DATETIMETZ_NAME
                                .equals(name)
                        || PrimitivesUtil.BOM_PRIMITIVE_TIME_NAME
                                .equals(name)) {
                    suffix = " ? new Date(" + jsVarAlias //$NON-NLS-1$
                            + ") : null"; //$NON-NLS-1$
                }
            }
        }

        if (isSimpleType(object)) {
            statement = jsVarAlias + suffix;
        } else {
            statement = "JSON.parse(JSON.stringify(" + jsVarAlias + "))"; //$NON-NLS-1$ //$NON-NLS-2$
        }
        return statement;
    }

    /**
     * Converts a dot separated path to reference children using ['child'].
     * 
     * @param path
     *            The dot separated path.
     * @return The converted path.
     */
    private String convertPath(String path) {
        String converted = null;
        if (path != null) {
            StringBuilder sb = new StringBuilder();
            String[] parts = path.split("\\."); //$NON-NLS-1$
            if (parts.length > 0) {
                sb.append(parts[0]);
            }
            for (int i = 1; i < parts.length; i++) {
                sb.append("['"); //$NON-NLS-1$
                sb.append(parts[i]);
                sb.append("']"); //$NON-NLS-1$
            }
            converted = sb.toString();
        }
        return converted;
    }

    /**
     * Prepends a script to the REST input or output mapping script to declare
     * any necessary variables.
     * 
     * @see com.tibco.xpd.datamapper.api.IScriptGeneratorInfoProvider#getScriptsToPrepend(com.tibco.xpd.xpdExtension.ScriptDataMapper,
     *      boolean)
     * 
     * @param container
     *            The script data mapper containing the mapping.
     * @param isSource
     *            true if the REST data is the mapping source.
     * @return Scripts to prepend to the final mapping script.
     */
    @Override
    public String getScriptsToPrepend(ScriptDataMapper container,
            boolean isSource) {
        JavaScriptStringBuilder jssb = new JavaScriptStringBuilder();
        Activity activity = Xpdl2ModelUtil.getParentActivity(container);
        RestMapperTreeItemFactory factory =
                RestMapperTreeItemFactory.getInstance();
        if (isSource) {
            // Declare response header variables.
            jssb.addComment("Get response data."); //$NON-NLS-1$
            RestParamContainerTreeItem headerItems =
                    factory.createParamContainerTreeItem(activity,
                            ParameterStyle.HEADER,
                            MappingDirection.OUT);

            for (RestMapperTreeItem item : headerItems.getChildren()) {
                if (item instanceof RestParamTreeItem) {
                    RestParamTreeItem param = (RestParamTreeItem) item;
                    jssb.append("var "); //$NON-NLS-1$
                    jssb.append(getPath(param));
                    jssb.append(" = REST_RESPONSE.getHeader('"); //$NON-NLS-1$
                    jssb.append(param.getParam().getName());
                    jssb.addLine("');"); //$NON-NLS-1$
                }
            }

            /*
             * Sid XPD-7805 : Distinguish between json, unprocessedtext and not
             * set.
             */
            PayloadType payloadType = getPayloadType(activity, isSource);

            jssb.addLine("var REST_STATUS_CODE = REST_RESPONSE.getStatus();"); //$NON-NLS-1$

            /*
             * Sid XPD-7805 catch parse exceptions so that we can provide a
             * better logged exception
             */
            jssb.addLine("var " + RestMappingPrefix.PAYLOAD.getPrefix() //$NON-NLS-1$
                    + " = null;"); //$NON-NLS-1$
            jssb.addLine(""); //$NON-NLS-1$

            /*
             * Sid XPD-7805 : If there is no response type set then do not parse
             * (as we don't know what type it might actually be
             */
            if (!PayloadType.UNASSIGNED.equals(payloadType)) {
                if (PayloadType.UNPROCESSED_TEXT.equals(payloadType)) {
                    /*
                     * Raw text payload assignment is easy.
                     */
                    jssb.addLine(RestMappingPrefix.PAYLOAD.getPrefix()
                            + " = REST_RESPONSE.getData();"); //$NON-NLS-1$

                } else {
                    /*
                     * For JSON payloads we need to handle with care!
                     */
                    jssb.addLine("try {", true, false); //$NON-NLS-1$

                    jssb.addLine(RestMappingPrefix.PAYLOAD.getPrefix()
                            + "  = JSON.parse(REST_RESPONSE.getData());"); //$NON-NLS-1$

                    /*
                     * wrap
                     */
                    jssb.addLine("} catch(ex) {", true, true); //$NON-NLS-1$
                    jssb.addComment(
                            "Wrap exception in something more obvious if the response data parse fails (probably not JSON format)"); //$NON-NLS-1$

                    jssb.addLine(
                            "throw \"    JSON Response payload parsing failed (not formatted correctly as JSON string?).\\n\" + ", //$NON-NLS-1$
                            true,
                            false);
                    jssb.addLine(
                            "\"        Parser Exception: \" + ex.message + \"\\n---------------------------------\\n\" + "); //$NON-NLS-1$
                    jssb.addLine(
                            "\"        Payload Text: \\n\" + REST_RESPONSE.getData() + \"\\n---------------------------------\\n\";"); //$NON-NLS-1$
                    jssb.addLine("", false, true); //$NON-NLS-1$
                    jssb.addLine("}", false, true); //$NON-NLS-1$
                }
            }

            /*
             * Assign default values from RSD param definitions if mapping
             * source for param null
             */
            assignDefaultParameterVars(activity,
                    factory,
                    ParameterStyle.HEADER,
                    jssb,
                    MappingDirection.OUT);

        } else {
            // Process to REST prepended script. Declare header, parameter and
            // payload variables that will be assigned in generated script.
            jssb.addComment("Declare local variables."); //$NON-NLS-1$
            declareParameterVars(activity,
                    factory,
                    ParameterStyle.HEADER,
                    jssb);
            declareParameterVars(activity, factory, ParameterStyle.PATH, jssb);
            declareParameterVars(activity, factory, ParameterStyle.QUERY, jssb);
            jssb.append("var "); //$NON-NLS-1$
            jssb.append(RestMappingPrefix.PAYLOAD.getPrefix());
            jssb.addLine(";"); //$NON-NLS-1$
        }
        return jssb.toString();
    }

    /**
     * Helper method to create variable declarations for REST parameters.
     * 
     * @param activity
     *            The activity containing the mapping.
     * @param factory
     *            The REST mapper tree item factory.
     * @param paramStyle
     *            The style of parameters to declare vars for.
     * @param jssb
     *            The script builder to append the declarations to.
     */
    private void declareParameterVars(Activity activity,
            RestMapperTreeItemFactory factory, ParameterStyle paramStyle,
            JavaScriptStringBuilder jssb) {
        RestParamContainerTreeItem headerItems =
                factory.createParamContainerTreeItem(activity,
                        paramStyle,
                        MappingDirection.IN);
        for (RestMapperTreeItem item : headerItems.getChildren()) {
            if (item instanceof RestParamTreeItem) {
                RestParamTreeItem param = (RestParamTreeItem) item;
                jssb.append("var "); //$NON-NLS-1$
                jssb.append(getPath(param));
                jssb.addLine(";"); //$NON-NLS-1$
            }
        }
    }

    /**
     * Helper method to create variable declarations for REST parameters.
     * 
     * @param activity
     *            The activity containing the mapping.
     * @param factory
     *            The REST mapper tree item factory.
     * @param paramStyle
     *            The style of parameters to declare vars for.
     * @param jssb
     *            The script builder to append the declarations to.
     */
    private void assignDefaultParameterVars(Activity activity,
            RestMapperTreeItemFactory factory, ParameterStyle paramStyle,
            JavaScriptStringBuilder jssb, MappingDirection direction) {
        RestParamContainerTreeItem items = factory
                .createParamContainerTreeItem(activity, paramStyle, direction);
        boolean commentAdded = false;
        for (RestMapperTreeItem item : items.getChildren()) {
            if (item instanceof RestParamTreeItem) {
                RestParamTreeItem paramItem = (RestParamTreeItem) item;
                Parameter param = paramItem.getParam();
                String defaultValue = param.getDefaultValue();
                if (defaultValue != null && defaultValue.length() > 0) {
                    if (!commentAdded) {
                        jssb.addComment("Assign default " //$NON-NLS-1$
                                + paramStyle.getName() + " values."); //$NON-NLS-1$
                        commentAdded = true;
                    }
                    jssb.append("if ("); //$NON-NLS-1$
                    jssb.append(getPath(paramItem));
                    jssb.append(" == null) {"); //$NON-NLS-1$
                    jssb.append(getPath(paramItem));
                    jssb.append(" = '"); //$NON-NLS-1$
                    jssb.append(defaultValue);
                    jssb.addLine("'};"); //$NON-NLS-1$
                }
            }
        }
    }

    /**
     * Appends a script to the REST input mappings to build the request object
     * from the parameter and payload mapping data.
     * 
     * @see com.tibco.xpd.datamapper.api.IScriptGeneratorInfoProvider#getScriptsToAppend(com.tibco.xpd.xpdExtension.ScriptDataMapper,
     *      boolean)
     * 
     * @param container
     *            The script data mapper containing the mapping.
     * @param isSource
     *            true if the REST data is the mapping source.
     * @return Scripts to append to the final mapping script.
     */
    @Override
    public String getScriptsToAppend(ScriptDataMapper container,
            boolean isSource) {
        if (!isSource) {
            RestMapperTreeItemFactory factory =
                    RestMapperTreeItemFactory.getInstance();
            Activity activity = Xpdl2ModelUtil.getParentActivity(container);
            JavaScriptStringBuilder jssb = new JavaScriptStringBuilder();
            
            RestServiceTaskAdapter rsta = new RestServiceTaskAdapter();
            Method method = rsta.getRSOMethod(activity);
            if (method != null) {
                HttpMethod httpMethod = method.getHttpMethod();

                PayloadType payloadType = getPayloadType(method, isSource);

                // Process to REST appended script. Package declared variable as
                // a request.

                // Assign default values where applicable.
                assignDefaultParameterVars(activity,
                        factory,
                        ParameterStyle.HEADER,
                        jssb,
                        MappingDirection.IN);
                assignDefaultParameterVars(activity,
                        factory,
                        ParameterStyle.PATH,
                        jssb,
                        MappingDirection.IN);
                assignDefaultParameterVars(activity,
                        factory,
                        ParameterStyle.QUERY,
                        jssb,
                        MappingDirection.IN);

                // Build URL.
                jssb.addComment("Build request URI."); //$NON-NLS-1$
                appendQueryUriFunctions(jssb);
                jssb.append("var REST_REQUEST_URI = "); //$NON-NLS-1$
                buildUri(jssb, factory, activity, method);
                jssb.addLine(";"); //$NON-NLS-1$

                jssb.addComment("Set request fields."); //$NON-NLS-1$
                Request request = method.getRequest();
                if (request != null) {
                    jssb.addLine("REST_REQUEST.setHeader('Content-Type','" //$NON-NLS-1$
                            + request.getContentType() + "');"); //$NON-NLS-1$
                    jssb.addLine("REST_REQUEST.setHeader('Accept','" //$NON-NLS-1$
                            + request.getAccept() + "');"); //$NON-NLS-1$
                }

                RestParamContainerTreeItem headerItems =
                        factory.createParamContainerTreeItem(activity,
                                ParameterStyle.HEADER,
                                MappingDirection.IN);
                for (RestMapperTreeItem item : headerItems.getChildren()) {
                    if (item instanceof RestParamTreeItem) {
                        RestParamTreeItem param = (RestParamTreeItem) item;
                        jssb.append("REST_REQUEST.setHeader('"); //$NON-NLS-1$
                        jssb.append(param.getParam().getName());
                        jssb.append("',"); //$NON-NLS-1$
                        jssb.append(getPath(param));
                        jssb.addLine(");"); //$NON-NLS-1$
                    }
                }
                jssb.addLine("REST_REQUEST.setUrl(REST_REQUEST_URI);"); //$NON-NLS-1$
                jssb.addLine("REST_REQUEST.setMethod('" + httpMethod.getName() //$NON-NLS-1$
                        + "');"); //$NON-NLS-1$
                jssb.addLine("if (REST_PAYLOAD) {", true, false); //$NON-NLS-1$
                StringBuilder payload = new StringBuilder();
                payload.append("REST_REQUEST.setData("); //$NON-NLS-1$

                if (PayloadType.JSON.equals(payloadType)) {
                    payload.append("JSON.stringify("); //$NON-NLS-1$
                }

                payload.append(RestMappingPrefix.PAYLOAD.getPrefix());

                if (PayloadType.JSON.equals(payloadType)) {
                    payload.append(")"); //$NON-NLS-1$
                }
                payload.append(");"); //$NON-NLS-1$
                jssb.addLine(payload.toString());
                jssb.addLine("}", false, true); //$NON-NLS-1$
                return jssb.toString();
            }
        }

        return null;
    }

    /**
     * @param activity
     *            The activity to check.
     * @param isSource
     *            true if this is a
     * @return <code>true</code> if Payload is the special "Unprocessed Text" type, <code>false</code> if it is not,
     *         <code>null</code> if the type is unset.
     */
    private PayloadType getPayloadType(Activity activity, boolean isSource) {
        PayloadType payloadType = PayloadType.UNASSIGNED;
        RestServiceTaskAdapter rsta = new RestServiceTaskAdapter();

        /*
         * Sid ACE-6123 When called for the PayloadType for a Catch Fault event then we need to look at the FAULT for
         * the payload type instead of the REST service normal response type.
         */
        if (rsta.isCatchEvent(activity)) {
            // May be a Catch for a REST fault.
            Activity thrower = rsta.getThrowerActivity(activity);
            if (thrower != null) {
                ResultError resultError = EventObjectUtil.getResultError(activity);
                
                if (resultError != null && resultError.getErrorCode() != null && !resultError.getErrorCode().isEmpty()) {
                    
                    Fault rsoFault = rsta.getRSOFault(thrower, resultError.getErrorCode());
                    
                    if (rsoFault != null) {
                        payloadType = getPayloadType(rsoFault);
                    }
                }
            }

        } else {
            Method method = rsta.getRSOMethod(activity);
            if (method != null) {
                payloadType = getPayloadType(method, isSource);
            }
        }
        return payloadType;
    }

    /**
     * @param method
     *            The method to check.
     * @param isSource
     *            true if this is a
     * @return <code>true</code> if mayload is the special "Unprocessed Text"
     *         type, <code>false</code> if it is not, <code>null</code> if the
     *         type is unset.
     */
    private PayloadType getPayloadType(Method method, boolean isSource) {
        PayloadRefContainer prc = null;

        if (isSource) {
            prc = method.getResponse();
        } else {
            prc = method.getRequest();
        }

        return getPayloadType(prc);
    }

    /**
     * @param 
     * @return the {@link PayloadType} referred to by the given payload ref container.
     */
    public PayloadType getPayloadType(PayloadRefContainer payloadRefContainer) {
        PayloadType payloadType = PayloadType.UNASSIGNED;
        if (payloadRefContainer != null) {
            PayloadReference pr = payloadRefContainer.getPayloadReference();
            if (pr != null) {
                if (JsonSchemaUtil.UNPROCESSED_TEXT_PAYLOAD_REFERENCE
                        .equals(pr.getRef())) {
                    payloadType = PayloadType.UNPROCESSED_TEXT;

                } else {
                    payloadType = PayloadType.JSON;
                }
            }
        }
        return payloadType;
    }

    /**
     * Append static functions to the given builder to assist with generating
     * the query string for the URI.
     * 
     * @param jssb
     *            The builder to add the functions to.
     */
    public void appendQueryUriFunctions(JavaScriptStringBuilder jssb) {
        jssb.addLine("function __filterQueryParams(__all_params){",
                true,
                false);
        jssb.addLine("return __all_params.filter(function(__filter_param){",
                true,
                false);
        jssb.addLine(
                "return __filter_param.mandatory||eval(__filter_param.path)!=null;");
        jssb.addLine("});", false, true);
        jssb.addLine("}", false, true);
        jssb.addLine("function __encodeQueryParams(__all_params){",
                true,
                false);
        jssb.addLine(
                "var __filtered_params = __filterQueryParams(__all_params);");
        jssb.addLine(
                "var __mapped_params = __filtered_params.map(function(__filtered_param){",
                true,
                false);
        jssb.addLine(
                "return __filtered_param.name+'='+encodeURIComponent(eval(__filtered_param.path));");
        jssb.addLine("});", false, true);
        jssb.addLine(
                "return __filtered_params.length>0?'?'+__mapped_params.join('&'):'';");
        jssb.addLine("}", false, true);
    }

    /**
     * Builds the script to create the full relative URL for the REST service.
     * 
     * @param jssb
     *            The string builder to append to.
     * @param factory
     *            The REST mapper item factory used to obtain parameters from
     *            the activity.
     * @param activity
     *            The activity invoking the service.
     * @param method
     *            The REST method for the invocation.
     */
    public void buildUri(JavaScriptStringBuilder jssb,
            RestMapperTreeItemFactory factory, Activity activity,
            Method method) {
        Resource resource = (Resource) method.eContainer();
        Service service = (Service) resource.eContainer();
        String contextPath = service.getContextPath();
        StringBuilder relativePath = new StringBuilder();

        /**
         * Context path
         */
        if (contextPath != null && contextPath.length() > 0) {
            /*
             * Sid XPD-7755. Any static part of the path must be URI encoded -
             * i.e. everything except URL separators etc is encoded (see JIRA
             * for detail).
             */
            relativePath.append("encodeURI('"); //$NON-NLS-1$
            relativePath.append(contextPath);
            relativePath.append("')"); //$NON-NLS-1$
        }

        /**
         * Resource path
         */
        String path = resource.getPathTemplate();
        if (path != null && path.length() > 0) {
            if (relativePath.length() > 0) {
                relativePath.append(" + "); //$NON-NLS-1$
                if (!contextPath.endsWith("/") && !path.startsWith("/")) { //$NON-NLS-1$ //$NON-NLS-2$
                    relativePath.append("'/' + "); //$NON-NLS-1$
                }
            }

            /*
             * Sid XPD-7755. Any static part of the path must be URI encoded -
             * i.e. everything except URL separators etc is encoded (see JIRA
             * for detail).
             */
            relativePath.append("encodeURI('"); //$NON-NLS-1$
            RestParamContainerTreeItem pathItems =
                    factory.createParamContainerTreeItem(activity,
                            ParameterStyle.PATH,
                            MappingDirection.IN);

            if (pathItems != null) {
                for (RestMapperTreeItem item : pathItems.getChildren()) {
                    if (item instanceof RestParamTreeItem) {
                        RestParamTreeItem param = (RestParamTreeItem) item;
                        String find = "{" + param.getParam().getName() + "}"; //$NON-NLS-1$//$NON-NLS-2$

                        /*
                         * Sid XPD-7773. Any path parameter value inserted must
                         * be URI-component-encoded - i.e. including url
                         * separator encoding (see JIRA for detail).
                         */
                        String replace = "') + encodeURIComponent(" //$NON-NLS-1$
                                + getPath(param) + ") + encodeURI('"; //$NON-NLS-1$
                        path = path.replace(find, replace);
                    }
                }
            }
            relativePath.append(path);
            relativePath.append("')"); //$NON-NLS-1$
        }

        String pathScript = relativePath.toString();
        pathScript = pathScript.replace(" + encodeURI('')", ""); //$NON-NLS-1$//$NON-NLS-2$

        if (pathScript.length() == 0) {
            jssb.append("''"); //$NON-NLS-1$
        } else {
            /*
             * Sid XPD-7755. Any static part of the path that must be URI
             * encoded has already been done individually above. So removed
             * wrapping of entire path with encodeURI() here.
             */
            jssb.append(pathScript);
        }

        /**
         * Query parameters
         */
        RestParamContainerTreeItem queryItems =
                factory.createParamContainerTreeItem(activity,
                        ParameterStyle.QUERY,
                        MappingDirection.IN);

        if (queryItems != null) {
            List<RestMapperTreeItem> queryItemList = queryItems.getChildren();
            if (queryItemList.size() > 0) {
                // XPD-8300 (Nick) We need to exclude optional query parameters
                // with null values. As we don't know
                // at design time which parameters to include we need to embed
                // an inline function which will build
                // a list of relevant parameters then construct the query
                // string.
                // The following function is passed a list of all of the query
                // parameters. It first filters out
                // any parameters that are optional and have a runtime value of
                // null. It then maps remaining
                // parameters to strings that will go in the URI as query
                // statements. Finally it joins the query
                // statements together separated by '&' and appends them to the
                // URI.
                jssb.append("+__encodeQueryParams([");
                boolean first = true;
                for (RestMapperTreeItem item : queryItemList) {
                    if (item instanceof RestParamTreeItem) {
                        if (first) {
                            first = false;
                        } else {
                            jssb.append(",");
                        }
                        RestParamTreeItem param = (RestParamTreeItem) item;
                        boolean isMandatory = param.getParam().isMandatory();
                        jssb.append("{name:'");
                        jssb.append(param.getParam().getName());
                        jssb.append("',path:'");
                        jssb.append(getPath(param));
                        jssb.append("',mandatory:"); //$NON-NLS-1$
                        jssb.append(Boolean.toString(isMandatory));
                        jssb.append("}"); //$NON-NLS-1$
                    }
                }
                jssb.append("])");
            }
        }
    }

    /**
     * @see com.tibco.xpd.datamapper.api.IScriptGeneratorInfoProvider#getCollectionSizeScript(java.lang.Object,
     *      java.lang.String)
     * 
     * @param object
     *            The collection object.
     * @param objectParentJsVar
     *            JS variable containing the parent, or null.
     * @return Script to get the collection size.
     */
    @Override
    public String getCollectionSizeScript(Object object,
            String objectParentJsVar) {
        StringBuilder script = new StringBuilder();
        if (objectParentJsVar != null && !objectParentJsVar.isEmpty()) {
            script.append(convertPath(objectParentJsVar) + "['" //$NON-NLS-1$
                    + getName(object) + "']"); //$NON-NLS-1$
        } else {
            script.append(convertPath(getPath(object)));

        }
        script.append(".length"); //$NON-NLS-1$
        return script.toString();
    }

    /**
     * @see com.tibco.xpd.datamapper.api.IScriptGeneratorInfoProvider#getCollectionElementScript(java.lang.Object,
     *      java.lang.String, java.lang.String)
     * 
     * @param collection
     *            The collection object.
     * @param indexVarName
     *            The index of the element to get.
     * @param objectParentJsVar
     *            JS variable containing the parent, or null.
     * @return Script to get the given element.
     */
    @Override
    public String getCollectionElementScript(Object collection,
            String indexVarName, String objectParentJsVar) {

        StringBuilder script = new StringBuilder();
        if (objectParentJsVar != null && !objectParentJsVar.isEmpty()) {
            script.append(convertPath(objectParentJsVar) + "['" //$NON-NLS-1$
                    + getName(collection) + "']"); //$NON-NLS-1$
        } else {
            script.append(convertPath(getPath(collection)));

        }
        script.append("["); //$NON-NLS-1$
        script.append(indexVarName);
        script.append("]"); //$NON-NLS-1$
        return script.toString();
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
        /*
         * Sid XPD-7712 - for JSON content, there's nothing special to do for
         * getting array element from source array or a target array (for source
         * array the act of assigning an element from there to a target does not
         * remove it from array, (where as process data arrays they can do as
         * they're implemneted as EMF lists).
         */
        return getCollectionElementScript(collection,
                indexVarName,
                objectParentJsVar);
    }

    /**
     * @see com.tibco.xpd.datamapper.api.IScriptGeneratorInfoProvider#getClearCollectionScript(java.lang.Object,
     *      java.lang.String)
     * 
     * @param collectionObject
     *            The collection object.
     * @param jsVarAlias
     *            JS variable containing the parent, or null.
     * @return Script to clear the collection.
     */
    @Override
    public String getClearCollectionScript(Object collectionObject,
            String jsVarAlias) {
        StringBuilder script = new StringBuilder();
        if (jsVarAlias != null && !jsVarAlias.isEmpty()) {
            script.append(convertPath(jsVarAlias) + "['" //$NON-NLS-1$
                    + getName(collectionObject) + "']"); //$NON-NLS-1$
        } else {
            script.append(convertPath(getPath(collectionObject)));

        }
        script.append(" = []"); //$NON-NLS-1$
        return script.toString();
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
     *            Sid ACE-6583 Handle exclusion of empty objects from target arrays if required.
     * @return
     */
    @Override
    public String getCollectionAddElementScript(Object collection,
            String jsElementToAdd, String objectParentJsVar, boolean excludeEmptyObjects) {

        StringBuilder script = new StringBuilder();

        /* Sid ACE-6583 Handle exclusion of empty objects from target arrays if required. */
        String targetPath; 
                
        if (objectParentJsVar != null && !objectParentJsVar.isEmpty()) {
            targetPath = convertPath(objectParentJsVar) + "['" //$NON-NLS-1$
                    + getName(collection) + "']"; //$NON-NLS-1$
        } else {
            targetPath = convertPath(getPath(collection));
        }
        
        if (excludeEmptyObjects) {
            // Add the 'if (!(<is empty object>) {' statement
            script.append(String.format("if (!(%1$s)) { ", getIsEmptyObjectConditionStatement(jsElementToAdd)));
        }
        
        script.append(targetPath);
        
        script.append(".push("); //$NON-NLS-1$

        appendRhsObjectStatement(collection, jsElementToAdd, script);
        script.append(");"); //$NON-NLS-1$
        
        if (excludeEmptyObjects) {
            // Terminate the 'if (!(<is empty object>) {' statement
            script.append(" } ");
        }
        
        return script.toString();
    }


    /**
     * @see com.tibco.xpd.datamapper.api.IScriptGeneratorInfoProvider#getCollectionSetElementScript(java.lang.Object,
     *      java.lang.String, java.lang.String, java.lang.String)
     * 
     * @param collection
     * @param jsElementToAdd
     * @param objectParentJsVar
     * @param loopIndexJsVar
     * @return
     */
    @Override
    public String getCollectionSetElementScript(Object collection,
            String jsElementToAdd, String objectParentJsVar,
            String loopIndexJsVar) {
        StringBuilder script = new StringBuilder();
        if (objectParentJsVar != null && !objectParentJsVar.isEmpty()) {
            script.append(convertPath(objectParentJsVar) + "['" //$NON-NLS-1$
                    + getName(collection) + "']"); //$NON-NLS-1$
        } else {
            script.append(convertPath(getPath(collection)));
        }

        /*
         * "Item['arrayProp'][1] = jsElementToAdd;"
         */
        script.append("["); //$NON-NLS-1$
        script.append(loopIndexJsVar);
        script.append("] = "); //$NON-NLS-1$
        appendRhsObjectStatement(collection, jsElementToAdd, script);
        script.append(";"); //$NON-NLS-1$

        return script.toString();
    }

    /**
     * @see com.tibco.xpd.datamapper.api.IScriptGeneratorInfoProvider#getComplexObjectCreationScript(java.lang.Object)
     * 
     * @param complexObject
     *            The complex object to create.
     * @return Script to create the object.
     */
    @Override
    public String getComplexObjectCreationScript(Object complexObject) {
        if (complexObject instanceof ConceptPath) {
            /*
             * Sid XPD-7660 - This method is ONLY ever called to create single
             * instance objects. Creation of arrays is handled via
             * getArrayCreationScript()
             */
            return "{}"; //$NON-NLS-1$
        }

        return null;
    }

    /**
     * @see com.tibco.xpd.datamapper.api.IScriptGeneratorInfoProvider#getArrayCreationScript(java.lang.Object)
     * 
     * @param arrayObject
     *            The complex object to create.
     * @return Script to create the object.
     */
    @Override
    public String getArrayCreationScript(Object arrayObject) {
        if (!isSimpleType(arrayObject)) {
            /*
             * Sid XPD-7660 - we are now explicitly asked for array creation
             * script.
             */
            return "[]"; //$NON-NLS-1$
        }

        return null;
    }

    /**
     * @param object
     *            The object to check.
     * @return true if it is a simple type.
     */
    private boolean isSimpleType(Object object) {
        boolean isSimple = true;
        if (object instanceof ConceptPath) {
            ConceptPath cp = (ConceptPath) object;
            if (cp.getItem() != null) {
                /*
                 * Any process data of primitive type should be counted as
                 * simple content
                 */
                BasicType basicType = BasicTypeConverterFactory.INSTANCE
                        .getBasicType(cp.getItem());
                if (basicType == null) {
                    isSimple = false;
                }
            }
        }
        return isSimple;
    }

    /**
     * Gets the Javascript variable name, without any preceding path, for a
     * given object.
     * 
     * @param object
     *            The object to get the variable name for.
     * @return The Javascript variable name for the object.
     */
    private String getName(Object object) {
        String name = null;
        if (object instanceof ConceptPath) {

            ConceptPath cp = (ConceptPath) object;
            name = cp.getName();
        } else if (object instanceof RestParamTreeItem) {
            RestParamTreeItem item = (RestParamTreeItem) object;
            Parameter param = item.getParam();
            name = param.getName();
            if (ParameterStyle.HEADER.equals(param.getStyle())) {
                name = name.replace('-', '_');
            }
        }
        return name;
    }

    /**
     * @param object
     *            The object to get the path for.
     * @return The full Javascript path for the object.
     */
    private String getPath(Object object) {
        String path = null;
        if (object instanceof RestConceptPath) {
            path = ((RestConceptPath) object).getPath();
        } else if (object instanceof ConceptPath) {
            ConceptPath cp = (ConceptPath) object;
            ConceptPath parent = cp.getParent();
            if (parent != null) {
                path = getPath(parent) + "['" + cp.getName() + "']"; //$NON-NLS-1$ //$NON-NLS-2$
            } else {
                path = cp.getName();
            }
        } else if (object instanceof RestParamTreeItem) {
            RestParamTreeItem item = (RestParamTreeItem) object;
            path = item.getPath();
            Parameter param = item.getParam();

            /**
             * Sid ACE-6071 (port of XPD-8546) JS script variable names for query params also need
             * to have special characters removed as well as header parameters.
             * 
             * Also, just replacing hyphen with underscore is a little weak, so
             * we will do that (as we always used to for header params) AND then
             * remove anything that is not alphanumeric or underscore - that
             * should cleanse everything else that will be an issue for JS
             * script variables.
             */
            if (ParameterStyle.HEADER.equals(param.getStyle()) || ParameterStyle.QUERY.equals(param.getStyle())) {
                path = path.replace('-', '_');
                
                path = NameUtil.getInternalName(path, true);
            }

        } else if (object instanceof UnprocessedTextRestMapperTreeItem) {
            path = RestMappingPrefix.PAYLOAD.getPrefix();
        }
        return path;
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
         * Sid XPD-7975: Note that we ignore checkType because it makes no
         * difference to JSON whether we are null-checking an array for loop
         * iteration OR doing null-checking for single-instance source of a
         * mapping.
         * 
         * In both cases for JSON, the source may be null OR undefined.
         * Attempting to do MyField = JSONObj.property where property is
         * undefined (not present at all) causes runtiem exception.
         */

        String pathToCheck = null;

        if (jsVarAlias != null && jsVarAlias.length() > 0) {
            pathToCheck = jsVarAlias;
        } else if (object instanceof ConceptPath) {
            pathToCheck = ((ConceptPath) object).getPath();
        }

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

            for (int i = 0; i < parts.length; i++) {
                String part = parts[i];
                /*
                 * The only reason for having source alias is for temp var
                 * within source tree (which we will have copied from a source
                 * list, so no need to check the root of the tree in this case).
                 */
                if (jsVarAlias != null && jsVarAlias.length() > 0 && i == 0) {
                    /* skip alias variable itself. */
                    pathTilNow = part;
                    continue;
                }

                if (sb.length() > 0) {
                    /* Not the first condition we've added so AND it. */
                    sb.append(" &&\n   "); //$NON-NLS-1$
                }

                if (i == 0) {
                    /*
                     * First part of path is only ever the REST variable itself
                     * so don't have to surround to create "['@name']"
                     */
                    pathTilNow = part;

                } else {
                    /*
                     * Surround properties for "['propertyname'] to allow for
                     * special chars in identifier names.
                     */
                    pathTilNow = pathTilNow + "['" + part + "']"; //$NON-NLS-1$ //$NON-NLS-2$
                }

                /*
                 * Sid XPD-7975. Cannot simply say
                 * "if ( REST_PAYLOAD['top']['child'] )"
                 * 
                 * This is because the if statement in this case IS NOT 'does
                 * property exist' it is actually 'is property true or false'.
                 * 
                 * In JavaScript integer type witha value of zero is FALSE, and
                 * so we would process the else clause instead and the target
                 * would be set to null instead of zero
                 * 
                 * So we have to explicitly check if the source is NOT undefined
                 * and is NOT null (in case it's a parent object that exists but
                 * is null. So we have to do this...
                 * 
                 * "if ( (typeof(REST_PAYLOAD['top']['child']) != "undefined
                 * " && REST_PAYLOAD['top']['child'] != null) )"
                 */
                String checkExistsExpr = String.format(
                        "(typeof(%1$s) != \"undefined\" && %2$s != null)", //$NON-NLS-1$
                        pathTilNow,
                        pathTilNow);

                sb.append(checkExistsExpr);
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
        return convertPath(path);
    }

    /**
     * Little enum to distinguish payload type.
     * 
     * @author aallway
     * @since 25 Aug 2015
     */
    private enum PayloadType {
        UNASSIGNED, JSON, UNPROCESSED_TEXT
    }

    /**
     * Sid ACE-6583
     * 
     * @see com.tibco.xpd.datamapper.api.IScriptGeneratorInfoProvider#getDeleteEmptyObjectScript(java.lang.Object, java.lang.String)
     *
     * @param object
     * @param jsVarAlias the 
     * @return
     */
    @Override
    public String getDeleteEmptyObjectScript(Object object, String jsVarAlias) {
        StringBuilder script = new StringBuilder();
        
        String targetPath = resolvePath(jsVarAlias, jsVarAlias);
        
        // Add the 'if (!(<is empty object>) {' statement
        script.append(String.format("if (%1$s) { delete %2$s; }\n", getIsEmptyObjectConditionStatement(targetPath), targetPath));

        return script.toString();
    }

    /**
     * Sid ACE-6583
     * 
     * @see com.tibco.xpd.datamapper.api.IScriptGeneratorInfoProvider#getDeleteEmptyArrayScript(java.lang.Object, java.lang.String)
     *
     * @param object
     * @param jsVarAlias
     * @return
     */
    @Override
    public String getDeleteEmptyArrayScript(Object object, String jsVarAlias) {
        StringBuilder script = new StringBuilder();
        
        String targetPath = resolvePath(jsVarAlias, jsVarAlias);
        
        // Add the 'if (!(<is empty object>) {' statement
        script.append(String.format("if (%1$s) { delete %2$s; }\n", getIsEmptyArrayConditionStatement(targetPath), targetPath));

        return script.toString();
    }


    /**
     * Sid ACE-6583 Get the javascript statement to check if the given object is empty.
     * 
     * @param path Path of object
     * 
     * @return the javascript statement to check if the given object is empty.
     */
    private Object getIsEmptyObjectConditionStatement(String path) {
        return String.format("!!%1$s && Object.getPrototypeOf(%1$s) === Object.prototype && Object.keys(%1$s).length === 0",
                path);
    }

    /**
     * Sid ACE-6583 Get the javascript statement to check if the given array is empty.
     * 
     * @param path Path of array
     * 
     * @return the javascript statement to check if the given array is empty.
     */
    private Object getIsEmptyArrayConditionStatement(String path) {
        return String.format("Array.isArray(%1$s) && %1$s.length === 0",
                path);
    }

}
    