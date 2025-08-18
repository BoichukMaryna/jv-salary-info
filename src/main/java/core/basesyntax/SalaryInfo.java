//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package core.basesyntax;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class SalaryInfo {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    @SuppressWarnings("checkstyle:VariableDeclarationUsageDistance")
    public String getSalaryInfo(String[] names, String[] data, String dateFrom, String dateTo) {
        LocalDate startDate = LocalDate.parse(dateFrom, FORMATTER);
        LocalDate endDate = LocalDate.parse(dateTo, FORMATTER);

        // Використовуємо Map для накопичення зарплат
        Map<String, Integer> salaryMap = new HashMap<>();
        for (String name : names) {
            salaryMap.put(name, 0);
        }

        for (String record : data) {
            String[] parts = record.split(" ");
            if (parts.length != 4) {
                continue; // пропускаємо некоректні рядки
            }

            try {
                LocalDate workDate = LocalDate.parse(parts[0], FORMATTER);
                String employeeName = parts[1];
                int hoursWorked = Integer.parseInt(parts[2]);
                int ratePerHour = Integer.parseInt(parts[3]);

                // Перевірка діапазону дат
                if ((workDate.isEqual(startDate) || workDate.isAfter(startDate))
                        && (workDate.isEqual(endDate) || workDate.isBefore(endDate))) {

                    // Оновлюємо значення через computeIfPresent
                    salaryMap.computeIfPresent(employeeName,
                            (k, v) -> v + hoursWorked * ratePerHour);
                }
            } catch (Exception ignored) {
                // ігноруємо некоректні рядки без println
            }
        }

        // Формуємо звіт
        StringBuilder report = new StringBuilder();
        report.append("Report for period ")
                .append(dateFrom)
                .append(" - ")
                .append(dateTo)
                .append(System.lineSeparator());

        for (String name : names) {
            report.append(name)
                    .append(" - ")
                    .append(salaryMap.get(name))
                    .append(System.lineSeparator());
        }

        return report.toString();
    }
}
