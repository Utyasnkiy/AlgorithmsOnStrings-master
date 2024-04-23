package KMP

/**
 * Class for realization of algorithm KMP
 * (more effective (O(n+m), where often m << n) than native algorithm O(n^2))
 */
class KMP {

    private fun prefixFunction(pattern : String) : IntArray{
        val piArray = IntArray(pattern.length){0}
        for (index in 1..<pattern.length) {
            var counter = 0
            var previewIndex = 0
            var endIndex : Int = index
            var prefix: String = pattern[previewIndex].toString()
            var suffix: String = pattern[endIndex].toString()
            while (prefix.length < index+1) {
                if (prefix == suffix) {
                    counter = prefix.length
                }
                previewIndex++
                endIndex--
                prefix = pattern.substring(IntRange(0,previewIndex))
                suffix = pattern.substring(IntRange(endIndex,index))
            }
            piArray[index] = counter
        }
        return piArray
    }

    private fun effectivePrefixFunction(pattern : String) : IntArray{
        val LTSArray = IntArray(pattern.length){0}
        for (i in 1..<pattern.length) {
            var bpRight = LTSArray[i-1]
            while (bpRight>0 && (pattern[i]!=pattern[bpRight])) bpRight = LTSArray[bpRight-1]
            LTSArray[i] = if (pattern[i] == pattern[bpRight])bpRight + 1 else 0
        }
        return LTSArray
    }

    fun searchInputKMP(text : String, pattern : String) : List<Int>{

        //val LTSArray = prefixFunction(image) <- O(n^2) (native algorithm search blocks)
        val LTSArray = effectivePrefixFunction(pattern) //<- O(m) (effective algorithm search blocks)
        val result = mutableListOf<Int>()
        var compareIndex = 0

        for (i in text.indices) {
            while (compareIndex > 0 && text[i] != pattern[compareIndex]) {
                compareIndex = LTSArray[compareIndex - 1]
            }

            if (text[i] == pattern[compareIndex]) {
                compareIndex++;
            }

            if (compareIndex == pattern.length) {
                result.add(i - compareIndex + 1)
                compareIndex = LTSArray[pattern.length - 1]
            }
        }
        return result
    }
}