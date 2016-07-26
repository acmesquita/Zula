package br.edu.ifpi.ads.tep.zula.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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

}
