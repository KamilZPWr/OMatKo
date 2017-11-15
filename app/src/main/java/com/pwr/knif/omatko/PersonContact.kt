package com.pwr.knif.omatko

/**
 * Created by jakub on 15.11.2017.
 */
data class PersonContact(
        val name:String,
        val jobTitle: String,
        val telephoneNumber: String,
        val mailAdress: String,
        val description:String,
        val image: Int) {
}