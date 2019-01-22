<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xsd="http://www.w3.org/2001/XMLSchema">
<xsl:output method="text" version="1.0" encoding="UTF-8" indent="yes"/>
	
	
<xsl:template match="/">

	<xsl:for-each select="//xsd:element[@name != '']">
		<xsl:variable name="type" select="substring-after(@type, ':')"/>
		<xsl:variable name="name" select="@name"/>
		
		<xsl:variable name="parent" select="xsd:complexType | //xsd:complexType[@name = $type]"/>
		
		<xsl:if test="$parent">

			<xsl:value-of select="$name"/><xsl:text>:		</xsl:text>

			<xsl:variable name="children" select="$parent/xsd:sequence/xsd:element | $parent//xsd:choice//xsd:element"/>

			<xsl:for-each select="$children">
				<xsl:if test="not(starts-with(@ref, 'deprecated:'))">
					<xsl:text> </xsl:text>
					<xsl:choose>
						<xsl:when test="@name">
								<xsl:call-template name="lowerFirst"><xsl:with-param name="str" select="@name"/></xsl:call-template>
						</xsl:when>
						<xsl:otherwise>
							<xsl:call-template name="lowerFirst"><xsl:with-param name="str" select="substring-after(@ref, ':')"/></xsl:call-template>
						</xsl:otherwise>
					</xsl:choose>
				</xsl:if>				
			</xsl:for-each>

			<xsl:value-of select="'&#10;'"/>


		</xsl:if>

	</xsl:for-each>
	


</xsl:template>
	
<xsl:template name="lowerFirst">
	<xsl:param name="str"/>
	
	<xsl:value-of select="translate(substring($str, 1, 1), 'ABCDEFGHIJKLMNOPQRSTUVWXWZ', 'abcdefghijklmnopqrstuvwxyz')"/>
	<xsl:value-of select="substring($str, 2)"/>
</xsl:template>	
	
</xsl:stylesheet>
