//********************************************************************
//
//Author:        Mauricio Rivas
//
//Program #:     2
//
//File Name:     FirstComeFirstServed.java
//
//Course:        COSC 4302 Operating Systems
//
//Due Date:      03/16/2025
//
//Instructor:    Prof. Fred Kumi
//
//Java Version:  23
//
//Chapter:       5
//
//Description:   Implements the First-Come, First-Served (FCFS) CPU 
//               scheduling algorithm. This algorithm schedules tasks 
//               in the order they arrive, without preemption.
//
//********************************************************************
import java.util.*;

// First-Come, First-Served (FCFS) Scheduling
class FirstComeFirstServed implements Algorithm {
    private List<Task> queue;
    //***************************************************************
    //
    //  Method:       FirstComeFirstServed (Constructor)
    // 
    //  Description:  Initializes the FCFS scheduler with a queue 
    //                of tasks.
    //
    //  Parameters:   List<Task> queue - List of tasks to be scheduled
    //
    //  Returns:      N/A
    //
    //**************************************************************
    public FirstComeFirstServed(List<Task> queue) {
        this.queue = new ArrayList<>(queue);
    }
    //***************************************************************
    //
    //  Method:       schedule
    // 
    //  Description:  Implements the FCFS scheduling by processing 
    //                each task sequentially in the order they arrive.
    //                Computes and displays the average turnaround 
    //                and waiting times.
    //
    //  Parameters:   None
    //
    //  Returns:      N/A
    //
    //**************************************************************
    @Override
    public void schedule() {
        int currentTime = 0;
        int totalTurnaroundTime = 0;
        int totalWaitingTime = 0;

        System.out.println("First-Come, First-Served Scheduling");
        for (Task task : queue) {
            int waitingTime = currentTime;
            int turnaroundTime = waitingTime + task.getBurst();
            totalWaitingTime += waitingTime;
            totalTurnaroundTime += turnaroundTime;
            
            CPU.run(task, task.getBurst());
            currentTime += task.getBurst();
        }

        System.out.println("Average Turnaround Time: " + (double) totalTurnaroundTime / queue.size());
        System.out.println("Average Waiting Time: " + (double) totalWaitingTime / queue.size());
    }
    //***************************************************************
    //
    //  Method:       pickNextTask
    // 
    //  Description:  Picks and returns the next task in the queue.
    //                If the queue is empty, returns null.
    //
    //  Parameters:   None
    //
    //  Returns:      Task - The next task in the queue
    //
    //**************************************************************
    @Override
    public Task pickNextTask() {
        return queue.isEmpty() ? null : queue.remove(0);
    }
}
