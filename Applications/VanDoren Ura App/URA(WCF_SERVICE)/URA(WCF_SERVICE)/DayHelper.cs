using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Globalization;

namespace URA_WCF_SERVICE_
{
    public static class DateTimeFunctions
    {
        public const DayOfWeek startOfWeek = DayOfWeek.Monday;

        public static int GetIso8601WeekOfYear(DateTime date)
        {
            var day = (int)CultureInfo.CurrentCulture.Calendar.GetDayOfWeek(date);
            return CultureInfo.CurrentCulture.Calendar.GetWeekOfYear(date.AddDays(4 - (day == 0 ? 7 : day)), CalendarWeekRule.FirstFourDayWeek, DayOfWeek.Monday);
        }

        public static DateTime FirstDateOfWeek(int year, int weekOfYear)
        {
           
            DateTime jan1 = new DateTime(year, 1, 1);

            int daysOffset = (int)CultureInfo.CurrentCulture.DateTimeFormat.FirstDayOfWeek - (int)jan1.DayOfWeek;

            DateTime firstMonday = jan1.AddDays(daysOffset);

            int firstWeek = CultureInfo.CurrentCulture.Calendar.GetWeekOfYear(jan1, CultureInfo.CurrentCulture.DateTimeFormat.CalendarWeekRule, CultureInfo.CurrentCulture.DateTimeFormat.FirstDayOfWeek);

            if (firstWeek <= 1)
            {
                weekOfYear -= 1;
            }

            return firstMonday.AddDays(weekOfYear * 7);
        }

        public static DateTime FirstDateOfWeek(DateTime dt)
        {
            int week = GetIso8601WeekOfYear(dt);

            int year = dt.Year;

            if (dt.Month == 12 && week < 4)
            {
                year += 1;
            }

            return FirstDateOfWeek(year, week);
        }

        public static string DateTimeToShortDay(DateTime dateTime)
        {
            CultureInfo cultureInfo = CultureInfo.CurrentCulture;

            string day = (cultureInfo.DateTimeFormat.DayNames[(int)dateTime.DayOfWeek]).Substring(0, 2);

            return day;
        }

        public static DateTime WeekNumberToDateTime(int week, int day)
        {
            if (day > 0)
            {
                day = day - 1;
            }

            DateTime newWeekDate = FirstDateOfWeek(DateTime.Now.Year, week);

            if (newWeekDate < DateTime.Now.AddDays(-20))
            {
                newWeekDate = FirstDateOfWeek(DateTime.Now.Year + 1, week);
            }

            newWeekDate = newWeekDate.AddDays(day);

            return newWeekDate;
        }

    }
}