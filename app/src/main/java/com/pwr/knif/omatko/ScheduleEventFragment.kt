package com.pwr.knif.omatko

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

enum class DayOfWeek {
    MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY
}

class ScheduleEventFragment : Fragment() {

    lateinit var type: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onAttach(context: Context?) {
        Log.e("TAG", "onAttach")
        super.onAttach(context)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_scheduleevent_list, container, false)

        //type = arguments.getStringArrayList("DAY_AND_TYPE")
        type = arrayListOf("!","2")

        if (view is RecyclerView) {
            val context = view.context
            view.layoutManager = LinearLayoutManager(context)

            //TODO: get real data based on the day of week

            val list = listOf(ScheduleEvent("id","Dzień "+type[0],"Rodzaj "+type[1],
                    "Krótki opis"
                    , "Jest to wykład o niczym, serdecznie nie zapraszam nikogo. Pozdrawiam"),
                    ScheduleEvent("id","Kolejny dziwny wykład","Janusz Polaczek",
                    "Na tym wykładzie nie będzie się nic działo! Jeśli chcesz odespać stracone noce to zapraszam."
                            ,"Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus dictum ex non sollicitudin laoreet. Cras augue nisi, mattis sit amet nunc nec, viverra malesuada quam. Aliquam non condimentum lectus. Nulla et ante congue dui placerat tincidunt a pharetra elit. Maecenas malesuada risus dictum urna aliquam, ut finibus nunc venenatis. Maecenas."),
                    ScheduleEvent("id","Title","Presenter", "Description","Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus dictum ex non sollicitudin laoreet. Cras augue nisi, mattis sit amet nunc nec, viverra malesuada quam. Aliquam non condimentum lectus. Nulla et ante congue dui placerat tincidunt a pharetra elit. Maecenas malesuada risus dictum urna aliquam, ut finibus nunc venenatis. Maecenas."),
                    ScheduleEvent("id","Jakiś dziwny wykład","Jan Kowalski",
                            "Jest to wykład o niczym, serdecznie nie zapraszam nikogo. Pozdrawiam"
                            ,"Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus dictum ex non sollicitudin laoreet. Cras augue nisi, mattis sit amet nunc nec, viverra malesuada quam. Aliquam non condimentum lectus. Nulla et ante congue dui placerat tincidunt a pharetra elit. Maecenas malesuada risus dictum urna aliquam, ut finibus nunc venenatis. Maecenas."),
                    ScheduleEvent("id","Kolejny dziwny wykład","Janusz Polaczek",
                            "Na tym wykładzie nie będzie się nic działo! Jeśli chcesz odespać stracone noce to zapraszam."
                            ,"Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus dictum ex non sollicitudin laoreet. Cras augue nisi, mattis sit amet nunc nec, viverra malesuada quam. Aliquam non condimentum lectus. Nulla et ante congue dui placerat tincidunt a pharetra elit. Maecenas malesuada risus dictum urna aliquam, ut finibus nunc venenatis. Maecenas."),
                    ScheduleEvent("id","Title","Presenter", "Description","Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus dictum ex non sollicitudin laoreet. Cras augue nisi, mattis sit amet nunc nec, viverra malesuada quam. Aliquam non condimentum lectus. Nulla et ante congue dui placerat tincidunt a pharetra elit. Maecenas malesuada risus dictum urna aliquam, ut finibus nunc venenatis. Maecenas."))

            view.adapter = ScheduleEventRecyclerViewAdapter(list)
        }
        return view
    }
}