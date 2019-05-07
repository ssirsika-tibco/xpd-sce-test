package com.tibco.xpd.bom.modeler.custom.internal.propertysection;

import java.util.List;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.IFilter;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.EnumerationLiteral;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.util.UMLUtil;

import com.tibco.xpd.bom.globaldata.api.BOMGlobalDataUtils;
import com.tibco.xpd.bom.globaldata.resources.GlobalDataProfileManager;
import com.tibco.xpd.bom.modeler.custom.internal.Messages;
import com.tibco.xpd.bom.modeler.custom.terminalstates.TerminalStateContentProvider;
import com.tibco.xpd.bom.modeler.custom.terminalstates.TerminalStateLabelProvider;
import com.tibco.xpd.bom.modeler.custom.terminalstates.TerminalStateProperties;
import com.tibco.xpd.ui.properties.AbstractTransactionalSection;
import com.tibco.xpd.ui.properties.XpdFormToolkit;

/**
 * Property section for setting the terminal states on a Case State attribute.
 *
 * @author nwilson
 * @since 1 Apr 2019
 */
public class TerminalStatesTabSection extends AbstractTransactionalSection
        implements IFilter {

    private CheckboxTableViewer terminal;

    private TerminalStateProperties properties;

    private Property property;

    /**
     * 
     */
    public TerminalStatesTabSection() {
        super();
        properties = new TerminalStateProperties();
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doCreateControls(org.eclipse.swt.widgets.Composite,
     *      com.tibco.xpd.ui.properties.XpdFormToolkit)
     *
     * @param parent
     * @param toolkit
     * @return
     */
    @Override
    protected Control doCreateControls(Composite parent,
            XpdFormToolkit toolkit) {
        Composite panel = toolkit.createComposite(parent);
        panel.setLayout(new GridLayout());
        Label terminalLabel = toolkit.createLabel(panel,
                Messages.TerminalStatesTabSection_statesLabel);
        terminalLabel
                .setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
        terminal = CheckboxTableViewer.newCheckList(panel,
                SWT.V_SCROLL | SWT.SINGLE | SWT.BORDER);
        terminal.getTable()
                .setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
        terminal.setContentProvider(new TerminalStateContentProvider());
        terminal.setLabelProvider(new TerminalStateLabelProvider());

        manageControl(terminal.getTable());
        return panel;
    }

    /**
     * @see org.eclipse.ui.views.properties.tabbed.AbstractPropertySection#setInput(org.eclipse.ui.IWorkbenchPart,
     *      org.eclipse.jface.viewers.ISelection)
     *
     * @param part
     * @param selection
     */
    @Override
    public void setInput(IWorkbenchPart part, ISelection selection) {
        super.setInput(part, selection);
        property = getInput(selection);
        updateFromProperty();
    }

    /**
     * 
     */
    private void updateFromProperty() {
        terminal.setInput(property);
        EList<EnumerationLiteral> states =
                properties.getTerminalStates(property);
        if (states != null) {
            terminal.setCheckedElements(states.toArray());
        } else {
            terminal.setAllChecked(false);
        }
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractTransactionalSection#resollveInput(java.lang.Object)
     *
     * @param object
     * @return
     */
    @Override
    protected EObject resollveInput(Object object) {
        return toProperty(object);
    }

    /**
     * Returns a single selected object from the ISelection input.
     * 
     * @param selection
     *            The selection.
     * @return The selected object or null.
     */
    private Property getInput(ISelection selection) {
        Property input = null;
        if (selection instanceof IStructuredSelection) {
            Object first = ((IStructuredSelection) selection).getFirstElement();
            input = toProperty(first);
        }
        return input;
    }

    private Property toProperty(Object obj) {
        Property property = null;
        if (obj instanceof Property) {
            property = (Property) obj;
        } else if (obj instanceof IAdaptable) {
            property = ((IAdaptable) obj).getAdapter(Property.class);
        }
        return property;
    }

    /**
     * Filter function to return true if the selected value is a case state
     * attribute.
     * 
     * @see org.eclipse.jface.viewers.IFilter#select(java.lang.Object)
     */
    @Override
    public boolean select(Object toTest) {
        Object adapted = toTest;
        if (adapted instanceof IAdaptable) {
            adapted = ((IAdaptable) adapted).getAdapter(Property.class);
        }
        if (adapted instanceof Property) {
            Property property = (Property) adapted;
            return GlobalDataProfileManager.getInstance().isCaseState(property);
        }
        return false;
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doRefresh()
     *
     */
    @Override
    protected void doRefresh() {
        property = getInput(getSelection());
        updateFromProperty();
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doGetCommand(java.lang.Object)
     *
     * @param obj
     * @return
     */
    @Override
    protected Command doGetCommand(Object obj) {
        Command cmd = null;
        if (obj instanceof TableItem) {
            TableItem item = (TableItem) obj;
            EnumerationLiteral state = (EnumerationLiteral) item.getData();
            if (item.getChecked()) {
                cmd = properties.getAddTerminalStateCommand(getEditingDomain(),
                        property,
                        state);
            } else {
                cmd = properties.getRemoveTerminalStateCommand(
                        getEditingDomain(),
                        property,
                        state);
            }
        }
        return cmd;
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractTransactionalSection#shouldRefresh(java.util.List)
     *
     * @param notifications
     * @return
     */
    @Override
    protected boolean shouldRefresh(List<Notification> notifications) {
        boolean shouldRefresh = false;
        for (Notification notification : notifications) {
            Object notifier = notification.getNotifier();
            if (notifier instanceof EObject) {
                EObject eo = (EObject) notifier;
                Element base = UMLUtil.getBaseElement(eo);
                if (base instanceof Property) {
                    Property baseProperty = (Property) base;
                    if (BOMGlobalDataUtils.isCaseState(baseProperty)) {
                        shouldRefresh = true;
                    }
                }
            }
        }
        return shouldRefresh;
    }

}
