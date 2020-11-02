object task1 extends App {
    // Task 1a
    val integerArray: Array[Int] = (for (i <- 1 to 50) yield i).toArray

    // Task 1b
    def sumWithFor(array: Array[Int]): Int = {
        var sum:Int = 0
        for (i <- array) {
            sum += i
        }
        return sum
    }

    // Task 1c
    def sumWithRecursion(array: Array[Int]): Int = {
        if (array.length > 0) {
            return array.head + sumWithRecursion(array.tail)
        } else {
            return 0
        }
    }

    // Task 1d
    // BigInt can store arbitrarily large integer values, whereas Int is
    // limited to a 32-bit signed integer.
    def fib(n: BigInt): BigInt = {
        if (n > 1) {
            return fib(n - 1) + fib(n - 2)
        } else {
            return n
        }
    }

    println("Sum of integer array (1 to 50) using for loop: " + sumWithFor(integerArray))
    println("Sum of integer array (1 to 50) using recursion: " + sumWithRecursion(integerArray))
    println("The 30th fibonacci number: " + fib(30))
}