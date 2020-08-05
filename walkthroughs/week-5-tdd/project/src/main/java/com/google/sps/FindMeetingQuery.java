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

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Enumeration;
import java.util.Map;
import java.util.Iterator;

import java.util.Comparator;

public final class FindMeetingQuery {
  public Collection<TimeRange> query(Collection<Event> events, MeetingRequest request) {
    // Create a list of existing meetings with mandatory attendees.
    List<TimePoint> importantEvents = new ArrayList<>();
  
    // List of possible time slots for the request.
    Collection<TimeRange> possibleSlots = new ArrayList<>();

    // Add start and end of the day as time points.
    TimePoint startOfTheDay = new TimePoint(0, true);
    TimePoint endOfTheDay = new TimePoint(24 * 60, false);

    importantEvents.add(startOfTheDay);
    importantEvents.add(endOfTheDay);

    // Pick events with mandatory attendees.
    for (Event event : events) {
      for (String attendee: request.getAttendees()) {
        if (event.getAttendees().contains(attendee)) {
          TimePoint startOfEvent = new TimePoint(event.getWhen().start(), true);
          importantEvents.add(startOfEvent);
          TimePoint endOfEvent = new TimePoint(event.getWhen().end(), false);
          importantEvents.add(endOfEvent);
          break;
        }
      }
    }

    // Sort all time points.
    Collections.sort(importantEvents, TimePoint.ORDER_END_START);

    // Number of active meeting for the moment under consideration.
    int numberOfActiveMeetings = 0;
    int endOfLastMeeting = 0;

    // numberOfActiveMeetings will be 0  - before start and after end of the day,
    // 1 - if current time slot is free for all mandatory attendees,
    // >1 - in any other case.
  
    // numberOfActiveMeetings will increase if new time point under consideration is a start for a meeting, and decrease in other case.
    for(TimePoint event : importantEvents) {
      if (numberOfActiveMeetings == 1) {
        // Check if there is enough time for requested meeting.
        if (event.time() - endOfLastMeeting >= request.getDuration()) {
          TimeRange freeSlot = TimeRange.fromStartDuration(endOfLastMeeting, event.time() - endOfLastMeeting);
          possibleSlots.add(freeSlot);
        }
      }
      
      if (event.type()) {
        numberOfActiveMeetings += 1;
      } else {
        numberOfActiveMeetings -= 1;
        endOfLastMeeting = event.time();
      }
    }
    return possibleSlots;
  }
}
