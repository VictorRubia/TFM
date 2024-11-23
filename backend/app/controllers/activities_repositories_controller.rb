class ActivitiesRepositoriesController < ApplicationController
  before_action :set_activities_repository, only: %i[ show edit update destroy ]

  # GET /activities_repositories or /activities_repositories.json
  def index
    @activities_repositories = ActivitiesRepository.all
  end

  # GET /activities_repositories/1 or /activities_repositories/1.json
  def show
  end

  # GET /activities_repositories/new
  def new
    @activities_repository = ActivitiesRepository.new
  end

  # GET /activities_repositories/1/edit
  def edit
  end

  # POST /activities_repositories or /activities_repositories.json
  def create
    @activities_repository = ActivitiesRepository.new(activities_repository_params)

    respond_to do |format|
      if @activities_repository.save
        format.html { redirect_to dashboard_activity_repo_url(@activities_repository), notice: "Activities repository was successfully created." }
        format.json { render :show, status: :created, location: @activities_repository }
      else
        format.html { render :new, status: :unprocessable_entity }
        format.json { render json: @activities_repository.errors, status: :unprocessable_entity }
      end
    end
  end

  # PATCH/PUT /activities_repositories/1 or /activities_repositories/1.json
  def update
    respond_to do |format|
      if @activities_repository.update(activities_repository_params)
        format.html { redirect_to dashboard_activity_repo_url(@activities_repository), notice: "Activities repository was successfully updated." }
        format.json { render :show, status: :ok, location: @activities_repository }
      else
        format.html { render :edit, status: :unprocessable_entity }
        format.json { render json: @activities_repository.errors, status: :unprocessable_entity }
      end
    end
  end

  # DELETE /activities_repositories/1 or /activities_repositories/1.json
  def destroy
    @activities_repository.destroy

    respond_to do |format|
      format.html { redirect_to dashboard_activity_repo_url, notice: "Activities repository was successfully destroyed." }
      format.json { head :no_content }
    end
  end

  private
    # Use callbacks to share common setup or constraints between actions.
    def set_activities_repository
      @activities_repository = ActivitiesRepository.find(params[:id])
    end

    # Only allow a list of trusted parameters through.
    def activities_repository_params
      params.require(:activities_repository).permit(:name, :name_wearos, :account_id, :icon)
    end
end
