package gr.uoi.dit.master2025.gkouvas.dpp.exception;

/**
 * Exception thrown when a requested resource (entity) is not found.
 * Used to return HTTP 404 responses from the REST API.
 */
public class ResourceNotFoundException extends RuntimeException {

    /**
     * Creates a new ResourceNotFoundException with a message.
     *
     * @param message detail message
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }
}

