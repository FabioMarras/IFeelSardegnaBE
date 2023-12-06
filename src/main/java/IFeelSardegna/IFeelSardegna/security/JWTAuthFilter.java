package IFeelSardegna.IFeelSardegna.security;

import IFeelSardegna.IFeelSardegna.entites.User;
import IFeelSardegna.IFeelSardegna.exceptions.UnauthorizedException;
import IFeelSardegna.IFeelSardegna.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
    public class JWTAuthFilter extends OncePerRequestFilter {
        @Autowired
        private JWTTools jwtTools;
        @Autowired
        private UserService usersService;

        @Override
        protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

            String authHeader = request.getHeader("Authorization");
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                throw new UnauthorizedException("Per favore passa il Bearer Token nell'Authorization header");
            } else {
                String token = authHeader.substring(7);
                System.out.println("TOKEN -> " + token);
                jwtTools.verifyToken(token);

                String id = jwtTools.extractIdFromToken(token);
                User currentUtente = usersService.findById(Long.parseLong((id)));

                Authentication authentication = new UsernamePasswordAuthenticationToken(currentUtente, null, currentUtente.getAuthorities() );
              SecurityContextHolder.getContext().setAuthentication(authentication);

                filterChain.doFilter(request, response);
            }

        }

        @Override
        protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
            String servletPath = request.getServletPath();
            AntPathMatcher pathMatcher = new AntPathMatcher();

            return pathMatcher.match("/auth/**", servletPath) ||
                    "/province".equals(servletPath) ||
                    pathMatcher.match("/mari/**", servletPath) ||
                    pathMatcher.match("/città/**", servletPath) ||
                    pathMatcher.match("/terme/**", servletPath);
        }
    }