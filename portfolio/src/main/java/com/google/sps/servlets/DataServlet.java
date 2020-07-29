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

package com.google.sps.servlets;

import com.google.sps.data.Comment;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;

import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Enumeration;
import java.util.Map;
import java.util.Iterator;

import com.google.gson.Gson; 
import com.google.gson.GsonBuilder;  

/** Servlet that store and return information about user comments. */
@WebServlet("/comments")
public class DataServlet extends HttpServlet {
  /** Processes GET requests for "/comments" and returns a list of comments in JSON format. */
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    // Get requested number of comments.
    int numberOfComments;
    
    // Check for the number parameter in request.
    try {
      numberOfComments = Integer.valueOf(request.getParameter("number"));
    } catch(Exception e) {
      numberOfComments = -1;
    }

    if (numberOfComments == 0) {
      numberOfComments = -1;
    }
    // Create new Query for Commen objects.
    Query query = new Query("Comment").addSort("timestamp", SortDirection.DESCENDING);

    // Get access to dataStore.
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    PreparedQuery results = datastore.prepare(query);

    // Get list of comments.
    List<Comment> commentsList = new ArrayList<>();
    for (Entity entity : results.asIterable()) {
      if (numberOfComments == 0) {
        break;
      } else {
        numberOfComments -= 1;
      }
      
      String name = (String) entity.getProperty("name");
      String text = (String) entity.getProperty("text");
      int rating = ((Long) entity.getProperty("rating")).intValue();

      Comment comment = new Comment(name, text, rating);
      
      commentsList.add(comment);
    }

    // Convert list of comments to JSON format.
    Gson gson = new Gson();
    String json = gson.toJson(commentsList);

    // Return response to the request.
    response.setContentType("application/json;");
    response.getWriter().println(json);
  }

  /** Processes POST request by storing received commentary. */
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    // Create DataStore entity.
    Entity commentEntity = new Entity("Comment");
    long timestamp = System.currentTimeMillis();

    commentEntity.setProperty("name", request.getParameter("user-name"));
    commentEntity.setProperty("text", request.getParameter("user-text"));
    commentEntity.setProperty("rating", Integer.valueOf(request.getParameter("user-rating")));
    commentEntity.setProperty("timestamp", timestamp);

    // Store new comment.
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    datastore.put(commentEntity);

    // Redirect back to the HTML page.
    response.sendRedirect("/index.html");
  }
}
