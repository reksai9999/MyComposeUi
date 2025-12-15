package reksai.compose.ui.ui.navigation

import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.navigation3.runtime.NavKey
import kotlin.system.exitProcess

object MyRoute {

    fun back() {
        backList().removeLastOrNull()
        if (backList().isEmpty()) {
            // 应用退出
            exitProcess(0)
        }
    }

    fun add(route: NavKey){
        backList().add(route)
    }

    fun backList (): MutableList<NavKey> {
        return backStackGlobal ?: SnapshotStateList()
    }
}