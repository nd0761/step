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
    List<TimeObj> importantEvents = new ArrayList<>();
  
    Collection<TimeRange> possibleSlots = new ArrayList<>();

    TimeObj startOfTheDay = new TimeObj(0, true);
    TimeObj endOfTheDay = new TimeObj(24 * 60, false);

    importantEvents.add(startOfTheDay);
    importantEvents.add(endOfTheDay);

    for (Event event : events) {
      for (String attendee: request.getAttendees()) {
        if (event.getAttendees().contains(attendee)) {
          TimeObj startOfEvent = new TimeObj(event.getWhen().start(), true);
          importantEvents.add(startOfEvent);
          TimeObj endOfEvent = new TimeObj(event.getWhen().end(), false);
          importantEvents.add(endOfEvent);
          break;
        }
      }
    }

    Collections.sort(importantEvents, TimeObj.ORDER_END_START);
    int numberOfActiveMeetings = 0;
    int endOfLastMeeting = 0;
    for(TimeObj event : importantEvents) {
      System.out.println(event.toString());
      if (numberOfActiveMeetings == 1) {
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
    System.out.println("HOLA");
    return possibleSlots;
  }
}
