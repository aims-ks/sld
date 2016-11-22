<?xml version="1.0" encoding="UTF-8"?>
<StyledLayerDescriptor version="1.0.0"
  xsi:schemaLocation="http://www.opengis.net/sld StyledLayerDescriptor.xsd"
  xmlns="http://www.opengis.net/sld" xmlns:ogc="http://www.opengis.net/ogc"
  xmlns:xlink="http://www.w3.org/1999/xlink"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
<NamedLayer>
  <Name>GBR_10m-GBR-cities</Name>
  <UserStyle>
    <Name>Cities</Name>
    <Title>City Names</Title>
    <FeatureTypeStyle>

      <!-- Cities -->
      <Rule>
        <Name>MajorCities</Name>
        <Title>Major Cities</Title>

        <ogc:Filter>
          <ogc:PropertyIsLessThanOrEqualTo>
<!--            <ogc:PropertyName>EA_RANK</ogc:PropertyName>-->
            <ogc:PropertyName>SCALERANK</ogc:PropertyName>
            <ogc:Literal>4</ogc:Literal>
          </ogc:PropertyIsLessThanOrEqualTo>
        </ogc:Filter>
        <MaxScaleDenominator>50000000</MaxScaleDenominator>
        <PointSymbolizer>
          <Graphic>
            <Mark>
              <WellKnownName>circle</WellKnownName>
              <Fill>
                <CssParameter name="fill">#663333</CssParameter>
                <CssParameter name="fill-opacity">1.0</CssParameter>
              </Fill>
            </Mark>
            <Size>8</Size>
          </Graphic>
        </PointSymbolizer>
        <TextSymbolizer>
          <Label>
            <ogc:PropertyName>NAME</ogc:PropertyName>
          </Label>
          <Font>
            <CssParameter name="font-family">SansSerif</CssParameter>
            <CssParameter name="font-style">Normal</CssParameter>
            <CssParameter name="font-size">14</CssParameter>
          </Font>
          <LabelPlacement>
            <PointPlacement>
              <AnchorPoint>
                <AnchorPointX>1.15</AnchorPointX>
                <AnchorPointY>0.5</AnchorPointY>
              </AnchorPoint>
            </PointPlacement>
          </LabelPlacement>

          <Halo>
            <Radius>
              <ogc:Literal>2</ogc:Literal>
            </Radius>
            <Fill>
              <CssParameter name="fill">#ffffff</CssParameter>
              <CssParameter name="fill-opacity">0.5</CssParameter>
            </Fill>
          </Halo>
          <Fill>
            <CssParameter name="fill">#000000</CssParameter>
            <CssParameter name="fill-opacity">1</CssParameter>
          </Fill>
          <Priority>
            <ogc:PropertyName>NATSCALE</ogc:PropertyName>
          </Priority>
          <!-- add a little space around the label so that the map isnt too cluttered-->
          <VendorOption name="spaceAround">10</VendorOption>
        </TextSymbolizer>
      </Rule>



      <Rule>
        <Name>SmallCities</Name>
        <Title>Minor Towns and Cities</Title>
        <ogc:Filter>
          <ogc:PropertyIsGreaterThan>
            <ogc:PropertyName>SCALERANK</ogc:PropertyName>
            <ogc:Literal>5</ogc:Literal>
          </ogc:PropertyIsGreaterThan>
        </ogc:Filter>
        <MaxScaleDenominator>5000000</MaxScaleDenominator>
        <PointSymbolizer>
          <Graphic>
            <Mark>
              <WellKnownName>circle</WellKnownName>
              <Fill>
                <CssParameter name="fill">#663333</CssParameter>
                <CssParameter name="fill-opacity">1.0</CssParameter>
              </Fill>
            </Mark>
            <Size>4</Size>
          </Graphic>
        </PointSymbolizer>
        <TextSymbolizer>
          <Label>
            <ogc:PropertyName>NAME</ogc:PropertyName>
          </Label>
          <Font>
            <CssParameter name="font-family">SansSerif</CssParameter>
            <CssParameter name="font-style">Normal</CssParameter>
            <CssParameter name="font-size">12</CssParameter>
          </Font>
          <LabelPlacement>
            <PointPlacement>
              <AnchorPoint>
                <AnchorPointX>1.15</AnchorPointX>
                <AnchorPointY>0.5</AnchorPointY>
              </AnchorPoint>
            </PointPlacement>
          </LabelPlacement>
          <Halo>
            <Radius>
              <ogc:Literal>2</ogc:Literal>
            </Radius>
            <Fill>
              <CssParameter name="fill">#ffffff</CssParameter>
              <CssParameter name="fill-opacity">0.5</CssParameter>
            </Fill>
          </Halo>
          <Fill>
            <CssParameter name="fill">#000000</CssParameter>
            <CssParameter name="fill-opacity">1</CssParameter>
          </Fill>
          <Priority>
            <ogc:PropertyName>NATSCALE</ogc:PropertyName>
          </Priority>
          <!-- add a little space around the label so that the map isnt too cluttered -->
          <VendorOption name="spaceAround">10</VendorOption>
        </TextSymbolizer>
      </Rule>


      <Rule>
        <Name>SmallCities</Name>
        <Title>Minor Towns and Cities</Title>
        <ogc:Filter>
          <ogc:And>
          <ogc:PropertyIsGreaterThan>
            <ogc:PropertyName>SCALERANK</ogc:PropertyName>
            <ogc:Literal>4</ogc:Literal>
          </ogc:PropertyIsGreaterThan>
          <ogc:PropertyIsLessThan>
            <ogc:PropertyName>SCALERANK</ogc:PropertyName>
            <ogc:Literal>6</ogc:Literal>
          </ogc:PropertyIsLessThan>
          </ogc:And>
        </ogc:Filter>
        <MaxScaleDenominator>10000000</MaxScaleDenominator>
        <PointSymbolizer>
          <Graphic>
            <Mark>
              <WellKnownName>circle</WellKnownName>
              <Fill>
                <CssParameter name="fill">#663333</CssParameter>
                <CssParameter name="fill-opacity">1.0</CssParameter>
              </Fill>
            </Mark>
            <Size>6</Size>
          </Graphic>
        </PointSymbolizer>
        <TextSymbolizer>
          <Label>
            <ogc:PropertyName>NAME</ogc:PropertyName>
          </Label>
          <Font>
            <CssParameter name="font-family">SansSerif</CssParameter>
            <CssParameter name="font-style">Normal</CssParameter>
            <CssParameter name="font-size">12</CssParameter>
          </Font>
          <LabelPlacement>
            <PointPlacement>
              <AnchorPoint>
                <AnchorPointX>1.15</AnchorPointX>
                <AnchorPointY>0.5</AnchorPointY>
              </AnchorPoint>
            </PointPlacement>
          </LabelPlacement>
          <Halo>
            <Radius>
              <ogc:Literal>2</ogc:Literal>
            </Radius>
            <Fill>
              <CssParameter name="fill">#ffffff</CssParameter>
              <CssParameter name="fill-opacity">0.5</CssParameter>
            </Fill>
          </Halo>
          <Fill>
            <CssParameter name="fill">#000000</CssParameter>
            <CssParameter name="fill-opacity">1</CssParameter>
          </Fill>
          <Priority>
            <ogc:PropertyName>NATSCALE</ogc:PropertyName>
          </Priority>
          <!-- add a little space around the label so that the map isnt too cluttered -->
          <VendorOption name="spaceAround">10</VendorOption>
        </TextSymbolizer>
      </Rule>

    </FeatureTypeStyle>
  </UserStyle>
</NamedLayer>
</StyledLayerDescriptor>
