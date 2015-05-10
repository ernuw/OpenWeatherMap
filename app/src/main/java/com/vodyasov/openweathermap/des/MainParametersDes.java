package com.vodyasov.openweathermap.des;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.vodyasov.openweathermap.data.MainParameters;


public class MainParametersDes implements JsonDeserializer<MainParameters>
{
    @Override
    public MainParameters deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
    {
        JsonObject jsonObject = json.getAsJsonObject();
        MainParameters params = new MainParameters();
        params.setTemperature(jsonObject.get("temp").getAsDouble());
        params.setHumidity(jsonObject.get("humidity").getAsInt());
        params.setPressure(jsonObject.get("pressure").getAsDouble());
        return params;
    }
}
