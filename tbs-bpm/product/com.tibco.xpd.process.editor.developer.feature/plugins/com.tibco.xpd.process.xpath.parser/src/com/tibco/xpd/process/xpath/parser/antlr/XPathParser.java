// $ANTLR 2.7.6 (2005-12-22): "xpath.g" -> "XPathParser.java"$

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

import antlr.TokenBuffer;
import antlr.TokenStreamException;
import antlr.TokenStreamIOException;
import antlr.ANTLRException;
import antlr.LLkParser;
import antlr.Token;
import antlr.TokenStream;
import antlr.RecognitionException;
import antlr.NoViableAltException;
import antlr.MismatchedTokenException;
import antlr.SemanticException;
import antlr.ParserSharedInputState;
import antlr.collections.impl.BitSet;
import antlr.collections.AST;
import java.util.Hashtable;
import antlr.ASTFactory;
import antlr.ASTPair;
import antlr.collections.impl.ASTArray;

public class XPathParser extends antlr.LLkParser       implements XPathTokenTypes
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
	
	
protected XPathParser(TokenBuffer tokenBuf, int k) {
  super(tokenBuf,k);
  tokenNames = _tokenNames;
  buildTokenTypeASTClassMap();
  astFactory = new ASTFactory(getTokenTypeToASTClassMap());
}

public XPathParser(TokenBuffer tokenBuf) {
  this(tokenBuf,2);
}

protected XPathParser(TokenStream lexer, int k) {
  super(lexer,k);
  tokenNames = _tokenNames;
  buildTokenTypeASTClassMap();
  astFactory = new ASTFactory(getTokenTypeToASTClassMap());
}

public XPathParser(TokenStream lexer) {
  this(lexer,2);
}

public XPathParser(ParserSharedInputState state) {
  super(state,2);
  tokenNames = _tokenNames;
  buildTokenTypeASTClassMap();
  astFactory = new ASTFactory(getTokenTypeToASTClassMap());
}

	public final void xpath() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST xpath_AST = null;
		XPathExpr currentXPathExpr = null;
		Expr currentRootExpr = null;
		
		try {      // for error handling
			currentRootExpr=union_expr();
			astFactory.addASTChild(currentAST, returnAST);
			if ( inputState.guessing==0 ) {
				xpath_AST = (AST)currentAST.root;
				
				if(currentRootExpr != null){
					currentXPathExpr = createXPath(currentRootExpr);
					if(currentXPathExpr != null){
						finalExpr = currentXPathExpr.getRootExpr();
					}
				}
				validateExpression(xpath_AST,LT(1), finalExpr);
				
			}
			xpath_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_0);
			} else {
			  throw ex;
			}
		}
		returnAST = xpath_AST;
	}
	
	public final Expr  union_expr() throws RecognitionException, TokenStreamException {
		Expr currentUnionExpr = null;
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST union_expr_AST = null;
		Expr lhs = null;
		Expr rhs = null;
		
		
		try {      // for error handling
			lhs=path_expr();
			astFactory.addASTChild(currentAST, returnAST);
			{
			_loop50:
			do {
				if ((LA(1)==PIPE)) {
					match(PIPE);
					rhs=path_expr();
					astFactory.addASTChild(currentAST, returnAST);
					if ( inputState.guessing==0 ) {
						
						if(lhs != null && rhs != null){
							currentUnionExpr = createUnionExpr(lhs, rhs);
							lhs = currentUnionExpr;
						}else if(lhs != null){
							currentUnionExpr = lhs;
						}
						
					}
				}
				else {
					break _loop50;
				}
				
			} while (true);
			}
			if ( inputState.guessing==0 ) {
				currentUnionExpr = lhs;
			}
			union_expr_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_1);
			} else {
			  throw ex;
			}
		}
		returnAST = union_expr_AST;
		return currentUnionExpr;
	}
	
	public final LocationPath  location_path() throws RecognitionException, TokenStreamException {
		LocationPath currentLocationPath = null;
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST location_path_AST = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case SLASH:
			case DOUBLE_SLASH:
			{
				currentLocationPath=absolute_location_path();
				astFactory.addASTChild(currentAST, returnAST);
				location_path_AST = (AST)currentAST.root;
				break;
			}
			case AT:
			case STAR:
			case IDENTIFIER:
			case DOT:
			case DOT_DOT:
			{
				currentLocationPath=relative_location_path();
				astFactory.addASTChild(currentAST, returnAST);
				location_path_AST = (AST)currentAST.root;
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_2);
			} else {
			  throw ex;
			}
		}
		returnAST = location_path_AST;
		return currentLocationPath;
	}
	
	public final LocationPath  absolute_location_path() throws RecognitionException, TokenStreamException {
		LocationPath currentLocationPath = null;
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST absolute_location_path_AST = null;
		currentLocationPath = createAbsoluteLocationPath(false);
		
		try {      // for error handling
			{
			switch ( LA(1)) {
			case SLASH:
			{
				AST tmp2_AST = null;
				tmp2_AST = astFactory.create(LT(1));
				astFactory.makeASTRoot(currentAST, tmp2_AST);
				match(SLASH);
				break;
			}
			case DOUBLE_SLASH:
			{
				AST tmp3_AST = null;
				tmp3_AST = astFactory.create(LT(1));
				astFactory.makeASTRoot(currentAST, tmp3_AST);
				match(DOUBLE_SLASH);
				if ( inputState.guessing==0 ) {
					currentLocationPath = createAbsoluteLocationPath(true);
				}
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			{
			boolean synPredMatched7 = false;
			if (((_tokenSet_3.member(LA(1))) && (_tokenSet_4.member(LA(2))))) {
				int _m7 = mark();
				synPredMatched7 = true;
				inputState.guessing++;
				try {
					{
					switch ( LA(1)) {
					case AT:
					{
						match(AT);
						break;
					}
					case STAR:
					{
						match(STAR);
						break;
					}
					case IDENTIFIER:
					{
						match(IDENTIFIER);
						break;
					}
					default:
					{
						throw new NoViableAltException(LT(1), getFilename());
					}
					}
					}
				}
				catch (RecognitionException pe) {
					synPredMatched7 = false;
				}
				rewind(_m7);
inputState.guessing--;
			}
			if ( synPredMatched7 ) {
				i_relative_location_path(currentLocationPath);
				astFactory.addASTChild(currentAST, returnAST);
			}
			else if ((_tokenSet_2.member(LA(1))) && (_tokenSet_5.member(LA(2)))) {
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			
			}
			absolute_location_path_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_2);
			} else {
			  throw ex;
			}
		}
		returnAST = absolute_location_path_AST;
		return currentLocationPath;
	}
	
	public final LocationPath  relative_location_path() throws RecognitionException, TokenStreamException {
		LocationPath currentLocationPath = null;
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST relative_location_path_AST = null;
		currentLocationPath = createRelativeLocationPath();
		
		try {      // for error handling
			i_relative_location_path(currentLocationPath);
			astFactory.addASTChild(currentAST, returnAST);
			relative_location_path_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_2);
			} else {
			  throw ex;
			}
		}
		returnAST = relative_location_path_AST;
		return currentLocationPath;
	}
	
	public final void i_relative_location_path(
		LocationPath locationPath
	) throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST i_relative_location_path_AST = null;
		
		try {      // for error handling
			step(locationPath);
			astFactory.addASTChild(currentAST, returnAST);
			{
			_loop12:
			do {
				if ((LA(1)==SLASH||LA(1)==DOUBLE_SLASH)) {
					{
					switch ( LA(1)) {
					case SLASH:
					{
						AST tmp4_AST = null;
						tmp4_AST = astFactory.create(LT(1));
						astFactory.makeASTRoot(currentAST, tmp4_AST);
						match(SLASH);
						break;
					}
					case DOUBLE_SLASH:
					{
						AST tmp5_AST = null;
						tmp5_AST = astFactory.create(LT(1));
						astFactory.makeASTRoot(currentAST, tmp5_AST);
						match(DOUBLE_SLASH);
						break;
					}
					default:
					{
						throw new NoViableAltException(LT(1), getFilename());
					}
					}
					}
					step(locationPath);
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					break _loop12;
				}
				
			} while (true);
			}
			i_relative_location_path_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_2);
			} else {
			  throw ex;
			}
		}
		returnAST = i_relative_location_path_AST;
	}
	
	public final void step(
		LocationPath locationPath
	) throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST step_AST = null;
		Token  ns = null;
		AST ns_AST = null;
		Token  id = null;
		AST id_AST = null;
		axis = 1;
		String prefix = null;
		Step currentStep = null;
		
		try {      // for error handling
			{
			switch ( LA(1)) {
			case AT:
			case STAR:
			case IDENTIFIER:
			{
				{
				{
				boolean synPredMatched18 = false;
				if (((LA(1)==AT||LA(1)==IDENTIFIER) && ((LA(2) >= STAR && LA(2) <= DOUBLE_COLON)))) {
					int _m18 = mark();
					synPredMatched18 = true;
					inputState.guessing++;
					try {
						{
						switch ( LA(1)) {
						case IDENTIFIER:
						{
							match(IDENTIFIER);
							match(DOUBLE_COLON);
							break;
						}
						case AT:
						{
							match(AT);
							break;
						}
						default:
						{
							throw new NoViableAltException(LT(1), getFilename());
						}
						}
						}
					}
					catch (RecognitionException pe) {
						synPredMatched18 = false;
					}
					rewind(_m18);
inputState.guessing--;
				}
				if ( synPredMatched18 ) {
					axis();
					astFactory.addASTChild(currentAST, returnAST);
					if ( inputState.guessing==0 ) {
						axis = createAxis(returnAST);
					}
				}
				else if ((LA(1)==STAR||LA(1)==IDENTIFIER) && (_tokenSet_6.member(LA(2)))) {
				}
				else {
					throw new NoViableAltException(LT(1), getFilename());
				}
				
				}
				{
				if ((LA(1)==STAR||LA(1)==IDENTIFIER) && (_tokenSet_7.member(LA(2)))) {
					{
					{
					{
					if ((LA(1)==IDENTIFIER) && (LA(2)==COLON)) {
						ns = LT(1);
						ns_AST = astFactory.create(ns);
						astFactory.addASTChild(currentAST, ns_AST);
						match(IDENTIFIER);
						AST tmp6_AST = null;
						tmp6_AST = astFactory.create(LT(1));
						astFactory.addASTChild(currentAST, tmp6_AST);
						match(COLON);
					}
					else if ((LA(1)==STAR||LA(1)==IDENTIFIER) && (_tokenSet_8.member(LA(2)))) {
					}
					else {
						throw new NoViableAltException(LT(1), getFilename());
					}
					
					}
					if ( inputState.guessing==0 ) {
						
						if(ns != null){
							prefix = ns.getText();
						}
						
					}
					{
					switch ( LA(1)) {
					case IDENTIFIER:
					{
						id = LT(1);
						id_AST = astFactory.create(id);
						astFactory.addASTChild(currentAST, id_AST);
						match(IDENTIFIER);
						break;
					}
					case STAR:
					{
						AST tmp7_AST = null;
						tmp7_AST = astFactory.create(LT(1));
						astFactory.addASTChild(currentAST, tmp7_AST);
						match(STAR);
						break;
					}
					default:
					{
						throw new NoViableAltException(LT(1), getFilename());
					}
					}
					}
					if ( inputState.guessing==0 ) {
						step_AST = (AST)currentAST.root;
						
						AST tempStepAST = step_AST;
						if(tempStepAST != null){
							currentStep = createNameStep(axis, prefix, tempStepAST);
						}
						
					}
					}
					}
				}
				else if ((LA(1)==IDENTIFIER) && (LA(2)==LEFT_PAREN)) {
					special_step(locationPath);
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					throw new NoViableAltException(LT(1), getFilename());
				}
				
				}
				{
				_loop25:
				do {
					if ((LA(1)==LEFT_BRACKET)) {
						predicate(currentStep);
						astFactory.addASTChild(currentAST, returnAST);
					}
					else {
						break _loop25;
					}
					
				} while (true);
				}
				}
				break;
			}
			case DOT:
			case DOT_DOT:
			{
				abbr_step(locationPath);
				astFactory.addASTChild(currentAST, returnAST);
				{
				_loop27:
				do {
					if ((LA(1)==LEFT_BRACKET)) {
						predicate(currentStep);
						astFactory.addASTChild(currentAST, returnAST);
					}
					else {
						break _loop27;
					}
					
				} while (true);
				}
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			if ( inputState.guessing==0 ) {
				if (locationPath != null && currentStep != null) {
					locationPath.addStep(currentStep);
				}
				
			}
			step_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_9);
			} else {
			  throw ex;
			}
		}
		returnAST = step_AST;
	}
	
	public final void axis() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST axis_AST = null;
		Token  id = null;
		AST id_AST = null;
		
		try {      // for error handling
			{
			switch ( LA(1)) {
			case IDENTIFIER:
			{
				id = LT(1);
				id_AST = astFactory.create(id);
				astFactory.addASTChild(currentAST, id_AST);
				match(IDENTIFIER);
				AST tmp8_AST = null;
				tmp8_AST = astFactory.create(LT(1));
				astFactory.makeASTRoot(currentAST, tmp8_AST);
				match(DOUBLE_COLON);
				break;
			}
			case AT:
			{
				AST tmp9_AST = null;
				tmp9_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp9_AST);
				match(AT);
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			axis_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_10);
			} else {
			  throw ex;
			}
		}
		returnAST = axis_AST;
	}
	
	public final void special_step(
		LocationPath locationPath
	) throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST special_step_AST = null;
		String specialStepName = null;
		Step currentStep = null;
		
		try {      // for error handling
			if (((LA(1)==IDENTIFIER) && (LA(2)==LEFT_PAREN))&&(
		LT(1).getText().equals("processing-instruction")
	)) {
				AST tmp10_AST = null;
				tmp10_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp10_AST);
				match(IDENTIFIER);
				AST tmp11_AST = null;
				tmp11_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp11_AST);
				match(LEFT_PAREN);
				{
				switch ( LA(1)) {
				case IDENTIFIER:
				{
					AST tmp12_AST = null;
					tmp12_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp12_AST);
					match(IDENTIFIER);
					break;
				}
				case RIGHT_PAREN:
				{
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
				AST tmp13_AST = null;
				tmp13_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp13_AST);
				match(RIGHT_PAREN);
				if ( inputState.guessing==0 ) {
					special_step_AST = (AST)currentAST.root;
					
					currentStep = createProcessingInstructionStep(special_step_AST, axis);
					
				}
				special_step_AST = (AST)currentAST.root;
			}
			else if (((LA(1)==IDENTIFIER) && (LA(2)==LEFT_PAREN))&&(
		LT(1).getText().equals("comment")
			||
		LT(1).getText().equals("text")
			||
		LT(1).getText().equals("node")
	)) {
				if ( inputState.guessing==0 ) {
					specialStepName = LT(1).getText();
				}
				AST tmp14_AST = null;
				tmp14_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp14_AST);
				match(IDENTIFIER);
				AST tmp15_AST = null;
				tmp15_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp15_AST);
				match(LEFT_PAREN);
				AST tmp16_AST = null;
				tmp16_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp16_AST);
				match(RIGHT_PAREN);
				if ( inputState.guessing==0 ) {
					
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
				special_step_AST = (AST)currentAST.root;
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_8);
			} else {
			  throw ex;
			}
		}
		returnAST = special_step_AST;
	}
	
	public final void predicate(
		Predicated predicated
	) throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST predicate_AST = null;
		
		try {      // for error handling
			AST tmp17_AST = null;
			tmp17_AST = astFactory.create(LT(1));
			astFactory.makeASTRoot(currentAST, tmp17_AST);
			match(LEFT_BRACKET);
			predicate_expr(predicated);
			astFactory.addASTChild(currentAST, returnAST);
			match(RIGHT_BRACKET);
			predicate_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_8);
			} else {
			  throw ex;
			}
		}
		returnAST = predicate_AST;
	}
	
	public final void abbr_step(
		LocationPath locationPath
	) throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST abbr_step_AST = null;
		Step currentStep = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case DOT:
			{
				AST tmp19_AST = null;
				tmp19_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp19_AST);
				match(DOT);
				if ( inputState.guessing==0 ) {
					currentStep = createAllNodeStep(Axis.SELF);
				}
				abbr_step_AST = (AST)currentAST.root;
				break;
			}
			case DOT_DOT:
			{
				AST tmp20_AST = null;
				tmp20_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp20_AST);
				match(DOT_DOT);
				if ( inputState.guessing==0 ) {
					currentStep = createAllNodeStep(Axis.PARENT);
				}
				if ( inputState.guessing==0 ) {
					if(locationPath != null && currentStep != null){
						locationPath.addStep(currentStep);
					}
				}
				abbr_step_AST = (AST)currentAST.root;
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_8);
			} else {
			  throw ex;
			}
		}
		returnAST = abbr_step_AST;
	}
	
	public final void predicate_expr(
		Predicated predicated
	) throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST predicate_expr_AST = null;
		Expr currentExpr = null;
		Predicate currentPredicate = null;
		
		try {      // for error handling
			currentExpr=expr();
			astFactory.addASTChild(currentAST, returnAST);
			if ( inputState.guessing==0 ) {
				if(currentExpr != null){
				currentPredicate = createXPathPredicate(currentExpr);
				if(predicated != null && currentPredicate != null){
					predicated.addPredicate(currentPredicate);
				}}
			}
			predicate_expr_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_11);
			} else {
			  throw ex;
			}
		}
		returnAST = predicate_expr_AST;
	}
	
	public final Expr  expr() throws RecognitionException, TokenStreamException {
		Expr currentExpr = null;
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST expr_AST = null;
		
		try {      // for error handling
			currentExpr=or_expr();
			astFactory.addASTChild(currentAST, returnAST);
			expr_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_12);
			} else {
			  throw ex;
			}
		}
		returnAST = expr_AST;
		return currentExpr;
	}
	
	public final void abbr_axis_specifier() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST abbr_axis_specifier_AST = null;
		
		try {      // for error handling
			{
			switch ( LA(1)) {
			case AT:
			{
				AST tmp21_AST = null;
				tmp21_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp21_AST);
				match(AT);
				break;
			}
			case EOF:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			abbr_axis_specifier_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_0);
			} else {
			  throw ex;
			}
		}
		returnAST = abbr_axis_specifier_AST;
	}
	
	public final Expr  or_expr() throws RecognitionException, TokenStreamException {
		Expr currentOrExpr = null;
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST or_expr_AST = null;
		Expr lhs = null;
		Expr rhs = null;
		
		try {      // for error handling
			lhs=and_expr();
			astFactory.addASTChild(currentAST, returnAST);
			{
			_loop66:
			do {
				if ((LA(1)==KW_OR)) {
					AST tmp22_AST = null;
					tmp22_AST = astFactory.create(LT(1));
					astFactory.makeASTRoot(currentAST, tmp22_AST);
					match(KW_OR);
					rhs=and_expr();
					astFactory.addASTChild(currentAST, returnAST);
					if ( inputState.guessing==0 ) {
						
						if (lhs != null && rhs != null) {
							currentOrExpr = createOrExpr(lhs, rhs);
							lhs = currentOrExpr;
						}else if(lhs != null){
							currentOrExpr = lhs;
						}
						
					}
				}
				else {
					break _loop66;
				}
				
			} while (true);
			}
			if ( inputState.guessing==0 ) {
				currentOrExpr = lhs;
			}
			or_expr_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_12);
			} else {
			  throw ex;
			}
		}
		returnAST = or_expr_AST;
		return currentOrExpr;
	}
	
	public final Expr  primary_expr() throws RecognitionException, TokenStreamException {
		Expr currentPrimaryExpr = null;
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST primary_expr_AST = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case DOLLAR_SIGN:
			{
				currentPrimaryExpr=variable_reference();
				astFactory.addASTChild(currentAST, returnAST);
				primary_expr_AST = (AST)currentAST.root;
				break;
			}
			case LEFT_PAREN:
			{
				match(LEFT_PAREN);
				currentPrimaryExpr=expr();
				astFactory.addASTChild(currentAST, returnAST);
				match(RIGHT_PAREN);
				primary_expr_AST = (AST)currentAST.root;
				break;
			}
			case LITERAL:
			{
				currentPrimaryExpr=literal();
				astFactory.addASTChild(currentAST, returnAST);
				primary_expr_AST = (AST)currentAST.root;
				break;
			}
			case NUMBER:
			{
				currentPrimaryExpr=number();
				astFactory.addASTChild(currentAST, returnAST);
				primary_expr_AST = (AST)currentAST.root;
				break;
			}
			case IDENTIFIER:
			{
				currentPrimaryExpr=function_call();
				astFactory.addASTChild(currentAST, returnAST);
				primary_expr_AST = (AST)currentAST.root;
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_8);
			} else {
			  throw ex;
			}
		}
		returnAST = primary_expr_AST;
		return currentPrimaryExpr;
	}
	
	public final VariableReferenceExpr  variable_reference() throws RecognitionException, TokenStreamException {
		VariableReferenceExpr currentVariableReferenceExpr = null;
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST variable_reference_AST = null;
		
		try {      // for error handling
			AST tmp25_AST = null;
			tmp25_AST = astFactory.create(LT(1));
			astFactory.makeASTRoot(currentAST, tmp25_AST);
			match(DOLLAR_SIGN);
			AST tmp26_AST = null;
			tmp26_AST = astFactory.create(LT(1));
			astFactory.makeASTRoot(currentAST, tmp26_AST);
			match(IDENTIFIER);
			if ( inputState.guessing==0 ) {
				variable_reference_AST = (AST)currentAST.root;
				
				currentVariableReferenceExpr = createXPathVariableRefExpr(variable_reference_AST);
						
			}
			variable_reference_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_8);
			} else {
			  throw ex;
			}
		}
		returnAST = variable_reference_AST;
		return currentVariableReferenceExpr;
	}
	
	public final LiteralExpr  literal() throws RecognitionException, TokenStreamException {
		LiteralExpr currentLiteralExpr = null;
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST literal_AST = null;
		Token  lit = null;
		AST lit_AST = null;
		
		try {      // for error handling
			lit = LT(1);
			lit_AST = astFactory.create(lit);
			astFactory.makeASTRoot(currentAST, lit_AST);
			match(LITERAL);
			if ( inputState.guessing==0 ) {
				literal_AST = (AST)currentAST.root;
				currentLiteralExpr = createXPathLiteralExpr(literal_AST);
			}
			literal_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_8);
			} else {
			  throw ex;
			}
		}
		returnAST = literal_AST;
		return currentLiteralExpr;
	}
	
	public final NumberExpr  number() throws RecognitionException, TokenStreamException {
		NumberExpr numberExpr = null;
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST number_AST = null;
		
		try {      // for error handling
			AST tmp27_AST = null;
			tmp27_AST = astFactory.create(LT(1));
			astFactory.makeASTRoot(currentAST, tmp27_AST);
			match(NUMBER);
			if ( inputState.guessing==0 ) {
				number_AST = (AST)currentAST.root;
				numberExpr = createXPathNumberExpr(number_AST);
			}
			number_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_8);
			} else {
			  throw ex;
			}
		}
		returnAST = number_AST;
		return numberExpr;
	}
	
	public final FunctionCallExpr  function_call() throws RecognitionException, TokenStreamException {
		FunctionCallExpr currentFunctionCall = null;
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST function_call_AST = null;
		
		try {      // for error handling
			AST tmp28_AST = null;
			tmp28_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp28_AST);
			match(IDENTIFIER);
			AST tmp29_AST = null;
			tmp29_AST = astFactory.create(LT(1));
			astFactory.makeASTRoot(currentAST, tmp29_AST);
			match(LEFT_PAREN);
			if ( inputState.guessing==0 ) {
				function_call_AST = (AST)currentAST.root;
				currentFunctionCall = createXPathFunctionCallExpr(function_call_AST);
			}
			{
			switch ( LA(1)) {
			case SLASH:
			case DOUBLE_SLASH:
			case AT:
			case STAR:
			case IDENTIFIER:
			case LEFT_PAREN:
			case DOT:
			case DOT_DOT:
			case LITERAL:
			case NUMBER:
			case DOLLAR_SIGN:
			case MINUS:
			{
				arg_list(currentFunctionCall);
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case RIGHT_PAREN:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			match(RIGHT_PAREN);
			function_call_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_8);
			} else {
			  throw ex;
			}
		}
		returnAST = function_call_AST;
		return currentFunctionCall;
	}
	
	public final void arg_list(
		FunctionCallExpr functionCall
	) throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST arg_list_AST = null;
		Expr currentArgumentExpr = null;
		
		try {      // for error handling
			currentArgumentExpr=argument();
			astFactory.addASTChild(currentAST, returnAST);
			if ( inputState.guessing==0 ) {
				if (functionCall != null && currentArgumentExpr != null) {
					functionCall.addParameter(currentArgumentExpr);
				}
				
			}
			{
			_loop46:
			do {
				if ((LA(1)==COMMA)) {
					AST tmp31_AST = null;
					tmp31_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp31_AST);
					match(COMMA);
					currentArgumentExpr=argument();
					astFactory.addASTChild(currentAST, returnAST);
					if ( inputState.guessing==0 ) {
						
						if (functionCall != null && currentArgumentExpr != null) {
							functionCall.addParameter(currentArgumentExpr);
						}
						
					}
				}
				else {
					break _loop46;
				}
				
			} while (true);
			}
			arg_list_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_13);
			} else {
			  throw ex;
			}
		}
		returnAST = arg_list_AST;
	}
	
	public final Expr  argument() throws RecognitionException, TokenStreamException {
		Expr currentArgumentExpr = null;
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST argument_AST = null;
		
		try {      // for error handling
			currentArgumentExpr=expr();
			astFactory.addASTChild(currentAST, returnAST);
			argument_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_14);
			} else {
			  throw ex;
			}
		}
		returnAST = argument_AST;
		return currentArgumentExpr;
	}
	
	public final Expr  path_expr() throws RecognitionException, TokenStreamException {
		Expr currentPathExpr = null;
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST path_expr_AST = null;
		LocationPath currentLocationPath = null;
		FilterExpr currentFilterExpr = null;
		
		
		try {      // for error handling
			{
			boolean synPredMatched54 = false;
			if ((((_tokenSet_15.member(LA(1))) && (_tokenSet_16.member(LA(2))))&&( 
			LT(1).getText().equals("processing-instruction")
				||
			LT(1).getText().equals("comment")
				||
			LT(1).getText().equals("text")
				||
			LT(1).getText().equals("node")
		))) {
				int _m54 = mark();
				synPredMatched54 = true;
				inputState.guessing++;
				try {
					{
					match(IDENTIFIER);
					match(LEFT_PAREN);
					}
				}
				catch (RecognitionException pe) {
					synPredMatched54 = false;
				}
				rewind(_m54);
inputState.guessing--;
			}
			if ( synPredMatched54 ) {
				currentLocationPath=location_path();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else {
				boolean synPredMatched56 = false;
				if (((_tokenSet_17.member(LA(1))) && (_tokenSet_5.member(LA(2))))) {
					int _m56 = mark();
					synPredMatched56 = true;
					inputState.guessing++;
					try {
						{
						match(IDENTIFIER);
						match(LEFT_PAREN);
						}
					}
					catch (RecognitionException pe) {
						synPredMatched56 = false;
					}
					rewind(_m56);
inputState.guessing--;
				}
				if ( synPredMatched56 ) {
					currentFilterExpr=filter_expr();
					astFactory.addASTChild(currentAST, returnAST);
					{
					switch ( LA(1)) {
					case SLASH:
					case DOUBLE_SLASH:
					{
						currentLocationPath=absolute_location_path();
						astFactory.addASTChild(currentAST, returnAST);
						break;
					}
					case EOF:
					case STAR:
					case RIGHT_PAREN:
					case RIGHT_BRACKET:
					case COMMA:
					case PIPE:
					case KW_OR:
					case KW_AND:
					case EQUALS:
					case NOT_EQUALS:
					case LT:
					case GT:
					case LTE:
					case GTE:
					case PLUS:
					case MINUS:
					case DIV:
					case MOD:
					{
						break;
					}
					default:
					{
						throw new NoViableAltException(LT(1), getFilename());
					}
					}
					}
				}
				else {
					boolean synPredMatched59 = false;
					if (((_tokenSet_15.member(LA(1))) && (_tokenSet_16.member(LA(2))))) {
						int _m59 = mark();
						synPredMatched59 = true;
						inputState.guessing++;
						try {
							{
							switch ( LA(1)) {
							case DOT:
							{
								match(DOT);
								break;
							}
							case DOT_DOT:
							{
								match(DOT_DOT);
								break;
							}
							case SLASH:
							{
								match(SLASH);
								break;
							}
							case DOUBLE_SLASH:
							{
								match(DOUBLE_SLASH);
								break;
							}
							case IDENTIFIER:
							{
								match(IDENTIFIER);
								break;
							}
							case AT:
							{
								match(AT);
								break;
							}
							default:
							{
								throw new NoViableAltException(LT(1), getFilename());
							}
							}
							}
						}
						catch (RecognitionException pe) {
							synPredMatched59 = false;
						}
						rewind(_m59);
inputState.guessing--;
					}
					if ( synPredMatched59 ) {
						currentLocationPath=location_path();
						astFactory.addASTChild(currentAST, returnAST);
					}
					else if ((_tokenSet_17.member(LA(1))) && (_tokenSet_5.member(LA(2)))) {
						currentFilterExpr=filter_expr();
						astFactory.addASTChild(currentAST, returnAST);
						{
						switch ( LA(1)) {
						case SLASH:
						case DOUBLE_SLASH:
						{
							currentLocationPath=absolute_location_path();
							astFactory.addASTChild(currentAST, returnAST);
							break;
						}
						case EOF:
						case STAR:
						case RIGHT_PAREN:
						case RIGHT_BRACKET:
						case COMMA:
						case PIPE:
						case KW_OR:
						case KW_AND:
						case EQUALS:
						case NOT_EQUALS:
						case LT:
						case GT:
						case LTE:
						case GTE:
						case PLUS:
						case MINUS:
						case DIV:
						case MOD:
						{
							break;
						}
						default:
						{
							throw new NoViableAltException(LT(1), getFilename());
						}
						}
						}
					}
					else {
						throw new NoViableAltException(LT(1), getFilename());
					}
					}}
					}
					if ( inputState.guessing==0 ) {
						if (currentLocationPath != null && currentFilterExpr != null) {
						currentPathExpr = createPathExpr(currentFilterExpr, currentLocationPath);
						}else if(currentFilterExpr != null){
							currentPathExpr = currentFilterExpr;
						}else if(currentLocationPath != null){
							currentPathExpr = currentLocationPath;
						}
						
					}
					path_expr_AST = (AST)currentAST.root;
				}
				catch (RecognitionException ex) {
					if (inputState.guessing==0) {
						reportError(ex);
						recover(ex,_tokenSet_2);
					} else {
					  throw ex;
					}
				}
				returnAST = path_expr_AST;
				return currentPathExpr;
			}
			
	public final FilterExpr  filter_expr() throws RecognitionException, TokenStreamException {
		FilterExpr currentFilterExpr = null;
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST filter_expr_AST = null;
		Expr currentPrimaryExpr = null;
		
		try {      // for error handling
			currentPrimaryExpr=primary_expr();
			astFactory.addASTChild(currentAST, returnAST);
			if ( inputState.guessing==0 ) {
				if(currentPrimaryExpr != null){
				currentFilterExpr = createXPathFilterExpr(currentPrimaryExpr);
				}
				
			}
			{
			_loop63:
			do {
				if ((LA(1)==LEFT_BRACKET)) {
					predicate(currentFilterExpr);
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					break _loop63;
				}
				
			} while (true);
			}
			filter_expr_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_9);
			} else {
			  throw ex;
			}
		}
		returnAST = filter_expr_AST;
		return currentFilterExpr;
	}
	
	public final Expr  and_expr() throws RecognitionException, TokenStreamException {
		Expr currentAndExpr = null;
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST and_expr_AST = null;
		Expr lhs = null;
		Expr rhs = null;
		
		try {      // for error handling
			lhs=equality_expr();
			astFactory.addASTChild(currentAST, returnAST);
			{
			switch ( LA(1)) {
			case KW_AND:
			{
				AST tmp32_AST = null;
				tmp32_AST = astFactory.create(LT(1));
				astFactory.makeASTRoot(currentAST, tmp32_AST);
				match(KW_AND);
				rhs=equality_expr();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case RIGHT_PAREN:
			case RIGHT_BRACKET:
			case COMMA:
			case KW_OR:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			if ( inputState.guessing==0 ) {
				
				if (lhs != null && rhs != null) {
					currentAndExpr = createAndExpr(lhs, rhs);
				}else if(lhs != null){
					currentAndExpr = lhs;
				}
				
			}
			and_expr_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_18);
			} else {
			  throw ex;
			}
		}
		returnAST = and_expr_AST;
		return currentAndExpr;
	}
	
	public final Expr  equality_expr() throws RecognitionException, TokenStreamException {
		Expr currentEqualityExpr = null;
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST equality_expr_AST = null;
		Expr lhs = null;
		Expr rhs = null;
		int equalityOperator = Operator.NO_OP;
		
		try {      // for error handling
			lhs=relational_expr();
			astFactory.addASTChild(currentAST, returnAST);
			{
			switch ( LA(1)) {
			case EQUALS:
			case NOT_EQUALS:
			{
				{
				switch ( LA(1)) {
				case EQUALS:
				{
					AST tmp33_AST = null;
					tmp33_AST = astFactory.create(LT(1));
					astFactory.makeASTRoot(currentAST, tmp33_AST);
					match(EQUALS);
					if ( inputState.guessing==0 ) {
						equalityOperator = Operator.EQUALS;
					}
					break;
				}
				case NOT_EQUALS:
				{
					AST tmp34_AST = null;
					tmp34_AST = astFactory.create(LT(1));
					astFactory.makeASTRoot(currentAST, tmp34_AST);
					match(NOT_EQUALS);
					if ( inputState.guessing==0 ) {
						equalityOperator = Operator.NOT_EQUALS;
					}
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
				rhs=relational_expr();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case RIGHT_PAREN:
			case RIGHT_BRACKET:
			case COMMA:
			case KW_OR:
			case KW_AND:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			if ( inputState.guessing==0 ) {
				
				if (lhs != null && rhs != null) {
					currentEqualityExpr = createEqualityExpr(lhs, rhs, equalityOperator);
				}else if(lhs != null){
					currentEqualityExpr = lhs;
				}
				
			}
			equality_expr_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_19);
			} else {
			  throw ex;
			}
		}
		returnAST = equality_expr_AST;
		return currentEqualityExpr;
	}
	
	public final Expr  relational_expr() throws RecognitionException, TokenStreamException {
		Expr currentRelationalExpr = null;
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST relational_expr_AST = null;
		Expr lhs = null;
		Expr rhs = null;
		int relationalOperator = Operator.NO_OP;
		
		try {      // for error handling
			lhs=additive_expr();
			astFactory.addASTChild(currentAST, returnAST);
			{
			switch ( LA(1)) {
			case LT:
			case GT:
			case LTE:
			case GTE:
			{
				{
				switch ( LA(1)) {
				case LT:
				{
					AST tmp35_AST = null;
					tmp35_AST = astFactory.create(LT(1));
					astFactory.makeASTRoot(currentAST, tmp35_AST);
					match(LT);
					if ( inputState.guessing==0 ) {
						relationalOperator = Operator.LESS_THAN;
					}
					break;
				}
				case GT:
				{
					AST tmp36_AST = null;
					tmp36_AST = astFactory.create(LT(1));
					astFactory.makeASTRoot(currentAST, tmp36_AST);
					match(GT);
					if ( inputState.guessing==0 ) {
						relationalOperator = Operator.GREATER_THAN;
					}
					break;
				}
				case LTE:
				{
					AST tmp37_AST = null;
					tmp37_AST = astFactory.create(LT(1));
					astFactory.makeASTRoot(currentAST, tmp37_AST);
					match(LTE);
					if ( inputState.guessing==0 ) {
						relationalOperator = Operator.LESS_THAN_EQUALS;
					}
					break;
				}
				case GTE:
				{
					AST tmp38_AST = null;
					tmp38_AST = astFactory.create(LT(1));
					astFactory.makeASTRoot(currentAST, tmp38_AST);
					match(GTE);
					if ( inputState.guessing==0 ) {
						relationalOperator = Operator.GREATER_THAN_EQUALS;
					}
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
				rhs=additive_expr();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case RIGHT_PAREN:
			case RIGHT_BRACKET:
			case COMMA:
			case KW_OR:
			case KW_AND:
			case EQUALS:
			case NOT_EQUALS:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			if ( inputState.guessing==0 ) {
				if(lhs != null && rhs != null){
					currentRelationalExpr = createRelationalExpr(lhs, rhs, relationalOperator);
				}else if(lhs != null){
					currentRelationalExpr = lhs;
				}
				
			}
			relational_expr_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_20);
			} else {
			  throw ex;
			}
		}
		returnAST = relational_expr_AST;
		return currentRelationalExpr;
	}
	
	public final Expr  additive_expr() throws RecognitionException, TokenStreamException {
		Expr currentAdditiveExpr = null;
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST additive_expr_AST = null;
		int additiveOperator = Operator.ADD;
		Expr lhs = null;
		Expr rhs = null;
		
		
		try {      // for error handling
			lhs=mult_expr();
			astFactory.addASTChild(currentAST, returnAST);
			{
			switch ( LA(1)) {
			case PLUS:
			case MINUS:
			{
				{
				switch ( LA(1)) {
				case PLUS:
				{
					AST tmp39_AST = null;
					tmp39_AST = astFactory.create(LT(1));
					astFactory.makeASTRoot(currentAST, tmp39_AST);
					match(PLUS);
					if ( inputState.guessing==0 ) {
						additiveOperator = Operator.ADD;
					}
					break;
				}
				case MINUS:
				{
					AST tmp40_AST = null;
					tmp40_AST = astFactory.create(LT(1));
					astFactory.makeASTRoot(currentAST, tmp40_AST);
					match(MINUS);
					if ( inputState.guessing==0 ) {
						additiveOperator = Operator.SUBTRACT;
					}
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
				rhs=mult_expr();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case RIGHT_PAREN:
			case RIGHT_BRACKET:
			case COMMA:
			case KW_OR:
			case KW_AND:
			case EQUALS:
			case NOT_EQUALS:
			case LT:
			case GT:
			case LTE:
			case GTE:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			if ( inputState.guessing==0 ) {
				if(lhs != null && rhs != null){
					currentAdditiveExpr = createAdditiveExpr(lhs, rhs, additiveOperator);
				}else if(lhs != null){
					currentAdditiveExpr = lhs;
				}
				
			}
			additive_expr_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_21);
			} else {
			  throw ex;
			}
		}
		returnAST = additive_expr_AST;
		return currentAdditiveExpr;
	}
	
	public final Expr  mult_expr() throws RecognitionException, TokenStreamException {
		Expr currentMultiplicativeExpr = null;
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST mult_expr_AST = null;
		int multiplicativeOperator = Operator.MULTIPLY;
		Expr lhs = null;
		Expr rhs = null;
		
		
		try {      // for error handling
			lhs=unary_expr();
			astFactory.addASTChild(currentAST, returnAST);
			{
			switch ( LA(1)) {
			case STAR:
			case DIV:
			case MOD:
			{
				{
				switch ( LA(1)) {
				case STAR:
				{
					AST tmp41_AST = null;
					tmp41_AST = astFactory.create(LT(1));
					astFactory.makeASTRoot(currentAST, tmp41_AST);
					match(STAR);
					if ( inputState.guessing==0 ) {
						multiplicativeOperator = Operator.MULTIPLY;
					}
					break;
				}
				case DIV:
				{
					AST tmp42_AST = null;
					tmp42_AST = astFactory.create(LT(1));
					astFactory.makeASTRoot(currentAST, tmp42_AST);
					match(DIV);
					if ( inputState.guessing==0 ) {
						multiplicativeOperator = Operator.DIV;
					}
					break;
				}
				case MOD:
				{
					AST tmp43_AST = null;
					tmp43_AST = astFactory.create(LT(1));
					astFactory.makeASTRoot(currentAST, tmp43_AST);
					match(MOD);
					if ( inputState.guessing==0 ) {
						multiplicativeOperator = Operator.MOD;
					}
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
				rhs=unary_expr();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case RIGHT_PAREN:
			case RIGHT_BRACKET:
			case COMMA:
			case KW_OR:
			case KW_AND:
			case EQUALS:
			case NOT_EQUALS:
			case LT:
			case GT:
			case LTE:
			case GTE:
			case PLUS:
			case MINUS:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			if ( inputState.guessing==0 ) {
				if(lhs != null && rhs != null){
					currentMultiplicativeExpr = createMultiplicativeExpr(lhs, rhs, multiplicativeOperator);
				}else if(lhs != null){
					currentMultiplicativeExpr = lhs;
				}
				
			}
			mult_expr_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_22);
			} else {
			  throw ex;
			}
		}
		returnAST = mult_expr_AST;
		return currentMultiplicativeExpr;
	}
	
	public final Expr  unary_expr() throws RecognitionException, TokenStreamException {
		Expr currentUnaryExpr = null;
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST unary_expr_AST = null;
		int unaryOperator = Operator.NO_OP;
		Expr currentExpr = null;
		
		try {      // for error handling
			{
			switch ( LA(1)) {
			case SLASH:
			case DOUBLE_SLASH:
			case AT:
			case STAR:
			case IDENTIFIER:
			case LEFT_PAREN:
			case DOT:
			case DOT_DOT:
			case LITERAL:
			case NUMBER:
			case DOLLAR_SIGN:
			{
				currentExpr=union_expr();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case MINUS:
			{
				AST tmp44_AST = null;
				tmp44_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp44_AST);
				match(MINUS);
				currentExpr=unary_expr();
				astFactory.addASTChild(currentAST, returnAST);
				if ( inputState.guessing==0 ) {
					unaryOperator = Operator.NEGATIVE;
				}
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			if ( inputState.guessing==0 ) {
				if(currentExpr != null){
				currentUnaryExpr = createUnaryExpr(currentExpr, unaryOperator);
				}
			}
			unary_expr_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_23);
			} else {
			  throw ex;
			}
		}
		returnAST = unary_expr_AST;
		return currentUnaryExpr;
	}
	
	
	public static final String[] _tokenNames = {
		"<0>",
		"EOF",
		"<2>",
		"NULL_TREE_LOOKAHEAD",
		"SLASH",
		"DOUBLE_SLASH",
		"AT",
		"STAR",
		"IDENTIFIER",
		"DOUBLE_COLON",
		"COLON",
		"LEFT_PAREN",
		"RIGHT_PAREN",
		"LEFT_BRACKET",
		"RIGHT_BRACKET",
		"DOT",
		"DOT_DOT",
		"LITERAL",
		"NUMBER",
		"DOLLAR_SIGN",
		"COMMA",
		"PIPE",
		"KW_OR",
		"KW_AND",
		"EQUALS",
		"NOT_EQUALS",
		"LT",
		"GT",
		"LTE",
		"GTE",
		"PLUS",
		"MINUS",
		"DIV",
		"MOD"
	};
	
	protected void buildTokenTypeASTClassMap() {
		tokenTypeToASTClassMap=null;
	};
	
	private static final long[] mk_tokenSet_0() {
		long[] data = { 2L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());
	private static final long[] mk_tokenSet_1() {
		long[] data = { 17176744066L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_1 = new BitSet(mk_tokenSet_1());
	private static final long[] mk_tokenSet_2() {
		long[] data = { 17178841218L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_2 = new BitSet(mk_tokenSet_2());
	private static final long[] mk_tokenSet_3() {
		long[] data = { 98752L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_3 = new BitSet(mk_tokenSet_3());
	private static final long[] mk_tokenSet_4() {
		long[] data = { 17178853298L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_4 = new BitSet(mk_tokenSet_4());
	private static final long[] mk_tokenSet_5() {
		long[] data = { 17179867634L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_5 = new BitSet(mk_tokenSet_5());
	private static final long[] mk_tokenSet_6() {
		long[] data = { 17178852530L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_6 = new BitSet(mk_tokenSet_6());
	private static final long[] mk_tokenSet_7() {
		long[] data = { 17178850482L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_7 = new BitSet(mk_tokenSet_7());
	private static final long[] mk_tokenSet_8() {
		long[] data = { 17178849458L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_8 = new BitSet(mk_tokenSet_8());
	private static final long[] mk_tokenSet_9() {
		long[] data = { 17178841266L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_9 = new BitSet(mk_tokenSet_9());
	private static final long[] mk_tokenSet_10() {
		long[] data = { 384L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_10 = new BitSet(mk_tokenSet_10());
	private static final long[] mk_tokenSet_11() {
		long[] data = { 16384L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_11 = new BitSet(mk_tokenSet_11());
	private static final long[] mk_tokenSet_12() {
		long[] data = { 1069056L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_12 = new BitSet(mk_tokenSet_12());
	private static final long[] mk_tokenSet_13() {
		long[] data = { 4096L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_13 = new BitSet(mk_tokenSet_13());
	private static final long[] mk_tokenSet_14() {
		long[] data = { 1052672L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_14 = new BitSet(mk_tokenSet_14());
	private static final long[] mk_tokenSet_15() {
		long[] data = { 98800L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_15 = new BitSet(mk_tokenSet_15());
	private static final long[] mk_tokenSet_16() {
		long[] data = { 17178951666L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_16 = new BitSet(mk_tokenSet_16());
	private static final long[] mk_tokenSet_17() {
		long[] data = { 919808L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_17 = new BitSet(mk_tokenSet_17());
	private static final long[] mk_tokenSet_18() {
		long[] data = { 5263360L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_18 = new BitSet(mk_tokenSet_18());
	private static final long[] mk_tokenSet_19() {
		long[] data = { 13651968L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_19 = new BitSet(mk_tokenSet_19());
	private static final long[] mk_tokenSet_20() {
		long[] data = { 63983616L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_20 = new BitSet(mk_tokenSet_20());
	private static final long[] mk_tokenSet_21() {
		long[] data = { 1070616576L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_21 = new BitSet(mk_tokenSet_21());
	private static final long[] mk_tokenSet_22() {
		long[] data = { 4291842048L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_22 = new BitSet(mk_tokenSet_22());
	private static final long[] mk_tokenSet_23() {
		long[] data = { 17176744064L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_23 = new BitSet(mk_tokenSet_23());
	
	}
