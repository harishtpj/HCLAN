package com.harishlangs.hcl;

import java.util.List;

class HclFunction implements HclCallable {
    private final Stmt.Function declaration;
    private final Environment closure;

    private final boolean isInitializer;

    HclFunction(Stmt.Function declaration, Environment closure, boolean isInitializer) {
        this.isInitializer = isInitializer;
        this.closure = closure;
        this.declaration = declaration;
    }

    HclFunction bind(HclInstance instance) {
        Environment environment = new Environment(closure);
        environment.define("self", instance);
        return new HclFunction(declaration, environment, isInitializer);
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
