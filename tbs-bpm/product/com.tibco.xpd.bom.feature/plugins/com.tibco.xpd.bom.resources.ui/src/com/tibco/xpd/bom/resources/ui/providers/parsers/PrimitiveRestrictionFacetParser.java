/**
 * 
 */
package com.tibco.xpd.bom.resources.ui.providers.parsers;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParser;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParserEditStatus;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;

import com.tibco.xpd.bom.resources.ui.internal.Messages;
import com.tibco.xpd.bom.types.PrimitivesUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.resources.wc.AbstractTransactionalWorkingCopy;

/**
 * @author wzurek
 * 
 */
public class PrimitiveRestrictionFacetParser implements IParser {

    private final String propertyName;

    /**
     * 
     */
    public PrimitiveRestrictionFacetParser(String attributeName) {
        this.propertyName = attributeName;
    }

    public IContentAssistProcessor getCompletionProcessor(IAdaptable element) {
        return null;
    }

    /**
     * Extract primitive type from the hint.
     * 
     * @param element
     * @return
     */
    protected PrimitiveType getPrimitiveType(IAdaptable element) {
        Property prop = getProperty(element);
        if (prop != null && prop.getType() instanceof PrimitiveType) {
            return (PrimitiveType) prop.getType();
        }
        return (PrimitiveType) element.getAdapter(PrimitiveType.class);
    }

    /**
     * Extract a property from the hint. It can return null if the property is
     * not available.
     * 
     * @param element
     * @return
     */
    protected Property getProperty(IAdaptable element) {
        return (Property) element.getAdapter(Property.class);
    }

    /*
     * @see
     * org.eclipse.gmf.runtime.common.ui.services.parser.IParser#getParseCommand
     * (org.eclipse.core.runtime.IAdaptable, java.lang.String, int)
     */
    public ICommand getParseCommand(final IAdaptable element,
            final String newString, int flags) {
        Command cmd = null;
        String value = null;
        final Property prop = getProperty(element);
        final PrimitiveType pt = getPrimitiveType(element);
        TransactionalEditingDomain ed =
                XpdResourcesPlugin.getDefault().getEditingDomain();
        final ResourceSet rs = ed.getResourceSet();
        if (newString != null && newString.length() == 0) {
            value = null;
        } else {
            try {
                value =
                        PrimitivesUtil.parseFacetValue(rs,
                                propertyName,
                                newString);
            } catch (Exception e) {
                return null;
            }
        }

        ChangeValueCommand command =
                new ChangeValueCommand(
                        ed,
                        Messages.PrimitiveRestrictionFacetParser_setRestrictionsValue_command_label,
                        pt, propertyName, prop, value);
        return command;
    }

    /**
     * @return the attributeName
     */
    public String getPropertyName() {
        return propertyName;
    }

    public boolean isAffectingEvent(Object event, int flags) {
        return false;
    }

    public String getEditString(IAdaptable element, int flags) {
        ResourceSet rs =
                XpdResourcesPlugin.getDefault().getEditingDomain()
                        .getResourceSet();
        Property property = getProperty(element);
        Object val =
                PrimitivesUtil.getFacetPropertyValue(getPrimitiveType(element),
                        propertyName,
                        property);
        try {
            return val == null ? "" : PrimitivesUtil.formatFacetValue(rs, //$NON-NLS-1$
                    propertyName,
                    String.valueOf(val));
        } catch (Exception e) {
            return ""; //$NON-NLS-1$
        }
    }

    public String getPrintString(IAdaptable element, int flags) {
        ResourceSet rs =
                XpdResourcesPlugin.getDefault().getEditingDomain()
                        .getResourceSet();
        Property property = getProperty(element);
        Object val =
                PrimitivesUtil.getFacetPropertyValue(getPrimitiveType(element),
                        propertyName,
                        property);
        try {
            return val == null ? Messages.PrimitiveRestrictionFacetParser_notset_label
                    : PrimitivesUtil.formatFacetValue(rs, propertyName, String
                            .valueOf(val));
        } catch (Exception e) {
            return Messages.PrimitiveRestrictionFacetParser_notset_label;
        }
    }

    public IParserEditStatus isValidEditString(IAdaptable element,
            String editString) {
        ResourceSet rs =
                XpdResourcesPlugin.getDefault().getEditingDomain()
                        .getResourceSet();
        Type facetType = PrimitivesUtil.getFacetType(rs, propertyName);
        return null;
    }

    /**
     * Command to set the restricted value on a Property/Primitive Type.
     * 
     * @author njpatel
     * 
     */
    private class ChangeValueCommand extends AbstractTransactionalCommand {

        private final String value;

        private final Property property;

        private final PrimitiveType pt;

        private final String propertyName;

        public ChangeValueCommand(TransactionalEditingDomain domain,
                String label, PrimitiveType pt, String propertyName,
                Property property, String value) {
            super(domain, label, getWorkspaceFiles(property));
            this.pt = pt;
            this.propertyName = propertyName;
            this.property = property;
            this.value = value;

            /*
             * Update the context
             */
            EObject eo = property != null ? property : pt;
            if (eo != null) {
                WorkingCopy wc = WorkingCopyUtil.getWorkingCopyFor(eo);
                if (wc instanceof AbstractTransactionalWorkingCopy) {
                    addContext(((AbstractTransactionalWorkingCopy) wc)
                            .getUndoContext());
                }
            }
        }

        @Override
        protected CommandResult doExecuteWithResult(IProgressMonitor monitor,
                IAdaptable info) throws ExecutionException {
            PrimitivesUtil.setFacetPropertyValue(pt,
                    propertyName,
                    value,
                    property);
            return CommandResult.newOKCommandResult();
        }

    }

}
