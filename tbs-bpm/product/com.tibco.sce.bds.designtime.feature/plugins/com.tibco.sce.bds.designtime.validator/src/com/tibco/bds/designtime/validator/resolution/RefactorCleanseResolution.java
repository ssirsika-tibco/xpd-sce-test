package com.tibco.bds.designtime.validator.resolution;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.ltk.ui.refactoring.RefactoringWizardOpenOperation;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.EnumerationLiteral;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Property;

import com.tibco.bds.designtime.validator.internal.Messages;
import com.tibco.xpd.bom.resources.ui.refactoring.RenameBOMElementWizard;
import com.tibco.xpd.bom.types.api.BOMEntityNameCleanser;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdProjectResourceFactory;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.validation.resolutions.IResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;

/**
 * Provides a name cleansing resolution that cleanses the names of various BOM
 * entities to adhere to CDS requirements.
 * 
 * @author smorgan
 * 
 */
public class RefactorCleanseResolution implements IResolution {

    private final BOMEntityNameCleanser cleanser =
            BOMEntityNameCleanser.getInstance();

    /**
     * @param marker
     *            The problem marker.
     * @throws ResolutionException
     *             If there was a problem during resolution.
     * @see com.tibco.xpd.validation.resolutions.IResolution#run(org.eclipse.core.resources.IMarker)
     */
    public void run(IMarker marker) throws ResolutionException {
        // Marker may not exist if the previous resolution resolved this problem
        // too
        if (marker != null && marker.exists()) {
            try {
                EObject target = getTarget(marker);

                EditingDomain domain = null;
                if (target != null) {
                    domain = WorkingCopyUtil.getEditingDomain(target);
                }

                if (domain != null) {
                    String newName = null;
                    NamedElement namedElement = null;
                    if (target instanceof EnumerationLiteral) {
                        namedElement = (NamedElement) target;
                        newName =
                                cleanser
                                        .cleanseAndMakeUniqueEnumerationLiteralName((EnumerationLiteral) namedElement,
                                                namedElement.getName());
                    } else if (target instanceof Enumeration) {
                        namedElement = (NamedElement) target;
                        newName =
                                cleanser
                                        .cleanseAndMakeUniqueEnumerationName((Enumeration) namedElement,
                                                namedElement.getName(),
                                                true);
                    } else if (target instanceof org.eclipse.uml2.uml.Class) {
                        namedElement = (NamedElement) target;
                        newName =
                                cleanser
                                        .cleanseAndMakeUniqueClassName((org.eclipse.uml2.uml.Class) namedElement,
                                                namedElement.getName(),
                                                true);
                    } else if (target instanceof PrimitiveType) {
                        namedElement = (NamedElement) target;
                        newName =
                                cleanser
                                        .cleanseAndMakeUniquePrimitiveTypeName((PrimitiveType) namedElement,
                                                namedElement.getName(),
                                                true);
                    } else if (target instanceof Property) {
                        namedElement = (NamedElement) target;
                        newName =
                                cleanser
                                        .cleanseAndMakeUniqueAttributeName((Property) namedElement,
                                                namedElement.getName(),
                                                true);
                    } else if (target instanceof org.eclipse.uml2.uml.Package) {
                        namedElement = (NamedElement) target;
                        newName =
                                cleanser
                                        .cleanseAndMakeUniquePackageName((org.eclipse.uml2.uml.Package) namedElement,
                                                namedElement.getName(),
                                                true);
                    }
                    if (newName != null && namedElement != null) {
                        RenameBOMElementWizard refactoringWizard =
                                new RenameBOMElementWizard(namedElement,
                                        newName);
                        RefactoringWizardOpenOperation op =
                                new RefactoringWizardOpenOperation(
                                        refactoringWizard);
                        try {
                            Shell activeShell =
                                    Display.getDefault().getActiveShell();
                            op
                                    .run(activeShell,
                                            Messages.RefactorModelResolution_RenameElement);
                        } catch (InterruptedException e) {
                            // do nothing
                        }
                    }
                }
                // originally threw a ResolutionException here if editingDomain
                // was null - however
                // in some cases this was null and it was found better to ignore
                // rather than throw exception
            } catch (CoreException e) {
                throw new ResolutionException(e);
            }
        }
    }

    /**
     * Get the target EObject of the given marker.
     * 
     * @param marker
     * @return
     * @throws CoreException
     * 
     * @since 3.3
     */
    protected EObject getTarget(IMarker marker) throws CoreException {
        XpdProjectResourceFactory factory =
                XpdResourcesPlugin.getDefault()
                        .getXpdProjectResourceFactory(marker.getResource()
                                .getProject());
        WorkingCopy workingCopy = factory.getWorkingCopy(marker.getResource());
        // Clear the uri cache
        workingCopy.clearUriCache();
        String location = (String) marker.getAttribute(IMarker.LOCATION);
        EObject rootObject = workingCopy.getRootElement();
        Resource resource = null;
        if (rootObject != null) {
            resource = rootObject.eResource();
        }
        EObject target = null;
        if (resource != null) {
            target = workingCopy.resolveEObject(location);
        }
        return target;
    }

}
