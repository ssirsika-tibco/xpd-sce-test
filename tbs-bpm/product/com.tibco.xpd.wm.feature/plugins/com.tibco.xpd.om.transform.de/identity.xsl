<xsl:stylesheet version="2.0" 
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://www.w3.org/1999/XSL/Transform http://www.w3.org/2007/schema-for-xslt20.xsd">
	
<xsl:output method="xml"/> 
 
<xsl:attribute-set name="locationRef">
  <!-- namespace="http://www.eclipse.org/emf/2002/Ecore" -->
  <xsl:attribute name="reference">de:location</xsl:attribute>
</xsl:attribute-set>

<xsl:template match="attribute[@name = 'locationId']" priority="2">
	<xsl:copy>
    	<xsl:apply-templates select="@*"/>
    </xsl:copy>	
	<xsl:copy use-attribute-sets="locationRef">
    </xsl:copy>	
</xsl:template>

<xsl:template match="node() | @*" priority="1">
  <xsl:copy>
    <xsl:apply-templates select="@* | node()"/>
  </xsl:copy>
</xsl:template>


</xsl:stylesheet>
