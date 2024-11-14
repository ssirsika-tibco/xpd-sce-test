/*
 * This provides an implementation of the BOM class com.example.swaggerscriptgentests_process.SwaggerScriptGenTests_Process.SwaggerRequestScriptPrependTests_Process. 
 */
bpm.data.Loader.classDefiner["com.example.swaggerscriptgentests_process.SwaggerScriptGenTests_Process.SwaggerRequestScriptPrependTests_Process"] = function() {
    /** Constructor. */
    var theClass = function(context) {
        this._init(theClass, context);
    };

    theClass.LOADER = bpm.data.Loader.currentLoader;
    theClass.LOADER.registerClass(theClass, "com.example.swaggerscriptgentests_process.SwaggerScriptGenTests_Process.SwaggerRequestScriptPrependTests_Process");
    bpm.data.Loader.extendClass(bpm.data.BomBase, theClass);
    theClass.ATTR_TEXTFIELD04 = "TextField04";
    theClass.ATTR_FIELD11 = "Field11";
    theClass.ATTR_FIELD12 = "Field12";
    theClass.ATTR_FIELD10 = "Field10";
    theClass.ATTR_TEXTFIELD02 = "TextField02";
    theClass.ATTR_TEXTFIELD01 = "TextField01";
    theClass.ATTR_TEXTARRAY = "TextArray";
    theClass.ATTR_TEXTFIELD09 = "TextField09";
    theClass.ATTR_TEXTFIELD03 = "TextField03";
    theClass.ATTR_INTEGERFIELD01 = "IntegerField01";
    theClass.ATTR_TEXTFIELD08 = "TextField08";
    theClass.ATTR_TEXTFIELD05 = "TextField05";
    theClass.ATTR_TEXTFIELD07 = "TextField07";
    theClass.ATTR_TAGSFIELD = "TagsField";
    theClass.ATTR_TEXTFIELD06 = "TextField06";

    theClass.TYPE_ARRAY = {};
    theClass.TYPE_ARRAY[theClass.ATTR_TEXTFIELD04] = {
        type: "BomPrimitiveTypes.Text",
        baseType: "BomPrimitiveTypes.Text",
        primitive: true,
        multivalued: false,
        upper: 1,
        required: false,
        length: 50
    };
    theClass.TYPE_ARRAY[theClass.ATTR_FIELD11] = {
        type: "BomPrimitiveTypes.Text",
        baseType: "BomPrimitiveTypes.Text",
        primitive: true,
        multivalued: false,
        upper: 1,
        required: false,
        length: 50
    };
    theClass.TYPE_ARRAY[theClass.ATTR_FIELD12] = {
        type: "BomPrimitiveTypes.Text",
        baseType: "BomPrimitiveTypes.Text",
        primitive: true,
        multivalued: false,
        upper: 1,
        required: false,
        length: 50
    };
    theClass.TYPE_ARRAY[theClass.ATTR_FIELD10] = {
        type: "BomPrimitiveTypes.Text",
        baseType: "BomPrimitiveTypes.Text",
        primitive: true,
        multivalued: false,
        upper: 1,
        required: false,
        length: 50
    };
    theClass.TYPE_ARRAY[theClass.ATTR_TEXTFIELD02] = {
        type: "BomPrimitiveTypes.Text",
        baseType: "BomPrimitiveTypes.Text",
        primitive: true,
        multivalued: false,
        upper: 1,
        required: false,
        length: 50
    };
    theClass.TYPE_ARRAY[theClass.ATTR_TEXTFIELD01] = {
        type: "BomPrimitiveTypes.Text",
        baseType: "BomPrimitiveTypes.Text",
        primitive: true,
        multivalued: false,
        upper: 1,
        required: false,
        length: 50
    };
    theClass.TYPE_ARRAY[theClass.ATTR_TEXTARRAY] = {
        type: "BomPrimitiveTypes.Text",
        baseType: "BomPrimitiveTypes.Text",
        primitive: true,
        multivalued: true,
        required: false,
        length: 50
    };
    theClass.TYPE_ARRAY[theClass.ATTR_TEXTFIELD09] = {
        type: "BomPrimitiveTypes.Text",
        baseType: "BomPrimitiveTypes.Text",
        primitive: true,
        multivalued: false,
        upper: 1,
        required: false,
        length: 50
    };
    theClass.TYPE_ARRAY[theClass.ATTR_TEXTFIELD03] = {
        type: "BomPrimitiveTypes.Text",
        baseType: "BomPrimitiveTypes.Text",
        primitive: true,
        multivalued: false,
        upper: 1,
        required: false,
        length: 50
    };
    theClass.TYPE_ARRAY[theClass.ATTR_INTEGERFIELD01] = {
        type: "BomPrimitiveTypes.Decimal",
        baseType: "BomPrimitiveTypes.Decimal",
        primitive: true,
        multivalued: false,
        upper: 1,
        required: false,
        length: 10,
        decimalPlaces: 0

    };
    theClass.TYPE_ARRAY[theClass.ATTR_TEXTFIELD08] = {
        type: "BomPrimitiveTypes.Text",
        baseType: "BomPrimitiveTypes.Text",
        primitive: true,
        multivalued: false,
        upper: 1,
        required: false,
        length: 50
    };
    theClass.TYPE_ARRAY[theClass.ATTR_TEXTFIELD05] = {
        type: "BomPrimitiveTypes.Text",
        baseType: "BomPrimitiveTypes.Text",
        primitive: true,
        multivalued: false,
        upper: 1,
        required: false,
        length: 50
    };
    theClass.TYPE_ARRAY[theClass.ATTR_TEXTFIELD07] = {
        type: "BomPrimitiveTypes.Text",
        baseType: "BomPrimitiveTypes.Text",
        primitive: true,
        multivalued: false,
        upper: 1,
        required: false,
        length: 50
    };
    theClass.TYPE_ARRAY[theClass.ATTR_TAGSFIELD] = {
        type: "com.example.SwaggerScriptGenTests_data.Tag",
        baseType: "com.example.SwaggerScriptGenTests_data.Tag",
        primitive: false,
        multivalued: true,
        required: false
    };
    theClass.TYPE_ARRAY[theClass.ATTR_TEXTFIELD06] = {
        type: "BomPrimitiveTypes.Text",
        baseType: "BomPrimitiveTypes.Text",
        primitive: true,
        multivalued: false,
        upper: 1,
        required: false,
        length: 50
    };

    bpm.data.BomBase.defineProperties(theClass);
};

bpm.data.Loader.classDefiner["com.example.swaggerscriptgentests_process.SwaggerScriptGenTests_Process.SwaggerRequestScriptPrependTests_Process"]();

bpm.scriptUtil._internal.checkVersionCompatibility("SwaggerRequestScriptPrependTests_Process.js", "11.4.0.048");
