header{
package com.tibco.xpd.script.parser.antlr;

import java.util.Collections;
import java.util.List;
import com.tibco.xpd.script.parser.validator.DefaultVarNameResolver;
import com.tibco.xpd.script.parser.validator.ISymbolTable;
import com.tibco.xpd.script.parser.validator.IValidationStrategy;
import com.tibco.xpd.script.parser.internal.validator.IVarNameResolver;
import com.tibco.xpd.script.parser.validator.SymbolTable;
import com.tibco.xpd.script.parser.internal.validator.ValidationUtil;
}
class JScriptParser extends Parser;
options {
	k = 2;                           // two token lookahead
	exportVocab=JScript;                // Call its vocabulary "Java"
//	codeGenMakeSwitchThreshold = 2;  // Some optimizations
//	codeGenBitsetTestThreshold = 3;
	defaultErrorHandler = false;     // Don't generate parser error handlers
	buildAST = true;
}

tokens {
	BLOCK; MODIFIERS; OBJBLOCK; SLIST; CTOR_DEF; METHOD_DEF; VARIABLE_DEF;
	INSTANCE_INIT; STATIC_INIT; TYPE; CLASS_DEF; INTERFACE_DEF;
	PACKAGE_DEF; ARRAY_DECLARATOR; EXTENDS_CLAUSE; IMPLEMENTS_CLAUSE;
	PARAMETERS; PARAMETER_DEF; LABELED_STAT; TYPECAST; INDEX_OP;
	POST_INC; POST_DEC; METHOD_CALL; EXPR; ARRAY_INIT;
	IMPORT; UNARY_MINUS; UNARY_PLUS; CASE_GROUP; ELIST; FOR_INIT; FOR_CONDITION;
	FOR_ITERATOR; EMPTY_STAT; FINAL="final"; ABSTRACT="abstract";
	STRICTFP="strictfp"; SUPER_CTOR_CALL; CTOR_CALL; BOOLEAN;DATE;DATETIME;TIME;
	UNDEFINE_DATA_TYPE="UndefinedDataType";ARRAY_TYPE;VAR_TYPE="VarType";NUMBER;
}

{
  private ISymbolTable symbolTable = null;
  private List<IValidationStrategy> validationStrategyList = null;
  private boolean shouldValidate = true;
  
  public ISymbolTable getSymbolTable(){
	  if(this.symbolTable==null){
		  this.symbolTable = new SymbolTable();
	  }
	return this.symbolTable;
  }

  public void setSymbolTable(ISymbolTable sTable){
	this.symbolTable=sTable;	
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
 protected void validateMethodCall(AST methodAST,Token token){
 	if (shouldValidate) {
		ValidationUtil.validateMethodCall(methodAST,token,this);
	}	
 }

 protected void validateUndefinedVariableUse(AST expression, Token token){
	 recordVariableUse(expression);
	 if (shouldValidate) {
	 	ValidationUtil.validateUndefinedVariableUse(this, expression, token);
	 }
 }
 private IVarNameResolver varNameResolver = null;
 public void setVarNameResolver(IVarNameResolver varNameResolver) {
	this.varNameResolver = varNameResolver;	
	initialiseValidationStrategy();
}
public IVarNameResolver getVarNameResolver() {
	if(varNameResolver==null){
		varNameResolver = new DefaultVarNameResolver();
	}
	return varNameResolver;
}
protected void initialiseValidationStrategy() {
        if (validationStrategyList != null) {
            for (IValidationStrategy validationStrategy : validationStrategyList) {
                validationStrategy.setScriptParser(this);
                if (varNameResolver != null) {
                    validationStrategy.setVarNameResolver(varNameResolver);
                }
            }
        }
    }

private List<String> supportedClassList = Collections.EMPTY_LIST;
public void setSupportedClasses(List<String> classList) {
        this.supportedClassList = classList;
}
 protected void recordVariableUse(AST expression){ 		
	List<String> variableNameList = getVarNameResolver().getVariableNameList(expression, supportedClassList);
	for (String strVarName : variableNameList) {
		getSymbolTable().recordVariableInUse(strVarName);
	}
	
 }

 protected void validateVariableDeclaration(AST varDefAST, Token token){
 	 if (shouldValidate) {
	 	ValidationUtil.validateVariableDeclaration(this, varDefAST, token);
	 }
 }

 protected void addLocalVariable(AST variableDeclaration, Token token){
	 	ValidationUtil.addLocalVariable(variableDeclaration, this, token);
 }

 protected void validateMethodDeclaration(AST methodDefinition,Token token){
	if (shouldValidate) {
		ValidationUtil.validateMethodDeclaration(this,methodDefinition, token);
	}	 
 }

  protected void validateNewExpression(AST newExpressionAST,Token token){
	if (shouldValidate) {
		ValidationUtil.validateNewExpression(newExpressionAST, token,this);
	}	 
 }
 protected void validateConditionalExpr(AST expressionAST,Token token){
	if (shouldValidate) {
		ValidationUtil.validateConditionalExpression(expressionAST, token,this);
	}	 
}

protected void validateExpression(AST expressionAST, Token token){
	if (shouldValidate) {
		ValidationUtil.validateExpression(this, expressionAST, token);
	}	 
}

protected void validateStatement(AST statAST,Token token){
	if (shouldValidate) {
		ValidationUtil.validateStatement(this, statAST, token);
	}	 
}
protected void validateTreeAST(Token token){
	if (shouldValidate) {
		ValidationUtil.validateTreeAST(this,token);
	}	 
}

public final void compilationUnit(boolean validate) throws RecognitionException, TokenStreamException {
    shouldValidate = validate;
    compilationUnit();
}
}



// Compilation Unit: In Java, this is a single file.  This is the start
//   rule for this parser
compilationUnit:	
		sourceElements
		EOF!{validateTreeAST(LT(1));}		
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
	fd:functionDeclaration {validateMethodDeclaration(#fd,LT(1));}	 
	| statAST:statement{validateStatement(#statAST,LT(1));}			
	;			

functionDeclaration!:
	"function" IDENT LPAREN! param:parameterDeclarationList RPAREN!		
			( 
				s2:compoundStatement 
				| SEMI 
			)
			{				
				#functionDeclaration = #(#[METHOD_DEF,"METHOD_DEF"],IDENT,param,s2);
				
			}
		
	;
// A list of formal parameters
parameterDeclarationList
	:	( parameterDeclaration ( COMMA! parameterDeclaration )* )?
		{#parameterDeclarationList = #(#[PARAMETERS,"PARAMETERS"],
									#parameterDeclarationList);}
	;
// A formal parameter.
parameterDeclaration!
	:	id:IDENT 
		{#parameterDeclaration = #(#[PARAMETER_DEF,"PARAMETER_DEF"], id);}
	;

/** A declaration is the creation of a reference or primitive-type variable
 *  Create a separate Type/Var tree for each var in the var list.
 *  This variable declaration is without the var identifier
 */
declarationWithoutVar!
	{AST t = astFactory.create(LITERAL_var);
t.setText("HCvar");}
	: 	v:variableDefinitions[#t]
		{#declarationWithoutVar = #v;
		 validateUndefinedVariableUse(#declarationWithoutVar,LT(1));}
	;

/** A declaration is the creation of a reference or primitive-type variable
 *  Create a separate Type/Var tree for each var in the var list.
 */
declaration!
	:	t:builtInTypeSpec[false] v:variableDefinitions[#t]
		{#declaration = #v;
		 validateUndefinedVariableUse(#declaration,LT(1));}
	;

// A builtin type specification is a builtin type with possible brackets
// afterwards (which would make it an array type).
builtInTypeSpec[boolean addImagNode]
	:	builtInType 
		{
			if ( addImagNode ) {
				#builtInTypeSpec = #(#[TYPE,"TYPE"], #builtInTypeSpec);
			}
		}
	;

// A type name. which is either a (possibly qualified) class name or
//   a primitive (builtin) type
type
	:	identifier
	|	builtInType
	;

// The primitive types.
builtInType
	: "var"
	;

// A (possibly-qualified) java identifier.  We start with the first IDENT
//   and expand its name by adding dots and following IDENTS
identifier
	:	IDENT  ( DOT^ IDENT )* 
	;

identifierStar
	:	IDENT
		( DOT^ IDENT )*
		( DOT^ STAR  )?
	;

variableDefinitions[AST t]
	:	variableDeclarator[getASTFactory().dupTree(t)]
		(	COMMA!
			variableDeclarator[getASTFactory().dupTree(t)]
		)*
	;

/** Declaration of a variable.  This can be a class/instance variable,
 *   or a local variable in a method
 * It can also include possible initialization.
 */
variableDeclarator![AST t]
	:	id:IDENT d:declaratorBrackets[t] v:varInitializer
		{	#variableDeclarator = #(#[VARIABLE_DEF,"VARIABLE_DEF"], #(#[TYPE,"TYPE"],d), id, v);		  
			validateVariableDeclaration(#variableDeclarator,LT(1));		  
			addLocalVariable(#variableDeclarator,LT(1));
		}		  
	;
	
declaratorBrackets[AST typ]
	:	{#declaratorBrackets=typ;}
		//(lb:LBRACK^ {#lb.setType(ARRAY_DECLARATOR);} RBRACK!)*
	;

varInitializer
	:	( ASSIGN^ initializer )?
	;

// This is an initializer used to set up an array.
arrayInitializer
	:	lc:LBRACK^ {#lc.setType(ARRAY_INIT);}
			(	initializer
				(
					// CONFLICT: does a COMMA after an initializer start a new
					//           initializer or start the option ',' at end?
					//           ANTLR generates proper code by matching
					//			 the comma as soon as possible.
					options {
						warnWhenFollowAmbig = false;
					}
				:
					COMMA! initializer
				)*
				(COMMA!)?
			)?
		RBRACK!
	;


// The two "things" that can initialize an array element are an expression
//   and another (nested) array initializer.
initializer
	:	expression
	|	arrayInitializer
	;

// Compound statement.  This is used in many contexts:
//   Inside a class definition prefixed with "static":
//      it is a class initializer
//   Inside a class definition without "static":
//      it is an instance initializer
//   As the body of a method
//   As a completely indepdent braced block of code inside a method
//      it starts a new scope for variable definitions

compoundStatement
	:	lc:LCURLY^ {#lc.setType(SLIST);}
			// include the (possibly-empty) list of statements
			(statAST:statement{validateStatement(#statAST,LT(1));})*
		RCURLY!
	;
ifElseStatement:
		"if"^ LPAREN! exprAST:expression{validateConditionalExpr(#exprAST,LT(1));} RPAREN! compoundStatement 
		(
			// CONFLICT: the old "dangling-else" problem...
			//           ANTLR generates proper code matching
			//			 as soon as possible.  Hush warning.
			options {
				warnWhenFollowAmbig = false;
			}
		:
			 "else"! (compoundStatement | ifElseStatement)
			 
		)?
		;

statement
	// A list of statements in curly braces -- start a new scope!
	:	compoundStatement

	// declarations are ambiguous with "ID DOT" relative to expression
	// statements.  Must backtrack to be sure.  Could use a semantic
	// predicate to test symbol table to see what the type was coming
	// up, but that's pretty hard without a symbol table ;)
	|	(declaration)=> declaration SEMI!

	// An expression statement.  This could be a method call,
	// assignment statement, or any other expression evaluated for
	// side-effects.
	|	expr:expression SEMI! {validateExpression(#expr,LT(0));}

	// If-else statement
	|      ifElseStatement

	// Attach a label to the front of a statement
	|	IDENT c:COLON^ {#c.setType(LABELED_STAT);} statement

	// For statement
	|	"for"^
			LPAREN!
				forInit SEMI!   // initializer
				forCond	SEMI!   // condition test
				forIter         // updater
			RPAREN!
			compoundStatement                     // statement to loop over

	// While statement
	|	"while"^ LPAREN! whileExpression:expression{validateConditionalExpr(#whileExpression,LT(1));} RPAREN! compoundStatement

	// do-while statement
	|! doLiteral:"do" doStatement:compoundStatement "while" LPAREN doExpression:expression{validateConditionalExpr(#doExpression,LT(1));} RPAREN SEMI {#statement = #(doLiteral, doExpression, doStatement);}
	
//  |	"do"^ statement "while"! LPAREN! expression RPAREN! SEMI!

	// get out of a loop (or switch)
	|	"break"^ (IDENT)? SEMI!

	// do next iteration of a loop
	|	"continue"^ (IDENT)? SEMI!

	// Return an expression
	|	"return"^ (exprReturn:expression{validateExpression(#exprReturn,LT(0));})? SEMI!

	// switch/case statement
	|	switchAST:"switch"^ LPAREN! exprSwitch:expression{validateExpression(#exprSwitch,LT(0));} RPAREN! LCURLY!
			( casesGroup )*
		RCURLY! 

	// exception try-catch block
	|	tryAST:tryBlock

	// throw an exception
	|	throwAST:"throw"^ exprThrow:expression{validateExpression(#exprThrow,LT(0));} SEMI!

	// synchronize a statement, Kamlesh:20/04/2007:Not Supported
//	|	"synchronized"^ LPAREN! expression RPAREN! compoundStatement

	// asserts (uncomment if you want 1.4 compatibility) Kamlesh:20/04/2007:Not Supported
	// |	"assert"^ expression ( COLON! expression )? SEMI!

	// empty statement
	|	s:SEMI {#s.setType(EMPTY_STAT);}
	;

casesGroup
	:	(	// CONFLICT: to which case group do the statements bind?
			//           ANTLR generates proper code: it groups the
			//           many "case"/"default" labels together then
			//           follows them with the statements
			options {
				greedy = true;
			}
			:
			aCase
		)+
		caseSList
		{#casesGroup = #([CASE_GROUP, "CASE_GROUP"], #casesGroup);}
	;

aCase
	:	("case"^ expr:expression{validateExpression(#expr,LT(0));} | "default") COLON!
	;

caseSList
	:	(statement)*
		{#caseSList = #(#[SLIST,"SLIST"],#caseSList);}
	;

// The initializer for a for loop
forInit
		// if it looks like a declaration, it is
	:	(	(declaration)=> declaration
		// otherwise it could be an expression list...
		|	expressionList
		)?
		{#forInit = #(#[FOR_INIT,"FOR_INIT"],#forInit);}
	;

forCond
	:	(exprAST:expression{validateConditionalExpr(#exprAST,LT(1));})?
		{#forCond = #(#[FOR_CONDITION,"FOR_CONDITION"],#forCond);}
	;

forIter
	:	(expressionList)?
		{#forIter = #(#[FOR_ITERATOR,"FOR_ITERATOR"],#forIter);}
	;

// an exception handler try/catch block
tryBlock
	:	"try"^ compoundStatement
		(handler)*
		( finallyClause )?
	;

finallyClause
	:	"finally"^ compoundStatement
	;

// an exception handler
handler
	:	"catch"^ LPAREN! parameterDeclaration RPAREN! compoundStatement
	;


// expressions
// Note that most of these expressions follow the pattern
//   thisLevelExpression :
//       nextHigherPrecedenceExpression
//           (OPERATOR nextHigherPrecedenceExpression)*
// which is a standard recursive definition for a parsing an expression.
// The operators in java have the following precedences:
//    lowest  (13)  = *= /= %= += -= <<= >>= >>>= &= ^= |=
//            (12)  ?:
//            (11)  ||
//            (10)  &&
//            ( 9)  |
//            ( 8)  ^
//            ( 7)  &
//            ( 6)  == !=
//            ( 5)  < <= > >=
//            ( 4)  << >>
//            ( 3)  +(binary) -(binary)
//            ( 2)  * / %
//            ( 1)  ++ -- +(unary) -(unary)  ~  !  (type)
//                  []   () (method call)  . (dot -- identifier qualification)
//                  new   ()  (explicit parenthesis)
//
// the last two are not usually on a precedence chart; I put them in
// to point out that new has a higher precedence than '.', so you
// can validy use
//     new Frame().show()
//
// Note that the above precedence levels map to the rules below...
// Once you have a precedence chart, writing the appropriate rules as below
//   is usually very straightfoward



// the mother of all expressions
expression
	:	assignExpr:assignmentExpression
		{
		#expression = #(#[EXPR,"EXPR"],#expression);
		//validateUndefinedVariableUse(#expression,LT(1));				
		}
	;


// This is a list of expressions.
expressionList
	:	expr1:expression{validateExpression(#expr1,LT(0));} (COMMA! expr2:expression{validateExpression(#expr2,LT(0));})*
		{#expressionList = #(#[ELIST,"ELIST"], expressionList);}
	;


// assignment expression (level 13)
assignmentExpression
	:	condExpr:conditionalExpression{validateUndefinedVariableUse(#condExpr,LT(1));}
		(
			(	
				ASSIGN^
			    |   PLUS_ASSIGN^
			    |   MINUS_ASSIGN^
			    |   STAR_ASSIGN^
			    |   DIV_ASSIGN^
			    |   MOD_ASSIGN^
			    |   SR_ASSIGN^
			    |   BSR_ASSIGN^
			    |   SL_ASSIGN^
			    |   BAND_ASSIGN^
			    |   BXOR_ASSIGN^
			    |   BOR_ASSIGN^
		        )
			assignExpr:assignmentExpression
		)?		
			{				
				validateUndefinedVariableUse(#assignExpr,LT(0));	
				//validateExpression(#assignmentExpression,LT(0));
			}
	;


// conditional test (level 12)
conditionalExpression
	:	logicalOrExpression
		( QUESTION^ assignmentExpression COLON! conditionalExpression )?
	;


// logical or (||)  (level 11)
logicalOrExpression
	:	logicalAndExpression (LOR^ logicalAndExpression)*
	;


// logical and (&&)  (level 10)
logicalAndExpression
	:	inclusiveOrExpression (LAND^ inclusiveOrExpression)*
	;


// bitwise or non-short-circuiting or (|)  (level 9)
inclusiveOrExpression
	:	exclusiveOrExpression (BOR^ exclusiveOrExpression)*
	;


// exclusive or (^)  (level 8)
exclusiveOrExpression
	:	andExpression (BXOR^ andExpression)*
	;


// bitwise or non-short-circuiting and (&)  (level 7)
andExpression
	:	equalityExpression (BAND^ equalityExpression)*
	;


// equality/inequality (==/!=) (level 6)
equalityExpression
	:	relationalExpression ((NOT_EQUAL^ | EQUAL^ | NOT_EQUAL2^) relationalExpression)*
	;


// boolean relational expressions (level 5)
relationalExpression
	:	shiftExpression
		(	(	(	LT^
				|	GT^
				|	LE^
				|	GE^
				)
				shiftExpression
			)*		
		)
	;


// bit shift expressions (level 4)
shiftExpression
	:	additiveExpression ((SL^ | SR^ | BSR^) additiveExpression)*
	;


// binary addition/subtraction (level 3)
additiveExpression
	:	multiplicativeExpression ((PLUS^ | MINUS^) multiplicativeExpression)*
	;


// multiplication/division/modulo (level 2)
multiplicativeExpression
	:	unaryExpression ((STAR^ | DIV^ | MOD^ ) unaryExpression)*
	;

unaryExpression
	:	INC^ unaryExpression
	|	DEC^ unaryExpression
	|	MINUS^ {#MINUS.setType(UNARY_MINUS);} unaryExpression
	|	PLUS^  {#PLUS.setType(UNARY_PLUS);} unaryExpression
	|	unaryExpressionNotPlusMinus
	;

unaryExpressionNotPlusMinus
	:	BNOT^ unaryExpression
	|	LNOT^ unaryExpression
	|	postfixExpression
	;

// qualified names, array expressions, method invocation, post inc/dec
postfixExpression
	:	primaryExpression(
					DOT^ IDENT
					(
						lp:LPAREN^ {#lp.setType(METHOD_CALL);}
						argList
						RPAREN!	{validateMethodCall(#lp,LT(1));}
					)?
					|	DOT^ newExpression
					|	lb:LBRACK^ {#lb.setType(INDEX_OP);} expression RBRACK!
					/*
					kamlesh:20/04/2007:Not supported this
					|	DOT^ "this"
					*/
					/*
					kamlesh:20/04/2007:Not supported new Outer()).super()
					|	DOT^ "super" 
						(   
							// (new Outer()).super()  (create enclosing instance)
							lp3:LPAREN^ argList RPAREN! {#lp3.setType(SUPER_CTOR_CALL);}
							|	DOT^ IDENT (lps:LPAREN^ {#lps.setType(METHOD_CALL);} argList RPAREN! {validateMethodCall(#lps,LT(1));} )? 
						)	   
					*/					
				)*

				(
						// possibly add on a post-increment or post-decrement.
						// allows INC/DEC on too much, but semantics can check
						in:INC^ {#in.setType(POST_INC);}
	 				|	de:DEC^ {#de.setType(POST_DEC);}
				)?
 	;

// the basic element of an expression
primaryExpression
	:	identPrimary ( options {greedy=true;} : DOT^ "class" )?
	|       constant
	|	"true"
	|	"false"
	|	"null"
	|	newExpression
	|	LPAREN! assignmentExpression RPAREN!
	/*
	|	"this"
	|	"super"
		kamlesh:20/04/2007:Not supported int.class and int[].class
		// look for int.class and int[].class
	|	builtInType
		( lbt:LBRACK^ {#lbt.setType(ARRAY_DECLARATOR);} RBRACK! )*
		DOT^ "class"
	*/
	;

/** Match a, a.b.c refs, a.b.c(...) refs, a.b.c[], a.b.c[].class,
 *  and a.b.c.class refs.  Also this(...) and super(...).  Match
 *  this or super.
 */
identPrimary
	:	IDENT
		(
			options {
				// .ident could match here or in postfixExpression.
				// We do want to match here.  Turn off warning.
				greedy=true;
			}: DOT^ IDENT
		)*
		(
			options {
				// ARRAY_DECLARATOR here conflicts with INDEX_OP in
				// postfixExpression on LBRACK RBRACK.
				// We want to match [] here, so greedy.  This overcomes
				// limitation of linear approximate lookahead.
				greedy=true;
			}:   
				( 
				lp:LPAREN^ {#lp.setType(METHOD_CALL);} argList RPAREN! ){validateMethodCall(#lp,LT(1));}
				|	
				( 
					options {greedy=true;} :
					lbc:LBRACK^ {#lbc.setType(ARRAY_DECLARATOR);} RBRACK!
				)+
		)?
    ;

/** object instantiation.
 *  Trees are built as illustrated by the following input/tree pairs:
 *
 *  new T()
 *
 *  new
 *   |
 *   T --  ELIST
 *           |
 *          arg1 -- arg2 -- .. -- argn
 *
 *  new int[]
 *
 *  new
 *   |
 *  int -- ARRAY_DECLARATOR
 *
 *  new int[] {1,2}
 *
 *  new
 *   |
 *  int -- ARRAY_DECLARATOR -- ARRAY_INIT
 *                                  |
 *                                EXPR -- EXPR
 *                                  |      |
 *                                  1      2
 *
 *  new int[3]
 *  new
 *   |
 *  int -- ARRAY_DECLARATOR
 *                |
 *              EXPR
 *                |
 *                3
 *
 *  new int[1][2]
 *
 *  new
 *   |
 *  int -- ARRAY_DECLARATOR
 *               |
 *         ARRAY_DECLARATOR -- EXPR
 *               |              |
 *             EXPR             1
 *               |
 *               2
 *
 */
newExpression
	:	newEXPR:"new"^ type
		 (	LPAREN! argList RPAREN!		 	
		 )	{validateNewExpression(#newEXPR,LT(1));}	
	;

argList
	:	(	expressionList
		|	/*nothing*/
			{#argList = #[ELIST,"ELIST"];}
		)
	;

constant
	:	NUM_INT
//	|	CHAR_LITERAL
	|	STRING_LITERAL
	|	NUM_FLOAT
	|	NUM_LONG
	|	NUM_DOUBLE
	;


//----------------------------------------------------------------------------
// The Java scanner
//----------------------------------------------------------------------------
class JScriptLexer extends Lexer;

options {
	exportVocab=JScript;      // call the vocabulary "Java"
	testLiterals=false;    // don't automatically test for literals
	k=4;                   // four characters of lookahead
	charVocabulary='\u0003'..'\uFFFF';
	// without inlining some bitset tests, couldn't do unicode;
	// I need to make ANTLR generate smaller bitsets; see
	// bottom of JavaLexer.java
	codeGenBitsetTestThreshold=20;
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


// OPERATORS
QUESTION		:	'?'		;
LPAREN			:	'('		;
RPAREN			:	')'		;
LBRACK			:	'['		;
RBRACK			:	']'		;
LCURLY			:	'{'		;
RCURLY			:	'}'		;
COLON			:	':'		;
COMMA			:	','		;
//DOT			    :	'.'		;
ASSIGN			:	'='		;
EQUAL			:	"=="	;
LNOT			:	'!'		;
BNOT			:	'~'		;
NOT_EQUAL		:	"!="	;
NOT_EQUAL2		:	"!=="	;
DIV				:	'/'		;
DIV_ASSIGN		:	"/="	;
PLUS			:	'+'		;
PLUS_ASSIGN		:	"+="	;
INC				:	"++"	;
MINUS			:	'-'		;
MINUS_ASSIGN	:	"-="	;
DEC				:	"--"	;
STAR			:	'*'		;
STAR_ASSIGN		:	"*="	;
MOD				:	'%'		;
MOD_ASSIGN		:	"%="	;
SR				:	">>"	;
SR_ASSIGN		:	">>="	;
BSR				:	">>>"	;
BSR_ASSIGN		:	">>>="	;
GE				:	">="	;
GT				:	">"		;
SL				:	"<<"	;
SL_ASSIGN		:	"<<="	;
LE				:	"<="	;
LT				:	'<'		;
BXOR			:	'^'		;
BXOR_ASSIGN		:	"^="	;
BOR				:	'|'		;
BOR_ASSIGN		:	"|="	;
LOR				:	"||"	;
BAND			:	'&'		;
BAND_ASSIGN		:	"&="	;
LAND			:	"&&"	;
SEMI			:	';'		;


// Whitespace -- ignored
WS	:	(	' '
		|	'\t'
		|	'\f'
			// handle newlines
		|	(	options {generateAmbigWarnings=false;}
			:	"\r\n"  // Evil DOS
			|	'\r'    // Macintosh
			|	'\n'    // Unix (the right way)
			)
			{ newline(); }
		)+
		{ _ttype = Token.SKIP; }
	;

// Single-line comments
SL_COMMENT
	:	"//"(~('\n'|'\r'){if (isInfiniteLoop()){break;}})* ('\n'|'\r'('\n')?)
		{$setType(Token.SKIP); newline();}
	;

// multiple-line comments
ML_COMMENT
	:	"/*"
		(	/*	'\r' '\n' can be matched in one alternative or by matching
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
			{ LA(2)!='/' }? '*'
		|	'\r' '\n'		{newline();}
		|	'\r'			{newline();}
		|	'\n'			{newline();}
		|	~('*'|'\n'|'\r')	{if (isInfiniteLoop()){break;}}
		)*
		"*/"
		{$setType(Token.SKIP);}
	;

/*
// character literals
CHAR_LITERAL
	:	'\'' ( ESC | ~('\''|'\n'|'\r'|'\\') ) '\''
	;
*/
// string literals
STRING_LITERAL
	:	'"' (ESC|~('"'|'\\'|'\n'|'\r'))* '"' 
		| '\'' (ESC|~('\''|'\\'|'\n'|'\r'))* '\'' 
	;


// escape sequence -- note that this is protected; it can only be called
//   from another lexer rule -- it will not ever directly return a token to
//   the parser
// There are various ambiguities hushed in this rule.  The optional
// '0'...'9' digit matches should be matched here rather than letting
// them go back to STRING_LITERAL to be matched.  ANTLR does the
// right thing by matching immediately; hence, it's ok to shut off
// the FOLLOW ambig warnings.
protected
ESC
	:	'\\'
		(	'n'
		|	'r'
		|	't'
		|	'b'
		|	'f'
		|	'"'
		|	'\''
		|	'\\'
		|	('u')+ HEX_DIGIT HEX_DIGIT HEX_DIGIT HEX_DIGIT
		|	'0'..'3'
			(
				options {
					warnWhenFollowAmbig = false;
				}
			:	'0'..'7'
				(
					options {
						warnWhenFollowAmbig = false;
					}
				:	'0'..'7'
				)?
			)?
		|	'4'..'7'
			(
				options {
					warnWhenFollowAmbig = false;
				}
			:	'0'..'7'
			)?
		)
	;


// hexadecimal digit (again, note it's protected!)
protected
HEX_DIGIT
	:	('0'..'9'|'A'..'F'|'a'..'f')
	;


// a dummy rule to force vocabulary to be all characters (except special
//   ones that ANTLR uses internally (0 to 2)
protected
VOCAB
	:	'\3'..'\377'
	;


// an identifier.  Note that testLiterals is set to true!  This means
// that after we match the rule, we look in the literals table to see
// if it's a literal or really an identifer
IDENT
	options {testLiterals=true;}
	:	('a'..'z'|'A'..'Z'|'_'|'$'|'@') ('a'..'z'|'A'..'Z'|'_'|'0'..'9'|'$'|':')*
		//('a'..'z'|'A'..'Z'|'_'|'$') ('a'..'z'|'A'..'Z'|'_'|'0'..'9'|'$')*
	;


// a numeric literal
NUM_INT
	{boolean isDecimal=false; Token t=null;}
    :   '.' {_ttype = DOT;}
            (	('0'..'9')+ (EXPONENT)? (f1:FLOAT_SUFFIX {t=f1;})?
                {
				if (t != null && t.getText().toUpperCase().indexOf('F')>=0) {
                	_ttype = NUM_FLOAT;
				}
				else {
                	_ttype = NUM_DOUBLE; // assume double
				}
				}
            )?

	|	(	'0' {isDecimal = true;} // special case for just '0'
			(	('x'|'X')
				(											// hex
					// the 'e'|'E' and float suffix stuff look
					// like hex digits, hence the (...)+ doesn't
					// know when to stop: ambig.  ANTLR resolves
					// it correctly by matching immediately.  It
					// is therefor ok to hush warning.
					options {
						warnWhenFollowAmbig=false;
					}
				:	HEX_DIGIT
				)+

			|	//float or double with leading zero
				(('0'..'9')+ ('.'|EXPONENT|FLOAT_SUFFIX)) => ('0'..'9')+

			|	('0'..'9')+									// octal
			)?
		|	('1'..'9') ('0'..'9')*  {isDecimal=true;}		// non-zero decimal
		)
		(	('l'|'L') { _ttype = NUM_LONG; }

		// only check to see if it's a float if looks like decimal so far
		|	{isDecimal}?
            (   '.' ('0'..'9')* (EXPONENT)? (f2:FLOAT_SUFFIX {t=f2;})?
            |   EXPONENT (f3:FLOAT_SUFFIX {t=f3;})?
            |   f4:FLOAT_SUFFIX {t=f4;}
            )
            {
			if (t != null && t.getText().toUpperCase() .indexOf('F') >= 0) {
                _ttype = NUM_FLOAT;
			}
            else {
	           	_ttype = NUM_DOUBLE; // assume double
			}
			}
        )?
	;


// a couple protected methods to assist in matching floating point numbers
protected
EXPONENT
	:	('e'|'E') ('+'|'-')? ('0'..'9')+
	;


protected
FLOAT_SUFFIX
	:	'f'|'F'|'d'|'D'
	;

