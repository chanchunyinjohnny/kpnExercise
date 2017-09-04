package kpn.util;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

public class DateDeserializer implements JsonDeserializer<Date> {

  @Override
  public Date deserialize(JsonElement element, Type arg1, JsonDeserializationContext arg2) throws JsonParseException {
      String date = element.getAsString();

      SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
      format.setTimeZone(TimeZone.getTimeZone("GMT"));

      try {
    	  if (date == null || date.equals("")){
          return null;
    	  }
    	  return format.parse(date);
      } catch (ParseException exp) {
          exp.printStackTrace();
          return null;
      }
   }
}