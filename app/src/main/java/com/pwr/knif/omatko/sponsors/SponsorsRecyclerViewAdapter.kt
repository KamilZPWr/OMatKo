package com.pwr.knif.omatko.sponsors

import android.support.v7.widget.RecyclerView
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pwr.knif.omatko.R
import kotlinx.android.synthetic.main.fragment_sponsor.view.*

class SponsorsRecyclerViewAdapter(
        private val sponsorList: List<Sponsor>,
        private val context: Context
) : RecyclerView.Adapter<SponsorsRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.fragment_sponsor, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val sponsor = sponsorList[position]

        holder.fill(sponsor)
    }

    override fun getItemCount(): Int = sponsorList.size

    class ViewHolder(private var sponsorView: View) : RecyclerView.ViewHolder(sponsorView) {

        fun fill(sponsor: Sponsor) {

            sponsorView.apply {
                tv_sponsor_name.text = sponsor.name
                if (sponsor.title != "") {
                    val params = tv_sponsor_title.layoutParams
                    params.height =  ViewGroup.LayoutParams.WRAP_CONTENT
                    tv_sponsor_title.layoutParams = params

                    tv_sponsor_title.text = sponsor.title
                }
                if (sponsor.description != "") {
                    val params = tv_sponsor_description.layoutParams
                    params.height = ViewGroup.LayoutParams.WRAP_CONTENT
                    tv_sponsor_description.layoutParams = params

                    tv_sponsor_description.text = sponsor.description

                }
                iv_sponsor_logo.setImageResource(getImageId(context, sponsor.imageId))
            }
        }

        private fun getImageId(context: Context, imageName: String): Int {
            return context.resources.getIdentifier("drawable/" + imageName, null, context.packageName)
        }
    }


}