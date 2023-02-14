class TagsRepositoriesController < ApplicationController
  before_action :set_tags_repository, only: %i[ show edit update destroy ]

  # GET /tags_repositories or /tags_repositories.json
  def index
    # @users = TagsRepository.search(params[:search])
    @tags_repositories = TagsRepository.all
  end

  # GET /tags_repositories/1 or /tags_repositories/1.json
  def show
  end

  # GET /tags_repositories/new
  def new
    @tags_repository = TagsRepository.new
  end

  # GET /tags_repositories/1/edit
  def edit
  end

  # POST /tags_repositories or /tags_repositories.json
  def create
    @tags_repository = TagsRepository.new(tags_repository_params)

    respond_to do |format|
      if @tags_repository.save
        format.html { redirect_to dashboard_tags_url, notice: "Etiqueta añadida." }
        format.html { redirect_to tags_repository_url(@tags_repository), notice: "Tags repository was successfully created." }
        format.json { render :show, status: :created, location: @tags_repository }
      else
        format.html { render :new, status: :unprocessable_entity }
        format.json { render json: @tags_repository.errors, status: :unprocessable_entity }
      end
    end
  end

  # PATCH/PUT /tags_repositories/1 or /tags_repositories/1.json
  def update
    respond_to do |format|
      if @tags_repository.update(tags_repository_params)
        format.html { redirect_to dashboard_tags_url, notice: "Etiqueta actualizada." }
        format.json { render :show, status: :ok, location: @tags_repository }
      else
        format.html { render :edit, status: :unprocessable_entity }
        format.json { render json: @tags_repository.errors, status: :unprocessable_entity }
      end
    end
  end

  # DELETE /tags_repositories/1 or /tags_repositories/1.json
  def destroy
    @tags_repository.destroy

    respond_to do |format|
      format.html { redirect_to tags_repository_url, notice: "Etiqueta eliminada." }
      format.json { head :no_content }
    end
  end

  private
    # Use callbacks to share common setup or constraints between actions.
    def set_tags_repository
      @tags_repository = TagsRepository.find(params[:id])
    end

    # Only allow a list of trusted parameters through.
    def tags_repository_params
      params.require(:tags_repository).permit(:name, :name_wearos, :icon, :account_id, :tags_type, :search)
    end
end
