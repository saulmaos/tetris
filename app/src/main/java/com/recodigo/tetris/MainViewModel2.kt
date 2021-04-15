package com.recodigo.tetris

import androidx.compose.runtime.*
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.recodigo.tetris.ui.theme.Blue
import com.recodigo.tetris.ui.theme.Green
import com.recodigo.tetris.ui.theme.Yellow
import kotlinx.coroutines.delay

class MainViewModel2 : ViewModel() {

    var gridPoints by mutableStateOf<List<GridPoint>>(emptyList())
        private set

    private var shouldMove = true
    private var maxHeight = 0f
    private var maxWidth = 0f
    private var cellSize = 1f
    lateinit var figureManager: FigureManager
        private set

    fun init(height1: Float, height2: Float, cellSize: Float, maxWidth: Float) {
        val scale = height1 / height2
        maxHeight = height1
        this.maxWidth = maxWidth
        this.cellSize = cellSize * scale
        if (!this::figureManager.isInitialized)
            figureManager = FigureManager(this.cellSize, maxHeight, maxWidth)
    }

    fun createGrid() {
        val gridPoints = mutableListOf<GridPoint>()
        for (i in 1..20) {
            val posY = maxHeight - (cellSize * i)
            for (j in 1..9) {
                val posX = cellSize * j
                gridPoints.add(GridPoint(posX, posY))
            }
        }
        figureManager.yOffset = gridPoints.last().posY
        this.gridPoints = gridPoints
    }

    fun rotate() {
        figureManager.rotate()
    }

    fun moveInX(moveToRight: Boolean) {
        figureManager.moveInX(moveToRight)
    }

    suspend fun start() {
        // send new blocks
        for (i in 1..3) {
            figureManager.createRandomFigure(i)
            figureManager.updatePosY()
        }
    }
}

class LineInMatrix : FigureInMatrixOperations {

    companion object {
        private const val COLUMNS = 4
        private const val ROWS = 1
        private const val INITIAL_POS_X_IN_MATRIX = 3
        private const val INITIAL_POS_Y_IN_MATRIX = 0
        private const val INITIAL_ROTATION_DEGREES = 0f
        private const val ROTATION_DEGREES = 90f
        private const val MIN_POS_X_Y_IN_MATRIX = 0
    }

    private val line: IntArray = IntArray(COLUMNS) { 1 }
    private var isInHorizontalMode = true
    private val numbers = arrayOf(1, 2, 3, 0)

    private var posYInMatrix = INITIAL_POS_Y_IN_MATRIX
    private var posXInMatrix = INITIAL_POS_X_IN_MATRIX
    private var heightInBlocks = ROWS
    private var widthInBlocks = COLUMNS
    private var rotation = INITIAL_ROTATION_DEGREES
    private var posYInGrid = 0f
    private var posXInGrid = 0f

    init {
        calculateGridPositions()
    }

    private fun isGoingToCrashInY(matrix: Array<IntArray>): Boolean {
        val nextPosYInGrid = posYInMatrix + heightInBlocks
        if (nextPosYInGrid >= FigureManager.MATRIX_ROWS) return true
        for (i in posXInMatrix until posXInMatrix + widthInBlocks) {
            if (matrix[nextPosYInGrid][i].willCrash()) return true
        }
        return false
    }

    private fun isGoingToCrashInX(matrix: Array<IntArray>, movingToRight: Boolean): Boolean {
        val posX = if (movingToRight) posXInMatrix + widthInBlocks else posXInMatrix - 1
        if (posX < MIN_POS_X_Y_IN_MATRIX || posX >= FigureManager.MATRIX_COLUMNS) return true
        for (i in posYInMatrix until posYInMatrix + heightInBlocks) {
            if (matrix[i][posX].willCrash()) return true
        }
        return false
    }

    override fun addFigureInMatrix(
        matrix: Array<IntArray>
    ): Array<IntArray> {
        line.forEachIndexed { index, value ->
            val i = if (isInHorizontalMode) posYInMatrix else (posYInMatrix + index)
            val j = if (isInHorizontalMode) (posXInMatrix + index) else posXInMatrix
            matrix[i][j] = value
        }
        return matrix
    }

    override fun increasePosYIfPossible(matrix: Array<IntArray>): Boolean {
        val willCrash = isGoingToCrashInY(matrix = matrix)
        if (willCrash) return false
        posYInMatrix += 1
        calculateGridPositions()
        return true
    }

    override fun posYInGrid(): Float {
        return posYInGrid
    }

    override fun posXInGrid(): Float {
        return posXInGrid
    }

    override fun rotateIfPossible(matrix: Array<IntArray>): Boolean {
        val didRotation = if (isInHorizontalMode) {
            rotateInYInMatrixIfPossible(matrix = matrix)
        } else {
            rotateInXInMatrixIfPossible(matrix = matrix)
        }
        if (didRotation) {
            isInHorizontalMode = !isInHorizontalMode
            rotation += ROTATION_DEGREES
            if (isInHorizontalMode) {
                heightInBlocks = ROWS
                widthInBlocks = COLUMNS
            } else {
                heightInBlocks = COLUMNS
                widthInBlocks = ROWS
            }
        }

        calculateGridPositions()
        return didRotation
    }

    // Will return true if it was able to rotate
    private fun rotateInYInMatrixIfPossible(matrix: Array<IntArray>): Boolean {
        val newHeightInBlocks = COLUMNS
        numbers.forEach { valueI ->
            val newX = posXInMatrix + valueI
            for (j in numbers.indices) {
                val valueJ = numbers[j]
                val newY = posYInMatrix - valueJ
                if (newY < MIN_POS_X_Y_IN_MATRIX || newY + newHeightInBlocks > FigureManager.MATRIX_ROWS) continue
                var willCrash = false
                for (k in newY until newY + newHeightInBlocks) {
                    val valueInMatrix = matrix[k][newX]
                    if (valueInMatrix.willCrash()) {
                        willCrash = true
                        break
                    }
                }
                if (!willCrash) {
                    posXInMatrix = newX
                    posYInMatrix = newY
                    return true
                }
            }
        }
        return false
    }

    // Will return true if it was able to rotate
    private fun rotateInXInMatrixIfPossible(matrix: Array<IntArray>): Boolean {
        val newWidthInBlocks = COLUMNS
        numbers.forEach { valueI ->
            val newY = posYInMatrix + valueI
            for (j in numbers.indices) {
                val valueJ = numbers[j]
                val newX = posXInMatrix - valueJ
                if (newX < MIN_POS_X_Y_IN_MATRIX) continue
                var willCrash = false
                for (k in newX until newX + newWidthInBlocks) {
                    val valueInMatrix = matrix[newY][k]
                    if (valueInMatrix.willCrash()) {
                        willCrash = true
                        break
                    }
                }
                if (!willCrash) {
                    posXInMatrix = newX
                    posYInMatrix = newY
                    return true
                }
            }
        }
        return false
    }

    private fun calculateGridPositions() {
        if (isInHorizontalMode) {
            posXInGrid = posXInMatrix.toFloat()
            posYInGrid = posYInMatrix.toFloat()
        } else {
            posXInGrid = posXInMatrix - 1.5f
            posYInGrid = posYInMatrix + 1.5f
        }
    }

    override fun rotationDegrees(): Float {
        return rotation
    }

    override fun moveInXIfPossible(matrix: Array<IntArray>, moveToRight: Boolean): Boolean {
        val willCrash = isGoingToCrashInX(matrix = matrix, movingToRight = moveToRight)
        if (willCrash) return false
        posXInMatrix = if (moveToRight) posXInMatrix + 1 else posXInMatrix - 1
        calculateGridPositions()
        return true
    }
}

fun Int.willCrash(): Boolean {
    return this == 1
}

interface FigureInMatrixOperations {
    fun addFigureInMatrix(matrix: Array<IntArray>): Array<IntArray>
    fun increasePosYIfPossible(matrix: Array<IntArray>): Boolean
    fun posYInGrid(): Float
    fun posXInGrid(): Float
    fun rotateIfPossible(matrix: Array<IntArray>): Boolean
    fun rotationDegrees(): Float
    fun moveInXIfPossible(matrix: Array<IntArray>, moveToRight: Boolean): Boolean
}

class FigureManager(
    private var cellSize: Float,
    private var gridMaxHeight: Float,
    private var gridMaxWidth: Float
) {
    companion object {
        const val MATRIX_ROWS = 20
        const val MATRIX_COLUMNS = 10
    }

    // matrix of positions
    private var figuresMatrix: Array<IntArray> =
        Array(MATRIX_ROWS) { IntArray(MATRIX_COLUMNS) { 0 } }

    // list of figures
    var figures = mutableStateListOf<Figure>()
        private set

    // current figure
    var currentFigure: Figure by mutableStateOf(
        Figure(FigureType.None),
        referentialEqualityPolicy()
    )
        private set

    // private val square = createSquare()
    private var currentFigureInMatrix: FigureInMatrixOperations = LineInMatrix()

    // maxHeightAvailable will change depending on figure type, x pos, rotation (perhaps) and gridMaxHeight
    private var maxYAvailableInGrid: Float = 20f

    // maxWidthAvailable will change depending on figure type, x pos, rotation (perhaps) and gridMaxWidth
    private var maxXAvailableInGrid: Float = 9f
    var yOffset = 0f

    fun createRandomFigure(i: Int) {
        val color = if (i == 1) Green else if (i == 2) Blue else Yellow
        currentFigureInMatrix = LineInMatrix()
//        currentFigureInMatrix.rotate()
        currentFigure =
            Figure(
                FigureType.Line,
                calculateNewPosY(),
                color = color,
                rotation = currentFigureInMatrix.rotationDegrees(),
                pos = i,
                posX = calculateNewPosX()
            )
    }

    fun rotate() {
        val didRotation = currentFigureInMatrix.rotateIfPossible(figuresMatrix)
        if (didRotation) updateFigure()
    }

    fun moveInX(moveToRight: Boolean) {
        val didMovement = currentFigureInMatrix.moveInXIfPossible(matrix = figuresMatrix, moveToRight)
        if (didMovement) updateFigure()
    }

    private fun updateFigure() {
        currentFigure = currentFigure.copy(
            posY = calculateNewPosY(),
            posX = calculateNewPosX(),
            rotation = currentFigureInMatrix.rotationDegrees()
        )
    }

    private fun calculateNewPosY(step: Int = 1): Float {
        return currentFigureInMatrix.posYInGrid() * (step * cellSize) + yOffset
    }

    private fun calculateNewPosX(): Float {
        return currentFigureInMatrix.posXInGrid() * cellSize
    }

    suspend fun updatePosY(step: Int = 1) {
        var isGoingToCrash = false
        while (!isGoingToCrash) {
            delay(500)
            val didMovement = currentFigureInMatrix.increasePosYIfPossible(matrix = figuresMatrix)
            if (didMovement) updateFigure()
            else isGoingToCrash = true
        }
        addFigureToMatrix()
        figures.add(currentFigure)
    }

    private fun addFigureToMatrix() {
        figuresMatrix = currentFigureInMatrix.addFigureInMatrix(figuresMatrix)
    }
}

//  IMPORTANT: Create children from this, with default positions on X depending on the figure type. This will remove the enum
data class Figure(
    val type: FigureType,
    var posY: Float = 0f,
    var posX: Float = 0f,
    var rotation: Float = 0f,
    val color: Color = Color.Black,
    val pos: Int = 0
)

enum class FigureType {
    Line,
    Square,
    None
}

data class GridPoint(var posX: Float, var posY: Float)