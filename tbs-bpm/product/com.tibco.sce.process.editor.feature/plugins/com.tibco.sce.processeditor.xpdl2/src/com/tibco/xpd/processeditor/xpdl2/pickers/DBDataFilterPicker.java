/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.processeditor.xpdl2.pickers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.widgets.Shell;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesConsts;
import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesPlugin;
import com.tibco.xpd.analyst.resources.xpdl2.pickers.DataFilterPicker;
import com.tibco.xpd.analyst.resources.xpdl2.pickers.IDataPickerProxyItem;
import com.tibco.xpd.analyst.resources.xpdl2.providers.DataFilterPickerProviderHelper;
import com.tibco.xpd.processeditor.xpdl2.fields.DataFieldContributor;
import com.tibco.xpd.processeditor.xpdl2.fields.DataFieldTextProvider;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptUtil;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * 
 * @author Miguel Torres
 * 
 */
public class DBDataFilterPicker extends DataFilterPicker {

    public DBDataFilterPicker(Shell shell, DataPickerType type, boolean multi) {
        super(shell, type, multi);
    }

    public DBDataFilterPicker(Shell shell, EObject[] exclude,
            DataPickerType type, boolean multi) {
        super(shell, exclude, type, multi);
    }
    
    private Set<IDataPickerProxyItem> dataPickerItems = null;
    /**
     * @see com.tibco.xpd.analyst.resources.xpdl2.pickers.DataFilterPicker#getExtraItems()
     *
     * @return
     */
    @Override
    protected Set<IDataPickerProxyItem> getExtraItems() {
        if (dataPickerItems == null) {
            dataPickerItems = new HashSet<IDataPickerProxyItem>();
            Collection<ConceptPath> parameters = new ArrayList<ConceptPath>();
            Process process = getProcess();
            if (process != null) {
                parameters = ConceptUtil.getContributedFields(process, DataFieldContributor.CONTEXT_DATABASE_SERVICE_TASK);
                for (ConceptPath conceptPath : parameters) {
                    IDataPickerProxyItem item =
                            getConceptPathDataIndexerItem(conceptPath);
                    if (item != null) {
                        dataPickerItems.add(item);
                    }
                }
            }
        }
        return dataPickerItems;
    }
    
    /**
     * @see com.tibco.xpd.analyst.resources.xpdl2.pickers.DataFilterPicker#getExtraItem(java.lang.String)
     * 
     * @param name
     * @return
     */
    @Override
    protected IDataPickerProxyItem getExtraItem(String name) {
        Set<IDataPickerProxyItem> extraItems = getExtraItems();
        IDataPickerProxyItem extraItem = null;
        if (extraItems != null) {
            for (IDataPickerProxyItem dataPickerProxyItem : extraItems) {
                if (dataPickerProxyItem.getName().equals(name)) {
                    return dataPickerProxyItem;
                }
            }
        }
        return extraItem;
    }
    
    private Process getProcess() {
        Process process = null;
        if (getScope() != null) {
            process = Xpdl2ModelUtil.getProcess(getScope());
        }
        return process;
    }
    
    private IDataPickerProxyItem getConceptPathDataIndexerItem(ConceptPath conceptPath) {
        String name = getLabelForConceptPath(conceptPath);
        URI uri = null;
        URI imageURI = URI.createPlatformPluginURI(Xpdl2ResourcesPlugin.PLUGIN_ID
                + "/" //$NON-NLS-1$
                + Xpdl2ResourcesConsts.ICON_DATAFIELD, true);
        IDataPickerProxyItem item =
                DataFilterPickerProviderHelper.getInstance().new DataPickerItem(
                        name, uri, imageURI, conceptPath);
        return item;
    }
    
    private String getLabelForConceptPath(ConceptPath conceptPath) {
        String label = null;
        Object item = conceptPath.getItem();
        if (item instanceof DataFieldTextProvider) {
            label = ((DataFieldTextProvider) item).getName();
        } else {
            label = conceptPath.getName();
        }
        
        return label == null ? "" : label; //$NON-NLS-1$
    }

}
