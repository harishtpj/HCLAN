package com.harishlangs.hcl.std;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.harishlangs.hcl.*;

public class ListModule implements HclModule{
    
    public ListModule() {
        currMod.put("__nlst"    , funNlst());
        currMod.put("__nlst_add", funNlstAdd());
        currMod.put("__nlst_get", funNlstGet());
        currMod.put("__nlst_rem", funNlstRem());
        currMod.put("__nlst_len", funNlstLen());
        currMod.put("__nlst_clr", funNlstClr());
    }

    public Map<String, Object> getObjects() {
        return currMod;
    }

    private Object funNlst() {
        return new HclCallable() {
            @Override
            public boolean isVaArg() { return false; }

            @Override
            public int arity() { return 0; }

            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
              return new ArrayList<Object>();
            }
      
            @Override
            public String toString() { return "<NativeFun __nlst>"; }
        };
    }

    private Object funNlstAdd() {
        return new HclCallable() {
            @Override
            public boolean isVaArg() { return false; }

            @Override
            public int arity() { return 2; }

            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
                interpreter.checkIfList(arguments.get(0));
                @SuppressWarnings("unchecked")
                ArrayList<Object> list = (ArrayList<Object>)arguments.get(0);
                list.add(arguments.get(1));
                return null;
            }
      
            @Override
            public String toString() { return "<NativeFun __nlst_add>"; }
        };
    }

    private Object funNlstGet() {
        return new HclCallable() {
            @Override
            public boolean isVaArg() { return false; }

            @Override
            public int arity() { return 2; }

            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
                interpreter.checkIfList(arguments.get(0));
                @SuppressWarnings("unchecked")
                ArrayList<Object> list = (ArrayList<Object>)arguments.get(0);
                return list.get((Integer)interpreter.convertNative(arguments.get(1)));
            }
      
            @Override
            public String toString() { return "<NativeFun __nlst_get>"; }
        };
    }

    private Object funNlstRem() {
        return new HclCallable() {
            @Override
            public boolean isVaArg() { return false; }

            @Override
            public int arity() { return 2; }

            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
                interpreter.checkIfList(arguments.get(0));
                @SuppressWarnings("unchecked")
                ArrayList<Object> list = (ArrayList<Object>)arguments.get(0);
                return list.remove((Integer)interpreter.convertNative(arguments.get(1)));
            }
      
            @Override
            public String toString() { return "<NativeFun __nlst_rem>"; }
        };
    }

    private Object funNlstLen() {
        return new HclCallable() {
            @Override
            public boolean isVaArg() { return false; }

            @Override
            public int arity() { return 1; }

            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
                interpreter.checkIfList(arguments.get(0));
                @SuppressWarnings("unchecked")
                ArrayList<Object> list = (ArrayList<Object>)arguments.get(0);
                return list.size();
            }
      
            @Override
            public String toString() { return "<NativeFun __nlst_len>"; }
        };
    }

    private Object funNlstClr() {
        return new HclCallable() {
            @Override
            public boolean isVaArg() { return false; }

            @Override
            public int arity() { return 1; }

            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
                interpreter.checkIfList(arguments.get(0));
                @SuppressWarnings("unchecked")
                ArrayList<Object> list = (ArrayList<Object>)arguments.get(0);
                list.clear();
                return null;
            }
      
            @Override
            public String toString() { return "<NativeFun __nlst_clr>"; }
        };
    }
}
