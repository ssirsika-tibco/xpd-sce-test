header{
package com.tibco.xpd.process.xpath.parser.antlr;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.wst.wsdl.Part;

import antlr.collections.AST;

import com.tibco.xpd.process.xpath.parser.util.XPathScriptParserUtil;
import com.tibco.xpd.process.xpath.parser.util.ProcessXPathUtil;
import com.tibco.xpd.process.xpath.parser.validator.xpath.XPathSymbolTable;
import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.script.parser.validator.ISymbolTable;
import com.tibco.xpd.script.parser.validator.IValidationStrategy;

import org.jaxen.saxpath.Axis;
import org.jaxen.saxpath.Operator;
import org.jaxen.expr.*;
import org.jaxen.JaxenException;
}
class XPathParser extends Parser;
	options
	{
		k = 2;
		exportVocab=XPath;
		buildAST = true;
	}
	{
	
    private List<RecognitionException> recognitionExceptions;

    private List<IValidationStrategy> validationStrategyList = null;
    
    private IScriptRelevantData mappingType = null;
    
    private ISymbolTable symbolTable = null;
    
    private Expr finalExpr = null;
    
    private int axis = 1;
    
    private Part wsdlPart;
    
    private boolean isWsdlSupported;
    
    
    public void startValidation()throws RecognitionException, TokenStreamException{
        xpath();        
    }
    
    @Override
    public void reportError(RecognitionException arg0) {
        // super.reportError(arg0);
        getRecognitionExceptions().add(arg0);
    }

    @Override
    public void reportError(String arg0) {
        // super.reportError(arg0);
        RecognitionException ex = new RecognitionException(arg0);
        getRecognitionExceptions().add(ex);
    }
    
    public Expr getFinalExpression(){
    	return finalExpr;
    }
    
    public IScriptRelevantData getMappingType(){
    	return this.mappingType;
    }
    
    public void setMappingType(IScriptRelevantData value){
    	this.mappingType = value;
    }
    
    public Part getWsdlPart(){
    	return this.wsdlPart;
    }
    
    public void setWsdlPart(Part value){
    	this.wsdlPart = value;
    }
    
    public boolean isWsdlSupported(){
    	return this.isWsdlSupported;
    }
    
    public void setIsWsdlSupported(boolean value){
    	this.isWsdlSupported = value;
    }

    public List<RecognitionException> getRecognitionExceptions() {
        if (recognitionExceptions == null) {
            recognitionExceptions = new ArrayList<RecognitionException>();
        }
        return recognitionExceptions;
    }
    
    
    public void setValidationStrategyList(List<IValidationStrategy> validationStrategyList){
      this.validationStrategyList = validationStrategyList;   
      initialiseValidationStrategy();
    }
    
    @SuppressWarnings("unchecked")
    public List<IValidationStrategy> getValidatorStrategyList() {
          if (validationStrategyList == null) {
              validationStrategyList = Collections.EMPTY_LIST;
          }
          return validationStrategyList;
    }
    
    private void initialiseValidationStrategy() {
      if (validationStrategyList != null) {
          for (IValidationStrategy validationStrategy : validationStrategyList) {
              validationStrategy.setScriptParser(this);            
          }
      }
    }
    
    public ISymbolTable getSymbolTable(){
	  if (this.symbolTable == null) {
		  this.symbolTable = new XPathSymbolTable();
	  }
	  return this.symbolTable;
    }

    public void setSymbolTable(ISymbolTable sTable){
	  this.symbolTable=sTable;	
    }
    
    private void validateExpression(AST varDefAST, Token token, Expr expression) {
    	if (getRecognitionExceptions().isEmpty()) {
        	XPathScriptParserUtil.validateExpression(this, varDefAST, token, expression);
    	}
    }
    
    private VariableReferenceExpr createXPathVariableRefExpr(AST astExpr) {
        VariableReferenceExpr expr = null;
        try{
            expr = ProcessXPathUtil.createXPathVariableRefExprFromAST(astExpr);
            recordVariableUse(expr);
        }catch(JaxenException ex){
            RecognitionException re = new RecognitionException(ex.getMessage(), null, 1, 1);
            getRecognitionExceptions().add(re);
        }
        return expr;
    }
    
    private void recordVariableUse(VariableReferenceExpr expr){ 
	    if (expr != null) {
	    	String strVarName = expr.getVariableName();
	    	if (strVarName != null) {		
				getSymbolTable().recordVariableInUse(strVarName);
			}
		}
 	}
 	
    private LiteralExpr createXPathLiteralExpr(AST astExpr) {
        LiteralExpr expr = null;
        try{
            expr = ProcessXPathUtil.createXPathLiteralExprFromAST(astExpr);
        }catch(JaxenException ex){
            RecognitionException re = new RecognitionException(ex.getMessage(), null, 1, 1);
            getRecognitionExceptions().add(re);
        }
        return expr;
    }
    
    private FunctionCallExpr createXPathFunctionCallExpr(AST astExpr) {
        FunctionCallExpr expr = null;
        try{
            expr = ProcessXPathUtil.createXPathFunctionCallExprFromAST(astExpr);
        }catch(JaxenException ex){
            RecognitionException re = new RecognitionException(ex.getMessage(), null, 1, 1);
            getRecognitionExceptions().add(re);
        }
        return expr;
    }
    
    private NumberExpr createXPathNumberExpr(AST astExpr) {
        NumberExpr expr = null;
        try{
            expr = ProcessXPathUtil.createXPathNumberExprFromAST(astExpr);
        }catch(JaxenException ex){
            RecognitionException re = new RecognitionException(ex.getMessage(), null, 1, 1);
            getRecognitionExceptions().add(re);
        }
        return expr;
    }
    
    private int createAxis(AST astExpr) {
    	int axis = 1;
        try {
            axis = ProcessXPathUtil.createXPathAxisFromAST(astExpr);
        } catch (JaxenException ex) {
            RecognitionException re = new RecognitionException(ex.getMessage(), null, 1, 1);
            getRecognitionExceptions().add(re);
        }
        return axis;
    }
    
    private XPathExpr createXPath(Expr rootExpr) {
        XPathExpr xpathExpr = null;
        try{
        	if(rootExpr != null){
            	xpathExpr = ProcessXPathUtil.getXPathFactory().createXPath(rootExpr);
        	}
        }catch(JaxenException ex){
            RecognitionException re = new RecognitionException(ex.getMessage(), null, 1, 1);
            getRecognitionExceptions().add(re);
        }
        return xpathExpr;
    }
    
    private FilterExpr createXPathFilterExpr(Expr expr) {
        FilterExpr filterExpr = null;
        try{
        	if(expr != null){
            	filterExpr = ProcessXPathUtil.getXPathFactory().createFilterExpr(expr);
        	}
        }catch(JaxenException ex){
            RecognitionException re = new RecognitionException(ex.getMessage(), null, 1, 1);
            getRecognitionExceptions().add(re);
        }
        return filterExpr;
    }
    
    private Predicate createXPathPredicate(Expr expr) {
        Predicate predicate = null;
        try{
        	if(expr != null){
            	predicate = ProcessXPathUtil.getXPathFactory().createPredicate(expr);
        	}
        }catch(JaxenException ex){
            RecognitionException re = new RecognitionException(ex.getMessage(), null, 1, 1);
            getRecognitionExceptions().add(re);
        }
        return predicate;
    }
    
    private BinaryExpr createAdditiveExpr(Expr lhs, Expr lhr, int additiveOperator) {
        BinaryExpr additiveExpr = null;
        try{
        	if(lhs != null && lhr != null){
            	additiveExpr = ProcessXPathUtil.getXPathFactory().createAdditiveExpr(lhs, lhr, additiveOperator);
        	}
        }catch(JaxenException ex){
            RecognitionException re = new RecognitionException(ex.getMessage(), null, 1, 1);
            getRecognitionExceptions().add(re);
        }
        return additiveExpr;
    }
    
    private BinaryExpr createMultiplicativeExpr(Expr lhs, Expr lhr, int multiplicativeOperator) {
        BinaryExpr multiplicativeExpr = null;
        try{
        	if(lhs != null && lhr != null){
            	multiplicativeExpr = ProcessXPathUtil.getXPathFactory().createMultiplicativeExpr(lhs, lhr, multiplicativeOperator);
        	}
        }catch(JaxenException ex){
            RecognitionException re = new RecognitionException(ex.getMessage(), null, 1, 1);
            getRecognitionExceptions().add(re);
        }
        return multiplicativeExpr;
    }
    
    private UnionExpr createUnionExpr(Expr lhs, Expr lhr) {
        UnionExpr unionExpr = null;
        try{
        	if(lhs != null && lhr != null){
            	unionExpr = ProcessXPathUtil.getXPathFactory().createUnionExpr(lhs, lhr);
        	}
        }catch(JaxenException ex){
            RecognitionException re = new RecognitionException(ex.getMessage(), null, 1, 1);
            getRecognitionExceptions().add(re);
        }
        return unionExpr;
    }
    
    private BinaryExpr createOrExpr(Expr lhs, Expr lhr) {
        BinaryExpr orExpr = null;
        try{
        	if(lhs != null && lhr != null){
            	orExpr = ProcessXPathUtil.getXPathFactory().createOrExpr(lhs, lhr);
        	}
        }catch(JaxenException ex){
            RecognitionException re = new RecognitionException(ex.getMessage(), null, 1, 1);
            getRecognitionExceptions().add(re);
        }
        return orExpr;
    }
        
    private BinaryExpr createAndExpr(Expr lhs, Expr lhr) {
        BinaryExpr andExpr = null;
        try{
        	if(lhs != null && lhr != null){
            	andExpr = ProcessXPathUtil.getXPathFactory().createAndExpr(lhs, lhr);
        	}
        }catch(JaxenException ex){
            RecognitionException re = new RecognitionException(ex.getMessage(), null, 1, 1);
            getRecognitionExceptions().add(re);
        }
        return andExpr;
    }
    
    private BinaryExpr createEqualityExpr(Expr lhs, Expr lhr, int equalityOperator) {
        BinaryExpr equalityExpr = null;
        try{
        	if(lhs != null && lhr != null){
            	equalityExpr = ProcessXPathUtil.getXPathFactory().createEqualityExpr(lhs, lhr, equalityOperator);
        	}
        }catch(JaxenException ex){
            RecognitionException re = new RecognitionException(ex.getMessage(), null, 1, 1);
            getRecognitionExceptions().add(re);
        }
        return equalityExpr;
    }
    
    private BinaryExpr createRelationalExpr(Expr lhs, Expr lhr, int relationalOperator) {
        BinaryExpr relationalExpr = null;
        try{
        	if(lhs != null && lhr != null){
            	relationalExpr = ProcessXPathUtil.getXPathFactory().createRelationalExpr(lhs, lhr, relationalOperator);
        	}
        }catch(JaxenException ex){
            RecognitionException re = new RecognitionException(ex.getMessage(), null, 1, 1);
            getRecognitionExceptions().add(re);
        }
        return relationalExpr;
    }
    
    private Expr createUnaryExpr(Expr expr, int unaryOperator) {
        Expr unaryExpr = null;
        try{
        	if(expr != null){
            	unaryExpr = ProcessXPathUtil.getXPathFactory().createUnaryExpr(expr, unaryOperator);
        	}
        }catch(JaxenException ex){
            RecognitionException re = new RecognitionException(ex.getMessage(), null, 1, 1);
            getRecognitionExceptions().add(re);
        }
        return unaryExpr;
    }
    
    private PathExpr createPathExpr(FilterExpr currentFilterExpr, LocationPath currentLocationPath) {
        PathExpr pathExpr = null;
        try{
        	if(currentFilterExpr != null && currentLocationPath != null){
            	pathExpr = ProcessXPathUtil.getXPathFactory().createPathExpr(currentFilterExpr, currentLocationPath);
        	}
        }catch(JaxenException ex){
            RecognitionException re = new RecognitionException(ex.getMessage(), null, 1, 1);
            getRecognitionExceptions().add(re);
        }
        return pathExpr;
    }
    
    private LocationPath createAbsoluteLocationPath(boolean isDoubleSlash) {
        LocationPath absoluteLocationPath = new TCDefaultAbsoluteLocationPath(isDoubleSlash);
        return absoluteLocationPath;
    }
    
    private LocationPath createRelativeLocationPath() {
        LocationPath relativeLocationPath = null;
        try{
        	relativeLocationPath = ProcessXPathUtil.getXPathFactory().createRelativeLocationPath();
        }catch(JaxenException ex){
            RecognitionException re = new RecognitionException(ex.getMessage(), null, 1, 1);
            getRecognitionExceptions().add(re);
        }
        return relativeLocationPath;
    }
    
    private Step createNameStep(int axis, String prefix, AST expr) {
        Step nameStep = null;
        try{
            	nameStep = ProcessXPathUtil.createXPathNameStepFromAST(axis, prefix, expr);
        }catch(JaxenException ex){
            RecognitionException re = new RecognitionException(ex.getMessage(), null, 1, 1);
            getRecognitionExceptions().add(re);
        }
        return nameStep;
    }
    
    private Step createProcessingInstructionStep(AST astExpr, int axis) {
        Step processingInstructionStep = null;
        try{
            processingInstructionStep = ProcessXPathUtil.createXPathProcessingInstructionStepFromAST(astExpr, axis);
        }catch(JaxenException ex){
            RecognitionException re = new RecognitionException(ex.getMessage(), null, 1, 1);
            getRecognitionExceptions().add(re);
        }
        return processingInstructionStep;
    }
    
    private Step createAllNodeStep(int axis) {
        Step allNodeStep = null;
        try{
            allNodeStep = ProcessXPathUtil.getXPathFactory().createAllNodeStep(axis);
        }catch(JaxenException ex){
            RecognitionException re = new RecognitionException(ex.getMessage(), null, 1, 1);
            getRecognitionExceptions().add(re);
        }
        return allNodeStep;
    }
    
    private Step createCommentNodeStep(int axis) {
        Step commentNodeStep = null;
        try{
            commentNodeStep = ProcessXPathUtil.getXPathFactory().createCommentNodeStep(axis);
        }catch(JaxenException ex){
            RecognitionException re = new RecognitionException(ex.getMessage(), null, 1, 1);
            getRecognitionExceptions().add(re);
        }
        return commentNodeStep;
    }
    
    private Step createTextNodeStep(int axis) {
        Step textNodeStep = null;
        try{
            textNodeStep = ProcessXPathUtil.getXPathFactory().createTextNodeStep(axis);
        }catch(JaxenException ex){
            RecognitionException re = new RecognitionException(ex.getMessage(), null, 1, 1);
            getRecognitionExceptions().add(re);
        }
        return textNodeStep;
    }
	
	}

xpath
{XPathExpr currentXPathExpr = null;
Expr currentRootExpr = null;}
	:
		currentRootExpr = union_expr
{
if(currentRootExpr != null){
	currentXPathExpr = createXPath(currentRootExpr);
	if(currentXPathExpr != null){
		finalExpr = currentXPathExpr.getRootExpr();
	}
}
validateExpression(#xpath,LT(1), finalExpr);
}
	;


location_path  returns [LocationPath currentLocationPath = null]
	:
			currentLocationPath = absolute_location_path
		|	currentLocationPath = relative_location_path
	;

absolute_location_path returns [LocationPath currentLocationPath = null]
{currentLocationPath = createAbsoluteLocationPath(false);}
	:
		(	SLASH^
		|	DOUBLE_SLASH^
			{currentLocationPath = createAbsoluteLocationPath(true);}
		)
		(	(AT|STAR|IDENTIFIER)=>
			i_relative_location_path[currentLocationPath]
			|
		)
	;

relative_location_path returns [LocationPath currentLocationPath = null]
{currentLocationPath = createRelativeLocationPath();}
	:
		i_relative_location_path[currentLocationPath]
	;

i_relative_location_path[LocationPath locationPath]
	:
		step[locationPath]
		(	(	SLASH^
			|	DOUBLE_SLASH^
			) 	step[locationPath]
		)*
	;

step[LocationPath locationPath]
{axis = 1;
String prefix = null;
Step currentStep = null;}
	:
		((
			// If it has an axis
			(	(IDENTIFIER DOUBLE_COLON | AT)=> axis{axis = createAxis(returnAST);}
			|
			)

			(
				(	
					(
							(ns:IDENTIFIER COLON)? 
{
if(ns != null){
	prefix = ns.getText();
}
}
							(	id:IDENTIFIER
							|	STAR
							)
{
AST tempStepAST = #step;
if(tempStepAST != null){
	currentStep = createNameStep(axis, prefix, tempStepAST);
}
}
					)
				)

				|	

				special_step[locationPath]
			)
			(
				predicate[currentStep]

			)*
		)
		|	abbr_step[locationPath]
			(
				predicate[currentStep]
			)*)
{if (locationPath != null && currentStep != null) {
	locationPath.addStep(currentStep);
}
}
	;

special_step[LocationPath locationPath]
{String specialStepName = null;
Step currentStep = null;}
	:
	{
		LT(1).getText().equals("processing-instruction")
	}?
		IDENTIFIER LEFT_PAREN 
			(	IDENTIFIER 
			)?
		RIGHT_PAREN
{
currentStep = createProcessingInstructionStep(#special_step, axis);
}
	|
	{
		LT(1).getText().equals("comment")
			||
		LT(1).getText().equals("text")
			||
		LT(1).getText().equals("node")
	}?{specialStepName = LT(1).getText();}
		IDENTIFIER LEFT_PAREN RIGHT_PAREN
{
if(specialStepName != null && specialStepName.equals("comment")){
	currentStep = createCommentNodeStep(axis);
}else if(specialStepName != null && specialStepName.equals("text")){
	currentStep = createTextNodeStep(axis);
}else if(specialStepName != null && specialStepName.equals("node")){
	currentStep = createAllNodeStep(axis);
}

if(locationPath != null && currentStep != null){
	locationPath.addStep(currentStep);
}
}
	;

axis
	:
		(	id:IDENTIFIER DOUBLE_COLON^
		|	AT
		)
	;

// ----------------------------------------
//		Section 2.4
//			Predicates
// ----------------------------------------

// .... production [8] ....
//
predicate[Predicated predicated]
	:
		LEFT_BRACKET^ predicate_expr[predicated] RIGHT_BRACKET!
	;

// .... production [9] ....
//
predicate_expr[Predicated predicated]
{Expr currentExpr = null;
Predicate currentPredicate = null;}
	:
		currentExpr = expr
{if(currentExpr != null){
currentPredicate = createXPathPredicate(currentExpr);
if(predicated != null && currentPredicate != null){
	predicated.addPredicate(currentPredicate);
}}}
	;

// .... production [12] ....
//
abbr_step[LocationPath locationPath]
{Step currentStep = null;}
	:
			DOT
			{currentStep = createAllNodeStep(Axis.SELF);}
		|	DOT_DOT
			{currentStep = createAllNodeStep(Axis.PARENT);}
{if(locationPath != null && currentStep != null){
	locationPath.addStep(currentStep);
}}
	;

// .... production [13] ....
//
abbr_axis_specifier
	:
		( AT )?
	;


// ----------------------------------------
//		Section 3
//			Expressions
// ----------------------------------------

// ----------------------------------------
//		Section 3.1
//			Basics
// ----------------------------------------

// .... production [14] ....
//
expr returns [Expr currentExpr = null]
	:
		currentExpr = or_expr
	;

// .... production [15] ....
//
primary_expr returns [Expr currentPrimaryExpr = null]
	:
			currentPrimaryExpr = variable_reference
		|	LEFT_PAREN! currentPrimaryExpr = expr RIGHT_PAREN!
		|	currentPrimaryExpr = literal
		|	currentPrimaryExpr = number
		|	currentPrimaryExpr = function_call
	;

literal returns [LiteralExpr currentLiteralExpr = null]
	:
		lit:LITERAL^{currentLiteralExpr = createXPathLiteralExpr(#literal);}
	;

number returns [NumberExpr numberExpr = null]
	:
		NUMBER^{numberExpr = createXPathNumberExpr(#number);}
	;

variable_reference returns [VariableReferenceExpr currentVariableReferenceExpr = null] 
	:
		DOLLAR_SIGN^ IDENTIFIER^ 
		{
currentVariableReferenceExpr = createXPathVariableRefExpr(#variable_reference);
		}
	;

// ----------------------------------------
//		Section 3.2
//			Function Calls
// ----------------------------------------

// .... production [16] ....
//
function_call returns [FunctionCallExpr currentFunctionCall = null]
	:
		IDENTIFIER LEFT_PAREN^{currentFunctionCall = createXPathFunctionCallExpr(#function_call);} ( arg_list[currentFunctionCall] )? RIGHT_PAREN!
	;

// .... production [16.1] ....
//
arg_list[FunctionCallExpr functionCall]
{Expr currentArgumentExpr = null;}
	:
		currentArgumentExpr = argument
{if (functionCall != null && currentArgumentExpr != null) {
	functionCall.addParameter(currentArgumentExpr);
}
}

		( COMMA currentArgumentExpr = argument
{
if (functionCall != null && currentArgumentExpr != null) {
	functionCall.addParameter(currentArgumentExpr);
}
}
		)*
	;

// .... production [17] ....
//
argument returns [Expr currentArgumentExpr = null]
	:
		currentArgumentExpr = expr
	;

// ----------------------------------------
//		Section 3.3
//			Node-sets
// ----------------------------------------

// .... production [18] ....
//
union_expr returns[Expr currentUnionExpr = null]
{Expr lhs = null;
 Expr rhs = null;
}
	:
		lhs = path_expr
		( 	PIPE! rhs = path_expr
{
if(lhs != null && rhs != null){
	currentUnionExpr = createUnionExpr(lhs, rhs);
	lhs = currentUnionExpr;
}else if(lhs != null){
	currentUnionExpr = lhs;
}
}
		)*
{currentUnionExpr = lhs;}
	;

// .... production [19] ....
//

path_expr returns [Expr currentPathExpr = null]
{LocationPath currentLocationPath = null;
FilterExpr currentFilterExpr = null;
}
	:	
		// This is here to differentiate between the
		// special case of the first step being a NodeTypeTest
		// or just a normal filter-expr function call.

		// Is it a special nodeType 'function name'

		((IDENTIFIER LEFT_PAREN)=>{ 
			LT(1).getText().equals("processing-instruction")
				||
			LT(1).getText().equals("comment")
				||
			LT(1).getText().equals("text")
				||
			LT(1).getText().equals("node")
		}?

		currentLocationPath = location_path
		|	
		(IDENTIFIER LEFT_PAREN)=>
		currentFilterExpr = filter_expr 
			(	currentLocationPath = absolute_location_path 
			)?
		|
		(DOT|DOT_DOT|SLASH|DOUBLE_SLASH|IDENTIFIER|AT)=>
		currentLocationPath = location_path	
		|	
		currentFilterExpr = filter_expr 
			(	currentLocationPath = absolute_location_path
			)?)
{if (currentLocationPath != null && currentFilterExpr != null) {
currentPathExpr = createPathExpr(currentFilterExpr, currentLocationPath);
}else if(currentFilterExpr != null){
	currentPathExpr = currentFilterExpr;
}else if(currentLocationPath != null){
	currentPathExpr = currentLocationPath;
}
}	
	;

// .... production [20] ....
//
filter_expr returns [FilterExpr currentFilterExpr = null]
{Expr currentPrimaryExpr = null;}
	:
		currentPrimaryExpr = primary_expr 
{if(currentPrimaryExpr != null){
currentFilterExpr = createXPathFilterExpr(currentPrimaryExpr);
}
}
		(	predicate[currentFilterExpr] 
		)*
	;


// ----------------------------------------
//		Section 3.4
//			Booleans
// ----------------------------------------

// .... production [21] ....
//
or_expr returns [Expr currentOrExpr = null]
{Expr lhs = null;
 Expr rhs = null;}
	:
		lhs = and_expr (	KW_OR^				
						rhs = and_expr
{
if (lhs != null && rhs != null) {
	currentOrExpr = createOrExpr(lhs, rhs);
	lhs = currentOrExpr;
}else if(lhs != null){
	currentOrExpr = lhs;
}
} 
		)*
{currentOrExpr = lhs;}
	;

// .... production [22] ....
//
and_expr returns [Expr currentAndExpr = null]
{Expr lhs = null;
 Expr rhs = null;}
	:
		lhs = equality_expr (	KW_AND^ 			
							rhs = equality_expr )?
{
if (lhs != null && rhs != null) {
	currentAndExpr = createAndExpr(lhs, rhs);
}else if(lhs != null){
	currentAndExpr = lhs;
}
}
	;

// .... production [23] ....
//
equality_expr returns [Expr currentEqualityExpr = null]
{Expr lhs = null;
 Expr rhs = null;
 int equalityOperator = Operator.NO_OP;}
	:
		lhs = relational_expr (	(	EQUALS^{equalityOperator = Operator.EQUALS;}		
								|	NOT_EQUALS^{equalityOperator = Operator.NOT_EQUALS;}
								)
								rhs = relational_expr
							)?
{
if (lhs != null && rhs != null) {
	currentEqualityExpr = createEqualityExpr(lhs, rhs, equalityOperator);
}else if(lhs != null){
	currentEqualityExpr = lhs;
}
}
	;

// .... production [24] ....
//
relational_expr returns [Expr currentRelationalExpr = null]
{Expr lhs = null;
 Expr rhs = null;
 int relationalOperator = Operator.NO_OP;}
	:
		lhs = additive_expr	(	(	LT^{relationalOperator = Operator.LESS_THAN;}		
								|	GT^{relationalOperator = Operator.GREATER_THAN;}	
								|	LTE^{relationalOperator = Operator.LESS_THAN_EQUALS;}	
								|	GTE^{relationalOperator = Operator.GREATER_THAN_EQUALS;}	
								)
								rhs = additive_expr
							)?
{if(lhs != null && rhs != null){
	currentRelationalExpr = createRelationalExpr(lhs, rhs, relationalOperator);
}else if(lhs != null){
	currentRelationalExpr = lhs;
}
}
	;

// ----------------------------------------
//		Section 3.5
//			Numbers
// ----------------------------------------

// .... production [25] ....
//
additive_expr returns [Expr currentAdditiveExpr = null]
{int additiveOperator = Operator.ADD;
 Expr lhs = null;
 Expr rhs = null;
}
	:	
		lhs = mult_expr	
							(	(	PLUS^{additiveOperator = Operator.ADD;}	
							|	MINUS^{additiveOperator = Operator.SUBTRACT;}
							)
							rhs = mult_expr
						)?
{if(lhs != null && rhs != null){
	currentAdditiveExpr = createAdditiveExpr(lhs, rhs, additiveOperator);
}else if(lhs != null){
	currentAdditiveExpr = lhs;
}
}
	;

// .... production [26] ....
//
mult_expr returns [Expr currentMultiplicativeExpr = null]
{int multiplicativeOperator = Operator.MULTIPLY;
 Expr lhs = null;
 Expr rhs = null;
}
	:
		lhs = unary_expr	
							(	(	STAR^{multiplicativeOperator = Operator.MULTIPLY;}	
							|	DIV^{multiplicativeOperator = Operator.DIV;}	
							|	MOD^{multiplicativeOperator = Operator.MOD;}	
							)
							rhs = unary_expr
						)?
{if(lhs != null && rhs != null){
	currentMultiplicativeExpr = createMultiplicativeExpr(lhs, rhs, multiplicativeOperator);
}else if(lhs != null){
	currentMultiplicativeExpr = lhs;
}
}
	;

// .... production [27] ....
//
unary_expr returns [Expr currentUnaryExpr = null]
{int unaryOperator = Operator.NO_OP;
Expr currentExpr = null;}
	:
			(currentExpr = union_expr
		|
			MINUS currentExpr = unary_expr
			{unaryOperator = Operator.NEGATIVE;}
			)
{if(currentExpr != null){
currentUnaryExpr = createUnaryExpr(currentExpr, unaryOperator);
}}
	;

class XPathLexer extends Lexer;
	options
	{
		charVocabulary='\3'..'\377';
		k = 3;
		importVocab=XPath;
	}

	tokens
	{
		KW_OR = "or";
		KW_AND = "and";
	}
	
	{
	    
	    private final int maxIteration = 5;
	
		private boolean isInfiniteLoop() {
			boolean infiniteLoop = true;
			int len = text.length();
			int index = 0;
			int maxLoopCount = maxIteration > len ? len : maxIteration;
			while (index < maxLoopCount) {
				char c = text.charAt(len - 1);
				if (c != EOF_CHAR) {
					infiniteLoop = false;
					break;
				}
				len--;
				index++;
			}
			return infiniteLoop;
		}
	}
WS
	:
		('\n' | ' ' | '\t' | '\r')+
		{
			$setType(Token.SKIP);
		}
	;

protected
DIGIT
	:
		('0'..'9')
	;

protected 
SINGLE_QUOTE_STRING
	:
		'\''! (~('\''))* '\''!
	;

protected
DOUBLE_QUOTE_STRING
	:
		'"'! (~('"'))* '"'!
	;

LITERAL
	:
		SINGLE_QUOTE_STRING | DOUBLE_QUOTE_STRING
	;

NUMBER
	:
		(DIGIT)+ ('.' (DIGIT)+)?
	;

IDENTIFIER

	options
	{
		testLiterals=true;
	}

	: 	
		('\241'..'\377'|'a'..'z'|'A'..'Z'|'_') ('\241'..'\377'|'a'..'z'|'A'..'Z'|'-'|'_'|'0'..'9'|'.')*	
	;

LEFT_PAREN
	:	'('		;

RIGHT_PAREN	
	:	')'		;

LEFT_BRACKET
	:	'['		;

RIGHT_BRACKET	
	:	']'		;
	
PIPE
	:	'|'		;

DOT
	:	'.'		;

DOT_DOT
	:	".."	;

AT
	:	'@'		;

COMMA
	:	','		;

DOUBLE_COLON
	:	"::"	;

COLON
	:	":"		;

SLASH
	:	'/'		;

DOUBLE_SLASH
	:	'/' '/'	;

DOLLAR_SIGN
	:	'$'		;

PLUS
	:	'+'		;

MINUS
	:	'-'		;

EQUALS
	:	'='		;

NOT_EQUALS
	:	"!="	;

LT
	:	'<'		;

LTE
	:	"<="	;

GT
	:	'>'		;

GTE
	:	">="	;

STAR
	:	'*'		;
	
// multiple-line comments
protected
COMMENT_DATA
        :       (       /*      '\r' '\n' can be matched in one alternative or by matching
                               '\r' in one iteration and '\n' in another.  I am trying to
                               handle any flavor of newline that comes in, but the language
                               that allows both "\r\n" and "\r" and "\n" to all be valid
                               newline is ambiguous.  Consequently, the resulting grammar
                               must be ambiguous.  I'm shutting this warning off.
                        */
                       options {
                               generateAmbigWarnings=false;
                       }
               :
                       {LA(2)!='-' && LA(3)!='>'}? '-' // allow '-' if not "-->"
               |       '\r' '\n'              {newline();}
               |       '\r'                   {newline();}
               |       '\n'                   {newline();}
               |       ~('-'|'\n'|'\r')
               )*
        ;
 
 
COMMENT
        :       "<!--" COMMENT_DATA "-->"      { $setType(Token.SKIP); }
        ;

