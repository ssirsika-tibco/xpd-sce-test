package com.tibco.xpd.core.help.internal;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.handlers.HandlerUtil;

import com.tibco.xpd.core.help.ProductHelpContent;
import com.tibco.xpd.core.help.XpdHelpService;

/**
 * Default handler to deal with F1 key binding and delegate to appropriate
 * product help.
 * 
 * @author jarciuch
 */
public class DefaultXpdHelpHandler extends AbstractHandler {
    /**
     * The constructor.
     */
    public DefaultXpdHelpHandler() {
    }

    /**
     * the command has been executed, so extract extract the needed information
     * from the application context.
     */
    @Override
    public Object execute(ExecutionEvent event) throws ExecutionException {
        XpdHelpService s = XpdHelpService.getInstance();
        ProductHelpContent productContent = s.getDefaultProductHelpContent();
        if (productContent != null) {
            s.showHelpContent(HandlerUtil.getActiveWorkbenchWindow(event),
                    productContent);
        }
        return null;
    }
}
