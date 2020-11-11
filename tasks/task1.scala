object task1 extends App {
    // Task 1a
    val integerArray: Array[Int] = (for (i <- 1 to 50) yield i).toArray

    println("Integers from 1 to 50: " + integerArray.mkString(" "))

    // Task 1b
    def sumForLoop(array: Array[Int]): Int = {
        var sum: Int = 0
        for (i <- array) {
            sum += i
        }
        sum
    }

    println("Sum of integer array (1 to 50) using for loop: " + sumForLoop(integerArray))

    // Task 1c
    def sumRecursion(array: Array[Int]): Int = {
        if (array.length > 0) {
            array.head + sumRecursion(array.tail)
        } else {
            0
        }
    }

    println("Sum of integer array (1 to 50) using recursion: " + sumRecursion(integerArray))

    // Task 1d
    def fib(n: Int): BigInt = {
        if (n > 1) {
            fib(n - 1) + fib(n - 2)
        } else {
            n
        }
    }

    // BigInt is represented by an array of integers, while Int only uses 32 bits for representation.
    // BigInt allows us to work with arbitrarily large numbers; however, we are in practice limited by memory constraints.

    // Int is used as parameter type since we are only interested in using relatively small numbers as input for
    // fibonacci. The function will fail due to performance and memory issues if we try to calculate fib for large n
    // way before we reach the limit of Int (2147483647).

    println("The 30th fibonacci number: " + fib(30))
}
