package uk.ac.ncl.openlab.irismsg.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import uk.ac.ncl.openlab.irismsg.R

class EditOrganisationActivity : AppCompatActivity() {
    
    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_organisation)
    }
    
    companion object {
        const val MODE_ADD = 1
        const val MODE_EDIT = 2
    }
}