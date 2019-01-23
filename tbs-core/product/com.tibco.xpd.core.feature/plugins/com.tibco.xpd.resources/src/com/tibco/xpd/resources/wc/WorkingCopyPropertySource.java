/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */

package com.tibco.xpd.resources.wc;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.IPropertySourceProvider;
import org.eclipse.ui.views.properties.PropertyDescriptor;

import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.internal.Messages;

/**
 * @author wzurek
 * 
 */
public class WorkingCopyPropertySource implements IAdapterFactory,
        IPropertySourceProvider {

    public class WCPropertySource implements IPropertySource,
            PropertyChangeListener {

        /** id of the 'loaded' property */
        private static final String LOADED = "loaded"; //$NON-NLS-1$

        /** id of the 'dirty' property */
        private static final String DIRTY = "dirty"; //$NON-NLS-1$

        /** id of the 'invalid' property */
        private static final String INVALID = "invalid"; //$NON-NLS-1$

        /** id of the 'dependencies' property */
        private static final String DEPENDENCIES = "dependencies"; //$NON-NLS-1$

        private final WorkingCopy wc;

        private PropertyDescriptor[] propertyDescriptors;

        private IPropertySource filePSource;

        public WCPropertySource(IFile file, WorkingCopy wc) {
            filePSource = (IPropertySource) file
                    .getAdapter(IPropertySource.class);
            this.wc = wc;
            wc.addListener(this);
        }

        public Object getEditableValue() {
            return wc;
        }

        public IPropertyDescriptor[] getPropertyDescriptors() {
            if (propertyDescriptors == null) {
                propertyDescriptors = new PropertyDescriptor[] {
                        new PropertyDescriptor(LOADED, Messages.WorkingCopyPropertySource_isLoaded_shortdesc),
                        new PropertyDescriptor(DIRTY, Messages.WorkingCopyPropertySource_isDirty_shortdesc),
                        new PropertyDescriptor(DEPENDENCIES, Messages.WorkingCopyPropertySource_dependencies_shortdesc),
                        new PropertyDescriptor(INVALID, Messages.WorkingCopyPropertySource_isInvalid_shortdesc) };
                for (PropertyDescriptor des : propertyDescriptors) {
                    des.setCategory(Messages.WorkingCopyPropertySource_workingCopy_shortdesc);
                }
            }
            ArrayList<IPropertyDescriptor> props = new ArrayList<IPropertyDescriptor>();
            for (IPropertyDescriptor desc : propertyDescriptors) {
                props.add((IPropertyDescriptor) desc);
            }
            for (IPropertyDescriptor desc : filePSource
                    .getPropertyDescriptors()) {
                props.add((IPropertyDescriptor) desc);
            }
            return props.toArray(new IPropertyDescriptor[props.size()]);
        }

        public Object getPropertyValue(Object id) {
            if (LOADED.equals(id)) {
                //return String.valueOf(wc.isLoaded());               
                if (wc.isLoaded()==true){
                    return (Messages.WorkingCopyPropertySource_ValueTrue_desc);
                } else {
                    return (Messages.WorkingCopyPropertySource_ValueFalse_desc);
                }
  
            } else if (DIRTY.equals(id)) {
                //return String.valueOf(wc.isLoaded() && wc.isWorkingCopyDirty());                
                if (wc.isLoaded() && wc.isWorkingCopyDirty()){
                    return (Messages.WorkingCopyPropertySource_ValueTrue_desc);
                } else {
                    return (Messages.WorkingCopyPropertySource_ValueFalse_desc);
                }
      
            } else if (INVALID.equals(id)) {
                //return String.valueOf(wc.isInvalidFile());                
                if (wc.isInvalidFile()){
                    return (Messages.WorkingCopyPropertySource_ValueTrue_desc);
                } else {
                    return (Messages.WorkingCopyPropertySource_ValueFalse_desc);
                }
                
            } else if (DEPENDENCIES.equals(id)) {
                List<IResource> deps = wc.getDependency();
                StringBuffer buf = new StringBuffer();
                for (IResource res : deps) {
                    buf.append(res.getFullPath().toString());
                    buf.append("; "); //$NON-NLS-1$
                }
                return buf.toString();
            } else {
                return filePSource.getPropertyValue(id);
            }
        }

        public boolean isPropertySet(Object id) {
            if (LOADED.equals(id) || INVALID.equals(id)) {
                return true;
            } else if (DIRTY.equals(id)) {
                return wc.isLoaded();
            } else if (DEPENDENCIES.equals(id)) {
                return !wc.getDependency().isEmpty();
            }
            return false;
        }

        public void resetPropertyValue(Object id) {
        }

        public void setPropertyValue(Object id, Object value) {
        }

        /*
         * (non-Javadoc)
         * 
         * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
         */
        public void propertyChange(PropertyChangeEvent evt) {
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.core.runtime.IAdapterFactory#getAdapter(java.lang.Object,
     *      java.lang.Class)
     */
    public Object getAdapter(Object object, Class adapterType) {
        if (object instanceof IFile) {
            WorkingCopy wc = XpdResourcesPlugin.getDefault().getWorkingCopy(
                    (IResource) object);
            if (wc != null) {
                return this;
            }
        }
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.core.runtime.IAdapterFactory#getAdapterList()
     */
    public Class[] getAdapterList() {
        return new Class[] { IPropertySourceProvider.class };
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.views.properties.IPropertySourceProvider#getPropertySource(java.lang.Object)
     */
    public IPropertySource getPropertySource(Object object) {
        if (object instanceof IFile) {
            WorkingCopy wc = XpdResourcesPlugin.getDefault().getWorkingCopy(
                    (IResource) object);
            if (wc != null) {
                IPropertySource ps = (IPropertySource) wc.getAttributes().get("PROPERTY_SOURCE"); //$NON-NLS-1$
                if (ps==null) {
                    ps = new WCPropertySource((IFile) object, wc);
                    wc.getAttributes().put("PROPERTY_SOURCE", ps); //$NON-NLS-1$
                }
                return ps;
            }
        }
        return null;
    }

}
