package view.details

import view.IViewContract

internal interface IViewDetailsContract: IViewContract {
    fun setCount(count: Int)
}