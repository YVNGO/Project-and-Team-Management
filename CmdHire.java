
public class CmdHire extends RecordedCommand 
{
	private Employee e;
	
	@Override
	public void execute(String[] cmdParts)
	{
        try {    
            if (cmdParts.length<2)
                throw new ExInsufficientArguments();
            
            Company company = Company.getInstance();

            if(company.employeeExist(cmdParts[1])==true)
                throw new ExDuplicateEmployee();
            
            e = company.createEmployee(cmdParts[1]);


            addUndoCommand(this); 
            clearRedoList(); 

            System.out.println("Done.");

        } catch (ExInsufficientArguments e){
            System.out.println(e.getMessage());
        } catch (ExDuplicateEmployee e){
            System.out.println(e.getMessage());
        }
	}
	
	@Override
	public void undoMe()
	{
        Company.getInstance().removeEmployee(e);
		addRedoCommand(this);
	}
	
	@Override
	public void redoMe()
	{
        Company.getInstance().addEmployee(e);
        addUndoCommand(this); 
	}
}