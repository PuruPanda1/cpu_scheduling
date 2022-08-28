import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class Priority {
    public static void main(String[] args) {
        int counter = 0;
        AtomicInteger burstTimeSum = new AtomicInteger();
        ArrayList<Process> processes = new ArrayList<>();
        ArrayList<Process> executedProcesses = new ArrayList<>();
        ArrayList<Process> completedProcesses = new ArrayList<>();
        Scanner scan = new Scanner(System.in);
        System.out.println("Higher the Priority higher the authority!");
        System.out.println("Enter the number of processes");
        int numberOfProcess = Integer.parseInt(scan.next());
        int[] burstTimeArray = new int[numberOfProcess];
        for (int i = 0; i < numberOfProcess; i++) {
            System.out.println("Enter Details for Process " + (i + 1));
            System.out.print("Process id: ");
            String pid = scan.next();
            System.out.print("Arrival Time: ");
            int at = Integer.parseInt(scan.next());
            System.out.print("Burst Time: ");
            int bt = Integer.parseInt(scan.next());
            burstTimeArray[i] = bt;
            System.out.print("Enter the priority: ");
            int priority = Integer.parseInt(scan.next());
            processes.add(new Process(pid, at, bt, priority));
        }
        Collections.sort(processes);

        processes.forEach(process -> burstTimeSum.addAndGet(process.getBurstTime()));
        executedProcesses.add(processes.get(0));
        int iterator = 1;

        while (executedProcesses.size() > 0) {
            while (counter < executedProcesses.get(0).getArrivalTime()) {
                ++counter;
            }

            while (iterator < processes.size()) {
                if (processes.get(iterator).getArrivalTime() > counter) {
                    break;
                }
                executedProcesses.add(processes.get(iterator));
                ++iterator;
            }

            executedProcesses.sort((p1, p2) -> p2.getPriority() - p1.getPriority());

            counter += 1;
            int burstTime = executedProcesses.get(0).getBurstTime() - 1;
            executedProcesses.get(0).setBurstTime(burstTime);
            if (burstTime <= 0) {
                completedProcesses.add(executedProcesses.get(0));
                Process completedProcess = completedProcesses.get(completedProcesses.size() - 1);
                completedProcess.setCompletionTime(counter);
                completedProcess.setTurnAroundTime(completedProcess.getCompletionTime() - completedProcess.getArrivalTime());
                executedProcesses.remove(0);
            }
        }

        completedProcesses.sort(Comparator.comparing(Process::getProcessId));
        processes.sort(Comparator.comparing(Process::getProcessId));

        for (int i = 0; i < processes.size(); i++) {
            completedProcesses.get(i).setBurstTime(burstTimeArray[i]);
            completedProcesses.get(i).setWaitingTime(completedProcesses.get(i).getTurnAroundTime() - completedProcesses.get(i).getBurstTime());
        }

        completedProcesses.sort(Comparator.comparing(Process::getProcessId));
        System.out.println("counter is " + counter);
        System.out.println("*****Process Table*****");
        System.out.print("PId\t");
        System.out.print("AT\t");
        System.out.print("BT\t");
        System.out.print("CT\t");
        System.out.print("TAT\t");
        System.out.print("WT\t");
        System.out.print("Priority\t");
        for (Process currentProcess : completedProcesses) {
            System.out.println();
            System.out.print(currentProcess.getProcessId() + "\t");
            System.out.print(currentProcess.getArrivalTime() + "\t");
            System.out.print(currentProcess.getBurstTime() + "\t");
            System.out.print(currentProcess.getCompletionTime() + "\t");
            System.out.print(currentProcess.getTurnAroundTime() + "\t");
            System.out.print(currentProcess.getWaitingTime() + "\t");
            System.out.print(currentProcess.getPriority() + "\t");
        }

    }
}
