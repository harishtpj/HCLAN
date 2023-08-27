package com.harishlangs.hclan.std;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.harishlangs.hclan.*;

public class NativeModule implements HclanModule {

    public NativeModule() {
        // General Functions
        currMod.put("clock", funClock());
        currMod.put("input", funInput());
        currMod.put("fmt"  , funFmt());
        currMod.put("num"  , funNum());
        currMod.put("str"  , funStr());
        currMod.put("bool" , funBool());
        currMod.put("exit" , funExit());
    }

    public Map<String, Object> getObjects() {
        return currMod;
    }

    private Object funClock() {
        return new HclanCallable() {
            @Override
            public boolean isVaArg() { return false; }

            @Override
            public int arity() { return 0; }
      
            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
              return (double)System.currentTimeMillis() / 1000.0;
            }
      
            @Override
            public String toString() { return "<NativeFun clock>"; }
          };
    }

    private Object funInput() {
        return new HclanCallable() {
            @Override
            public boolean isVaArg() { return false; }

            @Override
            public int arity() { return 1; }

            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
                System.out.print(arguments.get(0));
                return interpreter.stdin.nextLine();
            }

            @Override
            public String toString() { return "<NativeFun input>"; }
        };
    }

    private Object funFmt() {
        return new HclanCallable() {
            @Override
            public boolean isVaArg() { return true; }

            @Override
            public int arity() { return 1; }

            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
                String fmtString = (String)arguments.remove(0);
                Object[] argsArray = arguments.stream()
                                              .map(obj -> HclanUtils.convertNative(obj))
                                              .collect(Collectors.toList())
                                              .toArray();
                return String.format(fmtString, argsArray);
            }

            @Override
            public String toString() { return "<NativeFun input>"; }
        };
    }

    private Object funNum() {
        return new HclanCallable() {
            @Override
            public boolean isVaArg() { return false; }

            @Override
            public int arity() { return 1; }

            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
                return Double.parseDouble((String)arguments.get(0));
            }

            @Override
            public String toString() { return "<NativeFun num>"; }
        };
    }

    private Object funStr() {
        return new HclanCallable() {
            @Override
            public boolean isVaArg() { return false; }

            @Override
            public int arity() { return 1; }

            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
                return arguments.get(0).toString();
            }

            @Override
            public String toString() { return "<NativeFun str>"; }
        };
    }

    private Object funBool() {
        return new HclanCallable() {
            @Override
            public boolean isVaArg() { return false; }

            @Override
            public int arity() { return 1; }

            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
                return Boolean.parseBoolean((String)arguments.get(0));
            }

            @Override
            public String toString() { return "<NativeFun str>"; }
        };
    }

    private Object funExit() {
        return new HclanCallable() {
            @Override
            public boolean isVaArg() { return false; }

            @Override
            public int arity() { return 1; }

            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
                int exitCode = (int)HclanUtils.convertNative(arguments.get(0));
                System.exit(exitCode);
                return null;
            }

            @Override
            public String toString() { return "<NativeFun str>"; }
        };
    }
}
