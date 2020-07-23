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

  // A list of all comments.
  ArrayList<Comment> commentsList = new ArrayList<>();

  /** Processes GET requests for "/comments" and returns a list of comments in JSON format. */
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String json = convertToJsonList();

    response.setContentType("application/json;");
    response.getWriter().println(json);
  }

  private String convertToJsonList() {
    Gson gson = new Gson();
    String json = gson.toJson(commentsList);
    return json;
  }

  /** Processes POST request by storing received commentary. */
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

    // Get the input from the form.
    Comment newComment = new Comment();

    newComment.updateName(request.getParameter("user-name"));
    newComment.updateText(request.getParameter("user-comment"));
    newComment.updateRating(Integer.valueOf(request.getParameter("user-rating")));

    commentsList.add(newComment);

    // Redirect back to the HTML page.
    response.sendRedirect("/index.html");
  }
}
