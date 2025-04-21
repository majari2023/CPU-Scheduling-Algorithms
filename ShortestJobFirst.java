//********************************************************************
//
//  Author:        Mauricio Rivas
//
//  Program #:     2
//
//  File Name:     ShortestJobFirst.java
//
//  Course:        COSC 4302 Operating Systems
//
//  Due Date:      03/16/2025
//
//  Instructor:    Prof. Fred Kumi
//
//  Java Version:  23
//
//  Chapter:       5
//
//  Description:   Implements the Shortest Job First (SJF) CPU 
//                 scheduling algorithm. This algorithm selects the 
//                 task with the shortest burst time first, reducing 
//                 overall waiting time.
//
//********************************************************************

import java.util.*;

// Shortest Job First (SJF) Scheduling
class ShortestJobFirst implements Algorithm {
    private List<Task> queue;
    //***************************************************************
    //
    //  Method:       ShortestJobFirst (Constructor)
    // 
    //  Description:  Initializes the SJF scheduler with a queue 
    //                of tasks and sorts them in ascending order 
    //                based on burst time.
    //
    //  Parameters:   List<Task> queue - List of tasks to be scheduled
    //
    //  Returns:      N/A
    //
    //**************************************************************
    public ShortestJobFirst(List<Task> queue) {
        this.queue = new ArrayList<>(queue);
        this.queue.sort(Comparator.comparingInt(Task::getBurst));
    }
    //***************************************************************
    //
    //  Method:       schedule
    // 
    //  Description:  Implements the SJF scheduling by executing tasks 
    //                in order of shortest burst time first. Computes 
    //                and displays the average turnaround and waiting 
    //                times.
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

        System.out.println("Shortest Job First Scheduling");
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