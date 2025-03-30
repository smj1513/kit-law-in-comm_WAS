package kit.se.capstone2.auth.jwt;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class JwtProperties {
	public static final Long ACCESS_TOKEN_EXPIRATION = 1000L * 60 * 30; // 30 minutes
	public static final Long REFRESH_TOKEN_EXPIRATION = 1000L * 60 * 60 * 7; // 7 hours
	public static final String AUTH_HEADER = "X-ACCESS-TOKEN";
	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String ACCESS_TOKEN_TYPE = "access";
	public static final String REFRESH_TOKEN_TYPE = "refresh";
	public static final String ROLE = "role";
	public static final String USERNAME = "jti";
	public static final String CATEGORY = "category";
}

