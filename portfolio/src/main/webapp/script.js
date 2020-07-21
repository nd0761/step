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

//Fetch request from the page /comments and put returned info to the list
function showComments() {
    console.log("fetching comments");
    fetch("/comments").then(response => console.log(response.json().name.text()));

    fetch("/comments").then(response => response.json()).then((stats) => {
      const statsListElement = document.getElementById('comment-field');
      statsListElement.innerHTML = '';
      for (let i = 0; i < 3; i++) {
          statsListElement.appendChild(createListElement('Name: ' + stats.name[i]));
          statsListElement.appendChild(createListElement('Comment text: ' + stats.comment[i]));
          statsListElement.appendChild(createListElement('Rating: ' + stats.rating[i]));
          statsListElement.appendChild(createListElement('\n'));
      }
    });
}

function createListElement(text) {
  const liElement = document.createElement('li');
  liElement.innerText = text;
  return liElement;
}
