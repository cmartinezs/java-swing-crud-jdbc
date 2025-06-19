package cl.duoc.swingapp.view.exception;

public class ViewException extends Exception {
  public ViewException(String message) {
    super(message);
  }

  public ViewException(String message, Throwable cause) {
    super(message, cause);
  }

  public ViewException(Throwable cause) {
    super(cause);
  }
}
