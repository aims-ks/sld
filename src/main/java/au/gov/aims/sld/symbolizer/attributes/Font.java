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
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A "Font" element specifies the text font to use.
 * The allowed CssParameters are:
 *   - "font-family",
 *   - "font-style",
 *   - "font-weight", and
 *   - "font-size".
 */
public class Font extends Attribute {
	private static final Logger LOGGER = Logger.getLogger(Font.class.getName());

	private static double DEFAULT_FONT_SIZE = 10.0;

	private static FontStyle DEFAULT_FONT_STYLE = FontStyle.NORMAL;
	private static FontWeight DEFAULT_FONT_WEIGHT = FontWeight.NORMAL;

	// CssParameters

	// The "font-family" CssParameter element gives the family name of a font to use.
	// Allowed values are system-dependent. Any number of font-family CssParameter
	// elements may be given and they are assumed to be in preferred order.
	private String fontFamily;

	// The "font-style" CssParameter element gives the style to use for a font. The
	// allowed values are "normal", "italic", and "oblique".
	private FontStyle fontStyle;

	// The "font-weight" CssParameter element gives the amount of weight or boldness
	// to use for a font. Allowed values are "normal" and "bold".
	private FontWeight fontWeight;

	// The "font-size" CssParameter element gives the size to use for the font in pixels.
	// The default is defined to be 10 pixels, though various systems may have restrictions on
	// what sizes are available.
	// NOTE: According to GeoTools sources, this takes an "Expression" which can be evaluated into an "Object".
	//   We do not need to support all that fancy stuff, so we will assume it's a double, which get rounded to
	//   an integer when creating the AWT Font.
	private Double fontSize;

	public Font(StyleSheet styleSheet) {
		super(styleSheet);
	}

	public String getFontFamily() {
		return this.fontFamily;
	}

	public FontStyle getFontStyle() {
		return this.fontStyle;
	}

	public FontWeight getFontWeight() {
		return this.fontWeight;
	}

	public Double getFontSize() {
		return this.fontSize;
	}

	// fontSizeRatio: 1.0 for default font size. 2.0 for double size.
	public java.awt.Font getFont() {
		StyleSheet styleSheet = this.getStyleSheet();
		double fontSizeRatio = styleSheet == null ? 1.0 : styleSheet.getFontSizeRatio();
		return new java.awt.Font(this.fontFamily, this.getAwtFontStyle(), this.getAwtFontSize(fontSizeRatio));
	}

	private int getAwtFontStyle() {
		// NOTE: java.awt.Font.PLAIN = 0
		int awtFontStyle = java.awt.Font.PLAIN;

		if (this.fontStyle != null) {
			awtFontStyle += this.fontStyle.getAwtStyle();
		} else {
			awtFontStyle += Font.DEFAULT_FONT_STYLE.getAwtStyle();
		}

		if (this.fontWeight != null) {
			awtFontStyle += this.fontWeight.getAwtStyle();
		} else {
			awtFontStyle += Font.DEFAULT_FONT_WEIGHT.getAwtStyle();
		}

		return awtFontStyle;
	}

	private int getAwtFontSize(double fontSizeRatio) {
		double pixelFontSize = this.fontSize == null ? Font.DEFAULT_FONT_SIZE : this.fontSize;
		return (int)Math.round(pixelFontSize * fontSizeRatio);
	}

	@Override
	public void parse(Node xmlNode, XPath xPath) throws Exception {
		Map<String, String> cssParameters = this.parseCssParameter(xmlNode, xPath);
		this.fontFamily = cssParameters.get("font-family");

		String fontStyleStr = cssParameters.get("font-style");
		if (fontStyleStr != null && !fontStyleStr.isEmpty()) {
			try {
				this.fontStyle = FontStyle.valueOf(fontStyleStr.toUpperCase());
			} catch(Exception ex) {
				LOGGER.log(Level.WARNING, "Invalid font-style '" + fontStyleStr + "'.");
			}
		}

		String fontWeightStr = cssParameters.get("font-weight");
		if (fontWeightStr != null && !fontWeightStr.isEmpty()) {
			try {
				this.fontWeight = FontWeight.valueOf(fontWeightStr.toUpperCase());
			} catch(Exception ex) {
				LOGGER.log(Level.WARNING, "Invalid font-weight '" + fontWeightStr + "'.");
			}
		}

		String fontSizeStr = cssParameters.get("font-size");
		if (fontSizeStr != null && !fontSizeStr.isEmpty()) {
			try {
				this.fontSize = Double.parseDouble(fontSizeStr);
			} catch(Exception ex) {
				LOGGER.log(Level.WARNING, "Invalid font-size '" + fontSizeStr + "'.");
			}
		}
	}

	/**
	 * Enumeration of allow font-style values.
	 * <p>
	 * This is a way to document the constants allowable for the setStyle method
	 */
	public enum FontStyle {
		NORMAL("normal", java.awt.Font.PLAIN),
		ITALIC("italic", java.awt.Font.ITALIC),
		OBLIQUE("oblique", java.awt.Font.ITALIC);

		final String literal;
		final int awtStyle;

		FontStyle(String constant, int awtStyle) {
			this.literal = constant;
			this.awtStyle = awtStyle;
		}

		public int getAwtStyle() {
			return this.awtStyle;
		}
	}

	/**
	 * Enumeration of allow font-weight values.
	 */
	public enum FontWeight {
		NORMAL("normal", java.awt.Font.PLAIN),
		BOLD("bold", java.awt.Font.BOLD);

		final String literal;
		final int awtStyle;

		FontWeight(String constant, int awtStyle) {
			this.literal = constant;
			this.awtStyle = awtStyle;
		}

		public int getAwtStyle() {
			return this.awtStyle;
		}
	}
}
