import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.io.PrintWriter
import java.net.InetAddress
import java.net.Socket
import java.io.DataInputStream


fun main() {
    //задаем значения хоста и порта
    val host = "npm.mipt.ru"
    val port = 9048
    val address = InetAddress.getByName(host)

    //создаем сокет и подключаемся к серверу
    val socket: Socket = Socket(address, port)

    //получаем приветствие от сервера
    val reader = DataInputStream(socket.getInputStream())
    val response = reader.readLine()
    println("Response from server: $response")

    //отправляем HELLO\n серверу
    val writer = PrintWriter(OutputStreamWriter(socket.getOutputStream()))
    writer.println("HELLO")     //строка "HELLO"
    writer.flush()

    //получаем требуемый ответ и обробатываем
    var answer: Byte
    for (i in 0..2){
        answer = reader.readByte()
        println("Response from server: $answer")
    }
    val quantity = reader.readByte()
    var answer_array = Array(quantity.toInt(), {0})
    println("Size = $quantity")
    var sum = 0
    for (i in 0..quantity - 1){
        answer_array[i] = reader.readByte().toUByte().toInt()
        println(answer_array[i])
        sum += answer_array[i]
    }
    //отправляем ответ
    writer.println("SUM$sum\n")
    writer.flush()

    val final_response = reader.readLine()
    println("Response from server: $final_response")
    // Закрываем соединение
    socket.close()

}