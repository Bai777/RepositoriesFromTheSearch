package presenter.stub

import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import presenter.search.scheduler.SchedulerProvider

class ScheduleProviderStub: SchedulerProvider {

    override fun ui(): Scheduler {
        return Schedulers.trampoline()
    }

    override fun io(): Scheduler {
        return Schedulers.trampoline()
    }
}