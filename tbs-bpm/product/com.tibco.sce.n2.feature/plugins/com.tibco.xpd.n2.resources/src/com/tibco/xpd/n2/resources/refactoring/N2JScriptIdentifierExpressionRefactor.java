/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.n2.resources.refactoring;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.PackageableElement;

import antlr.Token;
import antlr.collections.AST;

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
import com.tibco.xpd.script.parser.internal.refactoring.jscript.JScriptIdentifierExpressionRefactor;
import com.tibco.xpd.ui.util.NameUtil;

/**
 * @author mtorres
 * 
 *         ExpressionValidator class that handles the validation of an
 *         identifier ie: field;
 * 
 */
public class N2JScriptIdentifierExpressionRefactor extends
        JScriptIdentifierExpressionRefactor {

    private final static String FACTORY_END_QUALIFIER = "_Factory";//$NON-NLS-1$

    @Override
    @SuppressWarnings("restriction")
    public RefactorResult evaluate(IExpr expresion) {
        if (expresion != null) {
            Object expr = expresion.getExpr();
            Object token = expresion.getToken();
            if (expr instanceof AST && token instanceof Token) {
                AST astExpr = (AST) expr;
                Token antlrToken = (Token) token;
                if (astExpr != null && antlrToken != null) {
                    switch (astExpr.getType()) {
                    case JScriptTokenTypes.IDENT:
                        String identifierText = astExpr.getText();
                        if (identifierText != null) {
                            IScriptRelevantData identifierScriptRelevantDataType =
                                    JScriptUtils
                                            .getIdentifierScriptRelevantDataType(identifierText,
                                                    getSupportedJsClasses(),
                                                    getSupportedGlobalProperties(),
                                                    getSupportedScriptRelevantDataMap(),
                                                    getLocalVariablesMap(),
                                                    getLocalMethodsMap(),
                                                    getScriptRelevantDataFactory(),
                                                    null);
                            if (identifierScriptRelevantDataType != null) {
                                RefactoringInfo info = getRefactoringInfo();
                                RefactorResult result = new RefactorResult();
                                result.setType(identifierScriptRelevantDataType);
                                if (info != null) {
                                    EObject refactoredElement =
                                            info.getRefactoredElement();
                                    String oldValue = info.getOldValue();
                                    String newValue = info.getNewValue();
                                    if (oldValue != null && newValue != null) {
                                        if (refactoredElement instanceof Model) {
                                            result =
                                                    getPackageRefactoredResult((Model) refactoredElement,
                                                            identifierText,
                                                            identifierScriptRelevantDataType,
                                                            oldValue,
                                                            newValue,
                                                            expresion);
                                            if (result != null) {
                                                getRefactorResultList()
                                                        .add(result);
                                            }
                                        } else if (refactoredElement instanceof Package) {
                                            String oldValueFQN =
                                                    N2Utils.getPackageQualifier((Package) refactoredElement);
                                            String newValueFQN =
                                                    getPackageNewValueFQN(oldValueFQN,
                                                            oldValue,
                                                            newValue,
                                                            (Package) refactoredElement);
                                            if (oldValueFQN != null
                                                    && newValueFQN != null) {
                                                result =
                                                        getPackageRefactoredResult((Package) refactoredElement,
                                                                identifierText,
                                                                identifierScriptRelevantDataType,
                                                                oldValueFQN,
                                                                newValueFQN,
                                                                expresion);
                                                if (result != null) {
                                                    getRefactorResultList()
                                                            .add(result);
                                                }
                                            }
                                        } else if (refactoredElement instanceof Enumeration) {
                                            result =
                                                    getEnumerationRefactoredResult((Enumeration) refactoredElement,
                                                            identifierText,
                                                            identifierScriptRelevantDataType,
                                                            oldValue,
                                                            newValue,
                                                            expresion);
                                            if (result != null) {
                                                getRefactorResultList()
                                                        .add(result);
                                            }
                                        }
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
        }
        return null;
    }

    /**
     * 
     * @param umlPackage
     *            the umg package being renamed
     * @param identifierText
     *            the old text that uses the package name
     * @param identifierType
     * @param oldValue
     *            the old package name
     * @param newValue
     *            the new package name
     * @param expr
     *            the Expression
     * @return the converted {@link RefactorResult} of the identifierText that
     *         was affected by package rename.
     */
    private RefactorResult getPackageRefactoredResult(Package umlPackage,
            String identifierText, IScriptRelevantData identifierType,
            String oldValue, String newValue, IExpr expr) {
        RefactorResult refactorResult = new RefactorResult();
        refactorResult.setExpr(expr);

        // Renaming of model or packages affects factory names
        String oldFactoryNameForPackage =
                CDSBOMIndexerService.getInstance()
                        .getCDSFactoryForPackage(oldValue);
        String newFactoryNameForPackage =
                CDSBOMIndexerService.getInstance().getCDSFactoryName(newValue);

        Map<String, String> old2NewNameMap = new HashMap<String, String>();

        if (oldFactoryNameForPackage != null
                && newFactoryNameForPackage != null) {

            if (identifierText.equals(oldFactoryNameForPackage)
                    && oldFactoryNameForPackage
                            .equals(identifierType.getName())) {
                old2NewNameMap.put(oldFactoryNameForPackage,
                        newFactoryNameForPackage);
            }
            // Add subpackages changes
            Iterator<PackageableElement> iterator =
                    umlPackage.getPackagedElements().iterator();
            while (iterator.hasNext()) {
                PackageableElement packageableElement = iterator.next();
                if (packageableElement instanceof org.eclipse.uml2.uml.Package) {
                    old2NewNameMap
                            .putAll(getSubPackageOld2NewNameMap(identifierText,
                                    identifierType,
                                    oldValue,
                                    newValue,
                                    oldFactoryNameForPackage,
                                    newFactoryNameForPackage,
                                    (org.eclipse.uml2.uml.Package) packageableElement));
                } else if (packageableElement instanceof Enumeration) {
                    /*
                     * XPD-6937: We did not handle the case where the uml
                     * package was renamed and the Enumeration used the fully
                     * qualified names.
                     */
                    /*
                     * get the old scripting name
                     */
                    String oldPkgScriptingName =
                            NameUtil.formatQualifiedNameForScripting(umlPackage
                                    .getQualifiedName());

                    /*
                     * get the new scripting name
                     */
                    String newPkgScriptingName =
                            NameUtil.formatQualifiedNameForScripting(newValue);

                    /*
                     * add the old enum scriting name to the new enum scripting
                     * name to the map.
                     */
                    if (identifierText != null && oldPkgScriptingName != null
                            && newPkgScriptingName != null
                            && !newPkgScriptingName.equals(oldPkgScriptingName)) {

                        old2NewNameMap.put(identifierText, identifierText
                                .replaceFirst(oldPkgScriptingName,
                                        newPkgScriptingName));
                    }
                }
            }
        }

        if (old2NewNameMap != null && !old2NewNameMap.isEmpty()) {
            List<RefactoringInfo> refactoringInfos =
                    new ArrayList<RefactoringInfo>();
            Set<String> keySet = old2NewNameMap.keySet();
            for (String key : keySet) {
                RefactoringInfo info =
                        new RefactoringInfo(umlPackage, key,
                                old2NewNameMap.get(key));
                refactoringInfos.add(info);
            }
            refactorResult.setRefactoringInfos(refactoringInfos);
        }
        return refactorResult;
    }

    private Map<String, String> getSubPackageOld2NewNameMap(
            String identifierText, IScriptRelevantData identifierType,
            String oldName, String newName, String oldParentFactoryName,
            String newParentFactoryName, org.eclipse.uml2.uml.Package umlPackage) {
        String newFactoryNameForPackage = null;
        String oldFactoryNameForPackage = null;
        String oldParentRootFactoryName =
                getFactoryRootName(oldParentFactoryName);
        String newParentRootFactoryName =
                getFactoryRootName(newParentFactoryName);
        if (oldParentRootFactoryName != null
                && newParentRootFactoryName != null) {
            String oldSubPackageQualifier =
                    N2Utils.getPackageQualifier(umlPackage);
            if (oldSubPackageQualifier != null) {
                oldFactoryNameForPackage =
                        CDSBOMIndexerService
                                .getInstance()
                                .getCDSFactoryForPackage(oldSubPackageQualifier);
                if (oldFactoryNameForPackage != null
                        && oldFactoryNameForPackage
                                .startsWith(oldParentRootFactoryName)) {
                    newFactoryNameForPackage =
                            oldFactoryNameForPackage
                                    .replaceFirst(oldParentRootFactoryName,
                                            newParentRootFactoryName);
                }
            }
        }
        if (oldFactoryNameForPackage != null
                && newFactoryNameForPackage != null) {
            Map<String, String> old2NewNameMap = new HashMap<String, String>();
            if (identifierText.equals(oldFactoryNameForPackage)
                    && oldFactoryNameForPackage
                            .equals(identifierType.getName())) {
                old2NewNameMap.put(oldFactoryNameForPackage,
                        newFactoryNameForPackage);
            }
            // Add subpackages changes
            Iterator<PackageableElement> iterator =
                    umlPackage.getPackagedElements().iterator();
            while (iterator.hasNext()) {
                PackageableElement packageableElement = iterator.next();
                if (packageableElement instanceof org.eclipse.uml2.uml.Package) {
                    old2NewNameMap
                            .putAll(getSubPackageOld2NewNameMap(identifierText,
                                    identifierType,
                                    oldName,
                                    newName,
                                    oldFactoryNameForPackage,
                                    newFactoryNameForPackage,
                                    (org.eclipse.uml2.uml.Package) packageableElement));
                }
            }
            return old2NewNameMap;
        }
        return Collections.emptyMap();
    }

    private String getFactoryRootName(String factoryName) {
        if (factoryName != null && factoryName.endsWith(FACTORY_END_QUALIFIER)) {
            int lastIndexOf = factoryName.lastIndexOf(FACTORY_END_QUALIFIER);
            if (lastIndexOf != -1
                    && factoryName.length() > FACTORY_END_QUALIFIER.length()) {
                return factoryName.substring(0, factoryName.length()
                        - FACTORY_END_QUALIFIER.length());
            }
        }
        return null;
    }

    private String getPackageNewValueFQN(String oldValueFQN, String oldValue,
            String newValue, Package umlPackage) {
        if (oldValueFQN != null && oldValueFQN.endsWith(oldValue)) {
            StringBuffer sb = new StringBuffer(oldValueFQN);
            if (sb.length() >= oldValue.length()) {
                StringBuffer newSb =
                        sb.replace(sb.length() - oldValue.length(),
                                sb.length(),
                                newValue);
                return newSb.toString();
            }
        }
        return null;
    }

    private RefactorResult getEnumerationRefactoredResult(
            Enumeration umlEnumeration, String identifierText,
            IScriptRelevantData identifierType, String oldValue,
            String newValue, IExpr expr) {
        RefactorResult refactorResult = new RefactorResult();
        refactorResult.setExpr(expr);
        String umlEnumQualifiedScriptingName =
                NameUtil.formatQualifiedNameForScripting(umlEnumeration
                        .getQualifiedName());
        // XPD-5018: Replace unqualified and qualified name references of an
        // enumeration in script.
        // added check for qualified name of enumeration.
        if ((identifierText.equals(oldValue) || identifierText
                .equals(umlEnumQualifiedScriptingName))
                && identifierType instanceof IUMLScriptRelevantData) {
            IUMLScriptRelevantData umlScriptRelevantData =
                    (IUMLScriptRelevantData) identifierType;
            JsClass jsClass = umlScriptRelevantData.getJsClass();
            if (jsClass instanceof JsEnumeration) {
                JsEnumeration jsEnumeration = (JsEnumeration) jsClass;
                if (jsEnumeration != null
                        && jsEnumeration.getName() != null
                        && umlEnumeration.getName() != null
                        && (jsEnumeration.getName()
                                .equals(umlEnumeration.getName()) || jsEnumeration
                                .getName()
                                .equals(umlEnumQualifiedScriptingName))) {
                    // oldValue is always set as the unqualified name, reset it
                    // to the actual reference to be replaced in script, which
                    // can be qualified name.
                    oldValue = identifierText;
                    newValue =
                            umlEnumQualifiedScriptingName.substring(0,
                                    umlEnumQualifiedScriptingName
                                            .lastIndexOf(umlEnumeration
                                                    .getName()))
                                    + newValue;
                    refactorResult.setRefactoringInfos(Collections
                            .singletonList(new RefactoringInfo(umlEnumeration,
                                    identifierText, newValue)));
                }
            }
        }
        return refactorResult;
    }

}
