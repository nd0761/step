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

package com.google.sps.servlets;

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Arrays;

import com.google.gson.Gson; 
import com.google.gson.GsonBuilder;  

/** Servlet that returns commentaries's statistics in JSON format. */
@WebServlet("/comments")
public class DataServlet extends HttpServlet {

  class commentStat {
      String name;
      String commentText;
      int rating;

      public commentStat(String newName, String newText, int newRating) {
        name = newName;
        commentText = newText;
        rating = newRating;
      }

      public String getName() {
        return name;
      }

      public String getText() {
        return commentText;
      }

      public int getRating() {
        return rating;
      }
  }

  /** Processing of GET request from the page "/comment", commentaries's statistics in JSON format will be returned. */
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

    // Some hardcoded examples.
    commentStat objt1 = new commentStat("Mark", "Hi! Im Mark!", 3);
    commentStat objt2 = new commentStat("Nora", "Hello) My name is Nora!", 2);
    commentStat objt3 = new commentStat("Sam", "Hi! Hello! Sam is here for you!", 5);

    List<commentStat> commentsList = Arrays.asList(objt1, objt2, objt3);

    String json = "{\"commentsArray\":[";
    for (int i = 0; i < commentsList.size(); ++i) {
      json += convertToJsonList(commentsList.get(i));
      if (i < commentsList.size() - 1) {
          json += ",";
      } else {
          json += "]";
      }
    }
    json += "}";

    response.setContentType("application/json;");
    response.getWriter().println(json);
  }

  private String convertToJsonList(commentStat obj) {
    Gson gson = new Gson();
    String json = gson.toJson(obj);
    return json;
  }
}
