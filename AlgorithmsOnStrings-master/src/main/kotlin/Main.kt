import SuffixArray.SuffixArray

fun main(args: Array<String>) {
    //  println(ShiftAnd().SearchString("ababababababcab","abc"))
    // KMP().searchInputKMP("abababababababababccccccabababab","abc").forEach { println("$it ") }
    // Zblocks().prefixBorderArray("ababa").forEach{print("$it ")}
    SuffixTreeUkkonen.SuffixTree("abcabxabcd").visualize()
   // SuffixArray().buildSuffixArray("abcabxabcd", "abcabxabcd".length)
}