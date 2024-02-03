import java.io.File

val testText = "Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53\n" +
        "Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19\n" +
        "Card 3:  1 21 53 59 44 | 69 82 63 72 16 21 14  1\n" +
        "Card 4: 41 92 73 84 69 | 59 84 76 51 58  5 54 83\n" +
        "Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36\n" +
        "Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11\n"

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

fun parser(text: String): Pair<ArrayList<ArrayList<Int>>, ArrayList<ArrayList<Int>>> {
    val cards = ArrayList<ArrayList<Int>>()
    val results = ArrayList<ArrayList<Int>>()
    text.split("\n").filter { it.isNotEmpty() }.forEach { line: String ->
        val cardsAndResults = line.substringAfter(": ").split(" | ")
        val cardTemp = ArrayList<Int>()
        cardsAndResults[0].trim().split(" ").filter { it.isNotEmpty() }.forEachIndexed { i: Int, value: String ->
            cardTemp.add(value.toInt())
        }
        cards.add(cardTemp)
        val resultTemp = ArrayList<Int>()
        cardsAndResults[1].trim().split(" ").filter { it.isNotEmpty() }.forEachIndexed { index, s ->
            resultTemp.add(s.toInt())
        }
        results.add(resultTemp)
    }

    return Pair(cards, results)

}

fun calculateWinningNumbers(parsed: Pair<ArrayList<ArrayList<Int>>, ArrayList<ArrayList<Int>>>): Int {
    val (parsedCards, parsedResults) = parsed
    var result = 0
    for (parsedCardIndex in parsedCards.indices) {
        var countWinningNumbers = 0
        for (card in parsedCards[parsedCardIndex])
            if (card in parsedResults[parsedCardIndex]) {
                if (countWinningNumbers == 0) {
                    countWinningNumbers = 1
                } else {
                    countWinningNumbers *= 2
                }
            }
        result += countWinningNumbers
    }
    return result
}

typealias Result = Pair<ArrayList<ArrayList<Int>>, ArrayList<ArrayList<Int>>>
typealias ResultMap = Pair<MutableMap<Int, ArrayList<Int>>, MutableMap<Int, ArrayList<Int>>>

fun elaborateScratchCards(parsed: Result, scratchCardsToElaborate: ResultMap): MutableMap<Int, Int> {
    var result = mutableMapOf<Int, Int>()
    val (scratchCards, scratchResults) = scratchCardsToElaborate
    val (parsedCards, parsedResults) = parsed

    for ((scratchCardIndex, cards) in scratchCards) {
        if (result.keys.contains(scratchCardIndex) && result.getValue(scratchCardIndex) != null) {
            result.set(scratchCardIndex, result.getValue(scratchCardIndex) + 1)
        } else {
            result.put(scratchCardIndex, 1)
        }

        var countWinningNumbers = 0
        for (card in cards) {
            if (card in scratchResults.getValue(scratchCardIndex)) {
                countWinningNumbers += 1
            }
        }

        var scratchCardToCheck: Pair<MutableMap<Int, ArrayList<Int>>, MutableMap<Int, ArrayList<Int>>> =
            Pair(mutableMapOf(), mutableMapOf())
        for (i in 0 until countWinningNumbers) {
            var index = scratchCardIndex + i + 1
            scratchCardToCheck.first.put(index, parsedCards[index])
            scratchCardToCheck.second.put(index, parsedResults[index])
        }

        if (countWinningNumbers > 0) {
            var recursiveResult = elaborateScratchCards(parsed, scratchCardToCheck)
            for (key in recursiveResult.keys) {
                if (result.keys.contains(key) && recursiveResult.keys.contains(key) && result.getValue(key) != null) {
                    result.set(key, result.getValue(key) + recursiveResult.getValue(key))
                } else {
                    result.put(key, recursiveResult.getValue(key))
                }
            }
        }
    }

    return result
}

fun testPartOne() {
    val parsed = parser(testText)
    if (calculateWinningNumbers(parsed) == 13) {
        println("Test part one passed")
    } else {
        println("Test part one failed")
    }
}

fun partOne() {
    val parsed = parser(readFile("input.txt"))
    println(calculateWinningNumbers(parsed))
}

fun testPartTwo() {
    val parsed = parser(testText)
    val scratchCards = elaborateScratchCards(
        parsed,
        Pair(
            parsed.first.withIndex().associate { (indice, lista) -> indice to lista }.toMutableMap(),
            parsed.second.withIndex().associate { (indice, lista) -> indice to lista }.toMutableMap()
        )
    )
    println(scratchCards)
    println(scratchCards.values.reduce({ acc, i -> acc + i }))
}

fun partTwo() {
    val parsed = parser(readFile("input.txt"))
    val scratchCards = elaborateScratchCards(
        parsed,
        Pair(
            parsed.first.withIndex().associate { (indice, lista) -> indice to lista }.toMutableMap(),
            parsed.second.withIndex().associate { (indice, lista) -> indice to lista }.toMutableMap()
        )
    )
    println(scratchCards)
    println(scratchCards.values.reduce({ acc, i -> acc + i }))
}

testPartOne()
partOne()
testPartTwo()
partTwo()