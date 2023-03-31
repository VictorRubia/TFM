class ActivityTagsAssignationsController < ApplicationController
  before_action :set_activity_tags_assignation, only: %i[ show edit update destroy ]

  # GET /activity_tags_assignations or /activity_tags_assignations.json
  def index
    @activity_tags_assignations = ActivityTagsAssignation.all
  end

  # GET /activity_tags_assignations/1 or /activity_tags_assignations/1.json
  def show
  end

  # GET /activity_tags_assignations/new
  def new
    @activity_tags_assignation = ActivityTagsAssignation.new
  end

  # GET /activity_tags_assignations/1/edit
  def edit
  end

  # POST /activity_tags_assignations or /activity_tags_assignations.json
  def create
    @activity_tags_assignation = ActivityTagsAssignation.new(activity_tags_assignation_params)

    respond_to do |format|
      if @activity_tags_assignation.save
        format.html { redirect_to activity_tags_assignation_url(@activity_tags_assignation), notice: "Activity tags assignation was successfully created." }
        format.json { render :show, status: :created, location: @activity_tags_assignation }
      else
        format.html { render :new, status: :unprocessable_entity }
        format.json { render json: @activity_tags_assignation.errors, status: :unprocessable_entity }
      end
    end
  end

  # PATCH/PUT /activity_tags_assignations/1 or /activity_tags_assignations/1.json
  def update
    respond_to do |format|
      if @activity_tags_assignation.update(activity_tags_assignation_params)
        format.html { redirect_to activity_tags_assignation_url(@activity_tags_assignation), notice: "Activity tags assignation was successfully updated." }
        format.json { render :show, status: :ok, location: @activity_tags_assignation }
      else
        format.html { render :edit, status: :unprocessable_entity }
        format.json { render json: @activity_tags_assignation.errors, status: :unprocessable_entity }
      end
    end
  end

  # DELETE /activity_tags_assignations/1 or /activity_tags_assignations/1.json
  def destroy
    @activity_tags_assignation.destroy

    respond_to do |format|
      format.html { redirect_to activity_tags_assignations_url, notice: "Activity tags assignation was successfully destroyed." }
      format.json { head :no_content }
    end
  end

  private
    # Use callbacks to share common setup or constraints between actions.
    def set_activity_tags_assignation
      @activity_tags_assignation = ActivityTagsAssignation.find(params[:id])
    end

    # Only allow a list of trusted parameters through.
    def activity_tags_assignation_params
      params.require(:activity_tags_assignation).permit(:activities_repository_id, :tags_repository_id, :account_id)
    end
end
