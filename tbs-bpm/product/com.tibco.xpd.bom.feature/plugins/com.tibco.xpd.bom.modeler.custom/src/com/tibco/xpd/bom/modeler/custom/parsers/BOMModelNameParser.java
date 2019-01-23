package com.tibco.xpd.bom.modeler.custom.parsers;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.emf.workspace.EMFCommandOperation;
import org.eclipse.gmf.runtime.common.core.command.CompositeCommand;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.common.core.command.UnexecutableCommand;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParserEditStatus;
import org.eclipse.gmf.runtime.common.ui.services.parser.ParserEditStatus;
import org.eclipse.gmf.runtime.emf.ui.services.parser.ISemanticParser;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.UMLPackage;

import com.tibco.xpd.bom.modeler.custom.internal.Messages;
import com.tibco.xpd.bom.resources.firstclassprofiles.FirstClassProfileManager;
import com.tibco.xpd.bom.types.PrimitivesUtil;

public class BOMModelNameParser implements ISemanticParser {

    public boolean areSemanticElementsAffected(EObject listener,
            Object notification) {

        return false;
    }

    public List<?> getSemanticElementsBeingParsed(EObject element) {
        List<EObject> lstElems = new ArrayList<EObject>();

        if (element instanceof Package) {
            lstElems.add(element);
        }
        return lstElems;
    }

    public IContentAssistProcessor getCompletionProcessor(IAdaptable element) {
        // TODO Auto-generated method stub
        return null;
    }

    public String getEditString(IAdaptable element, int flags) {
        // return getPrintString(element, flags);
        String text = Messages.BOMAuthorParser_AuthorNotSet_label;
        EObject obj = getElement(element);
        if (obj instanceof Model) {
            Model mod = (Model) obj;
            text = PrimitivesUtil.getDisplayLabel(mod);
        }
        return text;
    }

    protected EObject getElement(IAdaptable element) {
        return (EObject) element.getAdapter(EObject.class);
    }

    public ICommand getParseCommand(IAdaptable element, final String newString,
            int flags) {

        EObject obj = getElement(element);

        if (obj instanceof Model) {
            final Model mod = (Model) obj;
            TransactionalEditingDomain ed =
                    TransactionUtil.getEditingDomain(mod);

            Command cmd =
                    SetCommand.create(ed, mod, UMLPackage.eINSTANCE
                            .getNamedElement_Name(), newString);
            CompositeCommand result =
                    new CompositeCommand(
                            Messages.BOMBadgeModelNameParser_command_message);
            result.add(new EMFCommandOperation(ed, cmd));
            return result;
        }
        return UnexecutableCommand.INSTANCE;
    }

    public String getPrintString(IAdaptable element, int flags) {
        String text = Messages.BOMAuthorParser_AuthorNotSet_label;

        EObject obj = getElement(element);

        if (obj instanceof Model) {
            Model mod = (Model) obj;

            if (!mod.getAppliedStereotypes().isEmpty()) {
                if (!FirstClassProfileManager.getInstance()
                        .isFirstClassProfileApplied(mod)) {
                    String stereoLabel = "";
                    EList<Stereotype> lstStereos = mod.getAppliedStereotypes();

                    int idx = 0;
                    for (Stereotype stereotype : lstStereos) {

                        Profile profile = stereotype.getProfile();

                        if (profile != null && profile.eResource() != null) {
                            URI uri = profile.eResource().getURI();
                            if (uri != null && !uri.isPlatformResource()) {
                                continue;
                            }
                        }

                        if (idx == 0) {
                            stereoLabel = stereotype.getLabel();
                        } else {
                            stereoLabel =
                                    stereoLabel + ", " + stereotype.getLabel(); //$NON-NLS-1$
                        }
                        idx++;
                    }

                    if (idx > 0) {
                        text =
                                " <<"   + stereoLabel + ">>" + PrimitivesUtil.getDisplayLabel(mod); //$NON-NLS-1$ //$NON-NLS-2$
                    } else {
                        text = PrimitivesUtil.getDisplayLabel(mod);
                    }
                }
            } else {
                text = PrimitivesUtil.getDisplayLabel(mod);
            }

        } else if (obj instanceof Package) {
            Package pkg = (Package) obj;

            if (!pkg.getAppliedStereotypes().isEmpty()
                    && pkg.getModel() != null) {
                if (!FirstClassProfileManager.getInstance()
                        .isFirstClassProfileApplied(pkg.getModel())) {
                    String stereoLabel = "";
                    EList<Stereotype> lstStereos = pkg.getAppliedStereotypes();

                    int idx = 0;
                    for (Stereotype stereotype : lstStereos) {
                        Profile profile = stereotype.getProfile();

                        if (profile != null && profile.eResource() != null) {
                            URI uri = profile.eResource().getURI();
                            if (uri != null && !uri.isPlatformResource()) {
                                continue;
                            }
                        }

                        if (idx == 0) {
                            stereoLabel = stereotype.getLabel();
                        } else {
                            stereoLabel =
                                    stereoLabel + ", " + stereotype.getLabel(); //$NON-NLS-1$
                        }
                        idx++;
                    }

                    if (idx > 0) {
                        text =
                                pkg.getQualifiedName()
                                        + " <<" + stereoLabel + ">>"; //$NON-NLS-1$ //$NON-NLS-2$
                    } else {
                        text = PrimitivesUtil.getDisplayLabel(pkg);
                    }
                }
            } else {
                text = pkg.getQualifiedName();
            }
        }

        return text;
    }

    public boolean isAffectingEvent(Object event, int flags) {
        if (event instanceof Notification) {
            Notification n = (Notification) event;

            if (n.getFeature() instanceof EAttribute) {
                if (n.getNotifier() instanceof Package) {
                    if (n.getFeature() == UMLPackage.Literals.NAMED_ELEMENT__NAME) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public IParserEditStatus isValidEditString(IAdaptable element,
            String editString) {
        return ParserEditStatus.EDITABLE_STATUS;
    }

}
