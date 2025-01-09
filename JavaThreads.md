# **Life Cycle of a Thread in Java** 

The java.lang.Thread class contains a static State enum that defines its
potential states. During any given point in time, the thread can only be in one of these states:

- **NEW** – a newly created thread that hasn’t yet started the execution
- **RUNNABLE** – either running or ready for execution but waiting for resource allocation
- **BLOCKED** – waiting to acquire a monitor lock to enter or re-enter a
synchronized block/method
- **WAITING** – waiting for some other thread to perform a particular action without any time limit
- **TIMED_WAITING** – waiting for some other thread to perform a specific action for a specified period
- **TERMINATED** – has completed its execution