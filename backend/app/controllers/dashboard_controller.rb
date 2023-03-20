class DashboardController < ApplicationController
  before_action -> { rodauth.require_authentication }

  def index
  end

  def create_user
    @users = User.where(account_id: current_account.id)
    render 'dashboard/create_user'
  end

  def activity_user
    @users = User.where(account_id: current_account.id)
    render 'dashboard/activity_user'
  end

  def search_user
    @users = User.where(account_id: current_account.id).search(params[:search])
    render "/dashboard/create_user"
  end

  def search_tags
    @tags = TagsRepository.where(account_id: current_account.id).search(params[:search])
    render "/dashboard/dynamic_tags"
  end

  def search_activity_repo
    @activities = ActivitiesRepository.where(account_id: current_account.id).search(params[:search])
    render "/dashboard/dynamic_activities"
  end

  def activity_repo
    @activities = ActivitiesRepository.where(account_id: current_account.id)
    render 'dashboard/dynamic_activities'
  end

  def configure_activity_repo
    @activity = ActivitiesRepository.find(params[:id])

    @available_tags = TagsRepository.where(account_id: current_account.id)

    @chosen_tags = ActivityTagsAssignation.where(activities_repository_id: @activity.id, account_id: current_account.id)
    if @chosen_tags.present?
      @chosen_tags.each do |tag|
        @available_tags = @available_tags.where.not(id: tag.tags_repository_id)
      end
    end
    @tags_of_current_activity = []
    @chosen_tags.each do |tag|
      @tags_of_current_activity << TagsRepository.where(id: tag.tags_repository_id).first
    end
    render 'dashboard/configure_activity'
  end

  def search_user_activities
    @users = User.where(account_id: current_account.id).search(params[:search])
    render "/dashboard/activity_user"
  end

  def configure_activity_repo_post
    @activity = ActivitiesRepository.find(params[:id])
    @chosen_tags = params[:tag_ids]
    if @chosen_tags.present?
      ActivityTagsAssignation.where(activities_repository_id: @activity.id).delete_all

      @chosen_tags.each do |tag|
        @activity_assignation = ActivityTagsAssignation.new
        @activity_assignation.activities_repository_id = @activity.id
        @activity_assignation.tags_repository_id = tag
        @activity_assignation.account_id = current_account.id
        @activity_assignation.save
      end
    else
      ActivityTagsAssignation.where(activities_repository_id: @activity.id).delete_all
    end
    #
    # ActivityAssignation.save
    # if @activity.save
    #   flash[:success] = "Tags updated successfully."
    # else
    #   flash[:error] = "Failed to update tags."
    # end
    # redirect_to activity_path(@activity)
  end

  def activity_assignation
    @user = User.find(params[:id])
    # Get all activities in ActivitiesRepository
    @all_activities = ActivitiesRepository.where(account_id: current_account.id)

    # Get all activities of the user
    @assigned_activities = ActivityAssignation.where(user_id: @user.id).pluck(:activities_repository_id)

    render 'dashboard/activity_assignation'

  end

  def activity_assign
    user_id = params[:id]
    activity_id = params[:activity_id]

    # Create new activity assignation
    activity_assignation = ActivityAssignation.new(
      user_id: user_id,
      activities_repository_id: activity_id,
      account_id: current_account.id
    )

    # if activity_assignation.save
    #   flash[:success] = "updated successfully."
    # else
    #   flash[:error] = "Failed to update tags."
    # end
    # redirect_to create_user_path


    if activity_assignation.save
      render json: { success: true }, status: :ok
    else
      render json: { success: false, errors: activity_assignation.errors.full_messages }, status: :unprocessable_entity
    end
  end

  def activity_unassign
    activity_id = params[:activity_id]
    user_id = params[:id]

    activity_assignation = ActivityAssignation.find_by(
      activities_repository_id: activity_id,
      user_id: user_id
    )

    if activity_assignation&.destroy
      render json: { success: true }, status: :ok
    else
      render json: { success: false }, status: :unprocessable_entity
    end
  end

  def view_activities
    @user = User.find(params[:id])
  end

  def get_activities
    @activities = Activity.where(user_id: params[:id]).order('start_d DESC')
  end

  def get_activities_sorted
    @activities = Activity.where(user_id: params[:id]).order('end_d DESC')
    @grouped_activities = @activities.group_by{ |t| t.end_d.to_date == DateTime.now.to_date }

    if @grouped_activities[false].present?
      # Create month wise groups of messages
      @day_wise_sorted_activities  = @grouped_activities[false].group_by{ |t| t.created_at.month}
    end
  end

  def activity_details
    @activity = Activity.find(params[:id_activity])
  end

  def tags
    @tags = TagsRepository.where(account_id: current_account.id)
    render  'dashboard/dynamic_tags'
  end

  helper_method :get_activities, :get_activities_sorted, :activity_details, :search_user

end
