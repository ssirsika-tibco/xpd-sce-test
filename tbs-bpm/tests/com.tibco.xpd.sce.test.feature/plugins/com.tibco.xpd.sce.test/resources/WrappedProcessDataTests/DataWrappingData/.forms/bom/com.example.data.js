/*
 * DO NOT EDIT: This is automatically generated code.
 * This provides an implementation of the BOM package com.example.data.DataPackage.
 */

com.tibco.data.Loader.classDefiner["com.example.data.DataPackage"] = function() {
    /** Constructor. */
    var theClass = function() {
    };
    com.tibco.data.Loader.currentLoader.registerPackage(theClass, "com.example.data.DataPackage");

    // Define the enumerations declared by this package.
    theClass.Enumeration1 = ["ENUMLIT1", "ENUMLIT2", "ENUMLIT3"];
    theClass.Enumeration1.ENUMLIT1 = "ENUMLIT1";
    theClass.Enumeration1.ENUMLIT2 = "ENUMLIT2";
    theClass.Enumeration1.ENUMLIT3 = "ENUMLIT3";
};

com.tibco.data.Loader.classDefiner["com.example.data.DataPackage"]();

/*
 * DO NOT EDIT: This is automatically generated code.
 * This provides an implementation of the BOM factory com.example.data.DataFactory. 
 */ 

com.tibco.data.Loader.classDefiner["com.example.data.DataFactory"] = function() {
    /** Constructor. */
    var theClass = function(form) {
        this.context = new Object();
        this.context.form = form;
        if (form.getLogger)
            this.context.logger = form.getLogger();
        this.context.item = null;
        this.context.id = null;
        this.loader = form._loader;
    };
    com.tibco.data.Loader.currentLoader.registerFactory(theClass, "com.example.data.DataFactory");

    theClass.prototype.SUPPORTED_CLASSES = [
        "com.example.data.DataTypes",
        "com.example.data.Child"
    ];

    theClass.prototype.checkClassName = function(className) {
        for (var i = 0; i < this.SUPPORTED_CLASSES.length; i++) {
            if (className == this.SUPPORTED_CLASSES[i])
                return;
        };
        throw "Factory com.example.data.DataFactory does not support class \"" + className + "\"";
    };

    theClass.prototype.create = function(className, jsonData) {
        this.checkClassName(className);
        var instance = this.loader.newInstance(className, this.context);
        if (jsonData != undefined)
            instance = instance._setValue(instance, jsonData, this.context);
        return instance;
    };

    theClass.prototype.createDataTypes = function(jsonData) {
        var instance = this.loader.newInstance("com.example.data.DataTypes", this.context);
        if (jsonData != undefined)
            instance = instance._setValue(instance, jsonData);
        return instance;
    };

    theClass.prototype.listCreateDataTypes = function(jsonData) {
        var instance = this.loader.newInstance("com.example.data.DataTypes", this.context);
        return instance._setValueList(instance, jsonData);
    };
    theClass.prototype.createChild = function(jsonData) {
        var instance = this.loader.newInstance("com.example.data.Child", this.context);
        if (jsonData != undefined)
            instance = instance._setValue(instance, jsonData);
        return instance;
    };

    theClass.prototype.listCreateChild = function(jsonData) {
        var instance = this.loader.newInstance("com.example.data.Child", this.context);
        return instance._setValueList(instance, jsonData);
    };
    theClass.prototype.getClass = function(className) {
        this.checkClassName(className);
        return this.loader.getClass(className);
    };
};

com.tibco.data.Loader.classDefiner["com.example.data.DataFactory"]();

/*
 * This provides an implementation of the BOM class com.example.data.DataTypes. 
 */

com.tibco.data.Loader.classDefiner["com.example.data.DataTypes"] = function() {
    /** Constructor. */
    var theClass = function(context) {
        this._init(theClass, context);
    };

    theClass.LOADER = com.tibco.data.Loader.currentLoader;
    theClass.LOADER.registerClass(theClass, "com.example.data.DataTypes");
    com.tibco.data.Loader.extendClass(com.tibco.data.BomBase, theClass);

    theClass.ATTR_FLOAT1 = "float1";
    theClass.ATTR_FIXED = "fixed";
    theClass.ATTR_TEXT = "text";
    theClass.ATTR_DATETIMETZ = "dateTimeTz";
    theClass.ATTR_COMPLEXCHILD = "complexChild";
    theClass.ATTR_BOOLEAN1 = "boolean1";
    theClass.ATTR_URI = "URI";
    theClass.ATTR_DATE = "date";
    theClass.ATTR_TIME = "time";
    theClass.ATTR_TEXTLIST = "textList";
    theClass.ATTR_COMPLEXLIST = "complexList";
    theClass.ATTR_ENUM1 = "enum1";
    theClass.ATTR_ENUMLIST = "enumList";

    theClass.TYPE_ARRAY = new Object();
    theClass.TYPE_ARRAY[theClass.ATTR_FLOAT1] = {
        type: "BomPrimitiveTypes.Decimal",
        baseType: "BomPrimitiveTypes.Decimal",
        primitive: true,
        multivalued: false,
        required: false,
        defaultValue: ""
    };
    theClass.TYPE_ARRAY[theClass.ATTR_FIXED] = {
        type: "BomPrimitiveTypes.Decimal",
        baseType: "BomPrimitiveTypes.Decimal",
        primitive: true,
        multivalued: false,
        required: false,
        defaultValue: "",
        length: 10,
        decimalPlaces: 2

    };
    theClass.TYPE_ARRAY[theClass.ATTR_TEXT] = {
        type: "BomPrimitiveTypes.Text",
        baseType: "BomPrimitiveTypes.Text",
        primitive: true,
        multivalued: false,
        required: false,
        defaultValue: "",
        length: 50
    };
    theClass.TYPE_ARRAY[theClass.ATTR_DATETIMETZ] = {
        type: "BomPrimitiveTypes.DateTimeTZ",
        baseType: "BomPrimitiveTypes.DateTimeTZ",
        primitive: true,
        multivalued: false,
        required: false,
        defaultValue: ""
    };
    theClass.TYPE_ARRAY[theClass.ATTR_COMPLEXCHILD] = {
        type: "com.example.data.Child",
        baseType: "com.example.data.Child",
        primitive: false,
        multivalued: false,
        required: true,
        defaultValue: ""
    };
    theClass.TYPE_ARRAY[theClass.ATTR_BOOLEAN1] = {
        type: "BomPrimitiveTypes.Boolean",
        baseType: "BomPrimitiveTypes.Boolean",
        primitive: true,
        multivalued: false,
        required: false,
        defaultValue: ""
    };
    theClass.TYPE_ARRAY[theClass.ATTR_URI] = {
        type: "BomPrimitiveTypes.URI",
        baseType: "BomPrimitiveTypes.URI",
        primitive: true,
        multivalued: false,
        required: false,
        defaultValue: ""
    };
    theClass.TYPE_ARRAY[theClass.ATTR_DATE] = {
        type: "BomPrimitiveTypes.Date",
        baseType: "BomPrimitiveTypes.Date",
        primitive: true,
        multivalued: false,
        required: false,
        defaultValue: ""
    };
    theClass.TYPE_ARRAY[theClass.ATTR_TIME] = {
        type: "BomPrimitiveTypes.Time",
        baseType: "BomPrimitiveTypes.Time",
        primitive: true,
        multivalued: false,
        required: false,
        defaultValue: ""
    };
    theClass.TYPE_ARRAY[theClass.ATTR_TEXTLIST] = {
        type: "BomPrimitiveTypes.Text",
        baseType: "BomPrimitiveTypes.Text",
        primitive: true,
        multivalued: true,
        required: false,
        defaultValue: "",
        length: 50
    };
    theClass.TYPE_ARRAY[theClass.ATTR_COMPLEXLIST] = {
        type: "com.example.data.Child",
        baseType: "com.example.data.Child",
        primitive: false,
        multivalued: true,
        required: false,
        defaultValue: ""
    };
    theClass.TYPE_ARRAY[theClass.ATTR_ENUM1] = {
        type: "com.example.data.Enumeration1",
        baseType: "com.example.data.Enumeration1",
        primitive: true,
        multivalued: false,
        required: false,
        defaultValue: ""
    };
    theClass.TYPE_ARRAY[theClass.ATTR_ENUMLIST] = {
        type: "com.example.data.Enumeration1",
        baseType: "com.example.data.Enumeration1",
        primitive: true,
        multivalued: true,
        required: false,
        defaultValue: ""
    };

    theClass.PRIMITIVE_ATTRIBUTE_NAMES = [
        theClass.ATTR_FLOAT1,
        theClass.ATTR_FIXED,
        theClass.ATTR_TEXT,
        theClass.ATTR_DATETIMETZ,
        theClass.ATTR_BOOLEAN1,
        theClass.ATTR_URI,
        theClass.ATTR_DATE,
        theClass.ATTR_TIME,
        theClass.ATTR_TEXTLIST,
        theClass.ATTR_ENUM1,
        theClass.ATTR_ENUMLIST
    ];
    theClass.COMPOSITE_ATTRIBUTE_NAMES = [
        theClass.ATTR_COMPLEXCHILD,
        theClass.ATTR_COMPLEXLIST
    ];
    theClass.ATTRIBUTE_NAMES = [
        theClass.ATTR_FLOAT1,
        theClass.ATTR_FIXED,
        theClass.ATTR_TEXT,
        theClass.ATTR_DATETIMETZ,
        theClass.ATTR_COMPLEXCHILD,
        theClass.ATTR_BOOLEAN1,
        theClass.ATTR_URI,
        theClass.ATTR_DATE,
        theClass.ATTR_TIME,
        theClass.ATTR_TEXTLIST,
        theClass.ATTR_COMPLEXLIST,
        theClass.ATTR_ENUM1,
        theClass.ATTR_ENUMLIST
    ];

    theClass.getName = function() {
        return "com.example.data.DataTypes";
    };


    Object.defineProperty(theClass.prototype, 'float1', {
        get: function() {
            return this._getPrimitiveAttribute(this.loader.getClass("com.example.data.DataTypes").ATTR_FLOAT1);
        },
        set: function(float1) {
            this._setPrimitiveAttribute(this.loader.getClass("com.example.data.DataTypes").ATTR_FLOAT1, float1);
        },
        enumerable: true
    });


    Object.defineProperty(theClass.prototype, 'fixed', {
        get: function() {
            return this._getPrimitiveAttribute(this.loader.getClass("com.example.data.DataTypes").ATTR_FIXED);
        },
        set: function(fixed) {
            this._setPrimitiveAttribute(this.loader.getClass("com.example.data.DataTypes").ATTR_FIXED, fixed);
        },
        enumerable: true
    });


    Object.defineProperty(theClass.prototype, 'text', {
        get: function() {
            return this._getPrimitiveAttribute(this.loader.getClass("com.example.data.DataTypes").ATTR_TEXT);
        },
        set: function(text) {
            this._setPrimitiveAttribute(this.loader.getClass("com.example.data.DataTypes").ATTR_TEXT, text);
        },
        enumerable: true
    });


    Object.defineProperty(theClass.prototype, 'dateTimeTz', {
        get: function() {
            return this._getPrimitiveAttribute(this.loader.getClass("com.example.data.DataTypes").ATTR_DATETIMETZ);
        },
        set: function(dateTimeTz) {
            this._setPrimitiveAttribute(this.loader.getClass("com.example.data.DataTypes").ATTR_DATETIMETZ, dateTimeTz);
        },
        enumerable: true
    });


    Object.defineProperty(theClass.prototype, 'complexChild', {
        get: function() {
            return this._getComplexAttribute(this.loader.getClass("com.example.data.DataTypes").ATTR_COMPLEXCHILD);
        },
        set: function(complexChild) {
            var classRef = this.loader.getClass("com.example.data.DataTypes");
            var attrRef = classRef.ATTR_COMPLEXCHILD;
            var attrType = classRef._getTypeDef(attrRef);
            if (eval("complexChild instanceof this.loader.getClass(attrType.type)")) {
                this._setComplexAttribute(attrRef, complexChild);
            } else {
                throw "Wrong input object type.";
            }
        },
        enumerable: true
    });


    Object.defineProperty(theClass.prototype, 'boolean1', {
        get: function() {
            return this._getPrimitiveAttribute(this.loader.getClass("com.example.data.DataTypes").ATTR_BOOLEAN1);
        },
        set: function(boolean1) {
            this._setPrimitiveAttribute(this.loader.getClass("com.example.data.DataTypes").ATTR_BOOLEAN1, boolean1);
        },
        enumerable: true
    });


    Object.defineProperty(theClass.prototype, 'URI', {
        get: function() {
            return this._getPrimitiveAttribute(this.loader.getClass("com.example.data.DataTypes").ATTR_URI);
        },
        set: function(URI) {
            this._setPrimitiveAttribute(this.loader.getClass("com.example.data.DataTypes").ATTR_URI, URI);
        },
        enumerable: true
    });


    Object.defineProperty(theClass.prototype, 'date', {
        get: function() {
            return this._getPrimitiveAttribute(this.loader.getClass("com.example.data.DataTypes").ATTR_DATE);
        },
        set: function(date) {
            this._setPrimitiveAttribute(this.loader.getClass("com.example.data.DataTypes").ATTR_DATE, date);
        },
        enumerable: true
    });


    Object.defineProperty(theClass.prototype, 'time', {
        get: function() {
            return this._getPrimitiveAttribute(this.loader.getClass("com.example.data.DataTypes").ATTR_TIME);
        },
        set: function(time) {
            this._setPrimitiveAttribute(this.loader.getClass("com.example.data.DataTypes").ATTR_TIME, time);
        },
        enumerable: true
    });


    Object.defineProperty(theClass.prototype, 'textList', {
        get: function() {
            return this._getPrimitiveArrayAttribute(this.loader.getClass("com.example.data.DataTypes").ATTR_TEXTLIST);
        },
        set: function(textList) {
            this._setPrimitiveAttribute(this.loader.getClass("com.example.data.DataTypes").ATTR_TEXTLIST, textList);
        },
        enumerable: true
    });


    Object.defineProperty(theClass.prototype, 'complexList', {
        get: function() {
            return this._getComplexArrayAttribute(this.loader.getClass("com.example.data.DataTypes").ATTR_COMPLEXLIST);
        },
        set: function(complexList) {
            throw "Unsupported complex array attribute: complexList";
        },
        enumerable: true
    });


    Object.defineProperty(theClass.prototype, 'enum1', {
        get: function() {
            return this._getPrimitiveAttribute(this.loader.getClass("com.example.data.DataTypes").ATTR_ENUM1);
        },
        set: function(enum1) {
            this._setPrimitiveAttribute(this.loader.getClass("com.example.data.DataTypes").ATTR_ENUM1, enum1);
        },
        enumerable: true
    });


    Object.defineProperty(theClass.prototype, 'enumList', {
        get: function() {
            return this._getPrimitiveArrayAttribute(this.loader.getClass("com.example.data.DataTypes").ATTR_ENUMLIST);
        },
        set: function(enumList) {
            this._setPrimitiveAttribute(this.loader.getClass("com.example.data.DataTypes").ATTR_ENUMLIST, enumList);
        },
        enumerable: true
    });

};

com.tibco.data.Loader.classDefiner["com.example.data.DataTypes"]();

/*
 * This provides an implementation of the BOM class com.example.data.Child. 
 */

com.tibco.data.Loader.classDefiner["com.example.data.Child"] = function() {
    /** Constructor. */
    var theClass = function(context) {
        this._init(theClass, context);
    };

    theClass.LOADER = com.tibco.data.Loader.currentLoader;
    theClass.LOADER.registerClass(theClass, "com.example.data.Child");
    com.tibco.data.Loader.extendClass(com.tibco.data.BomBase, theClass);

    theClass.ATTR_ATTRIBUTE1 = "attribute1";
    theClass.ATTR_ATTRIBUTE2 = "attribute2";

    theClass.TYPE_ARRAY = new Object();
    theClass.TYPE_ARRAY[theClass.ATTR_ATTRIBUTE1] = {
        type: "BomPrimitiveTypes.Text",
        baseType: "BomPrimitiveTypes.Text",
        primitive: true,
        multivalued: false,
        required: false,
        defaultValue: "",
        length: 50
    };
    theClass.TYPE_ARRAY[theClass.ATTR_ATTRIBUTE2] = {
        type: "BomPrimitiveTypes.Text",
        baseType: "BomPrimitiveTypes.Text",
        primitive: true,
        multivalued: false,
        required: false,
        defaultValue: "",
        length: 50
    };

    theClass.PRIMITIVE_ATTRIBUTE_NAMES = [
        theClass.ATTR_ATTRIBUTE1,
        theClass.ATTR_ATTRIBUTE2
    ];
    theClass.COMPOSITE_ATTRIBUTE_NAMES = [

    ];
    theClass.ATTRIBUTE_NAMES = [
        theClass.ATTR_ATTRIBUTE1,
        theClass.ATTR_ATTRIBUTE2
    ];

    theClass.getName = function() {
        return "com.example.data.Child";
    };


    Object.defineProperty(theClass.prototype, 'attribute1', {
        get: function() {
            return this._getPrimitiveAttribute(this.loader.getClass("com.example.data.Child").ATTR_ATTRIBUTE1);
        },
        set: function(attribute1) {
            this._setPrimitiveAttribute(this.loader.getClass("com.example.data.Child").ATTR_ATTRIBUTE1, attribute1);
        },
        enumerable: true
    });


    Object.defineProperty(theClass.prototype, 'attribute2', {
        get: function() {
            return this._getPrimitiveAttribute(this.loader.getClass("com.example.data.Child").ATTR_ATTRIBUTE2);
        },
        set: function(attribute2) {
            this._setPrimitiveAttribute(this.loader.getClass("com.example.data.Child").ATTR_ATTRIBUTE2, attribute2);
        },
        enumerable: true
    });

};

com.tibco.data.Loader.classDefiner["com.example.data.Child"]();
