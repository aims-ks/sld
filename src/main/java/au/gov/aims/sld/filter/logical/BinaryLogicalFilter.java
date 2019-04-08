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
package au.gov.aims.sld.filter.logical;

import au.gov.aims.sld.SldUtils;
import au.gov.aims.sld.filter.Filter;
import au.gov.aims.sld.filter.FilterFactory;
import org.w3c.dom.Node;

import javax.xml.xpath.XPath;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class BinaryLogicalFilter extends LogicalFilter {
	private static final Logger LOGGER = Logger.getLogger(BinaryLogicalFilter.class.getSimpleName());

	private Filter leftFilter;
	private Filter rightFilter;

	public Filter getLeftFilter() {
		return this.leftFilter;
	}

	public Filter getRightFilter() {
		return this.rightFilter;
	}

	@Override
	public void parse(Node xmlNode, XPath xPath) throws Exception {
		List<Node> elements = SldUtils.getChildNodes(xmlNode, Node.ELEMENT_NODE);
		if (elements == null || elements.isEmpty()) {
			LOGGER.log(Level.WARNING, "Binary filter contains no elements.");
			return;
		}

		if (elements.size() != 2) {
			LOGGER.log(Level.WARNING, "Binary filter contains " + elements.size() + " elements. Expected 2.");
			return;
		}

		Filter leftFilter = FilterFactory.fromXML(elements.get(0), xPath);
		if (leftFilter == null) {
			LOGGER.log(Level.WARNING, "Binary filter contains invalid left element.");
			return;
		}

		Filter rightFilter = FilterFactory.fromXML(elements.get(1), xPath);
		if (rightFilter == null) {
			LOGGER.log(Level.WARNING, "Binary filter contains invalid right element.");
			return;
		}

		this.leftFilter = leftFilter;
		this.rightFilter = rightFilter;
	}

	@Override
	public String toString() {
		return "BinaryLogicalFilter{" +
				"leftFilter=" + leftFilter +
				", rightFilter=" + rightFilter +
				'}';
	}
}
