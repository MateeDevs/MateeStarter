package konsistTest

import com.lemonappdev.konsist.api.Konsist

// Exclude the root iOS workspace
internal fun scopeFromProjectExcludingIos() =
    Konsist.scopeFromProject() - Konsist.scopeFromDirectory("ios")
