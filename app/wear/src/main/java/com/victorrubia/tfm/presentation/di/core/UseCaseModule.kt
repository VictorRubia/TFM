package com.victorrubia.tfm.presentation.di.core

import com.victorrubia.tfm.domain.repository.*
import com.victorrubia.tfm.domain.usecase.*
import dagger.Module
import dagger.Provides

/**
 * Dagger module that provides use cases.
 */
@Module
class UseCaseModule {

    /**
     * Provides the [NewActivityUseCase] use case.
     *
     * @param activityRepository The repository where the activities are stored.
     * @return The [NewActivityUseCase] use case.
     */
    @Provides
    fun provideNewActivityUseCase(activityRepository: ActivityRepository) : NewActivityUseCase{
        return NewActivityUseCase(activityRepository)
    }

    /**
     * Provides the [GetCurrentActivityUseCase] use case.
     *
     * @param activityRepository The repository where the activities are stored.
     * @return The [GetCurrentActivityUseCase] use case.
     */
    @Provides
    fun provideGetCurrentActivityUseCase(activityRepository: ActivityRepository) : GetCurrentActivityUseCase {
        return GetCurrentActivityUseCase(activityRepository)
    }

    @Provides
    fun provideGetActivitiesAssignedUseCase(activityAssignedRepository: ActivityAssignationRepository) : GetActivitiesAssignedUseCase {
        return GetActivitiesAssignedUseCase(activityAssignedRepository)
    }

    @Provides
    fun provideClearActivitiesAssignedUseCase(activityAssignedRepository: ActivityAssignationRepository) : ClearActivitiesAssignedUseCase {
        return ClearActivitiesAssignedUseCase(activityAssignedRepository)
    }

    /**
     * Provides the [EndActivityUseCase] use case.
     *
     * @param activityRepository The repository where the activities are stored.
     * @return The [EndActivityUseCase] use case.
     */
    @Provides
    fun provideEndActivityUseCase(activityRepository: ActivityRepository) : EndActivityUseCase {
        return EndActivityUseCase(activityRepository)
    }

    /**
     * Provides the [RequestUserUseCase] use case.
     *
     * @param userRepository The repository where the users are stored.
     * @return The [RequestUserUseCase] use case.
     */
    @Provides
    fun provideRequestUserUseCase(userRepository: UserRepository) : RequestUserUseCase{
        return RequestUserUseCase(userRepository)
    }

    /**
     * Provides the [SaveUserUseCase] use case.
     *
     * @param userRepository The repository where the users are stored.
     * @return The [SaveUserUseCase] use case.
     */
    @Provides
    fun provideSaveUserUseCase(userRepository: UserRepository) : SaveUserUseCase {
        return SaveUserUseCase(userRepository)
    }

    /**
     * Provides the [GetUserUseCase] use case.
     *
     * @param userRepository The repository where the users are stored.
     * @return The [GetUserUseCase] use case.
     */
    @Provides
    fun provideGetUserUseCase(userRepository: UserRepository) : GetUserUseCase {
        return GetUserUseCase(userRepository)
    }

    /**
     * Provides the [SavePPGMeasureUseCase] use case.
     *
     * @param ppgMeasureRepository The repository where the ppg measures are stored.
     * @return The [SavePPGMeasureUseCase] use case.
     */
    @Provides
    fun provideSavePPGMeasureUseCase(ppgMeasureRepository: PPGMeasureRepository) : SavePPGMeasureUseCase {
        return SavePPGMeasureUseCase(ppgMeasureRepository)
    }

    @Provides
    fun provideSaveAccelerometerMeasureUseCase(accelerometerMeasureRepository: AccelerometerMeasureRepository) : SaveAccelerometerMeasureUseCase {
        return SaveAccelerometerMeasureUseCase(accelerometerMeasureRepository)
    }

    @Provides
    fun provideSaveGPSMeasureUseCase(gpsMeasureRepository: GPSMeasureRepository) : SaveGPSMeasureUseCase {
        return SaveGPSMeasureUseCase(gpsMeasureRepository)
    }

    @Provides
    fun provideSaveStepMeasureUseCase(stepMeasureRepository: StepMeasureRepository) : SaveStepMeasureUseCase {
        return SaveStepMeasureUseCase(stepMeasureRepository)
    }

    @Provides
    fun provideSaveSignificantMovMeasureUseCase(significantMovMeasureRepository: SignificantMovMeasureRepository) : SaveSignificantMovMeasureUseCase {
        return SaveSignificantMovMeasureUseCase(significantMovMeasureRepository)
    }

    /**
     * Provides the [EndPPGMeasureUseCase] use case.
     *
     * @param ppgMeasureRepository The repository where the ppg measures are stored.
     * @return The [EndPPGMeasureUseCase] use case.
     */
    @Provides
    fun provideEndPPGMeasureUseCase(ppgMeasureRepository: PPGMeasureRepository) : EndPPGMeasureUseCase {
        return EndPPGMeasureUseCase(ppgMeasureRepository)
    }

    @Provides
    fun provideEndAccelerometerMeasureUseCase(accelerometerMeasureRepository: AccelerometerMeasureRepository) : EndAccelerometerMeasureUseCase {
        return EndAccelerometerMeasureUseCase(accelerometerMeasureRepository)
    }

    @Provides
    fun provideEndGPSMeasureUseCase(gpsMeasureRepository: GPSMeasureRepository) : EndGPSMeasureUseCase {
        return EndGPSMeasureUseCase(gpsMeasureRepository)
    }

    @Provides
    fun provideEndStepMeasureUseCase(stepMeasureRepository: StepMeasureRepository) : EndStepMeasureUseCase {
        return EndStepMeasureUseCase(stepMeasureRepository)
    }

    @Provides
    fun provideEndSignificantMovMeasureUseCase(significantMovMeasureRepository: SignificantMovMeasureRepository) : EndSignificantMovMeasureUseCase {
        return EndSignificantMovMeasureUseCase(significantMovMeasureRepository)
    }

    /**
     * Provides the [AddTagUseCase] use case.
     *
     * @param tagRepository The repository where the tags are stored.
     * @return The [AddTagUseCase] use case.
     */
    @Provides
    fun provideAddTagUseCase(tagRepository: TagRepository) : AddTagUseCase {
        return AddTagUseCase(tagRepository)
    }

}
