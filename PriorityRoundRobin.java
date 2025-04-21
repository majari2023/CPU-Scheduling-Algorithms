//********************************************************************
//
//  Author:        Mauricio Rivas
//
//  Program #:     2
//
//  File Name:     PriorityRoundRobin.java
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
//  Description:   Implements the Priority with Round Robin (PRI-RR) 
//                 CPU scheduling algorithm. This algorithm first 
//                 schedules tasks based on priority and then applies 
//                 Round Robin scheduling within each priority level.
//
//********************************************************************

import java.util.*;

class PriorityRoundRobin implements Algorithm {
    private List<Task> queue;
    private static final int TIME_QUANTUM = 5; // Time quantum for round robin execution
    //***************************************************************
    //
    //  Method:       PriorityRoundRobin (Constructor)
    // 
    //  Description:  Initializes the Priority with Round Robin scheduler 
    //                with a queue of tasks.
    //
    //  Parameters:   List<Task> queue - List of tasks to be scheduled
    //
    //  Returns:      N/A
    //
    //**************************************************************
    public PriorityRoundRobin(List<Task> queue) {
        this.queue = new ArrayList<>(queue);
    }
    //***************************************************************
    //
    //  Method:       schedule
    // 
    //  Description:  Implements the PRI-RR scheduling by grouping tasks 
    //                by priority and executing them using Round Robin.
    //                Computes and displays the average turnaround and 
    //                waiting times.
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
        Map<Task, Integer> completionTime = new HashMap<>();
        Map<Task, Integer> waitingTimeMap = new HashMap<>();
        Map<Task, Integer> lastExecutionTime = new HashMap<>();

        
        TreeMap<Integer, Queue<Task>> priorityGroups = new TreeMap<>(Collections.reverseOrder());
        for (Task task : queue) {
            priorityGroups.putIfAbsent(task.getPriority(), new LinkedList<>());
            priorityGroups.get(task.getPriority()).add(task);
            remainingBurst.put(task, task.getBurst());
            waitingTimeMap.put(task, 0);
            lastExecutionTime.put(task, 0);
        }

        System.out.println("Priority with Round Robin Scheduling");

        
        for (Queue<Task> taskQueue : priorityGroups.values()) {
            LinkedList<Task> roundRobinQueue = new LinkedList<>(taskQueue); // Maintain RR order
            
            while (!roundRobinQueue.isEmpty()) {
                Task task = roundRobinQueue.poll(); // Fetch task in RR order
                int burst = remainingBurst.get(task);
                int timeSlice = Math.min(burst, TIME_QUANTUM);

                // Accumulate waiting time 
                if (lastExecutionTime.get(task) > 0) {
                    int waitingTime = currentTime - lastExecutionTime.get(task);
                    waitingTimeMap.put(task, waitingTimeMap.get(task) + waitingTime);
                }

                // Execute task
                lastExecutionTime.put(task, currentTime); // Update last execution time
                CPU.run(task, timeSlice);
                currentTime += timeSlice;
                burst -= timeSlice;

                if (burst == 0) {
                    completionTime.put(task, currentTime); // Store exact completion time
                    remainingBurst.remove(task);
                } else {
                    remainingBurst.put(task, burst);
                    roundRobinQueue.add(task); 
                }
            }
        }

        // Compute turnaround and waiting times
        for (Task task : queue) {
            int turnaroundTime = completionTime.get(task); // Since arrival time is 0
            int waitingTime = turnaroundTime - task.getBurst();
            totalTurnaroundTime += turnaroundTime;
            totalWaitingTime += waitingTime; // Use final accumulated waiting time
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
