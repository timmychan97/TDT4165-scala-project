object task2 extends App {
    // Task 2a
    def thread(fun: => Unit): Thread = {
        return new Thread {override def run() = fun}
    }

    // Task 2b
    private var counter: Int = 0
    def increaseCounter(): Unit = {
        counter += 1
    }

    def printCounter(): Unit = {
        println(counter)
    }

    thread(increaseCounter()).start
    thread(increaseCounter()).start
    thread(printCounter()).start

    // Task 2c

    def threadSafeIncreaseCounter(): Unit = this.synchronized {
        counter += 1
    }

    thread(threadSafeIncreaseCounter()).start
    thread(threadSafeIncreaseCounter()).start
    thread(printCounter()).start

    // Task 2d
    object A {
        lazy val value: Int = B.value
    }
    
    object B {
        lazy val value: Int = A.value
    }

}