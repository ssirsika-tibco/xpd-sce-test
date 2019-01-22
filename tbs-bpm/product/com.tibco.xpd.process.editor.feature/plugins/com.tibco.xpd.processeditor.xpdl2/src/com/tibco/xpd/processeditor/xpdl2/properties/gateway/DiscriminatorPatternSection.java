/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.processeditor.xpdl2.properties.gateway;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.fieldassist.DecoratedField;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.properties.util.FixedValueFieldAssistHelper;
import com.tibco.xpd.processeditor.xpdl2.properties.util.FixedValueFieldAssistHelper.FixedValueFieldChangedListener;
import com.tibco.xpd.processeditor.xpdl2.properties.util.FixedValueFieldAssistHelper.FixedValueFieldProposalProvider;
import com.tibco.xpd.ui.properties.AbstractFilteredTransactionalSection;
import com.tibco.xpd.ui.properties.TextFieldVerifier;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.xpdExtension.Discriminator;
import com.tibco.xpd.xpdExtension.StructuredDiscriminator;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.FlowContainer;
import com.tibco.xpd.xpdl2.JoinSplitType;
import com.tibco.xpd.xpdl2.NamedElement;
import com.tibco.xpd.xpdl2.Route;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.ProcessFlowAnalyser;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * 
 * <p>
 * <i>Created: 30 Mar 2008</i>
 * </p>
 * 
 * @author Kamlesh Upadhyaya
 * 
 */
public class DiscriminatorPatternSection extends
        AbstractFilteredTransactionalSection implements TextFieldVerifier,
        FixedValueFieldChangedListener {

    private Text incomingPathNumberText;

    private String instrumentationPrefixName;

    public static final int STANDARD_LABEL_WIDTH = 95;

    protected static final int TEXT_WIDTH_HINT = 150;

    private static final String INTEGER_PATTERN_STRING = "\\d*"; //$NON-NLS-1$

    private Pattern integerPattern;

    private DecoratedField gatewaySelectionField;

    private class ParallelSplitGatewayEntry {
        private NamedElement element;

        public ParallelSplitGatewayEntry(NamedElement element) {
            this.element = element;
        }

        @Override
        public String toString() {
            return getName();
        }

        public String getName() {
            if (null == element.getName()) {
                return "<Unnamed Gateway>"; //$NON-NLS-1$
            } else {
                return element.getName();
            }
        }

        public String getId() {
            return element.getId();
        }
    }

    private List<ParallelSplitGatewayEntry> parallelSplitGatewayList;

    public DiscriminatorPatternSection() {
        super(Xpdl2Package.eINSTANCE.getActivity());
    }

    public DiscriminatorPatternSection(String instrumentationPrefixName) {
        super(Xpdl2Package.eINSTANCE.getActivity());
        this.instrumentationPrefixName = instrumentationPrefixName;
    }

    @Override
    protected Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {
        integerPattern = Pattern.compile(INTEGER_PATTERN_STRING);
        Composite sectionComposite = toolkit.createComposite(parent);
        toolkit.adapt(sectionComposite);
        GridLayout gridLayout = new GridLayout(2, false);
        gridLayout.marginWidth = 0;
        sectionComposite.setLayout(gridLayout);

        Label lab =
                createLabel(sectionComposite,
                        toolkit,
                        Messages.DiscriminatorPatternSection_ParallelSplitGatewayLabel);
        lab.setLayoutData(new GridData());
        lab.setToolTipText(Messages.Parallel_Split_Gateway_Tooltip);

        // MR 37236 - begin
        /**
         * Add content assisted text control for parallel split gateway.
         * */
        FixedValueFieldProposalProvider proposalProvider =
                new FixedValueFieldAssistHelper.FixedValueFieldProposalProvider() {
                    public Object[] getProposals() {
                        if (null != parallelSplitGatewayList) {
                            return parallelSplitGatewayList.toArray();
                        }
                        return null;
                    }
                };

        FixedValueFieldAssistHelper gatewayHelper =
                new FixedValueFieldAssistHelper(toolkit, sectionComposite,
                        proposalProvider, true);
        gatewayHelper.addValueChangedListener(this);
        gatewaySelectionField = gatewayHelper.getDecoratedField();
        gatewaySelectionField.getControl()
                .setData("name", "textReferencedGateway"); //$NON-NLS-1$ //$NON-NLS-2$

        gatewaySelectionField.getLayoutControl().setLayoutData(new GridData(
                GridData.FILL_HORIZONTAL));

        gatewaySelectionField.getLayoutControl().setBackground(sectionComposite
                .getBackground());
        gatewaySelectionField.getControl()
                .setData(XpdFormToolkit.INSTRUMENTATION_DATA_NAME,
                        instrumentationPrefixName + "ReferencedGateway"); //$NON-NLS-1$
        gatewaySelectionField.getLayoutControl()
                .setData(XpdFormToolkit.INSTRUMENTATION_DATA_NAME,
                        "assistReferencedGateway"); //$NON-NLS-1$

        // MR 37236 - end

        lab =
                createLabel(sectionComposite,
                        toolkit,
                        Messages.DiscriminatorPatternSection_IncomingFlowLabel);
        lab.setLayoutData(new GridData());
        lab.setToolTipText(Messages.Incoming_Flow_Tooltip);

        incomingPathNumberText =
                toolkit.createText(sectionComposite,
                        "", instrumentationPrefixName + "incomingPathText"); //$NON-NLS-1$  //$NON-NLS-2$
        GridData gData = new GridData(GridData.FILL_HORIZONTAL);
        gData.widthHint = DiscriminatorPatternSection.TEXT_WIDTH_HINT;
        incomingPathNumberText.setLayoutData(gData);
        manageControl(incomingPathNumberText);

        return sectionComposite;
    }

    @Override
    protected Command doGetCommand(Object obj) {
        CompoundCommand cmd = null;
        Discriminator discriminator = getDiscriminator(getActivityInput());
        if (discriminator == null) {
            return cmd;
        }

        EditingDomain ed = getEditingDomain();
        cmd = new CompoundCommand();

        if (discriminator != null && ed != null) {

            StructuredDiscriminator structuredDiscriminator =
                    discriminator.getStructuredDiscriminator();
            if (structuredDiscriminator == null) {
                structuredDiscriminator =
                        XpdExtensionFactory.eINSTANCE
                                .createStructuredDiscriminator();

                cmd.append(SetCommand.create(ed,
                        discriminator,
                        XpdExtensionPackage.eINSTANCE
                                .getDiscriminator_StructuredDiscriminator(),
                        structuredDiscriminator));
            }

            if (obj == incomingPathNumberText) {
                cmd
                        .setLabel(Messages.DiscriminatorPatternSection_SetIncomingPathCommand);
                BigInteger intValue =
                        new BigInteger(incomingPathNumberText.getText());

                cmd
                        .append(new SetCommand(
                                ed,
                                structuredDiscriminator,
                                XpdExtensionPackage.eINSTANCE
                                        .getStructuredDiscriminator_WaitForIncomingPath(),
                                intValue));

            } else if (obj == gatewaySelectionField) {
                cmd
                        .setLabel(Messages.DiscriminatorPatternSection_SetSplitNameCommand);

                cmd
                        .append(new SetCommand(
                                ed,
                                structuredDiscriminator,
                                XpdExtensionPackage.eINSTANCE
                                        .getStructuredDiscriminator_UpStreamParallelSplit(),
                                gatewaySelectionField.getControl().getData()));

            }
        }
        return cmd;

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.ui.properties.AbstractEObjectSection#setInput(java.util
     * .Collection)
     */
    @Override
    public void setInput(Collection<?> items) {
        super.setInput(items);
    }

    @Override
    protected void doRefresh() {
        EditingDomain ed = getEditingDomain();
        Activity activity = getActivityInput();
        if (ed == null || activity == null) {
            return;
        }
        if (gatewaySelectionField.getControl().isDisposed()) {
            return;
        }
        if (incomingPathNumberText.isDisposed()) {
            return;
        }

        updateText(incomingPathNumberText, ""); //$NON-NLS-1$
        Discriminator discriminator = getDiscriminator(activity);
        if (discriminator == null) {
            return;
        }
        populateUIFields();
    }

    public void verifyText(Event event) {
        Text textControl = ((Text) event.widget);
        String t =
                textControl.getText(0, event.start - 1)
                        + event.text
                        + textControl.getText(event.end, textControl
                                .getCharCount() - 1);
        if (textControl == incomingPathNumberText) {
            if (!integerPattern.matcher(t).matches()) {
                event.doit = false;
            }
            return;
        }
    }

    private Activity getActivityInput() {
        return (Activity) getInput();
    }

    /**
     * @param toTest
     *            The object to test.
     * @return true if it is a valid object for this property section.
     * @see com.tibco.xpd.ui.properties.BaseXpdSection#select(java.lang.Object)
     */
    @Override
    public boolean select(Object toTest) {
        EObject eo = null;
        boolean ok = false;
        if (toTest instanceof EObject) {
            eo = (EObject) toTest;
        } else if (toTest instanceof IAdaptable) {
            eo = (EObject) ((IAdaptable) toTest).getAdapter(EObject.class);
        }
        if (eo != null) {
            if (eo instanceof Activity) {
                Activity activity = (Activity) eo;
                Route route = activity.getRoute();
                if (route == null) {
                    return ok;
                }
                JoinSplitType gatewayType = route.getGatewayType();
                if (gatewayType == null) {
                    return ok;
                }
                if (JoinSplitType.COMPLEX_LITERAL == gatewayType) {
                    Discriminator discriminator =
                            (Discriminator) Xpdl2ModelUtil
                                    .getOtherElement(route,
                                            XpdExtensionPackage.eINSTANCE
                                                    .getDocumentRoot_Discriminator());
                    if (discriminator == null) {
                        return ok;
                    }
                    ok = true;
                }
            }
        }
        return ok;
    }

    /**
     * This will populate the combo with upstream and Splits.
     */
    private void populateUIFields() {
        Activity activityInput = getActivityInput();
        FlowContainer flowContainer = activityInput.getFlowContainer();
        // MR 37236 - begin
        String textValue = ""; //$NON-NLS-1$
        Collection<Activity> activityList =
                Xpdl2ModelUtil.getAllActivitiesInProc(Xpdl2ModelUtil
                        .getProcess(activityInput));
        parallelSplitGatewayList =
                new ArrayList<ParallelSplitGatewayEntry>(activityList.size());
        // MR 37236 - end

        Map<String, NamedElement> andSplitMap =
                new HashMap<String, NamedElement>();
        getUpStreamAndSplit(flowContainer, null, andSplitMap);

        Collection<NamedElement> values = andSplitMap.values();
        if (values != null && !values.isEmpty()) {
            for (NamedElement andSplit : values) {
                String name = andSplit.getName();
                if (name == null) {
                    name = ""; //$NON-NLS-1$
                }
                parallelSplitGatewayList.add(new ParallelSplitGatewayEntry(
                        andSplit));
            }
        }
        StructuredDiscriminator structuredDiscriminator =
                getStructuredDiscriminator(activityInput);
        if (structuredDiscriminator != null) {
            String upStreamParallelSplit =
                    structuredDiscriminator.getUpStreamParallelSplit();
            if (upStreamParallelSplit != null
                    && upStreamParallelSplit.length() > 0) {
                // MR 37236 - begin
                /**
                 * from the model, set the name of the referenced gateway to the
                 * content assist control
                 * */
                Activity selectedActivity = null;
                for (Activity act : activityList) {
                    if (act.getId().equals(upStreamParallelSplit)) {
                        selectedActivity = act;
                    }
                }
                if (null != selectedActivity) {
                    if (null != selectedActivity.getName()) {
                        textValue = selectedActivity.getName();
                    } else {
                        /**
                         * The default value if there is no name for the gateway
                         * */
                        textValue = "<Unnamed Gateway>"; //$NON-NLS-1$ //
                    }
                } else {
                    /**
                     * if the referenced gateway is deleted from the diagram
                     * then setting the text to Cannot Access Gateway!
                     * */
                    textValue = "<Cannot Access Gateway>"; //$NON-NLS-1$
                }

                if (gatewaySelectionField != null
                        && !gatewaySelectionField.getControl().isDisposed()) {
                    ((Text) gatewaySelectionField.getControl())
                            .setText(textValue);
                }
            } else {
                ((Text) gatewaySelectionField.getControl()).setText(textValue);
            }
            // MR 37236 - end

            BigInteger waitForIncomingPath =
                    structuredDiscriminator.getWaitForIncomingPath();
            if (waitForIncomingPath != null) {
                updateText(incomingPathNumberText, structuredDiscriminator
                        .getWaitForIncomingPath().toString());
            }
        }

    }

    private void getUpStreamAndSplit(FlowContainer flowContainer,
            Transition tran, Map<String, NamedElement> andSplitMap) {
        /**
         * Get a list of upstream activity ids
         * */
        Activity act = getActivityInput();
        ProcessFlowAnalyser transitionCache =
                new ProcessFlowAnalyser(true, flowContainer);

        Set<String> upstreamActivities =
                transitionCache.getUpstreamActivityIds(act.getId());
        /**
         * Add all of them that are parallel splits to the list.
         * */
        for (String upstreamActId : upstreamActivities) {
            Activity upstreamAct = flowContainer.getActivity(upstreamActId);
            if (upstreamAct != null) {
                if (isParallelSplit(upstreamAct, transitionCache)) {
                    andSplitMap.put(upstreamAct.getId(), upstreamAct);
                }
            }
        }

        return;
    }

    private boolean isParallelSplit(Activity activity,
            ProcessFlowAnalyser transitionCache) {
        Route route = activity.getRoute();
        if (route != null) {
            JoinSplitType gatewayType =
                    Xpdl2ModelUtil.safeGetGatewayType(activity);
            if (gatewayType != null) {
                if (JoinSplitType.PARALLEL_LITERAL.equals(gatewayType)) {
                    /**
                     * It's a parallel gateway
                     */
                    if (transitionCache
                            .getOutgoingTransitions(activity.getId()).size() > 1) {
                        /**
                         * And it has multiple outgoing transitions So it's a
                         * parallel split.
                         */
                        return true;
                    }
                }
            }
        }

        return false;
    }

    private Discriminator getDiscriminator(Activity activity) {
        Route route = activity.getRoute();
        if (route == null) {
            return null;
        }
        Discriminator discriminator =
                (Discriminator) Xpdl2ModelUtil.getOtherElement(route,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_Discriminator());
        return discriminator;
    }

    private StructuredDiscriminator getStructuredDiscriminator(Activity activity) {
        Discriminator discriminator = getDiscriminator(activity);
        if (discriminator == null) {
            return null;
        }
        StructuredDiscriminator structuredDiscriminator =
                discriminator.getStructuredDiscriminator();
        return structuredDiscriminator;

    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.util.FixedValueFieldAssistHelper.FixedValueFieldChangedListener#fixedValueFieldChanged(java.lang.Object)
     * 
     * @param newValue
     */
    public void fixedValueFieldChanged(Object newValue) {
        CompoundCommand cmd = new CompoundCommand();
        Activity activity = getActivityInput();
        Route route = activity.getRoute();
        EditingDomain ed = getEditingDomain();

        ParallelSplitGatewayEntry gatewayEntry = null;

        if (null != route) {

            if (newValue instanceof ParallelSplitGatewayEntry) {
                gatewayEntry = (ParallelSplitGatewayEntry) newValue;
            }

            String currGatewayId =
                    Xpdl2ModelUtil.getReferencedGatewayActivityId(activity);
            if (null != gatewayEntry) {
                String newGatewayId = gatewayEntry.getId();

                Discriminator discriminator =
                        (Discriminator) Xpdl2ModelUtil.getOtherElement(route,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_Discriminator());
                StructuredDiscriminator structuredDiscriminator =
                        discriminator.getStructuredDiscriminator();

                if (currGatewayId == null
                        || !currGatewayId.equals(newGatewayId)) {
                    cmd
                            .setLabel(Messages.DiscriminatorPatternSection_SetSplitNameCommand);
                    cmd
                            .append(SetCommand
                                    .create(ed,
                                            structuredDiscriminator,
                                            XpdExtensionPackage.eINSTANCE
                                                    .getStructuredDiscriminator_UpStreamParallelSplit(),
                                            newGatewayId));
                }

                if (cmd != null && cmd.canExecute()) {
                    ed.getCommandStack().execute(cmd);
                }
            }
        }
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractTransactionalSection#shouldRefresh(java.util.List)
     * 
     * @param notifications
     * @return
     */
    @Override
    protected boolean shouldRefresh(List<Notification> notifications) {
        if (super.shouldRefresh(notifications)) {
            EObject input = getInput();

            if (input != null && notifications != null
                    && !notifications.isEmpty()) {
                if (input instanceof Activity) {
                    if (((Activity) input).getRoute() instanceof Route) {

                        return true;
                    }
                }
            }
        }
        return false;
    }
}
