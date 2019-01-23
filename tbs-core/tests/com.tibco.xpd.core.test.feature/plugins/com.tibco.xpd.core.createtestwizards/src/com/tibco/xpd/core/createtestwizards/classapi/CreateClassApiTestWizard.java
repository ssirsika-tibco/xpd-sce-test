/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.core.createtestwizards.classapi;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.swt.widgets.Composite;

import com.tibco.xpd.core.createtestwizards.CreateBaseTestWizard;
import com.tibco.xpd.core.createtestwizards.CreateTestWizardsConstants;
import com.tibco.xpd.core.createtestwizards.CreateTestWizardsPlugin;
import com.tibco.xpd.core.createtestwizards.generatordata.ApiClassTestJavaClassGeneratorData;
import com.tibco.xpd.core.createtestwizards.generators.ApiClassTestJavaClassGenerator;
import com.tibco.xpd.core.test.util.classapi.AbstractApiClassTest;

/**
 * CreateClassApiTestWizard
 * 
 * 
 * @author aallway
 * @since 3.3 (13 Oct 2009)
 */
public class CreateClassApiTestWizard extends CreateBaseTestWizard {

    private IProject javaProject;

    private IFolder javaPackageFolder;

    private CreateClassApiTestPage classApiTestPage;

    /**
     * @param selectedStudioResources
     */
    public CreateClassApiTestWizard(IProject javaProject,
            IFolder javaPackageFolder) {
        super(new ArrayList<IResource>());

        this.javaProject = javaProject;
        this.javaPackageFolder = javaPackageFolder;

        setWindowTitle(Messages.CreateClassApiTestWizard_CreateClassApiTest_title);

        setDefaultPageImageDescriptor(CreateTestWizardsPlugin
                .getImageDescriptor(CreateTestWizardsConstants.IMG_CREATECLASSAPITEST_WIZARD));
    }

    @Override
    public void addPages() {
        super.addPages();

        classApiTestPage = new CreateClassApiTestPage();

        addPage(classApiTestPage);

        return;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.jface.wizard.Wizard#createPageControls(org.eclipse.swt.widgets
     * .Composite)
     */
    @Override
    public void createPageControls(Composite pageContainer) {
        super.createPageControls(pageContainer);

        /* Preset and disable target project / java package selection. */
        baseTestPage.setTestProject(javaProject, true);
        baseTestPage.setTestPackageFolder(javaPackageFolder, true);
        baseTestPage.setTestRelativeBaseResourceFolder(null, true);

        return;
    }

    @Override
    protected String generateTestClassContent(String testName,
            String testClassName, String testPackageId, String testPluginId,
            IPath baseTestPluginResourcePath,
            Collection<String> testResourceInfoPaths) {

        List<Class<?>> apiClasses = new ArrayList<Class<?>>();

        getClassAndNestedClass(classApiTestPage.getSelectedClassOrPackage(),
                apiClasses);

        ApiClassTestJavaClassGeneratorData data =
                new ApiClassTestJavaClassGeneratorData(testName, testClassName,
                        testPluginId, testPackageId, classApiTestPage
                                .getApiClassParentPluginId(), apiClasses
                                .toArray(new Class[0]));

        ApiClassTestJavaClassGenerator generator =
                new ApiClassTestJavaClassGenerator();

        String testClassContent = generator.generate(data);

        return testClassContent;
    }

    /**
     * @param selectedClassOrPackage
     * @param apiClasses
     * @return
     */
    private void getClassAndNestedClass(Object selectedClassOrPackage,
            List<Class<?>> apiClasses) {
        if (selectedClassOrPackage instanceof Class) {
            Class<?> clazz = (Class<?>) selectedClassOrPackage;

            apiClasses.add(clazz);

            Set<Class<?>> nested =
                    AbstractApiClassTest.getApiNestedClasses(clazz);

            apiClasses.addAll(nested);
        }

        return;
    }

}
