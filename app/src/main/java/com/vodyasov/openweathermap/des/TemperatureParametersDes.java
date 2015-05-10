package com.vodyasov.openweathermap.des;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.vodyasov.openweathermap.data.TemperatureParameters;

public class TemperatureParametersDes implements JsonDeserializer<TemperatureParameters>
{
    @Override
    public TemperatureParameters deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
    {
        JsonObject jsonObject = json.getAsJsonObject();
        TemperatureParameters params = new TemperatureParameters();
        params.setNight(jsonObject.get("night").getAsDouble());
        params.setMorning(jsonObject.get("morn").getAsDouble());
        params.setDay(jsonObject.get("day").getAsDouble());
        params.setEvening(jsonObject.get("eve").getAsDouble());
        return params;
    }
}
