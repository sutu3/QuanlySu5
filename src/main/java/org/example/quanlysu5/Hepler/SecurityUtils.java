package org.example.quanlysu5.Hepler;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SecurityUtils {

    public static Jwt getJwt() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !(authentication.getPrincipal() instanceof Jwt jwt)) {
            return null;
        }

        return jwt;
    }

    public static String getUsername() {
        Jwt jwt = getJwt();
        return jwt == null ? null : jwt.getClaimAsString("userName");
    }

    public static String getToken() {
        Jwt jwt = getJwt();
        return jwt == null ? null : jwt.getTokenValue();
    }

    public static String getClaim(String claimName) {
        Jwt jwt = getJwt();
        return jwt == null ? null : jwt.getClaimAsString(claimName);
    }
}
