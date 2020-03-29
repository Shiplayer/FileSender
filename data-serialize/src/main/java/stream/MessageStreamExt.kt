package stream

import data.Message
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.ObjectInputStream

fun List<Message>.getContent(): List<Any> {
    return this.groupBy { it.messageId }.map{ (_, list) ->
        list.parseContent()
    }
}

internal fun List<Message>.parseContent(): Any {
    val commonBytes = ByteArrayOutputStream()
    this.sortedBy { it.part }.forEach {
        commonBytes.writeBytes(it.data)
    }
    commonBytes.flush()
    return ByteArrayInputStream(commonBytes.toByteArray()).let {
        ObjectInputStream(it).run {
            readObject()
        }
    }
}