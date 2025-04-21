//********************************************************************
//
//  Author:        Mauricio Rivas
//
//  Program #:     2
//
//  File Name:     RoundRobin.java
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
//  Description:   Implements the Round Robin (RR) CPU scheduling 
//                 algorithm. This algorithm processes tasks in a 
//                 cyclic order, allocating a fixed time quantum 
//                 before switching to the next task.
//
//********************************************************************

import java.util.*;

class RoundRobin implements Algorithm {
    private List<Task> queue;
    private static final int TIME_QUANTUM = 5;
    //***************************************************************
    //
    //  Method:       RoundRobin (Constructor)
    // 
    //  Description:  Initializes the Round Robin scheduler with a queue 
    //                of tasks.
    //
    //  Parameters:   List<Task> queue - List of tasks to be scheduled
    //
    //  Returns:      N/A
    //
    //**************************************************************
    public RoundRobin(List<Task> queue) {
        this.queue = new ArrayList<>(queue);
    }
    //***************************************************************
    //
    //  Method:       schedule
    // 
    //  Description:  Implements the Round Robin scheduling by processing 
    //                each task for a fixed time quantum before switching 
    //                to the next. Computes and displays the average 
    //                turnaround and waiting times.
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
        Map<Task, Integer> remainingBurst = new HashMap<>();
        Queue<Task> taskQueue = new LinkedList<>(queue);
        Map<Task, Integer> completionTime = new HashMap<>();
        Map<Task, Integer> waitingTimeMap = new HashMap<>();
        Map<Task, Integer> lastExecutionTime = new HashMap<>();

        // Initialize burst times and waiting times
        for (Task task : queue) {
            remainingBurst.put(task, task.getBurst());
            waitingTimeMap.put(task, 0);
            lastExecutionTime.put(task, 0);
        }

        System.out.println("Round Robin Scheduling");

        while (!taskQueue.isEmpty()) {
            Task task = taskQueue.poll();
            int burst = remainingBurst.get(task);
            int timeSlice = Math.min(burst, TIME_QUANTUM);

            
            if (lastExecutionTime.get(task) > 0) {
                int waitingTime = currentTime - lastExecutionTime.get(task);
                waitingTimeMap.put(task, waitingTimeMap.get(task) + waitingTime);
                totalWaitingTime += waitingTime;
            }

            // Execute task
            System.out.println("Executing " + task.getName() + " for " + timeSlice + " ms | Remaining Burst: " + (burst - timeSlice));
            CPU.run(task, timeSlice);
            currentTime += timeSlice;
            burst -= timeSlice;
            lastExecutionTime.put(task, currentTime); // Update last execution time

            // Store the reduced burst
            remainingBurst.put(task, burst);

            if (burst == 0) {
                completionTime.put(task, currentTime); // Store exact completion time
                remainingBurst.remove(task); // Remove finished task
            } else {
                taskQueue.add(task); // Add unfinished task back to queue
            }
        }

        // Calculate turnaround and waiting times
        for (Task task : queue) {
            int turnaroundTime = completionTime.get(task); // Turnaround time = completion time - arrival (0)
            int waitingTime = turnaroundTime - task.getBurst();
            totalTurnaroundTime += turnaroundTime;
            totalWaitingTime += waitingTime;
        }

        // Display final results
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