/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.n2.pe.datamapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.tibco.xpd.datamapper.staticcontent.AbstractStaticDataMapperContentProvider;
import com.tibco.xpd.datamapper.staticcontent.StaticContentDataMapperElement;
import com.tibco.xpd.datamapper.staticcontent.VirtualDataMapperFolder;
import com.tibco.xpd.n2.pe.PEActivator;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.xpdl2.BasicTypeType;

/**
 * Content provider for the data mapper content contributor(
 * {@link PEDataMapperContentContributor}) that represents the "Process"
 * JavaScript class that is available in process manager scripts.
 * 
 */
class PEDataMapperContentProvider extends
        AbstractStaticDataMapperContentProvider {

    /**
     * Process class content is completely static so we load on demand and keep
     * it.
     */
    private static VirtualDataMapperFolder processFolder = null;

    /**
     * 
     */
    public PEDataMapperContentProvider() {
        createContentCache();
    }

    /**
     * Create the content cache (if not already created) Process class content
     * is static so we load on demand and keep it.
     * 
     * @see com.tibco.xpd.datamapper.staticcontent.AbstractStaticDataMapperContentProvider#createContentCache()
     * 
     * @return
     */
    @Override
    protected ConceptPath[] createContentCache() {
        if (processFolder == null) {
            processFolder =
                    new VirtualDataMapperFolder("WMDataMapper.WorkItemFolder", //$NON-NLS-1$
                            "Process", PEActivator.getDefault() //$NON-NLS-1$
                            , PEActivator.BUSINESS_PROCESS_ICON);

            List<StaticContentDataMapperElement> children =
                    new ArrayList<StaticContentDataMapperElement>();

            children.add(new StaticContentDataMapperElement("description", //$NON-NLS-1$
                    "Process_description$", "Process.getDescription()", //$NON-NLS-1$ //$NON-NLS-2$
                    BasicTypeType.STRING_LITERAL, true, processFolder));
            children.add(new StaticContentDataMapperElement("id", //$NON-NLS-1$
                    "Process_id$", "Process.getId()", //$NON-NLS-1$ //$NON-NLS-2$
                    BasicTypeType.STRING_LITERAL, true, processFolder));
            children.add(new StaticContentDataMapperElement("masterProcessId", //$NON-NLS-1$
                    "Process_masterProcessId$", "Process.getMasterProcessId()", //$NON-NLS-1$ //$NON-NLS-2$
                    BasicTypeType.STRING_LITERAL, true, processFolder));
            children.add(new StaticContentDataMapperElement("moduleName", //$NON-NLS-1$
                    "Process_moduleName$", "Process.getModuleName()", //$NON-NLS-1$ //$NON-NLS-2$
                    BasicTypeType.STRING_LITERAL, true, processFolder));
            children.add(new StaticContentDataMapperElement("name", //$NON-NLS-1$
                    "Process_Name$", "Process.getName()", //$NON-NLS-1$ //$NON-NLS-2$
                    BasicTypeType.STRING_LITERAL, true, processFolder));
            children.add(new StaticContentDataMapperElement("originator", //$NON-NLS-1$
                    "Process_originator$", "Process.getOriginator()", //$NON-NLS-1$ //$NON-NLS-2$
                    BasicTypeType.STRING_LITERAL, true, processFolder));
            children.add(new StaticContentDataMapperElement("originatorName", //$NON-NLS-1$
                    "Process_originatorName$", "Process.getOriginatorName()", //$NON-NLS-1$ //$NON-NLS-2$
                    BasicTypeType.STRING_LITERAL, true, processFolder));
            children.add(new StaticContentDataMapperElement("parentProcessId", //$NON-NLS-1$
                    "Process_parentProcessId$", "Process.getParentProcessId()", //$NON-NLS-1$ //$NON-NLS-2$
                    BasicTypeType.STRING_LITERAL, true, processFolder));
            children.add(new StaticContentDataMapperElement("priority", //$NON-NLS-1$
                    "Process_priority$", "Process.priority", //$NON-NLS-1$ //$NON-NLS-2$
                    BasicTypeType.INTEGER_LITERAL, false, processFolder));
            children.add(new StaticContentDataMapperElement("startTime", //$NON-NLS-1$
                    "Process_startTime$", "Process.getStartTime()", //$NON-NLS-1$ //$NON-NLS-2$
                    BasicTypeType.DATETIME_LITERAL, true, processFolder));

            Collections.sort(children);

            processFolder.getChildren().addAll(children);
        }

        return new ConceptPath[] { processFolder };
    }

}