<?xml version="1.0" encoding="UTF-8"?>
<StyledLayerDescriptor version="1.0.0"
  xsi:schemaLocation="http://www.opengis.net/sld StyledLayerDescriptor.xsd"
  xmlns="http://www.opengis.net/sld" xmlns:ogc="http://www.opengis.net/ogc"
  xmlns:xlink="http://www.w3.org/1999/xlink"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
<NamedLayer>
    <Name>Polygon_Outline-Red</Name>
    <UserStyle>
      <Name>Polygon_Outline-Red</Name>
      <Title>Polygon outline (Red)</Title>
      <Abstract>Shows the outlines of all the polygons in red.</Abstract>
      <!-- ================ POLYGONS ================== -->
      <FeatureTypeStyle>
        <Rule>
          <Name>Outline</Name>
          <Title>Outline</Title>
          <PolygonSymbolizer>
            <Stroke>
              <CssParameter name="stroke">#ec1846</CssParameter>
              <CssParameter name="stroke-opacity">1</CssParameter>
              <CssParameter name="stroke-width">0.5</CssParameter>
            </Stroke>
          </PolygonSymbolizer>
        </Rule>
      </FeatureTypeStyle>
    </UserStyle>
  </NamedLayer>
</StyledLayerDescriptor>
