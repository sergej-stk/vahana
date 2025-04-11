package com.vahana.utils.v1.auth;

import java.util.HashMap;

public final class AuthUtils {
    public static HashMap<String, Object> getJWTClaim(AuthType type) {
        var result = new HashMap<String, Object>();
        result.put("token_type", type.toString());
        return result;
    }
}
