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

import java.io.InputStream;

/**
 * List of methods used in tests (to avoid code duplication)
 */
public class TestUtils {

	protected static StyleSheet getStyleSheet(SldParser parser, String sldPath) throws Exception {
		StyleSheet styleSheet = null;
		InputStream sldStream = null;
		try {
			sldStream = SldParserTest.class.getClassLoader().getResourceAsStream(sldPath);
			styleSheet = parser.parse(sldStream);
		} finally {
			if (sldStream != null) {
				sldStream.close();
			}
		}

		return styleSheet;
	}
}
