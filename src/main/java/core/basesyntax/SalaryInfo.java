package core.basesyntax;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;

public class SalaryInfo {
    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("dd.MM.yyyy");

    private static final int DATE_INDEX = 0;
    private static final int NAME_INDEX = 1;
    private static final int HOURS_INDEX = 2;
    private static final int RATE_INDEX = 3;

    public String getSalaryInfo(String[] names, String[] data,
                                String dateFrom, String dateTo) {
        LocalDate startDate = LocalDate.parse(dateFrom, FORMATTER);
        LocalDate endDate = LocalDate.parse(dateTo, FORMATTER);
        Map<String, Integer> salaries = new LinkedHashMap<>();

        for (String name : names) {
            salaries.put(name, 0);
        }
        for (String record : data) {
            String[] parts = record.split(" ");
            LocalDate workDate = LocalDate.parse(parts[DATE_INDEX], FORMATTER);
            String employeeName = parts[NAME_INDEX];
            int hoursWorked = Integer.parseInt(parts[HOURS_INDEX]);
            int ratePerHour = Integer.parseInt(parts[RATE_INDEX]);
            if ((workDate.isEqual(startDate) || workDate.isAfter(startDate))
                    && (workDate.isEqual(endDate) || workDate.isBefore(endDate))) {
                salaries.compute(employeeName, (k, v) -> v + hoursWorked * ratePerHour);
            }
        }
        StringBuilder report = new StringBuilder();
        report.append("Report for period ")
                .append(dateFrom)
                .append(" - ")
                .append(dateTo)
                .append(System.lineSeparator());

        for (Map.Entry<String, Integer> entry : salaries.entrySet()) {
            report.append(entry.getKey())
                    .append(" - ")
                    .append(entry.getValue())
                    .append(System.lineSeparator());
        }
        return report.toString();
    }
}
