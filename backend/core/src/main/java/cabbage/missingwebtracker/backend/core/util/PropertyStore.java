package cabbage.missingwebtracker.backend.core.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;

public final class PropertyStore {

    private final Map<String, String> data = new HashMap<>();

    public void importData(PropertyStore other) {
        this.data.putAll(other.data);
    }

    public int size() {
        return this.data.size();
    }

    public boolean hasProperty(String key) {
        return this.data.containsKey(key);
    }

    public void setProperty(String key, String property) {
        this.data.put(key, property);
    }

    public void setIntProperty(String key, int property) {
        this.data.put(key, String.valueOf(property));
    }

    public void setDoubleProperty(String key, double property) {
        this.data.put(key, String.valueOf(property));
    }

    public void setBooleanProperty(String key, boolean property) {
        this.data.put(key, String.valueOf(property));
    }

    public void removeProperty(String key) {
        this.data.remove(key);
    }

    public void clearProperties() {
        this.data.clear();
    }

    public Map<String, String> propertiesRef() {
        return Collections.unmodifiableMap(this.data);
    }

    public Optional<String> getProperty(String name) {
        return Optional.ofNullable(this.data.get(name));
    }

    public OptionalInt getIntProperty(String name) {
        final String raw = this.data.get(name);
        if (raw == null) {
            return OptionalInt.empty();
        }
        if (!Utils.isInt(raw)) {
            return OptionalInt.empty();
        }
        return OptionalInt.of(Integer.parseInt(raw));
    }

    public OptionalDouble getDoubleProperty(String name) {
        final String raw = this.data.get(name);
        if (raw == null) {
            return OptionalDouble.empty();
        }
        try {
            return OptionalDouble.of(Double.parseDouble(name));
        } catch (NumberFormatException ex) {
            return OptionalDouble.empty();
        }
    }

    public Optional<Boolean> getBooleanProperty(String name) {
        final String raw = this.data.get(name);
        return Optional.ofNullable(raw).map(Boolean::parseBoolean);
    }

}
