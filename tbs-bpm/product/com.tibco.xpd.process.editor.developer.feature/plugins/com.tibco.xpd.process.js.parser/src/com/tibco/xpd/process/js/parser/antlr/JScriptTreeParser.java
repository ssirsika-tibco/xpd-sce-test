// $ANTLR 2.7.6 (2005-12-22): "Jscript.tree.g" -> "JScriptTreeParser.java"$

package com.tibco.xpd.process.js.parser.antlr;
import java.util.*;
import com.tibco.xpd.process.js.parser.transform.DtTranslateUtil;
import com.tibco.xpd.script.parser.validator.ISymbolTable;
import com.tibco.xpd.script.parser.validator.SymbolTable;

import antlr.TreeParser;
import antlr.Token;
import antlr.collections.AST;
import antlr.RecognitionException;
import antlr.ANTLRException;
import antlr.NoViableAltException;
import antlr.MismatchedTokenException;
import antlr.SemanticException;
import antlr.collections.impl.BitSet;
import antlr.ASTPair;
import antlr.collections.impl.ASTArray;


public class JScriptTreeParser extends antlr.TreeParser       implements JScriptTreeParserTokenTypes
 {

    private Map<String,String> oldNewDataNameMap;
    public void setOldNewProcessDataMap(Map<String,String> oldNewDataNameMap){
    	this.oldNewDataNameMap = oldNewDataNameMap;
    }
    @SuppressWarnings("unchecked")
	public Map<String,String> getOldNewProcessDataMap(){
    	if(this.oldNewDataNameMap==null){
    		this.oldNewDataNameMap = Collections.EMPTY_MAP;
    	}
    	return this.oldNewDataNameMap;
    }

    private List<String> ignoreIdentifierList;
    public void setIgnoreIdentifierList(List<String> ignoreIdentifierList) {
	this.ignoreIdentifierList = ignoreIdentifierList;
    }
    @SuppressWarnings("unchecked")
    public List<String> getIgnoreIdentifierList() {
    if(this.ignoreIdentifierList==null){
    	this.ignoreIdentifierList = Collections.EMPTY_LIST;
    }
	return this.ignoreIdentifierList;
    }

    private String scriptType;

  public String getScriptType() {
	return scriptType;
  }

 public void setScriptType(String scriptType) {
	this.scriptType = scriptType;
 }

 private ISymbolTable symbolTable = null;
  
  public ISymbolTable getSymbolTable(){
	  if(this.symbolTable==null){
		  this.symbolTable = new SymbolTable();
	  }
	return this.symbolTable;
  }

  public void setSymbolTable(ISymbolTable sTable){
	this.symbolTable=sTable;	
  }


    private List<String> destinationList = null;
    public void setProcessDestinationList(List<String> destinationList){
	this.destinationList = destinationList;
    }
    @SuppressWarnings("unchecked")
    public List<String> getProcessDestinationList(){
    	if(this.destinationList==null){
    		this.destinationList = Collections.EMPTY_LIST;
    	}
    	return this.destinationList;
    }

    private List<String> supportedContextList;
    public void setSupportedContextList(List<String> supportedContextList) {
	this.supportedContextList = supportedContextList;
    }
    @SuppressWarnings("unchecked")
    public List<String> getSupportedContextList() {
    	if(this.supportedContextList==null){
    		this.supportedContextList = Collections.EMPTY_LIST;
    	}
    	return this.supportedContextList;
    }
    private void translateExprAST(AST exprAST){	
	// this one translates as per Dt rules.
	DtTranslateUtil.translateExprAST(exprAST,getOldNewProcessDataMap(),getIgnoreIdentifierList(),getProcessDestinationList(),getSupportedContextList(),getScriptType());
   }
public JScriptTreeParser() {
	tokenNames = _tokenNames;
}

	public final void compilationUnit(AST _t) throws RecognitionException {
		
		AST compilationUnit_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST compilationUnit_AST = null;
		
		try {      // for error handling
			sourceElements(_t);
			_t = _retTree;
			astFactory.addASTChild(currentAST, returnAST);
			compilationUnit_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		returnAST = compilationUnit_AST;
		_retTree = _t;
	}
	
	public final void sourceElements(AST _t) throws RecognitionException {
		
		AST sourceElements_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST sourceElements_AST = null;
		
		try {      // for error handling
			{
			int _cnt4=0;
			_loop4:
			do {
				if (_t==null) _t=ASTNULL;
				if ((_tokenSet_0.member(_t.getType()))) {
					sourceElement(_t);
					_t = _retTree;
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					if ( _cnt4>=1 ) { break _loop4; } else {throw new NoViableAltException(_t);}
				}
				
				_cnt4++;
			} while (true);
			}
			sourceElements_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		returnAST = sourceElements_AST;
		_retTree = _t;
	}
	
	public final void sourceElement(AST _t) throws RecognitionException {
		
		AST sourceElement_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST sourceElement_AST = null;
		
		try {      // for error handling
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case METHOD_DEF:
			{
				functionDeclaration(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				sourceElement_AST = (AST)currentAST.root;
				break;
			}
			case SLIST:
			case VARIABLE_DEF:
			case LABELED_STAT:
			case EXPR:
			case EMPTY_STAT:
			case LITERAL_if:
			case LITERAL_for:
			case LITERAL_while:
			case LITERAL_do:
			case LITERAL_break:
			case LITERAL_continue:
			case LITERAL_return:
			case LITERAL_switch:
			case LITERAL_throw:
			case LITERAL_try:
			{
				stat(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				sourceElement_AST = (AST)currentAST.root;
				break;
			}
			default:
			{
				throw new NoViableAltException(_t);
			}
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		returnAST = sourceElement_AST;
		_retTree = _t;
	}
	
	public final void functionDeclaration(AST _t) throws RecognitionException {
		
		AST functionDeclaration_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST functionDeclaration_AST = null;
		
		try {      // for error handling
			AST __t7 = _t;
			AST tmp1_AST = null;
			AST tmp1_AST_in = null;
			tmp1_AST = astFactory.create((AST)_t);
			tmp1_AST_in = (AST)_t;
			astFactory.addASTChild(currentAST, tmp1_AST);
			ASTPair __currentAST7 = currentAST.copy();
			currentAST.root = currentAST.child;
			currentAST.child = null;
			match(_t,METHOD_DEF);
			_t = _t.getFirstChild();
			methodHead(_t);
			_t = _retTree;
			astFactory.addASTChild(currentAST, returnAST);
			{
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case SLIST:
			{
				slist(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case 3:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(_t);
			}
			}
			}
			currentAST = __currentAST7;
			_t = __t7;
			_t = _t.getNextSibling();
			functionDeclaration_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		returnAST = functionDeclaration_AST;
		_retTree = _t;
	}
	
	public final void stat(AST _t) throws RecognitionException {
		
		AST stat_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST stat_AST = null;
		AST whileStat = null;
		AST whileStat_AST = null;
		AST whileBrace_AST = null;
		AST whileBrace = null;
		
		try {      // for error handling
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case VARIABLE_DEF:
			{
				variableDef(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				stat_AST = (AST)currentAST.root;
				break;
			}
			case EXPR:
			{
				expression(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				stat_AST = (AST)currentAST.root;
				break;
			}
			case LABELED_STAT:
			{
				AST __t45 = _t;
				AST tmp2_AST = null;
				AST tmp2_AST_in = null;
				tmp2_AST = astFactory.create((AST)_t);
				tmp2_AST_in = (AST)_t;
				astFactory.addASTChild(currentAST, tmp2_AST);
				ASTPair __currentAST45 = currentAST.copy();
				currentAST.root = currentAST.child;
				currentAST.child = null;
				match(_t,LABELED_STAT);
				_t = _t.getFirstChild();
				AST tmp3_AST = null;
				AST tmp3_AST_in = null;
				tmp3_AST = astFactory.create((AST)_t);
				tmp3_AST_in = (AST)_t;
				astFactory.addASTChild(currentAST, tmp3_AST);
				match(_t,IDENT);
				_t = _t.getNextSibling();
				stat(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				currentAST = __currentAST45;
				_t = __t45;
				_t = _t.getNextSibling();
				stat_AST = (AST)currentAST.root;
				break;
			}
			case LITERAL_if:
			{
				ifElseStat(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				stat_AST = (AST)currentAST.root;
				break;
			}
			case LITERAL_for:
			{
				AST __t46 = _t;
				AST tmp4_AST = null;
				AST tmp4_AST_in = null;
				tmp4_AST = astFactory.create((AST)_t);
				tmp4_AST_in = (AST)_t;
				astFactory.addASTChild(currentAST, tmp4_AST);
				ASTPair __currentAST46 = currentAST.copy();
				currentAST.root = currentAST.child;
				currentAST.child = null;
				match(_t,LITERAL_for);
				_t = _t.getFirstChild();
				AST __t47 = _t;
				AST tmp5_AST = null;
				AST tmp5_AST_in = null;
				tmp5_AST = astFactory.create((AST)_t);
				tmp5_AST_in = (AST)_t;
				astFactory.addASTChild(currentAST, tmp5_AST);
				ASTPair __currentAST47 = currentAST.copy();
				currentAST.root = currentAST.child;
				currentAST.child = null;
				match(_t,FOR_INIT);
				_t = _t.getFirstChild();
				{
				if (_t==null) _t=ASTNULL;
				switch ( _t.getType()) {
				case VARIABLE_DEF:
				{
					{
					int _cnt50=0;
					_loop50:
					do {
						if (_t==null) _t=ASTNULL;
						if ((_t.getType()==VARIABLE_DEF)) {
							forLoopVariableDef(_t);
							_t = _retTree;
							astFactory.addASTChild(currentAST, returnAST);
						}
						else {
							if ( _cnt50>=1 ) { break _loop50; } else {throw new NoViableAltException(_t);}
						}
						
						_cnt50++;
					} while (true);
					}
					break;
				}
				case ELIST:
				{
					elist(_t);
					_t = _retTree;
					astFactory.addASTChild(currentAST, returnAST);
					break;
				}
				case 3:
				{
					break;
				}
				default:
				{
					throw new NoViableAltException(_t);
				}
				}
				}
				currentAST = __currentAST47;
				_t = __t47;
				_t = _t.getNextSibling();
				AST __t51 = _t;
				AST tmp6_AST = null;
				AST tmp6_AST_in = null;
				tmp6_AST = astFactory.create((AST)_t);
				tmp6_AST_in = (AST)_t;
				astFactory.addASTChild(currentAST, tmp6_AST);
				ASTPair __currentAST51 = currentAST.copy();
				currentAST.root = currentAST.child;
				currentAST.child = null;
				match(_t,FOR_CONDITION);
				_t = _t.getFirstChild();
				{
				if (_t==null) _t=ASTNULL;
				switch ( _t.getType()) {
				case EXPR:
				{
					expression(_t);
					_t = _retTree;
					astFactory.addASTChild(currentAST, returnAST);
					break;
				}
				case 3:
				{
					break;
				}
				default:
				{
					throw new NoViableAltException(_t);
				}
				}
				}
				currentAST = __currentAST51;
				_t = __t51;
				_t = _t.getNextSibling();
				AST __t53 = _t;
				AST tmp7_AST = null;
				AST tmp7_AST_in = null;
				tmp7_AST = astFactory.create((AST)_t);
				tmp7_AST_in = (AST)_t;
				astFactory.addASTChild(currentAST, tmp7_AST);
				ASTPair __currentAST53 = currentAST.copy();
				currentAST.root = currentAST.child;
				currentAST.child = null;
				match(_t,FOR_ITERATOR);
				_t = _t.getFirstChild();
				{
				if (_t==null) _t=ASTNULL;
				switch ( _t.getType()) {
				case ELIST:
				{
					elist(_t);
					_t = _retTree;
					astFactory.addASTChild(currentAST, returnAST);
					break;
				}
				case 3:
				{
					break;
				}
				default:
				{
					throw new NoViableAltException(_t);
				}
				}
				}
				currentAST = __currentAST53;
				_t = __t53;
				_t = _t.getNextSibling();
				stat(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				currentAST = __currentAST46;
				_t = __t46;
				_t = _t.getNextSibling();
				stat_AST = (AST)currentAST.root;
				break;
			}
			case LITERAL_while:
			{
				AST __t55 = _t;
				whileStat = _t==ASTNULL ? null :(AST)_t;
				AST whileStat_AST_in = null;
				whileStat_AST = astFactory.create(whileStat);
				astFactory.addASTChild(currentAST, whileStat_AST);
				ASTPair __currentAST55 = currentAST.copy();
				currentAST.root = currentAST.child;
				currentAST.child = null;
				match(_t,LITERAL_while);
				_t = _t.getFirstChild();
				expression(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				whileBrace = _t==ASTNULL ? null : (AST)_t;
				stat(_t);
				_t = _retTree;
				whileBrace_AST = (AST)returnAST;
				astFactory.addASTChild(currentAST, returnAST);
				currentAST = __currentAST55;
				_t = __t55;
				_t = _t.getNextSibling();
				
							
						
				stat_AST = (AST)currentAST.root;
				break;
			}
			case LITERAL_do:
			{
				AST __t56 = _t;
				AST tmp8_AST = null;
				AST tmp8_AST_in = null;
				tmp8_AST = astFactory.create((AST)_t);
				tmp8_AST_in = (AST)_t;
				astFactory.addASTChild(currentAST, tmp8_AST);
				ASTPair __currentAST56 = currentAST.copy();
				currentAST.root = currentAST.child;
				currentAST.child = null;
				match(_t,LITERAL_do);
				_t = _t.getFirstChild();
				expression(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				stat(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				currentAST = __currentAST56;
				_t = __t56;
				_t = _t.getNextSibling();
				stat_AST = (AST)currentAST.root;
				break;
			}
			case LITERAL_break:
			{
				AST __t57 = _t;
				AST tmp9_AST = null;
				AST tmp9_AST_in = null;
				tmp9_AST = astFactory.create((AST)_t);
				tmp9_AST_in = (AST)_t;
				astFactory.addASTChild(currentAST, tmp9_AST);
				ASTPair __currentAST57 = currentAST.copy();
				currentAST.root = currentAST.child;
				currentAST.child = null;
				match(_t,LITERAL_break);
				_t = _t.getFirstChild();
				{
				if (_t==null) _t=ASTNULL;
				switch ( _t.getType()) {
				case IDENT:
				{
					AST tmp10_AST = null;
					AST tmp10_AST_in = null;
					tmp10_AST = astFactory.create((AST)_t);
					tmp10_AST_in = (AST)_t;
					astFactory.addASTChild(currentAST, tmp10_AST);
					match(_t,IDENT);
					_t = _t.getNextSibling();
					break;
				}
				case 3:
				{
					break;
				}
				default:
				{
					throw new NoViableAltException(_t);
				}
				}
				}
				currentAST = __currentAST57;
				_t = __t57;
				_t = _t.getNextSibling();
				stat_AST = (AST)currentAST.root;
				break;
			}
			case LITERAL_continue:
			{
				AST __t59 = _t;
				AST tmp11_AST = null;
				AST tmp11_AST_in = null;
				tmp11_AST = astFactory.create((AST)_t);
				tmp11_AST_in = (AST)_t;
				astFactory.addASTChild(currentAST, tmp11_AST);
				ASTPair __currentAST59 = currentAST.copy();
				currentAST.root = currentAST.child;
				currentAST.child = null;
				match(_t,LITERAL_continue);
				_t = _t.getFirstChild();
				{
				if (_t==null) _t=ASTNULL;
				switch ( _t.getType()) {
				case IDENT:
				{
					AST tmp12_AST = null;
					AST tmp12_AST_in = null;
					tmp12_AST = astFactory.create((AST)_t);
					tmp12_AST_in = (AST)_t;
					astFactory.addASTChild(currentAST, tmp12_AST);
					match(_t,IDENT);
					_t = _t.getNextSibling();
					break;
				}
				case 3:
				{
					break;
				}
				default:
				{
					throw new NoViableAltException(_t);
				}
				}
				}
				currentAST = __currentAST59;
				_t = __t59;
				_t = _t.getNextSibling();
				stat_AST = (AST)currentAST.root;
				break;
			}
			case LITERAL_return:
			{
				AST __t61 = _t;
				AST tmp13_AST = null;
				AST tmp13_AST_in = null;
				tmp13_AST = astFactory.create((AST)_t);
				tmp13_AST_in = (AST)_t;
				astFactory.addASTChild(currentAST, tmp13_AST);
				ASTPair __currentAST61 = currentAST.copy();
				currentAST.root = currentAST.child;
				currentAST.child = null;
				match(_t,LITERAL_return);
				_t = _t.getFirstChild();
				{
				if (_t==null) _t=ASTNULL;
				switch ( _t.getType()) {
				case EXPR:
				{
					expression(_t);
					_t = _retTree;
					astFactory.addASTChild(currentAST, returnAST);
					break;
				}
				case 3:
				{
					break;
				}
				default:
				{
					throw new NoViableAltException(_t);
				}
				}
				}
				currentAST = __currentAST61;
				_t = __t61;
				_t = _t.getNextSibling();
				stat_AST = (AST)currentAST.root;
				break;
			}
			case LITERAL_switch:
			{
				AST __t63 = _t;
				AST tmp14_AST = null;
				AST tmp14_AST_in = null;
				tmp14_AST = astFactory.create((AST)_t);
				tmp14_AST_in = (AST)_t;
				astFactory.addASTChild(currentAST, tmp14_AST);
				ASTPair __currentAST63 = currentAST.copy();
				currentAST.root = currentAST.child;
				currentAST.child = null;
				match(_t,LITERAL_switch);
				_t = _t.getFirstChild();
				expression(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				{
				_loop65:
				do {
					if (_t==null) _t=ASTNULL;
					if ((_t.getType()==CASE_GROUP)) {
						caseGroup(_t);
						_t = _retTree;
						astFactory.addASTChild(currentAST, returnAST);
					}
					else {
						break _loop65;
					}
					
				} while (true);
				}
				currentAST = __currentAST63;
				_t = __t63;
				_t = _t.getNextSibling();
				stat_AST = (AST)currentAST.root;
				break;
			}
			case LITERAL_throw:
			{
				AST __t66 = _t;
				AST tmp15_AST = null;
				AST tmp15_AST_in = null;
				tmp15_AST = astFactory.create((AST)_t);
				tmp15_AST_in = (AST)_t;
				astFactory.addASTChild(currentAST, tmp15_AST);
				ASTPair __currentAST66 = currentAST.copy();
				currentAST.root = currentAST.child;
				currentAST.child = null;
				match(_t,LITERAL_throw);
				_t = _t.getFirstChild();
				expression(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				currentAST = __currentAST66;
				_t = __t66;
				_t = _t.getNextSibling();
				stat_AST = (AST)currentAST.root;
				break;
			}
			case LITERAL_try:
			{
				tryBlock(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				stat_AST = (AST)currentAST.root;
				break;
			}
			case SLIST:
			{
				slist(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				stat_AST = (AST)currentAST.root;
				break;
			}
			case EMPTY_STAT:
			{
				AST tmp16_AST = null;
				AST tmp16_AST_in = null;
				tmp16_AST = astFactory.create((AST)_t);
				tmp16_AST_in = (AST)_t;
				astFactory.addASTChild(currentAST, tmp16_AST);
				match(_t,EMPTY_STAT);
				_t = _t.getNextSibling();
				stat_AST = (AST)currentAST.root;
				break;
			}
			default:
			{
				throw new NoViableAltException(_t);
			}
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		returnAST = stat_AST;
		_retTree = _t;
	}
	
	public final void methodHead(AST _t) throws RecognitionException {
		
		AST methodHead_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST methodHead_AST = null;
		
		try {      // for error handling
			AST tmp17_AST = null;
			AST tmp17_AST_in = null;
			tmp17_AST = astFactory.create((AST)_t);
			tmp17_AST_in = (AST)_t;
			astFactory.addASTChild(currentAST, tmp17_AST);
			match(_t,IDENT);
			_t = _t.getNextSibling();
			AST __t32 = _t;
			AST tmp18_AST = null;
			AST tmp18_AST_in = null;
			tmp18_AST = astFactory.create((AST)_t);
			tmp18_AST_in = (AST)_t;
			astFactory.addASTChild(currentAST, tmp18_AST);
			ASTPair __currentAST32 = currentAST.copy();
			currentAST.root = currentAST.child;
			currentAST.child = null;
			match(_t,PARAMETERS);
			_t = _t.getFirstChild();
			{
			_loop34:
			do {
				if (_t==null) _t=ASTNULL;
				if ((_t.getType()==PARAMETER_DEF)) {
					functionParameterDef(_t);
					_t = _retTree;
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					break _loop34;
				}
				
			} while (true);
			}
			currentAST = __currentAST32;
			_t = __t32;
			_t = _t.getNextSibling();
			methodHead_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		returnAST = methodHead_AST;
		_retTree = _t;
	}
	
	public final void slist(AST _t) throws RecognitionException {
		
		AST slist_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST slist_AST = null;
		
		try {      // for error handling
			AST __t38 = _t;
			AST tmp19_AST = null;
			AST tmp19_AST_in = null;
			tmp19_AST = astFactory.create((AST)_t);
			tmp19_AST_in = (AST)_t;
			astFactory.addASTChild(currentAST, tmp19_AST);
			ASTPair __currentAST38 = currentAST.copy();
			currentAST.root = currentAST.child;
			currentAST.child = null;
			match(_t,SLIST);
			_t = _t.getFirstChild();
			{
			_loop40:
			do {
				if (_t==null) _t=ASTNULL;
				if ((_tokenSet_1.member(_t.getType()))) {
					stat(_t);
					_t = _retTree;
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					break _loop40;
				}
				
			} while (true);
			}
			currentAST = __currentAST38;
			_t = __t38;
			_t = _t.getNextSibling();
			slist_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		returnAST = slist_AST;
		_retTree = _t;
	}
	
	public final void typeSpec(AST _t) throws RecognitionException {
		
		AST typeSpec_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST typeSpec_AST = null;
		
		try {      // for error handling
			AST __t10 = _t;
			AST tmp20_AST = null;
			AST tmp20_AST_in = null;
			tmp20_AST = astFactory.create((AST)_t);
			tmp20_AST_in = (AST)_t;
			astFactory.addASTChild(currentAST, tmp20_AST);
			ASTPair __currentAST10 = currentAST.copy();
			currentAST.root = currentAST.child;
			currentAST.child = null;
			match(_t,TYPE);
			_t = _t.getFirstChild();
			typeSpecArray(_t);
			_t = _retTree;
			astFactory.addASTChild(currentAST, returnAST);
			currentAST = __currentAST10;
			_t = __t10;
			_t = _t.getNextSibling();
			typeSpec_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		returnAST = typeSpec_AST;
		_retTree = _t;
	}
	
	public final void typeSpecArray(AST _t) throws RecognitionException {
		
		AST typeSpecArray_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST typeSpecArray_AST = null;
		
		try {      // for error handling
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case ARRAY_DECLARATOR:
			{
				AST __t12 = _t;
				AST tmp21_AST = null;
				AST tmp21_AST_in = null;
				tmp21_AST = astFactory.create((AST)_t);
				tmp21_AST_in = (AST)_t;
				astFactory.addASTChild(currentAST, tmp21_AST);
				ASTPair __currentAST12 = currentAST.copy();
				currentAST.root = currentAST.child;
				currentAST.child = null;
				match(_t,ARRAY_DECLARATOR);
				_t = _t.getFirstChild();
				typeSpecArray(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				currentAST = __currentAST12;
				_t = __t12;
				_t = _t.getNextSibling();
				typeSpecArray_AST = (AST)currentAST.root;
				break;
			}
			case IDENT:
			case LITERAL_var:
			case DOT:
			{
				type(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				typeSpecArray_AST = (AST)currentAST.root;
				break;
			}
			default:
			{
				throw new NoViableAltException(_t);
			}
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		returnAST = typeSpecArray_AST;
		_retTree = _t;
	}
	
	public final void type(AST _t) throws RecognitionException {
		
		AST type_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST type_AST = null;
		
		try {      // for error handling
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case IDENT:
			case DOT:
			{
				identifier(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				type_AST = (AST)currentAST.root;
				break;
			}
			case LITERAL_var:
			{
				builtInType(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				type_AST = (AST)currentAST.root;
				break;
			}
			default:
			{
				throw new NoViableAltException(_t);
			}
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		returnAST = type_AST;
		_retTree = _t;
	}
	
	public final void identifier(AST _t) throws RecognitionException {
		
		AST identifier_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST identifier_AST = null;
		
		try {      // for error handling
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case IDENT:
			{
				AST tmp22_AST = null;
				AST tmp22_AST_in = null;
				tmp22_AST = astFactory.create((AST)_t);
				tmp22_AST_in = (AST)_t;
				astFactory.addASTChild(currentAST, tmp22_AST);
				match(_t,IDENT);
				_t = _t.getNextSibling();
				identifier_AST = (AST)currentAST.root;
				break;
			}
			case DOT:
			{
				AST __t36 = _t;
				AST tmp23_AST = null;
				AST tmp23_AST_in = null;
				tmp23_AST = astFactory.create((AST)_t);
				tmp23_AST_in = (AST)_t;
				astFactory.addASTChild(currentAST, tmp23_AST);
				ASTPair __currentAST36 = currentAST.copy();
				currentAST.root = currentAST.child;
				currentAST.child = null;
				match(_t,DOT);
				_t = _t.getFirstChild();
				identifier(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				AST tmp24_AST = null;
				AST tmp24_AST_in = null;
				tmp24_AST = astFactory.create((AST)_t);
				tmp24_AST_in = (AST)_t;
				astFactory.addASTChild(currentAST, tmp24_AST);
				match(_t,IDENT);
				_t = _t.getNextSibling();
				currentAST = __currentAST36;
				_t = __t36;
				_t = _t.getNextSibling();
				identifier_AST = (AST)currentAST.root;
				break;
			}
			default:
			{
				throw new NoViableAltException(_t);
			}
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		returnAST = identifier_AST;
		_retTree = _t;
	}
	
	public final void builtInType(AST _t) throws RecognitionException {
		
		AST builtInType_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST builtInType_AST = null;
		
		try {      // for error handling
			AST tmp25_AST = null;
			AST tmp25_AST_in = null;
			tmp25_AST = astFactory.create((AST)_t);
			tmp25_AST_in = (AST)_t;
			astFactory.addASTChild(currentAST, tmp25_AST);
			match(_t,LITERAL_var);
			_t = _t.getNextSibling();
			builtInType_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		returnAST = builtInType_AST;
		_retTree = _t;
	}
	
	public final void variableDef(AST _t) throws RecognitionException {
		
		AST variableDef_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST variableDef_AST = null;
		AST ts_AST = null;
		AST ts = null;
		AST vd_AST = null;
		AST vd = null;
		AST vi_AST = null;
		AST vi = null;
		
		try {      // for error handling
			AST __t16 = _t;
			AST tmp26_AST = null;
			AST tmp26_AST_in = null;
			tmp26_AST = astFactory.create((AST)_t);
			tmp26_AST_in = (AST)_t;
			astFactory.addASTChild(currentAST, tmp26_AST);
			ASTPair __currentAST16 = currentAST.copy();
			currentAST.root = currentAST.child;
			currentAST.child = null;
			match(_t,VARIABLE_DEF);
			_t = _t.getFirstChild();
			ts = _t==ASTNULL ? null : (AST)_t;
			typeSpec(_t);
			_t = _retTree;
			ts_AST = (AST)returnAST;
			astFactory.addASTChild(currentAST, returnAST);
			vd = _t==ASTNULL ? null : (AST)_t;
			variableDeclarator(_t);
			_t = _retTree;
			vd_AST = (AST)returnAST;
			astFactory.addASTChild(currentAST, returnAST);
			vi = _t==ASTNULL ? null : (AST)_t;
			varInitializer(_t);
			_t = _retTree;
			vi_AST = (AST)returnAST;
			astFactory.addASTChild(currentAST, returnAST);
			currentAST = __currentAST16;
			_t = __t16;
			_t = _t.getNextSibling();
			variableDef_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		returnAST = variableDef_AST;
		_retTree = _t;
	}
	
	public final void variableDeclarator(AST _t) throws RecognitionException {
		
		AST variableDeclarator_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST variableDeclarator_AST = null;
		AST id = null;
		AST id_AST = null;
		
		try {      // for error handling
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case IDENT:
			{
				id = (AST)_t;
				AST id_AST_in = null;
				id_AST = astFactory.create(id);
				astFactory.addASTChild(currentAST, id_AST);
				match(_t,IDENT);
				_t = _t.getNextSibling();
				variableDeclarator_AST = (AST)currentAST.root;
				break;
			}
			case LBRACK:
			{
				AST tmp27_AST = null;
				AST tmp27_AST_in = null;
				tmp27_AST = astFactory.create((AST)_t);
				tmp27_AST_in = (AST)_t;
				astFactory.addASTChild(currentAST, tmp27_AST);
				match(_t,LBRACK);
				_t = _t.getNextSibling();
				variableDeclarator(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				variableDeclarator_AST = (AST)currentAST.root;
				break;
			}
			default:
			{
				throw new NoViableAltException(_t);
			}
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		returnAST = variableDeclarator_AST;
		_retTree = _t;
	}
	
	public final void varInitializer(AST _t) throws RecognitionException {
		
		AST varInitializer_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST varInitializer_AST = null;
		
		try {      // for error handling
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case ASSIGN:
			{
				AST __t25 = _t;
				AST tmp28_AST = null;
				AST tmp28_AST_in = null;
				tmp28_AST = astFactory.create((AST)_t);
				tmp28_AST_in = (AST)_t;
				astFactory.addASTChild(currentAST, tmp28_AST);
				ASTPair __currentAST25 = currentAST.copy();
				currentAST.root = currentAST.child;
				currentAST.child = null;
				match(_t,ASSIGN);
				_t = _t.getFirstChild();
				initializer(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				currentAST = __currentAST25;
				_t = __t25;
				_t = _t.getNextSibling();
				varInitializer_AST = (AST)currentAST.root;
				break;
			}
			case 3:
			{
				varInitializer_AST = (AST)currentAST.root;
				break;
			}
			default:
			{
				throw new NoViableAltException(_t);
			}
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		returnAST = varInitializer_AST;
		_retTree = _t;
	}
	
	public final void forLoopVariableDef(AST _t) throws RecognitionException {
		
		AST forLoopVariableDef_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST forLoopVariableDef_AST = null;
		
		try {      // for error handling
			AST __t18 = _t;
			AST tmp29_AST = null;
			AST tmp29_AST_in = null;
			tmp29_AST = astFactory.create((AST)_t);
			tmp29_AST_in = (AST)_t;
			astFactory.addASTChild(currentAST, tmp29_AST);
			ASTPair __currentAST18 = currentAST.copy();
			currentAST.root = currentAST.child;
			currentAST.child = null;
			match(_t,VARIABLE_DEF);
			_t = _t.getFirstChild();
			typeSpec(_t);
			_t = _retTree;
			astFactory.addASTChild(currentAST, returnAST);
			variableDeclarator(_t);
			_t = _retTree;
			astFactory.addASTChild(currentAST, returnAST);
			varInitializer(_t);
			_t = _retTree;
			astFactory.addASTChild(currentAST, returnAST);
			currentAST = __currentAST18;
			_t = __t18;
			_t = _t.getNextSibling();
			forLoopVariableDef_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		returnAST = forLoopVariableDef_AST;
		_retTree = _t;
	}
	
	public final void parameterDef(AST _t) throws RecognitionException {
		
		AST parameterDef_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST parameterDef_AST = null;
		
		try {      // for error handling
			AST __t20 = _t;
			AST tmp30_AST = null;
			AST tmp30_AST_in = null;
			tmp30_AST = astFactory.create((AST)_t);
			tmp30_AST_in = (AST)_t;
			astFactory.addASTChild(currentAST, tmp30_AST);
			ASTPair __currentAST20 = currentAST.copy();
			currentAST.root = currentAST.child;
			currentAST.child = null;
			match(_t,PARAMETER_DEF);
			_t = _t.getFirstChild();
			AST tmp31_AST = null;
			AST tmp31_AST_in = null;
			tmp31_AST = astFactory.create((AST)_t);
			tmp31_AST_in = (AST)_t;
			astFactory.addASTChild(currentAST, tmp31_AST);
			match(_t,IDENT);
			_t = _t.getNextSibling();
			currentAST = __currentAST20;
			_t = __t20;
			_t = _t.getNextSibling();
			parameterDef_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		returnAST = parameterDef_AST;
		_retTree = _t;
	}
	
	public final void functionParameterDef(AST _t) throws RecognitionException {
		
		AST functionParameterDef_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST functionParameterDef_AST = null;
		
		try {      // for error handling
			AST __t22 = _t;
			AST tmp32_AST = null;
			AST tmp32_AST_in = null;
			tmp32_AST = astFactory.create((AST)_t);
			tmp32_AST_in = (AST)_t;
			astFactory.addASTChild(currentAST, tmp32_AST);
			ASTPair __currentAST22 = currentAST.copy();
			currentAST.root = currentAST.child;
			currentAST.child = null;
			match(_t,PARAMETER_DEF);
			_t = _t.getFirstChild();
			AST tmp33_AST = null;
			AST tmp33_AST_in = null;
			tmp33_AST = astFactory.create((AST)_t);
			tmp33_AST_in = (AST)_t;
			astFactory.addASTChild(currentAST, tmp33_AST);
			match(_t,IDENT);
			_t = _t.getNextSibling();
			currentAST = __currentAST22;
			_t = __t22;
			_t = _t.getNextSibling();
			functionParameterDef_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		returnAST = functionParameterDef_AST;
		_retTree = _t;
	}
	
	public final void initializer(AST _t) throws RecognitionException {
		
		AST initializer_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST initializer_AST = null;
		
		try {      // for error handling
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case EXPR:
			{
				expression(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				initializer_AST = (AST)currentAST.root;
				break;
			}
			case ARRAY_INIT:
			{
				arrayInitializer(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				initializer_AST = (AST)currentAST.root;
				break;
			}
			default:
			{
				throw new NoViableAltException(_t);
			}
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		returnAST = initializer_AST;
		_retTree = _t;
	}
	
	public final void expression(AST _t) throws RecognitionException {
		
		AST expression_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST expression_AST = null;
		AST exPR_AST = null;
		AST exPR = null;
		
		try {      // for error handling
			AST __t85 = _t;
			AST tmp34_AST = null;
			AST tmp34_AST_in = null;
			tmp34_AST = astFactory.create((AST)_t);
			tmp34_AST_in = (AST)_t;
			astFactory.addASTChild(currentAST, tmp34_AST);
			ASTPair __currentAST85 = currentAST.copy();
			currentAST.root = currentAST.child;
			currentAST.child = null;
			match(_t,EXPR);
			_t = _t.getFirstChild();
			exPR = _t==ASTNULL ? null : (AST)_t;
			expr(_t);
			_t = _retTree;
			exPR_AST = (AST)returnAST;
			astFactory.addASTChild(currentAST, returnAST);
			translateExprAST(exPR_AST);
			currentAST = __currentAST85;
			_t = __t85;
			_t = _t.getNextSibling();
			expression_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		returnAST = expression_AST;
		_retTree = _t;
	}
	
	public final void arrayInitializer(AST _t) throws RecognitionException {
		
		AST arrayInitializer_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST arrayInitializer_AST = null;
		
		try {      // for error handling
			AST __t28 = _t;
			AST tmp35_AST = null;
			AST tmp35_AST_in = null;
			tmp35_AST = astFactory.create((AST)_t);
			tmp35_AST_in = (AST)_t;
			astFactory.addASTChild(currentAST, tmp35_AST);
			ASTPair __currentAST28 = currentAST.copy();
			currentAST.root = currentAST.child;
			currentAST.child = null;
			match(_t,ARRAY_INIT);
			_t = _t.getFirstChild();
			{
			_loop30:
			do {
				if (_t==null) _t=ASTNULL;
				if ((_t.getType()==EXPR||_t.getType()==ARRAY_INIT)) {
					initializer(_t);
					_t = _retTree;
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					break _loop30;
				}
				
			} while (true);
			}
			currentAST = __currentAST28;
			_t = __t28;
			_t = _t.getNextSibling();
			arrayInitializer_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		returnAST = arrayInitializer_AST;
		_retTree = _t;
	}
	
	public final void ifElseStat(AST _t) throws RecognitionException {
		
		AST ifElseStat_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST ifElseStat_AST = null;
		
		try {      // for error handling
			AST __t42 = _t;
			AST tmp36_AST = null;
			AST tmp36_AST_in = null;
			tmp36_AST = astFactory.create((AST)_t);
			tmp36_AST_in = (AST)_t;
			astFactory.addASTChild(currentAST, tmp36_AST);
			ASTPair __currentAST42 = currentAST.copy();
			currentAST.root = currentAST.child;
			currentAST.child = null;
			match(_t,LITERAL_if);
			_t = _t.getFirstChild();
			expression(_t);
			_t = _retTree;
			astFactory.addASTChild(currentAST, returnAST);
			slist(_t);
			_t = _retTree;
			astFactory.addASTChild(currentAST, returnAST);
			{
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case SLIST:
			{
				slist(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case LITERAL_if:
			{
				ifElseStat(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case 3:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(_t);
			}
			}
			}
			currentAST = __currentAST42;
			_t = __t42;
			_t = _t.getNextSibling();
			ifElseStat_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		returnAST = ifElseStat_AST;
		_retTree = _t;
	}
	
	public final void elist(AST _t) throws RecognitionException {
		
		AST elist_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST elist_AST = null;
		
		try {      // for error handling
			AST __t81 = _t;
			AST tmp37_AST = null;
			AST tmp37_AST_in = null;
			tmp37_AST = astFactory.create((AST)_t);
			tmp37_AST_in = (AST)_t;
			astFactory.addASTChild(currentAST, tmp37_AST);
			ASTPair __currentAST81 = currentAST.copy();
			currentAST.root = currentAST.child;
			currentAST.child = null;
			match(_t,ELIST);
			_t = _t.getFirstChild();
			{
			_loop83:
			do {
				if (_t==null) _t=ASTNULL;
				if ((_t.getType()==EXPR)) {
					expression(_t);
					_t = _retTree;
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					break _loop83;
				}
				
			} while (true);
			}
			currentAST = __currentAST81;
			_t = __t81;
			_t = _t.getNextSibling();
			elist_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		returnAST = elist_AST;
		_retTree = _t;
	}
	
	public final void caseGroup(AST _t) throws RecognitionException {
		
		AST caseGroup_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST caseGroup_AST = null;
		
		try {      // for error handling
			AST __t68 = _t;
			AST tmp38_AST = null;
			AST tmp38_AST_in = null;
			tmp38_AST = astFactory.create((AST)_t);
			tmp38_AST_in = (AST)_t;
			astFactory.addASTChild(currentAST, tmp38_AST);
			ASTPair __currentAST68 = currentAST.copy();
			currentAST.root = currentAST.child;
			currentAST.child = null;
			match(_t,CASE_GROUP);
			_t = _t.getFirstChild();
			{
			int _cnt71=0;
			_loop71:
			do {
				if (_t==null) _t=ASTNULL;
				switch ( _t.getType()) {
				case LITERAL_case:
				{
					AST __t70 = _t;
					AST tmp39_AST = null;
					AST tmp39_AST_in = null;
					tmp39_AST = astFactory.create((AST)_t);
					tmp39_AST_in = (AST)_t;
					astFactory.addASTChild(currentAST, tmp39_AST);
					ASTPair __currentAST70 = currentAST.copy();
					currentAST.root = currentAST.child;
					currentAST.child = null;
					match(_t,LITERAL_case);
					_t = _t.getFirstChild();
					expression(_t);
					_t = _retTree;
					astFactory.addASTChild(currentAST, returnAST);
					currentAST = __currentAST70;
					_t = __t70;
					_t = _t.getNextSibling();
					break;
				}
				case LITERAL_default:
				{
					AST tmp40_AST = null;
					AST tmp40_AST_in = null;
					tmp40_AST = astFactory.create((AST)_t);
					tmp40_AST_in = (AST)_t;
					astFactory.addASTChild(currentAST, tmp40_AST);
					match(_t,LITERAL_default);
					_t = _t.getNextSibling();
					break;
				}
				default:
				{
					if ( _cnt71>=1 ) { break _loop71; } else {throw new NoViableAltException(_t);}
				}
				}
				_cnt71++;
			} while (true);
			}
			slist(_t);
			_t = _retTree;
			astFactory.addASTChild(currentAST, returnAST);
			currentAST = __currentAST68;
			_t = __t68;
			_t = _t.getNextSibling();
			caseGroup_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		returnAST = caseGroup_AST;
		_retTree = _t;
	}
	
	public final void tryBlock(AST _t) throws RecognitionException {
		
		AST tryBlock_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST tryBlock_AST = null;
		
		try {      // for error handling
			AST __t73 = _t;
			AST tmp41_AST = null;
			AST tmp41_AST_in = null;
			tmp41_AST = astFactory.create((AST)_t);
			tmp41_AST_in = (AST)_t;
			astFactory.addASTChild(currentAST, tmp41_AST);
			ASTPair __currentAST73 = currentAST.copy();
			currentAST.root = currentAST.child;
			currentAST.child = null;
			match(_t,LITERAL_try);
			_t = _t.getFirstChild();
			slist(_t);
			_t = _retTree;
			astFactory.addASTChild(currentAST, returnAST);
			{
			_loop75:
			do {
				if (_t==null) _t=ASTNULL;
				if ((_t.getType()==LITERAL_catch)) {
					handler(_t);
					_t = _retTree;
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					break _loop75;
				}
				
			} while (true);
			}
			{
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case LITERAL_finally:
			{
				AST __t77 = _t;
				AST tmp42_AST = null;
				AST tmp42_AST_in = null;
				tmp42_AST = astFactory.create((AST)_t);
				tmp42_AST_in = (AST)_t;
				astFactory.addASTChild(currentAST, tmp42_AST);
				ASTPair __currentAST77 = currentAST.copy();
				currentAST.root = currentAST.child;
				currentAST.child = null;
				match(_t,LITERAL_finally);
				_t = _t.getFirstChild();
				slist(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				currentAST = __currentAST77;
				_t = __t77;
				_t = _t.getNextSibling();
				break;
			}
			case 3:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(_t);
			}
			}
			}
			currentAST = __currentAST73;
			_t = __t73;
			_t = _t.getNextSibling();
			tryBlock_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		returnAST = tryBlock_AST;
		_retTree = _t;
	}
	
	public final void handler(AST _t) throws RecognitionException {
		
		AST handler_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST handler_AST = null;
		
		try {      // for error handling
			AST __t79 = _t;
			AST tmp43_AST = null;
			AST tmp43_AST_in = null;
			tmp43_AST = astFactory.create((AST)_t);
			tmp43_AST_in = (AST)_t;
			astFactory.addASTChild(currentAST, tmp43_AST);
			ASTPair __currentAST79 = currentAST.copy();
			currentAST.root = currentAST.child;
			currentAST.child = null;
			match(_t,LITERAL_catch);
			_t = _t.getFirstChild();
			parameterDef(_t);
			_t = _retTree;
			astFactory.addASTChild(currentAST, returnAST);
			slist(_t);
			_t = _retTree;
			astFactory.addASTChild(currentAST, returnAST);
			currentAST = __currentAST79;
			_t = __t79;
			_t = _t.getNextSibling();
			handler_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		returnAST = handler_AST;
		_retTree = _t;
	}
	
	public final void expr(AST _t) throws RecognitionException {
		
		AST expr_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST expr_AST = null;
		
		try {      // for error handling
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case QUESTION:
			{
				AST __t87 = _t;
				AST tmp44_AST = null;
				AST tmp44_AST_in = null;
				tmp44_AST = astFactory.create((AST)_t);
				tmp44_AST_in = (AST)_t;
				astFactory.addASTChild(currentAST, tmp44_AST);
				ASTPair __currentAST87 = currentAST.copy();
				currentAST.root = currentAST.child;
				currentAST.child = null;
				match(_t,QUESTION);
				_t = _t.getFirstChild();
				expr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				expr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				expr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				currentAST = __currentAST87;
				_t = __t87;
				_t = _t.getNextSibling();
				expr_AST = (AST)currentAST.root;
				break;
			}
			case ASSIGN:
			{
				AST __t88 = _t;
				AST tmp45_AST = null;
				AST tmp45_AST_in = null;
				tmp45_AST = astFactory.create((AST)_t);
				tmp45_AST_in = (AST)_t;
				astFactory.addASTChild(currentAST, tmp45_AST);
				ASTPair __currentAST88 = currentAST.copy();
				currentAST.root = currentAST.child;
				currentAST.child = null;
				match(_t,ASSIGN);
				_t = _t.getFirstChild();
				expr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				expr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				currentAST = __currentAST88;
				_t = __t88;
				_t = _t.getNextSibling();
				expr_AST = (AST)currentAST.root;
				break;
			}
			case PLUS_ASSIGN:
			{
				AST __t89 = _t;
				AST tmp46_AST = null;
				AST tmp46_AST_in = null;
				tmp46_AST = astFactory.create((AST)_t);
				tmp46_AST_in = (AST)_t;
				astFactory.addASTChild(currentAST, tmp46_AST);
				ASTPair __currentAST89 = currentAST.copy();
				currentAST.root = currentAST.child;
				currentAST.child = null;
				match(_t,PLUS_ASSIGN);
				_t = _t.getFirstChild();
				expr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				expr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				currentAST = __currentAST89;
				_t = __t89;
				_t = _t.getNextSibling();
				expr_AST = (AST)currentAST.root;
				break;
			}
			case MINUS_ASSIGN:
			{
				AST __t90 = _t;
				AST tmp47_AST = null;
				AST tmp47_AST_in = null;
				tmp47_AST = astFactory.create((AST)_t);
				tmp47_AST_in = (AST)_t;
				astFactory.addASTChild(currentAST, tmp47_AST);
				ASTPair __currentAST90 = currentAST.copy();
				currentAST.root = currentAST.child;
				currentAST.child = null;
				match(_t,MINUS_ASSIGN);
				_t = _t.getFirstChild();
				expr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				expr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				currentAST = __currentAST90;
				_t = __t90;
				_t = _t.getNextSibling();
				expr_AST = (AST)currentAST.root;
				break;
			}
			case STAR_ASSIGN:
			{
				AST __t91 = _t;
				AST tmp48_AST = null;
				AST tmp48_AST_in = null;
				tmp48_AST = astFactory.create((AST)_t);
				tmp48_AST_in = (AST)_t;
				astFactory.addASTChild(currentAST, tmp48_AST);
				ASTPair __currentAST91 = currentAST.copy();
				currentAST.root = currentAST.child;
				currentAST.child = null;
				match(_t,STAR_ASSIGN);
				_t = _t.getFirstChild();
				expr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				expr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				currentAST = __currentAST91;
				_t = __t91;
				_t = _t.getNextSibling();
				expr_AST = (AST)currentAST.root;
				break;
			}
			case DIV_ASSIGN:
			{
				AST __t92 = _t;
				AST tmp49_AST = null;
				AST tmp49_AST_in = null;
				tmp49_AST = astFactory.create((AST)_t);
				tmp49_AST_in = (AST)_t;
				astFactory.addASTChild(currentAST, tmp49_AST);
				ASTPair __currentAST92 = currentAST.copy();
				currentAST.root = currentAST.child;
				currentAST.child = null;
				match(_t,DIV_ASSIGN);
				_t = _t.getFirstChild();
				expr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				expr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				currentAST = __currentAST92;
				_t = __t92;
				_t = _t.getNextSibling();
				expr_AST = (AST)currentAST.root;
				break;
			}
			case MOD_ASSIGN:
			{
				AST __t93 = _t;
				AST tmp50_AST = null;
				AST tmp50_AST_in = null;
				tmp50_AST = astFactory.create((AST)_t);
				tmp50_AST_in = (AST)_t;
				astFactory.addASTChild(currentAST, tmp50_AST);
				ASTPair __currentAST93 = currentAST.copy();
				currentAST.root = currentAST.child;
				currentAST.child = null;
				match(_t,MOD_ASSIGN);
				_t = _t.getFirstChild();
				expr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				expr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				currentAST = __currentAST93;
				_t = __t93;
				_t = _t.getNextSibling();
				expr_AST = (AST)currentAST.root;
				break;
			}
			case SR_ASSIGN:
			{
				AST __t94 = _t;
				AST tmp51_AST = null;
				AST tmp51_AST_in = null;
				tmp51_AST = astFactory.create((AST)_t);
				tmp51_AST_in = (AST)_t;
				astFactory.addASTChild(currentAST, tmp51_AST);
				ASTPair __currentAST94 = currentAST.copy();
				currentAST.root = currentAST.child;
				currentAST.child = null;
				match(_t,SR_ASSIGN);
				_t = _t.getFirstChild();
				expr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				expr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				currentAST = __currentAST94;
				_t = __t94;
				_t = _t.getNextSibling();
				expr_AST = (AST)currentAST.root;
				break;
			}
			case BSR_ASSIGN:
			{
				AST __t95 = _t;
				AST tmp52_AST = null;
				AST tmp52_AST_in = null;
				tmp52_AST = astFactory.create((AST)_t);
				tmp52_AST_in = (AST)_t;
				astFactory.addASTChild(currentAST, tmp52_AST);
				ASTPair __currentAST95 = currentAST.copy();
				currentAST.root = currentAST.child;
				currentAST.child = null;
				match(_t,BSR_ASSIGN);
				_t = _t.getFirstChild();
				expr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				expr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				currentAST = __currentAST95;
				_t = __t95;
				_t = _t.getNextSibling();
				expr_AST = (AST)currentAST.root;
				break;
			}
			case SL_ASSIGN:
			{
				AST __t96 = _t;
				AST tmp53_AST = null;
				AST tmp53_AST_in = null;
				tmp53_AST = astFactory.create((AST)_t);
				tmp53_AST_in = (AST)_t;
				astFactory.addASTChild(currentAST, tmp53_AST);
				ASTPair __currentAST96 = currentAST.copy();
				currentAST.root = currentAST.child;
				currentAST.child = null;
				match(_t,SL_ASSIGN);
				_t = _t.getFirstChild();
				expr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				expr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				currentAST = __currentAST96;
				_t = __t96;
				_t = _t.getNextSibling();
				expr_AST = (AST)currentAST.root;
				break;
			}
			case BAND_ASSIGN:
			{
				AST __t97 = _t;
				AST tmp54_AST = null;
				AST tmp54_AST_in = null;
				tmp54_AST = astFactory.create((AST)_t);
				tmp54_AST_in = (AST)_t;
				astFactory.addASTChild(currentAST, tmp54_AST);
				ASTPair __currentAST97 = currentAST.copy();
				currentAST.root = currentAST.child;
				currentAST.child = null;
				match(_t,BAND_ASSIGN);
				_t = _t.getFirstChild();
				expr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				expr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				currentAST = __currentAST97;
				_t = __t97;
				_t = _t.getNextSibling();
				expr_AST = (AST)currentAST.root;
				break;
			}
			case BXOR_ASSIGN:
			{
				AST __t98 = _t;
				AST tmp55_AST = null;
				AST tmp55_AST_in = null;
				tmp55_AST = astFactory.create((AST)_t);
				tmp55_AST_in = (AST)_t;
				astFactory.addASTChild(currentAST, tmp55_AST);
				ASTPair __currentAST98 = currentAST.copy();
				currentAST.root = currentAST.child;
				currentAST.child = null;
				match(_t,BXOR_ASSIGN);
				_t = _t.getFirstChild();
				expr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				expr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				currentAST = __currentAST98;
				_t = __t98;
				_t = _t.getNextSibling();
				expr_AST = (AST)currentAST.root;
				break;
			}
			case BOR_ASSIGN:
			{
				AST __t99 = _t;
				AST tmp56_AST = null;
				AST tmp56_AST_in = null;
				tmp56_AST = astFactory.create((AST)_t);
				tmp56_AST_in = (AST)_t;
				astFactory.addASTChild(currentAST, tmp56_AST);
				ASTPair __currentAST99 = currentAST.copy();
				currentAST.root = currentAST.child;
				currentAST.child = null;
				match(_t,BOR_ASSIGN);
				_t = _t.getFirstChild();
				expr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				expr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				currentAST = __currentAST99;
				_t = __t99;
				_t = _t.getNextSibling();
				expr_AST = (AST)currentAST.root;
				break;
			}
			case LOR:
			{
				AST __t100 = _t;
				AST tmp57_AST = null;
				AST tmp57_AST_in = null;
				tmp57_AST = astFactory.create((AST)_t);
				tmp57_AST_in = (AST)_t;
				astFactory.addASTChild(currentAST, tmp57_AST);
				ASTPair __currentAST100 = currentAST.copy();
				currentAST.root = currentAST.child;
				currentAST.child = null;
				match(_t,LOR);
				_t = _t.getFirstChild();
				expr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				expr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				currentAST = __currentAST100;
				_t = __t100;
				_t = _t.getNextSibling();
				expr_AST = (AST)currentAST.root;
				break;
			}
			case LAND:
			{
				AST __t101 = _t;
				AST tmp58_AST = null;
				AST tmp58_AST_in = null;
				tmp58_AST = astFactory.create((AST)_t);
				tmp58_AST_in = (AST)_t;
				astFactory.addASTChild(currentAST, tmp58_AST);
				ASTPair __currentAST101 = currentAST.copy();
				currentAST.root = currentAST.child;
				currentAST.child = null;
				match(_t,LAND);
				_t = _t.getFirstChild();
				expr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				expr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				currentAST = __currentAST101;
				_t = __t101;
				_t = _t.getNextSibling();
				expr_AST = (AST)currentAST.root;
				break;
			}
			case BOR:
			{
				AST __t102 = _t;
				AST tmp59_AST = null;
				AST tmp59_AST_in = null;
				tmp59_AST = astFactory.create((AST)_t);
				tmp59_AST_in = (AST)_t;
				astFactory.addASTChild(currentAST, tmp59_AST);
				ASTPair __currentAST102 = currentAST.copy();
				currentAST.root = currentAST.child;
				currentAST.child = null;
				match(_t,BOR);
				_t = _t.getFirstChild();
				expr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				expr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				currentAST = __currentAST102;
				_t = __t102;
				_t = _t.getNextSibling();
				expr_AST = (AST)currentAST.root;
				break;
			}
			case BXOR:
			{
				AST __t103 = _t;
				AST tmp60_AST = null;
				AST tmp60_AST_in = null;
				tmp60_AST = astFactory.create((AST)_t);
				tmp60_AST_in = (AST)_t;
				astFactory.addASTChild(currentAST, tmp60_AST);
				ASTPair __currentAST103 = currentAST.copy();
				currentAST.root = currentAST.child;
				currentAST.child = null;
				match(_t,BXOR);
				_t = _t.getFirstChild();
				expr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				expr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				currentAST = __currentAST103;
				_t = __t103;
				_t = _t.getNextSibling();
				expr_AST = (AST)currentAST.root;
				break;
			}
			case BAND:
			{
				AST __t104 = _t;
				AST tmp61_AST = null;
				AST tmp61_AST_in = null;
				tmp61_AST = astFactory.create((AST)_t);
				tmp61_AST_in = (AST)_t;
				astFactory.addASTChild(currentAST, tmp61_AST);
				ASTPair __currentAST104 = currentAST.copy();
				currentAST.root = currentAST.child;
				currentAST.child = null;
				match(_t,BAND);
				_t = _t.getFirstChild();
				expr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				expr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				currentAST = __currentAST104;
				_t = __t104;
				_t = _t.getNextSibling();
				expr_AST = (AST)currentAST.root;
				break;
			}
			case NOT_EQUAL:
			{
				AST __t105 = _t;
				AST tmp62_AST = null;
				AST tmp62_AST_in = null;
				tmp62_AST = astFactory.create((AST)_t);
				tmp62_AST_in = (AST)_t;
				astFactory.addASTChild(currentAST, tmp62_AST);
				ASTPair __currentAST105 = currentAST.copy();
				currentAST.root = currentAST.child;
				currentAST.child = null;
				match(_t,NOT_EQUAL);
				_t = _t.getFirstChild();
				expr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				expr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				currentAST = __currentAST105;
				_t = __t105;
				_t = _t.getNextSibling();
				expr_AST = (AST)currentAST.root;
				break;
			}
			case EQUAL:
			{
				AST __t106 = _t;
				AST tmp63_AST = null;
				AST tmp63_AST_in = null;
				tmp63_AST = astFactory.create((AST)_t);
				tmp63_AST_in = (AST)_t;
				astFactory.addASTChild(currentAST, tmp63_AST);
				ASTPair __currentAST106 = currentAST.copy();
				currentAST.root = currentAST.child;
				currentAST.child = null;
				match(_t,EQUAL);
				_t = _t.getFirstChild();
				expr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				expr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				currentAST = __currentAST106;
				_t = __t106;
				_t = _t.getNextSibling();
				expr_AST = (AST)currentAST.root;
				break;
			}
			case LT:
			{
				AST __t107 = _t;
				AST tmp64_AST = null;
				AST tmp64_AST_in = null;
				tmp64_AST = astFactory.create((AST)_t);
				tmp64_AST_in = (AST)_t;
				astFactory.addASTChild(currentAST, tmp64_AST);
				ASTPair __currentAST107 = currentAST.copy();
				currentAST.root = currentAST.child;
				currentAST.child = null;
				match(_t,LT);
				_t = _t.getFirstChild();
				expr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				expr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				currentAST = __currentAST107;
				_t = __t107;
				_t = _t.getNextSibling();
				expr_AST = (AST)currentAST.root;
				break;
			}
			case GT:
			{
				AST __t108 = _t;
				AST tmp65_AST = null;
				AST tmp65_AST_in = null;
				tmp65_AST = astFactory.create((AST)_t);
				tmp65_AST_in = (AST)_t;
				astFactory.addASTChild(currentAST, tmp65_AST);
				ASTPair __currentAST108 = currentAST.copy();
				currentAST.root = currentAST.child;
				currentAST.child = null;
				match(_t,GT);
				_t = _t.getFirstChild();
				expr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				expr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				currentAST = __currentAST108;
				_t = __t108;
				_t = _t.getNextSibling();
				expr_AST = (AST)currentAST.root;
				break;
			}
			case LE:
			{
				AST __t109 = _t;
				AST tmp66_AST = null;
				AST tmp66_AST_in = null;
				tmp66_AST = astFactory.create((AST)_t);
				tmp66_AST_in = (AST)_t;
				astFactory.addASTChild(currentAST, tmp66_AST);
				ASTPair __currentAST109 = currentAST.copy();
				currentAST.root = currentAST.child;
				currentAST.child = null;
				match(_t,LE);
				_t = _t.getFirstChild();
				expr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				expr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				currentAST = __currentAST109;
				_t = __t109;
				_t = _t.getNextSibling();
				expr_AST = (AST)currentAST.root;
				break;
			}
			case GE:
			{
				AST __t110 = _t;
				AST tmp67_AST = null;
				AST tmp67_AST_in = null;
				tmp67_AST = astFactory.create((AST)_t);
				tmp67_AST_in = (AST)_t;
				astFactory.addASTChild(currentAST, tmp67_AST);
				ASTPair __currentAST110 = currentAST.copy();
				currentAST.root = currentAST.child;
				currentAST.child = null;
				match(_t,GE);
				_t = _t.getFirstChild();
				expr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				expr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				currentAST = __currentAST110;
				_t = __t110;
				_t = _t.getNextSibling();
				expr_AST = (AST)currentAST.root;
				break;
			}
			case SL:
			{
				AST __t111 = _t;
				AST tmp68_AST = null;
				AST tmp68_AST_in = null;
				tmp68_AST = astFactory.create((AST)_t);
				tmp68_AST_in = (AST)_t;
				astFactory.addASTChild(currentAST, tmp68_AST);
				ASTPair __currentAST111 = currentAST.copy();
				currentAST.root = currentAST.child;
				currentAST.child = null;
				match(_t,SL);
				_t = _t.getFirstChild();
				expr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				expr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				currentAST = __currentAST111;
				_t = __t111;
				_t = _t.getNextSibling();
				expr_AST = (AST)currentAST.root;
				break;
			}
			case SR:
			{
				AST __t112 = _t;
				AST tmp69_AST = null;
				AST tmp69_AST_in = null;
				tmp69_AST = astFactory.create((AST)_t);
				tmp69_AST_in = (AST)_t;
				astFactory.addASTChild(currentAST, tmp69_AST);
				ASTPair __currentAST112 = currentAST.copy();
				currentAST.root = currentAST.child;
				currentAST.child = null;
				match(_t,SR);
				_t = _t.getFirstChild();
				expr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				expr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				currentAST = __currentAST112;
				_t = __t112;
				_t = _t.getNextSibling();
				expr_AST = (AST)currentAST.root;
				break;
			}
			case BSR:
			{
				AST __t113 = _t;
				AST tmp70_AST = null;
				AST tmp70_AST_in = null;
				tmp70_AST = astFactory.create((AST)_t);
				tmp70_AST_in = (AST)_t;
				astFactory.addASTChild(currentAST, tmp70_AST);
				ASTPair __currentAST113 = currentAST.copy();
				currentAST.root = currentAST.child;
				currentAST.child = null;
				match(_t,BSR);
				_t = _t.getFirstChild();
				expr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				expr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				currentAST = __currentAST113;
				_t = __t113;
				_t = _t.getNextSibling();
				expr_AST = (AST)currentAST.root;
				break;
			}
			case PLUS:
			{
				AST __t114 = _t;
				AST tmp71_AST = null;
				AST tmp71_AST_in = null;
				tmp71_AST = astFactory.create((AST)_t);
				tmp71_AST_in = (AST)_t;
				astFactory.addASTChild(currentAST, tmp71_AST);
				ASTPair __currentAST114 = currentAST.copy();
				currentAST.root = currentAST.child;
				currentAST.child = null;
				match(_t,PLUS);
				_t = _t.getFirstChild();
				expr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				expr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				currentAST = __currentAST114;
				_t = __t114;
				_t = _t.getNextSibling();
				expr_AST = (AST)currentAST.root;
				break;
			}
			case MINUS:
			{
				AST __t115 = _t;
				AST tmp72_AST = null;
				AST tmp72_AST_in = null;
				tmp72_AST = astFactory.create((AST)_t);
				tmp72_AST_in = (AST)_t;
				astFactory.addASTChild(currentAST, tmp72_AST);
				ASTPair __currentAST115 = currentAST.copy();
				currentAST.root = currentAST.child;
				currentAST.child = null;
				match(_t,MINUS);
				_t = _t.getFirstChild();
				expr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				expr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				currentAST = __currentAST115;
				_t = __t115;
				_t = _t.getNextSibling();
				expr_AST = (AST)currentAST.root;
				break;
			}
			case DIV:
			{
				AST __t116 = _t;
				AST tmp73_AST = null;
				AST tmp73_AST_in = null;
				tmp73_AST = astFactory.create((AST)_t);
				tmp73_AST_in = (AST)_t;
				astFactory.addASTChild(currentAST, tmp73_AST);
				ASTPair __currentAST116 = currentAST.copy();
				currentAST.root = currentAST.child;
				currentAST.child = null;
				match(_t,DIV);
				_t = _t.getFirstChild();
				expr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				expr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				currentAST = __currentAST116;
				_t = __t116;
				_t = _t.getNextSibling();
				expr_AST = (AST)currentAST.root;
				break;
			}
			case MOD:
			{
				AST __t117 = _t;
				AST tmp74_AST = null;
				AST tmp74_AST_in = null;
				tmp74_AST = astFactory.create((AST)_t);
				tmp74_AST_in = (AST)_t;
				astFactory.addASTChild(currentAST, tmp74_AST);
				ASTPair __currentAST117 = currentAST.copy();
				currentAST.root = currentAST.child;
				currentAST.child = null;
				match(_t,MOD);
				_t = _t.getFirstChild();
				expr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				expr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				currentAST = __currentAST117;
				_t = __t117;
				_t = _t.getNextSibling();
				expr_AST = (AST)currentAST.root;
				break;
			}
			case STAR:
			{
				AST __t118 = _t;
				AST tmp75_AST = null;
				AST tmp75_AST_in = null;
				tmp75_AST = astFactory.create((AST)_t);
				tmp75_AST_in = (AST)_t;
				astFactory.addASTChild(currentAST, tmp75_AST);
				ASTPair __currentAST118 = currentAST.copy();
				currentAST.root = currentAST.child;
				currentAST.child = null;
				match(_t,STAR);
				_t = _t.getFirstChild();
				expr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				expr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				currentAST = __currentAST118;
				_t = __t118;
				_t = _t.getNextSibling();
				expr_AST = (AST)currentAST.root;
				break;
			}
			case INC:
			{
				AST __t119 = _t;
				AST tmp76_AST = null;
				AST tmp76_AST_in = null;
				tmp76_AST = astFactory.create((AST)_t);
				tmp76_AST_in = (AST)_t;
				astFactory.addASTChild(currentAST, tmp76_AST);
				ASTPair __currentAST119 = currentAST.copy();
				currentAST.root = currentAST.child;
				currentAST.child = null;
				match(_t,INC);
				_t = _t.getFirstChild();
				expr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				currentAST = __currentAST119;
				_t = __t119;
				_t = _t.getNextSibling();
				expr_AST = (AST)currentAST.root;
				break;
			}
			case DEC:
			{
				AST __t120 = _t;
				AST tmp77_AST = null;
				AST tmp77_AST_in = null;
				tmp77_AST = astFactory.create((AST)_t);
				tmp77_AST_in = (AST)_t;
				astFactory.addASTChild(currentAST, tmp77_AST);
				ASTPair __currentAST120 = currentAST.copy();
				currentAST.root = currentAST.child;
				currentAST.child = null;
				match(_t,DEC);
				_t = _t.getFirstChild();
				expr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				currentAST = __currentAST120;
				_t = __t120;
				_t = _t.getNextSibling();
				expr_AST = (AST)currentAST.root;
				break;
			}
			case POST_INC:
			{
				AST __t121 = _t;
				AST tmp78_AST = null;
				AST tmp78_AST_in = null;
				tmp78_AST = astFactory.create((AST)_t);
				tmp78_AST_in = (AST)_t;
				astFactory.addASTChild(currentAST, tmp78_AST);
				ASTPair __currentAST121 = currentAST.copy();
				currentAST.root = currentAST.child;
				currentAST.child = null;
				match(_t,POST_INC);
				_t = _t.getFirstChild();
				expr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				currentAST = __currentAST121;
				_t = __t121;
				_t = _t.getNextSibling();
				expr_AST = (AST)currentAST.root;
				break;
			}
			case POST_DEC:
			{
				AST __t122 = _t;
				AST tmp79_AST = null;
				AST tmp79_AST_in = null;
				tmp79_AST = astFactory.create((AST)_t);
				tmp79_AST_in = (AST)_t;
				astFactory.addASTChild(currentAST, tmp79_AST);
				ASTPair __currentAST122 = currentAST.copy();
				currentAST.root = currentAST.child;
				currentAST.child = null;
				match(_t,POST_DEC);
				_t = _t.getFirstChild();
				expr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				currentAST = __currentAST122;
				_t = __t122;
				_t = _t.getNextSibling();
				expr_AST = (AST)currentAST.root;
				break;
			}
			case BNOT:
			{
				AST __t123 = _t;
				AST tmp80_AST = null;
				AST tmp80_AST_in = null;
				tmp80_AST = astFactory.create((AST)_t);
				tmp80_AST_in = (AST)_t;
				astFactory.addASTChild(currentAST, tmp80_AST);
				ASTPair __currentAST123 = currentAST.copy();
				currentAST.root = currentAST.child;
				currentAST.child = null;
				match(_t,BNOT);
				_t = _t.getFirstChild();
				expr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				currentAST = __currentAST123;
				_t = __t123;
				_t = _t.getNextSibling();
				expr_AST = (AST)currentAST.root;
				break;
			}
			case LNOT:
			{
				AST __t124 = _t;
				AST tmp81_AST = null;
				AST tmp81_AST_in = null;
				tmp81_AST = astFactory.create((AST)_t);
				tmp81_AST_in = (AST)_t;
				astFactory.addASTChild(currentAST, tmp81_AST);
				ASTPair __currentAST124 = currentAST.copy();
				currentAST.root = currentAST.child;
				currentAST.child = null;
				match(_t,LNOT);
				_t = _t.getFirstChild();
				expr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				currentAST = __currentAST124;
				_t = __t124;
				_t = _t.getNextSibling();
				expr_AST = (AST)currentAST.root;
				break;
			}
			case LITERAL_instanceof:
			{
				AST __t125 = _t;
				AST tmp82_AST = null;
				AST tmp82_AST_in = null;
				tmp82_AST = astFactory.create((AST)_t);
				tmp82_AST_in = (AST)_t;
				astFactory.addASTChild(currentAST, tmp82_AST);
				ASTPair __currentAST125 = currentAST.copy();
				currentAST.root = currentAST.child;
				currentAST.child = null;
				match(_t,LITERAL_instanceof);
				_t = _t.getFirstChild();
				expr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				expr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				currentAST = __currentAST125;
				_t = __t125;
				_t = _t.getNextSibling();
				expr_AST = (AST)currentAST.root;
				break;
			}
			case UNARY_MINUS:
			{
				AST __t126 = _t;
				AST tmp83_AST = null;
				AST tmp83_AST_in = null;
				tmp83_AST = astFactory.create((AST)_t);
				tmp83_AST_in = (AST)_t;
				astFactory.addASTChild(currentAST, tmp83_AST);
				ASTPair __currentAST126 = currentAST.copy();
				currentAST.root = currentAST.child;
				currentAST.child = null;
				match(_t,UNARY_MINUS);
				_t = _t.getFirstChild();
				expr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				currentAST = __currentAST126;
				_t = __t126;
				_t = _t.getNextSibling();
				expr_AST = (AST)currentAST.root;
				break;
			}
			case UNARY_PLUS:
			{
				AST __t127 = _t;
				AST tmp84_AST = null;
				AST tmp84_AST_in = null;
				tmp84_AST = astFactory.create((AST)_t);
				tmp84_AST_in = (AST)_t;
				astFactory.addASTChild(currentAST, tmp84_AST);
				ASTPair __currentAST127 = currentAST.copy();
				currentAST.root = currentAST.child;
				currentAST.child = null;
				match(_t,UNARY_PLUS);
				_t = _t.getFirstChild();
				expr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				currentAST = __currentAST127;
				_t = __t127;
				_t = _t.getNextSibling();
				expr_AST = (AST)currentAST.root;
				break;
			}
			case TYPE:
			case TYPECAST:
			case INDEX_OP:
			case METHOD_CALL:
			case IDENT:
			case DOT:
			case LITERAL_true:
			case LITERAL_false:
			case LITERAL_null:
			case LITERAL_new:
			case NUM_INT:
			case STRING_LITERAL:
			case NUM_FLOAT:
			case NUM_LONG:
			case NUM_DOUBLE:
			{
				primaryExpression(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				expr_AST = (AST)currentAST.root;
				break;
			}
			default:
			{
				throw new NoViableAltException(_t);
			}
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		returnAST = expr_AST;
		_retTree = _t;
	}
	
	public final void primaryExpression(AST _t) throws RecognitionException {
		
		AST primaryExpression_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST primaryExpression_AST = null;
		AST id1 = null;
		AST id1_AST = null;
		AST id2 = null;
		AST id2_AST = null;
		AST methodAST = null;
		AST methodAST_AST = null;
		
		try {      // for error handling
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case IDENT:
			{
				id1 = (AST)_t;
				AST id1_AST_in = null;
				id1_AST = astFactory.create(id1);
				astFactory.addASTChild(currentAST, id1_AST);
				match(_t,IDENT);
				_t = _t.getNextSibling();
				primaryExpression_AST = (AST)currentAST.root;
				break;
			}
			case DOT:
			{
				AST __t129 = _t;
				AST tmp85_AST = null;
				AST tmp85_AST_in = null;
				tmp85_AST = astFactory.create((AST)_t);
				tmp85_AST_in = (AST)_t;
				astFactory.addASTChild(currentAST, tmp85_AST);
				ASTPair __currentAST129 = currentAST.copy();
				currentAST.root = currentAST.child;
				currentAST.child = null;
				match(_t,DOT);
				_t = _t.getFirstChild();
				{
				if (_t==null) _t=ASTNULL;
				switch ( _t.getType()) {
				case TYPE:
				case TYPECAST:
				case INDEX_OP:
				case POST_INC:
				case POST_DEC:
				case METHOD_CALL:
				case UNARY_MINUS:
				case UNARY_PLUS:
				case IDENT:
				case DOT:
				case STAR:
				case ASSIGN:
				case PLUS_ASSIGN:
				case MINUS_ASSIGN:
				case STAR_ASSIGN:
				case DIV_ASSIGN:
				case MOD_ASSIGN:
				case SR_ASSIGN:
				case BSR_ASSIGN:
				case SL_ASSIGN:
				case BAND_ASSIGN:
				case BXOR_ASSIGN:
				case BOR_ASSIGN:
				case QUESTION:
				case LOR:
				case LAND:
				case BOR:
				case BXOR:
				case BAND:
				case NOT_EQUAL:
				case EQUAL:
				case LT:
				case GT:
				case LE:
				case GE:
				case SL:
				case SR:
				case BSR:
				case PLUS:
				case MINUS:
				case DIV:
				case MOD:
				case INC:
				case DEC:
				case BNOT:
				case LNOT:
				case LITERAL_true:
				case LITERAL_false:
				case LITERAL_null:
				case LITERAL_new:
				case NUM_INT:
				case STRING_LITERAL:
				case NUM_FLOAT:
				case NUM_LONG:
				case NUM_DOUBLE:
				case LITERAL_instanceof:
				{
					expr(_t);
					_t = _retTree;
					astFactory.addASTChild(currentAST, returnAST);
					{
					if (_t==null) _t=ASTNULL;
					switch ( _t.getType()) {
					case IDENT:
					{
						id2 = (AST)_t;
						AST id2_AST_in = null;
						id2_AST = astFactory.create(id2);
						astFactory.addASTChild(currentAST, id2_AST);
						match(_t,IDENT);
						_t = _t.getNextSibling();
						break;
					}
					case INDEX_OP:
					{
						arrayIndex(_t);
						_t = _retTree;
						astFactory.addASTChild(currentAST, returnAST);
						break;
					}
					case LITERAL_class:
					{
						AST tmp86_AST = null;
						AST tmp86_AST_in = null;
						tmp86_AST = astFactory.create((AST)_t);
						tmp86_AST_in = (AST)_t;
						astFactory.addASTChild(currentAST, tmp86_AST);
						match(_t,LITERAL_class);
						_t = _t.getNextSibling();
						break;
					}
					case LITERAL_new:
					{
						AST __t132 = _t;
						AST tmp87_AST = null;
						AST tmp87_AST_in = null;
						tmp87_AST = astFactory.create((AST)_t);
						tmp87_AST_in = (AST)_t;
						astFactory.addASTChild(currentAST, tmp87_AST);
						ASTPair __currentAST132 = currentAST.copy();
						currentAST.root = currentAST.child;
						currentAST.child = null;
						match(_t,LITERAL_new);
						_t = _t.getFirstChild();
						AST tmp88_AST = null;
						AST tmp88_AST_in = null;
						tmp88_AST = astFactory.create((AST)_t);
						tmp88_AST_in = (AST)_t;
						astFactory.addASTChild(currentAST, tmp88_AST);
						match(_t,IDENT);
						_t = _t.getNextSibling();
						elist(_t);
						_t = _retTree;
						astFactory.addASTChild(currentAST, returnAST);
						currentAST = __currentAST132;
						_t = __t132;
						_t = _t.getNextSibling();
						break;
					}
					default:
					{
						throw new NoViableAltException(_t);
					}
					}
					}
					break;
				}
				case ARRAY_DECLARATOR:
				{
					AST __t133 = _t;
					AST tmp89_AST = null;
					AST tmp89_AST_in = null;
					tmp89_AST = astFactory.create((AST)_t);
					tmp89_AST_in = (AST)_t;
					astFactory.addASTChild(currentAST, tmp89_AST);
					ASTPair __currentAST133 = currentAST.copy();
					currentAST.root = currentAST.child;
					currentAST.child = null;
					match(_t,ARRAY_DECLARATOR);
					_t = _t.getFirstChild();
					typeSpecArray(_t);
					_t = _retTree;
					astFactory.addASTChild(currentAST, returnAST);
					currentAST = __currentAST133;
					_t = __t133;
					_t = _t.getNextSibling();
					break;
				}
				case LITERAL_var:
				{
					builtInType(_t);
					_t = _retTree;
					astFactory.addASTChild(currentAST, returnAST);
					{
					if (_t==null) _t=ASTNULL;
					switch ( _t.getType()) {
					case LITERAL_class:
					{
						AST tmp90_AST = null;
						AST tmp90_AST_in = null;
						tmp90_AST = astFactory.create((AST)_t);
						tmp90_AST_in = (AST)_t;
						astFactory.addASTChild(currentAST, tmp90_AST);
						match(_t,LITERAL_class);
						_t = _t.getNextSibling();
						break;
					}
					case 3:
					{
						break;
					}
					default:
					{
						throw new NoViableAltException(_t);
					}
					}
					}
					break;
				}
				default:
				{
					throw new NoViableAltException(_t);
				}
				}
				}
				currentAST = __currentAST129;
				_t = __t129;
				_t = _t.getNextSibling();
				primaryExpression_AST = (AST)currentAST.root;
				break;
			}
			case INDEX_OP:
			{
				arrayIndex(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				primaryExpression_AST = (AST)currentAST.root;
				break;
			}
			case METHOD_CALL:
			{
				AST __t135 = _t;
				methodAST = _t==ASTNULL ? null :(AST)_t;
				AST methodAST_AST_in = null;
				methodAST_AST = astFactory.create(methodAST);
				astFactory.addASTChild(currentAST, methodAST_AST);
				ASTPair __currentAST135 = currentAST.copy();
				currentAST.root = currentAST.child;
				currentAST.child = null;
				match(_t,METHOD_CALL);
				_t = _t.getFirstChild();
				primaryExpression(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				elist(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				currentAST = __currentAST135;
				_t = __t135;
				_t = _t.getNextSibling();
				primaryExpression_AST = (AST)currentAST.root;
				break;
			}
			case TYPECAST:
			{
				AST __t136 = _t;
				AST tmp91_AST = null;
				AST tmp91_AST_in = null;
				tmp91_AST = astFactory.create((AST)_t);
				tmp91_AST_in = (AST)_t;
				astFactory.addASTChild(currentAST, tmp91_AST);
				ASTPair __currentAST136 = currentAST.copy();
				currentAST.root = currentAST.child;
				currentAST.child = null;
				match(_t,TYPECAST);
				_t = _t.getFirstChild();
				typeSpec(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				expr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				currentAST = __currentAST136;
				_t = __t136;
				_t = _t.getNextSibling();
				primaryExpression_AST = (AST)currentAST.root;
				break;
			}
			case LITERAL_new:
			{
				newExpression(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				primaryExpression_AST = (AST)currentAST.root;
				break;
			}
			case NUM_INT:
			case STRING_LITERAL:
			case NUM_FLOAT:
			case NUM_LONG:
			case NUM_DOUBLE:
			{
				constant(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				primaryExpression_AST = (AST)currentAST.root;
				break;
			}
			case LITERAL_true:
			{
				AST tmp92_AST = null;
				AST tmp92_AST_in = null;
				tmp92_AST = astFactory.create((AST)_t);
				tmp92_AST_in = (AST)_t;
				astFactory.addASTChild(currentAST, tmp92_AST);
				match(_t,LITERAL_true);
				_t = _t.getNextSibling();
				primaryExpression_AST = (AST)currentAST.root;
				break;
			}
			case LITERAL_false:
			{
				AST tmp93_AST = null;
				AST tmp93_AST_in = null;
				tmp93_AST = astFactory.create((AST)_t);
				tmp93_AST_in = (AST)_t;
				astFactory.addASTChild(currentAST, tmp93_AST);
				match(_t,LITERAL_false);
				_t = _t.getNextSibling();
				primaryExpression_AST = (AST)currentAST.root;
				break;
			}
			case LITERAL_null:
			{
				AST tmp94_AST = null;
				AST tmp94_AST_in = null;
				tmp94_AST = astFactory.create((AST)_t);
				tmp94_AST_in = (AST)_t;
				astFactory.addASTChild(currentAST, tmp94_AST);
				match(_t,LITERAL_null);
				_t = _t.getNextSibling();
				primaryExpression_AST = (AST)currentAST.root;
				break;
			}
			case TYPE:
			{
				typeSpec(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				primaryExpression_AST = (AST)currentAST.root;
				break;
			}
			default:
			{
				throw new NoViableAltException(_t);
			}
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		returnAST = primaryExpression_AST;
		_retTree = _t;
	}
	
	public final void arrayIndex(AST _t) throws RecognitionException {
		
		AST arrayIndex_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST arrayIndex_AST = null;
		
		try {      // for error handling
			AST __t138 = _t;
			AST tmp95_AST = null;
			AST tmp95_AST_in = null;
			tmp95_AST = astFactory.create((AST)_t);
			tmp95_AST_in = (AST)_t;
			astFactory.addASTChild(currentAST, tmp95_AST);
			ASTPair __currentAST138 = currentAST.copy();
			currentAST.root = currentAST.child;
			currentAST.child = null;
			match(_t,INDEX_OP);
			_t = _t.getFirstChild();
			expr(_t);
			_t = _retTree;
			astFactory.addASTChild(currentAST, returnAST);
			expression(_t);
			_t = _retTree;
			astFactory.addASTChild(currentAST, returnAST);
			currentAST = __currentAST138;
			_t = __t138;
			_t = _t.getNextSibling();
			arrayIndex_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		returnAST = arrayIndex_AST;
		_retTree = _t;
	}
	
	public final void newExpression(AST _t) throws RecognitionException {
		
		AST newExpression_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST newExpression_AST = null;
		
		try {      // for error handling
			AST __t141 = _t;
			AST tmp96_AST = null;
			AST tmp96_AST_in = null;
			tmp96_AST = astFactory.create((AST)_t);
			tmp96_AST_in = (AST)_t;
			astFactory.addASTChild(currentAST, tmp96_AST);
			ASTPair __currentAST141 = currentAST.copy();
			currentAST.root = currentAST.child;
			currentAST.child = null;
			match(_t,LITERAL_new);
			_t = _t.getFirstChild();
			type(_t);
			_t = _retTree;
			astFactory.addASTChild(currentAST, returnAST);
			elist(_t);
			_t = _retTree;
			astFactory.addASTChild(currentAST, returnAST);
			currentAST = __currentAST141;
			_t = __t141;
			_t = _t.getNextSibling();
			newExpression_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		returnAST = newExpression_AST;
		_retTree = _t;
	}
	
	public final void constant(AST _t) throws RecognitionException {
		
		AST constant_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST constant_AST = null;
		
		try {      // for error handling
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case NUM_INT:
			{
				AST tmp97_AST = null;
				AST tmp97_AST_in = null;
				tmp97_AST = astFactory.create((AST)_t);
				tmp97_AST_in = (AST)_t;
				astFactory.addASTChild(currentAST, tmp97_AST);
				match(_t,NUM_INT);
				_t = _t.getNextSibling();
				constant_AST = (AST)currentAST.root;
				break;
			}
			case STRING_LITERAL:
			{
				AST tmp98_AST = null;
				AST tmp98_AST_in = null;
				tmp98_AST = astFactory.create((AST)_t);
				tmp98_AST_in = (AST)_t;
				astFactory.addASTChild(currentAST, tmp98_AST);
				match(_t,STRING_LITERAL);
				_t = _t.getNextSibling();
				constant_AST = (AST)currentAST.root;
				break;
			}
			case NUM_FLOAT:
			{
				AST tmp99_AST = null;
				AST tmp99_AST_in = null;
				tmp99_AST = astFactory.create((AST)_t);
				tmp99_AST_in = (AST)_t;
				astFactory.addASTChild(currentAST, tmp99_AST);
				match(_t,NUM_FLOAT);
				_t = _t.getNextSibling();
				constant_AST = (AST)currentAST.root;
				break;
			}
			case NUM_DOUBLE:
			{
				AST tmp100_AST = null;
				AST tmp100_AST_in = null;
				tmp100_AST = astFactory.create((AST)_t);
				tmp100_AST_in = (AST)_t;
				astFactory.addASTChild(currentAST, tmp100_AST);
				match(_t,NUM_DOUBLE);
				_t = _t.getNextSibling();
				constant_AST = (AST)currentAST.root;
				break;
			}
			case NUM_LONG:
			{
				AST tmp101_AST = null;
				AST tmp101_AST_in = null;
				tmp101_AST = astFactory.create((AST)_t);
				tmp101_AST_in = (AST)_t;
				astFactory.addASTChild(currentAST, tmp101_AST);
				match(_t,NUM_LONG);
				_t = _t.getNextSibling();
				constant_AST = (AST)currentAST.root;
				break;
			}
			default:
			{
				throw new NoViableAltException(_t);
			}
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		returnAST = constant_AST;
		_retTree = _t;
	}
	
	public final void newArrayDeclarator(AST _t) throws RecognitionException {
		
		AST newArrayDeclarator_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST newArrayDeclarator_AST = null;
		
		try {      // for error handling
			AST __t143 = _t;
			AST tmp102_AST = null;
			AST tmp102_AST_in = null;
			tmp102_AST = astFactory.create((AST)_t);
			tmp102_AST_in = (AST)_t;
			astFactory.addASTChild(currentAST, tmp102_AST);
			ASTPair __currentAST143 = currentAST.copy();
			currentAST.root = currentAST.child;
			currentAST.child = null;
			match(_t,ARRAY_DECLARATOR);
			_t = _t.getFirstChild();
			{
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case ARRAY_DECLARATOR:
			{
				newArrayDeclarator(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case 3:
			case EXPR:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(_t);
			}
			}
			}
			{
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case EXPR:
			{
				expression(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case 3:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(_t);
			}
			}
			}
			currentAST = __currentAST143;
			_t = __t143;
			_t = _t.getNextSibling();
			newArrayDeclarator_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		returnAST = newArrayDeclarator_AST;
		_retTree = _t;
	}
	
	
	public static final String[] _tokenNames = {
		"<0>",
		"EOF",
		"<2>",
		"NULL_TREE_LOOKAHEAD",
		"BLOCK",
		"MODIFIERS",
		"OBJBLOCK",
		"SLIST",
		"CTOR_DEF",
		"METHOD_DEF",
		"VARIABLE_DEF",
		"INSTANCE_INIT",
		"STATIC_INIT",
		"TYPE",
		"CLASS_DEF",
		"INTERFACE_DEF",
		"PACKAGE_DEF",
		"ARRAY_DECLARATOR",
		"EXTENDS_CLAUSE",
		"IMPLEMENTS_CLAUSE",
		"PARAMETERS",
		"PARAMETER_DEF",
		"LABELED_STAT",
		"TYPECAST",
		"INDEX_OP",
		"POST_INC",
		"POST_DEC",
		"METHOD_CALL",
		"EXPR",
		"ARRAY_INIT",
		"IMPORT",
		"UNARY_MINUS",
		"UNARY_PLUS",
		"CASE_GROUP",
		"ELIST",
		"FOR_INIT",
		"FOR_CONDITION",
		"FOR_ITERATOR",
		"EMPTY_STAT",
		"\"final\"",
		"\"abstract\"",
		"\"strictfp\"",
		"SUPER_CTOR_CALL",
		"CTOR_CALL",
		"BOOLEAN",
		"DATE",
		"DATETIME",
		"TIME",
		"\"UndefinedDataType\"",
		"ARRAY_TYPE",
		"\"VarType\"",
		"NUMBER",
		"\"function\"",
		"IDENT",
		"LPAREN",
		"RPAREN",
		"SEMI",
		"COMMA",
		"\"var\"",
		"DOT",
		"STAR",
		"ASSIGN",
		"LBRACK",
		"RBRACK",
		"LCURLY",
		"RCURLY",
		"\"if\"",
		"\"else\"",
		"COLON",
		"\"for\"",
		"\"while\"",
		"\"do\"",
		"\"break\"",
		"\"continue\"",
		"\"return\"",
		"\"switch\"",
		"\"throw\"",
		"\"case\"",
		"\"default\"",
		"\"try\"",
		"\"finally\"",
		"\"catch\"",
		"PLUS_ASSIGN",
		"MINUS_ASSIGN",
		"STAR_ASSIGN",
		"DIV_ASSIGN",
		"MOD_ASSIGN",
		"SR_ASSIGN",
		"BSR_ASSIGN",
		"SL_ASSIGN",
		"BAND_ASSIGN",
		"BXOR_ASSIGN",
		"BOR_ASSIGN",
		"QUESTION",
		"LOR",
		"LAND",
		"BOR",
		"BXOR",
		"BAND",
		"NOT_EQUAL",
		"EQUAL",
		"LT",
		"GT",
		"LE",
		"GE",
		"SL",
		"SR",
		"BSR",
		"PLUS",
		"MINUS",
		"DIV",
		"MOD",
		"INC",
		"DEC",
		"BNOT",
		"LNOT",
		"\"class\"",
		"\"true\"",
		"\"false\"",
		"\"null\"",
		"\"new\"",
		"NUM_INT",
		"STRING_LITERAL",
		"NUM_FLOAT",
		"NUM_LONG",
		"NUM_DOUBLE",
		"WS",
		"SL_COMMENT",
		"ML_COMMENT",
		"ESC",
		"HEX_DIGIT",
		"VOCAB",
		"EXPONENT",
		"FLOAT_SUFFIX",
		"\"instanceof\""
	};
	
	private static final long[] mk_tokenSet_0() {
		long[] data = { 275150538368L, 40932L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());
	private static final long[] mk_tokenSet_1() {
		long[] data = { 275150537856L, 40932L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_1 = new BitSet(mk_tokenSet_1());
	}
	
