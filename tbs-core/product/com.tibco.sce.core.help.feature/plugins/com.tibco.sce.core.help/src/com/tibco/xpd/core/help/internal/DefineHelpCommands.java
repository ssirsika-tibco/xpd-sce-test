package com.tibco.xpd.core.help.internal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.Category;
import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.action.Separator;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchCommandConstants;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.menus.CommandContributionItem;
import org.eclipse.ui.menus.CommandContributionItemParameter;
import org.eclipse.ui.menus.ExtensionContributionFactory;
import org.eclipse.ui.menus.IContributionRoot;
import org.eclipse.ui.services.IServiceLocator;

import com.tibco.xpd.core.help.CoreHelpActivator;
import com.tibco.xpd.core.help.HelpImages;
import com.tibco.xpd.core.help.ProductHelpContent;
import com.tibco.xpd.core.help.XpdHelpContribManager;
import com.tibco.xpd.core.help.XpdHelpService;
import com.tibco.xpd.core.help.internal.preferences.ShowPreferencePageHandlerEx;

/**
 * Define help menu contributions, commands and handlers.
 * 
 * @author jarciuch
 * @since 6 Aug 2014
 */
public class DefineHelpCommands extends ExtensionContributionFactory {

    public enum HelpActionType {
        WEB_HELP("webHelp", //$NON-NLS-1$
                Messages.DefineHelpCommands_contentHelpPostfix_label), //
        PRODUCT_HELP("productHelp", //$NON-NLS-1$
                Messages.DefineHelpCommands_productHelpPagePostfix_label);

        private String id;

        private String labelPostfix;

        HelpActionType(String id, String labelPostfix) {
            this.id = id;
            this.labelPostfix = labelPostfix;
        }

        public String getId() {
            return id;
        }

        public String getLabelPostfix() {
            return labelPostfix;
        }
    }

    /**
     * Commands category.
     */
    private static final String TIBCO_HELP_COMMAND_CATEGORY =
            "com.tibco.xpd.core.help.command.category"; //$NON-NLS-1$

    /**
     * TIBCO help menu separator.
     */
    private static final String TIBCO_HELP_SEPARATOR =
            "com.tibco.xpd.core.help.tibcohelp"; //$NON-NLS-1$

    private static final String TIBCO_HELP_END_SEPARATOR =
            "com.tibco.xpd.core.help.tibcohelp.end"; //$NON-NLS-1$

    public static class HelpCommandHandler extends AbstractHandler {

        private ProductHelpContent productHelpContent;

        private HelpActionType actionType;

        public HelpCommandHandler(ProductHelpContent productHelpContent,
                HelpActionType actionType) {
            this.productHelpContent = productHelpContent;
            this.actionType = actionType;

        }

        @Override
        public Object execute(ExecutionEvent event) throws ExecutionException {
            IWorkbenchWindow workbenchWindow =
                    HandlerUtil.getActiveWorkbenchWindow(event);
            if (HelpActionType.WEB_HELP.equals(actionType)) {
                XpdHelpService.getInstance().showHelpContent(workbenchWindow,
                        productHelpContent);
            } else if (HelpActionType.PRODUCT_HELP.equals(actionType)) {
                XpdHelpService.getInstance().showProductHelpPage(
                        workbenchWindow,
                        productHelpContent);
            }
            return null;
        }
    };

    @Override
    public void createContributionItems(IServiceLocator serviceLocator,
            IContributionRoot additions) {
        IWorkbench workbench = PlatformUI.getWorkbench();
        createCommands(workbench);

        additions.addContributionItem(new Separator(TIBCO_HELP_SEPARATOR),
                null);
        for (CommandContributionItem c : getProductHelpContributionItems(
                workbench)) {
            additions.addContributionItem(c, null);
        }

        additions.addContributionItem(new Separator(TIBCO_HELP_END_SEPARATOR),
                null);
        // Added link to preference page.
        CommandContributionItem helpDownloadLinkCI =
                getHelpDownloadLinkContributionItem(workbench, null);
        additions.addContributionItem(helpDownloadLinkCI, null);

    }

    /**
     * Returns contribution items for showing contetn and main help page for all
     * product help contents.
     * 
     * @param serviceLocator
     *            to locate services.
     * @return the list of contributors.
     */
    public List<CommandContributionItem> getProductHelpContributionItems(
            IServiceLocator serviceLocator) {
        List<CommandContributionItem> items = new ArrayList<>();
        for (ProductHelpContent c : XpdHelpContribManager.getInstance()
                .getProductHelpContents()) {
            items.add(getCommandContributionItem(serviceLocator,
                    c,
                    HelpActionType.WEB_HELP));
        }
        for (ProductHelpContent c : XpdHelpContribManager.getInstance()
                .getProductHelpContents()) {
            CommandContributionItem cci =
                    getCommandContributionItem(serviceLocator,
                            c,
                            HelpActionType.PRODUCT_HELP);
            if (cci != null) {
                items.add(cci);
            }
        }
        return items;
    }

    /**
     * Gets command contribution for download help command.
     * 
     * @param serviceLocator
     *            locator.
     * @param displayIds
     *            list of preference page ids to be displayed (can be null).
     * @return command contribution for download help command.
     */
    public CommandContributionItem getHelpDownloadLinkContributionItem(
            IServiceLocator serviceLocator, String[] displayIds) {
        CommandContributionItemParameter contirbParam =
                new CommandContributionItemParameter(serviceLocator,
                        "com.tibco.xpd.core.help.gotoDownload.contribution", //$NON-NLS-1$
                        "com.tibco.xpd.core.help.helpDownload", //$NON-NLS-1$
                        CommandContributionItem.STYLE_PUSH);
        contirbParam.label =
                Messages.DefineHelpCommands_downloadOfflineHelp_label;
        contirbParam.icon = CoreHelpActivator.getDefault().getImageRegistry()
                .getDescriptor(HelpImages.HELP_DEFAULT_16);
        HashMap<String, String> params = new HashMap<>();
        params.put(IWorkbenchCommandConstants.WINDOW_PREFERENCES_PARM_PAGEID,
                "com.tibco.xpd.core.help.internal.preferences.XpdHelpContentPreferencePage"); //$NON-NLS-1$
        params.put(ShowPreferencePageHandlerEx.PARAM_DISPLAY_IDS,
                join(displayIds, ",")); //$NON-NLS-1$
        contirbParam.parameters = params;
        CommandContributionItem contribItem =
                new CommandContributionItem(contirbParam);
        contribItem.setVisible(true);
        return contribItem;
    }

    /**
     * Dynamically creates commands used for showing help contents and main help
     * page for the contributed help contents.
     * 
     * @param serviceLocator
     *            the service locator.
     */
    public void createCommands(final IServiceLocator serviceLocator) {
        ICommandService cmdService =
                serviceLocator.getService(ICommandService.class);
        Category tibcoHelpCategory =
                cmdService.getCategory(TIBCO_HELP_COMMAND_CATEGORY);
        if (!tibcoHelpCategory.isDefined()) {
            /*
             * The category should be already defined in extension, but just in
             * case.
             */
            tibcoHelpCategory.define(
                    Messages.DefineHelpCommands_helpCommandCategory_name,
                    Messages.DefineHelpCommands_helpCommandCategory_desc);
        }

        for (ProductHelpContent c : XpdHelpContribManager.getInstance()
                .getProductHelpContents()) {

            String productId = c.getProductId();

            String webHelpCommandId =
                    getHelpCommandId(HelpActionType.WEB_HELP, productId);
            Command webHelp = cmdService.getCommand(webHelpCommandId);
            if (!webHelp.isDefined()) {
                String commandName = getHelpCommandName(c.getProductName(),
                        HelpActionType.WEB_HELP);
                webHelp.define(commandName, commandName, tibcoHelpCategory);
            }

            /*
             * Sid XPD-8264 The Workbench Command Service caches the fact that
             * the command has already been created (even in a previous Studio
             * session for same workspace completely) BUT it doesn't keep track
             * of the command handler. So we need to add that every time.
             */
            webHelp.setHandler(
                    new HelpCommandHandler(c, HelpActionType.WEB_HELP));

            if (c.isProductHelpPageAvailable()) {
                String productHelpCommandId =
                        getHelpCommandId(HelpActionType.PRODUCT_HELP,
                                productId);
                Command productHelp =
                        cmdService.getCommand(productHelpCommandId);
                if (!productHelp.isDefined()) {
                    String commandName = getHelpCommandName(c.getProductName(),
                            HelpActionType.PRODUCT_HELP);
                    productHelp.define(commandName,
                            commandName,
                            tibcoHelpCategory);
                }

                /*
                 * Sid XPD-8264 The Workbench Command Service caches the fact
                 * that the command has already been created (even in a previous
                 * Studio session for same workspace completely) BUT it doesn't
                 * keep track of the command handler. So we need to add that
                 * every time.
                 */
                productHelp.setHandler(
                        new HelpCommandHandler(c, HelpActionType.PRODUCT_HELP));

            }
        }
    }

    private String join(String[] as, String delimiter) {
        if (as == null)
            return null;
        StringBuilder sb = new StringBuilder();
        boolean empty = true;
        for (String s : as) {
            if (s == null)
                continue;
            if (!empty) {
                sb.append(delimiter);
            } else {
                empty = false;
            }
            sb.append(s);
        }
        return sb.length() > 0 ? sb.toString() : null;
    }

    /**
     * Creates and command contribution item that can later be added to a menu,
     * toolbar, etc.
     * 
     * @param serviceLocator
     *            service locator.
     * @param c
     *            product help contribution.
     * @param actionType
     *            type of the action to create for the product.
     * @return the contribution command or 'null' if the corresponding command
     *         is not defined.
     */
    private CommandContributionItem getCommandContributionItem(
            IServiceLocator serviceLocator, ProductHelpContent c,
            HelpActionType actionType) {

        ICommandService cmdService =
                serviceLocator.getService(ICommandService.class);
        String productId = c.getProductId();
        String commandId = getHelpCommandId(actionType, productId);

        if (!cmdService.getCommand(commandId).isDefined()) {
            return null;
        }

        String contributionId = commandId + ".contribution";//$NON-NLS-1$

        CommandContributionItemParameter contirbParam =
                new CommandContributionItemParameter(serviceLocator,
                        contributionId, commandId,
                        CommandContributionItem.STYLE_PUSH);
        contirbParam.label = getHelpCommandName(c.getProductName(), actionType);
        // TODO add possibility of a custom icons from extension.
        contirbParam.icon = CoreHelpActivator.getDefault().getImageRegistry()
                .getDescriptor(HelpImages.HELP_DEFAULT_16);

        CommandContributionItem contribItem =
                new CommandContributionItem(contirbParam);
        contribItem.setVisible(true);
        return contribItem;
    }

    private String getHelpCommandId(HelpActionType actionType,
            String productId) {
        return new StringBuilder(productId).append('.')
                .append(actionType.getId()).append(".command").toString(); //$NON-NLS-1$
    }

    private String getHelpCommandName(String productName,
            HelpActionType actionType) {
        return new StringBuilder(productName).append(' ')
                .append(actionType.getLabelPostfix()).toString();
    }

}