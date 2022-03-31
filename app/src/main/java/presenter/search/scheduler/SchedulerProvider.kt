package presenter.search.scheduler

import io.reactivex.Scheduler

internal interface SchedulerProvider {
    fun ui(): Scheduler
    fun io(): Scheduler
}