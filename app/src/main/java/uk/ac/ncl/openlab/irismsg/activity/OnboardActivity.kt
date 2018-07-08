package uk.ac.ncl.openlab.irismsg.activity

import android.content.Intent
import android.net.Uri
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector

import kotlinx.android.synthetic.main.activity_onboard.*
import kotlinx.android.synthetic.main.fragment_onboard.view.*
import uk.ac.ncl.openlab.irismsg.R
import javax.inject.Inject

class OnboardActivity : AppCompatActivity(), HasSupportFragmentInjector {
    
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>
    
    override fun supportFragmentInjector() = dispatchingAndroidInjector
    
    /**
     * The [android.support.v4.view.PagerAdapter] that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * [android.support.v4.app.FragmentStatePagerAdapter].
     */
    private var mSectionsPagerAdapter : SectionsPagerAdapter? = null
    
    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboard)
        
        setSupportActionBar(toolbar)
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)
        
        // Set up the ViewPager with the sections adapter.
        container.adapter = mSectionsPagerAdapter
        
//        fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show()
//        }
    
        login_button.setOnClickListener { _ ->
            startActivityForResult(
                Intent(this, LoginActivity::class.java),
                LoginActivity.REQUEST_LOGIN
            )
        }
        
    }
    
    override fun onActivityResult(requestCode : Int, resultCode : Int, data : Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        
        if (requestCode == LoginActivity.REQUEST_LOGIN) {
            when (resultCode) {
                LoginActivity.RESULT_LOGGED_IN -> finish()
            }
        }
    }
    
    override fun onCreateOptionsMenu(menu : Menu) : Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_onboard, menu)
        return true
    }
    
    override fun onOptionsItemSelected(item : MenuItem) : Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        
        val intent = when (item.itemId) {
            R.id.action_even_more -> Intent(Intent.ACTION_VIEW, Uri.parse("http://irismsg.io"))
            R.id.action_open_lab  -> Intent(Intent.ACTION_VIEW, Uri.parse("http://openlab.ncl.ac.uk"))
            else -> null
        }
    
        if (intent != null) startActivity(intent)
        
        return super.onOptionsItemSelected(item)
    }
    
    
    /**
     * A [FragmentPagerAdapter] that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    inner class SectionsPagerAdapter(fm : FragmentManager) : FragmentPagerAdapter(fm) {
        
        override fun getItem(position : Int) : Fragment {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1)
        }
        
        override fun getCount() = 4
    }
    
    /**
     * A placeholder fragment containing a simple view.
     */
    class PlaceholderFragment : Fragment() {
        
        override fun onCreateView(inflater : LayoutInflater, container : ViewGroup?,
            savedInstanceState : Bundle?
        ) : View? {
            val rootView = inflater.inflate(R.layout.fragment_onboard, container, false)
            rootView.section_label.text = getString(R.string.section_format, arguments?.getInt(ARG_SECTION_NUMBER))
            return rootView
        }
        
        companion object {
            /**
             * The fragment argument representing the section number for this
             * fragment.
             */
            private val ARG_SECTION_NUMBER = "section_number"
            
            /**
             * Returns a new instance of this fragment for the given section
             * number.
             */
            fun newInstance(sectionNumber : Int) : PlaceholderFragment {
                val fragment = PlaceholderFragment()
                val args = Bundle()
                args.putInt(ARG_SECTION_NUMBER, sectionNumber)
                fragment.arguments = args
                return fragment
            }
        }
    }
}
