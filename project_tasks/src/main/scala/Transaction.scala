import scala.collection.mutable

object TransactionStatus extends Enumeration {
  val SUCCESS, PENDING, FAILED = Value
}

class TransactionQueue {
    var transactions = new mutable.Queue[Transaction]

    // Remove and return the first element from the queue
    def pop: Transaction = this.synchronized {
        transactions.dequeue
    }

    // Return whether the queue is empty
    def isEmpty: Boolean = this.synchronized {
        transactions.isEmpty
    }

    // Add new element to the back of the queue
    def push(t: Transaction): Unit = this.synchronized {
        transactions.enqueue(t)
    }

    // Return the first element from the queue without removing it
    def peek: Transaction = this.synchronized {
        transactions.head
    }

    // Return an iterator to allow you to iterate over the queue
    def iterator: Iterator[Transaction] = this.synchronized {
        transactions.iterator
    }
}

class Transaction(val transactionsQueue: TransactionQueue,
                  val processedTransactions: TransactionQueue,
                  val from: Account,
                  val to: Account,
                  val amount: Double,
                  val allowedAttempts: Int) extends Runnable {

  var status: TransactionStatus.Value = TransactionStatus.PENDING
  var attempt = 0

  override def run(): Unit = {
      def doTransaction(): Unit = this.synchronized {
          // Avoiding deadlock by using guid.
          if (from.uid < to.uid) {
              from.synchronized {to.synchronized {attemptTransaction()}}
          } else {
              to.synchronized {from.synchronized {attemptTransaction()}}
          }
      }

      def attemptTransaction(): Unit = {
          attempt += 1
          // Try to withdraw from sending account
          val withdrawResult = from.withdraw(amount)
          withdrawResult match {
              case Left(_) => {
                  // If successful deposit money and mark transaction as a success
                  to.deposit(amount)
                  status = TransactionStatus.SUCCESS
              }
              case Right(_) => {
                  // If transaction failed, either mark it as pending if enough attempts are left otherwise as failed
                  if (attempt < allowedAttempts) {
                      status = TransactionStatus.PENDING
                  } else {
                      status = TransactionStatus.FAILED
                  }
              }
          }
      }

      // If status is still pending try to perform the transaction
      if (status == TransactionStatus.PENDING) {
          doTransaction()
          Thread.sleep(50)
      }
    }
}
