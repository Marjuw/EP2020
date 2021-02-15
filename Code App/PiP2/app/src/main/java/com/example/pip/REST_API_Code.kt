import com.google.gson.GsonBuilder
import com.google.gson.annotations.SerializedName
//import java.net.http.HttpClient
//import java.net.http.HttpRequest
//import java.net.http.HttpHeaders
//import java.net.http.HttpResponse
import java.net.URI
import java.util.*
import java.util.concurrent.CompletableFuture
import kotlin.time.Duration
import com.google.gson.reflect.TypeToken
import java.net.http.*


fun main() {
    val ressource_users = "/users/"
    val ressource_projects = "/projects/"
    val gson = GsonBuilder().create()

    // Liste aller Nutzer holen und als String speichern und ausgeben
    val getListOfUsersString = httpGetRequestAsync(ressource_users)
    println(getListOfUsersString)

    // Liste aller Projekte holen und als String speichern und ausgeben
    val getListOfProjectsString = httpGetRequestAsync(ressource_projects)
    println(getListOfProjectsString)

    // Wert der die Liste aller User in einem Array vom Typ One_User enthält
    val userListe: List<One_User> = gson.fromJson(getListOfUsersString, Array<One_User>::class.java).toList()

    // Ausgabe aller Nutzer via Nicknamen
    println("Liste aller Nutzer:")
    for (user in userListe) {
        println("Nickname: ${user.nickname}")
    }

    // Einen GET auf einen spezifischen Nutzer ausführen und den Nicknamen ausgeben
    val specificUserID = "46fca541-7592-4dcc-a98a-9b4e354cac45" // die ID eines spezifischen User wird hier gespeichert
    val getSpecificUserString = httpGetRequestAsync(ressource_users ,specificUserID) // hole den JSON String vom spezifischen User aus der Ressource Users
    val specific_user = gson.fromJson(getSpecificUserString, One_User::class.java) // speichere den User in eine Variable vom Typ One_User
    println("Der gewünschte User ist unter dem Nicknamen \"${specific_user.nickname}\" zu finden.")

    // DELETE Request machen
//    val specificUserToDeleteID = "10b2d453-c712-41c0-8be9-1d01e655d2e0"
//    httpDeleteRequestAsync(ressource_users, specificUserToDeleteID) // erst wird die Ressource und dann die ID spezifiziert welche gelöscht werden soll und der Request gesendet


    // Nutzer bernd wird mit Daten gefüllt und dann via POST Request versendet
//    var bernd: One_User = One_User(nickname = "bernd", vorname = "bernd", interessen = listOf(1,2,3),faehigkeiten = listOf(101,102,103), email = "brotbackboy@hefe.com", passwort = "1234hehe" )
//    val jsonUserObject = gson.toJson(bernd) // das One_User Objekt wird in einen JSON String geparset
//    httpPostRequestAsync(ressource_users, jsonUserObject) // der User wird gepostet

    //PUT Request auf bestimmten Nutzer machen
    val specificUserPutID = "46fca541-7592-4dcc-a98a-9b4e354cac45"
    val updatedUser: One_User = One_User(nickname = "berndy", vorname = "Bernard", name = "Das Brot", interessen = listOf(1,2,3),faehigkeiten = listOf(101,102,103), email = "brotbackboy@hefe.com", passwort = "1234hehe" )
    val jsonUserObject = gson.toJson(updatedUser) // das One_User Objekt wird in einen JSON String geparset
    httpPutRequestAsync(ressource_users, specificUserPutID, jsonUserObject) // der User wird gepostet



    // Wert der die Liste aller Projekte in einem Array vom Typ One_Project enthält
    val projectListe: List<One_Project> = gson.fromJson(getListOfProjectsString, Array<One_Project>::class.java).toList()

    // Ausgabe aller Namen der Projekte und der Projektleiter
    println("Projekte und ihre Leiter:")
    for (project in projectListe) {
        println("Name: ${project.name}; Beschreibung: ${project.beschreibung}.")
    }

    // Einen GET auf ein spezifisches Projekt ausführen und den Namen sowie die Beschreibung ausgeben
    val specificProjectID = "fd873e82-c76f-4568-8225-fed46dc21289" // die ID eines spezifischen Projects wird hier gespeichert
    val getSpecificProjectString = httpGetRequestAsync(ressource_projects ,specificProjectID) // hole den JSON String vom spezifischen User aus der Ressource Users
    val specific_project = gson.fromJson(getSpecificProjectString, One_Project::class.java) // speichere den User in eine Variable vom Typ One_User
    println("Das Projekt hat den Namen: \"${specific_project.name}\" mit folgender Beschreibung: \"${specific_project.beschreibung}\".")

    // Projekt seifenkiste wird mit Daten gefüllt und dann via POST Request versendet
//    var seifenkiste: One_Project = One_Project(projektleiter = 221, name = "Seifenkisten bauen!", gewuenschteTeamgroesse = 3,
//        gewuenschteRollen = "Handwerker, Bastler",
//        beschreibung = "Hallo, wir wollen eine Seifenkiste für die Kinder aus der Nachbarschaft bauen und brauchen dabei deine Hilfe!",
//        kategorie = listOf(1,2,3), ausfuehrungsort = "Köln", zweck = listOf(1, 3), teilnehmer = listOf(2, 82) )
//
//    val jsonProjectObject = gson.toJson(seifenkiste) // das One_Project Objekt wird in einen JSON String geparset
//    httpPostRequestAsync(ressource_projects, jsonProjectObject) // der User wird gepostet
}

fun httpGetRequestAsync(ressource : String, id : String? = "") : String {
    var json_res = ""
    val httpClient: HttpClient = HttpClient.newBuilder()
        //.connectTimeout(Duration.ofSeconds(10))
        .connectTimeout(java.time.Duration.ofSeconds(10))
        .build()
    val requestHead = HttpRequest.newBuilder()
        .GET()
        .uri(URI.create("http://localhost:3000$ressource$id"))
        .build()
//        val response = httpClient.send(requestHead, HttpResponse.BodyHandlers.ofString())
    val response = httpClient
        .sendAsync(requestHead, HttpResponse.BodyHandlers.ofString())
        .thenApply(HttpResponse<String>::body)
        .thenAccept { htmlBody -> json_res = htmlBody
            println(htmlBody) }
        .join()

    return json_res
}

fun httpPostRequestAsync(ressource: String, json : String) : String {
    var json_res = ""
    val httpClient: HttpClient = HttpClient.newBuilder()
        //.connectTimeout(Duration.ofSeconds(10))
        .connectTimeout(java.time.Duration.ofSeconds(10))
        .build()
    val requestHead = HttpRequest.newBuilder()
        .uri(URI.create("http://localhost:3000$ressource"))
        .header("Content-Type", "application/json")
        .POST(HttpRequest.BodyPublishers.ofString(json))
        .build()

//        val response = httpClient.send(requestHead, HttpResponse.BodyHandlers.ofString())
    val response = httpClient
        .sendAsync(requestHead, HttpResponse.BodyHandlers.ofString())
        .thenApply(HttpResponse<String>::body)
        .thenAccept { htmlBody -> json_res = htmlBody
            println(htmlBody) }
        .join()

    return json_res
}

fun httpPutRequestAsync(ressource: String, id : String? = "", json : String) : String {
    var json_res = ""
    val httpClient: HttpClient = HttpClient.newBuilder()
        //.connectTimeout(Duration.ofSeconds(10))
        .connectTimeout(java.time.Duration.ofSeconds(10))
        .build()
    val requestHead = HttpRequest.newBuilder()
        .uri(URI.create("http://localhost:3000$ressource$id"))
        .header("Content-Type", "application/json")
        .PUT(HttpRequest.BodyPublishers.ofString(json))
        .build()

//        val response = httpClient.send(requestHead, HttpResponse.BodyHandlers.ofString())
    val response = httpClient
        .sendAsync(requestHead, HttpResponse.BodyHandlers.ofString())
        .thenApply(HttpResponse<String>::body)
        .thenAccept { statusCode -> json_res = statusCode
            println(statusCode) }
        .join()

    return json_res
}

fun httpDeleteRequestAsync(ressource: String, id : String) : String {
    var json_res = ""
    val httpClient: HttpClient = HttpClient.newBuilder()
        //.connectTimeout(Duration.ofSeconds(10))
        .connectTimeout(java.time.Duration.ofSeconds(10))
        .build()
    val requestHead = HttpRequest.newBuilder()
        .DELETE()
        .uri(URI.create("http://localhost:3000$ressource$id"))
        .build()
//        val response = httpClient.send(requestHead, HttpResponse.BodyHandlers.ofString())
    val response = httpClient
        .sendAsync(requestHead, HttpResponse.BodyHandlers.ofString())
        .thenApply(HttpResponse<String>::statusCode)
        .thenAccept { statusCode -> json_res = statusCode.toString()
            println(statusCode) }
        .join()

    return json_res
}


data class One_User(val _id: String? = null,
                    @SerializedName ("nickname") val nickname: String,
                    @SerializedName ("vorname") val vorname: String? = null,
                    @SerializedName ("vorname_sichtbar") val vorname_sichtbar: Boolean? = null,
                    @SerializedName ("name") val name: String? = null,
                    @SerializedName ("name_sichtbar") val name_sichtbar: Boolean? = null,
                    @SerializedName ("strasse") val strasse: String? = null,
                    @SerializedName ("strasse_sichtbar") val strasse_sichtbar: Boolean? = null,
                    @SerializedName ("plz") val plz: String? = null,
                    @SerializedName ("plz_sichtbar") val plz_sichtbar: Boolean? = null,
                    @SerializedName ("ort") val ort: String? = null,
                    @SerializedName ("ort_sichtbar") val ort_sichtbar: Boolean? = null,
                    @SerializedName ("beschreibung") val beschreibung: String? = null,
                    @SerializedName ("interessen") val interessen: List<Int>,
                    @SerializedName ("faehigkeiten") val faehigkeiten: List<Int>,
                    @SerializedName ("avatarPicture") val avatarPicture: String? = null,
                    @SerializedName ("kommunikation") val kommunikation: String? = null,
                    @SerializedName ("email") val email: String,
                    @SerializedName ("passwort") val passwort: String,
                    @SerializedName ("tags_abonniert") val tags_abonniert: List<Int>? = null,
                    @SerializedName ("chats") val chats: List<Int>? = null,
                    @SerializedName ("chat_anfragen") val chat_anfragen: List<Int>? = null,
                    @SerializedName ("__v") val __v: Int? = null)

data class One_Project(val _id: String? = null,
                    @SerializedName ("projektleiter") val projektleiter: Long,
                    @SerializedName ("name") val name: String,
                    @SerializedName ("gewuenschteTeamgroesse") val gewuenschteTeamgroesse: Int? = null,
                    @SerializedName ("gewuenschteRollen") val gewuenschteRollen: String? = null,
                    @SerializedName ("beschreibung") val beschreibung: String,
                    @SerializedName ("kategorie") val kategorie: List<Long>,
                    @SerializedName ("ausfuehrungsort") val ausfuehrungsort: String,
                    @SerializedName ("ausfuehrungsort_sichtbar") val ausfuehrungsort_sichtbar: Boolean? = null,

                    @SerializedName ("plz") val plz: String? = null,
                    @SerializedName ("plz_sichtbar") val plz_sichtbar: Boolean? = null,
                    @SerializedName ("ort") val ort: String? = null,
                    @SerializedName ("ort_sichtbar") val ort_sichtbar: Boolean? = null,
                    @SerializedName ("zweck") val zweck: List<Int>? = null,
                    @SerializedName ("startzeitpunkt") val startzeitpunkt: Int? = null,
                    @SerializedName ("dauer") val dauer: Int? = null,
                    @SerializedName ("anforderung") val anforderung: List<Int>? = null,
                    @SerializedName ("aktuelleTeilnehmerzahl") val aktuelleTeilnehmerzahl: Int? = null,

                    @SerializedName ("teilnehmer") val teilnehmer: List<Int>,
                    @SerializedName ("teilnehmer_sichtbar") val teilnehmer_sichtbar: Boolean? = null,
                    @SerializedName ("avatarPicture") val avatarPicture: String? = null,
                    @SerializedName ("__v") val __v: Int? = null)