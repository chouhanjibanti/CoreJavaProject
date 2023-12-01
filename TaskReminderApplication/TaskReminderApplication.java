// TaskReminderSystem

package TaskReminderApplication;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class TaskReminderApplication{

  static class ReminderTask extends TimerTask{
    
    private String taskDes;

    public ReminderTask(String taskDes){
        this.taskDes = taskDes;
    }

    public void run(){  // run method
        System.out.println("Task Executed at : "+ new Date());
        System.out.println("Reminder Don't forget to  " +taskDes);
    }
  }

  public static void main(String[] args) {
    
     Scanner scan = new Scanner(System.in);
     Timer timer= new Timer();

     List<TimerTask> tasks = new ArrayList<>();

    
    while(true){
        System.out.println("***********************");
        System.out.println("  Online Task Reminder ");
        System.out.println("***********************");
        System.out.println();
        System.out.println("****Enter Task Name : ");
        System.out.println();
        String task = scan.nextLine();

        if(task.equalsIgnoreCase("exit")){
          break;
        }

        System.out.println("Enter the task date and time in this order ->> (yyyy-MM-dd HH:mm:ss) : ");
        String dueDateString = scan.nextLine();

        System.out.println("Is This A Repetitive Task ? (Yes/No)");
       
        boolean isRepetitive = scan.nextLine().equalsIgnoreCase("yes");

        try{
            Date dueDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dueDateString);

            if(isRepetitive){
                System.out.println("Enter Repetitive interval in minutes : ");
                long interval = Long.parseLong(scan.nextLine()) * 60 * 1000;

                TimerTask repetitiTask = new ReminderTask(task);
                tasks.add(repetitiTask);
                timer.scheduleAtFixedRate(repetitiTask, dueDate, interval);
            }
            else{
                TimerTask onTimerTask = new ReminderTask((task));
                tasks.add(onTimerTask);
                timer.schedule(onTimerTask ,dueDate);
            }
            System.out.println("Task schedule for : "+dueDate);
        }
        catch(Exception e){
           System.out.println("Invalid input.please enter the date in the specified format. ");
        }
     }


     System.out.println("Schedule tasks : ");
     for(TimerTask task : tasks){
        System.out.println(((ReminderTask) task) .taskDes);
     }

     timer.cancel();
     scan.close();

  }
  
}