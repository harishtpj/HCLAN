package com.harishlangs.hcl.std;

import java.util.HashMap;
import java.util.Map;

interface HclModule {
    final Map<String, Object> currMod = new HashMap<>();
    public Map<String, Object> getObjects();
}
