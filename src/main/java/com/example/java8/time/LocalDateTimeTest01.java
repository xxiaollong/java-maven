package com.example.java8.time;

import org.junit.Test;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Set;

/**
 * LocalDate       只包含日期，比如：2018-09-24
 * LocalTime       只包含时间，比如：10:32:10
 * LocalDateTime   包含日期和时间，比如：2018-09-24 10:32:10
 *
 * Instant          时间戳
 *
 * Duration         计算两个“时间”之间的间隔
 * Period           计算两个“日期”之间的间隔
 *
 * TemporalAdjuster  时间矫正器
 * TemporalAdjusters 时间矫正器常用实现
 *
 * DateTimeFormatter  格式化时间
 *
 * ZonedDateTime      时区
 *
 */
public class LocalDateTimeTest01 {

    @Test
    public void test6(){
        Set<String> zoneIds = ZoneId.getAvailableZoneIds();
//        zoneIds.forEach(System.out::println);

        LocalDateTime prc = LocalDateTime.now(ZoneId.of("PRC"));
        System.out.println(prc);

    }

    // DateTimeFormatter
    @Test
    public void test5(){
        DateTimeFormatter fm1 = DateTimeFormatter.ISO_DATE;
        DateTimeFormatter fm2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH");

        LocalDateTime now = LocalDateTime.now();

        System.out.println(fm1.format(now));

        String format = fm2.format(now);
        System.out.println(format);

        LocalDateTime parse = LocalDateTime.parse(format, fm2);
        System.out.println(parse);
    }

    // TemporalAdjuster：时间矫正器
    @Test
    public void test4(){
        LocalDateTime now = LocalDateTime.now();
        System.out.println(now);

        LocalDateTime dateTime = now.with(TemporalAdjusters.next(DayOfWeek.SUNDAY));
        System.out.println(dateTime);

        // 自定义: 获取下一个工作日
        LocalDateTime dateTime1 = now.with((l) -> {
            LocalDateTime ldt = (LocalDateTime) l;
            DayOfWeek dayOfWeek = ldt.getDayOfWeek();
            if (dayOfWeek.equals(DayOfWeek.FRIDAY)) {
                return ldt.plusDays(3);
            } else if (dayOfWeek.equals(DayOfWeek.SATURDAY)) {
                return ldt.plusDays(2);
            } else {
                return ldt.plusDays(1);
            }
        });
        System.out.println(dateTime1);

    }

    // Duration、Period： 时差
    @Test
    public void test3() throws InterruptedException {
        Instant now = Instant.now();
        Thread.sleep(1000);
        Instant now1 = Instant.now();
        System.out.println(Duration.between(now, now1).toMillis());

        System.out.println("======================");

        LocalTime now2 = LocalTime.now();
        Thread.sleep(1000);
        LocalTime now3 = LocalTime.now();
        System.out.println(Duration.between(now2, now3).toMillis());

        System.out.println("=====================");

        LocalDate now4 = LocalDate.now();
        Thread.sleep(1000);
        LocalDate now5 = LocalDate.of(2015,10,1);
        System.out.println(Period.between(now4, now5).getYears());

    }

    // Instant
    @Test
    public void test2(){
        // 默认获取UTC时区
        Instant now = Instant.now();
        System.out.println(now);

        // 时间偏移
        OffsetDateTime offset = now.atOffset(ZoneOffset.ofHours(8));
        System.out.println(offset);

        // 获取时间戳
        System.out.println(now.toEpochMilli());

        // 设置时间
        Instant time1 = Instant.ofEpochSecond(60);
        System.out.println(time1);


    }


    // LocalDateTime、LocalDate、LocalTime
    @Test
    public void test1(){

        // 构造1
        LocalDateTime now = LocalDateTime.now();
        System.out.println(now);

        // 构造2
        LocalDateTime time = LocalDateTime.of(2020, 3, 18, 15, 59);
        System.out.println(time);

        // 构造3
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime parse = LocalDateTime.parse("2020-03-18 11:00:50", formatter);
        System.out.println(parse);

        // 加两年
        LocalDateTime time1 = parse.plusYears(2);
        System.out.println(time1);

        // 减两年
        LocalDateTime time2 = parse.plusYears(-2);
        System.out.println(time2);


    }


}
