package com.harishlangs.hclan;

import java.util.HashMap;
import java.util.Map;

public class HclanInstance {
    private HclanClass klass;
    private final Map<String, Object> fields = new HashMap<>();

    protected HclanInstance(HclanClass klass) {
        this.klass = klass;
    }

    protected Object get(Token name) {
        if (fields.containsKey(name.lexeme)) {
          return fields.get(name.lexeme);
        }

        HclanFunction method = klass.findMethod(name.lexeme);
        if (method != null) return method.bind(this);
    
        throw new RuntimeError(name, "Undefined property '" + name.lexeme + "'.");
    }

    protected void set(Token name, Object value) {
        fields.put(name.lexeme, value);
    }

    @Override
    public String toString() {
        return String.format("<Class'%s' instance>", klass.name);
    }
}
