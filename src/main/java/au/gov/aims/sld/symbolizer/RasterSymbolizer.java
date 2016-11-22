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
import au.gov.aims.sld.geom.GeoShape;
import au.gov.aims.sld.symbolizer.attributes.ChannelSelection;
import au.gov.aims.sld.symbolizer.attributes.ColorMapEntry;
import au.gov.aims.sld.symbolizer.attributes.ContrastEnhancement;
import au.gov.aims.sld.symbolizer.attributes.Geometry;
import au.gov.aims.sld.symbolizer.attributes.ImageOutline;
import au.gov.aims.sld.symbolizer.attributes.OverlapBehavior;
import au.gov.aims.sld.symbolizer.attributes.ShadedRelief;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A "RasterSymbolizer" is used to specify the rendering of raster/ matrix-coverage data (e.g., satellite images, DEMs).
 */
public class RasterSymbolizer extends Symbolizer {
	private static final Logger LOGGER = Logger.getLogger(RasterSymbolizer.class.getSimpleName());

	private Geometry geometry;
	private Double opacity;
	private ChannelSelection channelSelection;
	private OverlapBehavior overlapBehavior;
	private Map<String, ColorMapEntry> colorMap;
	private ContrastEnhancement contrastEnhancement;
	private ShadedRelief shadedRelief;
	private ImageOutline imageOutline;

	public RasterSymbolizer(StyleSheet styleSheet) {
		super(styleSheet);
	}

	public Geometry getGeometry() {
		return this.geometry;
	}

	public Double getOpacity() {
		return this.opacity;
	}

	public ChannelSelection getChannelSelection() {
		return this.channelSelection;
	}

	public OverlapBehavior getOverlapBehavior() {
		return this.overlapBehavior;
	}

	public Map<String, ColorMapEntry> getColorMap() {
		return this.colorMap;
	}

	public ContrastEnhancement getContrastEnhancement() {
		return this.contrastEnhancement;
	}

	public ShadedRelief getShadedRelief() {
		return this.shadedRelief;
	}

	public ImageOutline getImageOutline() {
		return this.imageOutline;
	}

	@Override
	public void parse(Node xmlNode, XPath xPath) throws Exception {
		Node geometryNode = (Node)xPath.evaluate("Geometry", xmlNode, XPathConstants.NODE);
		if (geometryNode != null) {
			this.geometry = new Geometry(this.getStyleSheet());
			this.geometry.parse(geometryNode, xPath);
		}

		Node opacityNode = (Node)xPath.evaluate("Opacity", xmlNode, XPathConstants.NODE);
		if (opacityNode != null) {
			String opacityStr = opacityNode.getTextContent();
			if (opacityStr != null && !opacityStr.isEmpty()) {
				try {
					this.opacity = Double.parseDouble(opacityStr);
				} catch(Exception ex) {
					LOGGER.log(Level.WARNING, "Invalid Opacity '" + opacityStr + "'.");
				}
			}
		}

		Node channelSelectionNode = (Node)xPath.evaluate("ChannelSelection", xmlNode, XPathConstants.NODE);
		if (channelSelectionNode != null) {
			this.channelSelection = new ChannelSelection(this.getStyleSheet());
			this.channelSelection.parse(channelSelectionNode, xPath);
		}

		Node overlapBehaviorNode = (Node)xPath.evaluate("OverlapBehavior", xmlNode, XPathConstants.NODE);
		if (overlapBehaviorNode != null) {
			String overlapBehaviorStr = overlapBehaviorNode.getTextContent();
			if (overlapBehaviorStr != null && !overlapBehaviorStr.isEmpty()) {
				try {
					this.overlapBehavior = OverlapBehavior.valueOf(overlapBehaviorStr);
				} catch(Exception ex) {
					LOGGER.log(Level.WARNING, "Invalid OverlapBehavior '" + overlapBehaviorStr + "'.");
				}
			}
		}

		/**
		 * <ColorMap>
		 *   <ColorMapEntry color="#323232" quantity="-300" label="label1" opacity="1"/>
		 *   <ColorMapEntry color="#BBBBBB" quantity="200" label="label2" opacity="1"/>
		 * </ColorMap>
		 */
		NodeList colorMapEntryNodes = (NodeList)xPath.evaluate("ColorMap/ColorMapEntry", xmlNode, XPathConstants.NODESET);
		if (colorMapEntryNodes != null) {
			this.colorMap = new HashMap<String, ColorMapEntry>();
			for (int i=0; i<colorMapEntryNodes.getLength(); i++) {
				Node colorMapEntryNode = colorMapEntryNodes.item(i);
				if (colorMapEntryNode != null) {
					ColorMapEntry colorMapEntry = new ColorMapEntry(this.getStyleSheet());
					colorMapEntry.parse(colorMapEntryNode, xPath);

					String colour = colorMapEntry.getColor();
					if (colour != null) {
						this.colorMap.put(colour, colorMapEntry);
					}
				}
			}
		}

		Node contrastEnhancementNode = (Node)xPath.evaluate("ContrastEnhancement", xmlNode, XPathConstants.NODE);
		if (contrastEnhancementNode != null) {
			this.contrastEnhancement = new ContrastEnhancement(this.getStyleSheet());
			this.contrastEnhancement.parse(contrastEnhancementNode, xPath);
		}

		Node shadedReliefNode = (Node)xPath.evaluate("ShadedRelief", xmlNode, XPathConstants.NODE);
		if (shadedReliefNode != null) {
			this.shadedRelief = new ShadedRelief(this.getStyleSheet());
			this.shadedRelief.parse(shadedReliefNode, xPath);
		}

		Node imageOutlineNode = (Node)xPath.evaluate("ImageOutline", xmlNode, XPathConstants.NODE);
		if (imageOutlineNode != null) {
			this.imageOutline = new ImageOutline(this.getStyleSheet());
			this.imageOutline.parse(imageOutlineNode, xPath);
		}
	}

	@Override
	public List<GeoShape> applyStyle(GeoShape geoShape) {
		throw new NotImplementedException();
	}
}
