package eu.brundo.bot.util;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

public class TimeUtils {

    public static ZonedDateTime convertToLocalDateTime(final Date dateToConvert) {
        return ZonedDateTime.ofInstant(
                dateToConvert.toInstant(), getZoneIdForGermany());
    }

    public static Date convertToDate(final ZonedDateTime zonedDateTime) {
        return Date.from(zonedDateTime.toInstant());
    }

    public static ZoneId getZoneIdForGermany() {
        return ZoneId.of("Europe/Berlin");
    }

    public static ZonedDateTime nowInGermany() {
        return ZonedDateTime.now(getZoneIdForGermany());
    }
}
