package com.epam.training.ticketservice.dateconverter;

import java.text.ParseException;
import java.util.Date;

public interface DateConverterService {

    Date convertStringToDate(String input) throws ParseException;

    String convertDateToString(Date date);
}
