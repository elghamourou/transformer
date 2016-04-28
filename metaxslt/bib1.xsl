<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	version="1.0">
	<xsl:output method="text" />
	<xsl:template match="/customer">
		<xsl:apply-templates />
	</xsl:template>

	<xsl:template match="address">
		<xsl:apply-templates select="postalcode" />
	</xsl:template>

	<xsl:template match="postalcode">
		<code>
			<xsl:apply-templates />
		</code>
	</xsl:template>
	<xsl:template match="name">
	</xsl:template>
</xsl:stylesheet>