import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class SJF {
    public static void main(String[] args) {
        int counter = 0;
        int sumOfBurstTime = 0;
        Scanner scan = new Scanner(System.in);
        ArrayList<Process> processes = new ArrayList<>();
        ArrayList<Process> executedProcess = new ArrayList<>();
        ArrayList<Process> availableProcess = new ArrayList<>();
        System.out.println("Enter the number of process");
        int numberOfProcess = Integer.parseInt(scan.next());
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

        for (Process p : processes) {
            sumOfBurstTime += p.getBurstTime();
        }

        Collections.sort(processes);

        while (counter <= sumOfBurstTime) {

            for (Process p : processes) {
                if (p.getArrivalTime() <= counter) {
                    System.out.print("Selected p is "+p.getProcessId());
                    availableProcess.add(p);
                }
            }

            if (availableProcess.size() == 0) {
                ++counter;
                continue;
            }

            availableProcess.sort(Comparator.comparingInt(Process::getBurstTime));

            processes.remove(availableProcess.get(0));
            availableProcess.get(0).setRespTime(counter - availableProcess.get(0).getArrivalTime());
            availableProcess.get(0).setCompletionTime(counter + availableProcess.get(0).getBurstTime());
            counter = availableProcess.get(0).getCompletionTime();
            availableProcess.get(0).setTurnAroundTime(counter - availableProcess.get(0).getArrivalTime());
            availableProcess.get(0).setWaitingTime(availableProcess.get(0).getTurnAroundTime() - availableProcess.get(0).getBurstTime());
            executedProcess.add(availableProcess.get(0));
            availableProcess.removeAll(availableProcess);
        }
//        print the table
        executedProcess.sort(Comparator.comparing(Process::getProcessId));
        System.out.println("*****Process Table*****");
        System.out.print("PId\t");
        System.out.print("AT\t");
        System.out.print("BT\t");
        System.out.print("CT\t");
        System.out.print("TAT\t");
        System.out.print("WT\t");
        System.out.print("RT\t");
        for (Process currentProcess : executedProcess) {
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
