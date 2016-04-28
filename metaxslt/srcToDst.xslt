<?xml version="1.0" encoding="utf-8"?><xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output indent="yes" method="xml" encoding="utf-8" omit-xml-declaration="yes"/>
<xsl:template match="/">
<dst>
	<datadst>
		<namedst>
			<xsl:value-of select="/src/data/name">
			</xsl:value-of>
		</namedst>
		<adressdst>
			<xsl:value-of select="/src/data/adress">
				
			</xsl:value-of>
		</adressdst>
	</datadst>
	
	<niknamesdst>
			<xsl:for-each select="/src/niknames/nik">
				<nikdst>
					<xsl:value-of select="."/>
					<xsl:value-of select="/src/data/name/@id"/>
				</nikdst>
			</xsl:for-each>
	</niknamesdst>
	
</dst>
</xsl:template>
</xsl:stylesheet>
