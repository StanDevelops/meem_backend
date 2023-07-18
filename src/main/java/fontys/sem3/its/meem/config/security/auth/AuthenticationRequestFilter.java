package fontys.sem3.its.meem.config.security.auth;

import fontys.sem3.its.meem.business.exception.InvalidAccessTokenException;
import fontys.sem3.its.meem.business.usecase.AccessToken.AccessTokenDecodingUseCase;
import fontys.sem3.its.meem.domain.model.AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

//@Component
//public class AuthenticationRequestFilter extends OncePerRequestFilter {
//    private final Key key;
//    private final static String SPRING_SECURITY_ROLE_PREFIX = "PRIVILEGE_";
//    @Autowired
//    private AccessTokenDecoder accessTokenDecoder;
//    private final static Logger LOGGER = LoggerFactory.getLogger(AuthenticationRequestFilter.class);
//
//    public AuthenticationRequestFilter(@Value("${jwt.secret}") String secretKey) {
//        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
//        this.key = Keys.hmacShaKeyFor(keyBytes);
//    }
//
//    @Override
//    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
//
//        // Check for authentication headers
//        final String authHeader = request.getHeader("Authorization");
//        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
//            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
//        }
//
//        // Decode and validate the authentication header
//        String token = authHeader.substring(7); // Extract the token from the header
//
//                try {
//            AccessToken accessTokenDTO = accessTokenDecoder.decode(token);
//            // Validate the token and get the authentication
//                    Authentication authentication = validateToken(accessTokenDTO);
//                    // Set the security context
//                    SecurityContextHolder.getContext().setAuthentication(authentication);
//
//                    // Continue processing the request
//                    chain.doFilter(request, response);
//        } catch (InvalidAccessTokenException e) {
//            logger.error("Error validating access token", e);
//            sendAuthenticationError(response);
//        }
//
//
//    }
//        private void sendAuthenticationError(HttpServletResponse response) throws IOException {
//        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//        response.flushBuffer();
//    }
//    private Authentication validateToken( AccessToken token) {
//        try {
////            JwtParser jwtParser = Jwts.parserBuilder()
////                    .setSigningKey(key)
////                    .build();
////
////            Claims claims = jwtParser.parseClaimsJws(token).getBody();
//            String username = token.getSubject();
//            String authority = SPRING_SECURITY_ROLE_PREFIX + token.getAuthority();
//            int userId = token.getUserId();
//
//            List<SimpleGrantedAuthority> authorities = Arrays.stream(authority.split(","))
//                    .map(SimpleGrantedAuthority::new)
//                    .collect(Collectors.toList());
//
//            Authentication authentication = new UsernamePasswordAuthenticationToken(userId, null, authorities);
////            List<String> roles = (List<String>) claims.get("authority");
////
////            List<SimpleGrantedAuthority> authorities = roles.stream()
////                    .map(SimpleGrantedAuthority::new)
////                    .collect(Collectors.toList());
//
//            return authentication;
//        } catch (ExpiredJwtException exception) {
//            LOGGER.warn("Request to parse expired JWT : {} failed : {}", token, exception.getMessage());
//        } catch (UnsupportedJwtException exception) {
//            LOGGER.warn("Request to parse unsupported JWT : {} failed : {}", token, exception.getMessage());
//        } catch (MalformedJwtException exception) {
//        }
//        return null;
//    }
//@Component
//public class AuthenticationRequestFilter extends OncePerRequestFilter {
//
//    private final static String SPRING_SECURITY_ROLE_PREFIX = "PRIVILEGE_";
//
//    @Autowired
//    private AccessTokenDecoder accessTokenDecoder;
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
//            throws ServletException, IOException {
//
//        final String requestTokenHeader = request.getHeader("Authorization");
//        if (requestTokenHeader == null || !requestTokenHeader.startsWith("Bearer ")) {
//            chain.doFilter(request, response);
//            return;
//        }
//
//        String accessToken = requestTokenHeader.substring(7);
//
//        try {
//            AccessToken accessTokenDTO = accessTokenDecoder.decode(accessToken);
//            setupSpringSecurityContext(accessTokenDTO);
//            chain.doFilter(request, response);
//        } catch (InvalidAccessTokenException e) {
//            logger.error("Error validating access token", e);
//            sendAuthenticationError(response);
//        }
//    }
//
//    private void sendAuthenticationError(HttpServletResponse response) throws IOException {
//        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//        response.flushBuffer();
//    }
//
//    private void setupSpringSecurityContext(AccessToken accessToken) {
//        UserDetails userDetails = new User(accessToken.getSubject(), "",
//                List.of(new SimpleGrantedAuthority(SPRING_SECURITY_ROLE_PREFIX + accessToken.getAuthority())));
//
//
//        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
//                userDetails, null, userDetails.getAuthorities());
//        usernamePasswordAuthenticationToken.setDetails(accessToken);
//        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
//    }
//}

@Component
public class AuthenticationRequestFilter extends OncePerRequestFilter {

    private final static String SPRING_SECURITY_ROLE_PREFIX = "ROLE_";

    @Autowired
    private AccessTokenDecodingUseCase accessTokenDecoder;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {


        final String requestTokenHeader = request.getHeader("Authorization");
        if (requestTokenHeader == null || !requestTokenHeader.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }

        String accessToken = requestTokenHeader.substring(7);

        try {
            AccessToken accessTokenDTO = accessTokenDecoder.decode(accessToken);
            setupSpringSecurityContext(accessTokenDTO);
            chain.doFilter(request, response);
        } catch (InvalidAccessTokenException e) {
            logger.error("Error validating access token", e);
            sendAuthenticationError(response);
        }
    }

    private void sendAuthenticationError(HttpServletResponse response) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.flushBuffer();
    }

    private void setupSpringSecurityContext(AccessToken accessToken) {
        UserDetails userDetails = new User(accessToken.getSubject(), "",
                List.of(new SimpleGrantedAuthority(SPRING_SECURITY_ROLE_PREFIX + accessToken.getAuthority())));

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
        usernamePasswordAuthenticationToken.setDetails(accessToken);
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
    }

}