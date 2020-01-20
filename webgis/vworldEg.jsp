<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
 <link rel="stylesheet" href="https://cdn.jsdelivr.net/gh/openlayers/openlayers.github.io@master/en/v6.1.1/css/ol.css" type="text/css">
    <style>
      .map {
        height: 400px;
        width: 100%;
      }
    </style>
    <script src="https://cdn.jsdelivr.net/gh/openlayers/openlayers.github.io@master/en/v6.1.1/build/ol.js"></script>
    <script src="http://map.vworld.kr/js/vworldMapInit.js.do?apiKey=AEA97758-6E01-36AF-9F91-8A712F2BEB49"></script>
     <script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
    <title>OpenLayers example</title>

</head>
<body>
     <h2>My Map</h2>
    <div id="map" class="map"></div>
    <script type="text/javascript">
    
    
      var map = new ol.Map({
        target: 'map',
        layers: [
          new ol.layer.Tile({
            source: new ol.source.XYZ({
                //Vworld Tile 변경
                url: 'http://xdworld.vworld.kr:8080/2d/Base/201802/{z}/{x}/{y}.png'
            })
          })
        ],
        view: new ol.View({
        	//경도,위도 (EPSG : 4326)
            center: [14128579.82, 4512570.74],
            zoom: 14,
            minZoom: 10,
            maxZoom: 19
        })
      });
      var wmsSource = new ol.source.TileWMS({
    	     url: 'https://wms.geo.admin.ch/',
    	     params: {'LAYERS': 'ch.babs.kulturgueter','TILED': true},
    	     serverType: 'geoserver',
    	     projection: 'EPSG:4326',
    	     transition: 0
    	});

    	var wmsLayer = new ol.layer.Tile({
    	    source: wmsSource
    	});

    	map.addLayer(wmsLayer);
      
       
       
      
    </script>
  </body>
</html>