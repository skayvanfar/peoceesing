package ir.sk.processing.mapper;

import ir.sk.processing.model.Cookie;

import java.util.*;

/**
 * Created by sad.kayvanfar on 11/17/2021.
 */
public class MostFrequentListMapper implements ListMapper<Cookie> {
    @Override
    public List<Cookie> apply(List<Cookie> list) {
        Map<String, Cookie> nameToCookieMap = convertListToMap(list);

        Map<String, Integer> frequencyCookieMap = cookieListToMapByFrequency(list);
        Map<String, Integer> sortedCookieCountMap = new LinkedHashMap<>();

        frequencyCookieMap.entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .forEach(entry -> sortedCookieCountMap.put(entry.getKey(), entry.getValue()));

        return getByMaxFrequency(nameToCookieMap, sortedCookieCountMap);
    }

    /**
     * return the most visited cookies
     */
    private List<Cookie> getByMaxFrequency(Map<String, Cookie> nameToCookieMap, Map<String, Integer> sortedElementCountMap) {
        List<Cookie> maxItems = new ArrayList<>();
        int maxCount = sortedElementCountMap.entrySet().stream().findFirst().get().getValue();
        for (Map.Entry<String, Integer> item : sortedElementCountMap.entrySet()) {
            if (item.getValue() == maxCount)
                maxItems.add(nameToCookieMap.get(item.getKey()));
            else
                break;
        }
        return maxItems;
    }

    /**
     * iterate the list and add each occurrence of item in the hash map
     */
    private Map<String, Integer> cookieListToMapByFrequency(List<Cookie> cookies) {
        Map<String, Integer> itemMap = new LinkedHashMap<>();
        cookies.forEach(cookie -> itemMap.put(cookie.getName(), itemMap.getOrDefault(cookie.getName(), 0) + 1));
        return itemMap;
    }

    private Map<String, Cookie> convertListToMap(List<Cookie> cookies) {
        Map<String, Cookie> nameToCookieMap = new HashMap<>();
        for (Cookie animal : cookies) {
            if(!nameToCookieMap.containsKey(animal.getName()))
                nameToCookieMap.put(animal.getName(), animal);
        }
        return nameToCookieMap;
    }
}
