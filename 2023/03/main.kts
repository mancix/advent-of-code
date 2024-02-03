import java.io.File

val testText = "467..114..\n" +
        "...*......\n" +
        "..35..633.\n" +
        "......#...\n" +
        "617*......\n" +
        ".....+.58.\n" +
        "..592.....\n" +
        "......755.\n" +
        "...\$.*....\n" +
        ".664.598..\n"


fun partOne() {
    val fileText = readFile("2023/03/input.txt")
    val (map, coordinates) = parser(fileText)
    var result = 0
    for (coordinate in coordinates) {
        if (coordinate.isNearASymbol(map)) {
            result += map[coordinate.row].slice(coordinate.start..coordinate.end)
                .joinToString("").toInt()
        }
    }

    println("part one: $result")
}

fun partTwo() {
    val fileText = readFile("2023/03/input.txt")
    val (map, coordinates) = parser(fileText)
    var resultPartTwo = 0
    val coordinatesNearByAsterisk = ArrayList<Coordinate>()
    for (coordinate in coordinates) {
        if (coordinate.isNearASymbol(map)) {
            coordinatesNearByAsterisk.add(coordinate)
        }
    }

    for (coordinateWithAsterisk in coordinatesNearByAsterisk) {
        for (anotherCoordinateWithAsterisk in coordinatesNearByAsterisk) {
            if (coordinateWithAsterisk != anotherCoordinateWithAsterisk
                && coordinateWithAsterisk.asterisk != null
                && coordinateWithAsterisk.asterisk.toString() == anotherCoordinateWithAsterisk.asterisk.toString()
            ) {
                resultPartTwo += (map[coordinateWithAsterisk.row].slice(coordinateWithAsterisk.start..coordinateWithAsterisk.end)
                    .joinToString("").toInt()) * (map[anotherCoordinateWithAsterisk.row].slice(
                    anotherCoordinateWithAsterisk.start..anotherCoordinateWithAsterisk.end
                )
                    .joinToString("").toInt())
            }
        }
    }
    resultPartTwo /= 2
    println("part two: $resultPartTwo")
}

fun parser(textToParse: String): Pair<ArrayList<ArrayList<Char>>, ArrayList<Coordinate>> {
    val map = ArrayList<ArrayList<Char>>()
    val coordinates = ArrayList<Coordinate>()
    textToParse.split("\n").forEachIndexed { i: Int, line: String ->
        map.add(i, ArrayList())
        var isPreviousLetterANumber = false
        line.forEachIndexed { k: Int, letter: Char ->
            map[i].add(k, letter)
            if (letter.isDigit() && !isPreviousLetterANumber) {
                isPreviousLetterANumber = true
                coordinates.add(Coordinate(i, k, k))
            } else if (letter.isDigit() && isPreviousLetterANumber) {
                coordinates.last().end = k
            } else {
                isPreviousLetterANumber = false
            }
        }
    }
    map.removeLast()

    return Pair(map, coordinates)
}


class Coordinate(var row: Int, var start: Int, var end: Int) {
    var asterisk: Coordinate? = null

    override fun toString(): String {
        return "$row-$start-$end"
    }

    fun isNearASymbol(map: ArrayList<ArrayList<Char>>): Boolean {
        var rowsRange: IntRange = this.row - 1..this.row + 1
        for (currentRow in rowsRange) {
            if (currentRow in map.indices) {
                for (currentColumn in this.start - 1..this.end + 1) {
                    if (currentColumn in map[currentRow].indices
                        && !map[currentRow][currentColumn].isLetterOrDigit()
                        && map[currentRow][currentColumn] != '.'
                    ) {
                        if (map[currentRow][currentColumn] == '*') {
                            this.asterisk = Coordinate(currentRow, currentColumn, currentColumn)
                        }
                        return true
                    }
                }
            }
        }
        return false
    }
}

fun readFile(filePath: String): String {

    var fileText = ""
    try {
        val bufferedReader = File(filePath).bufferedReader()
        bufferedReader.useLines { lines ->
            lines.forEach { line ->
                fileText += "$line\n"
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }

    return fileText
}

partOne()
partTwo()