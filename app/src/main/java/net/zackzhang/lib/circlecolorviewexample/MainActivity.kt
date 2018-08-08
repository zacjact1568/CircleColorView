package net.zackzhang.lib.circlecolorviewexample

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import net.zackzhang.lib.circlecolorview.CircleColorView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ccv.setOnClickListener { (it as CircleColorView).fillColor = Color.CYAN }
    }
}
