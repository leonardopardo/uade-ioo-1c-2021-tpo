package helpers;

import org.jdatepicker.impl.DateComponentFormatter;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.Properties;

public class Helpers {
    public static LocalDate datePickerFormatter(JDatePickerImpl toFormat) {
        return LocalDate.of(toFormat.getModel().getYear(), toFormat.getModel().getMonth() + 1, toFormat.getModel().getDay()
        );
    }

    public static JDatePickerImpl nuevoDatePicker() {
        UtilDateModel model = new UtilDateModel();
        JDatePanelImpl datePanel = new JDatePanelImpl(model, new Properties());
        JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateComponentFormatter());

        return datePicker;
    }

    public static void appendDatePicker(JPanel panel, JDatePickerImpl picker) {
        panel.setLayout(new GridLayout());
        panel.add(picker);
    }
}
