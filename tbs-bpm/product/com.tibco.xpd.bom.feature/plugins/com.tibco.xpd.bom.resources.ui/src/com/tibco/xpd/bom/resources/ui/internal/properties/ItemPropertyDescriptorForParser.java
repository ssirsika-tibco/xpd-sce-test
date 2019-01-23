/**
 * 
 */
package com.tibco.xpd.bom.resources.ui.internal.properties;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.workspace.IWorkspaceCommandStack;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParser;

import com.tibco.xpd.bom.resources.ui.Activator;
import com.tibco.xpd.bom.resources.ui.internal.Messages;
import com.tibco.xpd.resources.XpdResourcesPlugin;

/**
 * ItemPropertyDescriptor based on GMF parser.
 * 
 * @author wzurek
 * 
 */
public class ItemPropertyDescriptorForParser extends ItemPropertyDescriptor {

    private final IParser parser;

    /**
     * The Constructor.
     * 
     * All parameters apart from parser are passed to the parent.
     * 
     * @see ItemPropertyDescriptor
     * 
     * @param adapterFactory
     * @param resourceLocator
     * @param displayName
     * @param description
     * @param isSettable
     * @param multiLine
     * @param sortChoices
     * @param staticImage
     * @param category
     * @param filterFlags
     * @param parser
     */
    public ItemPropertyDescriptorForParser(AdapterFactory adapterFactory,
            ResourceLocator resourceLocator, String displayName,
            String description, boolean isSettable, boolean multiLine,
            boolean sortChoices, Object staticImage, String category,
            String[] filterFlags, IParser parser) {
        super(adapterFactory, resourceLocator, displayName, description, null,
                isSettable, multiLine, sortChoices, staticImage, category,
                filterFlags);
        this.parser = parser;
    }

    /**
     * Override label provider that work with the parser.
     */
    @Override
    public IItemLabelProvider getLabelProvider(Object object) {
        return new IItemLabelProvider() {
            public Object getImage(Object object) {
                return staticImage;
            }

            public String getText(Object object) {
                if (object instanceof String) {
                    String s = (String) object;
                    return s == null || "".equals(s) ? Messages.PrimitiveRestrictionFacetParser_notset_label //$NON-NLS-1$
                            : s;
                }
                return parser.getPrintString(getAdaptableWrapper(object), 0);
            }
        };
    }

    private IAdaptable getAdaptableWrapper(final Object obj) {
        return new IAdaptable() {
            @SuppressWarnings("unchecked")
            public Object getAdapter(Class adapter) {
                if (adapter.isInstance(obj)) {
                    return obj;
                }
                return null;
            }
        };
    }

    @Override
    public Object getFeature(Object object) {
        return parser;
    }

    @Override
    public Object getPropertyValue(Object object) {
        return parser.getEditString(getAdaptableWrapper(object), 0);
    }

    @Override
    public void setPropertyValue(Object object, Object value) {
        ICommand cmd =
                parser.getParseCommand(getAdaptableWrapper(object),
                        (String) value,
                        0);
        if (cmd == null) {
            return;
        }

        TransactionalEditingDomain ed =
                XpdResourcesPlugin.getDefault().getEditingDomain();

        if (ed.getCommandStack() instanceof IWorkspaceCommandStack) {
            try {
                ((IWorkspaceCommandStack) ed.getCommandStack())
                        .getOperationHistory().execute(cmd, null, null);
            } catch (ExecutionException e) {
                Activator.getDefault().getLogger().error(e);
            }
        }
    }
}