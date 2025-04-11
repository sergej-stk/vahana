package com.vahana.controllers.v1;

import com.vahana.configurations.logging.CustomMDCTypes;
import com.vahana.configurations.logging.TargetTypes;
import com.vahana.utils.v1.MDCUtils;

public class BaseController {
    public void InitializeMDC() {
        MDCUtils.configureMDCVar(CustomMDCTypes.TARGET, TargetTypes.ENDPOINT);
    }
}
