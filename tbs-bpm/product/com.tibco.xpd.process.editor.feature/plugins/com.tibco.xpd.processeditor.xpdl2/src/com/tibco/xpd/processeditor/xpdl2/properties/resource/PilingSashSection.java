package com.tibco.xpd.processeditor.xpdl2.properties.resource;

import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.properties.SashSection;
import com.tibco.xpd.ui.properties.AbstractFilteredTransactionalSection;
import com.tibco.xpd.ui.properties.TextFieldVerifier;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.xpdExtension.ActivityResourcePatterns;
import com.tibco.xpd.xpdExtension.PilingInfo;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

public class PilingSashSection extends AbstractFilteredTransactionalSection
        implements SashSection, TextFieldVerifier {

    private Button allowPilingCheckbox;

    private Text maxItemsToPileText;

    public PilingSashSection() {
        super(Xpdl2Package.eINSTANCE.getActivity());
    }

    /**
     * @param toTest
     * @return
     * @see org.eclipse.jface.viewers.IFilter#select(java.lang.Object)
     */
    @Override
    public boolean select(Object toTest) {
        boolean ok = false;
        if (toTest instanceof Activity) {
            Activity activity = (Activity) toTest;
            Implementation impl = activity.getImplementation();
            if (impl instanceof Task) {
                Task task = (Task) impl;
                if (task.getTaskUser() != null || task.getTaskManual() != null) {
                    ok = true;
                }
            }
        }
        return ok;
    }

    @Override
    protected Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {
        Composite composite = toolkit.createComposite(parent);
        composite.setLayout(new GridLayout(2, false));

        Label mayBePiledLabel =
                toolkit.createLabel(composite,
                        Messages.PilingSashSection_AllowPilingLabel,
                        SWT.WRAP);
        mayBePiledLabel
                .setToolTipText(Messages.PilingSashSection_AllowPilingLabel);

        allowPilingCheckbox = toolkit.createButton(composite, "", //$NON-NLS-1$
                SWT.CHECK,
                "allowPiling"); //$NON-NLS-1$ 

        Label maxItemsLabel =
                toolkit.createLabel(composite,
                        Messages.PilingSashSection_MaxItemsToPileLabel,
                        SWT.WRAP);
        maxItemsLabel
                .setToolTipText(Messages.PilingSashSection_MaxItemsToPile_Tooltip);

        maxItemsToPileText =
                toolkit.createText(composite, "", "textMaxItemsToPile"); //$NON-NLS-1$//$NON-NLS-2$
        maxItemsToPileText.setEnabled(false);

        GridData gData = new GridData(GridData.FILL_HORIZONTAL);
        maxItemsToPileText.setLayoutData(gData);

        manageControl(allowPilingCheckbox);
        manageControl(maxItemsToPileText);

        return composite;
    }

    @Override
    protected Command doGetCommand(Object obj) {
        Activity activity = null;

        EditingDomain ed = getEditingDomain();
        EObject input = getInput();

        CompoundCommand cmd = new CompoundCommand();

        if (input instanceof Activity) {
            activity = (Activity) input;

            if (obj == allowPilingCheckbox) {

                PilingInfo pilingInfo = getOrCreatePilingElement(activity, cmd);

                cmd.append(SetCommand.create(ed,
                        pilingInfo,
                        XpdExtensionPackage.eINSTANCE
                                .getPilingInfo_PilingAllowed(),
                        allowPilingCheckbox.getSelection()));

            } else if (obj == maxItemsToPileText) {
                /* Get or create the Piling data */
                PilingInfo pilingInfo = getOrCreatePilingElement(activity, cmd);

                cmd.append(SetCommand.create(ed,
                        pilingInfo,
                        XpdExtensionPackage.eINSTANCE
                                .getPilingInfo_MaxPiliableItems(),
                        maxItemsToPileText.getText()));

            }

        }

        if (!cmd.isEmpty()) {
            return cmd;
        }

        return null;
    }

    /**
     * Get the PilingInfo element for activity - create if not exists.
     * 
     * @param activity
     * @param cmd
     * @return PilingInfo
     */
    private PilingInfo getOrCreatePilingElement(Activity activity,
            CompoundCommand cmd) {

        ActivityResourcePatterns resourcePatterns =
                (ActivityResourcePatterns) Xpdl2ModelUtil
                        .getOtherElement(activity,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_ActivityResourcePatterns());
        if (resourcePatterns == null) {
            resourcePatterns =
                    XpdExtensionFactory.eINSTANCE
                            .createActivityResourcePatterns();
            cmd
                    .append(Xpdl2ModelUtil
                            .getSetOtherElementCommand(getEditingDomain(),
                                    activity,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_ActivityResourcePatterns(),
                                    resourcePatterns));
        }

        PilingInfo pilingInfo = resourcePatterns.getPiling();
        if (pilingInfo == null) {
            pilingInfo = XpdExtensionFactory.eINSTANCE.createPilingInfo();
            pilingInfo.setPilingAllowed(false);
            cmd.append(SetCommand.create(getEditingDomain(),
                    resourcePatterns,
                    XpdExtensionPackage.eINSTANCE
                            .getActivityResourcePatterns_Piling(),
                    pilingInfo));
        }

        return pilingInfo;
    }

    @Override
    protected void doRefresh() {
        if (getInput() instanceof Activity && allowPilingCheckbox != null
                && !allowPilingCheckbox.isDisposed()) {
            Activity activity = (Activity) getInput();

            boolean pilingAllowed = false;
            String maxPiledItems = null;

            ActivityResourcePatterns activityResourcePatterns =
                    (ActivityResourcePatterns) Xpdl2ModelUtil
                            .getOtherElement(activity,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_ActivityResourcePatterns());

            if (null != activityResourcePatterns) {
                PilingInfo pilingInfo = activityResourcePatterns.getPiling();
                if (pilingInfo != null) {
                    pilingAllowed = pilingInfo.isPilingAllowed();
                    maxPiledItems = pilingInfo.getMaxPiliableItems();
                }
            }

            allowPilingCheckbox.setSelection(pilingAllowed);
            updateText(maxItemsToPileText, maxPiledItems);

            maxItemsToPileText.setEnabled(pilingAllowed);
        }

        return;
    }

    public boolean shouldSashSectionRefresh(List<Notification> notifications) {
        return shouldRefresh(notifications);
    }

    public void verifyText(Event event) {
        Text textControl = ((Text) event.widget);
        if (textControl == maxItemsToPileText) {
            String t =
                    textControl.getText(0, event.start - 1)
                            + event.text
                            + textControl.getText(event.end, textControl
                                    .getCharCount() - 1);
            if ("".equals(t)) { //$NON-NLS-1$
                event.doit = true;
                return;
            }

            try {
                Short.valueOf(t);
                event.doit = true;
                return;
            } catch (NumberFormatException nfe) {
                event.doit = false;
                return;
            }
        }
    }

}
