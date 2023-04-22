package com.harishlangs.hcl;

import java.util.List;

public interface HclCallable {
    int[] argSize = {0};
    int arity();
    Object call(Interpreter interpreter, List<Object> arguments);
}
