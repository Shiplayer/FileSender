import data.Message
import stream.MessageStream
import stream.getContent
import java.io.*

object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        val messageStream = MessageStream()
        val objectMessageStream = ObjectOutputStream(messageStream)
        objectMessageStream.writeObject(SerData(readLine()!!))
        objectMessageStream.flush()
        messageStream.getMessages().getContent().forEach {
            println(it)
        }

        /*.forEach {
            val outputStream = ByteArrayOutputStream()
            val objectWriter = ObjectOutputStream(outputStream)
            objectWriter.writeObject(it)
            objectWriter.flush()
            outputStream.toByteArray().run {
                val inputStream = ByteArrayInputStream(this)
                val inputObject = ObjectInputStream(inputStream)
                inputObject.readObject() as Message
            }
        }*/
        /*val outputStream = ByteArrayOutputStream()
        val objectWriter = ObjectOutputStream(outputStream)
        val innerStream = ByteArrayOutputStream()
        val innerWriter = ObjectOutputStream(innerStream)
        innerWriter.writeObject(SerData(readLine()!!))
        innerWriter.flush()
        println(outputStream.size())
        val data = TestData(0, 1, outputStream.size(), innerStream.toByteArray())
        objectWriter.reset()
        objectWriter.writeObject(data)
        objectWriter.flush()
        outputStream.flush()
        outputStream.close()
        val inputStream = ByteArrayInputStream(outputStream.toByteArray())
        val restoreData = ObjectInputStream(inputStream).run {
            //(it.readObject() as SerData).apply(::println)
            (readObject() as TestData).apply(::println)
        }

        ByteArrayInputStream(restoreData.content).run {
            ObjectInputStream(this).run {
                readObject() as SerData
            }
        }.apply(::println)*/

        /*ByteArrayInputStream(restoreData.content).use {
            val serData = ObjectInputStream(it).run {
                readObject() as SerData
            }
            println("restore text: ${serData.data}")
        }*/
    }

    data class SerData(val data: String): Serializable
}