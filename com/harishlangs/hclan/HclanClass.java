package com.harishlangs.hclan;

import java.util.List;
import java.util.Map;

public class HclanClass extends HclanInstance implements HclanCallable {
    final String name;
    final HclanClass superclass;
    private final Map<String, HclanFunction> methods;

    protected HclanClass(HclanClass metaclass, String name, HclanClass superclass, Map<String, HclanFunction> methods) {
      super(metaclass);
      this.superclass = superclass;
      this.name = name;
      this.methods = methods;
    }

    HclanFunction findMethod(String name) {
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
        HclanInstance instance = new HclanInstance(this);

        HclanFunction initializer = findMethod("_init");
        if (initializer != null) {
          initializer.bind(instance).call(interpreter, arguments);
        }

        return instance;
    }

    @Override
    public int arity() {
        HclanFunction initializer = findMethod("_init");
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
