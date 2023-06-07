package tw.idv.william.androidwebserver.core.service


import android.util.Log
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.runBlocking
import java.lang.reflect.Type
import java.net.CookieHandler
import java.net.CookieManager
import java.net.HttpURLConnection
import java.net.HttpURLConnection.HTTP_OK
import java.net.URL
import java.sql.Timestamp


data class Explore(
    private val id: Integer,
    private val nickname: String,
    private val profile_pic: ByteArray,
    private val create_date: Timestamp,
    private val content: String,
    private val pic: String,
    private val comment: String
)

fun main() {
    val url = "http://localhost:8080/THP101G2-WebServer-School/explore"
    val type = object : TypeToken<List<Explore>>(){}.type
    val list = requestTask<List<Explore>>(url, respBodyType = type)
    println(list)
}

inline fun <reified T> requestTask(
    url: String,
    method: String = "GET",
    reqBody: Any? = null,
    respBodyType: Type = T::class.java,
): T? = runBlocking {
    val deferred: Deferred<T?> = coroutineScope {
        async(Dispatchers.IO) {
            if (CookieHandler.getDefault() == null) {
                CookieHandler.setDefault(CookieManager())
            }
            request<T>(url, method, reqBody, respBodyType)
        }
    }
    deferred.await()
}

inline fun <reified T> request(
    url: String,
    method: String = "GET",
    reqBody: Any? = null,
    respBodyType: Type = T::class.java,
): T? {
    var conn: HttpURLConnection? = null
    var result: T? = null
    try {
        conn = URL(url).openConnection() as HttpURLConnection
        with(conn) {
            requestMethod = method
            setRequestProperty("Content-Type", "application/json; charset=utf-8")
            useCaches = false
            doOutput = reqBody != null
            reqBody?.run {
                outputStream.use {
                    val writer = outputStream.writer()
                    writer.write(GSON.toJson(reqBody))
                    writer.flush()
                }
            }
            if (responseCode == HTTP_OK) {
                inputStream.use {
                    result = GSON.fromJson<T>(inputStream.reader(), respBodyType)
//                    Log.d("=============","$result")
                }
            }
        }
    } finally {
        conn?.disconnect()
    }
    return result
}

val GSON: Gson = GsonBuilder()
    .setDateFormat("MMM d, yyyy, hh:mm:ss a")
    .create();
