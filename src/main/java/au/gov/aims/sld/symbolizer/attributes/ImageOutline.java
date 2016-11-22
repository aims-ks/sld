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
package au.gov.aims.sld.symbolizer.attributes;

import au.gov.aims.sld.StyleSheet;
import au.gov.aims.sld.symbolizer.Symbolizer;
import au.gov.aims.sld.symbolizer.SymbolizerFactory;
import org.w3c.dom.Node;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;

/**
 * "ImageOutline" specifies how individual source rasters in a multi-raster set
 * (such as a set of satellite-image scenes) should be outlined to make the
 * individual-image locations visible.
 */
public class ImageOutline extends Attribute {
	private Symbolizer symbolizer; // Can be a LineSymbolizer or a PolygonSymbolizer

	public ImageOutline(StyleSheet styleSheet) {
		super(styleSheet);
	}

	public Symbolizer getSymbolizer() {
		return this.symbolizer;
	}

	@Override
	public void parse(Node xmlNode, XPath xPath) throws Exception {
		Node symbolizerNode = (Node)xPath.evaluate("LineSymbolizer", xmlNode, XPathConstants.NODE);
		if (symbolizerNode == null) {
			symbolizerNode = (Node)xPath.evaluate("PolygonSymbolizer", xmlNode, XPathConstants.NODE);
		}

		if (symbolizerNode != null) {
			Symbolizer symbolizer = SymbolizerFactory.fromXML(this.getStyleSheet(), symbolizerNode, xPath);
			if (symbolizer != null) {
				this.symbolizer = symbolizer;
			}
		}
	}
}
