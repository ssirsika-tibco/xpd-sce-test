package com.tibco.bx.validation.internal.validator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.AggregationKind;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;

import com.tibco.bx.validation.internal.Messages;
import com.tibco.xpd.analyst.resources.xpdl2.ReservedWords;
import com.tibco.xpd.bom.globaldata.resources.GlobalDataProfileManager;
import com.tibco.xpd.n2.cds.utils.CDSUtils;
import com.tibco.xpd.n2.pe.util.PEN2Utils;
import com.tibco.xpd.processeditor.xpdl2.util.ProcessRelevantDataUtil;
import com.tibco.xpd.script.model.JsConsts;
import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.script.model.client.IUMLScriptRelevantData;
import com.tibco.xpd.script.model.client.JsClass;
import com.tibco.xpd.script.model.client.globaldata.CaseRefJsClass;
import com.tibco.xpd.script.model.client.globaldata.CaseRefPaginatedListJsClass;
import com.tibco.xpd.script.model.client.globaldata.CaseUMLScriptRelevantData;
import com.tibco.xpd.script.model.internal.client.ITypeResolution;
import com.tibco.xpd.script.model.internal.client.IUMLElement;
import com.tibco.xpd.script.model.jscript.JScriptUtils;
import com.tibco.xpd.script.parser.antlr.JScriptTokenTypes;
import com.tibco.xpd.script.parser.internal.expr.IExpr;
import com.tibco.xpd.script.parser.internal.validator.IValidateResult;
import com.tibco.xpd.script.parser.internal.validator.jscript.AntlrExprImpl;
import com.tibco.xpd.script.parser.internal.validator.jscript.JScriptAssignmentExpressionValidator;
import com.tibco.xpd.xpdExtension.InitialValues;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

import antlr.Token;
import antlr.collections.AST;

public class N2JScriptAssignmentExpressionValidator
        extends JScriptAssignmentExpressionValidator {

    private final static String[] priorityValidValues =
            new String[] { "100", "200", "300", "400" }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$//$NON-NLS-4$

    @SuppressWarnings("restriction")
    @Override
    protected void performExtraAssignmentValidation(
            IValidateResult lhsValidateResult,
            IValidateResult rhsValidateResult, Token token) {
        super.performExtraAssignmentValidation(lhsValidateResult,
                rhsValidateResult,
                token);
        if (lhsValidateResult != null && rhsValidateResult != null) {

            // validate Process.priority assignment
            validateProcessPriorityAssignment(lhsValidateResult,
                    rhsValidateResult,
                    token);
            // XPD-4793 GlobalData: validate Assignment for Global Data
            // restrictions
            if (isValidAssignment(lhsValidateResult.getType(),
                    rhsValidateResult.getType())
                    && isValidGlobalDataAssignment(lhsValidateResult.getType(),
                            token)) {
                validateAllowedValues(lhsValidateResult,
                        rhsValidateResult,
                        token);
                validateCompositeAssignment(lhsValidateResult,
                        rhsValidateResult,
                        token);
                /*
                 * XPD-2189: if it is an assignment between two xsdAnyTypes
                 * without a ScriptUtils.copy warning message must be shown. for
                 * other sub types it is a valid assignment
                 */
                validateAnyTypeAssignment(lhsValidateResult,
                        rhsValidateResult,
                        token);
                validateAnonComplexToAnyTypeAssignment(lhsValidateResult,
                        rhsValidateResult,
                        token);
            }
        }
    }

    private void validateAnonComplexToAnyTypeAssignment(
            IValidateResult lhsValidateResult,
            IValidateResult rhsValidateResult, Token token) {
        if (null != lhsValidateResult && null != rhsValidateResult) {
            if (null != lhsValidateResult.getType()
                    && null != rhsValidateResult.getType()) {
                if (JScriptUtils.isXsdAnyType(lhsValidateResult.getType())
                        && JScriptUtils.isAnonymousComplexType(
                                rhsValidateResult.getType())) {
                    addErrorMessage(token,
                            Messages.N2JScriptAssignmentExpressionValidator_AnonComplexToAnyTypeAssignment);
                }
            }
        }
    }

    /**
     * @param lhsValidateResult
     * @param rhsValidateResult
     * @param token
     */
    @SuppressWarnings("restriction")
    private void validateAnyTypeAssignment(IValidateResult lhsValidateResult,
            IValidateResult rhsValidateResult, Token token) {
        if (shouldWarnComparison(lhsValidateResult, rhsValidateResult)) {
            IExpr expr = lhsValidateResult.getExpr();
            if (expr.getExpr() instanceof AST) {
                AST astExp = (AST) expr.getExpr();
                if (astExp.getType() == JScriptTokenTypes.DOT) {
                    AST firstChild = astExp.getNextSibling().getFirstChild();
                    if (null == firstChild) {
                        firstChild = astExp.getNextSibling();
                    }
                    if (null != firstChild && null != firstChild.getText()
                            && JScriptTokenTypes.IDENT == firstChild
                                    .getType()) {
                        if (!firstChild.getText().equals("ScriptUtil")) { //$NON-NLS-1$
                            /*
                             * Sid ACE-1318 - suppress the validation
                             * "Explicit assignment of containment relationships could cause contained object being removed from one parent and assigned to another. Please use ScriptUtil.copy(EObject)/ScriptUtil.copyAll(EObject) to avoid the same."
                             * As we use JavaScript then this isn't true
                             * anymore.
                             */
                            // addWarningMessage(token,
                            // Messages.N2JScriptAssignmentExpressionValidator_CompositeRelationshipsAssignment_1);
                        } else {
                            AST firstChild2 =
                                    firstChild.getNextSibling().getFirstChild();
                            if (null != firstChild2
                                    && null != firstChild2.getText()
                                    && JScriptTokenTypes.IDENT == firstChild2
                                            .getType()) {
                                if (!(firstChild2.getText().equals("copy") //$NON-NLS-1$
                                        || firstChild2.getText()
                                                .equals("copyAll"))) { //$NON-NLS-1$
                                    /*
                                     * Sid ACE-1318 - suppress the validation
                                     * "Explicit assignment of containment relationships could cause contained object being removed from one parent and assigned to another. Please use ScriptUtil.copy(EObject)/ScriptUtil.copyAll(EObject) to avoid the same."
                                     * As we use JavaScript then this isn't true
                                     * anymore.
                                     */
                                    // addWarningMessage(token,
                                    // Messages.N2JScriptAssignmentExpressionValidator_CompositeRelationshipsAssignment_1);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * @param lhsValidateResult
     * @param rhsValidateResult
     * @return
     */
    @SuppressWarnings("restriction")
    private boolean shouldWarnComparison(IValidateResult lhsValidateResult,
            IValidateResult rhsValidateResult) {
        if (null != lhsValidateResult && null != rhsValidateResult) {
            if (null != lhsValidateResult.getType()
                    && null != rhsValidateResult.getType()) {
                if (JScriptUtils.isXsdAnyType(lhsValidateResult.getType())) {
                    if (CDSUtils.canBeAssignedToXsdAnyType(
                            rhsValidateResult.getType())) {
                        if (JScriptUtils
                                .isXsdAnyType(rhsValidateResult.getType())
                                || JScriptUtils.isDynamicComplexType(
                                        rhsValidateResult.getType())) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * Returns a List containing the allowed values of the property
     * Process.priority. The first element in the list has all the priorities.
     * 
     * @return priorityList
     */
    private List<String> getPriorityListString() {
        StringBuilder prioprityString = new StringBuilder();
        List<String> priorityList = new ArrayList<String>();
        for (int i = 0; i < priorityValidValues.length; i++) {
            if (i == priorityValidValues.length - 1) {
                prioprityString.append(priorityValidValues[i]);
            } else {
                prioprityString.append(priorityValidValues[i] + ", "); //$NON-NLS-1$
            }
        }
        priorityList.add(prioprityString.toString());
        return priorityList;
    }

    @SuppressWarnings("restriction")
    protected void validateProcessPriorityAssignment(
            IValidateResult lhsValidateResult,
            IValidateResult rhsValidateResult, Token token) {

        /*
         * Sid ACE-552 - Extracted check for 'is assign process priority'to make
         * it easier.
         */
        if (isProcessPriorityLhsAssigment(lhsValidateResult)) {

            IExpr rhsExpr = rhsValidateResult.getExpr();
            if (rhsExpr.getExpr() instanceof AST) {
                AST rhsAstExp = (AST) rhsExpr.getExpr();
                if (isNumericDataType(rhsAstExp.getType())) {
                    String text = rhsAstExp.getText();
                    if (text != null) {
                        /*
                         * XPD-5475: Now we allow any value between minimum and
                         * maximum (100 - 400) or "" = server default
                         */
                        boolean validValue = false;
                        try {
                            int intPriority = Integer.parseInt(text);
                            if (intPriority >= 100 && intPriority <= 400) {
                                validValue = true;
                            }

                        } catch (NumberFormatException e) {
                        }

                        if (!validValue) {
                            // Not allowed value
                            addErrorMessage(token,
                                    Messages.N2JScriptAssignmentExpressionValidator_NotAllowedValueForProcessPriority2);
                        }
                    }
                } else if (rhsAstExp.getType() == JScriptTokenTypes.IDENT
                        || rhsAstExp.getType() == JScriptTokenTypes.DOT) {
                    /*
                     * XPD-5469. All Process.priority to be set from variable
                     * data now.
                     * 
                     * Sid XPD-7663 - Just checking IDENT didn't allow for
                     * "Process.priority = Class.intProperty" so we need to
                     * check for "." above else we'll complian.
                     * 
                     * So don't complain if it is.
                     */

                } else {
                    /*
                     * XPD-4334: Add a warning if the priority is negative.
                     */
                    addErrorMessage(token,
                            Messages.N2JScriptAssignmentExpressionValidator_NotAllowedValueForProcessPriority2);
                }
            }
        }
    }


    /**
     * Check whether the given LHS of expression is an assign to
     * bpm.process.priority
     * 
     * @param lhsValidateResult
     * @return <code>true</code>if this is an assign to bpm.process.priority
     */
    @SuppressWarnings("restriction")
    private boolean isProcessPriorityLhsAssigment(IValidateResult lhsValidateResult) {

        IExpr expr = lhsValidateResult.getExpr();
        if (expr.getExpr() instanceof AST) {
            AST astExp = (AST) expr.getExpr();

            if (astExp.getType() == JScriptTokenTypes.DOT) {
                AST firstChild = astExp.getFirstChild();

                if (firstChild != null && firstChild.getType() == JScriptTokenTypes.DOT) {

                    /*
                     * Is the thing in front of initial 'dot' is the top level
                     * "bpm" object?
                     */
                    AST maybeBpmNode = firstChild.getFirstChild();

                    if (maybeBpmNode != null && maybeBpmNode.getType() == JScriptTokenTypes.IDENT
                            && maybeBpmNode.getFirstChild() == null
                            && PEN2Utils.PE_BPM_CLASS_OBJECT_NAME.equals(maybeBpmNode.getText())) {

                        /*
                         * Is the thing after the dot after "bpm" the "process"
                         * object
                         */
                        AST maybeProcessNode = maybeBpmNode.getNextSibling();

                        if (maybeProcessNode != null && maybeProcessNode.getType() == JScriptTokenTypes.IDENT
                                && PEN2Utils.PE_BPM_PROCESS_OBJECT_NAME.equals(maybeProcessNode.getText())) {

                            /*
                             * Is the thing after the dot after "bpm.process."
                             * the "priority" object
                             */
                            AST maybePriorityNode = firstChild.getNextSibling();

                            if (maybePriorityNode != null && maybePriorityNode.getType() == JScriptTokenTypes.IDENT
                                    && maybePriorityNode.getNextSibling() == null
                                    && PEN2Utils.PE_BPM_PROCESS_PRIORITY_OBJECT_NAME
                                            .equals(maybePriorityNode.getText())) {

                                return true;
                            }
                        }

                    }

                }
            }
        }

        return false;
    }

    @SuppressWarnings("restriction")
    protected void validateAllowedValues(IValidateResult lhsValidateResult,
            IValidateResult rhsValidateResult, Token token) {

        AST astLhsExpr = getASTExpr(lhsValidateResult);
        if (astLhsExpr != null) {
            /*
             * Sid ACE-4093 for ACE astLhsExpr will be the '.' from 'data.param'. So check if it is and we'll skep to
             * first property after data.
             */
            if (astLhsExpr.getType() == JScriptTokenTypes.DOT) {
                /*
                 * Is the thing in front of initial 'dot' is the top level "data" object?
                 */
                AST maybeDataNode = astLhsExpr.getFirstChild();

                if (maybeDataNode != null && maybeDataNode.getType() == JScriptTokenTypes.IDENT
                        && maybeDataNode.getFirstChild() == null
                        && ReservedWords.PROCESS_DATA_WRAPPER_OBJECT_NAME.equals(maybeDataNode.getText())) {

                    /*
                     * The thing after "data" could be the parameter field we're looking for. So use that and allow it
                     * to fall thru below.
                     */
                    astLhsExpr = maybeDataNode.getNextSibling();
                }
            }

            // Check if it is a parameter
            if (astLhsExpr.getType() == JScriptTokenTypes.IDENT
                    && astLhsExpr.getFirstChild() == null) {
                String identName = astLhsExpr.getText();
                if (identName != null) {
                    EObject input = getInput(getInfoObject());
                    if (input != null) {
                        ProcessRelevantData processRelevantData =
                                ProcessRelevantDataUtil
                                        .findProcessRelevantDataByName(input,
                                                identName);
                        if (processRelevantData instanceof FormalParameter) {
                            FormalParameter formalParameter =
                                    (FormalParameter) processRelevantData;
                            List<String> paramAllowedValues =
                                    getParamAllowedValues(formalParameter);
                            if (paramAllowedValues != null
                                    && !paramAllowedValues.isEmpty()) {
                                // Check if the rhs is a literal
                                AST astRhsExpr = getASTExpr(rhsValidateResult);
                                if (astRhsExpr != null) {
                                    if (astRhsExpr
                                            .getType() == JScriptTokenTypes.STRING_LITERAL
                                            && astRhsExpr
                                                    .getFirstChild() == null) {
                                        String literalRHS =
                                                astRhsExpr.getText();
                                        if (literalRHS != null) {
                                            // Strip the quotes from the
                                            // literals
                                            if (literalRHS.startsWith("\"") //$NON-NLS-1$
                                                    && literalRHS.endsWith("\"") //$NON-NLS-1$
                                                    && literalRHS
                                                            .length() > 1) {
                                                literalRHS =
                                                        literalRHS.substring(1,
                                                                literalRHS
                                                                        .length()
                                                                        - 1);
                                            }

                                            for (String allowedValue : paramAllowedValues) {
                                                /*
                                                 * Sid ACE-4093 discount quotes in allowed values (which are now
                                                 * required there since XPD-8195) when checking length.
                                                 */
                                                if (allowedValue != null) {
                                                    String normalisedValue = allowedValue;
                                                    if (normalisedValue.startsWith("\"")) { //$NON-NLS-1$
                                                        normalisedValue = normalisedValue.substring(1);
                                                    }
                                                    if (normalisedValue.endsWith("\"")) { //$NON-NLS-1$
                                                        normalisedValue = normalisedValue.substring(0,
                                                                normalisedValue.length() - 1);
                                                    }

                                                    if (normalisedValue.equals(literalRHS)) {
                                                        return;
                                                    }
                                                }
                                            }

                                            // Not allowed value
                                            List<String> additionalAttributes =
                                                    new ArrayList<String>();
                                            additionalAttributes.add(identName);
                                            addErrorMessage(token,
                                                    Messages.N2JScriptAssignmentExpressionValidator_NotAllowedValue,
                                                    additionalAttributes);
                                        }
                                    } else {
                                        // Unable to check contents
                                        List<String> additionalAttributes =
                                                new ArrayList<String>();
                                        additionalAttributes.add(identName);
                                        addWarningMessage(token,
                                                Messages.N2JScriptAssignmentExpressionValidator_NotAllowedValueUnchecked,
                                                additionalAttributes);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @SuppressWarnings("restriction")
    public AST getASTExpr(IValidateResult validateResult) {
        if (validateResult != null) {
            IExpr expr = validateResult.getExpr();
            if (expr instanceof AntlrExprImpl) {
                AntlrExprImpl exprImpl = (AntlrExprImpl) expr;
                AST astLhsExpr = exprImpl.getExpr();
                if (astLhsExpr != null) {
                    return astLhsExpr;
                }
            }
        }
        return null;
    }

    public List<String> getParamAllowedValues(FormalParameter formalParameter) {
        List<String> allowed = new ArrayList<String>();
        Object other = Xpdl2ModelUtil.getOtherElement(formalParameter,
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_InitialValues());
        if (other instanceof InitialValues) {
            InitialValues values = (InitialValues) other;
            List<?> tokens = values.getValue();
            for (Object next : tokens) {
                if (next instanceof String) {
                    allowed.add((String) next);
                }
            }
        }
        return allowed;
    }

    @SuppressWarnings("restriction")
    protected void validateCompositeAssignment(
            IValidateResult lhsValidateResult,
            IValidateResult rhsValidateResult, Token token) {
        if (rhsValidateResult != null
                && rhsValidateResult.getType() instanceof IUMLScriptRelevantData
                && rhsValidateResult != null
                && rhsValidateResult.getExpr() instanceof AntlrExprImpl) {
            AntlrExprImpl expr = (AntlrExprImpl) rhsValidateResult.getExpr();
            AST parentAstExpr = expr.getExpr();
            // resolve parent type
            if (parentAstExpr != null
                    && parentAstExpr.getType() == JScriptTokenTypes.DOT) {
                // go to the end of ast
                while (parentAstExpr.getFirstChild() != null) {
                    parentAstExpr = parentAstExpr.getFirstChild();
                }
                AST lastSibling = parentAstExpr.getNextSibling();
                if (lastSibling != null) {
                    parentAstExpr.setNextSibling(null);
                    // Get the type of the parent
                    IValidateResult evaluateParent =
                            delegateEvaluateExpression(parentAstExpr, token);
                    parentAstExpr.setNextSibling(lastSibling);
                    if (evaluateParent != null && evaluateParent
                            .getType() instanceof IUMLScriptRelevantData) {
                        IUMLScriptRelevantData type =
                                (IUMLScriptRelevantData) rhsValidateResult
                                        .getType();
                        IUMLScriptRelevantData parentType =
                                (IUMLScriptRelevantData) evaluateParent
                                        .getType();
                        // Get both models
                        JsClass jsClass = type.getJsClass();
                        JsClass parentJsClass = parentType.getJsClass();
                        if (parentJsClass != null
                                && parentJsClass.getUmlClass() != null
                                && jsClass != null
                                && jsClass.getUmlClass() != null) {
                            /*
                             * XPD-5862: ScriptUtil.copy() must be used on bom
                             * types. For case ref types direct assignment from
                             * composition attributes should be valid and no
                             * warning message required
                             */
                            if (!(jsClass instanceof CaseRefJsClass
                                    && parentJsClass instanceof CaseRefJsClass)) {

                                Class umlClass = jsClass.getUmlClass();
                                Class parentUmlClass =
                                        parentJsClass.getUmlClass();
                                Set<Class> alreadyVisitedClasses =
                                        new HashSet<Class>();
                                if (isOwnedAttribute(parentUmlClass,
                                        umlClass,
                                        alreadyVisitedClasses)
                                        || isCompositeRelationship(
                                                parentUmlClass,
                                                umlClass)) {
                                    /*
                                     * Sid ACE-1318 - suppress the validation
                                     * "Explicit assignment of containment relationships could cause contained object being removed from one parent and assigned to another. Please use ScriptUtil.copy(EObject)/ScriptUtil.copyAll(EObject) to avoid the same."
                                     * As we use JavaScript then this isn't true
                                     * anymore.
                                     */
                                    // addWarningMessage(token,
                                    // Messages.N2JScriptAssignmentExpressionValidator_CompositeRelationshipsAssignment_1);
                                    return;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Navigates through the properties of the parentUmlClass to check if the
     * passed in uml class is owned by the parent uml class (composition
     * relationship). Recursively calls this method for each parent uml class's
     * property type class and compares with the passed in umlClass to check for
     * composition (to check for its grand children. for instance we want to
     * validate orderlines.items = order.orderlines.items )
     * 
     * @param parentUmlClass
     * @param umlClass
     * @param alreadyVisitedClasses
     *            - set to maintain visited classes to avoid getting into
     *            recursive loops (this is required when there is a
     *            bi-directional association in global data boms)
     * @return <code>true</code> if umlClass is a composition class in the
     *         parentUmlClass
     */
    private boolean isOwnedAttribute(Class parentUmlClass, Class umlClass,
            Set<Class> alreadyVisitedClasses) {

        if (!alreadyVisitedClasses.contains(parentUmlClass)) {

            EList<Property> allAttributes = parentUmlClass.getAllAttributes();
            alreadyVisitedClasses.add(parentUmlClass);
            if (allAttributes != null && !allAttributes.isEmpty()) {

                for (Property property : allAttributes) {

                    Type type = property.getType();
                    if (type instanceof Class) {

                        if (type.equals(umlClass)) {

                            return true;
                        }
                        /*
                         * XPD-5596: Warning*
                         * "Explicit assignment of composition relationships could have unexpected results at runtime"
                         * not raised for grandchildren
                         */
                        if ((isOwnedAttribute((Class) type,
                                umlClass,
                                alreadyVisitedClasses))) {

                            return true;
                        }

                    }
                }
            }
        }
        return false;
    }

    private boolean isCompositeRelationship(Class parentUmlClass,
            Class umlClass) {
        EList<Association> associations = parentUmlClass.getAssociations();
        if (associations != null && !associations.isEmpty()) {
            for (Association association : associations) {
                EList<Property> memberEnds = association.getMemberEnds();
                if (memberEnds != null && !memberEnds.isEmpty()) {
                    for (Property memberEnd : memberEnds) {
                        if (memberEnd != null && memberEnd.getType() != null
                                && memberEnd.getType().equals(umlClass)) {
                            AggregationKind aggregationKind =
                                    memberEnd.getAggregation();
                            if (aggregationKind == AggregationKind.COMPOSITE_LITERAL) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    @SuppressWarnings("restriction")
    @Override
    protected boolean isExplicitAssignmentAllowance(
            IScriptRelevantData lhsDataType, IScriptRelevantData rhsDataType) {
        /* Sid ACE-194 - we don't have XSD derived BOMs any more. */
        if (lhsDataType instanceof CaseUMLScriptRelevantData) {

            if (isPaginated(lhsDataType) && !isPaginated(rhsDataType)) {
                /*
                 * Return false ONLY if a PaginatedList is not assigned with a
                 * PaginatedList.
                 */
                return false;
            }
            if (JScriptUtils.isEObjectType(rhsDataType)
                    || JsConsts.OBJECT.equals(rhsDataType.getType())) {
                /*
                 * Return false if a BOM Class or an Object is assigned to a
                 * case ref type.
                 */
                return false;
            }
        }
        /* Let Base class handle it otherwise. */
        return super.isExplicitAssignmentAllowance(lhsDataType, rhsDataType);
    }

    /**
     * Checks if the given ScriptRelevantData is a PaginatedList, handles only
     * CaseUmlScriptRelevantData , returns false otherwise.
     * 
     * @param scriptRelevantData
     * @return true, if scriptRelevantData is a PaginatedList
     */
    private boolean isPaginated(IScriptRelevantData scriptRelevantData) {
        /* At the moment only CaseUMLScriptRelevantData uses PaginatedList */
        if (scriptRelevantData instanceof CaseUMLScriptRelevantData) {

            CaseUMLScriptRelevantData lhsData =
                    (CaseUMLScriptRelevantData) scriptRelevantData;
            JsClass jsClass = lhsData.getJsClass();
            if (jsClass instanceof CaseRefPaginatedListJsClass) {

                CaseRefPaginatedListJsClass caseRefPaginatedJsClass =
                        (CaseRefPaginatedListJsClass) jsClass;
                return caseRefPaginatedJsClass.isCanPaginate();
            }
        }
        return false;
    }

    /* Sid ACE-194 - we don't have XSD derived BOMs any more. */

    @SuppressWarnings("restriction")
    @Override
    protected boolean isExplicitAssignmentRestriction(
            IScriptRelevantData lhsDataType, IScriptRelevantData rhsDataType) {
        /* Sid ACE-194 - we don't have XSD derived BOMs any more. */
        return super.isExplicitAssignmentRestriction(lhsDataType, rhsDataType);
    }

    /**
     * Validates the assignment to left hand side with respect to Global Data
     * restrictions. Returns false if this is a AutoCaseIdentifier Property,
     * true otherwise.
     * 
     * @param lhsDataType
     * @param token
     * @return
     */
    private boolean isValidGlobalDataAssignment(IScriptRelevantData lhsDataType,
            Token token) {
        // ONLY interested in LHS to flag not-allowed assignments
        if (lhsDataType != null && lhsDataType.getType() != null) {
            Object extendedInfo =
                    ((ITypeResolution) lhsDataType).getExtendedInfo();
            if (extendedInfo instanceof IUMLElement) {
                Element element = ((IUMLElement) extendedInfo).getElement();
                if (element instanceof Property) {
                    Property property = (Property) element;
                    // XPD-4793: AUTOCID is Read Only , cannot be assigned;
                    if (GlobalDataProfileManager.getInstance()
                            .isAutoCaseIdentifier(property)) {
                        List<String> additionalInfo = new ArrayList<String>();
                        additionalInfo.add(property.getName());
                        // add global data specific error message before
                        // returning false
                        addErrorMessage(token,
                                Messages.N2JScriptAssignmentExpressionValidator_AutoCaseIDAssignment,
                                additionalInfo);
                        return false;
                    }
                }
            }

        }
        return true;
    }

    @Override
    protected String parseTypeMessage(IScriptRelevantData scriptRelevantData) {
        /*
         * Customise Type message to include, PaginatedList/List if applicable,
         * To show correct message for e.g. "Assignment of PaginatedList from
         * List is not supported" , ALSO include <Type> otherwise messages like
         * "Assignment of PaginatedList  from PaginatedList  is not supported"
         * is not clear enough. which should rather be
         * "Assignment of PaginatedList<com.example.xpd5365.OrderRef> from PaginatedList<com.example.xpd5365.CustomerRef> is not supported"
         */
        String collectionType = null;
        if (scriptRelevantData.isArray()) {

            collectionType = JsConsts.ARRAY;
        }

        if (collectionType != null) {

            return String.format("%1s<%2s>", //$NON-NLS-1$
                    collectionType,
                    scriptRelevantData.getType());
        }
        return super.parseTypeMessage(scriptRelevantData);
    }

    /**
     * 
     * XPD-4445: had to override this method because we want to show an error
     * message (not a warning) when sub-type is assigned super-type for global
     * data boms. (local boms subtype = supertype shows a warning message)
     * 
     * @see com.tibco.xpd.script.parser.internal.validator.jscript.JScriptAssignmentExpressionValidator#validateSuperTypeAssignment(com.tibco.xpd.script.parser.internal.validator.IValidateResult,
     *      com.tibco.xpd.script.parser.internal.validator.IValidateResult,
     *      antlr.Token)
     * 
     * @param lhsValidateResult
     * @param rhsValidateResult
     * @param token
     */
    @Override
    protected void validateSuperTypeAssignment(
            IValidateResult lhsValidateResult,
            IValidateResult rhsValidateResult, Token token) {

        if (JScriptUtils.isSubType(lhsValidateResult.getType(),
                rhsValidateResult.getType())) {

            /*
             * XPD-4445: check if the types are representing case classes. if so
             * add error message
             */
            boolean isLhsCaseType = isCaseType(lhsValidateResult.getType());
            boolean isRhsCaseType = isCaseType(rhsValidateResult.getType());
            if (isLhsCaseType && isRhsCaseType) {

                List<String> additionalAttributes = new ArrayList<String>();
                additionalAttributes
                        .add(parseTypeMessage(rhsValidateResult.getType()));
                additionalAttributes
                        .add(parseTypeMessage(lhsValidateResult.getType()));
                String errorMessage =
                        Messages.N2JScriptAssignmentExpressionValidator_AssignmentOfSuperclassToSubClass;
                addErrorMessage(token, errorMessage, additionalAttributes);
                return;
            }
        }

        /*
         * if the types are not representing case classes then show warning as
         * usual
         */
        super.validateSuperTypeAssignment(lhsValidateResult,
                rhsValidateResult,
                token);

    }

    /**
     * @param type
     * @param subType
     * @return <code>true</code> if the types represent case uml script relevant
     *         data
     */
    private boolean isCaseType(IScriptRelevantData type) {

        IScriptRelevantData resolvedType =
                JScriptUtils.resolveGenericType(type);

        if (resolvedType instanceof CaseUMLScriptRelevantData) {

            return true;
        }
        return false;
    }
}
