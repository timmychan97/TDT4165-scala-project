object task2 {
    // Task 2a
    def thread(fun: => Unit): Thread = new Thread(() => fun)

    def main(args: Array[String]): Unit = {
        // Task 2b
        var counter: Int = 0

        def increaseCounter(): Unit = counter += 1

        def printCounter(): Unit = println(counter)

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
        // 
        // A race condition for example occurs when thread A reads a variable X and then 
        // its execution is paused. Another thread B reads the same value and is paused.
        // The end value of X is then dependent on which thread continues its executiton first.
        // If thread A is continued first, mutates X, its value will be overwritten by thread B's following mutation.
        // An example of this could be two accounts depositing money to an account.

        // Task 2d

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

        lazy val getTheAnswer = () => 42
        this.synchronized {
            val t = thread(getTheAnswer())
            t.start()
            t.join()
        }

        // The main thread calls synchronized on the enclosing object,
        // starts a new thread, and waits for its termination.
        // The new thread attempts to initialize the lazy value getTheAnswer
        // but cannot acquire the monitor until the main thread releases it.
    }
}