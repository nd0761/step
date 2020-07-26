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

  if (commentSection.style.width == "450px") {
    commentSection.style.width = "0%";
    displayButton.innerHTML = "Show comment section";

    let comments = document.getElementById("shown-comment");
    while (comments != null) {
      comments.parentNode.removeChild(comments);
      comments = document.getElementById("shown-comment");
    }

    console.log("Remove comment section");
  } else {
    commentSection.style.width = "450px";
    displayButton.innerHTML = "Close comment section";

    console.log("Show comment section");
  }
}

function deleteComments() {
  fetch("/delete-comments", {
    method: 'POST',
  }).then(res => console.log("Delete all comments"));
}
