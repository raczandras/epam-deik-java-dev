package com.epam.training.ticketservice.dateconverter.impl;

import com.epam.training.ticketservice.dateconverter.DateConverterService;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class DateConverterServiceImpl implements DateConverterService {
    @Override
    public Date convertStringToDate(String input) throws ParseException {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(input);
    }

    @Override
    public String convertDateToString(Date date) {
        DateFormat dateDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return dateDateFormat.format(date);
    }
}