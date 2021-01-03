public class CmdShowEmployeeDetails extends RecordedCommand 
{
	
	@Override
	public void execute(String[] cmdParts)
	{
		Company company = Company.getInstance();
        company.listEmployeeDetails(cmdParts[1]);
	}
	
	@Override
	public void undoMe()
	{
        
	}
	
	@Override
	public void redoMe()
	{

	}
}