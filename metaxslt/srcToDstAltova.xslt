<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xs="http://www.w3.org/2001/XMLSchema" exclude-result-prefixes="xs">
	<xsl:output method="xml" encoding="UTF-8" indent="yes"/>
	<xsl:template match="/">
		<xsl:variable name="var1_instance_src" select="."/>
		<dst>
			<xsl:attribute name="xsi:noNamespaceSchemaLocation" namespace="http://www.w3.org/2001/XMLSchema-instance">
				<xsl:value-of select="'M:/project/transformer/metaxslt/dst.xsd'"/>
			</xsl:attribute>
			<datadst>
				<xsl:for-each select="$var1_instance_src/src">
					<xsl:variable name="var2_src" select="."/>
					<namedst>
						<xsl:value-of select="string($var2_src/data/name)"/>
					</namedst>
				</xsl:for-each>
				<xsl:for-each select="$var1_instance_src/src">
					<adressdst/>
				</xsl:for-each>
			</datadst>
			<niknamesdst>
				<xsl:for-each select="$var1_instance_src/src/niknames/nik">
					<nikdst/>
				</xsl:for-each>
			</niknamesdst>
		</dst>
	</xsl:template>
</xsl:stylesheet>
