// Copyright 2020 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

let map;

function showMarkers() {
  // Initialize new map with markers.
  let showMarkers = true;
  initMap(showMarkers);
  fetch("/markers").then(response => response.json()).then((markerList) => {
    console.log("Get list of markers");

    for (i in markerList) {
      let position = {lat: markerList[i].lat, lng: markerList[i].lng};
      let marker = new google.maps.Marker({position: position, map: map});
    }
  });
}

function initMap(showMarkers = true) {
  // Create styled map type object.
  let styledMapType = new google.maps.StyledMapType(
    [
      {elementType: 'geometry', stylers: [{color: '#ebe3cd'}]},
      {elementType: 'labels.text.fill', stylers: [{color: '#523735'}]},
      {elementType: 'labels.text.stroke', stylers: [{color: '#f5f1e6'}]},
      {
        featureType: 'administrative',
        elementType: 'geometry.stroke',
        stylers: [{color: '#c9b2a6'}]
      },
      {
        featureType: 'administrative.land_parcel',
        elementType: 'geometry.stroke',
        stylers: [{color: '#dcd2be'}]
      },
      {
        featureType: 'administrative.land_parcel',
        elementType: 'labels.text.fill',
        stylers: [{color: '#ae9e90'}]
      },
      {
        featureType: 'landscape.natural',
        elementType: 'geometry',
        stylers: [{color: '#dfd2ae'}]
      },
      {
        featureType: 'poi',
        elementType: 'geometry',
        stylers: [{color: '#dfd2ae'}]
      },
      {
        featureType: 'poi',
        elementType: 'labels.text.fill',
        stylers: [{color: '#93817c'}]
      },
      {
        featureType: 'poi.park',
        elementType: 'geometry.fill',
        stylers: [{color: '#a5b076'}]
      },
      {
        featureType: 'poi.park',
        elementType: 'labels.text.fill',
        stylers: [{color: '#447530'}]
      },
      {
        featureType: 'road',
        elementType: 'geometry',
        stylers: [{color: '#f5f1e6'}]
      },
      {
        featureType: 'road.arterial',
        elementType: 'geometry',
        stylers: [{color: '#fdfcf8'}]
      },
      {
        featureType: 'road.highway',
        elementType: 'geometry',
        stylers: [{color: '#f8c967'}]
      },
      {
        featureType: 'road.highway',
        elementType: 'geometry.stroke',
        stylers: [{color: '#e9bc62'}]
      },
      {
        featureType: 'road.highway.controlled_access',
        elementType: 'geometry',
        stylers: [{color: '#e98d58'}]
      },
      {
        featureType: 'road.highway.controlled_access',
        elementType: 'geometry.stroke',
        stylers: [{color: '#db8555'}]
      },
      {
        featureType: 'road.local',
        elementType: 'labels.text.fill',
        stylers: [{color: '#806b63'}]
      },
      {
        featureType: 'transit.line',
        elementType: 'geometry',
        stylers: [{color: '#dfd2ae'}]
      },
      {
        featureType: 'transit.line',
        elementType: 'labels.text.fill',
        stylers: [{color: '#8f7d77'}]
      },
      {
        featureType: 'transit.line',
        elementType: 'labels.text.stroke',
        stylers: [{color: '#ebe3cd'}]
      },
      {
        featureType: 'transit.station',
        elementType: 'geometry',
        stylers: [{color: '#dfd2ae'}]
      },
      {
        featureType: 'water',
        elementType: 'geometry',
        stylers: [{color: '#17263c'}]
      },
      {
        featureType: 'water',
        elementType: 'labels.text.fill',
        stylers: [{color: '#515c6d'}]
      },
      {
        featureType: 'water',
        elementType: 'labels.text.stroke',
        stylers: [{color: '#17263c'}]
      }
    ],
    {name: 'Styled Map'}
  );

  // Create markers.
  let japanTemple = {lat: 34.462117, lng: 135.830272};
  let newZealandLake = {lat: -43.979931, lng: 170.194799};
  let ugandaView = {lat: -0.099273, lng: 32.652921}

  // Create a map object, and include the MapTypeId to add
  // to the map type control.
  map = new google.maps.Map(document.getElementById('map'), {
    center: new google.maps.LatLng(0.211772, 102.290621),
    zoom: 2.3,
    mapTypeControlOptions: {
      mapTypeIds: ['roadmap', 'satellite', 'hybrid', 'terrain',
              'styled_map']
    }
  });

  //Associate the styled map with the MapTypeId and set it to display.
  map.mapTypes.set('styled_map', styledMapType);
  map.setMapTypeId('styled_map');

  // Set markers on the map if marker flag is True.
  if (showMarkers) {
    let markerJapanTemple = new google.maps.Marker({position: japanTemple, map: map});
    let markerNewZealand = new google.maps.Marker({position: newZealandLake, map: map});
    let markerUganda = new google.maps.Marker({position: ugandaView, map: map});
  }
  console.log("Initialise new map");
}

async function showEclipse() {
  // Initialize new map without markers.
  let showMarkers = false;
  initMap(showMarkers);
  console.log("Initialise new map");
  
  // Parse csv files for solar/lunar eclipses from 2020 to 2030.
  let solarEclipse = await parseCsv("./eclipse/solar_decade.csv");
  console.log("Parse solar eclipses file");
  console.log(solarEclipse[0]);

  let lunarEclipse = await parseCsv("./eclipse/lunar_decade.csv");
  console.log("Parse lunar eclipses file");
  console.log(lunarEclipse[0]);

  
  // Add markers to the map.
  let solarLatCoord = 9;
  let solarLngCoord = 10;
  let solarDate = 1;
  let solarTime = 2;
  for (let i = 1; i < solarEclipse.length; i++) {
    let lat = parseLat(solarEclipse[i][solarLatCoord]);
    let lng = parseLng(solarEclipse[i][solarLngCoord]);

    let infowindow = new google.maps.InfoWindow({
      content: "<p><b>Date</b>: " + solarEclipse[i][solarDate] +
              "</p><p><b>Time</b>: " + solarEclipse[i][solarTime] + "</p>"
    });

    let solarMarker = new google.maps.Marker({
      position: new google.maps.LatLng(lat, lng), 
      map: map,
      icon: {
        path: google.maps.SymbolPath.CIRCLE,
        scale: 8.5,
        fillColor: "#ff3",
        fillOpacity: 0.4,
        strokeWeight: 0.4
      },
    });

    solarMarker.addListener('mouseover', function() {
      infowindow.open(map, solarMarker);
    });
    solarMarker.addListener('mouseout', function() {
      infowindow.close();
    });
  }
  console.log("Add " + (solarEclipse.length - 1).toString() + " solar markers");

  let lunarLatCoord = 11;
  let lunarLngCoord = 12;
  let lunarDate = 1;
  let lunarTime = 2;
  for (let i = 1; i < lunarEclipse.length; i++) {
    let lat = parseLat(lunarEclipse[i][lunarLatCoord]);
    let lng = parseLng(lunarEclipse[i][lunarLngCoord]);

    let infowindow = new google.maps.InfoWindow({
      content: "<p><b>Date</b>: " + lunarEclipse[i][lunarDate] +
              "</p><p><b>Time</b>: " + lunarEclipse[i][lunarTime] + "</p>"
    });

    let lunarMarker = new google.maps.Marker({
      position: new google.maps.LatLng(lat, lng), 
      map: map,
      icon: {
        path: google.maps.SymbolPath.CIRCLE,
        scale: 8.5,
        fillColor: "#491e3c",
        fillOpacity: 0.4,
        strokeWeight: 0.4
      },
    });

    lunarMarker.addListener('mouseover', function() {
      infowindow.open(map, lunarMarker);
    });
    lunarMarker.addListener('mouseout', function() {
      infowindow.close();
    });
  }
  console.log("Add " + (lunarEclipse.length - 1).toString() + " lunar markers");
}

async function parseCsv(filepath) {
  return await fetch(filepath).then(response => response.text()).then(csvFile => {
    let parsedLines = [];

    // Store lines from the file.
    let fileLines = csvFile.split(/\r\n|\n/);

    // Parse file.
    for (let i = 0; i < fileLines.length; ++i) {
      parsedLines.push(fileLines[i].split(','));
    }
    return parsedLines;
  });
}

function parseLat(initLat) {
  let number = parseFloat(initLat.substr(0, initLat.length - 1));
  if (initLat[initLat.length - 1] == 's' || initLat[initLat.length - 1] == 'S') {
    number *= -1;
  }
  return number;
}

function parseLng(initLng) {
  let number = parseFloat(initLng.substr(0, initLng.length - 1));
  if (initLng[initLng.length - 1] == 'w' || initLng[initLng.length - 1] == 'W') {
    number *= -1;
  }
  return number;
}
