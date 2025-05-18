package br.com.rodrigogurgel.statemachine.condition.extensions

import br.com.rodrigogurgel.statemachine.condition.AndCondition
import br.com.rodrigogurgel.statemachine.condition.NotCondition
import br.com.rodrigogurgel.statemachine.condition.OrCondition
import br.com.rodrigogurgel.statemachine.core.Condition

fun Condition.and(other: Condition): Condition = AndCondition(this, other)
fun Condition.or(other: Condition): Condition = OrCondition(this, other)
fun Condition.not(): Condition = NotCondition(this)