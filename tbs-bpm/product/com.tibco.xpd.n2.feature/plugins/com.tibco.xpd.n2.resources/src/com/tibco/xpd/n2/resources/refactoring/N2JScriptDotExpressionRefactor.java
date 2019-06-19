/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.n2.resources.refactoring;

import java.util.Collections;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.EnumerationLiteral;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Property;

import com.tibco.bds.designtime.generator.CDSBOMIndexerService;
import com.tibco.xpd.n2.resources.util.N2Utils;
import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.script.model.client.IUMLScriptRelevantData;
import com.tibco.xpd.script.model.client.JsClass;
import com.tibco.xpd.script.model.internal.client.JsEnumeration;
import com.tibco.xpd.script.model.jscript.JScriptUtils;
import com.tibco.xpd.script.parser.antlr.JScriptTokenTypes;
import com.tibco.xpd.script.parser.internal.expr.IExpr;
import com.tibco.xpd.script.parser.internal.refactoring.RefactorResult;
import com.tibco.xpd.script.parser.internal.refactoring.RefactoringInfo;
import com.tibco.xpd.script.parser.internal.refactoring.jscript.AbstractExpressionRefactor;
import com.tibco.xpd.ui.util.NameUtil;

import antlr.Token;
import antlr.collections.AST;

/**
 * @author mtorres
 * 
 *         ExpressionValidator class that handles the validation of the dot
 *         expression after a field, Class, Factory, etc... ie:
 *         FactoryClass.method(""); ie: field.method("");
 * 
 */
public class N2JScriptDotExpressionRefactor extends AbstractExpressionRefactor {

    private final static String CREATE_PREFIX = "create";//$NON-NLS-1$

    @Override
    public RefactorResult evaluate(IExpr expression) {
        if (expression != null) {
            Object expr = expression.getExpr();
            Object token = expression.getToken();
            if (expr instanceof AST && token instanceof Token) {
                AST astExpression = (AST) expr;
                Token astToken = (Token) token;
                switch (astExpression.getType()) {
                case JScriptTokenTypes.DOT:
                    AST firstChild = astExpression.getFirstChild();
                    RefactorResult result = new RefactorResult();
                    if (firstChild != null) {
                        if (firstChild.getFirstChild() == null) {
                            // It is the end of the tree so this is the root of
                            // the dot expr
                            // Delegate the evaluation of the child
                            RefactorResult delegateEvaluateExpression =
                                    delegateEvaluateExpression(firstChild,
                                            token);
                            if (delegateEvaluateExpression != null) {
                                processExpressionAfterDot(expression,
                                                delegateEvaluateExpression.getType(),
                                                astExpression,
                                                firstChild,
                                                astToken,
                                                result);
                                if (result != null) {
                                    getRefactorResultList()
                                            .add(result);
                                }
                                return result;
                            }
                        } else {
                            RefactorResult delegateEvaluateExpression =
                                    delegateEvaluateExpression(firstChild,
                                            token);
                            if (delegateEvaluateExpression != null) {
                                processExpressionAfterDot(expression,
                                        delegateEvaluateExpression.getType(),
                                        astExpression,
                                        firstChild,
                                        astToken,
                                        result);
                                if (result != null) {
                                    getRefactorResultList().add(result);
                                }
                                return result;
                            }
                        }
                    }
                    break;

                default:
                    break;
                }
            }
        }
        return null;
    }

    private RefactorResult processExpressionAfterDot(IExpr expression,
            IScriptRelevantData type, AST astExpression, AST firstChild,
            Token token, RefactorResult result) {
        RefactoringInfo refactoringInfo = getRefactoringInfo();
        result.setType(type);
        result.setExpr(expression);
        // Check expression after the dot
        AST nextSibling = firstChild.getNextSibling();
        if (refactoringInfo != null && type != null && nextSibling != null) {
            String newValue = refactoringInfo.getNewValue();
            String oldValue = refactoringInfo.getOldValue();
            if (newValue != null && oldValue != null) {
                EObject refactoredElement =
                        refactoringInfo.getRefactoredElement();
                if (refactoredElement instanceof Class) {
                    if (astExpression != null
                            && astExpression.getNextSibling() != null
                            && astExpression.getNextSibling().getType() == JScriptTokenTypes.ELIST) {
                        result
                                .setRefactoringInfos(processFactoryClassMethodRename(type,
                                        (Class) refactoredElement,
                                        nextSibling.getText(),
                                        oldValue,
                                        newValue));
                    }
                } else if (refactoredElement instanceof Property) {
                    result.setRefactoringInfos(processAttributeRename(type,
                            (Property) refactoredElement,
                            nextSibling.getText(),
                            oldValue,
                            newValue));
                } else if(refactoredElement instanceof EnumerationLiteral){
                    result.setRefactoringInfos(processEnumerationLiteralRename(type,
                            (EnumerationLiteral) refactoredElement,
                            nextSibling.getText(),
                            oldValue,
                            newValue));
                }
            }
        }
        return result;
    }

    private List<RefactoringInfo> processFactoryClassMethodRename(
            IScriptRelevantData type, Class umlClass, String methodName,
            String oldValue, String newValue) {
        String factoryNameForClass = getFactoryNameForClass(umlClass);
        if (factoryNameForClass != null && methodName != null) {
            String typeName = type.getType();
            if (typeName != null && typeName.equals(factoryNameForClass)) {
                String factoryOldCreateMethod =
                        getFactoryCreateMethod(oldValue);
                if (factoryOldCreateMethod != null
                        && methodName.equals(factoryOldCreateMethod)) {
                    String factoryNewCreateMethod =
                            getFactoryCreateMethod(newValue);
                    return Collections.singletonList(new RefactoringInfo(
                            umlClass, factoryOldCreateMethod,
                            factoryNewCreateMethod));
                }
            }
        }
        return Collections.emptyList();
    }

    private String getFactoryNameForClass(org.eclipse.uml2.uml.Class umlClass) {
        if (umlClass != null) {
            Package umlPackage = umlClass.getPackage();
            if (umlPackage != null) {
                String packageQualifier =
                        N2Utils.getPackageQualifier(umlPackage);
                if (packageQualifier != null) {
                    return CDSBOMIndexerService.getInstance()
                            .getCDSFactoryForPackage(packageQualifier);
                }
            }
        }
        return null;
    }

    private String getFactoryCreateMethod(String umlClassName) {
        if (umlClassName != null) {
            return CREATE_PREFIX + umlClassName;
        }
        return null;
    }
    
    private List<RefactoringInfo> processAttributeRename(
            IScriptRelevantData type, Property umlProperty,
            String attributeName, String oldValue, String newValue) {
        if (attributeName != null && oldValue != null && newValue != null && umlProperty != null
                && JScriptUtils.isDynamicComplexType(type,
                        getSupportedJsClasses()) && attributeName.equals(oldValue)) {
            EObject container = umlProperty.eContainer();
            if(container != null){
                        return Collections.singletonList(new RefactoringInfo(
                                container, oldValue,
                                newValue));
            }
        }
        return Collections.emptyList();
    }
    
    private List<RefactoringInfo> processEnumerationLiteralRename(
            IScriptRelevantData type, EnumerationLiteral umlEnumerationLiteral,
            String enumerationName, String oldValue, String newValue) {

        if (enumerationName != null && oldValue != null && newValue != null
                && umlEnumerationLiteral != null
                && enumerationName.equals(oldValue)) {
            //Check that the container is the same
            if (type instanceof IUMLScriptRelevantData) {
                IUMLScriptRelevantData umlScriptRelevantData =
                        (IUMLScriptRelevantData) type;
                JsClass jsClass = umlScriptRelevantData.getJsClass();
                if (jsClass instanceof JsEnumeration) {
                    JsEnumeration jsEnumeration = (JsEnumeration) jsClass;
                    Element owner = umlEnumerationLiteral.getOwner();
                    if(owner instanceof Enumeration){
                        Enumeration enumerationOwner = (Enumeration)owner;
                        // XPD-5018: Handle Qualified name references for
                        // enumeration in script.
                        // added check for qualified name of enumeration.
                        String umlEnumQualifiedScriptingName =
                                NameUtil.formatQualifiedNameForScripting(enumerationOwner
                                        .getQualifiedName());
                        if (jsEnumeration != null
                                && jsEnumeration.getName() != null
                                && enumerationOwner.getName() != null
                                && (jsEnumeration.getName()
                                        .equals(enumerationOwner.getName()) || jsEnumeration
                                        .getName()
                                        .equals(umlEnumQualifiedScriptingName))) {
                            return Collections
                                    .singletonList(new RefactoringInfo(umlEnumerationLiteral,
                                            oldValue, newValue));
                        }
                    }                    
                }               
            }
        }
        return Collections.emptyList();
    }
    

}
