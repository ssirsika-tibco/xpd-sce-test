/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.implementer.nativeservices.databaseservice.properties.table;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.tibco.xpd.implementer.nativeservices.databaseservice.database.DatabaseType;
import com.tibco.xpd.implementer.nativeservices.databaseservice.database.ParameterType;
import com.tibco.xpd.implementer.nativeservices.databaseservice.properties.utils.DatabaseUtil;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.TaskService;

/**
 * The <code>IStructuredContentProvider</code> for the stored procedure data
 * mapping
 * 
 * @author njpatel
 */
public class ContentProvider implements IStructuredContentProvider {

    public Object[] getElements(Object inputElement) {
        if (inputElement instanceof TaskService) {
            TaskService taskService = (TaskService) inputElement;
            DatabaseType database = DatabaseUtil.getDatabaseObj(taskService);

            if (database != null) {
                List<ParameterType> params = DatabaseUtil
                        .getParametersFromOperation(database);

                if (params != null) {
                    List<DataMapping> dataMappings = new ArrayList<DataMapping>();

                    // Add all data mappings from the MessageIn section
                    if (taskService.getMessageIn() != null) {
                        dataMappings.addAll(taskService.getMessageIn()
                                .getDataMappings());
                    }

                    // Add all data mappings from the MessageOut section
                    if (taskService.getMessageOut() != null) {
                        dataMappings.addAll(taskService.getMessageOut()
                                .getDataMappings());
                    }
                    
                    return dataMappings.toArray();

//                    List<DataMapping> mappingToReturn = new ArrayList<DataMapping>();
//
//                    for (ParameterType param : params) {
//
//                        DataMapping mapping = getDataMappingFor(dataMappings,
//                                param.getName());
//
//                        if (mapping != null) {
//                            mappingToReturn.add(mapping);
//                        }
//                    }
//                    return mappingToReturn.toArray();
                }
            }
        }

        return new Object[] {};
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.viewers.IContentProvider#dispose()
     */
    public void dispose() {
        // Nothing to dispose

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer,
     *      java.lang.Object, java.lang.Object)
     */
    public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
        // Do nothing here

    }

    private DataMapping getDataMappingFor(List<DataMapping> dataMappings,
            String paramName) {

        if (dataMappings != null && paramName != null) {
            for (DataMapping mapping : dataMappings) {
                if (mapping.getFormal().equals(paramName)) {
                    return mapping;
                }
            }
        }

        return null;
    }

}
