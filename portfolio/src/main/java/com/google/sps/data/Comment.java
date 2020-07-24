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

package com.google.sps.data;

/** Class for storing an information about user commentaries. */
public class Comment {
  private String name;
  private String text;
  private int rating;

  public Comment(String newName, String newText, int newRating) {
    name = newName;
    text = newText;
    rating = newRating;
  }

  public Comment() {
    name = "";
    text = "";
    rating = -1;
  }

  public String getName() {
    return name;
  }

  public String getText() {
    return text;
  }

  public int getRating() {
    return rating;
  }

  public void updateName(String newName) {
    name = newName;
  }

  public void updateText(String newText) {
    text = newText;
  }

  public void updateRating(int newRating) {
    rating = newRating;
  }
}
