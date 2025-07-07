package com.volpis.welcome_screen.views

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.volpis.welcome_screen.config.WelcomeScreenConfig
import com.volpis.welcome_screen.WelcomeScreenData

class WelcomeScreenPagerAdapter(
    private val screens: List<WelcomeScreenData>,
    private val config: WelcomeScreenConfig
) : RecyclerView.Adapter<WelcomeScreenPagerAdapter.WelcomeScreenViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WelcomeScreenViewHolder {
        val binding = ViewWelcomeScreenBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return WelcomeScreenViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WelcomeScreenViewHolder, position: Int) {
        holder.bind(screens[position], config)
    }

    override fun getItemCount(): Int = screens.size

    class WelcomeScreenViewHolder(
        private val binding: ViewWelcomeScreenBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(screenData: WelcomeScreenData, config: WelcomeScreenConfig) {
            val welcomeScreenView = WelcomeScreenView(binding.root.context)
            welcomeScreenView.setupScreen(screenData, config)

            // Clear any existing views and add the configured view
            (binding.root as ViewGroup).removeAllViews()
            (binding.root as ViewGroup).addView(welcomeScreenView)
        }
    }
}