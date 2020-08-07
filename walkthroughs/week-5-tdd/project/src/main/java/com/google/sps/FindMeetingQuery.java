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

public final class FindMeetingQuery {
  /** Return free slots based on list of time points. */
  public Collection<TimeRange> getFreeSlots(List<TimePoint> importantEvents, MeetingRequest request) {
    Collection<TimeRange> possibleSlots = new ArrayList<>();
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
      
      if (event.type() == 1) {
        numberOfActiveMeetings += 1;
      } else {
        numberOfActiveMeetings -= 1;
        endOfLastMeeting = event.time();
      }
    }
    return possibleSlots;
  }

  public Collection<TimeRange> query(Collection<Event> events, MeetingRequest request) {
    // Create a list of existing meetings with mandatory attendees.
    List<TimePoint> importantEvents = new ArrayList<>();
    // Create a list of existing meetings with optional attendees considered as mandatory.
    List<TimePoint> optionalEvents = new ArrayList<>();
  
    // List of possible time slots for mandatory attendees.
    Collection<TimeRange> possibleMandatorySlots = new ArrayList<>();
    // List of possible time slots for optional attendees.
    Collection<TimeRange> possibleOptionalSlots = new ArrayList<>();

    // Add start and end of the day as time points.
    TimePoint startOfTheDay = new TimePoint(0, 1, -1);
    TimePoint endOfTheDay = new TimePoint(24 * 60, 2, -1);

    importantEvents.add(startOfTheDay);
    importantEvents.add(endOfTheDay);

    optionalEvents.add(startOfTheDay);
    optionalEvents.add(endOfTheDay);

    // Pick events with mandatory attendees.
    for (Event event : events) {
      boolean mandatoryAttendee = false;
      for (String attendee: request.getAttendees()) {
        if (event.getAttendees().contains(attendee)) {
          TimePoint startOfEvent = new TimePoint(event.getWhen().start(), 1, -1);
          importantEvents.add(startOfEvent);
          optionalEvents.add(startOfEvent);
          TimePoint endOfEvent = new TimePoint(event.getWhen().end(), 2, -1);
          importantEvents.add(endOfEvent);
          optionalEvents.add(endOfEvent);
          mandatoryAttendee = true;
          break;
        }
      }
      if (mandatoryAttendee) {
        continue;
      }
      for (String attendee: request.getOptionalAttendees()) {
        if (event.getAttendees().contains(attendee)) {
          TimePoint startOfEvent = new TimePoint(event.getWhen().start(), 1, -1);
          optionalEvents.add(startOfEvent);
          TimePoint endOfEvent = new TimePoint(event.getWhen().end(), 2, -1);
          optionalEvents.add(endOfEvent);
          break;
        }
      }
    }

    // Get possible slots for manadatory and optional attendees.
    possibleMandatorySlots = getFreeSlots(importantEvents, request);
    possibleOptionalSlots = getFreeSlots(optionalEvents, request);

    if (possibleOptionalSlots.size() != 0) {
      return possibleOptionalSlots;
    }
    return possibleMandatorySlots;
  }
}