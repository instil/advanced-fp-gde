package state.part0.model

import arrow.core.ListK

fun <T> foldOverChildren(initial: String, children: ListK<T>, indent: String ="\t") = children.fold(initial) { state, child ->
    "$state\n$indent$child"
}
