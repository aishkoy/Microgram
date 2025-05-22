package kg.attractor.instagram.exception.nsee;

import java.util.NoSuchElementException;

public class PostNotFoundException extends NoSuchElementException {
    public PostNotFoundException(String message) {
        super(message);
    }
}
