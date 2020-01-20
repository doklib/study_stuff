<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
 <link rel="stylesheet" href="https://cdn.jsdelivr.net/gh/openlayers/openlayers.github.io@master/en/v6.1.1/css/ol.css" type="text/css">
 <script src="https://cdn.jsdelivr.net/gh/openlayers/openlayers.github.io@master/en/v6.1.1/build/ol.js"></script>
 <script src="http://map.vworld.kr/js/vworldMapInit.js.do?apiKey=AEA97758-6E01-36AF-9F91-8A712F2BEB49"></script>
 <script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>   
    <style type="text/css">
	 #map {
            width: 100%;
            height: 500px;
        }
	</style>

    <title>OpenLayers example</title>

</head>
<body>
      <div id="map"></div>
    <script type="text/javascript">
    
    $(function () {
        var vectorSource = new ol.source.Vector({
            format: new ol.format.GeoJSON(),
            url: function (extent) {
                var strUrl = 'https://ahocevar.com/geoserver/wfs?service=WFS&' +
                    'version=1.1.0&request=GetFeature&typename=osm:water_areas&' +
                    'outputFormat=application/json&srsname=EPSG:3857&' +
                    'bbox=' + extent.join(',') + ',EPSG:3857';

                return strUrl;
            },
            strategy: ol.loadingstrategy.bbox
        });

        var vector = new ol.layer.Vector({
            source: vectorSource,
            style: new ol.style.Style({
                stroke: new ol.style.Stroke({
                    color: 'rgba(255, 255, 0, 1.0)',
                    width: 4
                }),
                fill: new ol.style.Fill({
                    color: 'rgba(255,0,0,0.4)'
                })
            })
        });

        var raster = new ol.layer.Tile({
            source: new ol.source.OSM()
        });

        var map = new ol.Map({
            layers: [raster, vector],
            target: 'map',
            view: new ol.View({
                center: [-8910887.277395891, 5382318.072437216],
                maxZoom: 19,
                zoom: 15
            })
        });
    });
      
    </script>
  </body>
</html>