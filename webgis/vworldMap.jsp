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
    <script src="http://map.vworld.kr/js/vworldMapInit.js.do?apiKey=B3AC76A3-7F61-3957-9190-461F50B2095E"></script>
    <title>OpenLayers example</title>

</head>
<body>
     <h2>My Map</h2>
    <div id="vMap" class="map"></div>
    <script type="text/javascript">
    var apiMap;//2D map
    var SOPPlugin;//3D map
    var apiMap2;//2D map
    var SOPPlugin2;//3D map
    vworld.showMode = true;//브이월드 배경지도 설정 컨트롤 유무(true:배경지도를 컨트롤 할수 있는 버튼 생성/false:버튼 해제)
     
    /**
     * - rootDiv, mapType, mapFunc, 3D initCall, 3D failCall
     * - 브이월드 5가지 파라미터를 셋팅하여 지도 호출
     */
    vworld.init("vMap", "map-first", 
        function() {        
            apiMap = this.vmap;//브이월드맵 apiMap에 셋팅 
            apiMap.setBaseLayer(apiMap.vworldBaseMap);//기본맵 설정 
            apiMap.setControlsType({"simpleMap":true}); //간단한 화면    
            apiMap.addVWORLDControl("zoomBar");
            apiMap.setCenterAndZoom(14153034.043823106,4501305.261406751,17);//화면중심점과 레벨로 이동 (초기 화면중심점과 레벨)
        },
        function (obj){//3D initCall(성공)
            SOPPlugin = obj;
        },
        function (msg){//3D failCall(실패)
            alert(msg);
        }
    );
    </script>
  </body>
</html>