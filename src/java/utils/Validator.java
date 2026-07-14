package utils;

import java.time.LocalDate;
import java.util.regex.Pattern;

/**
 *
 * @author Nguyen Trang
 */
public class Validator {

    // Basic but solid email pattern: text@text.text
    private static final Pattern EMAIL_PATTERN
            = Pattern.compile("^[\\w.+-]+@[\\w-]+\\.[a-zA-Z]{2,}$");

    // Vietnamese phone number: starts with 0, followed by 9 digits (10 digits total)
    private static final Pattern PHONE_PATTERN
            = Pattern.compile("^0\\d{9}$");

    // Date format yyyy-MM-dd (matches HTML <input type="date">)
    private static final Pattern DATE_PATTERN
            = Pattern.compile("^\\d{4}-\\d{2}-\\d{2}$");

    private Validator() {
        // utility class, no instance
    }

    // ---------- REQUIRED ----------
    public static boolean isRequired(String value) {
        return value != null && !value.trim().isEmpty();
    }

    // ---------- EMAIL ----------
    public static boolean isValidEmail(String email) {
        return email != null
                && EMAIL_PATTERN.matcher(email.trim()).matches();
    }

    // ---------- PHONE ----------
    public static boolean isValidPhone(String phone) {
        return phone != null
                && PHONE_PATTERN.matcher(phone.trim()).matches();
    }

    // ---------- DATE ----------
    public static boolean isValidDate(String date) {

        if (date == null || !DATE_PATTERN.matcher(date.trim()).matches()) {
            return false;
        }

        try {
            LocalDate.parse(date.trim());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Date must not be in the future (useful for Date of Birth)
    public static boolean isNotFutureDate(String date) {

        try {
            LocalDate d = LocalDate.parse(date.trim());
            return !d.isAfter(LocalDate.now());
        } catch (Exception e) {
            return false;
        }
    }

    // Date must not be in the past (useful for Appointment date)
    public static boolean isTodayOrFutureDate(String dateTime) {

        try {
            // dateTime is expected as yyyy-MM-ddTHH:mm (HTML datetime-local)
            // or yyyy-MM-dd HH:mm:ss
            String datePart = dateTime.trim().substring(0, 10);
            LocalDate d = LocalDate.parse(datePart);
            return !d.isBefore(LocalDate.now());
        } catch (Exception e) {
            return false;
        }
    }

    // ---------- DATETIME (for <input type="datetime-local">, format yyyy-MM-ddTHH:mm) ----------
    public static boolean isValidDateTime(String dateTime) {

        try {
            java.time.LocalDateTime.parse(dateTime.trim());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // ---------- NUMBER ----------
    public static boolean isValidPositiveInt(String value) {

        try {
            return Integer.parseInt(value.trim()) > 0;
        } catch (Exception e) {
            return false;
        }
    }
}
