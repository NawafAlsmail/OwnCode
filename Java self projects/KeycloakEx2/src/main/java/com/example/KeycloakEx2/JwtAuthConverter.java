package com.example.KeycloakEx2;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimNames;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Component
public class JwtAuthConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    private final JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter =
            new JwtGrantedAuthoritiesConverter();
    @Value("${jwt.auth.convertor.principleAttributeName}")
    private String principleAttributeName;
    @Value("${jwt.auth.convertor.resourceId}")
    private String resourceId;


    @Override
    public AbstractAuthenticationToken convert(@NonNull Jwt jwt) {
        Collection<GrantedAuthority> authorities = Stream.of(
                jwtGrantedAuthoritiesConverter.convert(jwt),
                extractRealmRoles(jwt),
                extractClientRoles(jwt)
                ).flatMap(Collection::stream)
                .collect(Collectors.toSet());
        return new JwtAuthenticationToken(jwt, authorities, getPrincipleClaimName(jwt));
    }

    private String getPrincipleClaimName(Jwt jwt) {
        String claimName = JwtClaimNames.SUB;
        if(principleAttributeName != null) {
            claimName = principleAttributeName;
        }
        return jwt.getClaim(claimName);
    }

    // that's Realm's extracting roles
    private Collection<? extends GrantedAuthority> extractRealmRoles(Jwt jwt) {
        Map<String, Object> realmAccess = jwt.getClaim("realm_access");
        if (realmAccess == null) return Set.of();
        Collection<String> roles = (Collection<String>) realmAccess.get("roles");
        if (roles == null) return Set.of();
        return roles.stream().map(r -> new SimpleGrantedAuthority("ROLE_" + r))
                .collect(Collectors.toSet());
    }

    // that's Client's extracting roles
    private Collection<? extends GrantedAuthority> extractClientRoles(Jwt jwt) {
        Map<String, Object> resourceAccess=jwt.getClaim("resource_access");
        if(resourceAccess == null) {
            return  Set.of();
        }
        if(resourceAccess.get(resourceId) == null) {
            return  Set.of();
        }
        Map<String, Object> resource = (Map<String, Object>) resourceAccess.get(resourceId);
        Collection<String> resourceRoles = (Collection<String>) resource.get("roles");
        return resourceRoles
                .stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toSet());
    }
}