package com.harishlangs.hcl.std;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.harishlangs.hcl.*;

public class HclList extends HclInstance {
    private final ArrayList<Object> list;

    HclList() {
        super(null);
        list = new ArrayList<>();
    }

    @Override
    protected Object get(Token name) {
        switch (name.lexeme) {
            case "add":
                return funAdd();
            case "addAt":
                return funAddAt(name);
            case "get":
                return funGet(name);
            case "clear":
                return funClear();
            case "delete":
                return funDelete(name);
            case "length":
                return funLength();
            default:
                throw new RuntimeError(name, String.format("Undefined Property: %s for List DataType.", name.lexeme));
        }
    }

    @Override
    protected void set(Token name, Object value) {
        throw new RuntimeError(name, "Can't add properties to List DataType"); 
    }

    @Override
    public String toString() {
        return list.stream()
                   .map(obj -> HclUtils.convertNative(obj))
                   .collect(Collectors.toList())
                   .toString();
    }

    private Object funAdd() {
        return new HclCallable() {
            @Override
            public boolean isVaArg() { return false; }

            @Override
            public int arity() { return 1; }

            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
                list.add(arguments.get(0));
                return null;
            }

            @Override
            public String toString() { return "<NativeList method .add>"; }
        };
    }

    private Object funAddAt(Token name) {
        return new HclCallable() {
            @Override
            public boolean isVaArg() { return false; }

            @Override
            public int arity() { return 2; }

            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
                HclUtils.checkNumber(name, arguments.get(0));
                Integer index = (Integer)HclUtils.convertNative(arguments.get(0));
                list.add(index, arguments.get(1));
                return null;
            }

            @Override
            public String toString() { return "<NativeList method .addAt>"; }
        };
    }

    private Object funGet(Token name) {
        return new HclCallable() {
            @Override
            public boolean isVaArg() { return false; }

            @Override
            public int arity() { return 1; }

            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
                HclUtils.checkNumber(name, arguments.get(0));
                return list.get((Integer)HclUtils.convertNative(arguments.get(0)));
            }

            @Override
            public String toString() { return "<NativeList method .get>"; }
        };
    }

    private Object funClear() {
        return new HclCallable() {
            @Override
            public boolean isVaArg() { return false; }

            @Override
            public int arity() { return 0; }

            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
                list.clear();
                return null;
            }

            @Override
            public String toString() { return "<NativeList method .clear>"; }
        };
    }

    private Object funDelete(Token name) {
        return new HclCallable() {
            @Override
            public boolean isVaArg() { return false; }

            @Override
            public int arity() { return 1; }

            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
                HclUtils.checkNumber(name, arguments.get(0));
                Integer index = (Integer)HclUtils.convertNative(arguments.get(0));
                return list.remove(index);
            }

            @Override
            public String toString() { return "<NativeList method .delete>"; }
        };
    }

    private Object funLength() {
        return new HclCallable() {
            @Override
            public boolean isVaArg() { return false; }

            @Override
            public int arity() { return 0; }

            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
                return list.size();
            }

            @Override
            public String toString() { return "<NativeList method .length>"; }
        };
    }
}
