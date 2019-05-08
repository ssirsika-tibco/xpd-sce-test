package com.tibco.xpd.analyst.resources.xpdl2.wizards.newparticipant;

import org.eclipse.core.runtime.Assert;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.ui.provider.ExtendedImageRegistry;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesConsts;
import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesPlugin;
import com.tibco.xpd.analyst.resources.xpdl2.internal.Messages;
import com.tibco.xpd.analyst.resources.xpdl2.wizards.pages.AbstractSpecialFolderFileSelectionPage;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.ui.properties.CreationWizard;
import com.tibco.xpd.ui.properties.CreationWizard.TemplateFactory;
import com.tibco.xpd.ui.util.NameUtil;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.ParticipantType;
import com.tibco.xpd.xpdl2.ParticipantTypeElem;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * New Participant wizard
 * <p>
 * Abstracted from NewParticipantWizard so that subclass can override
 * destination container according to the selected one (i.e. Task Library can
 * force participants to be added to the first process because that represents
 * the library.
 * 
 * @author njpatel
 */
public abstract class AbstractNewParticipantWizard extends CreationWizard
        implements INewWizard, TemplateFactory {

    private AbstractSpecialFolderFileSelectionPage containerSelectionPage;

    private String[] pageTitles = { Messages.NewParticipantWizard_PAGETITLE };

    private String[] pageDescriptions =
            { Messages.NewParticipantWizard_PAGEMESSAGE };

    private IWorkbench workbench;

    /**
     * New Participant wizard
     */
    public AbstractNewParticipantWizard() {
        containerSelectionPage = getFileSelectionPage();

        init(this, containerSelectionPage);
        setWindowTitle(Messages.NewParticipantWizard_TITLE);
        setDefaultPageImageDescriptor(ExtendedImageRegistry.INSTANCE
                .getImageDescriptor(Xpdl2ResourcesPlugin.getDefault()
                        .getImageRegistry()
                        .get(Xpdl2ResourcesConsts.WIZARD_NEW_PARTICIPANT)));
    }

    /**
     * @param locationPage
     * @return The Eobject that is to contain the new object.
     */
    protected abstract EObject getEContainer(
            AbstractSpecialFolderFileSelectionPage locationPage);

    protected abstract AbstractSpecialFolderFileSelectionPage getFileSelectionPage();

    protected abstract void addExtraContainerPageListeners(
            AbstractSpecialFolderFileSelectionPage containerSelectionPage);

    /**
     * @return the txtModifyListener
     */
    protected ModifyListener getTxtModifyListener() {
        return txtModifyListener;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.ui.properties.CreationWizard#performFinish()
     */
    @Override
    public boolean performFinish() {
        EObject container = getEContainer(containerSelectionPage);
        if (container == null) {
            return false;
        }
        WorkingCopy wc = WorkingCopyUtil.getWorkingCopyFor(container);
        Command cmd = getCommand();
        wc.getEditingDomain().getCommandStack().execute(cmd);

        // Select the new participant in the Project Explorer
        if (input != null) {
            selectAndReveal(input);
        }
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.IWorkbenchWizard#init(org.eclipse.ui.IWorkbench,
     * org.eclipse.jface.viewers.IStructuredSelection)
     */
    @Override
    public void init(IWorkbench workbench, IStructuredSelection selection) {
        this.workbench = workbench;
        containerSelectionPage.init(selection);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.ui.properties.CreationWizard#getPageDescription(int)
     */
    @Override
    public String getPageDescription(int index) {
        if (index > -1 && index < pageDescriptions.length) {
            return pageDescriptions[index];
        } else {
            return null;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.ui.properties.CreationWizard#getPageTitle(int)
     */
    @Override
    public String getPageTitle(int index) {
        if (index > -1 && index < pageTitles.length) {
            return pageTitles[index];
        } else {
            return null;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.ui.properties.CreationWizard.TemplateFactory#createCommand
     * (org.eclipse.emf.ecore.EObject, org.eclipse.jface.wizard.IWizardPage)
     */
    @Override
    public Command createCommand(EObject input, IWizardPage locationPage) {
        AbstractSpecialFolderFileSelectionPage spp =
                (AbstractSpecialFolderFileSelectionPage) locationPage;
        EObject container = getEContainer(spp);
        if (container == null) {
            return UnexecutableCommand.INSTANCE;
        }
        WorkingCopy wc = WorkingCopyUtil.getWorkingCopyFor(container);
        CompoundCommand cmd = new CompoundCommand();
        cmd.setLabel(Messages.NewParticipantWizard_AddParticipant_menu);

        /*
         * PropertySection contribution filter needs to be able to distinguish
         * between new participant for decision flow file and new participant
         * for normal xpdl.
         * 
         * The filter usually works on whether the participant is in a dflow
         * xpdl model but new participants are in the model yet!.
         * 
         * So we set a known predefined ID on the participant here so that the
         * filter can detect that and filter in participants with this ID..
         * 
         * Then just before we create the command we will set it to something
         * unique again.
         */
        ((Participant) input).eSet(Xpdl2Package.eINSTANCE
                .getUniqueIdElement_Id(), EcoreUtil.generateUUID());

        cmd.append(AddCommand.create(wc.getEditingDomain(),
                container,
                Xpdl2Package.eINSTANCE.getParticipantsContainer_Participants(),
                input));
        return cmd;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.ui.properties.CreationWizard.TemplateFactory#createTemplate
     * ()
     */
    @Override
    public EObject createTemplate() {
        Xpdl2Factory fact = Xpdl2Factory.eINSTANCE;
        Participant input = fact.createParticipant();
        input.setName(Messages.NewParticipantWizard_3);
        Xpdl2ModelUtil.setOtherAttribute(input,
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_DisplayName(),
                Messages.NewParticipantWizard_3);

        /*
         * Sid ACE-484 Default to Org Model Query instead of Role type (as the
         * latter isn't supported in ACE).
         */

        ParticipantTypeElem typeElem = fact.createParticipantTypeElem();
        typeElem.setType(ParticipantType.RESOURCE_SET_LITERAL);

        input.setParticipantType(typeElem);

        return input;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.ui.properties.CreationWizard#getWorkbench()
     */
    @Override
    public IWorkbench getWorkbench() {
        return workbench;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.ui.properties.XpdSection.XpdSectionContainer#getInputContainer
     * ()
     */
    @Override
    public EObject getInputContainer() {
        return getEContainer(containerSelectionPage);
    }

    private ModifyListener txtModifyListener = new ModifyListener() {

        @Override
        public void modifyText(ModifyEvent e) {
            if (e.getSource() instanceof Text) {
                if (getInputContainer() != null && input instanceof Participant) {
                    Participant participant = (Participant) input;
                    String baseName =
                            Messages.NewParticipantWizard_ParticipantName_value;
                    String finalName = baseName;
                    int idx = 1;
                    while (Xpdl2ModelUtil
                            .getDuplicateDisplayParticipant(getInputContainer(),
                                    participant,
                                    finalName) != null
                            || Xpdl2ModelUtil
                                    .getDuplicateParticipant(getInputContainer(),
                                            participant,
                                            NameUtil.getInternalName(finalName,
                                                    false)) != null) {
                        idx++;
                        finalName = baseName + idx;
                    }
                    participant.setName(NameUtil.getInternalName(finalName,
                            false));
                    Xpdl2ModelUtil.setOtherAttribute(participant,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_DisplayName(),
                            finalName);
                }
            }
        }
    };

    @Override
    public void createPageControls(Composite pageContainer) {
        // Create the page controls
        for (IWizardPage page : getPages()) {
            page.createControl(pageContainer);

            if (page == containerSelectionPage) {
                containerSelectionPage
                        .addPacakgeFileModifyListeners(txtModifyListener);

                addExtraContainerPageListeners(containerSelectionPage);
            }

            // Page is responsible for ensuring the created control is
            // accessable via getControl.
            Assert.isNotNull(page.getControl());
        }
    }
}
