package com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.groups;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
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

import com.tibco.xpd.analyst.resources.xpdl2.processeditorconfiguration.ProcessEditorConfigurationUtil;
import com.tibco.xpd.analyst.resources.xpdl2.processeditorconfiguration.ProcessEditorElementType;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.ui.projectexplorer.decorator.XPDDecorationStatusProvider;
import com.tibco.xpd.ui.projectexplorer.decorator.XPDProblemDecorator;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

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
public abstract class AbstractAssetGroup implements INavigatorGroup,
        INotifyChangedListener, XPDDecorationStatusProvider {

    protected List<Object> children = null;

    protected EObject parent = null;

    protected ItemProviderAdapter adapter = null;

    private ILabelProvider labelProvider = null;

    private IResource res = null;

    private ProcessEditorElementType assetGroupElementType = null;

    /**
     * Sid XPD-2516: Added store of {@link ProcessEditorElementType} for
     * checking exclusion via processEditConfiguration/ElementTypeExclusion
     * extension point.
     * 
     * @param parent
     * @param processEditorElementType
     *            Element type to use for exclusion checking or
     *            <code>null</code> if no exclusion checking required.
     */
    protected AbstractAssetGroup(EObject parent,
            ProcessEditorElementType assetGroupElementType) {
        this.parent = parent;
        this.assetGroupElementType = assetGroupElementType;

        AdapterFactoryEditingDomain ed =
                (AdapterFactoryEditingDomain) getEditingDomain(parent);
        AdapterFactory af = ed.getAdapterFactory();
        adapter =
                (ItemProviderAdapter) af.adapt(parent,
                        IEditingDomainItemProvider.class);

        if (adapter != null) {
            adapter.addListener(this);
        }
    }

    /**
     * Sid XPD-2516: Construct asset group that does not check for exclusion via
     * processEditorConfiguration extension.
     * 
     * @param parent
     */
    protected AbstractAssetGroup(EObject parent) {
        this(parent, null);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.groups.INavigatorGroup
     * #getText()
     */
    @Override
    public abstract String getText();

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.groups.INavigatorGroup
     * #getImage()
     */
    @Override
    public abstract Image getImage();

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.groups.INavigatorGroup
     * #getParent()
     */
    @Override
    public Object getParent() {
        return parent;
    }

    /**
     * Get the EStructurealFeature of this group
     * 
     * @return
     */
    public abstract EStructuralFeature getFeature();

    /**
     * Get the reference type (EClass) of the contents of this group
     * 
     * @return
     */
    public abstract EClass getReferenceType();

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.groups.INavigatorGroup
     * #getChildren()
     */
    @Override
    @SuppressWarnings("unchecked")
    public List getChildren() {

        // If children list not created then create it
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

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.groups.INavigatorGroup
     * #hasChildren()
     */
    @Override
    public boolean hasChildren() {
        return getChildren().size() > 0;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.groups.INavigatorGroup
     * #dispose()
     */
    @Override
    public void dispose() {
        if (adapter != null) {
            adapter.removeListener(this);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.emf.edit.provider.INotifyChangedListener#notifyChanged(org
     * .eclipse.emf.common.notify.Notification)
     */
    @Override
    public void notifyChanged(Notification notification) {
        if (!notification.isTouch()) {
            children = null;
        }
    }

    public Object getAdapter(Class<?> adapter) {
        Object ad = null;

        if (adapter == ILabelProvider.class) {
            if (labelProvider == null) {
                labelProvider = new LabelProvider() {
                    @Override
                    public String getText(Object element) {
                        return AbstractAssetGroup.this.getText();
                    }

                    @Override
                    public Image getImage(Object element) {
                        return AbstractAssetGroup.this.getImage();
                    }
                };
            }

            ad = labelProvider;

        } else if (adapter == IResource.class) {
            if (res == null) {
                // Get the working copy of the parent
                WorkingCopy wc = WorkingCopyUtil.getWorkingCopyFor(parent);

                if (wc != null) {
                    // Get the resource
                    res = wc.getEclipseResources().get(0);
                }
            }

            ad = res;
        }

        return ad;
    }

    /**
     * Get Editingdomain for the EObject
     * 
     * @param eo
     * @return Editingdomain or null if not found
     */
    protected EditingDomain getEditingDomain(EObject eo) {
        if (eo == null || eo.eResource() == null) {
            return null;
        }
        IEditingDomainProvider ep =
                (IEditingDomainProvider) EcoreUtil.getExistingAdapter(eo
                        .eResource(), IEditingDomainProvider.class);
        return ep != null ? ep.getEditingDomain() : null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.ui.projectexplorer.decorator.XPDDecorationStatusProvider
     * #getDecorationMarker()
     */
    @Override
    public int getDecorationMarker() {
        WorkingCopy wc =
                WorkingCopyUtil.getWorkingCopyFor((EObject) getParent());
        if (wc != null) {
            IResource res = wc.getEclipseResources().get(0);
            try {
                int result = 0;
                int cr;
                for (Object obj : getChildren()) {
                    if (obj instanceof EObject) {
                        cr =
                                XPDProblemDecorator.getSeverity((EObject) obj,
                                        res);
                        if (cr == IMarker.SEVERITY_ERROR) {
                            return cr;
                        } else if (cr == IMarker.SEVERITY_WARNING) {
                            result = cr;
                        }
                    }
                }
                return result;
            } catch (CoreException e) {
                // ignore
            }
        }
        return 0;
    }

    /**
     * Ability to exclude asset group when the represented element type has been
     * excluded.
     * 
     * @return <code>true</code> if this asset group is excluded (via the
     *         processEditConfiguration/ElementTypeExclusion extension point).
     */
    public boolean isAssetGroupElementTypeExcluded() {
        if (assetGroupElementType != null) {
            /*
             * Get the nearest ancestor process/interface/package for checking
             * exclusion
             */
            EObject procOrIfcOrPkg = Xpdl2ModelUtil.getProcess(parent);

            if (procOrIfcOrPkg == null) {
                procOrIfcOrPkg =
                        Xpdl2ModelUtil.getAncestor(parent,
                                ProcessInterface.class);

                if (procOrIfcOrPkg == null) {
                    procOrIfcOrPkg =
                            Xpdl2ModelUtil.getAncestor(parent, Package.class);
                }
            }

            if (procOrIfcOrPkg != null) {
                /*
                 * Check extension point contributions for exclusion of this
                 * particular element type.
                 */
                Set<ProcessEditorElementType> excludedElementTypes =
                        ProcessEditorConfigurationUtil
                                .getExcludedElementTypes(procOrIfcOrPkg);

                if (excludedElementTypes.contains(assetGroupElementType)) {
                    return true;
                }
            }
        }

        return false;
    }

}
