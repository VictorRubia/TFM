package com.victorrubia.tfg.presentation.di.entry

import com.victorrubia.tfg.presentation.entry.EntryActivity
import dagger.Subcomponent

/**
 * [Subcomponent] that injects required dependencies into [EntryActivity]
 */
@EntryScope
@Subcomponent(modules = [EntryModule::class])
interface EntrySubComponent {
    /**
     * Injects required dependencies into [EntryActivity]
     * @param entryActivity [EntryActivity] in which to inject dependencies
     */
    fun inject(entryActivity: EntryActivity)

    /**
     * Builder for [EntrySubComponent]
     */
    @Subcomponent.Factory
    interface Factory{
        fun create() : EntrySubComponent
    }

}