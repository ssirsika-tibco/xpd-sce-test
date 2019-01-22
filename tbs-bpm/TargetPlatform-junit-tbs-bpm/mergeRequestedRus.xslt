<?xml version="1.0" ?>
<xsl:transform
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    version="1.0">
    
    <xsl:param name="requestedRusBpmFile"></xsl:param>
    <xsl:param name="requestedRusFragmentFile"></xsl:param>
    <xsl:param name="fragmentTpDesc"></xsl:param>
    
    <!-- <xsl:template match="/feature/requires">
        <xsl:copy>
            <xsl:copy-of select="document($requestedRusBpmFile)/feature/requires/*" />
            <xsl:copy-of select="document($requestedRusSdaFile)/feature/requires/*" />
        </xsl:copy>
    </xsl:template> -->
    
    <xsl:template match="/feature/requires">
        <xsl:copy>
          <xsl:comment>This is a generated file. Please don't edit it or commit to version control.
          You can edit <xsl:value-of select="$requestedRusFragmentFile"/> template file or bpm tp file to change versions.  
          </xsl:comment>
          
          <xsl:comment>BPM Features</xsl:comment>
	      <xsl:for-each select="document($requestedRusBpmFile)/feature/requires/*">
	          <xsl:copy-of select="." />
	      </xsl:for-each>
          <xsl:comment><xsl:value-of select="$fragmentTpDesc"/> (Added Features from fragment.)</xsl:comment>
	      <xsl:for-each select="document($requestedRusFragmentFile)/feature/requires/*">
	          <xsl:copy-of select="." />
	      </xsl:for-each>
        </xsl:copy>
    </xsl:template>
    
    <xsl:template match="@* | node()">
        <xsl:copy>
            <xsl:apply-templates select="@* | node()"/>
        </xsl:copy>
    </xsl:template>
    
</xsl:transform>