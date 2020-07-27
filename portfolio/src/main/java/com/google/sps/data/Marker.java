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
public class Marker {
  private double lat;
  private double lng;

  public Marker(double newLat, double newLng) {
    lat = newLat;
    lng = newLng;
  }

  public Marker() {
    lat = 0.0;
    lng = 0.0;
  }

  public double getLat() {
    return lat;
  }

  public double getLng() {
    return lng;
  }

  public void updateLat(double newLat) {
    lat = newLat;
  }

  public void updateLng(double newLng) {
    lng = newLng;
  }
}
