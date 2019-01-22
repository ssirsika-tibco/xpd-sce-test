/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.processwidget.properties;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.processwidget.adapters.BaseProcessAdapter;
import com.tibco.xpd.processwidget.adapters.BaseProcessAdapter.ProcessWidgetEvent;
import com.tibco.xpd.processwidget.adapters.BaseProcessAdapter.ProcessWidgetListener;
import com.tibco.xpd.processwidget.parts.ModelAdapterEditPart;
import com.tibco.xpd.ui.properties.AbstractXpdSection;

/**
 * Abstract class for property sections for <b>Process Widgets</b>. This section
 * is intended to be used for <b>single selection</b> property tabs. It supports
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
 * The following abstract classes will need to be implemented by subclasses:
 * <ul>
 * <code>doCreateControls</code> - create the controls for the section
 * </ul>
 * <ul>
 * <code>doGetCommand</code> - called when the contents of managed controls are
 * changed
 * </ul>
 * <ul>
 * <code>doRefresh</code> - called to udpate the section when the model changes.
 * Only registered objects (see paragraph about <code>getNotifierObjects</code>
 * below) will affect this section.
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
 * properties) then <code>setInput</code> will have to be overriden.
 * </p>
 * 
 * @author njpatel
 */
public abstract class AbstractWidgetPropertiesSection extends
        AbstractXpdSection implements ProcessWidgetListener {

    private EditingDomain editingDomain;

    private ModelAdapterEditPart input;

    /**
     * List of adapters that this class will register listeners with
     */
    private List<BaseProcessAdapter> adapters =
            new ArrayList<BaseProcessAdapter>();

    @Override
    public EditingDomain getEditingDomain() {
        if (editingDomain == null) {
            if (input != null) {
                BaseProcessAdapter modelAdapter = input.getModelAdapter();

                if (modelAdapter != null) {
                    editingDomain =
                            getEditingDomain((EObject) modelAdapter.getTarget());
                }
            }

        }

        return editingDomain;
    }

    @Override
    protected boolean gotInput() {
        return (input != null);
    }

    @Override
    public void setInput(Collection<?> items) {

        if (input != null) {
            unregister();
            input = null;
        }

        if (items.size() == 1) {
            Object obj = items.iterator().next();

            if (obj instanceof ModelAdapterEditPart) {
                input = (ModelAdapterEditPart) obj;
                register();
            }
        }
    }

    /**
     * Register the <code>BaseProcessAdapter</code> objects that this section
     * needs to listen to for notifier events.
     */
    protected void register() {
        BaseProcessAdapter[] notifiers = getNotifierObjects();
        if (notifiers != null) {
            for (BaseProcessAdapter adapter : notifiers) {
                // If adapter not already added to the registered list then
                // register it
                if (!adapters.contains(adapter)) {
                    adapter.addListener(this);
                    adapters.add(adapter);
                }
            }
        }
    }

    /**
     * Unregister the registered notifier objects
     */
    protected void unregister() {
        if (adapters != null) {
            for (BaseProcessAdapter adapter : adapters) {
                adapter.removeListener(this);
            }

            adapters.clear();
        }
    }

    /**
     * Returns the model adapter that owns the properties on this sheet and to
     * which we should listen for notifier events. By default this is the input
     * object. If the properties are owned by an object other than the input
     * object, such as an extended attribute, this method should be overriden.
     * 
     * @return The object on which to listen.
     */
    protected BaseProcessAdapter[] getNotifierObjects() {
        return new BaseProcessAdapter[] { input.getModelAdapter() };
    }

    /**
     * Get the input of this section.
     * 
     * @return <code>ModelAdapterEditPart</code> input of this section.
     */
    protected ModelAdapterEditPart getInput() {
        return input;
    }

    /**
     * Get the model adapter from the input
     * 
     * @return <code>BaseProcessAdapter</code> if the input is valid,
     *         <b>null</b> otherwise.
     */
    protected BaseProcessAdapter getModelAdapter() {
        BaseProcessAdapter adapter = null;

        if (input != null) {
            adapter = input.getModelAdapter();

        }

        return adapter;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.processwidget.adapters.BaseProcessAdapter.ProcessWidgetListener
     * #notifyChanged(com.tibco.xpd.processwidget.adapters.BaseProcessAdapter.
     * ProcessWidgetEvent)
     */
    public void notifyChanged(ProcessWidgetEvent processEvent) {
        refresh();
    }

    @Override
    public void dispose() {
        if (gotInput()) {
            unregister();
        }
        super.dispose();
    }

    @Override
    protected EObject doGetInputContainer() {
        /*
         * This is not currently used for Widget property sections so returning
         * null
         */
        return null;
    }

    /**
     * Get input from the model (not the EditPart).
     * 
     * @return Input from the model.
     */
    protected Object getModelInput() {
        Object inputModel = null;
        if (getInput() != null) {
            inputModel = getInput().getModel();
        }
        return inputModel;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#refresh()
     */
    @Override
    public void refresh() {
        if (input != null) {
            super.refresh();
        }
    }
}
