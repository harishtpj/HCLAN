package com.harishlangs.hclan.std;

import java.util.List;
import java.util.Map;

import com.harishlangs.hclan.HclanCallable;
import com.harishlangs.hclan.Interpreter;

public class HclanStructures implements HclanModule {

    public HclanStructures() {
        currMod.put("List", funList());
        currMod.put("Dict", funDict());
    }

    public Map<String, Object> getObjects() {
        return currMod;
    }

    private Object funList() {
        return new HclanCallable() {
            @Override
            public boolean isVaArg() { return false; }

            @Override
            public int arity() { return 0; }
      
            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
              return new HclanList();
            }
      
            @Override
            public String toString() { return "<NativeList constructor>"; }
          };
    }

    private Object funDict() {
        return new HclanCallable() {
            @Override
            public boolean isVaArg() { return false; }

            @Override
            public int arity() { return 0; }
      
            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
              return new HclanDict();
            }
      
            @Override
            public String toString() { return "<NativeDict constructor>"; }
          };
    }
}
