object task2 extends App {
    // Task 2a
    def thread(fun: => Unit): Thread = new Thread(() => fun)

    // Task 2b
    // From the assignment
    private var counter: Int = 0
    def increaseCounter(): Unit = {
        counter += 1
    }

    def printCounter(): Unit = println(counter)

    private val t1 = thread(increaseCounter())
    private val t2 = thread(increaseCounter())
    private val t3 = thread(printCounter())

    t1.start()
    t2.start()
    t3.start()

    t1.join()
    t2.join()
    t3.join()

    // Print output from five runs: 2, 2, 2, 2, 1
    // This phenomenon is called a race condition
    // (the output of a concurrent program depends on
    // the execution schedule of the statements in the program).
    
    // An example of this could occur when withdrawing and depositing to the same bank account concurrently.
    // This could be a problem if the withdrawing thread reads the balance before the depositing thread reads
    // and updates the balance. When the withdrawing thread then updates the balance, it will mutate the balance 
    // based on what it read instead of the current balance.

    // Task 2c
    def threadSafeIncreaseCounter(): Unit = counter.synchronized {
        counter += 1
    }

    // Task 2d
    
    // A deadlock is a general situation in which two or more
    // executions wait for each other to complete an action
    // before proceeding with their own action.
    // A deadlock occurs when a set of two or more threads
    // acquire resources and then cyclically try to acquire
    // other thread's resources without releasing their own.

    // To prevent deadlocks, you can establish a total order
    // between resources when acquiring them. This ensures that
    // no set of threads cyclically wait on the resources they
    // previously acquired.

    private lazy val getTheAnswer = () => 42

    println("Entering deadlock")

    this.synchronized {
        val t = thread(getTheAnswer())
        t.start()
        t.join()
    }
    // The main thread calls synchronized on the enclosing object,
    // starts a new thread, and waits for its termination.
    // The new thread attempts to initialize the lazy value getTheAnswer,
    // but cannot acquire the monitor until the main thread releases it.
}