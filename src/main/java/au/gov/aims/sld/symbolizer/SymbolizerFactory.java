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
package au.gov.aims.sld.symbolizer;

import au.gov.aims.sld.StyleSheet;
import org.w3c.dom.Node;

import javax.xml.xpath.XPath;

public class SymbolizerFactory {
	public static Symbolizer fromXML(StyleSheet styleSheet, Node xmlNode, XPath xPath) throws Exception {
		Symbolizer symbolizer = null;
		String symbolizerName = xmlNode.getNodeName();

		if("LineSymbolizer".equals(symbolizerName)) {
			symbolizer = new LineSymbolizer(styleSheet);
		} else if ("PointSymbolizer".equals(symbolizerName)) {
			symbolizer = new PointSymbolizer(styleSheet);
		} else if ("PolygonSymbolizer".equals(symbolizerName)) {
			symbolizer = new PolygonSymbolizer(styleSheet);
		} else if ("RasterSymbolizer".equals(symbolizerName)) {
			symbolizer = new RasterSymbolizer(styleSheet);
		} else if ("TextSymbolizer".equals(symbolizerName)) {
			symbolizer = new TextSymbolizer(styleSheet);
		} else {
			throw new IllegalArgumentException("Invalid SLD symbolizer: " + symbolizerName);
		}

		if (symbolizer != null) {
			symbolizer.parse(xmlNode, xPath);
		}

		return symbolizer;
	}
}
