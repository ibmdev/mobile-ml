package fr.bet.components.material_design;

import android.app.DatePickerDialog;

import java.util.Calendar;

public class DatePickerComponent {

    private DatePickerDialog datePicker;
    private Calendar calendar;
    private String dateAsString;

    public DatePickerComponent() {
        this.calendar = Calendar.getInstance();
    }

    public DatePickerDialog getDatePicker() {
        return datePicker;
    }

    public void setDatePicker(DatePickerDialog datePicker) {
        this.datePicker = datePicker;
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }

    public String getDateAsString() {
        return dateAsString;
    }

    public void setDateAsString(String dateAsString) {
        this.dateAsString = dateAsString;
    }
}
