package com.pwr.knif.omatko.contact

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.text.util.Linkify
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pwr.knif.omatko.R
import kotlinx.android.synthetic.main.fragment_personcontact.view.*

class PersonContactRecyclerViewAdapter(
        private val contactsList: List<PersonContact>
) : RecyclerView.Adapter<PersonContactRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_personcontact, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val contact = contactsList[position]
        holder.fill(contact)
    }

    override fun getItemCount() = contactsList.size

    class ViewHolder(private var contactView: View) : RecyclerView.ViewHolder(contactView) {
        private var personContact: PersonContact? = null

        fun fill(contact: PersonContact) {
            personContact = contact
            contactView.apply {
                tv_personcontact_name.text = contact.name
                tv_personcontact_position.text = contact.position
                tv_personcontact_telephone.text = contact.telephoneNumber
                tv_personcontact_mail.text = contact.mailAddress
                tv_personcontact_description.text = contact.description
                img_personcontact.setImageResource(getImageId(context, contact.imageId))
                Linkify.addLinks(tv_personcontact_telephone, Linkify.PHONE_NUMBERS).toString()
                Linkify.addLinks(tv_personcontact_mail, Linkify.EMAIL_ADDRESSES).toString()
            }
        }

        private fun getImageId(context: Context, imageName: String): Int {
            return context.resources.getIdentifier("drawable/" + imageName, null, context.packageName)
        }
    }
}
