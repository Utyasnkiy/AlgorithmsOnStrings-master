package ZBlocks

import java.lang.Integer.max

class Zblocks {

    fun prefixBorderArray(pattern : String) : IntArray{ //TODO It works 100%
         val prefixEqArray = IntArray(pattern.length){0}
        for (index in 1..<pattern.length){
            var bpRight = prefixEqArray[index-1]
            while (bpRight > 0 && (pattern[index] != pattern[bpRight])) bpRight = prefixEqArray[bpRight-1]
                prefixEqArray[index] = if (pattern[index] == pattern[bpRight]) bpRight+1 else 0
        }
        return prefixEqArray
    }

  /*  fun suffixBorderArray(pattern: String) : IntArray{ //FIXME check workability!!! -> don't work!!! -> IndexError
        val suffixEqArray = IntArray(pattern.length){0}
        for(index in pattern.length-2 downTo 0) {
            var bsLeft = suffixEqArray[index+1]
            while(pattern[index] != pattern[pattern.length-bsLeft-1]){
                println(pattern.length - bsLeft)
                bsLeft = suffixEqArray[pattern.length - bsLeft]
            }
            suffixEqArray[index] = if(pattern[index] == pattern[pattern.length -bsLeft-1]) bsLeft+1 else 0
        }
        return suffixEqArray
    }
*/
    fun modificationPrefixBorderArray(pattern: String, prefixBorderArray : IntArray) : IntArray {
        val length = pattern.length
        val prefixModifArray = IntArray(length){0}
        prefixModifArray[length-1] = prefixBorderArray[length-1]
        for(i in 1..<length-1) prefixModifArray[i] =
            if(pattern[prefixBorderArray[i]] == pattern[i+1]) prefixModifArray[prefixBorderArray[i]-1] else prefixBorderArray[i]
        return prefixModifArray
    }

    fun adapterBPToBPM(prefixBorderArray: IntArray) : IntArray {
        val length = prefixBorderArray.size
        val prefixModifArray = IntArray(length){0}
        prefixModifArray[length-1] = prefixBorderArray[length-1]
        for(i in 1..<length-1) {
            prefixModifArray[i] = if(prefixBorderArray[i] + 1 == prefixBorderArray[i+1]) prefixModifArray[prefixBorderArray[i]-1] else prefixBorderArray[i]
        }
        return prefixModifArray
    }

    fun adapterBPMToBP(prefixModifArray: IntArray) : IntArray{
        val length = prefixModifArray.size
        val prefixBorderArray = IntArray(length){0}
        prefixBorderArray[length-1] = prefixModifArray[length-1]
        for(i in length-2 downTo  1) prefixBorderArray[i] = max(prefixBorderArray[i+ 1]-1, prefixModifArray[i])
    return prefixBorderArray
    }

    fun adapterBSToBSM(suffixBoarderArray : IntArray) : IntArray{
        val length = suffixBoarderArray.size
        val suffixModifArray = IntArray(length){0}
        suffixModifArray[0] = suffixBoarderArray[0]
        for(i in length-2 downTo 1){
            suffixModifArray[i] = if(suffixBoarderArray[i] + 1 == suffixBoarderArray[i-1])
                suffixModifArray[length -suffixBoarderArray[i]] else suffixBoarderArray[i]
        }
        return suffixModifArray
    }

    fun adapterBSMToBS(suffixModifArray : IntArray) : IntArray{
        val length = suffixModifArray.size
        val suffixBoarderArray = IntArray(length){0}
        suffixBoarderArray[0] = suffixModifArray[0]
        for(i in 1..<length-1) suffixBoarderArray[i] = max (suffixBoarderArray[i-1] -1, suffixModifArray[i])
    return suffixBoarderArray
    }

    /**
     * Z-blocks (equals prefixBoarderArray/SuffixBoarderArray)
     */

    private fun strComp(pattern : String, i1 : Int, i2 : Int) : Int{
        var copyI1 = i1
        var copyI2 = i2
        val length = pattern.length
        var eqLen= 0
        while (copyI1 < length && copyI2 < length && pattern[copyI1++] == pattern[copyI2++]) ++eqLen
        return eqLen
    }

    fun prefixZValues(pattern : String) : IntArray{
        val length = pattern.length
        val prefixZArray = IntArray(length){0}
        var left = 0
        var right = 0
        for(i in 1..<length) {
            if (i >= right) {
                prefixZArray[i] = strComp(pattern, 0, i); left= i; right = left + prefixZArray[i]
            }else {
                val j = i - left
                if (prefixZArray[j] < right -i) prefixZArray[i] = prefixZArray[j] else prefixZArray[i] =
                    right -i+ strComp(pattern, right-i, right); left= i; right = left+ prefixZArray[i]
            }
        }
        return prefixZArray
    }

    private fun strCompBack(pattern : String, i1 : Int, i2 : Int) : Int{
        var eqLen= 0
        var copyI1 = i1
        var copyI2 = i2
        while (copyI1 >= 0 && copyI2 >= 0 && pattern[copyI1--] == pattern[copyI2--]) ++eqLen
        return eqLen
    }

    fun suffixZValues(pattern : String) : IntArray {
        val length =pattern.length
        var left= length-1
        var right = length-1
        val suffixZArray = IntArray(length){0}
        for(i in length -2 downTo 0) {
            if (i<= left)
            {
                suffixZArray[i] = strCompBack(pattern, i, length-1); right = i; left= right -suffixZArray[i]
            }
            else
            {
                val j = length -(right + 1 -i)
                if (suffixZArray[j] < i-left) suffixZArray[i] = suffixZArray[j]
                else
                {
                    suffixZArray[i] = i-left+ strCompBack(pattern, left, length -i+ left); right = i; left= right -suffixZArray[i]
                }
            }
        }
        return suffixZArray
    }

    fun adapterZPToBPM(prefixZArray : IntArray) : IntArray { //FIXME check workability!!!
        val length = prefixZArray.size
        val prefixModifArray = IntArray(length){0}
        for(j in length-1 downTo 1) {
            val i = j + prefixZArray[j]-1
            prefixModifArray[i] = prefixZArray[j]
        }
        return prefixModifArray
    }

    fun adapterZPToBP(prefixZArray : IntArray) : IntArray{
        val length = prefixZArray.size
        val prefixBoarderArray = IntArray(length) { 0 }
        for (j in 1..<length) {
            for (i in j + prefixZArray[j] - 1 downTo j) {
                if (prefixBoarderArray[i] == 0) break else prefixBoarderArray[i] = i - j + 1
            }
        }
        return prefixBoarderArray
    }

    private fun valGrow(array : IntArray, nPos : Int, nVal : Int) : Int{
        val length = array.size
        var copyNPos = nPos
        var copyNVal = nVal
        var nSeqLen = 0
        while (nPos < length && array[copyNPos++] == copyNVal++) ++nSeqLen
        return nSeqLen
    }

    fun adapterBPToZP(prefixBoarderArray : IntArray) : IntArray {
        val length = prefixBoarderArray.size
        val prefixZArray = IntArray(length){0}
        var left = 0
        var right = 0
        for(i in 1 ..< length) {
            if (i >= right){
                prefixZArray[i] = valGrow(prefixBoarderArray, i, 1); left= i; right = left+ prefixZArray[i]
            }
            else {
                val j = i-left
                if (prefixZArray[j] < right -i) prefixZArray[i] = prefixZArray[j]
                else {
                    prefixZArray[i] = right -i+ valGrow(prefixBoarderArray, right, right -i+ 1); left= i; right = left+ prefixZArray[i]
                }
            }
        }
        return prefixZArray
    }

}