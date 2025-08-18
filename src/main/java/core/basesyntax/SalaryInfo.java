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

    public String getSalaryInfo(String[] employeeNames, String[] workRecords, String startDateStr, String endDateStr) {
        LocalDate startDate = LocalDate.parse(startDateStr, FORMATTER);
        LocalDate endDate = LocalDate.parse(endDateStr, FORMATTER);

        // Використовуємо Map замість масиву
        Map<String, Integer> salaryMap = new HashMap<>();
        for (String name : employeeNames) {
            salaryMap.put(name, 0);
        }

        for (String record : workRecords) {
            String[] parts = record.split(" ");
            if (parts.length != 4) {
                System.out.println("Некоректний запис: " + record);
                continue; // пропускаємо невірні рядки
            }

            try {
                LocalDate workDate = LocalDate.parse(parts[0], FORMATTER);
                String employeeName = parts[1];
                int hoursWorked = Integer.parseInt(parts[2]);
                int ratePerHour = Integer.parseInt(parts[3]);

                // Перевірка діапазону дат
                if ((workDate.isEqual(startDate) || workDate.isAfter(startDate))
                        && (workDate.isEqual(endDate) || workDate.isBefore(endDate))) {

                    if (salaryMap.containsKey(employeeName)) {
                        int currentSalary = salaryMap.get(employeeName);
                        salaryMap.put(employeeName, currentSalary + hoursWorked * ratePerHour);
                    }
                }
            } catch (Exception e) {
                System.out.println("Помилка обробки запису: " + record);
            }
        }

        // Формування звіту
        StringBuilder report = new StringBuilder();
        report.append("Report for period ").append(startDateStr).append(" - ").append(endDateStr)
                .append(System.lineSeparator());

        for (String name : employeeNames) {
            report.append(name).append(" - ").append(salaryMap.get(name)).append(System.lineSeparator());
        }

        return report.toString();
    }
}
