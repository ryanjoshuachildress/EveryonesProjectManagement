package com.ryanjoshuachildress.everyonesprojectmanagement

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class LoginLogoutLogAdapter(private val logList : ArrayList<Login_Logout_log>) : RecyclerView.Adapter<LoginLogoutLogAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LoginLogoutLogAdapter.MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.login_logout_log_list_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: LoginLogoutLogAdapter.MyViewHolder, position: Int) {
        val login_Logout_Log : Login_Logout_log = logList[position]
        holder.tvEntry.text = login_Logout_Log.Entry
        holder.tvUser.text = login_Logout_Log.User
        holder.tvTime.text = login_Logout_Log.Time?.toDate().toString()
    }

    override fun getItemCount(): Int {
        return logList.size
    }

    public class MyViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview){
        val tvEntry : TextView = itemview.findViewById(R.id.tvEntry)
        val tvTime : TextView = itemview.findViewById(R.id.tvTime)
        val tvUser : TextView = itemview.findViewById(R.id.tvUser)


    }
}