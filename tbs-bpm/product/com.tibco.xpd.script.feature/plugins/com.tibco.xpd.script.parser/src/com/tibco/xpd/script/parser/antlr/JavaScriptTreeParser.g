header{
package com.tibco.xpd.script.parser.antlr;
}
class JavaScriptTreeParser extends TreeParser;


options {
	importVocab = JScript;	
	buildAST=true;
}


compilationUnit:
	sourceElements		
	{}
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
	| stat
	;
	
functionDeclaration:
	#(METHOD_DEF methodHead (slist)?)
	;

typeSpec
	:	#(TYPE typeSpecArray)
	;

typeSpecArray
	:	#( ARRAY_DECLARATOR typeSpecArray )
	|	type
	;

type:	identifier
	|	builtInType
	;

builtInType
    :   "var"
    ;

variableDef
	:	#(VARIABLE_DEF ts:typeSpec vd:variableDeclarator vi:varInitializer)		
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
	#("if" expression slist (slist|ifElseStat)? ) 	
	;

stat:		variableDef
	|	expression
	|	#(LABELED_STAT IDENT stat)
	|	ifElseStat
	|	#(	"for"
			#(FOR_INIT ((forLoopVariableDef)+ | elist)?)
			#(FOR_CONDITION (expression)?)
			#(FOR_ITERATOR (elist)?)
			stat
		)
	|	#(whileStat:"while" expression whileBrace:stat)
		{
			
		}
	|	#("do" expression stat) 
	|	#("break" (IDENT)? )
	|	#("continue" (IDENT)? )
	|	#("return" (expression)? )
	|	#("switch" expression (caseGroup)*)
	|	#("throw" expression)
//	|	#("synchronized" expression stat)
	|	tryBlock
	|	slist // nested SLIST
    // uncomment to make assert JDK 1.4 stuff work
    // |   #("assert" expression (expression)?)
	|	EMPTY_STAT
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
	:  #(EXPR expr)
	;

expr:	#(QUESTION expr expr expr)	// trinary operator
	|	#(ASSIGN expr expr)     // binary operators...
	|	#(PLUS_ASSIGN expr expr)
	|	#(MINUS_ASSIGN expr expr)
	|	#(STAR_ASSIGN expr expr)
	|	#(DIV_ASSIGN expr expr)
	|	#(MOD_ASSIGN expr expr)
	|	#(SR_ASSIGN expr expr)
	|	#(BSR_ASSIGN expr expr)
	|	#(SL_ASSIGN expr expr)
	|	#(BAND_ASSIGN expr expr)
	|	#(BXOR_ASSIGN expr expr)
	|	#(BOR_ASSIGN expr expr)
	|	#(LOR expr expr)
	|	#(LAND expr expr)
	|	#(BOR expr expr)
	|	#(BXOR expr expr)
	|	#(BAND expr expr)
	|	#(NOT_EQUAL expr expr)
	|	#(NOT_EQUAL2 expr expr)
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
	|	#(methodAST:METHOD_CALL primaryExpression elist) 
	|	#(TYPECAST typeSpec expr)
	|   newExpression
	|   constant
//    |   "super"
    |   "true"
    |   "false"
//    |   "this"
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
