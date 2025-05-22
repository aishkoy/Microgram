package kg.attractor.instagram.exception.nsee;

import java.util.NoSuchElementException;

public class CommentNotFoundException extends NoSuchElementException {
    public CommentNotFoundException(String message) {
        super(message);
    }
}
