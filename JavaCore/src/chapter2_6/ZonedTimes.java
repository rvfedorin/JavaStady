package chapter2_6;

import java.time.*;

public class ZonedTimes {
    public static void main(String[] args) {

        for (String zone : ZoneId.getAvailableZoneIds()) {
            if (zone.contains("Kal")) {
                System.out.println(zone);
            }
        }


        ZonedDateTime apolloTime = ZonedDateTime.of(
                LocalDateTime.of(1969, 7, 16, 9, 32, 0),
                ZoneId.of("Europe/Kaliningrad"));
        System.out.println("Apollo: " + apolloTime);

        Instant instant = apolloTime.toInstant();
        System.out.println("Instant: " + instant);

        ZonedDateTime zonedDateTime = instant.atZone(ZoneId.of("UTC"));
        System.out.println("To Zone: " + zonedDateTime);

        ZonedDateTime meeting = ZonedDateTime.of(
                LocalDateTime.of(2013, 10, 31, 14, 30),
                ZoneId.of("America/Los_Angeles"));
        System.out.println("Meeting: " + meeting);

        ZonedDateTime nextMeeting = meeting.plus(Duration.ofDays(7));
        System.out.println("Next meeting: " + nextMeeting);

        nextMeeting = meeting.plus(Period.ofDays(7));
        System.out.println("Next meeting: " + nextMeeting);


    }
}
