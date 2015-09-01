package com.loneboat.spacesloth.main.content.partsystem;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.loneboat.spacesloth.main.SpaceSloth;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * com.loneboat.spacesloth.main.content.systems
 * Created by Dubpub on 8/30/2015.
 *
 *  This class is solely responsible for handling the player's parts of their ship.
 *
 */
public class PartFactory {

    private static SpaceSloth game;
    public static Json SeriesA;
    public static HashMap<String, Part> parts;

    public static class Property {
        private String key;
        private String value;

        private Property() {}

        public void setKey(String key) {
            this.key = key;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public Object getValue() {
            return value;
        }

        public float asFloat() {
            return Float.parseFloat(value);
        }
    }

    private PartFactory() {}

    public static PartFactory function(SpaceSloth game) {
        PartFactory.game = game;
        SeriesA = new Json();
        parts = new HashMap<String, Part>();
        ArrayList<?> list = SeriesA.fromJson(ArrayList.class, Gdx.files.internal("Parts/Series.json"));
        for(Object o : list) {
            if(o instanceof JsonValue) {
                JsonValue v = (JsonValue) o;
                Part temp = new Part();
                temp.setRank(v.getString(0));
                temp.setHealth(v.getFloat(1));
                temp.setRepairFactor(v.getFloat(2));
                temp.setCost(v.getDouble(3));
                temp.setVisible(v.getBoolean(4));
                temp.setLocalName(v.getString(5));
                temp.setFileName(v.getString(6));
                temp.setRGB(v.getString(7));
                temp.setAlpha(v.getFloat(8));
                temp.setShortHand(v.getString(9));
                temp.setPartType(PartType.valueOf(v.getString(10)));
                if(v.get(11) != null && v.get(11).isArray()) {
                    JsonValue propertyArray = v.get(11);
                    int size = propertyArray.size;
                    // This part has an array of properties, iterate it.
                    for(int i = 0; i < size; i++) {
                        Property property = SeriesA.fromJson(Property.class, v.get(11).get(i).toString());
                        temp.addProperty(property);
                    }
                }
                parts.put(temp.getShortHand(), temp);
            }
        }
        game.getLogger().info("Loaded " + parts.size() + " parts!");
        return new PartFactory();
    }

    public static Part fetchPart(String key) {
        if(parts.containsKey(key))
            return parts.get(key);
        return null;
    }

}
