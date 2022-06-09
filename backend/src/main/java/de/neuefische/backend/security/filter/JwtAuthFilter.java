package de.neuefische.backend.security.filter;

import de.neuefische.backend.security.dto.AppUserInfoDto;
import de.neuefische.backend.security.model.AppUser;
import de.neuefische.backend.security.repository.AppUserRepository;
import de.neuefische.backend.security.service.JwtUtilService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Slf4j
@Service
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtilService jwtUtilService;
    private final AppUserRepository appUserRepository;

    @Autowired
    public JwtAuthFilter(JwtUtilService jwtUtilService, AppUserRepository appUserRepository) {
        this.jwtUtilService = jwtUtilService;
        this.appUserRepository = appUserRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = getAuthToken(request);

        try {
            if (token != null && !token.isBlank()) {
                String userMail = jwtUtilService.extractUserMail(token);
                setContext(userMail);
            } else {
                log.error("Token is null or blank.");
            }
        } catch (Exception ex) {
            log.error("JWT error." + ex.getMessage());
        }

        filterChain.doFilter(request, response);
    }

    private void setContext(String userMail) {
        AppUser loggedUser = appUserRepository.findByMail(userMail).orElseThrow(() -> new UsernameNotFoundException("No username with this mail found"));
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(AppUserInfoDto.builder()
                        .id(loggedUser.getId())
                        .mail(loggedUser.getMail())
                        .firstName(loggedUser.getFirstName())
                        .lastName(loggedUser.getLastName())
                        .company(loggedUser.getCompany())
                        .role(loggedUser.getRole())
                        .build(), "", List.of(new SimpleGrantedAuthority(loggedUser.getRole())));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }

    private String getAuthToken(HttpServletRequest request) {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authHeader != null) {
            return authHeader.replace("Bearer ", "").trim();
        }
        return null;
    }
}
