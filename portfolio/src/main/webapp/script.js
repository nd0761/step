// Copyright 2019 Google LLC
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

/**
 * Adds a random greeting to the page.
 */
function addRandomGreeting() {
  const greetings =
      ['Hello world!', '¡Hola Mundo!', '你好，世界！', 'Bonjour le monde!'];

  // Pick a random greeting.
  const greeting = greetings[Math.floor(Math.random() * greetings.length)];

  // Add it to the page.
  const greetingContainer = document.getElementById('greeting-container');
  greetingContainer.innerText = greeting;
}

/**
 * Adds commentaries element to the page.
 */
function showComments() {
  let dropdownNumber = document.getElementById('number-of-comments');
  let numberOfComments = dropdownNumber.options[dropdownNumber.selectedIndex].text;

  if (numberOfComments == "all") {
    numberOfComments = "0";
  }

  // Hardcoded URL with requsted number of comments.
  let URL = "/comments?number=" + numberOfComments;

  // Response received in the form: [{name: "", comment:"", rating:}, {}, {}].
  fetch(URL).then(response => response.json()).then((commentList) => {
    console.log("Recieve request with comments array")
    const commentsListElement = document.getElementById('comment-field-table');
    commentsListElement.innerHTML = '';

    for (i in commentList) {
      commentsListElement.appendChild(createListElement(
        commentList[i].name, commentList[i].text, commentList[i].rating)
      );
    }
  });
}

function createListElement(name, text, rating) {
  let newCommentBox = document.createElement('div');
  newCommentBox.className = "comment-box";

  let topSection = document.createElement('div');
  topSection.id = "top-section";

  let newName = document.createElement('p');
  newName.id = "name-section";
  newName.innerHTML = name;

  topSection.appendChild(newName);

  let ratingSection = document.createElement('div');
  ratingSection.id = "rating-section";
  
  let newRating = document.createElement('p');
  newRating.innerHTML = "Rating→" + rating.toString();

  ratingSection.appendChild(newRating);
  topSection.appendChild(ratingSection);

  let textSection = document.createElement('div');
  textSection.id = "text-section";

  let newText = document.createElement('p');
  newText.innerHTML = text;

  textSection.appendChild(newText);

  newCommentBox.appendChild(topSection);
  newCommentBox.appendChild(textSection);

  newCommentBox.id = "shown-comment";

  return newCommentBox;
}

function toggleCommentSection() {
  let displayButton = document.getElementsByClassName('display-comments-buttons')[0];
  let commentSection = document.getElementsByClassName('comment-section')[0];

  if (commentSection.style.display == "block") {
    commentSection.style.width = "none";
    displayButton.innerHTML = "Show comment section";

    console.log("Close comment section");
  } else {
    commentSection.style.width = "block";
    displayButton.innerHTML = "Close comment section";

    console.log("Show comment section");
  }
}

function deleteComments() {
  fetch("/delete-comments", {
    method: 'POST',
  }).then(res => console.log("Delete all comments"));
}

function initMap() {
  // Create styled map.
  var styledMapType = new google.maps.StyledMapType(
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
  let map = new google.maps.Map(document.getElementById('map'), {
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

  // The marker, positioned at Uluru
  let markerJapanTemple = new google.maps.Marker({position: japanTemple, map: map});
  let markerNewZealand = new google.maps.Marker({position: newZealandLake, map: map});
  let markerUganda = new google.maps.Marker({position: ugandaView, map: map});
}
