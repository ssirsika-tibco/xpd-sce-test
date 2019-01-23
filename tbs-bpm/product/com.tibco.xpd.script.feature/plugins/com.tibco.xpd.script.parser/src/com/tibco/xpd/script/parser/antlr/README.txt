You need the following to generate the java files from antlr grammar file
=========================================================================

1. ".g" file -> for instance Jscript.g
2. ".cmd" file -> for instance generateParser.cmd file - This has the command that generates the java files from the grammar file
3. "antlr.jar" -> that has the antrl tool which actually generates the classes. You can get hold of this jar from target platform (\plugins\com.tibco.tpcl.antlr.eclipse_2.7.200.002\antlr.jar)


To generate the files
=====================

All the above list of files must exist in the same location. Open the command window or run it from your dev eclipse workspace by double clicking on the .cmd file. 

Important: Please note that you might have to edit the .cmd file to match the jar name you must have copied from the target platform.