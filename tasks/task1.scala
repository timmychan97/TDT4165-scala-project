object Task1 extends App {
    // Task 1a
    val integerArray: Array[Int] = (for (i <- 1 to 50) yield i).toArray

    // Task 1b
    def sumForLoop(array: Array[Int]): Int = {
        var sum: Int = 0
        for (i <- array) {
            sum += i
        }
        sum
    }

    // Task 1c
    def sumRecursion(array: Array[Int]): Int = {
        if (array.length > 0) {
            array.head + sumRecursion(array.tail)
        } else {
            0
        }
    }

    // Task 1d
    def fib(n: BigInt): BigInt = {
        if (n > 1) {
            fib(n - 1) + fib(n - 2)
        } else {
            n
        }
    }

    // BigInt is represented by an array of integers. 
    // While Int only uses 32 bits for representation.
    // BigInt allows us to work with numbers bigger than what we can represent with 32 bits / 64 bits (Long).

    println("Sum of integer array (1 to 50) using for loop: " + sumForLoop(integerArray))
    println("Sum of integer array (1 to 50) using recursion: " + sumRecursion(integerArray))
    println("The 30th fibonacci number: " + fib(30))
}
