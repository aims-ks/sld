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

import org.junit.Test;

public class SldParserTest {

	@Test
	public void testParser() throws Exception {
		SldParser parser = new SldParser();
		StyleSheet styleSheet = TestUtils.getStyleSheet(parser, "styles/GBR-features_Outlook.sld");

		System.out.println(styleSheet);
	}

	@Test
	public void testParserWithLineSymbolizer() throws Exception {
		SldParser parser = new SldParser();
		StyleSheet styleSheet = TestUtils.getStyleSheet(parser, "styles/Bright-Earth_Coastline.sld");

		System.out.println(styleSheet);
	}

	@Test
	public void testParserWithGraphics() throws Exception {
		SldParser parser = new SldParser();
		StyleSheet styleSheet = TestUtils.getStyleSheet(parser, "styles/GBR_10m-GBR-cities.sld");

		System.out.println(styleSheet);
	}
}
