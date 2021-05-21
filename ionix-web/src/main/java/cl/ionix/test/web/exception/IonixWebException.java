package cl.ionix.test.web.exception;

public class IonixWebException extends RuntimeException {

	private static final long serialVersionUID = 5438319255915097092L;

	public IonixWebException() {
		super();
	}

	public IonixWebException(String message) {
		super(message);
	}

	public IonixWebException(String message, Throwable cause) {
		super(message, cause);
	}

	public IonixWebException(Throwable cause) {
		super(cause);
	}
}
