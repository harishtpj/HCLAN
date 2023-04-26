package com.harishlangs.hcl.std;

import java.util.List;
import java.util.Map;

import com.harishlangs.hcl.HclCallable;
import com.harishlangs.hcl.Interpreter;

public class HclStructures implements HclModule {

    public HclStructures() {
        currMod.put("List", funList());
        currMod.put("Dict", funDict());
    }

    public Map<String, Object> getObjects() {
        return currMod;
    }

    private Object funList() {
        return new HclCallable() {
            @Override
            public boolean isVaArg() { return false; }

            @Override
            public int arity() { return 0; }
      
            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
              return new HclList();
            }
      
            @Override
            public String toString() { return "<NativeList constructor>"; }
          };
    }

    private Object funDict() {
        return new HclCallable() {
            @Override
            public boolean isVaArg() { return false; }

            @Override
            public int arity() { return 0; }
      
            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
              return new HclDict();
            }
      
            @Override
            public String toString() { return "<NativeDict constructor>"; }
          };
    }
}
