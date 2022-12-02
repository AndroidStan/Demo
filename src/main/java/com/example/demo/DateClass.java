package com.example.demo;

import jdk.jfr.ContentType;
import java.text.DateFormat;

import java.util.Date;
import java.util.TimeZone;

import java.time.ZonedDateTime;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

public class DateClass {
    @Getter
    private String formattedDate;

    public DateClass() {
        this.getCurrentDateAtISO8601FormatAtZuluTimezone();
    }

    private void getCurrentDateAtISO8601FormatAtZuluTimezone()
    {
        /*TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
        df.setTimeZone(tz);
        this.formattedDate = df.format(new Date());*/

        DateTimeFormatter formatter = DateTimeFormatter.ISO_INSTANT;
        this.formattedDate = formatter.format(ZonedDateTime.now());
    }
}
