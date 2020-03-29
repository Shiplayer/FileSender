package data

import java.io.*

data class Message constructor(
        val messageId: Long,
        val part: Int,
        val length: Int,
        val isEnd: Boolean,
        val data: ByteArray
) : Serializable {

    private lateinit var dataContent: Any

    fun getContent(): Any? {
        if(!isEnd) {
            return null
        }
        return if(::dataContent.isInitialized) {
            dataContent
        } else {
            deserializeData().also {
                dataContent = it
            }
        }
    }

    private fun deserializeData(): Any {
        val inputStream = ByteArrayInputStream(data)
        val objectInputStream = ObjectInputStream(inputStream)
        return objectInputStream.readObject()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Message

        if (messageId != other.messageId) return false
        if (part != other.part) return false
        if (length != other.length) return false
        if (!data.contentEquals(other.data)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = messageId.hashCode()
        result = 31 * result + part
        result = 31 * result + length
        result = 31 * result + data.contentHashCode()
        return result
    }
}