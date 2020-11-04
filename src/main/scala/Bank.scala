import java.lang.RuntimeException

class Bank(val allowedAttempts: Integer = 3) {

    private val transactionsQueue: TransactionQueue = new TransactionQueue()
    private val processedTransactions: TransactionQueue = new TransactionQueue()

    def addTransactionToQueue(from: Account, to: Account, amount: Double): Unit = {
        val trans: Transaction = new Transaction(transactionsQueue, processedTransactions, from, to, amount, allowedAttempts)
        transactionsQueue.push(trans)
        new Thread() {
            override def run() {
                processTransactions
            }
        }.start()
    }
    private def processTransactions: Unit = {
        val trans: Transaction = transactionsQueue.pop
        new Thread(){
            override def run(): Unit = {
                if (trans.status == TransactionStatus.PENDING){
                    transactionsQueue.push(trans)
                    processTransactions
                } else{
                    processedTransactions.push(trans)
                }
            }
        }.start()
    }

    def addAccount(initialBalance: Double): Account = {
        new Account(this, initialBalance)
    }

    def getProcessedTransactionsAsList: List[Transaction] = {
        processedTransactions.iterator.toList
    }

}
