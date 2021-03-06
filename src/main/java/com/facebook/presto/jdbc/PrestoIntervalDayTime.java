/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.facebook.presto.jdbc;

import com.google.common.base.Objects;

public class PrestoIntervalDayTime
{
    private static final long MILLIS_IN_SECOND = 1000;
    private static final long MILLIS_IN_MINUTE = 60 * MILLIS_IN_SECOND;
    private static final long MILLIS_IN_HOUR = 60 * MILLIS_IN_MINUTE;
    private static final long MILLIS_IN_DAY = 24 * MILLIS_IN_HOUR;

    private final long milliSeconds;

    public PrestoIntervalDayTime(long milliSeconds)
    {
        this.milliSeconds = milliSeconds;
    }

    public PrestoIntervalDayTime(int day, int hour, int minute, int second, int millis)
    {
        milliSeconds = toMillis(day, hour, minute, second, millis);
    }

    public long getMilliSeconds()
    {
        return milliSeconds;
    }

    @Override
    public int hashCode()
    {
        return Objects.hashCode(milliSeconds);
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        PrestoIntervalDayTime other = (PrestoIntervalDayTime) obj;
        return Objects.equal(this.milliSeconds, other.milliSeconds);
    }

    @Override
    public String toString()
    {
        return formatMillis(milliSeconds);
    }

    private static long toMillis(int day, int hour, int minute, int second, int millis)
    {
        return (day * MILLIS_IN_DAY) +
                (hour * MILLIS_IN_HOUR) +
                (minute * MILLIS_IN_MINUTE) +
                (second * MILLIS_IN_SECOND) +
                millis;
    }

    private static String formatMillis(long millis)
    {
        long day = millis / MILLIS_IN_DAY;
        millis %= MILLIS_IN_DAY;
        long hour = millis / MILLIS_IN_HOUR;
        millis %= MILLIS_IN_HOUR;
        long minute = millis / MILLIS_IN_MINUTE;
        millis %= MILLIS_IN_MINUTE;
        long second = millis / MILLIS_IN_SECOND;
        millis %= MILLIS_IN_SECOND;

        return String.format("%d %02d:%02d:%02d.%03d", day, hour, minute, second, millis);
    }
}
