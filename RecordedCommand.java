import java.util.*;

public abstract class RecordedCommand implements Command{
    /* 2 abstract methods for subclasses to implement */
    public abstract void undoMe();
    public abstract void redoMe();

    /* undolist and redolist; */
    private static ArrayList<RecordedCommand> undoList=new ArrayList<>();
    private static ArrayList<RecordedCommand> redoList=new ArrayList<>();
    /* adding command to the list */
    protected static void addUndoCommand(RecordedCommand cmd) {undoList.add(cmd);}
    protected static void addRedoCommand(RecordedCommand cmd) {redoList.add(cmd);}
    /* clear redo list*/
    protected static void clearRedoList() {redoList.clear();}
    /* carry out undo/redo */
    public static void undoOneCommand() {
        if(!undoList.isEmpty()){
            
                undoList.remove(undoList.size()-1).undoMe();
            
        }
        else
            System.out.println("Nothing to undo.");
        }
    public static void redoOneCommand() {
        if(!redoList.isEmpty()){
           
                redoList.remove(redoList.size()-1).redoMe();

        }
        else
            System.out.println("Nothing to redo.");
    }
}