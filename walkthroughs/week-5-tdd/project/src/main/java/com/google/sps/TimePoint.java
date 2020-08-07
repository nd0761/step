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
 * Class representing a point of time (either a start of an event or an end of it).
 */
public final class TimePoint {
  /**
   * A comparator for sorting objects by their time in ascending order.
   */
  public static final Comparator<TimePoint> ORDER_END_START = new Comparator<TimePoint>() {
    @Override
    public int compare(TimePoint a, TimePoint b) {
      if (a.time() == b.time()) {
        if (a.type() == 2) {
          return -1;
        }
        if (b.type() == 2) {
          return 1;
        }
        if (a.type() == 4) {
          return -1;
        }
        return 1;
      }
      return Long.compare(a.time(), b.time());
    }
  };

  private final int time;

  // Type of time point 1 - start of an event for mandatory attendee, 2 - end of an event for mandatory, 3 - start for optional, 4 - end for optional.
  private final int type;
  // A number of optional attendees for that event, it will be -1 if there some mandatory attendee for that event.
  private final int numberOfOptional;

  public TimePoint(int time, int type, int numberOfOptional) {
    this.time = time;
    this.type = type;
    this.numberOfOptional = numberOfOptional;
  }

  public int time() {
    return time;
  }

  public int type() {
    return type;
  }

  public int numberOfOptional() {
    return numberOfOptional;
  }

  public String toString() {
    return String.format("Time: [%d], Type: [%d]", time, type);
  }
}