package fontys.sem3.its.meem.business.service;

import fontys.sem3.its.meem.business.exception.InvalidAccessTokenException;
import fontys.sem3.its.meem.business.usecase.AccessToken.AccessTokenDecodingUseCase;
import fontys.sem3.its.meem.business.usecase.AccessToken.AccessTokenEncodingUseCase;
import fontys.sem3.its.meem.domain.model.AccessToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class AccessTokenDecodeEncodeImpl implements AccessTokenEncodingUseCase, AccessTokenDecodingUseCase {
    private final Key key;

    public AccessTokenDecodeEncodeImpl(@NotBlank @Value("${jwt.secret}") String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    @Override
    public String encode(@NotNull AccessToken accessToken) {
        Map<String, Object> claimsMap = new HashMap<>();
        claimsMap.put("authority", accessToken.getAuthority());
        claimsMap.put("userId", accessToken.getUserId());
        Instant now = Instant.now();
        return Jwts.builder()
                .setSubject(accessToken.getSubject())
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plus(24, ChronoUnit.HOURS)))
                .addClaims(claimsMap)
                .signWith(key)
                .compact();
    }

    @Override
    public AccessToken decode(String accessTokenEncoded) {
        try {
            Jwt jwt = Jwts.parserBuilder().setSigningKey(key).build().parse(accessTokenEncoded);
            Claims claims = (Claims) jwt.getBody();
//            Jwt<?, Claims> jwt = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessTokenEncoded);

            return AccessToken.builder()
                    .subject(((Claims) jwt.getBody()).getSubject())
                    .authority(claims.get("authority", String.class))
                    .userId(claims.get("userId", Integer.class))
                    .build();
        } catch (JwtException e) {
            throw new InvalidAccessTokenException(e.getMessage());
        }
    }


}
