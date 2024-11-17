package thePowderToyJava;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Element {
    public abstract void update(FallingSandGame game, int x, int y);
    public abstract void draw(Graphics g, int x, int y, int width, int height);
    protected int weight = -1;
    public static final List<Class<? extends Element>> elementClasses = new ArrayList<>();
    private static final Map<String, Integer> elementIdMap = new HashMap<>();

    protected static void registerElement(Class<? extends Element> clazz) {
        if (!elementIdMap.containsKey(clazz.getSimpleName())) {
            elementIdMap.put(clazz.getSimpleName(), elementClasses.size());
            elementClasses.add(clazz);
        }
    }

    public Element() {
        registerElement(this.getClass());
        this.weight = -1;
    }

    public Element(int weight) {
        this.weight = weight;
    }

    public int getWeight() {
        return weight;
    }

    public int getId() {
        return elementIdMap.getOrDefault(getClass().getSimpleName(), -1) + 1;
    }

    public static Class<? extends Element> getElementById(int id) {
        if (id > 0 && id <= elementClasses.size()) {
            return elementClasses.get(id - 1);
        } else {
            return null;
        }
    }

    public static int getElementIdByName(String name) {
        return elementIdMap.getOrDefault(name, -1);
    }
}