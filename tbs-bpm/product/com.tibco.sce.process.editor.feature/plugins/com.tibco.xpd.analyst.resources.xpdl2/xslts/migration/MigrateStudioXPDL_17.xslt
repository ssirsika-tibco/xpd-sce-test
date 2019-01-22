<?xml version="1.0" encoding="UTF-8"?>
<!--
	===================================================================================================================
	XSLT:       MigrateStudioXPDL_17.xslt
	
	DESCRIPTION:

  	    This xslt will updgrade a xpdl of format version 17 (3.9.0 GA) to format version 18 (v3.10.0 (@ V02+)).
  	     (See XpdlMigrate.java for format version <-> Studio version equivalence).
  	     
  	    - XPD-6976: Support for Asynch Sub-Proc :: Now that pageflow supports calling
	                Business Process, the sub-process StartStrategy etc now apply so add them where missing
	                
	    - XPD-6974: SOAP JMS Message Timeout Configurations, to be set to the Default value for Priority [0] and Delivery Mode [Persistent]
  	     
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
		<xsl:apply-templates/>
	</xsl:template>


	<!--
	===============================================================================
	XPD-6976: Support for Asynch Sub-Proc :: Now that pageflow supports calling
	          Business Process, the sub-process StartStrategy etc now apply
	          so add them where missing.    
	===============================================================================
    -->
    <xsl:template match="xpdl2:SubFlow"> 
    	<xsl:copy>
    		<!-- Do normal stuff (i.e. copy) for child attributes. -->
    		<xsl:apply-templates select="@*"/>
    		
    		<!-- Add start strategy attributes if missing -->
    		<xsl:if test="not(@xpdExt:StartStrategy)">
    			<xsl:attribute name="xpdExt:StartStrategy">StartImmediately</xsl:attribute>
    		</xsl:if>

    		<xsl:if test="not(@xpdExt:SuspendResumeWithParent)">
    			<xsl:attribute name="xpdExt:SuspendResumeWithParent">true</xsl:attribute>
    		</xsl:if>

    		<!-- Do normal stuff (i.e. copy) for child elements. -->
    		<xsl:apply-templates select="node()"/>

    	</xsl:copy>
    </xsl:template>


<!--
	===============================================================================
	XPD-6974: Support SOAP JMS Message Configuration Timeouts :: set defaults for Priority [0] and Delivery Mode [Persistent]
	===============================================================================
    -->
    <xsl:template match="xpdExt:Outbound/xpdExt:SoapJmsBinding"> 
    	<xsl:copy>
    		<!-- Set default values for attributes Delivery Mode and Priority -->
   			<xsl:attribute name="xpdExt:DeliveryMode">PERSISTENT</xsl:attribute>

   			<xsl:attribute name="xpdExt:Priority">0</xsl:attribute>

    		<!-- Do normal stuff (i.e. copy) for child attributes. -->
    		<xsl:apply-templates select="@*"/>
    		
    		<!-- Do normal stuff (i.e. copy) for child elements. -->
    		<xsl:apply-templates select="node()"/>

    	</xsl:copy>
    </xsl:template>
    
    
    <!-- 
    ====================================================================================================
    XPD-6975: XpdInterfaceType enumeration with values "ServiceProcessInterface" and "ProcessInterface"
    		  have been introduced for new Service Process Interface Type.
    		  Add XpdInterfaceType="ProcessInterface" for existing Process Interfaces (which is the default option)
    ====================================================================================================
     -->
    <xsl:template match="xpdExt:ProcessInterface">

		<xsl:copy>
			<xsl:attribute name="XpdInterfaceType">ProcessInterface</xsl:attribute>

    		<!-- Do normal stuff (i.e. copy) for child attributes. -->
    		<xsl:apply-templates select="@*"/>
    		
    		<!-- Do normal stuff (i.e. copy) for child elements. -->
    		<xsl:apply-templates select="node()"/>

		
		</xsl:copy>
		
	</xsl:template>
    
	<!--
	===============================================================================
	Update the format version.
	===============================================================================
    -->
	<xsl:template match="xpdl2:ExtendedAttribute[@Name = 'FormatVersion']">
		<xsl:element name="xpdl2:ExtendedAttribute">
			<xsl:attribute name="Name">FormatVersion</xsl:attribute>
			<xsl:attribute name="Value">18</xsl:attribute>
		</xsl:element>
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
