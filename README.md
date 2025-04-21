# CPU Scheduling Simulator

This Java-based simulator allows users to test and compare various CPU scheduling algorithms including:

- **FCFS** – First-Come, First-Served  
- **SJF** – Shortest Job First  
- **PRI** – Priority Scheduling  
- **RR** – Round Robin  
- **PRI-RR** – Priority with Round Robin

## 📦 Project Overview

The simulator models CPU scheduling by accepting a list of tasks and applying one of the implemented algorithms to compute:

- Average Turnaround Time
- Average Waiting Time

The simulation outputs results to the console, showing each task's execution as well as performance metrics.

## 🧠 Implemented Algorithms

| Algorithm | Description |
|----------|-------------|
| **FCFS** | Schedules tasks in the order they arrive without preemption. |
| **SJF** | Selects the task with the shortest CPU burst next. |
| **PRI** | Schedules tasks based on static priority values (higher number = higher priority). |
| **RR** | Uses a fixed time quantum to cycle through tasks. |
| **PRI-RR** | Groups tasks by priority, then schedules each group using Round Robin. |

## 📂 File Structure

| File | Description |
|------|-------------|
| `Program2.java` | Main driver that loads tasks, chooses algorithm, and runs the simulation. |
| `Task.java` | Represents a task with name, ID, priority, and burst time. |
| `CPU.java` | Simulates the CPU by displaying task execution. |
| `Algorithm.java` | Interface for all scheduling algorithms. |
| `FirstComeFirstServed.java` | Implements FCFS scheduling. |
| `ShortestJobFirst.java` | Implements SJF scheduling. |
| `Priority.java` | Implements static priority scheduling. |
| `RoundRobin.java` | Implements Round Robin with time slicing. |
| `PriorityRoundRobin.java` | Implements Priority with Round Robin. |

## 🚀 How to Compile & Run

1. **Compile all files:**
```bash
javac *.java

Each line represents a task in the format:
TaskName, Priority, BurstTime
Example:
T1, 3, 20
T2, 1, 15
T3, 2, 10
