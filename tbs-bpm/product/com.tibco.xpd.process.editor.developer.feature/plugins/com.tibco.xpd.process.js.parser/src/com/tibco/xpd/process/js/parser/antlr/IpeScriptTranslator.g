header{
package com.tibco.xpd.process.js.parser.antlr;
import java.util.*;
import com.tibco.xpd.process.js.parser.transform.JsIpeTranslateUtil;
import com.tibco.xpd.process.js.parser.transform.TranslateUtil;
import com.tibco.xpd.script.parser.validator.ISymbolTable;
}
class IpeScriptTranslator extends TreeParser;


options {
	importVocab = JScript;	
	buildAST=true;
}
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

    private void handleNewExpression(AST expressionAST){
	JsIpeTranslateUtil.handleNewExpression(expressionAST);
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
	return this.symbolTable;
  }
  public void setSymbolTable(ISymbolTable sTable){
	this.symbolTable=sTable;
  }
    private Map<String, Map<String, String>> studioIPMTaskNameMap = new HashMap<String, Map<String, String>>();

    public void setStudioIPMTaskNameMap(
            Map<String, Map<String, String>> studioiPMTaskNameMap) {
        this.studioIPMTaskNameMap = studioiPMTaskNameMap;
    }

    public Map<String, Map<String, String>> getStudioIPMTaskNameMap() {
        return studioIPMTaskNameMap;
    }
}

compilationUnit:
	sourceElements			
	;

sourceElements:
	/*
	 * SPEC:
	 * sourceStatement
	 * | sourceElements sourceElement
	 */
	(sourceElement)+
	;	

sourceElement:
	functionDeclaration	
	|stat
	;
	
functionDeclaration:
	#(METHOD_DEF methodHead (slist)?)
	;

typeSpec:	
	#(TYPE typeSpecArray)
	;

typeSpecArray:	
		#( ARRAY_DECLARATOR typeSpecArray )
	|	type
	;

type:		
		identifier
	|	builtInType
	;

builtInType
    :   "var"
    ;

variableDef
	:	#(VARIABLE_DEF ts:typeSpec vd:variableDeclarator vi:varInitializer){TranslateUtil.massageVariableDefinition(#variableDef);}		
	;

// hack by Kam for making for loop work
forLoopVariableDef
	:	#(VARIABLE_DEF typeSpec variableDeclarator varInitializer)        
	;	

parameterDef
	:	#(PARAMETER_DEF IDENT )
	;

functionParameterDef
	:	#(PARAMETER_DEF IDENT )
	;

variableDeclarator
	:	id:IDENT 
	|	LBRACK variableDeclarator
	;

varInitializer
	:	#(ASSIGN initializer)
	|
	;

initializer
	:	expression
	|	arrayInitializer
	;

arrayInitializer
	:	#(ARRAY_INIT (initializer)*)
	;

methodHead
	:	IDENT #( PARAMETERS (functionParameterDef)* ) 
	;

identifier
	:	IDENT
	|	#( DOT identifier IDENT )
	;

slist
	:	#( SLIST (stat)* )
	;

ifElseStat:
	#("if" ifCondExpr:expression{TranslateUtil.handleBooleanExpression(#ifCondExpr,this);} slist (slist|ifElseStat)? ) 	
	;
stat:		variableDef
	|	statExpr:expression {TranslateUtil.handleBooleanExpression(#statExpr,this);}
	|	#(LABELED_STAT IDENT stat)
	|	ifElseStat
	|	#(	"for"
			#(FOR_INIT ((forLoopVariableDef)+ | elist)?)
			#(FOR_CONDITION (forCondExpr:expression{TranslateUtil.handleBooleanExpression(#forCondExpr,this);})?){TranslateUtil.addDummyBooleanExpression(#FOR_CONDITION,this);}
			#(FOR_ITERATOR (elist)?)
			stat
		)
	|	#(whileStat:"while" whileCondExpr:expression{TranslateUtil.handleBooleanExpression(#whileCondExpr,this);} whileBrace:stat)		
	|	#("do" doWhileCondExpr:expression{TranslateUtil.handleBooleanExpression(#doWhileCondExpr,this);} stat) 
	|	#("break" (IDENT)? )
	|	#("continue" (IDENT)? )
	|	returnStatement
	|	#("switch" expression (caseGroup)*)
	|	#("throw" expression)
	//|	#("synchronized" expression stat)
	|	tryBlock
	|	slist // nested SLIST
    // uncomment to make assert JDK 1.4 stuff work
    // |   #("assert" expression (expression)?)
	|	EMPTY_STAT
	;
returnStatement: #("return" (expression)?) {TranslateUtil.translateReturnStatement(#returnStatement,scriptType);}		  
		;

caseGroup
	:	#(CASE_GROUP (#("case" expression) | "default")+ slist)
	;

tryBlock
	:	#( "try" slist (handler)* (#("finally" slist))? )
	;

handler
	:	#( "catch" parameterDef slist )
	;

elist
	:	#( ELIST (expression)* )
	;

expression
	:	#(aa:EXPR expr){handleNewExpression(#aa);}
	;

expr:	        #(QUESTION expr expr expr)	// trinary operator
	|	#(ASSIGN expr expr)     // binary operators...
	|	#(pAssign:PLUS_ASSIGN expr expr){TranslateUtil.translateCompoundOperatorAssignment(#pAssign);}
	|	#(mAssign:MINUS_ASSIGN expr expr){TranslateUtil.translateCompoundOperatorAssignment(#mAssign);}
	|	#(sAssign:STAR_ASSIGN expr expr) {TranslateUtil.translateCompoundOperatorAssignment(#sAssign);}
	|	#(dAssign:DIV_ASSIGN expr expr) {TranslateUtil.translateCompoundOperatorAssignment(#dAssign);}
	|	#(MOD_ASSIGN expr expr) 
	|	#(SR_ASSIGN expr expr)
	|	#(BSR_ASSIGN expr expr)
	|	#(SL_ASSIGN expr expr)
	|	#(bandAssign:BAND_ASSIGN expr expr) {TranslateUtil.translateCompoundOperatorAssignment(#bandAssign);}
	|	#(bxorAssign:BXOR_ASSIGN expr expr) {TranslateUtil.translateCompoundOperatorAssignment(#bxorAssign);}
	|	#(borAssign:BOR_ASSIGN expr expr) {TranslateUtil.translateCompoundOperatorAssignment(#borAssign);}
	|	#(LOR expr expr)
	|	#(LAND expr expr)
	|	#(BOR expr expr)
	|	#(BXOR expr expr)
	|	#(BAND expr expr)
	|	#(NOT_EQUAL expr expr)
	|	#(EQUAL expr expr)
	|	#(LT expr expr)
	|	#(GT expr expr)
	|	#(LE expr expr)
	|	#(GE expr expr)
	|	#(SL expr expr)
	|	#(SR expr expr)
	|	#(BSR expr expr)
	|	#(PLUS expr expr)
	|	#(MINUS expr expr)
	|	#(DIV expr expr)
	|	#(MOD expr expr)
	|	#(STAR expr expr)
	|	#(INC expr)
	|	#(DEC expr)
	|	#(POST_INC expr)
	|	#(POST_DEC expr)
	|	#(BNOT expr)
	|	#(LNOT expr)
	|	#("instanceof" expr expr)
	|	#(UNARY_MINUS expr)
	|	#(UNARY_PLUS expr)
	|	primaryExpression
	;

primaryExpression
    :   id1:IDENT
    |   #(	DOT(expr	(
				id2:IDENT
				|	arrayIndex
				//|	"this"
				|	"class"
				|	#( "new" IDENT elist )
				//|   "super"
				)
			|	#(ARRAY_DECLARATOR typeSpecArray)
			|	builtInType ("class")?
			)
		)
	|	arrayIndex
	|	#(methodAST:METHOD_CALL primaryExpression elist){TranslateUtil.handleMethodParams(this,#methodAST);TranslateUtil.translateAttributeMethodCall(this,#methodAST);TranslateUtil.translateProcessNameMethodCall(this,#methodAST);TranslateUtil.translateTaskNameMethodCall(this,#methodAST);} 
	|	#(TYPECAST typeSpec expr)
	|   newExpression
	|   constant
    //|   "super"
    |   "true"
    |   "false"
    //|   "this"
    |   "null"
	|	typeSpec // type name used with instanceof
	;
arrayIndex
	:	#(INDEX_OP expr expression)
	;

constant
    :   NUM_INT
//    |   CHAR_LITERAL
    |   STRING_LITERAL
    |   NUM_FLOAT
    |   NUM_DOUBLE
    |   NUM_LONG
    ;

newExpression
	:	#("new" type elist)
	;

newArrayDeclarator
	:	#( ARRAY_DECLARATOR (newArrayDeclarator)? (expression)? )
	;
