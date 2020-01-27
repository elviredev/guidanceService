package intra.poleemploi.guidanceservice.security;

public interface SecurityParams {
    String JWT_HEADER_NAME = "Authorization";
    String SECRET = "asBH56Ml1pWWuiopH45";
    long EXPIRATION = 864000000;
    String HEADER_PREFIX = "Bearer ";
}
