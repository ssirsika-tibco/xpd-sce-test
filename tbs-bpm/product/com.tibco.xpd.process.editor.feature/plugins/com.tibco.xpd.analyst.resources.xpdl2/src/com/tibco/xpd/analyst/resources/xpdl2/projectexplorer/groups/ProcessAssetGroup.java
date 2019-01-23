package com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.groups;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.INotifyChangedListener;
import org.eclipse.emf.edit.provider.ItemProviderAdapter;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

/**
 * Base class for package artefact groups such as data fields, participants etc.
 * The subclasses should define the following interfaces:
 * <ul>
 * <li><code>getText()</code></li>
 * <li><code>getImage ()</code></li>
 * <li><code>getParent()</code></li>
 * <li><code>getFeature()</code></li>
 * </ul>
 * 
 * @author njpatel
 * 
 */
public abstract class ProcessAssetGroup implements INavigatorGroup, INotifyChangedListener, IAdaptable{

	protected List<Object> children = null;
	
	protected EObject parent = null;
	
	protected ItemProviderAdapter adapter = null;
    
    private ILabelProvider labelProvider = null;
    
	protected ProcessAssetGroup(EObject parent) {
		this.parent = parent;
		AdapterFactoryEditingDomain ed = (AdapterFactoryEditingDomain) getEditingDomain(parent);
        AdapterFactory af = ed.getAdapterFactory();
        adapter = (ItemProviderAdapter) af.adapt(parent,
                IEditingDomainItemProvider.class);
        
        if (adapter != null) {
        	adapter.addListener(this);
        }
	}

	/* (non-Javadoc)
	 * @see com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.groups.INavigatorGroup#getText()
	 */
	public abstract String getText();

	/* (non-Javadoc)
	 * @see com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.groups.INavigatorGroup#getImage()
	 */
	public abstract Image getImage();
	

	/* (non-Javadoc)
	 * @see com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.groups.INavigatorGroup#getParent()
	 */
	public Object getParent() {
		return parent;
	}
	
	/**
	 * Get the EStructurealFeature of this group
	 * @return
	 */
	public abstract EStructuralFeature getFeature();
	
	/**
	 * Get the reference type (EClass) of the contents of this group
	 * @return
	 */
	public abstract EClass getReferenceType();

	/* (non-Javadoc)
	 * @see com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.groups.INavigatorGroup#getChildren()
	 */
	@SuppressWarnings("unchecked")  
	public List getChildren() {
		
		//If children list not created then create it
		if (children == null) {
			children = new ArrayList<Object>();
		
			if (getParent() != null && getFeature() != null) {
				
				if (getParent() instanceof EObject) {
					EObject parent = (EObject) getParent();
					
					Object eList = parent.eGet(getFeature());
					children.addAll((Collection) eList);
				}
			}
		}
		
		return children;
	}
	
	

	/* (non-Javadoc)
	 * @see com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.groups.INavigatorGroup#hasChildren()
	 */
	public boolean hasChildren() {
		return getChildren().size() > 0;
	}

	/* (non-Javadoc)
	 * @see com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.groups.INavigatorGroup#dispose()
	 */
	public void dispose() {
		if (adapter != null) {
			adapter.removeListener(this);
		}
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.emf.edit.provider.INotifyChangedListener#notifyChanged(org.eclipse.emf.common.notify.Notification)
	 */
	public void notifyChanged(Notification notification) {
    	if (!notification.isTouch()) {
    		children = null;
    	}    	
    }
    
    public Object getAdapter(Class adapter) {
        
        if (adapter == ILabelProvider.class) {
            if (labelProvider == null) {
                labelProvider = new LabelProvider() {
                    @Override
                    public String getText(Object element) {
                        return ProcessAssetGroup.this.getText();
                    }
                    
                    @Override
                    public Image getImage(Object element) {
                        return ProcessAssetGroup.this.getImage();
                    }
                };
            }
            
            return labelProvider;
        }
        
        
        return null;
    }
	
	/**
     * Get Editingdomain for the EObject
     * 
     * @param eo
     * @return Editingdomain or null if not found
     */
    protected EditingDomain getEditingDomain(EObject eo) {
        if (eo == null) {
            return null;
        }
        IEditingDomainProvider ep = (IEditingDomainProvider) EcoreUtil
                .getExistingAdapter(eo.eResource(),
                        IEditingDomainProvider.class);
        return ep != null ? ep.getEditingDomain() : null;
    }
    
}
