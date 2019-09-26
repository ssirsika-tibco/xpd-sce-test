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
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Property;

import com.tibco.bds.designtime.generator.BDSUtils;
import com.tibco.bds.designtime.generator.CDSBOMIndexerService;
import com.tibco.xpd.analyst.resources.xpdl2.ReservedWords;
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

    @SuppressWarnings("restriction")
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

    @SuppressWarnings({ "deprecation", "restriction" })
    private RefactorResult processExpressionAfterDot(IExpr expression,
            IScriptRelevantData type, AST astExpression, AST firstChild,
            Token token, RefactorResult result) {
        RefactoringInfo refactoringInfo = getRefactoringInfo();
        result.setType(type);
        result.setExpr(expression);
        // Check expression after the dot
        AST nextSibling = firstChild.getNextSibling();
        /*
         * Sid ACE-3153 Handle factory.bom_package_name and pkg.bom_package_name renames (avoiding as much of the
         * bonkers uncommented stuff below.
         */
        RefactoringInfo packageNameRefactor = packageNameRefactor(firstChild, result, refactoringInfo, nextSibling);
        if (packageNameRefactor != null) {
            result.setRefactoringInfos(Collections.singletonList(packageNameRefactor));
            return result;
        }

        /*
         * Sid ACE-3153 Handle Enumeration class rename
         */
        RefactoringInfo enumNameRefactor =
                enumerationNameRefactor(astExpression, firstChild, refactoringInfo, nextSibling);

        if (enumNameRefactor != null) {
            result.setRefactoringInfos(Collections.singletonList(enumNameRefactor));
            return result;
        }

        /*
         * Sid ACE-3153 Handle Enumeration class rename
         */
        RefactoringInfo classNameRefactor = classNameRefactor(astExpression, firstChild, refactoringInfo, nextSibling);

        if (classNameRefactor != null) {
            result.setRefactoringInfos(Collections.singletonList(classNameRefactor));
            return result;
        }

        /*
         * Sid ACE-3153
         * 
         * TODO ACE-3260 If you (using proper rename from context menu in project explorer) rename an enumeration
         * literal, class name or attribute name then references to these in process scripts (for classes this would
         * mean factory.package.createClassXXX() methods) to not get refactored.
         * 
         * In BPM 4.3 grandchild attributes already didn't work because the refactoring code employed an algorithm that
         * could only work for the first and second level element name of an a.b.c reference (a and b could be
         * refactored but not c).
         * 
         * In ACE, Enumeration, Class and Property names are now ALWAYS at least third level+ references
         * (factory.package.createClass(), pkg.package.enumeration, pkg.package.enumeration.enumliteral,
         * data.field.attribute). Whilst ACE-3153 managed to fix Package, Class and Enumeration refactoring, a different
         * approach would be required for 4th level+ items (properties, enumeration literals).
         * 
         * Separating the fix for this out from ACE-3153 to this JIRA as it requires a considerable rework of the
         * refactoring framework for this relative minor issue (means that on rename, users have to fix their scripts
         * manaully).
         * 
         * One possible approach is to look at how the content assist provider works to resolve the type of a reference
         * and use that approach...
         * 
         * - Look thru the parsed script AST tree looking for an IDENT token that matches the name being renamed
         * 
         * - Check whether the token with same name is a reference to UML element being refactored
         * 
         * -- Can't be a root element as these are all now static objects (factory, pkg etc) so ignore root tokens with
         * matching name
         * 
         * -- Else, use something like content assist does to discover what type the parent of the matching token is.
         * 
         * -- Then compare that type against the parentage of the UML element being refactored -- If matching token's
         * parent type is the same as the refactored element's parent type then replace the reference.
         * 
         * So for now, no point in doing any of the following as we know it doesn't work and there could be more chance
         * of it breaking something than anything else!
         */
        boolean disableOldCode = true;
        if (disableOldCode) {
            return result;
        }

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


    /**
     * Sid ACE-3153 Handle factory.bom_package_name and pkg.bom_package_name renames
     * 
     * @param firstChild
     * @param result
     * @param refactoringInfo
     * @param nextSibling
     * @return RefactoringInfo or <code>null</code> if not a package name refactor that this method deals with.
     */
    private RefactoringInfo packageNameRefactor(AST firstChild, RefactorResult result, RefactoringInfo refactoringInfo,
            AST nextSibling) {

        if ((ReservedWords.BOM_FACTORY_WRAPPER_OBJECT_NAME.equals(firstChild.getText())
                || ReservedWords.BOM_PACKAGE_WRAPPER_OBJECT_NAME.equals(firstChild.getText()))
                && refactoringInfo.getRefactoredElement() instanceof Model) {
            /*
             * The LHS of the . is "factory" or "pkg" and the element being refactored is a BOM package - so if the
             * token we're on matches the old package name then rename to the new one.
             * 
             * Remembering to use the underscore separate name instead.
             */
            String newValue = BDSUtils.getCDSFactoryName(refactoringInfo.getNewValue());
            String oldValue = BDSUtils.getCDSFactoryName(refactoringInfo.getOldValue());

            if (nextSibling != null && oldValue != null && oldValue.equals(nextSibling.getText())) {
                return new RefactoringInfo(refactoringInfo.getRefactoredElement(), oldValue, newValue);
            }
        }

        return null;
    }

    /**
     * Sid ACE-3153 Handle Enumeration class rename
     * 
     * @param astExpression
     * @param firstChild
     * @param refactoringInfo
     * @param nextSibling
     * 
     * @return RefactoringInfo or <code>null</code> if not an enumeration name refactor that this method deals with.
     */
    private RefactoringInfo enumerationNameRefactor(AST astExpression, AST firstChild, RefactoringInfo refactoringInfo,
            AST nextSibling) {
        if (ReservedWords.BOM_PACKAGE_WRAPPER_OBJECT_NAME.equals(firstChild.getText())
                && refactoringInfo.getRefactoredElement() instanceof Enumeration) {
            /*
             * The LHS of the . is "pkg" and the element being refactored is an enumeration - so if the token we're on
             * matches the old enumeration name then rename to the new one.
             */

            /*
             * But first we need to ensure it's reference to an enumeration in the same package
             * 
             * Remembering to use the underscore separate name instead.
             */
            Model model = ((Enumeration) refactoringInfo.getRefactoredElement()).getModel();

            if (model != null) {
                String scriptPkgName = BDSUtils.getCDSFactoryName(model.getName());

                if (nextSibling != null && scriptPkgName.equals(nextSibling.getText())) {

                    /* OK, so we have found pkg.same_pkg_as_refactored_enum in script. So check the actual enum. */
                    AST nextNextSibling = astExpression.getNextSibling();

                    String newValue = BDSUtils.getCDSFactoryName(refactoringInfo.getNewValue());
                    String oldValue = BDSUtils.getCDSFactoryName(refactoringInfo.getOldValue());

                    if (nextNextSibling != null && oldValue != null && oldValue.equals(nextNextSibling.getText())) {
                        return new RefactoringInfo(refactoringInfo.getRefactoredElement(), oldValue, newValue);
                    }
                }
            }
        }
        return null;
    }

    /**
     * Sid ACE-3153 Handle class rename (rename of factory.package.createClassXX())
     * 
     * @param astExpression
     * @param firstChild
     * @param refactoringInfo
     * @param nextSibling
     * 
     * @return RefactoringInfo or <code>null</code> if not an enumeration name refactor that this method deals with.
     */
    @SuppressWarnings("restriction")
    private RefactoringInfo classNameRefactor(AST astExpression, AST firstChild, RefactoringInfo refactoringInfo,
            AST nextSibling) {
        if (ReservedWords.BOM_FACTORY_WRAPPER_OBJECT_NAME.equals(firstChild.getText())
                && refactoringInfo.getRefactoredElement() instanceof Class) {
            /*
             * The LHS of the . is "factory" and the element being refactored is a class - so if the token we're on
             * matches the old enumeration name then rename to the new one.
             */

            /*
             * But first we need to ensure it's reference to a class in the same package
             * 
             * Remembering to use the underscore separate name instead.
             */
            Model model = ((Class) refactoringInfo.getRefactoredElement()).getModel();

            if (model != null) {
                String scriptPkgName = BDSUtils.getCDSFactoryName(model.getName());

                if (nextSibling != null && scriptPkgName.equals(nextSibling.getText())) {

                    /*
                     * OK, so we have found factory.same_pkg_as_refactored_class in script. So check the actual class
                     * creator method.
                     */
                    AST nextNextSibling = astExpression.getNextSibling();

                    String newValue = getFactoryCreateMethod(refactoringInfo.getNewValue());
                    String oldValue = getFactoryCreateMethod(refactoringInfo.getOldValue());

                    if (nextNextSibling != null && oldValue != null && oldValue.equals(nextNextSibling.getText())) {
                        return new RefactoringInfo(refactoringInfo.getRefactoredElement(), oldValue, newValue);
                    }
                }
            }
        }
        return null;
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
        /* Sid ACE-3153 USe the correct method of name generation. */
        if (umlClassName != null) {
            return BDSUtils.getFactoryClassCreatorMethodName(umlClassName);
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
