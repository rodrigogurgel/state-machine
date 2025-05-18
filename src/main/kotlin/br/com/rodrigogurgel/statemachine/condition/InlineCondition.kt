package br.com.rodrigogurgel.statemachine.condition

import br.com.rodrigogurgel.statemachine.core.Condition

class InlineCondition(
    private val block: () -> Boolean,
): Condition {
    override fun isSatisfied() = block()
}