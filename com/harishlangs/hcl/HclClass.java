package com.harishlangs.hcl;

import java.util.List;
import java.util.Map;

class HclClass extends HclInstance implements HclCallable {
    final String name;
    final HclClass superclass;
    private final Map<String, HclFunction> methods;

    HclClass(HclClass metaclass, String name, HclClass superclass, Map<String, HclFunction> methods) {
      super(metaclass);
      this.superclass = superclass;
      this.name = name;
      this.methods = methods;
    }

    HclFunction findMethod(String name) {
      if (methods.containsKey(name)) {
        return methods.get(name);
      }

      if (superclass != null) {
        return superclass.findMethod(name);
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
