<?xml version="1.0" encoding="UTF-8"?>
<!--
	===================================================================================================================
	XSLT:       MigrateStudioXPDL_6.xslt
	
	DESCRIPTION:

  	    This xslt will updgrade a xpdl of format version 7 (v3.2.0) to format version 8 (3.3.0 a2+)
  	     (See XpdlMigrate.java for format version <-> Studio version equivalence).

	===================================================================================================================
-->

<xsl:stylesheet version="1.0"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:xpdExt="http://www.tibco.com/XPD/xpdExtension1.0.0"
	xmlns:eaijava="http://www.tibco.com/XPD/EAIJava1.0.0" 
	xmlns:simulation="http://www.tibco.com/xpd/Simulation1.0.1"
	xmlns:database="http://www.tibco.com/XPD/database1.0.0"
	xmlns:email="http://www.tibco.com/XPD/email1.0.0"
	xmlns:iProcessExt="http://www.tibco.com/XPD/iProcessExt1.0.0"	
	xmlns:java="http://xml.apache.org/xslt/java"

	xmlns:xpdl2="http://www.wfmc.org/2008/XPDL2.1"
	
	exclude-result-prefixes="java"
	>

	<xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes" />

	<!--
		===============================================================================
		XSLT processing start point (process whole document)
		===============================================================================
	-->
	<xsl:template match="/">
		<xsl:apply-templates />
	</xsl:template>

	<xsl:template match="xpdl2:ExtendedAttribute[@Name = 'FormatVersion']">
		<xsl:element name="xpdl2:ExtendedAttribute">
			<xsl:attribute name="Name">FormatVersion</xsl:attribute>
			<xsl:attribute name="Value">8</xsl:attribute>
		</xsl:element>
	</xsl:template>

	<!--
        ===============================================================================
        Convert process data side of DataMappings to new BOM ConceptPath notation.
        ===============================================================================
    -->
    <xsl:template match="xpdl2:DataMapping/xpdl2:Actual | xpdExt:DataMapping/xpdl2:Actual">
		<xsl:copy>
			<xsl:apply-templates select="@*"/>
			
			<xsl:variable name="newValue">
				<!-- Ignore user-defined-script mappings on INPUT to external app (WSDL operation,. sub-process, java service etc). -->
				<xsl:if test="count(../xpdExt:ScriptInformation) = 0 or ../@Direction != 'IN'">
					<xsl:choose>
						<!-- 
							Handle DataMappings in Web service related events/tasks (any tasksend/service/receive with webservice or m essage event implementation 
								(or any reply activity is inherently a message to).
						-->
						<xsl:when test="ancestor::xpdl2:*/@xpdExt:ImplementationType = 'WebService' 
								or ancestor::xpdl2:*/@xpdExt:ImplementationType = 'Web-Service'
								or count(ancestor::xpdl2:*/@xpdExt:ReplyToActivityId) > 0">
							<xsl:value-of select="java:com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessDataUtil.convertV32ConceptPath(.)"/>
						</xsl:when>
						<!-- 
							Handle DataMappings Catch Error Events
						-->
						<xsl:when test="ancestor::xpdExt:CatchErrorMappings">
							<xsl:value-of select="java:com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessDataUtil.convertV32ConceptPath(text())"/>
						</xsl:when>
						<!-- 
							Handle DataMappings in Sub-Process (Main Process Side) mappings.
						-->
						<xsl:when test="ancestor::xpdl2:SubFlow">
							<xsl:value-of select="java:com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessDataUtil.convertV32ConceptPath(text())"/>
						</xsl:when>

						<!--
							Handle DataMappings in Transform Scripts Actual (Formal handled in template below).
						-->
						<xsl:when test="ancestor::xpdl2:*/@ScriptGrammar = 'Transform'">
							<xsl:value-of select="java:com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessDataUtil.convertV32ConceptPath(text())"/>
						</xsl:when>
	
						<!-- 
							Handle DataMappings in Java Service tasks.
							THESE ARE SLIGHTLY DIFFERENT - being standard BOM Path PREFIXED with "process."
						-->
						<xsl:when test="ancestor::xpdl2:*/@xpdExt:ImplementationType = 'Java'">
							<xsl:variable name="nameMinusProcess">
								<xsl:choose>
									<xsl:when test="starts-with(text(), 'process.')">
										<xsl:value-of select="substring-after(text(), 'process.')"/>
									</xsl:when>
									<xsl:otherwise>
										<xsl:value-of select="text()"/>
									</xsl:otherwise>
								</xsl:choose>
							</xsl:variable>
							
							<xsl:value-of select="java:com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessDataUtil.convertV32ConceptPath($nameMinusProcess)" />
							
						</xsl:when>
					</xsl:choose>
				</xsl:if>
			</xsl:variable>    
	
			<!-- 
				If we have converted the value, then output new value else copy original.
			-->
			<xsl:choose>
				<xsl:when test="$newValue != ''">
					<xsl:value-of select="$newValue"/>
				</xsl:when>
				<xsl:otherwise>
					<!-- otherwise just copy original content. -->
					<xsl:apply-templates select="node()"/>
				</xsl:otherwise>
			</xsl:choose>

		</xsl:copy>
		
    </xsl:template>

	<!--
        ===============================================================================
		Handle DataMappings in Transform Scripts (need to change xpdl2:Actual AND Formal) 
        ===============================================================================
    -->
    <xsl:template match="xpdExt:DataMapping[ancestor::xpdl2:*/@ScriptGrammar = 'Transform']/@Formal">
		<xsl:attribute name="Formal"><xsl:value-of select="java:com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessDataUtil.convertV32ConceptPath(.)"/></xsl:attribute>
	</xsl:template>

	<!--
        ===============================================================================
		Move xpdl2:DataField/xpdl2:InitialValues to xpdl2:DataFields/xpdExt:InitialValues
        ===============================================================================
    -->
	<xsl:template match="xpdl2:DataField">
		<xsl:copy>
			<xsl:apply-templates select="@*"/>
			<xsl:apply-templates select="*[local-name() != 'InitialValue']"/>
			
			<xsl:if test="xpdl2:InitialValue != ''">
				<xpdExt:InitialValues>
					<!-- If this is an array field with multiple values and then create separate en tries for each ~ separated value -->
					<xsl:choose>
						<xsl:when test="@IsArray='true' and contains(xpdl2:InitialValue, '~')">
							<xsl:call-template name="recursiveSplitInitialValues"><xsl:with-param name="initialValues" select="xpdl2:InitialValue"/></xsl:call-template>
						</xsl:when>
						
						<xsl:otherwise>
							<xpdExt:Value><xsl:value-of select="xpdl2:InitialValue"/></xpdExt:Value>
						</xsl:otherwise>
					</xsl:choose>
				</xpdExt:InitialValues>
			</xsl:if>
		</xsl:copy>
	</xsl:template>
	
	<xsl:template name="recursiveSplitInitialValues">
		<xsl:param name="initialValues"/>
			
		<xsl:if test="$initialValues != ''">
			<xsl:choose>
				<xsl:when test="contains($initialValues, '~')">
					<xsl:variable name="before" select="substring-before($initialValues, '~')"/>
					
					<xpdExt:Value><xsl:value-of select="$before"/></xpdExt:Value>
					
					<xsl:variable name="after" select="substring-after($initialValues, '~')"/>
					<xsl:call-template name="recursiveSplitInitialValues"><xsl:with-param name="initialValues" select="$after"/></xsl:call-template>
				</xsl:when>
				
				<xsl:otherwise>
					<xpdExt:Value><xsl:value-of select="$initialValues"/></xpdExt:Value>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:if>
		
	</xsl:template>


	<!--
        ===============================================================================
       Default copy all template.
        ===============================================================================
    -->
	<xsl:template match="node()" priority="-9">
		<xsl:if test="name() != ''">
			<xsl:copy>
				<xsl:apply-templates select="@* | node() | text()"/>
			</xsl:copy>
		</xsl:if>
	</xsl:template>
		
	<xsl:template match="@* | text()" priority="-9">
		<!-- Copy input attribute to output. -->
		<xsl:copy>
			<xsl:apply-templates select="@* | text()"/>
		</xsl:copy>
	</xsl:template>


</xsl:stylesheet>
