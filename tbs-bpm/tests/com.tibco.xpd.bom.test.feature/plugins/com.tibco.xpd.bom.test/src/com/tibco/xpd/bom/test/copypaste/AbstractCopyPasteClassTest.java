/**
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.bom.test.copypaste;

import java.io.InputStream;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.undo.CreateFileOperation;
import org.eclipse.ui.ide.undo.WorkspaceUndoUtil;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.ProfileApplication;
import org.eclipse.uml2.uml.Stereotype;

import com.tibco.xpd.bom.test.util.BOMTestUtils;
import com.tibco.xpd.core.test.util.TestUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;

/**
 * Tests the copy of a class with an applied Stereotype from one model file to
 * another.
 * 
 * @author rassisi
 * 
 */
abstract public class AbstractCopyPasteClassTest extends AbstractCopyPasteTest {

    private static final String MY_VALUE = "myValue";

    private Class c1;

    private Profile profile;

    private Stereotype stereotype;

    private final String profileName = "monstersProfile2"; // Profile name

    /**
     * Create one Class. Make sure, that it has all view elements.
     * 
     * @see com.tibco.xpd.bom.test.copypaste.AbstractCopyPasteTest#createSourceElements()
     */
    @Override
    protected void createSourceElements() {

        // ---------- create a class -------------------------------------------

        try {
            c1 =
                    BOMTestUtils.createClasses((Package) sourceWorkingCopy
                            .getRootElement(), 1)[0];
            sourceElements.add(c1);
        } catch (Exception e) {
            fail("Failed to create the Class.");
        }
        TestUtil.waitForJobs();
        c1 = (Class) sourceElements.get(0);
    }

    /**
     * Apply a Stereotype to the source Class which is derived from a Profile
     * which is loaded from a file.
     * 
     */
    @Override
    protected void treatSource() {

        // ---------- import an UML Profile model ------------------------------

        final IFile file =
                conceptsSourceFolder.getFolder().getFile("test.profile.uml");

        final InputStream inStream =
                getClass().getClassLoader().getResourceAsStream(MODEL_FILE);
        assertNotNull(String.format("Failed to access %s", MODEL_FILE),
                inStream);

        CreateFileOperation createOp =
                new CreateFileOperation(file, null, inStream,
                        "Create Profile UML");

        try {
            PlatformUI
                    .getWorkbench()
                    .getOperationSupport()
                    .getOperationHistory()
                    .execute(createOp,
                            new NullProgressMonitor(),
                            WorkspaceUndoUtil.getUIInfoAdapter(Display
                                    .getDefault().getActiveShell()));
        } catch (ExecutionException e) {
            fail("File could not be created.");
        }

        TestUtil.waitForJobs();
        assertTrue("File not created.", file.exists());

        // ---------- Apply a Stereotype to the class --------------------------

        WorkingCopy profileWC =
                XpdResourcesPlugin.getDefault().getWorkingCopy(file);
        assertNotNull("Could not obtain profile working copy", profileWC);
        assertNotNull("Profile is invalid", profileWC.getRootElement());
        profile = (Profile) profileWC.getRootElement();

        stereotype = profile.getOwnedStereotype("Cyberman2");

        Command cmd1 =
                new RecordingCommand((TransactionalEditingDomain) editingDomain) {
                    @Override
                    protected void doExecute() {
                        Package pk = c1.getPackage();
                        pk.applyProfile(profile);
                        EObject application = c1.applyStereotype(stereotype);
                        EList<EAttribute> attributes =
                                application.eClass().getEAttributes();
                        application.eSet(attributes.get(0), MY_VALUE);
                    }
                };

        if (cmd1.canExecute()) {
            editingDomain.getCommandStack().execute(cmd1);
        } else {
            fail("Stereotype could not be applied to the class");
        }

        // ----------- Apply a Profile to target profile in order to avoid a UI
        // message -----
        Command cmd2 =
                new RecordingCommand((TransactionalEditingDomain) editingDomain) {
                    @Override
                    protected void doExecute() {
                        Model pk = (Model) targetWorkingCopy.getRootElement();
                        pk.applyProfile(profile);
                    }
                };

        if (cmd2.canExecute()) {
            editingDomain.getCommandStack().execute(cmd2);
        } else {
            fail("Profiel could not be applied to the target model");
        }

        TestUtil.waitForJobs();
    }

    @Override
    protected void treatTarget() {
    }

    /*
     * Compare the original Class with the Copy. Check, that all View attributes
     * are equal, check that the Stereotype with a set Property was copied
     * properly.
     * 
     * @see com.tibco.xpd.bom.test.copypaste.AbstractCopyPasteTest#validate()
     */
    @Override
    protected void validateCopies() {

        EObject root2 = targetWorkingCopy.getRootElement();
        EList<EObject> copiedContent = root2.eContents();

        assertEquals("The Number of the copied Elements is incorrect.",
                4,
                copiedContent.size());

        EObject copiedEObject = null;
        boolean isProfileApplied = false;
        for (EObject eCopiedContent : copiedContent) {
            if (eCopiedContent instanceof ProfileApplication) {
                ProfileApplication pA = (ProfileApplication) eCopiedContent;
                if (pA.getAppliedProfile().getName().equals(profileName)) {
                    isProfileApplied = true;
                }

            } else if (eCopiedContent instanceof Class) {
                copiedEObject = eCopiedContent;
            }

        }
        if (!isProfileApplied) {
            assertFalse("The Profile was not applied to the target model", true);
        }

        if (copiedEObject instanceof Class) {
            BOMTestUtils.validateNamesAndViews(editingDomain,
                    (Classifier) c1,
                    (Classifier) copiedEObject);

            EList<EObject> steros2 =
                    ((Class) copiedEObject).getStereotypeApplications();
            assertEquals("Invalid number of stereotype application after paste",
                    2,
                    steros2.size());

            boolean isStereoAttribute = false;
            for (EObject stereotype : steros2) {
                if (stereotype.eClass().getName().equals("Cyberman2")) {
                    isStereoAttribute = true;
                    assertEquals("Stereotype application attributes were not copied properly",
                            MY_VALUE,
                            stereotype.eGet(stereotype.eClass()
                                    .getEAllAttributes().get(0)));
                }
            }
            if (!isStereoAttribute) {
                assertFalse("Stereotype application attributes were not copied properly",
                        true);
            }

            saveTargetModel();

            return;
        }

        fail("The Class was not copied.");
    }

    @Override
    protected EObject getCopyContext() {
        return sourceElements.get(0);
    }

}
