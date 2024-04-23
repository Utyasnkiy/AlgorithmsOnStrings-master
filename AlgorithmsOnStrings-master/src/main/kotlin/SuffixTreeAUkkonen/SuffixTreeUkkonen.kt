package SuffixTreeUkkonen

// Определение класса узла суффиксного дерева
private class Node {
    var sub = "" // подстрока входной строки, соответствующая данному узлу
    var ch: MutableList<Int> = ArrayList() // список индексов дочерних узлов
}

// Определение класса суффиксного дерева
class SuffixTree(str: String) {
    private val nodes: MutableList<Node> = ArrayList() // список узлов дерева

    // Конструктор класса, который строит суффиксное дерево для заданной строки
    init {
        nodes.add(Node()) // добавляем корневой узел
        for (i in str.indices) {
            addSuffix(str.substring(i)) // добавляем каждый суффикс строки в дерево
        }
    }

    // Метод для добавления суффикса в суффиксное дерево
    private fun addSuffix(suf: String) {
        var n = 0 // индекс текущего узла
        var i = 0 // индекс текущего символа в суффиксе
        while (i < suf.length) {
            val b = suf[i] // текущий символ суффикса
            val children = nodes[n].ch // список дочерних узлов текущего узла
            var x2 = 0 // индекс текущего дочернего узла
            var n2: Int
            while (true) {
                if (x2 == children.size) {
                    // нет совпадающего дочернего узла, создаем новый узел для оставшегося суффикса
                    n2 = nodes.size
                    val temp = Node()

                    temp.sub = suf.substring(i)
                    nodes.add(temp)
                    children.add(n2)
                    return
                }
                n2 = children[x2]
                if (nodes[n2].sub[0] == b) break // нашли совпадение с подстрокой текущего дочернего узла
                x2++
            }
            // нашли префикс оставшегося суффикса, который совпадает с подстрокой текущего дочернего узла
            val sub2 = nodes[n2].sub
            var j = 0
            while (j < sub2.length) {
                if (suf[i + j] != sub2[j]) {
                    // разбиваем текущий дочерний узел на два
                    val n3 = n2
                    // новый узел для общей части
                    n2 = nodes.size
                    val temp = Node()
                    temp.sub = sub2.substring(0, j)
                    temp.ch.add(n3)
                    nodes.add(temp)
                    nodes[n3].sub = sub2.substring(j) // старый узел теряет общую часть
                    nodes[n].ch[x2] = n2
                    break // продолжаем спуск вниз по дереву
                }
                j++
            }
            i += j // переходим к следующей части суффикса
            n = n2 // продолжаем спуск вниз по дереву
        }
    }

    // Метод для визуализации суффиксного дерева
    fun visualize() {
        if (nodes.isEmpty()) {
            println("")
            return
        }
        visualizeF(0, "") // начинаем визуализацию с корневого узла
    }

    // Вспомогательный метод для рекурсивной визуализации узлов суффиксного дерева
    private fun visualizeF(n: Int, pre: String) {
        val children: List<Int> = nodes[n].ch // список дочерних узлов текущего узла
        if (children.isEmpty()) {
            println("- " + nodes[n].sub) // если у узла нет дочерних, выводим его и завершаем
            return
        }
        println("+ " + nodes[n].sub) // выводим текущий узел
        for (i in 0 until children.size - 1) {
            val c = children[i]
            print("$pre+-")
            visualizeF(c, "$pre|") // рекурсивно визуализируем дочерние узлы
        }
        print("$pre+-")
        visualizeF(children[children.size - 1], "$pre  ") // рекурсивно визуализируем последний дочерний узел
    }
}
