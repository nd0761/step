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
import com.google.sps.data.Marker;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;

import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

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

/** Servlet that process information about user's markers. */
@WebServlet("/markers")
public class MapMarker extends HttpServlet {
  /** Processes GET request by returning list of marker coords in JSON format. */
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {// Create new Query for Commen objects.
    Query query = new Query("Marker");

    // Get access to dataStore.
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    PreparedQuery results = datastore.prepare(query);

    Gson gson = new Gson();

    // Get list of markers.
    List<Marker> markersList = new ArrayList<>();
    for (Entity entity : results.asIterable()) {
      double lat = 123.0, lng = 123.0;
      lat = (Double) entity.getProperty("lat");
      lng = (Double) entity.getProperty("lng");

      Marker marker = new Marker(lat, lng);
      
      markersList.add(marker);
    }

    // Convert list of comments to JSON format.
    String json = gson.toJson(markersList);

    // Return response to the request.
    response.setContentType("application/json;");
    response.getWriter().println(json);
  }

  /** Processes POST request by storing received markers. */
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    // Create DataStore entity.
    Entity markerEntity = new Entity("Marker");

    markerEntity.setProperty("lat", Double.valueOf(request.getParameter("lat")));
    markerEntity.setProperty("lng", Double.valueOf(request.getParameter("lng")));

    // Store new marker.
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    datastore.put(markerEntity);

    // Redirect back to the HTML page.
    response.sendRedirect("/index.html");
  }
}
