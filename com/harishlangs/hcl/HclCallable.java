package com.harishlangs.hcl;

import java.util.List;

public interface HclCallable {
    boolean isVaArg();
    int arity();
    Object call(Interpreter interpreter, List<Object> arguments);
}
