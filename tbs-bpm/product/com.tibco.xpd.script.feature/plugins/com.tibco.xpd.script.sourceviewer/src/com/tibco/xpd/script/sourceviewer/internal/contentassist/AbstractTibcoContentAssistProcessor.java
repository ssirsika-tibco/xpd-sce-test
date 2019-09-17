package com.tibco.xpd.script.sourceviewer.internal.contentassist;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Vector;

import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.Region;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.jface.text.contentassist.IContextInformation;
import org.eclipse.jface.text.contentassist.IContextInformationValidator;
import org.eclipse.jface.text.templates.Template;
import org.eclipse.jface.text.templates.TemplateCompletionProcessor;
import org.eclipse.jface.text.templates.TemplateContext;
import org.eclipse.jface.text.templates.TemplateException;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;
import org.eclipse.wst.sse.core.internal.provisional.IndexedRegion;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import com.tibco.xpd.script.model.JsConsts;
import com.tibco.xpd.script.model.client.AbstractJsMethod;
import com.tibco.xpd.script.model.client.DefaultJsClass;
import com.tibco.xpd.script.model.client.DefaultScriptRelevantData;
import com.tibco.xpd.script.model.client.DefaultScriptRelevantDataFactory;
import com.tibco.xpd.script.model.client.DefaultUMLScriptRelevantData;
import com.tibco.xpd.script.model.client.IMultipleJsClassResolver;
import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.script.model.client.ITypeResolver;
import com.tibco.xpd.script.model.client.IUMLScriptRelevantData;
import com.tibco.xpd.script.model.client.JsAttribute;
import com.tibco.xpd.script.model.client.JsClass;
import com.tibco.xpd.script.model.client.JsClassDefinitionReader;
import com.tibco.xpd.script.model.client.JsMethod;
import com.tibco.xpd.script.model.client.JsMethodParam;
import com.tibco.xpd.script.model.client.JsReference;
import com.tibco.xpd.script.model.internal.client.DefaultJsClassMultiple;
import com.tibco.xpd.script.model.internal.client.IGlobalDataDefinitionReader;
import com.tibco.xpd.script.model.internal.client.IJsElementExt;
import com.tibco.xpd.script.model.internal.client.ITypeResolution;
import com.tibco.xpd.script.model.internal.client.ITypeResolverProvider;
import com.tibco.xpd.script.model.internal.client.IUMLElement;
import com.tibco.xpd.script.model.internal.client.PojoJsMethodParam;
import com.tibco.xpd.script.model.jscript.JScriptGenericsService;
import com.tibco.xpd.script.model.jscript.JScriptUtils;
import com.tibco.xpd.script.parser.validator.IValidationStrategy;
import com.tibco.xpd.script.sourceviewer.client.IScriptInfoProvider;
import com.tibco.xpd.script.sourceviewer.internal.client.IDisposable;
import com.tibco.xpd.script.sourceviewer.internal.preferences.AbstractScriptCommonUIPreferenceNames;
import com.tibco.xpd.script.sourceviewer.internal.util.PreferenceUtils;

public abstract class AbstractTibcoContentAssistProcessor
        extends TemplateCompletionProcessor implements IContentAssistProcessor,
        IPropertyChangeListener, IDisposable {

    protected static final String START_CLASS = "MyStartClass"; //$NON-NLS-1$

    protected String errorMessage = null;

    protected IPreferenceStore fPreferenceStore = null;

    protected char completionProposalAutoActivationCharacters[] = null;

    protected char contextInformationAutoActivationCharacters[] = null;

    private IScriptInfoProvider scriptInfoProvider;

    private AbstractScriptCommonUIPreferenceNames scriptCommonUIPreferenceNames =
            null;

    public AbstractTibcoContentAssistProcessor() {
        init();
    }

    protected ICompletionProposal[] getProposalForConfiguredClasses(
            ITextViewer viewer, String text, String prefix,
            int documentOffset) {
        ICompletionProposal[] proposals =
                computeCompletionProposals(viewer, prefix, documentOffset);
        return proposals;
    }

    protected ICompletionProposal[] templateComputeCompletionProposals(
            ITextViewer viewer, int offset) {
        ITextSelection selection =
                (ITextSelection) viewer.getSelectionProvider().getSelection();
        if (selection.getOffset() == offset) {
            offset = selection.getOffset() + selection.getLength();
        }
        String prefix = extractPrefix(viewer, offset);
        String indent = extractIndent(viewer, offset - prefix.length());
        IRegion region = new Region(offset - prefix.length(), prefix.length());
        TemplateContext context = createContext(viewer, region);
        if (context == null) {
            return new ICompletionProposal[0];
        }
        context.setVariable("selection", selection.getText()); //$NON-NLS-1$
        Template templates[] = getTemplates(context.getContextType().getId());
        List<ICompletionProposal> matches =
                new ArrayList<ICompletionProposal>();
        for (int i = 0; i < templates.length; i++) {
            Template template = prepareTemplate(templates[i], indent);
            try {
                context.getContextType().validate(template.getPattern());
            } catch (TemplateException _ex) {
                continue;
            }
            if (template.getName().startsWith(prefix))
                matches.add(createProposal(template,
                        context,
                        region,
                        getRelevance(template, prefix)));
        }

        return matches.toArray(new ICompletionProposal[matches.size()]);
    }

    private Template prepareTemplate(Template template, String indent) {
        if (indent.length() == 0)
            return template;
        String newPattern = template.getPattern();
        StringBuffer sb = new StringBuffer();
        int i;
        for (i = 0; i < newPattern.length() - 1; i++) {
            char c = newPattern.charAt(i);
            char next = newPattern.charAt(i + 1);
            if ((c == '\n' || c == '\r') && next != '\n' && next != '\r') {
                sb.append(c).append(indent);
            } else {
                sb.append(c);
            }
        }
        sb.append(newPattern.charAt(i));
        newPattern = sb.toString();
        Template t = new Template(template.getName(), template.getDescription(),
                template.getContextTypeId(), newPattern,
                template.isAutoInsertable());
        return t;
    }

    protected String extractIndent(ITextViewer viewer, int offset) {
        int i;
        IDocument document;
        i = offset;
        document = viewer.getDocument();
        if (i > document.getLength())
            return ""; //$NON-NLS-1$

        try {
            for (; i > 0; i--) {
                char ch;
                ch = document.getChar(i - 1);
                if (ch != ' ' && ch != '\t')
                    break;
            }
            if (i > 0 && (document.getChar(i - 1) == '\n'
                    || document.getChar(i - 1) == '\r')) {
                return document.get(i, offset - i);
            } else {
                return ""; //$NON-NLS-1$
            }
        } catch (BadLocationException e) {
            return ""; //$NON-NLS-1$
        }
    }

    @Override
    public IContextInformation[] computeContextInformation(ITextViewer viewer,
            int offset) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IContextInformationValidator getContextInformationValidator() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public char[] getCompletionProposalAutoActivationCharacters() {
        return completionProposalAutoActivationCharacters;
    }

    @Override
    public char[] getContextInformationAutoActivationCharacters() {
        return contextInformationAutoActivationCharacters;
    }

    @Override
    public String getErrorMessage() {
        return errorMessage;
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        String property = event.getProperty();

        String autoProposeKey = ""; //$NON-NLS-1$
        String autoProposeCodeKey = ""; //$NON-NLS-1$
        AbstractScriptCommonUIPreferenceNames commonUIPreferenceNames =
                getCommonUIPreferenceNames();
        if (commonUIPreferenceNames != null) {
            autoProposeKey = commonUIPreferenceNames.getAutoProposeKey();
            autoProposeCodeKey =
                    commonUIPreferenceNames.getAutoProposeCodeKey();
        }
        if (property.compareTo(autoProposeKey) == 0
                || property.compareTo(autoProposeCodeKey) == 0) {
            reinit();
        }

    }

    protected abstract AbstractScriptCommonUIPreferenceNames getCommonUIPreferenceNames();

    @Override
    public void dispose() {
        getPreferenceStore().removePropertyChangeListener(this);
    }

    protected IPreferenceStore getPreferenceStore() {
        if (fPreferenceStore == null) {
            fPreferenceStore = PreferenceUtils.getPreferenceStore();
        }
        return fPreferenceStore;
    }

    public class ProposalComparator implements Comparator {

        @Override
        public int compare(Object o1, Object o2) {
            /*
             * XPD-5535: Saket: Sort the content assist list by the type of
             * object (e.g. attribute, method etc). If both the objects are of
             * same type, then sort them alphabetically.
             */
            if (o1 instanceof CustomCompletionProposal
                    && o2 instanceof CustomCompletionProposal) {
                CustomCompletionProposal a1 = (CustomCompletionProposal) o1;
                CustomCompletionProposal a2 = (CustomCompletionProposal) o2;

                int r1 = a1.getRelevance();
                int r2 = a2.getRelevance();

                if (r1 != r2) {
                    return (r1 - r2);
                }
            }
            ICompletionProposal b1 = (ICompletionProposal) o1;
            ICompletionProposal b2 = (ICompletionProposal) o2;
            return b1.getDisplayString().compareTo(b2.getDisplayString());
        }

        public ProposalComparator() {
            super();
        }
    }

    protected void init() {
        getPreferenceStore().addPropertyChangeListener(this);
        reinit();
    }

    protected void reinit() {
        String autoProposeKey = "";//$NON-NLS-1$
        AbstractScriptCommonUIPreferenceNames commonUIPreferenceNames =
                getCommonUIPreferenceNames();
        if (commonUIPreferenceNames != null) {
            autoProposeKey = commonUIPreferenceNames.getAutoProposeKey();
        }
        boolean doAuto = getPreferenceStore().getBoolean(autoProposeKey);
        if (doAuto) {
            String autoProposeCodeKey = "";//$NON-NLS-1$
            if (commonUIPreferenceNames != null) {
                autoProposeCodeKey =
                        commonUIPreferenceNames.getAutoProposeCodeKey();
            }
            completionProposalAutoActivationCharacters = getPreferenceStore()
                    .getString(autoProposeCodeKey).toCharArray();
        } else {
            completionProposalAutoActivationCharacters = null;
        }
    }

    public void setScriptInfoProvider(IScriptInfoProvider scriptInfoProvider) {
        this.scriptInfoProvider = scriptInfoProvider;
    }

    public IScriptInfoProvider getScriptInfoProvider() {
        return this.scriptInfoProvider;
    }

    private List<JsClassDefinitionReader> definitionReaderList =
            Collections.EMPTY_LIST;

    public void setClassDefinitionReaders(
            List<JsClassDefinitionReader> definitionReaderList) {
        this.definitionReaderList = definitionReaderList;
    }

    public List<JsClassDefinitionReader> getClassDefinitionReaders() {
        return this.definitionReaderList;
    }

    public List<ITypeResolver> getTypeResolvers() {
        List<ITypeResolver> typeResolvers = new ArrayList<ITypeResolver>();
        if (getClassDefinitionReaders() != null
                && !getClassDefinitionReaders().isEmpty()) {
            Set<ITypeResolverProvider> typeResolverProviders = JScriptUtils
                    .getTypeResolverProviders(getClassDefinitionReaders());
            if (typeResolverProviders != null
                    && !typeResolverProviders.isEmpty()) {
                typeResolvers.addAll(JScriptUtils.getTypeResolvers(
                        new ArrayList(typeResolverProviders)));
            }
        }
        return typeResolvers;
    }

    private List<IValidationStrategy> validationStrategyList =
            Collections.EMPTY_LIST;

    public void setValidationStrategyList(
            List<IValidationStrategy> validationStrategyList) {
        this.validationStrategyList = validationStrategyList;
    }

    public List<IValidationStrategy> getValidationStrategyList() {
        return this.validationStrategyList;
    }

    public ICompletionProposal[] computeCompletionProposals(ITextViewer viewer,
            String prefix, int documentPosition) {
        org.w3c.dom.Node node = null;
        IndexedRegion indexedNode = null;
        /*
         * if (viewer instanceof StructuredTextViewer) { indexedNode =
         * ContentAssistUtils.getNodeAt( (StructuredTextViewer) viewer,
         * documentPosition); }
         */
        if (indexedNode instanceof Node) {
            node = (Node) indexedNode;
        }
        if (prefix == null || prefix.length() == 0)
        // do not recompute if prefix has a value other than null or blank
        {
            prefix = getStringToComplete(viewer, documentPosition, node);
        }
        if (prefix == null) {
            return null;
        }
        Vector props = new Vector();
        props = getProposalVector(viewer,
                prefix,
                documentPosition,
                indexedNode);
        java.util.Enumeration elements = props.elements();
        ICompletionProposal[] result = null;
        java.util.SortedMap tm = new java.util.TreeMap();
        for (int i = props.size(); i-- > 0;) {
            ICompletionProposal cp =
                    (ICompletionProposal) elements.nextElement();
            tm.put(cp.getDisplayString(), cp);
        }
        int tmsize = tm.values().size();
        result = new ICompletionProposal[tmsize];
        Iterator iter = tm.values().iterator();
        int i = 0;
        while (iter.hasNext()) {
            result[i++] = (ICompletionProposal) iter.next();
        }
        return result;
    }

    public MyFollowClass getMyFollowPropertyClass(JsAttribute jsAttribute,
            String propertyName, String dataType, boolean isArray) {
        MyFollowClass toReturn = null;
        List<IScriptRelevantData> resolvedTypes =
                new ArrayList<IScriptRelevantData>();
        if (propertyName != null && dataType != null) {
            // Script Editor enhancements for TIBCO Forms
            IScriptRelevantData scriptRelevantData = jsAttribute == null ? null
                    : jsAttribute.getScriptRelevantData();

            if (scriptRelevantData != null) {
                resolvedTypes.add(scriptRelevantData);
            }
            if ((jsAttribute == null) || (scriptRelevantData == null)) {

                resolvedTypes = JScriptUtils.resolveType(jsAttribute,
                        getTypeResolvers(),
                        isArray,
                        null);
            }
            toReturn = addCompletionStringNodes(resolvedTypes, toReturn);
            if (toReturn != null) {

                DefaultScriptRelevantData genericContext = new DefaultScriptRelevantData(
                        propertyName, dataType, isArray);
                genericContext.setExtendedInfo(jsAttribute);
                toReturn.setGenericContext(genericContext);
            }
        }
        return toReturn;
    }

    protected MyFollowClass getMyFollowAttributeClass(JsAttribute jsAttribute) {
        MyFollowClass toReturn = null;
        if (jsAttribute != null) {
            // Script Editor enhancements for TIBCO Forms
            toReturn = getMyFollowPropertyClass(jsAttribute,
                    jsAttribute.getName(),
                    jsAttribute.getDataType(),
                    jsAttribute.isMultiple());
        }
        return toReturn;
    }

    /**
     * This method replaces the last method as will try to load the class which
     * has not been configured
     * 
     * @param jsMethod
     * @return
     */
    protected MyFollowClass getMyFollowMethodClass(JsMethod jsMethod,
            IScriptRelevantData genericContext) {
        MyFollowClass toReturn = null;
        if (jsMethod != null) {
            JsMethodParam parameter = jsMethod.getReturnType();
            if (parameter != null) {
                JScriptGenericsService gs = new JScriptGenericsService();
                Type type = null;
                String dataType;
                if (gs.isGeneric(parameter)) {
                    Map<String, Type> typeMap = gs.createTypeMap(genericContext, parameter);
                    type = typeMap.get(parameter.getType());
                    if (type instanceof PrimitiveType) {
                        dataType = type.getName();
                    } else if (type != null) {
                        dataType = type.getQualifiedName().replace("::", "."); //$NON-NLS-1$ //$NON-NLS-2$
                    } else {
                        dataType = JsConsts.UNDEFINED_DATA_TYPE;
                    }
                } else {
                    type = parameter.getUMLParameter().getType();
                    dataType = type != null ? type.getName() : null;
                }
                String propertyName = jsMethod.getName();
                boolean isArray = jsMethod.isMultiple();
                Parameter umlParameter = parameter.getUMLParameter();
                if (umlParameter != null
                        || parameter instanceof PojoJsMethodParam
                        || parameter instanceof IUMLElement) {
                    if (propertyName != null && dataType != null) {
                        List<IScriptRelevantData> resolvedTypes =
                                new ArrayList<IScriptRelevantData>();
                        // Script Editor enhancements for TIBCO Forms
                        IScriptRelevantData scriptRelevantData =
                                parameter.getScriptRelevantData();
                        if (scriptRelevantData == null) {
                            resolvedTypes = JScriptUtils.resolveType(jsMethod,
                                    getTypeResolvers(),
                                    isArray,
                                    genericContext);
                            if (isArray) {
                                List<IScriptRelevantData> resolveContext =
                                        JScriptUtils.resolveType(jsMethod,
                                                getTypeResolvers(),
                                                false,
                                                genericContext);
                                if (resolveContext != null
                                        && !resolveContext.isEmpty()) {
                                    genericContext = resolveContext
                                            .listIterator().next();
                                }
                            }
                        } else {
                            resolvedTypes =
                                    JScriptUtils.resolveType(scriptRelevantData,
                                            getTypeResolvers(),
                                            isArray,
                                            genericContext);
                            if (isArray) {
                                List<IScriptRelevantData> resolveContext =
                                        JScriptUtils.resolveType(
                                                scriptRelevantData,
                                                getTypeResolvers(),
                                                false,
                                                genericContext);
                                if (resolveContext != null
                                        && !resolveContext.isEmpty()) {
                                    genericContext = resolveContext
                                            .listIterator().next();
                                }
                            }
                        }
                        toReturn = addCompletionStringNodes(resolvedTypes,
                                toReturn);
                        if (type instanceof Class) {
                            Class umlClass =
                                    (Class) type;
                            if (umlClass != null
                                    && !JScriptUtils.isGenericType(dataType)
                                    && !JScriptUtils
                                            .isContextlessClass(umlClass)) {
                                genericContext =
                                        new DefaultScriptRelevantDataFactory()
                                                .createUMLScriptRelevantData(
                                                        dataType,
                                                        false,
                                                        umlClass,
                                                        null,
                                                        false,
                                                        umlParameter);
                            }
                        }
                    }
                } else if (jsMethod instanceof AbstractJsMethod) {
                    List<IScriptRelevantData> resolvedTypes =
                            new ArrayList<IScriptRelevantData>();
                    resolvedTypes = JScriptUtils.resolveType(jsMethod,
                            getTypeResolvers(),
                            isArray,
                            genericContext);
                    toReturn =
                            addCompletionStringNodes(resolvedTypes, toReturn);
                } else {
                    // Script Editor enhancements for TIBCO Forms
                    toReturn = getMyFollowPropertyClass(null,
                            jsMethod.getName(),
                            parameter.getType(),
                            jsMethod.isMultiple());
                }
            }
        }
        if (toReturn != null) {
            toReturn.setGenericContext(genericContext);
        }
        return toReturn;
    }

    protected MyFollowClass getMyFollowReferenceClass(JsReference jsReference,
            boolean isExpressionArray, IScriptRelevantData genericContext) {
        MyFollowClass toReturn = null;
        if (jsReference != null) {
            boolean isArray = jsReference.isMultiple();
            if (isExpressionArray && isArray) {
                isArray = false;
            }
            List<IScriptRelevantData> resolvedTypes =
                    JScriptUtils.resolveType(jsReference,
                            getTypeResolvers(),
                            isArray,
                            genericContext);
            toReturn = addCompletionStringNodes(resolvedTypes, toReturn);
            JsClass referencedJsClass = jsReference.getReferencedJsClass();
            if (referencedJsClass != null && toReturn != null) {
                toReturn.setGenericContext(new DefaultUMLScriptRelevantData(
                        jsReference.getName(), referencedJsClass.getName(),
                        false, referencedJsClass));
            }
        }
        return toReturn;
    }

    private MyFollowClass addCompletionStringNodes(
            List<IScriptRelevantData> resolvedTypes, MyFollowClass toReturn) {
        if (resolvedTypes != null && !resolvedTypes.isEmpty()) {
            for (IScriptRelevantData resolvedType : resolvedTypes) {
                if (resolvedType instanceof IUMLScriptRelevantData) {
                    IUMLScriptRelevantData umlScriptRelevantData =
                            (IUMLScriptRelevantData) resolvedType;
                    if (umlScriptRelevantData != null) {
                        JsClass jsClass = umlScriptRelevantData.getJsClass();
                        if (jsClass != null) {
                            toReturn = addMyCompletionStringNodes(jsClass,
                                    toReturn,
                                    false);
                        }
                    }
                }
            }
        }
        return toReturn;
    }

    protected IUMLScriptRelevantData getRegisteredScriptRelevantDataForClassName(
            String className) {
        IUMLScriptRelevantData scriptRelevantData = null;
        if (getGlobalUMLScriptRelevantClass(className) != null) {
            scriptRelevantData = getGlobalUMLScriptRelevantClass(className);
            if (scriptRelevantData != null) {
                return scriptRelevantData;
            }
        } else if (getUMLScriptRelevantClass(className) != null) {
            scriptRelevantData = getUMLScriptRelevantClass(className);
            if (scriptRelevantData != null) {
                return scriptRelevantData;
            }
        } else if (getLocalUMLScriptRelevantClass(className) != null) {
            scriptRelevantData = getLocalUMLScriptRelevantClass(className);
            if (scriptRelevantData != null) {
                return scriptRelevantData;
            }
        }
        return null;
    }

    private boolean isLocalUMLScriptRelevantClass(String className) {
        if (getLocalUMLScriptRelevantClass(className) != null) {
            return true;
        }
        return false;
    }

    /**
     * For a uml class name returns the list of possible completion String
     * nodes. These list will be all the attributes and methods of the class .
     * The class should be present in the passed list of relevant data.
     * 
     * @param className
     *            The name of the class.
     * @return The MyFollowClass that contains a list of MyCompletionStringNode.
     */
    protected MyFollowClass getMyFollowClass(String className,
            boolean isExpressionArray) {
        return getMyFollowClass(className, null, isExpressionArray, null);
    }

    /**
     * For a uml class name returns the list of possible completion String
     * nodes. These list will be all the attributes and methods of the class .
     * The class should be present in the passed list of relevant data.
     * 
     * @param className
     *            The name of the class.
     * @return The MyFollowClass that contains a list of MyCompletionStringNode.
     */
    protected MyFollowClass getMyFollowClass(String className,
            MyFollowClass startMyFollowClass, boolean isExpressionArray,
            IScriptRelevantData genericContext) {
        MyFollowClass toReturn = null;
        if (startMyFollowClass == null) {
            startMyFollowClass = getStartMyFollowClass();
        }
        List<MyCompletionStringNode> completionNodes =
                startMyFollowClass.getCompletionNodes();
        for (MyCompletionStringNode myCompletionStringNode : completionNodes) {
            if (className
                    .equals(myCompletionStringNode.getCompletionString())) {
                JsClass jsClass = myCompletionStringNode.getJsClass();
                if (jsClass != null) {
                    toReturn =
                            addMyCompletionStringNodes(jsClass, toReturn, true);
                } else {
                    IUMLScriptRelevantData registeredScriptRelevantData =
                            getRegisteredScriptRelevantDataForClassName(
                                    className);
                    toReturn = new MyFollowClass(className);
                    if (registeredScriptRelevantData != null) {
                        boolean isArray =
                                registeredScriptRelevantData.isArray();
                        if (isArray && isExpressionArray) {
                            isArray = false;
                        }
                        List<IScriptRelevantData> resolvedTypes = JScriptUtils
                                .resolveType(registeredScriptRelevantData,
                                        getTypeResolvers(),
                                        isArray,
                                        genericContext);
                        toReturn = addCompletionStringNodes(resolvedTypes,
                                toReturn);
                        IScriptRelevantData newGenericContext = null;
                        if (isLocalUMLScriptRelevantClass(className)) {
                            newGenericContext =
                                    JScriptUtils.getCurrentGenericContext(
                                            registeredScriptRelevantData);
                        } else {
                            newGenericContext = registeredScriptRelevantData;
                        }

                        toReturn.setGenericContext(newGenericContext);
                    } else if (myCompletionStringNode.isMultiple()) {
                        // ACE-1322 Untyped temporary array
                        Class arrClass = JScriptUtils.getDefaultMultipleClass();
                        DefaultJsClassMultiple arrJsClass = new DefaultJsClassMultiple(arrClass);
                        IScriptRelevantData srd = JScriptUtils.getScriptRelevantData(arrJsClass, className, true);
                        toReturn = addCompletionStringNodes(Collections.singletonList(srd), toReturn);
                    }
                }
            }
        }
        return toReturn;
    }

    private MyFollowClass addMyCompletionStringNodes(JsClass jsClass,
            MyFollowClass toReturn, boolean isStaticReference) {
        if (jsClass != null) {
            // if is static reference only should show static properties
            if (toReturn == null) {
                toReturn = new MyFollowClass(jsClass.getName());
            }
            List<JsAttribute> attributeList = jsClass.getAttributeList();
            for (JsAttribute attribute : attributeList) {
                boolean isAttributeStatic = false;
                if (attribute instanceof IJsElementExt) {
                    isAttributeStatic = ((IJsElementExt) attribute).isStatic();
                }
                if (isStaticReference) {
                    // Only show static properties
                    if (isAttributeStatic) {
                        toReturn.addMyCompletionStringNode(
                                new MyCompletionStringNode(attribute));
                    }
                } else {
                    toReturn.addMyCompletionStringNode(
                            new MyCompletionStringNode(attribute));
                }
            }
            List<JsReference> referenceList = jsClass.getReferenceList();
            for (JsReference reference : referenceList) {
                boolean isAttributeStatic = false;
                if (reference instanceof IJsElementExt) {
                    isAttributeStatic = ((IJsElementExt) reference).isStatic();
                }
                if (isStaticReference) {
                    // Only show static properties
                    if (isAttributeStatic) {
                        toReturn.addMyCompletionStringNode(
                                new MyCompletionStringNode(reference));
                    }
                } else {
                    toReturn.addMyCompletionStringNode(
                            new MyCompletionStringNode(reference));
                }
            }
            Collection<JsMethod> methodList = jsClass.getMethodList();
            for (JsMethod method : methodList) {
                boolean isAttributeStatic = false;
                if (method instanceof IJsElementExt) {
                    isAttributeStatic = ((IJsElementExt) method).isStatic();
                }
                if (isStaticReference) {
                    // Only show static properties
                    if (isAttributeStatic) {
                        toReturn.addMyCompletionStringNode(
                                new MyCompletionStringNode(method));
                    }
                } else {
                    toReturn.addMyCompletionStringNode(
                            new MyCompletionStringNode(method));
                }
            }
        }
        return toReturn;
    }

    /**
     * @param cl
     * @param allAttributes
     */
    private void collectAllClassAttributes(Class cl,
            List<Property> allAttributes) {

        if (null != cl) {
            allAttributes.addAll(cl.getOwnedAttributes());

            if (!JScriptUtils.isClassXsdComplexTypeRestriction(cl)) {
                /* Collect attributes and recurse if necessary */
                EList<Classifier> generals = cl.getGenerals();
                if (!generals.isEmpty()) {
                    Classifier classifier = generals.get(0);
                    if (classifier instanceof Class) {
                        collectAllClassAttributes((Class) classifier,
                                allAttributes);
                    }
                }
            }
        }
    }

    // private MyFollowClass addMyCompletionStringNodes(List<JsClass> jsClasses,
    // MyFollowClass toReturn) {
    // if (jsClasses != null && !jsClasses.isEmpty()) {
    // if (toReturn == null) {
    // toReturn = new MyFollowClass(JsConsts.MULTIPLE_CONSTRAINT_NAME);
    // }
    // for (JsClass jsClass : jsClasses) {
    // List<JsAttribute> attributeList = jsClass.getAttributeList();
    // for (JsAttribute attribute : attributeList) {
    // toReturn
    // .addMyCompletionStringNode(new MyCompletionStringNode(
    // attribute));
    // }
    // List<JsReference> referenceList = jsClass.getReferenceList();
    // for (JsReference reference : referenceList) {
    // toReturn
    // .addMyCompletionStringNode(new MyCompletionStringNode(
    // reference));
    // }
    // Collection<JsMethod> methodList = jsClass.getMethodList();
    // for (JsMethod method : methodList) {
    // toReturn
    // .addMyCompletionStringNode(new MyCompletionStringNode(
    // method));
    // }
    // }
    // }
    // return toReturn;
    // }
    //
    // private List<JsClass> getJsMultipleClasses() {
    // Collection<JsClass> readContributedClass = readContributedClass();
    // if (readContributedClass != null) {
    // List<JsClass> jsClassMultiples =
    // new ArrayList<JsClass>();
    // for (JsClass jsClass : readContributedClass) {
    // if (jsClass instanceof JsClassMultiple) {
    // jsClassMultiples.add((JsClass) jsClass);
    // }
    // }
    // return jsClassMultiples;
    // }
    // return Collections.emptyList();
    // }

    private IUMLScriptRelevantData getUMLRelevantClass(String dataName,
            Map<String, IScriptRelevantData> umlDataMap) {
        IUMLScriptRelevantData umlDataClass = null;
        if (umlDataMap == null) {
            return null;
        }
        Set<Entry<String, IScriptRelevantData>> entrySet =
                umlDataMap.entrySet();
        for (Entry<String, IScriptRelevantData> entry : entrySet) {
            String key = entry.getKey();
            IScriptRelevantData data = entry.getValue();
            if (data instanceof IUMLScriptRelevantData) {
                IUMLScriptRelevantData umlData = (IUMLScriptRelevantData) data;
                if (umlData.isArray()) {
                    key = getArrayContentAssistName(key);
                }
                if (key.equals(dataName)) {
                    // just name of the data has matched
                    umlDataClass = umlData;
                    break;
                }
            } else if (data instanceof ITypeResolution) {
                // TODO handle 2 or more types resolutions
                List<IScriptRelevantData> typesResolution =
                        ((ITypeResolution) data).getTypesResolution();
                if (typesResolution != null) {
                    IScriptRelevantData resolution =
                            typesResolution.iterator().next();
                    if (resolution instanceof IUMLScriptRelevantData) {
                        IUMLScriptRelevantData umlData =
                                (IUMLScriptRelevantData) resolution;
                        if (umlData.isArray()) {
                            key = getArrayContentAssistName(key);
                        }
                        if (key.equals(dataName)) {
                            // just name of the data has matched
                            umlDataClass = umlData;
                            break;
                        }
                    }
                }
            }
        }
        return umlDataClass;
    }

    private IUMLScriptRelevantData getGlobalUMLScriptRelevantClass(
            String dataName) {
        Map<String, IScriptRelevantData> umlDataList = getGlobalScriptDataMap();
        return getUMLRelevantClass(dataName, umlDataList);
    }

    private IUMLScriptRelevantData getUMLScriptRelevantClass(String dataName) {
        Map<String, IScriptRelevantData> umlDataMap = getScriptDataAsMap();
        return getUMLRelevantClass(dataName, umlDataMap);
    }

    private IUMLScriptRelevantData getLocalUMLScriptRelevantClass(
            String dataName) {
        Map<String, IScriptRelevantData> umlDataMap = getLocalScriptDataMap();
        return getUMLRelevantClass(dataName, umlDataMap);
    }

    /**
     * given the existing string to complete, return a list of completions
     * 
     * @param estring
     *            the string in the document at which the cursor is at the end
     *            of and which we are to complete
     * @param docPosition
     *            the position in the document that the cursor and the end of
     *            the estring are at.
     */
    protected Vector getProposalVector(ITextViewer viewer, String estring,
            int docPosition, IndexedRegion idxnodeScript) {
        MyFollowClass myFollowClass = getStartMyFollowClass();
        return getProposalVector(viewer, estring, docPosition, myFollowClass);
    }

    /**
     * give the next position of either of the two characters starting at the
     * given index of the given string
     * 
     * @param estring
     *            the string to search
     * @param sidx
     *            index at which to start the search
     * @param ch1
     *            one of the characters to search for
     * @param ch2
     *            one of the characters to search for
     */
    protected static int strNextPos(String estring, int sidx, char ch1,
            char ch2) {
        int idx1;
        int idx11 = estring.indexOf(ch1, sidx);
        int idx12 = estring.indexOf(ch2, sidx);
        if (idx11 == -1) {
            idx1 = idx12;
        } else if (idx12 == -1) {
            idx1 = idx11;
        } else {
            idx1 = (idx11 < idx12) ? idx11 : idx12;
        }
        return idx1;
    }

    /**
     * give the position of the last found character starting at the given index
     * of the given string backwards
     * 
     * @param estring
     *            the string to search
     * @param sidx
     *            index at which to start the search
     * @param ch1
     *            one of the characters to search for
     * @param ch2
     *            one of the characters to search for
     */
    protected static int strLastPos(String estring, int sidx, char ch1,
            char ch2) {
        int idx1;
        int idx11 = estring.lastIndexOf(ch1, sidx);
        int idx12 = estring.lastIndexOf(ch2, sidx);
        if (idx11 == -1) {
            idx1 = idx12;
        } else if (idx12 == -1) {
            idx1 = idx11;
        } else {
            idx1 = (idx11 < idx12) ? idx11 : idx12;
        }
        return idx1;
    }

    protected Collection<JsClass> readContributedClass() {
        Collection<JsClass> allSupporteClasses = new ArrayList<JsClass>();
        List<JsClassDefinitionReader> jsClassDefReader =
                getClassDefinitionReaders();
        for (JsClassDefinitionReader classDefinitionReader : jsClassDefReader) {
            if (classDefinitionReader != null) {
                Collection<JsClass> supportedClasses =
                        classDefinitionReader.getSupportedClasses();
                if (supportedClasses != null) {
                    allSupporteClasses.addAll(supportedClasses);
                }
            }
        }
        return allSupporteClasses;
    }

    protected Collection<JsMethod> readContributedGlobalFunctions() {
        Collection<JsMethod> allSupportedGlobalFunctions =
                new ArrayList<JsMethod>();
        List<JsClassDefinitionReader> jsClassProvider =
                getClassDefinitionReaders();
        for (JsClassDefinitionReader classDefinitionReader : jsClassProvider) {
            if (classDefinitionReader instanceof IGlobalDataDefinitionReader) {
                IGlobalDataDefinitionReader globalDataDefinitionReader =
                        (IGlobalDataDefinitionReader) classDefinitionReader;
                List<JsMethod> supportedGlobalMethods =
                        globalDataDefinitionReader.getSupportedGlobalMethods();
                if (supportedGlobalMethods != null) {
                    allSupportedGlobalFunctions.addAll(supportedGlobalMethods);
                }
            }
        }
        return allSupportedGlobalFunctions;
    }

    protected Collection<JsAttribute> readContributedGlobalProperties() {
        Collection<JsAttribute> allSupportedGlobalProperties =
                new ArrayList<JsAttribute>();
        List<JsClassDefinitionReader> jsClassProvider =
                getClassDefinitionReaders();
        for (JsClassDefinitionReader classDefinitionReader : jsClassProvider) {
            if (classDefinitionReader instanceof IGlobalDataDefinitionReader) {
                IGlobalDataDefinitionReader globalDataDefinitionReader =
                        (IGlobalDataDefinitionReader) classDefinitionReader;
                List<JsAttribute> supportedGlobalProperties =
                        globalDataDefinitionReader
                                .getSupportedGlobalProperties();
                if (supportedGlobalProperties != null) {
                    allSupportedGlobalProperties
                            .addAll(supportedGlobalProperties);
                }
            }
        }
        return allSupportedGlobalProperties;
    }

    protected Collection<JsClass> getAllSupportedClasses() {
        Collection<JsClass> allSupportedClasses = new ArrayList<JsClass>();
        allSupportedClasses.addAll(readContributedClass());
        List<IScriptRelevantData> dataList = getScriptDataList();
        if (dataList != null) {
            for (IScriptRelevantData scriptRelevantData : dataList) {
                if (scriptRelevantData instanceof IUMLScriptRelevantData) {
                    IUMLScriptRelevantData uScriptRelevantData =
                            (IUMLScriptRelevantData) scriptRelevantData;
                    if (uScriptRelevantData.getJsClass() != null) {
                        allSupportedClasses
                                .add(uScriptRelevantData.getJsClass());
                    }
                }
            }
        }
        return allSupportedClasses;
    }

    protected MyFollowClass myStartFollowClass = null;

    protected MyFollowClass getStartMyFollowClass() {
        myStartFollowClass = new MyFollowClass(START_CLASS);
        Collection<JsClass> classList = readContributedClass();
        for (JsClass jsClass : classList) {
            if (!JScriptUtils.isAbstract(jsClass)
                    && JScriptUtils.containsStaticFeatures(jsClass)) {
                MyCompletionStringNode myCompletionStringNode =
                        new MyCompletionStringNode(jsClass);
                myStartFollowClass
                        .addMyCompletionStringNode(myCompletionStringNode);
            }
            // adding the behaviour to include the function name and local
            // variables
        }
        Collection<JsMethod> globalFunctionList =
                readContributedGlobalFunctions();
        for (JsMethod jsGlobalFunction : globalFunctionList) {
            if (jsGlobalFunction != null) {
                MyCompletionStringNode myCompletionStringNode =
                        new MyCompletionStringNode(jsGlobalFunction);
                myStartFollowClass
                        .addMyCompletionStringNode(myCompletionStringNode);
            }
        }
        Collection<JsAttribute> globalPropertyList =
                readContributedGlobalProperties();
        for (JsAttribute jsGlobalProperty : globalPropertyList) {
            if (jsGlobalProperty != null) {
                MyCompletionStringNode myCompletionStringNode =
                        new MyCompletionStringNode(jsGlobalProperty);
                myStartFollowClass
                        .addMyCompletionStringNode(myCompletionStringNode);
            }
        }

        if (myStartFollowClass == null) {
            return myStartFollowClass;
        }
        addCustomCompletionString(myStartFollowClass);
        addScriptRelevantData(myStartFollowClass);
        addComplexScriptRelevantData(myStartFollowClass);
        addLocalScriptRelevantData(myStartFollowClass);
        return myStartFollowClass;
    }

    private void addLocalScriptRelevantData(MyFollowClass myFollowClass) {
        Map<String, IScriptRelevantData> dataMap = getLocalScriptDataMap();
        if (dataMap != null) {
            Set<Entry<String, IScriptRelevantData>> entrySet =
                    dataMap.entrySet();
            for (Iterator<Entry<String, IScriptRelevantData>> iterator =
                    entrySet.iterator(); iterator.hasNext();) {
                Entry<String, IScriptRelevantData> next = iterator.next();
                IScriptRelevantData scriptRelevantData = next.getValue();
                String dfName = next.getKey();
                String contentAssistString = dfName;
                boolean isArray = false;
                if (scriptRelevantData.isArray()) {
                    contentAssistString = getArrayContentAssistName(dfName);
                    isArray = true;
                }
                MyCompletionStringNode myCompletionStringNode =
                        new MyCompletionStringNode(contentAssistString,
                                scriptRelevantData.getAdditionalInfo(),
                                scriptRelevantData.getIcon(), isArray);
                myFollowClass.addMyCompletionStringNode(myCompletionStringNode);
            }
        }
    }

    protected void addScriptRelevantData(MyFollowClass myFollowClass) {
        List<IScriptRelevantData> dataList = getScriptDataList();
        if (dataList != null) {
            for (IScriptRelevantData scriptRelevantData : dataList) {
                String dfName = scriptRelevantData.getName();
                boolean isArray = false;
                String contentAssistString = dfName;
                if (scriptRelevantData.isArray()) {
                    contentAssistString = getArrayContentAssistName(dfName);
                    isArray = true;
                }
                MyCompletionStringNode myCompletionStringNode =
                        new MyCompletionStringNode(contentAssistString,
                                scriptRelevantData.getAdditionalInfo(),
                                scriptRelevantData.getIcon(), isArray);

                myFollowClass.addMyCompletionStringNode(myCompletionStringNode);
            }
        }
    }

    protected void addComplexScriptRelevantData(MyFollowClass myFollowClass) {
        List<Node> dataList = getComplexScriptDataList();
        if (dataList != null) {
            for (Node documentNode : dataList) {
                if (documentNode.getFirstChild() != null) {
                    Node node = documentNode.getFirstChild();
                    String dfName = node.getLocalName();
                    String contentAssistString = dfName;
                    // Find the way of knowing if the node is an array
                    /*
                     * if (scriptRelevantData.isArray()) { contentAssistString =
                     * getArrayContentAssistName(dfName); }
                     */
                    MyCompletionStringNode myCompletionStringNode =
                            new MyCompletionStringNode(contentAssistString, "",
                                    getNodeImage(node), false);
                    myFollowClass
                            .addMyCompletionStringNode(myCompletionStringNode);
                }
            }
        }
    }

    protected Image getNodeImage(Node node) {
        return null;
    }

    protected List<IScriptRelevantData> getScriptDataList() {
        ;
        if (getScriptInfoProvider() != null) {
            return getScriptInfoProvider().getScriptRelevantData();
        }
        return Collections.emptyList();
    }

    protected Map getNamespaces() {
        Map<String, String> namespaces = new HashMap<String, String>();
        if (getScriptInfoProvider() != null) {
            List allComplexScriptRelevantDataList =
                    getScriptInfoProvider().getComplexScriptRelevantData();
            if (allComplexScriptRelevantDataList != null) {
                for (Object obj : allComplexScriptRelevantDataList) {
                    if (obj instanceof Map) {
                        namespaces = (Map) obj;
                    }
                }
            }
        }
        return namespaces;
    }

    protected List<Node> getComplexScriptDataList() {
        List<Node> iComplexScriptRelevantDataList = new ArrayList<Node>();
        if (getScriptInfoProvider() != null) {
            List allComplexScriptRelevantDataList =
                    getScriptInfoProvider().getComplexScriptRelevantData();
            if (allComplexScriptRelevantDataList != null) {
                for (Object obj : allComplexScriptRelevantDataList) {
                    if (obj instanceof Node) {
                        iComplexScriptRelevantDataList.add((Node) obj);
                    }
                }
            }
        }
        return iComplexScriptRelevantDataList;
    }

    protected Map<String, IScriptRelevantData> getScriptDataAsMap() {
        Map<String, IScriptRelevantData> iScriptRelevantDataMap =
                new HashMap<String, IScriptRelevantData>();
        if (getScriptInfoProvider() != null) {
            List<IScriptRelevantData> iScriptRelevantDataList =
                    getScriptInfoProvider().getScriptRelevantData();
            if (iScriptRelevantDataList != null) {
                for (IScriptRelevantData scriptRelevantData : iScriptRelevantDataList) {
                    iScriptRelevantDataMap.put(scriptRelevantData.getName(),
                            scriptRelevantData);
                }
            }
        }
        return iScriptRelevantDataMap;
    }

    protected Map<String, Node> getComplexScriptDataAsMap() {
        Map<String, Node> iComplexScriptRelevantDataMap =
                new HashMap<String, Node>();
        if (getScriptInfoProvider() != null) {
            List<Node> iComplexScriptRelevantDataList =
                    getComplexScriptDataList();
            if (iComplexScriptRelevantDataList != null) {
                for (Node complexScriptRelevantData : iComplexScriptRelevantDataList) {
                    if (complexScriptRelevantData instanceof Document) {
                        Document documentRoot =
                                (Document) complexScriptRelevantData;
                        Node childNode = documentRoot.getFirstChild();
                        iComplexScriptRelevantDataMap
                                .put(childNode.getNodeName(), documentRoot);
                    }
                }
            }
        }
        return iComplexScriptRelevantDataMap;
    }

    /**
     * This method should return an Array of completion proposals to be shown in
     * the content assist
     * 
     * @return An array of ICompletionProposal.
     * 
     */
    @Override
    public abstract ICompletionProposal[] computeCompletionProposals(
            ITextViewer viewer, int documentOffset);

    /**
     * This method parses the file and grabs the full string that we have to
     * help complete.
     */
    protected abstract String getStringToComplete(ITextViewer viewer,
            int documentPosition, Node node);

    /**
     * given the existing string to complete, return a list of completions,
     * 
     * 
     * 
     * @param estring
     *            the string in the document at which the cursor is at the end
     *            of and which we are to complete
     * @param docPosition
     *            the position in the document that the cursor and the end of
     *            the estring are at.
     */
    protected abstract Vector<CustomCompletionProposal> getProposalVector(
            ITextViewer viewer, String estring, int docPosition,
            MyFollowClass myFollowClass);

    /**
     * Custom strings to be added to the list of classes in the completion
     * content assist, It is not needed to do anything here if all the
     * completion classes are passed throught JsClassDefinitionReaders
     * 
     */
    protected abstract void addCustomCompletionString(
            MyFollowClass myStartFollowClass);

    protected abstract String getArrayContentAssistName(String name);

    protected abstract Map<String, IScriptRelevantData> getLocalScriptDataMap();

    protected abstract Map<String, IScriptRelevantData> getGlobalScriptDataMap();

    protected abstract String getGrammarType();

    protected JsClass getMultipleJsClass(JsClass jsClass) {
        JsClass multipleJsClass = null;
        if (jsClass instanceof IMultipleJsClassResolver) {
            IMultipleJsClassResolver multipleResolver =
                    (IMultipleJsClassResolver) jsClass;
            multipleJsClass = multipleResolver.getMultipleJsClass();
        }
        if (multipleJsClass == null) {
            multipleJsClass =
                    new DefaultJsClass(JScriptUtils.getDefaultMultipleClass());
        }
        return multipleJsClass;
    }

}
