object Task2 extends App {
    // Task 2a
    def thread(fun: => Unit): Thread = {
        new Thread(() => fun)
    }

    // Task 2b
    private var counter: Int = 0
    def increaseCounter(): Unit = {
        counter += 1
    }

    def printCounter(): Unit = {
        println(counter)
    }

    val t1 = thread(increaseCounter())
    val t2 = thread(increaseCounter())
    val t3 = thread(printCounter())
    
    t1.start()
    t2.start()
    t3.start()

    // Task 2c
    def threadSafeIncreaseCounter(): Unit = {
        counter.synchronized {
            counter += 1
        }
    }

    // Print output from five runs: 2, 2, 2, 2, 1
    // This phenomenon is called a race condition
    // (the output of a concurrent program depends on
    // the execution schedule of the statements in the program). 
    // One example of a situation where it can be problematic:

    // Task 2d
    lazy val getTheAnswer = () => 42
    this.synchronized {
        val t = thread(getTheAnswer())
        t.join()
    }

    // A deadlock is a general situation in which two or more
    // executions wait for each other to complete an action
    // before proceeding with their own action.
    // A deadlock occurs when a set of two or more threads
    // acquire resources and then cyclically try to acqire 
    // other thread's resources without releasing their own.

    // To prevent deadlocks, you can establish a total order
    // between resources when acquiring them. This ensures that
    // no set of threads cyclically wait on the resources they 
    // previously acquired. 

    // The main thread calls synchronized on the enclosing object,
    // starts a new thread, and waits for its termination.
    // The new thread attempts to initialize the lazy value getTheAnswer 
    // but cannot acquire the monitor until the main thread releases it.
}