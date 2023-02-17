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

data class Contributors (
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
        val name: String,
        val company: String,
        val blog: String,
        val location: String,
        val email: String,
        val hireable: String,
        val bio: String,
        val twitter_username: String,
        val public_repos: Int,
        val public_gists: Int,
        val followers: Int,
        val following: Int,
        val created_at: String,
        val updated_at: String

    ) 
    
class KotlinApp {
    public fun get() {
        var url: URL = URL("https://api.github.com/repos/freeCodeCamp/freeCodeCamp/contributors")
        val conn: HttpURLConnection = url.openConnection() as HttpURLConnection

        conn.setRequestProperty("Authorization","Bearer "+ " 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")
        conn.setRequestMethod("GET")

        val responseCode: Int = conn.responseCode
        if (responseCode == HttpURLConnection.HTTP_OK) {
            val response: String = conn.inputStream.bufferedReader().use { it.readText() }
            val gson = Gson();
            val listType: Type = object : TypeToken<List<Contributors>>() {}.getType();
            val list: List<Contributors> = gson.fromJson(response, listType);
            val sortedList: List<Contributors> = list.sortedByDescending { it.contributions }
            val sortedListSortedMutableList: List<Contributors> = sortedList.take(25)

            for (contributor in sortedListSortedMutableList) {
                var userUrl: URL = URL("https://api.github.com/users/" + contributor.login)
                val userConn: HttpURLConnection = userUrl.openConnection() as HttpURLConnection
                userConn.setRequestMethod("GET")
                val userResponseCode: Int = conn.responseCode

                if (userResponseCode == HttpURLConnection.HTTP_OK) {
                    val userResponse: String = userConn.inputStream.bufferedReader().use { it.readText() }
                    val user: User = gson.fromJson(userResponse, User::class.java);

                    println("username: " + contributor.login + ", contributions: "+ contributor.contributions + ", location: "+ user.location);
                }
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
