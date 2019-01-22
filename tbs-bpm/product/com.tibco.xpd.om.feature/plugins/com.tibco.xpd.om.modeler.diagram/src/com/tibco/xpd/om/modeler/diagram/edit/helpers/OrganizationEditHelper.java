package com.tibco.xpd.om.modeler.diagram.edit.helpers;

import java.util.Collection;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.util.ECrossReferenceAdapter;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyDependentsRequest;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.DiagramLinkStyle;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.Style;
import org.eclipse.gmf.runtime.notation.View;

import com.tibco.xpd.om.core.om.Organization;

/**
 * @generated
 */
public class OrganizationEditHelper extends OrganizationModelBaseEditHelper {

    @Override
    protected ICommand getDestroyDependentsCommand(DestroyDependentsRequest req) {
        EObject toDestroy = req.getElementToDestroy();

        /*
         * When an Organization is deleted then it's associated Diagram should
         * also be deleted.
         */
        if (toDestroy instanceof Organization) {
            ECrossReferenceAdapter adapter = ECrossReferenceAdapter
                    .getCrossReferenceAdapter(toDestroy);

            if (adapter != null) {
                Collection<Setting> references = adapter
                        .getInverseReferences(toDestroy);

                if (references != null) {
                    for (Setting ref : references) {
                        EObject eo = ref.getEObject();

                        if (eo instanceof View) {
                            Style style = ((View) eo)
                                    .getStyle(NotationPackage.eINSTANCE
                                            .getDiagramLinkStyle());

                            if (style instanceof DiagramLinkStyle) {
                                Diagram diagram = ((DiagramLinkStyle) style)
                                        .getDiagramLink();

                                if (diagram != null) {
                                    return req
                                            .getDestroyDependentCommand(diagram);
                                }
                            }
                        }
                    }
                }
            }
        }
        return super.getDestroyDependentsCommand(req);
    }

}
