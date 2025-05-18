package br.com.rodrigogurgel.statemachine.condition

import br.com.rodrigogurgel.statemachine.core.Condition

class AndCondition(
    private val left: Condition,
    private val right: Condition,
) : Condition {
    override fun isSatisfied() = left.isSatisfied() && right.isSatisfied()
}