
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

    theClass.ATTR_BOOLEANFIELD = "BooleanField";
    theClass.ATTR_SIMPLELISTFIELD = "SimpleListField";
    theClass.ATTR_DATETIMEFIELD = "DateTimeField";
    theClass.ATTR_DATEFIELD = "DateField";
    theClass.ATTR_CLASSFIELD = "ClassField";
    theClass.ATTR_PERFORMERFIELD = "PerformerField";
    theClass.ATTR_TIMEFIELD = "TimeField";
    theClass.ATTR_FIXEDPOINTNUMBERFIELD = "FixedPointNumberField";
    theClass.ATTR_NUMBERFIELD = "NumberField";
    theClass.ATTR_COMPLEXLISTFIELD = "ComplexListField";
    theClass.ATTR_LISTFIXEDNUMBERTYPEDECL = "ListFixedNumberTypeDecl";
    theClass.ATTR_FIXEDNUMBERTYPEDECL = "FixedNumberTypeDecl";
    theClass.ATTR_TEXTFIELD = "TextField";
    theClass.ATTR_COPY_OF_PERFORMERFIELD = "Copy_Of_PerformerField";
    theClass.ATTR_COPY_OF_DATETIMEFIELD = "Copy_Of_DateTimeField";
    theClass.ATTR_COPY_OF_COMPLEXLISTFIELD = "Copy_Of_ComplexListField";
    theClass.ATTR_COPY_OF_CLASSFIELD = "Copy_Of_ClassField";
    theClass.ATTR_COPY_OF_BOOLEANFIELD = "Copy_Of_BooleanField";
    theClass.ATTR_COPY_OF_FIXEDPOINTNUMBERFIELD = "Copy_Of_FixedPointNumberField";
    theClass.ATTR_COPY_OF_FIXEDNUMBERTYPEDECL = "Copy_Of_FixedNumberTypeDecl";
    theClass.ATTR_COPY_OF_SIMPLELISTFIELD = "Copy_Of_SimpleListField";
    theClass.ATTR_COPY_OF_NUMBERFIELD = "Copy_Of_NumberField";
    theClass.ATTR_COPY_OF_TEXTFIELD = "Copy_Of_TextField";
    theClass.ATTR_COPY_OF_LISTFIXEDNUMBERTYPEDECL = "Copy_Of_ListFixedNumberTypeDecl";
    theClass.ATTR_COPY_OF_DATEFIELD = "Copy_Of_DateField";
    theClass.ATTR_COPY_OF_TIMEFIELD = "Copy_Of_TimeField";
    theClass.ATTR_MERGINGSIMPLELISTFIELD = "MergingSimpleListField";
    theClass.ATTR_MERGINGCOMPLEXLISTFIELD = "MergingComplexListField";
    theClass.ATTR_COPY_OF_MERGINGCOMPLEXLISTFIELD = "Copy_Of_MergingComplexListField";
    theClass.ATTR_COPY_OF_MERGINGSIMPLELISTFIELD = "Copy_Of_MergingSimpleListField";
    theClass.ATTR_COPY_OF_MERGINGPARTIALCOMPLEXLISTFIELD = "Copy_Of_MergingPartialComplexListField";
    theClass.ATTR_MERGINGPARTIALCOMPLEXLISTFIELD = "MergingPartialComplexListField";
    theClass.ATTR_CLASSFIELDTOINFLATE = "ClassFieldtoInflate";
    theClass.ATTR_COPY_OF_CLASSFIELDTOINFLATE = "Copy_Of_ClassFieldtoInflate";
    theClass.ATTR_TEXTFIELD2 = "TextField2";

    theClass.TYPE_ARRAY = new Object();
    theClass.TYPE_ARRAY[theClass.ATTR_BOOLEANFIELD] = {
        type: "BomPrimitiveTypes.Boolean",
        baseType: "BomPrimitiveTypes.Boolean",
        primitive: true,
        multivalued: false,
        required: true,
        defaultValue: ""
    };
    theClass.TYPE_ARRAY[theClass.ATTR_SIMPLELISTFIELD] = {
        type: "BomPrimitiveTypes.Text",
        baseType: "BomPrimitiveTypes.Text",
        primitive: true,
        multivalued: true,
        required: true,
        defaultValue: "",
        length: 50
    };
    theClass.TYPE_ARRAY[theClass.ATTR_DATETIMEFIELD] = {
        type: "BomPrimitiveTypes.DateTime",
        baseType: "BomPrimitiveTypes.DateTime",
        primitive: true,
        multivalued: false,
        required: true,
        defaultValue: ""
    };
    theClass.TYPE_ARRAY[theClass.ATTR_DATEFIELD] = {
        type: "BomPrimitiveTypes.Date",
        baseType: "BomPrimitiveTypes.Date",
        primitive: true,
        multivalued: false,
        required: true,
        defaultValue: ""
    };
    theClass.TYPE_ARRAY[theClass.ATTR_CLASSFIELD] = {
        type: "com.example.data.DataTypes",
        baseType: "com.example.data.DataTypes",
        primitive: false,
        multivalued: false,
        required: true,
        defaultValue: ""
    };
    theClass.TYPE_ARRAY[theClass.ATTR_PERFORMERFIELD] = {
        type: "BomPrimitiveTypes.Text",
        baseType: "BomPrimitiveTypes.Text",
        primitive: true,
        multivalued: false,
        required: true,
        defaultValue: "",
        length: -1
    };
    theClass.TYPE_ARRAY[theClass.ATTR_TIMEFIELD] = {
        type: "BomPrimitiveTypes.Time",
        baseType: "BomPrimitiveTypes.Time",
        primitive: true,
        multivalued: false,
        required: true,
        defaultValue: ""
    };
    theClass.TYPE_ARRAY[theClass.ATTR_FIXEDPOINTNUMBERFIELD] = {
        type: "BomPrimitiveTypes.Decimal",
        baseType: "BomPrimitiveTypes.Decimal",
        primitive: true,
        multivalued: false,
        required: true,
        defaultValue: "",
        length: 10,
        decimalPlaces: 2

    };
    theClass.TYPE_ARRAY[theClass.ATTR_NUMBERFIELD] = {
        type: "BomPrimitiveTypes.Decimal",
        baseType: "BomPrimitiveTypes.Decimal",
        primitive: true,
        multivalued: false,
        required: true,
        defaultValue: "",
        length: 0,
        decimalPlaces: 0

    };
    theClass.TYPE_ARRAY[theClass.ATTR_COMPLEXLISTFIELD] = {
        type: "com.example.data.DataTypes",
        baseType: "com.example.data.DataTypes",
        primitive: false,
        multivalued: true,
        required: true,
        defaultValue: ""
    };
    theClass.TYPE_ARRAY[theClass.ATTR_LISTFIXEDNUMBERTYPEDECL] = {
        type: "BomPrimitiveTypes.Decimal",
        baseType: "BomPrimitiveTypes.Decimal",
        primitive: true,
        multivalued: true,
        required: true,
        defaultValue: "",
        length: 10,
        decimalPlaces: 2

    };
    theClass.TYPE_ARRAY[theClass.ATTR_FIXEDNUMBERTYPEDECL] = {
        type: "BomPrimitiveTypes.Decimal",
        baseType: "BomPrimitiveTypes.Decimal",
        primitive: true,
        multivalued: false,
        required: true,
        defaultValue: "",
        length: 10,
        decimalPlaces: 2

    };
    theClass.TYPE_ARRAY[theClass.ATTR_TEXTFIELD] = {
        type: "BomPrimitiveTypes.Text",
        baseType: "BomPrimitiveTypes.Text",
        primitive: true,
        multivalued: false,
        required: true,
        defaultValue: "",
        length: 50
    };
    theClass.TYPE_ARRAY[theClass.ATTR_COPY_OF_PERFORMERFIELD] = {
        type: "BomPrimitiveTypes.Text",
        baseType: "BomPrimitiveTypes.Text",
        primitive: true,
        multivalued: false,
        required: true,
        defaultValue: "",
        length: -1
    };
    theClass.TYPE_ARRAY[theClass.ATTR_COPY_OF_DATETIMEFIELD] = {
        type: "BomPrimitiveTypes.DateTime",
        baseType: "BomPrimitiveTypes.DateTime",
        primitive: true,
        multivalued: false,
        required: true,
        defaultValue: ""
    };
    theClass.TYPE_ARRAY[theClass.ATTR_COPY_OF_COMPLEXLISTFIELD] = {
        type: "com.example.data.DataTypes",
        baseType: "com.example.data.DataTypes",
        primitive: false,
        multivalued: true,
        required: true,
        defaultValue: ""
    };
    theClass.TYPE_ARRAY[theClass.ATTR_COPY_OF_CLASSFIELD] = {
        type: "com.example.data.DataTypes",
        baseType: "com.example.data.DataTypes",
        primitive: false,
        multivalued: false,
        required: true,
        defaultValue: ""
    };
    theClass.TYPE_ARRAY[theClass.ATTR_COPY_OF_BOOLEANFIELD] = {
        type: "BomPrimitiveTypes.Boolean",
        baseType: "BomPrimitiveTypes.Boolean",
        primitive: true,
        multivalued: false,
        required: true,
        defaultValue: ""
    };
    theClass.TYPE_ARRAY[theClass.ATTR_COPY_OF_FIXEDPOINTNUMBERFIELD] = {
        type: "BomPrimitiveTypes.Decimal",
        baseType: "BomPrimitiveTypes.Decimal",
        primitive: true,
        multivalued: false,
        required: true,
        defaultValue: "",
        length: 10,
        decimalPlaces: 2

    };
    theClass.TYPE_ARRAY[theClass.ATTR_COPY_OF_FIXEDNUMBERTYPEDECL] = {
        type: "BomPrimitiveTypes.Decimal",
        baseType: "BomPrimitiveTypes.Decimal",
        primitive: true,
        multivalued: false,
        required: true,
        defaultValue: "",
        length: 10,
        decimalPlaces: 2

    };
    theClass.TYPE_ARRAY[theClass.ATTR_COPY_OF_SIMPLELISTFIELD] = {
        type: "BomPrimitiveTypes.Text",
        baseType: "BomPrimitiveTypes.Text",
        primitive: true,
        multivalued: true,
        required: true,
        defaultValue: "",
        length: 50
    };
    theClass.TYPE_ARRAY[theClass.ATTR_COPY_OF_NUMBERFIELD] = {
        type: "BomPrimitiveTypes.Decimal",
        baseType: "BomPrimitiveTypes.Decimal",
        primitive: true,
        multivalued: false,
        required: true,
        defaultValue: "",
        length: 0,
        decimalPlaces: 0

    };
    theClass.TYPE_ARRAY[theClass.ATTR_COPY_OF_TEXTFIELD] = {
        type: "BomPrimitiveTypes.Text",
        baseType: "BomPrimitiveTypes.Text",
        primitive: true,
        multivalued: false,
        required: true,
        defaultValue: "",
        length: 50
    };
    theClass.TYPE_ARRAY[theClass.ATTR_COPY_OF_LISTFIXEDNUMBERTYPEDECL] = {
        type: "BomPrimitiveTypes.Decimal",
        baseType: "BomPrimitiveTypes.Decimal",
        primitive: true,
        multivalued: true,
        required: true,
        defaultValue: "",
        length: 10,
        decimalPlaces: 2

    };
    theClass.TYPE_ARRAY[theClass.ATTR_COPY_OF_DATEFIELD] = {
        type: "BomPrimitiveTypes.Date",
        baseType: "BomPrimitiveTypes.Date",
        primitive: true,
        multivalued: false,
        required: true,
        defaultValue: ""
    };
    theClass.TYPE_ARRAY[theClass.ATTR_COPY_OF_TIMEFIELD] = {
        type: "BomPrimitiveTypes.Time",
        baseType: "BomPrimitiveTypes.Time",
        primitive: true,
        multivalued: false,
        required: true,
        defaultValue: ""
    };
    theClass.TYPE_ARRAY[theClass.ATTR_MERGINGSIMPLELISTFIELD] = {
        type: "BomPrimitiveTypes.Text",
        baseType: "BomPrimitiveTypes.Text",
        primitive: true,
        multivalued: true,
        required: true,
        defaultValue: "",
        length: 50
    };
    theClass.TYPE_ARRAY[theClass.ATTR_MERGINGCOMPLEXLISTFIELD] = {
        type: "com.example.data.DataTypes",
        baseType: "com.example.data.DataTypes",
        primitive: false,
        multivalued: true,
        required: true,
        defaultValue: ""
    };
    theClass.TYPE_ARRAY[theClass.ATTR_COPY_OF_MERGINGCOMPLEXLISTFIELD] = {
        type: "com.example.data.DataTypes",
        baseType: "com.example.data.DataTypes",
        primitive: false,
        multivalued: true,
        required: true,
        defaultValue: ""
    };
    theClass.TYPE_ARRAY[theClass.ATTR_COPY_OF_MERGINGSIMPLELISTFIELD] = {
        type: "BomPrimitiveTypes.Text",
        baseType: "BomPrimitiveTypes.Text",
        primitive: true,
        multivalued: true,
        required: true,
        defaultValue: "",
        length: 50
    };
    theClass.TYPE_ARRAY[theClass.ATTR_COPY_OF_MERGINGPARTIALCOMPLEXLISTFIELD] = {
        type: "com.example.data.DataTypes",
        baseType: "com.example.data.DataTypes",
        primitive: false,
        multivalued: true,
        required: true,
        defaultValue: ""
    };
    theClass.TYPE_ARRAY[theClass.ATTR_MERGINGPARTIALCOMPLEXLISTFIELD] = {
        type: "com.example.data.DataTypes",
        baseType: "com.example.data.DataTypes",
        primitive: false,
        multivalued: true,
        required: true,
        defaultValue: ""
    };
    theClass.TYPE_ARRAY[theClass.ATTR_CLASSFIELDTOINFLATE] = {
        type: "com.example.data.DataTypes",
        baseType: "com.example.data.DataTypes",
        primitive: false,
        multivalued: false,
        required: true,
        defaultValue: ""
    };
    theClass.TYPE_ARRAY[theClass.ATTR_COPY_OF_CLASSFIELDTOINFLATE] = {
        type: "com.example.data.DataTypes",
        baseType: "com.example.data.DataTypes",
        primitive: false,
        multivalued: false,
        required: true,
        defaultValue: ""
    };
    theClass.TYPE_ARRAY[theClass.ATTR_TEXTFIELD2] = {
        type: "BomPrimitiveTypes.Text",
        baseType: "BomPrimitiveTypes.Text",
        primitive: true,
        multivalued: false,
        required: true,
        defaultValue: "",
        length: 50
    };

    theClass.PRIMITIVE_ATTRIBUTE_NAMES = [
        theClass.ATTR_BOOLEANFIELD,
        theClass.ATTR_SIMPLELISTFIELD,
        theClass.ATTR_DATETIMEFIELD,
        theClass.ATTR_DATEFIELD,
        theClass.ATTR_PERFORMERFIELD,
        theClass.ATTR_TIMEFIELD,
        theClass.ATTR_FIXEDPOINTNUMBERFIELD,
        theClass.ATTR_NUMBERFIELD,
        theClass.ATTR_LISTFIXEDNUMBERTYPEDECL,
        theClass.ATTR_FIXEDNUMBERTYPEDECL,
        theClass.ATTR_TEXTFIELD,
        theClass.ATTR_COPY_OF_PERFORMERFIELD,
        theClass.ATTR_COPY_OF_DATETIMEFIELD,
        theClass.ATTR_COPY_OF_BOOLEANFIELD,
        theClass.ATTR_COPY_OF_FIXEDPOINTNUMBERFIELD,
        theClass.ATTR_COPY_OF_FIXEDNUMBERTYPEDECL,
        theClass.ATTR_COPY_OF_SIMPLELISTFIELD,
        theClass.ATTR_COPY_OF_NUMBERFIELD,
        theClass.ATTR_COPY_OF_TEXTFIELD,
        theClass.ATTR_COPY_OF_LISTFIXEDNUMBERTYPEDECL,
        theClass.ATTR_COPY_OF_DATEFIELD,
        theClass.ATTR_COPY_OF_TIMEFIELD,
        theClass.ATTR_MERGINGSIMPLELISTFIELD,
        theClass.ATTR_COPY_OF_MERGINGSIMPLELISTFIELD,
        theClass.ATTR_TEXTFIELD2
    ];
    theClass.COMPOSITE_ATTRIBUTE_NAMES = [
        theClass.ATTR_CLASSFIELD,
        theClass.ATTR_COMPLEXLISTFIELD,
        theClass.ATTR_COPY_OF_COMPLEXLISTFIELD,
        theClass.ATTR_COPY_OF_CLASSFIELD,
        theClass.ATTR_MERGINGCOMPLEXLISTFIELD,
        theClass.ATTR_COPY_OF_MERGINGCOMPLEXLISTFIELD,
        theClass.ATTR_COPY_OF_MERGINGPARTIALCOMPLEXLISTFIELD,
        theClass.ATTR_MERGINGPARTIALCOMPLEXLISTFIELD,
        theClass.ATTR_CLASSFIELDTOINFLATE,
        theClass.ATTR_COPY_OF_CLASSFIELDTOINFLATE
    ];
    theClass.ATTRIBUTE_NAMES = [
        theClass.ATTR_BOOLEANFIELD,
        theClass.ATTR_SIMPLELISTFIELD,
        theClass.ATTR_DATETIMEFIELD,
        theClass.ATTR_DATEFIELD,
        theClass.ATTR_CLASSFIELD,
        theClass.ATTR_PERFORMERFIELD,
        theClass.ATTR_TIMEFIELD,
        theClass.ATTR_FIXEDPOINTNUMBERFIELD,
        theClass.ATTR_NUMBERFIELD,
        theClass.ATTR_COMPLEXLISTFIELD,
        theClass.ATTR_LISTFIXEDNUMBERTYPEDECL,
        theClass.ATTR_FIXEDNUMBERTYPEDECL,
        theClass.ATTR_TEXTFIELD,
        theClass.ATTR_COPY_OF_PERFORMERFIELD,
        theClass.ATTR_COPY_OF_DATETIMEFIELD,
        theClass.ATTR_COPY_OF_COMPLEXLISTFIELD,
        theClass.ATTR_COPY_OF_CLASSFIELD,
        theClass.ATTR_COPY_OF_BOOLEANFIELD,
        theClass.ATTR_COPY_OF_FIXEDPOINTNUMBERFIELD,
        theClass.ATTR_COPY_OF_FIXEDNUMBERTYPEDECL,
        theClass.ATTR_COPY_OF_SIMPLELISTFIELD,
        theClass.ATTR_COPY_OF_NUMBERFIELD,
        theClass.ATTR_COPY_OF_TEXTFIELD,
        theClass.ATTR_COPY_OF_LISTFIXEDNUMBERTYPEDECL,
        theClass.ATTR_COPY_OF_DATEFIELD,
        theClass.ATTR_COPY_OF_TIMEFIELD,
        theClass.ATTR_MERGINGSIMPLELISTFIELD,
        theClass.ATTR_MERGINGCOMPLEXLISTFIELD,
        theClass.ATTR_COPY_OF_MERGINGCOMPLEXLISTFIELD,
        theClass.ATTR_COPY_OF_MERGINGSIMPLELISTFIELD,
        theClass.ATTR_COPY_OF_MERGINGPARTIALCOMPLEXLISTFIELD,
        theClass.ATTR_MERGINGPARTIALCOMPLEXLISTFIELD,
        theClass.ATTR_CLASSFIELDTOINFLATE,
        theClass.ATTR_COPY_OF_CLASSFIELDTOINFLATE,
        theClass.ATTR_TEXTFIELD2
    ];

    theClass.getName = function() {
        return "$Process.Data";
    };


    Object.defineProperty(theClass.prototype, 'BooleanField', {
        get: function() {
            return this._getPrimitiveAttribute(this.loader.getClass("$Process.Data").ATTR_BOOLEANFIELD);
        },
        set: function(BooleanField) {
            this._setPrimitiveAttribute(this.loader.getClass("$Process.Data").ATTR_BOOLEANFIELD, BooleanField);
        },
        enumerable: true
    });


    Object.defineProperty(theClass.prototype, 'SimpleListField', {
        get: function() {
            return this._getPrimitiveArrayAttribute(this.loader.getClass("$Process.Data").ATTR_SIMPLELISTFIELD);
        },
        set: function(SimpleListField) {
            this._setPrimitiveAttribute(this.loader.getClass("$Process.Data").ATTR_SIMPLELISTFIELD, SimpleListField);
        },
        enumerable: true
    });


    Object.defineProperty(theClass.prototype, 'DateTimeField', {
        get: function() {
            return this._getPrimitiveAttribute(this.loader.getClass("$Process.Data").ATTR_DATETIMEFIELD);
        },
        set: function(DateTimeField) {
            this._setPrimitiveAttribute(this.loader.getClass("$Process.Data").ATTR_DATETIMEFIELD, DateTimeField);
        },
        enumerable: true
    });


    Object.defineProperty(theClass.prototype, 'DateField', {
        get: function() {
            return this._getPrimitiveAttribute(this.loader.getClass("$Process.Data").ATTR_DATEFIELD);
        },
        set: function(DateField) {
            this._setPrimitiveAttribute(this.loader.getClass("$Process.Data").ATTR_DATEFIELD, DateField);
        },
        enumerable: true
    });


    Object.defineProperty(theClass.prototype, 'ClassField', {
        get: function() {
            return this._getComplexAttribute(this.loader.getClass("$Process.Data").ATTR_CLASSFIELD);
        },
        set: function(ClassField) {
            var classRef = this.loader.getClass("$Process.Data");
            var attrRef = classRef.ATTR_CLASSFIELD;
            var attrType = classRef._getTypeDef(attrRef);
            if (eval("ClassField instanceof this.loader.getClass(attrType.type)")) {
                this._setComplexAttribute(attrRef, ClassField);
            } else {
                throw "Wrong input object type.";
            }
        },
        enumerable: true
    });


    Object.defineProperty(theClass.prototype, 'PerformerField', {
        get: function() {
            return this._getPrimitiveAttribute(this.loader.getClass("$Process.Data").ATTR_PERFORMERFIELD);
        },
        set: function(PerformerField) {
            this._setPrimitiveAttribute(this.loader.getClass("$Process.Data").ATTR_PERFORMERFIELD, PerformerField);
        },
        enumerable: true
    });


    Object.defineProperty(theClass.prototype, 'TimeField', {
        get: function() {
            return this._getPrimitiveAttribute(this.loader.getClass("$Process.Data").ATTR_TIMEFIELD);
        },
        set: function(TimeField) {
            this._setPrimitiveAttribute(this.loader.getClass("$Process.Data").ATTR_TIMEFIELD, TimeField);
        },
        enumerable: true
    });


    Object.defineProperty(theClass.prototype, 'FixedPointNumberField', {
        get: function() {
            return this._getPrimitiveAttribute(this.loader.getClass("$Process.Data").ATTR_FIXEDPOINTNUMBERFIELD);
        },
        set: function(FixedPointNumberField) {
            this._setPrimitiveAttribute(this.loader.getClass("$Process.Data").ATTR_FIXEDPOINTNUMBERFIELD, FixedPointNumberField);
        },
        enumerable: true
    });


    Object.defineProperty(theClass.prototype, 'NumberField', {
        get: function() {
            return this._getPrimitiveAttribute(this.loader.getClass("$Process.Data").ATTR_NUMBERFIELD);
        },
        set: function(NumberField) {
            this._setPrimitiveAttribute(this.loader.getClass("$Process.Data").ATTR_NUMBERFIELD, NumberField);
        },
        enumerable: true
    });


    Object.defineProperty(theClass.prototype, 'ComplexListField', {
        get: function() {
            return this._getComplexArrayAttribute(this.loader.getClass("$Process.Data").ATTR_COMPLEXLISTFIELD);
        },
        set: function(ComplexListField) {
            throw "Unsupported complex array attribute: ComplexListField";
        },
        enumerable: true
    });


    Object.defineProperty(theClass.prototype, 'ListFixedNumberTypeDecl', {
        get: function() {
            return this._getPrimitiveArrayAttribute(this.loader.getClass("$Process.Data").ATTR_LISTFIXEDNUMBERTYPEDECL);
        },
        set: function(ListFixedNumberTypeDecl) {
            this._setPrimitiveAttribute(this.loader.getClass("$Process.Data").ATTR_LISTFIXEDNUMBERTYPEDECL, ListFixedNumberTypeDecl);
        },
        enumerable: true
    });


    Object.defineProperty(theClass.prototype, 'FixedNumberTypeDecl', {
        get: function() {
            return this._getPrimitiveAttribute(this.loader.getClass("$Process.Data").ATTR_FIXEDNUMBERTYPEDECL);
        },
        set: function(FixedNumberTypeDecl) {
            this._setPrimitiveAttribute(this.loader.getClass("$Process.Data").ATTR_FIXEDNUMBERTYPEDECL, FixedNumberTypeDecl);
        },
        enumerable: true
    });


    Object.defineProperty(theClass.prototype, 'TextField', {
        get: function() {
            return this._getPrimitiveAttribute(this.loader.getClass("$Process.Data").ATTR_TEXTFIELD);
        },
        set: function(TextField) {
            this._setPrimitiveAttribute(this.loader.getClass("$Process.Data").ATTR_TEXTFIELD, TextField);
        },
        enumerable: true
    });


    Object.defineProperty(theClass.prototype, 'Copy_Of_PerformerField', {
        get: function() {
            return this._getPrimitiveAttribute(this.loader.getClass("$Process.Data").ATTR_COPY_OF_PERFORMERFIELD);
        },
        set: function(Copy_Of_PerformerField) {
            this._setPrimitiveAttribute(this.loader.getClass("$Process.Data").ATTR_COPY_OF_PERFORMERFIELD, Copy_Of_PerformerField);
        },
        enumerable: true
    });


    Object.defineProperty(theClass.prototype, 'Copy_Of_DateTimeField', {
        get: function() {
            return this._getPrimitiveAttribute(this.loader.getClass("$Process.Data").ATTR_COPY_OF_DATETIMEFIELD);
        },
        set: function(Copy_Of_DateTimeField) {
            this._setPrimitiveAttribute(this.loader.getClass("$Process.Data").ATTR_COPY_OF_DATETIMEFIELD, Copy_Of_DateTimeField);
        },
        enumerable: true
    });


    Object.defineProperty(theClass.prototype, 'Copy_Of_ComplexListField', {
        get: function() {
            return this._getComplexArrayAttribute(this.loader.getClass("$Process.Data").ATTR_COPY_OF_COMPLEXLISTFIELD);
        },
        set: function(Copy_Of_ComplexListField) {
            throw "Unsupported complex array attribute: Copy_Of_ComplexListField";
        },
        enumerable: true
    });


    Object.defineProperty(theClass.prototype, 'Copy_Of_ClassField', {
        get: function() {
            return this._getComplexAttribute(this.loader.getClass("$Process.Data").ATTR_COPY_OF_CLASSFIELD);
        },
        set: function(Copy_Of_ClassField) {
            var classRef = this.loader.getClass("$Process.Data");
            var attrRef = classRef.ATTR_COPY_OF_CLASSFIELD;
            var attrType = classRef._getTypeDef(attrRef);
            if (eval("Copy_Of_ClassField instanceof this.loader.getClass(attrType.type)")) {
                this._setComplexAttribute(attrRef, Copy_Of_ClassField);
            } else {
                throw "Wrong input object type.";
            }
        },
        enumerable: true
    });


    Object.defineProperty(theClass.prototype, 'Copy_Of_BooleanField', {
        get: function() {
            return this._getPrimitiveAttribute(this.loader.getClass("$Process.Data").ATTR_COPY_OF_BOOLEANFIELD);
        },
        set: function(Copy_Of_BooleanField) {
            this._setPrimitiveAttribute(this.loader.getClass("$Process.Data").ATTR_COPY_OF_BOOLEANFIELD, Copy_Of_BooleanField);
        },
        enumerable: true
    });


    Object.defineProperty(theClass.prototype, 'Copy_Of_FixedPointNumberField', {
        get: function() {
            return this._getPrimitiveAttribute(this.loader.getClass("$Process.Data").ATTR_COPY_OF_FIXEDPOINTNUMBERFIELD);
        },
        set: function(Copy_Of_FixedPointNumberField) {
            this._setPrimitiveAttribute(this.loader.getClass("$Process.Data").ATTR_COPY_OF_FIXEDPOINTNUMBERFIELD, Copy_Of_FixedPointNumberField);
        },
        enumerable: true
    });


    Object.defineProperty(theClass.prototype, 'Copy_Of_FixedNumberTypeDecl', {
        get: function() {
            return this._getPrimitiveAttribute(this.loader.getClass("$Process.Data").ATTR_COPY_OF_FIXEDNUMBERTYPEDECL);
        },
        set: function(Copy_Of_FixedNumberTypeDecl) {
            this._setPrimitiveAttribute(this.loader.getClass("$Process.Data").ATTR_COPY_OF_FIXEDNUMBERTYPEDECL, Copy_Of_FixedNumberTypeDecl);
        },
        enumerable: true
    });


    Object.defineProperty(theClass.prototype, 'Copy_Of_SimpleListField', {
        get: function() {
            return this._getPrimitiveArrayAttribute(this.loader.getClass("$Process.Data").ATTR_COPY_OF_SIMPLELISTFIELD);
        },
        set: function(Copy_Of_SimpleListField) {
            this._setPrimitiveAttribute(this.loader.getClass("$Process.Data").ATTR_COPY_OF_SIMPLELISTFIELD, Copy_Of_SimpleListField);
        },
        enumerable: true
    });


    Object.defineProperty(theClass.prototype, 'Copy_Of_NumberField', {
        get: function() {
            return this._getPrimitiveAttribute(this.loader.getClass("$Process.Data").ATTR_COPY_OF_NUMBERFIELD);
        },
        set: function(Copy_Of_NumberField) {
            this._setPrimitiveAttribute(this.loader.getClass("$Process.Data").ATTR_COPY_OF_NUMBERFIELD, Copy_Of_NumberField);
        },
        enumerable: true
    });


    Object.defineProperty(theClass.prototype, 'Copy_Of_TextField', {
        get: function() {
            return this._getPrimitiveAttribute(this.loader.getClass("$Process.Data").ATTR_COPY_OF_TEXTFIELD);
        },
        set: function(Copy_Of_TextField) {
            this._setPrimitiveAttribute(this.loader.getClass("$Process.Data").ATTR_COPY_OF_TEXTFIELD, Copy_Of_TextField);
        },
        enumerable: true
    });


    Object.defineProperty(theClass.prototype, 'Copy_Of_ListFixedNumberTypeDecl', {
        get: function() {
            return this._getPrimitiveArrayAttribute(this.loader.getClass("$Process.Data").ATTR_COPY_OF_LISTFIXEDNUMBERTYPEDECL);
        },
        set: function(Copy_Of_ListFixedNumberTypeDecl) {
            this._setPrimitiveAttribute(this.loader.getClass("$Process.Data").ATTR_COPY_OF_LISTFIXEDNUMBERTYPEDECL, Copy_Of_ListFixedNumberTypeDecl);
        },
        enumerable: true
    });


    Object.defineProperty(theClass.prototype, 'Copy_Of_DateField', {
        get: function() {
            return this._getPrimitiveAttribute(this.loader.getClass("$Process.Data").ATTR_COPY_OF_DATEFIELD);
        },
        set: function(Copy_Of_DateField) {
            this._setPrimitiveAttribute(this.loader.getClass("$Process.Data").ATTR_COPY_OF_DATEFIELD, Copy_Of_DateField);
        },
        enumerable: true
    });


    Object.defineProperty(theClass.prototype, 'Copy_Of_TimeField', {
        get: function() {
            return this._getPrimitiveAttribute(this.loader.getClass("$Process.Data").ATTR_COPY_OF_TIMEFIELD);
        },
        set: function(Copy_Of_TimeField) {
            this._setPrimitiveAttribute(this.loader.getClass("$Process.Data").ATTR_COPY_OF_TIMEFIELD, Copy_Of_TimeField);
        },
        enumerable: true
    });


    Object.defineProperty(theClass.prototype, 'MergingSimpleListField', {
        get: function() {
            return this._getPrimitiveArrayAttribute(this.loader.getClass("$Process.Data").ATTR_MERGINGSIMPLELISTFIELD);
        },
        set: function(MergingSimpleListField) {
            this._setPrimitiveAttribute(this.loader.getClass("$Process.Data").ATTR_MERGINGSIMPLELISTFIELD, MergingSimpleListField);
        },
        enumerable: true
    });


    Object.defineProperty(theClass.prototype, 'MergingComplexListField', {
        get: function() {
            return this._getComplexArrayAttribute(this.loader.getClass("$Process.Data").ATTR_MERGINGCOMPLEXLISTFIELD);
        },
        set: function(MergingComplexListField) {
            throw "Unsupported complex array attribute: MergingComplexListField";
        },
        enumerable: true
    });


    Object.defineProperty(theClass.prototype, 'Copy_Of_MergingComplexListField', {
        get: function() {
            return this._getComplexArrayAttribute(this.loader.getClass("$Process.Data").ATTR_COPY_OF_MERGINGCOMPLEXLISTFIELD);
        },
        set: function(Copy_Of_MergingComplexListField) {
            throw "Unsupported complex array attribute: Copy_Of_MergingComplexListField";
        },
        enumerable: true
    });


    Object.defineProperty(theClass.prototype, 'Copy_Of_MergingSimpleListField', {
        get: function() {
            return this._getPrimitiveArrayAttribute(this.loader.getClass("$Process.Data").ATTR_COPY_OF_MERGINGSIMPLELISTFIELD);
        },
        set: function(Copy_Of_MergingSimpleListField) {
            this._setPrimitiveAttribute(this.loader.getClass("$Process.Data").ATTR_COPY_OF_MERGINGSIMPLELISTFIELD, Copy_Of_MergingSimpleListField);
        },
        enumerable: true
    });


    Object.defineProperty(theClass.prototype, 'Copy_Of_MergingPartialComplexListField', {
        get: function() {
            return this._getComplexArrayAttribute(this.loader.getClass("$Process.Data").ATTR_COPY_OF_MERGINGPARTIALCOMPLEXLISTFIELD);
        },
        set: function(Copy_Of_MergingPartialComplexListField) {
            throw "Unsupported complex array attribute: Copy_Of_MergingPartialComplexListField";
        },
        enumerable: true
    });


    Object.defineProperty(theClass.prototype, 'MergingPartialComplexListField', {
        get: function() {
            return this._getComplexArrayAttribute(this.loader.getClass("$Process.Data").ATTR_MERGINGPARTIALCOMPLEXLISTFIELD);
        },
        set: function(MergingPartialComplexListField) {
            throw "Unsupported complex array attribute: MergingPartialComplexListField";
        },
        enumerable: true
    });


    Object.defineProperty(theClass.prototype, 'ClassFieldtoInflate', {
        get: function() {
            return this._getComplexAttribute(this.loader.getClass("$Process.Data").ATTR_CLASSFIELDTOINFLATE);
        },
        set: function(ClassFieldtoInflate) {
            var classRef = this.loader.getClass("$Process.Data");
            var attrRef = classRef.ATTR_CLASSFIELDTOINFLATE;
            var attrType = classRef._getTypeDef(attrRef);
            if (eval("ClassFieldtoInflate instanceof this.loader.getClass(attrType.type)")) {
                this._setComplexAttribute(attrRef, ClassFieldtoInflate);
            } else {
                throw "Wrong input object type.";
            }
        },
        enumerable: true
    });


    Object.defineProperty(theClass.prototype, 'Copy_Of_ClassFieldtoInflate', {
        get: function() {
            return this._getComplexAttribute(this.loader.getClass("$Process.Data").ATTR_COPY_OF_CLASSFIELDTOINFLATE);
        },
        set: function(Copy_Of_ClassFieldtoInflate) {
            var classRef = this.loader.getClass("$Process.Data");
            var attrRef = classRef.ATTR_COPY_OF_CLASSFIELDTOINFLATE;
            var attrType = classRef._getTypeDef(attrRef);
            if (eval("Copy_Of_ClassFieldtoInflate instanceof this.loader.getClass(attrType.type)")) {
                this._setComplexAttribute(attrRef, Copy_Of_ClassFieldtoInflate);
            } else {
                throw "Wrong input object type.";
            }
        },
        enumerable: true
    });


    Object.defineProperty(theClass.prototype, 'TextField2', {
        get: function() {
            return this._getPrimitiveAttribute(this.loader.getClass("$Process.Data").ATTR_TEXTFIELD2);
        },
        set: function(TextField2) {
            this._setPrimitiveAttribute(this.loader.getClass("$Process.Data").ATTR_TEXTFIELD2, TextField2);
        },
        enumerable: true
    });

};

com.tibco.data.Loader.classDefiner["$Process.Data"]();
