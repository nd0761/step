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
 * Class representing information about choosen time slot.
 */
public final class TimeSlot {
  // Basic information about time slot (start, duration and the end).
  private final TimeRange timeSlotInfo;
  
  // A number of optional attendees for that event.
  private final int numberOfOptional;

  public TimeSlot(TimeRange timeSlotInfo, int numberOfOptional) {
    this.timeSlotInfo = timeSlotInfo;
    this.numberOfOptional = numberOfOptional;
  }

  public TimeRange timeSlotInfo() {
    return timeSlotInfo;
  }

  public int numberOfOptional() {
    return numberOfOptional;
  }

  public String toString() {
    return String.format("TimeRange: %s, Number of Optinonal: %d", timeSlotInfo.toString(), numberOfOptional);
  }
}
