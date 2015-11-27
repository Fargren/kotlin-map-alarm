package com.mapalarm.usecases

import com.mapalarm.PresentablePosition

interface MovePresenter {
    fun showUserAt(position: PresentablePosition)
    open fun showPositionUnknown()
}
