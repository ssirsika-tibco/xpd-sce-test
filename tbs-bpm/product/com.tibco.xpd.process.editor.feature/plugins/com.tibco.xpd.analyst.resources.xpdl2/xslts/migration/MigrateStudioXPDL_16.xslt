<?xml version="1.0" encoding="UTF-8"?>
<!--
	===================================================================================================================
	XSLT:       MigrateStudioXPDL_16.xslt
	
	DESCRIPTION:

  	    This xslt will updgrade a xpdl of format version 16 (3.7.0 GA) to format version 17 (v3.9.0 (@ V07+)).

		- Add proper prefixes to Global Data Service Task definition elements.

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
	Add proper prefixes to Global Data Service Task definition elements.
	===============================================================================
    -->
	<xsl:template match="xpdExt:GlobalDataOperation">
	
		<!-- Get the exact prefix used in the source XPDL (in case it is non standard). -->
		<xsl:variable name="xpdExtPrefix">
			<xsl:choose>
				<xsl:when test="contains(name(.), ':')"><xsl:value-of select="substring-before(name(.), ':')"/>:</xsl:when>
				<xsl:otherwise></xsl:otherwise>
			</xsl:choose>
		</xsl:variable>
		
		<xsl:copy>
			<xsl:apply-templates select="@*"/>
			
			<xsl:for-each select="*">
				<xsl:call-template name="copyAndAddXpdExt">
					<xsl:with-param name="copyNode" select="."/>
					<xsl:with-param name="xpdExtPrefix" select="$xpdExtPrefix"/>
				</xsl:call-template>
			</xsl:for-each>
		
		</xsl:copy>
	
	</xsl:template>

	<!--
	===============================================================================
	Copy the given node and it's contents adding the xpdExt prefix.
	===============================================================================
    -->
	<xsl:template name="copyAndAddXpdExt">
		<xsl:param name="copyNode"/>
		<xsl:param name="xpdExtPrefix"/>
		
		<!-- if element does not have prefix then add the one we've been given. -->
		<xsl:variable name="nodeName">
			<xsl:choose>
				<xsl:when test="contains(name(.), ':')"><xsl:value-of select="name(.)"/></xsl:when>
				<xsl:otherwise><xsl:value-of select="$xpdExtPrefix"/><xsl:value-of select="local-name(.)"/></xsl:otherwise>
			</xsl:choose>
		</xsl:variable>
	
		<xsl:element name="{$nodeName}">
		
			<xsl:apply-templates select="@*"/>

			<xsl:for-each select="*">
				<xsl:call-template name="copyAndAddXpdExt">
					<xsl:with-param name="copyNode" select="."/>
					<xsl:with-param name="xpdExtPrefix" select="$xpdExtPrefix"/>
				</xsl:call-template>
			</xsl:for-each>
			
			<xsl:apply-templates select="text()"/>
		</xsl:element>
		
	</xsl:template>

	<!--
	===============================================================================
	Update the format version.
	===============================================================================
    -->
	<xsl:template match="xpdl2:ExtendedAttribute[@Name = 'FormatVersion']">
		<xsl:element name="xpdl2:ExtendedAttribute">
			<xsl:attribute name="Name">FormatVersion</xsl:attribute>
			<xsl:attribute name="Value">17</xsl:attribute>
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
