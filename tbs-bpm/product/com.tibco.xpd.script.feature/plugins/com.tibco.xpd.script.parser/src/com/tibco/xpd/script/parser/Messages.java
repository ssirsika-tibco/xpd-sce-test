package com.tibco.xpd.script.parser;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
    private static final String BUNDLE_NAME =
            "com.tibco.xpd.script.parser.messages"; //$NON-NLS-1$

    public static String AbstractExpressionValidator_1;

    public static String AbstractExpressionValidator_2;

    public static String AbstractExpressionValidator_26;

    public static String AbstractExpressionValidator_Undefined_DataType;

    public static String AbstractExpressionValidator_Invalid_Index_Type;

    public static String AbstractExpressionValidator_Not_An_Array;

    public static String AbstractExpressionValidator_30;

    public static String AbstractStrategy_WrongParameterNumber_Passed;

    public static String AbstractStrategy_NoDataTypeForLocalVariable;

    public static String AbstractStrategy_NoDataTypeSpecifiedForProcessData;

    public static String AbstractStrategy_VariableNameCannotBeAFutureKeyWord;

    public static String AbstractStrategy_VarNameCannotBeAKeyword;

    public static String ErrorMessage_ColumnNumber;

    public static String ErrorMessage_ErrorMessage;

    public static String ErrorMessage_LineNumber;

    public static String AbstractExpressionValidation_DataTypeOfVariableNotEstablished;

    public static String JScriptAssignmentExpressionValidator_AssignmentOfSuperclassToSubClass;

    public static String JScriptConditionalExprValidator_ConditionalExpressionIsVExpected;

    public static String JScriptExpressionValidator_NewKeywordShouldBeFollowedByAnIdentifier2;

    public static String JScriptMethodDefinitionValidator_LocalMethodDefinitionIsNotAllowed;

    public static String JScriptMethodDefinitionValidator_OnlyMethodDefinitionCanBeValidated;

    public static String JScriptNewExpressionValidator_OnlyNewExpressionCanBeValidated;

    public static String JScriptVariableDeclarationValidator_DataTypeOfLocalVariable;

    public static String JScriptVariableDeclarationValidator_OnlyVarDefinitionStatCanBeValidated;

    public static String JSExpressionValidatorFactory_Passed_Operator_is_not_supported;

    public static String JsValidationStrategy_MethodInvalid_For_Data_Type;

    public static String JsValidationStrategy_PropertyInvalid_For_Data_Type;

    public static String JsValidationStrategy_ClassNoSupported;

    public static String JsValidationStrategy_MethodNotSupportedOnClass;

    public static String JsValidationStrategy_Variable_Undefined;

    public static String JsValidationStrategy_Ambiguity_Unqualified_Enum_NotSupported;

    public static String JsValidationStrategy_ClassNotDefined;

    public static String JsValidationStrategy_Array_Expected;

    public static String JsValidationStrategy_Array_NotExpected;

    public static String JsValidationStrategy_SemicolonExpected;

    public static String Utility_8;

    public static String WsdlVarNameResolver_UnsupportedASTType;

    public static String DtVarNameResolver_UnSupportedAST;

    public static String DefaultJavaScriptCompiler_GenericMessage;

    public static String AbstractExpressionValidator_ExpressionFactoryNotRegistered;

    public static String AbstractExpressionValidator_ExpressionValidatorFactoryNotRegistered;

    public static String AbstractExpressionValidator_ExpressionValidatorNotFound;

    public static String ExpressionValidator_Operation_Undefined;

    public static String ExpressionValidator_EqualityOperationBetween;

    public static String ExpressionValidator_EqualityOperationComparison;

    public static String AbstractExpressionValidator_InfoObject;

    public static String ExpressionValidator_ProblemProcessingAST;

    public static String ExpressionValidator_ComparisonOperationBetween;

    public static String ExpressionValidator_MethodInvalid_For_Data_Type;

    public static String ExpressionValidator_MethodParamsNumberInvalid_For_Data_Type;

    public static String ExpressionValidator_MethodParamsTypeInvalid_For_Data_Type;

    public static String ExpressionValidator_PropertyInvalid_For_Data_Type;

    public static String ExpressionValidator_AssignmentOperationBetween;

    public static String AbstractExpressionValidator_ScriptRelevantDataFactoryNotRegistered;

    public static String ExpressionValidator_MinusOperationBetweenNumbers_NotSupported;

    public static String ExpressionValidator_PlusOperation;

    public static String ExpressionValidator_PlusOperation_Concatenation;

    public static String ExpressionValidator_MultiplicationOperationBetween;

    public static String ExpressionValidator_DivisionOperationBetween;

    public static String ExpressionValidator_ExponentationOperationBetween;

    public static String ExpressionValidator_UnaryOperatorsShouldBeOnlyOnFloatAndInteger;

    public static String ExpressionValidator_BooleanUnaryOperatorsShouldBeOnlyOnBooleans;

    public static String ExpressionValidator_Array_Initialisation_Not_Supported;

    public static String ExpressionValidator_LogicalOperationBetween;

    public static String ExpressionValidator_BitwiseOperation;

    public static String ExpressionValidator_ModOperationBetween;

    public static String ExpressionValidator_AssignmentMultiplicityOperationBetween;

    public static String ExpressionValidator_VariableNameCannotBeAFutureKeyWord;

    public static String ExpressionValidator_VarNameCannotBeAKeyword;

    public static String ExpressionValidator_VariableNameCannotBeAClassName;

    public static String ExpressionValidator_VariableNameCannotBeAFieldName;

    public static String ExpressionValidator_MethodNotSupported;

    public static String ExpressionValidator_StaticCallToNonStaticMethod;

    public static String ExpressionValidator_NonStaticCallToStaticMethod;

    public static String ExpressionValidator_ReadOnlyFieldAssigned;

    public static String ExpressionValidator_ListsAssignedDirectly;

    public static String ExpressionValidator_GlobalFunctionInvalid_For_Data_Type;

    public static String ExpressionValidator_GlobalFunctionParamsNumberInvalid_For_Data_Type;

    public static String ExpressionValidator_GlobalFunctionParamsTypeInvalid_For_Data_Type;

    public static String JScriptConditionalExprValidator_Unable_to_validateConditionalExpression;

    public static String JScriptConditionalExprValidator_ConditionalExpressionExpected;

    public static String ExpressionValidator_lhsOfAssignmentCanOnlyBeVariable;

    public static String ExpressionValidator_Operation_Void;

    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }

    private Messages() {
    }
}
