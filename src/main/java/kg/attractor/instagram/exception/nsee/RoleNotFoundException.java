package kg.attractor.instagram.exception.nsee;

import java.util.NoSuchElementException;

public class RoleNotFoundException extends NoSuchElementException {
    public RoleNotFoundException(String message) {
        super(message);
    }
}
