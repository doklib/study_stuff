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
		width: 1400px;
		height: 1000px;
		border: 2px solid #ccc;
	}
	
	table.list_t1 { 
		margin:10px 0; text-align:center;border-collapse:collapse; width:600px; border-top:2px solid #005aa1;border-bottom:1px solid #e3e8e9;
	}
	table.list_t1 th, table.list_t1 td {
		border-right:1px solid #e3e8e9;border-bottom:1px solid #e3e8e9;font-size:13px;
	}
	table.list_t1 td {
		padding:2px; height:25px;
	}
	table.list_t1 th {
		background:#f6f6f6;padding:9px 10px;text-align:center;
	}
	table.list_t1 th:last-child,table.list_t1 td:last-child {
		border-right:none;
	}
	</style>

    <title>OpenLayers example</title>

</head>
<body>
     <div id="content_wp">
	<div id="content">

        <div class="custom-popup" id="map">
        	        
        </div>
		<input id="base-layer" type="button" value="지도" onclick="baseChange('satellite')">
        <input type="button" value="wms point" onclick="mapSubmit()">
		
		<table class="list_t1" id="gisTable">
	    	<tr>
	    		<th>위치</th>
	    		<th>경도</th>
				<th>위도</th>
	    	</tr>
	    </table>
	</div>
	</div>
    <script type="text/javascript">
    
    
 // 전역변수 (좌표들의 정보)
	var svyData;

	// 지도
    var layers = {};
    layers['vworld'] = new ol.layer.Tile({
        title : 'VWorld Gray Map',
        visible : true,
        type : 'base',
        source : new ol.source.XYZ({
            url : 'http://xdworld.vworld.kr:8080/2d/Base/201612/{z}/{x}/{y}.png'
        })
    });
//     layers['satellite'] = new ol.layer.Tile({
//         title : 'VWorld Gray Map',
//         visible : true,
//         type : 'base',
//         source : new ol.source.XYZ({
//             url : 'http://xdworld.vworld.kr:8080/2d/Satellite/201612/{z}/{x}/{y}.jpeg'
//         })
//     });
	layers['satellite'] = new ol.layer.Tile({
        title : 'osm',
        visible : true,
        type : 'base',
        source: new ol.source.OSM()
    });

	// vectorSource 선언
    var vectorSource = new ol.source.Vector({
        projection: 'EPSG:4326'
    });

	// vectorLayer 선언
    var vectorLayer = new ol.layer.Vector({
        source: vectorSource
    });
	
     
	// 지도뿌리기
    var map = new ol.Map({

		layers : [ layers['vworld'], vectorLayer],
		target : 'map',
		view : new ol.View({
			// center: ol.proj.transform([getLongi, getLati ], 'EPSG:4326', 'EPSG:3857'),
			center: [-8914042.07, 5382281.26],//ol.proj.fromLonLat([126.980321, 37.578453]),		// center 좌표
			zoom: 18,										// 초기화면 zoom level
			minZoom: 6,										// 최소 zoom level
			maxZoom: 20										// 최대 zoom level
		})
	});
	
	// 버튼 클릭시, 해당 타일(지도와 위성)로 변경
	function baseChange(data){
		if(data=="satellite"){	// 위성으로 변경
			$("#base-layer").val("위성");
			$("#base-layer").attr("onclick", "baseChange('vworld')");
			
		} else {				// 지도로 변경
			$("#base-layer").val("지도");
			$("#base-layer").attr("onclick", "baseChange('satellite')");
		}
		
		var layer = layers[data];
	    if (layer) {
	        layer.setOpacity(1);
	        updateRenderEdgesOnLayer(layer);
	        map.getLayers().setAt(0, layer);
	    }
	}

	// 타일 변경
	var updateRenderEdgesOnLayer = function(layer) {
	    if (layer instanceof ol.layer.Tile) {
	        var source = layer.getSource();
	    }
	};
	//wfs
// 	var vectorSource = new ol.source.Vector({
// 	    format: new ol.format.GeoJSON(),
// 	    url: function (extent) {
// 	        var strUrl = 'https://ahocevar.com/geoserver/wfs?service=WFS&' +
// 	            'version=1.1.0&request=GetFeature&typename=osm:water_areas&' +
// 	            'outputFormat=application/json&srsname=EPSG:3857&' +
// 	            'bbox=' + extent.join(',') + ',EPSG:3857';
	 
// 	        return strUrl;
// 	    },
// 	    strategy: ol.loadingstrategy.bbox
// 	});
// 	//wfs vector
// 	var vector = new ol.layer.Vector({
// 	    source: vectorSource,
// 	    style: new ol.style.Style({
// 	        stroke: new ol.style.Stroke({
// 	            color: 'rgba(255, 255, 0, 1.0)',
// 	            width: 4
// 	        }),
// 	        fill: new ol.style.Fill({
// 	            color: 'rgba(255,0,0,0.4)'
// 	        })
// 	    })
// 	});
	
	
	//wms
 	var wmsSource = new ol.source.TileWMS({
	     url: 'http://localhost:8080/geoserver/tutorial/wms',
	     params: {
				VERSION : '1.1.1',
				LAYERS : 'tutorial:AF01_Base,tutorial:AF01_BeaconOut,tutorial:AF01_Path',
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

//json data
// 	function wfs(longitude, latitude){
// // 	    function getJson(data) {
// // 	        var myVar1=data['avgtemp1'];
// // 	        document.getElementById("v1").innerHTML = myVar;
// // 	    }
// 	    function getJson(data) {
// 	        var myVar1=data.features[0].properties['avgtemp1'];
// 	        document.getElementById("v1").innerHTML = myVar1;
// 	    }
	 
// 	    var JsonUrl = "http://localhost:8080/geoserver/tutorial/wfs?service=wfs&version=2.0.0&request=GetFeature&typeNames=agristats:beekeeping&cql_filter=INTERSECTS(geom,POINT(" + longitude + " " + latitude + "))&outputFormat=text/javascript&format_options=callback:getJson";
// 	    $.ajax({
// 	        type: 'GET', 
// 	        url: JsonUrl,
// 	        dataType: 'jsonp',
// 	        jsonpCallback:'getJson',
// 	        success: getJson
// 	    });
// 	}
// 	wfs(38, 23);
	
	
	// 포인트 클릭
    function mapSubmit(){
		// get  // 이 부분은 나중에 ajax 등을 통해서 좌표 값을 json object 형태로 가져와서 data 에 넣어주면 됨.
		var data = new Array();
		
		var coord = new Object();
		coord.svy_name = "미술관";
		coord.svy_longi = 126.980321;
		coord.svy_lati = 37.578453;
    	data.push(coord);  
    	
// 		var coord = new Object();
// 		coord.svy_name = "약진너머해수욕장";
// 		coord.svy_longi = 126.233439;
// 		coord.svy_lati = 37.180287;
//     	data.push(coord); 
// 		var coord = new Object();
// 		coord.svy_name = "떼뿌루해수욕장";
// 		coord.svy_longi = 126.175860;
// 		coord.svy_lati = 37.211874;
//     	data.push(coord); 
		// get
	
		svyData = data;
		var jsonData = JSON.stringify(data) ;
		addFeatures();	 
    }
    
	// feature 추가
    function addFeatures() {
    	
        var results = svyData;
		var len = results.length;
        
        var i, nm, lat, lon, geom, iconFeature, iconFeatures = [];
        
        vectorSource.clear();	// vectorSource 초기화 (안에 들어있던 features 를 삭제

		for(i=0 ; i<len ; i++){	// object 길이 만큼 반복문
			nm = results[i].svy_name;
			lon = results[i].svy_longi;
			lat = results[i].svy_lati;
			
			iconFeature = new ol.Feature({
				geometry: new ol.geom.Point(ol.proj.transform([lon, lat], 'EPSG:4326', 'EPSG:3857')),
				nm: nm,
				lon: lon,
				lat: lat
			});
			
			iconFeature.setStyle(
				new ol.style.Style({
						// jsp 에서는 잘 되던데.. html 로 뜯어오니.. 정상 작동을 안해서 주석처리
// 							 image: new ol.style.Icon({
// 							anchor: [0.5, 0.95],
// 							color: "#ff0000",
// 							src: '이미지경로'
// 						}) 
						
					
					// 지도상에 찍히는 포인트 스타일 설정
			  			 image: new ol.style.Circle({
						fill: new ol.style.Fill({ color: [255,190,10,0.7] }),
						stroke: new ol.style.Stroke({ color: [120,120,120,1] }),
						radius: 6
					}) 
				})
			);
			iconFeatures.push(iconFeature);		// iconFeature 에 넣기. 조건문을 이용해 아이콘(포인트)를 다르게 찍어도 괜찮을듯.
		}
		 
        vectorSource.addFeatures(iconFeatures);	// vectorSource 에 features 를 넣기.
        
        map.getView().fit(vectorLayer.getSource().getExtent(), map.getSize());	// 지도상 좌표들이 다 보이는 최소크기로 이동
        
        return iconFeatures;
    }
	
	// 맵클릭시 팝업 이벤트
	map.on('click', function (evt) {
		var feature = map.forEachFeatureAtPixel(evt.pixel,
			function (feature) {
				return feature;
			}
		);
		if (feature) {
			alert("위치 : "+feature.get('nm'));
		}
	});
	
	
	// feature들 선택하는 이벤트  (컨트롤 + 드래그)
	var select = new ol.interaction.Select();
    map.addInteraction(select);

    var selectedFeatures = select.getFeatures();

	var dragBox = new ol.interaction.DragBox({
		condition: ol.events.condition.platformModifierKeyOnly
	});
	
    map.addInteraction(dragBox);

    // 드래그 할때 이벤트
    dragBox.on('boxend', function() {
      // var info = [];
		var extent = dragBox.getGeometry().getExtent();
		vectorSource.forEachFeatureIntersectingExtent(extent, function(feature) {
			selectedFeatures.push(feature);				
			$("#gisTable").append("<tr>"+"<td>"+feature.get('nm')+"</td>"+"<td>"+feature.get('lon')+"</td>"+"<td>"+deg_to_dms (feature.get('lat'))+"</td>"+"<tr>");
		});      
    });

    // 드래그 하려고 클릭했을때 이벤트
    dragBox.on('boxstart', function() {
    	var infoTable = document.getElementById('gisTable');
		var fileIndex = infoTable.rows.length-1;
		// 목록과 첫번째줄 남기고 삭제
		for (var i=0; i<fileIndex ; i++){
			$('#gisTable > tbody:last > tr:last').remove();
		}
		selectedFeatures.clear();	// 선택한 feature 삭제.
		// infoBox.innerHTML = '&nbsp;';
    });
	
	// degree 를 도분초로
	function deg_to_dms (deg) {
		
		var d = Math.floor (Number(deg));
		
		var minfloat = (deg-d)*60;
		var m = Math.floor(minfloat);
		
		var secfloat = (minfloat-m)*60;
		
		var s = secfloat.toFixed(4);
		
		if (s==60) {
			m++;
			s=0;
		}
		if (m==60) {
			d++;
			m=0;
		}
		return ("" + d + "˚ " + pad(m, 2) + "′ " + pad(s, 2) + "″");
	}
	
	// 소수점 자리 제한
	function pad(n, width) {
	  n = n + '';
	  return n.length >= width ? n : new Array(width - n.length + 1).join('0') + n;
	}
	 
	
      
    </script>
  </body>
</html>