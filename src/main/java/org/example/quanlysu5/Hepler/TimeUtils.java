package org.example.quanlysu5.Hepler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class TimeUtils {
    public static String toRelative(LocalDateTime time) {

        if (time == null) return "";

        LocalDateTime now = LocalDateTime.now();

        Duration duration = Duration.between(time, now);

        long seconds = duration.getSeconds();

        if (seconds < 10)
            return "Vừa xong";

        if (seconds < 60)
            return seconds + " giây trước";

        long minutes = seconds / 60;

        if (minutes < 60)
            return minutes + " phút trước";

        long hours = minutes / 60;

        if (hours < 24) {

            long remainMinute = minutes % 60;

            if (remainMinute == 0)
                return hours + " giờ trước";

            return hours + " giờ " + remainMinute + " phút trước";
        }

        long days = hours / 24;

        if (days == 1)
            return "Hôm qua lúc "
                    + String.format("%02d:%02d",
                    time.getHour(),
                    time.getMinute());

        if (days < 7)
            return days + " ngày trước";

        long weeks = days / 7;

        if (weeks < 5)
            return weeks + " tuần trước";

        long months = days / 30;

        if (months < 12) {

            long remainDay = days % 30;

            if (remainDay == 0)
                return months + " tháng trước";

            return months + " tháng " + remainDay + " ngày trước";
        }

        long years = months / 12;
        long remainMonth = months % 12;

        if (remainMonth == 0)
            return years + " năm trước";

        return years + " năm " + remainMonth + " tháng trước";
    }}
