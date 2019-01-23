// $ANTLR 2.7.7 (20060906): "Jscript.tree.emitter.g" -> "JScriptEmitter.java"$

package com.tibco.xpd.script.parser.antlr;
import java.util.*;

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


public class JScriptEmitter extends antlr.TreeParser       implements JScriptEmitterTokenTypes
 {

    /** A big buffer to store output.  A single
     *  output buffer works because the order of
     *  rule calls mirrors the order in which we want
     *  to generate output for Java->Java.
     */
    protected StringBuffer output = null;

    /** Tab indent level 0..n */
    protected int tabs = 0;

    /** Track when do a newline; implies indent should happen next. */
    protected boolean lastOutputWasNewline = true;

    protected static final int MAX_TOKEN_TYPE=300; // imprecise but big enough

    /** Encodes precedence of various operators; indexed by token type.
     *  If precedence[op1] > precedence[op2] then op1 should happen
     *  before op2;
     */
    public static int precedence[] = new int[MAX_TOKEN_TYPE];

    static {
        for (int i=0; i<precedence.length; i++) {
            // anything but these operators binds super tight
            // for example METHOD_CALL binds tighter than PLUS
            precedence[i] = Integer.MAX_VALUE;
        }
        precedence[ASSIGN] = 1;
        precedence[PLUS_ASSIGN] = 1;
        precedence[MINUS_ASSIGN] = 1;
        precedence[STAR_ASSIGN] = 1;
        precedence[DIV_ASSIGN] = 1;
        precedence[MOD_ASSIGN] = 1;
        precedence[SR_ASSIGN] = 1;
        precedence[BSR_ASSIGN] = 1;
        precedence[SL_ASSIGN] = 1;
        precedence[BAND_ASSIGN] =1;
        precedence[BXOR_ASSIGN] = 1;
        precedence[BOR_ASSIGN] = 1;

        precedence[QUESTION] = 2;

        precedence[LOR] = 3;

        precedence[LAND] = 4;

        precedence[BOR] = 5;

        precedence[BXOR] = 6;

        precedence[BAND] = 7;

        precedence[NOT_EQUAL] = 8;
        precedence[NOT_EQUAL2] = 8;
        precedence[EQUAL] = 8;

        precedence[LT] = 9;
        precedence[GT] = 9;
        precedence[LE] = 9;
        precedence[GE] = 9;
        precedence[INSTANCEOF] = 9; // I *think* it's here

        precedence[SL] = 10;
        precedence[SR] = 10;
        precedence[BSR] = 10;

        precedence[PLUS] = 11;
        precedence[MINUS] = 11;

        precedence[DIV] = 12;
        precedence[MOD] = 12;
        precedence[STAR] = 12;

        precedence[INC] = 13;
        precedence[DEC] = 13;
        precedence[POST_INC] = 13;
        precedence[POST_DEC] = 13;
        precedence[BNOT] = 13;
        precedence[LNOT] = 13;
        precedence[UNARY_MINUS] = 13;
        precedence[UNARY_PLUS] = 13;
    }

    public static boolean hasHigherPrecedence(int op1, int op2) {
        return precedence[op1]>precedence[op2];
    }         

    protected void out(String text) {
        if ( lastOutputWasNewline ) {
            tab();
        }
        lastOutputWasNewline = false;
        output.append(text);
    }

    /** Indent to correct tab position */
    protected void tab() {
        for (int i=1; i<=tabs; i++) {
            output.append('\t');
        }
    }

    /** Indent one level */
    protected void indent() {
        tabs++;
    }

    /** Indent out one level */
    protected void undent() {
        tabs--;
    }

     protected void nl() {
        output.append('\n');
        lastOutputWasNewline = true;
    }    

    public JScriptEmitter(StringBuffer output) {
        this();
        this.output = output;
    } 

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
    private String scriptType;

  public String getScriptType() {
	return scriptType;
  }

 public void setScriptType(String scriptType) {
	this.scriptType = scriptType;
 }
	
private String translateIdentifier(String varName){
	    if(oldNewDataNameMap==null){
	        return varName;
	    }
	    String newName = oldNewDataNameMap.get(varName);
        if (newName == null) {
            newName = varName;
        }
        return newName;
	}
    
public JScriptEmitter() {
	tokenNames = _tokenNames;
}

	public final void compilationUnit(AST _t) throws RecognitionException {
		
		AST compilationUnit_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		
		try {      // for error handling
			sourceElements(_t);
			_t = _retTree;
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		_retTree = _t;
	}
	
	public final void sourceElements(AST _t) throws RecognitionException {
		
		AST sourceElements_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		
		try {      // for error handling
			{
			int _cnt4=0;
			_loop4:
			do {
				if (_t==null) _t=ASTNULL;
				if ((_tokenSet_0.member(_t.getType()))) {
					sourceElement(_t);
					_t = _retTree;
				}
				else {
					if ( _cnt4>=1 ) { break _loop4; } else {throw new NoViableAltException(_t);}
				}
				
				_cnt4++;
			} while (true);
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		_retTree = _t;
	}
	
	public final void sourceElement(AST _t) throws RecognitionException {
		
		AST sourceElement_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		
		try {      // for error handling
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case METHOD_DEF:
			{
				functionDeclaration(_t);
				_t = _retTree;
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
			case LITERAL_synchronized:
			{
				stat(_t);
				_t = _retTree;
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
		_retTree = _t;
	}
	
	public final void functionDeclaration(AST _t) throws RecognitionException {
		
		AST functionDeclaration_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		
		try {      // for error handling
			AST __t7 = _t;
			AST tmp1_AST_in = (AST)_t;
			match(_t,METHOD_DEF);
			_t = _t.getFirstChild();
			out("function ");
			methodHead(_t);
			_t = _retTree;
			{
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case SLIST:
			{
				slist(_t);
				_t = _retTree;
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
			_t = __t7;
			_t = _t.getNextSibling();
			
					out("}");	     
					nl();	           		
				
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		_retTree = _t;
	}
	
	public final void stat(AST _t) throws RecognitionException {
		
		AST stat_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		AST id = null;
		AST bid = null;
		AST cid = null;
		
		
			
		
		try {      // for error handling
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case VARIABLE_DEF:
			{
				variableDef(_t);
				_t = _retTree;
				break;
			}
			case EXPR:
			{
				expression(_t);
				_t = _retTree;
				
							{out(";"); nl();}
							
						
				break;
			}
			case LABELED_STAT:
			{
				AST __t53 = _t;
				AST tmp2_AST_in = (AST)_t;
				match(_t,LABELED_STAT);
				_t = _t.getFirstChild();
				id = (AST)_t;
				match(_t,IDENT);
				_t = _t.getNextSibling();
				out(id.getText()+":"); nl();
				stat(_t);
				_t = _retTree;
				_t = __t53;
				_t = _t.getNextSibling();
				break;
			}
			case LITERAL_if:
			{
				AST __t54 = _t;
				AST tmp3_AST_in = (AST)_t;
				match(_t,LITERAL_if);
				_t = _t.getFirstChild();
				out("if (");
				expression(_t);
				_t = _retTree;
				out(") ");
				stat(_t);
				_t = _retTree;
				{
				if (_t==null) _t=ASTNULL;
				switch ( _t.getType()) {
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
				case LITERAL_synchronized:
				{
					out("else ");
					stat(_t);
					_t = _retTree;
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
				_t = __t54;
				_t = _t.getNextSibling();
				break;
			}
			case LITERAL_for:
			{
				AST __t56 = _t;
				AST tmp4_AST_in = (AST)_t;
				match(_t,LITERAL_for);
				_t = _t.getFirstChild();
				out("for (");
				AST __t57 = _t;
				AST tmp5_AST_in = (AST)_t;
				match(_t,FOR_INIT);
				_t = _t.getFirstChild();
				{
				if (_t==null) _t=ASTNULL;
				switch ( _t.getType()) {
				case VARIABLE_DEF:
				{
					variableDef(_t);
					_t = _retTree;
					break;
				}
				case ELIST:
				{
					elist(_t);
					_t = _retTree;
					break;
				}
				case 3:
				{
					out(";");
					break;
				}
				default:
				{
					throw new NoViableAltException(_t);
				}
				}
				}
				_t = __t57;
				_t = _t.getNextSibling();
				AST __t59 = _t;
				AST tmp6_AST_in = (AST)_t;
				match(_t,FOR_CONDITION);
				_t = _t.getFirstChild();
				{
				if (_t==null) _t=ASTNULL;
				switch ( _t.getType()) {
				case EXPR:
				{
					expression(_t);
					_t = _retTree;
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
				_t = __t59;
				_t = _t.getNextSibling();
				out(";");
				AST __t61 = _t;
				AST tmp7_AST_in = (AST)_t;
				match(_t,FOR_ITERATOR);
				_t = _t.getFirstChild();
				{
				if (_t==null) _t=ASTNULL;
				switch ( _t.getType()) {
				case ELIST:
				{
					elist(_t);
					_t = _retTree;
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
				_t = __t61;
				_t = _t.getNextSibling();
				out(") ");
				stat(_t);
				_t = _retTree;
				_t = __t56;
				_t = _t.getNextSibling();
				break;
			}
			case LITERAL_while:
			{
				AST __t63 = _t;
				AST tmp8_AST_in = (AST)_t;
				match(_t,LITERAL_while);
				_t = _t.getFirstChild();
				out("while (");
				expression(_t);
				_t = _retTree;
				out(") ");
				stat(_t);
				_t = _retTree;
				_t = __t63;
				_t = _t.getNextSibling();
				break;
			}
			case LITERAL_do:
			{
				AST __t64 = _t;
				AST tmp9_AST_in = (AST)_t;
				match(_t,LITERAL_do);
				_t = _t.getFirstChild();
				out("do ");
				stat(_t);
				_t = _retTree;
				out(" while (");
				expression(_t);
				_t = _retTree;
				out(");"); nl();
				_t = __t64;
				_t = _t.getNextSibling();
				break;
			}
			case LITERAL_break:
			{
				AST __t65 = _t;
				AST tmp10_AST_in = (AST)_t;
				match(_t,LITERAL_break);
				_t = _t.getFirstChild();
				out("break ");
				{
				if (_t==null) _t=ASTNULL;
				switch ( _t.getType()) {
				case IDENT:
				{
					bid = (AST)_t;
					match(_t,IDENT);
					_t = _t.getNextSibling();
					out(bid.getText());
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
				_t = __t65;
				_t = _t.getNextSibling();
				out(";"); nl();
				break;
			}
			case LITERAL_continue:
			{
				AST __t67 = _t;
				AST tmp11_AST_in = (AST)_t;
				match(_t,LITERAL_continue);
				_t = _t.getFirstChild();
				out("continue ");
				{
				if (_t==null) _t=ASTNULL;
				switch ( _t.getType()) {
				case IDENT:
				{
					cid = (AST)_t;
					match(_t,IDENT);
					_t = _t.getNextSibling();
					out(cid.getText());
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
				_t = __t67;
				_t = _t.getNextSibling();
				out(";"); nl();
				break;
			}
			case LITERAL_return:
			{
				AST __t69 = _t;
				AST tmp12_AST_in = (AST)_t;
				match(_t,LITERAL_return);
				_t = _t.getFirstChild();
				out("return ");
				{
				if (_t==null) _t=ASTNULL;
				switch ( _t.getType()) {
				case EXPR:
				{
					expression(_t);
					_t = _retTree;
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
				_t = __t69;
				_t = _t.getNextSibling();
				out(";"); nl();
				break;
			}
			case LITERAL_switch:
			{
				AST __t71 = _t;
				AST tmp13_AST_in = (AST)_t;
				match(_t,LITERAL_switch);
				_t = _t.getFirstChild();
				out("switch (");
				expression(_t);
				_t = _retTree;
				out(") {"); nl(); indent();
				{
				_loop73:
				do {
					if (_t==null) _t=ASTNULL;
					if ((_t.getType()==CASE_GROUP)) {
						caseGroup(_t);
						_t = _retTree;
					}
					else {
						break _loop73;
					}
					
				} while (true);
				}
				undent(); out("}"); nl();
				_t = __t71;
				_t = _t.getNextSibling();
				break;
			}
			case LITERAL_throw:
			{
				AST __t74 = _t;
				AST tmp14_AST_in = (AST)_t;
				match(_t,LITERAL_throw);
				_t = _t.getFirstChild();
				out("throw ");
				expression(_t);
				_t = _retTree;
				_t = __t74;
				_t = _t.getNextSibling();
				out(";"); nl();
				break;
			}
			case LITERAL_synchronized:
			{
				AST __t75 = _t;
				AST tmp15_AST_in = (AST)_t;
				match(_t,LITERAL_synchronized);
				_t = _t.getFirstChild();
				out("synchronized (");
				expression(_t);
				_t = _retTree;
				out(") ");
				stat(_t);
				_t = _retTree;
				_t = __t75;
				_t = _t.getNextSibling();
				break;
			}
			case LITERAL_try:
			{
				tryBlock(_t);
				_t = _retTree;
				break;
			}
			case SLIST:
			{
				slist(_t);
				_t = _retTree;
				break;
			}
			case EMPTY_STAT:
			{
				AST tmp16_AST_in = (AST)_t;
				match(_t,EMPTY_STAT);
				_t = _t.getNextSibling();
				
							out(";");
						
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
		_retTree = _t;
	}
	
	public final void methodHead(AST _t) throws RecognitionException {
		
		AST methodHead_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		AST id = null;
		AST p = null;
		
		try {      // for error handling
			id = (AST)_t;
			match(_t,IDENT);
			_t = _t.getNextSibling();
			out(" "+id.getText()+"(");
			AST __t40 = _t;
			AST tmp17_AST_in = (AST)_t;
			match(_t,PARAMETERS);
			_t = _t.getFirstChild();
			{
			_loop42:
			do {
				if (_t==null) _t=ASTNULL;
				if ((_t.getType()==PARAMETER_DEF)) {
					p = _t==ASTNULL ? null : (AST)_t;
					functionParameterDef(_t);
					_t = _retTree;
					if (p.getNextSibling()!=null) out(",");
				}
				else {
					break _loop42;
				}
				
			} while (true);
			}
			_t = __t40;
			_t = _t.getNextSibling();
			out(") ");
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		_retTree = _t;
	}
	
	public final void slist(AST _t) throws RecognitionException {
		
		AST slist_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		AST sList = null;
		
		try {      // for error handling
			AST __t46 = _t;
			sList = _t==ASTNULL ? null :(AST)_t;
			match(_t,SLIST);
			_t = _t.getFirstChild();
			
				           	
				          out("{");nl();indent();
			
			{
			_loop48:
			do {
				if (_t==null) _t=ASTNULL;
				if ((_tokenSet_1.member(_t.getType()))) {
					stat(_t);
					_t = _retTree;
				}
				else {
					break _loop48;
				}
				
			} while (true);
			}
			undent(); out("}"); nl();
			_t = __t46;
			_t = _t.getNextSibling();
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		_retTree = _t;
	}
	
	public final void typeSpec(AST _t) throws RecognitionException {
		
		AST typeSpec_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		
		try {      // for error handling
			AST __t10 = _t;
			AST tmp18_AST_in = (AST)_t;
			match(_t,TYPE);
			_t = _t.getFirstChild();
			typeSpecArray(_t);
			_t = _retTree;
			_t = __t10;
			_t = _t.getNextSibling();
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		_retTree = _t;
	}
	
	public final void typeSpecArray(AST _t) throws RecognitionException {
		
		AST typeSpecArray_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		
		try {      // for error handling
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case ARRAY_DECLARATOR:
			{
				AST __t12 = _t;
				AST tmp19_AST_in = (AST)_t;
				match(_t,ARRAY_DECLARATOR);
				_t = _t.getFirstChild();
				typeSpecArray(_t);
				_t = _retTree;
				out("[]");
				_t = __t12;
				_t = _t.getNextSibling();
				break;
			}
			case IDENT:
			case LITERAL_var:
			case DOT:
			{
				type(_t);
				_t = _retTree;
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
		_retTree = _t;
	}
	
	public final void type(AST _t) throws RecognitionException {
		
		AST type_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		
		try {      // for error handling
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case IDENT:
			case DOT:
			{
				identifier(_t);
				_t = _retTree;
				out(" ");
				break;
			}
			case LITERAL_var:
			{
				builtInType(_t);
				_t = _retTree;
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
		_retTree = _t;
	}
	
	public final void identifier(AST _t) throws RecognitionException {
		
		AST identifier_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		AST id = null;
		AST id2 = null;
		
		try {      // for error handling
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case IDENT:
			{
				id = (AST)_t;
				match(_t,IDENT);
				_t = _t.getNextSibling();
				
							out(id.getText());
						
				break;
			}
			case DOT:
			{
				AST __t44 = _t;
				AST tmp20_AST_in = (AST)_t;
				match(_t,DOT);
				_t = _t.getFirstChild();
				identifier(_t);
				_t = _retTree;
				id2 = (AST)_t;
				match(_t,IDENT);
				_t = _t.getNextSibling();
				_t = __t44;
				_t = _t.getNextSibling();
				out("."+id2.getText());
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
		_retTree = _t;
	}
	
	public final void builtInType(AST _t) throws RecognitionException {
		
		AST builtInType_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		out(builtInType_AST_in.getText()+" ");
		
		try {      // for error handling
			AST tmp21_AST_in = (AST)_t;
			match(_t,LITERAL_var);
			_t = _t.getNextSibling();
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		_retTree = _t;
	}
	
	public final void objBlock(AST _t) throws RecognitionException {
		
		AST objBlock_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		
		try {      // for error handling
			AST __t16 = _t;
			AST tmp22_AST_in = (AST)_t;
			match(_t,OBJBLOCK);
			_t = _t.getFirstChild();
			{
			_loop20:
			do {
				if (_t==null) _t=ASTNULL;
				switch ( _t.getType()) {
				case VARIABLE_DEF:
				{
					variableDef(_t);
					_t = _retTree;
					break;
				}
				case STATIC_INIT:
				{
					AST __t18 = _t;
					AST tmp23_AST_in = (AST)_t;
					match(_t,STATIC_INIT);
					_t = _t.getFirstChild();
					out("static ");
					slist(_t);
					_t = _retTree;
					_t = __t18;
					_t = _t.getNextSibling();
					break;
				}
				case INSTANCE_INIT:
				{
					AST __t19 = _t;
					AST tmp24_AST_in = (AST)_t;
					match(_t,INSTANCE_INIT);
					_t = _t.getFirstChild();
					slist(_t);
					_t = _retTree;
					_t = __t19;
					_t = _t.getNextSibling();
					break;
				}
				default:
				{
					break _loop20;
				}
				}
			} while (true);
			}
			_t = __t16;
			_t = _t.getNextSibling();
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		_retTree = _t;
	}
	
	public final void variableDef(AST _t) throws RecognitionException {
		
		AST variableDef_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		
		try {      // for error handling
			AST __t22 = _t;
			AST tmp25_AST_in = (AST)_t;
			match(_t,VARIABLE_DEF);
			_t = _t.getFirstChild();
			typeSpec(_t);
			_t = _retTree;
			variableDeclarator(_t);
			_t = _retTree;
			varInitializer(_t);
			_t = _retTree;
			_t = __t22;
			_t = _t.getNextSibling();
			out(";"); nl();
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		_retTree = _t;
	}
	
	public final void variableDeclarator(AST _t) throws RecognitionException {
		
		AST variableDeclarator_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		AST id = null;
		
		try {      // for error handling
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case IDENT:
			{
				id = (AST)_t;
				match(_t,IDENT);
				_t = _t.getNextSibling();
				out(" "+id.getText());
				break;
			}
			case LBRACK:
			{
				AST tmp26_AST_in = (AST)_t;
				match(_t,LBRACK);
				_t = _t.getNextSibling();
				variableDeclarator(_t);
				_t = _retTree;
				out("[]");
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
		_retTree = _t;
	}
	
	public final void varInitializer(AST _t) throws RecognitionException {
		
		AST varInitializer_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		AST init = null;
		
		try {      // for error handling
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case ASSIGN:
			{
				AST __t33 = _t;
				AST tmp27_AST_in = (AST)_t;
				match(_t,ASSIGN);
				_t = _t.getFirstChild();
				out(" = ");
				init = _t==ASTNULL ? null : (AST)_t;
				initializer(_t);
				_t = _retTree;
				_t = __t33;
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
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		_retTree = _t;
	}
	
	public final void forLoopVariableDef(AST _t) throws RecognitionException {
		
		AST forLoopVariableDef_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		
		try {      // for error handling
			AST __t24 = _t;
			AST tmp28_AST_in = (AST)_t;
			match(_t,VARIABLE_DEF);
			_t = _t.getFirstChild();
			typeSpec(_t);
			_t = _retTree;
			variableDeclarator(_t);
			_t = _retTree;
			varInitializer(_t);
			_t = _retTree;
			_t = __t24;
			_t = _t.getNextSibling();
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		_retTree = _t;
	}
	
	public final void parameterDef(AST _t) throws RecognitionException {
		
		AST parameterDef_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		AST id = null;
		
		try {      // for error handling
			AST __t26 = _t;
			AST tmp29_AST_in = (AST)_t;
			match(_t,PARAMETER_DEF);
			_t = _t.getFirstChild();
			typeSpec(_t);
			_t = _retTree;
			id = (AST)_t;
			match(_t,IDENT);
			_t = _t.getNextSibling();
			out(" "+id.getText());
			_t = __t26;
			_t = _t.getNextSibling();
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		_retTree = _t;
	}
	
	public final void functionParameterDef(AST _t) throws RecognitionException {
		
		AST functionParameterDef_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		AST id = null;
		
		try {      // for error handling
			AST __t28 = _t;
			AST tmp30_AST_in = (AST)_t;
			match(_t,PARAMETER_DEF);
			_t = _t.getFirstChild();
			id = (AST)_t;
			match(_t,IDENT);
			_t = _t.getNextSibling();
			out(" "+id.getText());
			_t = __t28;
			_t = _t.getNextSibling();
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		_retTree = _t;
	}
	
	public final void objectinitializer(AST _t) throws RecognitionException {
		
		AST objectinitializer_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		
		try {      // for error handling
			AST __t30 = _t;
			AST tmp31_AST_in = (AST)_t;
			match(_t,INSTANCE_INIT);
			_t = _t.getFirstChild();
			slist(_t);
			_t = _retTree;
			_t = __t30;
			_t = _t.getNextSibling();
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		_retTree = _t;
	}
	
	public final void initializer(AST _t) throws RecognitionException {
		
		AST initializer_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		
		try {      // for error handling
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case EXPR:
			{
				expression(_t);
				_t = _retTree;
				break;
			}
			case ARRAY_INIT:
			{
				arrayInitializer(_t);
				_t = _retTree;
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
		_retTree = _t;
	}
	
	public final void expression(AST _t) throws RecognitionException {
		
		AST expression_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		
		try {      // for error handling
			AST __t94 = _t;
			AST tmp32_AST_in = (AST)_t;
			match(_t,EXPR);
			_t = _t.getFirstChild();
			expr(_t);
			_t = _retTree;
			_t = __t94;
			_t = _t.getNextSibling();
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		_retTree = _t;
	}
	
	public final void arrayInitializer(AST _t) throws RecognitionException {
		
		AST arrayInitializer_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		AST init = null;
		
		try {      // for error handling
			AST __t36 = _t;
			AST tmp33_AST_in = (AST)_t;
			match(_t,ARRAY_INIT);
			_t = _t.getFirstChild();
			out("[");indent();
			{
			_loop38:
			do {
				if (_t==null) _t=ASTNULL;
				if ((_t.getType()==EXPR||_t.getType()==ARRAY_INIT)) {
					init = _t==ASTNULL ? null : (AST)_t;
					initializer(_t);
					_t = _retTree;
					if (init.getNextSibling()!=null) out(", ");
				}
				else {
					break _loop38;
				}
				
			} while (true);
			}
			undent();out("]");
			_t = __t36;
			_t = _t.getNextSibling();
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		_retTree = _t;
	}
	
	public final void ifElseStat(AST _t) throws RecognitionException {
		
		AST ifElseStat_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		
		try {      // for error handling
			AST __t50 = _t;
			AST tmp34_AST_in = (AST)_t;
			match(_t,LITERAL_if);
			_t = _t.getFirstChild();
			out("if (");
			expression(_t);
			_t = _retTree;
			out(") ");
			slist(_t);
			_t = _retTree;
			{
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case SLIST:
			{
				out("else ");
				slist(_t);
				_t = _retTree;
				break;
			}
			case LITERAL_if:
			{
				ifElseStat(_t);
				_t = _retTree;
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
			_t = __t50;
			_t = _t.getNextSibling();
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		_retTree = _t;
	}
	
	public final void elist(AST _t) throws RecognitionException {
		
		AST elist_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		AST e = null;
		
		try {      // for error handling
			AST __t90 = _t;
			AST tmp35_AST_in = (AST)_t;
			match(_t,ELIST);
			_t = _t.getFirstChild();
			{
			_loop92:
			do {
				if (_t==null) _t=ASTNULL;
				if ((_t.getType()==EXPR)) {
					e = _t==ASTNULL ? null : (AST)_t;
					expression(_t);
					_t = _retTree;
					if (e.getNextSibling()!=null) out(",");
				}
				else {
					break _loop92;
				}
				
			} while (true);
			}
			_t = __t90;
			_t = _t.getNextSibling();
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		_retTree = _t;
	}
	
	public final void caseGroup(AST _t) throws RecognitionException {
		
		AST caseGroup_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		
		try {      // for error handling
			AST __t77 = _t;
			AST tmp36_AST_in = (AST)_t;
			match(_t,CASE_GROUP);
			_t = _t.getFirstChild();
			{
			int _cnt80=0;
			_loop80:
			do {
				if (_t==null) _t=ASTNULL;
				switch ( _t.getType()) {
				case LITERAL_case:
				{
					AST __t79 = _t;
					AST tmp37_AST_in = (AST)_t;
					match(_t,LITERAL_case);
					_t = _t.getFirstChild();
					out("case ");
					expression(_t);
					_t = _retTree;
					out(" :"); nl();
					_t = __t79;
					_t = _t.getNextSibling();
					break;
				}
				case LITERAL_default:
				{
					AST tmp38_AST_in = (AST)_t;
					match(_t,LITERAL_default);
					_t = _t.getNextSibling();
					out("default :"); nl();
					break;
				}
				default:
				{
					if ( _cnt80>=1 ) { break _loop80; } else {throw new NoViableAltException(_t);}
				}
				}
				_cnt80++;
			} while (true);
			}
			slist(_t);
			_t = _retTree;
			_t = __t77;
			_t = _t.getNextSibling();
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		_retTree = _t;
	}
	
	public final void tryBlock(AST _t) throws RecognitionException {
		
		AST tryBlock_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		
		try {      // for error handling
			AST __t82 = _t;
			AST tmp39_AST_in = (AST)_t;
			match(_t,LITERAL_try);
			_t = _t.getFirstChild();
			out("try ");
			slist(_t);
			_t = _retTree;
			{
			_loop84:
			do {
				if (_t==null) _t=ASTNULL;
				if ((_t.getType()==LITERAL_catch)) {
					handler(_t);
					_t = _retTree;
				}
				else {
					break _loop84;
				}
				
			} while (true);
			}
			{
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case LITERAL_finally:
			{
				AST __t86 = _t;
				AST tmp40_AST_in = (AST)_t;
				match(_t,LITERAL_finally);
				_t = _t.getFirstChild();
				out("finally ");
				slist(_t);
				_t = _retTree;
				_t = __t86;
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
			_t = __t82;
			_t = _t.getNextSibling();
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		_retTree = _t;
	}
	
	public final void handler(AST _t) throws RecognitionException {
		
		AST handler_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		
		try {      // for error handling
			AST __t88 = _t;
			AST tmp41_AST_in = (AST)_t;
			match(_t,LITERAL_catch);
			_t = _t.getFirstChild();
			out("catch (");
			parameterDef(_t);
			_t = _retTree;
			out(") ");
			slist(_t);
			_t = _retTree;
			_t = __t88;
			_t = _t.getNextSibling();
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		_retTree = _t;
	}
	
	public final void expr(AST _t) throws RecognitionException {
		
		AST expr_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		
		try {      // for error handling
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case QUESTION:
			{
				AST __t96 = _t;
				AST tmp42_AST_in = (AST)_t;
				match(_t,QUESTION);
				_t = _t.getFirstChild();
				expr(_t);
				_t = _retTree;
				out("?");
				expr(_t);
				_t = _retTree;
				out(":");
				expr(_t);
				_t = _retTree;
				_t = __t96;
				_t = _t.getNextSibling();
				break;
			}
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
			case LOR:
			case LAND:
			case BOR:
			case BXOR:
			case BAND:
			case NOT_EQUAL:
			case EQUAL:
			case NOT_EQUAL2:
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
			case INSTANCEOF:
			{
				{
				if (_t==null) _t=ASTNULL;
				switch ( _t.getType()) {
				case ASSIGN:
				{
					AST tmp43_AST_in = (AST)_t;
					match(_t,ASSIGN);
					_t = _t.getNextSibling();
					break;
				}
				case PLUS_ASSIGN:
				{
					AST tmp44_AST_in = (AST)_t;
					match(_t,PLUS_ASSIGN);
					_t = _t.getNextSibling();
					break;
				}
				case MINUS_ASSIGN:
				{
					AST tmp45_AST_in = (AST)_t;
					match(_t,MINUS_ASSIGN);
					_t = _t.getNextSibling();
					break;
				}
				case STAR_ASSIGN:
				{
					AST tmp46_AST_in = (AST)_t;
					match(_t,STAR_ASSIGN);
					_t = _t.getNextSibling();
					break;
				}
				case DIV_ASSIGN:
				{
					AST tmp47_AST_in = (AST)_t;
					match(_t,DIV_ASSIGN);
					_t = _t.getNextSibling();
					break;
				}
				case MOD_ASSIGN:
				{
					AST tmp48_AST_in = (AST)_t;
					match(_t,MOD_ASSIGN);
					_t = _t.getNextSibling();
					break;
				}
				case SR_ASSIGN:
				{
					AST tmp49_AST_in = (AST)_t;
					match(_t,SR_ASSIGN);
					_t = _t.getNextSibling();
					break;
				}
				case BSR_ASSIGN:
				{
					AST tmp50_AST_in = (AST)_t;
					match(_t,BSR_ASSIGN);
					_t = _t.getNextSibling();
					break;
				}
				case SL_ASSIGN:
				{
					AST tmp51_AST_in = (AST)_t;
					match(_t,SL_ASSIGN);
					_t = _t.getNextSibling();
					break;
				}
				case BAND_ASSIGN:
				{
					AST tmp52_AST_in = (AST)_t;
					match(_t,BAND_ASSIGN);
					_t = _t.getNextSibling();
					break;
				}
				case BXOR_ASSIGN:
				{
					AST tmp53_AST_in = (AST)_t;
					match(_t,BXOR_ASSIGN);
					_t = _t.getNextSibling();
					break;
				}
				case BOR_ASSIGN:
				{
					AST tmp54_AST_in = (AST)_t;
					match(_t,BOR_ASSIGN);
					_t = _t.getNextSibling();
					break;
				}
				case LOR:
				{
					AST tmp55_AST_in = (AST)_t;
					match(_t,LOR);
					_t = _t.getNextSibling();
					break;
				}
				case LAND:
				{
					AST tmp56_AST_in = (AST)_t;
					match(_t,LAND);
					_t = _t.getNextSibling();
					break;
				}
				case BOR:
				{
					AST tmp57_AST_in = (AST)_t;
					match(_t,BOR);
					_t = _t.getNextSibling();
					break;
				}
				case BXOR:
				{
					AST tmp58_AST_in = (AST)_t;
					match(_t,BXOR);
					_t = _t.getNextSibling();
					break;
				}
				case BAND:
				{
					AST tmp59_AST_in = (AST)_t;
					match(_t,BAND);
					_t = _t.getNextSibling();
					break;
				}
				case NOT_EQUAL:
				{
					AST tmp60_AST_in = (AST)_t;
					match(_t,NOT_EQUAL);
					_t = _t.getNextSibling();
					break;
				}
				case NOT_EQUAL2:
				{
					AST tmp61_AST_in = (AST)_t;
					match(_t,NOT_EQUAL2);
					_t = _t.getNextSibling();
					break;
				}
				case EQUAL:
				{
					AST tmp62_AST_in = (AST)_t;
					match(_t,EQUAL);
					_t = _t.getNextSibling();
					break;
				}
				case LT:
				{
					AST tmp63_AST_in = (AST)_t;
					match(_t,LT);
					_t = _t.getNextSibling();
					break;
				}
				case GT:
				{
					AST tmp64_AST_in = (AST)_t;
					match(_t,GT);
					_t = _t.getNextSibling();
					break;
				}
				case LE:
				{
					AST tmp65_AST_in = (AST)_t;
					match(_t,LE);
					_t = _t.getNextSibling();
					break;
				}
				case GE:
				{
					AST tmp66_AST_in = (AST)_t;
					match(_t,GE);
					_t = _t.getNextSibling();
					break;
				}
				case SL:
				{
					AST tmp67_AST_in = (AST)_t;
					match(_t,SL);
					_t = _t.getNextSibling();
					break;
				}
				case SR:
				{
					AST tmp68_AST_in = (AST)_t;
					match(_t,SR);
					_t = _t.getNextSibling();
					break;
				}
				case BSR:
				{
					AST tmp69_AST_in = (AST)_t;
					match(_t,BSR);
					_t = _t.getNextSibling();
					break;
				}
				case PLUS:
				{
					AST tmp70_AST_in = (AST)_t;
					match(_t,PLUS);
					_t = _t.getNextSibling();
					break;
				}
				case MINUS:
				{
					AST tmp71_AST_in = (AST)_t;
					match(_t,MINUS);
					_t = _t.getNextSibling();
					break;
				}
				case DIV:
				{
					AST tmp72_AST_in = (AST)_t;
					match(_t,DIV);
					_t = _t.getNextSibling();
					break;
				}
				case MOD:
				{
					AST tmp73_AST_in = (AST)_t;
					match(_t,MOD);
					_t = _t.getNextSibling();
					break;
				}
				case STAR:
				{
					AST tmp74_AST_in = (AST)_t;
					match(_t,STAR);
					_t = _t.getNextSibling();
					break;
				}
				case INSTANCEOF:
				{
					AST tmp75_AST_in = (AST)_t;
					match(_t,INSTANCEOF);
					_t = _t.getNextSibling();
					break;
				}
				default:
				{
					throw new NoViableAltException(_t);
				}
				}
				}
				
				AST op = expr_AST_in;
				AST left = op.getFirstChild();
				AST right = left.getNextSibling();
				boolean lp = false;
				boolean rp = false;
				if ( hasHigherPrecedence(op.getType(),left.getType()) ) {
				lp = true;
				}
				if ( hasHigherPrecedence(op.getType(),right.getType()) ) {
				rp = true;
				}
				if ( lp ) out("(");
				expr(left);
				if ( lp ) out(")");
				if ( op.getType()==INSTANCEOF ) {
				out(" "+op.getText()+" ");
				}
				else {
				out(op.getText());
				}
				if ( rp ) out("(");
				expr(right); // manually invoke
				if ( rp ) out(")");
				
				break;
			}
			case UNARY_MINUS:
			case UNARY_PLUS:
			case INC:
			case DEC:
			case BNOT:
			case LNOT:
			{
				{
				if (_t==null) _t=ASTNULL;
				switch ( _t.getType()) {
				case INC:
				{
					AST tmp76_AST_in = (AST)_t;
					match(_t,INC);
					_t = _t.getNextSibling();
					break;
				}
				case DEC:
				{
					AST tmp77_AST_in = (AST)_t;
					match(_t,DEC);
					_t = _t.getNextSibling();
					break;
				}
				case BNOT:
				{
					AST tmp78_AST_in = (AST)_t;
					match(_t,BNOT);
					_t = _t.getNextSibling();
					break;
				}
				case LNOT:
				{
					AST tmp79_AST_in = (AST)_t;
					match(_t,LNOT);
					_t = _t.getNextSibling();
					break;
				}
				case UNARY_MINUS:
				{
					AST tmp80_AST_in = (AST)_t;
					match(_t,UNARY_MINUS);
					_t = _t.getNextSibling();
					break;
				}
				case UNARY_PLUS:
				{
					AST tmp81_AST_in = (AST)_t;
					match(_t,UNARY_PLUS);
					_t = _t.getNextSibling();
					break;
				}
				default:
				{
					throw new NoViableAltException(_t);
				}
				}
				}
				
				AST op = expr_AST_in;
				AST opnd = op.getFirstChild();
				boolean p = false;
				if ( hasHigherPrecedence(op.getType(),opnd.getType()) ) {
				p = true;
				}
				out(op.getText());
				if ( p ) out("(");
				expr(opnd);
				if ( p ) out(")");
				
				break;
			}
			case POST_INC:
			{
				AST __t99 = _t;
				AST tmp82_AST_in = (AST)_t;
				match(_t,POST_INC);
				_t = _t.getFirstChild();
				expr(_t);
				_t = _retTree;
				out("++");
				_t = __t99;
				_t = _t.getNextSibling();
				break;
			}
			case POST_DEC:
			{
				AST __t100 = _t;
				AST tmp83_AST_in = (AST)_t;
				match(_t,POST_DEC);
				_t = _t.getFirstChild();
				expr(_t);
				_t = _retTree;
				out("--");
				_t = __t100;
				_t = _t.getNextSibling();
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
		_retTree = _t;
	}
	
	public final void primaryExpression(AST _t) throws RecognitionException {
		
		AST primaryExpression_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		AST id = null;
		AST id2 = null;
		AST id3 = null;
		
		try {      // for error handling
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case IDENT:
			{
				id = (AST)_t;
				match(_t,IDENT);
				_t = _t.getNextSibling();
				//out(id.getText());
						out(translateIdentifier(id.getText()));
						
				break;
			}
			case DOT:
			{
				AST __t102 = _t;
				AST tmp84_AST_in = (AST)_t;
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
				case NOT_EQUAL2:
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
				case INSTANCEOF:
				{
					expr(_t);
					_t = _retTree;
					out(".");
					{
					if (_t==null) _t=ASTNULL;
					switch ( _t.getType()) {
					case IDENT:
					{
						id2 = (AST)_t;
						match(_t,IDENT);
						_t = _t.getNextSibling();
						
												out(translateIdentifier(id2.getText()));
												//out(id2.getText());
											
						break;
					}
					case INDEX_OP:
					{
						arrayIndex(_t);
						_t = _retTree;
						break;
					}
					case LITERAL_class:
					{
						AST tmp85_AST_in = (AST)_t;
						match(_t,LITERAL_class);
						_t = _t.getNextSibling();
						out("class");
						break;
					}
					case LITERAL_new:
					{
						AST __t105 = _t;
						AST tmp86_AST_in = (AST)_t;
						match(_t,LITERAL_new);
						_t = _t.getFirstChild();
						id3 = (AST)_t;
						match(_t,IDENT);
						_t = _t.getNextSibling();
						out("new "+id3.getText());
						elist(_t);
						_t = _retTree;
						_t = __t105;
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
					AST __t106 = _t;
					AST tmp87_AST_in = (AST)_t;
					match(_t,ARRAY_DECLARATOR);
					_t = _t.getFirstChild();
					typeSpecArray(_t);
					_t = _retTree;
					_t = __t106;
					_t = _t.getNextSibling();
					AST tmp88_AST_in = (AST)_t;
					match(_t,LITERAL_class);
					_t = _t.getNextSibling();
					out("class");
					break;
				}
				case LITERAL_var:
				{
					builtInType(_t);
					_t = _retTree;
					AST tmp89_AST_in = (AST)_t;
					match(_t,LITERAL_class);
					_t = _t.getNextSibling();
					out("class");
					break;
				}
				default:
				{
					throw new NoViableAltException(_t);
				}
				}
				}
				_t = __t102;
				_t = _t.getNextSibling();
				break;
			}
			case INDEX_OP:
			{
				arrayIndex(_t);
				_t = _retTree;
				break;
			}
			case METHOD_CALL:
			{
				AST __t107 = _t;
				AST tmp90_AST_in = (AST)_t;
				match(_t,METHOD_CALL);
				_t = _t.getFirstChild();
				primaryExpression(_t);
				_t = _retTree;
				out("(");
				elist(_t);
				_t = _retTree;
				out(")");
				_t = __t107;
				_t = _t.getNextSibling();
				break;
			}
			case TYPECAST:
			{
				AST __t108 = _t;
				AST tmp91_AST_in = (AST)_t;
				match(_t,TYPECAST);
				_t = _t.getFirstChild();
				out("((");
				typeSpec(_t);
				_t = _retTree;
				out(")");
				expr(_t);
				_t = _retTree;
				out(")");
				_t = __t108;
				_t = _t.getNextSibling();
				break;
			}
			case LITERAL_new:
			{
				newExpression(_t);
				_t = _retTree;
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
				break;
			}
			case LITERAL_true:
			case LITERAL_false:
			case LITERAL_null:
			{
				{
				if (_t==null) _t=ASTNULL;
				switch ( _t.getType()) {
				case LITERAL_true:
				{
					AST tmp92_AST_in = (AST)_t;
					match(_t,LITERAL_true);
					_t = _t.getNextSibling();
					break;
				}
				case LITERAL_false:
				{
					AST tmp93_AST_in = (AST)_t;
					match(_t,LITERAL_false);
					_t = _t.getNextSibling();
					break;
				}
				case LITERAL_null:
				{
					AST tmp94_AST_in = (AST)_t;
					match(_t,LITERAL_null);
					_t = _t.getNextSibling();
					break;
				}
				default:
				{
					throw new NoViableAltException(_t);
				}
				}
				}
				out(primaryExpression_AST_in.getText());
				break;
			}
			case TYPE:
			{
				typeSpec(_t);
				_t = _retTree;
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
		_retTree = _t;
	}
	
	public final void arrayIndex(AST _t) throws RecognitionException {
		
		AST arrayIndex_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		
		try {      // for error handling
			AST __t111 = _t;
			AST tmp95_AST_in = (AST)_t;
			match(_t,INDEX_OP);
			_t = _t.getFirstChild();
			expr(_t);
			_t = _retTree;
			out("[");
			expression(_t);
			_t = _retTree;
			out("]");
			_t = __t111;
			_t = _t.getNextSibling();
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		_retTree = _t;
	}
	
	public final void newExpression(AST _t) throws RecognitionException {
		
		AST newExpression_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		
		try {      // for error handling
			AST __t114 = _t;
			AST tmp96_AST_in = (AST)_t;
			match(_t,LITERAL_new);
			_t = _t.getFirstChild();
			out("new ");
			type(_t);
			_t = _retTree;
			{
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case ELIST:
			{
				out("(");
				elist(_t);
				_t = _retTree;
				out(")");
				{
				if (_t==null) _t=ASTNULL;
				switch ( _t.getType()) {
				case OBJBLOCK:
				{
					out(" {"); nl(); indent();
					objBlock(_t);
					_t = _retTree;
					undent(); out("}"); nl();
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
			case ARRAY_DECLARATOR:
			{
				{
				int _cnt118=0;
				_loop118:
				do {
					if (_t==null) _t=ASTNULL;
					if ((_t.getType()==ARRAY_DECLARATOR)) {
						newArrayDeclarator(_t);
						_t = _retTree;
					}
					else {
						if ( _cnt118>=1 ) { break _loop118; } else {throw new NoViableAltException(_t);}
					}
					
					_cnt118++;
				} while (true);
				}
				{
				if (_t==null) _t=ASTNULL;
				switch ( _t.getType()) {
				case ARRAY_INIT:
				{
					arrayInitializer(_t);
					_t = _retTree;
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
			_t = __t114;
			_t = _t.getNextSibling();
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		_retTree = _t;
	}
	
	public final void constant(AST _t) throws RecognitionException {
		
		AST constant_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		out(constant_AST_in.getText());
		
		try {      // for error handling
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case NUM_INT:
			{
				AST tmp97_AST_in = (AST)_t;
				match(_t,NUM_INT);
				_t = _t.getNextSibling();
				break;
			}
			case STRING_LITERAL:
			{
				AST tmp98_AST_in = (AST)_t;
				match(_t,STRING_LITERAL);
				_t = _t.getNextSibling();
				break;
			}
			case NUM_FLOAT:
			{
				AST tmp99_AST_in = (AST)_t;
				match(_t,NUM_FLOAT);
				_t = _t.getNextSibling();
				break;
			}
			case NUM_DOUBLE:
			{
				AST tmp100_AST_in = (AST)_t;
				match(_t,NUM_DOUBLE);
				_t = _t.getNextSibling();
				break;
			}
			case NUM_LONG:
			{
				AST tmp101_AST_in = (AST)_t;
				match(_t,NUM_LONG);
				_t = _t.getNextSibling();
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
		_retTree = _t;
	}
	
	public final void newArrayDeclarator(AST _t) throws RecognitionException {
		
		AST newArrayDeclarator_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		
		try {      // for error handling
			AST __t121 = _t;
			AST tmp102_AST_in = (AST)_t;
			match(_t,ARRAY_DECLARATOR);
			_t = _t.getFirstChild();
			out("[");
			{
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case EXPR:
			{
				expression(_t);
				_t = _retTree;
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
			out("]");
			_t = __t121;
			_t = _t.getNextSibling();
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
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
		"NOT_EQUAL2",
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
		"\"synchronized\"",
		"INSTANCEOF"
	};
	
	private static final long[] mk_tokenSet_0() {
		long[] data = { 275150538368L, 40932L, 128L, 0L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());
	private static final long[] mk_tokenSet_1() {
		long[] data = { 275150537856L, 40932L, 128L, 0L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_1 = new BitSet(mk_tokenSet_1());
	}
	
