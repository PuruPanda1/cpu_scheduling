import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class RR {
    public static void main(String[] args) {
        int counter = 0;
        AtomicInteger burstTimeSum = new AtomicInteger();
        ArrayList<Process> processes = new ArrayList<>();
        ArrayList<Process> executedProcesses = new ArrayList<>();
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter the quantum time");
        int qTime = Integer.parseInt(scan.next());
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
            processes.add(new Process(pid, at, bt));
        }
        Collections.sort(processes);

        processes.forEach(process -> burstTimeSum.addAndGet(process.getBurstTime()));

        Queue<Process> waitingQueue = new LinkedList<>();
        waitingQueue.add(processes.get(0));
        int iterator = 1;

        while (waitingQueue.size() > 0) {
            while (counter < waitingQueue.peek().getArrivalTime()) {
                ++counter;
            }
            int burstTime = waitingQueue.peek().getBurstTime() - qTime;
            counter += qTime;
            waitingQueue.peek().setBurstTime(burstTime);

            if (burstTime > 0) {
                Process lastElement = waitingQueue.poll();
                while (iterator < processes.size()) {
                    if (processes.get(iterator).getArrivalTime() > counter) {
                        break;
                    }
                    waitingQueue.add(processes.get(iterator));
                    ++iterator;
                }
                waitingQueue.add(lastElement);
            } else {
                counter += burstTime;
                waitingQueue.peek().setCompletionTime(counter);
                waitingQueue.peek().setTurnAroundTime(waitingQueue.peek().getCompletionTime() - waitingQueue.peek().getArrivalTime());
                executedProcesses.add(waitingQueue.poll());
                if(waitingQueue.size() == 0 && iterator!=processes.size()-1){
                    while (iterator < processes.size()) {
                        if (processes.get(iterator).getArrivalTime() > counter) {
                            break;
                        }
                        waitingQueue.add(processes.get(iterator));
                        ++iterator;
                    }
                }
            }
        }
        executedProcesses.sort(Comparator.comparing(Process::getProcessId));
        processes.sort(Comparator.comparing(Process::getProcessId));

        for (int i = 0; i < processes.size(); i++) {
            executedProcesses.get(i).setBurstTime(burstTimeArray[i]);
            executedProcesses.get(i).setWaitingTime(executedProcesses.get(i).getTurnAroundTime() - executedProcesses.get(i).getBurstTime());
            executedProcesses.get(i).setRespTime(executedProcesses.get(i).getWaitingTime() - executedProcesses.get(i).getArrivalTime());
        }

        executedProcesses.sort(Comparator.comparing(Process::getProcessId));
        System.out.println("counter is " + counter);
        System.out.println("*****Process Table*****");
        System.out.print("PId\t");
        System.out.print("AT\t");
        System.out.print("BT\t");
        System.out.print("CT\t");
        System.out.print("TAT\t");
        System.out.print("WT\t");
        System.out.print("RT\t");
        for (Process currentProcess : executedProcesses) {
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
