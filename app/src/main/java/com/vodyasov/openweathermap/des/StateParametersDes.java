package com.vodyasov.openweathermap.des;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.vodyasov.openweathermap.data.StateParameters;

public class StateParametersDes implements JsonDeserializer<StateParameters>
{
    @Override
    public StateParameters deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
    {
        JsonObject jsonObject = json.getAsJsonObject();
        StateParameters params = new StateParameters();
        params.setIcon(jsonObject.get("icon").getAsString());
        params.setDescription(jsonObject.get("description").getAsString());
        return params;
    }
}
