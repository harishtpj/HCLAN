package com.harishlangs.hclan.std;

import java.util.HashMap;
import java.util.Map;

interface HclanModule {
    final Map<String, Object> currMod = new HashMap<>();
    public Map<String, Object> getObjects();
}
