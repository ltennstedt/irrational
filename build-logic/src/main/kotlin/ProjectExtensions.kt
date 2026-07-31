import org.gradle.api.Project
import org.gradle.api.provider.Provider

val Project.isCi: Provider<Boolean>
    get() = providers
        .environmentVariable("CI")
        .map { it.equals("true", ignoreCase = true) }
        .orElse(false)
val Project.isNotCi: Provider<Boolean>
    get() = isCi.map { !it }
