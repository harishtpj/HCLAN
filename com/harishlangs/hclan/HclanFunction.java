package com.harishlangs.hclan;

import java.util.List;

class HclanFunction implements HclanCallable {
    private final Stmt.Function declaration;
    private final Environment closure;

    private final boolean isInitializer;

    HclanFunction(Stmt.Function declaration, Environment closure, boolean isInitializer) {
        this.isInitializer = isInitializer;
        this.closure = closure;
        this.declaration = declaration;
    }

    HclanFunction bind(HclanInstance instance) {
        Environment environment = new Environment(closure);
        environment.define("self", instance);
        return new HclanFunction(declaration, environment, isInitializer);
    }

    @Override
    public Object call(Interpreter interpreter, List<Object> arguments) {
        Environment environment = new Environment(closure);
        for (int i = 0; i < declaration.params.size(); i++) {
            environment.define(declaration.params.get(i).lexeme,
            arguments.get(i));
        }

        try {
            interpreter.executeBlock(declaration.body, environment);
        } catch (Return returnValue) {
            if (isInitializer) return closure.getAt(0, "self");
            return returnValue.value;
        }

        if (isInitializer) return closure.getAt(0, "self");
        return null;
    }

    @Override
    public int arity() {
        return declaration.params.size();
    }

    @Override
    public boolean isVaArg() {
        return false; //TODO: support multiple arguments
    }

    @Override
    public String toString() {
        return "<fun " + declaration.name.lexeme + ">";
    }
}
