package com.tibco.xpd.analyst.resources.xpdl2.wizards.newtypedeclaration;

import org.eclipse.core.runtime.Assert;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.ecore.EObject;
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
import com.tibco.xpd.xpdl2.BasicType;
import com.tibco.xpd.xpdl2.BasicTypeType;
import com.tibco.xpd.xpdl2.Length;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.TypeDeclaration;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * New Type Declaration wizard
 * 
 * @author njpatel
 */
public abstract class AbstractNewTypeDeclarationWizard extends CreationWizard
        implements INewWizard, TemplateFactory {

    // Package selection page
    private AbstractSpecialFolderFileSelectionPage selectProjectPage;

    private String[] pageTitles = new String[] {
            Messages.NewTypeDeclarationWizard_0,
            Messages.NewTypeDeclarationWizard_1 };

    private String[] pageDescriptions = new String[] {
            Messages.NewTypeDeclarationWizard_2,
            Messages.NewTypeDeclarationWizard_3 };

    private IWorkbench workbench;

    /**
     * New Type Declaration wizard
     */
    public AbstractNewTypeDeclarationWizard() {
        selectProjectPage = getFileSelectionPage();
        init(this, selectProjectPage);
        setWindowTitle(Messages.NewTypeDeclarationWizard_TITLE);

        setDefaultPageImageDescriptor(ExtendedImageRegistry.INSTANCE
                .getImageDescriptor(Xpdl2ResourcesPlugin.getDefault()
                        .getImageRegistry()
                        .get(Xpdl2ResourcesConsts.WIZARD_NEW_TYPEDECLARATION)));
    }

    protected abstract EObject getEContainer(
            AbstractSpecialFolderFileSelectionPage locationPage);

    protected abstract AbstractSpecialFolderFileSelectionPage getFileSelectionPage();

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.ui.properties.CreationWizard#performFinish()
     */
    @Override
    public boolean performFinish() {
        EObject container = getEContainer(selectProjectPage);

        if (container != null) {
            WorkingCopy wc = WorkingCopyUtil.getWorkingCopyFor(container);

            if (wc != null) {
                Command cmd = getCommand();
                wc.getEditingDomain().getCommandStack().execute(cmd);

                // Select the new type declaration in the Project Explorer
                if (input != null) {
                    selectAndReveal(input);
                }

                return true;
            }
        }

        return false;
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
        selectProjectPage.init(selection);
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

        if (container != null) {
            WorkingCopy wc = WorkingCopyUtil.getWorkingCopyFor(container);

            if (wc != null) {

                CompoundCommand cmd = new CompoundCommand();
                cmd.setLabel(Messages.NewTypeDeclarationWizard_AddTypeDecl_menu);
                cmd.append(AddCommand.create(wc.getEditingDomain(),
                        wc.getRootElement(),
                        Xpdl2Package.eINSTANCE.getPackage_TypeDeclarations(),
                        input));
                return cmd;
            }
        }

        return UnexecutableCommand.INSTANCE;
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
        TypeDeclaration input = fact.createTypeDeclaration();

        input.setName(NameUtil
                .getInternalName(Messages.NewTypeDeclarationWizard_5, true));

        Xpdl2ModelUtil.setOtherAttribute(input,
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_DisplayName(),
                Messages.NewTypeDeclarationWizard_5);

        BasicType basicType = fact.createBasicType();
        basicType.setType(BasicTypeType.STRING_LITERAL);

        Length len = Xpdl2Factory.eINSTANCE.createLength();
        len.setValue("50"); //$NON-NLS-1$
        basicType.setLength(len);

        input.setBasicType(basicType);

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
        return getEContainer(selectProjectPage);
    }

    private ModifyListener packageFileNameListener = new ModifyListener() {

        @Override
        public void modifyText(ModifyEvent e) {
            if (e.getSource() instanceof Text) {
                if (getInputContainer() != null
                        && input instanceof TypeDeclaration) {
                    TypeDeclaration typeDeclaration = (TypeDeclaration) input;
                    String baseName =
                            Messages.NewTypeDeclarationWizard_TypeDeclarationName_value;
                    String finalName = baseName;
                    int idx = 1;
                    while (Xpdl2ModelUtil
                            .getDuplicateDisplayTypeDeclaration((Package) getInputContainer(),
                                    typeDeclaration,
                                    finalName) != null
                            || Xpdl2ModelUtil
                                    .getDuplicateTypeDeclaration((Package) getInputContainer(),
                                            typeDeclaration,
                                            NameUtil.getInternalName(finalName,
                                                    true)) != null) {
                        idx++;
                        finalName = baseName + idx;
                    }
                    Xpdl2ModelUtil.setOtherAttribute(typeDeclaration,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_DisplayName(),
                            finalName);
                    typeDeclaration.setName(NameUtil.getInternalName(finalName,
                            true));
                }
            }
        }
    };

    @Override
    public void createPageControls(Composite pageContainer) {
        // Create the page controls
        for (IWizardPage page : getPages()) {
            page.createControl(pageContainer);

            if (page == selectProjectPage) {
                selectProjectPage
                        .addPacakgeFileModifyListeners(packageFileNameListener);
            }
            // Page is responsible for ensuring the created control is
            // accessable via getControl.
            Assert.isNotNull(page.getControl());
        }
    }

}
