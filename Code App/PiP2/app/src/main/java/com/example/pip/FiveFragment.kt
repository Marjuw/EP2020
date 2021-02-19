package com.example.pip


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.REST_API_Client.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FiveFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FiveFragment : Fragment() {

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null



    override fun onCreate(savedInstanceState: Bundle?) {



        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val main: MainActivity = MainActivity()
        main.loggedinUserID

        // Inflate the layout for this fragment
        val v: View= inflater.inflate(com.example.pip.R.layout.activity_profile, container, false)


        // Einen GET auf einen spezifischen Nutzer ausführen
        val getSpecificUserString = okHttpGet(ressource_users ,main.loggedinUserID) // hole den JSON String vom spezifischen User aus der Ressource Users
        val loggedinUser = gson.fromJson(getSpecificUserString, One_User::class.java) // speichere den User in eine Variable vom Typ One_User

        //Ab hier alle Profildaten den Mustereinträgen überschreiben
        val nicknameProfil :TextView =  v.findViewById(R.id.nickname_eigen_feld)   // nickname Textfeld identifizieren
        nicknameProfil.setText(loggedinUser.nickname)  //Mustereintrag von Nickname zu dem Eintrag von Nickname der Get Anfrage überschreiben

        val nameProfil :TextView =  v.findViewById(R.id.name_eigen_feld)
        nameProfil.setText(loggedinUser.vorname)

        val nachnameProfil :TextView =  v.findViewById(R.id.nachname_eigen_feld)
        nachnameProfil.setText(loggedinUser.name)

        val beschreibungProfil :TextView =  v.findViewById(R.id.beschreibung_eigen_feld)
        beschreibungProfil.setText(loggedinUser.beschreibung)

        val kommunikationProfil :TextView =  v.findViewById(R.id.kommunikation_eigen_feld)
        kommunikationProfil.setText(loggedinUser.kommunikation)


        val interessenString = mutableListOf<String>()
        loggedinUser.interessen.forEach { x ->
            main.tagListe?.forEach { y -> if ( y.id == x ) interessenString.add(y.bezeichnung) }
        }

        val faehigkeitenString = mutableListOf<String>()
        loggedinUser.faehigkeiten.forEach { x ->
            main.tagListe?.forEach{ y -> if ( y.id == x) faehigkeitenString.add(y.bezeichnung)}
        }

        val interessenProfil :TextView =  v.findViewById(R.id.interessen_eigen_feld)
        interessenProfil.setText(interessenString.joinToString(separator = ", "))

        val faehigkeitenProfil :TextView =  v.findViewById(R.id.fähigkeiten_eigen_feld)
        faehigkeitenProfil.setText(faehigkeitenString.joinToString(separator = ", "))



        var edit:ImageView= v.findViewById(com.example.pip.R.id.editicon)   //Beim klicken auf Edit wird das Layout gewechselt, damit man Text editieren kann
        edit.setOnClickListener(  View.OnClickListener {

                var profileEditFragment: ProfileEditFragment = ProfileEditFragment()
                var transaction: FragmentTransaction = parentFragmentManager.beginTransaction()
                transaction.replace(this.id, profileEditFragment)
                transaction.commit()


        })


        var bundle=getArguments().toString();  //Ab hier werden gesendete Nachrichten von anderen Fragmenten entgen genommen
        var v1: String= getArguments()?.getString("key").toString()

        var x : TextView= v.findViewById(com.example.pip.R.id.name_eigen_feld)

        if (v1 != "null") {
            x.setText(v1)
        }


        Toast.makeText(this.context, "hallo", Toast.LENGTH_LONG).show()  //Pop Up Nachricht


     /*    var params: LinearLayout.LayoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,  //Text manuell in XML Dynamisch hinzufügen
        LinearLayout.LayoutParams.WRAP_CONTENT);

        params.setMargins(900,1900,5,6);

        val textView: TextView = TextView(this.context) as TextView
        textView.setText("textbeispiel")
        //textView.layoutParams=params
        this.activity?.addContentView(textView,params)

      */


        Log.d("HTTP-Response", "U THERE BRO?")


        // Liste aller Projekte holen und als String speichern
        val getListOfProjectsString = okHttpGet(ressource_projects)

        // Wert der die Liste aller Projekte in einem Array vom Typ One_Project enthält
        val projectListe: List<One_Project> = gson.fromJson(getListOfProjectsString, Array<One_Project>::class.java).toList()

        var loggedinUserProjektListe = mutableListOf<One_Project>()
        for (projekt in projectListe){
            if (projekt.teilnehmer.contains(main.loggedinUserID)) loggedinUserProjektListe.add(projekt)
        }

           //Projekte dynamisch anzeigen lassen
        var scrollbar: LinearLayout=v.findViewById(com.example.pip.R.id.layout_list)

        for(projekt in loggedinUserProjektListe) {

            var projektListenAnforderungenString = mutableListOf<String>()
            projekt.anforderung?.forEach { x ->
                main.tagListe?.forEach { y -> if (y.id == x) projektListenAnforderungenString.add(y.bezeichnung) }
            }

            var projektListenKategorieString = mutableListOf<String>()
            projekt.kategorie.forEach { x ->
                main.tagListe?.forEach { y -> if (y.id == x) projektListenKategorieString.add(y.bezeichnung) }
            }

            var projectsview: View= layoutInflater.inflate(com.example.pip.R.layout.projects,null, false)  //ein Project erzeugen

            var projektEintragName: TextView = projectsview.findViewById(R.id.projektname)
            projektEintragName.setText(projekt.name)

            var projektEintragOrt: TextView = projectsview.findViewById(R.id.projektort)
            projektEintragOrt.setText(projekt.ausfuehrungsort)

            var projektEintragSkills: TextView = projectsview.findViewById(R.id.projektskills)
            projektEintragSkills.setText(projektListenAnforderungenString.joinToString(separator = ", "))

            var projektEintragKategorie: TextView = projectsview.findViewById(R.id.projektkategorie)
            projektEintragKategorie.setText(projektListenKategorieString.joinToString(separator = ", "))

            // der ID Wert des Projekts, den du benötigst wenn du auf den Pfeil klickst um das spezifische Projekt dann zu öffnen
            val projektEintragID = projekt._id

            scrollbar.addView(projectsview)  //Projects erzeugen in diesem Bereich der LayoutListe
        }



        return v


    }


    companion object {

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FiveFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FiveFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }

    }

}