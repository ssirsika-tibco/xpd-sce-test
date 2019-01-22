package com.tibco.xpd.bom.modeler.custom.parsers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.emf.workspace.util.WorkspaceSynchronizer;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.common.core.command.UnexecutableCommand;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParserEditStatus;
import org.eclipse.gmf.runtime.common.ui.services.parser.ParserEditStatus;
import org.eclipse.gmf.runtime.emf.ui.services.parser.ISemanticParser;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.ParameterDirectionKind;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.UMLPackage;

import com.tibco.xpd.bom.modeler.custom.commands.SetDisplayLabelCommand;
import com.tibco.xpd.bom.modeler.custom.internal.Messages;
import com.tibco.xpd.bom.types.PrimitivesUtil;

public class OperationParser implements ISemanticParser {

    private static final String STAR = "*"; //$NON-NLS-1$

    private static final String ARRAY = "[ ]"; //$NON-NLS-1$

    /*
     * (non-Javadoc)
     * 
     * @seeorg.eclipse.gmf.runtime.emf.ui.services.parser.ISemanticParser#
     * areSemanticElementsAffected(org.eclipse.emf.ecore.EObject,
     * java.lang.Object)
     */
    public boolean areSemanticElementsAffected(EObject listener,
            Object notification) {
        if (notification instanceof Notification) {

            Notification n = (Notification) notification;
            Object feature = n.getFeature();

            if (feature != null) {
                if (feature
                        .equals(UMLPackage.Literals.MULTIPLICITY_ELEMENT__LOWER_VALUE)) {
                    return true;
                }

                if (feature
                        .equals(UMLPackage.Literals.MULTIPLICITY_ELEMENT__UPPER_VALUE)) {
                    return true;
                }

                if (feature
                        .equals(UMLPackage.Literals.LITERAL_UNLIMITED_NATURAL__VALUE)) {
                    return true;
                }

                if (feature.equals(UMLPackage.Literals.LITERAL_INTEGER__VALUE)) {
                    return true;
                }

                if (feature
                        .equals(UMLPackage.Literals.BEHAVIORAL_FEATURE__OWNED_PARAMETER)) {
                    return true;
                }

            }

        }

        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @seeorg.eclipse.gmf.runtime.emf.ui.services.parser.ISemanticParser#
     * getSemanticElementsBeingParsed(org.eclipse.emf.ecore.EObject)
     */
    public List<?> getSemanticElementsBeingParsed(EObject element) {
        if (element instanceof Operation) {
            Operation operation = (Operation) element;

            ArrayList<EObject> list = new ArrayList<EObject>();
            list.add(element);

            EList<Parameter> ownedParameters = operation.getOwnedParameters();

            if (!ownedParameters.isEmpty()) {
                for (Parameter parameter : ownedParameters) {
                    list.add(parameter);
                    if (parameter.getLowerValue() != null) {
                        list.add(parameter.getLowerValue());
                    }
                    if (parameter.getUpperValue() != null) {
                        list.add(parameter.getUpperValue());
                    }
                }
            }

            return list;
        }
        return Collections.singletonList(element);
    }

    /*
     * (non-Javadoc)
     * 
     * @seeorg.eclipse.gmf.runtime.common.ui.services.parser.IParser#
     * getCompletionProcessor(org.eclipse.core.runtime.IAdaptable)
     */
    public IContentAssistProcessor getCompletionProcessor(IAdaptable element) {
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.gmf.runtime.common.ui.services.parser.IParser#getEditString
     * (org.eclipse.core.runtime.IAdaptable, int)
     */
    public String getEditString(IAdaptable element, int flags) {
        EObject obj = getElement(element);

        if (obj instanceof NamedElement) {
            NamedElement ne = (NamedElement) obj;
            return PrimitivesUtil.getDisplayLabel(ne);
        }

        return ""; //$NON-NLS-1$
    }

    /**
     * @param element
     * @return
     */
    protected EObject getElement(IAdaptable element) {
        return (EObject) element.getAdapter(EObject.class);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.gmf.runtime.common.ui.services.parser.IParser#getParseCommand
     * (org.eclipse.core.runtime.IAdaptable, java.lang.String, int)
     */
    public ICommand getParseCommand(IAdaptable element, String newString,
            int flags) {

        // TODO: BOMDisplayLabel
        EObject obj = getElement(element);

        if (obj instanceof NamedElement) {
            final NamedElement ne = (NamedElement) obj;
            TransactionalEditingDomain ed =
                    TransactionUtil.getEditingDomain(ne);
            Resource resource = ne.eResource();

            if (resource != null) {
                IFile file = WorkspaceSynchronizer.getFile(resource);

                return new SetDisplayLabelCommand(ed, file, ne, newString);
            }
        }

        return UnexecutableCommand.INSTANCE;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.gmf.runtime.common.ui.services.parser.IParser#getPrintString
     * (org.eclipse.core.runtime.IAdaptable, int)
     */
    public String getPrintString(IAdaptable element, int flags) {
        String text = ""; //$NON-NLS-1$
        EObject obj = getElement(element);
        if (obj instanceof Operation) {
            Operation op = (Operation) obj;

            StringBuilder sb = new StringBuilder();

            EList<Stereotype> stereotypes = op.getAppliedStereotypes();
            // Check for stereotypes
            String stereos = ""; //$NON-NLS-1$

            if (stereotypes != null) {
                if (stereotypes.size() > 0) {
                    int idx = 0;

                    for (Stereotype stereotype : stereotypes) {
                        Profile profile = stereotype.getProfile();

                        if (profile != null && profile.eResource() != null) {
                            URI uri = profile.eResource().getURI();
                            if (uri != null && !uri.isPlatformResource()) {
                                continue;
                            }
                        }

                        if (idx == 0) {
                            stereos = stereotype.getName();
                        } else {
                            stereos = stereos + ", " + stereotype.getName(); //$NON-NLS-1$
                        }
                        idx++;
                    }

                    if (idx > 0) {
                        text = "<<" + stereos + ">>" + " " + text; //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
                    }

                }

                if (!text.equals("")) {
                    sb.append(text);
                }
            }

            if (op.getName() == null) {
                sb.append(Messages.OperationParser_operation_name);
            } else {
                sb.append(PrimitivesUtil.getDisplayLabel(op));
            }

            ArrayList<String> params =
                    new ArrayList<String>(op.getOwnedParameters().size());
            for (Parameter param : op.getOwnedParameters()) {
                if (param.getDirection() == ParameterDirectionKind.IN_LITERAL) {
                    String argDesc = param.getName();

                    // See if this parameter has a return type set and display
                    // it
                    Type type = param.getType();

                    if (type != null) {
                        argDesc = argDesc + ":" + type.getName() //$NON-NLS-1$
                                + getMultiplicityString(param);
                    }

                    params.add(argDesc);
                }
            }
            sb.append("("); //$NON-NLS-1$
            if (params.size() > 0) {
                Iterator<String> iter = params.iterator();
                sb.append(iter.next());
                while (iter.hasNext()) {
                    sb.append(", "); //$NON-NLS-1$

                    sb.append(iter.next());
                }
            }
            sb.append(")"); //$NON-NLS-1$
            Type ret = op.getType();
            if (ret != null && !ret.eIsProxy()) {
                sb.append(" : "); //$NON-NLS-1$
                // TODO: displayLabel
                sb.append(PrimitivesUtil.getDisplayLabel(ret));

                // Find the return type parameter so that we can
                EList<Parameter> ownedParameters = op.getOwnedParameters();

                for (Parameter parameter : ownedParameters) {
                    if (parameter.getDirection() == ParameterDirectionKind.RETURN_LITERAL) {
                        sb.append(getMultiplicityString(parameter));
                    }
                }

            }
            text = sb.toString();
        }
        return text;
    }

    /**
     * @param param
     * @return
     */
    private String getMultiplicityString(Parameter param) {
        String multiplicity = ""; //$NON-NLS-1$

        if (param != null) {

            int upper = param.getUpper();
            int lower = param.getLower();

            // if Upper and Lower are 1, then effectively multiplicity is not
            // set
            if (upper == lower && upper > 1) {
                multiplicity = ARRAY;
            }

            // multiplicity set to *
            if (upper == -1) {
                multiplicity = ARRAY;
            }

            if (lower > 1) {
                multiplicity = ARRAY;
            }
        }

        return multiplicity;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.gmf.runtime.common.ui.services.parser.IParser#isAffectingEvent
     * (java.lang.Object, int)
     */
    public boolean isAffectingEvent(Object event, int flags) {
        boolean isAffected = false;
        if (event instanceof Notification) {
            Notification n = (Notification) event;
            Object notifier = n.getNotifier();

            if (notifier instanceof Parameter) {
                if (n.getFeature() == UMLPackage.Literals.MULTIPLICITY_ELEMENT__LOWER_VALUE) {
                    return true;
                }

                if (n.getFeature() == UMLPackage.Literals.MULTIPLICITY_ELEMENT__UPPER_VALUE) {
                    return true;
                }
            }

            if (n.getFeature() instanceof EAttribute) {
                EAttribute ea = (EAttribute) n.getFeature();

                // Check for changes to attribute name or multiplicity values.
                return ea == UMLPackage.Literals.NAMED_ELEMENT__NAME
                        || ea == UMLPackage.Literals.LITERAL_UNLIMITED_NATURAL__VALUE
                        || ea == UMLPackage.Literals.LITERAL_INTEGER__VALUE;

            }

            if (notifier instanceof Parameter) {
                if (((Parameter) notifier).getDirection() == ParameterDirectionKind.RETURN_LITERAL
                        || ((Parameter) notifier).getDirection() == ParameterDirectionKind.IN_LITERAL)
                    // If return parameter then check if type has changed
                    isAffected =
                            n.getFeature() == UMLPackage.Literals.TYPED_ELEMENT__TYPE;
            } else if (notifier instanceof NamedElement) {
                // Not return parameter so check if name of the parameter or
                // operation has changed
                isAffected =
                        n.getFeature() == UMLPackage.Literals.NAMED_ELEMENT__NAME;
            }

        }
        return isAffected;

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.gmf.runtime.common.ui.services.parser.IParser#isValidEditString
     * (org.eclipse.core.runtime.IAdaptable, java.lang.String)
     */
    public IParserEditStatus isValidEditString(IAdaptable element,
            String editString) {
        return ParserEditStatus.EDITABLE_STATUS;
    }
}
