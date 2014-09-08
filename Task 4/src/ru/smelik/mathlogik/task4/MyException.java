package ru.smelik.mathlogik.task4;

/**
 * Created by Smelik Nick.
 */
class MyException extends Exception {
    private final String detail;

    public MyException(String exception) {
        detail = exception;
    }

    @Override
    public String toString() {
        return detail;
    }
}
