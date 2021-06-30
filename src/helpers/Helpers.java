package helpers;

import org.jdatepicker.impl.JDatePickerImpl;

import java.time.LocalDate;

public class Helpers {
    public static LocalDate datePickerFormatter(JDatePickerImpl toFormat) {
        return LocalDate.of(
                toFormat.getModel().getYear(),
                toFormat.getModel().getMonth() + 1,
                toFormat.getModel().getDay()
        );
    }
}
