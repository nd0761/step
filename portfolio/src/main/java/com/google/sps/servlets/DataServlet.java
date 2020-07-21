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

/* Servlet that returns json of commentaries statistics*/
@WebServlet("/comments")
public class DataServlet extends HttpServlet {

  /* Some hardcoded examples*/
  String [] commentList = {"Hi! I'm Mark", "Hello, my name is Nancy :3", "Good morning/afternoon/evening! I'm Alex"};
  String [] nameList = {"Mark", "Nancy", "Alex"};
  String [] ratingList = {"4/5", "5/5", "0/5"};
  int listLength = commentList.length;

  /* Processing of GET request to /comment page, json dtring will be returned*/
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String json = "{";
    json += convertToJsonList("comment", commentList);
    json += ",";
    json += convertToJsonList("name", nameList);
    json += ",";
    json += convertToJsonList("rating", ratingList);
    json += "}";

    // Send the JSON as the response
    response.setContentType("application/json;");
    response.getWriter().println(json);
  }

  //Converter to json format "name":[item1, item2, item3, ...]
  private String convertToJsonList(String type, String [] listOfObj) {
    String json = "\"" + type + "\":[";
    for (int i = 0; i < listLength; ++i) {
        json += "\"" + listOfObj[i] + "\"";
        if (i != listLength - 1) {
            json += ",";
        } else {
            json += "]";
        }
    }
    return json;
  }
}
