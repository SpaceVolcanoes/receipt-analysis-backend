package ee.taltech.receipt.security;

import org.springframework.security.access.annotation.Secured;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public enum Role {

    USER("ROLE_USER"), ADMIN("ROLE_ADMIN"), GUEST("ROLE_GUEST");

    public final String name;

    Role(String name) {
        this.name = name;
    }

    @Target({ ElementType.METHOD, ElementType.TYPE })
    @Retention(RetentionPolicy.RUNTIME)
    @Secured("ROLE_ADMIN")
    public @interface Admin {}

    @Target({ ElementType.METHOD, ElementType.TYPE })
    @Retention(RetentionPolicy.RUNTIME)
    @Secured("ROLE_USER")
    public @interface User {}

    public @interface Guest {}

}
