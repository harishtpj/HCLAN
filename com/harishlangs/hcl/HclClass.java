package com.harishlangs.hcl;

import java.util.List;
import java.util.Map;

public class HclClass implements HclCallable {
    final String name;
    private final Map<String, HclFunction> methods;

    HclClass(String name, Map<String, HclFunction> methods) {
      this.name = name;
      this.methods = methods;
    }

    HclFunction findMethod(String name) {
      if (methods.containsKey(name)) {
        return methods.get(name);
      }
  
      return null;
    }

    @Override
    public Object call(Interpreter interpreter, List<Object> arguments) {
        HclInstance instance = new HclInstance(this);

        HclFunction initializer = findMethod("_init");
        if (initializer != null) {
          initializer.bind(instance).call(interpreter, arguments);
        }

        return instance;
    }

    @Override
    public int arity() {
        HclFunction initializer = findMethod("_init");
        if (initializer == null) return 0;
        return initializer.arity();
    }

    @Override
    public boolean isVaArg() {
        return false;
    }

    @Override
    public String toString() {
      return name;
    }
}
