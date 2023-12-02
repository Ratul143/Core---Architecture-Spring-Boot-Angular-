package jwt.backend.exception.CustomExceptionHandler;

public class DataExistsException extends RuntimeException {

    private final String uniqueKey;
    private final String year;
    private final Integer week;
    private final Integer unit;
    private final String message;
    private final Integer responseCode;

    public DataExistsException(String uniqueKey, String year, Integer week, Integer unit, String message, Integer responseCode) {
        this.uniqueKey = uniqueKey;
        this.year = year;
        this.week = week;
        this.unit = unit;
        this.message = message;
        this.responseCode = responseCode;
    }

    public String getUniqueKey() {
        return uniqueKey;
    }

    public String getYear() {
        return year;
    }

    public Integer getWeek() {
        return week;
    }

    public Integer getUnit() {
        return unit;
    }

    public String getMessage() {
        return message;
    }

    public Integer getResponseCode() {
        return responseCode;
    }
}

