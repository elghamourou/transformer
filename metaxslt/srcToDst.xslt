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
			
			
				<xsl:value-of select="/src/data/adress"/>
				<xsl:for-each select="/src/data/name/@id">
<xsl:variable name="string-value" select="."/>
<xsl:if test="starts-with($string-value, 'to')">
					<xsl:value-of select="/src/data/name/@id"/>
					
				</xsl:if>
</xsl:for-each>
			
		</adressdst>
	</datadst>

	<xsl:for-each select="/src/niknames">
		<niknamesdst>
			<xsl:for-each select="/src/niknames/nik">
				<nikdst>
<!-- 					<map:to-attribute xmlns:map="mapping" name="attr" -->
<!-- 						path='/src/data/name' /> -->
<!-- 					<map:value xmlns:map="mapping" path='.' /> -->
					<xsl:for-each select="/src/data/name/@id">
<xsl:variable name="string-value" select="."/>
<xsl:if test="substring($string-value, (string-length($string-value) - string-length('co')) + 1) = 'co'">
						<xsl:value-of select="/src/data/name/@id"/>
					</xsl:if>
</xsl:for-each>

				</nikdst>
			</xsl:for-each>
		</niknamesdst>
	</xsl:for-each>
</dst>
</xsl:template>
</xsl:stylesheet>
