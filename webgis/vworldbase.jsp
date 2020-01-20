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
    <script src="https://www.openlayers.org/api/2.13/OpenLayers.js" type="text/javascript"></script>
    <script src="http://map.vworld.kr/js/apis.do?type=Base&apiKey=AEA97758-6E01-36AF-9F91-8A712F2BEB49"></script>
    <title>OpenLayers example</title>

</head>

 <body onload="init()">
    <div id="map" style="height:600px;"></div>
    <div >
        <button type="button" onclick="javascript:deleteLayerByName('VHYBRID');" name="rpg_1" >레이어삭제하기</button>
    </div> 
 </body>          
    <script type="text/javascript">
    var map;
    var mapBounds = new OpenLayers.Bounds(123 , 32, 134 , 43);
    var mapMinZoom = 7;
    var mapMaxZoom = 19;

    // avoid pink tiles
    OpenLayers.IMAGE_RELOAD_ATTEMPTS = 3;
    OpenLayers.Util.onImageLoadErrorColor = "transparent";
     
    function init(){
    var options = {
        controls: [],
        projection: new OpenLayers.Projection("EPSG:900913"),
        displayProjection: new OpenLayers.Projection("EPSG:4326"),
        units: "m",
        controls : [],
        numZoomLevels:21,
        maxResolution: 156543.0339,
        maxExtent: new OpenLayers.Bounds(-20037508.34, -20037508.34, 20037508.34, 20037508.34)
        };
    map = new OpenLayers.Map('map', options);
     
    var osm = new OpenLayers.Layer.OSM();                   
    var options = {serviceVersion : "",
        layername: "",
        isBaseLayer: false,
        opacity : 1,
        type: 'png',
        transitionEffect: 'resize',
        tileSize: new OpenLayers.Size(256,256),
        min_level : 7,
        max_level : 18,
        buffer:0};
    //======================================
    //1. 배경지도 추가하기
    vBase = new vworld.Layers.Base('VBASE');
    if (vBase != null){map.addLayer(vBase);}
    //2. 영상지도 추가하기
    vSAT = new vworld.Layers.Satellite('VSAT');
    if (vSAT != null) {map.addLayer(vSAT);};
    //3. 하이브리드지도 추가하기
    vHybrid = new vworld.Layers.Hybrid('VHYBRID');
    if (vHybrid != null) {map.addLayer(vHybrid);} 
    //4. Gray지도 추가하기
    vGray = new vworld.Layers.Gray('VGRAY');
    if (vGray != null){map.addLayer(vGray);}
    //5. Midnight지도 추가하기
    vMidnight = new vworld.Layers.Midnight('VMIDNIGHT');
    if (vMidnight != null){map.addLayer(vMidnight);}
    //===========================================
     
    map.addLayers([osm]);

    var switcherControl = new OpenLayers.Control.LayerSwitcher();
    map.addControl(switcherControl);
    switcherControl.maximizeControl();

    map.zoomToExtent( mapBounds.transform(map.displayProjection, map.projection ) );
    map.zoomTo(11);
         
    map.addControl(new OpenLayers.Control.PanZoomBar());
    //map.addControl(new OpenLayers.Control.MousePosition());
    map.addControl(new OpenLayers.Control.Navigation());
    //map.addControl(new OpenLayers.Control.MouseDefaults()); //2.12 No Support
    map.addControl(new OpenLayers.Control.Attribution({separator:" "}))
}
function deleteLayerByName(name){
    for (var i=0, len=map.layers.length; i<len; i++) {
        var layer = map.layers[i];
        if (layer.name == name) {
            map.removeLayer(layer);
            //return layer;
            break;
        }
    }
}
    </script>
  
</html>