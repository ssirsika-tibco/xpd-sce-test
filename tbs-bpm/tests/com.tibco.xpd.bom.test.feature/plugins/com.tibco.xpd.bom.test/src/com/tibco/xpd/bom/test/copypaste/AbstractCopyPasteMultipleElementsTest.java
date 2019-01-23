/**
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.bom.test.copypaste;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Namespace;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.ProfileApplication;

import com.tibco.xpd.bom.test.util.BOMTestUtils;

/**
 * Tests the copy of a class with an applied Stereotype from one model file to
 * another.
 * 
 * @author rassisi
 * 
 */
abstract public class AbstractCopyPasteMultipleElementsTest extends
        AbstractCopyPasteTest {
    // /**
    // * @param copyContext
    // */
    // @Override
    // protected void copy() {
    // EObject context = getCopyContext();
    // if (context instanceof Class) {
    // context = ((Class) context).getPackage();
    // }
    // ICommand command = BOMCopyPasteCommandFactory.getInstance()
    // .createCopyCommand((TransactionalEditingDomain) editingDomain,
    // context, sourceElements);
    // IOperationHistory stack = PlatformUI.getWorkbench()
    // .getOperationSupport().getOperationHistory();
    // assertTrue("Copy Command could not be executed", command.canExecute());
    // try {
    // stack.execute(command, new NullProgressMonitor(), null);
    // } catch (ExecutionException e) {
    // fail("Execution of the copy command failed.");
    // }
    // }

    /**
     * Apply a Stereotype to the source Class which is derived from a Profile
     * which is loaded from a file.
     * 
     */
    @Override
    protected void treatSource() {
        saveSourceModel();
    }

    /**
     * Apply a Profile to the target model.
     * 
     */
    @Override
    protected void treatTarget() {
        saveTargetModel();
    }

    /**
     * Compare the original Class with the Copy. Check, that all View attributes
     * are equal, check that the Stereotype with a set Property was copied
     * properly.
     * 
     * @see com.tibco.xpd.bom.test.copypaste.AbstractCopyPasteTest#validateCopies()
     * 
     */
    @Override
    protected void validateCopies() {

        for (EObject sElement : sourceElements) {
            if (!(sElement instanceof EAnnotation)) {

                EObject tElement = getMatchingTargetObject(sElement);

                if (tElement != null) {

                    if (!(tElement instanceof ProfileApplication)) {
                        if (tElement instanceof Namespace) {
                            BOMTestUtils.validateNamesAndViews(editingDomain,
                                    (Namespace) sElement,
                                    (Namespace) tElement);
                        }

                        if (tElement instanceof Element) {
                            checkStereoTypes(sElement, tElement);
                        }
                    }
                } else {
                    assertFalse("Could not find the Target(Target did not get copied)",
                            true);
                }

            }

        }

    }

    /**
     * Checks if the source element is present in the target element, if not
     * then returns null.
     * 
     * @param sElement
     * @return
     */
    private EObject getMatchingTargetObject(EObject sElement) {
        EObject root2 = targetWorkingCopy.getRootElement();
        EList<EObject> copiedContent = root2.eContents();

        for (EObject cContent : copiedContent) {

            if (!(cContent instanceof EAnnotation)) {
                if (sElement instanceof Class && cContent instanceof Class) {

                    Class sEClass = (Class) sElement;
                    Class cEClass = (Class) cContent;
                    if (sEClass.getName().equals(cEClass.getName())) {
                        return cContent;
                    }

                } else if (sElement instanceof PrimitiveType
                        && cContent instanceof PrimitiveType) {

                    PrimitiveType sPrimitive = (PrimitiveType) sElement;
                    PrimitiveType cPrimitive = (PrimitiveType) cContent;
                    if (sPrimitive.getName().equals(cPrimitive.getName())) {
                        return cContent;
                    }

                } else if (sElement instanceof ProfileApplication
                        && cContent instanceof ProfileApplication) {

                    ProfileApplication sProfile = (ProfileApplication) sElement;
                    if (!sProfile.getAppliedProfile().getName()
                            .equals("PrimitiveTypeFacets")) {
                        ProfileApplication cProfile =
                                (ProfileApplication) cContent;
                        if (!sProfile.getAppliedProfile().getName()
                                .equals(cProfile.getAppliedProfile().getName())) {
                            assertFalse("The Profile application is not applied correctly.",
                                    true);
                        }
                    }
                    return cContent;

                }

                else if (sElement instanceof Package) {
                    if (cContent instanceof Package) {
                        Package sPackage = (Package) sElement;
                        Package cPackage = (Package) cContent;
                        if (sPackage.getName().equals(cPackage.getName())) {
                            return cContent;
                        }

                    }
                }

            }
        }
        return null;
    }

    /**
     * Checks if the Element StereoTypes match.
     * 
     * @param sElement
     * @param tElement
     */
    private void checkStereoTypes(EObject sElement, EObject tElement) {

        EList<EObject> steros1 =
                ((Element) sElement).getStereotypeApplications();

        EList<EObject> steros2 =
                ((Element) tElement).getStereotypeApplications();

        assertEquals("Invalid number of stereotype application",
                steros1.size(),
                steros2.size());

        int index = 0;

        for (EObject stereotype : steros1) {
            EObject stereotype2 = steros1.get(index++);

            assertEquals("StereoType names do not match.", stereotype.eClass()
                    .getName(), stereotype2.eClass().getName());

            assertEquals("Stereotype application attributes were not copied properly",
                    stereotype.eGet(stereotype.eClass().getEAllAttributes()
                            .get(0)),
                    stereotype2.eGet(stereotype.eClass().getEAllAttributes()
                            .get(0)));

        }

    }

    @Override
    protected EObject getCopyContext() {
        return sourceElements.get(0);
    }

}
