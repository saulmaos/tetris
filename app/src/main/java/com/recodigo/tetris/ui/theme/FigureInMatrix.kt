package com.recodigo.tetris.ui.theme

import com.recodigo.tetris.FigureInMatrixOperations
import com.recodigo.tetris.FigureManager
import com.recodigo.tetris.LineInMatrix

class TFigureInMatrix : FigureInMatrixOperations {
    companion object {
        private const val COLUMNS = 3
        private const val ROWS = 2
        private const val INITIAL_POS_X_IN_MATRIX = 3
        private const val INITIAL_POS_Y_IN_MATRIX = 0
        private const val INITIAL_ROTATION_DEGREES = 0f
        private const val ROTATION_DEGREES = 90f
        private const val MIN_POS_X_Y_IN_MATRIX = 0
    }

    private val tFigure: Array<IntArray> =
        arrayOf(
            intArrayOf(0, 1, 0),
            intArrayOf(1, 1, 1)
        )
    private var isInHorizontalMode = true
    private var posYInMatrix = INITIAL_POS_Y_IN_MATRIX
    private var posXInMatrix = INITIAL_POS_X_IN_MATRIX
    private var heightInBlocks = ROWS
    private var widthInBlocks = COLUMNS
    private var rotation = INITIAL_ROTATION_DEGREES
    private var posYInGrid = 0f
    private var posXInGrid = 0f

    init {
//        calculateGridPositions()
    }

    override fun addFigureInMatrix(matrix: Array<IntArray>): Array<IntArray> {
        TODO("Not yet implemented")
    }

    override fun increasePosYIfPossible(matrix: Array<IntArray>): Boolean {
        TODO("Not yet implemented")
    }

    override fun posYInGrid(): Float {
        TODO("Not yet implemented")
    }

    override fun posXInGrid(): Float {
        TODO("Not yet implemented")
    }

    override fun rotateIfPossible(matrix: Array<IntArray>): Boolean {
        TODO("Not yet implemented")
    }

    override fun rotationDegrees(): Float {
        TODO("Not yet implemented")
    }

    override fun moveInXIfPossible(matrix: Array<IntArray>, moveToRight: Boolean): Boolean {
        TODO("Not yet implemented")
    }

}