/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.rest.schema.ui.test;

import java.lang.reflect.Field;
import java.util.function.Predicate;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Property;

import com.tibco.xpd.core.test.util.TestUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.util.SpecialFolderUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.rest.schema.ui.internal.wizard.ImportJsonSchemaWizard;
import com.tibco.xpd.rest.schema.ui.internal.wizard.JsonSchemaImportPage;
import com.tibco.xpd.rest.schema.ui.internal.wizard.NewJsonSchemaWizard;
import com.tibco.xpd.rest.ui.RestServicesUtil;
import com.tibco.xpd.ui.wizards.newproject.BasicNewXpdResourceWizard;

import junit.framework.TestCase;

/**
 * Functional test for JSON Schema editor.
 * 
 * @author nwilson
 * @since 30 Mar 2015
 */
public class JsonSchemaEditorTest extends TestCase {

    /**
     * Test plugin ID.
     */
    private static final String PLUGIN_ID = "com.tibco.xpd.rest.test"; //$NON-NLS-1$

    /**
     * Test creating a new JSON schema through the wizard.
     */
    public void testCreateSchema() {
        NewJsonSchemaWizard wizard = new NewJsonSchemaWizard();
        createWizard("createSchema", wizard); //$NON-NLS-1$
        assertTrue(wizard.canFinish());
        assertTrue(wizard.performFinish());
        IEditorPart editor = getActiveEditor();
        assertEquals("NewJsonSchema.jsd", editor.getTitle()); //$NON-NLS-1$
    }

    /**
     * Test importing an IETF JSON schema.
     */
    public void testImportJsonSchema() {
        ImportJsonSchemaWizard wizard = new ImportJsonSchemaWizard();
        String projectName = "importSchema"; //$NON-NLS-1$
        IProject project = ResourcesPlugin.getWorkspace().getRoot()
                .getProject(projectName);
        createWizard(projectName, wizard);
        SpecialFolder sf = SpecialFolderUtil.getSpecialFolderOfKind(project,
                RestServicesUtil.REST_SPECIAL_FOLDER_KIND);
        IFile file = TestUtil.createFileFromResource(PLUGIN_ID,
                sf,
                "resources/jsonSchema1.json"); //$NON-NLS-1$
        IWizardPage[] pages = wizard.getPages();
        assertEquals(1, pages.length);
        IWizardPage page = pages[0];
        assertTrue(page instanceof JsonSchemaImportPage);
        JsonSchemaImportPage importPage = (JsonSchemaImportPage) page;
        setRadioButton(importPage, "importJsonSchema"); //$NON-NLS-1$
        setText(importPage, "jsonSchemaFile", file.getLocation().toOSString()); //$NON-NLS-1$

        assertTrue(wizard.canFinish());
        assertTrue(wizard.performFinish());

        IFile resultfile = sf.getFolder().getFile("NewJsonSchema.jsd"); //$NON-NLS-1$
        boolean containsRootClassWithStorageProp =
                testJsonClass(resultfile, "root", (aClass) -> { //$NON-NLS-1$
                    return aClass.getOwnedAttributes().stream()
                            .anyMatch(p -> "storage".equals(p.getName())); //$NON-NLS-1$
                });

        assertTrue(containsRootClassWithStorageProp);
    }

    /**
     * Test importing a JSON sample file.
     */
    public void testImportJsonSchemaWithArraya() {
        ImportJsonSchemaWizard wizard = new ImportJsonSchemaWizard();
        String projectName = "importSchema"; //$NON-NLS-1$
        IProject project = ResourcesPlugin.getWorkspace().getRoot()
                .getProject(projectName);
        createWizard(projectName, wizard);
        SpecialFolder sf = SpecialFolderUtil.getSpecialFolderOfKind(project,
                RestServicesUtil.REST_SPECIAL_FOLDER_KIND);
        IFile file = TestUtil.createFileFromResource(PLUGIN_ID,
                sf,
                "resources/arrays.schema.json"); //$NON-NLS-1$
        IWizardPage[] pages = wizard.getPages();
        assertEquals(1, pages.length);
        IWizardPage page = pages[0];
        assertTrue(page instanceof JsonSchemaImportPage);
        JsonSchemaImportPage importPage = (JsonSchemaImportPage) page;
        setRadioButton(importPage, "importJsonSchema"); //$NON-NLS-1$
        setText(importPage, "jsonSchemaFile", file.getLocation().toOSString()); //$NON-NLS-1$
        importPage.setFileName("arrays.jsd"); //$NON-NLS-1$

        assertTrue(wizard.canFinish());
        assertTrue(wizard.performFinish());

        IFile resultfile = sf.getFolder().getFile("arrays.jsd"); //$NON-NLS-1$
        boolean vegetablesPropetyIsOfTypeVeggie =
                testJsonClass(resultfile, "root", (aClass) -> { //$NON-NLS-1$
                    Property vegetables = aClass.getOwnedAttributes().stream()
                            .filter(p -> "vegetables".equals(p.getName())) //$NON-NLS-1$
                            .findFirst().get();
                    Class vegetablesType = (Class) vegetables.getType();
                    return vegetablesType.getName().equals("veggie"); //$NON-NLS-1$
                });

        assertTrue(vegetablesPropetyIsOfTypeVeggie);
    }

    /**
     * Loads jsd file and checks predicate on a specific class element
     * (representing root or custom type).
     * 
     * @param jsdFile
     *            the jsd file to check.
     * @param className
     *            the name of the class ("root" or custom type name).
     * @param predicate
     *            the predicate to check.
     * @return result of the predicate check.
     */
    boolean testJsonClass(IFile jsdFile, String className,
            Predicate<Class> predicate) {
        WorkingCopy schemaWc = WorkingCopyUtil.getWorkingCopy(jsdFile);
        Object root = schemaWc.getRootElement();
        assertTrue(root instanceof org.eclipse.uml2.uml.Package);
        Class aClass = (Class) ((org.eclipse.uml2.uml.Package) root)
                .getPackagedElement(className);
        return predicate.test(aClass);
    }

    /**
     * Test importing a JSON sample file.
     */
    public void testImportJsonSample() {
        ImportJsonSchemaWizard wizard = new ImportJsonSchemaWizard();
        String projectName = "importSample"; //$NON-NLS-1$
        IProject project = ResourcesPlugin.getWorkspace().getRoot()
                .getProject(projectName);
        createWizard(projectName, wizard);
        SpecialFolder sf = SpecialFolderUtil.getSpecialFolderOfKind(project,
                RestServicesUtil.REST_SPECIAL_FOLDER_KIND);
        IFile file = TestUtil.createFileFromResource(PLUGIN_ID,
                sf,
                "resources/jsonSample1.json"); //$NON-NLS-1$
        IWizardPage[] pages = wizard.getPages();
        assertEquals(1, pages.length);
        IWizardPage page = pages[0];
        assertTrue(page instanceof JsonSchemaImportPage);
        JsonSchemaImportPage importPage = (JsonSchemaImportPage) page;
        setRadioButton(importPage, "importJsonSample"); //$NON-NLS-1$
        setText(importPage, "jsonSampleFile", file.getLocation().toOSString()); //$NON-NLS-1$

        assertTrue(wizard.canFinish());
        boolean finish = wizard.performFinish();
        assertNull(importPage.getErrorMessage());
        assertTrue(finish);
    }

    /**
     * Selects a radio button on a page.
     * 
     * @param importPage
     *            The import page.
     * @param radioName
     *            The radio button to check.
     */
    private void setRadioButton(JsonSchemaImportPage importPage,
            String fieldName) {
        try {
            Field radioField =
                    JsonSchemaImportPage.class.getDeclaredField(fieldName);
            radioField.setAccessible(true);
            Object radioValue = radioField.get(importPage);
            if (radioValue instanceof Button) {
                Button radioButton = (Button) radioValue;
                radioButton.setSelection(true);
            }
        } catch (NoSuchFieldException | SecurityException
                | IllegalArgumentException | IllegalAccessException e) {
            fail(e.getMessage());
        }
    }

    /**
     * Sets a text field value on a page.
     * 
     * @param importPage
     *            The import page.
     * @param fieldName
     *            The text field to set.
     * @param value
     *            The new value.
     */
    private void setText(JsonSchemaImportPage importPage, String fieldName,
            String value) {
        try {
            Field textField =
                    JsonSchemaImportPage.class.getDeclaredField(fieldName);
            textField.setAccessible(true);
            Object radioValue = textField.get(importPage);
            if (radioValue instanceof Text) {
                Text text = (Text) radioValue;
                text.setText(value);
            }
        } catch (NoSuchFieldException | SecurityException
                | IllegalArgumentException | IllegalAccessException e) {
            fail(e.getMessage());
        }
    }

    /**
     * @return The currently active editor.
     */
    private IEditorPart getActiveEditor() {
        IWorkbench workbench = PlatformUI.getWorkbench();
        assertNotNull(workbench);
        IWorkbenchWindow activeWorkbenchWindow =
                workbench.getActiveWorkbenchWindow();
        assertNotNull(activeWorkbenchWindow);
        IWorkbenchPage activePage = activeWorkbenchWindow.getActivePage();
        assertNotNull(activePage);
        IEditorPart editor = activePage.getActiveEditor();
        assertNotNull(editor);
        return editor;
    }

    /**
     * @param projectName
     *            The name of the project to create.
     * @param wizard
     *            The wizard to use.
     */
    private void createWizard(String projectName,
            BasicNewXpdResourceWizard wizard) {
        IProject project = TestUtil.createBPMProjectFromWizard(projectName,
                "com.tibco.xpd.rest.wizard.project.new"); //$NON-NLS-1$
        SpecialFolder sf = SpecialFolderUtil.getSpecialFolderOfKind(project,
                RestServicesUtil.REST_SPECIAL_FOLDER_KIND);
        IWorkbench workbench = PlatformUI.getWorkbench();
        assertNotNull(workbench);
        wizard.init(workbench, new StructuredSelection(sf));
        IWorkbenchWindow activeWorkbenchWindow =
                workbench.getActiveWorkbenchWindow();
        assertNotNull(activeWorkbenchWindow);
        WizardDialog dialog =
                new WizardDialog(activeWorkbenchWindow.getShell(), wizard);
        dialog.create();
    }
}
