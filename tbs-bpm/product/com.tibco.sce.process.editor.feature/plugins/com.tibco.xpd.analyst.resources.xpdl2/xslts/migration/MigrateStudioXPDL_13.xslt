<?xml version="1.0" encoding="UTF-8"?>
<!--
	===================================================================================================================
	XSLT:       MigrateStudioXPDL_13.xslt
	
	DESCRIPTION:

  	    This xslt will updgrade a xpdl of format version 13 (3.5.10 GA) to format version 14 (v3.5.20).

		- Adds new OriginalFormatVersion to preserve detail on when exactly the xpdl was first created (which can be useful 
			in situations where we could make some retrospective changes to fix regressions caused by 
			changes made in a given studio version far easier when Studio can tell the version which the XPDL was originally designed for

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
		<xsl:apply-templates/>
	</xsl:template>


	<!--  
	===============================================================================
	Add the new OriginalFormatVersion extended attribute.
	===============================================================================
	 -->
	<xsl:template match="xpdl2:Package/xpdl2:ExtendedAttributes">
		<xsl:copy>
			<xsl:apply-templates select="@* | node() | text()"/>
			
			<xpdl2:ExtendedAttribute Name="OriginalFormatVersion" Value="{xpdl2:ExtendedAttribute[@Name='FormatVersion']/@Value}"/>
			
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
			<xsl:attribute name="Value">14</xsl:attribute>
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
