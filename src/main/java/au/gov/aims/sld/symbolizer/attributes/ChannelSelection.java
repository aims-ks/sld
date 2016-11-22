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
import org.w3c.dom.Node;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;

/**
 * "ChannelSelection" specifies the false-color channel selection for a multi-spectral raster source.
 */
public class ChannelSelection extends Attribute {
	private SelectedChannelType redChannel;
	private SelectedChannelType greenChannel;
	private SelectedChannelType blueChannel;
	private SelectedChannelType grayChannel;

	public ChannelSelection(StyleSheet styleSheet) {
		super(styleSheet);
	}

	public SelectedChannelType getRedChannel() {
		return this.redChannel;
	}

	public SelectedChannelType getGreenChannel() {
		return this.greenChannel;
	}

	public SelectedChannelType getBlueChannel() {
		return this.blueChannel;
	}

	public SelectedChannelType getGrayChannel() {
		return this.grayChannel;
	}

	@Override
	public void parse(Node xmlNode, XPath xPath) throws Exception {
		Node redChannelNode = (Node)xPath.evaluate("RedChannel", xmlNode, XPathConstants.NODE);
		if (redChannelNode != null) {
			this.redChannel = new SelectedChannelType(this.getStyleSheet());
			this.redChannel.parse(redChannelNode, xPath);
		}

		Node greenChannelNode = (Node)xPath.evaluate("GreenChannel", xmlNode, XPathConstants.NODE);
		if (greenChannelNode != null) {
			this.greenChannel = new SelectedChannelType(this.getStyleSheet());
			this.greenChannel.parse(greenChannelNode, xPath);
		}

		Node blueChannelNode = (Node)xPath.evaluate("BlueChannel", xmlNode, XPathConstants.NODE);
		if (blueChannelNode != null) {
			this.blueChannel = new SelectedChannelType(this.getStyleSheet());
			this.blueChannel.parse(blueChannelNode, xPath);
		}

		Node grayChannelNode = (Node)xPath.evaluate("GrayChannel", xmlNode, XPathConstants.NODE);
		if (grayChannelNode != null) {
			this.grayChannel = new SelectedChannelType(this.getStyleSheet());
			this.grayChannel.parse(grayChannelNode, xPath);
		}
	}

	public static class SelectedChannelType extends Attribute {
		private String sourceChannelName;
		private ContrastEnhancement contrastEnhancement;

		public SelectedChannelType(StyleSheet styleSheet) {
			super(styleSheet);
		}

		public String getSourceChannelName() {
			return this.sourceChannelName;
		}

		public ContrastEnhancement getContrastEnhancement() {
			return this.contrastEnhancement;
		}

		@Override
		public void parse(Node xmlNode, XPath xPath) throws Exception {
			Node sourceChannelNameNode = (Node)xPath.evaluate("SourceChannelName", xmlNode, XPathConstants.NODE);
			if (sourceChannelNameNode != null) {
				this.sourceChannelName = sourceChannelNameNode.getTextContent();
			}

			Node contrastEnhancementNode = (Node)xPath.evaluate("ContrastEnhancement", xmlNode, XPathConstants.NODE);
			if (contrastEnhancementNode != null) {
				this.contrastEnhancement = new ContrastEnhancement(this.getStyleSheet());
				this.contrastEnhancement.parse(contrastEnhancementNode, xPath);
			}
		}
	}
}
