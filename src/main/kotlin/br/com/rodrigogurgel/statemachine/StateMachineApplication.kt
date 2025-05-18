package br.com.rodrigogurgel.statemachine

import br.com.rodrigogurgel.statemachine.condition.InlineCondition
import br.com.rodrigogurgel.statemachine.condition.extensions.not
import br.com.rodrigogurgel.statemachine.core.State
import br.com.rodrigogurgel.statemachine.core.StateMachine
import br.com.rodrigogurgel.statemachine.core.Transaction
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.time.Instant
import java.util.UUID

@SpringBootApplication
open class StateMachineApplication

fun main(args: Array<String>) {
    runApplication<StateMachineApplication>(*args)
}

@RestController
class Controller {
    companion object {
        lateinit var stateMachine: StateMachine
    }

    @GetMapping("/start")
    suspend fun start() {
        val todo = Todo(
            id = UUID.randomUUID(),
            title = "Hello World",
            done = false
        )
        stateMachine = StateMachine.Builder().withStates(
            listOf(
                TodoState(todo, Instant.now().minusMillis(100), Instant.now().minusMillis(99)),
                InProgressState(todo, Instant.now().minusMillis(100), Instant.now().minusMillis(99)),
                TodoState(todo, Instant.now().minusMillis(100), Instant.now().minusMillis(99)),
                InProgressState(todo, Instant.now().minusMillis(100), Instant.now().minusMillis(99)),
                DoneState(todo, Instant.now().minusMillis(100), Instant.now().minusMillis(99)),
            )
        ).build()
    }

    @GetMapping("/update")
    suspend fun update() {
        stateMachine.update()
    }

    @GetMapping("/states")
    fun states(): List<String> {
        return stateMachine.states.map { it.toString() }
    }
}

data class TodoState(
    val todo: Todo,
    override var startedAt: Instant? = null,
    override var completedAt: Instant? = null,
) : State(startedAt, completedAt) {
    override val transactions: List<Transaction>
        get() = listOf(
            Transaction.Builder()
                .toState(InProgressState(todo))
                .withCondition(InlineCondition {
                    todo.done
                }.not())
                .build(),
            Transaction.Builder()
                .toState(DoneState(todo))
                .withCondition(InlineCondition {
                    todo.done
                })
                .build(),
        )
}

data class InProgressState(
    val todo: Todo,
    override var startedAt: Instant? = null,
    override var completedAt: Instant? = null,
) : State(startedAt, completedAt) {
    override fun onUpdate() {
        todo.done = true
    }

    override val transactions: List<Transaction>
        get() = listOf(
            Transaction.Builder()
                .toState(DoneState(todo))
                .withCondition(InlineCondition {
                    todo.done
                })
                .build(),
        )
}

data class DoneState(
    val todo: Todo,
    override var startedAt: Instant? = null,
    override var completedAt: Instant? = null,
) : State(startedAt, completedAt) {
    override fun onEnter() {
        completedAt = Instant.now()
    }
}

data class Todo(
    val id: UUID,
    val title: String,
    var done: Boolean
)