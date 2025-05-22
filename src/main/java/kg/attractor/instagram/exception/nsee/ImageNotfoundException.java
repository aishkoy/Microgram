package kg.attractor.instagram.exception.nsee;

import java.util.NoSuchElementException;

public class ImageNotfoundException extends NoSuchElementException {
    public ImageNotfoundException(String message) {
        super(message);
    }
}
