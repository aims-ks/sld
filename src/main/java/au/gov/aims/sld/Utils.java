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

import org.w3c.dom.Node;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class Utils {
	/**
	 * Example: Utils.getChildNodes(xmlNode, Node.ELEMENT_NODE);
	 */
	public static List<Node> getChildNodes(Node xmlNode, short filter) {
		List<Node> filteredNodes = new ArrayList<Node>();
		Node childNode = xmlNode.getFirstChild();
		while (childNode != null) {
			if (childNode.getNodeType() == filter) {
				filteredNodes.add(childNode);
			}
			childNode = childNode.getNextSibling();
		}
		return filteredNodes;
	}

	public static boolean isNumeric(String str) {
		try {
			Double.parseDouble(str);
		} catch(NumberFormatException ex) {
			return false;
		}
		return true;
	}

	public static Color parseHexColor(String hexColor, Double opacity) {
		int intOpacity = opacity == null ? 255 :
				(int)Math.round(opacity * 255);

		return new Color(
			Integer.valueOf(hexColor.substring(1, 3), 16),
			Integer.valueOf(hexColor.substring(3, 5), 16),
			Integer.valueOf(hexColor.substring(5, 7), 16),
			intOpacity
		);
	}
}
