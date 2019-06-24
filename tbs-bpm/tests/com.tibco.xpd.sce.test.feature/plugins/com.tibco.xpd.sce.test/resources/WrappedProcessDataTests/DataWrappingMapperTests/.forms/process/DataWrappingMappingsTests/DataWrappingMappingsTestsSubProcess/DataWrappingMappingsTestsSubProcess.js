
/*
 * This provides an implementation of the BOM class $Process.Data. 
 */

com.tibco.data.Loader.classDefiner["$Process.Data"] = function() {
    /** Constructor. */
    var theClass = function(context) {
        this._init(theClass, context);
    };

    theClass.LOADER = com.tibco.data.Loader.currentLoader;
    theClass.LOADER.registerClass(theClass, "$Process.Data");
    com.tibco.data.Loader.extendClass(com.tibco.data.BomBase, theClass);

    theClass.ATTR_CLASSPARAMETER = "ClassParameter";
    theClass.ATTR_BOOLEANPARAMETER = "BooleanParameter";
    theClass.ATTR_DATEPARAMETER = "DateParameter";
    theClass.ATTR_TEXTPARAMETER = "TextParameter";
    theClass.ATTR_DATETIMEPARAMETER = "DateTimeParameter";
    theClass.ATTR_COMPLEXLISTPARAMETER = "ComplexListParameter";
    theClass.ATTR_SIMPLELISTPARAMETER = "SimpleListParameter";
    theClass.ATTR_PERFORMERPARAMETER = "PerformerParameter";
    theClass.ATTR_FIXEDPOINTNUMBERPARAMETER = "FixedPointNumberParameter";
    theClass.ATTR_NUMBERPARAMETER = "NumberParameter";
    theClass.ATTR_TIMEPARAMETER = "TimeParameter";

    theClass.TYPE_ARRAY = new Object();
    theClass.TYPE_ARRAY[theClass.ATTR_CLASSPARAMETER] = {
        type: "com.example.data.DataTypes",
        baseType: "com.example.data.DataTypes",
        primitive: false,
        multivalued: false,
        required: true,
        defaultValue: ""
    };
    theClass.TYPE_ARRAY[theClass.ATTR_BOOLEANPARAMETER] = {
        type: "BomPrimitiveTypes.Boolean",
        baseType: "BomPrimitiveTypes.Boolean",
        primitive: true,
        multivalued: false,
        required: true,
        defaultValue: ""
    };
    theClass.TYPE_ARRAY[theClass.ATTR_DATEPARAMETER] = {
        type: "BomPrimitiveTypes.Date",
        baseType: "BomPrimitiveTypes.Date",
        primitive: true,
        multivalued: false,
        required: true,
        defaultValue: ""
    };
    theClass.TYPE_ARRAY[theClass.ATTR_TEXTPARAMETER] = {
        type: "BomPrimitiveTypes.Text",
        baseType: "BomPrimitiveTypes.Text",
        primitive: true,
        multivalued: false,
        required: true,
        defaultValue: "",
        length: 50
    };
    theClass.TYPE_ARRAY[theClass.ATTR_DATETIMEPARAMETER] = {
        type: "BomPrimitiveTypes.DateTime",
        baseType: "BomPrimitiveTypes.DateTime",
        primitive: true,
        multivalued: false,
        required: true,
        defaultValue: ""
    };
    theClass.TYPE_ARRAY[theClass.ATTR_COMPLEXLISTPARAMETER] = {
        type: "com.example.data.DataTypes",
        baseType: "com.example.data.DataTypes",
        primitive: false,
        multivalued: true,
        required: true,
        defaultValue: ""
    };
    theClass.TYPE_ARRAY[theClass.ATTR_SIMPLELISTPARAMETER] = {
        type: "BomPrimitiveTypes.Text",
        baseType: "BomPrimitiveTypes.Text",
        primitive: true,
        multivalued: true,
        required: true,
        defaultValue: "",
        length: 50
    };
    theClass.TYPE_ARRAY[theClass.ATTR_PERFORMERPARAMETER] = {
        type: "BomPrimitiveTypes.Text",
        baseType: "BomPrimitiveTypes.Text",
        primitive: true,
        multivalued: false,
        required: true,
        defaultValue: "",
        length: -1
    };
    theClass.TYPE_ARRAY[theClass.ATTR_FIXEDPOINTNUMBERPARAMETER] = {
        type: "BomPrimitiveTypes.Decimal",
        baseType: "BomPrimitiveTypes.Decimal",
        primitive: true,
        multivalued: false,
        required: true,
        defaultValue: "",
        length: 10,
        decimalPlaces: 2

    };
    theClass.TYPE_ARRAY[theClass.ATTR_NUMBERPARAMETER] = {
        type: "BomPrimitiveTypes.Decimal",
        baseType: "BomPrimitiveTypes.Decimal",
        primitive: true,
        multivalued: false,
        required: true,
        defaultValue: "",
        length: 0,
        decimalPlaces: 0

    };
    theClass.TYPE_ARRAY[theClass.ATTR_TIMEPARAMETER] = {
        type: "BomPrimitiveTypes.Time",
        baseType: "BomPrimitiveTypes.Time",
        primitive: true,
        multivalued: false,
        required: true,
        defaultValue: ""
    };

    theClass.PRIMITIVE_ATTRIBUTE_NAMES = [
        theClass.ATTR_BOOLEANPARAMETER,
        theClass.ATTR_DATEPARAMETER,
        theClass.ATTR_TEXTPARAMETER,
        theClass.ATTR_DATETIMEPARAMETER,
        theClass.ATTR_SIMPLELISTPARAMETER,
        theClass.ATTR_PERFORMERPARAMETER,
        theClass.ATTR_FIXEDPOINTNUMBERPARAMETER,
        theClass.ATTR_NUMBERPARAMETER,
        theClass.ATTR_TIMEPARAMETER
    ];
    theClass.COMPOSITE_ATTRIBUTE_NAMES = [
        theClass.ATTR_CLASSPARAMETER,
        theClass.ATTR_COMPLEXLISTPARAMETER
    ];
    theClass.ATTRIBUTE_NAMES = [
        theClass.ATTR_CLASSPARAMETER,
        theClass.ATTR_BOOLEANPARAMETER,
        theClass.ATTR_DATEPARAMETER,
        theClass.ATTR_TEXTPARAMETER,
        theClass.ATTR_DATETIMEPARAMETER,
        theClass.ATTR_COMPLEXLISTPARAMETER,
        theClass.ATTR_SIMPLELISTPARAMETER,
        theClass.ATTR_PERFORMERPARAMETER,
        theClass.ATTR_FIXEDPOINTNUMBERPARAMETER,
        theClass.ATTR_NUMBERPARAMETER,
        theClass.ATTR_TIMEPARAMETER
    ];

    theClass.getName = function() {
        return "$Process.Data";
    };


    Object.defineProperty(theClass.prototype, 'ClassParameter', {
        get: function() {
            return this._getComplexAttribute(this.loader.getClass("$Process.Data").ATTR_CLASSPARAMETER);
        },
        set: function(ClassParameter) {
            var classRef = this.loader.getClass("$Process.Data");
            var attrRef = classRef.ATTR_CLASSPARAMETER;
            var attrType = classRef._getTypeDef(attrRef);
            if (eval("ClassParameter instanceof this.loader.getClass(attrType.type)")) {
                this._setComplexAttribute(attrRef, ClassParameter);
            } else {
                throw "Wrong input object type.";
            }
        },
        enumerable: true
    });


    Object.defineProperty(theClass.prototype, 'BooleanParameter', {
        get: function() {
            return this._getPrimitiveAttribute(this.loader.getClass("$Process.Data").ATTR_BOOLEANPARAMETER);
        },
        set: function(BooleanParameter) {
            this._setPrimitiveAttribute(this.loader.getClass("$Process.Data").ATTR_BOOLEANPARAMETER, BooleanParameter);
        },
        enumerable: true
    });


    Object.defineProperty(theClass.prototype, 'DateParameter', {
        get: function() {
            return this._getPrimitiveAttribute(this.loader.getClass("$Process.Data").ATTR_DATEPARAMETER);
        },
        set: function(DateParameter) {
            this._setPrimitiveAttribute(this.loader.getClass("$Process.Data").ATTR_DATEPARAMETER, DateParameter);
        },
        enumerable: true
    });


    Object.defineProperty(theClass.prototype, 'TextParameter', {
        get: function() {
            return this._getPrimitiveAttribute(this.loader.getClass("$Process.Data").ATTR_TEXTPARAMETER);
        },
        set: function(TextParameter) {
            this._setPrimitiveAttribute(this.loader.getClass("$Process.Data").ATTR_TEXTPARAMETER, TextParameter);
        },
        enumerable: true
    });


    Object.defineProperty(theClass.prototype, 'DateTimeParameter', {
        get: function() {
            return this._getPrimitiveAttribute(this.loader.getClass("$Process.Data").ATTR_DATETIMEPARAMETER);
        },
        set: function(DateTimeParameter) {
            this._setPrimitiveAttribute(this.loader.getClass("$Process.Data").ATTR_DATETIMEPARAMETER, DateTimeParameter);
        },
        enumerable: true
    });


    Object.defineProperty(theClass.prototype, 'ComplexListParameter', {
        get: function() {
            return this._getComplexArrayAttribute(this.loader.getClass("$Process.Data").ATTR_COMPLEXLISTPARAMETER);
        },
        set: function(ComplexListParameter) {
            throw "Unsupported complex array attribute: ComplexListParameter";
        },
        enumerable: true
    });


    Object.defineProperty(theClass.prototype, 'SimpleListParameter', {
        get: function() {
            return this._getPrimitiveArrayAttribute(this.loader.getClass("$Process.Data").ATTR_SIMPLELISTPARAMETER);
        },
        set: function(SimpleListParameter) {
            this._setPrimitiveAttribute(this.loader.getClass("$Process.Data").ATTR_SIMPLELISTPARAMETER, SimpleListParameter);
        },
        enumerable: true
    });


    Object.defineProperty(theClass.prototype, 'PerformerParameter', {
        get: function() {
            return this._getPrimitiveAttribute(this.loader.getClass("$Process.Data").ATTR_PERFORMERPARAMETER);
        },
        set: function(PerformerParameter) {
            this._setPrimitiveAttribute(this.loader.getClass("$Process.Data").ATTR_PERFORMERPARAMETER, PerformerParameter);
        },
        enumerable: true
    });


    Object.defineProperty(theClass.prototype, 'FixedPointNumberParameter', {
        get: function() {
            return this._getPrimitiveAttribute(this.loader.getClass("$Process.Data").ATTR_FIXEDPOINTNUMBERPARAMETER);
        },
        set: function(FixedPointNumberParameter) {
            this._setPrimitiveAttribute(this.loader.getClass("$Process.Data").ATTR_FIXEDPOINTNUMBERPARAMETER, FixedPointNumberParameter);
        },
        enumerable: true
    });


    Object.defineProperty(theClass.prototype, 'NumberParameter', {
        get: function() {
            return this._getPrimitiveAttribute(this.loader.getClass("$Process.Data").ATTR_NUMBERPARAMETER);
        },
        set: function(NumberParameter) {
            this._setPrimitiveAttribute(this.loader.getClass("$Process.Data").ATTR_NUMBERPARAMETER, NumberParameter);
        },
        enumerable: true
    });


    Object.defineProperty(theClass.prototype, 'TimeParameter', {
        get: function() {
            return this._getPrimitiveAttribute(this.loader.getClass("$Process.Data").ATTR_TIMEPARAMETER);
        },
        set: function(TimeParameter) {
            this._setPrimitiveAttribute(this.loader.getClass("$Process.Data").ATTR_TIMEPARAMETER, TimeParameter);
        },
        enumerable: true
    });

};

com.tibco.data.Loader.classDefiner["$Process.Data"]();
