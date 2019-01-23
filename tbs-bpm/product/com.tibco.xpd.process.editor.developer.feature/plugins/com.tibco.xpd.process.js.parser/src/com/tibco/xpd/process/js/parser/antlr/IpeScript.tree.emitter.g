header{
package com.tibco.xpd.process.js.parser.antlr;
import java.util.*;
import com.tibco.xpd.process.js.parser.transform.TranslateUtil;
import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.script.parser.antlr.JScriptTokenTypes;
import com.tibco.xpd.script.parser.util.ParseUtil;
import com.tibco.xpd.script.parser.validator.ISymbolTable;
import com.tibco.xpd.process.js.parser.transform.CodeBlock;
import com.tibco.xpd.process.js.parser.transform.TranslateConsts;
}
class IpeScriptEmitter extends TreeParser;

options {
	importVocab = JScript;
	}

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
        boolean toReturn = precedence[op1] > precedence[op2];
        if (!toReturn) {
	    // fix for MR 37179 and SR 1-998OI3
            if (isLogicalOprerator(op1) && isLogicalOprerator(op2)) {
                toReturn = true;
            }
        }
        return toReturn;
    }         
    
    /**
     * Method introduced to fix fix for MR 37179 and SR 1-998OI3
     * 
     * @param operator
     * @return
     */
    private static boolean isLogicalOprerator(int operator) {
        boolean toReturn = false;
        if (JScriptTokenTypes.BOR == operator
                || JScriptTokenTypes.LOR == operator
                || JScriptTokenTypes.BAND == operator
                || JScriptTokenTypes.LAND == operator) {
            toReturn = true;
        }
        return toReturn;
    }
    private void lineStart() {
        if (isCurrentBlockCommented()) {            
            commentLine=true;            
        }
    }       

    private static boolean commentLine = false;        
    
    private static boolean commentCode = false;
    private static boolean forLoopIterator = false;
    private static boolean conditionalExpression = false;
    
    private StringBuffer lineOutput = new StringBuffer();    
    
    private void insertGeneratedStatement(boolean prependNewLine,
            boolean appendNewLine) {
        CodeBlock block = getCurrentCodeBlock();
        ArrayList list = block.getAppendCode();
        printBlockCode(prependNewLine, list, appendNewLine);
        block.clearAppendCode();
    }
    /**
    * This method deals with k-- and k++ cases. It will replace k := k+1
    */
    private boolean handlePostPrefixOperator(AST postAST,String incrementDecrementStr){
        boolean toReturn = false;
        AST firstChildAST = postAST.getFirstChild();               
        if(firstChildAST.getType() == JScriptTokenTypes.IDENT){
            boolean bool = TranslateUtil.isProcessDataField(firstChildAST,getProcessDataField()); 
            String prefixLine = "";
            if(!bool){
                prefixLine = ";";
            }
            CodeBlock block = getCurrentCodeBlock();
            String firstChildText = firstChildAST.getText();                                                
            if(forLoopIterator){                    
                out(firstChildText +" := "+ firstChildText + incrementDecrementStr);                             
            }else if(conditionalExpression){
            	out(firstChildText);
            	block.appendCode(prefixLine+firstChildText +" := " +firstChildText+incrementDecrementStr);                                                        
            }else{
            	block.appendCode(prefixLine+firstChildText +" := " +firstChildText+incrementDecrementStr);
            }   
            toReturn = true;
        }
        return toReturn;
    }
    
    /**
     * This method is used to insert line comment ';' before a line 
     * which needs to be commented out. Used in the case of FOR_ITERATOR.
     */
    private void insertLineComment(){
    	if (commentLine) {
            String str = lineOutput.toString();
            String[] stringArr = str.split(",");
            String lastString = stringArr[stringArr.length - 1];
            String modifiedLastString = ";" + lastString;
            int lastComma = str.lastIndexOf(',');
            lineOutput.replace(lastComma+1, lineOutput.length(),
                    modifiedLastString);
            commentLine = false;
        }
    }

    private void printBlockCode(boolean prependNewLine, ArrayList list,
            boolean appendNewLine) {
        if (list == null) {
            return;
        }
        if (prependNewLine) {
            nl();
        }        
        for (int index = 0; index < list.size(); index++) {
            String element = (String) list.get(index);
            lineStart();
            out(element);
            if(index!=list.size()-1){
                nl();
            }
        }
        if (appendNewLine) {
            nl();
        }
    }

    private void insertBlockEndCode(boolean prependNewLine,
            boolean appendNewLine) {
        CodeBlock block = getCurrentCodeBlock();
        ArrayList list = block.getBlockEndCode();
        printBlockCode(prependNewLine, list, appendNewLine);
        block.clearBlockEndCode();
    } 
    
    private boolean isCurrentBlockCommented(){
    	boolean toReturn = false;
    	if(!blockStack.empty()){    		
    		CodeBlock block = getCurrentCodeBlock();
    		toReturn = block.isBlockToBeCommented();    		
    	}
    	return toReturn;
    }
    
    private void commentCurrentBlock(){
    	if(!blockStack.empty()){
    		CodeBlock block = getCurrentCodeBlock();
			block.commentBlock(true);				
		}
    }
    
  private ISymbolTable symbolTable = null;
  public ISymbolTable getSymbolTable(){
	return this.symbolTable;
  }
  public void setSymbolTable(ISymbolTable sTable){
	this.symbolTable=sTable;
  }
  private List<String> destinationList = null;
  public void setProcessDestination(List<String> destinationList){
	this.destinationList = destinationList;
  }
  public List<String> getProcessDestination(){
	return this.destinationList;
  }
  private String scriptType;

  public String getScriptType() {
	return scriptType;
  }

 public void setScriptType(String scriptType) {
	this.scriptType = scriptType;
 }
   
    
/**
* @return the processDataField
*/
 public Map<String,IScriptRelevantData> getProcessDataField() {
     return getSymbolTable().getScriptRelevantDataTypeMap();
 }


    
    
    private Stack blockStack = new Stack(); 
    
    private CodeBlock getCurrentCodeBlock(){
    	CodeBlock block = (CodeBlock)blockStack.peek();
    	return block;
    	
    }
        
    private static boolean commentBlock = false;

    protected void out(String text) {
        if (lastOutputWasNewline) {
            tab();
        }
        lastOutputWasNewline = false;        
        lineOutput.append(text);
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
    	boolean commentedBlock = isCurrentBlockCommented();
        if (commentLine || commentedBlock) {
            output.append(";");
            commentLine = false;
        }
        output.append(lineOutput);
        lineOutput = new StringBuffer();
        output.append('\n');
        lastOutputWasNewline = true;
    }    

    public IpeScriptEmitter(StringBuffer output) {
        this();
        this.output = output;
    } 

    private Map<String,String> oldNewDataNameMap;
    
    public void setOldNewProcessDataMap(Map<String,String> oldNewDataNameMap){
    	this.oldNewDataNameMap = oldNewDataNameMap;
    }
    public Map<String,String> getOldNewProcessDataMap(){
    	return this.oldNewDataNameMap;
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
	| 
	{			
			blockStack.push(new CodeBlock(TranslateConsts.METHOD_BLOCK));			
	}
	stat
	{		
   		nl();	           		
   		blockStack.pop();
	}
	;
	
functionDeclaration:
	{			
			blockStack.push(new CodeBlock(TranslateConsts.METHOD_BLOCK));			
			commentCurrentBlock();
	}
	#(METHOD_DEF {out("function ");} methodHead (slist)?)
	{
		out("}");	     
   		nl();	           		
   		blockStack.pop();
	}
	;
		
typeSpec
	:	#(TYPE typeSpecArray)
	;

typeSpecArray
	:	#( ARRAY_DECLARATOR typeSpecArray {commentLine = true;out("[]");} )
	|	type
	;

type:	identifier {out(" ");}
	|	builtInType
	;

builtInType
{	String corrKeyWord = TranslateUtil.getCorrespondingKeyWord(#builtInType.getText());
	if(corrKeyWord!=null){
		out(corrKeyWord+" ");
	}else{
		out(#builtInType.getText()+" ");
	}
}
    :   "var"
    ;
	
variableDef
	:	#(VARIABLE_DEF
		{	           
            commentLine =true;
		} 
		ts:typeSpec 
		{
		AST firstChild = #ts.getFirstChild();
		if(firstChild!=null){
		    String fcText = firstChild.getText();
		    if(fcText.equals("HCvar")){
		        commentLine =false;
		    }		    
		}		  
		}
		variableDeclarator varInitializer)
        {   
        	nl();
        	insertGeneratedStatement(false,false);        	        	        	
        }
	;

// hack by Kam for making for loop work
forLoopVariableDef
	:	#(VARIABLE_DEF
		{			
			commentLine =true;
		} 
		typeSpec variableDeclarator varInitializer)
        {   
        	insertGeneratedStatement(false,false);        	        	        	
        }
	;	

parameterDef
	:	#(PARAMETER_DEF id:IDENT {out(" "+#id.getText());} )
	;

functionParameterDef
	:	#(PARAMETER_DEF id:IDENT {out(" "+#id.getText());} )
	;

variableDeclarator
	:	id:IDENT {out(" "+id.getText());}
	|	LBRACK variableDeclarator {out("[]");}
	;

varInitializer
	:	#(ASSIGN {out(" := ");} init:initializer)
	|
	;

initializer
	:	expression
	|	arrayInitializer
	;

arrayInitializer
	:	#(  ARRAY_INIT
            {out("[");indent();}
            (   init:initializer
                {if (init.getNextSibling()!=null) out(", ");}
            )*
            {undent();out("]");}
         )
	;

methodHead
	:	id:IDENT {out(" "+id.getText()+"(");}
        #( PARAMETERS
            (   p:functionParameterDef
                {if (#p.getNextSibling()!=null) out(",");}
            )*
        )
        {out(") ");}        
	;

identifier
    :   id:IDENT {
    			String corrKeyWord = TranslateUtil.getCorrespondingKeyWord(id.getText());
	        	if(corrKeyWord!=null){
	        		out(corrKeyWord);
	        	}else{
    				out(id.getText());
    			}
    		}
    |   #( DOT identifier id2:IDENT ) {out("."+id2.getText());}
    ;

slist
	:	#( sList:SLIST
           {
	           	if (!blockStack.empty()) {
				CodeBlock block = getCurrentCodeBlock();
				String blockName = block.getBlockName();
				if (blockName.equals(TranslateConsts.IF_STATEMENT)
					|| blockName.equals(TranslateConsts.WHILE_STATEMENT)
					|| blockName.equals(TranslateConsts.METHOD_BLOCK)
					|| blockName.equals(TranslateConsts.FOR_STATEMENT)
					|| blockName.equals(TranslateConsts.DO_WHILE_STATEMENT)) {
					out(" ");
				}
			} else {
				out("{");
			}
           		nl(); 
           		indent();
           	}
           (stat)*
           {
           		undent();
           		if (!blockStack.empty()) {
				CodeBlock block = getCurrentCodeBlock();
				String blockName = block.getBlockName();
				if (blockName.equals(TranslateConsts.IF_STATEMENT)
						|| blockName.equals(TranslateConsts.IF_ELSE_STATEMENT)
						|| blockName.equals(TranslateConsts.FOR_STATEMENT)
						|| blockName.equals(TranslateConsts.WHILE_STATEMENT)
						|| blockName.equals(TranslateConsts.METHOD_BLOCK)
						|| blockName.equals(TranslateConsts.DO_WHILE_STATEMENT)) {

				} else if (blockName.equals(TranslateConsts.IF_ELSEIF_STATEMENT)) {
					out("ELSE");
				} else {
					out("}//unidentified code block");
				}
			} else {
				out("}");
			}            	
           	}
         )
	;
ifElseStat:
	#(ifStat:"if"
		{			
			boolean blockToComment = false;
			AST exprAST = #ifStat.getFirstChild();			
			List<Integer> methodASTList = new ArrayList<Integer>();
			methodASTList.add(JScriptTokenTypes.METHOD_CALL);
			List<AST> methodCall = ParseUtil.getFirstLevelChildren(exprAST,methodASTList);
			boolean isTrue = TranslateUtil.isMethodCallListFromSupportedClass(this,methodCall);
			if(!isTrue){
				commentLine=true;								
				blockToComment = true;				
			}
			out("IF ");
			TranslateUtil.handleIfBlock(#ifStat,lineOutput,blockStack);				
			if(blockToComment){
				commentCurrentBlock();
			}
		} 
		condExpr:expression 
			{				
				out(" ");
				indent();
				insertGeneratedStatement(true,false);
				undent();
				CodeBlock cBlock = getCurrentCodeBlock();					
			} 
		trueBlock:slist ( 
			{
				out("ELSE");												
				// clearing identifiers to ignore list
				CodeBlock elseBlock = getCurrentCodeBlock();
				elseBlock.clearIdentifierToIgnore();			
				elseBlock.setBlockName(TranslateConsts.IF_STATEMENT);
				AST elseAST = trueBlock.getNextSibling();
				if(elseAST!=null){
					int elseASTType = elseAST.getType(); 
					if(!(elseASTType == JScriptTokenTypes.SLIST || elseASTType == JScriptTokenTypes.LITERAL_if)){
						nl();                          
					}                                        
				}
			} 
		slist|ifElseStat)? )
			{
				CodeBlock block = getCurrentCodeBlock();				
				if(block.getBlockName().equals(TranslateConsts.IF_STATEMENT)){										
					if(block.getChildBlockCount()==0){
						out("ENDIF");
						nl();					
                				blockStack.pop();                    	
		                	}else{                		
						block.decrementChildBlockCount();
		                	}
				}
			}
	;

stat {

	 }
:	variableDef 
	|	expression 
		{
			nl();
			insertGeneratedStatement(false,true);						
			
		}
	|	#(LABELED_STAT id:IDENT {out(id.getText()+":"); nl();} stat)
	|	ifElseStat
	|	#(	"for" 				
			#(FOR_INIT (forLoopVariableDef {out(";");}| elist | 
			{
				out(";");
				nl();
			}))
			{
				nl();
				out("WHILE ");
				CodeBlock forBlock = new CodeBlock(TranslateConsts.FOR_STATEMENT);
            			CodeBlock parentBlock = getCurrentCodeBlock();
				forBlock.setParentBlock(parentBlock);			
				blockStack.push(forBlock);
			}
			#(FOR_CONDITION {conditionalExpression = true;}(expression)?)
            {
		conditionalExpression = false;
            	//out(";");            	
            	nl();
            }
			#(FOR_ITERATOR {forLoopIterator=true;}(elist)?)
            {	
                CodeBlock currentBlock = getCurrentCodeBlock();
            	String str = lineOutput.toString();                
                String[] strings = str.split(",");
                for (int index = 0; index < strings.length; index++) {                	
                    currentBlock.appendBlockEndCode(strings[index]);  
                }                
                lineOutput = new StringBuffer();                
                forLoopIterator = false;
            }
			stat
			{		
							
			}
		)
		{
			indent();
			insertGeneratedStatement(true,true);
			insertBlockEndCode(false,true);					
			undent();					
			blockStack.pop();	
			out("WEND");
			nl();
		}
	|	#(whileStat:"while" 
		{
			out("WHILE ");
			AST whileAST = #whileStat.getFirstChild().getNextSibling();
			if(whileAST!=null && whileAST.getText().equals("{")){				
				CodeBlock parentBlock = getCurrentCodeBlock();
				CodeBlock whileBlock = new CodeBlock(TranslateConsts.WHILE_STATEMENT);
				whileBlock.setParentBlock(parentBlock);					
				blockStack.push(whileBlock);
			}
			conditionalExpression = true;
		} 
		expression {conditionalExpression = false;out(" ");indent();insertGeneratedStatement(true,false);undent();} stat)		
		{
			blockStack.pop();
			out("WEND");
			nl();
		}
	|	#(doWhileStat:"do" {
				out("WHILE ");
				AST doWhileAST = #doWhileStat.getFirstChild().getNextSibling();
				if(doWhileAST!=null && doWhileAST.getText().equals("{")){				
					CodeBlock parentBlock = getCurrentCodeBlock();
					CodeBlock doWhileBlock = new CodeBlock(TranslateConsts.DO_WHILE_STATEMENT);
					doWhileBlock.setParentBlock(parentBlock);					
					blockStack.push(doWhileBlock);
				}
				conditionalExpression = true;
			}	 
			expression {conditionalExpression = false;out(" ");indent();insertGeneratedStatement(true,false);undent();} stat 
				{
					blockStack.pop();
					out("WEND");
					nl();
				}
			)
	|	#("break" {out("break ");}
            (bid:IDENT {out(#bid.getText());} )?
        )
        {
        	//out(";"); 
        	nl();
        }
	|	#("continue" {out("continue ");}
            (cid:IDENT {out(#cid.getText());} )?
        )
        {
        	//out(";"); 
        	nl();
        }
	|	#("return" {out("EXIT ");} (expression)? ) 
		{
			//out(";"); 
			nl();
		}
	|	#("switch" {out("switch (");} expression {out(") {"); nl(); indent();}
            (caseGroup)*
            {undent(); out("}"); nl();}
        )
	|	#("throw" {out("throw ");} expression) 
		{
			//out(";"); 
			nl();
		}
	//|	#("synchronized" {out("synchronized (");} expression {out(") ");} stat)
	|	tryBlock
	|	slist // nested SLIST

    // uncomment to make assert JDK 1.4 stuff work
    // |   #("assert" expression (expression)?)
	
	|	EMPTY_STAT 
		{
			//out(";");
		}
	;

caseGroup
	:	#(  CASE_GROUP
            (   #("case" {out("case ");} expression {out(" :"); nl();})
            |   "default" {out("default :"); nl();}
            )+
            slist
         )
	;

tryBlock
	:	#( "try" {out("try ");}
            slist (handler)*
            ( #("finally" {out("finally ");} slist) )?
         )
	;

handler
	:	#( "catch" {out("catch (");} parameterDef {out(") ");} slist )
	;

elist
	:	#( ELIST (e:expression 
		{
			if(forLoopIterator){
				insertLineComment();
			}
			if (#e.getNextSibling()!=null) 
			{				
				out(",");				
			}
		} 
		)* )
	;

expression
	:  #(EXPR 
			{			
				AST assignAST = #expression.getFirstChild();
				TranslateUtil.handleBooleanValue(#expression);
				TranslateUtil.handleStringLiteral(#expression);
				TranslateUtil.handleDateTimeDotAST(#expression,this);
				if(assignAST!=null)
				{
					AST identAST = assignAST.getFirstChild();
					if(!blockStack.empty()){
				                CodeBlock block = getCurrentCodeBlock();
						if(identAST!=null && block.isIdentifierToIgnore(identAST.getText())){
							commentLine=true;
						}
					}
				}
				// Note::As an expression can appear on a if statement we do not want to do the check
				/*ArrayList methodCall = TranslateUtil.getChildASTList(#expression,IpeScriptEmitterTokenTypes.METHOD_CALL);
				boolean isTrue = TranslateUtil.isMethodCallListFromSupportedClass(this,methodCall);
				if(!isTrue){
					commentLine=true;
				}*/
				
			}
	expr)
	;

expr:   #(QUESTION expr {out("?");} expr {out(":");} expr)	// trinary operator
        // binary operators...
    |   (ASSIGN|PLUS_ASSIGN|MINUS_ASSIGN|STAR_ASSIGN|DIV_ASSIGN
        |MOD_ASSIGN|SR_ASSIGN|BSR_ASSIGN|SL_ASSIGN|BAND_ASSIGN
        |BXOR_ASSIGN|BOR_ASSIGN|LOR|LAND|BOR|BXOR|BAND|NOT_EQUAL
        |EQUAL|LT|GT|LE|GE|SL|SR|BSR|PLUS|MINUS|DIV|MOD|STAR|INSTANCEOF
        )
        {
        AST op = #expr;
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
		out(" "+#op.getText()+" ");
        }else if(( op.getType()==EQUAL )){
		TranslateUtil.handleDateTimeExpression(left,right,this);
                out("=");
        }else if(op.getType()==BXOR){
                out("^");
        }else if(( op.getType()==ASSIGN)){
		// check whether left is part of the 
		TranslateUtil.handleDateTimeExpression(left,right,this);
		boolean bool = TranslateUtil.isProcessDataField(left,getProcessDataField()); 
		if(!bool){
			commentLine = true;
		}        			
                out(":=");
        }else if(( op.getType()==NOT_EQUAL)){
		TranslateUtil.handleDateTimeExpression(left,right,this);
                out("<>");
        }else if(op.getType()==LOR||op.getType()==BOR){
                    out(" OR ");
        }else if(op.getType()==LAND||op.getType()==BAND){
                    out(" AND ");
        }else if(( op.getType()==PLUS_ASSIGN)){        	 
             out(":="+left.getText()+ " + " +right.getText());             
             return;
        }else if(( op.getType()==MINUS_ASSIGN)){
             out(":="+left.getText()+ " - " +right.getText());
             return;
        }else if(( op.getType()==STAR_ASSIGN)){
             out(":="+left.getText()+ " * " +right.getText());
             return;
        }else if(( op.getType()==DIV_ASSIGN)){
             out(":="+left.getText()+ " / " +right.getText());
             return;
        }else if(( op.getType()==BAND_ASSIGN)){
             out(":="+left.getText()+ " AND " +right.getText());
             return;
        }else if(( op.getType()==BXOR_ASSIGN)){
             out(":="+left.getText()+ " ^ " +right.getText());
             return;
        }else if(op.getType()==BOR_ASSIGN){
             out(":="+left.getText()+ " OR " +right.getText());
             return;
        }else if(op.getType()==GT
		||op.getType()==GE
		||op.getType()==LT
		||op.getType()==LE){
		TranslateUtil.handleDateTimeExpression(left,right,this);
		out(#op.getText()); 
        }	
        else {
            out(#op.getText());
        }
        if ( rp ) out("(");
        expr(right); // manually invoke
        if ( rp ) out(")");
        }

    |   (INC|DEC|BNOT|LNOT|UNARY_MINUS|UNARY_PLUS)
        {
        AST op = #expr;
        AST opnd = op.getFirstChild();
        boolean p = false;
        if ( hasHigherPrecedence(op.getType(),opnd.getType()) ) {
            p = true;
        }
        if(op.getType()==INC){
        	out("(");
		if(opnd.getType()==JScriptTokenTypes.IDENT){					
			boolean bool = TranslateUtil.isProcessDataField(opnd,getProcessDataField()); 
			if(!bool){
				commentLine = true;
			}							
			out(opnd.getText());
			out(":=");							
		}
		expr(opnd);
		out("+1)");
		return;
        	/*
        	Note, this is dealing with the case of ++index, 
        	we do not want the identifier to be printed again
        	*/
        	
        }else if(op.getType()==DEC){
        	out("(");
		if(opnd.getType()==JScriptTokenTypes.IDENT){					
			boolean bool = TranslateUtil.isProcessDataField(opnd,getProcessDataField()); 
			if(!bool){
				commentLine = true;
			}							
			out(opnd.getText());
			out(":=");							
		}
		expr(opnd);
		out("-1)");
		return;
        	/*
        	Note, this is dealing with the case of --index, 
        	we do not want the identifier to be printed again
        	*/
        	
        }
        else{
        	out(op.getText());
        }
        if ( p ) out("(");
        expr(opnd);
        if ( p ) out(")");
        }

    |   #( postAST:POST_INC  
    		{    			    		
    			boolean bool = handlePostPrefixOperator(#postAST,"+1");
    			if(bool){
    				return;
    			}											
    		} 
    	 expr{out("++");commentLine=true;})
    |   #( postDecAST:POST_DEC  
    		{    			
    			boolean bool = handlePostPrefixOperator(#postDecAST,"-1");
    			if(bool){
    				return;
    			}				
    		} 
    	  expr{out("--");commentLine=true;})
    |	primaryExpression
	;

primaryExpression
    :   id:IDENT {
    					if(id!=null && TranslateUtil.isSupportedClass(this,id.getText())){
						out("");
					}else if(id!=null && TranslateUtil.isUnSupportedKeyWord(id.getText(),this.destinationList,getScriptType())){
						commentLine=true;
						out(id.getText());
					}					
					else{									
						out(TranslateUtil.getTranslatedName(id.getText(), this.oldNewDataNameMap));																								
					}
				}
    |   #(	dotAST:DOT
			(	expr {
						AST staffwareAST = dotAST.getFirstChild();
						if(staffwareAST!=null && TranslateUtil.isSupportedClass(this,staffwareAST.getText())){
							out("");
						}else{
							out(".");
						}
					}
				(	id2:IDENT {
								/*if(id2!=null && TranslateUtil.isSupportedClass(this,id2.getText())){
									out("");
								}else{
									out(id2.getText());
								}*/
								out(id2.getText());
							  }
							  
				|	arrayIndex
				//|	"this" {out("this");}
				|	"class" {out("class");}
				|	#( "new" id3:IDENT {out("new "+id3.getText());} elist )
				//|   "super" {out("super");}
				)
			|	#(ARRAY_DECLARATOR typeSpecArray) "class" {out("class");}
			|	builtInType "class" {out("class");}
			)
		)
	|	arrayIndex
	|	#(methodAST:METHOD_CALL 
			{
				boolean result = TranslateUtil.isMethodCallFromSupportedClass(this,#methodAST);				
				if(!result){
					commentLine = true;
				}
			} 
			primaryExpression {out("(");} elist {out(")");} )
	|	#(TYPECAST {out("((");} typeSpec {out(")");} expr {out(")");} )
	|   newExpression 
	|   constant
    //|   ("super"|"true"|"false"|"this"|"null")
	|   ("true"|"false"|"null")
	{out(TranslateUtil.getCorrespondingKeyWord(#primaryExpression.getText()));}        
	|	typeSpec // type name used with instanceof
	;

arrayIndex
	:	#(INDEX_OP expr {out("[");} expression {out("]");} )
	;

constant
{if (#constant.getType() == STRING_LITERAL) { 
	out(#constant.getText().replace("\\\"", "\"\""));
 } else {
 	out(#constant.getText());
 }}
    :   NUM_INT
//    |   CHAR_LITERAL
    |   STRING_LITERAL
    |   NUM_FLOAT
    |   NUM_DOUBLE
    |   NUM_LONG
    ;

newExpression
	:	#(	newAST:"new" 
				{
					String str = TranslateUtil.handleNewExpression(#newAST);
					if(str!=null){
						out(str);
						nl();
						//return;
						
					}
					commentLine = true;
					out("new ");
				} 
				type {out("("); } 
				elist {out(")");} 									
				    
		)
	;

newArrayDeclarator
	:	#( ARRAY_DECLARATOR {out("[");} (expression)? {out("]");} )
	;
