<?xml version="1.0" encoding="UTF-8"?>
<StyledLayerDescriptor version="1.0.0"
  xsi:schemaLocation="http://www.opengis.net/sld StyledLayerDescriptor.xsd"
  xmlns="http://www.opengis.net/sld" xmlns:ogc="http://www.opengis.net/ogc"
  xmlns:xlink="http://www.w3.org/1999/xlink"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
<NamedLayer>
    <Name>GBR-features_Outlook</Name>
    <UserStyle>
      <Name>GBR-features_Outlook</Name>
      <Title>GBR features (Outlook style)</Title>
      <Abstract>Shows all of the features in this dataset with styling
       similar to the GBRMPA Outlook 2009 report</Abstract>
      <!-- ================ POLYGONS ================== -->
      <FeatureTypeStyle>

        <!-- Remove small reefs when zoomed out -->
        <Rule>
          <Name>Reefs</Name>
          <Title>Reefs</Title>

          <ogc:Filter>
            <ogc:And>
              <ogc:PropertyIsEqualTo>
                <ogc:PropertyName>FEAT_NAME</ogc:PropertyName>
                <ogc:Literal>Reef</ogc:Literal>
              </ogc:PropertyIsEqualTo>
              <!-- Remove small reefs to declutter the map -->
              <ogc:PropertyIsGreaterThan>
                <ogc:PropertyName>SHAPE_AREA</ogc:PropertyName>
                <ogc:Literal>0.0001</ogc:Literal>
              </ogc:PropertyIsGreaterThan>
            </ogc:And>
          </ogc:Filter>
          <MinScaleDenominator>3000000</MinScaleDenominator>
          <PolygonSymbolizer>
            <Fill>
              <CssParameter name="fill">#c9d8e6</CssParameter>
              <CssParameter name="fill-opacity">0.0</CssParameter>
            </Fill>
            <Stroke>
              <CssParameter name="stroke">#d1d2d3</CssParameter>
              <CssParameter name="stroke-opacity">1</CssParameter>
              <CssParameter name="stroke-width">0.2</CssParameter>
            </Stroke>
          </PolygonSymbolizer>
        </Rule>

        <Rule>
          <Name>Reefs</Name>
          <Title>Small Reefs (&lt;1:3M)</Title>

          <ogc:Filter>
            <ogc:PropertyIsEqualTo>
              <ogc:PropertyName>FEAT_NAME</ogc:PropertyName>
              <ogc:Literal>Reef</ogc:Literal>
            </ogc:PropertyIsEqualTo>
          </ogc:Filter>
          <MaxScaleDenominator>3000000</MaxScaleDenominator>
          <PolygonSymbolizer>
            <Fill>
              <CssParameter name="fill">#c9d8e6</CssParameter>
              <CssParameter name="fill-opacity">0.0</CssParameter>
            </Fill>
            <Stroke>
              <CssParameter name="stroke">#d1d2d3</CssParameter>
              <CssParameter name="stroke-opacity">1</CssParameter>
              <CssParameter name="stroke-width">0.5</CssParameter>
            </Stroke>
          </PolygonSymbolizer>
        </Rule>


        <Rule>
          <Name>Cay</Name>
          <Title>Cay</Title>
          <ogc:Filter>
            <ogc:PropertyIsEqualTo>
              <ogc:PropertyName>FEAT_NAME</ogc:PropertyName>
              <ogc:Literal>Cay</ogc:Literal>
            </ogc:PropertyIsEqualTo>
          </ogc:Filter>
          <PolygonSymbolizer>
            <Fill>
              <!-- <CssParameter name="fill">#dedb97</CssParameter> -->
              <CssParameter name="fill">#fcf6e3</CssParameter>
              <CssParameter name="fill-opacity">1</CssParameter>
            </Fill>
            <Stroke>
<!--              <CssParameter name="stroke">#095f82</CssParameter> -->
              <CssParameter name="stroke">#a9acad</CssParameter>
              <CssParameter name="stroke-opacity">1</CssParameter>
              <CssParameter name="stroke-width">0.5</CssParameter>
            </Stroke>
          </PolygonSymbolizer>
        </Rule>

        <Rule>
          <Name>Main Island Rock</Name>
          <Title>Mainland,Island,Rock</Title>
          <ogc:Filter>
            <ogc:Or>
              <ogc:Or>
                <ogc:PropertyIsEqualTo>
                  <ogc:PropertyName>FEAT_NAME</ogc:PropertyName>
                  <ogc:Literal>Mainland</ogc:Literal>
                </ogc:PropertyIsEqualTo>
                <ogc:PropertyIsEqualTo>
                  <ogc:PropertyName>FEAT_NAME</ogc:PropertyName>
                  <ogc:Literal>Island</ogc:Literal>
                </ogc:PropertyIsEqualTo>
              </ogc:Or>
              <ogc:PropertyIsEqualTo>
                <ogc:PropertyName>FEAT_NAME</ogc:PropertyName>
                <ogc:Literal>Rock</ogc:Literal>
              </ogc:PropertyIsEqualTo>
            </ogc:Or>
          </ogc:Filter>

          <PolygonSymbolizer>
            <Fill>
              <!-- <CssParameter name="fill">#dedb97</CssParameter> -->
              <CssParameter name="fill">#fcf6e3</CssParameter>
              <CssParameter name="fill-opacity">1</CssParameter>
            </Fill>
            <Stroke>
              <CssParameter name="stroke">#a9acad</CssParameter>
              <CssParameter name="stroke-opacity">1</CssParameter>
              <CssParameter name="stroke-width">0.5</CssParameter>
            </Stroke>
          </PolygonSymbolizer>
        </Rule>

      </FeatureTypeStyle>

      <!-- ======================= LABELS ========================= -->
      <FeatureTypeStyle>

      <!-- Don't bother with labels when zoomed out greater than 1:3M -->
        <Rule>
          <Name>Feature label</Name>
          <Title>&gt;1:250k General Names</Title>

          <ogc:Filter>
            <ogc:And>
              <ogc:PropertyIsNotEqualTo>
                <ogc:PropertyName>FEAT_NAME</ogc:PropertyName>
                <ogc:Literal>Mainland</ogc:Literal>
              </ogc:PropertyIsNotEqualTo>
              <!-- Don't label Unnamed reefs when zoomed out -->
              <ogc:PropertyIsNotEqualTo>
                <ogc:PropertyName>GBR_NAME</ogc:PropertyName>
                <ogc:Literal>U/N Reef</ogc:Literal>
              </ogc:PropertyIsNotEqualTo>
            </ogc:And>
          </ogc:Filter>
          <MinScaleDenominator>250000</MinScaleDenominator>
          <MaxScaleDenominator>3000000</MaxScaleDenominator>

          <TextSymbolizer>
            <Label>
              <!-- Only use the general name (no ID) -->
              <ogc:PropertyName>GBR_NAME</ogc:PropertyName>
            </Label>
            <Font>
              <CssParameter name="font-family">SansSerif</CssParameter>
              <CssParameter name="font-style">Normal</CssParameter>
              <CssParameter name="font-size">9</CssParameter>
            </Font>
            <!-- this centers the label on the polygon's centroid-->
            <LabelPlacement>
              <PointPlacement>
                <AnchorPoint>
                  <AnchorPointX>0.5</AnchorPointX>
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
                <CssParameter name="fill-opacity">1</CssParameter>
              </Fill>
            </Halo>
            <Fill>
              <CssParameter name="fill">#000000</CssParameter>
              <CssParameter name="fill-opacity">1</CssParameter>
            </Fill>
            <Priority>
              <ogc:PropertyName>SHAPE_AREA</ogc:PropertyName>
            </Priority>
            <VendorOption name="autoWrap">60</VendorOption>
            <VendorOption name="maxDisplacement">150</VendorOption>
          </TextSymbolizer>
        </Rule>

        <!-- When zoomed in closer than 1:250000 use the feature name that
          includes the feature code. This is important for identifying unnamed reefs. -->
        <Rule>
          <Name>Feature label</Name>
          <Title>&lt;1:250k Names+GBR ID</Title>

          <ogc:Filter>
            <ogc:PropertyIsNotEqualTo>
              <ogc:PropertyName>FEAT_NAME</ogc:PropertyName>
              <ogc:Literal>Mainland</ogc:Literal>
            </ogc:PropertyIsNotEqualTo>
          </ogc:Filter>
          <MaxScaleDenominator>250000</MaxScaleDenominator>
          <TextSymbolizer>
            <Label>
              <ogc:PropertyName>LOC_NAME_S</ogc:PropertyName>
            </Label>
            <Font>
              <CssParameter name="font-family">SansSerif</CssParameter>
              <CssParameter name="font-style">Normal</CssParameter>
              <CssParameter name="font-size">9</CssParameter>
            </Font>
            <!-- this centers the label on the polygon's centroid-->
            <LabelPlacement>
              <PointPlacement>
                <AnchorPoint>
                  <AnchorPointX>0.5</AnchorPointX>
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
                <CssParameter name="fill-opacity">1</CssParameter>
              </Fill>
            </Halo>
            <Fill>
              <CssParameter name="fill">#000000</CssParameter>
              <CssParameter name="fill-opacity">1</CssParameter>
            </Fill>
            <Priority>
              <ogc:PropertyName>SHAPE_AREA</ogc:PropertyName>
            </Priority>
            <VendorOption name="autoWrap">60</VendorOption>
            <VendorOption name="maxDisplacement">150</VendorOption>
          </TextSymbolizer>
        </Rule>
      </FeatureTypeStyle>
    </UserStyle>
  </NamedLayer>
</StyledLayerDescriptor>
