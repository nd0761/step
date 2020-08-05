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

package com.google.sps;

import java.util.Comparator;

/**
 * Class representing a span of time, enforcing properties (e.g. start comes before end) and
 * providing methods to make ranges easier to work with (e.g. {@code overlaps}).
 */
public final class TimeObj {

  /**
   * A comparator for sorting ranges by their start time in ascending order.
   */
  public static final Comparator<TimeObj> ORDER_END_START = new Comparator<TimeObj>() {
    @Override
    public int compare(TimeObj a, TimeObj b) {
        return Long.compare(a.time(), b.time());
    }
  };

  private final int time;
  private final boolean type;

  public TimeObj(int time, boolean type) {
    this.time = time;
    this.type = type;
  }

  /**
   * Returns the start of the range in minutes.
   */
  public int time() {
    return time;
  }
  public boolean type() {
    return type;
  }public String toString() {
    return String.format("Time: [%d] Type:, (%b)", time, type);
  }
}
