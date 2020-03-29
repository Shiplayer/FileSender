package stream

import data.Message
import java.io.ByteArrayOutputStream
import java.io.OutputStream
import java.util.*

class MessageStream(private val commonSize: Int = 1024) : OutputStream() {
    private val messagesList = mutableListOf<Message>()
    private val byteArrayOutputStream = ByteArrayOutputStream()
    private var part = 0
    private var messageId = Random().nextLong()

    override fun write(b: Int) {
        if (byteArrayOutputStream.size() == commonSize) {
            byteArrayOutputStream.flush()
            messagesList += Message(
                    messageId,
                    ++part,
                    byteArrayOutputStream.size(),
                    false,
                    byteArrayOutputStream.toByteArray()
            )
            byteArrayOutputStream.reset()
        }
        byteArrayOutputStream.write(b)
    }

    override fun flush() {
        super.flush()
        byteArrayOutputStream.flush()
        messagesList += Message(
                messageId,
                ++part,
                byteArrayOutputStream.size(),
                true,
                byteArrayOutputStream.toByteArray()
        )
        byteArrayOutputStream.reset()
        messageId = Random().nextLong()
    }

    fun getMessages(): List<Message> {
        if (byteArrayOutputStream.size() <= commonSize && byteArrayOutputStream.size() != 0) {
            messagesList += Message(
                    messageId,
                    ++part,
                    byteArrayOutputStream.size(),
                    true,
                    byteArrayOutputStream.toByteArray()
            )
        }
        messagesList.fold(0) {acc, message -> acc + message.length}.also(::println)
        return messagesList
    }
}