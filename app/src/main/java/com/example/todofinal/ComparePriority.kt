package com.example.todofinal

class ComparePriority {

    companion object: Comparator<Todo> {
        override fun compare(o1: Todo, o2: Todo): Int {
            val priority_map = mapOf(Pair("Low", 1), Pair("Medium", 2), Pair("High", 3))

            val o1_priority = priority_map.get(o1.priority)
            val o2_priority = priority_map.get(o2.priority)
            o1_priority?.let {
                o2_priority?.let {
                    return o1_priority - o2_priority

                }
            }

            return 1
        }

    }
}