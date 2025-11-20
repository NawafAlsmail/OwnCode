package com.example.KeycloakEx2.Controller;
import com.example.KeycloakEx2.JwtAuthConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class MainController {

    @Autowired
    private JwtAuthConverter jwtAuthConverter;
    @Autowired
    private RoleHierarchy  roleHierarchy;

    @RequestMapping("/login")
    public Map<String, Object> login(JwtAuthenticationToken token) {
        var jwt = token.getToken();
        AbstractAuthenticationToken auth = jwtAuthConverter.convert(jwt);

        var mainRoles = auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

        // Expand authorities based on the hierarchy
        var expandedAuthorities = roleHierarchy.getReachableGrantedAuthorities(auth.getAuthorities());
        var inheritedRoles = expandedAuthorities
                .stream().map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet())
                .stream().filter(r -> !mainRoles.contains(r))
                .collect(Collectors.toSet());

        String message;
        if (expandedAuthorities.stream().anyMatch(a ->
                a.getAuthority().equals("ROLE_Client_admin") ||
                a.getAuthority().equals("ROLE_admin_role"))) {
            message = "Hello ADMIN, full access granted!";
        } else {
                message = "Hello USER, limited access granted!";
        }
            return Map.of(
                    "username", auth.getName(),
                    "main_roles", mainRoles,
                    "inherited_roles", inheritedRoles,
                    "message", message
            );
    }

    @RequestMapping("/whoami")
    public Map<String, Object> whoamiJwt(JwtAuthenticationToken token) {
        if (token == null) {
            return Map.of("error", "no authentication (call with Authorization: Bearer <token>)");
        }
        return Map.of(
                "name", token.getName(),
                "authorities", token.getAuthorities().stream().map(a ->
                        a.getAuthority()).collect(Collectors.toList()),
                "claims", token.getToken().getClaims()
        );
    }
}

