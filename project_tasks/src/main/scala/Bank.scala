class Bank(val allowedAttempts: Integer = 3) {

    private val transactionsQueue: TransactionQueue = new TransactionQueue()
    private val processedTransactions: TransactionQueue = new TransactionQueue()

    private var uidCount: BigInt = 0

    // uid is used to avoid deadlocks
    def uniqueAccountId: BigInt = this.synchronized {
        uidCount += 1
        uidCount
    }

    def addTransactionToQueue(from: Account, to: Account, amount: Double): Unit = {
        val trans: Transaction = new Transaction(transactionsQueue, processedTransactions, from, to, amount, allowedAttempts)
        transactionsQueue.push(trans)
        new Thread(() => processTransactions()).start()
    }

    private def processTransactions(): Unit = {
        // Spawn new thread to process transaction
        new Thread(() => {
                // Get next transaction in the queue
                val trans: Transaction = transactionsQueue.pop
                trans.run()
                if (trans.status == TransactionStatus.PENDING) {
                    // Retry later
                    transactionsQueue.push(trans)
                    processTransactions()
                } else {
                    // Transaction is processed
                    processedTransactions.push(trans)
                }
            }).start()
    }

    def addAccount(initialBalance: Double): Account = {
        new Account(this, initialBalance)
    }

    def getProcessedTransactionsAsList: List[Transaction] = {
        processedTransactions.iterator.toList
    }
}
