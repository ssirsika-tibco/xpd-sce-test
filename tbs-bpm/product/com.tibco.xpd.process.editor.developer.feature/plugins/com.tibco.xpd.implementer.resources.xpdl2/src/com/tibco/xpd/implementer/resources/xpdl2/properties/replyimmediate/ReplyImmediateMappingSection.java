/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */
package com.tibco.xpd.implementer.resources.xpdl2.properties.replyimmediate;

import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.viewers.IFilter;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessDestinationUtil;
import com.tibco.xpd.implementer.resources.xpdl2.properties.ActivityMessageMappingCommandFactory;
import com.tibco.xpd.implementer.resources.xpdl2.properties.ActivityMessageWsdlItemProvider;
import com.tibco.xpd.implementer.resources.xpdl2.properties.BaseResponseSection;
import com.tibco.xpd.implementer.resources.xpdl2.properties.ParameterLabelProvider;
import com.tibco.xpd.implementer.resources.xpdl2.properties.filter.WsdlFilter.WsdlDirection;
import com.tibco.xpd.mapper.MapperLabelProvider;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.processeditor.xpdl2.properties.IMappingCommandFactory;
import com.tibco.xpd.processeditor.xpdl2.properties.StandardMappingUtil;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptGrammarFactory;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptableLabelProvider;
import com.tibco.xpd.script.ui.internal.BaseScriptSection;
import com.tibco.xpd.xpdExtension.ReplyImmediateDataMappings;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.Message;
import com.tibco.xpd.xpdl2.util.ReplyActivityUtil;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Mapping source content provider for "Output Process Id" mappings (the reply
 * mappings for a start event configured to reply-immediately).
 * 
 * @author aallway
 * @since 31 Jul 2012
 */
public class ReplyImmediateMappingSection extends BaseResponseSection implements
        IFilter {

    public ReplyImmediateMappingSection() {
        super();
    }

    /**
     * @see com.tibco.xpd.implementer.resources.xpdl2.properties.AbstractEditableMappingSection#getGrammar()
     * 
     * @return
     */
    @Override
    protected String getGrammar() {
        /*
         * FOr now we will force to XPath as that is all that the process engine
         * supprots.
         */
        return ScriptGrammarFactory.XPATH;
    }

    /**
     * @see com.tibco.xpd.implementer.resources.xpdl2.properties.BaseResponseSection#shouldDisableEditGrammarViewer()
     * 
     * @return
     */
    @Override
    protected boolean shouldDisableEditGrammarViewer() {
        /*
         * FOr now we will force to XPath as that is all that the process engine
         * supprots.
         */
        return true;
    }

    /**
     * @see com.tibco.xpd.implementer.resources.xpdl2.properties.AbstractWebServiceMappingSection#getScriptSection()
     * 
     * @return
     */
    @SuppressWarnings("restriction")
    @Override
    protected BaseScriptSection getScriptSection() {
        /* No script mappings anyway. */
        return null;
    }

    /**
     * @see com.tibco.xpd.implementer.resources.xpdl2.properties.BaseResponseSection#createMappingCommandFactory()
     * 
     * @return
     */
    @Override
    protected IMappingCommandFactory createMappingCommandFactory() {
        return new ReplyImmediateMappingCommandFactory();
    }

    /**
     * @see com.tibco.xpd.implementer.resources.xpdl2.properties.BaseResponseSection#createSourceContentProvider()
     * 
     * @return
     */
    @Override
    protected ITreeContentProvider createSourceContentProvider() {
        return new ReplyImmediateMappingSourceContentProvider();
    }

    /**
     * @see com.tibco.xpd.implementer.resources.xpdl2.properties.BaseResponseSection#createTargetContentProvider()
     * 
     * @return
     */
    @Override
    protected ITreeContentProvider createTargetContentProvider() {
        return new ActivityMessageWsdlItemProvider(MappingDirection.OUT,
                DirectionType.IN_LITERAL, WsdlDirection.OUT) {
            /**
             * @see com.tibco.xpd.implementer.resources.xpdl2.properties.ActivityMessageWsdlItemProvider#getDefaultScriptGrammar(com.tibco.xpd.xpdl2.Activity)
             * 
             * @param activity
             * @return
             */
            @Override
            protected String getDefaultScriptGrammar(Activity activity) {
                /*
                 * For the moment at least we must force default grammar to be
                 * XPath as process engine cannot deal with anything else.
                 */
                return ScriptGrammarFactory.XPATH;
            }
        };
    }

    /**
     * @see com.tibco.xpd.implementer.resources.xpdl2.properties.BaseResponseSection#createMapperLabelProvider()
     * 
     * @return
     */
    @Override
    protected MapperLabelProvider createMapperLabelProvider() {

        ILabelProvider labelProvider =
                new ScriptableLabelProvider(new ParameterLabelProvider()) {
                    /**
                     * @see com.tibco.xpd.implementer.resources.xpdl2.properties.ParameterLabelProvider#getText(java.lang.Object)
                     * 
                     * @param element
                     * @return
                     */
                    @Override
                    public String getText(Object element) {
                        /*
                         * Don't display the field name just the label for
                         * special process idf field.
                         */
                        if (element instanceof ConceptPath
                                && StandardMappingUtil.REPLY_IMMEDIATE_PROCESS_ID_FORMALPARAMETER
                                        .equals(((ConceptPath) element)
                                                .getItem())) {
                            return Xpdl2ModelUtil
                                    .getDisplayName(StandardMappingUtil.REPLY_IMMEDIATE_PROCESS_ID_FORMALPARAMETER);
                        }
                        return super.getText(element);
                    }
                };

        return new MapperLabelProvider(labelProvider, labelProvider);

    }

    /**
     * @see com.tibco.xpd.implementer.resources.xpdl2.properties.BaseResponseSection#createMappingContentProvider()
     * 
     * @return
     */
    @Override
    protected IStructuredContentProvider createMappingContentProvider() {
        return new ReplyImmediateMappingContentProvider();
    }

    /**
     * @see com.tibco.xpd.implementer.resources.xpdl2.properties.BaseResponseSection#createSourceFilter()
     * 
     * @return
     */
    @Override
    protected ViewerFilter createSourceFilter() {
        final ViewerFilter superFilter = super.createSourceFilter();

        ViewerFilter processIdConceptPathFilter = new ViewerFilter() {

            @Override
            public boolean select(Viewer viewer, Object parentElement,
                    Object element) {
                if (element instanceof ConceptPath) {
                    if (StandardMappingUtil.REPLY_IMMEDIATE_PROCESS_ID_FORMALPARAMETER
                            .equals(((ConceptPath) element).getItem())) {
                        return true;
                    }
                }

                if (superFilter != null) {
                    return superFilter.select(viewer, parentElement, element);
                }
                return false;
            }
        };

        return processIdConceptPathFilter;
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractFilteredTransactionalSection#select(java.lang.Object)
     * 
     * @param toTest
     * @return
     */
    @Override
    public boolean select(Object toTest) {
        EObject baseSelectObject = getBaseSelectObject(toTest);
        if (baseSelectObject instanceof Activity) {
            if (ReplyActivityUtil
                    .isStartMessageWithReplyImmediate((Activity) baseSelectObject)) {

                /*
                 * Only show when BPM destination is selected. Technically this
                 * probably means that this section contribution should be in N2
                 * feature but it's late in the day for that.
                 */
                if (ProcessDestinationUtil
                        .isBPMDestinationSelected(((Activity) baseSelectObject)
                                .getProcess())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @see com.tibco.xpd.implementer.resources.xpdl2.properties.BaseResponseSection#isValidTransfer(java.lang.Object,
     *      java.lang.Object)
     * 
     * @param source
     * @param target
     * @return
     */
    @Override
    public boolean isValidTransfer(Object source, Object target) {

        /* Don't allow any kind of mapping change when activity is generated. */
        EObject input = getInput();
        if (input instanceof Activity) {
            if (!Xpdl2ModelUtil.isGeneratedRequestActivity((Activity) input)) {
                return super.isValidTransfer(source, target);
            }
        }

        return false;
    }

    /**
     * Mapping command factory for reply immediate start event Output mappings
     * tab.
     * 
     * @author aallway
     * @since 1 Aug 2012
     */
    private static class ReplyImmediateMappingCommandFactory extends
            ActivityMessageMappingCommandFactory {

        public ReplyImmediateMappingCommandFactory() {
            /*
             * Direction is IN because it is from perspecitve of "WSDL DATA" so
             * in a "reply to service" mapping we are mapping OUT from process
             * data IN to WSDL output message data.
             */
            super(MappingDirection.IN);
        }

        /**
         * @see com.tibco.xpd.implementer.resources.xpdl2.properties.ActivityMessageMappingCommandFactory#getScriptGrammar(com.tibco.xpd.xpdl2.Activity,
         *      com.tibco.xpd.xpdl2.DirectionType)
         * 
         * @param act
         * @param dir
         * @return
         */
        @Override
        protected String getScriptGrammar(Activity act, DirectionType dir) {
            /* FOr now we will force to XPath */
            return ScriptGrammarFactory.XPATH;
        }

        /**
         * @see com.tibco.xpd.implementer.resources.xpdl2.properties.ActivityMessageMappingCommandFactory#appendAddMappingCommand(org.eclipse.emf.edit.domain.EditingDomain,
         *      org.eclipse.emf.common.command.CompoundCommand,
         *      com.tibco.xpd.xpdl2.Message, com.tibco.xpd.xpdl2.DataMapping,
         *      java.lang.Object, java.lang.Object)
         * 
         * @param ed
         * @param cmd
         * @param message
         * @param mapping
         * @param source
         * @param target
         */
        @Override
        protected void appendAddMappingCommand(EditingDomain ed,
                CompoundCommand cmd, Message message, DataMapping mapping,
                Object source, Object target) {
            // add the reply immediate container if not exist.
            ReplyImmediateDataMappings replyImmediateDataMappings =
                    (ReplyImmediateDataMappings) Xpdl2ModelUtil
                            .getOtherElement(message,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_ReplyImmediateDataMappings());

            if (replyImmediateDataMappings == null) {
                replyImmediateDataMappings =
                        XpdExtensionFactory.eINSTANCE
                                .createReplyImmediateDataMappings();
                cmd.append(Xpdl2ModelUtil.getAddOtherElementCommand(ed,
                        message,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_ReplyImmediateDataMappings(),
                        replyImmediateDataMappings));
            }

            cmd.append(AddCommand.create(ed,
                    replyImmediateDataMappings,
                    XpdExtensionPackage.eINSTANCE
                            .getReplyImmediateDataMappings_DataMappings(),
                    mapping));
        }

        /**
         * @see com.tibco.xpd.implementer.resources.xpdl2.properties.ActivityMessageMappingCommandFactory#appendRemoveDataMappingCommand(org.eclipse.emf.edit.domain.EditingDomain,
         *      org.eclipse.emf.common.command.CompoundCommand,
         *      com.tibco.xpd.xpdl2.Message, org.eclipse.emf.ecore.EObject)
         * 
         * @param ed
         * @param cmd
         * @param message
         * @param dataMapping
         */
        @Override
        protected void appendRemoveDataMappingCommand(EditingDomain ed,
                CompoundCommand cmd, Message message, EObject dataMapping) {
            /*
             * Remove the whole ReplyImmediateDataMappings container if this is
             * last mapping.
             */
            if (dataMapping.eContainer() instanceof ReplyImmediateDataMappings) {
                ReplyImmediateDataMappings replyImmediateDataMappings =
                        (ReplyImmediateDataMappings) dataMapping.eContainer();

                if (replyImmediateDataMappings.getDataMappings().size() == 1) {
                    cmd.append(Xpdl2ModelUtil
                            .getRemoveOtherElementCommand(ed,
                                    message,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_ReplyImmediateDataMappings(),
                                    replyImmediateDataMappings));
                    return;
                }
            }

            /* Else just remove the mapping in the usual way. */
            cmd.append(RemoveCommand.create(ed, dataMapping));
        }

    }

}
