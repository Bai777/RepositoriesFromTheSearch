package presenter.details

import view.details.IViewDetailsContract

internal class DetailsPresenter internal constructor(
    private val viewContract: IViewDetailsContract,
    private var count: Int = 0
): IPresenterDetailsContract {


    override fun setCounter(count: Int) {
        this.count = count
    }

    override fun onIncrement() {
        count++
        viewContract.setCount(count)
    }

    override fun onDecrement() {
        count--
        viewContract.setCount(count)
    }
}
