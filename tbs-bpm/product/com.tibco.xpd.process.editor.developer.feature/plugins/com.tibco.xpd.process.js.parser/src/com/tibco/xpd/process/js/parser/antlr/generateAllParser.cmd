REM /***************************************************************/
REM /* Please note that for the correct generation of this files   */
REM /*  the file Jscript.g needs to be placed in the same directory*/ 
REM /***************************************************************/

REM these come from script feature
java -Xms200m -Xmx400m antlr.Tool Jscript.g
java -Xms200m -Xmx400m antlr.Tool JavaScriptTreeParser.g
java -Xms200m -Xmx400m antlr.Tool Jscript.tree.emitter.g

REM these come from process developer feature
java -Xms200m -Xmx400m antlr.Tool Jscript.tree.g
java -Xms200m -Xmx400m antlr.Tool IpeScriptTranslator.g
java -Xms200m -Xmx400m antlr.Tool IpeScript.tree.emitter.g


