package br.edu.ifpi.ads.tep.zula.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by catharina on 25/07/16.
 */
public class UtilsData {

    public static Date parseForDate(String date) throws ParseException {
        Date data = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        data = sdf.parse(date);
        return data;
    }

    public static String getData(Date data){
        DateFormat format = DateFormat.getDateInstance(DateFormat.SHORT);
        return format.format(data);

    }

    public static String getData(int year, int month, int day) {
        Calendar dataEspecial = Calendar.getInstance();
        dataEspecial.set(year, month, day);
        Date data = dataEspecial.getTime();

        DateFormat format = DateFormat.getDateInstance(DateFormat.SHORT);
        String dataFormatada = format.format(data);
        return dataFormatada;
    }

    public static Date getDate(int year, int month, int day){
        Calendar dataEspecial = Calendar.getInstance();
        dataEspecial.set(year, month, day);
        return dataEspecial.getTime();
    }
}
