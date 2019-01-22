/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.rsd.ui.components;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;

import com.tibco.xpd.rsd.Parameter;
import com.tibco.xpd.rsd.ParameterStyle;
import com.tibco.xpd.rsd.Resource;
import com.tibco.xpd.rsd.RsdFactory;
import com.tibco.xpd.rsd.ui.internal.Messages;

/**
 * Handles request to add new parameters extracted from the path to the list of
 * the resource path parameters.
 * 
 * @author jarciuch
 * @since 19 Mar 2015
 */
public class AddParamsFromPathHandler {

    final private Resource resource;

    final private String path;

    final private TransactionalEditingDomain ed;

    /**
     * Creates a request handler for a resource, a path and an editing domain.
     */
    public AddParamsFromPathHandler(Resource resource, final String path,
            TransactionalEditingDomain ed) {
        this.resource = resource;
        this.path = path;
        this.ed = ed;

    }

    /**
     * Executes this handler.
     */
    public IStatus execute() {
        addParamsFromPath(resource, path, ed);
        return Status.OK_STATUS;
    }

    /**
     * Checks if there are any new parameters to add to the resource parameters
     * from path.
     * 
     * @return 'true' if there are new valid parameters in the path that can be
     *         added to the resource.
     */
    public boolean hasNewParams() {
        final List<String> paramNames = extractParamsFromPath(path);
        for (String paramName : paramNames) {
            if (isValidParamName(paramName)
                    && findParamByName(resource, paramName) == null) {
                return true;
            }
        }
        return false;
    }

    /**
     * Adds new parameters extracted from the path to the list of the resource
     * path parameters.
     * 
     * @param resource
     *            the context resource.
     * @param path
     *            the path to extract new parameters from.
     * @param ed
     *            the EMF transactional editing domain.
     */
    private void addParamsFromPath(final Resource resource, final String path,
            TransactionalEditingDomain ed) {
        final List<String> paramNames = extractParamsFromPath(path);
        RecordingCommand cmd =
                new RecordingCommand(ed,
                        Messages.AddParamsFromPathHandler_AddNewParamsCmd_label) {

                    @Override
                    protected void doExecute() {
                        for (String paramName : paramNames) {
                            if (isValidParamName(paramName)
                                    && findParamByName(resource, paramName) == null) {
                                Parameter newParam =
                                        RsdFactory.eINSTANCE.createParameter();
                                newParam.setName(paramName);
                                newParam.setStyle(ParameterStyle.PATH);
                                newParam.setMandatory(true);
                                resource.getParameters().add(newParam);
                            }
                        }

                    }
                };
        ed.getCommandStack().execute(cmd);
    }

    /**
     * Finds parameter by name.
     * 
     * @param resource
     *            the context resource for parameter search.
     * @param paramName
     *            the name of the parameter to find.
     * @return the first parameter found or <code>null</code> if not found.
     */
    private Parameter findParamByName(Resource resource, String paramName) {
        for (Parameter p : resource.getParameters()) {
            if (paramName.equals(p.getName())) {
                return p;
            }
        }
        return null;
    }

    /**
     * Pattern describing a valid parameter name.
     */
    private static Pattern VALID_PARAM_NAME = Pattern
            .compile("[a-z,A-Z][\\w-]*"); //$NON-NLS-1$

    /**
     * Checks if the name is a valid parameter name./
     * 
     * @param name
     *            the name of the parameter.
     * @return <code>true</code> if the name is valid and <code>false</code>
     *         otherwise.
     */
    private static boolean isValidParamName(String name) {
        return name != null && !name.trim().isEmpty()
                && VALID_PARAM_NAME.matcher(name).matches();
    }

    /**
     * Returns list of parameter names extracted from the path.
     * 
     * @param path
     *            the path to extract parameter names from.
     * @return the list of parameter names extracted from the path.
     */
    private static List<String> extractParamsFromPath(String path) {
        String delims = PathTemplateField.DELIMS;
        char startParamChar = PathTemplateField.START_PARAM_CHAR;
        char endParamChar = PathTemplateField.END_PARAM_CHAR;
        ArrayList<String> names = new ArrayList<String>();
        StringBuilder param = null;
        for (int i = 0, len = path.length(); i < len; i++) {
            char c = path.charAt(i);
            if (delims.indexOf(c) != -1) { // one of delims
                if (c == startParamChar) {
                    param = new StringBuilder();
                } else if (c == endParamChar) {
                    if (param != null) {
                        names.add(param.toString());
                        param = null;
                    }
                } else {
                    param = null;
                }
            } else if (param != null) {
                param.append(c);
            }
        }
        return names;
    }

}