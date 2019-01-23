<?xml version="1.0" encoding="UTF-8"?>
<!--
	===================================================================================================================
	XSLT:       MigrateStudioXPDL_4.xslt
	
	DESCRIPTION:
	This xslt will updgrade a xpdl of format version 4 to format version 5.  

	DO NOT MAKE ANY FURTHER CHANGES TO THIS XSLT!..

	NOTE: that the FormatVersion between Studio v3.1 jumps from version 4 to version 6 in one go. 
			This is purely to allow this migration xslt to deal ONLY with changing the xpdl2 namespace from 2.0alpha to 2.1.

			As this is not a very natural thing to do in xslt it is reasonably easy to mess it up. 

		Therefore all real migrations for 3.0 to 3.1 (like changing old elements for new ones and so on should be done in MigrateStudsioXPDL_5.xslt).
	
	
	===================================================================================================================
-->

<xsl:stylesheet version="1.0"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:xpdExt="http://www.tibco.com/XPD/xpdExtension1.0.0"
	xmlns:ej="http://www.tibco.com/XPD/EAIJava1.0.0"
	xmlns:simulation="http://www.tibco.com/xpd/Simulation1.0.1"
	xmlns:database="http://www.tibco.com/XPD/database1.0.0"
	xmlns:email="http://www.tibco.com/XPD/email1.0.0"
	xmlns:iProcessExt="http://www.tibco.com/XPD/iProcessExt1.0.0"	
	xmlns:java="http://xml.apache.org/xslt/java"

	xmlns:xpdl2="http://www.wfmc.org/2004/XPDL2.0alpha"
	
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

	<!--
		===============================================================================
		Handle package - do default on everything EXCEPT extended attributes 
		===============================================================================
	-->
	<xsl:template match="xpdl2:Package">

		<xpdl2:Package xmlns:xpdl2="http://www.wfmc.org/2008/XPDL2.1" xsi:schemaLocation="http://www.wfmc.org/2008/XPDL2.1 http://www.wfmc.org/standards/bpmnxpdl_31.xsd">
			<xsl:apply-templates select="@*"/>

			<xsl:apply-templates select="node()"/>
			
		</xpdl2:Package>
		
	</xsl:template>

	<xsl:template match="xpdl2:ExtendedAttribute[@Name = 'FormatVersion']">
		<xsl:element name="xpdl2:ExtendedAttribute" namespace="http://www.wfmc.org/2008/XPDL2.1">
			<xsl:attribute name="Name">FormatVersion</xsl:attribute>
			<xsl:attribute name="Value">5</xsl:attribute>
		</xsl:element>
	</xsl:template>

	<!--
		===============================================================================
		Default template to copy all attributes nodes and text of an element AND CHANGE NAMESPACE
		===============================================================================
	-->
	<!-- This template should swap old xpdl2 nodes to new ones. -->
	<xsl:template match="xpdl2:*" priority="-7">
		<xsl:element name="{name()}" namespace="http://www.wfmc.org/2008/XPDL2.1"> 
			<xsl:apply-templates select="@* | node()"/>
		</xsl:element>
	</xsl:template>

	<!-- This template removes the invlida xpdl2: from in front of xpdl2 attributes in xpdl2 elements. -->
	<xsl:template match="@xpdl2:*" priority="-7">
		<xsl:attribute name="{local-name()}" namespace=""><xsl:value-of select="."/></xsl:attribute>
	</xsl:template>

	<!-- This template will activate for anything thing that isn't old xpdl2 (xpdExt etc) and prevent old xpdl2 namespace being specified oon the element itself. -->
	<xsl:template match="*" priority="-8">
		<xsl:element name="{name()}"> 
			<xsl:apply-templates select="@* | node()"/>
		</xsl:element>
	</xsl:template>



	<xsl:template match="@xsi:schemaLocation">
		<xsl:attribute name="xsi:schemaLocation">http://www.wfmc.org/2008/XPDL2.1 http://www.wfmc.org/standards/bpmnxpdl_31.xsd</xsl:attribute>
	</xsl:template>

	<xsl:template match="@* | text()" priority="-9">
		<xsl:copy>
			<xsl:apply-templates select="@* | text()" />
		</xsl:copy>
	</xsl:template>

</xsl:stylesheet>
