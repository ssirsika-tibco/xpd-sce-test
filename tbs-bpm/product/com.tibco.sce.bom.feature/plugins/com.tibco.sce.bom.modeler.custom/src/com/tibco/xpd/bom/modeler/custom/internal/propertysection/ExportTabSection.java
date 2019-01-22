/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.custom.internal.propertysection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.ui.provider.TransactionalAdapterFactoryContentProvider;
import org.eclipse.emf.transaction.ui.provider.TransactionalAdapterFactoryLabelProvider;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTreeViewer;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.dialogs.ContainerCheckedTreeViewer;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.PrimitiveType;

import com.tibco.xpd.bom.modeler.custom.internal.Messages;
import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.bom.resources.utils.BOMProfileUtils;
import com.tibco.xpd.bom.resources.utils.BOMUtils;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.ui.properties.XpdFormToolkit;

/**
 * 
 * The Export tab properties section.</br></br>
 * 
 * Shows the following:</br></br>
 * 
 * <b>XML Schema Export Options:</b></br>
 * 
 * All Classes, PrimitiveTypes and Enumerations can be configured to create a
 * top level element when exported to XML schema. If selected/unselected the top
 * level element will/wil not be created appropriately.
 * 
 * @author rgreen
 * 
 */
public class ExportTabSection extends AbstractGeneralSection {

    private static final String EXPORT_OPTIONS_TITLE =
            Messages.ExportTabSection_title;

    private CheckboxTreeViewer modelViewer;

    public ExportTabSection() {
        setShouldUseExtraSpace(true);
    }

    @Override
    public boolean select(Object toTest) {
        EObject input = resollveInput(toTest);

        // Also check whether the XSD profile is set. If it is then don't show
        // this tab
        if (input instanceof Model) {
            Model mod = (Model) input;

            if (BOMProfileUtils.isProfileAppliedToModel(mod,
                    BOMResourcesPlugin.PATHMAP_XSDNOTATION_PROFILE)) {
                return false;
            } else {
                return true;
            }
        }

        return false;

    }

    @Override
    protected Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {
        Composite root = toolkit.createComposite(parent);
        root.setLayout(new GridLayout(2, false));

        // Heading
        Label heading =
                createSectionHeading(root, toolkit, EXPORT_OPTIONS_TITLE);
        GridData gdheading = new GridData(SWT.LEFT, SWT.CENTER, false, false);
        gdheading.horizontalSpan = 2;
        heading.setLayoutData(gdheading);

        // Description label
        Label descLabel =
                toolkit.createLabel(root,
                        Messages.ExportTabSection_description_label);
        GridData gdDescLabel = new GridData(SWT.LEFT, SWT.CENTER, false, false);
        descLabel.setLayoutData(gdDescLabel);

        // Select and UnSelect All buttons
        Composite btnsControl = toolkit.createComposite(root);
        GridData data = new GridData();
        data = new GridData(SWT.RIGHT, SWT.CENTER, true, false);
        data.horizontalSpan = 1;
        btnsControl.setLayoutData(data);
        btnsControl.setLayout(new RowLayout());

        Button selectAllBtn =
                toolkit.createButton(btnsControl,
                        Messages.ExportTabSection_SelectAll_action,
                        SWT.NONE);
        selectAllBtn.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                TransactionalEditingDomain ed =
                        (TransactionalEditingDomain) getEditingDomain();
                RecordingCommand cmd = new SetAllItemsFlagCommand(ed, true);

                if (cmd.canExecute()) {
                    ed.getCommandStack().execute(cmd);
                }
                doRefresh();
            }
        });

        Button unSelectAllBtn =
                toolkit.createButton(btnsControl,
                        Messages.ExportTabSection_UnselectAll_action,
                        SWT.NONE);
        unSelectAllBtn.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                TransactionalEditingDomain ed =
                        (TransactionalEditingDomain) getEditingDomain();
                RecordingCommand cmd = new SetAllItemsFlagCommand(ed, false);

                if (cmd.canExecute()) {
                    ed.getCommandStack().execute(cmd);
                }
                doRefresh();
            }
        });

        // The CheckBox Tree viewer for the model elements
        // The only UML2 elements that need to be shown are
        // Class, PrimitiveType and Enumeration
        modelViewer = new ContainerCheckedTreeViewer(root, SWT.BORDER);
        GridData layoutData = new GridData(SWT.FILL, SWT.FILL, true, true);
        layoutData.horizontalSpan = 2;
        modelViewer.getTree().setLayoutData(layoutData);
        TransactionalEditingDomain ed =
                XpdResourcesPlugin.getDefault().getEditingDomain();
        AdapterFactory factory =
                XpdResourcesPlugin.getDefault().getAdapterFactory();
        modelViewer.setContentProvider(new MyContentProvider(
                new TransactionalAdapterFactoryContentProvider(ed, factory)));
        modelViewer
                .setLabelProvider(new TransactionalAdapterFactoryLabelProvider(
                        ed, factory));

        modelViewer.setSorter(new ViewerSorter() {
            @Override
            public int category(Object element) {
                int cat = 0;
                if (element instanceof Class) {
                    cat = 1;
                } else if (element instanceof PrimitiveType) {
                    cat = 2;
                } else if (element instanceof Enumeration) {
                    cat = 3;
                }
                return cat;
            }
        });

        modelViewer.addCheckStateListener(new ICheckStateListener() {
            @Override
            public void checkStateChanged(CheckStateChangedEvent event) {
                // Set the TLE export flag appropriately
                Object element = event.getElement();

                if (element instanceof NamedElement) {
                    NamedElement elem = (NamedElement) element;
                    TransactionalEditingDomain ed =
                            (TransactionalEditingDomain) getEditingDomain();
                    RecordingCommand cmd =
                            new SetTLEExportFlagCommand(ed, elem, event
                                    .getChecked());

                    if (cmd.canExecute()) {
                        ed.getCommandStack().execute(cmd);
                    }
                    doRefresh();
                }

            }
        });

        return root;
    }

    @Override
    public void setInput(Collection<?> items) {
        super.setInput(items);

        EObject input2 = getInput();
        if (modelViewer != null && !modelViewer.getControl().isDisposed()) {
            if (input2 != modelViewer.getInput()) {
                modelViewer.setInput(input2);
            }
        }
    }

    @Override
    protected Command doGetCommand(Object obj) {
        return null;
    }

    @Override
    protected void doRefresh() {
        EObject input = getInput();
        TreeIterator<Object> allContents =
                EcoreUtil.getAllContents(input, false);
        List<EObject> tle = new ArrayList<EObject>();
        while (allContents.hasNext()) {
            Object next = allContents.next();

            if (next instanceof Class || next instanceof PrimitiveType
                    || next instanceof Enumeration) {
                if (BOMUtils.isExportAsTLE((Classifier) next)) {
                    tle.add((EObject) next);
                }
            }
        }

        modelViewer.refresh();
        modelViewer.setCheckedElements(tle.toArray());

    }

    @Override
    protected boolean shouldDisplay(EObject eo) {
        return (eo instanceof Model);
    }

    /**
     * 
     * Content Provider that overides getChildren() and hasChildren().
     * 
     * @author rgreen
     * 
     */
    private class MyContentProvider implements ITreeContentProvider {

        private final TransactionalAdapterFactoryContentProvider provider;

        public MyContentProvider(
                TransactionalAdapterFactoryContentProvider provider) {
            this.provider = provider;
        }

        @Override
        public Object[] getChildren(Object parentElement) {
            Object[] children = null;

            if (parentElement instanceof Package) {
                children = provider.getChildren(parentElement);
            }

            return children;
        }

        @Override
        public Object getParent(Object element) {
            Object parent = provider.getParent(element);
            return parent;
        }

        @Override
        public boolean hasChildren(Object element) {
            if (element instanceof Class || element instanceof Enumeration) {
                return false;
            }
            boolean hasChildren = provider.hasChildren(element);
            return hasChildren;
        }

        @Override
        public Object[] getElements(Object inputElement) {
            return provider.getElements(inputElement);
        }

        @Override
        public void dispose() {
        }

        @Override
        public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
            provider.inputChanged(viewer, oldInput, newInput);

        }

    }

    /**
     * 
     * Sets the TLE export flag on the UML2 element for which the checkbox state
     * has changed.
     * 
     * @author rgreen
     * 
     */
    private class SetTLEExportFlagCommand extends RecordingCommand {

        private NamedElement namedElement;

        private boolean isChecked;

        private TransactionalEditingDomain ed;

        public SetTLEExportFlagCommand(TransactionalEditingDomain domain,
                NamedElement elem, boolean state) {
            super(domain,
                    Messages.ExportTabSection_ExportTLESetFlagCommand_label);

            namedElement = elem;
            isChecked = state;
            ed = domain;

        }

        @Override
        public boolean canExecute() {
            if (ed == null || namedElement == null) {
                return false;
            }
            return super.canExecute();
        }

        @Override
        protected void doExecute() {
            setTLEExportFlag(namedElement);
        }

        private void setTLEExportFlag(NamedElement elem) {
            if (elem instanceof Package) {
                EList<PackageableElement> packagedElements =
                        ((Package) elem).getPackagedElements();

                for (PackageableElement packageableElement : packagedElements) {
                    if (packageableElement instanceof Class
                            || packageableElement instanceof PrimitiveType
                            || packageableElement instanceof Enumeration) {
                        BOMUtils.setTLEFlag(packageableElement, isChecked);
                    } else if (packageableElement instanceof Package) {
                        setTLEExportFlag(packageableElement);
                    }
                }

            } else {
                BOMUtils.setTLEFlag((PackageableElement) namedElement,
                        isChecked);
            }

        }

    }

    /**
     * 
     * Command that responds to an event from the click of the Select All and
     * Unselect All buttons. If the event state passed in is true i.e. a Select
     * All event, then all element export flags are set to true. Conversely, if
     * false i.e. Unselect All, then all element export flags are set to false.
     * 
     * @author rgreen
     * 
     */
    private class SetAllItemsFlagCommand extends RecordingCommand {

        private boolean state = false;

        public SetAllItemsFlagCommand(TransactionalEditingDomain domain,
                boolean state) {

            super(domain);

            if (state) {
                setLabel(Messages.ExportTabSection_ExportTLESelectAllCommand_label);
            } else {
                setLabel(Messages.ExportTabSection_ExportTLEUnSelectAllCommand_label);
            }

            this.state = state;
        }

        @Override
        protected void doExecute() {
            EObject input2 = getInput();

            if (input2 instanceof Model) {
                Model mod = (Model) input2;
                setAllChecked(mod.getPackagedElements());
            }
        }

        private void setAllChecked(EList<PackageableElement> packagedElements) {

            for (PackageableElement packageableElement : packagedElements) {
                if (packageableElement instanceof Package) {
                    setAllChecked(((Package) packageableElement)
                            .getPackagedElements());
                } else if (packageableElement instanceof Class
                        || packageableElement instanceof PrimitiveType
                        || packageableElement instanceof Enumeration) {
                    BOMUtils.setTLEFlag(packageableElement, state);
                }
            }
        }

    }

}
