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
               // url: 'http://xdworld.vworld.kr:8080/2d/gray/201802/{z}/{x}/{y}.png'
                
            })
          })
        ],
        view: new ol.View({
            center : [ 14135652.514834214, 4520180.104672182 ],
            zoom: 18,
            minZoom: 10,
            maxZoom: 19
        })
      });
      var wmsSource = new ol.source.TileWMS({
    	     url: 'http://localhost:8080/geoserver/tutorial/wms',
    	     params: {
    				VERSION : '1.1.1',
    				LAYERS : 'tutorial:AF01_Base,tutorial:AF01_Beacon,tutorial:AF01_Path',
    				//WIDTH : 478,
    				//HEIGHT : 768,
    				//STYLES : 'population_density',
    				SRS : 'EPSG:5186',
    				TILED : true
    			},
    			serverType : 'geoserver'
    		});

    	var wmsLayer = new ol.layer.Tile({
    	    source: wmsSource
    	});

    	map.addLayer(wmsLayer);
      
       
       
      
    </script>
  </body>
</html>