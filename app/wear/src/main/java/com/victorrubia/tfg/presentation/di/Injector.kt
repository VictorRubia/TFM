package com.victorrubia.tfg.presentation.di

import com.victorrubia.tfg.presentation.di.activity_confirmation.ActivityConfirmationSubComponent
import com.victorrubia.tfg.presentation.di.activity_type.ActivityTypeSubComponent
import com.victorrubia.tfg.presentation.di.feelings_menu.FeelingsMenuSubComponent
import com.victorrubia.tfg.presentation.di.home.HomeSubComponent
import com.victorrubia.tfg.presentation.di.measuring_menu.MeasuringMenuSubComponent
import com.victorrubia.tfg.presentation.di.measuring_service.MeasuringServiceSubComponent
import com.victorrubia.tfg.presentation.di.status_menu.StatusMenuSubComponent
import com.victorrubia.tfg.presentation.di.user_context_menu.UserContextMenuSubComponent

/**
 * Injector class that holds all the subcomponents
 */
interface Injector {

    /**
     * Creates the [HomeSubComponent].
     *
     * @return the [HomeSubComponent].
     */
    fun createHomeSubComponent() : HomeSubComponent

    /**
     * Creates the [FeelingsMenuSubComponent].
     *
     * @return the [FeelingsMenuSubComponent].
     */
    fun createActivityConfirmationSubComponent() : ActivityConfirmationSubComponent

    /**
     * Creates the [MeasuringMenuSubComponent].
     *
     * @return the [MeasuringMenuSubComponent].
     */
    fun createMeasuringMenuSubComponent() : MeasuringMenuSubComponent

    fun createMeasuringServiceSubComponent() : MeasuringServiceSubComponent

    /**
     * Creates the [FeelingsMenuSubComponent].
     *
     * @return the [FeelingsMenuSubComponent].
     */
    fun createFeelingsMenuSubComponent() : FeelingsMenuSubComponent

    /**
     * Creates the [FeelingsMenuSubComponent].
     *
     * @return the [FeelingsMenuSubComponent].
     */
    fun createActivityTypeSubComponent() : ActivityTypeSubComponent

    fun createStatusMenuSubComponent() : StatusMenuSubComponent

    fun createUserContextMenuSubComponent() : UserContextMenuSubComponent
}