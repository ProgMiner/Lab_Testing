package ru.byprogminer.lab1testing.task2

import java.util.*
import kotlin.NoSuchElementException


class LeftistHeap<K: Comparable<K>> {

    companion object {

        private fun <K> dist(node: Node<K>?) = node?.dist ?: 0
    }

    private val logBuffer: Queue<String> = LinkedList()
    var logger: ((msg: String) -> Unit)? = null
        set(value) {
            field = value

            if (field != null && !logBuffer.isEmpty()) {
                logBuffer.forEach(field)
                logBuffer.clear()
            }
        }

    val isEmpty get() = root == null

    private var root: Node<K>? = null

    constructor()

    constructor(collection: Collection<K>) {
        log("insert constructor", collection)
        collection.forEach(this::insert)
    }

    fun merge(that: LeftistHeap<K>) {
        log("merge", that.root)

        this.root = mergeNodes(this.root, that.root)
        that.root = null
    }

    fun insert(key: K) {
        log("insert", key)

        root = mergeNodes(root, Node(key))
    }

    fun extractMin(): K {
        log("extractMin")

        val root = this.root ?: throw NoSuchElementException()
        this.root = mergeNodes(root.left, root.right)

        return root.key
    }

    private tailrec fun <K: Comparable<K>> mergeNodes(x: Node<K>?, y: Node<K>?): Node<K>? {
        log("mergeNodes <-", x, y)

        if (x == null) {
            log("mergeNodes ->", y)
            return y
        }

        if (y == null) {
            log("mergeNodes ->", x)
            return x
        }

        if (y.key < x.key) {
            return mergeNodes(y, x)
        }

        @Suppress("NON_TAIL_RECURSIVE_CALL")
        x.right = mergeNodes(x.right, y)

        if (dist(x.right) > dist(x.left)) {
            val tmp = x.right
            x.right = x.left
            x.left = tmp
        }

        x.dist = dist(x.right) + 1
        log("mergeNodes ->", x)
        return x
    }

    private fun log(vararg items: Any?) = (logger to items.joinToString(" "))
        .let { (logger, items) -> logger?.invoke(items) ?: logBuffer.offer(items) }

    private class Node<K>(val key: K) {

        var left: Node<K>? = null
        var right: Node<K>? = null

        var dist: Int = 1

        override fun toString(): String =
            if (left == null && right == null) {
                "($key, 1)"
            } else {
                "($key, $dist -> $left, $right)"
            }
    }
}
