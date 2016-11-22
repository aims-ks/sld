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

import au.gov.aims.sld.geom.GeoShapeGroup;
import au.gov.aims.sld.geom.Layer;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import java.util.ArrayList;
import java.util.List;

public class FeatureTypeStyle {
	private StyleSheet styleSheet;

	private String name;
	private String title;
	private String description;
	private String featureTypeName;
	private List<String> semanticTypeIdentifiers;
	private List<Rule> rules;

	public FeatureTypeStyle(StyleSheet styleSheet) {
		this.styleSheet = styleSheet;
		this.semanticTypeIdentifiers = new ArrayList<String>();
		this.rules = new ArrayList<Rule>();
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

	public String getFeatureTypeName() {
		return this.featureTypeName;
	}

	public List<String> getSemanticTypeIdentifiers() {
		return this.semanticTypeIdentifiers;
	}

	public List<Rule> getRules() {
		return this.rules;
	}

	public void parse(Node xmlNode, XPath xPath) throws Exception {

		Node nameNode = (Node)xPath.evaluate("Name", xmlNode, XPathConstants.NODE);
		this.name = nameNode == null ? null : nameNode.getTextContent();

		Node titleNode = (Node)xPath.evaluate("Title", xmlNode, XPathConstants.NODE);
		this.title = titleNode == null ? null : titleNode.getTextContent();

		Node abstractNode = (Node)xPath.evaluate("Abstract", xmlNode, XPathConstants.NODE);
		this.description = abstractNode == null ? null : abstractNode.getTextContent();

		Node featureTypeNameNode = (Node)xPath.evaluate("FeatureTypeName", xmlNode, XPathConstants.NODE);
		this.featureTypeName = featureTypeNameNode == null ? null : featureTypeNameNode.getTextContent();

		NodeList semanticTypeIdentifierNodes = (NodeList)xPath.evaluate("SemanticTypeIdentifier", xmlNode, XPathConstants.NODESET);
		for (int i=0; i<semanticTypeIdentifierNodes.getLength(); i++) {
			Node semanticTypeIdentifierNode = semanticTypeIdentifierNodes.item(i);
			this.semanticTypeIdentifiers.add(semanticTypeIdentifierNode.getTextContent());
		}

		NodeList ruleNodes = (NodeList)xPath.evaluate("Rule", xmlNode, XPathConstants.NODESET);
		for (int i=0; i<ruleNodes.getLength(); i++) {
			Node ruleNode = ruleNodes.item(i);
			Rule rule = new Rule(this.styleSheet);
			rule.parse(ruleNode, xPath);
			this.rules.add(rule);
		}
	}

	public Layer applyStyle(Layer layer, int index, int total, int scale) {
		// Generate a pretty layer name.
		//   "<layer name> (i/n) <feature type name>"
		//   "Cities (1/2) Points"
		String featureTypeName = this.getName();
		String layerName = layer.getName();
		String count = null;

		if (total > 1) {
			count = (index + 1) + "/" + total;
		}

		if (count != null) {
			layerName += " " + count;
		}

		if (featureTypeName != null) {
			layerName += " " + featureTypeName;
		}

		Layer styledLayer = new Layer(layerName);

		if (this.rules != null && !this.rules.isEmpty()) {
			List<GeoShapeGroup> shapeGroups = layer.getShapeGroups();
			if (shapeGroups != null && !shapeGroups.isEmpty()) {
				for (Rule rule : this.rules) {
					GeoShapeGroup ruleGroup = new GeoShapeGroup("rule_" + rule.getName());
					for (GeoShapeGroup group : shapeGroups) {
						GeoShapeGroup styledGroup = rule.applyStyle(group, scale);
						if (styledGroup != null && !styledGroup.isEmpty()) {
							ruleGroup.add(styledGroup);
						}
					}
					if (!ruleGroup.isEmpty()) {
						styledLayer.add(ruleGroup);
					}
				}
			}
		}

		return styledLayer;
	}
}
