package fr.bet.tools.material_design;

import android.app.DatePickerDialog;
import android.content.Context;

import com.google.android.material.button.MaterialButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import fr.bet.components.material_design.DatePickerComponent;

public class ComponentBuilder {

    public static DatePickerDialog createDatePicker(final Context context, final DatePickerComponent pickerComponent, final MaterialButton button) {
        Calendar calendar = pickerComponent.getCalendar();
        DatePickerDialog datePicker = new DatePickerDialog(context, (view, year, month, day) -> {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, day);
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            dateFormat.setTimeZone(calendar.getTimeZone());
            pickerComponent.setDateAsString(dateFormat.format(calendar.getTime()));
            button.setText(pickerComponent.getDateAsString());
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        return datePicker;
    }
}
