object Task1 extends App {
    import scala.collection._ 
    val numbers = mutable.ArrayBuffer[Int]()
        
    for (i <- 1 to 50) { 
        numbers += i
    }
    println(numbers) 

    def sumList(inputList: mutable.ArrayBuffer[Int]): Int = {
        var sum = 0 
        for (i <- inputList) {
            sum += i
        }
        sum
    }
    println(sumList(numbers))

    def sumRecursion(inputList: mutable.ArrayBuffer[Int]): Int = {
        if (inputList.length == 1) {
            inputList(0)
        } else {
            inputList(0) + sumRecursion(inputList.tail)
        }
    }
    println(sumRecursion(numbers))

    def fibonacci(n: BigInt): BigInt = {
        if (n == 0 || n == 1) {
            n
        } else {
            fibonacci(n-1) + fibonacci(n-2)
        }
    }
    println(fibonacci(6))
} 

// BigInt vs Int