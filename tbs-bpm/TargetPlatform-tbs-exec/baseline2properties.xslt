<!--
 | COPYRIGHT : Copyright (C) 2014 TIBCO Software Inc.
 -->
<xsl:stylesheet version="2.0" 
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    exclude-result-prefixes="xsi"
>
    <xsl:output method="text" indent="no"/>
    <xsl:strip-space elements="*"/>
    
	<xsl:template match="/">
		<xsl:apply-templates/>
    </xsl:template>
	<xsl:template match="/targets/target">
		<xsl:value-of xml:space="preserve" select="@feature"/>=<xsl:value-of select="@version"/><xsl:text>&#10;</xsl:text>
	</xsl:template>
</xsl:stylesheet>
