/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.rsd.ui.components;

import java.util.ArrayList;

import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.bindings.keys.KeyStroke;
import org.eclipse.jface.bindings.keys.ParseException;
import org.eclipse.jface.fieldassist.ContentProposal;
import org.eclipse.jface.fieldassist.ContentProposalAdapter;
import org.eclipse.jface.fieldassist.ControlDecoration;
import org.eclipse.jface.fieldassist.FieldDecorationRegistry;
import org.eclipse.jface.fieldassist.IContentProposal;
import org.eclipse.jface.fieldassist.IContentProposalProvider;
import org.eclipse.jface.fieldassist.IControlContentAdapter2;
import org.eclipse.jface.fieldassist.TextContentAdapter;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;

import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.rsd.Parameter;
import com.tibco.xpd.rsd.Resource;
import com.tibco.xpd.rsd.ui.internal.Messages;
import com.tibco.xpd.rsd.ui.util.RestServicePathValidator;
import com.tibco.xpd.rsd.ui.util.RestServicePathValidator.RestPathIssue;
import com.tibco.xpd.rsd.ui.util.RestServicePathValidator.RestPathIssueType;

/**
 * The decorator for Path Template text control providing decoration (tooltip
 * icon) and content assist.
 * 
 * Before using content assist the context Resource object needs to be set using
 * {@link #setContextResource(Resource)} method.
 * 
 * @author jarciuch
 * @since 19 Mar 2015
 */
public class PathTemplateField {

    /**
     * Starting character for a parameter reference within path.
     */
    protected static final char START_PARAM_CHAR = '{';

    /**
     * Ending character for a parameter reference within path.
     */
    protected static final char END_PARAM_CHAR = '}';

    /**
     * Delimiting characters for processing parameters in a path.
     */
    protected static final String DELIMS = "{}/ ".toString(); //$NON-NLS-1$

    /**
     * Decorated text field.
     */
    protected Text text;

    /**
     * Control adapter providing content assist.
     */
    protected ContentProposalAdapter contentProposalAdapter;

    private ControlDecoration pathTextDecoration;

    /**
     * Creates a new decoration for a text field (providing control decoration
     * and content assist).
     */
    public PathTemplateField(Text text) {
        this.text = text;
        // decoration
        pathTextDecoration = new ControlDecoration(text, SWT.TOP | SWT.LEFT);

        /* Sid XPD-7755 - display path errors in UI. */
        setPathDecoration(FieldDecorationRegistry
                .getDefault()
                .getFieldDecoration(FieldDecorationRegistry.DEC_CONTENT_PROPOSAL)
                .getImage(),
                String.format(Messages.PathTemplateField_PathTemplate_tooltip,
                        START_PARAM_CHAR,
                        END_PARAM_CHAR));

        /*
         * Do validation of field live rather than always waiting until
         * deactivate text.
         */
        this.text.addModifyListener(new ModifyListener() {
            @Override
            public void modifyText(ModifyEvent e) {
                validatePath();
            }
        });

        // content assist
        char[] autoActivationCharacters = new char[] { START_PARAM_CHAR };
        KeyStroke keyStroke = null;
        try {
            keyStroke = KeyStroke.getInstance("Ctrl+Space"); //$NON-NLS-1$
        } catch (ParseException e) {
            new AssertionError(e);
        }

        TextContentAdapter controlContentAdapter = new TextContentAdapter();
        ParamContentProposalProvider contentProposalProvider =
                new ParamContentProposalProvider(text, controlContentAdapter);
        contentProposalAdapter =
                new ContentProposalAdapter(text, controlContentAdapter,
                        contentProposalProvider, keyStroke,
                        autoActivationCharacters);
        contentProposalAdapter.setPopupSize(new Point(400, 200));
        contentProposalAdapter.setPropagateKeys(true);
        contentProposalAdapter
                .setProposalAcceptanceStyle(ContentProposalAdapter.PROPOSAL_REPLACE);
        contentProposalAdapter.setLabelProvider(new LabelProvider() {
            /**
             * {@inheritDoc}
             */
            @Override
            public Image getImage(Object element) {
                if (element instanceof ParamContentProposal) {
                    Parameter param =
                            ((ParamContentProposal) element).getParam();
                    return WorkingCopyUtil.getImage(param);
                }
                return super.getImage(element);
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public String getText(Object element) {
                if (element instanceof IContentProposal) {
                    return ((IContentProposal) element).getLabel();
                }
                return super.getText(element);
            }

        });

    }

    /**
     * Sets the context resource.
     * 
     * @param resource
     *            resource to set.
     */
    public void setContextResource(Resource resource) {
        IContentProposalProvider cpp =
                contentProposalAdapter.getContentProposalProvider();
        if (cpp instanceof ParamContentProposalProvider) {
            ((ParamContentProposalProvider) cpp).setResource(resource);
        }
    }

    /**
     * Returns the context resource set by {@link #setContextResource(Resource)}
     * .
     * 
     * @return the context resource.
     */
    public Resource getContextResource() {
        IContentProposalProvider cpp =
                contentProposalAdapter.getContentProposalProvider();
        if (cpp instanceof ParamContentProposalProvider) {
            return ((ParamContentProposalProvider) cpp).getResource();
        }
        return null;
    }

    /**
     * Sid XPD-7755 - validate and display path errors in UI.
     * <p>
     * Updates path control decoration.
     * 
     * @return <code>true</code> if valid or <code>false</code> not
     */
    public boolean validatePath() {
        if (pathTextDecoration == null
                || pathTextDecoration.getControl() == null
                || pathTextDecoration.getControl().isDisposed()) {
            return true;
        }

        RestPathIssue pathIssue = null;

        Resource contextResource = getContextResource();
        if (contextResource != null) {
            RestServicePathValidator validator = new RestServicePathValidator();

            pathIssue = validator.validate(contextResource, text.getText());
        }

        if (pathIssue == null
                || RestPathIssueType.OK.equals(pathIssue.getType())) {
            setPathDecoration(FieldDecorationRegistry
                    .getDefault()
                    .getFieldDecoration(FieldDecorationRegistry.DEC_CONTENT_PROPOSAL)
                    .getImage(),
                    Messages.ServiceGeneralSection_ContextPathDecorationLabel);
            return true;

        } else {
            setPathDecoration(FieldDecorationRegistry.getDefault()
                    .getFieldDecoration(FieldDecorationRegistry.DEC_ERROR)
                    .getImage(), pathIssue.getMessage());
            return false;
        }

    }

    /**
     * Sid XPD-7755 - display path errors in UI.
     * <p>
     * Set the path decoration image / text (if details are different from
     * current).
     * 
     * @param image
     * @param tooltip
     */
    private void setPathDecoration(Image image, String tooltip) {
        if (image != pathTextDecoration.getImage()
                || !tooltip.equals(pathTextDecoration.getDescriptionText())) {
            pathTextDecoration.setImage(image);
            pathTextDecoration.setDescriptionText(tooltip);
        }
    }

    /**
     * Provides proposals for inserting parameters into the path.
     * 
     * @author jarciuch
     * @since 19 Mar 2015
     */
    protected static class ParamContentProposalProvider implements
            IContentProposalProvider {

        private Resource resource;

        private IControlContentAdapter2 controlContentAdapter;

        private Control control;

        /**
         * @param text
         *            text control used to
         */
        public ParamContentProposalProvider(Control control,
                IControlContentAdapter2 controlContentAdapter) {
            this.control = control;
            this.controlContentAdapter = controlContentAdapter;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public IContentProposal[] getProposals(String content, int position) {
            if (getResource() == null) {
                return new IContentProposal[0];
            }
            boolean insertMode = false;

            EList<Parameter> params = getResource().getParameters();
            String behind = content.substring(0, position);
            String ahead = content.substring(position);
            String[] behindTokens = splitLast(behind);
            String start = behindTokens[0];
            String startRest = behindTokens[1];
            String ctxStart = behindTokens[1];
            if (start.endsWith("{")) { //$NON-NLS-1$
                // remove '{' from the end of the start.
                start = start.substring(0, start.length() - 1);
            } else {
                insertMode = true;
                ctxStart = ""; //$NON-NLS-1$
            }
            String[] aheadTokens = splitFirst(ahead);
            String ctxEnd = aheadTokens[0];
            String end = aheadTokens[1];

            /*
             * If there is selection then switch into insert mode and just
             * replace the selection
             */
            Point selection = controlContentAdapter.getSelection(control);
            if (selection.x < selection.y) {
                insertMode = true;
                start = content.substring(0, selection.x);
                startRest = ""; //$NON-NLS-1$
                ctxEnd = ""; //$NON-NLS-1$
                end = content.substring(selection.y);
            }

            ArrayList<ContentProposal> list = new ArrayList<>();
            for (Parameter param : params) {
                String paramName = param.getName();
                if (paramName.length() >= ctxStart.length()
                        && paramName.substring(0, ctxStart.length())
                                .equalsIgnoreCase(ctxStart)) {
                    if (insertMode) {

                        StringBuilder proposalSb =
                                new StringBuilder(start).append(startRest)
                                        .append(START_PARAM_CHAR)
                                        .append(paramName)
                                        .append(END_PARAM_CHAR);
                        int cursorPosition = proposalSb.length();
                        String proposal =
                                proposalSb.append(ctxEnd).append(end)
                                        .toString();
                        String description =
                                Messages.PathTemplateField_Preview_label + '\n'
                                        + proposal;
                        list.add(new ParamContentProposal(proposal, paramName,
                                description, cursorPosition, param));
                    } else { // replace mode

                        StringBuilder proposalSb =
                                new StringBuilder(start)
                                        .append(START_PARAM_CHAR)
                                        .append(paramName)
                                        .append(END_PARAM_CHAR);
                        int cursorPosition = proposalSb.length();
                        String proposal = proposalSb.append(end).toString();
                        String description =
                                Messages.PathTemplateField_Preview_label + '\n'
                                        + proposal;
                        list.add(new ParamContentProposal(proposal, paramName,
                                description, cursorPosition, param));
                    }
                }
            }

            return list.toArray(new IContentProposal[list.size()]);
        }

        /**
         * Splits the first part of the (possible) parameter from the beginning
         * of the string (including the STRAT_PARAM_CHAR).
         * <p>
         * For example: splitLast("/path/{a}/sth/{pa") -> [/path/{a}/sth/{],
         * [pa]
         * </p>
         * 
         * @param content
         *            the content to split.
         * @return two element array with beginning of the content in first
         *         ([0]) and the start parameter part in second ([1]).
         */
        private static String[] splitLast(String content) {
            for (int i = content.length() - 1; i > -1; i--) {
                char c = content.charAt(i);
                if (DELIMS.indexOf(c) != -1) { // one of delims
                    if (c == START_PARAM_CHAR) {
                        return new String[] { content.substring(0, i + 1),
                                content.substring(i + 1) };
                    } else {
                        return new String[] { content.substring(0, i + 1),
                                content.substring(i + 1) };
                    }
                }
            }
            return new String[] { "", content }; //$NON-NLS-1$
        }

        /**
         * Splits the last part of the (possible) parameter from the rest of the
         * string (ignoring the END_PARAM_CHAR).
         * <p>
         * For example: splitFirst("ara}/path/sth") -> [ara], [/path/sth]
         * </p>
         * 
         * @param content
         *            the content to split.
         * @return two element array with the remaining parameter part ([0]) and
         *         the rest of the content part ([1]).
         */
        private static String[] splitFirst(String content) {
            for (int i = 0, len = content.length(); i < len; i++) {
                char c = content.charAt(i);
                if (DELIMS.indexOf(c) != -1) { // one of delims
                    if (c == END_PARAM_CHAR) {
                        return new String[] { content.substring(0, i),
                                content.substring(i + 1) };
                    } else {
                        return new String[] { content.substring(0, i),
                                content.substring(i) };
                    }
                }
            }
            return new String[] { content, "" }; //$NON-NLS-1$
        }

        /**
         * Gets the context resource.
         * 
         * @return the resource.
         */
        public Resource getResource() {
            return resource;
        }

        /**
         * Sets the context resource.
         * 
         * @param resource
         *            the resource to set.
         */
        public void setResource(Resource resource) {
            this.resource = resource;
        }
    }

    /**
     * Content proposal for parameter (holds a reference to the parameter).
     * 
     * @author jarciuch
     * @since 19 Mar 2015
     */
    public static class ParamContentProposal extends ContentProposal {

        private Parameter param;

        /**
         * {@inheritDoc}
         * 
         * @param param
         *            the context resource parameter.
         */
        public ParamContentProposal(String content, String label,
                String description, int cursorPosition, Parameter param) {
            super(content, label, description, cursorPosition);
            this.param = param;
        }

        /**
         * @return the context resource parameter.
         */
        public Parameter getParam() {
            return param;
        }

    }
}
