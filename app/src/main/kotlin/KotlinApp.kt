package kotlingithub

import java.net.URL
import java.net.HttpURLConnection

import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.DataOutputStream

import kotlin.collections.List;
import kotlin.collections.Map;

import java.lang.reflect.Type

import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.reflect.TypeToken

data class User (
        val login: String,
        val id: Int,
        val node_id: String,
        val avatar_url: String,
        val gravatar_id: String,
        val url: String,
        val html_url: String,
        val followers_url: String,
        val following_url: String,
        val gists_url: String,
        val starred_url: String,
        val subscriptions_url: String,
        val organizations_url: String,
        val repos_url: String,
        val events_url: String,
        val received_events_url: String,
        val type: String,
        val site_admin: Boolean,
        val contributions: Int
    ) 

class KotlinApp {
    var url: URL = URL("https://api.github.com/repos/freeCodeCamp/freeCodeCamp/contributors")
    var token: String = " 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"

    public fun get() {
        var url: URL = this.url
        val conn: HttpURLConnection = url.openConnection() as HttpURLConnection

        conn.setRequestProperty("Authorization","Bearer "+ this.token)
        conn.setRequestMethod("GET")

        val responseCode: Int = conn.responseCode
        if (responseCode == HttpURLConnection.HTTP_OK) {
            val response: String = conn.inputStream.bufferedReader().use { it.readText() }
            
            val gson = Gson();
            val listType: Type = object : TypeToken<List<User>>() {}.getType();
            val userList: List<User> = gson.fromJson(response, listType);
            val userSortedList: List<User> = userList.sortedByDescending { it.contributions }
            val sortedMutableList: List<User> = userSortedList.take(25)

            for (user in sortedMutableList) {
                println(user.contributions);
            }

            
            
        } else {
            println("GET request failed")
        }

        conn.disconnect()
    }
}

fun main() {
    KotlinApp().get()
}
