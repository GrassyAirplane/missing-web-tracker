package cabbage.missingwebtracker.backend.core.util;

import java.util.StringJoiner;

public record Age(int years, int months, int days) {

    public String weeksAndDays() {
        final StringJoiner joiner = new StringJoiner(", ");
        int weeks = months * 4 + (days / 7);
        int remainingDays = days % 7;
        if (months > 0) {
            joiner.add(weeks + " Weeks");
        }
        if (remainingDays > 0) {
            joiner.add(remainingDays + " Days");
        }
        final String result = joiner.toString();
        if (result.isEmpty()) {
            return "0 Weeks";
        }
        return result;
    }

    public String format() {
        final StringJoiner joiner = new StringJoiner(", ");
        if (years != 0) {
            joiner.add(years + " Years");
        }
        if (months != 0) {
            joiner.add(months + " Months");
        }
        if (days != 0) {
            joiner.add(days + " Days");
        }
        return joiner.toString();
    }

}
