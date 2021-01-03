public class ExInsufficientManpower extends Exception
{
    /**
    	 *
    	 */
    private static final long serialVersionUID = 1L;

public ExInsufficientManpower() {
    super("Estimated manpower should not be zero or negative.");
}
public ExInsufficientManpower(String message) { super(message); }
}

