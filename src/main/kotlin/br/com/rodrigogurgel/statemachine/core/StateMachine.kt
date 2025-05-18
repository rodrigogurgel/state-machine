package br.com.rodrigogurgel.statemachine.core


class StateMachine private constructor(
    states: List<State>
) {

    constructor(initialState: State): this(listOf(initialState))

    var currentState: State = states.first()
        private set

    var states: MutableList<State> = states.toMutableList()
        private set

    init {
        if(states.isEmpty()) throw Exception("States must not be empty")

        currentState.enter()
    }

    fun changeState(newState: State) {
        currentState.exit()
        currentState = newState
        currentState.enter()
        states += newState
    }

    fun update() {
        currentState.update()?.let { transaction -> changeState(transaction.goToState) }
    }

    class Builder {
        private var states: List<State> = emptyList()

        fun withStates(states: List<State>): Builder = apply { this.states = states }
        fun build(): StateMachine = StateMachine(states)
    }
}