package com.pwr.knif.omatko

import android.support.v7.widget.RecyclerView
import android.text.method.LinkMovementMethod
import android.text.util.Linkify
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.pwr.knif.omatko.PersonContactFragment.OnPersonContactListFragmentInteractionListener
import kotlinx.android.synthetic.main.fragment_personcontact.view.*

/**
 * [RecyclerView.Adapter] that can display a [PersonContact] and makes a call to the
 * specified [OnPersonContactListFragmentInteractionListener].
 */
class PersonContactRecyclerViewAdapter(
        val contactsList: List<PersonContact>,
        val listener: OnPersonContactListFragmentInteractionListener?
) : RecyclerView.Adapter<PersonContactRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_personcontact, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val contact = contactsList[position]
        
        holder.fill(contact)

        holder.contactView.setOnClickListener {
            listener?.onListFragmentInteraction(holder.personContact!!)
        }
    }

    override fun getItemCount() = contactsList.size

    class ViewHolder(var contactView: View) : RecyclerView.ViewHolder(contactView) {
        var personContact: PersonContact? = null
        
        fun fill(contact: PersonContact) {
            personContact = contact
            contactView.apply {
                tv_personcontact_name.text = contact.name
                tv_personcontact_position.text = contact.jobTitle
                tv_personcontact_telephone.text = contact.telephoneNumber
                tv_personcontact_mail.text = contact.mailAddress
                tv_personcontact_description.text = contact.description
                img_personcontact.setImageResource(contact.imageId)
                Log.d(this::class.java.name, Linkify.addLinks(tv_personcontact_telephone, Linkify.PHONE_NUMBERS).toString())
                Log.d(this::class.java.name, Linkify.addLinks(tv_personcontact_mail, Linkify.EMAIL_ADDRESSES).toString())
            }
        }

        override fun toString(): String {
            return super.toString() + " '" + contactView.tv_personcontact_name.text + "'"
        }
    }
}
