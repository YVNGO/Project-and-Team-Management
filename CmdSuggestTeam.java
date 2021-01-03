public class CmdSuggestTeam extends RecordedCommand 
{
	
	@Override
	public void execute(String[] cmdParts)
	{
        Company company = Company.getInstance();
        Project p = company.findProject(cmdParts[1]);
        Integer manpower = p.getEstManpower();
        company.suggestTeam(p, manpower);
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