package com.lso.sandbox.simulator.exposed.http.shared;

public interface Message {
    String getKey();

    Object[] getArgs();

    String getDescription();

    class SimpleMessage implements Message {

        private final String key;

        private final Object[] args;

        private final String description;

        public SimpleMessage(String key, Object[] args, String description) {
            this.key = key;
            this.args = args;
            this.description = description;
        }

        @Override
        public String getKey() {
            return key;
        }

        @Override
        public Object[] getArgs() {
            return args;
        }

        @Override
        public String getDescription() {
            return description;
        }
    }

    public static Message of(String key, Object[] args, String description) {
        return new SimpleMessage(key, args, description);
    }

    public static Message of(String key, Object[] args) {
        return new SimpleMessage(key, args, "");
    }

    public static Message of(String key, String description) {
        return new SimpleMessage(key, new Object[] {}, description);
    }

    public static Message of(String key) {
        return new SimpleMessage(key, new Object[] {}, "");
    }
}
