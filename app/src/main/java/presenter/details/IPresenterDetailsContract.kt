package presenter.details

import presenter.IPresenterContract

internal interface IPresenterDetailsContract: IPresenterContract {
    fun setCounter(count: Int)
    fun onIncrement()
    fun onDecrement()
}