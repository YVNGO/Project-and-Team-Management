public class CmdJoinTeam extends RecordedCommand 
{
	private Employee e;
    private Team t;
    
	@Override
	public void execute(String[] cmdParts)
	{
        try {    
            if (cmdParts.length<3)
                throw new ExInsufficientArguments();
            
            Company company = Company.getInstance();
            
            if(company.employeeExist(cmdParts[1])==false){
                throw new ExEmployeeDoesNotExist();
            }

            if(company.employeeJoinedTeam(cmdParts[1])==true){
				throw new ExJoinedTeam();
			}

            if(company.teamExist(cmdParts[2])==false){
                throw new ExTeamDoesNotExist();
            }

            e = company.findEmployee(cmdParts[1]);
            t = company.findTeam(cmdParts[2]);
            company.joinTeam(e, t);

            addUndoCommand(this); 
            clearRedoList(); 

            System.out.println("Done.");

        } catch (ExInsufficientArguments e){
            System.out.println(e.getMessage());
        } catch (ExJoinedTeam e){
            System.out.println(e.getMessage());
        }catch (ExTeamDoesNotExist e){
            System.out.println(e.getMessage());
        }catch (ExEmployeeDoesNotExist e){
            System.out.println(e.getMessage());
        }
	}
	
	@Override
	public void undoMe()
	{
        Company.getInstance().disjoinTeam(e,t);
		addRedoCommand(this); 
	}
	
	@Override
	public void redoMe()
	{
        Company.getInstance().joinTeam(e,t);
        addUndoCommand(this); 
	}
}
