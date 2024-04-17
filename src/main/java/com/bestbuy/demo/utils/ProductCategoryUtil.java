package com.bestbuy.demo.utils;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductCategoryUtil {
    public static final Map<String, List<String>> FILTER_TO_TYPE_MAP;

    static {
        Map<String, List<String>> map = new HashMap<>();
        map.put("fruits & vegetables", Arrays.asList("Fruits", "Vegetables"));
        map.put("bakery", Arrays.asList("Donuts", "Croissant"));
        map.put("poultry, meat & seafood", Arrays.asList("Poultry", "Meat", "Seafood"));
        map.put("dairy, eggs & fridge", Arrays.asList("Dairy", "Eggs", "Fridge"));
        map.put("freezer", Collections.singletonList("Freezer"));
        map.put("drinks", Arrays.asList("Coffee", "Energy", "Soft", "Water"));
        map.put("beer, wine & spirits", Arrays.asList("Beer", "Wine", "Spirits"));
        map.put("cleaning", Arrays.asList("Laundry", "Kitchen", "Cleaning Goods"));
        map.put("pharmacy", Collections.singletonList("Pharmacy"));
        map.put("pet", Arrays.asList("Cat", "Dog", "Small Pet"));
        map.put("home & lifestyle", Arrays.asList("Dining", "Kitchenware", "Home Appliance"));
        FILTER_TO_TYPE_MAP = Collections.unmodifiableMap(map);
    }
}
