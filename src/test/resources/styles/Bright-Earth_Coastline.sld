<?xml version="1.0" encoding="ISO-8859-1"?>
<StyledLayerDescriptor version="1.0.0" xmlns="http://www.opengis.net/sld" xmlns:ogc="http://www.opengis.net/ogc"
  xmlns:xlink="http://www.w3.org/1999/xlink" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://www.opengis.net/sld http://schemas.opengis.net/sld/1.0.0/StyledLayerDescriptor.xsd">
  <NamedLayer>
    <Name>Bright-Earth_Coastline</Name>
    <UserStyle>
      <Title>Coastline in blue</Title>
      <FeatureTypeStyle>

        <Rule>
          <Title>Coastline</Title>
          <LineSymbolizer>
            <Stroke>
              <CssParameter name="stroke">#276787</CssParameter>
              <CssParameter name="stroke-opacity">0.8</CssParameter>
            <CssParameter name="stroke-width">0.5</CssParameter>
            </Stroke>
          </LineSymbolizer>
        </Rule>

      </FeatureTypeStyle>
    </UserStyle>
  </NamedLayer>
</StyledLayerDescriptor>
