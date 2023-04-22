package com.harishlangs.hcl.std;

import com.harishlangs.hcl.*;

import java.util.List;
import java.util.Map;

public class NativeModule implements HclModule {

    public NativeModule() {
        // Global Variables
        currMod.put("__VERSION__", 1.0);

        // General Functions
        currMod.put("clock", funClock());
        currMod.put("input", funInput());
        currMod.put("num"  , funNum());
        currMod.put("str"  , funStr());
        currMod.put("bool" , funBool());
    }

    public Map<String, Object> getObjects() {
        return currMod;
    }

    private Object funClock() {
        return new HclCallable() {
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
        return new HclCallable() {
            @Override
            public int arity() { return 1; }

            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
                System.out.print(arguments.get(0));
                return Interpreter.stdin.nextLine();
            }

            @Override
            public String toString() { return "<NativeFun input>"; }
        };
    }

    private Object funNum() {
        return new HclCallable() {
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
        return new HclCallable() {
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
        return new HclCallable() {
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
}
