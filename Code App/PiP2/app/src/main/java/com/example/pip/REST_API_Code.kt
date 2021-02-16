package com.example.pip

import android.util.Log
import com.google.gson.GsonBuilder
import com.google.gson.annotations.SerializedName
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException
import java.util.*

val gson = GsonBuilder().create()
val ressource_users = "/users/"
val ressource_projects = "/projects/"
val specificUserID = "eb5e03b4-15bc-4bb4-8450-73448265ca2a" // die ID eines spezifischen User wird hier gespeichert
val specificProjectID = "fd873e82-c76f-4568-8225-fed46dc21289" // die ID eines spezifischen Projects wird hier gespeichert


fun main() {
    val gson = GsonBuilder().create()
    val ressource_users = "/users/"
    val ressource_projects = "/projects/"
    val specificUserID = "eb5e03b4-15bc-4bb4-8450-73448265ca2a" // die ID eines spezifischen User wird hier gespeichert
    val specificProjectID = "fd873e82-c76f-4568-8225-fed46dc21289" // die ID eines spezifischen Projects wird hier gespeichert


    // ############################################################ GET Abfragen ############################################################

    // ############################## USERS ##############################
    // Liste aller Nutzer holen und als String speichern und ausgeben
    val getListOfUsersString = okHttpGet(ressource_users)
    println(getListOfUsersString)

    // Wert der die Liste aller User in einem Array vom Typ One_User enthält
    val userListe: List<One_User> = gson.fromJson(getListOfUsersString, Array<One_User>::class.java).toList()

    // Ausgabe aller Nutzer mit Nicknamen
    println("Liste aller Nutzer:")
    for (user in userListe) {
        println("Nickname: ${user.nickname}")
    }

    // Einen GET auf einen spezifischen Nutzer ausführen und den Nicknamen ausgeben
    val getSpecificUserString = okHttpGet(ressource_users ,specificUserID) // hole den JSON String vom spezifischen User aus der Ressource Users
    val specific_user = gson.fromJson(getSpecificUserString, One_User::class.java) // speichere den User in eine Variable vom Typ One_User
    println("Der gewünschte User ist unter dem Nicknamen \"${specific_user.nickname}\" zu finden.")

    // ############################## PROJECTS ##############################

    // Liste aller Projekte holen und als String speichern und ausgeben
    val getListOfProjectsString = okHttpGet(ressource_projects)
    println(getListOfProjectsString)

    // Wert der die Liste aller Projekte in einem Array vom Typ One_Project enthält
    val projectListe: List<One_Project> = gson.fromJson(getListOfProjectsString, Array<One_Project>::class.java).toList()

    // Ausgabe aller Namen der Projekte und der Projektleiter
    println("Projekte und ihre Leiter:")
    for (project in projectListe) {
        println("Name: ${project.name}; Beschreibung: ${project.beschreibung}.")
    }

    // Einen GET auf ein spezifisches Projekt ausführen und den Namen sowie die Beschreibung ausgeben
    val getSpecificProjectString = okHttpGet(ressource_projects ,specificProjectID) // hole den JSON String vom spezifischen User aus der Ressource Users
    val specific_project = gson.fromJson(getSpecificProjectString, One_Project::class.java) // speichere den User in eine Variable vom Typ One_User
    println("Das Projekt hat den Namen: \"${specific_project.name}\" mit folgender Beschreibung: \"${specific_project.beschreibung}\".")

    // ############################################################ POST Abfragen ############################################################

    // Nutzer bernd wird mit Daten gefüllt und dann via POST Request versendet
    var bernd: One_User = One_User(nickname = "bernd222a", vorname = "bernd", interessen = listOf(1,2,3),faehigkeiten = listOf(101,102,103), email = "brotbackboy@hefe.com", passwort = "1234hehe" )
    val jsonUserObject = gson.toJson(bernd) // das One_User Objekt wird in einen JSON String geparset
    okHttpPost(ressource_users, json = jsonUserObject) // der User wird gepostet

    // Projekt seifenkiste wird mit Daten gefüllt und dann via POST Request versendet
    var seifenkiste: One_Project = One_Project(projektleiter = 221, name = "Seifenkisten bauen Teil 2!", gewuenschteTeamgroesse = 3,
        gewuenschteRollen = "Handwerker, Bastler",
        beschreibung = "Hallo, wir wollen eine Seifenkiste für die Kinder aus der Nachbarschaft bauen und brauchen dabei deine Hilfe!",
        kategorie = listOf(1,2,3), ausfuehrungsort = "Köln", zweck = listOf(1, 3), teilnehmer = listOf(2, 82) )
    val jsonProjectObject = gson.toJson(seifenkiste) // das One_Project Objekt wird in einen JSON String geparset
    okHttpPost(ressource_projects, json = jsonProjectObject) // der User wird gepostet

    // ############################################################ PUT Abfragen ############################################################

    //PUT Request auf bestimmten Nutzer machen
    val specificUserPutID = "c54e1bf5-5bc8-4516-a7d4-688d21bc9f03"
    val updatedUser: One_User = One_User(nickname = "berndy der Lockere", vorname = "Bernard", name = "Das Brot", interessen = listOf(1,2,3),faehigkeiten = listOf(101,102,103), email = "brotbackboy@hefe.com", passwort = "1234hehe" )
    val jsonUserObject2 = gson.toJson(updatedUser) // das One_User Objekt wird in einen JSON String geparset
    okHttpPut(ressource_users, specificUserPutID, jsonUserObject2) // der User wird gepostet


    // ############################################################ DELETE Abfragen ############################################################

    // DELETE Request auf einen User machen
//    val specificUserToDeleteID = "46fca541-7592-4dcc-a98a-9b4e354cac45"
//    okHttpDelete(ressource_users, specificUserToDeleteID) // erst wird die Ressource und dann die ID spezifiziert welche gelöscht werden soll und der Request gesendet
//
//    // DELETE Request auf ein Projekt machen
//    val specificProjectToDeleteID = "46fca541-7592-4dcc-a98a-9b4e354cac45"
//    okHttpDelete(ressource_projects, specificProjectToDeleteID) // erst wird die Ressource und dann die ID spezifiziert welche gelöscht werden soll und der Request gesendet
}

fun okHttpGet(URL_RESSOURCE : String, URL_DETAILS : String?= "") : String {
    val client: OkHttpClient = OkHttpClient()
    val BASE_URL = "http://localhost:3000"

    val request: Request = Request.Builder()
        .url(BASE_URL + URL_RESSOURCE + URL_DETAILS)
        .build()

    client.newCall(request).execute().use { response ->
        if (!response.isSuccessful) throw IOException("Unexpected code $response")

        val resJson = response.body!!.string()
        Log.d("HTTP-Request","HTTP Code Response für HTTP-GET: "+response.code)
        return resJson
    }
}

fun okHttpGetAsync(URL_RESSOURCE : String, URL_DETAILS : String?= "") : String {
    val client: OkHttpClient = OkHttpClient()
    val BASE_URL = "http://localhost:3000"
    var resJson : String = ""

    val request: Request = Request.Builder()
        .url(BASE_URL + URL_RESSOURCE + URL_DETAILS)
        .build()

    client.newCall(request).enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            e.printStackTrace()
        }

        override fun onResponse(call: Call, response: Response) {
            response.use {
                if (!response.isSuccessful) throw IOException("Unexpected code $response")

                resJson = response.body!!.string()
                Log.d("HTTP-Request","HTTP Code Response für HTTP-GET-Async: "+response.code)
            }
        }
    })
    return resJson
}

fun okHttpPost(URL_RESSOURCE: String, URL_DETAILS: String? = "", json : String) : String {
    val client: OkHttpClient = OkHttpClient()
    val BASE_URL = "http://localhost:3000"

    val body: RequestBody = json.toRequestBody(
        "application/json".toMediaTypeOrNull()
    )

    val request: Request = Request.Builder()
        .url(BASE_URL + URL_RESSOURCE + URL_DETAILS)
        .post(body)
        .build()

    client.newCall(request).execute().use { response ->
        if (!response.isSuccessful) throw IOException("Unexpected code $response")

        val resJson = response.body!!.string()
        Log.d("HTTP-Request","HTTP Code Response für HTTP-POST: "+response.code)
        return resJson
    }
}

fun okHttpPostAsync(URL_RESSOURCE: String, URL_DETAILS: String? = "", json : String) : String {
    val client: OkHttpClient = OkHttpClient()
    val BASE_URL = "http://localhost:3000"
    var resJson : String = ""

    val body: RequestBody = json.toRequestBody(
        "application/json".toMediaTypeOrNull()
    )

    val request: Request = Request.Builder()
        .url(BASE_URL + URL_RESSOURCE + URL_DETAILS)
        .post(body)
        .build()

    client.newCall(request).enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            e.printStackTrace()
        }

        override fun onResponse(call: Call, response: Response) {
            response.use {
                if (!response.isSuccessful) throw IOException("Unexpected code $response")

                resJson = response.body!!.string()
                Log.d("HTTP-Request","HTTP Code Response für HTTP-POST-Async: "+response.code)
            }
        }
    })
    return resJson
}

fun okHttpPut(URL_RESSOURCE: String, URL_DETAILS: String? = "", json : String) : String {
    val client: OkHttpClient = OkHttpClient()
    val BASE_URL = "http://localhost:3000"

    val body: RequestBody = json.toRequestBody(
        "application/json".toMediaTypeOrNull()
    )

    val request: Request = Request.Builder()
        .url(BASE_URL + URL_RESSOURCE + URL_DETAILS)
        .put(body)
        .build()

    client.newCall(request).execute().use { response ->
        if (!response.isSuccessful) throw IOException("Unexpected code $response")

        val resJson = response.body!!.string()
        Log.d("HTTP-Request","HTTP Code Response für HTTP-PUT: "+response.code)
        return resJson
    }
}

fun okHttpPutAsync(URL_RESSOURCE: String, URL_DETAILS: String? = "", json : String) : String {
    val client: OkHttpClient = OkHttpClient()
    val BASE_URL = "http://localhost:3000"
    var resJson : String = ""

    val body: RequestBody = json.toRequestBody(
        "application/json".toMediaTypeOrNull()
    )

    val request: Request = Request.Builder()
        .url(BASE_URL + URL_RESSOURCE + URL_DETAILS)
        .put(body)
        .build()

    client.newCall(request).enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            e.printStackTrace()
        }

        override fun onResponse(call: Call, response: Response) {
            response.use {
                if (!response.isSuccessful) throw IOException("Unexpected code $response")

                resJson = response.body!!.string()
                Log.d("HTTP-Request","HTTP Code Response für HTTP-PUT-Async: "+response.code)
            }
        }
    })
    return resJson
}

fun okHttpDelete(URL_RESSOURCE : String, URL_DETAILS : String?= "") : String {
    val client: OkHttpClient = OkHttpClient()
    val BASE_URL = "http://localhost:3000"

    val request: Request = Request.Builder()
        .url(BASE_URL + URL_RESSOURCE + URL_DETAILS)
        .delete()
        .build()

    client.newCall(request).execute().use { response ->
        if (!response.isSuccessful) throw IOException("Unexpected code $response")

        val resJson = response.body!!.string()
        Log.d("HTTP-Request","HTTP Code Response für HTTP-DELETE: "+response.code)
        println("Delete Body: "+resJson)
        return resJson
    }
}

fun okHttpDeleteAsync(URL_RESSOURCE : String, URL_DETAILS : String?= "") : String {
    val client: OkHttpClient = OkHttpClient()
    val BASE_URL = "http://localhost:3000"
    var resJson : String = ""

    val request: Request = Request.Builder()
        .url(BASE_URL + URL_RESSOURCE + URL_DETAILS)
        .delete()
        .build()

    client.newCall(request).enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            e.printStackTrace()
        }

        override fun onResponse(call: Call, response: Response) {
            response.use {
                if (!response.isSuccessful) throw IOException("Unexpected code $response")

                resJson = response.body!!.string()
                Log.d("HTTP-Request","HTTP Code Response für HTTP-DELETE-Async: "+response.code)
            }
        }
    })
    return resJson
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