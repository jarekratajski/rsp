package rsp.util.json;

import java.util.*;
import java.util.stream.Collectors;

/**
 * A representation of the JSON data types.
 */
public interface JsonDataType {

    /**
     * A boolean JSON data type.
     */
    final class Boolean implements JsonDataType {
        private final boolean value;

        public Boolean(boolean value) {
            this.value = value;
        }

        public boolean value() {
            return value;
        }

        @Override
        public java.lang.String toString() {
            return java.lang.Boolean.toString(value);
        }
    }

    /**
     * A numeric JSON data type.
     */
    final class Number implements JsonDataType {
        private final java.lang.Number value;

        public Number(java.lang.Number value) {
            this.value = value;
        }

        public java.lang.Number value() {
            return value;
        }

        @Override
        public java.lang.String toString() {
            return value.toString();
        }
    }

    /**
     * A string JSON data type.
     */
    final class String implements JsonDataType {
        private final java.lang.String value;

        public String(java.lang.String value) {
            this.value = value;
        }

        public java.lang.String value() {
            return value;
        }

        @Override
        public java.lang.String toString() {
            return value;
        }

        @Override
        public java.lang.String toStringValue() {
            return "\"" + toString() + "\"";
        }
    }

    /**
     * A JSON object, a collection of name/value pairs.
     */
    final class Object implements JsonDataType {
        public static final Object EMPTY = new Object(Collections.EMPTY_MAP);

        private final Map<java.lang.String, JsonDataType> values;

        public Object(Map<java.lang.String, JsonDataType> values) {
            this.values = values;
        }

        public Optional<JsonDataType> value(java.lang.String name) {
            return Optional.ofNullable(values.get(name));
        }

        public Set<java.lang.String> keys() {
            return values.keySet();
        }

        @Override
        public java.lang.String toString() {
            return "{"
                    + java.lang.String.join(",",
                                            values.entrySet().stream().map(e -> "\"" + e.getKey()
                                                                                     + "\": \""
                                                                                     + e.getValue().toStringValue())
                                                             .collect(Collectors.toList()))
                    + '}';
        }
    }

    /**
     * A JSON array, an ordered list of values.
     */
    final class Array implements JsonDataType {
        private final JsonDataType[] items;

        public Array(JsonDataType[] items) {
            this.items = items;
        }

        public JsonDataType[] items() {
            return items;
        }

        @Override
        public java.lang.String toString() {
            return "["
                    + java.lang.String.join(",",
                                            Arrays.stream(items).map(JsonDataType::toStringValue).collect(Collectors.toList()))
                    + ']';
        }
    }

    /**
     * The JSON null type.
     */
    final class Null implements JsonDataType {
        public static Null INSTANCE = new Null();

        @Override
        public java.lang.String toString() {
            return "null";
        }
    }

    default java.lang.String toStringValue() {
        return this.toString();
    }
}