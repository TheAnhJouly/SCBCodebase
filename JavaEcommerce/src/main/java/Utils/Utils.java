package Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils { 
    public static boolean checkValidDate(String dateString, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);

        dateFormat.setLenient(false);

        try {
            Date date = dateFormat.parse(dateString);

            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    public static boolean checkValidMonth(int month) {
        return month > 0 && month <= 12;
    }

    public static boolean checkValidFileName(String fileName) {
        String pattern = "^[a-zA-Z0-9._-]+$";

        boolean isValidFileName = Pattern.matches(pattern, fileName);

        return isValidFileName;
    }

    public static boolean checkValidEmail(String email) {
        String regex = "\\w+@\\w+\\.com";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean checkValidPhoneNumber(String phoneNumber) {
        return phoneNumber.matches("\\d{10,11}");
    }

    public static boolean checkValidName(String name) {
        if (name.length() == 0) return false;
        for (int i = 0; i < name.length(); i++) {
            if (Character.isDigit(name.charAt(i)))
                return false;
        }
        return true;
    }

}
