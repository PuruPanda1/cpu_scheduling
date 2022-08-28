import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class FCFS {
    public static void main(String[] args) {
        int counter = 0;
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter the number of processes");
        int numberOfProcess = Integer.parseInt(scan.next());
        ArrayList<Process> processes = new ArrayList<>();
        for (int i = 0; i < numberOfProcess; i++) {
            System.out.println("Enter Details for Process " + (i + 1));
            System.out.print("Process id: ");
            String pid = scan.next();
            System.out.print("Arrival Time: ");
            int at = Integer.parseInt(scan.next());
            System.out.print("Burst Time: ");
            int bt = Integer.parseInt(scan.next());
            processes.add(new Process(pid, at, bt));
        }
        Collections.sort(processes);
        for (Process currentProcess : processes) {
            while (counter < currentProcess.getArrivalTime()) {
                ++counter;
            }
            currentProcess.setRespTime(counter - currentProcess.getArrivalTime());
            currentProcess.setCompletionTime(counter + currentProcess.getBurstTime());
            counter = currentProcess.getCompletionTime();
            currentProcess.setTurnAroundTime(counter - currentProcess.getArrivalTime());
            currentProcess.setWaitingTime(currentProcess.getTurnAroundTime() - currentProcess.getBurstTime());
        }
//        print the table
        processes.sort(Comparator.comparing(Process::getProcessId));
        System.out.println("*****Process Table*****");
        System.out.print("PId\t");
        System.out.print("AT\t");
        System.out.print("BT\t");
        System.out.print("CT\t");
        System.out.print("TAT\t");
        System.out.print("WT\t");
        System.out.print("RT\t");
        for (Process currentProcess : processes) {
            System.out.println();
            System.out.print(currentProcess.getProcessId() + "\t");
            System.out.print(currentProcess.getArrivalTime() + "\t");
            System.out.print(currentProcess.getBurstTime() + "\t");
            System.out.print(currentProcess.getCompletionTime() + "\t");
            System.out.print(currentProcess.getTurnAroundTime() + "\t");
            System.out.print(currentProcess.getWaitingTime() + "\t");
            System.out.print(currentProcess.getRespTime() + "\t");
        }
    }
}
