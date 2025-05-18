package br.com.rodrigogurgel.statemachine.condition

import br.com.rodrigogurgel.statemachine.core.Condition

class NotCondition(
    private val condition: Condition
) : Condition {
    override fun isSatisfied() = !condition.isSatisfied()
}