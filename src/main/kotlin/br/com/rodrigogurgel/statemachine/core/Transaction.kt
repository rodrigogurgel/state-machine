package br.com.rodrigogurgel.statemachine.core

class Transaction private constructor(builder: Builder) {
    var condition: Condition = builder.condition!!
    var goToState: State = builder.goToState!!

    fun isSatisfied(): Boolean = condition.isSatisfied()

    class Builder() {
        var condition: Condition? = null
            private set
        var goToState: State? = null
            private set

        fun toState(state: State) = apply {
            this.goToState = state
        }

        fun withCondition(condition: Condition) = apply {
            this.condition = condition
        }

        fun build(): Transaction {
            if (condition == null) throw IllegalArgumentException("Condition cannot be null")
            if (goToState == null) throw IllegalArgumentException("Go to State cannot be null")
            return Transaction(this)
        }
    }
}