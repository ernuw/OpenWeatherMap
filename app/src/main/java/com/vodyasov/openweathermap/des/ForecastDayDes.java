package com.vodyasov.openweathermap.des;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.vodyasov.openweathermap.data.ForecastDay;
import com.vodyasov.openweathermap.data.StateParameters;
import com.vodyasov.openweathermap.data.TemperatureParameters;
import com.vodyasov.openweathermap.data.WindParameters;


public class ForecastDayDes implements JsonDeserializer<ForecastDay>
{
    @Override
    public ForecastDay deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
    {
        JsonObject jsonObject = json.getAsJsonObject();
        ForecastDay day = new ForecastDay();
        day.setTempParams((TemperatureParameters) context.deserialize(
                jsonObject.get("temp"), TemperatureParameters.class));
        day.setHumidity(jsonObject.get("humidity").getAsInt());
        day.setPressure(jsonObject.get("pressure").getAsDouble());
        day.setDate(jsonObject.get("dt").getAsLong());
        day.setWindParams((WindParameters) context.deserialize(json, WindParameters.class));
        day.setStateParams((StateParameters) context.deserialize(
                jsonObject.get("weather").getAsJsonArray().get(0), StateParameters.class));
        return day;
    }
}
