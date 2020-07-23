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


import com.google.sps.data.CommentStat;
import com.google.sps.data.CommentsArray;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Enumeration;
import java.util.Map;
import java.util.Iterator;

import com.google.gson.Gson; 
import com.google.gson.GsonBuilder;  

/** Servlet that store and return information about user comments. */
@WebServlet("/comments")
public class DataServlet extends HttpServlet {

  // A list of all comments.
  CommentsArray commentsList = new CommentsArray();

  /** Processing GET request from the page "/comments", commentaries's statistics in JSON format will be returned. */
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

    String json = convertToJsonList(commentsList);

    response.setContentType("application/json;");
    response.getWriter().println(json);
  }

  private String convertToJsonList(CommentsArray obj) {
    Gson gson = new Gson();
    String json = gson.toJson(obj);
    return json;
  }

  /** Processing POST request by storing received commentary. */
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

    // Get the input from the form.
    CommentStat obj = new CommentStat();

    obj.name = request.getParameter("user-name");
    obj.commentText = request.getParameter("user-comment");
    obj.rating = Integer.valueOf(request.getParameter("user-rating"));

    commentsList.add(obj);

    // Create entity.
    Entity objEntity = new Entity("Comment");
    objEntity.setProperty("user-name", obj.name);
    objEntity.setProperty("user-comment", obj.commentText);
    objEntity.setProperty("user-rating", obj.rating);

    // Store entity.
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    datastore.put(objEntity);

    // Redirect back to the HTML page.
    response.sendRedirect("/index.html");
  }
}
