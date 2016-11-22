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

import au.gov.aims.sld.filter.Filter;
import au.gov.aims.sld.filter.FilterFactory;
import au.gov.aims.sld.geom.GeoShape;
import au.gov.aims.sld.geom.GeoShapeGroup;
import au.gov.aims.sld.symbolizer.Symbolizer;
import au.gov.aims.sld.symbolizer.SymbolizerFactory;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * http://docs.geoserver.org/2.3.5/user/styling/sld-reference/rules.html
 * DTD: http://schemas.opengis.net/sld/1.0.0/StyledLayerDescriptor.xsd
 */
public class Rule {
	private static final Logger LOGGER = Logger.getLogger(Rule.class.getSimpleName());

	private StyleSheet styleSheet;

	private String name;
	private String title;
	private String description;

	// Specifies the minimum scale denominator (inclusive) for the scale range in which this rule applies. If present, the rule applies at the given scale and all smaller scales.
	private Double minScaleDenominator;
	// Specifies the maximum scale denominator (exclusive) for the scale range in which this rule applies. If present, the rule applies at scales larger than the given scale.
	private Double maxScaleDenominator;

	private Filter filter;
	private List<Symbolizer> symbolizers;

	public Rule(StyleSheet styleSheet) {
		this.styleSheet = styleSheet;
		this.symbolizers = new ArrayList<Symbolizer>();
	}

	public String getName() {
		return this.name;
	}

	public String getTitle() {
		return this.title;
	}

	public String getDescription() {
		return this.description;
	}

	public double getMinScaleDenominator() {
		return this.minScaleDenominator;
	}

	public double getMaxScaleDenominator() {
		return this.maxScaleDenominator;
	}

	public Filter getFilter() {
		return this.filter;
	}

	public List<Symbolizer> getSymbolizers() {
		return this.symbolizers;
	}

	public void parse(Node ruleNode, XPath xPath) throws Exception {

		// Rule name
		Node ruleNameNode = (Node)xPath.evaluate("Name", ruleNode, XPathConstants.NODE);
		if (ruleNameNode == null) {
			throw new SAXException("Rule without name.");
		}
		this.name = ruleNameNode.getTextContent();

		// Rule title
		Node ruleTitleNode = (Node)xPath.evaluate("Title", ruleNode, XPathConstants.NODE);
		if (ruleTitleNode != null) {
			this.title = ruleTitleNode.getTextContent();
		}

		// Rule description
		Node ruleAbstractNode = (Node)xPath.evaluate("Abstract", ruleNode, XPathConstants.NODE);
		if (ruleAbstractNode != null) {
			this.description = ruleAbstractNode.getTextContent();
		}

		// Rule MinScaleDenominator
		Node ruleMinScaleDenominatorNode = (Node)xPath.evaluate("MinScaleDenominator", ruleNode, XPathConstants.NODE);
		if (ruleMinScaleDenominatorNode != null) {
			String ruleMinScaleDenominatorStr = ruleMinScaleDenominatorNode.getTextContent();
			if (ruleMinScaleDenominatorStr != null && !ruleMinScaleDenominatorStr.isEmpty()) {
				try {
					this.minScaleDenominator = Double.parseDouble(ruleMinScaleDenominatorStr);
				} catch(Exception ex) {
					LOGGER.log(Level.WARNING, "Invalid MinScaleDenominator '" + ruleMinScaleDenominatorStr + "'.");
				}
			}
		}

		// Rule MaxScaleDenominator
		Node ruleMaxScaleDenominatorNode = (Node)xPath.evaluate("MaxScaleDenominator", ruleNode, XPathConstants.NODE);
		if (ruleMaxScaleDenominatorNode != null) {
			String ruleMaxScaleDenominatorStr = ruleMaxScaleDenominatorNode.getTextContent();
			if (ruleMaxScaleDenominatorStr != null && !ruleMaxScaleDenominatorStr.isEmpty()) {
				try {
					this.maxScaleDenominator = Double.parseDouble(ruleMaxScaleDenominatorStr);
				} catch(Exception ex) {
					LOGGER.log(Level.WARNING, "Invalid MaxScaleDenominator '" + ruleMaxScaleDenominatorStr + "'.");
				}
			}
		}

		// Rule filter
		Node ruleFilterNode = (Node)xPath.evaluate("Filter", ruleNode, XPathConstants.NODE);
		if (ruleFilterNode != null) {
			// Find the operator in the "ogc:Filter". According to the DTD, it can be only one.
			Node ruleFilterOperatorNode = ruleFilterNode.getFirstChild();
			while (ruleFilterOperatorNode != null && ruleFilterOperatorNode.getNodeType() != Node.ELEMENT_NODE) {
				ruleFilterOperatorNode = ruleFilterOperatorNode.getNextSibling();
			}

			if (ruleFilterOperatorNode != null) {
				Filter filter = FilterFactory.fromXML(ruleFilterOperatorNode, xPath);
				if (filter != null) {
					this.filter = filter;
				}
			}
		}

		// Rule symbolizers
		this.symbolizers.addAll(this.parseSymbolizers(ruleNode, "LineSymbolizer", xPath));
		this.symbolizers.addAll(this.parseSymbolizers(ruleNode, "PointSymbolizer", xPath));
		this.symbolizers.addAll(this.parseSymbolizers(ruleNode, "PolygonSymbolizer", xPath));
		this.symbolizers.addAll(this.parseSymbolizers(ruleNode, "RasterSymbolizer", xPath));
		this.symbolizers.addAll(this.parseSymbolizers(ruleNode, "TextSymbolizer", xPath));
	}

	private List<Symbolizer> parseSymbolizers(Node ruleNode, String symbolizerName, XPath xPath) throws Exception {

		List<Symbolizer> symbolizers = null;
		NodeList symbolizerNodes = (NodeList)xPath.evaluate(symbolizerName, ruleNode, XPathConstants.NODESET);
		if (symbolizerNodes != null) {
			symbolizers = new ArrayList<Symbolizer>();
			for (int i = 0; i < symbolizerNodes.getLength(); i++) {
				Node symbolizerNode = symbolizerNodes.item(i);
				Symbolizer symbolizer = SymbolizerFactory.fromXML(this.styleSheet, symbolizerNode, xPath);
				if (symbolizer != null) {
					symbolizers.add(symbolizer);
				}
			}
		}

		return symbolizers;
	}

	public GeoShapeGroup applyStyle(GeoShapeGroup group, int scale) {
		// NOTE: Features get "added" for every rule it apply to.

		// Min is inclusive
		if (this.minScaleDenominator != null && scale < this.minScaleDenominator) {
			return null;
		}
		// Max is exclusive
		if (this.maxScaleDenominator != null && scale >= this.maxScaleDenominator) {
			return null;
		}

		GeoShapeGroup styledGroup = new GeoShapeGroup("rule_subgroup_" + this.getName());
		for (GeoShapeGroup subGroup : group.getGeoShapeGroups()) {
			if (!subGroup.isEmpty()) {
				styledGroup.add(this.applyStyle(subGroup, scale));
			}
		}

		for (GeoShape geoShape : group.getGeoShapes()) {
			if (this.filter == null || this.filter.filter(geoShape)) {
				for (Symbolizer symbolizer : this.symbolizers) {
					styledGroup.addAll(symbolizer.applyStyle(geoShape));
				}
			}
		}

		return styledGroup;
	}

	@Override
	public String toString() {
		return "Rule {\n" +
				"\t\tname='" + this.name + "',\n" +
				"\t\ttitle='" + this.title + "'\n" +
				"\t\tdescription='" + this.description + "'\n" +
				"\t\tminScaleDenominator='" + this.minScaleDenominator + "'\n" +
				"\t\tmaxScaleDenominator='" + this.maxScaleDenominator + "'\n" +
				"\t\tfilter='" + this.filter + "'\n" +
				"\t\tsymbolizers='" + this.symbolizers + "'\n" +
				"\t}";
	}
}
