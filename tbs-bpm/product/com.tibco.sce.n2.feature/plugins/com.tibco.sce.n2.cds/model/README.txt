DataUtil.uml contains static classes, methods and attributes specific to case classes used in global data. 

The classes that are required to be provided in content assist must be declared as static.
To make a class static in this uml, its attributes and methods must be made static.

The classes defined in this uml are:

DataUtil
PaginatedList
Criteria

The class that is required in content assist is DataUtil class. So only DataUtil class will be declared as static.
DataUtil class has 3 methods. These 3 methods are declared as static.

Following are the methods that will be listed in content assist when DataUtil class is used.
1. createList() : List<Object> - creates a list for use in scripting
2. createCriteria(String) : Criteria - create criteria object for the given string
3. createCriteria(String, int, int) : Criteria - create criteria for given string with zero based start index and pagesize 

The other 2 classes - PaginatedList and Criteria are available when their objects are available (in content assist).
So they are not declared as static.
For instance, Criteria object will be available when DataUtil.createCriteria(String) is called.
Once the criteria object is available the attributes and methods on it will be available in the content assist.

Js classes, Js attributes, Js methods will be created for each of the classes, attributes and methods.

Java Doc binding to the classes/methods/attributes defined in this uml:
----------------------------------------------------------------------
To provide java doc binding create <uml file name>.properties file. And Provide the key value pairs for documentation and it automatically binds them to their attributes/methods/classes based on their name
So in this case it is DataUtil.properties

If there are two methods with same name (overloaded methods) in a particular class then java doc comments are not mapped automatically...
	 * For Operations we may have more than one operation with same name (i.e.
	 * overloaded methods) - in which case the standard mechanism for looking
	 * for resource bundle .properties file message matching the qualified name
	 * for the element could be ambiguous.
	 * 
	 * So we have added a special clause whereby, if we don't specify the
	 * message and key according to just the method name in the properties file
	 * BUT DO specify the key as
	 * 
	 * - <qualified methodname>__paramTypeName__paramTypeName
	 * 
	 * Then we will use that. The parameter type names INCLUDE return paramter
	 * AND must be specified in the message key in the same order as declared in
	 * the UML
	 * 
	 * For List / Array type parameters append "[]" to the parameter type name.

Debugging information
---------------------
If you want to debug this info put a break point at JScriptUtils.getUmlElementComment(NamedElement element).
This gets called from each of the js classes (i.e JsClass, JsMethod, JsAttribute etc) getComment() method.