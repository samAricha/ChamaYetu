package teka.android.chamaaapp.util

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import teka.android.chamaaapp.data.local.room.entities.MemberEntity
import teka.android.chamaaapp.domain.models.Task

fun today(): LocalDateTime {
    return Clock.System.now()
        .toLocalDateTime(TimeZone.currentSystemDefault())
}

fun taskCompletionPercentage(tasks: List<Task>): Int {
    val completedTasks = tasks.filter { it.completed }.size
    return if (completedTasks == 0) {
        0
    } else {
        (completedTasks.toFloat() / tasks.size.toFloat() * 100).toInt()
    }
}


fun contributionLevelImageName(
    percentageContributed: Float
): String {
    return when {
//        percentageContributed >= 90 -> "contrib4"
        percentageContributed >= 75 -> "contrib4"
        percentageContributed >= 50 -> "contrib3"
        percentageContributed >= 25 -> "contrib2"
        percentageContributed >= 0 -> "contrib1"
        else -> "defaultImage"
    }
}








fun taskCompleteMessage(
    tasks: List<Task>,
    members: Int
): String {
    val completedTasks = members
//    val completedTasks = tasks.filter { it.completed }.size
    return if (completedTasks == tasks.size && completedTasks != 0) {
        "Nice Work! All of your members have contributed."
    } else if (completedTasks == 0) {
        "None of your members have contributed"
    } else if (taskCompletionPercentage(tasks) >= 90) {
        "Almost there! You're almost done with your contributions"
    } else if (taskCompletionPercentage(tasks) >= 75) {
        "Three quarter!, Contributions almost complete"
    } else if (taskCompletionPercentage(tasks) >= 50) {
        "HalfWay through contributions"
    } else if (taskCompletionPercentage(tasks) >= 25) {
        "Almost halfway through contributions"
    } else if (taskCompletionPercentage(tasks) >= 10) {
        "A few of your members have contributed"
    } else {
        "$completedTasks members contributed"
    }
}
