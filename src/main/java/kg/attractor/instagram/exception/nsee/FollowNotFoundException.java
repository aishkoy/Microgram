package kg.attractor.instagram.exception.nsee;

import java.util.NoSuchElementException;

public class FollowNotFoundException extends NoSuchElementException {
    public FollowNotFoundException(String message) {
        super(message);
    }
}
