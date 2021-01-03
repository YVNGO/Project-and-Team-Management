import java.util.ArrayList;
import java.util.Collections;

public class Project implements Comparable<Project>{
    private String projectName;
    private Integer estManpower;
    private Team assignedTeam;
    private Day startDay;
    private Day endDay;
    private ArrayList<Employee> assignedTeamMembersHistory = new ArrayList<>();
    

    public Project(String aProjectName, String aEstManpower) {
        projectName=aProjectName;//Set name
        estManpower = Integer.parseInt(aEstManpower);
        } 

    public String getProjectName() { return projectName;/*simply return the name string */}

    public Integer getEstManpower(){ return estManpower;}

    public static Project searchProject(ArrayList<Project> list,
    String nameToSearch) {
    for(int i=0; i<list.size(); i++){
        if(list.get(i).getProjectName().equals(nameToSearch)){
            return list.get(i);
        }
    } //search the arrayList for the project with the given name
    return null;
    }

    public String getAssignedTeam(){
        if(assignedTeam!=null){
            return assignedTeam.getTeamName();
        }
        else{
            return "(Not Assigned)";
        }
    }

    public static void list(ArrayList<Project> list) {
        //Learn: "-" means left-aligned
        System.out.printf("%-9s%-14s%-13s%-13s%-13s\n", "Project", "Est manpower", "Team", "Start Day", "End Day");
        for (Project p : list)
            System.out.printf("%-9s%-14s%-13s%-13s%-13s\n", p.projectName, p.estManpower + " man-days", p.getAssignedTeam(), p.getStartDayString(), p.getEndDayString()); //display t.teamName, etc..
        }

    @Override
    public int compareTo(Project another) {
        return this.projectName.compareTo(another.projectName);
    }

	public void assignTeam(Team t) {
        assignedTeam = t;
        for(int i=0; i<t.getTeamMembers().size(); i++){
            if(!t.getTeamMembers().get(i).getName().equals(t.getLeaderName())){
                assignedTeamMembersHistory.add(t.getTeamMembers().get(i));
            }
        }
    }
    
	public void setStartEndDays(Day startDay2, Day endDay2) {
        startDay = startDay2;
        endDay = endDay2;
    }
    
    public void removeTeam(){
        assignedTeam = null;
        assignedTeamMembersHistory.clear();
    }

	public void resetStartEndDays() {
        startDay = null;
        endDay = null;
    }
    
    public String getStartDayString(){
        if(startDay!=null){
            return startDay.toString();
        }
        return "";
    }

    public String getEndDayString(){
        if(endDay!=null){
            return endDay.toString();
        }
        return "";
    }

    public boolean hasTeam(){
        if(assignedTeam!=null){
            return true;
        }
        return false;
    }
    
    public void showWorkerDetails(){
        ArrayList<String> employeeRecordsOutput = new ArrayList<>();
        System.out.println("Est manpower : " + estManpower + " man-days");
        System.out.println("Team         : " + assignedTeam.getTeamName() + " (Leader is " + assignedTeam.getLeaderName() + ")");
        System.out.println("Work period  : " + startDay.toString() + " to " + endDay.toString());
        System.out.println("\nMembers: ");
        for(int i=0; i<assignedTeamMembersHistory.size(); i++){
            if(!assignedTeamMembersHistory.get(i).getProjectEndDay(this, assignedTeam).isEarlier(startDay)){
                employeeRecordsOutput.add("  " + assignedTeamMembersHistory.get(i).getName() + " (" + assignedTeamMembersHistory.get(i).projectStartDate(this, assignedTeam) + " to " + assignedTeamMembersHistory.get(i).projectEndDate(this, assignedTeam) + ")");
            }
        }
        Collections.sort(employeeRecordsOutput);
        for(String s: employeeRecordsOutput){
            System.out.println(s);
        }
    }

    public void addAssignedTeamMembersHistory(Employee e){
        assignedTeamMembersHistory.add(e);
    }

    public Day getProjectTakenDate(){
        return startDay;
    }

    public Day getProjectFinishedDate(){
        return endDay;
    }
}
