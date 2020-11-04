import exceptions._

class Account(val bank: Bank, initialBalance: Double) {
    val uid: BigInt = bank.uniqueAccountId()
    class Balance(var amount: Double) {}

    val balance = new Balance(initialBalance)

    def withdraw(amount: Double): Either[Unit, String] = this.synchronized {
        if (balance.amount - amount < 0) {
            Right("Error: Not enough money deposited.")
        } else if (amount < 0) {
            Right("Error: Tried to withdraw negative amount, which is illegal. Action thus not performed")
        } else {
            balance.amount -= amount
            Left(Unit)
        }
    }

    def deposit (amount: Double): Either[Unit, String] = this.synchronized {
        if(amount < 0) {
            Right("Error: Tried to deposit negative amount, which is illegal. Action thus not performed")
        } else {
            balance.amount += amount;
            Left(Unit)
        }
    }
    
    def getBalanceAmount: Double = this.synchronized {balance.amount}

    def transferTo(account: Account, amount: Double) = {
        bank.addTransactionToQueue (this, account, amount)
    }
}