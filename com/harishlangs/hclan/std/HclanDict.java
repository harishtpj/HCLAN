package com.harishlangs.hclan.std;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.harishlangs.hclan.*;

public class HclanDict extends HclanInstance {
    private final Map<Object, Object> dict;

    HclanDict() {
        super(null);
        this.dict = new HashMap<>();
    }

    @Override
    protected Object get(Token name) {
        switch (name.lexeme) {
            case "put":
                return funPut();
            case "get":
                return funGet();
            case "clear":
                return funClear();
            case "containsKey":
                return funContainsKey();
            case "containsValue":
                return funContainsValue();
            case "keys":
                return funKeys();
            case "values":
                return funValues();
            case "remove":
                return funRemove();
            case "size":
                return funSize();
            default:
                throw new RuntimeError(name, String.format("Undefined Property: %s for Dict DataType.", name.lexeme));
        }
    }

    @Override
    protected void set(Token name, Object value) {
        throw new RuntimeError(name, "Can't add properties to Dict DataType"); 
    }

    @Override
    public String toString() {
        Map<Object, Object> ndict  = new HashMap<>();
        dict.forEach((key, val) -> 
                            ndict.put(HclanUtils.convertNative(key), HclanUtils.convertNative(val)));
        return ndict.toString();
    }

    private Object funPut() {
        return new HclanCallable() {
            @Override
            public boolean isVaArg() { return false; }

            @Override
            public int arity() { return 2; }

            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
                dict.put(arguments.get(0), arguments.get(1));
                return null;
            }

            @Override
            public String toString() { return "<NativeDict method .put>"; }
        };
    }

    private Object funGet() {
        return new HclanCallable() {
            @Override
            public boolean isVaArg() { return false; }

            @Override
            public int arity() { return 1; }

            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
                dict.get(arguments.get(0));
                return null;
            }

            @Override
            public String toString() { return "<NativeDict method .get>"; }
        };
    }

    private Object funClear() {
        return new HclanCallable() {
            @Override
            public boolean isVaArg() { return false; }

            @Override
            public int arity() { return 0; }

            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
                dict.clear();
                return null;
            }

            @Override
            public String toString() { return "<NativeDict method .clear>"; }
        };
    }

    private Object funContainsKey() {
        return new HclanCallable() {
            @Override
            public boolean isVaArg() { return false; }

            @Override
            public int arity() { return 1; }

            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
                return dict.containsKey(arguments.get(0));
            }

            @Override
            public String toString() { return "<NativeDict method .containsKey>"; }
        };
    }

    private Object funContainsValue() {
        return new HclanCallable() {
            @Override
            public boolean isVaArg() { return false; }

            @Override
            public int arity() { return 1; }

            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
                return dict.containsValue(arguments.get(0));
            }

            @Override
            public String toString() { return "<NativeDict method .containsValue>"; }
        };
    }

    private Object funKeys() {
        return new HclanCallable() {
            @Override
            public boolean isVaArg() { return false; }

            @Override
            public int arity() { return 0; }

            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
                Object[] keys = dict.keySet().toArray();
                return new HclanList(keys);
            }

            @Override
            public String toString() { return "<NativeDict method .keys>"; }
        };
    }

    private Object funValues() {
        return new HclanCallable() {
            @Override
            public boolean isVaArg() { return false; }

            @Override
            public int arity() { return 0; }

            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
                Object[] values = dict.values().toArray();
                return new HclanList(values);
            }

            @Override
            public String toString() { return "<NativeDict method .values>"; }
        };
    }

    private Object funRemove() {
        return new HclanCallable() {
            @Override
            public boolean isVaArg() { return false; }

            @Override
            public int arity() { return 1; }

            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
                return dict.remove(arguments.get(0));
            }

            @Override
            public String toString() { return "<NativeDict method .remove>"; }
        };
    }

    private Object funSize() {
        return new HclanCallable() {
            @Override
            public boolean isVaArg() { return false; }

            @Override
            public int arity() { return 0; }

            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
                return dict.size();
            }

            @Override
            public String toString() { return "<NativeDict method .size>"; }
        };
    }
}
