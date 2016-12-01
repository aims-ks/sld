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

import au.gov.aims.sld.geom.Layer;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StyleSheet {
	private static final Logger LOGGER = Logger.getLogger(StyleSheet.class.getSimpleName());

	private String name;
	private String title;
	private String description;
	private Boolean isDefault;

	private float strokeWidthRatio = 1.0f;
	private float fontSizeRatio = 1.0f;

	// Those are basically layers. First in the list is drawn first (appear bellow the others).
	private List<FeatureTypeStyle> featureTypeStyles;

	public StyleSheet() {
		this.featureTypeStyles = new ArrayList<FeatureTypeStyle>();
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

	public Boolean isDefault() {
		return this.isDefault;
	}

	public float getStrokeWidthRatio() {
		return this.strokeWidthRatio;
	}

	public void setStrokeWidthRatio(float strokeWidthRatio) {
		this.strokeWidthRatio = strokeWidthRatio;
	}

	public float getFontSizeRatio() {
		return this.fontSizeRatio;
	}

	public void setFontSizeRatio(float fontSizeRatio) {
		this.fontSizeRatio = fontSizeRatio;
	}

	public List<FeatureTypeStyle> getFeatureTypeStyles() {
		return this.featureTypeStyles;
	}

	public void parse(Node xmlNode, XPath xPath) throws Exception {
		Node userStyleNode = (Node)xPath.evaluate("/StyledLayerDescriptor/NamedLayer/UserStyle", xmlNode, XPathConstants.NODE);

		if (userStyleNode != null) {
			// Style name
			Node styleNameNode = (Node)xPath.evaluate("Name", userStyleNode, XPathConstants.NODE);
			this.name = styleNameNode == null ? null : styleNameNode.getTextContent();

			// Style title
			Node styleTitleNode = (Node)xPath.evaluate("Title", userStyleNode, XPathConstants.NODE);
			this.title = styleTitleNode == null ? null : styleTitleNode.getTextContent();

			// Style description (abstract)
			Node styleAbstractNode = (Node)xPath.evaluate("Abstract", userStyleNode, XPathConstants.NODE);
			this.description = styleAbstractNode == null ? null : styleAbstractNode.getTextContent();

			Node isDefaultNode = (Node)xPath.evaluate("IsDefault", userStyleNode, XPathConstants.NODE);
			if (isDefaultNode != null) {
				String isDefaultStr = isDefaultNode.getTextContent();
				if (isDefaultStr != null && !isDefaultStr.isEmpty()) {
					try {
						this.isDefault = Boolean.parseBoolean(isDefaultStr);
					} catch(Exception ex) {
						LOGGER.log(Level.WARNING, "Invalid IsDefault '" + isDefaultStr + "'.");
					}
				}
			}

			// Style rules
			NodeList featureTypeStyleNodes = (NodeList)xPath.evaluate("FeatureTypeStyle", userStyleNode, XPathConstants.NODESET);
			for (int i=0; i<featureTypeStyleNodes.getLength(); i++) {
				Node featureTypeStyleNode = featureTypeStyleNodes.item(i);
				FeatureTypeStyle featureTypeStyle = new FeatureTypeStyle(this);
				featureTypeStyle.parse(featureTypeStyleNode, xPath);
				this.featureTypeStyles.add(featureTypeStyle);
			}
		}
	}

	/**
	 * Go through each "GeoShape" of the layer and attach "Style" to each of them.
	 * NOTE: SLD define multiple "FeatureTypeStyle", which are basically layers.
	 * scale: The scale at which the map is viewed (zoom level)
	 */
	public List<Layer> generateStyledLayers(Layer layer, int scale) {
		List<Layer> stylesLayers = new ArrayList<Layer>();

		if (this.featureTypeStyles != null && !this.featureTypeStyles.isEmpty()) {
			for (int i=0; i<this.featureTypeStyles.size(); i++) {
				FeatureTypeStyle featureTypeStyle = this.featureTypeStyles.get(i);
				Layer featureTypeLayer = featureTypeStyle.applyStyle(layer, i, this.featureTypeStyles.size(), scale);
				if (!featureTypeLayer.isEmpty()) {
					stylesLayers.add(featureTypeLayer);
				}
			}
		}

		return stylesLayers;
	}

	@Override
	public String toString() {
		return "StyleSheet {\n" +
				"\tname='" + this.name + "',\n" +
				"\ttitle='" + this.title + "',\n" +
				"\tdescription='" + this.description + "',\n" +
				"\tfeatureTypeStyles=" + this.featureTypeStyles + "\n" +
				"}";
	}
}
