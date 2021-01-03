import java.util.ArrayList;
import java.util.Collections; //Provides sorting

public class Company {
    private ArrayList<Employee> allEmployees;
    private ArrayList<Team> allTeams;
    private ArrayList<Project> allProjects;
    private static Company instance = new Company(); 

    private Company() {         
        allEmployees = new ArrayList<Employee>();
        allTeams = new ArrayList<Team>();
        allProjects = new ArrayList<Project>();
    }

    public static Company getInstance() { return instance; }

    public void listTeams() { 
        Collections.sort(allTeams);
        Team.list(allTeams); 
        }

    public Employee createEmployee(String s) { 
        Employee e = new Employee(s);
        allEmployees.add(e);
        Collections.sort(allEmployees); 
        return e; 
        }    

    public Team createTeam(String tname, String hname) { 
        Employee e = Employee.searchEmployee(allEmployees, hname);
        Team t = new Team(tname, e);
        e.joinTeam(t);
        allTeams.add(t);
        Collections.sort(allTeams); 
        return t; 
        }

    public void addEmployee(Employee e) {
        allEmployees.add(e);
        Collections.sort(allEmployees);
        }
        
    public void removeEmployee(Employee e) {
        allEmployees.remove(e); 
        } 

    public void listEmpl() {
        for(int i=0; i<allEmployees.size(); i++){
            if(allEmployees.get(i).getJoinedTeam()!=null){
                System.out.println(allEmployees.get(i).getName() + " (" + allEmployees.get(i).getJoinedTeam().getTeamName() + ")" );
            }
            else{
                System.out.println(allEmployees.get(i).getName());
            }
        }
    } 

    public void delTeam(Team t){
        allTeams.remove(t);
    }

    public void addTeam(Team t){
        allTeams.add(t);
    }
    
    public Project createProject(String pName, String eManpower){
        Project p = new Project(pName, eManpower);
        allProjects.add(p);
        Collections.sort(allProjects); 
        return p; 
    }

    public void delProject(Project p){
        allProjects.remove(p);
    }

    public void addProject(Project p){
        allProjects.add(p);
    }

    public boolean employeeExist(String employeeName){
        boolean result = false;
        if(Employee.searchEmployee(allEmployees, employeeName)!=null){
            result = true;
        }
        return result;
    }

    public boolean teamExist(String teamName){
        boolean result = false;
        if(Team.searchTeam(allTeams, teamName)!=null){
            result = true;
        }
        return result;
    }

    public boolean employeeJoinedTeam(String employeeName){
        boolean result = false;
        for(int i=0; i<allTeams.size(); i++){
            if(allTeams.get(i).getLeaderName().equals(employeeName)){
                result = true;
                return result;
            }
            for(int j=0; j<allTeams.get(i).getTeamMembers().size(); j++){
                if(allTeams.get(i).getTeamMembers().get(j).getName().equals(employeeName)){
                    result = true;
                    return result;
                }
            }
        }
        return result;
    }

    public void listProjects() { 
        Collections.sort(allProjects);
        Project.list(allProjects); 
        }
    
    public boolean projectExists(String projectName){
        boolean result = false;
        if(Project.searchProject(allProjects, projectName)!=null){
            result = true;
        }
        return result;
    }

    public Employee findEmployee(String employeeName){
        Employee e = Employee.searchEmployee(allEmployees, employeeName);
        return e;
    }

    public Team findTeam(String teamName){
        Team t = Team.searchTeam(allTeams, teamName);
        return t;
    }

	public void joinTeam(Employee e, Team t) {
        if(e.getJoinedTeam()!=null){
            e.getJoinedTeam().fireTeamMember(e);
            e.leaveTeam();
            e.addLastDay();
        }
        e.joinTeam(t);
        e.addTeam(t);
        t.addMember(e);
    }
    
    public void disjoinTeam(Employee e, Team t){
        t.eraseMember(e);
        e.leaveTeam();
    }

    public Project findProject(String projectName){
        Project p = Project.searchProject(allProjects, projectName);
        return p;
    }

	public void assignProjectToTeam(Team t, Project p, Day startDay, Day endDay) {
        t.addProject(p);
        p.assignTeam(t);
        p.setStartEndDays(startDay , endDay);
        t.bookBusyDays(startDay, endDay);
	}

	public void removeProjectToTeam(Team t, Project p, Day startDay, Day endDay) {
        t.removeProject(p);
        p.removeTeam();
        p.resetStartEndDays();
        t.unbookBusyDays(startDay, endDay);
    }
    
    public void suggestTeam(Project p, Integer manpower){
        ArrayList<Day> finishingRecords = new ArrayList<>();
        for(int i=0; i<allTeams.size(); i++){
            finishingRecords.add(allTeams.get(i).estimateFinishDay(manpower));
        }
        Day earlisetFinishDay = new Day(finishingRecords.get(0).toString());
        for(int i=0; i<finishingRecords.size(); i++){
            if(finishingRecords.get(i).isEarlier(earlisetFinishDay)){
                earlisetFinishDay = finishingRecords.get(i);
            }
        }
        for(int i=0; i<finishingRecords.size(); i++){
            if(earlisetFinishDay.toString().equals(finishingRecords.get(i).toString())){
                System.out.println(allTeams.get(i).getTeamName() + " (Work period: " + allTeams.get(i).estimateStartDay(manpower).toString() + " to " + allTeams.get(i).estimateFinishDay(manpower).toString() + ")");
            }
        }
        // int minTime = allTeams.get(0).estimateFinishDays(manpower);
        // Team t;

        // for(int i=0; i<allTeams.size(); i++){
        //     if(allTeams.get(i).estimateFinishDays(manpower)<=minTime){
        //         minTime = allTeams.get(i).estimateFinishDays(manpower);
        //         t = allTeams.get(i);
        //     }
        //     finishingRecords.add(allTeams.get(i).estimateFinishDays(manpower));
        // }

        // for(int i=0; i<finishingRecords.size(); i++){
        //     if(finishingRecords.get(i)==minTime){
        //         System.out.println(allTeams.get(i).getTeamName() + " (Work period: " + allTeams.get(i).getStartDay(manpower).toString() + " to " + allTeams.get(i).getEndDay(manpower).toString());
        //     }
        // }
    }

    public void listEmployeeDetails(String employeeName){
        Employee e = findEmployee(employeeName);
        e.showJoinedTeams();
    }

    public void listProjectWorkerDetails(String projectName){
        Project p = findProject(projectName);
        p.showWorkerDetails();
    }
}
