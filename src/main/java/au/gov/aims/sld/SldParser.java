/*
 *  Copyright (C) 2016 Australian Institute of Marine Science
 *
 *  Contact: Gael Lafond <g.lafond@aims.org.au>
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package au.gov.aims.sld;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.logging.Logger;

/**
 * SLD file parser.
 * SLD (Styled Layer Descriptor) is an XML document which define how to display a layer.
 *
 * Specification: http://www.opengeospatial.org/standards/sld
 * DTD: http://schemas.opengis.net/sld/1.0.0/StyledLayerDescriptor.xsd
 */
public class SldParser {
	private static final Logger LOGGER = Logger.getLogger(SldParser.class.getSimpleName());

	private DocumentBuilderFactory documentBuilderFactory;
	private XPathFactory xPathFactory;

	public SldParser() {
		this.documentBuilderFactory = DocumentBuilderFactory.newInstance();
		this.xPathFactory = XPathFactory.newInstance();
	}

	public StyleSheet parse(File sldFile) throws Exception {
		InputStream sldStream = null;
		StyleSheet styleSheet = null;

		try {
			sldStream = new FileInputStream(sldFile);
			styleSheet = this.parse(sldStream);
		} finally {
			if (sldStream != null) {
				sldStream.close();
			}
		}

		return styleSheet;
	}

	/**
	 * <?xml version="1.0" encoding="UTF-8"?>
	 * <StyledLayerDescriptor ...>
	 *   <NamedLayer>
	 *     <Name>...</Name>       ==> Name of the layer to which this style has been created
	 *     <UserStyle>
	 *       <Name>...</Name>     ==> Name of the style
	 *       <Title>...</Title>
	 *       <Abstract>...</Abstract>
	 *       <FeatureTypeStyle>
	 *
	 *         <Rule>
	 *           <Name>...</Name> ==> Name of the rule
	 *           <Title>...</Title>
	 *           <Abstract>...</Abstract>
	 *
	 *           <ogc:Filter>...</ogc:Filter>
	 *           <MinScaleDenominator>...</MinScaleDenominator>
	 *           <MaxScaleDenominator>...</MaxScaleDenominator>
	 *           <TextSymbolizer>...</TextSymbolizer>
	 *           <LineSymbolizer>...</LineSymbolizer>
	 *           <PointSymbolizer>...</PointSymbolizer>
	 *           <RasterSymbolizer>...</RasterSymbolizer>
	 *           <PolygonSymbolizer>
	 *             <Fill>
	 *               <CssParameter name="fill">...</CssParameter>
	 *               <CssParameter name="fill-opacity">...</CssParameter>
	 *             </Fill>
	 *             <Stroke>
	 *               <CssParameter name="stroke">...</CssParameter>
	 *               <CssParameter name="stroke-opacity">...</CssParameter>
	 *               <CssParameter name="stroke-width">...</CssParameter>
	 *             </Stroke>
	 *           </PolygonSymbolizer>
	 *         </Rule>
	 *
	 *       </FeatureTypeStyle>
	 *     </UserStyle>
	 *   </NamedLayer>
	 * </StyledLayerDescriptor>
	 */
	public StyleSheet parse(InputStream sldStream) throws Exception {
		DocumentBuilder documentBuilder = this.documentBuilderFactory.newDocumentBuilder();
		Document document = documentBuilder.parse(sldStream);
		XPath xPath = this.xPathFactory.newXPath();

		Element root = document.getDocumentElement();

		// Normalised the document - easier to parse
		//   http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
		root.normalize();

		StyleSheet styleSheet = new StyleSheet();
		styleSheet.parse(root, xPath);

		return styleSheet;
	}
}
