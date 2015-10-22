package com.toshkin.popularmovies.network;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @author Lazar
 */
public class ServerDateDeserializer implements JsonDeserializer<Date> {

    private static final DateFormat[] formats = new DateFormat[]{
            new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US),
            new SimpleDateFormat("yyyy-MM-dd", Locale.US)};

    @Override
    public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        String dateString = json.getAsString();
        Date date = null;
        for (DateFormat df : formats){
            try{
                date = df.parse(dateString);
                break;
            }catch (ParseException e){
                // Do nothing and continue with next format.
            }
        }

        if (date == null) throw new JsonParseException("Could not parse Date.");

        return date;
    }
}
