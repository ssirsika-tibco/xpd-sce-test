/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.ui.properties;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.ItemProviderAdapter;
import org.eclipse.jface.viewers.IFilter;
import org.eclipse.ui.IPluginContribution;
import org.eclipse.ui.activities.WorkbenchActivityHelper;

import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;

/**
 * Abstract class for property sections for <b>EObjects</b>. This section is
 * intended to be used for <b>single selection</b> property tabs. It supports
 * refreshing on events from the model and delayed events from the widgets.
 * <p>
 * To take advantage of event handling and delayed events execution controls can
 * be 'managed' by calling the method <code>manageControl</code>. This will
 * register the change listeners of the controls with this class. When a change
 * is made to any of the managed controls the method <code>doGetCommand</code>
 * will be ultimately called for the command to update the model with the
 * change.
 * </p>
 * <p>
 * The following abstract methods will need to be implemented by subclasses:
 * <ul>
 * <li>
 * <code>doCreateControls</code> - create the controls for the section</li>
 * <li>
 * <code>doGetCommand</code> - called when the contents of managed controls are
 * changed</li>
 * <li>
 * <code>doRefresh</code> - called to update the section when the model changes.
 * Only registered objects (see paragraph about <code>getNotifierObjects</code>
 * below) will affect this section.</li>
 * </ul>
 * </p>
 * <p>
 * The method <code>getNotifierObjects</code> returns the object that owns the
 * properties on this sheet and to which we should listen for notifier events.
 * By default, it will return the input for this section. Subclasses can
 * override this method if they wish to listen to other objects.
 * </p>
 * <p>
 * If any subclass needs to maintain multiple inputs (for multi selection
 * properties) then <code>setInput</code> will have to be overridden.
 * </p>
 * <p>
 * <strong> This class has now been superseded by the
 * {@link AbstractTransactionalSection} to be used with the transactional
 * editing domain.</strong>
 * </p>
 * 
 * @author njpatel, Jan Arciuchiewicz
 */
public abstract class AbstractEObjectSection extends AbstractXpdSection
        implements IFilter, Adapter, ModelChangeListener {

    private EditingDomain editingDomain;

    private EObject input;

    private List<Adapter> adapters = new ArrayList<Adapter>();

    protected final EClass eClass;

    private boolean ignoreModelEvents = false;

    /**
     * Constructor. The parameter <b>eClass</b> will be used by the
     * <code>IFilter</code> to filter the input for this property section
     * (through the <code>Select</code> method).
     * 
     * @param eClass
     */
    public AbstractEObjectSection(EClass eClass) {
        this.eClass = eClass;
    }

    @Override
    public EditingDomain getEditingDomain() {
        if (editingDomain != null) {
            return editingDomain;
        }
        return getEditingDomain(input);
    }

    /**
     * Set editing domain to use with this section. If set to null (default) the
     * section will try extract the editing domain from the input
     * 
     * @param editingDomain
     */
    public void setEditingDomain(EditingDomain editingDomain) {
        this.editingDomain = editingDomain;
    }

    /**
     * Sets the input for this section. This section is intended for a <b>single
     * selection</b> property tab. Subclasses for multiple selection tabs should
     * override this method.
     */
    @Override
    public void setInput(Collection<?> items) {
        if (input != null) {
            unregister();
        }
        adapters = new ArrayList<Adapter>();
        if (items.size() == 1) {
            // Get the input object we are interested in from the input
            // selection
            input = getEObject(items.iterator().next());
            register();
        } else {
            input = null;
        }
    }

    /**
     * Get the <code>EObject</code> input of this section
     * 
     * @return input
     */
    protected EObject getInput() {
        return input;
    }

    /**
     * Get the list of adapters registered for this section
     * 
     * @return list of <code>Adapter</code> objects registered.
     */
    protected List<Adapter> getAdapters() {
        return adapters;
    }

    /**
     * Adapt the given obj to an <code>EObject</code> if possible. If obj is an
     * <code>EObject</code> then it will just be cast, otherwise it will be
     * adapted to an <code>EObject</code> if it has an adapter.
     * 
     * @param obj
     * @return <code>EObject</code> from the parameter passed in.
     */
    protected EObject getEObject(Object obj) {
        // Need to set the EObject as the input
        if (obj instanceof EObject) {
            return (EObject) obj;
        }
        if (obj instanceof IAdaptable) {
            return (EObject) ((IAdaptable) obj).getAdapter(EObject.class);
        }
        return null;
    }

    /**
     * Register the notifier objects that this section needs to listen to for
     * events.
     */
    protected void register() {
        EObject[] eos = getNotifierObjects();
        for (int i = 0; i < eos.length; i++) {
            EObject eo = eos[i];
            /*
             * If this section is contained in a wizard then don't check for
             * eo.eResource as it will be null. This is because the EObject
             * created in the wizard will be a new one and not yet added to the
             * model (this will happen when the wizard completes).
             */
            boolean doRegister = false;
            if (getSectionContainerType() == ContainerType.PROPERTYVIEW) {
                doRegister = (eo != null && eo.eResource() != null);
            } else {
                doRegister = (eo != null);
            }

            if (doRegister) {
                // Register the object
                ItemProviderAdapter ad = getItemProviderFor(eo);
                if (!adapters.contains(ad)) {
                    adapters.add(ad);
                    ad.addListener(this);
                }
            }
        }
    }

    /**
     * Unregister the registered notifier objects
     */
    protected void unregister() {
        if (input != null) {
            if (adapters != null) {
                for (Iterator<?> i = adapters.iterator(); i.hasNext();) {
                    ItemProviderAdapter adapter =
                            (ItemProviderAdapter) i.next();
                    adapter.removeListener(this);
                    adapter = null;
                }
                adapters.clear();
            }
        }
    }

    /**
     * Return the toTest object if it is of the EClass type that this section
     * was constructed with <i>and it is enabledInCapabilities()</i>.
     * <p>
     * This is especially useful for sub-filtering of selection (where the
     * property sheet is included only for a particular sub-type of the
     * selection object).
     * <p>
     * See {@link AbstractEObjectSection#select(Object)} for more details.
     * 
     * @param toTest
     * 
     * @return <b>toTest</b> object if it matches base EClass that section was
     *         constructed with <b>or null</b> if it does not match or is not
     *         enabled in capabilities.</b>
     */
    public EObject getBaseSelectObject(Object toTest) {
        if (enabledInCapabilities()) {
            EObject eo = null;
            if (toTest instanceof EObject) {
                eo = (EObject) toTest;
            } else if (toTest instanceof IAdaptable) {
                eo = (EObject) ((IAdaptable) toTest).getAdapter(EObject.class);
            }
            if (eo != null && eClass != null) {
                if (eClass.isSuperTypeOf(eo.eClass())) {
                    return eo;
                }
            }
        }
        return null;
    }

    /**
     * Derived classes should check this method as a first thing in the method
     * implementation, so the beginning of the overriding method could look like
     * this:
     * <p/>
     * 
     * <code>
     * <pre>
     * if (!super.select(toTest)) {
     *     return false;
     * }
     * </pre>
     * </code>
     * <p>
     * or add capability checking code like this:
     * </p>
     * <code>
     * <pre>
     * if (!enabledInCapabilities()) {
     *     return false;
     * }
     * </pre>
     * </code>
     * 
     * <p/>
     * <p>
     * IT IS ALSO IMPORTANT TO PROPERLY OVERWRITE
     * <code>getPluginContribution()</code> METHOD.
     * </p>
     * <p>
     * It is also possible to access the EObject for a given selection object
     * using <code>getBaseSelectObject()</code>. This will return the object as
     * the EObject type selected for EClass we were constructed with (either if
     * it is the particular class type or can adapt to it).
     * </p>
     * <p>
     * This makes it possible (and easy to perform further selection filtering
     * based on sub-elements of the base object. For example, let us say that
     * the actual selection object for a Task is Activity, but there are many
     * sub-types of Activity, so we must therefore perform further filtering.
     * </p>
     * <p>
     * We are constructed with the ECLass Activity. Therefore the subclass can
     * override the select() method as follows...
     * </p>
     * 
     * <pre>
     * public boolean select(Object toTest) {
     *     if (super.select(toTest)) {
     *         // We now know that toTest matches our base EClass - Activity.
     *         Activity act = (Activity)getBaseSelectObject);
     *         
     *         // Now we can test for the sub-type we are interested in...
     *         if (act.getTask() != null) {
     *              // Its a Task type Activity - we're interested.
     *              return true;
     *          }
     *      }
     *      return false;
     *  }
     * </pre>
     * 
     * @param toTest
     *            object input to test.
     * 
     * @see org.eclipse.jface.viewers.IFilter#select(java.lang.Object)
     * @see AbstractEObjectSection#getPluginContributon()
     */
    public boolean select(Object toTest) {
        if (getBaseSelectObject(toTest) != null) {
            return true;
        }
        return false;
    }

    /**
     * Check if the contribution provided by section should be enabled
     * concerning current set of capabilities.
     * 
     * @return true if section should be enabled, false otherwise.
     * 
     */
    protected boolean enabledInCapabilities() {
        IPluginContribution pluginContributon = getPluginContributon();
        if (pluginContributon != null
                && WorkbenchActivityHelper.filterItem(pluginContributon)) {
            return false;
        }
        return true;
    }

    /**
     * Returns the target from which the adapter receives notification. In
     * general, an adapter may be shared by more than one notifier.
     * 
     * @see org.eclipse.emf.common.notify.Adapter#getTarget()
     */
    public Notifier getTarget() {
        // Do nothing
        return null;
    }

    /**
     * Returns whether the adapter is of the given type. In general, an adapter
     * may be the adapter for many types.
     * 
     * @see org.eclipse.emf.common.notify.Adapter#isAdapterForType(java.lang.Object)
     */
    public boolean isAdapterForType(Object type) {
        return false;
    }

    /**
     * Method invoked by notifiers in response to model changes. If the change
     * requires the tabs to be changes this method should be overridden to call
     * {@link #refreshTabs()} in that circumstance only. {{@link #refreshTabs()}
     * is an expensive operation, which will include the refresh of this section
     * and all others.
     * 
     * @see org.eclipse.emf.common.notify.Adapter#notifyChanged(org.eclipse.emf.common.notify.Notification)
     */
    public void notifyChanged(Notification notification) {
        if (!notification.isTouch() && !ignoreModelEvents) {
            unregister();
            refresh();
            register();
        }
    }

    /**
     * Sets the target from which the adapter will receive notification. This
     * method is only to be called by a notifier when this adapter is added to
     * or removed from its adapter list. In general, an adapter may be shared by
     * more than one notifier.
     * 
     * @see org.eclipse.emf.common.notify.Adapter#setTarget(org.eclipse.emf.common.notify.Notifier)
     */
    public void setTarget(Notifier newTarget) {
        // Do nothing
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.ui.properties.ModelChangeListener#ignoreModelEvents(boolean
     * )
     */
    public void ignoreModelEvents(boolean ignore) {
        ignoreModelEvents = ignore;
    }

    @Override
    public void dispose() {
        if (gotInput()) {
            unregister();
        }
        super.dispose();
    }

    @Override
    protected boolean gotInput() {
        return (input != null);
    }

    /**
     * Returns the object that owns the properties on this sheet and to which we
     * should listen for notifier events. By default this is the input object.
     * If the properties are owned by an object other than the input object,
     * such as an extended attribute, this method should be overridden.
     * 
     * @return The object on which to listen.
     */
    protected EObject[] getNotifierObjects() {
        return new EObject[] { input };
    }

    /**
     * Returns item provider adapter for given EObject, it use ResourceSet (see
     * EcoreUtil. getRegisteredAtapter() ), as a fallback it try to cast
     * EditingDomain to AdpaterFActoryEditingDomain ant use AtapterFactory from
     * there.
     * 
     * @param eo
     *            EObject for which we want ItemProviderAdapter
     * @return ItemProviderAdapter
     * @throws IllegalStateException
     *             when it cannot produce any adapter.
     */
    protected ItemProviderAdapter getItemProviderFor(EObject eo) {

        ItemProviderAdapter ad;

        /*
         * workaround for ComposedAdapterFactory it doesn't work with
         * ComposedAdapterFactory because of it's adaptNew method
         * https://bugs.eclipse.org/bugs/show_bug.cgi?id=159737
         * http://dev.eclipse
         * .org/viewcvs/index.cgi/~checkout~/org.eclipse.emf/plugins
         * /org.eclipse.
         * emf.edit/src/org/eclipse/emf/edit/provider/ComposedAdapterFactory
         * .java.diff?r1=1.4&r2=1.5&cvsroot=Tools_Project
         */
        try {
            ad =
                    (ItemProviderAdapter) EcoreUtil.getRegisteredAdapter(eo,
                            IEditingDomainItemProvider.class);
        } catch (Exception ex) {
            EList<AdapterFactory> adapterFactories =
                    eo.eResource().getResourceSet().getAdapterFactories();
            AdapterFactory adapterFactory =
                    EcoreUtil.getAdapterFactory(adapterFactories,
                            IEditingDomainItemProvider.class);
            ad =
                    (ItemProviderAdapter) adapterFactory.adapt(eo,
                            IEditingDomainItemProvider.class);
        }
        // ----------------------------------------

        if (ad == null) {
            EditingDomain ed = getEditingDomain();
            /*
             * Change for a problem raised in XPD-712 (as a result of FORM-140).
             * There are cases in the Forms properties sections where the input
             * of the section will not be connected to a resource and so the
             * method "getEditingDomain" above will throw an
             * IllegalArgumentException as it cannot establish the editing
             * domain of the input to the section. Therefore, as a fail-safe,
             * the following code is added to get the editing domain of the
             * EObject passed to this method.
             */
            if (ed == null) {
                ed = getEditingDomain(eo);
            }

            if (ed instanceof AdapterFactoryEditingDomain) {
                AdapterFactory af =
                        ((AdapterFactoryEditingDomain) ed).getAdapterFactory();
                ad =
                        (ItemProviderAdapter) af.adapt(eo,
                                IEditingDomainItemProvider.class);
            }
        }

        if (ad == null) {
            throw new IllegalStateException(
                    "Cannot find ItemProviderAdapter for: " + eo); //$NON-NLS-1$
        }
        return ad;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doGetInputContainer()
     */
    @Override
    protected EObject doGetInputContainer() {
        if (input != null) {
            // Return the input's container
            return input.eContainer();
        }
        return null;
    }

    /**
     * @return The project for the current input.
     */
    protected IProject getProject() {
        IProject project = null;
        Object input = getInput();
        if (input instanceof EObject) {
            WorkingCopy wc = WorkingCopyUtil.getWorkingCopyFor((EObject) input);

            // Sometimes (like new EObject Wizard) input Eobject is not yet
            // associated with working copy. in which case we should try getting
            // working copy for input container.
            if (wc == null) {
                EObject container = getInputContainer();
                if (container != null) {
                    wc = WorkingCopyUtil.getWorkingCopyFor(container);
                }
            }

            if (wc != null) {
                IResource resource = wc.getEclipseResources().get(0);
                if (resource != null) {
                    project = resource.getProject();
                }
            }
        }
        return project;
    }

    /**
     * Returns plug-in contribution descriptor if the section should be
     * capabilities enabled (highly recommended) or null otherwise.
     * <p/>
     * Returned descriptor should reflect plug-in contribution which contributes
     * this section so the <code>getPluginId()</code> should return id of the
     * contributing plug-in and <code>getLocalId()</code> should return id of
     * the section declared in the plug-in. It is also important to properly
     * override <code>AbstractEObjectSection.select(Object)</code> method to
     * properly support capabilities.
     * 
     * @return plug-in contribution descriptor if the section should be
     *         capabilities enabled (highly recommended) or null otherwise.
     * @see IPluginContribution
     * @see AbstractEObjectSection#select(Object)
     */
    public IPluginContribution getPluginContributon() {
        return null;
    }

    /**
     * This is a workaround for a problem that the subclass property sheet's
     * getNotifierObjects() method does not get called in some circumstances
     * that are necessary.
     * <p>
     * For instance, if the propsheet provides a command for setting text in the
     * model from text field changes and that command has to add a new
     * sub-element to the main notifier object. Then when the
     * BaseTextFieldHandler executes the command it sets AbstractEObjectSection
     * to ignore events. Unfortunately this means that the notifyChanged()
     * method does NOT reget notifier objects from subclass property sheet and
     * hence the property sheet will not have a chance to add the new
     * sub-element to the list of notifiers. Hence, further changes to the
     * sub-element will not cause refreshes on the property sheet.
     * <p>
     * The forceNotifyChanged() method is a 'quick and dirty' method of working
     * around this. It is used by the SubElementTextCommandTextHandler class
     * whenever the text control is deactivated.
     * <p>
     * This then ensures that the property sheet subclass's getNotifierObjects()
     * gets the chance to resynch with the model properly.
     * 
     * @since 3.0
     */
    public void forceNotifyChanged() {
        unregister();
        refresh();
        register();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#refresh()
     */
    @Override
    public void refresh() {
        /* XPD-1128: DOn't refresh if object has been divorced from model! */
        if (input != null
                && (input.eResource() != null || getSectionContainerType() == ContainerType.WIZARD)) {
            super.refresh();
        }
        return;
    }
}