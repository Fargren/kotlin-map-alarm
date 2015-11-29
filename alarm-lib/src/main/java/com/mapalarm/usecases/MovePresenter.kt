package com.mapalarm.usecases

import com.mapalarm.datatypes.Position

interface MovePresenter {
    fun showUserAt(position: Position)
    open fun showPositionUnknown()
}
