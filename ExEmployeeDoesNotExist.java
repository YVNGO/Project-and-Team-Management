public class ExEmployeeDoesNotExist extends Exception
{
    /**
    	 *
    	 */
    private static final long serialVersionUID = 1L;

    public ExEmployeeDoesNotExist() {
    super("Employee name does not exist.");
}
public ExEmployeeDoesNotExist(String message) { super(message); }
}

