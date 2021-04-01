package ru.byprogminer.lab1testing.task2

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.assertThrows


class LeftistHeapTest {

    @Test
    fun `Test new LeftistHeap is empty`() = assertEquals(
        emptyList<Int>() to emptyList<String>(),
        useHeap({ LeftistHeap<Int>() }) { heap ->
            assertEquals(true, heap.isEmpty, "new LeftistHeap is not empty")
        },
    )

    @Test
    fun `Test empty LeftistHeap extractMin throws exception`() = assertAll(
        { assertThrows<NoSuchElementException> { useHeap({ LeftistHeap<Int>() }) { heap -> heap.extractMin() } } }
    )

    @Test
    fun `Test new LeftistHeap from collection builds heap`() {
        val items = listOf(123, -2, 1337, -228, -322)

        val (contents, log) = useHeap({ LeftistHeap(items) }) { heap ->
            assertEquals(false, heap.isEmpty, "new LeftistHeap is empty")
        }

        assertEquals(listOf(-322, -228, -2, 123, 1337), contents, "LeftistHeap has wrong order")
        assertEquals(listOf(
            "insert constructor [123, -2, 1337, -228, -322]",
            "insert 123",
            "mergeNodes <- null (123, 1)",
            "mergeNodes -> (123, 1)",
            "insert -2",
            "mergeNodes <- (123, 1) (-2, 1)",
            "mergeNodes <- (-2, 1) (123, 1)",
            "mergeNodes <- null (123, 1)",
            "mergeNodes -> (123, 1)",
            "mergeNodes -> (-2, 1 -> (123, 1), null)",
            "insert 1337",
            "mergeNodes <- (-2, 1 -> (123, 1), null) (1337, 1)",
            "mergeNodes <- null (1337, 1)",
            "mergeNodes -> (1337, 1)",
            "mergeNodes -> (-2, 2 -> (123, 1), (1337, 1))",
            "insert -228",
            "mergeNodes <- (-2, 2 -> (123, 1), (1337, 1)) (-228, 1)",
            "mergeNodes <- (-228, 1) (-2, 2 -> (123, 1), (1337, 1))",
            "mergeNodes <- null (-2, 2 -> (123, 1), (1337, 1))",
            "mergeNodes -> (-2, 2 -> (123, 1), (1337, 1))",
            "mergeNodes -> (-228, 1 -> (-2, 2 -> (123, 1), (1337, 1)), null)",
            "insert -322",
            "mergeNodes <- (-228, 1 -> (-2, 2 -> (123, 1), (1337, 1)), null) (-322, 1)",
            "mergeNodes <- (-322, 1) (-228, 1 -> (-2, 2 -> (123, 1), (1337, 1)), null)",
            "mergeNodes <- null (-228, 1 -> (-2, 2 -> (123, 1), (1337, 1)), null)",
            "mergeNodes -> (-228, 1 -> (-2, 2 -> (123, 1), (1337, 1)), null)",
            "mergeNodes -> (-322, 1 -> (-228, 1 -> (-2, 2 -> (123, 1), (1337, 1)), null), null)",
        ), log, "insert constructor works wrong")
    }

    @Test
    fun `Test insert adds one element`() {
        val (contents, log) = useHeap({ LeftistHeap<Int>() }) { heap ->
            heap.insert(0)

            assertEquals(false, heap.isEmpty, "insert didn't add element")
            assertEquals(0, heap.extractMin(), "insert added wrong element")
            assertEquals(true, heap.isEmpty, "insert added more than one element")
        }

        assertEquals(emptyList<Int>(), contents, "isEmpty is not consistent with extractMin")
        assertEquals(listOf(
            "insert 0",
            "mergeNodes <- null (0, 1)",
            "mergeNodes -> (0, 1)",
            "extractMin",
            "mergeNodes <- null null",
            "mergeNodes -> null",
        ), log, "insert works wrong")
    }

    @Test
    fun `Test insert adds duplicated elements`() {
        val (contents, log) = useHeap({ LeftistHeap(listOf(123)) }) { heap ->
            heap.insert(123)
        }

        assertEquals(listOf(123, 123), contents, "insert didn't add duplicate")
        assertEquals(listOf(
                "insert constructor [123]",
                "insert 123",
                "mergeNodes <- null (123, 1)",
                "mergeNodes -> (123, 1)",
                "insert 123",
                "mergeNodes <- (123, 1) (123, 1)",
                "mergeNodes <- null (123, 1)",
                "mergeNodes -> (123, 1)",
                "mergeNodes -> (123, 1 -> (123, 1), null)",
        ), log, "insert adds duplicate in a bad way")
    }

    @Test
    fun `Test LeftistHeap merge merges heaps`() {
        val (contents, log) = useHeap({ LeftistHeap(listOf(1, 2, 3)) }) { a ->
            val b = LeftistHeap(listOf(-1, -2, -3))

            a.merge(b)

            assertEquals(true, b.isEmpty, "second heap is not empty after merging")
        }

        assertEquals(listOf(-3, -2, -1, 1, 2, 3), contents, "wrong merge")
        assertEquals(listOf(
            "insert constructor [1, 2, 3]",
            "insert 1",
            "mergeNodes <- null (1, 1)",
            "mergeNodes -> (1, 1)",
            "insert 2",
            "mergeNodes <- (1, 1) (2, 1)",
            "mergeNodes <- null (2, 1)",
            "mergeNodes -> (2, 1)",
            "mergeNodes -> (1, 1 -> (2, 1), null)",
            "insert 3",
            "mergeNodes <- (1, 1 -> (2, 1), null) (3, 1)",
            "mergeNodes <- null (3, 1)",
            "mergeNodes -> (3, 1)",
            "mergeNodes -> (1, 2 -> (2, 1), (3, 1))",
            "merge (-3, 1 -> (-2, 1 -> (-1, 1), null), null)",
            "mergeNodes <- (1, 2 -> (2, 1), (3, 1)) (-3, 1 -> (-2, 1 -> (-1, 1), null), null)",
            "mergeNodes <- (-3, 1 -> (-2, 1 -> (-1, 1), null), null) (1, 2 -> (2, 1), (3, 1))",
            "mergeNodes <- null (1, 2 -> (2, 1), (3, 1))",
            "mergeNodes -> (1, 2 -> (2, 1), (3, 1))",
            "mergeNodes -> (-3, 2 -> (1, 2 -> (2, 1), (3, 1)), (-2, 1 -> (-1, 1), null))",
        ), log, "merge works wrong")
    }

    @Test
    fun `Test LeftistHeap merge with two empty heaps produces empty heap`() {
        val (contents, log) = useHeap({ LeftistHeap<Int>() }) { a ->
            val b = LeftistHeap<Int>()

            a.merge(b)

            assertEquals(true, a.isEmpty)
            assertEquals(true, b.isEmpty)
        }

        assertEquals(emptyList<Int>(), contents)
        assertEquals(listOf(
            "merge null",
            "mergeNodes <- null null",
            "mergeNodes -> null",
        ), log, "merge works wrong")
    }

    @Test
    fun `Test LeftistHeap merge with empty heap as argument not breaking heap`() {
        val (contents, log) = useHeap({ LeftistHeap(listOf(1, -2, 3)) }) { heap ->
            heap.merge(LeftistHeap())
        }

        assertEquals(listOf(-2, 1, 3), contents)
        assertEquals(listOf(
            "insert constructor [1, -2, 3]",
            "insert 1",
            "mergeNodes <- null (1, 1)",
            "mergeNodes -> (1, 1)",
            "insert -2",
            "mergeNodes <- (1, 1) (-2, 1)",
            "mergeNodes <- (-2, 1) (1, 1)",
            "mergeNodes <- null (1, 1)",
            "mergeNodes -> (1, 1)",
            "mergeNodes -> (-2, 1 -> (1, 1), null)",
            "insert 3",
            "mergeNodes <- (-2, 1 -> (1, 1), null) (3, 1)",
            "mergeNodes <- null (3, 1)",
            "mergeNodes -> (3, 1)",
            "mergeNodes -> (-2, 2 -> (1, 1), (3, 1))",
            "merge null",
            "mergeNodes <- (-2, 2 -> (1, 1), (3, 1)) null",
            "mergeNodes -> (-2, 2 -> (1, 1), (3, 1))",
        ), log, "merge works wrong")
    }

    @Test
    fun `Test LeftistHeap merge with empty heap as this moves contents from argument`() {
        val (contents, log) = useHeap({ LeftistHeap<Int>() }) { a ->
            val b = LeftistHeap(listOf(1, -2, 3))

            a.merge(b)
            assertEquals(true, b.isEmpty)
        }

        assertEquals(listOf(-2, 1, 3), contents, "content not moved properly")
        assertEquals(listOf(
            "merge (-2, 2 -> (1, 1), (3, 1))",
            "mergeNodes <- null (-2, 2 -> (1, 1), (3, 1))",
            "mergeNodes -> (-2, 2 -> (1, 1), (3, 1))",
        ), log, "content moved in a bad way")
    }

    private fun <K: Comparable<K>> extractAll(heap: LeftistHeap<K>): List<K> {
        val items = mutableListOf<K>()

        while (!heap.isEmpty) {
            items.add(heap.extractMin())
        }

        return items.toList()
    }

    private inline fun <K: Comparable<K>> useHeap(
        factory: () -> LeftistHeap<K>,
        block: (heap: LeftistHeap<K>) -> Unit = {},
    ): Pair<List<K>, List<String>> {
        val log = mutableListOf<String>()

        val heap = factory()
        heap.logger = { msg -> log.add(msg) }

        try {
            block(heap)
        } finally {
            heap.logger = null
        }

        return extractAll(heap) to log
    }
}
