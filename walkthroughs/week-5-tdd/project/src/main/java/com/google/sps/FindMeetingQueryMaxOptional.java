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

public final class FindMeetingQueryMaxOptional {
  //public void query(Collection<Event> events, MeetingRequest request) {
  public Collection<TimeRange> query(Collection<Event> events, MeetingRequest request) {
    // Create a list of existing meetings with mandatory and optional attendees.
    List<TimePoint> importantEvents = new ArrayList<>();

    int numberOfOptionalForRequest = request.getOptionalAttendees().size();
  
    // List of possible time slots for the request for maandatory and optional attendees only.
    Collection<TimeRange> possibleSlots = new ArrayList<>();

    // Add start and end of the day as time points.
    TimePoint startOfTheDay = new TimePoint(0, 1, numberOfOptionalForRequest);
    TimePoint endOfTheDay = new TimePoint(24 * 60, 2, numberOfOptionalForRequest);

    importantEvents.add(startOfTheDay);
    importantEvents.add(endOfTheDay);

    // Pick events with mandatory attendees.
    for (Event event : events) {
      // Get number of optional attendees for an event under consideration.
      int numberOfOptionalForEvent = 0;
      for (String attendee: request.getOptionalAttendees()) {
        if (event.getAttendees().contains(attendee)) {
          numberOfOptionalForEvent++;
        }
      }

      // Check if at least one of the attendees is a mandatory for the request.
      boolean mandatoryAttendee = false;
      for (String attendee: request.getAttendees()) {
        if (event.getAttendees().contains(attendee)) {
          TimePoint startOfEvent = new TimePoint(event.getWhen().start(), 1, -1);
          importantEvents.add(startOfEvent);
          TimePoint endOfEvent = new TimePoint(event.getWhen().end(), 2, -1);
          importantEvents.add(endOfEvent);
          mandatoryAttendee = true;
          break;
        }
      }

      if (!mandatoryAttendee && numberOfOptionalForEvent != 0) {
        TimePoint startOfEvent = new TimePoint(event.getWhen().start(), 3, numberOfOptionalForRequest - numberOfOptionalForEvent);
        importantEvents.add(startOfEvent);
        TimePoint endOfEvent = new TimePoint(event.getWhen().end(), 4,  numberOfOptionalForRequest - numberOfOptionalForEvent);
        importantEvents.add(endOfEvent);
      }
    }

    // Sort all time points.
    Collections.sort(importantEvents, TimePoint.ORDER_END_START);

    // Number of active meeting for the moment under consideration.
    int startOfTheRequest = 0;
    int endOfTheRequest = 1;

    // Number of optional attendees who can't attend meeting during that time slot.
    // That variable will change only for events with no mandatory attendees.
    int numberOfBusyOptional = 0;
    int maxOfOptional = -1;

    // numberOfActiveMeetings will be 0  - before the start and after the end of the day,
    // 1 - if current time slot is free for all mandatory attendees,
    // >1 - in any other case.
  
    // numberOfActiveMeetings will increase if new time point under consideration is a start for a meeting, and decrease in other case.
    for (startOfTheRequest = 0; startOfTheRequest < importantEvents.size(); startOfTheRequest++) {
      for (endOfTheRequest = importantEvents.size() - 1; endOfTheRequest > startOfTheRequest; endOfTheRequest--) {
        int start = importantEvents.get(startOfTheRequest).time();
        int end = importantEvents.get(endOfTheRequest).time();
        int duration = end - start;

        if (duration < request.getDuration()) {
          break;
        }
        numberOfBusyOptional = numberOfOptionalForRequest;
        for (int i = startOfTheRequest; i < endOfTheRequest; ++i) {
          int type = importantEvents.get(i).type();
          if (type == 2 || type == 4) {
            continue;
          }

          numberOfBusyOptional = Math.min(numberOfBusyOptional, importantEvents.get(i).numberOfOptional());
          if (numberOfBusyOptional == -1) {
            System.out.println("-1 " + String.valueOf(importantEvents.get(i).time()) + "  type  " + String.valueOf(importantEvents.get(i).type()));
            break;
          }
        }

        System.out.println("Start: " + String.valueOf(start) + "   End: " + String.valueOf(end) + "   Number: " + String.valueOf(numberOfBusyOptional));

        if (numberOfBusyOptional == -1) {
          continue;
        }

        if (numberOfBusyOptional > maxOfOptional) {
          maxOfOptional = numberOfBusyOptional;
          possibleSlots.clear();
          possibleSlots.add(TimeRange.fromStartDuration(start, duration));
        } else if (numberOfBusyOptional == maxOfOptional) {
          possibleSlots.add(TimeRange.fromStartDuration(start, duration));
        }
      }
    }
    System.out.println("----Answer----");

    for (TimeRange slot : possibleSlots) {
      System.out.println(slot.toString());
    }
    System.out.println("End of a log");
    System.out.println("");
    return possibleSlots;
  }
}
