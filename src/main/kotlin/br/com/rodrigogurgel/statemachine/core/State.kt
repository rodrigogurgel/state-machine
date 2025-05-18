package br.com.rodrigogurgel.statemachine.core

import java.time.Instant

abstract class State(
    protected open var startedAt: Instant?,
    protected open var completedAt: Instant?,
) {
    protected open val transactions: List<Transaction> = emptyList()

    protected open fun onEnter() {}
    protected open fun onUpdate() {}
    protected open fun onExit() {}


    fun enter() {
        if (startedAt == null) {
            startedAt = Instant.now()
        }
        onEnter()
    }

    fun update(): Transaction? {
        checkTransactions()?.let { transaction -> return transaction }
        onUpdate()
        return checkTransactions()
    }

    fun exit() {
        if (completedAt == null) {
            completedAt = Instant.now()
        }
        onExit()
    }

    private fun checkTransactions(): Transaction? {
        for (transaction in transactions) {
            if (transaction.isSatisfied().not()) continue
            else return transaction
        }
        return null
    }
}