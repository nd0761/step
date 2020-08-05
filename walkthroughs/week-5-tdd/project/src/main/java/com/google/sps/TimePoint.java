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

package com.google.sps;

import java.util.Comparator;

/**
 * Class representing a point of time (either start of an event or end of it).
 */
public final class TimePoint {

  /**
   * A comparator for sorting objects by their time in ascending order.
   */
  public static final Comparator<TimePoint> ORDER_END_START = new Comparator<TimePoint>() {
    @Override
    public int compare(TimePoint a, TimePoint b) {
        return Long.compare(a.time(), b.time());
    }
  };

  private final int time;

  // Type of time point true - if it is the start of an event, false - for the end of it.
  private final boolean type;

  public TimePoint(int time, boolean type) {
    this.time = time;
    this.type = type;
  }

  public int time() {
    return time;
  }

  public boolean type() {
    return type;
  }

  public String toString() {
    return String.format("Time: [%d], Type: [%b]", time, type);
  }
}
