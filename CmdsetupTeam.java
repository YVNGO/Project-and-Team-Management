
public class CmdsetupTeam extends RecordedCommand 
{
    Team t;
   
	@Override
	public void execute(String[] cmdParts)
	{
		try{
			if (cmdParts.length<3)
                throw new ExInsufficientArguments();
            
			Company company = Company.getInstance();

			if(company.teamExist(cmdParts[1])==true)
                throw new ExDuplicateTeam();

			if(company.employeeExist(cmdParts[2])==false){
				throw new ExEmployeeDoesNotExist();
			}	

			if(company.employeeJoinedTeam(cmdParts[2])==true){
				throw new ExJoinedTeam();
			}

			t = company.createTeam(cmdParts[1], cmdParts[2]);
        

			addUndoCommand(this); 
			clearRedoList(); 

			System.out.println("Done.");

		}catch(ExDuplicateTeam d){
			System.out.println(d.getMessage());
		}catch(ExInsufficientArguments e){
			System.out.println(e.getMessage());
		}catch (ExEmployeeDoesNotExist e){
			System.out.println(e.getMessage());
		}catch (ExJoinedTeam e){
			System.out.println(e.getMessage());
		}		
	}
	
	@Override
	public void undoMe()
	{
    Company.getInstance().delTeam(t);
		addRedoCommand(this); 
	}
	
	@Override
	public void redoMe()
	{
		Company.getInstance().addTeam(t);
        addUndoCommand(this); 
	}
}