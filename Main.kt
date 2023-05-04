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

    //получаем требуемый ответ и обрабатываем
    var answer: Byte
        //считываем первые три символа: "RES"
    for (i in 0..2){
        answer = reader.readByte()
        println("Response from server: $answer - " + answer.toChar())
    }
    val quantity = reader.readByte()    //считываем байт с информацией о длине сообщения
    var answer_array = Array(quantity.toInt(), {0})
    println("Size = $quantity")
    var sum = 0
    for (i in 0..quantity - 1){     //считываем N байтов, переводим их в беззнаковый тип и суммируем
        answer_array[i] = reader.readByte().toUByte().toInt()
        val j =
        println((i+1).toString() + " - " + answer_array[i].toString())
        sum += answer_array[i]      //ответ записывается в переменную sum
    }
    //отправляем ответ
    writer.println("SUM$sum")
    writer.flush()
    println("Sum: $sum")
    //олучаем ответ с тестом "OK"
    val ok = reader.readLine()    //почему то после всех байтов идет "0\n", и мы считываем эту строку
    val final_response = reader.readLine()
    println("Response from server: $final_response")
    //закрываем соединение
    socket.close()
}