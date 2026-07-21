package io.github.ltennstedt.irrational.core

import io.kotest.core.config.AbstractProjectConfig
import io.kotest.engine.concurrency.SpecExecutionMode
import io.kotest.engine.concurrency.TestExecutionMode

class ProjectConfig : AbstractProjectConfig() {
    override val specExecutionMode = SpecExecutionMode.LimitedConcurrency(2)
    override val testExecutionMode = TestExecutionMode.LimitedConcurrency(4)
}
