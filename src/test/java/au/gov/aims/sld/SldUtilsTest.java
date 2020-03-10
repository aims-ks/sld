/*
 *  Copyright (C) 2020 Australian Institute of Marine Science
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

import org.junit.Assert;
import org.junit.Test;

import java.awt.Color;

public class SldUtilsTest {

    @Test(expected = NumberFormatException.class)
    public void testParseBadHexColour() {
        String hexColour = "#POTATO";
        Color parsedColour = SldUtils.parseHexColour(hexColour);

        Assert.assertNull(String.format("The string %s returned a colour?", hexColour), parsedColour);
    }

    @Test
    public void testParseHexColour() {
        String hexColour = "#FF9900";
        Color parsedColour = SldUtils.parseHexColour(hexColour);

        Assert.assertNotNull(String.format("The Hex colour string %s could not be parsed", hexColour), parsedColour);
        Assert.assertEquals("Wrong red", 0xFF, parsedColour.getRed());
        Assert.assertEquals("Wrong green", 0x99, parsedColour.getGreen());
        Assert.assertEquals("Wrong blue", 0, parsedColour.getBlue());
        Assert.assertEquals("Wrong alpha", 255, parsedColour.getAlpha());
    }

    @Test
    public void testParseHexColourWithAlpha() {
        String hexColour = "#124578AB";
        Color parsedColour = SldUtils.parseHexColour(hexColour);

        Assert.assertNotNull(String.format("The Hex colour string %s could not be parsed", hexColour), parsedColour);
        Assert.assertEquals("Wrong red", 0x12, parsedColour.getRed());
        Assert.assertEquals("Wrong green", 0x45, parsedColour.getGreen());
        Assert.assertEquals("Wrong blue", 0x78, parsedColour.getBlue());
        Assert.assertEquals("Wrong alpha", 0xAB, parsedColour.getAlpha());
    }
}
