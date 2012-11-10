package Utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class TextUtils {
	public static final int defaultDateLength = 11;
	public static final String defaultDateFormat = "dd-MM-yyyy";
	public static final String defaultTimeFormat = "HH:mm";
	public TextUtils() {}
	
	public static String zeroToNull(String s) {
		if ("".equals(s)) {
			s = null;
		}
		return s;
	}
	
	/**
	 * Gets the current date as a String in the default format
	 * 
	 * @return	The current date
	 */
	public String currentDate() {
		SimpleDateFormat df = new SimpleDateFormat(defaultDateFormat);
		return df.format(new java.util.Date()).toUpperCase();
	}

	/**
	 * Gets the current time as a String in the default format
	 * 
	 * @return	The current time
	 */
	public String currentTime() {
		SimpleDateFormat df = new SimpleDateFormat(defaultTimeFormat);
		return df.format(new java.util.Date()).toUpperCase();
	}

	/**
	 * Builds a single String from a String array
	 * 
	 * @param sa	The array to be converted to a single String
	 * @return	The array converted to a single String
	 */
	public String buildString(String[] sa) {
		StringBuffer s = new StringBuffer("");
		for (int i = 0; i < sa.length; i++) {
			if (sa[i] != null) {
				if (s.length() > 0) {
					s.append("\n");
				}
				s.append(sa[i]);
			}
		}
		if (s.length() == 0) {
			return null;
		}
		else {
			return s.toString();
		}
	}
	
	/**
     * Converts a Date to a String
     *
     * @param d        Date to be converted
     * @return        The Date as a String
     */
    public static String dateToString(java.util.Date d) {
        return dateToString(d, defaultDateFormat);
    }

    /**
     * Converts a Date to a String
     *
     * @param d        Date to be converted
     * @return        The Date as a String
     */
    public static String dateToString(java.util.Date d, String dateFormat) {
        if (d == null) {
            return null;
        }
        else {
            SimpleDateFormat df = new SimpleDateFormat(dateFormat);
            df.setLenient(false);
            return df.format(d).toUpperCase();
        }
    }

    /**
     * Converts a String to a Date
     *
     * @param s        String to be converted
     * @return        The String as a Date
     */
    public static java.util.Date stringToDate(String s) {
        return stringToDate(s, defaultDateFormat);
    }

    /**
     * Converts a String to a Date
     *
     * @param s        String to be converted
     * @return        The String as a Date
     */
    public static java.util.Date stringToDate(String s, String dateFormat) {
        if (s == null) {
            return null;
        }
        else {
            SimpleDateFormat df = new SimpleDateFormat(dateFormat);
            df.setLenient(false);
            try {
                return df.parse(s);               
            }
            catch (java.text.ParseException e) {
                return null;
            }
        }
    }

    /**
     * Converts a String to an SQL date for use in queries
     *
     * @param s        String to be converted
     * @return        The String as an SQL Date
     */
    public static java.sql.Date stringToSqlDate(String s) {
        return stringToSqlDate(s, defaultDateFormat);
    }

    /**
     * Converts a String to an SQL date for use in queries
     *
     * @param s        String to be converted
     * @return        The String as an SQL Date
     */
    public static java.sql.Date stringToSqlDate(String s, String dateFormat) {
        java.util.Date d = stringToDate(s, dateFormat);
        if (d == null) {
            return null;
        }
        else {
            return new java.sql.Date(d.getTime());
        }
    }
}