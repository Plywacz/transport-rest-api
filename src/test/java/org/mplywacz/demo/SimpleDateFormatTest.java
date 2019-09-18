package org.mplywacz.demo;
/*
Author: BeGieU
Date: 18.09.2019
*/

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SimpleDateFormatTest {
    @Test
    public void basicTest() {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-mm-mm-dd");
        System.out.println(formatter.format(date));
    }

    @Test
    public void advancedFormatTest() {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("MMMM, d");

        var dateStr = formatter.format(date);
        var strs = (dateStr.split(" "));

        switch (strs[1]) {
            case "1":
                strs[1] += "st";
                break;
            case "2":
                strs[1] += "nd";
                break;
            case "3":
                strs[1] += "rd";
            default:
                strs[1] += "th";
                break;
        }

        dateStr = String.join(" ", strs);
        System.out.println(dateStr);
    }

    @Test
    public void markDaysThatPassedInCurrentMonth() throws ParseException {
        var currDate = new Date();
        var formatter = new SimpleDateFormat("yyyy-MM-dd");

        var endEdgeDate = formatter.format(currDate);

        //generate date that represents first day of current month
        var beginEdgeDate = endEdgeDate.substring(0, endEdgeDate.length() - 3) + "-01";

        java.sql.Date sDate = java.sql.Date.valueOf(beginEdgeDate);
        java.sql.Date eDate = java.sql.Date.valueOf(endEdgeDate);

        System.out.println(sDate + "    " + eDate);

        //generate dates to select period of current month that has passed
        System.out.println("SELECT * FROM transit t WHERE t.date >= " + beginEdgeDate + " AND t.date<= " + endEdgeDate);
    }
}
