package com.traumsportzone.live.cricket.tv.scores.streaming.ui.activities

import android.view.Menu
import com.google.android.gms.cast.framework.CastButtonFactory
import com.google.android.gms.cast.framework.media.widget.ExpandedControllerActivity
import com.traumsportzone.live.cricket.tv.scores.R

/////Class is for chromecast functionality
class ExpendedActivity : ExpandedControllerActivity() {


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.menu, menu)
        CastButtonFactory.setUpMediaRouteButton(this, menu, R.id.media_route_menu_item)
        return true
    }
}
