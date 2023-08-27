package com.harishlangs.hclan;

import java.util.List;

public interface HclanCallable {
    boolean isVaArg();
    int arity();
    Object call(Interpreter interpreter, List<Object> arguments);
}
