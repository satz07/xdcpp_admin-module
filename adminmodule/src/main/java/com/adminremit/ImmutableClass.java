package com.adminremit;

import java.util.HashMap;
import java.util.Map;
final class MutableClass {
    private String field;
    private Map<String, String> fieldMap;
    public MutableClass(String field, Map<String, String> fieldMap) {
        this.field = field;
        this.fieldMap = fieldMap;
    }
    public String getField() {
        return field;
    }
    public Map<String, String> getFieldMap() {
        Map<String, String> deepCopy = new HashMap<String, String>();
        for(String key : fieldMap.keySet()) {
            deepCopy.put(key, fieldMap.get(key));
        }
        return deepCopy;
        //return fieldMap;
    }
}

public class ImmutableClass {
    public static void main(String[] args) {
        Map<String, String> map = new HashMap<>();
        map.put("key", "value");

        // Initializing our new "immutable" class
        MutableClass mutable = new MutableClass("this is not immutable", map);
        //we can easily add new elements to the map == changing the state
        mutable.getFieldMap().put("unwanted key", "another value");
        mutable.getFieldMap().keySet().forEach(e ->  System.out.println(e));
    }

}
