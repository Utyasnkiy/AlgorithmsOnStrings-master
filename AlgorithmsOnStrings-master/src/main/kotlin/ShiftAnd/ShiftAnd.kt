package ShiftAnd

class ShiftAnd {

    fun shiftAnd(pattern : String, text : String) : Int { // FIXME check workability
        val lengthText = text.length
        val lengthPattern = pattern.length
        val startCharacter = '0'
        val endCharacter = 'z'
        val lengthAlphabet = endCharacter-startCharacter+1
        val arrayIncluded = IntArray(lengthAlphabet){0}

        for (i in 0..<lengthPattern) arrayIncluded[pattern[i]-startCharacter] += 1 shl (lengthPattern-1-i)
        val higherDigit = 1 shl (lengthPattern-1)
        var checkIncluded = 0
        for (j in 0..<lengthText){
            checkIncluded =(checkIncluded shr 1 or higherDigit) and arrayIncluded[text[j]-startCharacter]
            if (checkIncluded > 1){
                return j-lengthPattern+1
            }
        }
        return -1
    }

    fun shiftAndFz(pattern: String, text : String) : Int { //FIXME add some code
        val lengthText = text.length
        val lengthPattern = pattern.length
        val startCharacter = '0'
        val endCharacter = 'z'
        val lengthAlphabet = endCharacter-startCharacter+1
        val arrayIncluded = IntArray(lengthAlphabet){0}
        for (i in 0..<lengthPattern) arrayIncluded[pattern[i]-startCharacter] += 1 shl (lengthPattern-1-i)
        val higherDigit = 1 shl (lengthPattern-1)
        var checkIncluded = 0


        return -1
    }
}