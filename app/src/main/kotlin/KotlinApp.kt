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

// data class User {
//     private String login;
//     private Int id;
//     private String node_id;
//     private String avatar_url;
//     private String gravatar_id;
//     private String url;
//     private String html_url;
//     private String followers_url;
//     private String following_url;
//     private String gists_url;
//     private String starred_url;
//     private String subscriptions_url;
//     private String organizations_url;
//     private String repos_url;
//     private String events_url;
//     private String received_events_url;
//     private String type;
//     private Boolean site_admin;
//     private Int contributions;

//     public User(
//         String login,
//         Int id,
//         String node_id,
//         String avatar_url,
//         String gravatar_id,
//         String url,
//         String html_url,
//         String followers_url,
//         String following_url,
//         String gists_url,
//         String starred_url,
//         String subscriptions_url,
//         String organizations_url,
//         String repos_url,
//         String events_url,
//         String received_events_url,
//         String type,
//         Boolean site_admin,
//         Int contributions
//         ) 
//         {
//             this.login = login;
//             this.id = id;
//             this.node_id = node_id;
//             this.avatar_url = avatar_url;
//             this.gravatar_id = gravatar_id;
//             this.url = url;
//             this.html_url = html_url;
//             this.followers_url = followers_url;
//             this.following_url = following_url;
//             this.gists_url = gists_url;
//             this.starred_url = starred_url;
//             this.subscriptions_url = subscriptions_url;
//             this.organizations_url = organizations_url;
//             this.repos_url = repos_url;
//             this.events_url = events_url;
//             this.received_events_url = received_events_url;
//             this.type = type;
//             this.site_admin = site_admin;
//             this.contributions = contributions;
//         }
// }

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
    public fun get() {
        var url: URL = URL("https://api.github.com/repos/freeCodeCamp/freeCodeCamp/contributors")
        val conn: HttpURLConnection = url.openConnection() as HttpURLConnection

        conn.setRequestProperty("Authorization","Bearer "+" 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")
        conn.setRequestMethod("GET")

        val responseCode: Int = conn.responseCode
        if (responseCode == HttpURLConnection.HTTP_OK) {
            val response: String = conn.inputStream.bufferedReader().use { it.readText() }
            val gson = Gson();

            val listType: Type = object : TypeToken<List<User>>() {}.getType();
            val userList: List<User> = gson.fromJson(response, listType);
            // val userMutableList: MutableList<User> = mutableListOf()
            // userMutableList.sortBy { it.contributions }
            val newUserList: List<User> = listOf()
            val userSortedList: List<User> = newUserList.sortedByDescending { it.contributions }
            print(userSortedList)
            // val sortedMutableList = userMutableList.take(25).toTypedArray()
            // print(userMutableList.take(25).toTypedArray())
            for (user in userSortedList) {
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
