/**
 * 
 */
package com.tibco.xpd.processeditor.xpdl2.properties.event;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.views.properties.tabbed.ISection;

import com.tibco.xpd.processeditor.xpdl2.properties.messages.Messages;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.ui.properties.AbstractFilteredTransactionalSection;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.TriggerConditional;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * @author scrossle
 * 
 */
public class EventTriggerTypeConditionalSection extends AbstractFilteredTransactionalSection
        implements ISection {
    private String instrumentationPrefixName;
    
    private Text nameText;

    public EventTriggerTypeConditionalSection() {
        super(Xpdl2Package.eINSTANCE.getActivity());
    }
    
    public EventTriggerTypeConditionalSection(String instrumentationPrefixName) {
        super(Xpdl2Package.eINSTANCE.getActivity());
        this.instrumentationPrefixName = instrumentationPrefixName;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doCreateControls(org.eclipse.swt.widgets.Composite,
     *      com.tibco.xpd.ui.properties.XpdFormToolkit)
     */
    @Override
    protected Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {
        Composite root = toolkit.createComposite(parent);
        root.setLayout(new GridLayout(2, false));

        toolkit.createLabel(root,
                Messages.EventTriggerTypeRuleSection_Name_label,
                SWT.NONE);
        nameText = toolkit.createText(root, "", SWT.NONE,instrumentationPrefixName+"Name"); //$NON-NLS-1$ //$NON-NLS-2$
        nameText.setData("name", "textEventRuleName");  //$NON-NLS-1$ //$NON-NLS-2$
        nameText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        manageControlUpdateOnDeactivate(nameText);
        
        return root;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doGetCommand(java.lang.Object)
     */
    @Override
    protected Command doGetCommand(Object obj) {
        Command cmd = null;
        if (obj == nameText) {
            Activity eventAct = getActivity();
            EditingDomain ed = getEditingDomain();

            if (ed != null && eventAct != null) {
                cmd = EventObjectUtil.getSetRuleNameCommand(ed, eventAct, nameText.getText());
            }
        }
        return cmd;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doRefresh()
     */
    @Override
    protected void doRefresh() {
        // If controls have been disposed then unregister adapter
        if (nameText.isDisposed()) {
            return;
        }

        Activity eventAct = getActivity();

        if (eventAct != null) {
            // Update the name
            String conditionName = null;
            TriggerConditional triggerRule = EventObjectUtil.getTriggerConditional(getActivity());
            if (triggerRule != null) {
                conditionName = triggerRule.getConditionName();
            }

            updateText(nameText, conditionName);
        }

    }

    /**
     * Get the selected input object as an activity
     * 
     * @return activity for event or null on error.
     */
    public Activity getActivity() {
        Object o = getInput();
        if (o instanceof Activity) {
            Activity act = (Activity) o;

            return act;
        }
        return null;
    }
}
