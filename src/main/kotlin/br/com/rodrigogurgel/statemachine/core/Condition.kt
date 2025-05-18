package br.com.rodrigogurgel.statemachine.core

interface Condition {
    fun isSatisfied(): Boolean
}