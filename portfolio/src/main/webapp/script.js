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

  // Response received in the form: [{name: "", comment:"", rating:}, {}, {}].
  fetch("/comments").then(response => response.json()).then((commentList) => {
    const commentsListElement = document.getElementById('comment-field-table');
    commentsListElement.innerHTML = '';

    for (i in commentList) {
      commentsListElement.appendChild(createListElement('Name: ' + commentList[i].name));
      commentsListElement.appendChild(createListElement('Comment text: ' + commentList[i].text));
      commentsListElement.appendChild(createListElement('Rating: ' + commentList[i].rating));
      commentsListElement.appendChild(createListElement('\n'));
    }
  });
}

function createListElement(text) {
  const liElement = document.createElement('li');
  liElement.innerText = text;
  return liElement;
}
