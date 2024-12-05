import java.io.File

fun readFile(filePath: String): ArrayList<ArrayList<Int>> {

    var result: ArrayList<ArrayList<Int>> = ArrayList()
    try {
        val bufferedReader = File(filePath).bufferedReader()
        bufferedReader.useLines { lines ->
            lines.forEach { line ->
                var linesResult = ArrayList<Int>()
                line.split(" ").forEach { number ->

                    linesResult.add(number.toInt())
                }
                result.add(linesResult)
            }

        }
    } catch (e: Exception) {
        e.printStackTrace()
    }

    return result
}

fun checkSafety(numbers: ArrayList<Int>): Boolean {
    var isIncreasing = false;
    var isDecreasing = false;
    for (i in 0 until numbers.size) {
        //determine if the numbers are increasing or decreasing
        if (!isDecreasing && !isIncreasing && i < numbers.size - 1) {
            if (numbers[i] < numbers[i + 1]) {
                isIncreasing = true
            } else if (numbers[i] > numbers[i + 1]) {
                isDecreasing = true
            }
        }

        if (i < numbers.size - 1) {
            val step = Math.abs(numbers[i] - numbers[i + 1])
            if ((step < 1 || step > 3)
                || (isDecreasing && numbers[i] < numbers[i + 1])
                || (isIncreasing && numbers[i] > numbers[i + 1])) {
                return false;
            }
        }
    }
    return true
}

val result = readFile("input.txt")
var count = 0

result.forEach { numbers ->
    var safe = checkSafety(numbers)
    if (safe) {
        count++
    } else { // brute force
        var index = 0
        while (!safe && index < numbers.size) {
            var temp = ArrayList(numbers)
            temp.removeAt(index)
            safe = checkSafety(temp)
            if (safe) {
                count++
            }
            index++
        }
    }
}

println(count)