package com.vahana.utils.v1;

import com.vahana.configurations.logging.CustomMDCTypes;
import com.vahana.configurations.logging.TargetTypes;
import org.slf4j.MDC;

public final class MDCUtils {
    public static void configureMDCVar(CustomMDCTypes type, TargetTypes value) {
        configureMDCVar(type, value.toString());
    }

    public static void configureMDCVar(CustomMDCTypes type, String value) {
        MDC.put(type.toString(), value);
    }
}
