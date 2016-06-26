<?xml version="1.0" encoding="utf-8"?><xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output indent="yes" method="xml" encoding="utf-8" omit-xml-declaration="no" />
xsl:output method="xml" />

    <xsl:template match="/">
        <xsl:apply-templates select="*"/>
    </xsl:template>

   <xsl:template match="*[not(descendant-or-self::*[@*]) and not(descendant-or-self::*[normalize-space(text())!=''])]"/>
   
   <!-- template to copy elements -->
	<xsl:template match="*">
				<xsl:copy >
					<xsl:apply-templates select="@* | node() |text()" />
				</xsl:copy>
	</xsl:template>

	<!-- template to copy attributes -->
	<xsl:template match="@*">
		<xsl:attribute name="{local-name()}">
			<xsl:value-of select="." />
		</xsl:attribute>
	</xsl:template>

	<!-- template to copy the rest of the nodes -->
	<xsl:template match="comment()| processing-instruction()">
		<xsl:copy />
	</xsl:template>
	
	
	<xsl:variable name='newline'><xsl:text>
	</xsl:text></xsl:variable>
	<xsl:template match="text()" >
<!-- 		<xsl:param name="pReplacement" select="../@LongName"/> -->
		<xsl:param name="pReplacement" select="$newline"/>
		<xsl:value-of select="$pReplacement"></xsl:value-of>
	</xsl:template>
</xsl:stylesheet>
