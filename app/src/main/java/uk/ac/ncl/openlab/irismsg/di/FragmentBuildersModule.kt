package uk.ac.ncl.openlab.irismsg.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import uk.ac.ncl.openlab.irismsg.ui.OrganisationListFragment

@Suppress("unused")
@Module
abstract class FragmentBuildersModule {
    
    @ContributesAndroidInjector
    abstract fun contributeOrganisationListFragment(): OrganisationListFragment
    
}