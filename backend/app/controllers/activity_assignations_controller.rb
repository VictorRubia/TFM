class ActivityAssignationsController < ApplicationController
  before_action :set_activity_assignation, only: %i[ show edit update destroy ]

  # GET /activity_assignations or /activity_assignations.json
  def index
    @activity_assignations = ActivityAssignation.all
  end

  # GET /activity_assignations/1 or /activity_assignations/1.json
  def show
  end

  # GET /activity_assignations/new
  def new
    @activity_assignation = ActivityAssignation.new
  end

  # GET /activity_assignations/1/edit
  def edit
  end

  # POST /activity_assignations or /activity_assignations.json
  def create
    @activity_assignation = ActivityAssignation.new(activity_assignation_params)

    respond_to do |format|
      if @activity_assignation.save
        format.html { redirect_to activity_assignation_url(@activity_assignation), notice: "Activity assignation was successfully created." }
        format.json { render :show, status: :created, location: @activity_assignation }
      else
        format.html { render :new, status: :unprocessable_entity }
        format.json { render json: @activity_assignation.errors, status: :unprocessable_entity }
      end
    end
  end

  # PATCH/PUT /activity_assignations/1 or /activity_assignations/1.json
  def update
    respond_to do |format|
      if @activity_assignation.update(activity_assignation_params)
        format.html { redirect_to activity_assignation_url(@activity_assignation), notice: "Activity assignation was successfully updated." }
        format.json { render :show, status: :ok, location: @activity_assignation }
      else
        format.html { render :edit, status: :unprocessable_entity }
        format.json { render json: @activity_assignation.errors, status: :unprocessable_entity }
      end
    end
  end

  # DELETE /activity_assignations/1 or /activity_assignations/1.json
  def destroy
    @activity_assignation.destroy

    respond_to do |format|
      format.html { redirect_to activity_assignations_url, notice: "Activity assignation was successfully destroyed." }
      format.json { head :no_content }
    end
  end

  private
    # Use callbacks to share common setup or constraints between actions.
    def set_activity_assignation
      @activity_assignation = ActivityAssignation.find(params[:id])
    end

    # Only allow a list of trusted parameters through.
    def activity_assignation_params
      params.require(:activity_assignation).permit(:activities_repository_id, :user_id, :account_id)
    end
end
