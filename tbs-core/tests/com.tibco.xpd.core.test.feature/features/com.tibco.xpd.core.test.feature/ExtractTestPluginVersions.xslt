<?xml version="1.0" encoding="UTF-8"?>
<!--
	This xslt takes an eclipse feature.xml and for each /feature/plugin element whose id ends with ".test" or ".tests" then output

		<id>_<version>\n

	Author: 	Sid Allway
	Date:		07/05/2010

	Copyright (c) TIBCO Software Inc 2004-2010. All rights reserved.
-->
<xsl:stylesheet version="1.0" 
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format"
	xmlns=""
	>

<xsl:output indent="no" method="text"/>

<!-- 
For each test plugin output properties with plugin version  in the format

	<featureId>.pluginList=<pluginIdEndingIn.test>_<pluginVersion>, <pluginIdEndingIn.test>_<pluginVersion>

-->
<xsl:template match="/feature">
	
	<xsl:text>junitTestFeature.pluginList=</xsl:text>

	<!-- 
		Have to make sure that we don't output traling ", " so have to create a temporary newline separated 
		list of plugiId_version and then process that again after. This is because there is no way of telling in xsl for-each
		whether you are outputting the first or subsequent items (because first might not be a plugin ending in .test
	-->
	<xsl:variable name="testPluginList">
		<xsl:for-each select="plugin">
			<!-- 
				Only interested in plguin's whose id ends with ".test".
				Unfortunately xpath has a "starts-with()" function but no "ends-with()". 
				So we have no choice but to recursively  process the id splitting it up by "." 
					until we get to the last segment.
			-->
			<xsl:variable name="isTestPlugin"><xsl:call-template name="isTestPlugin"><xsl:with-param name="str" select="@id"/></xsl:call-template></xsl:variable>
		
			<xsl:if test="$isTestPlugin = 'true'">
				<!--
				<xsl:value-of select="@id"/>_<xsl:value-of select="@version"/><xsl:value-of select="'&#10;'"/>
				-->
				<xsl:value-of select="@id"/><xsl:value-of select="'&#10;'"/>
				<!--
				<xsl:value-of select="@id"/><xsl:value-of select="'&#10;'"/>
				-->
			</xsl:if>
		</xsl:for-each>
	</xsl:variable>

	<xsl:call-template name="newLineListToCommaList"><xsl:with-param name="newLineList" select="$testPluginList"/></xsl:call-template>

</xsl:template>

<!-- 
	Output a comma separated list from a newline separated list. 
	Outputs top item on list then recurs to do remaining items. 
-->
<xsl:template name="newLineListToCommaList">
	<xsl:param name="newLineList"/>

	<xsl:variable name="nextItem" select="substring-before($newLineList, '&#10;')"/>
	<xsl:variable name="remainingItems" select="substring-after($newLineList, '&#10;')"/>
	
	<xsl:value-of select="$nextItem"/>
	
	<xsl:if test="$remainingItems != ''">
		<xsl:text>,</xsl:text>
		
		<!-- Recurs and do remaing items -->
		<xsl:call-template name="newLineListToCommaList"><xsl:with-param name="newLineList" select="$remainingItems"/></xsl:call-template>
	
	</xsl:if>
	
</xsl:template>


<!-- Outputs "true" if the given string indicates a test plugin id (string ends with ".test") -->
<xsl:template name="isTestPlugin">
	<xsl:param name="str"/>

	<xsl:if test="$str != ''">
		<xsl:choose>
			<xsl:when test="not(contains($str, '.'))">
				<!-- There are no more "." separators in string, this is last segment - if it is "test" then it's a test plugin. -->
				<xsl:if test="$str = 'test' or $str='tests'">
					<xsl:text>true</xsl:text>
				</xsl:if>
			</xsl:when>
			<xsl:otherwise>
				<!-- Knock off first segment and recurs. -->
				<xsl:call-template name="isTestPlugin"><xsl:with-param name="str" select="substring-after($str, '.')"/></xsl:call-template>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:if>
	
</xsl:template>

<xsl:template match="text()">
	<!-- ignore text nodes -->
</xsl:template>

<!-- By default recurs without copying --> 
<xsl:template match="@* | node()">
	<xsl:apply-templates select="@* | node()"/>
</xsl:template>

</xsl:stylesheet>